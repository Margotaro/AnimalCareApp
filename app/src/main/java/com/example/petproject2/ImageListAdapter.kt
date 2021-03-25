package com.example.petproject2

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.petproject2.database.PetEntity

internal class ImageListAdapter internal constructor(context: Context, private val resource: Int, private val itemList: List<PetEntity>) : ArrayAdapter<ImageListAdapter.ItemHolder>(context, resource) {


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

    override fun add(`object`: ItemHolder?) {
        super.insert(`object`, itemList!!.size - 1)
    }
}
