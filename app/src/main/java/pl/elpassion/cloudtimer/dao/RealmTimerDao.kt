package pl.elpassion.cloudtimer.dao

import io.realm.Realm
import pl.elpassion.cloudtimer.domain.Timer

object RealmTimerDao : TimerDao {

    private fun Realm.inTransaction(inTransaction: Realm.() -> Unit){
        beginTransaction()
        inTransaction()
        commitTransaction()
    }

    private fun inRealm(inRealm: Realm.() -> Unit){
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
        throw UnsupportedOperationException()
    }

    override fun findOne(uuId: String): Timer {
        throw UnsupportedOperationException()
    }

    override fun deleteAll() {
        throw UnsupportedOperationException()
    }

    override fun findNextTimerToSchedule(): Timer? {
        throw UnsupportedOperationException()
    }

    override fun changeTimerToSynced(timerUuid: String) {
        throw UnsupportedOperationException()
    }
}