package pl.elpassion.cloudtimer.dao

object TimerDaoProvider {
    fun getInstance() : TimerDao = RealmTimerDao
}