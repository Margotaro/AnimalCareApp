package com.example.petproject2.measurements

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable
import java.util.*

class Measurement(val values: Array<MeasurementRecord>, val type: String, val name: String): Serializable {

}

class MeasurementRecord(val value: Double, val date: Date): Serializable {

}