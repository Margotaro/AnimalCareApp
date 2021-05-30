package com.example.petproject2.measurements

import android.os.Bundle
import android.view.View
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.petproject2.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class MeasurementRecordsListActivity : AppCompatActivity(), MeasurementRecordListItemViewControllerDelegate {

    lateinit var measurement: Measurement
    lateinit var listView: ListView
    lateinit var addButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_measurement_records_list)

        val oldIntent = getIntent()
        val defaultMeasurement = Measurement(
            arrayOf(MeasurementRecord(0.0, Date())),
            "error",
            "error"
        )
        oldIntent.getSerializableExtra("measurement")?.let {
            (it as? Measurement)?.let {
                measurement = it
            } ?: run {
                measurement = defaultMeasurement
            }
        } ?: run {
            measurement = defaultMeasurement
        }

        listView = findViewById(R.id.measurementRecordsList)
        addButton = findViewById(R.id.addRecordButton)

        listView.adapter = MeasurementRecordsListAdapter(measurement, this, this, layoutInflater)
    }

    override fun recordItemWantsToBeRemoved(record: MeasurementRecordListItemViewController) {
        measurement.remove(record.data)
        listView.deferNotifyDataSetChanged()
    }

    override fun recordItemWantToBeEdited(
        record: MeasurementRecordListItemViewController,
        callback: () -> Unit
    ) {
        val builder = AlertDialog.Builder(this)
        val rootView = layoutInflater.inflate(R.layout.dialogue_record_edit, null)
//        val date =
        builder.setView(rootView)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}

