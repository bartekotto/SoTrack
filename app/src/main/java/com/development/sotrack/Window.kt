package com.development.sotrack

import android.app.Service
import android.app.usage.UsageEvents
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.PixelFormat
import android.os.Build
import android.view.*
import android.widget.ImageButton
import com.development.sotrack.R.id
import com.development.sotrack.R.layout
import com.development.sotrack.log.Log
import com.development.sotrack.log.LogListHolder
import java.util.*

class Window(
    private val context: Context
) {
    private val mView: View
    private var mParams: WindowManager.LayoutParams? = null
    private val mWindowManager: WindowManager
    private val layoutInflater: LayoutInflater
    fun open() {
        try {
            // check if the view is already
            // inflated or present in the window
            if (mView.windowToken == null) {
                if (mView.parent == null) {
                    mWindowManager.addView(mView, mParams)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Throws(PackageManager.NameNotFoundException::class)
    fun getForegroundApp(): String {
        var foregroundApp: String? = null

        val mUsageStatsManager =
            context.getSystemService(Service.USAGE_STATS_SERVICE) as UsageStatsManager
        val time = System.currentTimeMillis()

        val usageEvents = mUsageStatsManager.queryEvents(time - 1000 * 3600, time)
        val event = UsageEvents.Event()
        while (usageEvents.hasNextEvent()) {
            usageEvents.getNextEvent(event)
            if (event.eventType == UsageEvents.Event.MOVE_TO_FOREGROUND) {
                foregroundApp = event.packageName
            }
        }
        return foregroundApp.toString()
    }

    private fun assignClickListener(imageButton: ImageButton) {
        imageButton.setOnClickListener {
            val newLog = Log(
                UUID.randomUUID(),
                "temporary",
                Calendar.DAY_OF_YEAR,
                Date(System.currentTimeMillis()),
                getForegroundApp(),
                arrayOf(),
                imageButton.id
            )
            LogListHolder.logList.list += newLog

        }
    }

    fun close() {
        try {
            // remove the view from the window
            (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).removeView(mView)
            // invalidate the view
            mView.invalidate()
            // remove all views
            (mView.parent as ViewGroup).removeAllViews()

            // the above steps are necessary when you are adding and removing
            // the view simultaneously, it might give some exceptions
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

//    private fun screenshot(view: View): File? {
//        val screenshotFile =
//            File(Environment.getExternalStorageDirectory().path, SCREENSHOT_FILE_NAME)
//
//        val view1 = view.findViewById<View>(android.R.id.content).rootView
//        val bitmap = Bitmap.createBitmap(view1.width, view1.height, Bitmap.Config.ARGB_8888)
//        val canvas = Canvas(bitmap)
//        view1.draw(canvas)
//
//        try {
//            saveMediaToStorage(bitmap)
//        } catch (e: java.lang.Exception) {
//            e.printStackTrace()
//        }
//        return null
//
//    }

//    private fun saveMediaToStorage(bitmap: Bitmap) {
//        //Generating a file name
//        val filename = "${System.currentTimeMillis()}.jpg"
//
//        //Output stream
//        var fos: OutputStream? = null
//
//        //For devices running android >= Q
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            //getting the contentResolver
//            context?.contentResolver?.also { resolver ->
//
//                val contentValues = ContentValues().apply {
//
//                    //putting file information in content values
//                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
//                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
//                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
//                }
//
//                //Inserting the contentValues to contentResolver and getting the Uri
//                val imageUri: Uri? =
//                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
//
//                //Opening an outputstream with the Uri that we got
//                fos = imageUri?.let { resolver.openOutputStream(it) }
//            }
//        } else {
//            //These for devices running on android < Q
//            //So I don't think an explanation is needed here
//            val imagesDir =
//                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
//            val image = File(imagesDir, filename)
//            fos = FileOutputStream(image)
//        }
//
//        fos?.use {
//            //Finally writing the bitmap to the output stream that we opened
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
//        }
//    }

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mParams = WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
                        WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH or
                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
            )
        }
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mView = layoutInflater.inflate(layout.popup_button, null)
        mView.findViewById<View>(id.close).setOnClickListener { close() }
        val thumbUp: ImageButton = mView.findViewById(id.thumb_up)
        assignClickListener(mView.findViewById(id.thumb_up))
        assignClickListener(mView.findViewById(id.thumb_down))

        mParams!!.gravity = Gravity.RIGHT
        mWindowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    }
}