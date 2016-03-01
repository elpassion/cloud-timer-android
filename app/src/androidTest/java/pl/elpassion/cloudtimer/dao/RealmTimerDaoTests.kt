package pl.elpassion.cloudtimer.dao

import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import pl.elpassion.cloudtimer.currentTimeInMillis
import pl.elpassion.cloudtimer.domain.Group
import pl.elpassion.cloudtimer.domain.Timer

class RealmTimerDaoTests() {

    private val dao = TimerDaoProvider.getInstance()

    @Before
    fun setUp(){
        currentTimeInMillis = { 0 }
    }

    @After
    fun cleanUp(){
        currentTimeInMillis = { System.currentTimeMillis() }
    }

    @Test
    fun daoShouldBeAbleToSaveTimer() {
        val timer = Timer("", 200)
        dao.save(timer)
    }

    @Test
    fun daoShouldReturnListOfTimersWithCorrectTimersCount() {
        val firstTimer = Timer("timer1", 200)
        dao.save(firstTimer)
        val timersSize = dao.findAll().size
        val secondTimer = Timer("timer2", 200)
        dao.save(secondTimer)
        assertEquals(timersSize + 1, dao.findAll().size)
    }

    @Test
    fun findOneShouldReturnCorrectObject() {
        val firstTimer = Timer("timer1", 200, 200, "123", Group("name", 1), true)
        dao.save(firstTimer)
        val timerFromDb = dao.findOne(firstTimer.uid)
        assertEquals(firstTimer, timerFromDb)
    }

    @Test
    fun deleteAllShouldDeleteAllObjects() {
        val firstTimer = Timer("timer1", 200)
        dao.save(firstTimer)
        val secondTimer = Timer("timer2", 200)
        dao.save(secondTimer)
        dao.deleteAll()
        assertTrue(dao.findAll().isEmpty())
    }

    @Test
    fun findNextToScheduleShouldReturnCorrectTimer() {
        val firstTimerToScheduleUuid = setUpDBAndReturnFirstToScheduleTimerUuid()
        val foundNextTimerToSchedule = dao.findNextTimerToSchedule()
        assertEquals(firstTimerToScheduleUuid, foundNextTimerToSchedule!!.uid)
    }

    @Test
    fun findNextToScheduleShouldReturnNullIfThereAreNoTimersToSchedule() {
        dao.deleteAll()
        Assert.assertNull(dao.findNextTimerToSchedule())
    }

    private fun setUpDBAndReturnFirstToScheduleTimerUuid(): String {
        dao.deleteAll()
        dao.save(Timer(uid = "1",title="", duration = 1000 * 60))
        dao.save(Timer(uid = "2",title="", duration = -1000 * 60))
        return dao.save(Timer(uid = "3",title="", duration = 1000 * 49))
    }

}