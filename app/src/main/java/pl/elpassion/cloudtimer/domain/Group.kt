package pl.elpassion.cloudtimer.domain

import android.os.Parcel
import android.os.Parcelable
import java.util.*

data class Group(val name: String, val invitationToken: String? = null, val users: MutableList<User> = ArrayList()): Parcelable{
    companion object {
        @JvmField
        final val CREATOR: Parcelable.Creator<Group> = object : Parcelable.Creator<Group> {

            override fun createFromParcel(parcel: Parcel): Group {
                return Group(parcel)
            }

            override fun newArray(size: Int): Array<Group?> {
                return arrayOfNulls(size)
            }
        }
    }

    override fun describeContents(): Int = 0

    constructor(parcel: Parcel) : this(parcel.readString(), parcel.readString()){
        parcel.readTypedList(users, User.CREATOR)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(invitationToken)
        parcel.writeTypedList(users)
    }

}
