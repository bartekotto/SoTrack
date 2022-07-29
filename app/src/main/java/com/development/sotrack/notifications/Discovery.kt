package com.development.sotrack.notifications

import java.util.*

data class Discovery(
    val id : Int,
    val message : String,
    val time : Date,
    val type : String,
    val icon : Int
) {

}
