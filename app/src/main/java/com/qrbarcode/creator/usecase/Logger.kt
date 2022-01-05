package com.qrbarcode.creator.usecase

import com.qrbarcode.creator.BuildConfig
import io.sentry.core.Sentry

object Logger {
    var isEnabled = BuildConfig.ERROR_REPORTS_ENABLED_BY_DEFAULT

    fun log(error: Throwable) {
        if (isEnabled) {
            Sentry.captureException(error)
        }
    }
}