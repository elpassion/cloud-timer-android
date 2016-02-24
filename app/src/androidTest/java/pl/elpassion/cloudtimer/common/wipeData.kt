package pl.elpassion.cloudtimer.common

import java.io.File

fun deleteSharedPreferences() {
    File("/data/data/pl.elpassion.cloudtimer/shared_prefs")
            .listFiles().forEach { it.delete() }
}
