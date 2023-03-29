package com.nisaefendioglu.mynotes.util

import java.text.SimpleDateFormat
import java.util.*

fun Date.getCurrentDateFormatted(): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return sdf.format(this)
}

