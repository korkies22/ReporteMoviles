/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.animation.TimeInterpolator
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
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
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.animation.AnimationUtils
 *  android.view.animation.Interpolator
 *  android.widget.LinearLayout
 *  android.widget.LinearLayout$LayoutParams
 */
package android.support.design.widget;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.annotation.VisibleForTesting;
import android.support.design.R;
import android.support.design.widget.AnimationUtils;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.HeaderBehavior;
import android.support.design.widget.HeaderScrollingViewBehavior;
import android.support.design.widget.ThemeUtils;
import android.support.design.widget.ViewUtilsLollipop;
import android.support.v4.math.MathUtils;
import android.support.v4.util.ObjectsCompat;
import android.support.v4.view.AbsSavedState;
import android.support.v4.view.OnApplyWindowInsetsListener;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

@CoordinatorLayout.DefaultBehavior(value=Behavior.class)
public class AppBarLayout
extends LinearLayout {
    private static final int INVALID_SCROLL_RANGE = -1;
    static final int PENDING_ACTION_ANIMATE_ENABLED = 4;
    static final int PENDING_ACTION_COLLAPSED = 2;
    static final int PENDING_ACTION_EXPANDED = 1;
    static final int PENDING_ACTION_FORCE = 8;
    static final int PENDING_ACTION_NONE = 0;
    private boolean mCollapsed;
    private boolean mCollapsible;
    private int mDownPreScrollRange = -1;
    private int mDownScrollRange = -1;
    private boolean mHaveChildWithInterpolator;
    private WindowInsetsCompat mLastInsets;
    private List<OnOffsetChangedListener> mListeners;
    private int mPendingAction = 0;
    private int[] mTmpStatesArray;
    private int mTotalScrollRange = -1;

    public AppBarLayout(Context context) {
        this(context, null);
    }

    public AppBarLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.setOrientation(1);
        ThemeUtils.checkAppCompatTheme(context);
        if (Build.VERSION.SDK_INT >= 21) {
            ViewUtilsLollipop.setBoundsViewOutlineProvider((View)this);
            ViewUtilsLollipop.setStateListAnimatorFromAttrs((View)this, attributeSet, 0, R.style.Widget_Design_AppBarLayout);
        }
        context = context.obtainStyledAttributes(attributeSet, R.styleable.AppBarLayout, 0, R.style.Widget_Design_AppBarLayout);
        ViewCompat.setBackground((View)this, context.getDrawable(R.styleable.AppBarLayout_android_background));
        if (context.hasValue(R.styleable.AppBarLayout_expanded)) {
            this.setExpanded(context.getBoolean(R.styleable.AppBarLayout_expanded, false), false, false);
        }
        if (Build.VERSION.SDK_INT >= 21 && context.hasValue(R.styleable.AppBarLayout_elevation)) {
            ViewUtilsLollipop.setDefaultAppBarLayoutStateListAnimator((View)this, context.getDimensionPixelSize(R.styleable.AppBarLayout_elevation, 0));
        }
        if (Build.VERSION.SDK_INT >= 26) {
            if (context.hasValue(R.styleable.AppBarLayout_android_keyboardNavigationCluster)) {
                this.setKeyboardNavigationCluster(context.getBoolean(R.styleable.AppBarLayout_android_keyboardNavigationCluster, false));
            }
            if (context.hasValue(R.styleable.AppBarLayout_android_touchscreenBlocksFocus)) {
                this.setTouchscreenBlocksFocus(context.getBoolean(R.styleable.AppBarLayout_android_touchscreenBlocksFocus, false));
            }
        }
        context.recycle();
        ViewCompat.setOnApplyWindowInsetsListener((View)this, new OnApplyWindowInsetsListener(){

            @Override
            public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                return AppBarLayout.this.onWindowInsetChanged(windowInsetsCompat);
            }
        });
    }

    private void invalidateScrollRanges() {
        this.mTotalScrollRange = -1;
        this.mDownPreScrollRange = -1;
        this.mDownScrollRange = -1;
    }

    private boolean setCollapsibleState(boolean bl) {
        if (this.mCollapsible != bl) {
            this.mCollapsible = bl;
            this.refreshDrawableState();
            return true;
        }
        return false;
    }

    private void setExpanded(boolean bl, boolean bl2, boolean bl3) {
        int n = bl ? 1 : 2;
        int n2 = 0;
        int n3 = bl2 ? 4 : 0;
        if (bl3) {
            n2 = 8;
        }
        this.mPendingAction = n | n3 | n2;
        this.requestLayout();
    }

    private void updateCollapsible() {
        boolean bl;
        int n = this.getChildCount();
        boolean bl2 = false;
        int n2 = 0;
        do {
            bl = bl2;
            if (n2 >= n) break;
            if (((LayoutParams)this.getChildAt(n2).getLayoutParams()).isCollapsible()) {
                bl = true;
                break;
            }
            ++n2;
        } while (true);
        this.setCollapsibleState(bl);
    }

    public void addOnOffsetChangedListener(OnOffsetChangedListener onOffsetChangedListener) {
        if (this.mListeners == null) {
            this.mListeners = new ArrayList<OnOffsetChangedListener>();
        }
        if (onOffsetChangedListener != null && !this.mListeners.contains(onOffsetChangedListener)) {
            this.mListeners.add(onOffsetChangedListener);
        }
    }

    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    void dispatchOffsetUpdates(int n) {
        if (this.mListeners != null) {
            int n2 = this.mListeners.size();
            for (int i = 0; i < n2; ++i) {
                OnOffsetChangedListener onOffsetChangedListener = this.mListeners.get(i);
                if (onOffsetChangedListener == null) continue;
                onOffsetChangedListener.onOffsetChanged(this, n);
            }
        }
    }

    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -2);
    }

    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(this.getContext(), attributeSet);
    }

    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (Build.VERSION.SDK_INT >= 19 && layoutParams instanceof LinearLayout.LayoutParams) {
            return new LayoutParams((LinearLayout.LayoutParams)layoutParams);
        }
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            return new LayoutParams((ViewGroup.MarginLayoutParams)layoutParams);
        }
        return new LayoutParams(layoutParams);
    }

    int getDownNestedPreScrollRange() {
        int n;
        if (this.mDownPreScrollRange != -1) {
            return this.mDownPreScrollRange;
        }
        int n2 = 0;
        for (int i = this.getChildCount() - 1; i >= 0; --i) {
            View view = this.getChildAt(i);
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            n = view.getMeasuredHeight();
            int n3 = layoutParams.mScrollFlags;
            if ((n3 & 5) == 5) {
                n = (n3 & 8) != 0 ? n2 + ViewCompat.getMinimumHeight(view) : ((n3 & 2) != 0 ? n2 + (n - ViewCompat.getMinimumHeight(view)) : (n2 += layoutParams.topMargin + layoutParams.bottomMargin) + (n - this.getTopInset()));
            } else {
                n = n2;
                if (n2 > 0) break;
            }
            n2 = n;
        }
        this.mDownPreScrollRange = n = Math.max(0, n2);
        return n;
    }

    int getDownNestedScrollRange() {
        int n;
        int n2;
        if (this.mDownScrollRange != -1) {
            return this.mDownScrollRange;
        }
        int n3 = this.getChildCount();
        int n4 = n = 0;
        do {
            n2 = n4;
            if (n >= n3) break;
            View view = this.getChildAt(n);
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            int n5 = view.getMeasuredHeight();
            int n6 = layoutParams.topMargin;
            int n7 = layoutParams.bottomMargin;
            int n8 = layoutParams.mScrollFlags;
            n2 = n4;
            if ((n8 & 1) == 0) break;
            n4 += n5 + (n6 + n7);
            if ((n8 & 2) != 0) {
                n2 = n4 - (ViewCompat.getMinimumHeight(view) + this.getTopInset());
                break;
            }
            ++n;
        } while (true);
        this.mDownScrollRange = n4 = Math.max(0, n2);
        return n4;
    }

    final int getMinimumHeightForVisibleOverlappingContent() {
        int n = this.getTopInset();
        int n2 = ViewCompat.getMinimumHeight((View)this);
        if (n2 != 0) {
            return n2 * 2 + n;
        }
        n2 = this.getChildCount();
        n2 = n2 >= 1 ? ViewCompat.getMinimumHeight(this.getChildAt(n2 - 1)) : 0;
        if (n2 != 0) {
            return n2 * 2 + n;
        }
        return this.getHeight() / 3;
    }

    int getPendingAction() {
        return this.mPendingAction;
    }

    @Deprecated
    public float getTargetElevation() {
        return 0.0f;
    }

    @VisibleForTesting
    final int getTopInset() {
        if (this.mLastInsets != null) {
            return this.mLastInsets.getSystemWindowInsetTop();
        }
        return 0;
    }

    public final int getTotalScrollRange() {
        int n;
        int n2;
        if (this.mTotalScrollRange != -1) {
            return this.mTotalScrollRange;
        }
        int n3 = this.getChildCount();
        int n4 = n = 0;
        do {
            n2 = n4;
            if (n >= n3) break;
            View view = this.getChildAt(n);
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            int n5 = view.getMeasuredHeight();
            int n6 = layoutParams.mScrollFlags;
            n2 = n4;
            if ((n6 & 1) == 0) break;
            n4 += n5 + layoutParams.topMargin + layoutParams.bottomMargin;
            if ((n6 & 2) != 0) {
                n2 = n4 - ViewCompat.getMinimumHeight(view);
                break;
            }
            ++n;
        } while (true);
        this.mTotalScrollRange = n4 = Math.max(0, n2 - this.getTopInset());
        return n4;
    }

    int getUpNestedPreScrollRange() {
        return this.getTotalScrollRange();
    }

    boolean hasChildWithInterpolator() {
        return this.mHaveChildWithInterpolator;
    }

    boolean hasScrollableChildren() {
        if (this.getTotalScrollRange() != 0) {
            return true;
        }
        return false;
    }

    protected int[] onCreateDrawableState(int n) {
        if (this.mTmpStatesArray == null) {
            this.mTmpStatesArray = new int[2];
        }
        int[] arrn = this.mTmpStatesArray;
        int[] arrn2 = super.onCreateDrawableState(n + arrn.length);
        n = this.mCollapsible ? R.attr.state_collapsible : - R.attr.state_collapsible;
        arrn[0] = n;
        n = this.mCollapsible && this.mCollapsed ? R.attr.state_collapsed : - R.attr.state_collapsed;
        arrn[1] = n;
        return AppBarLayout.mergeDrawableStates((int[])arrn2, (int[])arrn);
    }

    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        super.onLayout(bl, n, n2, n3, n4);
        this.invalidateScrollRanges();
        this.mHaveChildWithInterpolator = false;
        n2 = this.getChildCount();
        for (n = 0; n < n2; ++n) {
            if (((LayoutParams)this.getChildAt(n).getLayoutParams()).getScrollInterpolator() == null) continue;
            this.mHaveChildWithInterpolator = true;
            break;
        }
        this.updateCollapsible();
    }

    protected void onMeasure(int n, int n2) {
        super.onMeasure(n, n2);
        this.invalidateScrollRanges();
    }

    WindowInsetsCompat onWindowInsetChanged(WindowInsetsCompat windowInsetsCompat) {
        WindowInsetsCompat windowInsetsCompat2 = ViewCompat.getFitsSystemWindows((View)this) ? windowInsetsCompat : null;
        if (!ObjectsCompat.equals(this.mLastInsets, windowInsetsCompat2)) {
            this.mLastInsets = windowInsetsCompat2;
            this.invalidateScrollRanges();
        }
        return windowInsetsCompat;
    }

    public void removeOnOffsetChangedListener(OnOffsetChangedListener onOffsetChangedListener) {
        if (this.mListeners != null && onOffsetChangedListener != null) {
            this.mListeners.remove(onOffsetChangedListener);
        }
    }

    void resetPendingAction() {
        this.mPendingAction = 0;
    }

    boolean setCollapsedState(boolean bl) {
        if (this.mCollapsed != bl) {
            this.mCollapsed = bl;
            this.refreshDrawableState();
            return true;
        }
        return false;
    }

    public void setExpanded(boolean bl) {
        this.setExpanded(bl, ViewCompat.isLaidOut((View)this));
    }

    public void setExpanded(boolean bl, boolean bl2) {
        this.setExpanded(bl, bl2, true);
    }

    public void setOrientation(int n) {
        if (n != 1) {
            throw new IllegalArgumentException("AppBarLayout is always vertical and does not support horizontal orientation");
        }
        super.setOrientation(n);
    }

    @Deprecated
    public void setTargetElevation(float f) {
        if (Build.VERSION.SDK_INT >= 21) {
            ViewUtilsLollipop.setDefaultAppBarLayoutStateListAnimator((View)this, f);
        }
    }

    public static class Behavior
    extends HeaderBehavior<AppBarLayout> {
        private static final int INVALID_POSITION = -1;
        private static final int MAX_OFFSET_ANIMATION_DURATION = 600;
        private WeakReference<View> mLastNestedScrollingChildRef;
        private ValueAnimator mOffsetAnimator;
        private int mOffsetDelta;
        private int mOffsetToChildIndexOnLayout = -1;
        private boolean mOffsetToChildIndexOnLayoutIsMinHeight;
        private float mOffsetToChildIndexOnLayoutPerc;
        private Behavior$DragCallback mOnDragCallback;

        public Behavior() {
        }

        public Behavior(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
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
                LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
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
                if (!(object2 instanceof ScrollingViewBehavior)) continue;
                if (((ScrollingViewBehavior)object2).getOverlayTop() != 0) {
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
            if (n3 >= 0 && ((n = ((LayoutParams)(view = appBarLayout.getChildAt(n3)).getLayoutParams()).getScrollFlags()) & 17) == 17) {
                int n4;
                int n5 = - view.getTop();
                int n6 = n4 = - view.getBottom();
                if (n3 == appBarLayout.getChildCount() - 1) {
                    n6 = n4 + appBarLayout.getTopInset();
                }
                if (Behavior.checkFlag(n, 2)) {
                    n4 = n6 + ViewCompat.getMinimumHeight(view);
                    n3 = n5;
                } else {
                    n3 = n5;
                    n4 = n6;
                    if (Behavior.checkFlag(n, 5)) {
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
                        int n3;
                        boolean bl3;
                        int n4;
                        block6 : {
                            view = Behavior.getAppBarChildOnOffset(appBarLayout, n);
                            if (view == null) break block4;
                            n4 = ((LayoutParams)view.getLayoutParams()).getScrollFlags();
                            bl2 = bl3 = false;
                            if ((n4 & 1) == 0) break block5;
                            n3 = ViewCompat.getMinimumHeight(view);
                            if (n2 <= 0 || (n4 & 12) == 0) break block6;
                            bl2 = bl3;
                            if (- n < view.getBottom() - n3 - appBarLayout.getTopInset()) break block5;
                            break block7;
                        }
                        bl2 = bl3;
                        if ((n4 & 2) == 0) break block5;
                        bl2 = bl3;
                        if (- n < view.getBottom() - n3 - appBarLayout.getTopInset()) break block5;
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
            if (parcelable instanceof Behavior$SavedState) {
                parcelable = (Behavior$SavedState)parcelable;
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
                parcelable = new Behavior$SavedState(parcelable);
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

        public void setDragCallback(@Nullable Behavior$DragCallback behavior$DragCallback) {
            this.mOnDragCallback = behavior$DragCallback;
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

    }

    public static abstract class Behavior$DragCallback {
        public abstract boolean canDrag(@NonNull AppBarLayout var1);
    }

    protected static class Behavior$SavedState
    extends AbsSavedState {
        public static final Parcelable.Creator<Behavior$SavedState> CREATOR = new Parcelable.ClassLoaderCreator<Behavior$SavedState>(){

            public Behavior$SavedState createFromParcel(Parcel parcel) {
                return new Behavior$SavedState(parcel, null);
            }

            public Behavior$SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new Behavior$SavedState(parcel, classLoader);
            }

            public Behavior$SavedState[] newArray(int n) {
                return new Behavior$SavedState[n];
            }
        };
        boolean firstVisibleChildAtMinimumHeight;
        int firstVisibleChildIndex;
        float firstVisibleChildPercentageShown;

        public Behavior$SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.firstVisibleChildIndex = parcel.readInt();
            this.firstVisibleChildPercentageShown = parcel.readFloat();
            boolean bl = parcel.readByte() != 0;
            this.firstVisibleChildAtMinimumHeight = bl;
        }

        public Behavior$SavedState(Parcelable parcelable) {
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

    public static class LayoutParams
    extends LinearLayout.LayoutParams {
        static final int COLLAPSIBLE_FLAGS = 10;
        static final int FLAG_QUICK_RETURN = 5;
        static final int FLAG_SNAP = 17;
        public static final int SCROLL_FLAG_ENTER_ALWAYS = 4;
        public static final int SCROLL_FLAG_ENTER_ALWAYS_COLLAPSED = 8;
        public static final int SCROLL_FLAG_EXIT_UNTIL_COLLAPSED = 2;
        public static final int SCROLL_FLAG_SCROLL = 1;
        public static final int SCROLL_FLAG_SNAP = 16;
        int mScrollFlags = 1;
        Interpolator mScrollInterpolator;

        public LayoutParams(int n, int n2) {
            super(n, n2);
        }

        public LayoutParams(int n, int n2, float f) {
            super(n, n2, f);
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            attributeSet = context.obtainStyledAttributes(attributeSet, R.styleable.AppBarLayout_Layout);
            this.mScrollFlags = attributeSet.getInt(R.styleable.AppBarLayout_Layout_layout_scrollFlags, 0);
            if (attributeSet.hasValue(R.styleable.AppBarLayout_Layout_layout_scrollInterpolator)) {
                this.mScrollInterpolator = android.view.animation.AnimationUtils.loadInterpolator((Context)context, (int)attributeSet.getResourceId(R.styleable.AppBarLayout_Layout_layout_scrollInterpolator, 0));
            }
            attributeSet.recycle();
        }

        @RequiresApi(value=19)
        public LayoutParams(LayoutParams layoutParams) {
            super((LinearLayout.LayoutParams)layoutParams);
            this.mScrollFlags = layoutParams.mScrollFlags;
            this.mScrollInterpolator = layoutParams.mScrollInterpolator;
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        @RequiresApi(value=19)
        public LayoutParams(LinearLayout.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public int getScrollFlags() {
            return this.mScrollFlags;
        }

        public Interpolator getScrollInterpolator() {
            return this.mScrollInterpolator;
        }

        boolean isCollapsible() {
            if ((this.mScrollFlags & 1) == 1 && (this.mScrollFlags & 10) != 0) {
                return true;
            }
            return false;
        }

        public void setScrollFlags(int n) {
            this.mScrollFlags = n;
        }

        public void setScrollInterpolator(Interpolator interpolator) {
            this.mScrollInterpolator = interpolator;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public static @interface LayoutParams$ScrollFlags {
    }

    public static interface OnOffsetChangedListener {
        public void onOffsetChanged(AppBarLayout var1, int var2);
    }

    public static class ScrollingViewBehavior
    extends HeaderScrollingViewBehavior {
        public ScrollingViewBehavior() {
        }

        public ScrollingViewBehavior(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            context = context.obtainStyledAttributes(attributeSet, R.styleable.ScrollingViewBehavior_Layout);
            this.setOverlayTop(context.getDimensionPixelSize(R.styleable.ScrollingViewBehavior_Layout_behavior_overlapTop, 0));
            context.recycle();
        }

        private static int getAppBarLayoutOffset(AppBarLayout object) {
            if ((object = ((CoordinatorLayout.LayoutParams)object.getLayoutParams()).getBehavior()) instanceof Behavior) {
                return ((Behavior)object).getTopBottomOffsetForScrollingSibling();
            }
            return 0;
        }

        private void offsetChildAsNeeded(CoordinatorLayout object, View view, View view2) {
            object = ((CoordinatorLayout.LayoutParams)view2.getLayoutParams()).getBehavior();
            if (object instanceof Behavior) {
                object = (Behavior)object;
                ViewCompat.offsetTopAndBottom(view, view2.getBottom() - view.getTop() + ((Behavior)object).mOffsetDelta + this.getVerticalLayoutGap() - this.getOverlapPixelsForOffset(view2));
            }
        }

        AppBarLayout findFirstDependency(List<View> list) {
            int n = list.size();
            for (int i = 0; i < n; ++i) {
                View view = list.get(i);
                if (!(view instanceof AppBarLayout)) continue;
                return (AppBarLayout)view;
            }
            return null;
        }

        @Override
        float getOverlapRatioForOffset(View object) {
            if (object instanceof AppBarLayout) {
                object = (AppBarLayout)((Object)object);
                int n = object.getTotalScrollRange();
                int n2 = object.getDownNestedPreScrollRange();
                int n3 = ScrollingViewBehavior.getAppBarLayoutOffset((AppBarLayout)((Object)object));
                if (n2 != 0 && n + n3 <= n2) {
                    return 0.0f;
                }
                if ((n -= n2) != 0) {
                    return 1.0f + (float)n3 / (float)n;
                }
            }
            return 0.0f;
        }

        @Override
        int getScrollRange(View view) {
            if (view instanceof AppBarLayout) {
                return ((AppBarLayout)view).getTotalScrollRange();
            }
            return super.getScrollRange(view);
        }

        @Override
        public boolean layoutDependsOn(CoordinatorLayout coordinatorLayout, View view, View view2) {
            return view2 instanceof AppBarLayout;
        }

        @Override
        public boolean onDependentViewChanged(CoordinatorLayout coordinatorLayout, View view, View view2) {
            this.offsetChildAsNeeded(coordinatorLayout, view, view2);
            return false;
        }

        @Override
        public boolean onRequestChildRectangleOnScreen(CoordinatorLayout coordinatorLayout, View view, Rect rect, boolean bl) {
            AppBarLayout appBarLayout = this.findFirstDependency(coordinatorLayout.getDependencies(view));
            if (appBarLayout != null) {
                rect.offset(view.getLeft(), view.getTop());
                view = this.mTempRect1;
                view.set(0, 0, coordinatorLayout.getWidth(), coordinatorLayout.getHeight());
                if (!view.contains(rect)) {
                    appBarLayout.setExpanded(false, bl ^ true);
                    return true;
                }
            }
            return false;
        }
    }

}
