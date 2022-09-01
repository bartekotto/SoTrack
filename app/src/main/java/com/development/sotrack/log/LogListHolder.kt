package com.development.sotrack.log

import android.app.Application

class LogListHolder : Application() {
    companion object {
        val logList: LogList by lazy { LogList(listOf()) }
        var tagList: List<String> = listOf("")
    }

}