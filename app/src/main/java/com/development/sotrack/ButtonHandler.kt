package com.development.sotrack

import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import android.view.*
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.core.view.get
import com.development.sotrack.R.id
import com.development.sotrack.R.layout
import com.development.sotrack.log.Log
import com.development.sotrack.log.LogListHolder
import com.google.gson.Gson
import java.util.*


class ButtonHandler(
    private val context: Context
) {
    private val mView: View
    private var mParams: WindowManager.LayoutParams? = null
    private val mWindowManager: WindowManager
    private val layoutInflater: LayoutInflater


    fun open() {
        try {
            if (mView.windowToken == null) {
                if (mView.parent == null) {
                    mWindowManager.addView(mView, mParams)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun assignClickListener(imageButton: ImageButton) {
        imageButton.setOnClickListener {
            val cal: Calendar = Calendar.getInstance()
            var pressedButton = 1
            if (imageButton.id == id.thumb_down) {
                pressedButton = 0
            }
            addLog(cal, pressedButton)
        }
    }

    private fun assignLongClickListener(imageButton: ImageButton) {
        imageButton.setOnLongClickListener {
            var pressedButton = 1
            if (imageButton.id == id.thumb_down) {
                pressedButton = 0
            }
            val spinner = mView.findViewById<Spinner>(id.tag_spinner_hover)
            spinner.visibility = View.VISIBLE
            spinner.adapter = spinnerAdapter()
            spinner.performClick()
            spinner.setSelection(0, false)
            spinner.onItemSelectedListener = onItemSelectedListener(spinner, pressedButton)
            return@setOnLongClickListener true
        }
    }

    private fun onItemSelectedListener(
        spinner: Spinner,
        pressedButton: Int
    ): OnItemSelectedListener {
        val onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val cal: Calendar = Calendar.getInstance()
                cal.time = Date(System.currentTimeMillis())
                addLog(cal, pressedButton, (p1 as TextView).text.toString())
                spinner.visibility = View.GONE

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        return onItemSelectedListener
    }

    private fun spinnerAdapter(): ArrayAdapter<String> {
        val arrayAdapter = ArrayAdapter(
            context,
            android.R.layout.simple_spinner_item,
            LogListHolder.tagList.toTypedArray()
        )
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        return arrayAdapter
    }

    private fun addLog(cal: Calendar, pressedButton: Int, tag: String = "") {
        cal.time = Date(System.currentTimeMillis())
        val newLog = Log(
            UUID.randomUUID(),
            Calendar.DAY_OF_YEAR,
            cal,
            tag,
            pressedButton

        )
        LogListHolder.logList.list += newLog
        val sharedPreference =
            context.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        val prefsEditor = sharedPreference.edit()
        val gson = Gson()
        val json = gson.toJson(LogListHolder.logList)
        prefsEditor.putString("MyObject", json)
        prefsEditor.apply()
    }


    private fun close() {
        try {
            (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).removeView(mView)
            mView.invalidate()
            (mView.parent as ViewGroup).removeAllViews()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

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
        assignLongClickListener(mView.findViewById(id.thumb_up))
        assignLongClickListener(mView.findViewById(id.thumb_down))

        mParams!!.gravity = Gravity.RIGHT
        mWindowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    }
}