package com.qrbarcode.creator.feature.barcode

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.view.isVisible
import com.qrbarcode.creator.R
import com.qrbarcode.creator.di.barcodeImageGenerator
import com.qrbarcode.creator.extension.applySystemWindowInsets
import com.qrbarcode.creator.extension.toStringId
import com.qrbarcode.creator.extension.unsafeLazy
import com.qrbarcode.creator.feature.BaseActivity
import com.qrbarcode.creator.model.Barcode
import com.qrbarcode.creator.usecase.Logger

import java.text.SimpleDateFormat
import java.util.*

class BarcodeImageActivity : BaseActivity() {

    companion object {
        private const val BARCODE_KEY = "BARCODE_KEY"

        fun start(context: Context, barcode: Barcode) {
            val intent = Intent(context, BarcodeImageActivity::class.java)
            intent.putExtra(BARCODE_KEY, barcode)
            context.startActivity(intent)
        }
    }

    private val dateFormatter = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.ENGLISH)
    private val barcode by unsafeLazy {
        intent?.getSerializableExtra(BARCODE_KEY) as? Barcode ?: throw IllegalArgumentException("No barcode passed")
    }
    private var originalBrightness: Float = 0.5f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_barcode_image)
        supportEdgeToEdge()
        saveOriginalBrightness()
        handleToolbarBackPressed()
        handleToolbarMenuItemClicked()
        showMenu()
        showBarcode()
    }

    private fun supportEdgeToEdge() {
        root_view.applySystemWindowInsets(applyTop = true, applyBottom = true)
    }

    private fun saveOriginalBrightness() {
        originalBrightness = window.attributes.screenBrightness
    }

    private fun handleToolbarBackPressed() {
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun handleToolbarMenuItemClicked() {
        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.item_increase_brightness -> {
                    increaseBrightnessToMax()
                    toolbar.menu.apply {
                        findItem(R.id.item_increase_brightness).isVisible = false
                        findItem(R.id.item_decrease_brightness).isVisible = true
                    }
                }
                R.id.item_decrease_brightness -> {
                    restoreOriginalBrightness()
                    toolbar.menu.apply {
                        findItem(R.id.item_decrease_brightness).isVisible = false
                        findItem(R.id.item_increase_brightness).isVisible = true
                    }
                }
            }
            return@setOnMenuItemClickListener true
        }
    }

    private fun showMenu() {
        toolbar.inflateMenu(R.menu.menu_barcode_image)
    }

    private fun showBarcode() {
        showBarcodeImage()
        showBarcodeDate()
        showBarcodeFormat()
        showBarcodeText()
    }

    private fun showBarcodeImage() {
        try {
            val bitmap = barcodeImageGenerator.generateBitmap(barcode, 2000, 2000, 0, )
            image_view_barcode.setImageBitmap(bitmap)


        } catch (ex: Exception) {
            Logger.log(ex)
            image_view_barcode.isVisible = false
        }
    }

    private fun showBarcodeDate() {
        text_view_date.text = dateFormatter.format(barcode.date)
    }

    private fun showBarcodeFormat() {
        val format = barcode.format.toStringId()
        toolbar.setTitle(format)
    }

    private fun showBarcodeText() {
        text_view_barcode_text.text = barcode.text
    }

    private fun increaseBrightnessToMax() {
        setBrightness(1.0f)
    }

    private fun restoreOriginalBrightness() {
        setBrightness(originalBrightness)
    }

    private fun setBrightness(brightness: Float) {
        window.attributes = window.attributes.apply {
            screenBrightness = brightness
        }
    }
}

// Former kotlinx.android.synthetic accessors (plugin removed in Kotlin 2).
private val android.app.Activity.image_view_barcode: com.qrbarcode.creator.feature.common.view.SquareImageView get() = findViewById(com.qrbarcode.creator.R.id.image_view_barcode)
private val androidx.fragment.app.Fragment.image_view_barcode: com.qrbarcode.creator.feature.common.view.SquareImageView get() = requireView().findViewById(com.qrbarcode.creator.R.id.image_view_barcode)
private val android.app.Dialog.image_view_barcode: com.qrbarcode.creator.feature.common.view.SquareImageView get() = findViewById(com.qrbarcode.creator.R.id.image_view_barcode)
private val android.app.Activity.root_view: androidx.coordinatorlayout.widget.CoordinatorLayout get() = findViewById(com.qrbarcode.creator.R.id.root_view)
private val androidx.fragment.app.Fragment.root_view: androidx.coordinatorlayout.widget.CoordinatorLayout get() = requireView().findViewById(com.qrbarcode.creator.R.id.root_view)
private val android.app.Dialog.root_view: androidx.coordinatorlayout.widget.CoordinatorLayout get() = findViewById(com.qrbarcode.creator.R.id.root_view)
private val android.app.Activity.text_view_barcode_text: android.widget.TextView get() = findViewById(com.qrbarcode.creator.R.id.text_view_barcode_text)
private val androidx.fragment.app.Fragment.text_view_barcode_text: android.widget.TextView get() = requireView().findViewById(com.qrbarcode.creator.R.id.text_view_barcode_text)
private val android.app.Dialog.text_view_barcode_text: android.widget.TextView get() = findViewById(com.qrbarcode.creator.R.id.text_view_barcode_text)
private val android.app.Activity.text_view_date: android.widget.TextView get() = findViewById(com.qrbarcode.creator.R.id.text_view_date)
private val androidx.fragment.app.Fragment.text_view_date: android.widget.TextView get() = requireView().findViewById(com.qrbarcode.creator.R.id.text_view_date)
private val android.app.Dialog.text_view_date: android.widget.TextView get() = findViewById(com.qrbarcode.creator.R.id.text_view_date)
private val android.app.Activity.toolbar: androidx.appcompat.widget.Toolbar get() = findViewById(com.qrbarcode.creator.R.id.toolbar)
private val androidx.fragment.app.Fragment.toolbar: androidx.appcompat.widget.Toolbar get() = requireView().findViewById(com.qrbarcode.creator.R.id.toolbar)
private val android.app.Dialog.toolbar: androidx.appcompat.widget.Toolbar get() = findViewById(com.qrbarcode.creator.R.id.toolbar)
