package com.qrbarcode.creator.feature.tabs.history.export

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.qrbarcode.creator.R
import com.qrbarcode.creator.di.barcodeDatabase
import com.qrbarcode.creator.di.barcodeSaver
import com.qrbarcode.creator.di.permissionsHelper
import com.qrbarcode.creator.extension.applySystemWindowInsets
import com.qrbarcode.creator.extension.isNotBlank
import com.qrbarcode.creator.extension.showError
import com.qrbarcode.creator.extension.textString
import com.qrbarcode.creator.feature.BaseActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

class ExportHistoryActivity : BaseActivity() {
    private val disposable = CompositeDisposable()

    companion object {
        private const val REQUEST_PERMISSIONS_CODE = 101
        // Saving goes through MediaStore, which needs no permission from Android 10 on.
        private val PERMISSIONS = if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        } else {
            emptyArray()
        }

        fun start(context: Context) {
            val intent = Intent(context, ExportHistoryActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_export_history)
        supportEdgeToEdge()
        initToolbar()
        initExportTypeSpinner()
        initFileNameEditText()
        initExportButton()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (permissionsHelper.areAllPermissionsGranted(grantResults)) {
            exportHistory()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }

    private fun supportEdgeToEdge() {
        root_view.applySystemWindowInsets(applyTop = true, applyBottom = true)
    }

    private fun initToolbar() {
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun initExportTypeSpinner() {
        spinner_export_as.adapter = ArrayAdapter.createFromResource(
            this, R.array.activity_export_history_types, R.layout.item_spinner
        ).apply {
            setDropDownViewResource(R.layout.item_spinner_dropdown)
        }
    }

    private fun initFileNameEditText() {
        edit_text_file_name.addTextChangedListener {
            button_export.isEnabled = edit_text_file_name.isNotBlank()
        }
    }

    private fun initExportButton() {
        button_export.setOnClickListener {
            requestPermissions()
        }
    }

    private fun requestPermissions() {
        if (PERMISSIONS.isEmpty()) {
            exportHistory()
            return
        }
        permissionsHelper.requestPermissions(this, PERMISSIONS, REQUEST_PERMISSIONS_CODE)
    }

    private fun exportHistory() {
        val fileName = edit_text_file_name.textString
        val saveFunc = when (spinner_export_as.selectedItemPosition) {
            0 -> barcodeSaver::saveBarcodeHistoryAsCsv
            1 -> barcodeSaver::saveBarcodeHistoryAsJson
            else -> return
        }

        showLoading(true)

        barcodeDatabase
            .getAllForExport()
            .flatMapCompletable { barcodes ->
                saveFunc(this, fileName, barcodes)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    showHistoryExported()
                },
                { error ->
                    showLoading(false)
                    showError(error)
                }
            )
            .addTo(disposable)
    }

    private fun showLoading(isLoading: Boolean) {
        progress_bar_loading.isVisible = isLoading
        scroll_view.isVisible = isLoading.not()
    }

    private fun showHistoryExported() {
        Toast.makeText(this, R.string.activity_export_history_exported, Toast.LENGTH_LONG).show()
        finish()
    }
}

// Former kotlinx.android.synthetic accessors (plugin removed in Kotlin 2).
private val android.app.Activity.button_export: android.widget.Button get() = findViewById(com.qrbarcode.creator.R.id.button_export)
private val androidx.fragment.app.Fragment.button_export: android.widget.Button get() = requireView().findViewById(com.qrbarcode.creator.R.id.button_export)
private val android.app.Dialog.button_export: android.widget.Button get() = findViewById(com.qrbarcode.creator.R.id.button_export)
private val android.app.Activity.edit_text_file_name: android.widget.EditText get() = findViewById(com.qrbarcode.creator.R.id.edit_text_file_name)
private val androidx.fragment.app.Fragment.edit_text_file_name: android.widget.EditText get() = requireView().findViewById(com.qrbarcode.creator.R.id.edit_text_file_name)
private val android.app.Dialog.edit_text_file_name: android.widget.EditText get() = findViewById(com.qrbarcode.creator.R.id.edit_text_file_name)
private val android.app.Activity.progress_bar_loading: android.widget.ProgressBar get() = findViewById(com.qrbarcode.creator.R.id.progress_bar_loading)
private val androidx.fragment.app.Fragment.progress_bar_loading: android.widget.ProgressBar get() = requireView().findViewById(com.qrbarcode.creator.R.id.progress_bar_loading)
private val android.app.Dialog.progress_bar_loading: android.widget.ProgressBar get() = findViewById(com.qrbarcode.creator.R.id.progress_bar_loading)
private val android.app.Activity.root_view: androidx.coordinatorlayout.widget.CoordinatorLayout get() = findViewById(com.qrbarcode.creator.R.id.root_view)
private val androidx.fragment.app.Fragment.root_view: androidx.coordinatorlayout.widget.CoordinatorLayout get() = requireView().findViewById(com.qrbarcode.creator.R.id.root_view)
private val android.app.Dialog.root_view: androidx.coordinatorlayout.widget.CoordinatorLayout get() = findViewById(com.qrbarcode.creator.R.id.root_view)
private val android.app.Activity.scroll_view: androidx.core.widget.NestedScrollView get() = findViewById(com.qrbarcode.creator.R.id.scroll_view)
private val androidx.fragment.app.Fragment.scroll_view: androidx.core.widget.NestedScrollView get() = requireView().findViewById(com.qrbarcode.creator.R.id.scroll_view)
private val android.app.Dialog.scroll_view: androidx.core.widget.NestedScrollView get() = findViewById(com.qrbarcode.creator.R.id.scroll_view)
private val android.app.Activity.spinner_export_as: android.widget.Spinner get() = findViewById(com.qrbarcode.creator.R.id.spinner_export_as)
private val androidx.fragment.app.Fragment.spinner_export_as: android.widget.Spinner get() = requireView().findViewById(com.qrbarcode.creator.R.id.spinner_export_as)
private val android.app.Dialog.spinner_export_as: android.widget.Spinner get() = findViewById(com.qrbarcode.creator.R.id.spinner_export_as)
private val android.app.Activity.toolbar: androidx.appcompat.widget.Toolbar get() = findViewById(com.qrbarcode.creator.R.id.toolbar)
private val androidx.fragment.app.Fragment.toolbar: androidx.appcompat.widget.Toolbar get() = requireView().findViewById(com.qrbarcode.creator.R.id.toolbar)
private val android.app.Dialog.toolbar: androidx.appcompat.widget.Toolbar get() = findViewById(com.qrbarcode.creator.R.id.toolbar)
