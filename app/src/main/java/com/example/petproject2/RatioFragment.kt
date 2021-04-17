package com.example.petproject2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

private const val ARG_PET_ID = "PetId"

class RatioFragment : Fragment(), PetScenarioSliderFragment {
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
        return inflater.inflate(R.layout.fragment_ratio, container, false)
    }

    override fun showContent(petId: Int) {

    }

    override fun getFragmentObject(): Fragment {
        return this
    }
    companion object {
        @JvmStatic
        fun newInstance(petId: Int) =
            RatioFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PET_ID, petId)
                }
            }
    }
}