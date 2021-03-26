package com.example.petproject2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView


/*
internal class AlarmListAdapter internal constructor(context: Context, private val resource: Int, private val itemList: List<String>) : ArrayAdapter<ImageListAdapter.ItemHolder>(context, resource) {

    override fun getCount(): Int {
        return this.itemList.size + 1
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView


        val holder: ItemHolder
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, null)
            holder = ItemHolder()
            holder.name = convertView.findViewById(R.id.textView)
            holder.icon = convertView.findViewById(R.id.icon)
            convertView.tag = holder
        } else {
            holder = convertView.tag as ItemHolder
        }

        if (position >= itemList.size) {
            holder.name?.text = "Add"
            holder.icon?.setImageResource(R.drawable.newpet)
        } else {
            holder.name?.text = itemList[position].name
            holder.icon?.setImageResource(itemList[position].image)
        }

        return convertView!!
    }

    internal class ItemHolder {
        var name: TextView? = null
        var icon: ImageView? = null
    }
/*
    override fun add('object': ItemHolder?) {
        super.insert('object', itemList!!.size - 1)
    }*/
}
*/
class NotificationListAdapter(private val mAlarm: List<Alarm>) : RecyclerView.Adapter<NotificationListAdapter.ViewHolder>(){
    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView)  {
        // Your holder should contain and initialize a member variable
        // for any view that will be set as you render a row
        val alarmNameTextView = itemView.findViewById<TextView>(R.id.alarm_context_name)
        var alarmSwitch = itemView.findViewById<Switch>(R.id.alarm_on_off)
        val alarmTimeTextView = itemView.findViewById<TextView>(R.id.alarm_time)
        val alarmRepeat = itemView.findViewById<TextView>(R.id.alarm_repeat)//TODO: check types later
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
        time.setText(alarm.call_time_string)
        repeatDescriptionTextView.setText(if (alarm.repeatState == true) "Once" else "Repeating")
        switch.setOnCheckedChangeListener { buttonView, isChecked ->
            mAlarm.elementAt(position).isOn = isChecked
        }
    }
    override fun getItemCount(): Int {
        return mAlarm.size
    }
}