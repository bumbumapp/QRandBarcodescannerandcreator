package com.qrbarcode.creator.feature.barcode.customize

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import com.qrbarcode.creator.R
import com.qrbarcode.creator.di.barcodeImageSaver
import com.qrbarcode.creator.extension.applySystemWindowInsets
import com.qrbarcode.creator.extension.showError
import com.qrbarcode.creator.extension.unsafeLazy
import com.qrbarcode.creator.feature.BaseActivity
import com.qrbarcode.creator.feature.common.view.SquareImageView
import com.qrbarcode.creator.model.Barcode
import com.qrbarcode.creator.model.ParsedBarcode
import com.qrbarcode.creator.usecase.ColorContrast
import com.qrbarcode.creator.usecase.Logger
import com.qrbarcode.creator.usecase.StyledQrGenerator
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

/** Style a QR code (colours, error correction, centre logo), then share it or save it to the gallery. */
class CustomizeBarcodeActivity : BaseActivity() {

    companion object {
        private const val BARCODE_KEY = "BARCODE_KEY"

        val codeColors = intArrayOf(
            0xFF000000.toInt(), 0xFF1F3A8A.toInt(), 0xFF14532D.toInt(),
            0xFF9F1239.toInt(), 0xFF581C87.toInt(), 0xFF9A3412.toInt()
        )
        val backgroundColors = intArrayOf(
            0xFFFFFFFF.toInt(), 0xFFFFF7E0.toInt(), 0xFFE0F2FE.toInt(),
            0xFFDCFCE7.toInt(), 0xFFFCE7F3.toInt(), 0xFFF1F5F9.toInt()
        )

        fun start(context: Context, barcode: Barcode) {
            context.startActivity(Intent(context, CustomizeBarcodeActivity::class.java).putExtra(BARCODE_KEY, barcode))
        }
    }

    private val barcode by unsafeLazy {
        intent?.getSerializableExtra(BARCODE_KEY) as? Barcode ?: throw IllegalArgumentException("No barcode passed")
    }
    private val disposable = CompositeDisposable()

    private var codeColor = codeColors[0]
    private var backgroundColor = backgroundColors[0]
    private var level = "M"
    private var logo: Bitmap? = null
    private var current: Bitmap? = null

    private val pickLogo = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            logo = decodeLogo(uri)
            if (logo == null) {
                Toast.makeText(this, R.string.customize_logo_failed, Toast.LENGTH_LONG).show()
            }
            render()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customize_barcode)
        findViewById<View>(R.id.root_view).applySystemWindowInsets(applyTop = true, applyBottom = true)
        findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar).setNavigationOnClickListener { finish() }

        buildSwatches(findViewById(R.id.row_code_colors), codeColors) { codeColor = it; render() }
        buildSwatches(findViewById(R.id.row_background_colors), backgroundColors) { backgroundColor = it; render() }
        buildLevelChips()

        findViewById<Button>(R.id.button_logo).setOnClickListener {
            if (logo != null) {
                logo = null
                render()
            } else {
                pickLogo.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
        }
        findViewById<Button>(R.id.button_share).setOnClickListener { share() }
        findViewById<Button>(R.id.button_save).setOnClickListener { save() }
        render()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }

    private fun buildSwatches(row: LinearLayout, colors: IntArray, onPick: (Int) -> Unit) {
        val size = (resources.displayMetrics.density * 44).toInt()
        val gap = (resources.displayMetrics.density * 10).toInt()
        for ((i, color) in colors.withIndex()) {
            val v = View(this)
            v.layoutParams = LinearLayout.LayoutParams(size, size).apply { marginEnd = gap }
            v.contentDescription = String.format("#%06X", color and 0xFFFFFF)
            v.setOnClickListener {
                onPick(color)
                markSelected(row, i)
            }
            row.addView(v)
        }
        markSelected(row, 0)
    }

    private fun markSelected(row: LinearLayout, selected: Int) {
        val d = resources.displayMetrics.density
        val colors = if (row.id == R.id.row_code_colors) codeColors else backgroundColors
        for (i in 0 until row.childCount) {
            val bg = GradientDrawable().apply {
                shape = GradientDrawable.OVAL
                setColor(colors[i])
                if (i == selected) setStroke((3 * d).toInt(), getColor(R.color.blue)) else setStroke((1 * d).toInt(), getColor(R.color.delimiter_color))
            }
            row.getChildAt(i).background = bg
        }
    }

    private fun buildLevelChips() {
        val row = findViewById<LinearLayout>(R.id.row_levels)
        val labels = listOf(R.string.customize_level_l, R.string.customize_level_m, R.string.customize_level_q, R.string.customize_level_h)
        val d = resources.displayMetrics.density
        for ((i, name) in StyledQrGenerator.levels.withIndex()) {
            val chip = TextView(this)
            chip.text = getString(labels[i])
            chip.gravity = android.view.Gravity.CENTER
            chip.setPadding((14 * d).toInt(), 0, (14 * d).toInt(), 0)
            chip.minHeight = (44 * d).toInt()
            chip.tag = name
            chip.layoutParams = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f).apply { marginEnd = (8 * d).toInt() }
            chip.setOnClickListener {
                level = name
                render()
            }
            row.addView(chip)
        }
    }

    private fun refreshChips() {
        val row = findViewById<LinearLayout>(R.id.row_levels)
        val forced = logo != null
        for (i in 0 until row.childCount) {
            val chip = row.getChildAt(i) as TextView
            val selected = (if (forced) "H" else level) == chip.tag
            chip.setBackgroundResource(if (selected) R.drawable.background_customize_chip_on else R.drawable.background_customize_chip_off)
            chip.setTextColor(getColor(if (selected) R.color.white else R.color.default_text_color))
            chip.alpha = if (forced && chip.tag != "H") 0.4f else 1f
            chip.isEnabled = !forced
        }
    }

    private fun render() {
        val bitmap = try {
            StyledQrGenerator.generate(barcode.text, 900, codeColor, backgroundColor, level, logo)
        } catch (ex: Exception) {
            Logger.log(ex)
            showError(ex)
            return
        }
        current = bitmap
        findViewById<SquareImageView>(R.id.image_preview).setImageBitmap(bitmap)
        findViewById<Button>(R.id.button_logo).setText(if (logo != null) R.string.customize_remove_logo else R.string.customize_add_logo)
        val warning = findViewById<TextView>(R.id.text_warning)
        when (ColorContrast.check(codeColor, backgroundColor)) {
            ColorContrast.Verdict.OK -> warning.isVisible = false
            ColorContrast.Verdict.LOW_CONTRAST -> {
                warning.isVisible = true
                warning.setText(R.string.customize_warning_low_contrast)
            }
            ColorContrast.Verdict.INVERTED -> {
                warning.isVisible = true
                warning.setText(R.string.customize_warning_inverted)
            }
        }
        findViewById<TextView>(R.id.text_level_hint).setText(
            if (logo != null) R.string.customize_level_forced else R.string.customize_level_hint
        )
        refreshChips()
    }

    private fun share() {
        val image = current ?: return
        val uri = try {
            barcodeImageSaver.saveImageToCache(this, image, ParsedBarcode(barcode))
        } catch (ex: Exception) {
            Logger.log(ex)
            showError(ex)
            return
        }
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "image/png"
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        try {
            startActivity(Intent.createChooser(intent, getString(R.string.customize_share)))
        } catch (ex: Exception) {
            Logger.log(ex)
        }
    }

    private fun save() {
        val image = current ?: return
        Completable.defer { barcodeImageSaver.savePngImageToPublicDirectory(this, image, barcode) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { Toast.makeText(this, R.string.activity_save_barcode_as_image_file_name_saved, Toast.LENGTH_LONG).show() },
                ::showError
            )
            .addTo(disposable)
    }

    private fun decodeLogo(uri: Uri): Bitmap? {
        return try {
            val bounds = BitmapFactory.Options().apply { inJustDecodeBounds = true }
            contentResolver.openInputStream(uri)?.use { BitmapFactory.decodeStream(it, null, bounds) }
            var sample = 1
            while (bounds.outWidth / sample > 512 || bounds.outHeight / sample > 512) sample *= 2
            val opts = BitmapFactory.Options().apply { inSampleSize = sample }
            contentResolver.openInputStream(uri)?.use { BitmapFactory.decodeStream(it, null, opts) }
        } catch (ex: Exception) {
            Logger.log(ex)
            null
        }
    }
}
