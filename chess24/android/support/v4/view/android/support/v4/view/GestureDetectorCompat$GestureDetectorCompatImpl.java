/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.GestureDetector
 *  android.view.GestureDetector$OnDoubleTapListener
 *  android.view.MotionEvent
 */
package android.support.v4.view;

import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;

static interface GestureDetectorCompat.GestureDetectorCompatImpl {
    public boolean isLongpressEnabled();

    public boolean onTouchEvent(MotionEvent var1);

    public void setIsLongpressEnabled(boolean var1);

    public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener var1);
}
