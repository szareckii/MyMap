package com.szareckii.map.view.marks

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.szareckii.map.R
import kotlinx.android.synthetic.main.dialog_edit_mark.view.*
import org.koin.android.viewmodel.ext.android.viewModel

private const val INDEX = "com.szareckii.map.view.marks.index"
private const val NAME = "com.szareckii.map.view.marks.name"
private const val DESCRIPTION = "com.szareckii.map.view.marks.description"

class EditMarkDialogFragment : DialogFragment() {

    val model: MarksViewModel by viewModel()

    private var markEditable: MarkEditable? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
            markEditable = context as MarkEditable
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        dialog!!.window?.setBackgroundDrawableResource(R.drawable.round_corner)
        val rootView: View = inflater.inflate(R.layout.dialog_edit_mark, container, false)

        val index = arguments?.getInt(INDEX)?:0
        val name = arguments?.getString(NAME)
        val description = arguments?.getString(DESCRIPTION)
        rootView.name_edit_text.setText(name)
        rootView.description_edit_text.setText(description)

        rootView.cancel_button.setOnClickListener { dialog!!.dismiss() }

        rootView.ok_button.setOnClickListener {
            markEditable?.edit(index, rootView.name_edit_text.text.toString(), rootView.description_edit_text.text.toString())
            dialog!!.dismiss()
        }

        return rootView
    }

}