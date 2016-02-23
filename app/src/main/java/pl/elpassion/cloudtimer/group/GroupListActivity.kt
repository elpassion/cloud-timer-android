package pl.elpassion.cloudtimer.group

import android.content.Intent
import android.os.Bundle
import pl.elpassion.cloudtimer.base.CloudTimerActivity

class GroupListActivity() : CloudTimerActivity() {
    companion object {
        var noGroupExists = true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (noGroupExists) {
            val intent = Intent(this, NewGroupActivity::class.java)
            startActivity(intent)
        }
    }
}