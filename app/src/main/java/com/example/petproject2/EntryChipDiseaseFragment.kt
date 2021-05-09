package com.example.petproject2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.content.res.AppCompatResources.getColorStateList
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class EntryChipDiseaseFragment : Fragment() {
    private val DISEASES = arrayOf("Flu", "Cavities", "Colitis", "Flea", "Helminthiasis", "Alopecia")
    private lateinit var mView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_entry_chip_disease, container, false)

        context?.let {
            val adapter = ArrayAdapter<String>(it,
                android.R.layout.simple_dropdown_item_1line, DISEASES)

            val autoCompleteTextView = mView.findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)
            val chipGroup2 = mView.findViewById<ChipGroup>(R.id.chipGroup2)
            autoCompleteTextView.setAdapter<ArrayAdapter<String>>(adapter)
            autoCompleteTextView.setOnItemClickListener { parent, arg1, position, arg3 ->
                autoCompleteTextView.text = null
                val selected = parent.getItemAtPosition(position) as String
                addChipToGroup(selected, chipGroup2)
            }
        }

        return mView
    }


    private fun addChipToGroup(person: String, chipGroup: ChipGroup) {
        val chip = Chip(context)
        chip.text = person
        chip.isCloseIconVisible = true
        context?.let {
            chip.chipBackgroundColor = getColorStateList(it, R.color.design_default_color_secondary)
        }
        // necessary to get single selection working
        chip.isClickable = true
        chip.isCheckable = false
        chipGroup.addView(chip as View)
        chip.setOnCloseIconClickListener { chipGroup.removeView(chip as View) }
    }

    companion object {
        @JvmStatic
        fun newInstance() = EntryChipDiseaseFragment()
    }
}