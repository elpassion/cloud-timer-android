package pl.elpassion.cloudtimer

import java.text.SimpleDateFormat
import java.util.*

object TimeConverter {

    private val fromMilliToTimeFormatter by lazy {
        val formatter = SimpleDateFormat("HH:mm")
        formatter.timeZone = TimeZone.getDefault()
        formatter
    }
    private val twoNumbersWithZeros = "%02d"
    private val hoursConstant = 60 * 60 * 1000
    private val minutesConstant = 60 * 1000
    private val secondsConstant = 1000

    fun formatFromMilliToTime(timeInMilli: Long): String {
        val date = Date(timeInMilli)
        return fromMilliToTimeFormatter.format(date)
    }

    fun formatFromMilliToMinutes(timeInMilli: Long): String {
        val hours = timeInMilli / hoursConstant
        val minutes = timeInMilli % hoursConstant / minutesConstant
        val seconds = timeInMilli % minutesConstant / secondsConstant
        return createStringTime(hours, minutes, seconds)
    }

    private fun createStringTime(hours: Long, minutes: Long, seconds: Long): String {
        val secondsWithZeros = twoNumbersWithZeros.format(seconds)
        val minutesWithZeros = twoNumbersWithZeros.format(minutes)
        if (hours > 0)
            return "$hours:$minutesWithZeros:$secondsWithZeros"
         else
            return "$minutes:$secondsWithZeros"
    }


}