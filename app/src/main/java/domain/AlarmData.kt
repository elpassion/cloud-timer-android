package domain

class AlarmData(val timeInMillis: Long, val timeLeft: Long?, var uuId: Long?, var id: Long? = null) {
    constructor(timeInMillis: Long) : this(timeInMillis, null, null, null) {
    }
}