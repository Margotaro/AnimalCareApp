package com.example.petproject2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


private const val ARG_PET_ID = "PetId"

class MedicalFragment : Fragment(), PetScenarioSliderFragment {
    private var petId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            petId = it.getInt(ARG_PET_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_medical, container, false)
        return rootView
    }

    override fun showContent(petId: Int) {

    }
    override fun getFragmentObject(): Fragment {
        return this
    }

    companion object {
        @JvmStatic
        fun newInstance(petId: Int) =
            MedicalFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PET_ID, petId)
                }
            }
    }
}