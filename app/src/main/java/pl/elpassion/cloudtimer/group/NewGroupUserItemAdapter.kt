package pl.elpassion.cloudtimer.group

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import pl.elpassion.cloudtimer.R
import pl.elpassion.cloudtimer.adapter.ItemAdapter
import pl.elpassion.cloudtimer.domain.User

class NewGroupUserItemAdapter(val user: User) : ItemAdapter<RecyclerView.ViewHolder>() {

    override val itemViewType: Int = R.layout.group_user_item

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder) {
        val userHolder = holder as NewGroupUserItemAdapter.NewGroupUserHolder
        userHolder.userEmail.text = user.email
    }

    override fun onCreateViewHolder(view: View): RecyclerView.ViewHolder = NewGroupUserHolder(view)

    private inner class NewGroupUserHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userPic = itemView.findViewById(R.id.user_pic_view) as ImageView
        val userEmail = itemView.findViewById(R.id.user_email_text_view) as TextView
    }
}