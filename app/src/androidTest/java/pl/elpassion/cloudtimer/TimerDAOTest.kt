package pl.elpassion.cloudtimer

import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.Assert.assertTrue
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import pl.elpassion.cloudtimer.domain.Timer
import java.util.*

@RunWith(AndroidJUnit4::class)
class TimerDAOTest {
    companion object {
        var timer = Timer("test", 2000, System.currentTimeMillis() + 2000)
        var timer2 = Timer("test", 3000, System.currentTimeMillis() + 3000)
    }

    @Rule @JvmField
    public val activity = ActivityTestRule<TimerActivity>(TimerActivity::class.java)

    protected val alarmDao by lazy { TimerDAO.getInstance(activity.getActivity().applicationContext) }

    @Ignore
    @Test
    fun isAlarmCanByAddedToDB() {
        val uid = alarmDao.save(timer)
        timer = Timer(timer.title, timer.duration, timer.endTime, uid)
        assertTrue(alarmDao.findOne(uid).title.equals(timer.title))
    }

    @Test(expected = NoSuchElementException::class)
    fun isTestTimerCanByDeleted(){
        val uid = alarmDao.save(timer2)
        timer2 = Timer(timer2.title, timer2.duration, timer2.endTime, uid)
        alarmDao.deleteOne(uid)
        assertTrue(alarmDao.findOne(uid).title.equals(timer2.title))
    }

    @Test
    fun deleteAllTimers(){
        alarmDao.save(timer)
        alarmDao.save(timer2)
        assertTrue(alarmDao.findAll().isNotEmpty())
        alarmDao.deleteAll()
        assertTrue(alarmDao.findAll().isEmpty())
    }
}