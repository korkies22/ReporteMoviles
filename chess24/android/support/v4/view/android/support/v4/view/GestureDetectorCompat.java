/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 *  android.view.GestureDetector
 *  android.view.GestureDetector$OnDoubleTapListener
 *  android.view.GestureDetector$OnGestureListener
 *  android.view.MotionEvent
 *  android.view.VelocityTracker
 *  android.view.ViewConfiguration
 */
package android.support.v4.view;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;

public final class GestureDetectorCompat {
    private final GestureDetectorCompatImpl mImpl;

    public GestureDetectorCompat(Context context, GestureDetector.OnGestureListener onGestureListener) {
        this(context, onGestureListener, null);
    }

    public GestureDetectorCompat(Context context, GestureDetector.OnGestureListener onGestureListener, Handler handler) {
        if (Build.VERSION.SDK_INT > 17) {
            this.mImpl = new GestureDetectorCompatImplJellybeanMr2(context, onGestureListener, handler);
            return;
        }
        this.mImpl = new GestureDetectorCompatImplBase(context, onGestureListener, handler);
    }

    public boolean isLongpressEnabled() {
        return this.mImpl.isLongpressEnabled();
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        return this.mImpl.onTouchEvent(motionEvent);
    }

    public void setIsLongpressEnabled(boolean bl) {
        this.mImpl.setIsLongpressEnabled(bl);
    }

    public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener onDoubleTapListener) {
        this.mImpl.setOnDoubleTapListener(onDoubleTapListener);
    }

    static interface GestureDetectorCompatImpl {
        public boolean isLongpressEnabled();

        public boolean onTouchEvent(MotionEvent var1);

        public void setIsLongpressEnabled(boolean var1);

        public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener var1);
    }

    static class GestureDetectorCompatImplBase
    implements GestureDetectorCompatImpl {
        private static final int DOUBLE_TAP_TIMEOUT;
        private static final int LONGPRESS_TIMEOUT;
        private static final int LONG_PRESS = 2;
        private static final int SHOW_PRESS = 1;
        private static final int TAP = 3;
        private static final int TAP_TIMEOUT;
        private boolean mAlwaysInBiggerTapRegion;
        private boolean mAlwaysInTapRegion;
        MotionEvent mCurrentDownEvent;
        boolean mDeferConfirmSingleTap;
        GestureDetector.OnDoubleTapListener mDoubleTapListener;
        private int mDoubleTapSlopSquare;
        private float mDownFocusX;
        private float mDownFocusY;
        private final Handler mHandler;
        private boolean mInLongPress;
        private boolean mIsDoubleTapping;
        private boolean mIsLongpressEnabled;
        private float mLastFocusX;
        private float mLastFocusY;
        final GestureDetector.OnGestureListener mListener;
        private int mMaximumFlingVelocity;
        private int mMinimumFlingVelocity;
        private MotionEvent mPreviousUpEvent;
        boolean mStillDown;
        private int mTouchSlopSquare;
        private VelocityTracker mVelocityTracker;

        static {
            LONGPRESS_TIMEOUT = ViewConfiguration.getLongPressTimeout();
            TAP_TIMEOUT = ViewConfiguration.getTapTimeout();
            DOUBLE_TAP_TIMEOUT = ViewConfiguration.getDoubleTapTimeout();
        }

        GestureDetectorCompatImplBase(Context context, GestureDetector.OnGestureListener onGestureListener, Handler handler) {
            this.mHandler = handler != null ? new GestureDetectorCompatImplBase$GestureHandler(handler) : new GestureDetectorCompatImplBase$GestureHandler();
            this.mListener = onGestureListener;
            if (onGestureListener instanceof GestureDetector.OnDoubleTapListener) {
                this.setOnDoubleTapListener((GestureDetector.OnDoubleTapListener)onGestureListener);
            }
            this.init(context);
        }

        private void cancel() {
            this.mHandler.removeMessages(1);
            this.mHandler.removeMessages(2);
            this.mHandler.removeMessages(3);
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
            this.mIsDoubleTapping = false;
            this.mStillDown = false;
            this.mAlwaysInTapRegion = false;
            this.mAlwaysInBiggerTapRegion = false;
            this.mDeferConfirmSingleTap = false;
            if (this.mInLongPress) {
                this.mInLongPress = false;
            }
        }

        private void cancelTaps() {
            this.mHandler.removeMessages(1);
            this.mHandler.removeMessages(2);
            this.mHandler.removeMessages(3);
            this.mIsDoubleTapping = false;
            this.mAlwaysInTapRegion = false;
            this.mAlwaysInBiggerTapRegion = false;
            this.mDeferConfirmSingleTap = false;
            if (this.mInLongPress) {
                this.mInLongPress = false;
            }
        }

        private void init(Context context) {
            if (context == null) {
                throw new IllegalArgumentException("Context must not be null");
            }
            if (this.mListener == null) {
                throw new IllegalArgumentException("OnGestureListener must not be null");
            }
            this.mIsLongpressEnabled = true;
            context = ViewConfiguration.get((Context)context);
            int n = context.getScaledTouchSlop();
            int n2 = context.getScaledDoubleTapSlop();
            this.mMinimumFlingVelocity = context.getScaledMinimumFlingVelocity();
            this.mMaximumFlingVelocity = context.getScaledMaximumFlingVelocity();
            this.mTouchSlopSquare = n * n;
            this.mDoubleTapSlopSquare = n2 * n2;
        }

        private boolean isConsideredDoubleTap(MotionEvent motionEvent, MotionEvent motionEvent2, MotionEvent motionEvent3) {
            int n;
            boolean bl = this.mAlwaysInBiggerTapRegion;
            boolean bl2 = false;
            if (!bl) {
                return false;
            }
            if (motionEvent3.getEventTime() - motionEvent2.getEventTime() > (long)DOUBLE_TAP_TIMEOUT) {
                return false;
            }
            int n2 = (int)motionEvent.getX() - (int)motionEvent3.getX();
            if (n2 * n2 + (n = (int)motionEvent.getY() - (int)motionEvent3.getY()) * n < this.mDoubleTapSlopSquare) {
                bl2 = true;
            }
            return bl2;
        }

        void dispatchLongPress() {
            this.mHandler.removeMessages(3);
            this.mDeferConfirmSingleTap = false;
            this.mInLongPress = true;
            this.mListener.onLongPress(this.mCurrentDownEvent);
        }

        @Override
        public boolean isLongpressEnabled() {
            return this.mIsLongpressEnabled;
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        @Override
        public boolean onTouchEvent(MotionEvent var1_1) {
            var6_2 = var1_1.getAction();
            if (this.mVelocityTracker == null) {
                this.mVelocityTracker = VelocityTracker.obtain();
            }
            this.mVelocityTracker.addMovement(var1_1);
            var10_3 = var6_2 & 255;
            var12_4 = 0;
            var6_2 = var10_3 == 6 ? 1 : 0;
            var7_5 = var6_2 != 0 ? var1_1.getActionIndex() : -1;
            var9_6 = var1_1.getPointerCount();
            var2_9 = var3_8 = 0.0f;
            for (var8_7 = 0; var8_7 < var9_6; ++var8_7) {
                if (var7_5 == var8_7) continue;
                var3_8 += var1_1.getX(var8_7);
                var2_9 += var1_1.getY(var8_7);
            }
            var6_2 = var6_2 != 0 ? var9_6 - 1 : var9_6;
            var4_10 = var6_2;
            var3_8 /= var4_10;
            var2_9 /= var4_10;
            switch (var10_3) {
                default: {
                    return false;
                }
                case 6: {
                    this.mLastFocusX = var3_8;
                    this.mDownFocusX = var3_8;
                    this.mLastFocusY = var2_9;
                    this.mDownFocusY = var2_9;
                    this.mVelocityTracker.computeCurrentVelocity(1000, (float)this.mMaximumFlingVelocity);
                    var7_5 = var1_1.getActionIndex();
                    var6_2 = var1_1.getPointerId(var7_5);
                    var2_9 = this.mVelocityTracker.getXVelocity(var6_2);
                    var3_8 = this.mVelocityTracker.getYVelocity(var6_2);
                    var6_2 = 0;
                    do {
                        var11_11 = var12_4;
                        if (var6_2 >= var9_6) return (boolean)var11_11;
                        if (var6_2 != var7_5 && this.mVelocityTracker.getXVelocity(var8_7 = var1_1.getPointerId(var6_2)) * var2_9 + this.mVelocityTracker.getYVelocity(var8_7) * var3_8 < 0.0f) {
                            this.mVelocityTracker.clear();
                            return false;
                        }
                        ++var6_2;
                    } while (true);
                }
                case 5: {
                    this.mLastFocusX = var3_8;
                    this.mDownFocusX = var3_8;
                    this.mLastFocusY = var2_9;
                    this.mDownFocusY = var2_9;
                    this.cancelTaps();
                    return false;
                }
                case 3: {
                    this.cancel();
                    return false;
                }
                case 2: {
                    if (this.mInLongPress) {
                        return false;
                    }
                    var4_10 = this.mLastFocusX - var3_8;
                    var5_14 = this.mLastFocusY - var2_9;
                    if (this.mIsDoubleTapping) {
                        return false | this.mDoubleTapListener.onDoubleTapEvent(var1_1);
                    }
                    if (this.mAlwaysInTapRegion) {
                        var6_2 = (int)(var3_8 - this.mDownFocusX);
                        var7_5 = (int)(var2_9 - this.mDownFocusY);
                        if ((var6_2 = var6_2 * var6_2 + var7_5 * var7_5) > this.mTouchSlopSquare) {
                            var11_12 = this.mListener.onScroll(this.mCurrentDownEvent, var1_1, var4_10, var5_14);
                            this.mLastFocusX = var3_8;
                            this.mLastFocusY = var2_9;
                            this.mAlwaysInTapRegion = false;
                            this.mHandler.removeMessages(3);
                            this.mHandler.removeMessages(1);
                            this.mHandler.removeMessages(2);
                        } else {
                            var11_12 = 0;
                        }
                        var12_4 = var11_12;
                        if (var6_2 <= this.mTouchSlopSquare) return (boolean)var12_4;
                        this.mAlwaysInBiggerTapRegion = false;
                        return (boolean)var11_12;
                    }
                    if (Math.abs(var4_10) < 1.0f) {
                        var11_11 = var12_4;
                        if (Math.abs(var5_14) < 1.0f) return (boolean)var11_11;
                    }
                    var11_11 = this.mListener.onScroll(this.mCurrentDownEvent, var1_1, var4_10, var5_14);
                    this.mLastFocusX = var3_8;
                    this.mLastFocusY = var2_9;
                    return (boolean)var11_11;
                }
                case 1: {
                    this.mStillDown = false;
                    var13_15 = MotionEvent.obtain((MotionEvent)var1_1);
                    if (!this.mIsDoubleTapping) ** GOTO lbl89
                    var11_13 = this.mDoubleTapListener.onDoubleTapEvent(var1_1) | false;
                    ** GOTO lbl107
lbl89: // 1 sources:
                    if (!this.mInLongPress) ** GOTO lbl93
                    this.mHandler.removeMessages(3);
                    this.mInLongPress = false;
                    ** GOTO lbl-1000
lbl93: // 1 sources:
                    if (this.mAlwaysInTapRegion) {
                        var11_13 = this.mListener.onSingleTapUp(var1_1);
                        if (this.mDeferConfirmSingleTap && this.mDoubleTapListener != null) {
                            this.mDoubleTapListener.onSingleTapConfirmed(var1_1);
                        }
                    } else {
                        var14_16 = this.mVelocityTracker;
                        var6_2 = var1_1.getPointerId(0);
                        var14_16.computeCurrentVelocity(1000, (float)this.mMaximumFlingVelocity);
                        var2_9 = var14_16.getYVelocity(var6_2);
                        var3_8 = var14_16.getXVelocity(var6_2);
                        if (Math.abs(var2_9) <= (float)this.mMinimumFlingVelocity && Math.abs(var3_8) <= (float)this.mMinimumFlingVelocity) lbl-1000: // 2 sources:
                        {
                            var11_13 = 0;
                        } else {
                            var11_13 = this.mListener.onFling(this.mCurrentDownEvent, var1_1, var3_8, var2_9);
                        }
                    }
lbl107: // 4 sources:
                    if (this.mPreviousUpEvent != null) {
                        this.mPreviousUpEvent.recycle();
                    }
                    this.mPreviousUpEvent = var13_15;
                    if (this.mVelocityTracker != null) {
                        this.mVelocityTracker.recycle();
                        this.mVelocityTracker = null;
                    }
                    this.mIsDoubleTapping = false;
                    this.mDeferConfirmSingleTap = false;
                    this.mHandler.removeMessages(1);
                    this.mHandler.removeMessages(2);
                    return (boolean)var11_13;
                }
                case 0: 
            }
            if (this.mDoubleTapListener == null) ** GOTO lbl128
            var11_11 = this.mHandler.hasMessages(3);
            if (var11_11 != 0) {
                this.mHandler.removeMessages(3);
            }
            if (this.mCurrentDownEvent != null && this.mPreviousUpEvent != null && var11_11 != 0 && this.isConsideredDoubleTap(this.mCurrentDownEvent, this.mPreviousUpEvent, var1_1)) {
                this.mIsDoubleTapping = true;
                var6_2 = this.mDoubleTapListener.onDoubleTap(this.mCurrentDownEvent) | false | this.mDoubleTapListener.onDoubleTapEvent(var1_1);
            } else {
                this.mHandler.sendEmptyMessageDelayed(3, (long)GestureDetectorCompatImplBase.DOUBLE_TAP_TIMEOUT);
lbl128: // 2 sources:
                var6_2 = 0;
            }
            this.mLastFocusX = var3_8;
            this.mDownFocusX = var3_8;
            this.mLastFocusY = var2_9;
            this.mDownFocusY = var2_9;
            if (this.mCurrentDownEvent != null) {
                this.mCurrentDownEvent.recycle();
            }
            this.mCurrentDownEvent = MotionEvent.obtain((MotionEvent)var1_1);
            this.mAlwaysInTapRegion = true;
            this.mAlwaysInBiggerTapRegion = true;
            this.mStillDown = true;
            this.mInLongPress = false;
            this.mDeferConfirmSingleTap = false;
            if (this.mIsLongpressEnabled) {
                this.mHandler.removeMessages(2);
                this.mHandler.sendEmptyMessageAtTime(2, this.mCurrentDownEvent.getDownTime() + (long)GestureDetectorCompatImplBase.TAP_TIMEOUT + (long)GestureDetectorCompatImplBase.LONGPRESS_TIMEOUT);
            }
            this.mHandler.sendEmptyMessageAtTime(1, this.mCurrentDownEvent.getDownTime() + (long)GestureDetectorCompatImplBase.TAP_TIMEOUT);
            return (boolean)(var6_2 | this.mListener.onDown(var1_1));
        }

        @Override
        public void setIsLongpressEnabled(boolean bl) {
            this.mIsLongpressEnabled = bl;
        }

        @Override
        public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener onDoubleTapListener) {
            this.mDoubleTapListener = onDoubleTapListener;
        }
    }

    private class GestureDetectorCompatImplBase$GestureHandler
    extends Handler {
        GestureDetectorCompatImplBase$GestureHandler() {
        }

        GestureDetectorCompatImplBase$GestureHandler(Handler handler) {
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

    static class GestureDetectorCompatImplJellybeanMr2
    implements GestureDetectorCompatImpl {
        private final GestureDetector mDetector;

        GestureDetectorCompatImplJellybeanMr2(Context context, GestureDetector.OnGestureListener onGestureListener, Handler handler) {
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

}
