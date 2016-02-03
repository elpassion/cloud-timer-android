package pl.elpassion.cloudtimer.domain

class Timer(val uid: String? = null, val title: String, val duration: Long, val endTime: Long, val timeLeft: Long, val group: Group? = null) {

    fun isShared(): Boolean {
        return group != null
    }
}
