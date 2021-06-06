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
                MeasurementRecord(450.0, Date(Date().time - Math.round(3600 * 24 * 1000 * 3.5))),
                MeasurementRecord(448.3, Date(Date().time - 3600 * 24 * 1000 * 3)),
                MeasurementRecord(430.0, Date(Date().time - 3600 * 24 * 1000 * 2)),
                MeasurementRecord(457.8, Date(Date().time - 3600 * 24 * 1000 * 1))
            ),"кг.", "Вага"),
            Measurement(arrayOf(
                MeasurementRecord(36.7, Date(Date().time - Math.round(3600 * 24 * 1000 * 3.5))),
                MeasurementRecord(36.8, Date(Date().time - 3600 * 24 * 1000 * 3)),
                MeasurementRecord(36.8, Date(Date().time - 3600 * 24 * 1000 * 2)),
                MeasurementRecord(36.9, Date(Date().time - 3600 * 24 * 1000 * 1)),
                MeasurementRecord(37.0, Date(Date().time - 3600 * 24 * 1000 * 1)),
                MeasurementRecord(36.8, Date(Date().time - 3600 * 24 * 1000 * 1)),
                MeasurementRecord(36.5, Date(Date().time - 3600 * 24 * 1000 * 1)),
                MeasurementRecord(36.7, Date(Date().time - 3600 * 24 * 1000 * 1))
            ),"град.", "Температура тіла"),
            Measurement(arrayOf(
                MeasurementRecord(3500.0, Date(Date().time - Math.round(3600 * 24 * 1000 * 3.5))),
                MeasurementRecord(3492.0, Date(Date().time - 3600 * 24 * 1000 * 3)),
                MeasurementRecord(3491.0, Date(Date().time - 3600 * 24 * 1000 * 2))
            ),"ньют.", "Потужність стиснення щелеп"),
            Measurement(arrayOf(
                MeasurementRecord(5.0, Date(Date().time - Math.round(3600 * 24 * 1000 * 3.5))),
                MeasurementRecord(4.8, Date(Date().time - 3600 * 24 * 1000 * 3)),
                MeasurementRecord(1.2, Date(Date().time - 3600 * 24 * 1000 * 2)),
                MeasurementRecord(10.0, Date(Date().time - 3600 * 24 * 1000 * 1))
            ),"км./сут.", "Пройдена відстань"),
            Measurement(arrayOf(
                MeasurementRecord(58.0, Date(Date().time - Math.round(3600 * 24 * 1000 * 3.5))),
                MeasurementRecord(81.0, Date(Date().time - 3600 * 24 * 1000 * 3)),
                MeasurementRecord(52.2, Date(Date().time - 3600 * 24 * 1000 * 2)),
                MeasurementRecord(67.8, Date(Date().time - 3600 * 24 * 1000 * 1))
            ),"уд./хв.", "Серцебиття"),
            Measurement(arrayOf(
                MeasurementRecord(120.0, Date(Date().time - Math.round(3600 * 24 * 1000 * 3.5))),
                MeasurementRecord(119.0, Date(Date().time - 3600 * 24 * 1000 * 3)),
                MeasurementRecord(120.0, Date(Date().time - 3600 * 24 * 1000 * 2)),
                MeasurementRecord(117.0, Date(Date().time - 3600 * 24 * 1000 * 1))
            ),"мм.рт.ст.", "Тиск систоличний"),
            Measurement(arrayOf(
                MeasurementRecord(80.0, Date(Date().time - Math.round(3600 * 24 * 1000 * 3.5))),
                MeasurementRecord(81.0, Date(Date().time - 3600 * 24 * 1000 * 3)),
                MeasurementRecord(83.0, Date(Date().time - 3600 * 24 * 1000 * 2)),
                MeasurementRecord(80.0, Date(Date().time - 3600 * 24 * 1000 * 1))
            ),"мм.рт.ст.", "Тиск діастоличний"),

            Measurement(arrayOf(
                MeasurementRecord(40.0, Date(Date().time - Math.round(3600 * 24 * 1000 * 3.5))),
                MeasurementRecord(29.8, Date(Date().time - 3600 * 24 * 1000 * 3)),
                MeasurementRecord(28.2, Date(Date().time - 3600 * 24 * 1000 * 2)),
                MeasurementRecord(30.0, Date(Date().time - 3600 * 24 * 1000 * 1))
            ),"км./час", "Швидкість")

        )
    }

    override fun dataForPosition(position: Int): Measurement {
        return data[position]
    }

    override fun count(): Int {
        return data.size
    }

}