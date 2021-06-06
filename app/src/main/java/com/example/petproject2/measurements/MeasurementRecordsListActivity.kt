package com.example.petproject2.measurements

import android.R.id.message
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.petproject2.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
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

        listView.adapter = MeasurementRecordsListAdapter(
            measurement,
            this, this, layoutInflater
        )

        addButton.setOnClickListener {
            configureAddDialogue()
        }
    }

    override fun onStop() {
        super.onStop()

        val mIntent = Intent()
        mIntent.putExtra("keyName", message)
        setResult(RESULT_OK, mIntent)
        finish()
    }

    override fun recordItemWantsToBeRemoved(record: MeasurementRecordListItemViewController) {
        measurement.remove(record.data)
        runOnUiThread {
            listView.deferNotifyDataSetChanged()
            listView.invalidateViews()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun recordItemWantToBeEdited(
        record: MeasurementRecordListItemViewController,
        callback: () -> Unit
    ) {
        configureEditDialogue(record.data)
    }

    fun configureAddDialogue() {
        val builder = AlertDialog.Builder(this)
        val rootView = layoutInflater.inflate(R.layout.dialogue_record_create, null)
        builder.setView(rootView)
        val dialog: AlertDialog = builder.create()
        val saveButton = rootView.findViewById<Button>(R.id.saveButton)
        saveButton.setOnClickListener {
            val textInput = rootView.findViewById<TextInputEditText>(R.id.measurementValue)
            val textValue = textInput.text?.toString() ?: ""
            textValue.toDoubleOrNull()?.let {
                val record = MeasurementRecord(it, Date())
                measurement.add(record)
                dialog.hide()
                listView.deferNotifyDataSetChanged()
            } ?: run {
                dialog.hide()
            }
        }
        val cancelButton = rootView.findViewById<Button>(R.id.cancelButton)
        cancelButton.setOnClickListener { dialog.hide() }
        dialog.show()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun configureEditDialogue(record: MeasurementRecord) {
        val builder = AlertDialog.Builder(this)
        val rootView = layoutInflater.inflate(R.layout.dialogue_record_edit, null)
        builder.setView(rootView)
        val dialog: AlertDialog = builder.create()
        val saveButton = rootView.findViewById<Button>(R.id.saveButton)

        val textInput = rootView.findViewById<TextInputEditText>(R.id.measurementValue)
        textInput.setText("${record.value}")

        val timeInput = rootView.findViewById<TimePicker>(R.id.timePicker)
        val calendar = Calendar.getInstance()
        calendar.time = record.date
        timeInput.hour = calendar.get(Calendar.HOUR_OF_DAY)
        timeInput.minute = calendar.get(Calendar.MINUTE)

        saveButton.setOnClickListener {
            val textValue = textInput.text?.toString() ?: ""
            textValue.toDoubleOrNull()?.let {
                record.value = it
                calendar.set(Calendar.HOUR_OF_DAY, timeInput.hour)
                calendar.set(Calendar.MINUTE, timeInput.minute)
                record.date = calendar.time
                dialog.hide()
                listView.deferNotifyDataSetChanged()
                listView.invalidateViews()
            } ?: run {
                dialog.hide()
            }
        }
        val cancelButton = rootView.findViewById<Button>(R.id.cancelButton)
        cancelButton.setOnClickListener { dialog.hide() }
        dialog.show()
    }
}

