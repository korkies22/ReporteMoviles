/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Handler
 *  android.view.GestureDetector
 *  android.view.GestureDetector$OnDoubleTapListener
 *  android.view.GestureDetector$OnGestureListener
 *  android.view.MotionEvent
 */
package android.support.v4.view;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;

static class GestureDetectorCompat.GestureDetectorCompatImplJellybeanMr2
implements GestureDetectorCompat.GestureDetectorCompatImpl {
    private final GestureDetector mDetector;

    GestureDetectorCompat.GestureDetectorCompatImplJellybeanMr2(Context context, GestureDetector.OnGestureListener onGestureListener, Handler handler) {
        this.mDetector = new GestureDetector(context, onGestureListener, handler);
    }

    @Override
    public boolean isLongpressEnabled() {
        return this.mDetector.isLongpressEnabled();
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        return this.mDetector.onTouchEvent(motionEvent);
    }

    @Override
    public void setIsLongpressEnabled(boolean bl) {
        this.mDetector.setIsLongpressEnabled(bl);
    }

    @Override
    public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener onDoubleTapListener) {
        this.mDetector.setOnDoubleTapListener(onDoubleTapListener);
    }
}
