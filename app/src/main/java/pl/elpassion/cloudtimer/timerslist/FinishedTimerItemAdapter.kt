package pl.elpassion.cloudtimer.timerslist

import android.graphics.PorterDuff
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.triggertrap.seekarc.SeekArc
import de.greenrobot.event.EventBus
import pl.elpassion.cloudtimer.R
import pl.elpassion.cloudtimer.adapter.ItemAdapter
import pl.elpassion.cloudtimer.domain.Timer

class FinishedTimerItemAdapter(val timer: Timer) : ItemAdapter<FinishedTimerItemAdapter.FinishedTimerHolder>() {

    override val itemViewType: Int = R.layout.user_timers_list_finished_timer_item

    override fun onCreateViewHolder(itemView: View) = FinishedTimerHolder(itemView)

    override fun onBindViewHolder(holder: FinishedTimerHolder) {
        holder.ThumbCounter.time = timer.duration
        holder.title.text = timer.title
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
        holder.ThumbCounter.setOnClickListener {
            Log.e("Click", "On duration")
        }
    }

    class FinishedTimerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ThumbCounter = ThumbTimer(itemView.findViewById(R.id.timer_thumb_seekArc_text) as TextView,
                itemView.findViewById(R.id.timer_thumb_seekArc) as SeekArc)
        val title = itemView.findViewById(R.id.finished_timer_title) as TextView
        val shareButton = itemView.findViewById(R.id.finished_timer_share_button) as Button
        val groupCircle = itemView.findViewById(R.id.group_circle) as TextView
    }
}
