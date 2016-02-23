package pl.elpassion.cloudtimer.domain

import java.util.*

class Group(val name: String, val color: Int, val invitationToken: String? = null, val users: MutableList<User> = ArrayList())
