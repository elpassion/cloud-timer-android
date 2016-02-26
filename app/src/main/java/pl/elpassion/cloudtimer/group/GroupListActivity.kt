package pl.elpassion.cloudtimer.group

import android.content.Context
import android.content.Intent
import android.os.Bundle
import pl.elpassion.cloudtimer.base.CloudTimerActivity
import pl.elpassion.cloudtimer.domain.Timer

class GroupListActivity() : CloudTimerActivity() {
    companion object {
        var noGroupExists = true
        private val timerKey = "Timer"
        fun start(context: Context, timer: Timer) {
            val intent = Intent(context, GroupListActivity::class.java)
            intent.putExtra(timerKey, timer)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (noGroupExists) {
            NewGroupActivity.start(this, intent.extras.getParcelable<Timer>(timerKey))
        }
    }
}