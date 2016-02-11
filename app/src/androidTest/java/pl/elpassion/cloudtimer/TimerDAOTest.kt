package pl.elpassion.cloudtimer

import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import pl.elpassion.cloudtimer.domain.Group
import pl.elpassion.cloudtimer.domain.Timer
import java.util.*

@RunWith(AndroidJUnit4::class)
class TimerDAOTest {
    companion object {
        val oneSecond = 1000L
        val twoSeconds = 2000L
        val threeSeconds = 3000L
        var timer = Timer("test", twoSeconds)
        var timer2 = Timer("test", threeSeconds)
    }

    @Rule @JvmField
    val activity = ActivityTestRule<TimerActivity>(TimerActivity::class.java)

    protected val alarmDao by lazy { TimerDAO.getInstance(activity.getActivity().applicationContext) }

    @Test
    fun isAlarmCanByAddedToDB() {
        val uid = alarmDao.save(timer)
        timer = Timer(timer.title, timer.duration, timer.endTime, uid)
        assertTrue(alarmDao.findOne(uid).title.equals(timer.title))
    }

    @Test(expected = NoSuchElementException::class)
    fun isTestTimerCanByDeleted() {
        val uid = alarmDao.save(timer2)
        timer2 = Timer(timer2.title, timer2.duration, timer2.endTime, uid)
        alarmDao.deleteOne(uid)
        assertTrue(alarmDao.findOne(uid).title.equals(timer2.title))
    }

    @Test
    fun deleteAllTimers() {
        alarmDao.deleteAll()
        alarmDao.save(Timer("local1", oneSecond))
        alarmDao.save(Timer("local2", twoSeconds))
        assertEquals(2, alarmDao.findAll().size)
        alarmDao.deleteAll()
        assertTrue(alarmDao.findAll().isEmpty())
    }

    @Test
    fun getOnlyLocalTimers() {
        alarmDao.deleteAll()
        val uuIds = arrayOf(
                alarmDao.save(Timer("local1", oneSecond)),
                alarmDao.save(Timer("local2", twoSeconds)),
                alarmDao.save(Timer(
                        title = "notLocal",
                        duration = threeSeconds,
                        group = Group("elParafia"))))
        assertEquals(2, alarmDao.findLocalTimers().size)
        uuIds.forEach { alarmDao.deleteOne(it) }
    }

    @Test
    fun insertTimerWithUUID() {
        val uuid = "testUuId"
        alarmDao.save(Timer(title = "test", duration = oneSecond, uid = uuid))
        assertEquals(alarmDao.findOne(uuid).title, "test")
        alarmDao.deleteOne(uuid)
    }

    @Test
    fun editTimerInDB() {
        val uid = alarmDao.save(Timer("elParafia", oneSecond))
        alarmDao.save(Timer(title = "pralka", duration = twoSeconds, uid = uid))
        assertEquals("pralka", alarmDao.findOne(uid).title)
    }
}