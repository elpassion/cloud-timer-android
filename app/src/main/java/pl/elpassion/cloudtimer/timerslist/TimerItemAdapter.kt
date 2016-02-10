package pl.elpassion.cloudtimer.timerslist

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import pl.elpassion.cloudtimer.LoginActivity
import pl.elpassion.cloudtimer.R
import pl.elpassion.cloudtimer.TimeConverter
import pl.elpassion.cloudtimer.adapter.ItemAdapter
import pl.elpassion.cloudtimer.domain.Timer

class TimerItemAdapter(private val timer: Timer) : ItemAdapter {

    override val itemViewType: Int = R.layout.user_timers_list_timer_item

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(itemViewType, parent, false)
        return TimerHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder) {
        val sharedTimerHolder = holder as TimerHolder
        val timeLeftInMilliSec = timer.endTime - System.currentTimeMillis()
        sharedTimerHolder.counter.text = TimeConverter.formatFromMilliToMinutes(timeLeftInMilliSec)
        sharedTimerHolder.title.text = timer.title
        sharedTimerHolder.endTime.text = TimeConverter.formatFromMilliToTime(timer.endTime)
        sharedTimerHolder.shareButton.setOnClickListener { view ->
            //todo
            login(view)
        }
    }

    private fun login(view: View) =
        view.context.startActivity(Intent(view.context, LoginActivity::class.java))

    private inner class TimerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val counter = itemView.findViewById(R.id.timer_counter) as TextView
        val title = itemView.findViewById(R.id.timer_title) as TextView
        val endTime = itemView.findViewById(R.id.timer_end_time) as TextView
        val shareButton = itemView.findViewById(R.id.timer_share_button) as Button
    }
}