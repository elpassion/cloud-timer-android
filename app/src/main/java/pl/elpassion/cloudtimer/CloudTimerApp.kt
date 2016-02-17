package pl.elpassion.cloudtimer

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
        CloudTimerApp.applicationContext = applicationContext
        Fabric.with(this, Crashlytics())
    }
}