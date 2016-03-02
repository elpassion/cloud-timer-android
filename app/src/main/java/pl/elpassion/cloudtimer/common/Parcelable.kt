package pl.elpassion.cloudtimer.common

import android.os.Parcel
import android.os.Parcelable

inline fun <reified T : Parcelable> createCreator(crossinline createObject: Parcel.() -> T) = object : Parcelable.Creator<T> {
    override fun createFromParcel(source: Parcel) = source.createObject()
    override fun newArray(size: Int) = arrayOfNulls<T>(size)
}

fun Parcel.readBoolean() = readInt() != 0

fun Parcel.writeBoolean(value: Boolean) = writeInt(if (value) 1 else 0)

fun Parcelable?.writeNullableToParcel(dest: Parcel, flags: Int) {
    dest.writeBoolean(this != null)
    if (this != null)
        writeToParcel(dest, flags)
}

fun <T> Parcelable.Creator<T>.createNullableFromParcel(source: Parcel): T? =
        if (source.readBoolean()) createFromParcel(source) else null
