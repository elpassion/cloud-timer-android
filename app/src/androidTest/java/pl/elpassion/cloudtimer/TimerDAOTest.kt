package pl.elpassion.cloudtimer

import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import pl.elpassion.cloudtimer.domain.Timer
import java.util.*

@RunWith(AndroidJUnit4::class)
class TimerDAOTest {
    companion object {
        var timer = Timer("test", 2000, System.currentTimeMillis() + 2000)
        var uid = ""
    }

    @Rule @JvmField
    public val activity = ActivityTestRule<TimerActivity>(TimerActivity::class.java)

    protected val alarmDao by lazy { TimerDAO.getInstance(activity.getActivity().applicationContext) }

    @Test
    fun isAlarmCanByAddedToDB() {
        uid = alarmDao.save(timer)
        timer = Timer(timer.title, timer.duration, timer.endTime, uid)
        assertTrue(alarmDao.findAll().isNotEmpty())
    }

    @Test(expected = NoSuchElementException::class)
    fun isTestTimerCanByDeleted(){
        alarmDao.deleteOne(uid)
        assertTrue(alarmDao.findOne(uid).title.equals(timer.title))
    }
}