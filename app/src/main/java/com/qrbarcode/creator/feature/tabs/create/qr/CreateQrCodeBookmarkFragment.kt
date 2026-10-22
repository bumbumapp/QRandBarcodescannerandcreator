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
import com.qrbarcode.creator.model.schema.Bookmark
import com.qrbarcode.creator.model.schema.Schema

class CreateQrCodeBookmarkFragment : BaseCreateBarcodeFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_create_qr_code_bookmark, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTitleEditText()
        handleTextChanged()
    }

    override fun getBarcodeSchema(): Schema {
        return Bookmark(
            title = edit_text_title.textString,
            url = edit_text_url.textString
        )
    }

    private fun initTitleEditText() {
        edit_text_title.requestFocus()
    }

    private fun handleTextChanged() {
        edit_text_title.addTextChangedListener { toggleCreateBarcodeButton() }
        edit_text_url.addTextChangedListener { toggleCreateBarcodeButton() }
    }

    private fun toggleCreateBarcodeButton() {
        parentActivity.isCreateBarcodeButtonEnabled = edit_text_title.isNotBlank() || edit_text_url.isNotBlank()
    }
}

// Former kotlinx.android.synthetic accessors (plugin removed in Kotlin 2).
private val android.app.Activity.edit_text_title: android.widget.EditText get() = findViewById(com.qrbarcode.creator.R.id.edit_text_title)
private val androidx.fragment.app.Fragment.edit_text_title: android.widget.EditText get() = requireView().findViewById(com.qrbarcode.creator.R.id.edit_text_title)
private val android.app.Dialog.edit_text_title: android.widget.EditText get() = findViewById(com.qrbarcode.creator.R.id.edit_text_title)
private val android.app.Activity.edit_text_url: android.widget.EditText get() = findViewById(com.qrbarcode.creator.R.id.edit_text_url)
private val androidx.fragment.app.Fragment.edit_text_url: android.widget.EditText get() = requireView().findViewById(com.qrbarcode.creator.R.id.edit_text_url)
private val android.app.Dialog.edit_text_url: android.widget.EditText get() = findViewById(com.qrbarcode.creator.R.id.edit_text_url)
