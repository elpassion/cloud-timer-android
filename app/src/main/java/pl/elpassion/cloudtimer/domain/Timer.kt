package pl.elpassion.cloudtimer.domain

import java.lang.System.currentTimeMillis
import java.util.*

class Timer(val title: String, val duration: Long, val endTime: Long, val uid: String = randomUUID(), val group: Group? = null, val timeLeft: Long? = null) {
    companion object {
        fun randomUUID(): String {
            return UUID.randomUUID().toString()
        }
    }

    constructor(title: String, duration: Long, uid: String? = null, group: Group? = null, timeLeft: Long? = null)
    : this(title, duration, currentTimeMillis() + duration, uid ?: randomUUID(), group, timeLeft) {
    }

    fun isShared(): Boolean {
        return group != null
    }

    fun isPaused(): Boolean {
        return timeLeft != null
    }

}
