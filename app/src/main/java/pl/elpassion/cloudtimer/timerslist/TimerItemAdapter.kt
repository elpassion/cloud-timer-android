package pl.elpassion.cloudtimer.timerslist

import android.graphics.PorterDuff
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.triggertrap.seekarc.SeekArc
import de.greenrobot.event.EventBus
import pl.elpassion.cloudtimer.R
import pl.elpassion.cloudtimer.TimeConverter
import pl.elpassion.cloudtimer.adapter.ItemAdapter
import pl.elpassion.cloudtimer.domain.Timer

class TimerItemAdapter(val timer: Timer) : ItemAdapter<TimerItemAdapter.TimerHolder>() {

    override val itemViewType: Int = R.layout.user_timers_list_timer_item

    override fun onCreateViewHolder(itemView: View) = TimerHolder(itemView)

    override fun onBindViewHolder(holder: TimerHolder) {
        val timeLeftInMilliSec = timer.endTime - System.currentTimeMillis()
        holder.ThumbCounter.time = timeLeftInMilliSec
        holder.title.text = timer.title
        holder.endTime.text = TimeConverter.formatFromMilliToTime(timer.endTime)
        if (timer.group != null) {
            holder.shareButton.visibility = View.GONE
            holder.groupCircle.background.setColorFilter(timer.group.color, PorterDuff.Mode.MULTIPLY)
            holder.groupCircle.text = (timer.group.name)[0].toString()
        } else {
            holder.groupCircle.visibility = View.GONE
            holder.shareButton.setOnClickListener {
                EventBus.getDefault().post(OnShareTimerButtonClick(timer))
            }
        }
    }

    class TimerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ThumbCounter = ThumbTimer(itemView.findViewById(R.id.timer_thumb_seekArc_text) as TextView,
                itemView.findViewById(R.id.timer_thumb_seekArc) as SeekArc)
        val title = itemView.findViewById(R.id.timer_title) as TextView
        val endTime = itemView.findViewById(R.id.timer_end_time) as TextView
        val shareButton = itemView.findViewById(R.id.timer_share_button) as Button
        val groupCircle = itemView.findViewById(R.id.group_circle) as TextView
    }
}
