package com.qrbarcode.creator.feature.tabs.create.barcode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.qrbarcode.creator.R
import com.qrbarcode.creator.extension.isNotBlank
import com.qrbarcode.creator.extension.textString
import com.qrbarcode.creator.feature.tabs.create.BaseCreateBarcodeFragment
import com.qrbarcode.creator.model.schema.Other
import com.qrbarcode.creator.model.schema.Schema

class CreateAztecFragment : BaseCreateBarcodeFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_create_aztec, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        edit_text.requestFocus()
        edit_text.addTextChangedListener {
            parentActivity.isCreateBarcodeButtonEnabled = edit_text.isNotBlank()
        }
    }

    override fun getBarcodeSchema(): Schema {
        return Other(edit_text.textString)
    }
}

// Former kotlinx.android.synthetic accessors (plugin removed in Kotlin 2).
private val android.app.Activity.edit_text: android.widget.EditText get() = findViewById(com.qrbarcode.creator.R.id.edit_text)
private val androidx.fragment.app.Fragment.edit_text: android.widget.EditText get() = requireView().findViewById(com.qrbarcode.creator.R.id.edit_text)
private val android.app.Dialog.edit_text: android.widget.EditText get() = findViewById(com.qrbarcode.creator.R.id.edit_text)
