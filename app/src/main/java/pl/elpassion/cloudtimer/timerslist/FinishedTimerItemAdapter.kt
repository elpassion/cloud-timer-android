package pl.elpassion.cloudtimer.timerslist

import android.graphics.PorterDuff
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.triggertrap.seekarc.SeekArc
import pl.elpassion.cloudtimer.R
import pl.elpassion.cloudtimer.adapter.ItemAdapter
import pl.elpassion.cloudtimer.domain.Timer

class FinishedTimerItemAdapter(val timer: Timer) : ItemAdapter {

    override val itemViewType: Int = R.layout.user_timers_list_finished_timer_item

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(itemViewType, parent, false)
        return FinishedTimerHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder) {
        val sharedTimerHolder = holder as FinishedTimerHolder
        sharedTimerHolder.ThumbCounter.time = timer.duration
        sharedTimerHolder.title.text = timer.title
        if (timer.group != null) {
            sharedTimerHolder.shareButton.visibility = View.GONE
            sharedTimerHolder.groupCircle.background.setColorFilter(timer.group.color, PorterDuff.Mode.MULTIPLY)
            sharedTimerHolder.groupCircle.text = (timer.group.name)[0].toString()
            sharedTimerHolder.groupCircle.text = (timer.group.name)[0].toString()
        } else {
            sharedTimerHolder.groupCircle.visibility = View.GONE
            sharedTimerHolder.shareButton.setOnClickListener {
                Log.e("CLICK", " ON Share Button")
            }
        }
        sharedTimerHolder.ThumbCounter.setOnClickListener {
            Log.e("Click", "On duration")
        }
    }

    private inner class FinishedTimerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ThumbCounter = ThumbTimer(itemView.findViewById(R.id.timer_thumb_seekArc_text) as TextView,
                itemView.findViewById(R.id.timer_thumb_seekArc) as SeekArc)
        val title = itemView.findViewById(R.id.finished_timer_title) as TextView
        val shareButton = itemView.findViewById(R.id.finished_timer_share_button) as Button
        val groupCircle = itemView.findViewById(R.id.groupCircle) as TextView
    }
}