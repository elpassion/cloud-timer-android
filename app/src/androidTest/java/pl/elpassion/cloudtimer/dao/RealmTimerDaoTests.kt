package pl.elpassion.cloudtimer.dao

import org.junit.Assert.assertEquals
import org.junit.Test
import pl.elpassion.cloudtimer.domain.Timer

class RealmTimerDaoTests(){

    @Test
    fun daoShouldBeAbleToSaveTimer() {
        val timer = Timer("", 200)
        TimerDaoProvider.getInstance().save(timer)
    }

    @Test
    fun daoShouldReturnListOfTimersWithCorrectTimersCount() {
        val dao = TimerDaoProvider.getInstance()
        val firstTimer = Timer("timer1", 200)
        dao.save(firstTimer)
        val timersSize = dao.findAll().size
        val secondTimer = Timer("timer1", 200)
        dao.save(secondTimer)
        assertEquals(timersSize + 1, dao.findAll().size)
    }
}