package pl.elpassion.cloudtimer.common

import android.content.ContentValues
import android.database.Cursor

fun Cursor.getBoolean(key: String): Boolean {
    return getInt(getColumnIndex(key)) != 0
}

fun Cursor.getString(key: String): String {
    return getString(getColumnIndex(key))
}

fun Cursor.getNullableString(key: String): String? {
    return getString(getColumnIndex(key))
}

fun Cursor.getInt(key: String): Int {
    return getInt(getColumnIndex(key))
}

fun Cursor.getNullableInt(key: String): Int? {
    val columnIndex = getColumnIndex(key)
    if (this.isNull(columnIndex))
        return getInt(columnIndex)
    else return null
}


fun Cursor.getLong(key: String): Long {
    return getLong(getColumnIndex(key))
}

fun ContentValues.putBoolean(key: String, value : Boolean){
    val intValue = if (value) 1 else 0
    put(key, intValue)
}