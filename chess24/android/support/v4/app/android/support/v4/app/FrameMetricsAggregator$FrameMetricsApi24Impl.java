/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.os.Handler
 *  android.os.HandlerThread
 *  android.os.Looper
 *  android.util.SparseIntArray
 *  android.view.FrameMetrics
 *  android.view.Window
 *  android.view.Window$OnFrameMetricsAvailableListener
 */
package android.support.v4.app;

import android.app.Activity;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FrameMetricsAggregator;
import android.util.SparseIntArray;
import android.view.FrameMetrics;
import android.view.Window;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

@RequiresApi(value=24)
private static class FrameMetricsAggregator.FrameMetricsApi24Impl
extends FrameMetricsAggregator.FrameMetricsBaseImpl {
    private static final int NANOS_PER_MS = 1000000;
    private static final int NANOS_ROUNDING_VALUE = 500000;
    private static Handler sHandler;
    private static HandlerThread sHandlerThread;
    private ArrayList<WeakReference<Activity>> mActivities = new ArrayList();
    Window.OnFrameMetricsAvailableListener mListener = new Window.OnFrameMetricsAvailableListener(){

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
    };
    private SparseIntArray[] mMetrics = new SparseIntArray[9];
    private int mTrackingFlags;

    FrameMetricsAggregator.FrameMetricsApi24Impl(int n) {
        super(null);
        this.mTrackingFlags = n;
    }

    @Override
    public void add(Activity activity) {
        if (sHandlerThread == null) {
            sHandlerThread = new HandlerThread("FrameMetricsAggregator");
            sHandlerThread.start();
            sHandler = new Handler(sHandlerThread.getLooper());
        }
        for (int i = 0; i <= 8; ++i) {
            if (this.mMetrics[i] != null || (this.mTrackingFlags & 1 << i) == 0) continue;
            this.mMetrics[i] = new SparseIntArray();
        }
        activity.getWindow().addOnFrameMetricsAvailableListener(this.mListener, sHandler);
        this.mActivities.add(new WeakReference<Activity>(activity));
    }

    void addDurationItem(SparseIntArray sparseIntArray, long l) {
        if (sparseIntArray != null) {
            int n = (int)((l + 500000L) / 1000000L);
            if (l >= 0L) {
                sparseIntArray.put(n, sparseIntArray.get(n) + 1);
            }
        }
    }

    @Override
    public SparseIntArray[] getMetrics() {
        return this.mMetrics;
    }

    @Override
    public SparseIntArray[] remove(Activity activity) {
        for (WeakReference<Activity> weakReference : this.mActivities) {
            if (weakReference.get() != activity) continue;
            this.mActivities.remove(weakReference);
            break;
        }
        activity.getWindow().removeOnFrameMetricsAvailableListener(this.mListener);
        return this.mMetrics;
    }

    @Override
    public SparseIntArray[] reset() {
        SparseIntArray[] arrsparseIntArray = this.mMetrics;
        this.mMetrics = new SparseIntArray[9];
        return arrsparseIntArray;
    }

    @Override
    public SparseIntArray[] stop() {
        for (int i = this.mActivities.size() - 1; i >= 0; --i) {
            WeakReference<Activity> weakReference = this.mActivities.get(i);
            Activity activity = (Activity)weakReference.get();
            if (weakReference.get() == null) continue;
            activity.getWindow().removeOnFrameMetricsAvailableListener(this.mListener);
            this.mActivities.remove(i);
        }
        return this.mMetrics;
    }

}
