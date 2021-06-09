package com.example.petproject2

import android.app.*
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.example.petproject2.database.AppDatabase
import com.example.petproject2.database.NotificationEntity
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

    private fun createNotification(alarm: Alarm) {
        val intent = Intent(this, PetPageActivity::class.java)
        intent.extras?.putInt("PetId", alarm.petId)

        val contentIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val notificationId = 1
        val notificationChannelId = alarm.petId.toString()
        val service = getSystemService(NOTIFICATION_SERVICE) as? NotificationManager

        val b: NotificationCompat.Builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val mChannel = NotificationChannel(
                notificationChannelId,
                alarm.petId.toString(),
                NotificationManager.IMPORTANCE_DEFAULT
            )
            service?.createNotificationChannel(mChannel)
            NotificationCompat.Builder(this, notificationChannelId)
        } else {
            NotificationCompat.Builder(this)
        }

        b.setAutoCancel(true)
            .setDefaults(Notification.DEFAULT_ALL)
            .setSmallIcon(R.drawable.avatar)
            .setContentTitle(alarm.notification_msg)
            .setContentText(alarm.notification_msg)
            .setDefaults(Notification.DEFAULT_LIGHTS or Notification.DEFAULT_SOUND)
            .setContentIntent(contentIntent)
            .setContentInfo("Info")

        val notificationIntent = Intent(this, AlarmReceiver::class.java)
        notificationIntent.putExtra(AlarmReceiver.NOTIFICATION_ID, notificationId)
        notificationIntent.putExtra(AlarmReceiver.NOTIFICATION, b.build())
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            notificationId,
            notificationIntent,
            PendingIntent.FLAG_CANCEL_CURRENT
        )

        (getSystemService(ALARM_SERVICE) as? AlarmManager)?.setExact(AlarmManager.RTC_WAKEUP, alarm.callTimestamp, pendingIntent)
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
            } else {
                alarm?.notification_msg = descriptionTextField.text.toString()
                alarm?.let { alarm ->

                    if (!alarm.timeWasSet) {
                        Toast.makeText(this, "Set time" + alarm.displayCallTime, Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    else if (!alarm.dateWasSet) {
                    Toast.makeText(this, "Set date" + alarm.displayCallTime, Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                else {
                        alarm.notification_msg = descriptionTextField.text.toString()
                        alarm.isOn = switch.isChecked
                        var oldSavedIntent = getIntent()
                        val v = oldSavedIntent.getIntExtra("PetId", 0)
                        alarm.petId = oldSavedIntent.getIntExtra("PetId", 0)
                        val database = AppDatabase.getDatabase(this@CreateNotificationActivity)
                        database.notificationDao().insertAll(
                            NotificationEntity(
                                0,
                                alarm.petId,
                                alarm.notification_msg,
                                alarm.callTimestamp,
                                alarm.isOn
                            )
                        )
                        createNotification(alarm)

                    }
                    finish()
                }?.run {
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
        dateTextField.setText("Set date and time, please.")
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