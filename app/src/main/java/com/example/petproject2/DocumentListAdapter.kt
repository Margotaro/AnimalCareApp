package com.example.petproject2

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.petproject2.database.Document
import com.example.petproject2.database.PetEntity

internal class DocumentListAdapter internal constructor(
    context: Context,
    private val resource: Int,
    private val itemList: List<Document>
) : ArrayAdapter<DocumentListAdapter.ItemHolder>(context, resource) {

    internal class ItemHolder {
        var name: TextView? = null
        var icon: ImageView? = null
        var rectanglePadding: View? = null
        var placeholderShadowing: View? = null
    }

    override fun getCount(): Int {
        return this.itemList.size + 1
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView

        val holder: DocumentListAdapter.ItemHolder
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, null)
            holder = DocumentListAdapter.ItemHolder()
            holder.name = convertView.findViewById(R.id.documentTitle)
            holder.icon = convertView.findViewById(R.id.documentIcon)

            convertView.tag = holder
        } else {
            holder = convertView.tag as DocumentListAdapter.ItemHolder
        }

        if (position >= itemList.size) {
            holder.name?.text = ""
            holder.icon?.setImageResource(R.drawable.newpet)
        } else {
            holder.name?.text = itemList[position].name
            val packageName = context.packageName
            val avatarPlaceholderId = context.getResources().getIdentifier("pet_placeholder1", "drawable", packageName)
            var selectedImage = BitmapFactory.decodeResource(context.getResources(), avatarPlaceholderId)
            if(itemList[position].link != null) {
                val avatarUri = Uri.parse(itemList[position].link)
                context.contentResolver.openInputStream(avatarUri)?.let { stream ->
                    selectedImage = BitmapFactory.decodeStream(stream)
                }
            } else {
                holder.placeholderShadowing?.alpha = 0.2f
            }
            //holder.icon?.setImageResource(context.getResources().getIdentifier(itemList[position].image, "drawable", packageName))
            holder.icon?.setImageBitmap(selectedImage)
        }

        return convertView!!
    }
}
