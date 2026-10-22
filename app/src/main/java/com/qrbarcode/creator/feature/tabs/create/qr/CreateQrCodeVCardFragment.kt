package com.qrbarcode.creator.feature.tabs.create.qr

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.qrbarcode.creator.R
import com.qrbarcode.creator.extension.textString
import com.qrbarcode.creator.feature.tabs.create.BaseCreateBarcodeFragment
import com.qrbarcode.creator.model.Contact
import com.qrbarcode.creator.model.schema.Schema
import com.qrbarcode.creator.model.schema.VCard

class CreateQrCodeVCardFragment : BaseCreateBarcodeFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_create_qr_code_vcard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        edit_text_first_name.requestFocus()
        parentActivity.isCreateBarcodeButtonEnabled = true
    }

    override fun getBarcodeSchema(): Schema {
       return VCard(
           firstName = edit_text_first_name.textString,
           lastName = edit_text_last_name.textString,
           organization = edit_text_organization.textString,
           title = edit_text_job.textString,
           email = edit_text_email.textString,
           phone = edit_text_phone.textString,
           secondaryPhone = edit_text_fax.textString,
           url = edit_text_website.textString
       )
    }

    override fun showContact(contact: Contact) {
        edit_text_first_name.setText(contact.firstName)
        edit_text_last_name.setText(contact.lastName)
        edit_text_email.setText(contact.email)
        edit_text_phone.setText(contact.phone)
    }
}

// Former kotlinx.android.synthetic accessors (plugin removed in Kotlin 2).
private val android.app.Activity.edit_text_email: android.widget.EditText get() = findViewById(com.qrbarcode.creator.R.id.edit_text_email)
private val androidx.fragment.app.Fragment.edit_text_email: android.widget.EditText get() = requireView().findViewById(com.qrbarcode.creator.R.id.edit_text_email)
private val android.app.Dialog.edit_text_email: android.widget.EditText get() = findViewById(com.qrbarcode.creator.R.id.edit_text_email)
private val android.app.Activity.edit_text_fax: android.widget.EditText get() = findViewById(com.qrbarcode.creator.R.id.edit_text_fax)
private val androidx.fragment.app.Fragment.edit_text_fax: android.widget.EditText get() = requireView().findViewById(com.qrbarcode.creator.R.id.edit_text_fax)
private val android.app.Dialog.edit_text_fax: android.widget.EditText get() = findViewById(com.qrbarcode.creator.R.id.edit_text_fax)
private val android.app.Activity.edit_text_first_name: android.widget.EditText get() = findViewById(com.qrbarcode.creator.R.id.edit_text_first_name)
private val androidx.fragment.app.Fragment.edit_text_first_name: android.widget.EditText get() = requireView().findViewById(com.qrbarcode.creator.R.id.edit_text_first_name)
private val android.app.Dialog.edit_text_first_name: android.widget.EditText get() = findViewById(com.qrbarcode.creator.R.id.edit_text_first_name)
private val android.app.Activity.edit_text_job: android.widget.EditText get() = findViewById(com.qrbarcode.creator.R.id.edit_text_job)
private val androidx.fragment.app.Fragment.edit_text_job: android.widget.EditText get() = requireView().findViewById(com.qrbarcode.creator.R.id.edit_text_job)
private val android.app.Dialog.edit_text_job: android.widget.EditText get() = findViewById(com.qrbarcode.creator.R.id.edit_text_job)
private val android.app.Activity.edit_text_last_name: android.widget.EditText get() = findViewById(com.qrbarcode.creator.R.id.edit_text_last_name)
private val androidx.fragment.app.Fragment.edit_text_last_name: android.widget.EditText get() = requireView().findViewById(com.qrbarcode.creator.R.id.edit_text_last_name)
private val android.app.Dialog.edit_text_last_name: android.widget.EditText get() = findViewById(com.qrbarcode.creator.R.id.edit_text_last_name)
private val android.app.Activity.edit_text_organization: android.widget.EditText get() = findViewById(com.qrbarcode.creator.R.id.edit_text_organization)
private val androidx.fragment.app.Fragment.edit_text_organization: android.widget.EditText get() = requireView().findViewById(com.qrbarcode.creator.R.id.edit_text_organization)
private val android.app.Dialog.edit_text_organization: android.widget.EditText get() = findViewById(com.qrbarcode.creator.R.id.edit_text_organization)
private val android.app.Activity.edit_text_phone: android.widget.EditText get() = findViewById(com.qrbarcode.creator.R.id.edit_text_phone)
private val androidx.fragment.app.Fragment.edit_text_phone: android.widget.EditText get() = requireView().findViewById(com.qrbarcode.creator.R.id.edit_text_phone)
private val android.app.Dialog.edit_text_phone: android.widget.EditText get() = findViewById(com.qrbarcode.creator.R.id.edit_text_phone)
private val android.app.Activity.edit_text_website: android.widget.EditText get() = findViewById(com.qrbarcode.creator.R.id.edit_text_website)
private val androidx.fragment.app.Fragment.edit_text_website: android.widget.EditText get() = requireView().findViewById(com.qrbarcode.creator.R.id.edit_text_website)
private val android.app.Dialog.edit_text_website: android.widget.EditText get() = findViewById(com.qrbarcode.creator.R.id.edit_text_website)
