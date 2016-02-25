package pl.elpassion.cloudtimer.groups

import android.content.Context
import android.content.Intent
import android.os.Bundle
import pl.elpassion.cloudtimer.R
import pl.elpassion.cloudtimer.base.CloudTimerActivity

class GroupActivity : CloudTimerActivity() {

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, GroupActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_groups_list)
    }
}