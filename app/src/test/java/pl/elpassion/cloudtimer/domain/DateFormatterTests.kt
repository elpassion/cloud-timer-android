package pl.elpassion.cloudtimer.domain

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import pl.elpassion.cloudtimer.TimeConverter
import java.util.*

class DateFormatterTests {

    val ninePM: Long = 1454961600000
    val twoHoursTwentyMinutesAndTwoSeconds: Long = 8402000


    @Before
    fun setUpTimeZone() = TimeZone.setDefault(TimeZone.getTimeZone("Europe/Warsaw"))

    @Test
    fun ifNinePMInMillisecondsIsConvertedCorrectly() {
        val ninePM = TimeConverter.formatFromMilliToTime(ninePM)
        assertEquals("21:00", ninePM)
    }

    @Test
    fun ifOneSecondInMillisecondIsConvertedCorrectly(){
        val oneSecond = TimeConverter.formatFromMilliToMinutes(1000)
        assertEquals("0:01", oneSecond)
    }

    @Test
    fun ifOneMinuteInMillisecondIsConvertedCorrectly(){
        val oneMinute = TimeConverter.formatFromMilliToMinutes(1000 * 60)
        assertEquals("1:00", oneMinute)
    }

    @Test
    fun ifOneHourInMillisecondIsConvertedCorrectly(){
        val oneHour = TimeConverter.formatFromMilliToMinutes(1000 * 60 * 60)
        assertEquals("1:00:00", oneHour)
    }

    @Test
    fun ifTwoHoursTwentyMinutesAndTwoSecondsInMillisecondsAreConvertedToMinutesCorrectly() {
        val oneHourTwentyMinutesAndTwoSeconds = TimeConverter.formatFromMilliToMinutes(twoHoursTwentyMinutesAndTwoSeconds)
        assertEquals("2:20:02", oneHourTwentyMinutesAndTwoSeconds)
    }

    @Test
    fun ifOneMinuteAndTwoSecondsAreConvertedCorrectlyFromMilliToMinutes() {
        val oneMinuteAndTwoSeconds = TimeConverter.formatFromMilliToMinutes(62000)
        assertEquals("1:02", oneMinuteAndTwoSeconds)
    }


}