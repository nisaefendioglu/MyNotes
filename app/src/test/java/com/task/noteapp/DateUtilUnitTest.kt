package com.task.noteapp

import com.task.noteapp.util.getCurrentDateFormatted
import junit.framework.TestCase.assertEquals
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class DateUtilUnitTest {
    private val date = Date().getCurrentDateFormatted()
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())

    @Test
    fun validateDateFormat() {
        val result = date
        assertEquals(dateFormat, result)
    }

}