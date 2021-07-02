package com.szareckii.map.view.marks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.szareckii.map.R
import kotlinx.android.synthetic.main.dialog_edit_mark.view.*

class EditMarkDialogFragment : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog!!.window?.setBackgroundDrawableResource(R.drawable.round_corner)
        val rootView: View = inflater.inflate(R.layout.dialog_edit_mark, container, false)

        val name = arguments?.getString("name")
        val description = arguments?.getString("description")
        rootView.name_edit_text.setText(name)
        rootView.description_edit_text.setText(description)

        rootView.cancel_button.setOnClickListener { dialog!!.dismiss() }

        rootView.ok_button.setOnClickListener {

            dialog!!.dismiss()
        }

        return rootView
    }

}