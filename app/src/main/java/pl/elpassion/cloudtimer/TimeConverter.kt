package pl.elpassion.cloudtimer

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit.*

object TimeConverter {

    private val fromMilliToTimeFormatter by lazy {
        val formatter = SimpleDateFormat("HH:mm")
        formatter.timeZone = TimeZone.getDefault()
        formatter
    }
    private val twoNumbersWithZeros = "%02d"

    fun formatFromMilliToTime(timeInMilli: Long): String {
        val date = Date(timeInMilli)
        return fromMilliToTimeFormatter.format(date)
    }

    fun formatFromMilliToMinutes(timeInMilli: Long): String {
        return createStringTime(getHours(timeInMilli), getMinutes(timeInMilli), getSeconds(timeInMilli))
    }

    fun formatShortFromMilliToMinutes(timeInMilli: Long): String {
        return createShortTime(getHours(timeInMilli), getMinutes(timeInMilli), getSeconds(timeInMilli))
    }

    private fun createStringTime(hours: Long, minutes: Long, seconds: Long): String {
        if (hours > 0)
            return "$hours:${applyFormat(minutes)}:${applyFormat(seconds)}"
        else
            return "$minutes:${applyFormat(seconds)}"
    }

    private fun createShortTime(hours: Long, minutes: Long, seconds: Long): String {
        if (hours > 0)
            return "${hours}h:${applyFormat(minutes)}"
        else
            return "$minutes:${applyFormat(seconds)}"
    }

    private fun applyFormat(number: Long) = twoNumbersWithZeros.format(number)
    private fun getHours(timeInMilli: Long) = MILLISECONDS.toHours(timeInMilli)
    private fun getMinutes(timeInMilli: Long) = MILLISECONDS.toMinutes(timeInMilli) - HOURS.toMinutes(getHours(timeInMilli))
    private fun getSeconds(timeInMilli: Long) = MILLISECONDS.toSeconds(timeInMilli) - MINUTES.toSeconds(getMinutes(timeInMilli)) - HOURS.toSeconds(getHours(timeInMilli))
}