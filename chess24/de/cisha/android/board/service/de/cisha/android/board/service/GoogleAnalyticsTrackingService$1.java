/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.service;

class GoogleAnalyticsTrackingService
implements Thread.UncaughtExceptionHandler {
    GoogleAnalyticsTrackingService() {
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        GoogleAnalyticsTrackingService.this.cacheException(thread.getName(), throwable);
        if (GoogleAnalyticsTrackingService.this._systemExceptionHandler != null) {
            GoogleAnalyticsTrackingService.this._systemExceptionHandler.uncaughtException(thread, throwable);
        }
    }
}
