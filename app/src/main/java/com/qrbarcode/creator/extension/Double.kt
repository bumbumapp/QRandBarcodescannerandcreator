package com.qrbarcode.creator.extension

fun Double?.orZero(): Double {
    return this ?: 0.0
}