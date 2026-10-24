package com.qrbarcode.creator.extension

import android.content.ClipboardManager
import android.content.Context
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Vibrator
import java.util.*

val Context.vibrator: Vibrator?
    get() = getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator

val Context.wifiManager: WifiManager?
    get() = applicationContext.getSystemService(Context.WIFI_SERVICE) as? WifiManager

val Context.clipboardManager: ClipboardManager?
    get() = getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager

val Context.currentLocale: Locale?
    get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        resources.configuration.locales.get(0)
    } else {
        resources.configuration.locale
    }
/**
 * Runs [start] (a startActivity call) and returns false if no app can handle the intent.
 * Intent.resolveActivity() can't be used for this check: since Android 11 package visibility hides
 * other apps from it, so it returned null and links, calls and the image picker never opened.
 */
inline fun tryStartActivity(start: () -> Unit): Boolean = try {
    start()
    true
} catch (e: android.content.ActivityNotFoundException) {
    false
}
