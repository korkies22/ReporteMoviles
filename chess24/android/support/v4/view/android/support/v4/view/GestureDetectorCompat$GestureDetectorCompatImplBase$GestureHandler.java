/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 *  android.view.GestureDetector
 *  android.view.GestureDetector$OnDoubleTapListener
 *  android.view.GestureDetector$OnGestureListener
 *  android.view.MotionEvent
 */
package android.support.v4.view;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;

private class GestureDetectorCompat.GestureDetectorCompatImplBase.GestureHandler
extends Handler {
    GestureDetectorCompat.GestureDetectorCompatImplBase.GestureHandler() {
    }

    GestureDetectorCompat.GestureDetectorCompatImplBase.GestureHandler(Handler handler) {
        super(handler.getLooper());
    }

    public void handleMessage(Message message) {
        switch (message.what) {
            default: {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unknown message ");
                stringBuilder.append((Object)message);
                throw new RuntimeException(stringBuilder.toString());
            }
            case 3: {
                if (GestureDetectorCompatImplBase.this.mDoubleTapListener == null) break;
                if (!GestureDetectorCompatImplBase.this.mStillDown) {
                    GestureDetectorCompatImplBase.this.mDoubleTapListener.onSingleTapConfirmed(GestureDetectorCompatImplBase.this.mCurrentDownEvent);
                    return;
                }
                GestureDetectorCompatImplBase.this.mDeferConfirmSingleTap = true;
                return;
            }
            case 2: {
                GestureDetectorCompatImplBase.this.dispatchLongPress();
                return;
            }
            case 1: {
                GestureDetectorCompatImplBase.this.mListener.onShowPress(GestureDetectorCompatImplBase.this.mCurrentDownEvent);
            }
        }
    }
}
