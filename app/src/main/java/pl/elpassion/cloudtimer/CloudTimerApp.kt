package pl.elpassion.cloudtimer

import android.app.Application
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric


class CloudTimerApp : Application()
{
    override fun onCreate() {
        super.onCreate()
        Fabric.with(this, Crashlytics())
    }
}