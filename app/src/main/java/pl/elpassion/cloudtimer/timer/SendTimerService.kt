package pl.elpassion.cloudtimer.timer

import pl.elpassion.cloudtimer.common.retrofit
import rx.Observable

interface SendTimerService {
    fun sendTimer(timer: Any) : Observable<Any>
}

var sendTimerService = retrofit.create(SendTimerService::class.java)