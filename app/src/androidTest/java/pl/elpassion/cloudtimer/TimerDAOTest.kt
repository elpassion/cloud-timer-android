package pl.elpassion.cloudtimer

import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.Assert.assertTrue
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import pl.elpassion.cloudtimer.domain.Timer

@RunWith(AndroidJUnit4::class)
class TimerDAOTest {
    companion object{
        val timer = Timer("test",2000,System.currentTimeMillis()+2000)
    }
    @Rule @JvmField
    public val activity = ActivityTestRule<TimerActivity>(TimerActivity::class.java)

    protected val alarmDao by lazy { TimerDAO.getInstance(activity.getActivity().applicationContext) }

    @Ignore
    @Test
    fun isAlarmCanByAddedToDB() {
       //alarmDao.save(timer)
        assertTrue(alarmDao.findAll().isNotEmpty())
    }
}