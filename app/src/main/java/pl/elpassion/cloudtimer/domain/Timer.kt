package pl.elpassion.cloudtimer.domain

class Timer(val title: String, val duration: Long, val endTime: Long, val uid: String? = null, val group: Group? = null, val timeLeft: Long? = null) {

    fun isShared(): Boolean {
        return group != null
    }
}
