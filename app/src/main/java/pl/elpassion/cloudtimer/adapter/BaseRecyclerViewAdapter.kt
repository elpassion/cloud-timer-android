package pl.elpassion.cloudtimer.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import java.util.*

open class BaseRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val adapters: MutableList<ItemAdapter> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        return adapters.first { it.itemViewType == viewType }
                .onCreateViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        adapters[position].onBindViewHolder(holder)
    }

    override fun getItemCount(): Int {
        return adapters.size
    }

    override fun getItemViewType(position: Int): Int {
        return adapters[position].itemViewType
    }
}
