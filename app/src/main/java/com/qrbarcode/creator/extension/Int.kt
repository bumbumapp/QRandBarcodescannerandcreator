package com.qrbarcode.creator.extension

fun Int?.orZero(): Int {
    return this ?: 0
}