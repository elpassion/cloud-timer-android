package pl.elpassion.cloudtimer

/**
 * Created by jasiekpor on 02.02.2016.
 */

fun convertTime(hours: Int, minutes: Int): Long {
    return hours * 3600000L + minutes * 60000L
}
