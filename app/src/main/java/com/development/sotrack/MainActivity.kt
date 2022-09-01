package com.development.sotrack

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.development.sotrack.log.Log
import com.development.sotrack.log.LogList
import com.development.sotrack.log.LogListHolder
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val firstFragment = SettingsFragment()
        val secondFragment = MainFragment()
        val thirdFragment = AnalysisFragment()
        LogListHolder.logList.list = readLogListFromStorage()

        setCurrentFragment(secondFragment)

        findViewById<BottomNavigationView>(R.id.bottomNavigationView).setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> setCurrentFragment(secondFragment)
                R.id.person -> setCurrentFragment(firstFragment)
                R.id.settings -> setCurrentFragment(thirdFragment)

            }
            true
        }
        checkOverlayPermission()
        startService()
    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }

    private fun startService() {
        if (Settings.canDrawOverlays(this)) {
            startForegroundService(Intent(this, ForegroundService::class.java))
        }
    }

    private fun checkOverlayPermission() {
        if (!Settings.canDrawOverlays(this)) {
            // send user to the device settings
            val myIntent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
            startActivity(myIntent)
        }
    }

    override fun onResume() {
        super.onResume()
        readLogListFromStorage()
        startService()
    }

    private fun readLogListFromStorage(): List<Log> {
        val sharedPreference = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        val gson = Gson()
        var json: String = sharedPreference.getString("MyObject", "")!!
        if (json.isNotEmpty())
            LogListHolder.logList.list = gson.fromJson(json, LogList::class.java).list
        json = sharedPreference.getString("taglist", "")!!
        if (json.isNotEmpty())
            LogListHolder.tagList = gson.fromJson(json, List::class.java) as List<String>
        return LogListHolder.logList.list
    }

}
