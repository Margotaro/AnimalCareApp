package com.example.petproject2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView


class NotificationListAdapter(private val mAlarm: List<Alarm>, val alarmChangeListener: OnAlarmChangeListener) : RecyclerView.Adapter<NotificationListAdapter.ViewHolder>(){
    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    inner class ViewHolder(val listItemView: View) : RecyclerView.ViewHolder(listItemView)  {
        // Your holder should contain and initialize a member variable
        // for any view that will be set as you render a row
        val alarmNameTextView = itemView.findViewById<TextView>(R.id.alarm_context_name)
        var alarmSwitch = itemView.findViewById<Switch>(R.id.alarm_on_off)
        val alarmTimeTextView = itemView.findViewById<TextView>(R.id.alarm_time)
        val alarmRepeat = itemView.findViewById<TextView>(R.id.alarm_repeat)//TODO: check types later
        fun onClickDelete(alarm: Alarm, onClickListener: OnAlarmChangeListener) {
            listItemView.setOnLongClickListener {
                onClickListener.onItemClicked(alarm)
                return@setOnLongClickListener true
            }
        }
        fun onSwitchClicked(alarm: Alarm, onClickListener: OnAlarmChangeListener) {
            alarmSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
                alarm.isOn = isChecked
                onClickListener.onSwitchChecked(alarm)
            }
        }
    }

    // ... constructor and member variables
    // Usually involves inflating a layout from XML and returning the holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationListAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val alarmView = inflater.inflate(R.layout.item_alarm, parent, false)
        // Return a new holder instance
        return ViewHolder(alarmView)
    }
    // Involves populating data into the item through holder
    override fun onBindViewHolder(viewHolder: NotificationListAdapter.ViewHolder, position: Int) {
        // Get the data model based on position
        val alarm: Alarm = mAlarm.get(position)
        // Set item views based on your views and data model
        val descriptionTextView = viewHolder.alarmNameTextView
        val switch = viewHolder.alarmSwitch
        val time = viewHolder.alarmTimeTextView
        val repeatDescriptionTextView = viewHolder.alarmRepeat
        descriptionTextView.setText(alarm.notification_msg)
        switch.setChecked(alarm.isOn)
        time.setText(alarm.displayCallTime)
        repeatDescriptionTextView.setText(if (alarm.repeatState == true) "Once" else "Repeating")

        viewHolder.onClickDelete(alarm, alarmChangeListener)
        viewHolder.onSwitchClicked(alarm, alarmChangeListener)
    }

    override fun getItemCount(): Int {
        return mAlarm.size
    }
}

