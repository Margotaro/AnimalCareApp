package com.example.petproject2

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.text.SimpleDateFormat
import java.util.*


@Parcelize
class Alarm(var notification_msg: String, var call_time_long: Long, var pet_id: Int, var isOn: Boolean, var repeatState: Boolean) : BroadcastReceiver(), Parcelable { //TODO: make it more than 2 options (for now only once 0 & everyday 1)

    private var date: Date = Date(call_time_long)//1586822400L * 1000L
    private var simpleDateFormat: SimpleDateFormat = SimpleDateFormat("yyyy/MM/dd")
    var call_time_string: String = simpleDateFormat.format(date)//param 1

    override fun onReceive(p0: Context?, p1: Intent?) {
        TODO("Not yet implemented")
    }
}

