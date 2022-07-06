package com.development.sotrack

import java.util.*

data class Log(
    val id: UUID,
    val screenshot: String,
    val time: Date,
    val app: String,
    val tags: Array<String>,
    val buttonValue: Int
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Log

        if (id != other.id) return false
        if (screenshot != other.screenshot) return false
        if (time != other.time) return false
        if (app != other.app) return false
        if (!tags.contentEquals(other.tags)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + screenshot.hashCode()
        result = 31 * result + time.hashCode()
        result = 31 * result + app.hashCode()
        result = 31 * result + tags.contentHashCode()
        return result
    }

}