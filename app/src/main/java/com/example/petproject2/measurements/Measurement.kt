package com.example.petproject2.measurements

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable
import java.util.*

class Measurement(private var _values: Array<MeasurementRecord>, val type: String, val name: String): Serializable {

    val values get() = _values

    fun remove(record: MeasurementRecord) {
        _values = _values.filter { it.equals(record) }.toTypedArray()
    }

    fun add(record: MeasurementRecord) {
        _values = _values.plus(record)
    }

}

class MeasurementRecord(private var _value: Double, private var _date: Date): Serializable {

    var value
        get() = _value
        set(it) {
            _value = it
        }
    var date
        get() = _date
        set(it) {
            _date = it
        }

}