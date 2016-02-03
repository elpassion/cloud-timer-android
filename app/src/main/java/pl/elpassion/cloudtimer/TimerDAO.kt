package pl.elpassion.cloudtimer

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

import java.util.*

class TimerDAO(context: Context, name: String = "cloudTimerDB", factory: SQLiteDatabase.CursorFactory?, version: Int = 1) : SQLiteOpenHelper(context, name, factory, version) {

    companion object {
        private val TABLE_TIMER = "timer"
        private val KEY_TIMER_TITLE = "title"
        private val KEY_TIMER_UUID = "uuId"
        private val KEY_DURATION = "duration"
        private val KEY_TIME_LEFT = "timeLeft"
        private val KEY_END_TIME = "endTime"
        private val KEY_GROUP_NAME = "groupName"


        private var dao: TimerDAO? = null
        fun getInstance(context: Context): TimerDAO {
            if (dao == null) dao = TimerDAO(context = context, factory = null)
            return dao!!
        }
    }

    override fun onConfigure(db: SQLiteDatabase) {
        super.onConfigure(db)
        db.setForeignKeyConstraintsEnabled(true)
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_TIMER_TABLE = " CREATE TABLE $TABLE_TIMER($KEY_TIMER_UUID STRING PRIMARY KEY, $KEY_TIMER_TITLE STRING, $KEY_DURATION LONG, $KEY_END_TIME LONG, $KEY_TIME_LEFT LONG, $KEY_GROUP_NAME STRING)"
        db.execSQL(CREATE_TIMER_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    public fun save(timer: Timer) {
        val values = ContentValues()
        //values.put(KEY_ALARM_TITLE, alarm.title)
        values.put(KEY_DURATION, timer.duration)
        values.put(KEY_END_TIME, timer.timeLeft)
        values.put(KEY_TIMER_UUID, timer.uuId)

        if (timer.id == null) {
            writableDatabase.insert(TABLE_TIMER, null, values)
        } else {
            writableDatabase.update(TABLE_TIMER, values, "$KEY_TIMER_ID = ? ", arrayOf(timer.id.toString()))
        }
    }

    public fun findAll(): List<AlarmData> {
        val alarms: MutableList<AlarmData> = ArrayList()
        val res: Cursor = readableDatabase.rawQuery("select * from $TABLE_TIMER", null)
        res.moveToFirst()
        while (res.isAfterLast == false) {
            val id = res.getLong(res.getColumnIndex(KEY_TIMER_ID))
            val uiId = res.getLong(res.getColumnIndex(KEY_TIMER_UUID))
            val timeInMillis = res.getLong(res.getColumnIndex(KEY_DURATION))
            val timeLeft = res.getLong(res.getColumnIndex(KEY_END_TIME))
            val alarm = AlarmData(timeInMillis, timeLeft, uiId, id)
            alarms.add(alarm)
            res.moveToNext()
        }
        return alarms
    }

}