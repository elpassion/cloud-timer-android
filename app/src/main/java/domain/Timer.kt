package domain

class Timer(val uuid: String, val title: String, val duration: Long, val endTime: Long, val timeLeft: Long, val group: Group) {
}