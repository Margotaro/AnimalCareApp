package com.example.petproject2.measurements

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.petproject2.R

class MeasurementRecordsListAdapter(
    val measurement: Measurement,
    val delegate: MeasurementRecordListItemViewControllerDelegate,
    val context: Context,
    val layoutInflater: LayoutInflater
) : BaseAdapter() {

    override fun getCount(): Int {
        return measurement.values.size
    }

    override fun getItem(p0: Int): Any {
        return measurement.values[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(index: Int, convertView: View?, group: ViewGroup?): View {
        val resultView = convertView ?:
            layoutInflater.inflate(R.layout.measurement_record_list_view_item, group, false)
        MeasurementRecordListItemViewController(resultView, measurement.values[index], delegate)
        return resultView
    }

}

