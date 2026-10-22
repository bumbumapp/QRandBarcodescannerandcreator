package com.qrbarcode.creator.feature.common.view

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.isInvisible
import com.qrbarcode.creator.R

class SettingsRadioButton : FrameLayout {
    private val view: View
    
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, -1)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        view = LayoutInflater
            .from(context)
            .inflate(R.layout.layout_settings_radio_button, this, true)

        context.obtainStyledAttributes(attrs, R.styleable.SettingsRadioButton).apply {
            showText(this)
            showDelimiter(this)
            recycle()
        }

        view.setOnClickListener {
            radio_button.toggle()
        }
    }

    var isChecked: Boolean
        get() = view.radio_button.isChecked
        set(value) { view.radio_button.isChecked = value }
    
    fun setCheckedChangedListener(listener: ((Boolean) -> Unit)?) {
        view.radio_button.setOnCheckedChangeListener { _, isChecked ->
            listener?.invoke(isChecked)
        }
    }

    private fun showText(attributes: TypedArray) {
        view.text_view_text.text = attributes.getString(R.styleable.SettingsRadioButton_text).orEmpty()
    }

    private fun showDelimiter(attributes: TypedArray) {
        view.delimiter.isInvisible = attributes.getBoolean(R.styleable.SettingsRadioButton_isDelimiterVisible, true).not()
    }
}

// Former kotlinx.android.synthetic accessors (plugin removed in Kotlin 2).
private val android.view.View.delimiter: android.view.View get() = findViewById(com.qrbarcode.creator.R.id.delimiter)
private val android.view.View.radio_button: android.widget.RadioButton get() = findViewById(com.qrbarcode.creator.R.id.radio_button)
private val android.view.View.text_view_text: android.widget.TextView get() = findViewById(com.qrbarcode.creator.R.id.text_view_text)
