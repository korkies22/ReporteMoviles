/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.util.SparseIntArray
 *  android.view.FrameMetrics
 *  android.view.Window
 *  android.view.Window$OnFrameMetricsAvailableListener
 */
package android.support.v4.app;

import android.support.v4.app.FrameMetricsAggregator;
import android.util.SparseIntArray;
import android.view.FrameMetrics;
import android.view.Window;

class FrameMetricsAggregator.FrameMetricsApi24Impl
implements Window.OnFrameMetricsAvailableListener {
    FrameMetricsAggregator.FrameMetricsApi24Impl() {
    }

    public void onFrameMetricsAvailable(Window window, FrameMetrics frameMetrics, int n) {
        if ((FrameMetricsApi24Impl.this.mTrackingFlags & 1) != 0) {
            FrameMetricsApi24Impl.this.addDurationItem(FrameMetricsApi24Impl.this.mMetrics[0], frameMetrics.getMetric(8));
        }
        if ((FrameMetricsApi24Impl.this.mTrackingFlags & 2) != 0) {
            FrameMetricsApi24Impl.this.addDurationItem(FrameMetricsApi24Impl.this.mMetrics[1], frameMetrics.getMetric(1));
        }
        if ((FrameMetricsApi24Impl.this.mTrackingFlags & 4) != 0) {
            FrameMetricsApi24Impl.this.addDurationItem(FrameMetricsApi24Impl.this.mMetrics[2], frameMetrics.getMetric(3));
        }
        if ((FrameMetricsApi24Impl.this.mTrackingFlags & 8) != 0) {
            FrameMetricsApi24Impl.this.addDurationItem(FrameMetricsApi24Impl.this.mMetrics[3], frameMetrics.getMetric(4));
        }
        if ((FrameMetricsApi24Impl.this.mTrackingFlags & 16) != 0) {
            FrameMetricsApi24Impl.this.addDurationItem(FrameMetricsApi24Impl.this.mMetrics[4], frameMetrics.getMetric(5));
        }
        if ((FrameMetricsApi24Impl.this.mTrackingFlags & 64) != 0) {
            FrameMetricsApi24Impl.this.addDurationItem(FrameMetricsApi24Impl.this.mMetrics[6], frameMetrics.getMetric(7));
        }
        if ((FrameMetricsApi24Impl.this.mTrackingFlags & 32) != 0) {
            FrameMetricsApi24Impl.this.addDurationItem(FrameMetricsApi24Impl.this.mMetrics[5], frameMetrics.getMetric(6));
        }
        if ((FrameMetricsApi24Impl.this.mTrackingFlags & 128) != 0) {
            FrameMetricsApi24Impl.this.addDurationItem(FrameMetricsApi24Impl.this.mMetrics[7], frameMetrics.getMetric(0));
        }
        if ((FrameMetricsApi24Impl.this.mTrackingFlags & 256) != 0) {
            FrameMetricsApi24Impl.this.addDurationItem(FrameMetricsApi24Impl.this.mMetrics[8], frameMetrics.getMetric(2));
        }
    }
}
