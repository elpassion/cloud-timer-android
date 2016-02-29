package pl.elpassion.cloudtimer

import android.graphics.Color
import org.junit.Assert.*
import org.junit.Test
import pl.elpassion.cloudtimer.domain.Group
import pl.elpassion.cloudtimer.domain.Timer
import java.util.*

class TimerDAOTest {
    companion object {
        val oneSecond = 1000L
        val twoSeconds = 2000L
        val threeSeconds = 3000L
        var timer = Timer("test", twoSeconds)
        var timer2 = Timer("test", threeSeconds)
    }

    protected val alarmDao by lazy { TimerDAO.getInstance() }

    @Test
    fun timerShouldBeSavedCorrectly() {
        val timer = Timer("test", 10000, group = Group("elParafia", Color.RED))
        val uid = alarmDao.save(timer)
        assertEquals(timer, alarmDao.findOne(uid))
    }

    @Test(expected = NoSuchElementException::class)
    fun checkIfTimerCanBeDeleted() {
        val uid = alarmDao.save(timer2)
        timer2 = Timer(timer2.title, timer2.duration, timer2.endTime, uid)
        alarmDao.deleteOne(uid)
        assertTrue(alarmDao.findOne(uid).title.equals(timer2.title))
    }

    @Test
    fun deleteAllTimers() {
        alarmDao.deleteAll()
        alarmDao.save(Timer("local1", oneSecond))
        alarmDao.save(Timer("local2", twoSeconds))
        assertEquals(2, alarmDao.findAll().size)
        alarmDao.deleteAll()
        assertTrue(alarmDao.findAll().isEmpty())
    }

    @Test
    fun getOnlyLocalTimers() {
        alarmDao.deleteAll()
        val uuIds = arrayOf(
                alarmDao.save(Timer("local1", oneSecond)),
                alarmDao.save(Timer("local2", twoSeconds)),
                alarmDao.save(Timer(
                        title = "notLocal",
                        duration = threeSeconds,
                        group = Group("elParafia", Color.MAGENTA))))
        assertEquals(2, alarmDao.findLocalTimers().size)
        uuIds.forEach { alarmDao.deleteOne(it) }
    }

    @Test
    fun insertTimerWithUUID() {
        val uuid = "testUuId"
        alarmDao.save(Timer(title = "test", duration = oneSecond, uid = uuid))
        assertEquals(alarmDao.findOne(uuid).title, "test")
        alarmDao.deleteOne(uuid)
    }

    @Test
    fun editTimerInDB() {
        val uid = alarmDao.save(Timer("elParafia", oneSecond))
        alarmDao.save(Timer(title = "pralka", duration = twoSeconds, uid = uid))
        assertEquals("pralka", alarmDao.findOne(uid).title)
    }

    @Test
    fun findNextToSchedule() {
        val firstTimerToScheduleUuid = setUpDBAndReturnFirstToScheduleTimerUuid()
        val foundNextTimerToSchedule = alarmDao.findNextTimerToSchedule()
        assertEquals(firstTimerToScheduleUuid, foundNextTimerToSchedule!!.uid)
    }

    private fun setUpDBAndReturnFirstToScheduleTimerUuid(): String {
        alarmDao.deleteAll()
        alarmDao.save(Timer("", 1000 * 60))
        alarmDao.save(Timer("", -1000 * 60))
        return alarmDao.save(Timer("", 1000 * 49))
    }

    @Test
    fun ifThereAreNoTimersToScheduleMethodShouldReturnNull() {
        alarmDao.deleteAll()
        assertNull(alarmDao.findNextTimerToSchedule())
    }

    @Test
    fun addedSyncTimerShouldBeInsertedToDBCorrectly() {
        alarmDao.deleteAll()
        val timer = Timer("", 1, sync = true)
        val timerUuid = alarmDao.save(timer)
        assertEquals(timer, alarmDao.findOne(timerUuid))
    }


}