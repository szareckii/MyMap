package com.szareckii.map.view.favorites

import android.R
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.dialog_edit_mark.*


class EditMarkDialogFragment : DialogFragment(), DialogInterface.OnClickListener {

//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        dialog!!.setTitle("Title!")
//        val v: View = inflater.inflate(R.layout.activity_list_item, null)
//        ok_button.setOnClickListener(this)
////        v.findViewById(R.id.btnNo).setOnClickListener(this)
//        return v
//    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setTitle("Title!")
            .setMessage(R.string.ok)
            .setPositiveButton(R.string.ok, this)
            .setNegativeButton(R.string.cancel, this)
            .create()


//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        val dialogEditMark = AlertDialog.Builder(requireContext())
//                val view = layoutInflater.inflate(R.layout.activity_main)
//            .setTitle("Title!")
//            .setMessage(R.string.ok)
//            .setPositiveButton(R.string.ok, this)
//            .setNegativeButton(R.string.cancel, this)
//            .create()
//        return
//    }

    override fun onClick(dialog: DialogInterface?, which: Int) {
//        when (which) {
//            Dialog.BUTTON_POSITIVE -> i = R.string.ok
            if (which == Dialog.BUTTON_POSITIVE) {
                println("!!!!!!!!!!!!!!!!!!!!!!!!")
            }
    }
}

//    fun onDismiss(dialog: DialogInterface?) {
//        super.onDismiss(dialog!!)
//        Log.d(LOG_TAG, "Dialog 2: onDismiss")
//    }
//
//    fun onCancel(dialog: DialogInterface?) {
//        super.onCancel(dialog!!)
//        Log.d(LOG_TAG, "Dialog 2: onCancel")
//    }

