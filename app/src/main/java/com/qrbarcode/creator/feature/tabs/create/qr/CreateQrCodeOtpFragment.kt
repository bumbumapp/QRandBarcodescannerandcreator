package com.qrbarcode.creator.feature.tabs.create.qr

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.qrbarcode.creator.R
import com.qrbarcode.creator.extension.encodeBase32
import com.qrbarcode.creator.extension.isNotBlank
import com.qrbarcode.creator.extension.textString
import com.qrbarcode.creator.extension.toHmacAlgorithm
import com.qrbarcode.creator.feature.tabs.create.BaseCreateBarcodeFragment
import com.qrbarcode.creator.model.schema.OtpAuth
import com.qrbarcode.creator.model.schema.Schema
import dev.turingcomplete.kotlinonetimepassword.RandomSecretGenerator

import java.util.*

class CreateQrCodeOtpFragment : BaseCreateBarcodeFragment() {
    private val randomGenerator = RandomSecretGenerator()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_create_qr_code_otp, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initOtpTypesSpinner()
        initAlgorithmsSpinner()
        initEditTexts()
        initGenerateRandomSecretButton()
        showRandomSecret()
    }

    override fun getBarcodeSchema(): Schema {
        return OtpAuth(
            type = spinner_opt_types.selectedItem?.toString()?.lowercase(Locale.ENGLISH),
            algorithm = spinner_algorithms.selectedItem?.toString(),
            label = if (edit_text_issuer.isNotBlank()) {
                "${edit_text_issuer.textString}:${edit_text_account.textString}"
            } else {
                edit_text_account.textString
            },
            issuer = edit_text_issuer.textString,
            digits = edit_text_digits.textString.toIntOrNull(),
            period = edit_text_period.textString.toLongOrNull(),
            counter = edit_text_counter.textString.toLongOrNull(),
            secret = edit_text_secret.textString
        )
    }

    private fun initOtpTypesSpinner() {
        spinner_opt_types.adapter = ArrayAdapter.createFromResource(
            requireContext(), R.array.fragment_create_qr_code_otp_types, R.layout.item_spinner
        ).apply {
            setDropDownViewResource(R.layout.item_spinner_dropdown)
        }

        spinner_opt_types.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                text_input_layout_counter.isVisible = position == 0
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }

    private fun initAlgorithmsSpinner() {
        spinner_algorithms.adapter = ArrayAdapter.createFromResource(
            requireContext(), R.array.fragment_create_qr_code_otp_algorithms, R.layout.item_spinner
        ).apply {
            setDropDownViewResource(R.layout.item_spinner_dropdown)
        }
    }

    private fun initEditTexts() {
        edit_text_account.addTextChangedListener { toggleCreateBarcodeButton() }
        edit_text_secret.addTextChangedListener { toggleCreateBarcodeButton() }
        edit_text_period.addTextChangedListener { toggleCreateBarcodeButton() }
        edit_text_counter.addTextChangedListener { toggleCreateBarcodeButton() }
    }

    private fun initGenerateRandomSecretButton() {
        button_generate_random_secret.setOnClickListener {
            showRandomSecret()
        }
    }

    private fun toggleCreateBarcodeButton() {
        val isHotp = spinner_opt_types.selectedItemPosition == 0
        val areGeneralFieldsNotBlank = edit_text_account.isNotBlank() && edit_text_secret.isNotBlank()
        val areHotpFieldsNotBlank = edit_text_counter.isNotBlank() && edit_text_period.isNotBlank()
        parentActivity.isCreateBarcodeButtonEnabled = areGeneralFieldsNotBlank && (isHotp.not() || isHotp && areHotpFieldsNotBlank)
    }

    private fun showRandomSecret() {
        edit_text_secret.setText(generateRandomSecret())
    }

    private fun generateRandomSecret(): String {
        val algorithm = spinner_algorithms.selectedItem?.toString().toHmacAlgorithm()
        val secret = randomGenerator.createRandomSecret(algorithm)
        return secret.encodeBase32()
    }
}

// Former kotlinx.android.synthetic accessors (plugin removed in Kotlin 2).
private val android.app.Activity.button_generate_random_secret: android.widget.Button get() = findViewById(com.qrbarcode.creator.R.id.button_generate_random_secret)
private val androidx.fragment.app.Fragment.button_generate_random_secret: android.widget.Button get() = requireView().findViewById(com.qrbarcode.creator.R.id.button_generate_random_secret)
private val android.app.Dialog.button_generate_random_secret: android.widget.Button get() = findViewById(com.qrbarcode.creator.R.id.button_generate_random_secret)
private val android.app.Activity.edit_text_account: android.widget.EditText get() = findViewById(com.qrbarcode.creator.R.id.edit_text_account)
private val androidx.fragment.app.Fragment.edit_text_account: android.widget.EditText get() = requireView().findViewById(com.qrbarcode.creator.R.id.edit_text_account)
private val android.app.Dialog.edit_text_account: android.widget.EditText get() = findViewById(com.qrbarcode.creator.R.id.edit_text_account)
private val android.app.Activity.edit_text_counter: android.widget.EditText get() = findViewById(com.qrbarcode.creator.R.id.edit_text_counter)
private val androidx.fragment.app.Fragment.edit_text_counter: android.widget.EditText get() = requireView().findViewById(com.qrbarcode.creator.R.id.edit_text_counter)
private val android.app.Dialog.edit_text_counter: android.widget.EditText get() = findViewById(com.qrbarcode.creator.R.id.edit_text_counter)
private val android.app.Activity.edit_text_digits: android.widget.EditText get() = findViewById(com.qrbarcode.creator.R.id.edit_text_digits)
private val androidx.fragment.app.Fragment.edit_text_digits: android.widget.EditText get() = requireView().findViewById(com.qrbarcode.creator.R.id.edit_text_digits)
private val android.app.Dialog.edit_text_digits: android.widget.EditText get() = findViewById(com.qrbarcode.creator.R.id.edit_text_digits)
private val android.app.Activity.edit_text_issuer: android.widget.EditText get() = findViewById(com.qrbarcode.creator.R.id.edit_text_issuer)
private val androidx.fragment.app.Fragment.edit_text_issuer: android.widget.EditText get() = requireView().findViewById(com.qrbarcode.creator.R.id.edit_text_issuer)
private val android.app.Dialog.edit_text_issuer: android.widget.EditText get() = findViewById(com.qrbarcode.creator.R.id.edit_text_issuer)
private val android.app.Activity.edit_text_period: android.widget.EditText get() = findViewById(com.qrbarcode.creator.R.id.edit_text_period)
private val androidx.fragment.app.Fragment.edit_text_period: android.widget.EditText get() = requireView().findViewById(com.qrbarcode.creator.R.id.edit_text_period)
private val android.app.Dialog.edit_text_period: android.widget.EditText get() = findViewById(com.qrbarcode.creator.R.id.edit_text_period)
private val android.app.Activity.edit_text_secret: android.widget.EditText get() = findViewById(com.qrbarcode.creator.R.id.edit_text_secret)
private val androidx.fragment.app.Fragment.edit_text_secret: android.widget.EditText get() = requireView().findViewById(com.qrbarcode.creator.R.id.edit_text_secret)
private val android.app.Dialog.edit_text_secret: android.widget.EditText get() = findViewById(com.qrbarcode.creator.R.id.edit_text_secret)
private val android.app.Activity.spinner_algorithms: android.widget.Spinner get() = findViewById(com.qrbarcode.creator.R.id.spinner_algorithms)
private val androidx.fragment.app.Fragment.spinner_algorithms: android.widget.Spinner get() = requireView().findViewById(com.qrbarcode.creator.R.id.spinner_algorithms)
private val android.app.Dialog.spinner_algorithms: android.widget.Spinner get() = findViewById(com.qrbarcode.creator.R.id.spinner_algorithms)
private val android.app.Activity.spinner_opt_types: android.widget.Spinner get() = findViewById(com.qrbarcode.creator.R.id.spinner_opt_types)
private val androidx.fragment.app.Fragment.spinner_opt_types: android.widget.Spinner get() = requireView().findViewById(com.qrbarcode.creator.R.id.spinner_opt_types)
private val android.app.Dialog.spinner_opt_types: android.widget.Spinner get() = findViewById(com.qrbarcode.creator.R.id.spinner_opt_types)
private val android.app.Activity.text_input_layout_counter: com.google.android.material.textfield.TextInputLayout get() = findViewById(com.qrbarcode.creator.R.id.text_input_layout_counter)
private val androidx.fragment.app.Fragment.text_input_layout_counter: com.google.android.material.textfield.TextInputLayout get() = requireView().findViewById(com.qrbarcode.creator.R.id.text_input_layout_counter)
private val android.app.Dialog.text_input_layout_counter: com.google.android.material.textfield.TextInputLayout get() = findViewById(com.qrbarcode.creator.R.id.text_input_layout_counter)
