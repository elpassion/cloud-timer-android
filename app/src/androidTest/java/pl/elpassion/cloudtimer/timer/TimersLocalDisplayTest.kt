package pl.elpassion.cloudtimer.timer

import org.junit.Rule
import org.junit.Test
import pl.elpassion.cloudtimer.ComponentsTestsUtils.isComponentDisplayed
import pl.elpassion.cloudtimer.ComponentsTestsUtils.isComponentNotDisplayed
import pl.elpassion.cloudtimer.R
import pl.elpassion.cloudtimer.dao.TimerDaoProvider
import pl.elpassion.cloudtimer.domain.Timer
import pl.elpassion.cloudtimer.rule
import pl.elpassion.cloudtimer.timerslist.ListOfTimersActivity

class TimersLocalDisplayTest {
    @Rule @JvmField
    val rule = rule<ListOfTimersActivity> {
        TimerDaoProvider.getInstance().deleteAll()
        TimerDaoProvider.getInstance().save(Timer("test", 10000))
    }

    @Test
    fun localTimerDisplayCorrectly() {
        isComponentDisplayed(R.id.timer_thumb_seekArc)
        isComponentDisplayed(R.id.timer_thumb_seekArc_text)
        isComponentDisplayed(R.id.timer_title)
        isComponentDisplayed(R.id.timer_end_time)
        isComponentDisplayed(R.id.timer_share_button)
        isComponentNotDisplayed(R.id.group_circle)
    }

}