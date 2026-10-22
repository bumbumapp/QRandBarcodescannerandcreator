package com.qrbarcode.creator.feature.tabs.create.barcode

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.qrbarcode.creator.R
import com.qrbarcode.creator.extension.applySystemWindowInsets
import com.qrbarcode.creator.feature.BaseActivity
import com.qrbarcode.creator.feature.tabs.create.CreateBarcodeActivity
import com.google.zxing.BarcodeFormat

class CreateBarcodeAllActivity : BaseActivity() {

    companion object {

        fun start(context: Context) {
            val intent = Intent(context, CreateBarcodeAllActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_barcode_all)
        supportEdgeToEdge()
        handleToolbarBackClicked()
        handleButtonsClicked()
    }

    private fun supportEdgeToEdge() {
        root_view.applySystemWindowInsets(applyTop = true, applyBottom = true)
    }

    private fun handleToolbarBackClicked() {
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun handleButtonsClicked() {
        button_data_matrix.setOnClickListener { CreateBarcodeActivity.start(this, BarcodeFormat.DATA_MATRIX) }
        button_aztec.setOnClickListener { CreateBarcodeActivity.start(this, BarcodeFormat.AZTEC) }
        button_pdf_417.setOnClickListener { CreateBarcodeActivity.start(this, BarcodeFormat.PDF_417) }
        button_codabar.setOnClickListener { CreateBarcodeActivity.start(this, BarcodeFormat.CODABAR) }
        button_code_39.setOnClickListener { CreateBarcodeActivity.start(this, BarcodeFormat.CODE_39) }
        button_code_93.setOnClickListener { CreateBarcodeActivity.start(this, BarcodeFormat.CODE_93) }
        button_code_128.setOnClickListener { CreateBarcodeActivity.start(this, BarcodeFormat.CODE_128) }
        button_ean_8.setOnClickListener { CreateBarcodeActivity.start(this, BarcodeFormat.EAN_8) }
        button_ean_13.setOnClickListener { CreateBarcodeActivity.start(this, BarcodeFormat.EAN_13) }
        button_itf_14.setOnClickListener { CreateBarcodeActivity.start(this, BarcodeFormat.ITF) }
        button_upc_a.setOnClickListener { CreateBarcodeActivity.start(this, BarcodeFormat.UPC_A) }
        button_upc_e.setOnClickListener { CreateBarcodeActivity.start(this, BarcodeFormat.UPC_E) }
    }
}

// Former kotlinx.android.synthetic accessors (plugin removed in Kotlin 2).
private val android.app.Activity.button_aztec: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = findViewById(com.qrbarcode.creator.R.id.button_aztec)
private val androidx.fragment.app.Fragment.button_aztec: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = requireView().findViewById(com.qrbarcode.creator.R.id.button_aztec)
private val android.app.Dialog.button_aztec: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = findViewById(com.qrbarcode.creator.R.id.button_aztec)
private val android.app.Activity.button_codabar: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = findViewById(com.qrbarcode.creator.R.id.button_codabar)
private val androidx.fragment.app.Fragment.button_codabar: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = requireView().findViewById(com.qrbarcode.creator.R.id.button_codabar)
private val android.app.Dialog.button_codabar: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = findViewById(com.qrbarcode.creator.R.id.button_codabar)
private val android.app.Activity.button_code_128: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = findViewById(com.qrbarcode.creator.R.id.button_code_128)
private val androidx.fragment.app.Fragment.button_code_128: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = requireView().findViewById(com.qrbarcode.creator.R.id.button_code_128)
private val android.app.Dialog.button_code_128: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = findViewById(com.qrbarcode.creator.R.id.button_code_128)
private val android.app.Activity.button_code_39: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = findViewById(com.qrbarcode.creator.R.id.button_code_39)
private val androidx.fragment.app.Fragment.button_code_39: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = requireView().findViewById(com.qrbarcode.creator.R.id.button_code_39)
private val android.app.Dialog.button_code_39: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = findViewById(com.qrbarcode.creator.R.id.button_code_39)
private val android.app.Activity.button_code_93: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = findViewById(com.qrbarcode.creator.R.id.button_code_93)
private val androidx.fragment.app.Fragment.button_code_93: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = requireView().findViewById(com.qrbarcode.creator.R.id.button_code_93)
private val android.app.Dialog.button_code_93: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = findViewById(com.qrbarcode.creator.R.id.button_code_93)
private val android.app.Activity.button_data_matrix: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = findViewById(com.qrbarcode.creator.R.id.button_data_matrix)
private val androidx.fragment.app.Fragment.button_data_matrix: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = requireView().findViewById(com.qrbarcode.creator.R.id.button_data_matrix)
private val android.app.Dialog.button_data_matrix: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = findViewById(com.qrbarcode.creator.R.id.button_data_matrix)
private val android.app.Activity.button_ean_13: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = findViewById(com.qrbarcode.creator.R.id.button_ean_13)
private val androidx.fragment.app.Fragment.button_ean_13: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = requireView().findViewById(com.qrbarcode.creator.R.id.button_ean_13)
private val android.app.Dialog.button_ean_13: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = findViewById(com.qrbarcode.creator.R.id.button_ean_13)
private val android.app.Activity.button_ean_8: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = findViewById(com.qrbarcode.creator.R.id.button_ean_8)
private val androidx.fragment.app.Fragment.button_ean_8: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = requireView().findViewById(com.qrbarcode.creator.R.id.button_ean_8)
private val android.app.Dialog.button_ean_8: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = findViewById(com.qrbarcode.creator.R.id.button_ean_8)
private val android.app.Activity.button_itf_14: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = findViewById(com.qrbarcode.creator.R.id.button_itf_14)
private val androidx.fragment.app.Fragment.button_itf_14: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = requireView().findViewById(com.qrbarcode.creator.R.id.button_itf_14)
private val android.app.Dialog.button_itf_14: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = findViewById(com.qrbarcode.creator.R.id.button_itf_14)
private val android.app.Activity.button_pdf_417: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = findViewById(com.qrbarcode.creator.R.id.button_pdf_417)
private val androidx.fragment.app.Fragment.button_pdf_417: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = requireView().findViewById(com.qrbarcode.creator.R.id.button_pdf_417)
private val android.app.Dialog.button_pdf_417: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = findViewById(com.qrbarcode.creator.R.id.button_pdf_417)
private val android.app.Activity.button_upc_a: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = findViewById(com.qrbarcode.creator.R.id.button_upc_a)
private val androidx.fragment.app.Fragment.button_upc_a: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = requireView().findViewById(com.qrbarcode.creator.R.id.button_upc_a)
private val android.app.Dialog.button_upc_a: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = findViewById(com.qrbarcode.creator.R.id.button_upc_a)
private val android.app.Activity.button_upc_e: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = findViewById(com.qrbarcode.creator.R.id.button_upc_e)
private val androidx.fragment.app.Fragment.button_upc_e: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = requireView().findViewById(com.qrbarcode.creator.R.id.button_upc_e)
private val android.app.Dialog.button_upc_e: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = findViewById(com.qrbarcode.creator.R.id.button_upc_e)
private val android.app.Activity.root_view: androidx.coordinatorlayout.widget.CoordinatorLayout get() = findViewById(com.qrbarcode.creator.R.id.root_view)
private val androidx.fragment.app.Fragment.root_view: androidx.coordinatorlayout.widget.CoordinatorLayout get() = requireView().findViewById(com.qrbarcode.creator.R.id.root_view)
private val android.app.Dialog.root_view: androidx.coordinatorlayout.widget.CoordinatorLayout get() = findViewById(com.qrbarcode.creator.R.id.root_view)
private val android.app.Activity.toolbar: androidx.appcompat.widget.Toolbar get() = findViewById(com.qrbarcode.creator.R.id.toolbar)
private val androidx.fragment.app.Fragment.toolbar: androidx.appcompat.widget.Toolbar get() = requireView().findViewById(com.qrbarcode.creator.R.id.toolbar)
private val android.app.Dialog.toolbar: androidx.appcompat.widget.Toolbar get() = findViewById(com.qrbarcode.creator.R.id.toolbar)
