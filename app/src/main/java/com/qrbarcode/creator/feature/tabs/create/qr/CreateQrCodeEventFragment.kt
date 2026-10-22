package com.qrbarcode.creator.feature.tabs.create.qr

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.qrbarcode.creator.R
import com.qrbarcode.creator.extension.textString
import com.qrbarcode.creator.feature.tabs.create.BaseCreateBarcodeFragment
import com.qrbarcode.creator.model.schema.Schema
import com.qrbarcode.creator.model.schema.VEvent

class CreateQrCodeEventFragment : BaseCreateBarcodeFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_create_qr_code_vevent, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        edit_text_title.requestFocus()
        parentActivity.isCreateBarcodeButtonEnabled = true
    }

    override fun getBarcodeSchema(): Schema {
        return VEvent(
            uid = edit_text_title.textString,
            organizer = edit_text_organizer.textString,
            summary = edit_text_summary.textString,
            startDate = button_date_time_start.dateTime,
            endDate = button_date_time_end.dateTime
        )
    }
}

// Former kotlinx.android.synthetic accessors (plugin removed in Kotlin 2).
private val android.app.Activity.button_date_time_end: com.qrbarcode.creator.feature.common.view.DateTimePickerButton get() = findViewById(com.qrbarcode.creator.R.id.button_date_time_end)
private val androidx.fragment.app.Fragment.button_date_time_end: com.qrbarcode.creator.feature.common.view.DateTimePickerButton get() = requireView().findViewById(com.qrbarcode.creator.R.id.button_date_time_end)
private val android.app.Dialog.button_date_time_end: com.qrbarcode.creator.feature.common.view.DateTimePickerButton get() = findViewById(com.qrbarcode.creator.R.id.button_date_time_end)
private val android.app.Activity.button_date_time_start: com.qrbarcode.creator.feature.common.view.DateTimePickerButton get() = findViewById(com.qrbarcode.creator.R.id.button_date_time_start)
private val androidx.fragment.app.Fragment.button_date_time_start: com.qrbarcode.creator.feature.common.view.DateTimePickerButton get() = requireView().findViewById(com.qrbarcode.creator.R.id.button_date_time_start)
private val android.app.Dialog.button_date_time_start: com.qrbarcode.creator.feature.common.view.DateTimePickerButton get() = findViewById(com.qrbarcode.creator.R.id.button_date_time_start)
private val android.app.Activity.edit_text_organizer: android.widget.EditText get() = findViewById(com.qrbarcode.creator.R.id.edit_text_organizer)
private val androidx.fragment.app.Fragment.edit_text_organizer: android.widget.EditText get() = requireView().findViewById(com.qrbarcode.creator.R.id.edit_text_organizer)
private val android.app.Dialog.edit_text_organizer: android.widget.EditText get() = findViewById(com.qrbarcode.creator.R.id.edit_text_organizer)
private val android.app.Activity.edit_text_summary: android.widget.EditText get() = findViewById(com.qrbarcode.creator.R.id.edit_text_summary)
private val androidx.fragment.app.Fragment.edit_text_summary: android.widget.EditText get() = requireView().findViewById(com.qrbarcode.creator.R.id.edit_text_summary)
private val android.app.Dialog.edit_text_summary: android.widget.EditText get() = findViewById(com.qrbarcode.creator.R.id.edit_text_summary)
private val android.app.Activity.edit_text_title: android.widget.EditText get() = findViewById(com.qrbarcode.creator.R.id.edit_text_title)
private val androidx.fragment.app.Fragment.edit_text_title: android.widget.EditText get() = requireView().findViewById(com.qrbarcode.creator.R.id.edit_text_title)
private val android.app.Dialog.edit_text_title: android.widget.EditText get() = findViewById(com.qrbarcode.creator.R.id.edit_text_title)
