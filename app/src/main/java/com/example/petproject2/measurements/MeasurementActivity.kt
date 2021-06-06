package com.example.petproject2.measurements

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.example.petproject2.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class MeasurementActivity : Activity() {

    lateinit var measurement: Measurement
    lateinit var countLabel: TextView
    lateinit var nameLabel: TextView
    lateinit var valueLabel: TextView
    lateinit var dateLabel: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_measurement)

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

        countLabel = findViewById(R.id.measurementsCountLabel)
        valueLabel = findViewById(R.id.measurementValue)
        nameLabel = findViewById(R.id.measurementNameLabel)

        val lastRecord = measurement.values.last()
        countLabel.setText("${measurement.values.size} записей")
        valueLabel.setText("${lastRecord.value} ${measurement.type}")
        nameLabel.setText(measurement.name)

        val firstDate = measurement.values.first().date.time
        val chart = findViewById<LineChart>(R.id.chart)
        chart.setBackgroundColor(Color.WHITE);
        val dataSet = LineDataSet(measurement.values.map{ Entry(it.date.time.toFloat() - firstDate, it.value.toFloat()) }, "data set")
        chart.data = LineData(dataSet)
        chart.xAxis.valueFormatter = object: IAxisValueFormatter {
            override fun getFormattedValue(value: Float, axis: AxisBase?): String {
                val date = Date((firstDate + value).toLong())
                return SimpleDateFormat("dd MMM").format(date)// + System.lineSeparator() + SimpleDateFormat("hh:mm").format(date)
            }
        }

        val editButton = findViewById<FloatingActionButton>(R.id.editRecordButton)
        if (measurement != defaultMeasurement) {
            editButton.setOnClickListener({
                val intent = Intent(this, MeasurementRecordsListActivity::class.java)
                intent.putExtra("measurement", measurement)
                startActivity(intent)
            })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        (data?.getSerializableExtra("measurement") as? Measurement)?.let {
            Toast.makeText(this, "$measurement", Toast.LENGTH_SHORT).show()
        }
    }
}