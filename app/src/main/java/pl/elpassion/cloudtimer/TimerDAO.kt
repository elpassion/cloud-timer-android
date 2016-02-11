package pl.elpassion.cloudtimer

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import pl.elpassion.cloudtimer.CloudTimerApp.Companion.applicationContext
import pl.elpassion.cloudtimer.domain.Group
import pl.elpassion.cloudtimer.domain.Timer
import java.util.*

class TimerDAO(context: Context, name: String = "cloudTimerDB", factory: SQLiteDatabase.CursorFactory?, version: Int = 1) : SQLiteOpenHelper(context, name, factory, version) {

    companion object {
        private val TABLE_TIMER = "timer"
        private val KEY_TIMER_TITLE = "title"
        private val KEY_TIMER_UID = "uId"
        private val KEY_DURATION = "duration"
        private val KEY_TIME_LEFT = "timeLeft"
        private val KEY_END_TIME = "endTime"
        private val KEY_GROUP_NAME = "groupName"
        private var dao: TimerDAO? = null
        private fun getInstance(context: Context): TimerDAO {
            if (dao == null) dao = TimerDAO(context = context, factory = null)
            return dao!!
        }

        fun getInstance(): TimerDAO = getInstance(applicationContext)

    }

    override fun onConfigure(db: SQLiteDatabase) {
        super.onConfigure(db)
        db.setForeignKeyConstraintsEnabled(true)
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_TIMER_TABLE = "CREATE TABLE $TABLE_TIMER($KEY_TIMER_UID STRING PRIMARY KEY," +
                " $KEY_TIMER_TITLE STRING," +
                " $KEY_DURATION LONG," +
                " $KEY_END_TIME LONG," +
                " $KEY_TIME_LEFT LONG NULLABLE," +
                " $KEY_GROUP_NAME STRING NULLABLE)"
        db.execSQL(CREATE_TIMER_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    fun save(timer: Timer): String {
        val values = ContentValues()
        values.put(KEY_TIMER_UID, timer.uid)
        values.put(KEY_TIMER_TITLE, timer.title)
        values.put(KEY_DURATION, timer.duration)
        values.put(KEY_END_TIME, timer.endTime)
        values.put(KEY_TIME_LEFT, timer.timeLeft)

        if (timer.group != null)
            values.put(KEY_GROUP_NAME, timer.group.name)

        if (writableDatabase.update(TABLE_TIMER, values, "$KEY_TIMER_UID = ? ", arrayOf(timer.uid)) == 0)
            writableDatabase.insert(TABLE_TIMER, null, values)
        return timer.uid
    }

    fun findAll(): List<Timer> {
        val alarms: MutableList<Timer> = ArrayList()
        val res: Cursor = readableDatabase.rawQuery("select * from $TABLE_TIMER", null)
        res.moveToFirst()
        while (res.isAfterLast == false) {
            alarms.add(getTimerFromCursor(res))
            res.moveToNext()
        }
        return alarms
    }

    fun findOne(uuId: String): Timer {
        val res: Cursor = readableDatabase.rawQuery("select * from $TABLE_TIMER where $KEY_TIMER_UID = '$uuId'", null)
        res.moveToFirst()
        if (res.isAfterLast == false) {
            return getTimerFromCursor(res)
        }
        throw NoSuchElementException()
    }

    private fun getTimerFromCursor(res: Cursor): Timer {
        val uId = res.getString(res.getColumnIndex(KEY_TIMER_UID))
        val title = res.getString(res.getColumnIndex(KEY_TIMER_TITLE))
        val duration = res.getLong(res.getColumnIndex(KEY_DURATION))
        val endTime = res.getLong(res.getColumnIndex(KEY_END_TIME))
        val timeLeft = res.getLong(res.getColumnIndex(KEY_TIME_LEFT))
        val groupName = res.getString(res.getColumnIndex(KEY_GROUP_NAME))
        return Timer(title, duration, endTime, uId, if (groupName != null) Group(groupName) else null, timeLeft)
    }

    fun deleteOne(uid: String) {
        readableDatabase.delete(TABLE_TIMER, KEY_TIMER_UID + "=?", arrayOf(uid))
    }

    fun deleteAll() {
        readableDatabase.delete(TABLE_TIMER, null, null)
    }

    fun findLocalTimers(): List<Timer> {
        val alarms: MutableList<Timer> = ArrayList()
        val res: Cursor = readableDatabase.rawQuery("select * from $TABLE_TIMER where $KEY_GROUP_NAME is null", null)
        res.moveToFirst()
        while (res.isAfterLast == false) {
            alarms.add(getTimerFromCursor(res))
            res.moveToNext()
        }
        return alarms
    }

}