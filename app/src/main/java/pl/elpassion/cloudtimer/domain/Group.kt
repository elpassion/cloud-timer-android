package pl.elpassion.cloudtimer.domain

import android.os.Parcel
import android.os.Parcelable
import pl.elpassion.cloudtimer.common.createCreator
import java.util.*

data class Group(val name: String, val invitationToken: String? = null, var color: Int = randomColor(), val users: MutableList<User> = ArrayList()) : Parcelable {
    companion object {
        @JvmField final val CREATOR = createCreator { Group(this) }
        fun randomColor() : Int {
            val baseColors = intArrayOf(0xFFFF0000.toInt(), 0xFFFF00FF.toInt(), 0xFF0000FF.toInt(),
                    0xFF00FFFF.toInt(), 0xFF00FF00.toInt(), 0xFFFFFF00.toInt(), 0xFFFF0000.toInt())
            return baseColors[Random().nextInt(baseColors.size)]
        }
    }

    override fun describeContents(): Int = 0

    constructor(parcel: Parcel) : this(parcel.readString(),parcel.readString(), parcel.readInt()){
        parcel.readTypedList(users, User.CREATOR)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(invitationToken)
        parcel.writeInt(color)
        parcel.writeTypedList(users)
    }

}
