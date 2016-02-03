package pl.elpassion.cloudtimer.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import pl.elpassion.cloudtimer.adapter.ItemAdapter
import java.util.*

open class BaseAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    protected val adapters = ArrayList<ItemAdapter>()

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
