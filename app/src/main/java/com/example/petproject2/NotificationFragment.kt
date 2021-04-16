package com.example.petproject2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petproject2.database.AppDatabase


const val DIALOG_FRAGMENT  = 2
private const val ALARM_CREATE = 1


class NotificationFragment : Fragment(), OnAlarmChangeListener {
    var notifications: MutableList<Alarm> = mutableListOf()
    lateinit var database: AppDatabase
    lateinit var rvNotifications: RecyclerView

    var petId: Int? = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_alarm, container, false)
        rvNotifications = view.findViewById<View>(R.id.rvNotification) as RecyclerView

        rvNotifications.layoutManager = LinearLayoutManager(context)
        rvNotifications.adapter = NotificationListAdapter(notifications, this)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val createNotificationButton = view?.findViewById<ImageButton>(R.id.buttonAddAlarm)
        createNotificationButton?.setOnClickListener {
            //getting data from activity to fragment
            val intent = Intent(context, CreateNotificationActivity::class.java)
            intent.putExtra("PetId", petId)

            startActivityForResult(intent, ALARM_CREATE)
        }
    }
    fun show(petId: Int) {
        this.petId = petId
        rvNotifications.adapter = NotificationListAdapter(loadFromDatabase(), this)
    }

    fun loadFromDatabase(): MutableList<Alarm>{
        context?.let {
            database = AppDatabase.getDatabase(it)
            petId?.let {
                var notificationcollection = database.notificationDao().findPetNotifications(it)
                return notificationcollection?.bar?.map{ notificationEntity -> Alarm(
                    notificationEntity
                )
                }?.toMutableList() ?: mutableListOf<Alarm>()
            }
            return mutableListOf<Alarm>()
        }
        return mutableListOf<Alarm>()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == ALARM_CREATE) {
            database.let {
                if(petId == null) return@let
                val petEntityList = it.notificationDao().findPetNotifications(petId!!)?.bar
                val alarmList = petEntityList?.map{ notificationEntity -> Alarm(notificationEntity) }?.toMutableList()
                alarmList?.let {
                    notifications = it
                }?.run {
                    notifications = mutableListOf()
                }

            }
        } else if (requestCode == DIALOG_FRAGMENT) {
            if (resultCode == Activity.RESULT_OK) {
                data?.getParcelableExtra<Alarm>("Alarm")?.generateNotificationEntity()?.let {
                    alarm ->
                    database.notificationDao().delete(alarm)
                    rvNotifications.adapter = NotificationListAdapter(loadFromDatabase(), this)
                }
            }
        }
    }

    override fun onItemClicked(alarm: Alarm) {

        val confirmDeleteDialog = ConfirmNotificationDeleteDialogFragment()
        confirmDeleteDialog.setTargetFragment(this, 0)
        val bundle = Bundle()
        bundle.putParcelable("Alarm", alarm)
        confirmDeleteDialog.arguments = bundle
        confirmDeleteDialog.show(parentFragmentManager, "dialog")
    }

    override fun onSwitchChecked(alarm: Alarm) {
        database.notificationDao().update(alarm.generateNotificationEntity())
    }
}