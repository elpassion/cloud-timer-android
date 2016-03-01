package pl.elpassion.cloudtimer.dao

import io.realm.Realm
import pl.elpassion.cloudtimer.domain.Timer

object RealmTimerDao : TimerDao {

    private fun <T> Realm.inTransaction(inTransaction: Realm.() -> T): T {
        beginTransaction()
        val funcResult = inTransaction()
        commitTransaction()
        return funcResult
    }

    private fun <T> inRealm(inRealm: Realm.() -> T): T {
        val realm = Realm.getDefaultInstance()
        try {
            return inRealm(realm)
        } finally{
            realm.close()
        }
    }

    override fun save(timer: Timer): String {
        return inRealm {
            inTransaction {
                copyToRealmOrUpdate(TimerRealmObject(timer)).uuid
            }
        }
    }

    override fun findAll(): List<Timer> {
        return inRealm {
            where(TimerRealmObject::class.java)
                    .findAll().map { it.createTimer() }
        }
    }

    override fun findOne(uuId: String): Timer? {
        return inRealm {
            where(TimerRealmObject::class.java)
                    .equalTo("uuid", uuId).findFirst().createTimer()
        }
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