package pl.elpassion.cloudtimer.domain

import android.os.Parcel
import android.os.Parcelable
import pl.elpassion.cloudtimer.common.createCreator
import pl.elpassion.cloudtimer.currentTimeInMillis
import java.lang.System.currentTimeMillis
import java.util.*

data class Timer(val title: String, val duration: Long, val endTime: Long = currentTimeMillis() + duration, val uid: String = randomUUID(), val group: Group? = null, val timeLeft: Long? = null) : Parcelable{

    val finished: Boolean
        get() = endTime < currentTimeInMillis()

    companion object {
        fun randomUUID(): String = UUID.randomUUID().toString()
        @JvmField final val CREATOR = createCreator { Timer(this) }
    }

    override fun describeContents(): Int = 0

    constructor(parcel: Parcel) : this(parcel.readString(), parcel.readLong(), parcel.readLong(),  parcel.readString(), parcel.readParcelable(Group::class.java.classLoader))

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeLong(duration)
        parcel.writeLong(endTime)
        parcel.writeString(uid)
        parcel.writeParcelable(group, flags)
    }


}
