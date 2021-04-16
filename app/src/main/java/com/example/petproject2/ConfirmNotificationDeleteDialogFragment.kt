package com.example.petproject2

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.petproject2.database.AppDatabase


class ConfirmNotificationDeleteDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)

            builder.setMessage(R.string.dialog_delete_notification)
                .setPositiveButton(R.string.yes,
                    DialogInterface.OnClickListener { dialog, id ->
                        arguments?.let {
                            val alarm = it.getParcelable<Alarm>("Alarm")
                            alarm?.let{
                                val intent = Intent()
                                intent.putExtra("Alarm", alarm)
                                targetFragment?.onActivityResult(DIALOG_FRAGMENT, Activity.RESULT_OK, intent)
                            }
                        }
                    })
                .setNegativeButton(R.string.no,
                    DialogInterface.OnClickListener { dialog, id ->
                        dismiss()
                    })
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}