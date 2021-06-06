package com.example.petproject2.measurements

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.ListView
import androidx.room.PrimaryKey
import com.example.petproject2.R
import kotlinx.android.synthetic.main.measurement_list_view_item.view.*

class MeasurementViewScenario(
    private val listView: ListView,
    val addButton: View,
    val petId: Int,
    val context: Context,
    val layoutInflater: LayoutInflater
) : BaseAdapter(), AdapterView.OnItemClickListener {

    private val dataSource: MeasurementsListDataSource
    init {
        dataSource = MeasurementsListDataSourceMock()
    }

    fun loadViews() {
        listView.adapter = this
        listView.onItemClickListener = this
    }

    override fun getCount(): Int {
        return dataSource.count()
    }

    override fun getItem(p: Int): Any {
        return dataSource.dataForPosition(p) ?: Any()
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(index: Int, convertView: View?, group: ViewGroup?): View {
        val resultView = convertView ?:
            layoutInflater.inflate(R.layout.measurement_list_view_item, group, false)
        val viewController = MeasurementListViewController(resultView)
        dataSource.dataForPosition(index)?.let { data ->
            viewController.display(data)
        }
        return resultView
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        dataSource.dataForPosition(position)?.let {
            val intent = Intent(context, MeasurementActivity::class.java)
            intent.putExtra("measurement", it)
            context.startActivity(intent)
        }
    }

}