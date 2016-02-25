package pl.elpassion.cloudtimer.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import java.util.*

open class BaseRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val adapters: MutableList<ItemAdapter<RecyclerView.ViewHolder>> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val adapter = adapters.first { it.itemViewType == viewType }
        val itemView = LayoutInflater.from(parent.context).inflate(adapter.itemViewType, parent, false)
        return adapter.onCreateViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        adapters[position].onBindBaseViewHolder(holder)
    }

    override fun getItemCount(): Int {
        return adapters.size
    }

    override fun getItemViewType(position: Int): Int {
        return adapters[position].itemViewType
    }
}
