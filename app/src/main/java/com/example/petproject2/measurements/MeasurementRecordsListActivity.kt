package com.example.petproject2.measurements

import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.petproject2.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class MeasurementRecordsListActivity : AppCompatActivity() {

    lateinit var measurement: Measurement
    lateinit var listView: ListView
    lateinit var addButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_measurement_records_list)

        val oldIntent = getIntent()
        val defaultMeasurement = Measurement(arrayOf(MeasurementRecord(0.0, Date())), "error", "error")
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

        listView.adapter = MeasurementRecordsListAdapter(measurement, this, layoutInflater)
    }
}

