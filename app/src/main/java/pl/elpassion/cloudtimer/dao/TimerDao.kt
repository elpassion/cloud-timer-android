package pl.elpassion.cloudtimer.dao

import pl.elpassion.cloudtimer.domain.Timer

interface TimerDao {

    fun save(timer: Timer): String

    fun findAll(): List<Timer>

    fun findOne(uuId: String): Timer

    fun deleteAll()

    fun findNextTimerToSchedule(): Timer?

    fun changeTimerToSynced(timerUuid: String)
}