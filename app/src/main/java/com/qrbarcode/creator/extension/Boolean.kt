package com.qrbarcode.creator.extension

fun Boolean?.orFalse(): Boolean {
    return this ?: false
}