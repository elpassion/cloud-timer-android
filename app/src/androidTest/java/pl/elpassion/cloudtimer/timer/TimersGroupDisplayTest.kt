package pl.elpassion.cloudtimer.timer

import android.graphics.Color
import org.junit.Rule
import org.junit.Test
import pl.elpassion.cloudtimer.ComponentsTestsUtils.isComponentDisplayed
import pl.elpassion.cloudtimer.ComponentsTestsUtils.isComponentNotDisplayed
import pl.elpassion.cloudtimer.R
import pl.elpassion.cloudtimer.dao.TimerDaoProvider
import pl.elpassion.cloudtimer.domain.Group
import pl.elpassion.cloudtimer.domain.Timer
import pl.elpassion.cloudtimer.rule
import pl.elpassion.cloudtimer.timerslist.ListOfTimersActivity

class TimersGroupDisplayTest {
    @Rule @JvmField
    val rule = rule<ListOfTimersActivity> {
        TimerDaoProvider.getInstance().deleteAll()
        TimerDaoProvider.getInstance().save(Timer("test", 10000, group = Group("elParafia", Color.RED)))
    }

    @Test
    fun groupTimerDisplayCorrectly() {
        isComponentDisplayed(R.id.timer_thumb_seekArc)
        isComponentDisplayed(R.id.timer_thumb_seekArc_text)
        isComponentDisplayed(R.id.timer_title)
        isComponentDisplayed(R.id.timer_end_time)
        isComponentDisplayed(R.id.group_circle)
        isComponentNotDisplayed(R.id.timer_share_button)
    }

}