package com.development.sotrack.log

import java.util.*

data class Log(
    val id: UUID,
    val date: Int,
    val exactTime: Calendar,
    val tags: String,
    val buttonValue: Int
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Log

        if (id != other.id) return false
        if (date != other.date) return false
        if (exactTime != other.exactTime) return false
        if (!tags.contentEquals(other.tags)) return false
        if (buttonValue != other.buttonValue) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + date
        result = 31 * result + exactTime.hashCode()
        result = 31 * result + tags.hashCode()
        result = 31 * result + buttonValue
        return result
    }


}