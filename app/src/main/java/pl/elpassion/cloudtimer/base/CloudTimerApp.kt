package pl.elpassion.cloudtimer.base

import android.app.Application
import android.content.Context
import com.crashlytics.android.Crashlytics
import com.squareup.leakcanary.LeakCanary
import io.fabric.sdk.android.Fabric


class CloudTimerApp : Application() {
    companion object {
        lateinit var applicationContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        LeakCanary.install(this)
        Companion.applicationContext = applicationContext
        Fabric.with(this, Crashlytics())
    }
}