package com.qrbarcode.creator

sealed class AdsShowed<out T>{
        data class Showed<out T>(val data: T) : AdsShowed<T>()
        data class NotShowed<out T>(val data: T) : AdsShowed<T>()
}
