package com.beller.person

import android.widget.DatePicker
import java.text.SimpleDateFormat
import java.util.*

class Extensions {
    fun dateFormatter(datePicker: DatePicker): Date {
        val day: Int = datePicker.dayOfMonth
        val month: Int = datePicker.month
        val year: Int = datePicker.year
        val calendar = Calendar.getInstance()
        calendar[year, month] = day

        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val formatedDate: String = sdf.format(calendar.time)
        return sdf.parse(formatedDate)
    }

    fun formatToDatePicker(date: Date, type: String): Int {
        val cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"))
        cal.time = date
        val year = cal[Calendar.YEAR]
        val month = cal[Calendar.MONTH]
        val day = cal[Calendar.DAY_OF_MONTH]

       // val formattedDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH).parse(date.toString())
        return when (type) {
            "day" -> {
                day
            }
            "month" -> {
                month
            }
            "year" -> {
                year
            }
            else -> {
                0
            }
        }
    }

    fun getActiveRadioButton(active: Boolean): String {
        return when (active) {
            true -> {
                "yes"
            }
            else -> {
                "no"
            }
        }
    }

}