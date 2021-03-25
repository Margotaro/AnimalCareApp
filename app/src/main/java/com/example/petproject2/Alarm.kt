package com.example.petproject2

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
class Alarm(var notification_msg: String, var call_time: Long, var pet_id: Int) : Parcelable {

    var date: Date = Date(call_time)//1586822400L * 1000L
    var simpleDateFormat: SimpleDateFormat = SimpleDateFormat("yyyy/MM/dd")
    var strDate: String = simpleDateFormat.format(date)//param 1


}
