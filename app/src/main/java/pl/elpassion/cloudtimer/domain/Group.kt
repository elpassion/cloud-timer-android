package pl.elpassion.cloudtimer.domain

import java.util.*

class Group(val name: String, val invitationToken: String? = null, val users: MutableList<User> = ArrayList(), var color: Int = randomColor()) {

    companion object {
        fun randomColor() : Int {
            val baseColors = intArrayOf(0xFFFF0000.toInt(), 0xFFFF00FF.toInt(), 0xFF0000FF.toInt(),
                    0xFF00FFFF.toInt(), 0xFF00FF00.toInt(), 0xFFFFFF00.toInt(), 0xFFFF0000.toInt())
            return baseColors[Random().nextInt(baseColors.size)]
        }
    }
}
