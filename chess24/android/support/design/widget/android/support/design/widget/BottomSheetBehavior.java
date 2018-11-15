/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$ClassLoaderCreator
 *  android.os.Parcelable$Creator
 *  android.util.AttributeSet
 *  android.util.TypedValue
 *  android.view.MotionEvent
 *  android.view.VelocityTracker
 *  android.view.View
 *  android.view.ViewConfiguration
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewParent
 */
package android.support.design.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.annotation.VisibleForTesting;
import android.support.design.R;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.math.MathUtils;
import android.support.v4.view.AbsSavedState;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;

public class BottomSheetBehavior<V extends View>
extends CoordinatorLayout.Behavior<V> {
    private static final float HIDE_FRICTION = 0.1f;
    private static final float HIDE_THRESHOLD = 0.5f;
    public static final int PEEK_HEIGHT_AUTO = -1;
    public static final int STATE_COLLAPSED = 4;
    public static final int STATE_DRAGGING = 1;
    public static final int STATE_EXPANDED = 3;
    public static final int STATE_HIDDEN = 5;
    public static final int STATE_SETTLING = 2;
    int mActivePointerId;
    private BottomSheetCallback mCallback;
    private final ViewDragHelper.Callback mDragCallback = new ViewDragHelper.Callback(){

        @Override
        public int clampViewPositionHorizontal(View view, int n, int n2) {
            return view.getLeft();
        }

        @Override
        public int clampViewPositionVertical(View view, int n, int n2) {
            int n3 = BottomSheetBehavior.this.mMinOffset;
            n2 = BottomSheetBehavior.this.mHideable ? BottomSheetBehavior.this.mParentHeight : BottomSheetBehavior.this.mMaxOffset;
            return MathUtils.clamp(n, n3, n2);
        }

        @Override
        public int getViewVerticalDragRange(View view) {
            if (BottomSheetBehavior.this.mHideable) {
                return BottomSheetBehavior.this.mParentHeight - BottomSheetBehavior.this.mMinOffset;
            }
            return BottomSheetBehavior.this.mMaxOffset - BottomSheetBehavior.this.mMinOffset;
        }

        @Override
        public void onViewDragStateChanged(int n) {
            if (n == 1) {
                BottomSheetBehavior.this.setStateInternal(1);
            }
        }

        @Override
        public void onViewPositionChanged(View view, int n, int n2, int n3, int n4) {
            BottomSheetBehavior.this.dispatchOnSlide(n2);
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        @Override
        public void onViewReleased(View var1_1, float var2_2, float var3_3) {
            block7 : {
                var5_4 = 4;
                if (var3_3 >= 0.0f) break block7;
                var4_5 = BottomSheetBehavior.this.mMinOffset;
                ** GOTO lbl14
            }
            if (BottomSheetBehavior.this.mHideable && BottomSheetBehavior.this.shouldHide(var1_1, var3_3)) {
                var4_5 = BottomSheetBehavior.this.mParentHeight;
                var5_4 = 5;
            } else if (var3_3 == 0.0f) {
                var4_5 = var1_1.getTop();
                if (Math.abs(var4_5 - BottomSheetBehavior.this.mMinOffset) < Math.abs(var4_5 - BottomSheetBehavior.this.mMaxOffset)) {
                    var4_5 = BottomSheetBehavior.this.mMinOffset;
lbl14: // 2 sources:
                    var5_4 = 3;
                } else {
                    var4_5 = BottomSheetBehavior.this.mMaxOffset;
                }
            } else {
                var4_5 = BottomSheetBehavior.this.mMaxOffset;
            }
            if (BottomSheetBehavior.this.mViewDragHelper.settleCapturedViewAt(var1_1.getLeft(), var4_5)) {
                BottomSheetBehavior.this.setStateInternal(2);
                ViewCompat.postOnAnimation(var1_1, new SettleRunnable(var1_1, var5_4));
                return;
            }
            BottomSheetBehavior.this.setStateInternal(var5_4);
        }

        @Override
        public boolean tryCaptureView(View view, int n) {
            View view2;
            if (BottomSheetBehavior.this.mState == 1) {
                return false;
            }
            if (BottomSheetBehavior.this.mTouchingScrollingChild) {
                return false;
            }
            if (BottomSheetBehavior.this.mState == 3 && BottomSheetBehavior.this.mActivePointerId == n && (view2 = (View)BottomSheetBehavior.this.mNestedScrollingChildRef.get()) != null && view2.canScrollVertically(-1)) {
                return false;
            }
            if (BottomSheetBehavior.this.mViewRef != null && BottomSheetBehavior.this.mViewRef.get() == view) {
                return true;
            }
            return false;
        }
    };
    boolean mHideable;
    private boolean mIgnoreEvents;
    private int mInitialY;
    private int mLastNestedScrollDy;
    int mMaxOffset;
    private float mMaximumVelocity;
    int mMinOffset;
    private boolean mNestedScrolled;
    WeakReference<View> mNestedScrollingChildRef;
    int mParentHeight;
    private int mPeekHeight;
    private boolean mPeekHeightAuto;
    private int mPeekHeightMin;
    private boolean mSkipCollapsed;
    int mState = 4;
    boolean mTouchingScrollingChild;
    private VelocityTracker mVelocityTracker;
    ViewDragHelper mViewDragHelper;
    WeakReference<V> mViewRef;

    public BottomSheetBehavior() {
    }

    public BottomSheetBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        attributeSet = context.obtainStyledAttributes(attributeSet, R.styleable.BottomSheetBehavior_Layout);
        TypedValue typedValue = attributeSet.peekValue(R.styleable.BottomSheetBehavior_Layout_behavior_peekHeight);
        if (typedValue != null && typedValue.data == -1) {
            this.setPeekHeight(typedValue.data);
        } else {
            this.setPeekHeight(attributeSet.getDimensionPixelSize(R.styleable.BottomSheetBehavior_Layout_behavior_peekHeight, -1));
        }
        this.setHideable(attributeSet.getBoolean(R.styleable.BottomSheetBehavior_Layout_behavior_hideable, false));
        this.setSkipCollapsed(attributeSet.getBoolean(R.styleable.BottomSheetBehavior_Layout_behavior_skipCollapsed, false));
        attributeSet.recycle();
        this.mMaximumVelocity = ViewConfiguration.get((Context)context).getScaledMaximumFlingVelocity();
    }

    public static <V extends View> BottomSheetBehavior<V> from(V object) {
        if (!((object = object.getLayoutParams()) instanceof CoordinatorLayout.LayoutParams)) {
            throw new IllegalArgumentException("The view is not a child of CoordinatorLayout");
        }
        if (!((object = ((CoordinatorLayout.LayoutParams)((Object)object)).getBehavior()) instanceof BottomSheetBehavior)) {
            throw new IllegalArgumentException("The view is not associated with BottomSheetBehavior");
        }
        return (BottomSheetBehavior)object;
    }

    private float getYVelocity() {
        this.mVelocityTracker.computeCurrentVelocity(1000, this.mMaximumVelocity);
        return this.mVelocityTracker.getYVelocity(this.mActivePointerId);
    }

    private void reset() {
        this.mActivePointerId = -1;
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    void dispatchOnSlide(int n) {
        View view = (View)this.mViewRef.get();
        if (view != null && this.mCallback != null) {
            if (n > this.mMaxOffset) {
                this.mCallback.onSlide(view, (float)(this.mMaxOffset - n) / (float)(this.mParentHeight - this.mMaxOffset));
                return;
            }
            this.mCallback.onSlide(view, (float)(this.mMaxOffset - n) / (float)(this.mMaxOffset - this.mMinOffset));
        }
    }

    @VisibleForTesting
    View findScrollingChild(View view) {
        if (ViewCompat.isNestedScrollingEnabled(view)) {
            return view;
        }
        if (view instanceof ViewGroup) {
            view = (ViewGroup)view;
            int n = view.getChildCount();
            for (int i = 0; i < n; ++i) {
                View view2 = this.findScrollingChild(view.getChildAt(i));
                if (view2 == null) continue;
                return view2;
            }
        }
        return null;
    }

    public final int getPeekHeight() {
        if (this.mPeekHeightAuto) {
            return -1;
        }
        return this.mPeekHeight;
    }

    @VisibleForTesting
    int getPeekHeightMin() {
        return this.mPeekHeightMin;
    }

    public boolean getSkipCollapsed() {
        return this.mSkipCollapsed;
    }

    public final int getState() {
        return this.mState;
    }

    public boolean isHideable() {
        return this.mHideable;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public boolean onInterceptTouchEvent(CoordinatorLayout var1_1, V var2_2, MotionEvent var3_3) {
        var6_5 = var2_2 /* !! */ .isShown();
        var7_6 = false;
        if (!var6_5) {
            this.mIgnoreEvents = true;
            return false;
        }
        var4_7 = var3_4.getActionMasked();
        if (var4_7 == 0) {
            this.reset();
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement((MotionEvent)var3_4);
        if (var4_7 != 3) {
            switch (var4_7) {
                default: {
                    ** break;
                }
                case 0: {
                    var5_8 = (int)var3_4.getX();
                    this.mInitialY = (int)var3_4.getY();
                    var8_9 = this.mNestedScrollingChildRef != null ? (View)this.mNestedScrollingChildRef.get() : null;
                    if (var8_9 != null && var1_1.isPointInChildBounds(var8_9, var5_8, this.mInitialY)) {
                        this.mActivePointerId = var3_4.getPointerId(var3_4.getActionIndex());
                        this.mTouchingScrollingChild = true;
                    }
                    var6_5 = this.mActivePointerId == -1 && var1_1.isPointInChildBounds((View)var2_2 /* !! */ , var5_8, this.mInitialY) == false;
                    this.mIgnoreEvents = var6_5;
                    ** break;
                }
                case 1: 
            }
        }
        this.mTouchingScrollingChild = false;
        this.mActivePointerId = -1;
        if (this.mIgnoreEvents) {
            this.mIgnoreEvents = false;
            return false;
        }
lbl32: // 4 sources:
        if (!this.mIgnoreEvents && this.mViewDragHelper.shouldInterceptTouchEvent((MotionEvent)var3_4)) {
            return true;
        }
        var2_3 = (View)this.mNestedScrollingChildRef.get();
        var6_5 = var7_6;
        if (var4_7 != 2) return var6_5;
        var6_5 = var7_6;
        if (var2_3 == null) return var6_5;
        var6_5 = var7_6;
        if (this.mIgnoreEvents != false) return var6_5;
        var6_5 = var7_6;
        if (this.mState == 1) return var6_5;
        var6_5 = var7_6;
        if (var1_1.isPointInChildBounds(var2_3, (int)var3_4.getX(), (int)var3_4.getY()) != false) return var6_5;
        var6_5 = var7_6;
        if (Math.abs((float)this.mInitialY - var3_4.getY()) <= (float)this.mViewDragHelper.getTouchSlop()) return var6_5;
        return true;
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout coordinatorLayout, V v, int n) {
        if (ViewCompat.getFitsSystemWindows((View)coordinatorLayout) && !ViewCompat.getFitsSystemWindows(v)) {
            ViewCompat.setFitsSystemWindows(v, true);
        }
        int n2 = v.getTop();
        coordinatorLayout.onLayoutChild((View)v, n);
        this.mParentHeight = coordinatorLayout.getHeight();
        if (this.mPeekHeightAuto) {
            if (this.mPeekHeightMin == 0) {
                this.mPeekHeightMin = coordinatorLayout.getResources().getDimensionPixelSize(R.dimen.design_bottom_sheet_peek_height_min);
            }
            n = Math.max(this.mPeekHeightMin, this.mParentHeight - coordinatorLayout.getWidth() * 9 / 16);
        } else {
            n = this.mPeekHeight;
        }
        this.mMinOffset = Math.max(0, this.mParentHeight - v.getHeight());
        this.mMaxOffset = Math.max(this.mParentHeight - n, this.mMinOffset);
        if (this.mState == 3) {
            ViewCompat.offsetTopAndBottom(v, this.mMinOffset);
        } else if (this.mHideable && this.mState == 5) {
            ViewCompat.offsetTopAndBottom(v, this.mParentHeight);
        } else if (this.mState == 4) {
            ViewCompat.offsetTopAndBottom(v, this.mMaxOffset);
        } else if (this.mState == 1 || this.mState == 2) {
            ViewCompat.offsetTopAndBottom(v, n2 - v.getTop());
        }
        if (this.mViewDragHelper == null) {
            this.mViewDragHelper = ViewDragHelper.create(coordinatorLayout, this.mDragCallback);
        }
        this.mViewRef = new WeakReference<V>(v);
        this.mNestedScrollingChildRef = new WeakReference<View>(this.findScrollingChild((View)v));
        return true;
    }

    @Override
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, V v, View view, float f, float f2) {
        if (view == this.mNestedScrollingChildRef.get() && (this.mState != 3 || super.onNestedPreFling(coordinatorLayout, v, view, f, f2))) {
            return true;
        }
        return false;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, V v, View view, int n, int n2, int[] arrn) {
        if (view != (View)this.mNestedScrollingChildRef.get()) {
            return;
        }
        n = v.getTop();
        int n3 = n - n2;
        if (n2 > 0) {
            if (n3 < this.mMinOffset) {
                arrn[1] = n - this.mMinOffset;
                ViewCompat.offsetTopAndBottom(v, - arrn[1]);
                this.setStateInternal(3);
            } else {
                arrn[1] = n2;
                ViewCompat.offsetTopAndBottom(v, - n2);
                this.setStateInternal(1);
            }
        } else if (n2 < 0 && !view.canScrollVertically(-1)) {
            if (n3 > this.mMaxOffset && !this.mHideable) {
                arrn[1] = n - this.mMaxOffset;
                ViewCompat.offsetTopAndBottom(v, - arrn[1]);
                this.setStateInternal(4);
            } else {
                arrn[1] = n2;
                ViewCompat.offsetTopAndBottom(v, - n2);
                this.setStateInternal(1);
            }
        }
        this.dispatchOnSlide(v.getTop());
        this.mLastNestedScrollDy = n2;
        this.mNestedScrolled = true;
    }

    @Override
    public void onRestoreInstanceState(CoordinatorLayout coordinatorLayout, V v, Parcelable parcelable) {
        parcelable = (SavedState)parcelable;
        super.onRestoreInstanceState(coordinatorLayout, v, parcelable.getSuperState());
        if (parcelable.state != 1 && parcelable.state != 2) {
            this.mState = parcelable.state;
            return;
        }
        this.mState = 4;
    }

    @Override
    public Parcelable onSaveInstanceState(CoordinatorLayout coordinatorLayout, V v) {
        return new SavedState(super.onSaveInstanceState(coordinatorLayout, v), this.mState);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, V v, View view, View view2, int n) {
        boolean bl = false;
        this.mLastNestedScrollDy = 0;
        this.mNestedScrolled = false;
        if ((n & 2) != 0) {
            bl = true;
        }
        return bl;
    }

    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, V v, View view) {
        block4 : {
            int n;
            int n2;
            block6 : {
                block10 : {
                    block8 : {
                        block9 : {
                            block7 : {
                                block5 : {
                                    n = v.getTop();
                                    int n3 = this.mMinOffset;
                                    n2 = 3;
                                    if (n == n3) {
                                        this.setStateInternal(3);
                                        return;
                                    }
                                    if (this.mNestedScrollingChildRef == null || view != this.mNestedScrollingChildRef.get()) break block4;
                                    if (!this.mNestedScrolled) {
                                        return;
                                    }
                                    if (this.mLastNestedScrollDy <= 0) break block5;
                                    n = this.mMinOffset;
                                    break block6;
                                }
                                if (!this.mHideable || !this.shouldHide((View)v, this.getYVelocity())) break block7;
                                n = this.mParentHeight;
                                n2 = 5;
                                break block6;
                            }
                            if (this.mLastNestedScrollDy != 0) break block8;
                            n = v.getTop();
                            if (Math.abs(n - this.mMinOffset) >= Math.abs(n - this.mMaxOffset)) break block9;
                            n = this.mMinOffset;
                            break block6;
                        }
                        n = this.mMaxOffset;
                        break block10;
                    }
                    n = this.mMaxOffset;
                }
                n2 = 4;
            }
            if (this.mViewDragHelper.smoothSlideViewTo((View)v, v.getLeft(), n)) {
                this.setStateInternal(2);
                ViewCompat.postOnAnimation(v, new SettleRunnable((View)v, n2));
            } else {
                this.setStateInternal(n2);
            }
            this.mNestedScrolled = false;
            return;
        }
    }

    @Override
    public boolean onTouchEvent(CoordinatorLayout coordinatorLayout, V v, MotionEvent motionEvent) {
        if (!v.isShown()) {
            return false;
        }
        int n = motionEvent.getActionMasked();
        if (this.mState == 1 && n == 0) {
            return true;
        }
        if (this.mViewDragHelper != null) {
            this.mViewDragHelper.processTouchEvent(motionEvent);
        }
        if (n == 0) {
            this.reset();
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(motionEvent);
        if (n == 2 && !this.mIgnoreEvents && Math.abs((float)this.mInitialY - motionEvent.getY()) > (float)this.mViewDragHelper.getTouchSlop()) {
            this.mViewDragHelper.captureChildView((View)v, motionEvent.getPointerId(motionEvent.getActionIndex()));
        }
        return this.mIgnoreEvents ^ true;
    }

    public void setBottomSheetCallback(BottomSheetCallback bottomSheetCallback) {
        this.mCallback = bottomSheetCallback;
    }

    public void setHideable(boolean bl) {
        this.mHideable = bl;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final void setPeekHeight(int n) {
        int n2 = 1;
        if (n == -1) {
            if (this.mPeekHeightAuto) return;
            this.mPeekHeightAuto = true;
            n = n2;
        } else {
            if (!this.mPeekHeightAuto) {
                if (this.mPeekHeight == n) return;
            }
            this.mPeekHeightAuto = false;
            this.mPeekHeight = Math.max(0, n);
            this.mMaxOffset = this.mParentHeight - n;
            n = n2;
        }
        if (n == 0) return;
        if (this.mState != 4) return;
        if (this.mViewRef == null) return;
        View view = (View)this.mViewRef.get();
        if (view == null) return;
        view.requestLayout();
    }

    public void setSkipCollapsed(boolean bl) {
        this.mSkipCollapsed = bl;
    }

    public final void setState(final int n) {
        if (n == this.mState) {
            return;
        }
        if (this.mViewRef == null) {
            if (n == 4 || n == 3 || this.mHideable && n == 5) {
                this.mState = n;
            }
            return;
        }
        final View view = (View)this.mViewRef.get();
        if (view == null) {
            return;
        }
        ViewParent viewParent = view.getParent();
        if (viewParent != null && viewParent.isLayoutRequested() && ViewCompat.isAttachedToWindow(view)) {
            view.post(new Runnable(){

                @Override
                public void run() {
                    BottomSheetBehavior.this.startSettlingAnimation(view, n);
                }
            });
            return;
        }
        this.startSettlingAnimation(view, n);
    }

    void setStateInternal(int n) {
        if (this.mState == n) {
            return;
        }
        this.mState = n;
        View view = (View)this.mViewRef.get();
        if (view != null && this.mCallback != null) {
            this.mCallback.onStateChanged(view, n);
        }
    }

    boolean shouldHide(View view, float f) {
        if (this.mSkipCollapsed) {
            return true;
        }
        if (view.getTop() < this.mMaxOffset) {
            return false;
        }
        if (Math.abs((float)view.getTop() + f * 0.1f - (float)this.mMaxOffset) / (float)this.mPeekHeight > 0.5f) {
            return true;
        }
        return false;
    }

    void startSettlingAnimation(View object, int n) {
        block7 : {
            int n2;
            block5 : {
                block6 : {
                    block4 : {
                        if (n != 4) break block4;
                        n2 = this.mMaxOffset;
                        break block5;
                    }
                    if (n != 3) break block6;
                    n2 = this.mMinOffset;
                    break block5;
                }
                if (!this.mHideable || n != 5) break block7;
                n2 = this.mParentHeight;
            }
            if (this.mViewDragHelper.smoothSlideViewTo((View)object, object.getLeft(), n2)) {
                this.setStateInternal(2);
                ViewCompat.postOnAnimation((View)object, new SettleRunnable((View)object, n));
                return;
            }
            this.setStateInternal(n);
            return;
        }
        object = new StringBuilder();
        object.append("Illegal state argument: ");
        object.append(n);
        throw new IllegalArgumentException(object.toString());
    }

    public static abstract class BottomSheetCallback {
        public abstract void onSlide(@NonNull View var1, float var2);

        public abstract void onStateChanged(@NonNull View var1, int var2);
    }

    protected static class SavedState
    extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>(){

            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel, null);
            }

            public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            public SavedState[] newArray(int n) {
                return new SavedState[n];
            }
        };
        final int state;

        public SavedState(Parcel parcel) {
            this(parcel, null);
        }

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.state = parcel.readInt();
        }

        public SavedState(Parcelable parcelable, int n) {
            super(parcelable);
            this.state = n;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            parcel.writeInt(this.state);
        }

    }

    private class SettleRunnable
    implements Runnable {
        private final int mTargetState;
        private final View mView;

        SettleRunnable(View view, int n) {
            this.mView = view;
            this.mTargetState = n;
        }

        @Override
        public void run() {
            if (BottomSheetBehavior.this.mViewDragHelper != null && BottomSheetBehavior.this.mViewDragHelper.continueSettling(true)) {
                ViewCompat.postOnAnimation(this.mView, this);
                return;
            }
            BottomSheetBehavior.this.setStateInternal(this.mTargetState);
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public static @interface State {
    }

}
