package com.qrbarcode.creator.usecase

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter

/** Draws a QR code with chosen colours, error correction level and an optional centre logo. */
object StyledQrGenerator {

    val levels = listOf("L", "M", "Q", "H")

    /** Share of the code width the logo may cover; H level restores up to ~30% damage. */
    const val LOGO_SHARE = 0.20f

    fun generate(
        text: String,
        size: Int,
        codeColor: Int,
        backgroundColor: Int,
        level: String,
        logo: Bitmap?
    ): Bitmap {
        val effectiveLevel = if (logo != null) "H" else level
        val hints = mapOf(
            EncodeHintType.CHARACTER_SET to "utf-8",
            EncodeHintType.MARGIN to 2,
            EncodeHintType.ERROR_CORRECTION to effectiveLevel
        )
        val matrix = QRCodeWriter().encode(text, BarcodeFormat.QR_CODE, size, size, hints)
        val w = matrix.width
        val h = matrix.height
        val pixels = IntArray(w * h)
        for (y in 0 until h) {
            val offset = y * w
            for (x in 0 until w) {
                pixels[offset + x] = if (matrix[x, y]) codeColor else backgroundColor
            }
        }
        val bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        bitmap.setPixels(pixels, 0, w, 0, 0, w, h)
        if (logo != null) {
            drawLogo(bitmap, logo, backgroundColor)
        }
        return bitmap
    }

    private fun drawLogo(target: Bitmap, logo: Bitmap, backgroundColor: Int) {
        val canvas = Canvas(target)
        val logoSize = target.width * LOGO_SHARE
        val pad = logoSize * 0.12f
        val cx = target.width / 2f
        val cy = target.height / 2f
        val backing = RectF(cx - logoSize / 2 - pad, cy - logoSize / 2 - pad, cx + logoSize / 2 + pad, cy + logoSize / 2 + pad)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = backgroundColor }
        canvas.drawRoundRect(backing, logoSize * 0.18f, logoSize * 0.18f, paint)
        // centre-crop the logo into a square
        val side = minOf(logo.width, logo.height)
        val src = android.graphics.Rect((logo.width - side) / 2, (logo.height - side) / 2,
            (logo.width + side) / 2, (logo.height + side) / 2)
        val dst = RectF(cx - logoSize / 2, cy - logoSize / 2, cx + logoSize / 2, cy + logoSize / 2)
        canvas.drawBitmap(logo, src, dst.toRect(), Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG))
    }

    private fun RectF.toRect() = android.graphics.Rect(left.toInt(), top.toInt(), right.toInt(), bottom.toInt())
}
