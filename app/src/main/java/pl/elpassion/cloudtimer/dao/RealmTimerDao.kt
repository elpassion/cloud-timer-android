package pl.elpassion.cloudtimer.dao

import io.realm.Realm
import io.realm.Sort
import pl.elpassion.cloudtimer.currentTimeInMillis
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
        } finally {
            realm.close()
        }
    }

    private fun <T> inRealmAndTransaction(func: Realm.() -> T): T {
        return inRealm {
            inTransaction {
                func()
            }
        }
    }

    override fun save(timer: Timer): String {
        return inRealmAndTransaction {
            copyToRealmOrUpdate(TimerRealmObject(timer)).uuid
        }
    }

    override fun findAll(): List<Timer> {
        return inRealm {
            where(TimerRealmObject::class.java)
                    .findAll().map { it.createTimer() }
        }
    }

    override fun findOne(uuId: String): Timer {
        return inRealm {
            where(TimerRealmObject::class.java)
                    .equalTo("uuid", uuId).findFirst().createTimer()
        }
    }

    override fun deleteAll() {
        inRealmAndTransaction {
            where(TimerRealmObject::class.java)
                    .findAll().removeAll { true }
        }
    }

    override fun findNextTimerToSchedule(): Timer? {
        return inRealm {
            where(TimerRealmObject::class.java)
                    .greaterThan("endTime", currentTimeInMillis())
                    .findAllSorted("endTime", Sort.ASCENDING).firstOrNull()?.createTimer()
        }
    }

    override fun changeTimerToSynced(uuid: String) {
        inRealmAndTransaction {
            where(TimerRealmObject::class.java)
                    .equalTo("uuid", uuid).findFirst().sync = true
        }

    }
}