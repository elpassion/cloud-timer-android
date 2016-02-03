package pl.elpassion.cloudtimer

import java.util.concurrent.TimeUnit

fun convertTime(hours: Int, minutes: Int, seconds: Int): Long {
    return TimeUnit.MILLISECONDS.convert(hours.toLong(), TimeUnit.HOURS) +
            TimeUnit.MILLISECONDS.convert(minutes.toLong(), TimeUnit.MINUTES) +
            TimeUnit.MILLISECONDS.convert(seconds.toLong(), TimeUnit.SECONDS)
}
