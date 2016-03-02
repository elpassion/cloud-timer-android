package pl.elpassion.cloudtimer.domain

import android.os.Parcel
import org.junit.Assert
import org.junit.Test

class TimerParcelableTests {

    val group = Group("grupa1", 1, "123456" , mutableListOf(User("user1", "user1@gmail.com"), User("user2", "user2@gmail.com")))
    val timer = Timer("timer", 2000, 2000, "1234", group, true)
    val timerWithNulls = Timer("timer", 2000, 2000, "1234", null)

    @Test
    fun ifParcelableWorksCorrectly() {
        val parcel = Parcel.obtain()
        timer.writeToParcel(parcel, 0)
        parcel.setDataPosition(0)
        val timerFromParcel = Timer.CREATOR.createFromParcel(parcel)
        Assert.assertEquals(timer, timerFromParcel)
    }

    @Test
    fun parcelablesMethodDescribeContentsShouldAlwaysReturnZero() {
        Assert.assertEquals(0, timer.describeContents())
        Assert.assertEquals(0, group.describeContents())
        Assert.assertEquals(0, group.users[0].describeContents())
    }

    @Test
    fun parcelablesShouldWorkWithNullValues() {
        val parcel = Parcel.obtain()
        timerWithNulls.writeToParcel(parcel, 0)
        parcel.setDataPosition(0)
        val timerFromParcel = Timer.CREATOR.createFromParcel(parcel)
        Assert.assertEquals(timerWithNulls, timerFromParcel)
    }
}