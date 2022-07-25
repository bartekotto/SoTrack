package com.development.sotrack.log

import android.app.Application
import com.development.sotrack.log.LogList

class LogListHolder() : Application() {
    companion object {
        val logList: LogList by lazy { LogList(listOf()) }
    }

}