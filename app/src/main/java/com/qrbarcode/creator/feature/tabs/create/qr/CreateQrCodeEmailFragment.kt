package com.qrbarcode.creator.feature.tabs.create.qr

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.qrbarcode.creator.R
import com.qrbarcode.creator.extension.isNotBlank
import com.qrbarcode.creator.extension.textString
import com.qrbarcode.creator.feature.tabs.create.BaseCreateBarcodeFragment
import com.qrbarcode.creator.model.schema.Email
import com.qrbarcode.creator.model.schema.Schema

class CreateQrCodeEmailFragment : BaseCreateBarcodeFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_create_qr_code_email, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTitleEditText()
        handleTextChanged()
    }

    override fun getBarcodeSchema(): Schema {
        return Email(
            email = edit_text_email.textString,
            subject = edit_text_subject.textString,
            body = edit_text_message.textString
        )
    }

    private fun initTitleEditText() {
        edit_text_email.requestFocus()
    }

    private fun handleTextChanged() {
        edit_text_email.addTextChangedListener { toggleCreateBarcodeButton() }
        edit_text_subject.addTextChangedListener { toggleCreateBarcodeButton() }
        edit_text_message.addTextChangedListener { toggleCreateBarcodeButton() }
    }

    private fun toggleCreateBarcodeButton() {
        parentActivity.isCreateBarcodeButtonEnabled = edit_text_email.isNotBlank() || edit_text_subject.isNotBlank() || edit_text_message.isNotBlank()
    }
}

// Former kotlinx.android.synthetic accessors (plugin removed in Kotlin 2).
private val android.app.Activity.edit_text_email: android.widget.EditText get() = findViewById(com.qrbarcode.creator.R.id.edit_text_email)
private val androidx.fragment.app.Fragment.edit_text_email: android.widget.EditText get() = requireView().findViewById(com.qrbarcode.creator.R.id.edit_text_email)
private val android.app.Dialog.edit_text_email: android.widget.EditText get() = findViewById(com.qrbarcode.creator.R.id.edit_text_email)
private val android.app.Activity.edit_text_message: android.widget.EditText get() = findViewById(com.qrbarcode.creator.R.id.edit_text_message)
private val androidx.fragment.app.Fragment.edit_text_message: android.widget.EditText get() = requireView().findViewById(com.qrbarcode.creator.R.id.edit_text_message)
private val android.app.Dialog.edit_text_message: android.widget.EditText get() = findViewById(com.qrbarcode.creator.R.id.edit_text_message)
private val android.app.Activity.edit_text_subject: android.widget.EditText get() = findViewById(com.qrbarcode.creator.R.id.edit_text_subject)
private val androidx.fragment.app.Fragment.edit_text_subject: android.widget.EditText get() = requireView().findViewById(com.qrbarcode.creator.R.id.edit_text_subject)
private val android.app.Dialog.edit_text_subject: android.widget.EditText get() = findViewById(com.qrbarcode.creator.R.id.edit_text_subject)
