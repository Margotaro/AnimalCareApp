package com.example.petproject2

interface PickerListener {
    fun UpdateDate(year: Int, month: Int, day: Int)
    fun UpdateTime(hourOfDay: Int, minute: Int)
}