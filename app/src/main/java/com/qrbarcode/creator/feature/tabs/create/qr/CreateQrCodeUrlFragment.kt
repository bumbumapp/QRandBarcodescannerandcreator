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
import com.qrbarcode.creator.model.schema.Url
import kotlinx.android.synthetic.main.fragment_create_qr_code_url.*

class CreateQrCodeUrlFragment : BaseCreateBarcodeFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_create_qr_code_url, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showUrlPrefix()
        handleTextChanged()
    }

    override fun getBarcodeSchema(): Schema {
        return Url(edit_text.textString)
    }

    private fun showUrlPrefix() {
        val prefix = "https://"
        edit_text.apply {
            setText(prefix)
            setSelection(prefix.length)
            requestFocus()
        }
    }

    private fun handleTextChanged() {
        edit_text.addTextChangedListener {
            parentActivity.isCreateBarcodeButtonEnabled = edit_text.isNotBlank()
        }
    }
}