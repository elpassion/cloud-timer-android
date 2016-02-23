package pl.elpassion.cloudtimer.group

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import pl.elpassion.cloudtimer.R
import pl.elpassion.cloudtimer.adapter.ItemAdapter
import pl.elpassion.cloudtimer.domain.User

class NewGroupUserItemAdapter(val user: User) : ItemAdapter {

    override val itemViewType: Int = R.layout.group_user_item

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder) {
        val userHolder = holder as NewGroupUserItemAdapter.NewGroupUserHolder
        userHolder.userEmail.text = user.email
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(itemViewType, parent, false)
        return NewGroupUserHolder(view)
    }

    private inner class NewGroupUserHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userPic = itemView.findViewById(R.id.user_pic_view) as ImageView
        val userEmail = itemView.findViewById(R.id.user_email_text_view) as TextView
    }
}