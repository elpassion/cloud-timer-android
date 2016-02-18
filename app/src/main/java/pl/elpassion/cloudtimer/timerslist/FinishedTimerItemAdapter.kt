package pl.elpassion.cloudtimer.timerslist

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import de.greenrobot.event.EventBus
import pl.elpassion.cloudtimer.R
import pl.elpassion.cloudtimer.TimeConverter
import pl.elpassion.cloudtimer.adapter.ItemAdapter
import pl.elpassion.cloudtimer.domain.Timer

class FinishedTimerItemAdapter (val timer: Timer) : ItemAdapter {

    override val itemViewType: Int = R.layout.user_timers_list_finished_timer_item

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(itemViewType, parent, false)
        return FinishedTimerHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder) {
        val sharedTimerHolder = holder as FinishedTimerHolder
        sharedTimerHolder.duration.text = TimeConverter.formatFromMilliToMinutes(timer.duration)
        sharedTimerHolder.title.text = timer.title
        sharedTimerHolder.shareButton.setOnClickListener {
            EventBus.getDefault().post(OnShareTimerButtonClick(timer))
        }
        sharedTimerHolder.duration.setOnClickListener {
            Log.e("Click","On duration")
        }
    }

    private inner class FinishedTimerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val duration = itemView.findViewById(R.id.finished_timer_counter) as TextView
        val title = itemView.findViewById(R.id.finished_timer_title) as TextView
        val shareButton = itemView.findViewById(R.id.finished_timer_share_button) as Button
    }
}