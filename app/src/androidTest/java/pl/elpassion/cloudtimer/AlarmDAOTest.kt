package pl.elpassion.cloudtimer

import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.util.Log
import domain.AlarmData
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AlarmDAOTest {

    @Rule @JvmField
    public val activity = ActivityTestRule<TimerActivity>(TimerActivity::class.java)

    protected val alarmDao by lazy {  AlarmDAO.getInstance(activity.getActivity().applicationContext) }

    @Test
    fun isAlarmCanByAddedToDB(){
        activity
        alarmDao.save(AlarmData(10L, 10L, 666L, null))
        assertTrue(alarmDao.findAll().isNotEmpty())
    }
}