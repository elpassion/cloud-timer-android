package pl.elpassion.cloudtimer.group


import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import pl.elpassion.cloudtimer.R
import pl.elpassion.cloudtimer.TimeConverter
import pl.elpassion.cloudtimer.adapter.ItemAdapter
import pl.elpassion.cloudtimer.domain.Timer

class NewGroupTimerItemAdapter(val timer: Timer) : ItemAdapter<RecyclerView.ViewHolder>() {
    override val itemViewType: Int = R.layout.new_group_timer_item

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder) {
        val sharedTimerHolder = holder as NewGroupTimerItemAdapter.NewGroupTimerHolder
        sharedTimerHolder.timer.text = TimeConverter.formatFromMilliToTime(timer.endTime)
    }

    override fun onCreateViewHolder(itemView: View): RecyclerView.ViewHolder = NewGroupTimerHolder(itemView)

    private inner class NewGroupTimerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val timer = itemView.findViewById(R.id.new_group_timer_text_view) as TextView
    }
}