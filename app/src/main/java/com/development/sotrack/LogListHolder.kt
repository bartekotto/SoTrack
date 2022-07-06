package com.development.sotrack

import android.app.Application
import android.os.Parcel
import android.os.Parcelable
import java.util.*

class LogListHolder() : Application(), Parcelable {

    val logList: LogList by lazy { LogList(listOf()) }
    constructor(parcel: Parcel) : this() {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LogListHolder> {
        override fun createFromParcel(parcel: Parcel): LogListHolder {
            return LogListHolder(parcel)
        }

        override fun newArray(size: Int): Array<LogListHolder?> {
            return arrayOfNulls(size)
        }
    }

}