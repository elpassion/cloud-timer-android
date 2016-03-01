package pl.elpassion.cloudtimer.timer

import pl.elpassion.cloudtimer.common.retrofit
import pl.elpassion.cloudtimer.domain.Timer
import rx.Observable

interface SendTimerService {
    fun sendTimer(timer: TimerToSend) : Observable<ReceivedTimer>
}

var sendTimerService = retrofit.create(SendTimerService::class.java)

class ReceivedTimer(val uuid: String)

class TimerToSend(val uuid: String, val name : String,val data: TimerTimeVariables){
    constructor(timer: Timer) : this(timer.uid, timer.title, TimerTimeVariables(timer.duration, timer.endTime))
}

class TimerTimeVariables(val duration: Long, val endTime: Long)
