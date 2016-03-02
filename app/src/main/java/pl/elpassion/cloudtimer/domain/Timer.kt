package pl.elpassion.cloudtimer.domain

import android.os.Parcel
import android.os.Parcelable
import pl.elpassion.cloudtimer.common.*
import pl.elpassion.cloudtimer.currentTimeInMillis
import java.util.*

data class Timer(
        val title: String,
        val duration: Long,
        val endTime: Long = currentTimeInMillis() + duration,
        val uid: String = randomUUID(),
        val group: Group? = null,
        val sync: Boolean = false) : Parcelable {

    companion object {
        fun randomUUID(): String = UUID.randomUUID().toString()
        @JvmField final val CREATOR = createCreator {
            Timer(
                    title = readString(),
                    duration = readLong(),
                    endTime = readLong(),
                    uid = readString(),
                    group = Group.CREATOR.createNullableFromParcel(this),
                    sync = readBoolean()
            )
        }
    }

    override fun describeContents(): Int = 0

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeLong(duration)
        parcel.writeLong(endTime)
        parcel.writeString(uid)
        group.writeNullableToParcel(parcel, flags)
        parcel.writeBoolean(sync)
    }

    val finished: Boolean
        get() = endTime < currentTimeInMillis()
}
