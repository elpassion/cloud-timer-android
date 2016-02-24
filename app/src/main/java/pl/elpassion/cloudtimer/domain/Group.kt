package pl.elpassion.cloudtimer.domain

import android.graphics.Color
import java.util.*

class Group(val name: String, val invitationToken: String? = null, val users: MutableList<User> = ArrayList(), var color: Int = randomColor()) {

    companion object {
        fun randomColor() : Int {
            val rnd = Random()
            return Color.rgb(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
        }
    }
}
