package com.example.petproject2.measurements

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import com.example.petproject2.R
import kotlinx.android.synthetic.main.measurement_record_list_view_item.view.*
import java.text.SimpleDateFormat

interface   MeasurementRecordListItemViewControllerDelegate {
    fun recordItemWantsToBeRemoved(record: MeasurementRecordListItemViewController)
    fun recordItemWantToBeEdited(record: MeasurementRecordListItemViewController, callback: (() -> Unit))
}

class MeasurementRecordListItemViewController(
    val rootView: View,
    val data: MeasurementRecord,
    val delegate: MeasurementRecordListItemViewControllerDelegate) {

    init {
        val valueLabel = rootView.findViewById<TextView>(R.id.recordValueLabel)
        val dateLabel = rootView.findViewById<TextView>(R.id.recordDateLabel)

        valueLabel.setText(data.value.toString())
        val formatter = SimpleDateFormat("DD/mmm/yyyy hh:mm")
        dateLabel.setText(formatter.format(data.date))

        val editRecordButton = rootView.findViewById<View>(R.id.editRecordButton)
        editRecordButton.setOnClickListener {
            delegate.recordItemWantToBeEdited(this) {
                valueLabel.setText(data.value.toString())
                val formatter = SimpleDateFormat("DD/mmm/yyyy hh:mm")
                dateLabel.setText(formatter.format(data.date))
            }
        }
        val removeRecordButton = rootView.findViewById<View>(R.id.removeRecordButton)
        removeRecordButton.setOnClickListener {
            delegate.recordItemWantsToBeRemoved(this)
        }
    }

}