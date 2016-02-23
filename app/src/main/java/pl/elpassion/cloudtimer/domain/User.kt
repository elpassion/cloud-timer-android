package pl.elpassion.cloudtimer.domain

import android.os.Parcel
import android.os.Parcelable

data class User(val name : String, val email : String ): Parcelable{
    companion object {
        @JvmField
        final val CREATOR: Parcelable.Creator<User> = object : Parcelable.Creator<User> {

            override fun createFromParcel(parcel: Parcel): User {
                return User(parcel)
            }

            override fun newArray(size: Int): Array<User?> {
                return arrayOfNulls(size)
            }
        }
    }

    override fun describeContents(): Int = 0

    constructor(parcel: Parcel) : this(parcel.readString(), parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(email)
    }

}