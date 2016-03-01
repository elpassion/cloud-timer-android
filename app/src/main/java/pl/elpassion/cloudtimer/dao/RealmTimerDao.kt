package pl.elpassion.cloudtimer.dao

import io.realm.Realm
import pl.elpassion.cloudtimer.domain.Timer
import java.util.*

object RealmTimerDao : TimerDao {

    private fun Realm.inTransaction(inTransaction: Realm.() -> Unit) {
        beginTransaction()
        inTransaction()
        commitTransaction()
    }

    private fun inRealm(inRealm: Realm.() -> Unit) {
        val realm = Realm.getDefaultInstance();
        inRealm(realm)
        realm.close();
    }

    override fun save(timer: Timer): String {
        inRealm {
            inTransaction {
                copyToRealmOrUpdate(TimerRealmObject(timer))
            }
        }
        return timer.uid
    }

    override fun findAll(): List<Timer> {
        val timers: MutableList<Timer> = ArrayList()
        inRealm {
            val list = where(TimerRealmObject::class.java)
                    .findAll().map { it.createTimer() }
            timers.addAll(list)
        }
        return timers
    }

    override fun findOne(uuId: String): Timer? {
        var timer : Timer? = null
        inRealm {
            timer = where(TimerRealmObject::class.java)
                    .equalTo("uuid", uuId).findFirst().createTimer()
        }
        return timer
    }

    override fun deleteAll() {
        inRealm {
            inTransaction {
                where(TimerRealmObject::class.java)
                .findAll().removeAll { true }
            }
        }
    }

    override fun findNextTimerToSchedule(): Timer? {
        throw UnsupportedOperationException()
    }

    override fun changeTimerToSynced(timerUuid: String) {
        throw UnsupportedOperationException()
    }
}