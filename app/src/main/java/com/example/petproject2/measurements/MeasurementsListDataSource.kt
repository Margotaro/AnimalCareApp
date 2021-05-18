package com.example.petproject2.measurements

import java.util.*

interface MeasurementsListDataSource {
    fun dataForPosition(position: Int): Measurement?
    fun count(): Int
}

class MeasurementsListDataSourceMock() : MeasurementsListDataSource {

    val data: Array<Measurement>

    init {
        data = arrayOf(
            Measurement(arrayOf(
                MeasurementRecord(3.5, Date(Date().time - Math.round(3600 * 24 * 1000 * 3.5))),
                MeasurementRecord(3.8, Date(Date().time - 3600 * 24 * 1000 * 3)),
                MeasurementRecord(3.2, Date(Date().time - 3600 * 24 * 1000 * 2)),
                MeasurementRecord(3.0, Date(Date().time - 3600 * 24 * 1000 * 1))
            ),"шт.", "Вареники"),
            Measurement(arrayOf(
                MeasurementRecord(3.5, Date(Date().time - Math.round(3600 * 24 * 1000 * 3.5))),
                MeasurementRecord(3.8, Date(Date().time - 3600 * 24 * 1000 * 3)),
                MeasurementRecord(3.2, Date(Date().time - 3600 * 24 * 1000 * 2)),
                MeasurementRecord(3.0, Date(Date().time - 3600 * 24 * 1000 * 1))
            ),"шт.", "Пельмени"),
            Measurement(arrayOf(
                MeasurementRecord(3.5, Date(Date().time - Math.round(3600 * 24 * 1000 * 3.5))),
                MeasurementRecord(3.8, Date(Date().time - 3600 * 24 * 1000 * 3)),
                MeasurementRecord(3.2, Date(Date().time - 3600 * 24 * 1000 * 2)),
                MeasurementRecord(3.0, Date(Date().time - 3600 * 24 * 1000 * 1))
            ),"шт.", "Бульмени"),
            Measurement(arrayOf(
                MeasurementRecord(3.5, Date(Date().time - Math.round(3600 * 24 * 1000 * 3.5))),
                MeasurementRecord(3.8, Date(Date().time - 3600 * 24 * 1000 * 3)),
                MeasurementRecord(3.2, Date(Date().time - 3600 * 24 * 1000 * 2)),
                MeasurementRecord(3.0, Date(Date().time - 3600 * 24 * 1000 * 1))
            ),"шт.", "Равиолли")
        )
    }

    override fun dataForPosition(position: Int): Measurement {
        return data[position]
    }

    override fun count(): Int {
        return data.size
    }

}