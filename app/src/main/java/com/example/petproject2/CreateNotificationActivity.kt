package com.example.petproject2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.*
import com.example.petproject2.database.AppDatabase
import com.example.petproject2.database.NotificationEntity
import com.example.petproject2.database.NotificationListener
import kotlinx.android.synthetic.main.item_alarm.view.*

class CreateNotificationActivity : AppCompatActivity(), PickerListener {

    private lateinit var okButton: ImageButton
    private lateinit var noButton: ImageButton
    private lateinit var setDateButton: Button
    private lateinit var datePickerFragment: DatePickerFragment
    private lateinit var switch: Switch
    private lateinit var descriptionTextField: TextView
    private lateinit var dateTextField: TextView
    private lateinit var timePicker: TimePicker

    var alarm: Alarm? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_notification)

        datePickerFragment = DatePickerFragment()
        alarm = Alarm()
    }

    override fun onStart() {
        super.onStart()
        okButton = findViewById(R.id.okButtonCreateNotification)
        noButton = findViewById(R.id.noButtonCreateNotification)
        setDateButton = findViewById(R.id.setDateButtonCreateNotification)
        switch = findViewById(R.id.switchCreateNotification)
        descriptionTextField = findViewById(R.id.descriptionTextInputCreateNotification)
        dateTextField = findViewById(R.id.texViewDateCreateNotification)
        timePicker = findViewById(R.id.timePickerCreateNotification)

        timePicker.setIs24HourView(true)

        okButton.setOnClickListener {
            if (TextUtils.isEmpty(descriptionTextField.text)) {
                Toast.makeText(this, "Make description", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else {
                alarm?.let { alarm ->
                    if (!alarm.timeWasSet) {
                        Toast.makeText(this, "Set time" + alarm.displayCallTime, Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    else if (!alarm.dateWasSet) {
                        Toast.makeText(this, "Set date" + alarm.displayCallTime, Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    else
                    {
                        alarm.notification_msg = descriptionTextField.text.toString()
                        alarm.isOn = switch.isChecked
                        var oldSavedIntent = getIntent()
                        val v = oldSavedIntent.getIntExtra("PetId", 0)
                        alarm.petId = oldSavedIntent.getIntExtra("PetId", 0)
                        val database = AppDatabase.getDatabase(this@CreateNotificationActivity)
                        database.notificationDao().insertAll(NotificationEntity(0, alarm.petId, alarm.notification_msg, alarm.callTimestamp, alarm.isOn))
                        finish()
                    }
                }?.run {
                    //alarm = Alarm(descriptionTextField.text.toString(), timePicker)}
                    return@setOnClickListener
                }
            }
        }
        noButton.setOnClickListener {
            finish()
        }

        setDateButton.setOnClickListener {
            datePickerFragment.show(supportFragmentManager, "datePicker");
        }

        timePicker.setOnTimeChangedListener { _, hour, minute ->
            UpdateTime(hour, minute)
        }

    }
    override fun UpdateDate(year: Int, month: Int, day: Int) {
        alarm?.setDate(year, month, day)
        dateTextField.setText(alarm?.displayCallTime)
    }

    override fun UpdateTime(hourOfDay: Int, minute: Int) {
        alarm?.setTime(hourOfDay, minute)
        dateTextField.setText(alarm?.displayCallTime)
    }
}