/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.util.DisplayMetrics
 *  android.util.Log
 *  android.view.MotionEvent
 *  android.view.VelocityTracker
 *  android.view.View
 *  android.view.ViewConfiguration
 *  android.view.ViewGroup
 *  android.view.ViewParent
 *  android.view.animation.Interpolator
 *  android.widget.OverScroller
 */
package android.support.v4.widget;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.Interpolator;
import android.widget.OverScroller;
import java.util.Arrays;

public class ViewDragHelper {
    private static final int BASE_SETTLE_DURATION = 256;
    public static final int DIRECTION_ALL = 3;
    public static final int DIRECTION_HORIZONTAL = 1;
    public static final int DIRECTION_VERTICAL = 2;
    public static final int EDGE_ALL = 15;
    public static final int EDGE_BOTTOM = 8;
    public static final int EDGE_LEFT = 1;
    public static final int EDGE_RIGHT = 2;
    private static final int EDGE_SIZE = 20;
    public static final int EDGE_TOP = 4;
    public static final int INVALID_POINTER = -1;
    private static final int MAX_SETTLE_DURATION = 600;
    public static final int STATE_DRAGGING = 1;
    public static final int STATE_IDLE = 0;
    public static final int STATE_SETTLING = 2;
    private static final String TAG = "ViewDragHelper";
    private static final Interpolator sInterpolator = new Interpolator(){

        public float getInterpolation(float f) {
            return f * f * f * f * (f -= 1.0f) + 1.0f;
        }
    };
    private int mActivePointerId = -1;
    private final Callback mCallback;
    private View mCapturedView;
    private int mDragState;
    private int[] mEdgeDragsInProgress;
    private int[] mEdgeDragsLocked;
    private int mEdgeSize;
    private int[] mInitialEdgesTouched;
    private float[] mInitialMotionX;
    private float[] mInitialMotionY;
    private float[] mLastMotionX;
    private float[] mLastMotionY;
    private float mMaxVelocity;
    private float mMinVelocity;
    private final ViewGroup mParentView;
    private int mPointersDown;
    private boolean mReleaseInProgress;
    private OverScroller mScroller;
    private final Runnable mSetIdleRunnable = new Runnable(){

        @Override
        public void run() {
            ViewDragHelper.this.setDragState(0);
        }
    };
    private int mTouchSlop;
    private int mTrackingEdges;
    private VelocityTracker mVelocityTracker;

    private ViewDragHelper(@NonNull Context context, @NonNull ViewGroup viewGroup, @NonNull Callback callback) {
        if (viewGroup == null) {
            throw new IllegalArgumentException("Parent view may not be null");
        }
        if (callback == null) {
            throw new IllegalArgumentException("Callback may not be null");
        }
        this.mParentView = viewGroup;
        this.mCallback = callback;
        viewGroup = ViewConfiguration.get((Context)context);
        this.mEdgeSize = (int)(20.0f * context.getResources().getDisplayMetrics().density + 0.5f);
        this.mTouchSlop = viewGroup.getScaledTouchSlop();
        this.mMaxVelocity = viewGroup.getScaledMaximumFlingVelocity();
        this.mMinVelocity = viewGroup.getScaledMinimumFlingVelocity();
        this.mScroller = new OverScroller(context, sInterpolator);
    }

    private boolean checkNewEdgeDrag(float f, float f2, int n, int n2) {
        f = Math.abs(f);
        f2 = Math.abs(f2);
        int n3 = this.mInitialEdgesTouched[n];
        boolean bl = false;
        if ((n3 & n2) == n2 && (this.mTrackingEdges & n2) != 0 && (this.mEdgeDragsLocked[n] & n2) != n2 && (this.mEdgeDragsInProgress[n] & n2) != n2) {
            if (f <= (float)this.mTouchSlop && f2 <= (float)this.mTouchSlop) {
                return false;
            }
            if (f < f2 * 0.5f && this.mCallback.onEdgeLock(n2)) {
                int[] arrn = this.mEdgeDragsLocked;
                arrn[n] = arrn[n] | n2;
                return false;
            }
            boolean bl2 = bl;
            if ((this.mEdgeDragsInProgress[n] & n2) == 0) {
                bl2 = bl;
                if (f > (float)this.mTouchSlop) {
                    bl2 = true;
                }
            }
            return bl2;
        }
        return false;
    }

    private boolean checkTouchSlop(View view, float f, float f2) {
        boolean bl = false;
        boolean bl2 = false;
        boolean bl3 = false;
        if (view == null) {
            return false;
        }
        boolean bl4 = this.mCallback.getViewHorizontalDragRange(view) > 0;
        boolean bl5 = this.mCallback.getViewVerticalDragRange(view) > 0;
        if (bl4 && bl5) {
            if (f * f + f2 * f2 > (float)(this.mTouchSlop * this.mTouchSlop)) {
                bl3 = true;
            }
            return bl3;
        }
        if (bl4) {
            bl3 = bl;
            if (Math.abs(f) > (float)this.mTouchSlop) {
                bl3 = true;
            }
            return bl3;
        }
        if (bl5) {
            bl3 = bl2;
            if (Math.abs(f2) > (float)this.mTouchSlop) {
                bl3 = true;
            }
            return bl3;
        }
        return false;
    }

    private float clampMag(float f, float f2, float f3) {
        float f4 = Math.abs(f);
        if (f4 < f2) {
            return 0.0f;
        }
        if (f4 > f3) {
            if (f > 0.0f) {
                return f3;
            }
            return - f3;
        }
        return f;
    }

    private int clampMag(int n, int n2, int n3) {
        int n4 = Math.abs(n);
        if (n4 < n2) {
            return 0;
        }
        if (n4 > n3) {
            if (n > 0) {
                return n3;
            }
            return - n3;
        }
        return n;
    }

    private void clearMotionHistory() {
        if (this.mInitialMotionX == null) {
            return;
        }
        Arrays.fill(this.mInitialMotionX, 0.0f);
        Arrays.fill(this.mInitialMotionY, 0.0f);
        Arrays.fill(this.mLastMotionX, 0.0f);
        Arrays.fill(this.mLastMotionY, 0.0f);
        Arrays.fill(this.mInitialEdgesTouched, 0);
        Arrays.fill(this.mEdgeDragsInProgress, 0);
        Arrays.fill(this.mEdgeDragsLocked, 0);
        this.mPointersDown = 0;
    }

    private void clearMotionHistory(int n) {
        if (this.mInitialMotionX != null) {
            if (!this.isPointerDown(n)) {
                return;
            }
            this.mInitialMotionX[n] = 0.0f;
            this.mInitialMotionY[n] = 0.0f;
            this.mLastMotionX[n] = 0.0f;
            this.mLastMotionY[n] = 0.0f;
            this.mInitialEdgesTouched[n] = 0;
            this.mEdgeDragsInProgress[n] = 0;
            this.mEdgeDragsLocked[n] = 0;
            this.mPointersDown = ~ (1 << n) & this.mPointersDown;
            return;
        }
    }

    private int computeAxisDuration(int n, int n2, int n3) {
        if (n == 0) {
            return 0;
        }
        int n4 = this.mParentView.getWidth();
        int n5 = n4 / 2;
        float f = Math.min(1.0f, (float)Math.abs(n) / (float)n4);
        float f2 = n5;
        f = this.distanceInfluenceForSnapDuration(f);
        n = (n2 = Math.abs(n2)) > 0 ? 4 * Math.round(1000.0f * Math.abs((f2 + f * f2) / (float)n2)) : (int)(((float)Math.abs(n) / (float)n3 + 1.0f) * 256.0f);
        return Math.min(n, 600);
    }

    /*
     * Enabled aggressive block sorting
     */
    private int computeSettleDuration(View view, int n, int n2, int n3, int n4) {
        float f;
        float f2;
        n3 = this.clampMag(n3, (int)this.mMinVelocity, (int)this.mMaxVelocity);
        n4 = this.clampMag(n4, (int)this.mMinVelocity, (int)this.mMaxVelocity);
        int n5 = Math.abs(n);
        int n6 = Math.abs(n2);
        int n7 = Math.abs(n3);
        int n8 = Math.abs(n4);
        int n9 = n7 + n8;
        int n10 = n5 + n6;
        if (n3 != 0) {
            f = n7;
            f2 = n9;
        } else {
            f = n5;
            f2 = n10;
        }
        float f3 = f / f2;
        if (n4 != 0) {
            f = n8;
            f2 = n9;
        } else {
            f = n6;
            f2 = n10;
        }
        n = this.computeAxisDuration(n, n3, this.mCallback.getViewHorizontalDragRange(view));
        n2 = this.computeAxisDuration(n2, n4, this.mCallback.getViewVerticalDragRange(view));
        return (int)((float)n * f3 + (float)n2 * (f /= f2));
    }

    public static ViewDragHelper create(@NonNull ViewGroup object, float f, @NonNull Callback callback) {
        object = ViewDragHelper.create(object, callback);
        object.mTouchSlop = (int)((float)object.mTouchSlop * (1.0f / f));
        return object;
    }

    public static ViewDragHelper create(@NonNull ViewGroup viewGroup, @NonNull Callback callback) {
        return new ViewDragHelper(viewGroup.getContext(), viewGroup, callback);
    }

    private void dispatchViewReleased(float f, float f2) {
        this.mReleaseInProgress = true;
        this.mCallback.onViewReleased(this.mCapturedView, f, f2);
        this.mReleaseInProgress = false;
        if (this.mDragState == 1) {
            this.setDragState(0);
        }
    }

    private float distanceInfluenceForSnapDuration(float f) {
        return (float)Math.sin((f - 0.5f) * 0.47123894f);
    }

    private void dragTo(int n, int n2, int n3, int n4) {
        int n5 = this.mCapturedView.getLeft();
        int n6 = this.mCapturedView.getTop();
        int n7 = n;
        if (n3 != 0) {
            n7 = this.mCallback.clampViewPositionHorizontal(this.mCapturedView, n, n3);
            ViewCompat.offsetLeftAndRight(this.mCapturedView, n7 - n5);
        }
        n = n2;
        if (n4 != 0) {
            n = this.mCallback.clampViewPositionVertical(this.mCapturedView, n2, n4);
            ViewCompat.offsetTopAndBottom(this.mCapturedView, n - n6);
        }
        if (n3 != 0 || n4 != 0) {
            this.mCallback.onViewPositionChanged(this.mCapturedView, n7, n, n7 - n5, n - n6);
        }
    }

    private void ensureMotionHistorySizeForId(int n) {
        if (this.mInitialMotionX == null || this.mInitialMotionX.length <= n) {
            float[] arrf = new float[++n];
            float[] arrf2 = new float[n];
            float[] arrf3 = new float[n];
            float[] arrf4 = new float[n];
            int[] arrn = new int[n];
            int[] arrn2 = new int[n];
            int[] arrn3 = new int[n];
            if (this.mInitialMotionX != null) {
                System.arraycopy(this.mInitialMotionX, 0, arrf, 0, this.mInitialMotionX.length);
                System.arraycopy(this.mInitialMotionY, 0, arrf2, 0, this.mInitialMotionY.length);
                System.arraycopy(this.mLastMotionX, 0, arrf3, 0, this.mLastMotionX.length);
                System.arraycopy(this.mLastMotionY, 0, arrf4, 0, this.mLastMotionY.length);
                System.arraycopy(this.mInitialEdgesTouched, 0, arrn, 0, this.mInitialEdgesTouched.length);
                System.arraycopy(this.mEdgeDragsInProgress, 0, arrn2, 0, this.mEdgeDragsInProgress.length);
                System.arraycopy(this.mEdgeDragsLocked, 0, arrn3, 0, this.mEdgeDragsLocked.length);
            }
            this.mInitialMotionX = arrf;
            this.mInitialMotionY = arrf2;
            this.mLastMotionX = arrf3;
            this.mLastMotionY = arrf4;
            this.mInitialEdgesTouched = arrn;
            this.mEdgeDragsInProgress = arrn2;
            this.mEdgeDragsLocked = arrn3;
        }
    }

    private boolean forceSettleCapturedViewAt(int n, int n2, int n3, int n4) {
        int n5 = this.mCapturedView.getLeft();
        int n6 = this.mCapturedView.getTop();
        if ((n -= n5) == 0 && (n2 -= n6) == 0) {
            this.mScroller.abortAnimation();
            this.setDragState(0);
            return false;
        }
        n3 = this.computeSettleDuration(this.mCapturedView, n, n2, n3, n4);
        this.mScroller.startScroll(n5, n6, n, n2, n3);
        this.setDragState(2);
        return true;
    }

    private int getEdgesTouched(int n, int n2) {
        int n3 = n < this.mParentView.getLeft() + this.mEdgeSize ? 1 : 0;
        int n4 = n3;
        if (n2 < this.mParentView.getTop() + this.mEdgeSize) {
            n4 = n3 | 4;
        }
        n3 = n4;
        if (n > this.mParentView.getRight() - this.mEdgeSize) {
            n3 = n4 | 2;
        }
        n = n3;
        if (n2 > this.mParentView.getBottom() - this.mEdgeSize) {
            n = n3 | 8;
        }
        return n;
    }

    private boolean isValidPointerForActionMove(int n) {
        if (!this.isPointerDown(n)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Ignoring pointerId=");
            stringBuilder.append(n);
            stringBuilder.append(" because ACTION_DOWN was not received ");
            stringBuilder.append("for this pointer before ACTION_MOVE. It likely happened because ");
            stringBuilder.append(" ViewDragHelper did not receive all the events in the event stream.");
            Log.e((String)TAG, (String)stringBuilder.toString());
            return false;
        }
        return true;
    }

    private void releaseViewForPointerUp() {
        this.mVelocityTracker.computeCurrentVelocity(1000, this.mMaxVelocity);
        this.dispatchViewReleased(this.clampMag(this.mVelocityTracker.getXVelocity(this.mActivePointerId), this.mMinVelocity, this.mMaxVelocity), this.clampMag(this.mVelocityTracker.getYVelocity(this.mActivePointerId), this.mMinVelocity, this.mMaxVelocity));
    }

    private void reportNewEdgeDrags(float f, float f2, int n) {
        int n2 = 1;
        if (!this.checkNewEdgeDrag(f, f2, n, 1)) {
            n2 = 0;
        }
        int n3 = n2;
        if (this.checkNewEdgeDrag(f2, f, n, 4)) {
            n3 = n2 | 4;
        }
        n2 = n3;
        if (this.checkNewEdgeDrag(f, f2, n, 2)) {
            n2 = n3 | 2;
        }
        n3 = n2;
        if (this.checkNewEdgeDrag(f2, f, n, 8)) {
            n3 = n2 | 8;
        }
        if (n3 != 0) {
            int[] arrn = this.mEdgeDragsInProgress;
            arrn[n] = arrn[n] | n3;
            this.mCallback.onEdgeDragStarted(n3, n);
        }
    }

    private void saveInitialMotion(float f, float f2, int n) {
        this.ensureMotionHistorySizeForId(n);
        float[] arrf = this.mInitialMotionX;
        this.mLastMotionX[n] = f;
        arrf[n] = f;
        arrf = this.mInitialMotionY;
        this.mLastMotionY[n] = f2;
        arrf[n] = f2;
        this.mInitialEdgesTouched[n] = this.getEdgesTouched((int)f, (int)f2);
        this.mPointersDown |= 1 << n;
    }

    private void saveLastMotion(MotionEvent motionEvent) {
        int n = motionEvent.getPointerCount();
        for (int i = 0; i < n; ++i) {
            int n2 = motionEvent.getPointerId(i);
            if (!this.isValidPointerForActionMove(n2)) continue;
            float f = motionEvent.getX(i);
            float f2 = motionEvent.getY(i);
            this.mLastMotionX[n2] = f;
            this.mLastMotionY[n2] = f2;
        }
    }

    public void abort() {
        this.cancel();
        if (this.mDragState == 2) {
            int n = this.mScroller.getCurrX();
            int n2 = this.mScroller.getCurrY();
            this.mScroller.abortAnimation();
            int n3 = this.mScroller.getCurrX();
            int n4 = this.mScroller.getCurrY();
            this.mCallback.onViewPositionChanged(this.mCapturedView, n3, n4, n3 - n, n4 - n2);
        }
        this.setDragState(0);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected boolean canScroll(@NonNull View view, boolean bl, int n, int n2, int n3, int n4) {
        boolean bl2 = view instanceof ViewGroup;
        boolean bl3 = true;
        if (bl2) {
            ViewGroup viewGroup = (ViewGroup)view;
            int n5 = view.getScrollX();
            int n6 = view.getScrollY();
            for (int i = viewGroup.getChildCount() - 1; i >= 0; --i) {
                int n7;
                int n8 = n3 + n5;
                View view2 = viewGroup.getChildAt(i);
                if (n8 < view2.getLeft() || n8 >= view2.getRight() || (n7 = n4 + n6) < view2.getTop() || n7 >= view2.getBottom() || !this.canScroll(view2, true, n, n2, n8 - view2.getLeft(), n7 - view2.getTop())) continue;
                return true;
            }
        }
        if (!bl) return false;
        bl = bl3;
        if (view.canScrollHorizontally(- n)) return bl;
        if (!view.canScrollVertically(- n2)) return false;
        return true;
    }

    public void cancel() {
        this.mActivePointerId = -1;
        this.clearMotionHistory();
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    public void captureChildView(@NonNull View object, int n) {
        if (object.getParent() != this.mParentView) {
            object = new StringBuilder();
            object.append("captureChildView: parameter must be a descendant of the ViewDragHelper's tracked parent view (");
            object.append((Object)this.mParentView);
            object.append(")");
            throw new IllegalArgumentException(object.toString());
        }
        this.mCapturedView = object;
        this.mActivePointerId = n;
        this.mCallback.onViewCaptured((View)object, n);
        this.setDragState(1);
    }

    public boolean checkTouchSlop(int n) {
        int n2 = this.mInitialMotionX.length;
        for (int i = 0; i < n2; ++i) {
            if (!this.checkTouchSlop(n, i)) continue;
            return true;
        }
        return false;
    }

    public boolean checkTouchSlop(int n, int n2) {
        boolean bl = this.isPointerDown(n2);
        boolean bl2 = false;
        boolean bl3 = false;
        boolean bl4 = false;
        if (!bl) {
            return false;
        }
        boolean bl5 = (n & 1) == 1;
        n = (n & 2) == 2 ? 1 : 0;
        float f = this.mLastMotionX[n2] - this.mInitialMotionX[n2];
        float f2 = this.mLastMotionY[n2] - this.mInitialMotionY[n2];
        if (bl5 && n != 0) {
            if (f * f + f2 * f2 > (float)(this.mTouchSlop * this.mTouchSlop)) {
                bl4 = true;
            }
            return bl4;
        }
        if (bl5) {
            bl4 = bl2;
            if (Math.abs(f) > (float)this.mTouchSlop) {
                bl4 = true;
            }
            return bl4;
        }
        if (n != 0) {
            bl4 = bl3;
            if (Math.abs(f2) > (float)this.mTouchSlop) {
                bl4 = true;
            }
            return bl4;
        }
        return false;
    }

    public boolean continueSettling(boolean bl) {
        int n = this.mDragState;
        boolean bl2 = false;
        if (n == 2) {
            boolean bl3 = this.mScroller.computeScrollOffset();
            n = this.mScroller.getCurrX();
            int n2 = this.mScroller.getCurrY();
            int n3 = n - this.mCapturedView.getLeft();
            int n4 = n2 - this.mCapturedView.getTop();
            if (n3 != 0) {
                ViewCompat.offsetLeftAndRight(this.mCapturedView, n3);
            }
            if (n4 != 0) {
                ViewCompat.offsetTopAndBottom(this.mCapturedView, n4);
            }
            if (n3 != 0 || n4 != 0) {
                this.mCallback.onViewPositionChanged(this.mCapturedView, n, n2, n3, n4);
            }
            boolean bl4 = bl3;
            if (bl3) {
                bl4 = bl3;
                if (n == this.mScroller.getFinalX()) {
                    bl4 = bl3;
                    if (n2 == this.mScroller.getFinalY()) {
                        this.mScroller.abortAnimation();
                        bl4 = false;
                    }
                }
            }
            if (!bl4) {
                if (bl) {
                    this.mParentView.post(this.mSetIdleRunnable);
                } else {
                    this.setDragState(0);
                }
            }
        }
        bl = bl2;
        if (this.mDragState == 2) {
            bl = true;
        }
        return bl;
    }

    @Nullable
    public View findTopChildUnder(int n, int n2) {
        for (int i = this.mParentView.getChildCount() - 1; i >= 0; --i) {
            View view = this.mParentView.getChildAt(this.mCallback.getOrderedChildIndex(i));
            if (n < view.getLeft() || n >= view.getRight() || n2 < view.getTop() || n2 >= view.getBottom()) continue;
            return view;
        }
        return null;
    }

    public void flingCapturedView(int n, int n2, int n3, int n4) {
        if (!this.mReleaseInProgress) {
            throw new IllegalStateException("Cannot flingCapturedView outside of a call to Callback#onViewReleased");
        }
        this.mScroller.fling(this.mCapturedView.getLeft(), this.mCapturedView.getTop(), (int)this.mVelocityTracker.getXVelocity(this.mActivePointerId), (int)this.mVelocityTracker.getYVelocity(this.mActivePointerId), n, n3, n2, n4);
        this.setDragState(2);
    }

    public int getActivePointerId() {
        return this.mActivePointerId;
    }

    @Nullable
    public View getCapturedView() {
        return this.mCapturedView;
    }

    public int getEdgeSize() {
        return this.mEdgeSize;
    }

    public float getMinVelocity() {
        return this.mMinVelocity;
    }

    public int getTouchSlop() {
        return this.mTouchSlop;
    }

    public int getViewDragState() {
        return this.mDragState;
    }

    public boolean isCapturedViewUnder(int n, int n2) {
        return this.isViewUnder(this.mCapturedView, n, n2);
    }

    public boolean isEdgeTouched(int n) {
        int n2 = this.mInitialEdgesTouched.length;
        for (int i = 0; i < n2; ++i) {
            if (!this.isEdgeTouched(n, i)) continue;
            return true;
        }
        return false;
    }

    public boolean isEdgeTouched(int n, int n2) {
        if (this.isPointerDown(n2) && (n & this.mInitialEdgesTouched[n2]) != 0) {
            return true;
        }
        return false;
    }

    public boolean isPointerDown(int n) {
        if ((1 << n & this.mPointersDown) != 0) {
            return true;
        }
        return false;
    }

    public boolean isViewUnder(@Nullable View view, int n, int n2) {
        boolean bl = false;
        if (view == null) {
            return false;
        }
        boolean bl2 = bl;
        if (n >= view.getLeft()) {
            bl2 = bl;
            if (n < view.getRight()) {
                bl2 = bl;
                if (n2 >= view.getTop()) {
                    bl2 = bl;
                    if (n2 < view.getBottom()) {
                        bl2 = true;
                    }
                }
            }
        }
        return bl2;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public void processTouchEvent(@NonNull MotionEvent var1_1) {
        var9_2 = var1_1.getActionMasked();
        var8_3 = var1_1.getActionIndex();
        if (var9_2 == 0) {
            this.cancel();
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(var1_1);
        var7_4 = 0;
        switch (var9_2) {
            default: {
                return;
            }
            case 6: {
                var7_4 = var1_1.getPointerId(var8_3);
                if (this.mDragState != 1 || var7_4 != this.mActivePointerId) ** GOTO lbl24
                var8_3 = var1_1.getPointerCount();
                for (var6_5 = 0; var6_5 < var8_3; ++var6_5) {
                    var9_2 = var1_1.getPointerId(var6_5);
                    if (var9_2 == this.mActivePointerId || this.findTopChildUnder((int)(var2_6 = var1_1.getX(var6_5)), (int)(var3_11 = var1_1.getY(var6_5))) != this.mCapturedView || !this.tryCaptureViewForDrag(this.mCapturedView, var9_2)) continue;
                    var6_5 = this.mActivePointerId;
                    ** GOTO lbl22
                }
                var6_5 = -1;
lbl22: // 2 sources:
                if (var6_5 == -1) {
                    this.releaseViewForPointerUp();
                }
lbl24: // 4 sources:
                this.clearMotionHistory(var7_4);
                return;
            }
            case 5: {
                var6_5 = var1_1.getPointerId(var8_3);
                var2_7 = var1_1.getX(var8_3);
                var3_12 = var1_1.getY(var8_3);
                this.saveInitialMotion(var2_7, var3_12, var6_5);
                if (this.mDragState == 0) {
                    this.tryCaptureViewForDrag(this.findTopChildUnder((int)var2_7, (int)var3_12), var6_5);
                    var7_4 = this.mInitialEdgesTouched[var6_5];
                    if ((this.mTrackingEdges & var7_4) == 0) return;
                    this.mCallback.onEdgeTouched(var7_4 & this.mTrackingEdges, var6_5);
                    return;
                }
                if (this.isCapturedViewUnder((int)var2_7, (int)var3_12) == false) return;
                this.tryCaptureViewForDrag(this.mCapturedView, var6_5);
                return;
            }
            case 3: {
                if (this.mDragState == 1) {
                    this.dispatchViewReleased(0.0f, 0.0f);
                }
                this.cancel();
                return;
            }
            case 2: {
                if (this.mDragState == 1) {
                    if (!this.isValidPointerForActionMove(this.mActivePointerId)) {
                        return;
                    }
                    var6_5 = var1_1.findPointerIndex(this.mActivePointerId);
                    var2_8 = var1_1.getX(var6_5);
                    var3_13 = var1_1.getY(var6_5);
                    var6_5 = (int)(var2_8 - this.mLastMotionX[this.mActivePointerId]);
                    var7_4 = (int)(var3_13 - this.mLastMotionY[this.mActivePointerId]);
                    this.dragTo(this.mCapturedView.getLeft() + var6_5, this.mCapturedView.getTop() + var7_4, var6_5, var7_4);
                    this.saveLastMotion(var1_1);
                    return;
                }
                var8_3 = var1_1.getPointerCount();
                for (var6_5 = var7_4; var6_5 < var8_3; ++var6_5) {
                    var7_4 = var1_1.getPointerId(var6_5);
                    if (!this.isValidPointerForActionMove(var7_4)) continue;
                    var2_9 = var1_1.getX(var6_5);
                    var3_14 = var1_1.getY(var6_5);
                    var4_16 = var2_9 - this.mInitialMotionX[var7_4];
                    var5_17 = var3_14 - this.mInitialMotionY[var7_4];
                    this.reportNewEdgeDrags(var4_16, var5_17, var7_4);
                    if (this.mDragState == 1 || this.checkTouchSlop(var10_18 = this.findTopChildUnder((int)var2_9, (int)var3_14), var4_16, var5_17) && this.tryCaptureViewForDrag(var10_18, var7_4)) break;
                }
                this.saveLastMotion(var1_1);
                return;
            }
            case 1: {
                if (this.mDragState == 1) {
                    this.releaseViewForPointerUp();
                }
                this.cancel();
                return;
            }
            case 0: 
        }
        var2_10 = var1_1.getX();
        var3_15 = var1_1.getY();
        var6_5 = var1_1.getPointerId(0);
        var1_1 = this.findTopChildUnder((int)var2_10, (int)var3_15);
        this.saveInitialMotion(var2_10, var3_15, var6_5);
        this.tryCaptureViewForDrag((View)var1_1, var6_5);
        var7_4 = this.mInitialEdgesTouched[var6_5];
        if ((this.mTrackingEdges & var7_4) == 0) return;
        this.mCallback.onEdgeTouched(var7_4 & this.mTrackingEdges, var6_5);
    }

    void setDragState(int n) {
        this.mParentView.removeCallbacks(this.mSetIdleRunnable);
        if (this.mDragState != n) {
            this.mDragState = n;
            this.mCallback.onViewDragStateChanged(n);
            if (this.mDragState == 0) {
                this.mCapturedView = null;
            }
        }
    }

    public void setEdgeTrackingEnabled(int n) {
        this.mTrackingEdges = n;
    }

    public void setMinVelocity(float f) {
        this.mMinVelocity = f;
    }

    public boolean settleCapturedViewAt(int n, int n2) {
        if (!this.mReleaseInProgress) {
            throw new IllegalStateException("Cannot settleCapturedViewAt outside of a call to Callback#onViewReleased");
        }
        return this.forceSettleCapturedViewAt(n, n2, (int)this.mVelocityTracker.getXVelocity(this.mActivePointerId), (int)this.mVelocityTracker.getYVelocity(this.mActivePointerId));
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public boolean shouldInterceptTouchEvent(@NonNull MotionEvent var1_1) {
        var7_2 = var1_1.getActionMasked();
        var6_3 = var1_1.getActionIndex();
        if (var7_2 == 0) {
            this.cancel();
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(var1_1);
        switch (var7_2) {
            case 6: {
                this.clearMotionHistory(var1_1.getPointerId(var6_3));
                ** break;
            }
            case 5: {
                var7_2 = var1_1.getPointerId(var6_3);
                var2_4 = var1_1.getX(var6_3);
                var3_7 = var1_1.getY(var6_3);
                this.saveInitialMotion(var2_4, var3_7, var7_2);
                if (this.mDragState == 0) {
                    var6_3 = this.mInitialEdgesTouched[var7_2];
                    if ((this.mTrackingEdges & var6_3) != 0) {
                        this.mCallback.onEdgeTouched(var6_3 & this.mTrackingEdges, var7_2);
                        ** break;
                    }
                } else if (this.mDragState == 2 && (var1_1 = this.findTopChildUnder((int)var2_4, (int)var3_7)) == this.mCapturedView) {
                    this.tryCaptureViewForDrag((View)var1_1, var7_2);
                    ** break;
                }
                ** GOTO lbl-1000
            }
            case 2: {
                ** if (this.mInitialMotionX == null) goto lbl-1000
lbl-1000: // 1 sources:
                {
                    if (this.mInitialMotionY == null) {
                        ** break;
                    } else {
                        ** GOTO lbl31
                    }
lbl31: // 2 sources:
                    ** GOTO lbl34
                }
            }
lbl-1000: // 4 sources:
            {
                default: {
                    ** break;
                }
            }
lbl34: // 1 sources:
            var8_10 = var1_1.getPointerCount();
            for (var6_3 = 0; var6_3 < var8_10; ++var6_3) {
                var9_13 = var1_1.getPointerId(var6_3);
                if (!this.isValidPointerForActionMove(var9_13)) continue;
                var2_5 = var1_1.getX(var6_3);
                var3_8 = var1_1.getY(var6_3);
                var4_11 = var2_5 - this.mInitialMotionX[var9_13];
                var5_12 = var3_8 - this.mInitialMotionY[var9_13];
                var17_20 = this.findTopChildUnder((int)var2_5, (int)var3_8);
                var7_2 = var17_20 != null && this.checkTouchSlop(var17_20, var4_11, var5_12) != false ? 1 : 0;
                if (var7_2 != 0) {
                    var10_14 = var17_20.getLeft();
                    var11_15 = (int)var4_11;
                    var11_15 = this.mCallback.clampViewPositionHorizontal(var17_20, var10_14 + var11_15, var11_15);
                    var12_16 = var17_20.getTop();
                    var13_17 = (int)var5_12;
                    var13_17 = this.mCallback.clampViewPositionVertical(var17_20, var12_16 + var13_17, var13_17);
                    var14_18 = this.mCallback.getViewHorizontalDragRange(var17_20);
                    var15_19 = this.mCallback.getViewVerticalDragRange(var17_20);
                    if ((var14_18 == 0 || var14_18 > 0 && var11_15 == var10_14) && (var15_19 == 0 || var15_19 > 0 && var13_17 == var12_16)) break;
                }
                this.reportNewEdgeDrags(var4_11, var5_12, var9_13);
                if (this.mDragState == 1 || var7_2 != 0 && this.tryCaptureViewForDrag(var17_20, var9_13)) break;
            }
            this.saveLastMotion(var1_1);
            ** break;
            case 1: 
            case 3: {
                this.cancel();
                ** break;
            }
            case 0: 
        }
        var2_6 = var1_1.getX();
        var3_9 = var1_1.getY();
        var6_3 = var1_1.getPointerId(0);
        this.saveInitialMotion(var2_6, var3_9, var6_3);
        var1_1 = this.findTopChildUnder((int)var2_6, (int)var3_9);
        if (var1_1 == this.mCapturedView && this.mDragState == 2) {
            this.tryCaptureViewForDrag((View)var1_1, var6_3);
        }
        if ((this.mTrackingEdges & (var7_2 = this.mInitialEdgesTouched[var6_3])) != 0) {
            this.mCallback.onEdgeTouched(var7_2 & this.mTrackingEdges, var6_3);
        }
lbl72: // 10 sources:
        var16_21 = false;
        if (this.mDragState != 1) return var16_21;
        return true;
    }

    public boolean smoothSlideViewTo(@NonNull View view, int n, int n2) {
        this.mCapturedView = view;
        this.mActivePointerId = -1;
        boolean bl = this.forceSettleCapturedViewAt(n, n2, 0, 0);
        if (!bl && this.mDragState == 0 && this.mCapturedView != null) {
            this.mCapturedView = null;
        }
        return bl;
    }

    boolean tryCaptureViewForDrag(View view, int n) {
        if (view == this.mCapturedView && this.mActivePointerId == n) {
            return true;
        }
        if (view != null && this.mCallback.tryCaptureView(view, n)) {
            this.mActivePointerId = n;
            this.captureChildView(view, n);
            return true;
        }
        return false;
    }

    public static abstract class Callback {
        public int clampViewPositionHorizontal(@NonNull View view, int n, int n2) {
            return 0;
        }

        public int clampViewPositionVertical(@NonNull View view, int n, int n2) {
            return 0;
        }

        public int getOrderedChildIndex(int n) {
            return n;
        }

        public int getViewHorizontalDragRange(@NonNull View view) {
            return 0;
        }

        public int getViewVerticalDragRange(@NonNull View view) {
            return 0;
        }

        public void onEdgeDragStarted(int n, int n2) {
        }

        public boolean onEdgeLock(int n) {
            return false;
        }

        public void onEdgeTouched(int n, int n2) {
        }

        public void onViewCaptured(@NonNull View view, int n) {
        }

        public void onViewDragStateChanged(int n) {
        }

        public void onViewPositionChanged(@NonNull View view, int n, int n2, int n3, int n4) {
        }

        public void onViewReleased(@NonNull View view, float f, float f2) {
        }

        public abstract boolean tryCaptureView(@NonNull View var1, int var2);
    }

}
