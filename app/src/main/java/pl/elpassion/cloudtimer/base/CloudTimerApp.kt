package pl.elpassion.cloudtimer.base

import android.app.Application
import android.content.Context
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric


class CloudTimerApp : Application() {
    companion object {
        lateinit var applicationContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        Companion.applicationContext = applicationContext
        Fabric.with(this, Crashlytics())
    }
}