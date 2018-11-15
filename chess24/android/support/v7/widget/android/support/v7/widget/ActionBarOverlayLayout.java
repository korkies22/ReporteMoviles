/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.content.Context
 *  android.content.pm.ApplicationInfo
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.content.res.TypedArray
 *  android.graphics.Canvas
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.IBinder
 *  android.os.Parcelable
 *  android.util.AttributeSet
 *  android.util.SparseArray
 *  android.view.Menu
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.ViewPropertyAnimator
 *  android.view.Window
 *  android.view.Window$Callback
 *  android.widget.OverScroller
 */
package android.support.v7.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.annotation.RestrictTo;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.R;
import android.support.v7.view.menu.MenuPresenter;
import android.support.v7.widget.ActionBarContainer;
import android.support.v7.widget.ContentFrameLayout;
import android.support.v7.widget.DecorContentParent;
import android.support.v7.widget.DecorToolbar;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.ViewUtils;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.Window;
import android.widget.OverScroller;

@RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
public class ActionBarOverlayLayout
extends ViewGroup
implements DecorContentParent,
NestedScrollingParent {
    static final int[] ATTRS = new int[]{R.attr.actionBarSize, 16842841};
    private static final String TAG = "ActionBarOverlayLayout";
    private final int ACTION_BAR_ANIMATE_DELAY = 600;
    private int mActionBarHeight;
    ActionBarContainer mActionBarTop;
    private ActionBarVisibilityCallback mActionBarVisibilityCallback;
    private final Runnable mAddActionBarHideOffset = new Runnable(){

        @Override
        public void run() {
            ActionBarOverlayLayout.this.haltActionBarHideOffsetAnimations();
            ActionBarOverlayLayout.this.mCurrentActionBarTopAnimator = ActionBarOverlayLayout.this.mActionBarTop.animate().translationY((float)(- ActionBarOverlayLayout.this.mActionBarTop.getHeight())).setListener((Animator.AnimatorListener)ActionBarOverlayLayout.this.mTopAnimatorListener);
        }
    };
    boolean mAnimatingForFling;
    private final Rect mBaseContentInsets = new Rect();
    private final Rect mBaseInnerInsets = new Rect();
    private ContentFrameLayout mContent;
    private final Rect mContentInsets = new Rect();
    ViewPropertyAnimator mCurrentActionBarTopAnimator;
    private DecorToolbar mDecorToolbar;
    private OverScroller mFlingEstimator;
    private boolean mHasNonEmbeddedTabs;
    private boolean mHideOnContentScroll;
    private int mHideOnContentScrollReference;
    private boolean mIgnoreWindowContentOverlay;
    private final Rect mInnerInsets = new Rect();
    private final Rect mLastBaseContentInsets = new Rect();
    private final Rect mLastBaseInnerInsets = new Rect();
    private final Rect mLastInnerInsets = new Rect();
    private int mLastSystemUiVisibility;
    private boolean mOverlayMode;
    private final NestedScrollingParentHelper mParentHelper;
    private final Runnable mRemoveActionBarHideOffset = new Runnable(){

        @Override
        public void run() {
            ActionBarOverlayLayout.this.haltActionBarHideOffsetAnimations();
            ActionBarOverlayLayout.this.mCurrentActionBarTopAnimator = ActionBarOverlayLayout.this.mActionBarTop.animate().translationY(0.0f).setListener((Animator.AnimatorListener)ActionBarOverlayLayout.this.mTopAnimatorListener);
        }
    };
    final AnimatorListenerAdapter mTopAnimatorListener = new AnimatorListenerAdapter(){

        public void onAnimationCancel(Animator animator) {
            ActionBarOverlayLayout.this.mCurrentActionBarTopAnimator = null;
            ActionBarOverlayLayout.this.mAnimatingForFling = false;
        }

        public void onAnimationEnd(Animator animator) {
            ActionBarOverlayLayout.this.mCurrentActionBarTopAnimator = null;
            ActionBarOverlayLayout.this.mAnimatingForFling = false;
        }
    };
    private Drawable mWindowContentOverlay;
    private int mWindowVisibility = 0;

    public ActionBarOverlayLayout(Context context) {
        this(context, null);
    }

    public ActionBarOverlayLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init(context);
        this.mParentHelper = new NestedScrollingParentHelper(this);
    }

    private void addActionBarHideOffset() {
        this.haltActionBarHideOffsetAnimations();
        this.mAddActionBarHideOffset.run();
    }

    private boolean applyInsets(View object, Rect rect, boolean bl, boolean bl2, boolean bl3, boolean bl4) {
        boolean bl5;
        object = (LayoutParams)object.getLayoutParams();
        if (bl && object.leftMargin != rect.left) {
            object.leftMargin = rect.left;
            bl5 = true;
        } else {
            bl5 = false;
        }
        bl = bl5;
        if (bl2) {
            bl = bl5;
            if (object.topMargin != rect.top) {
                object.topMargin = rect.top;
                bl = true;
            }
        }
        bl2 = bl;
        if (bl4) {
            bl2 = bl;
            if (object.rightMargin != rect.right) {
                object.rightMargin = rect.right;
                bl2 = true;
            }
        }
        bl = bl2;
        if (bl3) {
            bl = bl2;
            if (object.bottomMargin != rect.bottom) {
                object.bottomMargin = rect.bottom;
                bl = true;
            }
        }
        return bl;
    }

    private DecorToolbar getDecorToolbar(View view) {
        if (view instanceof DecorToolbar) {
            return (DecorToolbar)view;
        }
        if (view instanceof Toolbar) {
            return ((Toolbar)view).getWrapper();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Can't make a decor toolbar out of ");
        stringBuilder.append(view.getClass().getSimpleName());
        throw new IllegalStateException(stringBuilder.toString());
    }

    private void init(Context context) {
        TypedArray typedArray = this.getContext().getTheme().obtainStyledAttributes(ATTRS);
        boolean bl = false;
        this.mActionBarHeight = typedArray.getDimensionPixelSize(0, 0);
        this.mWindowContentOverlay = typedArray.getDrawable(1);
        boolean bl2 = this.mWindowContentOverlay == null;
        this.setWillNotDraw(bl2);
        typedArray.recycle();
        bl2 = bl;
        if (context.getApplicationInfo().targetSdkVersion < 19) {
            bl2 = true;
        }
        this.mIgnoreWindowContentOverlay = bl2;
        this.mFlingEstimator = new OverScroller(context);
    }

    private void postAddActionBarHideOffset() {
        this.haltActionBarHideOffsetAnimations();
        this.postDelayed(this.mAddActionBarHideOffset, 600L);
    }

    private void postRemoveActionBarHideOffset() {
        this.haltActionBarHideOffsetAnimations();
        this.postDelayed(this.mRemoveActionBarHideOffset, 600L);
    }

    private void removeActionBarHideOffset() {
        this.haltActionBarHideOffsetAnimations();
        this.mRemoveActionBarHideOffset.run();
    }

    private boolean shouldHideActionBarOnFling(float f, float f2) {
        this.mFlingEstimator.fling(0, 0, 0, (int)f2, 0, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        if (this.mFlingEstimator.getFinalY() > this.mActionBarTop.getHeight()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean canShowOverflowMenu() {
        this.pullChildren();
        return this.mDecorToolbar.canShowOverflowMenu();
    }

    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    @Override
    public void dismissPopups() {
        this.pullChildren();
        this.mDecorToolbar.dismissPopupMenus();
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (this.mWindowContentOverlay != null && !this.mIgnoreWindowContentOverlay) {
            int n = this.mActionBarTop.getVisibility() == 0 ? (int)((float)this.mActionBarTop.getBottom() + this.mActionBarTop.getTranslationY() + 0.5f) : 0;
            this.mWindowContentOverlay.setBounds(0, n, this.getWidth(), this.mWindowContentOverlay.getIntrinsicHeight() + n);
            this.mWindowContentOverlay.draw(canvas);
        }
    }

    protected boolean fitSystemWindows(Rect rect) {
        this.pullChildren();
        ViewCompat.getWindowSystemUiVisibility((View)this);
        boolean bl = this.applyInsets((View)this.mActionBarTop, rect, true, true, false, true);
        this.mBaseInnerInsets.set(rect);
        ViewUtils.computeFitSystemWindows((View)this, this.mBaseInnerInsets, this.mBaseContentInsets);
        if (!this.mLastBaseInnerInsets.equals((Object)this.mBaseInnerInsets)) {
            this.mLastBaseInnerInsets.set(this.mBaseInnerInsets);
            bl = true;
        }
        if (!this.mLastBaseContentInsets.equals((Object)this.mBaseContentInsets)) {
            this.mLastBaseContentInsets.set(this.mBaseContentInsets);
            bl = true;
        }
        if (bl) {
            this.requestLayout();
        }
        return true;
    }

    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -1);
    }

    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(this.getContext(), attributeSet);
    }

    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new LayoutParams(layoutParams);
    }

    public int getActionBarHideOffset() {
        if (this.mActionBarTop != null) {
            return - (int)this.mActionBarTop.getTranslationY();
        }
        return 0;
    }

    @Override
    public int getNestedScrollAxes() {
        return this.mParentHelper.getNestedScrollAxes();
    }

    @Override
    public CharSequence getTitle() {
        this.pullChildren();
        return this.mDecorToolbar.getTitle();
    }

    void haltActionBarHideOffsetAnimations() {
        this.removeCallbacks(this.mRemoveActionBarHideOffset);
        this.removeCallbacks(this.mAddActionBarHideOffset);
        if (this.mCurrentActionBarTopAnimator != null) {
            this.mCurrentActionBarTopAnimator.cancel();
        }
    }

    @Override
    public boolean hasIcon() {
        this.pullChildren();
        return this.mDecorToolbar.hasIcon();
    }

    @Override
    public boolean hasLogo() {
        this.pullChildren();
        return this.mDecorToolbar.hasLogo();
    }

    @Override
    public boolean hideOverflowMenu() {
        this.pullChildren();
        return this.mDecorToolbar.hideOverflowMenu();
    }

    @Override
    public void initFeature(int n) {
        this.pullChildren();
        if (n != 2) {
            if (n != 5) {
                if (n != 109) {
                    return;
                }
                this.setOverlayMode(true);
                return;
            }
            this.mDecorToolbar.initIndeterminateProgress();
            return;
        }
        this.mDecorToolbar.initProgress();
    }

    public boolean isHideOnContentScrollEnabled() {
        return this.mHideOnContentScroll;
    }

    public boolean isInOverlayMode() {
        return this.mOverlayMode;
    }

    @Override
    public boolean isOverflowMenuShowPending() {
        this.pullChildren();
        return this.mDecorToolbar.isOverflowMenuShowPending();
    }

    @Override
    public boolean isOverflowMenuShowing() {
        this.pullChildren();
        return this.mDecorToolbar.isOverflowMenuShowing();
    }

    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.init(this.getContext());
        ViewCompat.requestApplyInsets((View)this);
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.haltActionBarHideOffsetAnimations();
    }

    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        n2 = this.getChildCount();
        n3 = this.getPaddingLeft();
        this.getPaddingRight();
        n4 = this.getPaddingTop();
        this.getPaddingBottom();
        for (n = 0; n < n2; ++n) {
            View view = this.getChildAt(n);
            if (view.getVisibility() == 8) continue;
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            int n5 = view.getMeasuredWidth();
            int n6 = view.getMeasuredHeight();
            int n7 = layoutParams.leftMargin + n3;
            int n8 = layoutParams.topMargin + n4;
            view.layout(n7, n8, n5 + n7, n6 + n8);
        }
    }

    protected void onMeasure(int n, int n2) {
        int n3;
        int n4;
        this.pullChildren();
        this.measureChildWithMargins((View)this.mActionBarTop, n, 0, n2, 0);
        LayoutParams layoutParams = (LayoutParams)this.mActionBarTop.getLayoutParams();
        int n5 = Math.max(0, this.mActionBarTop.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin);
        int n6 = Math.max(0, this.mActionBarTop.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin);
        int n7 = View.combineMeasuredStates((int)0, (int)this.mActionBarTop.getMeasuredState());
        int n8 = (ViewCompat.getWindowSystemUiVisibility((View)this) & 256) != 0 ? 1 : 0;
        if (n8 != 0) {
            n3 = n4 = this.mActionBarHeight;
            if (this.mHasNonEmbeddedTabs) {
                n3 = n4;
                if (this.mActionBarTop.getTabContainer() != null) {
                    n3 = n4 + this.mActionBarHeight;
                }
            }
        } else {
            n3 = this.mActionBarTop.getVisibility() != 8 ? this.mActionBarTop.getMeasuredHeight() : 0;
        }
        this.mContentInsets.set(this.mBaseContentInsets);
        this.mInnerInsets.set(this.mBaseInnerInsets);
        if (!this.mOverlayMode && n8 == 0) {
            layoutParams = this.mContentInsets;
            layoutParams.top += n3;
            layoutParams = this.mContentInsets;
            layoutParams.bottom += 0;
        } else {
            layoutParams = this.mInnerInsets;
            layoutParams.top += n3;
            layoutParams = this.mInnerInsets;
            layoutParams.bottom += 0;
        }
        this.applyInsets((View)this.mContent, this.mContentInsets, true, true, true, true);
        if (!this.mLastInnerInsets.equals((Object)this.mInnerInsets)) {
            this.mLastInnerInsets.set(this.mInnerInsets);
            this.mContent.dispatchFitSystemWindows(this.mInnerInsets);
        }
        this.measureChildWithMargins((View)this.mContent, n, 0, n2, 0);
        layoutParams = (LayoutParams)this.mContent.getLayoutParams();
        n3 = Math.max(n5, this.mContent.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin);
        n8 = Math.max(n6, this.mContent.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin);
        n4 = View.combineMeasuredStates((int)n7, (int)this.mContent.getMeasuredState());
        n7 = this.getPaddingLeft();
        n6 = this.getPaddingRight();
        n8 = Math.max(n8 + (this.getPaddingTop() + this.getPaddingBottom()), this.getSuggestedMinimumHeight());
        this.setMeasuredDimension(View.resolveSizeAndState((int)Math.max(n3 + (n7 + n6), this.getSuggestedMinimumWidth()), (int)n, (int)n4), View.resolveSizeAndState((int)n8, (int)n2, (int)(n4 << 16)));
    }

    @Override
    public boolean onNestedFling(View view, float f, float f2, boolean bl) {
        if (this.mHideOnContentScroll && bl) {
            if (this.shouldHideActionBarOnFling(f, f2)) {
                this.addActionBarHideOffset();
            } else {
                this.removeActionBarHideOffset();
            }
            this.mAnimatingForFling = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean onNestedPreFling(View view, float f, float f2) {
        return false;
    }

    @Override
    public void onNestedPreScroll(View view, int n, int n2, int[] arrn) {
    }

    @Override
    public void onNestedScroll(View view, int n, int n2, int n3, int n4) {
        this.mHideOnContentScrollReference += n2;
        this.setActionBarHideOffset(this.mHideOnContentScrollReference);
    }

    @Override
    public void onNestedScrollAccepted(View view, View view2, int n) {
        this.mParentHelper.onNestedScrollAccepted(view, view2, n);
        this.mHideOnContentScrollReference = this.getActionBarHideOffset();
        this.haltActionBarHideOffsetAnimations();
        if (this.mActionBarVisibilityCallback != null) {
            this.mActionBarVisibilityCallback.onContentScrollStarted();
        }
    }

    @Override
    public boolean onStartNestedScroll(View view, View view2, int n) {
        if ((n & 2) != 0 && this.mActionBarTop.getVisibility() == 0) {
            return this.mHideOnContentScroll;
        }
        return false;
    }

    @Override
    public void onStopNestedScroll(View view) {
        if (this.mHideOnContentScroll && !this.mAnimatingForFling) {
            if (this.mHideOnContentScrollReference <= this.mActionBarTop.getHeight()) {
                this.postRemoveActionBarHideOffset();
            } else {
                this.postAddActionBarHideOffset();
            }
        }
        if (this.mActionBarVisibilityCallback != null) {
            this.mActionBarVisibilityCallback.onContentScrollStopped();
        }
    }

    public void onWindowSystemUiVisibilityChanged(int n) {
        if (Build.VERSION.SDK_INT >= 16) {
            super.onWindowSystemUiVisibilityChanged(n);
        }
        this.pullChildren();
        int n2 = this.mLastSystemUiVisibility;
        this.mLastSystemUiVisibility = n;
        boolean bl = false;
        boolean bl2 = (n & 4) == 0;
        if ((n & 256) != 0) {
            bl = true;
        }
        if (this.mActionBarVisibilityCallback != null) {
            this.mActionBarVisibilityCallback.enableContentAnimations(bl ^ true);
            if (!bl2 && bl) {
                this.mActionBarVisibilityCallback.hideForSystem();
            } else {
                this.mActionBarVisibilityCallback.showForSystem();
            }
        }
        if (((n2 ^ n) & 256) != 0 && this.mActionBarVisibilityCallback != null) {
            ViewCompat.requestApplyInsets((View)this);
        }
    }

    protected void onWindowVisibilityChanged(int n) {
        super.onWindowVisibilityChanged(n);
        this.mWindowVisibility = n;
        if (this.mActionBarVisibilityCallback != null) {
            this.mActionBarVisibilityCallback.onWindowVisibilityChanged(n);
        }
    }

    void pullChildren() {
        if (this.mContent == null) {
            this.mContent = (ContentFrameLayout)this.findViewById(R.id.action_bar_activity_content);
            this.mActionBarTop = (ActionBarContainer)this.findViewById(R.id.action_bar_container);
            this.mDecorToolbar = this.getDecorToolbar(this.findViewById(R.id.action_bar));
        }
    }

    @Override
    public void restoreToolbarHierarchyState(SparseArray<Parcelable> sparseArray) {
        this.pullChildren();
        this.mDecorToolbar.restoreHierarchyState(sparseArray);
    }

    @Override
    public void saveToolbarHierarchyState(SparseArray<Parcelable> sparseArray) {
        this.pullChildren();
        this.mDecorToolbar.saveHierarchyState(sparseArray);
    }

    public void setActionBarHideOffset(int n) {
        this.haltActionBarHideOffsetAnimations();
        n = Math.max(0, Math.min(n, this.mActionBarTop.getHeight()));
        this.mActionBarTop.setTranslationY((float)(- n));
    }

    public void setActionBarVisibilityCallback(ActionBarVisibilityCallback actionBarVisibilityCallback) {
        this.mActionBarVisibilityCallback = actionBarVisibilityCallback;
        if (this.getWindowToken() != null) {
            this.mActionBarVisibilityCallback.onWindowVisibilityChanged(this.mWindowVisibility);
            if (this.mLastSystemUiVisibility != 0) {
                this.onWindowSystemUiVisibilityChanged(this.mLastSystemUiVisibility);
                ViewCompat.requestApplyInsets((View)this);
            }
        }
    }

    public void setHasNonEmbeddedTabs(boolean bl) {
        this.mHasNonEmbeddedTabs = bl;
    }

    public void setHideOnContentScrollEnabled(boolean bl) {
        if (bl != this.mHideOnContentScroll) {
            this.mHideOnContentScroll = bl;
            if (!bl) {
                this.haltActionBarHideOffsetAnimations();
                this.setActionBarHideOffset(0);
            }
        }
    }

    @Override
    public void setIcon(int n) {
        this.pullChildren();
        this.mDecorToolbar.setIcon(n);
    }

    @Override
    public void setIcon(Drawable drawable) {
        this.pullChildren();
        this.mDecorToolbar.setIcon(drawable);
    }

    @Override
    public void setLogo(int n) {
        this.pullChildren();
        this.mDecorToolbar.setLogo(n);
    }

    @Override
    public void setMenu(Menu menu, MenuPresenter.Callback callback) {
        this.pullChildren();
        this.mDecorToolbar.setMenu(menu, callback);
    }

    @Override
    public void setMenuPrepared() {
        this.pullChildren();
        this.mDecorToolbar.setMenuPrepared();
    }

    public void setOverlayMode(boolean bl) {
        this.mOverlayMode = bl;
        bl = bl && this.getContext().getApplicationInfo().targetSdkVersion < 19;
        this.mIgnoreWindowContentOverlay = bl;
    }

    public void setShowingForActionMode(boolean bl) {
    }

    @Override
    public void setUiOptions(int n) {
    }

    @Override
    public void setWindowCallback(Window.Callback callback) {
        this.pullChildren();
        this.mDecorToolbar.setWindowCallback(callback);
    }

    @Override
    public void setWindowTitle(CharSequence charSequence) {
        this.pullChildren();
        this.mDecorToolbar.setWindowTitle(charSequence);
    }

    public boolean shouldDelayChildPressedState() {
        return false;
    }

    @Override
    public boolean showOverflowMenu() {
        this.pullChildren();
        return this.mDecorToolbar.showOverflowMenu();
    }

    public static interface ActionBarVisibilityCallback {
        public void enableContentAnimations(boolean var1);

        public void hideForSystem();

        public void onContentScrollStarted();

        public void onContentScrollStopped();

        public void onWindowVisibilityChanged(int var1);

        public void showForSystem();
    }

    public static class LayoutParams
    extends ViewGroup.MarginLayoutParams {
        public LayoutParams(int n, int n2) {
            super(n, n2);
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }
    }

}
