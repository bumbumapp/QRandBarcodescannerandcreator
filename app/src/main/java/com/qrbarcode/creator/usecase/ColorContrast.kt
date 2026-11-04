package com.qrbarcode.creator.usecase

import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow

/** Contrast rules that decide whether a custom-coloured QR code will still scan. */
object ColorContrast {

    enum class Verdict { OK, LOW_CONTRAST, INVERTED }

    /** Minimum ratio for a dependable scan. */
    const val MIN_RATIO = 3.0

    private fun channel(c: Int): Double {
        val v = c / 255.0
        return if (v <= 0.03928) v / 12.92 else ((v + 0.055) / 1.055).pow(2.4)
    }

    fun luminance(color: Int): Double {
        val r = (color shr 16) and 0xFF
        val g = (color shr 8) and 0xFF
        val b = color and 0xFF
        return 0.2126 * channel(r) + 0.7152 * channel(g) + 0.0722 * channel(b)
    }

    fun ratio(a: Int, b: Int): Double {
        val la = luminance(a)
        val lb = luminance(b)
        return (max(la, lb) + 0.05) / (min(la, lb) + 0.05)
    }

    /** Dark modules on a light background scan everywhere; the reverse fails on many scanners. */
    fun check(code: Int, background: Int): Verdict {
        if (ratio(code, background) < MIN_RATIO) return Verdict.LOW_CONTRAST
        if (luminance(code) > luminance(background)) return Verdict.INVERTED
        return Verdict.OK
    }
}
