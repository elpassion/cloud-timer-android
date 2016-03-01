package pl.elpassion.cloudtimer.dao

import org.junit.Test
import pl.elpassion.cloudtimer.domain.Timer

class RealmTimerDaoTests(){

    @Test
    fun daoShouldBeAbleToSaveTimer() {
        val timer = Timer("", 200)
        TimerDaoProvider.getInstance().save(timer)
    }
}