package com.asirim.mvvmnewsappstudy.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
fun String.formatStringTime(): String {

    val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    val formatter = SimpleDateFormat("dd.MM.yyyy - HH:mm")

    return formatter.format(parser.parse(this) ?: "Unknown Time")

}