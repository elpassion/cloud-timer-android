package pl.elpassion.cloudtimer.domain

import java.lang.System.currentTimeMillis
import java.util.*

class Timer(val title: String, val duration: Long, val endTime: Long = currentTimeMillis() + duration, val uid: String = randomUUID(), val group: Group? = null, val timeLeft: Long? = null) {

    companion object {
        fun randomUUID(): String = UUID.randomUUID().toString()
    }

    fun isShared(): Boolean = this.group != null
    fun isPaused(): Boolean = this.timeLeft != null
}
