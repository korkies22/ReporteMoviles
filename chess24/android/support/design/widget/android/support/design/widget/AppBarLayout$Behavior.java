/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.animation.TimeInterpolator
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 *  android.content.Context
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$ClassLoaderCreator
 *  android.os.Parcelable$Creator
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.animation.Interpolator
 */
package android.support.design.widget;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.AnimationUtils;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.HeaderBehavior;
import android.support.v4.math.MathUtils;
import android.support.v4.view.AbsSavedState;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import java.lang.ref.WeakReference;
import java.util.List;

public static class AppBarLayout.Behavior
extends HeaderBehavior<AppBarLayout> {
    private static final int INVALID_POSITION = -1;
    private static final int MAX_OFFSET_ANIMATION_DURATION = 600;
    private WeakReference<View> mLastNestedScrollingChildRef;
    private ValueAnimator mOffsetAnimator;
    private int mOffsetDelta;
    private int mOffsetToChildIndexOnLayout = -1;
    private boolean mOffsetToChildIndexOnLayoutIsMinHeight;
    private float mOffsetToChildIndexOnLayoutPerc;
    private DragCallback mOnDragCallback;

    public AppBarLayout.Behavior() {
    }

    public AppBarLayout.Behavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    static /* synthetic */ int access$000(AppBarLayout.Behavior behavior) {
        return behavior.mOffsetDelta;
    }

    private void animateOffsetTo(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, int n, float f) {
        int n2 = Math.abs(this.getTopBottomOffsetForScrollingSibling() - n);
        n2 = (f = Math.abs(f)) > 0.0f ? 3 * Math.round(1000.0f * ((float)n2 / f)) : (int)(((float)n2 / (float)appBarLayout.getHeight() + 1.0f) * 150.0f);
        this.animateOffsetWithDuration(coordinatorLayout, appBarLayout, n, n2);
    }

    private void animateOffsetWithDuration(final CoordinatorLayout coordinatorLayout, final AppBarLayout appBarLayout, int n, int n2) {
        int n3 = this.getTopBottomOffsetForScrollingSibling();
        if (n3 == n) {
            if (this.mOffsetAnimator != null && this.mOffsetAnimator.isRunning()) {
                this.mOffsetAnimator.cancel();
            }
            return;
        }
        if (this.mOffsetAnimator == null) {
            this.mOffsetAnimator = new ValueAnimator();
            this.mOffsetAnimator.setInterpolator((TimeInterpolator)AnimationUtils.DECELERATE_INTERPOLATOR);
            this.mOffsetAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){

                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    Behavior.this.setHeaderTopBottomOffset(coordinatorLayout, appBarLayout, (Integer)valueAnimator.getAnimatedValue());
                }
            });
        } else {
            this.mOffsetAnimator.cancel();
        }
        this.mOffsetAnimator.setDuration((long)Math.min(n2, 600));
        this.mOffsetAnimator.setIntValues(new int[]{n3, n});
        this.mOffsetAnimator.start();
    }

    private static boolean checkFlag(int n, int n2) {
        if ((n & n2) == n2) {
            return true;
        }
        return false;
    }

    private static View getAppBarChildOnOffset(AppBarLayout appBarLayout, int n) {
        int n2 = Math.abs(n);
        int n3 = appBarLayout.getChildCount();
        for (n = 0; n < n3; ++n) {
            View view = appBarLayout.getChildAt(n);
            if (n2 < view.getTop() || n2 > view.getBottom()) continue;
            return view;
        }
        return null;
    }

    private int getChildIndexOnOffset(AppBarLayout appBarLayout, int n) {
        int n2 = appBarLayout.getChildCount();
        for (int i = 0; i < n2; ++i) {
            int n3;
            View view = appBarLayout.getChildAt(i);
            int n4 = view.getTop();
            if (n4 > (n3 = - n) || view.getBottom() < n3) continue;
            return i;
        }
        return -1;
    }

    private int interpolateOffset(AppBarLayout appBarLayout, int n) {
        int n2 = Math.abs(n);
        int n3 = appBarLayout.getChildCount();
        int n4 = 0;
        for (int i = 0; i < n3; ++i) {
            View view = appBarLayout.getChildAt(i);
            AppBarLayout.LayoutParams layoutParams = (AppBarLayout.LayoutParams)view.getLayoutParams();
            Interpolator interpolator = layoutParams.getScrollInterpolator();
            if (n2 < view.getTop() || n2 > view.getBottom()) continue;
            if (interpolator == null) break;
            n3 = layoutParams.getScrollFlags();
            i = n4;
            if ((n3 & 1) != 0) {
                i = n4 = 0 + (view.getHeight() + layoutParams.topMargin + layoutParams.bottomMargin);
                if ((n3 & 2) != 0) {
                    i = n4 - ViewCompat.getMinimumHeight(view);
                }
            }
            n4 = i;
            if (ViewCompat.getFitsSystemWindows(view)) {
                n4 = i - appBarLayout.getTopInset();
            }
            if (n4 <= 0) break;
            i = view.getTop();
            float f = n4;
            i = Math.round(f * interpolator.getInterpolation((float)(n2 - i) / f));
            return Integer.signum(n) * (view.getTop() + i);
        }
        return n;
    }

    private boolean shouldJumpElevationState(CoordinatorLayout object, AppBarLayout object2) {
        object = object.getDependents((View)object2);
        int n = object.size();
        boolean bl = false;
        for (int i = 0; i < n; ++i) {
            object2 = ((CoordinatorLayout.LayoutParams)((View)object.get(i)).getLayoutParams()).getBehavior();
            if (!(object2 instanceof AppBarLayout.ScrollingViewBehavior)) continue;
            if (((AppBarLayout.ScrollingViewBehavior)object2).getOverlayTop() != 0) {
                bl = true;
            }
            return bl;
        }
        return false;
    }

    private void snapToChildIfNeeded(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout) {
        int n;
        View view;
        int n2 = this.getTopBottomOffsetForScrollingSibling();
        int n3 = this.getChildIndexOnOffset(appBarLayout, n2);
        if (n3 >= 0 && ((n = ((AppBarLayout.LayoutParams)(view = appBarLayout.getChildAt(n3)).getLayoutParams()).getScrollFlags()) & 17) == 17) {
            int n4;
            int n5 = - view.getTop();
            int n6 = n4 = - view.getBottom();
            if (n3 == appBarLayout.getChildCount() - 1) {
                n6 = n4 + appBarLayout.getTopInset();
            }
            if (AppBarLayout.Behavior.checkFlag(n, 2)) {
                n4 = n6 + ViewCompat.getMinimumHeight(view);
                n3 = n5;
            } else {
                n3 = n5;
                n4 = n6;
                if (AppBarLayout.Behavior.checkFlag(n, 5)) {
                    n4 = ViewCompat.getMinimumHeight(view) + n6;
                    if (n2 < n4) {
                        n3 = n4;
                        n4 = n6;
                    } else {
                        n3 = n5;
                    }
                }
            }
            n6 = n3;
            if (n2 < (n4 + n3) / 2) {
                n6 = n4;
            }
            this.animateOffsetTo(coordinatorLayout, appBarLayout, MathUtils.clamp(n6, - appBarLayout.getTotalScrollRange(), 0), 0.0f);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void updateAppBarLayoutDrawableState(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, int n, int n2, boolean bl) {
        block4 : {
            boolean bl2;
            block5 : {
                block7 : {
                    View view;
                    boolean bl3;
                    int n3;
                    int n4;
                    block6 : {
                        view = AppBarLayout.Behavior.getAppBarChildOnOffset(appBarLayout, n);
                        if (view == null) break block4;
                        n3 = ((AppBarLayout.LayoutParams)view.getLayoutParams()).getScrollFlags();
                        bl2 = bl3 = false;
                        if ((n3 & 1) == 0) break block5;
                        n4 = ViewCompat.getMinimumHeight(view);
                        if (n2 <= 0 || (n3 & 12) == 0) break block6;
                        bl2 = bl3;
                        if (- n < view.getBottom() - n4 - appBarLayout.getTopInset()) break block5;
                        break block7;
                    }
                    bl2 = bl3;
                    if ((n3 & 2) == 0) break block5;
                    bl2 = bl3;
                    if (- n < view.getBottom() - n4 - appBarLayout.getTopInset()) break block5;
                }
                bl2 = true;
            }
            bl2 = appBarLayout.setCollapsedState(bl2);
            if (Build.VERSION.SDK_INT >= 11 && (bl || bl2 && this.shouldJumpElevationState(coordinatorLayout, appBarLayout))) {
                appBarLayout.jumpDrawablesToCurrentState();
            }
        }
    }

    @Override
    boolean canDragView(AppBarLayout appBarLayout) {
        if (this.mOnDragCallback != null) {
            return this.mOnDragCallback.canDrag(appBarLayout);
        }
        if (this.mLastNestedScrollingChildRef != null) {
            appBarLayout = (View)this.mLastNestedScrollingChildRef.get();
            if (appBarLayout != null && appBarLayout.isShown() && !appBarLayout.canScrollVertically(-1)) {
                return true;
            }
            return false;
        }
        return true;
    }

    @Override
    int getMaxDragOffset(AppBarLayout appBarLayout) {
        return - appBarLayout.getDownNestedScrollRange();
    }

    @Override
    int getScrollRangeForDragFling(AppBarLayout appBarLayout) {
        return appBarLayout.getTotalScrollRange();
    }

    @Override
    int getTopBottomOffsetForScrollingSibling() {
        return this.getTopAndBottomOffset() + this.mOffsetDelta;
    }

    @VisibleForTesting
    boolean isOffsetAnimatorRunning() {
        if (this.mOffsetAnimator != null && this.mOffsetAnimator.isRunning()) {
            return true;
        }
        return false;
    }

    @Override
    void onFlingFinished(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout) {
        this.snapToChildIfNeeded(coordinatorLayout, appBarLayout);
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, int n) {
        boolean bl = super.onLayoutChild(coordinatorLayout, appBarLayout, n);
        int n2 = appBarLayout.getPendingAction();
        if (this.mOffsetToChildIndexOnLayout >= 0 && (n2 & 8) == 0) {
            View view = appBarLayout.getChildAt(this.mOffsetToChildIndexOnLayout);
            n = - view.getBottom();
            n = this.mOffsetToChildIndexOnLayoutIsMinHeight ? (n += ViewCompat.getMinimumHeight(view) + appBarLayout.getTopInset()) : (n += Math.round((float)view.getHeight() * this.mOffsetToChildIndexOnLayoutPerc));
            this.setHeaderTopBottomOffset(coordinatorLayout, appBarLayout, n);
        } else if (n2 != 0) {
            n = (n2 & 4) != 0 ? 1 : 0;
            if ((n2 & 2) != 0) {
                n2 = - appBarLayout.getUpNestedPreScrollRange();
                if (n != 0) {
                    this.animateOffsetTo(coordinatorLayout, appBarLayout, n2, 0.0f);
                } else {
                    this.setHeaderTopBottomOffset(coordinatorLayout, appBarLayout, n2);
                }
            } else if ((n2 & 1) != 0) {
                if (n != 0) {
                    this.animateOffsetTo(coordinatorLayout, appBarLayout, 0, 0.0f);
                } else {
                    this.setHeaderTopBottomOffset(coordinatorLayout, appBarLayout, 0);
                }
            }
        }
        appBarLayout.resetPendingAction();
        this.mOffsetToChildIndexOnLayout = -1;
        this.setTopAndBottomOffset(MathUtils.clamp(this.getTopAndBottomOffset(), - appBarLayout.getTotalScrollRange(), 0));
        this.updateAppBarLayoutDrawableState(coordinatorLayout, appBarLayout, this.getTopAndBottomOffset(), 0, true);
        appBarLayout.dispatchOffsetUpdates(this.getTopAndBottomOffset());
        return bl;
    }

    @Override
    public boolean onMeasureChild(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, int n, int n2, int n3, int n4) {
        if (((CoordinatorLayout.LayoutParams)appBarLayout.getLayoutParams()).height == -2) {
            coordinatorLayout.onMeasureChild((View)appBarLayout, n, n2, View.MeasureSpec.makeMeasureSpec((int)0, (int)0), n4);
            return true;
        }
        return super.onMeasureChild(coordinatorLayout, appBarLayout, n, n2, n3, n4);
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view, int n, int n2, int[] arrn, int n3) {
        if (n2 != 0) {
            if (n2 < 0) {
                n = - appBarLayout.getTotalScrollRange();
                n3 = appBarLayout.getDownNestedPreScrollRange() + n;
            } else {
                n = - appBarLayout.getUpNestedPreScrollRange();
                n3 = 0;
            }
            if (n != n3) {
                arrn[1] = this.scroll(coordinatorLayout, appBarLayout, n2, n, n3);
            }
        }
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view, int n, int n2, int n3, int n4, int n5) {
        if (n4 < 0) {
            this.scroll(coordinatorLayout, appBarLayout, n4, - appBarLayout.getDownNestedScrollRange(), 0);
        }
    }

    @Override
    public void onRestoreInstanceState(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, Parcelable parcelable) {
        if (parcelable instanceof SavedState) {
            parcelable = (SavedState)parcelable;
            super.onRestoreInstanceState(coordinatorLayout, appBarLayout, parcelable.getSuperState());
            this.mOffsetToChildIndexOnLayout = parcelable.firstVisibleChildIndex;
            this.mOffsetToChildIndexOnLayoutPerc = parcelable.firstVisibleChildPercentageShown;
            this.mOffsetToChildIndexOnLayoutIsMinHeight = parcelable.firstVisibleChildAtMinimumHeight;
            return;
        }
        super.onRestoreInstanceState(coordinatorLayout, appBarLayout, parcelable);
        this.mOffsetToChildIndexOnLayout = -1;
    }

    @Override
    public Parcelable onSaveInstanceState(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout) {
        Parcelable parcelable = super.onSaveInstanceState(coordinatorLayout, appBarLayout);
        int n = this.getTopAndBottomOffset();
        int n2 = appBarLayout.getChildCount();
        boolean bl = false;
        for (int i = 0; i < n2; ++i) {
            coordinatorLayout = appBarLayout.getChildAt(i);
            int n3 = coordinatorLayout.getBottom() + n;
            if (coordinatorLayout.getTop() + n > 0 || n3 < 0) continue;
            parcelable = new SavedState(parcelable);
            parcelable.firstVisibleChildIndex = i;
            if (n3 == ViewCompat.getMinimumHeight((View)coordinatorLayout) + appBarLayout.getTopInset()) {
                bl = true;
            }
            parcelable.firstVisibleChildAtMinimumHeight = bl;
            parcelable.firstVisibleChildPercentageShown = (float)n3 / (float)coordinatorLayout.getHeight();
            return parcelable;
        }
        return parcelable;
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view, View view2, int n, int n2) {
        boolean bl = (n & 2) != 0 && appBarLayout.hasScrollableChildren() && coordinatorLayout.getHeight() - view.getHeight() <= appBarLayout.getHeight();
        if (bl && this.mOffsetAnimator != null) {
            this.mOffsetAnimator.cancel();
        }
        this.mLastNestedScrollingChildRef = null;
        return bl;
    }

    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view, int n) {
        if (n == 0) {
            this.snapToChildIfNeeded(coordinatorLayout, appBarLayout);
        }
        this.mLastNestedScrollingChildRef = new WeakReference<View>(view);
    }

    public void setDragCallback(@Nullable DragCallback dragCallback) {
        this.mOnDragCallback = dragCallback;
    }

    @Override
    int setHeaderTopBottomOffset(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, int n, int n2, int n3) {
        int n4 = this.getTopBottomOffsetForScrollingSibling();
        if (n2 != 0 && n4 >= n2 && n4 <= n3) {
            if (n4 != (n2 = MathUtils.clamp(n, n2, n3))) {
                n = appBarLayout.hasChildWithInterpolator() ? this.interpolateOffset(appBarLayout, n2) : n2;
                boolean bl = this.setTopAndBottomOffset(n);
                this.mOffsetDelta = n2 - n;
                if (!bl && appBarLayout.hasChildWithInterpolator()) {
                    coordinatorLayout.dispatchDependentViewsChanged((View)appBarLayout);
                }
                appBarLayout.dispatchOffsetUpdates(this.getTopAndBottomOffset());
                n = n2 < n4 ? -1 : 1;
                this.updateAppBarLayoutDrawableState(coordinatorLayout, appBarLayout, n2, n, false);
                return n4 - n2;
            }
        } else {
            this.mOffsetDelta = 0;
        }
        return 0;
    }

    public static abstract class DragCallback {
        public abstract boolean canDrag(@NonNull AppBarLayout var1);
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
        boolean firstVisibleChildAtMinimumHeight;
        int firstVisibleChildIndex;
        float firstVisibleChildPercentageShown;

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.firstVisibleChildIndex = parcel.readInt();
            this.firstVisibleChildPercentageShown = parcel.readFloat();
            boolean bl = parcel.readByte() != 0;
            this.firstVisibleChildAtMinimumHeight = bl;
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            parcel.writeInt(this.firstVisibleChildIndex);
            parcel.writeFloat(this.firstVisibleChildPercentageShown);
            parcel.writeByte((byte)(this.firstVisibleChildAtMinimumHeight ? 1 : 0));
        }

    }

}
