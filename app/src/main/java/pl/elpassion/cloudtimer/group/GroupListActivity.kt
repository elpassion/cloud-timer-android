package pl.elpassion.cloudtimer.group

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import pl.elpassion.cloudtimer.base.CloudTimerActivity

class GroupListActivity() : CloudTimerActivity() {
    companion object {
        var noGroupExists = true
        private val timerUUIDKey = "TIMERUUID"
        fun start(context: Context, sharedTimerUUID: String) {
            Log.e("ACTIVITY", " New Group Activity created")
            val intent = Intent(context, GroupListActivity::class.java)
            intent.putExtra(timerUUIDKey, sharedTimerUUID)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (noGroupExists) {
            NewGroupActivity.start(this, intent.extras.getString(timerUUIDKey))
        }
    }
}