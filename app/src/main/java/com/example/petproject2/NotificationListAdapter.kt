package com.example.petproject2

import android.content.Context
import android.graphics.drawable.Drawable
import android.provider.AlarmClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.petproject2.database.PetEntity
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
class NotificationListAdapter (private val mAlarm: List<Alarm>) : RecyclerView.Adapter<NotificationListAdapter.ViewHolder>() {
    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        // Your holder should contain and initialize a member variable
        // for any view that will be set as you render a row
        val nameTextView = itemView.findViewById<TextView>(R.id.alarm_context_name)
        val messageButton = itemView.findViewById<Button>(R.id.turn_on_off_name)
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
        val textView = viewHolder.nameTextView
        textView.setText(alarm.notification_msg)
        val button = viewHolder.messageButton
        button.text = "TODO"
    }

    override fun getItemCount(): Int {
        return mAlarm.size
    }
}