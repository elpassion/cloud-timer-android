package pl.elpassion.cloudtimer.domain

import java.util.*

class Group(val name: String, val invitationToken: String? = null, val users: MutableList<User> = ArrayList())
