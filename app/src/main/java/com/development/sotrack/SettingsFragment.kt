package com.development.sotrack

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import com.development.sotrack.log.LogListHolder
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*


class SettingsFragment : Fragment(R.layout.fragment_button) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        spinnerAdapter(view)
        assignButtonClickListeners(view)
    }

    private fun assignButtonClickListeners(view: View) {
        view.findViewById<Button>(R.id.export_button).setOnClickListener {
            writeTextToFile(collectData())
        }
        view.findViewById<Button>(R.id.add_tag_button).setOnClickListener {
            addTag(view)
        }
        view.findViewById<Button>(R.id.remove_tag_button).setOnClickListener {
            removeTag(view)
        }
    }

    private fun addTag(view: View) {
        LogListHolder.tagList += view.findViewById<EditText>(R.id.tag_name_input).text.toString()
        updateSharedPref()
    }

    private fun removeTag(view: View) {
        LogListHolder.tagList -= view.findViewById<Spinner>(R.id.tag_spinner).selectedItem.toString()
        updateSharedPref()
    }

    private fun updateSharedPref() {
        val sharedPreference =
            requireContext().getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        val prefsEditor = sharedPreference.edit()
        val gson = Gson()
        val json = gson.toJson(LogListHolder.tagList)
        prefsEditor.putString("taglist", json)
        prefsEditor.commit()
    }

    private fun spinnerAdapter(view: View) {
        val spinner: Spinner = view.findViewById(R.id.tag_spinner)
        val arrayAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            LogListHolder.tagList.toTypedArray()
        )
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = arrayAdapter

    }

    private fun collectData(): String? {
        var gson: Gson? = null
        gson = GsonBuilder().serializeNulls().setPrettyPrinting().create()
        return gson?.toJson(LogListHolder.logList.list)
    }

    private fun getRandomFileName(): String {
        return Calendar.getInstance().timeInMillis.toString() + ".json"
    }

    private fun writeTextToFile(jsonResponse: String?) {
        if (jsonResponse != "") {
            val clipboard: ClipboardManager? =
                activity?.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager?
            val clip = ClipData.newPlainText("sth", jsonResponse)
            clipboard?.setPrimaryClip(clip)

            val dir = File("//sdcard//Download//")
            val myExternalFile = File(dir, getRandomFileName())
            var fos: FileOutputStream? = null
            try {
                fos = FileOutputStream(myExternalFile)
                fos.write(jsonResponse?.toByteArray())
                fos.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            Toast.makeText(
                context,
                "Information saved to SD card. $myExternalFile",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
