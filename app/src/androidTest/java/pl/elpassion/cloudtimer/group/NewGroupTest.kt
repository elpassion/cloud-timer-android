package pl.elpassion.cloudtimer.group

import org.junit.Rule
import org.junit.Test
import pl.elpassion.cloudtimer.ComponentsTestsUtils
import pl.elpassion.cloudtimer.R
import pl.elpassion.cloudtimer.TimerDAO
import pl.elpassion.cloudtimer.domain.Timer
import pl.elpassion.cloudtimer.rule

class NewGroupTest {
    @Rule @JvmField
    val rule = rule<NewGroupActivity> {
        val timerDAO = TimerDAO.Companion.getInstance()
        timerDAO.deleteAll()
        timerDAO.save(Timer("title", duration = 10000, uid = "test"))
    }

    @Test
    fun sharedTimerNewGroup() {
        ComponentsTestsUtils.isInRecyclerView(R.id.timers_recycler_view, R.id.new_group_timer_text_view)
    }
}