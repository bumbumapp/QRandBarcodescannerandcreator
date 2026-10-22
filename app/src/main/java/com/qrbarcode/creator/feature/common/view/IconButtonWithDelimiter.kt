package com.qrbarcode.creator.feature.common.view

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.qrbarcode.creator.R

class IconButtonWithDelimiter : FrameLayout {
    private val view: View

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, -1)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        view = LayoutInflater
            .from(context)
            .inflate(R.layout.layout_icon_button_with_delimiter, this, true)

        context.obtainStyledAttributes(attrs, R.styleable.IconButtonWithDelimiter).apply {
            showIcon(this)
            showIconBackgroundColor(this)
            showText(this)
            showArrow(this)
            showDelimiter(this)
            recycle()
        }
    }

    private fun showIcon(attributes: TypedArray) {
        val iconResId = attributes.getResourceId(R.styleable.IconButtonWithDelimiter_icon, -1)
        val icon = AppCompatResources.getDrawable(context, iconResId)
        view.image_view_schema.setImageDrawable(icon)
    }

    private fun showIconBackgroundColor(attributes: TypedArray) {
        val color = attributes.getColor(R.styleable.IconButtonWithDelimiter_iconBackground, view.context.resources.getColor(R.color.green))
        (view.layout_image.background.mutate() as GradientDrawable).setColor(color)
    }

    private fun showText(attributes: TypedArray) {
        view.text_view.text = attributes.getString(R.styleable.IconButtonWithDelimiter_text).orEmpty()

    }

    private fun showArrow(attributes: TypedArray) {
        view.image_view_arrow.isVisible = attributes.getBoolean(R.styleable.IconButtonWithDelimiter_isArrowVisible, false)
    }

    private fun showDelimiter(attributes: TypedArray) {
        view.delimiter.isInvisible = attributes.getBoolean(R.styleable.IconButtonWithDelimiter_isDelimiterVisible, true).not()
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        view.image_view_schema.isEnabled = enabled
        view.text_view.isEnabled = enabled
    }
}

// Former kotlinx.android.synthetic accessors (plugin removed in Kotlin 2).
private val android.view.View.delimiter: android.view.View get() = findViewById(com.qrbarcode.creator.R.id.delimiter)
private val android.view.View.image_view_arrow: android.widget.ImageView get() = findViewById(com.qrbarcode.creator.R.id.image_view_arrow)
private val android.view.View.image_view_schema: android.widget.ImageView get() = findViewById(com.qrbarcode.creator.R.id.image_view_schema)
private val android.view.View.layout_image: android.widget.FrameLayout get() = findViewById(com.qrbarcode.creator.R.id.layout_image)
private val android.view.View.text_view: android.widget.TextView get() = findViewById(com.qrbarcode.creator.R.id.text_view)
