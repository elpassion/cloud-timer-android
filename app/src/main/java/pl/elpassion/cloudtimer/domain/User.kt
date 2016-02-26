package pl.elpassion.cloudtimer.domain

import android.os.Parcel
import android.os.Parcelable
import pl.elpassion.cloudtimer.common.createCreator

data class User(val name: String, val email: String) : Parcelable {
    companion object {
        @JvmField final val CREATOR = createCreator {
            User(
                    name = readString(),
                    email = readString()
            )
        }
    }

    override fun describeContents(): Int = 0

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(email)
    }

}