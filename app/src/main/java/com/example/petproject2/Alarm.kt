package com.example.petproject2

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Parcelable
import android.text.format.DateFormat
import com.example.petproject2.database.NotificationEntity
import kotlinx.android.parcel.Parcelize
import java.util.*


@Parcelize
class Alarm(
    var id: Int,
    var notification_msg: String,
    var callTimestamp: Long,
    var petId: Int,
    var isOn: Boolean,
    var repeatState: Boolean
) : BroadcastReceiver(), Parcelable { //TODO: make it more than 2 options (for now only once 0 & everyday 1)

    constructor(): this(1, "", 0, 0, false, false)
    constructor(ne: NotificationEntity): this(ne.id, ne.notificationMsg, ne.callTimestamp, ne.petId, ne.isOn, false)
    private var calendar = Calendar.getInstance(Locale.ENGLISH) //1586822400L * 1000L
    private var date =  calendar.setTimeInMillis(callTimestamp)
    var displayCallTime: String = DateFormat.format("dd-MM-yyyy hh:mm:ss", calendar).toString()
    var timeWasSet = false
    var dateWasSet = false

    override fun onReceive(p0: Context?, p1: Intent?) {
        TODO("Not yet implemented")
    }

    fun setDate(year: Int, month: Int, day: Int) {
        calendar.set(Calendar.DAY_OF_MONTH, day)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.YEAR, year)
        displayCallTime = DateFormat.format("dd-MM-yyyy hh:mm:ss", calendar).toString()
        callTimestamp = calendar.timeInMillis
        timeWasSet = true
    }

    fun setTime(hourOfDay: Int, minute: Int) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)
        displayCallTime = DateFormat.format("dd-MM-yyyy hh:mm:ss", calendar).toString()
        callTimestamp = calendar.timeInMillis
        dateWasSet = true
    }
    fun generateNotificationEntity(): NotificationEntity {
        return NotificationEntity(id, petId, notification_msg, callTimestamp, isOn)
    }
}
