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
import androidx.room.Update
import com.example.petproject2.database.AppDatabase


const val DIALOG_FRAGMENT  = 2
private const val ALARM_CREATE = 1
private const val ARG_PET_ID = "PetId"

class NotificationFragment : Fragment(), OnAlarmChangeListener, PetScenarioSliderFragment {
    lateinit var database: AppDatabase
    lateinit var rvNotifications: RecyclerView
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
        val view = inflater.inflate(R.layout.fragment_alarm, container, false)
        rvNotifications = view.findViewById<View>(R.id.rvNotification) as RecyclerView

        rvNotifications.layoutManager = LinearLayoutManager(context)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)  {
        super.onActivityCreated(savedInstanceState)
        val createNotificationButton = view.findViewById<ImageButton>(R.id.buttonAddAlarm)
        createNotificationButton?.setOnClickListener {
            //getting data from activity to fragment
            val intent = Intent(context, CreateNotificationActivity::class.java)
            intent.putExtra("PetId", petId)

            startActivityForResult(intent, ALARM_CREATE)
        }
        petId?.let{
            showContent(it)
        }?.run{
            showContent(1)
        }
    }
    override fun showContent(petId: Int) {
        this.petId = petId
        RefreshNotificationListView()
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
                    RefreshNotificationListView()
                }

            }
        } else if (requestCode == DIALOG_FRAGMENT) {
            if (resultCode == Activity.RESULT_OK) {
                data?.getParcelableExtra<Alarm>("Alarm")?.generateNotificationEntity()?.let {
                    alarm ->
                    database.notificationDao().delete(alarm)
                    RefreshNotificationListView()
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

    override fun getFragmentObject(): Fragment {
        return this
    }
    fun RefreshNotificationListView() {
        rvNotifications.adapter = NotificationListAdapter(loadFromDatabase(), this)
    }
    companion object {
        @JvmStatic
        fun newInstance(petId: Int) =
            NotificationFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PET_ID, petId)
                }
            }
    }
}