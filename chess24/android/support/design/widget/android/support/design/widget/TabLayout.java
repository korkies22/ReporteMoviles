/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.TimeInterpolator
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.database.DataSetObserver
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.IBinder
 *  android.text.Layout
 *  android.text.TextPaint
 *  android.text.TextUtils
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.ViewParent
 *  android.view.accessibility.AccessibilityEvent
 *  android.view.accessibility.AccessibilityNodeInfo
 *  android.view.animation.Interpolator
 *  android.widget.FrameLayout
 *  android.widget.FrameLayout$LayoutParams
 *  android.widget.HorizontalScrollView
 *  android.widget.ImageView
 *  android.widget.LinearLayout
 *  android.widget.LinearLayout$LayoutParams
 *  android.widget.TextView
 */
package android.support.design.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.StringRes;
import android.support.design.R;
import android.support.design.widget.AnimationUtils;
import android.support.design.widget.TabItem;
import android.support.design.widget.ThemeUtils;
import android.support.v4.util.Pools;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PointerIconCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.appcompat.R;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.TooltipCompat;
import android.text.Layout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;

@ViewPager.DecorView
public class TabLayout
extends HorizontalScrollView {
    private static final int ANIMATION_DURATION = 300;
    static final int DEFAULT_GAP_TEXT_ICON = 8;
    private static final int DEFAULT_HEIGHT = 48;
    private static final int DEFAULT_HEIGHT_WITH_TEXT_ICON = 72;
    static final int FIXED_WRAP_GUTTER_MIN = 16;
    public static final int GRAVITY_CENTER = 1;
    public static final int GRAVITY_FILL = 0;
    private static final int INVALID_WIDTH = -1;
    public static final int MODE_FIXED = 1;
    public static final int MODE_SCROLLABLE = 0;
    static final int MOTION_NON_ADJACENT_OFFSET = 24;
    private static final int TAB_MIN_WIDTH_MARGIN = 56;
    private static final Pools.Pool<Tab> sTabPool = new Pools.SynchronizedPool<Tab>(16);
    private AdapterChangeListener mAdapterChangeListener;
    private int mContentInsetStart;
    private OnTabSelectedListener mCurrentVpSelectedListener;
    int mMode;
    private TabLayoutOnPageChangeListener mPageChangeListener;
    private PagerAdapter mPagerAdapter;
    private DataSetObserver mPagerAdapterObserver;
    private final int mRequestedTabMaxWidth;
    private final int mRequestedTabMinWidth;
    private ValueAnimator mScrollAnimator;
    private final int mScrollableTabMinWidth;
    private OnTabSelectedListener mSelectedListener;
    private final ArrayList<OnTabSelectedListener> mSelectedListeners;
    private Tab mSelectedTab;
    private boolean mSetupViewPagerImplicitly;
    final int mTabBackgroundResId;
    int mTabGravity;
    int mTabMaxWidth;
    int mTabPaddingBottom;
    int mTabPaddingEnd;
    int mTabPaddingStart;
    int mTabPaddingTop;
    private final SlidingTabStrip mTabStrip;
    int mTabTextAppearance;
    ColorStateList mTabTextColors;
    float mTabTextMultiLineSize;
    float mTabTextSize;
    private final Pools.Pool<TabView> mTabViewPool;
    private final ArrayList<Tab> mTabs;
    ViewPager mViewPager;

    public TabLayout(Context context) {
        this(context, null);
    }

    public TabLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public TabLayout(Context context, AttributeSet attributeSet, int n) {
        block4 : {
            super(context, attributeSet, n);
            this.mTabs = new ArrayList();
            this.mTabMaxWidth = Integer.MAX_VALUE;
            this.mSelectedListeners = new ArrayList();
            this.mTabViewPool = new Pools.SimplePool<TabView>(12);
            ThemeUtils.checkAppCompatTheme(context);
            this.setHorizontalScrollBarEnabled(false);
            this.mTabStrip = new SlidingTabStrip(context);
            super.addView((View)this.mTabStrip, 0, (ViewGroup.LayoutParams)new FrameLayout.LayoutParams(-2, -1));
            attributeSet = context.obtainStyledAttributes(attributeSet, R.styleable.TabLayout, n, R.style.Widget_Design_TabLayout);
            this.mTabStrip.setSelectedIndicatorHeight(attributeSet.getDimensionPixelSize(R.styleable.TabLayout_tabIndicatorHeight, 0));
            this.mTabStrip.setSelectedIndicatorColor(attributeSet.getColor(R.styleable.TabLayout_tabIndicatorColor, 0));
            this.mTabPaddingBottom = n = attributeSet.getDimensionPixelSize(R.styleable.TabLayout_tabPadding, 0);
            this.mTabPaddingEnd = n;
            this.mTabPaddingTop = n;
            this.mTabPaddingStart = n;
            this.mTabPaddingStart = attributeSet.getDimensionPixelSize(R.styleable.TabLayout_tabPaddingStart, this.mTabPaddingStart);
            this.mTabPaddingTop = attributeSet.getDimensionPixelSize(R.styleable.TabLayout_tabPaddingTop, this.mTabPaddingTop);
            this.mTabPaddingEnd = attributeSet.getDimensionPixelSize(R.styleable.TabLayout_tabPaddingEnd, this.mTabPaddingEnd);
            this.mTabPaddingBottom = attributeSet.getDimensionPixelSize(R.styleable.TabLayout_tabPaddingBottom, this.mTabPaddingBottom);
            this.mTabTextAppearance = attributeSet.getResourceId(R.styleable.TabLayout_tabTextAppearance, R.style.TextAppearance_Design_Tab);
            context = context.obtainStyledAttributes(this.mTabTextAppearance, R.styleable.TextAppearance);
            this.mTabTextSize = context.getDimensionPixelSize(R.styleable.TextAppearance_android_textSize, 0);
            this.mTabTextColors = context.getColorStateList(R.styleable.TextAppearance_android_textColor);
            if (!attributeSet.hasValue(R.styleable.TabLayout_tabTextColor)) break block4;
            this.mTabTextColors = attributeSet.getColorStateList(R.styleable.TabLayout_tabTextColor);
        }
        if (attributeSet.hasValue(R.styleable.TabLayout_tabSelectedTextColor)) {
            n = attributeSet.getColor(R.styleable.TabLayout_tabSelectedTextColor, 0);
            this.mTabTextColors = TabLayout.createColorStateList(this.mTabTextColors.getDefaultColor(), n);
        }
        this.mRequestedTabMinWidth = attributeSet.getDimensionPixelSize(R.styleable.TabLayout_tabMinWidth, -1);
        this.mRequestedTabMaxWidth = attributeSet.getDimensionPixelSize(R.styleable.TabLayout_tabMaxWidth, -1);
        this.mTabBackgroundResId = attributeSet.getResourceId(R.styleable.TabLayout_tabBackground, 0);
        this.mContentInsetStart = attributeSet.getDimensionPixelSize(R.styleable.TabLayout_tabContentStart, 0);
        this.mMode = attributeSet.getInt(R.styleable.TabLayout_tabMode, 1);
        this.mTabGravity = attributeSet.getInt(R.styleable.TabLayout_tabGravity, 0);
        attributeSet.recycle();
        context = this.getResources();
        this.mTabTextMultiLineSize = context.getDimensionPixelSize(R.dimen.design_tab_text_size_2line);
        this.mScrollableTabMinWidth = context.getDimensionPixelSize(R.dimen.design_tab_scrollable_min_width);
        this.applyModeAndGravity();
        return;
        finally {
            context.recycle();
        }
    }

    private void addTabFromItemView(@NonNull TabItem tabItem) {
        Tab tab = this.newTab();
        if (tabItem.mText != null) {
            tab.setText(tabItem.mText);
        }
        if (tabItem.mIcon != null) {
            tab.setIcon(tabItem.mIcon);
        }
        if (tabItem.mCustomLayout != 0) {
            tab.setCustomView(tabItem.mCustomLayout);
        }
        if (!TextUtils.isEmpty((CharSequence)tabItem.getContentDescription())) {
            tab.setContentDescription(tabItem.getContentDescription());
        }
        this.addTab(tab);
    }

    private void addTabView(Tab tab) {
        TabView tabView = tab.mView;
        this.mTabStrip.addView((View)tabView, tab.getPosition(), (ViewGroup.LayoutParams)this.createLayoutParamsForTabs());
    }

    private void addViewInternal(View view) {
        if (view instanceof TabItem) {
            this.addTabFromItemView((TabItem)view);
            return;
        }
        throw new IllegalArgumentException("Only TabItem instances can be added to TabLayout");
    }

    private void animateToTab(int n) {
        if (n == -1) {
            return;
        }
        if (this.getWindowToken() != null && ViewCompat.isLaidOut((View)this) && !this.mTabStrip.childrenNeedLayout()) {
            int n2;
            int n3 = this.getScrollX();
            if (n3 != (n2 = this.calculateScrollXForTab(n, 0.0f))) {
                this.ensureScrollAnimator();
                this.mScrollAnimator.setIntValues(new int[]{n3, n2});
                this.mScrollAnimator.start();
            }
            this.mTabStrip.animateIndicatorToPosition(n, 300);
            return;
        }
        this.setScrollPosition(n, 0.0f, true);
    }

    private void applyModeAndGravity() {
        int n = this.mMode == 0 ? Math.max(0, this.mContentInsetStart - this.mTabPaddingStart) : 0;
        ViewCompat.setPaddingRelative((View)this.mTabStrip, n, 0, 0, 0);
        switch (this.mMode) {
            default: {
                break;
            }
            case 1: {
                this.mTabStrip.setGravity(1);
                break;
            }
            case 0: {
                this.mTabStrip.setGravity(8388611);
            }
        }
        this.updateTabViews(true);
    }

    private int calculateScrollXForTab(int n, float f) {
        int n2 = this.mMode;
        int n3 = 0;
        if (n2 == 0) {
            View view = this.mTabStrip.getChildAt(n);
            View view2 = ++n < this.mTabStrip.getChildCount() ? this.mTabStrip.getChildAt(n) : null;
            n = view != null ? view.getWidth() : 0;
            if (view2 != null) {
                n3 = view2.getWidth();
            }
            n2 = view.getLeft() + n / 2 - this.getWidth() / 2;
            n = (int)((float)(n + n3) * 0.5f * f);
            if (ViewCompat.getLayoutDirection((View)this) == 0) {
                return n2 + n;
            }
            return n2 - n;
        }
        return 0;
    }

    private void configureTab(Tab tab, int n) {
        tab.setPosition(n);
        this.mTabs.add(n, tab);
        int n2 = this.mTabs.size();
        while (++n < n2) {
            this.mTabs.get(n).setPosition(n);
        }
    }

    private static ColorStateList createColorStateList(int n, int n2) {
        return new ColorStateList((int[][])new int[][]{SELECTED_STATE_SET, EMPTY_STATE_SET}, new int[]{n2, n});
    }

    private LinearLayout.LayoutParams createLayoutParamsForTabs() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -1);
        this.updateTabViewLayoutParams(layoutParams);
        return layoutParams;
    }

    private TabView createTabView(@NonNull Tab tab) {
        TabView tabView = this.mTabViewPool != null ? this.mTabViewPool.acquire() : null;
        TabView tabView2 = tabView;
        if (tabView == null) {
            tabView2 = new TabView(this.getContext());
        }
        tabView2.setTab(tab);
        tabView2.setFocusable(true);
        tabView2.setMinimumWidth(this.getTabMinWidth());
        return tabView2;
    }

    private void dispatchTabReselected(@NonNull Tab tab) {
        for (int i = this.mSelectedListeners.size() - 1; i >= 0; --i) {
            this.mSelectedListeners.get(i).onTabReselected(tab);
        }
    }

    private void dispatchTabSelected(@NonNull Tab tab) {
        for (int i = this.mSelectedListeners.size() - 1; i >= 0; --i) {
            this.mSelectedListeners.get(i).onTabSelected(tab);
        }
    }

    private void dispatchTabUnselected(@NonNull Tab tab) {
        for (int i = this.mSelectedListeners.size() - 1; i >= 0; --i) {
            this.mSelectedListeners.get(i).onTabUnselected(tab);
        }
    }

    private void ensureScrollAnimator() {
        if (this.mScrollAnimator == null) {
            this.mScrollAnimator = new ValueAnimator();
            this.mScrollAnimator.setInterpolator((TimeInterpolator)AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
            this.mScrollAnimator.setDuration(300L);
            this.mScrollAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){

                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    TabLayout.this.scrollTo(((Integer)valueAnimator.getAnimatedValue()).intValue(), 0);
                }
            });
        }
    }

    private int getDefaultHeight() {
        boolean bl;
        int n = this.mTabs.size();
        boolean bl2 = false;
        int n2 = 0;
        do {
            bl = bl2;
            if (n2 >= n) break;
            Tab tab = this.mTabs.get(n2);
            if (tab != null && tab.getIcon() != null && !TextUtils.isEmpty((CharSequence)tab.getText())) {
                bl = true;
                break;
            }
            ++n2;
        } while (true);
        if (bl) {
            return 72;
        }
        return 48;
    }

    private float getScrollPosition() {
        return this.mTabStrip.getIndicatorPosition();
    }

    private int getTabMinWidth() {
        if (this.mRequestedTabMinWidth != -1) {
            return this.mRequestedTabMinWidth;
        }
        if (this.mMode == 0) {
            return this.mScrollableTabMinWidth;
        }
        return 0;
    }

    private int getTabScrollRange() {
        return Math.max(0, this.mTabStrip.getWidth() - this.getWidth() - this.getPaddingLeft() - this.getPaddingRight());
    }

    private void removeTabViewAt(int n) {
        TabView tabView = (TabView)this.mTabStrip.getChildAt(n);
        this.mTabStrip.removeViewAt(n);
        if (tabView != null) {
            tabView.reset();
            this.mTabViewPool.release(tabView);
        }
        this.requestLayout();
    }

    private void setSelectedTabView(int n) {
        int n2 = this.mTabStrip.getChildCount();
        if (n < n2) {
            for (int i = 0; i < n2; ++i) {
                View view = this.mTabStrip.getChildAt(i);
                boolean bl = i == n;
                view.setSelected(bl);
            }
        }
    }

    private void setupWithViewPager(@Nullable ViewPager viewPager, boolean bl, boolean bl2) {
        if (this.mViewPager != null) {
            if (this.mPageChangeListener != null) {
                this.mViewPager.removeOnPageChangeListener(this.mPageChangeListener);
            }
            if (this.mAdapterChangeListener != null) {
                this.mViewPager.removeOnAdapterChangeListener(this.mAdapterChangeListener);
            }
        }
        if (this.mCurrentVpSelectedListener != null) {
            this.removeOnTabSelectedListener(this.mCurrentVpSelectedListener);
            this.mCurrentVpSelectedListener = null;
        }
        if (viewPager != null) {
            this.mViewPager = viewPager;
            if (this.mPageChangeListener == null) {
                this.mPageChangeListener = new TabLayoutOnPageChangeListener(this);
            }
            this.mPageChangeListener.reset();
            viewPager.addOnPageChangeListener(this.mPageChangeListener);
            this.mCurrentVpSelectedListener = new ViewPagerOnTabSelectedListener(viewPager);
            this.addOnTabSelectedListener(this.mCurrentVpSelectedListener);
            PagerAdapter pagerAdapter = viewPager.getAdapter();
            if (pagerAdapter != null) {
                this.setPagerAdapter(pagerAdapter, bl);
            }
            if (this.mAdapterChangeListener == null) {
                this.mAdapterChangeListener = new AdapterChangeListener();
            }
            this.mAdapterChangeListener.setAutoRefresh(bl);
            viewPager.addOnAdapterChangeListener(this.mAdapterChangeListener);
            this.setScrollPosition(viewPager.getCurrentItem(), 0.0f, true);
        } else {
            this.mViewPager = null;
            this.setPagerAdapter(null, false);
        }
        this.mSetupViewPagerImplicitly = bl2;
    }

    private void updateAllTabs() {
        int n = this.mTabs.size();
        for (int i = 0; i < n; ++i) {
            this.mTabs.get(i).updateView();
        }
    }

    private void updateTabViewLayoutParams(LinearLayout.LayoutParams layoutParams) {
        if (this.mMode == 1 && this.mTabGravity == 0) {
            layoutParams.width = 0;
            layoutParams.weight = 1.0f;
            return;
        }
        layoutParams.width = -2;
        layoutParams.weight = 0.0f;
    }

    public void addOnTabSelectedListener(@NonNull OnTabSelectedListener onTabSelectedListener) {
        if (!this.mSelectedListeners.contains(onTabSelectedListener)) {
            this.mSelectedListeners.add(onTabSelectedListener);
        }
    }

    public void addTab(@NonNull Tab tab) {
        this.addTab(tab, this.mTabs.isEmpty());
    }

    public void addTab(@NonNull Tab tab, int n) {
        this.addTab(tab, n, this.mTabs.isEmpty());
    }

    public void addTab(@NonNull Tab tab, int n, boolean bl) {
        if (tab.mParent != this) {
            throw new IllegalArgumentException("Tab belongs to a different TabLayout.");
        }
        this.configureTab(tab, n);
        this.addTabView(tab);
        if (bl) {
            tab.select();
        }
    }

    public void addTab(@NonNull Tab tab, boolean bl) {
        this.addTab(tab, this.mTabs.size(), bl);
    }

    public void addView(View view) {
        this.addViewInternal(view);
    }

    public void addView(View view, int n) {
        this.addViewInternal(view);
    }

    public void addView(View view, int n, ViewGroup.LayoutParams layoutParams) {
        this.addViewInternal(view);
    }

    public void addView(View view, ViewGroup.LayoutParams layoutParams) {
        this.addViewInternal(view);
    }

    public void clearOnTabSelectedListeners() {
        this.mSelectedListeners.clear();
    }

    int dpToPx(int n) {
        return Math.round(this.getResources().getDisplayMetrics().density * (float)n);
    }

    public FrameLayout.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return this.generateDefaultLayoutParams();
    }

    public int getSelectedTabPosition() {
        if (this.mSelectedTab != null) {
            return this.mSelectedTab.getPosition();
        }
        return -1;
    }

    @Nullable
    public Tab getTabAt(int n) {
        if (n >= 0 && n < this.getTabCount()) {
            return this.mTabs.get(n);
        }
        return null;
    }

    public int getTabCount() {
        return this.mTabs.size();
    }

    public int getTabGravity() {
        return this.mTabGravity;
    }

    int getTabMaxWidth() {
        return this.mTabMaxWidth;
    }

    public int getTabMode() {
        return this.mMode;
    }

    @Nullable
    public ColorStateList getTabTextColors() {
        return this.mTabTextColors;
    }

    @NonNull
    public Tab newTab() {
        Tab tab;
        Tab tab2 = tab = sTabPool.acquire();
        if (tab == null) {
            tab2 = new Tab();
        }
        tab2.mParent = this;
        tab2.mView = this.createTabView(tab2);
        return tab2;
    }

    protected void onAttachedToWindow() {
        ViewParent viewParent;
        super.onAttachedToWindow();
        if (this.mViewPager == null && (viewParent = this.getParent()) instanceof ViewPager) {
            this.setupWithViewPager((ViewPager)viewParent, true, true);
        }
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mSetupViewPagerImplicitly) {
            this.setupWithViewPager(null);
            this.mSetupViewPagerImplicitly = false;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    protected void onMeasure(int var1_1, int var2_2) {
        block9 : {
            var3_3 = this.dpToPx(this.getDefaultHeight()) + this.getPaddingTop() + this.getPaddingBottom();
            var4_4 = View.MeasureSpec.getMode((int)var2_2);
            if (var4_4 != Integer.MIN_VALUE) {
                if (var4_4 == 0) {
                    var2_2 = View.MeasureSpec.makeMeasureSpec((int)var3_3, (int)1073741824);
                }
            } else {
                var2_2 = View.MeasureSpec.makeMeasureSpec((int)Math.min(var3_3, View.MeasureSpec.getSize((int)var2_2)), (int)1073741824);
            }
            var3_3 = View.MeasureSpec.getSize((int)var1_1);
            if (View.MeasureSpec.getMode((int)var1_1) != 0) {
                var3_3 = this.mRequestedTabMaxWidth > 0 ? this.mRequestedTabMaxWidth : (var3_3 -= this.dpToPx(56));
                this.mTabMaxWidth = var3_3;
            }
            super.onMeasure(var1_1, var2_2);
            if (this.getChildCount() != 1) return;
            var1_1 = 0;
            var5_5 = this.getChildAt(0);
            switch (this.mMode) {
                default: {
                    ** break;
                }
                case 1: {
                    if (var5_5.getMeasuredWidth() != this.getMeasuredWidth()) {
                        break;
                    }
                    break block9;
                }
                case 0: {
                    if (var5_5.getMeasuredWidth() >= this.getMeasuredWidth()) break block9;
                }
            }
            var1_1 = 1;
            ** break;
        }
        if (var1_1 == 0) return;
        var1_1 = TabLayout.getChildMeasureSpec((int)var2_2, (int)(this.getPaddingTop() + this.getPaddingBottom()), (int)var5_5.getLayoutParams().height);
        var5_5.measure(View.MeasureSpec.makeMeasureSpec((int)this.getMeasuredWidth(), (int)1073741824), var1_1);
    }

    void populateFromPagerAdapter() {
        this.removeAllTabs();
        if (this.mPagerAdapter != null) {
            int n;
            int n2 = this.mPagerAdapter.getCount();
            for (n = 0; n < n2; ++n) {
                this.addTab(this.newTab().setText(this.mPagerAdapter.getPageTitle(n)), false);
            }
            if (this.mViewPager != null && n2 > 0 && (n = this.mViewPager.getCurrentItem()) != this.getSelectedTabPosition() && n < this.getTabCount()) {
                this.selectTab(this.getTabAt(n));
            }
        }
    }

    public void removeAllTabs() {
        for (int i = this.mTabStrip.getChildCount() - 1; i >= 0; --i) {
            this.removeTabViewAt(i);
        }
        Iterator<Tab> iterator = this.mTabs.iterator();
        while (iterator.hasNext()) {
            Tab tab = iterator.next();
            iterator.remove();
            tab.reset();
            sTabPool.release(tab);
        }
        this.mSelectedTab = null;
    }

    public void removeOnTabSelectedListener(@NonNull OnTabSelectedListener onTabSelectedListener) {
        this.mSelectedListeners.remove(onTabSelectedListener);
    }

    public void removeTab(Tab tab) {
        if (tab.mParent != this) {
            throw new IllegalArgumentException("Tab does not belong to this TabLayout.");
        }
        this.removeTabAt(tab.getPosition());
    }

    public void removeTabAt(int n) {
        int n2 = this.mSelectedTab != null ? this.mSelectedTab.getPosition() : 0;
        this.removeTabViewAt(n);
        Tab tab = this.mTabs.remove(n);
        if (tab != null) {
            tab.reset();
            sTabPool.release(tab);
        }
        int n3 = this.mTabs.size();
        for (int i = n; i < n3; ++i) {
            this.mTabs.get(i).setPosition(i);
        }
        if (n2 == n) {
            tab = this.mTabs.isEmpty() ? null : this.mTabs.get(Math.max(0, n - 1));
            this.selectTab(tab);
        }
    }

    void selectTab(Tab tab) {
        this.selectTab(tab, true);
    }

    void selectTab(Tab tab, boolean bl) {
        Tab tab2 = this.mSelectedTab;
        if (tab2 == tab) {
            if (tab2 != null) {
                this.dispatchTabReselected(tab);
                this.animateToTab(tab.getPosition());
                return;
            }
        } else {
            int n = tab != null ? tab.getPosition() : -1;
            if (bl) {
                if ((tab2 == null || tab2.getPosition() == -1) && n != -1) {
                    this.setScrollPosition(n, 0.0f, true);
                } else {
                    this.animateToTab(n);
                }
                if (n != -1) {
                    this.setSelectedTabView(n);
                }
            }
            if (tab2 != null) {
                this.dispatchTabUnselected(tab2);
            }
            this.mSelectedTab = tab;
            if (tab != null) {
                this.dispatchTabSelected(tab);
            }
        }
    }

    @Deprecated
    public void setOnTabSelectedListener(@Nullable OnTabSelectedListener onTabSelectedListener) {
        if (this.mSelectedListener != null) {
            this.removeOnTabSelectedListener(this.mSelectedListener);
        }
        this.mSelectedListener = onTabSelectedListener;
        if (onTabSelectedListener != null) {
            this.addOnTabSelectedListener(onTabSelectedListener);
        }
    }

    void setPagerAdapter(@Nullable PagerAdapter pagerAdapter, boolean bl) {
        if (this.mPagerAdapter != null && this.mPagerAdapterObserver != null) {
            this.mPagerAdapter.unregisterDataSetObserver(this.mPagerAdapterObserver);
        }
        this.mPagerAdapter = pagerAdapter;
        if (bl && pagerAdapter != null) {
            if (this.mPagerAdapterObserver == null) {
                this.mPagerAdapterObserver = new PagerAdapterObserver();
            }
            pagerAdapter.registerDataSetObserver(this.mPagerAdapterObserver);
        }
        this.populateFromPagerAdapter();
    }

    void setScrollAnimatorListener(Animator.AnimatorListener animatorListener) {
        this.ensureScrollAnimator();
        this.mScrollAnimator.addListener(animatorListener);
    }

    public void setScrollPosition(int n, float f, boolean bl) {
        this.setScrollPosition(n, f, bl, true);
    }

    void setScrollPosition(int n, float f, boolean bl, boolean bl2) {
        int n2 = Math.round((float)n + f);
        if (n2 >= 0) {
            if (n2 >= this.mTabStrip.getChildCount()) {
                return;
            }
            if (bl2) {
                this.mTabStrip.setIndicatorPositionFromTabPosition(n, f);
            }
            if (this.mScrollAnimator != null && this.mScrollAnimator.isRunning()) {
                this.mScrollAnimator.cancel();
            }
            this.scrollTo(this.calculateScrollXForTab(n, f), 0);
            if (bl) {
                this.setSelectedTabView(n2);
            }
            return;
        }
    }

    public void setSelectedTabIndicatorColor(@ColorInt int n) {
        this.mTabStrip.setSelectedIndicatorColor(n);
    }

    public void setSelectedTabIndicatorHeight(int n) {
        this.mTabStrip.setSelectedIndicatorHeight(n);
    }

    public void setTabGravity(int n) {
        if (this.mTabGravity != n) {
            this.mTabGravity = n;
            this.applyModeAndGravity();
        }
    }

    public void setTabMode(int n) {
        if (n != this.mMode) {
            this.mMode = n;
            this.applyModeAndGravity();
        }
    }

    public void setTabTextColors(int n, int n2) {
        this.setTabTextColors(TabLayout.createColorStateList(n, n2));
    }

    public void setTabTextColors(@Nullable ColorStateList colorStateList) {
        if (this.mTabTextColors != colorStateList) {
            this.mTabTextColors = colorStateList;
            this.updateAllTabs();
        }
    }

    @Deprecated
    public void setTabsFromPagerAdapter(@Nullable PagerAdapter pagerAdapter) {
        this.setPagerAdapter(pagerAdapter, false);
    }

    public void setupWithViewPager(@Nullable ViewPager viewPager) {
        this.setupWithViewPager(viewPager, true);
    }

    public void setupWithViewPager(@Nullable ViewPager viewPager, boolean bl) {
        this.setupWithViewPager(viewPager, bl, false);
    }

    public boolean shouldDelayChildPressedState() {
        if (this.getTabScrollRange() > 0) {
            return true;
        }
        return false;
    }

    void updateTabViews(boolean bl) {
        for (int i = 0; i < this.mTabStrip.getChildCount(); ++i) {
            View view = this.mTabStrip.getChildAt(i);
            view.setMinimumWidth(this.getTabMinWidth());
            this.updateTabViewLayoutParams((LinearLayout.LayoutParams)view.getLayoutParams());
            if (!bl) continue;
            view.requestLayout();
        }
    }

    private class AdapterChangeListener
    implements ViewPager.OnAdapterChangeListener {
        private boolean mAutoRefresh;

        AdapterChangeListener() {
        }

        @Override
        public void onAdapterChanged(@NonNull ViewPager viewPager, @Nullable PagerAdapter pagerAdapter, @Nullable PagerAdapter pagerAdapter2) {
            if (TabLayout.this.mViewPager == viewPager) {
                TabLayout.this.setPagerAdapter(pagerAdapter2, this.mAutoRefresh);
            }
        }

        void setAutoRefresh(boolean bl) {
            this.mAutoRefresh = bl;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public static @interface Mode {
    }

    public static interface OnTabSelectedListener {
        public void onTabReselected(Tab var1);

        public void onTabSelected(Tab var1);

        public void onTabUnselected(Tab var1);
    }

    private class PagerAdapterObserver
    extends DataSetObserver {
        PagerAdapterObserver() {
        }

        public void onChanged() {
            TabLayout.this.populateFromPagerAdapter();
        }

        public void onInvalidated() {
            TabLayout.this.populateFromPagerAdapter();
        }
    }

    private class SlidingTabStrip
    extends LinearLayout {
        private ValueAnimator mIndicatorAnimator;
        private int mIndicatorLeft;
        private int mIndicatorRight;
        private int mLayoutDirection;
        private int mSelectedIndicatorHeight;
        private final Paint mSelectedIndicatorPaint;
        int mSelectedPosition;
        float mSelectionOffset;

        SlidingTabStrip(Context context) {
            super(context);
            this.mSelectedPosition = -1;
            this.mLayoutDirection = -1;
            this.mIndicatorLeft = -1;
            this.mIndicatorRight = -1;
            this.setWillNotDraw(false);
            this.mSelectedIndicatorPaint = new Paint();
        }

        private void updateIndicatorPosition() {
            int n;
            int n2;
            View view = this.getChildAt(this.mSelectedPosition);
            if (view != null && view.getWidth() > 0) {
                int n3;
                int n4 = view.getLeft();
                n = n3 = view.getRight();
                n2 = n4;
                if (this.mSelectionOffset > 0.0f) {
                    n = n3;
                    n2 = n4;
                    if (this.mSelectedPosition < this.getChildCount() - 1) {
                        view = this.getChildAt(this.mSelectedPosition + 1);
                        n2 = (int)(this.mSelectionOffset * (float)view.getLeft() + (1.0f - this.mSelectionOffset) * (float)n4);
                        n = (int)(this.mSelectionOffset * (float)view.getRight() + (1.0f - this.mSelectionOffset) * (float)n3);
                    }
                }
            } else {
                n2 = -1;
                n = -1;
            }
            this.setIndicatorPosition(n2, n);
        }

        /*
         * Enabled aggressive block sorting
         */
        void animateIndicatorToPosition(final int n, int n2) {
            int n3;
            if (this.mIndicatorAnimator != null && this.mIndicatorAnimator.isRunning()) {
                this.mIndicatorAnimator.cancel();
            }
            final int n4 = ViewCompat.getLayoutDirection((View)this) == 1 ? 1 : 0;
            View view = this.getChildAt(n);
            if (view == null) {
                this.updateIndicatorPosition();
                return;
            }
            final int n5 = view.getLeft();
            final int n6 = view.getRight();
            if (Math.abs(n - this.mSelectedPosition) <= 1) {
                n4 = this.mIndicatorLeft;
                n3 = this.mIndicatorRight;
            } else {
                n3 = TabLayout.this.dpToPx(24);
                n4 = n < this.mSelectedPosition ? (n4 != 0 ? n5 - n3 : n3 + n6) : (n4 != 0 ? n3 + n6 : n5 - n3);
                n3 = n4;
            }
            if (n4 != n5 || n3 != n6) {
                view = new ValueAnimator();
                this.mIndicatorAnimator = view;
                view.setInterpolator((TimeInterpolator)AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
                view.setDuration((long)n2);
                view.setFloatValues(new float[]{0.0f, 1.0f});
                view.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){

                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        float f = valueAnimator.getAnimatedFraction();
                        SlidingTabStrip.this.setIndicatorPosition(AnimationUtils.lerp(n4, n5, f), AnimationUtils.lerp(n3, n6, f));
                    }
                });
                view.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter(){

                    public void onAnimationEnd(Animator animator) {
                        SlidingTabStrip.this.mSelectedPosition = n;
                        SlidingTabStrip.this.mSelectionOffset = 0.0f;
                    }
                });
                view.start();
            }
        }

        boolean childrenNeedLayout() {
            int n = this.getChildCount();
            for (int i = 0; i < n; ++i) {
                if (this.getChildAt(i).getWidth() > 0) continue;
                return true;
            }
            return false;
        }

        public void draw(Canvas canvas) {
            super.draw(canvas);
            if (this.mIndicatorLeft >= 0 && this.mIndicatorRight > this.mIndicatorLeft) {
                canvas.drawRect((float)this.mIndicatorLeft, (float)(this.getHeight() - this.mSelectedIndicatorHeight), (float)this.mIndicatorRight, (float)this.getHeight(), this.mSelectedIndicatorPaint);
            }
        }

        float getIndicatorPosition() {
            return (float)this.mSelectedPosition + this.mSelectionOffset;
        }

        protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
            super.onLayout(bl, n, n2, n3, n4);
            if (this.mIndicatorAnimator != null && this.mIndicatorAnimator.isRunning()) {
                this.mIndicatorAnimator.cancel();
                long l = this.mIndicatorAnimator.getDuration();
                this.animateIndicatorToPosition(this.mSelectedPosition, Math.round((1.0f - this.mIndicatorAnimator.getAnimatedFraction()) * (float)l));
                return;
            }
            this.updateIndicatorPosition();
        }

        protected void onMeasure(int n, int n2) {
            super.onMeasure(n, n2);
            if (View.MeasureSpec.getMode((int)n) != 1073741824) {
                return;
            }
            int n3 = TabLayout.this.mMode;
            int n4 = 1;
            if (n3 == 1 && TabLayout.this.mTabGravity == 1) {
                View view;
                int n5;
                int n6 = this.getChildCount();
                int n7 = 0;
                int n8 = n3 = 0;
                while (n3 < n6) {
                    view = this.getChildAt(n3);
                    n5 = n8;
                    if (view.getVisibility() == 0) {
                        n5 = Math.max(n8, view.getMeasuredWidth());
                    }
                    ++n3;
                    n8 = n5;
                }
                if (n8 <= 0) {
                    return;
                }
                n3 = TabLayout.this.dpToPx(16);
                if (n8 * n6 <= this.getMeasuredWidth() - n3 * 2) {
                    n3 = 0;
                    for (n5 = n7; n5 < n6; ++n5) {
                        view = (LinearLayout.LayoutParams)this.getChildAt(n5).getLayoutParams();
                        if (view.width == n8 && view.weight == 0.0f) continue;
                        view.width = n8;
                        view.weight = 0.0f;
                        n3 = 1;
                    }
                } else {
                    TabLayout.this.mTabGravity = 0;
                    TabLayout.this.updateTabViews(false);
                    n3 = n4;
                }
                if (n3 != 0) {
                    super.onMeasure(n, n2);
                }
            }
        }

        public void onRtlPropertiesChanged(int n) {
            super.onRtlPropertiesChanged(n);
            if (Build.VERSION.SDK_INT < 23 && this.mLayoutDirection != n) {
                this.requestLayout();
                this.mLayoutDirection = n;
            }
        }

        void setIndicatorPosition(int n, int n2) {
            if (n != this.mIndicatorLeft || n2 != this.mIndicatorRight) {
                this.mIndicatorLeft = n;
                this.mIndicatorRight = n2;
                ViewCompat.postInvalidateOnAnimation((View)this);
            }
        }

        void setIndicatorPositionFromTabPosition(int n, float f) {
            if (this.mIndicatorAnimator != null && this.mIndicatorAnimator.isRunning()) {
                this.mIndicatorAnimator.cancel();
            }
            this.mSelectedPosition = n;
            this.mSelectionOffset = f;
            this.updateIndicatorPosition();
        }

        void setSelectedIndicatorColor(int n) {
            if (this.mSelectedIndicatorPaint.getColor() != n) {
                this.mSelectedIndicatorPaint.setColor(n);
                ViewCompat.postInvalidateOnAnimation((View)this);
            }
        }

        void setSelectedIndicatorHeight(int n) {
            if (this.mSelectedIndicatorHeight != n) {
                this.mSelectedIndicatorHeight = n;
                ViewCompat.postInvalidateOnAnimation((View)this);
            }
        }

    }

    public static final class Tab {
        public static final int INVALID_POSITION = -1;
        private CharSequence mContentDesc;
        private View mCustomView;
        private Drawable mIcon;
        TabLayout mParent;
        private int mPosition = -1;
        private Object mTag;
        private CharSequence mText;
        TabView mView;

        Tab() {
        }

        @Nullable
        public CharSequence getContentDescription() {
            return this.mContentDesc;
        }

        @Nullable
        public View getCustomView() {
            return this.mCustomView;
        }

        @Nullable
        public Drawable getIcon() {
            return this.mIcon;
        }

        public int getPosition() {
            return this.mPosition;
        }

        @Nullable
        public Object getTag() {
            return this.mTag;
        }

        @Nullable
        public CharSequence getText() {
            return this.mText;
        }

        public boolean isSelected() {
            if (this.mParent == null) {
                throw new IllegalArgumentException("Tab not attached to a TabLayout");
            }
            if (this.mParent.getSelectedTabPosition() == this.mPosition) {
                return true;
            }
            return false;
        }

        void reset() {
            this.mParent = null;
            this.mView = null;
            this.mTag = null;
            this.mIcon = null;
            this.mText = null;
            this.mContentDesc = null;
            this.mPosition = -1;
            this.mCustomView = null;
        }

        public void select() {
            if (this.mParent == null) {
                throw new IllegalArgumentException("Tab not attached to a TabLayout");
            }
            this.mParent.selectTab(this);
        }

        @NonNull
        public Tab setContentDescription(@StringRes int n) {
            if (this.mParent == null) {
                throw new IllegalArgumentException("Tab not attached to a TabLayout");
            }
            return this.setContentDescription(this.mParent.getResources().getText(n));
        }

        @NonNull
        public Tab setContentDescription(@Nullable CharSequence charSequence) {
            this.mContentDesc = charSequence;
            this.updateView();
            return this;
        }

        @NonNull
        public Tab setCustomView(@LayoutRes int n) {
            return this.setCustomView(LayoutInflater.from((Context)this.mView.getContext()).inflate(n, (ViewGroup)this.mView, false));
        }

        @NonNull
        public Tab setCustomView(@Nullable View view) {
            this.mCustomView = view;
            this.updateView();
            return this;
        }

        @NonNull
        public Tab setIcon(@DrawableRes int n) {
            if (this.mParent == null) {
                throw new IllegalArgumentException("Tab not attached to a TabLayout");
            }
            return this.setIcon(AppCompatResources.getDrawable(this.mParent.getContext(), n));
        }

        @NonNull
        public Tab setIcon(@Nullable Drawable drawable) {
            this.mIcon = drawable;
            this.updateView();
            return this;
        }

        void setPosition(int n) {
            this.mPosition = n;
        }

        @NonNull
        public Tab setTag(@Nullable Object object) {
            this.mTag = object;
            return this;
        }

        @NonNull
        public Tab setText(@StringRes int n) {
            if (this.mParent == null) {
                throw new IllegalArgumentException("Tab not attached to a TabLayout");
            }
            return this.setText(this.mParent.getResources().getText(n));
        }

        @NonNull
        public Tab setText(@Nullable CharSequence charSequence) {
            this.mText = charSequence;
            this.updateView();
            return this;
        }

        void updateView() {
            if (this.mView != null) {
                this.mView.update();
            }
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public static @interface TabGravity {
    }

    public static class TabLayoutOnPageChangeListener
    implements ViewPager.OnPageChangeListener {
        private int mPreviousScrollState;
        private int mScrollState;
        private final WeakReference<TabLayout> mTabLayoutRef;

        public TabLayoutOnPageChangeListener(TabLayout tabLayout) {
            this.mTabLayoutRef = new WeakReference<TabLayout>(tabLayout);
        }

        @Override
        public void onPageScrollStateChanged(int n) {
            this.mPreviousScrollState = this.mScrollState;
            this.mScrollState = n;
        }

        @Override
        public void onPageScrolled(int n, float f, int n2) {
            TabLayout tabLayout = (TabLayout)((Object)this.mTabLayoutRef.get());
            if (tabLayout != null) {
                n2 = this.mScrollState;
                boolean bl = false;
                boolean bl2 = n2 != 2 || this.mPreviousScrollState == 1;
                if (this.mScrollState != 2 || this.mPreviousScrollState != 0) {
                    bl = true;
                }
                tabLayout.setScrollPosition(n, f, bl2, bl);
            }
        }

        @Override
        public void onPageSelected(int n) {
            TabLayout tabLayout = (TabLayout)((Object)this.mTabLayoutRef.get());
            if (tabLayout != null && tabLayout.getSelectedTabPosition() != n && n < tabLayout.getTabCount()) {
                boolean bl = this.mScrollState == 0 || this.mScrollState == 2 && this.mPreviousScrollState == 0;
                tabLayout.selectTab(tabLayout.getTabAt(n), bl);
            }
        }

        void reset() {
            this.mScrollState = 0;
            this.mPreviousScrollState = 0;
        }
    }

    class TabView
    extends LinearLayout {
        private ImageView mCustomIconView;
        private TextView mCustomTextView;
        private View mCustomView;
        private int mDefaultMaxLines;
        private ImageView mIconView;
        private Tab mTab;
        private TextView mTextView;

        public TabView(Context context) {
            super(context);
            this.mDefaultMaxLines = 2;
            if (TabLayout.this.mTabBackgroundResId != 0) {
                ViewCompat.setBackground((View)this, AppCompatResources.getDrawable(context, TabLayout.this.mTabBackgroundResId));
            }
            ViewCompat.setPaddingRelative((View)this, TabLayout.this.mTabPaddingStart, TabLayout.this.mTabPaddingTop, TabLayout.this.mTabPaddingEnd, TabLayout.this.mTabPaddingBottom);
            this.setGravity(17);
            this.setOrientation(1);
            this.setClickable(true);
            ViewCompat.setPointerIcon((View)this, PointerIconCompat.getSystemIcon(this.getContext(), 1002));
        }

        private float approximateLineWidth(Layout layout, int n, float f) {
            return layout.getLineWidth(n) * (f / layout.getPaint().getTextSize());
        }

        private void updateTextAndIcon(@Nullable TextView textView, @Nullable ImageView imageView) {
            Object object = this.mTab;
            Object var9_4 = null;
            Drawable drawable = object != null ? this.mTab.getIcon() : null;
            CharSequence charSequence = this.mTab != null ? this.mTab.getText() : null;
            object = this.mTab != null ? this.mTab.getContentDescription() : null;
            int n = 0;
            if (imageView != null) {
                if (drawable != null) {
                    imageView.setImageDrawable(drawable);
                    imageView.setVisibility(0);
                    this.setVisibility(0);
                } else {
                    imageView.setVisibility(8);
                    imageView.setImageDrawable(null);
                }
                imageView.setContentDescription((CharSequence)object);
            }
            boolean bl = TextUtils.isEmpty((CharSequence)charSequence) ^ true;
            if (textView != null) {
                if (bl) {
                    textView.setText(charSequence);
                    textView.setVisibility(0);
                    this.setVisibility(0);
                } else {
                    textView.setVisibility(8);
                    textView.setText(null);
                }
                textView.setContentDescription((CharSequence)object);
            }
            if (imageView != null) {
                textView = (ViewGroup.MarginLayoutParams)imageView.getLayoutParams();
                int n2 = n;
                if (bl) {
                    n2 = n;
                    if (imageView.getVisibility() == 0) {
                        n2 = TabLayout.this.dpToPx(8);
                    }
                }
                if (n2 != textView.bottomMargin) {
                    textView.bottomMargin = n2;
                    imageView.requestLayout();
                }
            }
            if (bl) {
                object = var9_4;
            }
            TooltipCompat.setTooltipText((View)this, (CharSequence)object);
        }

        public Tab getTab() {
            return this.mTab;
        }

        public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
            super.onInitializeAccessibilityEvent(accessibilityEvent);
            accessibilityEvent.setClassName((CharSequence)ActionBar.Tab.class.getName());
        }

        public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
            accessibilityNodeInfo.setClassName((CharSequence)ActionBar.Tab.class.getName());
        }

        public void onMeasure(int n, int n2) {
            block13 : {
                int n3;
                int n4;
                float f;
                block14 : {
                    block15 : {
                        int n5;
                        int n6;
                        block11 : {
                            block12 : {
                                n4 = View.MeasureSpec.getSize((int)n);
                                n6 = View.MeasureSpec.getMode((int)n);
                                n5 = TabLayout.this.getTabMaxWidth();
                                n3 = n;
                                if (n5 <= 0) break block11;
                                if (n6 == 0) break block12;
                                n3 = n;
                                if (n4 <= n5) break block11;
                            }
                            n3 = View.MeasureSpec.makeMeasureSpec((int)TabLayout.this.mTabMaxWidth, (int)Integer.MIN_VALUE);
                        }
                        super.onMeasure(n3, n2);
                        if (this.mTextView == null) break block13;
                        this.getResources();
                        float f2 = TabLayout.this.mTabTextSize;
                        n4 = this.mDefaultMaxLines;
                        ImageView imageView = this.mIconView;
                        n6 = 1;
                        if (imageView != null && this.mIconView.getVisibility() == 0) {
                            n = 1;
                            f = f2;
                        } else {
                            f = f2;
                            n = n4;
                            if (this.mTextView != null) {
                                f = f2;
                                n = n4;
                                if (this.mTextView.getLineCount() > 1) {
                                    f = TabLayout.this.mTabTextMultiLineSize;
                                    n = n4;
                                }
                            }
                        }
                        f2 = this.mTextView.getTextSize();
                        n5 = this.mTextView.getLineCount();
                        n4 = TextViewCompat.getMaxLines(this.mTextView);
                        if (f == f2 && (n4 < 0 || n == n4)) break block13;
                        n4 = n6;
                        if (TabLayout.this.mMode != 1) break block14;
                        n4 = n6;
                        if (f <= f2) break block14;
                        n4 = n6;
                        if (n5 != 1) break block14;
                        imageView = this.mTextView.getLayout();
                        if (imageView == null) break block15;
                        n4 = n6;
                        if (this.approximateLineWidth((Layout)imageView, 0, f) <= (float)(this.getMeasuredWidth() - this.getPaddingLeft() - this.getPaddingRight())) break block14;
                    }
                    n4 = 0;
                }
                if (n4 != 0) {
                    this.mTextView.setTextSize(0, f);
                    this.mTextView.setMaxLines(n);
                    super.onMeasure(n3, n2);
                }
            }
        }

        public boolean performClick() {
            boolean bl = super.performClick();
            if (this.mTab != null) {
                if (!bl) {
                    this.playSoundEffect(0);
                }
                this.mTab.select();
                return true;
            }
            return bl;
        }

        void reset() {
            this.setTab(null);
            this.setSelected(false);
        }

        public void setSelected(boolean bl) {
            boolean bl2 = this.isSelected() != bl;
            super.setSelected(bl);
            if (bl2 && bl && Build.VERSION.SDK_INT < 16) {
                this.sendAccessibilityEvent(4);
            }
            if (this.mTextView != null) {
                this.mTextView.setSelected(bl);
            }
            if (this.mIconView != null) {
                this.mIconView.setSelected(bl);
            }
            if (this.mCustomView != null) {
                this.mCustomView.setSelected(bl);
            }
        }

        void setTab(@Nullable Tab tab) {
            if (tab != this.mTab) {
                this.mTab = tab;
                this.update();
            }
        }

        final void update() {
            Tab tab = this.mTab;
            View view = tab != null ? tab.getCustomView() : null;
            if (view != null) {
                ViewParent viewParent = view.getParent();
                if (viewParent != this) {
                    if (viewParent != null) {
                        ((ViewGroup)viewParent).removeView(view);
                    }
                    this.addView(view);
                }
                this.mCustomView = view;
                if (this.mTextView != null) {
                    this.mTextView.setVisibility(8);
                }
                if (this.mIconView != null) {
                    this.mIconView.setVisibility(8);
                    this.mIconView.setImageDrawable(null);
                }
                this.mCustomTextView = (TextView)view.findViewById(16908308);
                if (this.mCustomTextView != null) {
                    this.mDefaultMaxLines = TextViewCompat.getMaxLines(this.mCustomTextView);
                }
                this.mCustomIconView = (ImageView)view.findViewById(16908294);
            } else {
                if (this.mCustomView != null) {
                    this.removeView(this.mCustomView);
                    this.mCustomView = null;
                }
                this.mCustomTextView = null;
                this.mCustomIconView = null;
            }
            view = this.mCustomView;
            boolean bl = false;
            if (view == null) {
                if (this.mIconView == null) {
                    view = (ImageView)LayoutInflater.from((Context)this.getContext()).inflate(R.layout.design_layout_tab_icon, (ViewGroup)this, false);
                    this.addView(view, 0);
                    this.mIconView = view;
                }
                if (this.mTextView == null) {
                    view = (TextView)LayoutInflater.from((Context)this.getContext()).inflate(R.layout.design_layout_tab_text, (ViewGroup)this, false);
                    this.addView(view);
                    this.mTextView = view;
                    this.mDefaultMaxLines = TextViewCompat.getMaxLines(this.mTextView);
                }
                TextViewCompat.setTextAppearance(this.mTextView, TabLayout.this.mTabTextAppearance);
                if (TabLayout.this.mTabTextColors != null) {
                    this.mTextView.setTextColor(TabLayout.this.mTabTextColors);
                }
                this.updateTextAndIcon(this.mTextView, this.mIconView);
            } else if (this.mCustomTextView != null || this.mCustomIconView != null) {
                this.updateTextAndIcon(this.mCustomTextView, this.mCustomIconView);
            }
            boolean bl2 = bl;
            if (tab != null) {
                bl2 = bl;
                if (tab.isSelected()) {
                    bl2 = true;
                }
            }
            this.setSelected(bl2);
        }
    }

    public static class ViewPagerOnTabSelectedListener
    implements OnTabSelectedListener {
        private final ViewPager mViewPager;

        public ViewPagerOnTabSelectedListener(ViewPager viewPager) {
            this.mViewPager = viewPager;
        }

        @Override
        public void onTabReselected(Tab tab) {
        }

        @Override
        public void onTabSelected(Tab tab) {
            this.mViewPager.setCurrentItem(tab.getPosition());
        }

        @Override
        public void onTabUnselected(Tab tab) {
        }
    }

}
