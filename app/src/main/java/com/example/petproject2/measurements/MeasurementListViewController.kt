package com.example.petproject2.measurements

import android.view.View
import android.widget.TextView
import com.example.petproject2.R
import kotlinx.android.synthetic.main.measurement_list_view_item.*
import kotlinx.android.synthetic.main.measurement_list_view_item.view.*

class MeasurementListViewController(val rootView: View) {

    val lastModifiedLabel: TextView
    val nameLabel: TextView
    val valueLabel: TextView
    val recordsCount: TextView

    init {
        lastModifiedLabel = rootView.findViewById(R.id.lastUpdatedTime)
        nameLabel = rootView.findViewById(R.id.measurementName)
        valueLabel = rootView.findViewById(R.id.measurementCurrentValue)
        recordsCount = rootView.findViewById(R.id.measurementsCount)
    }

    fun display(data: Measurement) {
        lastModifiedLabel.setText(data.values.last().date.toString())
        nameLabel.setText(data.name)
        valueLabel.setText(data.values.last().value.toString())
        recordsCount.setText("${data.values.size} records")
    }

}