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
import com.qrbarcode.creator.model.schema.Schema
import com.qrbarcode.creator.model.schema.Sms

class CreateQrCodeSmsFragment : BaseCreateBarcodeFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_create_qr_code_sms, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTitleEditText()
        handleTextChanged()
    }

    override fun showPhone(phone: String) {
        edit_text_phone.apply {
            setText(phone)
            setSelection(phone.length)
        }
    }

    override fun getBarcodeSchema(): Schema {
       return Sms(
           phone = edit_text_phone.textString,
           message = edit_text_message.textString
       )
    }

    private fun initTitleEditText() {
        edit_text_phone.requestFocus()
    }

    private fun handleTextChanged() {
        edit_text_phone.addTextChangedListener { toggleCreateBarcodeButton() }
        edit_text_message.addTextChangedListener { toggleCreateBarcodeButton() }
    }

    private fun toggleCreateBarcodeButton() {
        parentActivity.isCreateBarcodeButtonEnabled = edit_text_phone.isNotBlank() || edit_text_message.isNotBlank()
    }
}

// Former kotlinx.android.synthetic accessors (plugin removed in Kotlin 2).
private val android.app.Activity.edit_text_message: android.widget.EditText get() = findViewById(com.qrbarcode.creator.R.id.edit_text_message)
private val androidx.fragment.app.Fragment.edit_text_message: android.widget.EditText get() = requireView().findViewById(com.qrbarcode.creator.R.id.edit_text_message)
private val android.app.Dialog.edit_text_message: android.widget.EditText get() = findViewById(com.qrbarcode.creator.R.id.edit_text_message)
private val android.app.Activity.edit_text_phone: android.widget.EditText get() = findViewById(com.qrbarcode.creator.R.id.edit_text_phone)
private val androidx.fragment.app.Fragment.edit_text_phone: android.widget.EditText get() = requireView().findViewById(com.qrbarcode.creator.R.id.edit_text_phone)
private val android.app.Dialog.edit_text_phone: android.widget.EditText get() = findViewById(com.qrbarcode.creator.R.id.edit_text_phone)
