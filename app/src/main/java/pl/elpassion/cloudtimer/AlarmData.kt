package pl.elpassion.cloudtimer

class AlarmData (val timeInMillis: Long, val timeLeft: Long?, var uiId: Long?) {
    constructor(timeInMillis: Long) : this(timeInMillis, null, null) {
    }
}