package com.qrbarcode.creator.usecase

import com.qrbarcode.creator.usecase.LinkSafety.Level
import com.qrbarcode.creator.usecase.LinkSafety.Reason
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ScanToolsTest {

    @Test
    fun plainHttpsLinkIsOk() {
        val v = LinkSafety.analyze("https://www.example.com/path?a=1")
        assertEquals(Level.OK, v.level)
        assertTrue(v.reasons.isEmpty())
    }

    @Test
    fun httpLinkIsACaution() {
        val v = LinkSafety.analyze("http://example.com")
        assertEquals(Level.CAUTION, v.level)
        assertTrue(Reason.NOT_HTTPS in v.reasons)
    }

    @Test
    fun addressWithoutSchemeIsNotFlaggedForMissingHttps() {
        assertEquals(Level.OK, LinkSafety.analyze("www.example.com").level)
    }

    @Test
    fun shortenerAndRiskyTldAreCautions() {
        assertTrue(Reason.SHORTENER in LinkSafety.analyze("https://bit.ly/abc").reasons)
        assertTrue(Reason.RISKY_TLD in LinkSafety.analyze("https://free-prize.zip").reasons)
    }

    @Test
    fun ipAddressOverHttpIsDanger() {
        val v = LinkSafety.analyze("http://192.168.1.5/login")
        assertEquals(Level.DANGER, v.level)
        assertTrue(Reason.IP_ADDRESS in v.reasons)
    }

    @Test
    fun userInfoTrickIsDanger() {
        val v = LinkSafety.analyze("https://google.com@evil.example.net/x")
        assertEquals(Level.DANGER, v.level)
        assertTrue(Reason.USER_INFO in v.reasons)
    }

    @Test
    fun brandLookalikeIsDangerButRealBrandIsOk() {
        assertEquals(Level.DANGER, LinkSafety.analyze("https://paypal-secure-login.example.com").level)
        assertEquals(Level.DANGER, LinkSafety.analyze("https://paypal.com.evil.net/").level)
        assertEquals(Level.OK, LinkSafety.analyze("https://www.paypal.com/signin").level)
        assertEquals(Level.OK, LinkSafety.analyze("https://accounts.google.com").level)
    }

    @Test
    fun punycodeAndScriptSchemesAreDanger() {
        assertEquals(Level.DANGER, LinkSafety.analyze("https://xn--pple-43d.com").level)
        assertEquals(Level.DANGER, LinkSafety.analyze("javascript:alert(1)").level)
    }

    @Test
    fun colourContrastRules() {
        assertEquals(ColorContrast.Verdict.OK, ColorContrast.check(0xFF000000.toInt(), 0xFFFFFFFF.toInt()))
        assertEquals(ColorContrast.Verdict.OK, ColorContrast.check(0xFF1F3A8A.toInt(), 0xFFFFF7E0.toInt()))
        assertEquals(ColorContrast.Verdict.LOW_CONTRAST, ColorContrast.check(0xFF888888.toInt(), 0xFFAAAAAA.toInt()))
        assertEquals(ColorContrast.Verdict.INVERTED, ColorContrast.check(0xFFFFFFFF.toInt(), 0xFF000000.toInt()))
        assertEquals(21.0, ColorContrast.ratio(0xFF000000.toInt(), 0xFFFFFFFF.toInt()), 0.01)
    }

    @Test
    fun insightsCountTypesDaysAndFavorites() {
        val day = Insights.DAY_MS
        val now = 10 * day + 5_000
        val rows = listOf(
            InsightRow(now, "QR_CODE", "URL", false, true),
            InsightRow(now - 1000, "QR_CODE", "URL", false, false),
            InsightRow(now - day, "EAN_13", "PRODUCT", false, false),
            InsightRow(now - 3 * day, "QR_CODE", "WIFI", true, false),
            InsightRow(now - 30 * day, "QR_CODE", "TEXT", true, false)
        )
        val s = Insights.summarize(rows, now, 0)
        assertEquals(3, s.scanned)
        assertEquals(2, s.created)
        assertEquals(1, s.favorites)
        assertEquals(5, s.total)
        assertEquals("URL" to 2, s.topSchemas[0])
        assertEquals("QR_CODE" to 4, s.topFormats[0])
        assertEquals(listOf(0, 0, 0, 1, 0, 1, 2), s.lastWeek)
        assertEquals(4, s.activeDays)
    }

    @Test
    fun insightsTimeZoneShiftsTheDay() {
        val day = Insights.DAY_MS
        // 23:00 UTC is already the next day at UTC+2
        val t = 5 * day + 23 * 60 * 60 * 1000L
        assertEquals(5L, Insights.dayIndex(t, 0))
        assertEquals(6L, Insights.dayIndex(t, 2 * 60 * 60 * 1000L))
    }
}
