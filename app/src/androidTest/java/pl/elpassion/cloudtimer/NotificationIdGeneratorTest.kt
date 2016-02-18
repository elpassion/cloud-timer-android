package pl.elpassion.cloudtimer

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import pl.elpassion.cloudtimer.alarm.NotificationIdGenerator
import pl.elpassion.cloudtimer.alarm.NotificationIdGenerator.sharedPreferences

class NotificationIdGeneratorTest {

    @Before()
    fun wipeSharedPreferences() {
        sharedPreferences.edit().clear().commit()
    }

    @Test
    fun ifAtStartIdHasValueOne(){
        assertEquals(1, NotificationIdGenerator.incrementAndGet())
    }

    @Test
    fun ifNextValueIsIncrementedByOne(){
        var id = NotificationIdGenerator.incrementAndGet()
        assertEquals(++id, NotificationIdGenerator.incrementAndGet())
    }

}