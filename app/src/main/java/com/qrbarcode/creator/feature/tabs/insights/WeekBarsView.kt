package com.qrbarcode.creator.feature.tabs.insights

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.qrbarcode.creator.R

/** Seven simple bars, oldest first. */
class WeekBarsView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    private val bar = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = context.getColor(R.color.blue) }
    private val empty = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = context.getColor(R.color.delimiter_color) }
    private val label = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = context.getColor(R.color.edit_text_hint_color)
        textAlign = Paint.Align.CENTER
        textSize = resources.displayMetrics.scaledDensity * 11
    }
    private var values: List<Int> = List(7) { 0 }

    fun setValues(v: List<Int>) {
        values = v
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val n = values.size
        if (n == 0) return
        val slot = width.toFloat() / n
        val barW = slot * 0.5f
        val topPad = label.textSize + 4f
        val bottomPad = label.textSize + 8f
        val chartH = height - topPad - bottomPad
        val max = (values.maxOrNull() ?: 0).coerceAtLeast(1)
        for (i in 0 until n) {
            val cx = slot * i + slot / 2
            val h = if (values[i] == 0) 4f else chartH * values[i] / max
            val top = topPad + chartH - h
            canvas.drawRoundRect(cx - barW / 2, top, cx + barW / 2, topPad + chartH, 6f, 6f, if (values[i] == 0) empty else bar)
            if (values[i] > 0) canvas.drawText(values[i].toString(), cx, top - 4f, label)
            canvas.drawText(if (i == n - 1) "0" else "-${n - 1 - i}", cx, height - 4f, label)
        }
    }
}
