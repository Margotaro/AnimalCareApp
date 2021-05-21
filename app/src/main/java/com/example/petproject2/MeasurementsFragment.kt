package com.example.petproject2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.example.petproject2.measurements.MeasurementViewScenario

private const val ARG_PET_ID = "PetId"

class MeasurementsFragment : Fragment(), PetScenarioSliderFragment {
    private lateinit var listView: ListView
    private lateinit var addButton: View
    private lateinit var scenario: MeasurementViewScenario
    var petId = -1

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
        val rootView = inflater.inflate(R.layout.fragment_measurements, container, false)
        listView = rootView.findViewById<ListView>(R.id.measurementRecordsList)
        addButton = rootView.findViewById(R.id.buttonAddMeasurement)
        return rootView
    }

    override fun onStart() {
        super.onStart()
        scenario = MeasurementViewScenario(listView, addButton, petId,
            layoutInflater.context, layoutInflater)
        scenario.loadViews()
    }

    override fun showContent(petId: Int) {

    }

    override fun getFragmentObject(): Fragment {
        return this
    }
    companion object {
        @JvmStatic
        fun newInstance(petId: Int) =
            MeasurementsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PET_ID, petId)
                }
            }
    }
}