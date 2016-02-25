package pl.elpassion.cloudtimer.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

abstract class ItemAdapter<VH : RecyclerView.ViewHolder> {

    abstract val itemViewType: Int
    abstract fun onCreateViewHolder(parent: ViewGroup): VH
    abstract fun onBindViewHolder(holder: VH)

    fun onBindBaseViewHolder(holder: RecyclerView.ViewHolder) {
        @Suppress("UNCHECKED_CAST")
        onBindViewHolder(holder as VH)
    }
}
