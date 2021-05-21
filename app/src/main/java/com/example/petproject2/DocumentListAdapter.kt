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
import com.example.petproject2.database.PetEntity

internal class DocumentListAdapter internal constructor(
    context: Context,
    private val resource: Int,
    private val itemList: List<PetEntity>
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


        val holder: ImageListAdapter.ItemHolder
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, null)
            holder = ImageListAdapter.ItemHolder()
            holder.name = convertView.findViewById(R.id.textView)
            holder.icon = convertView.findViewById(R.id.icon)
            holder.rectanglePadding = convertView.findViewById(R.id.myRectangleView)
            holder.placeholderShadowing = convertView.findViewById(R.id.myRectanglePlaceholderView)

            convertView.tag = holder
        } else {
            holder = convertView.tag as ImageListAdapter.ItemHolder
        }

        if (position >= itemList.size) {
            holder.name?.text = ""
            holder.icon?.setImageResource(R.drawable.newpet)
            holder.rectanglePadding?.alpha = 0.0f
        } else {
            holder.name?.text = itemList[position].name
            val packageName = context.packageName
            val avatarPlaceholderId = context.getResources().getIdentifier("pet_placeholder1", "drawable", packageName)
            var selectedImage = BitmapFactory.decodeResource(context.getResources(), avatarPlaceholderId)
            if(itemList[position].image != null) {
                val avatarUri = Uri.parse(itemList[position].image)
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