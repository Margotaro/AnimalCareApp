package com.example.petproject2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.petproject2.database.VetNote

class StringListAdapter(
    private val mStringList: MutableList<String>,
    val listItemChangeListener: IllnessOptionClickListener
) : RecyclerView.Adapter<StringListAdapter.ViewHolder>() {

    inner class ViewHolder(val listItemView: View) : RecyclerView.ViewHolder(listItemView)  {
        val illnessTextView = itemView.findViewById<TextView>(R.id.title_illnessType)
        fun onClickOpen(illnessTypeString: String, onClickListener: IllnessOptionClickListener) {
            listItemView.setOnClickListener {
                onClickListener.onItemClicked(illnessTypeString)
                return@setOnClickListener
            }
        }
    }

    // ... constructor and member variables
    // Usually involves inflating a layout from XML and returning the holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StringListAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val vetNoteView = inflater.inflate(R.layout.item_illness_type_option, parent, false)
        // Return a new holder instance
        return ViewHolder(vetNoteView)
    }
    // Involves populating data into the item through holder
    override fun onBindViewHolder(viewHolder: StringListAdapter.ViewHolder, position: Int) {
        // Get the data model based on position
        val illness: String = mStringList.get(position)
        // Set item views based on your views and data model
        val title = viewHolder.illnessTextView
        title.setText(illness)
        viewHolder.onClickOpen(illness, listItemChangeListener)
    }

    override fun getItemCount(): Int {
        return mStringList.size
    }
}

