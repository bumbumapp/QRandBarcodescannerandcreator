package com.qrbarcode.creator.extension

import android.content.ActivityNotFoundException
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Android 11+ package visibility made Intent.resolveActivity() return null, so the image picker,
 * links and calls never opened. Starting is now attempted and only a missing app is reported.
 */
class TryStartActivityTest {
    @Test
    fun reportsStartedWhenAnAppHandlesTheIntent() {
        var started = false
        assertTrue(tryStartActivity { started = true })
        assertTrue(started)
    }

    @Test
    fun reportsMissingAppInsteadOfCrashing() {
        assertFalse(tryStartActivity { throw ActivityNotFoundException() })
    }
}
