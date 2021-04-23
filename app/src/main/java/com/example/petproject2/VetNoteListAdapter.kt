package com.example.petproject2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.petproject2.database.VetNote
import com.example.petproject2.database.VetNoteEntity

class VetNoteListAdapter(
    private val mVetNote: MutableList<VetNote>,
    val vetNoteChangeListener: OnVetNoteChangeListener
) : RecyclerView.Adapter<VetNoteListAdapter.ViewHolder>() {
    inner class ViewHolder(val listItemView: View) : RecyclerView.ViewHolder(listItemView)  {
        val vetNoteTitleTextView = itemView.findViewById<TextView>(R.id.title_vetNote)
        var vetNoteTimeTextView = itemView.findViewById<TextView>(R.id.time_vetNote)
        fun onClickDelete(note: VetNote, onClickListener: OnVetNoteChangeListener) {
            listItemView.setOnLongClickListener {
                onClickListener.onItemClicked(note)
                return@setOnLongClickListener true
            }
        }
    }

    // ... constructor and member variables
    // Usually involves inflating a layout from XML and returning the holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VetNoteListAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val vetNoteView = inflater.inflate(R.layout.item_vetnote, parent, false)
        // Return a new holder instance
        return ViewHolder(vetNoteView)
    }
    // Involves populating data into the item through holder
    override fun onBindViewHolder(viewHolder: VetNoteListAdapter.ViewHolder, position: Int) {
        // Get the data model based on position
        val note: VetNote = mVetNote.get(position)
        // Set item views based on your views and data model
        val title = viewHolder.vetNoteTitleTextView
        val time = viewHolder.vetNoteTimeTextView
        title.setText(note.diagnosisNote)
        time.setText(note.displayDiagnosisDate)

        viewHolder.onClickDelete(note, vetNoteChangeListener)
    }

    override fun getItemCount(): Int {
        return mVetNote.size
    }
}
