package pl.elpassion.cloudtimer.common

import android.os.Parcel
import android.os.Parcelable

inline fun <reified T : Parcelable> createCreator(crossinline createObject: Parcel.() -> T) = object : Parcelable.Creator<T> {
    override fun createFromParcel(source: Parcel) = source.createObject()
    override fun newArray(size: Int) = arrayOfNulls<T>(size)
}