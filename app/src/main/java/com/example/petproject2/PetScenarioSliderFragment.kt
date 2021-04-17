package com.example.petproject2

import androidx.fragment.app.Fragment

interface PetScenarioSliderFragment {
    fun showContent(petId: Int)
    fun getFragmentObject(): Fragment
}