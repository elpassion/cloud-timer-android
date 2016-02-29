package pl.elpassion.cloudtimer.timer

import org.junit.Rule
import pl.elpassion.cloudtimer.TimerDAO
import pl.elpassion.cloudtimer.currentTimeInMillis
import pl.elpassion.cloudtimer.rule

class TimerActivityTests() {

    @Rule @JvmField
    val rule = rule<TimerActivity> {
        currentTimeInMillis = { 0 }
        TimerDAO.getInstance().deleteAll()
    }
}