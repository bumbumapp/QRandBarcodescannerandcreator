package com.qrbarcode.creator.feature.tabs.create.qr

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import com.qrbarcode.creator.R
import com.qrbarcode.creator.extension.isNotBlank
import com.qrbarcode.creator.extension.textString
import com.qrbarcode.creator.feature.tabs.create.BaseCreateBarcodeFragment
import com.qrbarcode.creator.model.schema.Cryptocurrency
import com.qrbarcode.creator.model.schema.Schema

class CreateQrCodeCryptocurrencyFragment : BaseCreateBarcodeFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_create_qr_code_cryptocurrency, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCryptocurrenciesSpinner()
        initAddressEditText()
        handleTextChanged()
    }

    override fun getBarcodeSchema(): Schema {
        val cryptocurrency = when (spinner_cryptocurrency.selectedItemPosition) {
            0 -> "bitcoin"
            1 -> "bitcoincash"
            2 -> "ethereum"
            3 -> "litecoin"
            4 -> "dash"
            else -> "bitcoin"
        }
        return Cryptocurrency(
            cryptocurrency = cryptocurrency,
            address = edit_text_address.textString,
            label = edit_text_label.textString,
            amount = edit_text_amount.textString,
            message = edit_text_message.textString
        )
    }

    private fun initCryptocurrenciesSpinner() {
        spinner_cryptocurrency.adapter = ArrayAdapter.createFromResource(
            requireContext(), R.array.fragment_create_qr_code_cryptocurrencies, R.layout.item_spinner
        ).apply {
            setDropDownViewResource(R.layout.item_spinner_dropdown)
        }
    }

    private fun initAddressEditText() {
        edit_text_address.requestFocus()
    }

    private fun handleTextChanged() {
        listOf(edit_text_address, edit_text_amount, edit_text_label, edit_text_message).forEach { editText ->
            editText.addTextChangedListener { toggleCreateBarcodeButton() }
        }
    }

    private fun toggleCreateBarcodeButton() {
        parentActivity.isCreateBarcodeButtonEnabled = edit_text_address.isNotBlank()
    }
}

// Former kotlinx.android.synthetic accessors (plugin removed in Kotlin 2).
private val android.app.Activity.edit_text_address: android.widget.EditText get() = findViewById(com.qrbarcode.creator.R.id.edit_text_address)
private val androidx.fragment.app.Fragment.edit_text_address: android.widget.EditText get() = requireView().findViewById(com.qrbarcode.creator.R.id.edit_text_address)
private val android.app.Dialog.edit_text_address: android.widget.EditText get() = findViewById(com.qrbarcode.creator.R.id.edit_text_address)
private val android.app.Activity.edit_text_amount: android.widget.EditText get() = findViewById(com.qrbarcode.creator.R.id.edit_text_amount)
private val androidx.fragment.app.Fragment.edit_text_amount: android.widget.EditText get() = requireView().findViewById(com.qrbarcode.creator.R.id.edit_text_amount)
private val android.app.Dialog.edit_text_amount: android.widget.EditText get() = findViewById(com.qrbarcode.creator.R.id.edit_text_amount)
private val android.app.Activity.edit_text_label: android.widget.EditText get() = findViewById(com.qrbarcode.creator.R.id.edit_text_label)
private val androidx.fragment.app.Fragment.edit_text_label: android.widget.EditText get() = requireView().findViewById(com.qrbarcode.creator.R.id.edit_text_label)
private val android.app.Dialog.edit_text_label: android.widget.EditText get() = findViewById(com.qrbarcode.creator.R.id.edit_text_label)
private val android.app.Activity.edit_text_message: android.widget.EditText get() = findViewById(com.qrbarcode.creator.R.id.edit_text_message)
private val androidx.fragment.app.Fragment.edit_text_message: android.widget.EditText get() = requireView().findViewById(com.qrbarcode.creator.R.id.edit_text_message)
private val android.app.Dialog.edit_text_message: android.widget.EditText get() = findViewById(com.qrbarcode.creator.R.id.edit_text_message)
private val android.app.Activity.spinner_cryptocurrency: android.widget.Spinner get() = findViewById(com.qrbarcode.creator.R.id.spinner_cryptocurrency)
private val androidx.fragment.app.Fragment.spinner_cryptocurrency: android.widget.Spinner get() = requireView().findViewById(com.qrbarcode.creator.R.id.spinner_cryptocurrency)
private val android.app.Dialog.spinner_cryptocurrency: android.widget.Spinner get() = findViewById(com.qrbarcode.creator.R.id.spinner_cryptocurrency)
