package com.example.petproject2


interface OnAlarmChangeListener{
    fun onItemClicked(alarm: Alarm)
    fun onSwitchChecked(alarm: Alarm)
}