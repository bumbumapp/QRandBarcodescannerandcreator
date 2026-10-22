package com.qrbarcode.creator.feature.barcode.save

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import com.qrbarcode.creator.R
import com.qrbarcode.creator.di.barcodeSaver
import com.qrbarcode.creator.di.permissionsHelper
import com.qrbarcode.creator.extension.applySystemWindowInsets
import com.qrbarcode.creator.extension.showError
import com.qrbarcode.creator.extension.unsafeLazy
import com.qrbarcode.creator.feature.BaseActivity
import com.qrbarcode.creator.model.Barcode
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

class SaveBarcodeAsTextActivity : BaseActivity() {

    companion object {
        private const val REQUEST_PERMISSIONS_CODE = 101
        // Saving goes through MediaStore, which needs no permission from Android 10 on.
        private val PERMISSIONS = if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        } else {
            emptyArray()
        }

        private const val BARCODE_KEY = "BARCODE_KEY"

        fun start(context: Context, barcode: Barcode) {
            val intent = Intent(context, SaveBarcodeAsTextActivity::class.java).apply {
                putExtra(BARCODE_KEY, barcode)
            }
            context.startActivity(intent)
        }
    }

    private val barcode by unsafeLazy {
        intent?.getSerializableExtra(BARCODE_KEY) as? Barcode ?: throw IllegalArgumentException("No barcode passed")
    }

    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save_barcode_as_text)
        supportEdgeToEdge()
        initToolbar()
        initFormatSpinner()
        initSaveButton()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (permissionsHelper.areAllPermissionsGranted(grantResults)) {
            saveBarcode()
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

    private fun initFormatSpinner() {
        spinner_save_as.adapter = ArrayAdapter.createFromResource(
            this, R.array.activity_save_barcode_as_text_formats, R.layout.item_spinner
        ).apply {
            setDropDownViewResource(R.layout.item_spinner_dropdown)
        }
    }

    private fun initSaveButton() {
        button_save.setOnClickListener {
            requestPermissions()
        }
    }

    private fun requestPermissions() {
        if (PERMISSIONS.isEmpty()) {
            saveBarcode()
            return
        }
        permissionsHelper.requestPermissions(this, PERMISSIONS, REQUEST_PERMISSIONS_CODE)
    }

    private fun saveBarcode() {
        val saveFunc = when (spinner_save_as.selectedItemPosition) {
            0 -> barcodeSaver::saveBarcodeAsCsv
            1 -> barcodeSaver::saveBarcodeAsJson
            else -> return
        }

        showLoading(true)

        saveFunc(this, barcode)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { showBarcodeSaved() },
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

    private fun showBarcodeSaved() {
        Toast.makeText(this, R.string.activity_save_barcode_as_text_file_name_saved, Toast.LENGTH_LONG).show()
        finish()
    }
}

// Former kotlinx.android.synthetic accessors (plugin removed in Kotlin 2).
private val android.app.Activity.button_save: android.widget.Button get() = findViewById(com.qrbarcode.creator.R.id.button_save)
private val androidx.fragment.app.Fragment.button_save: android.widget.Button get() = requireView().findViewById(com.qrbarcode.creator.R.id.button_save)
private val android.app.Dialog.button_save: android.widget.Button get() = findViewById(com.qrbarcode.creator.R.id.button_save)
private val android.app.Activity.progress_bar_loading: android.widget.ProgressBar get() = findViewById(com.qrbarcode.creator.R.id.progress_bar_loading)
private val androidx.fragment.app.Fragment.progress_bar_loading: android.widget.ProgressBar get() = requireView().findViewById(com.qrbarcode.creator.R.id.progress_bar_loading)
private val android.app.Dialog.progress_bar_loading: android.widget.ProgressBar get() = findViewById(com.qrbarcode.creator.R.id.progress_bar_loading)
private val android.app.Activity.root_view: androidx.coordinatorlayout.widget.CoordinatorLayout get() = findViewById(com.qrbarcode.creator.R.id.root_view)
private val androidx.fragment.app.Fragment.root_view: androidx.coordinatorlayout.widget.CoordinatorLayout get() = requireView().findViewById(com.qrbarcode.creator.R.id.root_view)
private val android.app.Dialog.root_view: androidx.coordinatorlayout.widget.CoordinatorLayout get() = findViewById(com.qrbarcode.creator.R.id.root_view)
private val android.app.Activity.scroll_view: androidx.core.widget.NestedScrollView get() = findViewById(com.qrbarcode.creator.R.id.scroll_view)
private val androidx.fragment.app.Fragment.scroll_view: androidx.core.widget.NestedScrollView get() = requireView().findViewById(com.qrbarcode.creator.R.id.scroll_view)
private val android.app.Dialog.scroll_view: androidx.core.widget.NestedScrollView get() = findViewById(com.qrbarcode.creator.R.id.scroll_view)
private val android.app.Activity.spinner_save_as: android.widget.Spinner get() = findViewById(com.qrbarcode.creator.R.id.spinner_save_as)
private val androidx.fragment.app.Fragment.spinner_save_as: android.widget.Spinner get() = requireView().findViewById(com.qrbarcode.creator.R.id.spinner_save_as)
private val android.app.Dialog.spinner_save_as: android.widget.Spinner get() = findViewById(com.qrbarcode.creator.R.id.spinner_save_as)
private val android.app.Activity.toolbar: androidx.appcompat.widget.Toolbar get() = findViewById(com.qrbarcode.creator.R.id.toolbar)
private val androidx.fragment.app.Fragment.toolbar: androidx.appcompat.widget.Toolbar get() = requireView().findViewById(com.qrbarcode.creator.R.id.toolbar)
private val android.app.Dialog.toolbar: androidx.appcompat.widget.Toolbar get() = findViewById(com.qrbarcode.creator.R.id.toolbar)
