package pl.elpassion.cloudtimer.domain

import android.os.Parcel
import android.os.Parcelable
import pl.elpassion.cloudtimer.common.createCreator
import java.util.*

data class Group(val name: String, val color: Int, val invitationToken: String? = null, val users: MutableList<User> = ArrayList()): Parcelable{
    companion object {
        @JvmField final val CREATOR = createCreator { Group(this) }
    }

    override fun describeContents(): Int = 0

    constructor(parcel: Parcel) : this(parcel.readString(), parcel.readInt(),parcel.readString()){
        parcel.readTypedList(users, User.CREATOR)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(color)
        parcel.writeString(invitationToken)
        parcel.writeTypedList(users)
    }

}
