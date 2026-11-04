package com.qrbarcode.creator.usecase

import java.net.URI
import java.util.Locale

/**
 * Offline check of a scanned link. Nothing is sent anywhere: it only looks at the shape of the
 * address and lists the reasons to be careful, so the user can decide before opening it.
 */
object LinkSafety {

    enum class Reason {
        NOT_HTTPS,
        IP_ADDRESS,
        SHORTENER,
        PUNYCODE,
        USER_INFO,
        RISKY_TLD,
        MANY_SUBDOMAINS,
        VERY_LONG,
        SCRIPT_SCHEME,
        BRAND_IMPERSONATION,
        UNPARSEABLE
    }

    enum class Level { OK, CAUTION, DANGER }

    data class Verdict(val level: Level, val reasons: List<Reason>, val host: String?)

    private val shorteners = setOf(
        "bit.ly", "tinyurl.com", "t.co", "goo.gl", "ow.ly", "is.gd", "buff.ly", "rebrand.ly",
        "cutt.ly", "shorturl.at", "tiny.cc", "rb.gy", "t.ly", "s.id", "lnkd.in"
    )
    private val riskyTlds = setOf("zip", "mov", "tk", "ml", "ga", "cf", "gq", "top", "click", "country")
    private val brands = mapOf(
        "paypal" to "paypal.com", "google" to "google.com", "apple" to "apple.com",
        "microsoft" to "microsoft.com", "amazon" to "amazon.com", "facebook" to "facebook.com",
        "instagram" to "instagram.com", "netflix" to "netflix.com", "whatsapp" to "whatsapp.com",
        "huawei" to "huawei.com", "binance" to "binance.com"
    )
    private val ipv4 = Regex("^\\d{1,3}(\\.\\d{1,3}){3}$")

    fun analyze(raw: String): Verdict {
        val text = raw.trim()
        val lower = text.lowercase(Locale.ROOT)
        if (lower.startsWith("javascript:") || lower.startsWith("data:") || lower.startsWith("vbscript:")) {
            return Verdict(Level.DANGER, listOf(Reason.SCRIPT_SCHEME), null)
        }
        val withScheme = if (Regex("^[a-z][a-z0-9+.-]*://").containsMatchIn(lower)) text else "http://$text"
        val uri = try {
            URI(withScheme.replace(" ", "%20"))
        } catch (e: Exception) {
            return Verdict(Level.CAUTION, listOf(Reason.UNPARSEABLE), null)
        }
        val host = uri.host?.lowercase(Locale.ROOT)
            ?: return Verdict(Level.CAUTION, listOf(Reason.UNPARSEABLE), null)

        val reasons = mutableListOf<Reason>()
        val hadScheme = withScheme === text
        if (uri.scheme?.lowercase(Locale.ROOT) == "http" && hadScheme) reasons += Reason.NOT_HTTPS
        if (ipv4.matches(host) || host.startsWith("[")) reasons += Reason.IP_ADDRESS
        if (host in shorteners) reasons += Reason.SHORTENER
        if (host.split('.').any { it.startsWith("xn--") }) reasons += Reason.PUNYCODE
        if (!uri.rawUserInfo.isNullOrEmpty()) reasons += Reason.USER_INFO
        val labels = host.split('.')
        if (labels.size >= 2 && labels.last() in riskyTlds) reasons += Reason.RISKY_TLD
        if (labels.size >= 5 && !ipv4.matches(host)) reasons += Reason.MANY_SUBDOMAINS
        if (text.length > 250) reasons += Reason.VERY_LONG
        if (impersonatesBrand(host)) reasons += Reason.BRAND_IMPERSONATION

        val level = when {
            Reason.USER_INFO in reasons || Reason.BRAND_IMPERSONATION in reasons ||
                (Reason.PUNYCODE in reasons) || (Reason.IP_ADDRESS in reasons && Reason.NOT_HTTPS in reasons) -> Level.DANGER
            reasons.isNotEmpty() -> Level.CAUTION
            else -> Level.OK
        }
        return Verdict(level, reasons, host)
    }

    /** "paypal-login.example.com" or "paypal.com.evil.net": names a brand but is not that brand's domain. */
    private fun impersonatesBrand(host: String): Boolean {
        for ((brand, domain) in brands) {
            if (!host.contains(brand)) continue
            if (host == domain || host.endsWith(".$domain")) continue
            return true
        }
        return false
    }
}
