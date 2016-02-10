package pl.elpassion.cloudtimer

import android.app.Application
import android.content.Context
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric


class CloudTimerApp : Application()
{
    companion object {
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        Fabric.with(this, Crashlytics())
    }
}