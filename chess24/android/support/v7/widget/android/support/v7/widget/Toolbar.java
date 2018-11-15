/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$ClassLoaderCreator
 *  android.os.Parcelable$Creator
 *  android.text.Layout
 *  android.text.TextUtils
 *  android.text.TextUtils$TruncateAt
 *  android.util.AttributeSet
 *  android.view.ContextThemeWrapper
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.ViewParent
 *  android.widget.ImageButton
 *  android.widget.ImageView
 *  android.widget.TextView
 */
package android.support.v7.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.MenuRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.support.v4.view.AbsSavedState;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MarginLayoutParamsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.appcompat.R;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.view.CollapsibleActionView;
import android.support.v7.view.SupportMenuInflater;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.view.menu.MenuPresenter;
import android.support.v7.view.menu.MenuView;
import android.support.v7.view.menu.SubMenuBuilder;
import android.support.v7.widget.ActionMenuPresenter;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DecorToolbar;
import android.support.v7.widget.RtlSpacingHelper;
import android.support.v7.widget.TintTypedArray;
import android.support.v7.widget.ToolbarWidgetWrapper;
import android.support.v7.widget.ViewUtils;
import android.text.Layout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class Toolbar
extends ViewGroup {
    private static final String TAG = "Toolbar";
    private MenuPresenter.Callback mActionMenuPresenterCallback;
    int mButtonGravity;
    ImageButton mCollapseButtonView;
    private CharSequence mCollapseDescription;
    private Drawable mCollapseIcon;
    private boolean mCollapsible;
    private int mContentInsetEndWithActions;
    private int mContentInsetStartWithNavigation;
    private RtlSpacingHelper mContentInsets;
    private boolean mEatingHover;
    private boolean mEatingTouch;
    View mExpandedActionView;
    private ExpandedActionViewMenuPresenter mExpandedMenuPresenter;
    private int mGravity = 8388627;
    private final ArrayList<View> mHiddenViews = new ArrayList();
    private ImageView mLogoView;
    private int mMaxButtonHeight;
    private MenuBuilder.Callback mMenuBuilderCallback;
    private ActionMenuView mMenuView;
    private final ActionMenuView.OnMenuItemClickListener mMenuViewItemClickListener = new ActionMenuView.OnMenuItemClickListener(){

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            if (Toolbar.this.mOnMenuItemClickListener != null) {
                return Toolbar.this.mOnMenuItemClickListener.onMenuItemClick(menuItem);
            }
            return false;
        }
    };
    private ImageButton mNavButtonView;
    OnMenuItemClickListener mOnMenuItemClickListener;
    private ActionMenuPresenter mOuterActionMenuPresenter;
    private Context mPopupContext;
    private int mPopupTheme;
    private final Runnable mShowOverflowMenuRunnable = new Runnable(){

        @Override
        public void run() {
            Toolbar.this.showOverflowMenu();
        }
    };
    private CharSequence mSubtitleText;
    private int mSubtitleTextAppearance;
    private int mSubtitleTextColor;
    private TextView mSubtitleTextView;
    private final int[] mTempMargins = new int[2];
    private final ArrayList<View> mTempViews = new ArrayList();
    private int mTitleMarginBottom;
    private int mTitleMarginEnd;
    private int mTitleMarginStart;
    private int mTitleMarginTop;
    private CharSequence mTitleText;
    private int mTitleTextAppearance;
    private int mTitleTextColor;
    private TextView mTitleTextView;
    private ToolbarWidgetWrapper mWrapper;

    public Toolbar(Context context) {
        this(context, null);
    }

    public Toolbar(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.toolbarStyle);
    }

    public Toolbar(Context object, @Nullable AttributeSet object2, int n) {
        int n2;
        super((Context)object, object2, n);
        object = TintTypedArray.obtainStyledAttributes(this.getContext(), object2, R.styleable.Toolbar, n, 0);
        this.mTitleTextAppearance = object.getResourceId(R.styleable.Toolbar_titleTextAppearance, 0);
        this.mSubtitleTextAppearance = object.getResourceId(R.styleable.Toolbar_subtitleTextAppearance, 0);
        this.mGravity = object.getInteger(R.styleable.Toolbar_android_gravity, this.mGravity);
        this.mButtonGravity = object.getInteger(R.styleable.Toolbar_buttonGravity, 48);
        n = n2 = object.getDimensionPixelOffset(R.styleable.Toolbar_titleMargin, 0);
        if (object.hasValue(R.styleable.Toolbar_titleMargins)) {
            n = object.getDimensionPixelOffset(R.styleable.Toolbar_titleMargins, n2);
        }
        this.mTitleMarginBottom = n;
        this.mTitleMarginTop = n;
        this.mTitleMarginEnd = n;
        this.mTitleMarginStart = n;
        n = object.getDimensionPixelOffset(R.styleable.Toolbar_titleMarginStart, -1);
        if (n >= 0) {
            this.mTitleMarginStart = n;
        }
        if ((n = object.getDimensionPixelOffset(R.styleable.Toolbar_titleMarginEnd, -1)) >= 0) {
            this.mTitleMarginEnd = n;
        }
        if ((n = object.getDimensionPixelOffset(R.styleable.Toolbar_titleMarginTop, -1)) >= 0) {
            this.mTitleMarginTop = n;
        }
        if ((n = object.getDimensionPixelOffset(R.styleable.Toolbar_titleMarginBottom, -1)) >= 0) {
            this.mTitleMarginBottom = n;
        }
        this.mMaxButtonHeight = object.getDimensionPixelSize(R.styleable.Toolbar_maxButtonHeight, -1);
        n = object.getDimensionPixelOffset(R.styleable.Toolbar_contentInsetStart, Integer.MIN_VALUE);
        n2 = object.getDimensionPixelOffset(R.styleable.Toolbar_contentInsetEnd, Integer.MIN_VALUE);
        int n3 = object.getDimensionPixelSize(R.styleable.Toolbar_contentInsetLeft, 0);
        int n4 = object.getDimensionPixelSize(R.styleable.Toolbar_contentInsetRight, 0);
        this.ensureContentInsets();
        this.mContentInsets.setAbsolute(n3, n4);
        if (n != Integer.MIN_VALUE || n2 != Integer.MIN_VALUE) {
            this.mContentInsets.setRelative(n, n2);
        }
        this.mContentInsetStartWithNavigation = object.getDimensionPixelOffset(R.styleable.Toolbar_contentInsetStartWithNavigation, Integer.MIN_VALUE);
        this.mContentInsetEndWithActions = object.getDimensionPixelOffset(R.styleable.Toolbar_contentInsetEndWithActions, Integer.MIN_VALUE);
        this.mCollapseIcon = object.getDrawable(R.styleable.Toolbar_collapseIcon);
        this.mCollapseDescription = object.getText(R.styleable.Toolbar_collapseContentDescription);
        object2 = object.getText(R.styleable.Toolbar_title);
        if (!TextUtils.isEmpty((CharSequence)object2)) {
            this.setTitle((CharSequence)object2);
        }
        if (!TextUtils.isEmpty((CharSequence)(object2 = object.getText(R.styleable.Toolbar_subtitle)))) {
            this.setSubtitle((CharSequence)object2);
        }
        this.mPopupContext = this.getContext();
        this.setPopupTheme(object.getResourceId(R.styleable.Toolbar_popupTheme, 0));
        object2 = object.getDrawable(R.styleable.Toolbar_navigationIcon);
        if (object2 != null) {
            this.setNavigationIcon((Drawable)object2);
        }
        if (!TextUtils.isEmpty((CharSequence)(object2 = object.getText(R.styleable.Toolbar_navigationContentDescription)))) {
            this.setNavigationContentDescription((CharSequence)object2);
        }
        if ((object2 = object.getDrawable(R.styleable.Toolbar_logo)) != null) {
            this.setLogo((Drawable)object2);
        }
        if (!TextUtils.isEmpty((CharSequence)(object2 = object.getText(R.styleable.Toolbar_logoDescription)))) {
            this.setLogoDescription((CharSequence)object2);
        }
        if (object.hasValue(R.styleable.Toolbar_titleTextColor)) {
            this.setTitleTextColor(object.getColor(R.styleable.Toolbar_titleTextColor, -1));
        }
        if (object.hasValue(R.styleable.Toolbar_subtitleTextColor)) {
            this.setSubtitleTextColor(object.getColor(R.styleable.Toolbar_subtitleTextColor, -1));
        }
        object.recycle();
    }

    private void addCustomViewsWithGravity(List<View> list, int n) {
        int n2 = ViewCompat.getLayoutDirection((View)this);
        int n3 = 0;
        n2 = n2 == 1 ? 1 : 0;
        int n4 = this.getChildCount();
        int n5 = GravityCompat.getAbsoluteGravity(n, ViewCompat.getLayoutDirection((View)this));
        list.clear();
        if (n2 != 0) {
            for (n = n4 - 1; n >= 0; --n) {
                View view = this.getChildAt(n);
                LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
                if (layoutParams.mViewType != 0 || !this.shouldLayout(view) || this.getChildHorizontalGravity(layoutParams.gravity) != n5) continue;
                list.add(view);
            }
        } else {
            for (n = n3; n < n4; ++n) {
                View view = this.getChildAt(n);
                LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
                if (layoutParams.mViewType != 0 || !this.shouldLayout(view) || this.getChildHorizontalGravity(layoutParams.gravity) != n5) continue;
                list.add(view);
            }
        }
    }

    private void addSystemView(View view, boolean bl) {
        Object object = view.getLayoutParams();
        object = object == null ? this.generateDefaultLayoutParams() : (!this.checkLayoutParams((ViewGroup.LayoutParams)object) ? this.generateLayoutParams((ViewGroup.LayoutParams)object) : (LayoutParams)((Object)object));
        object.mViewType = 1;
        if (bl && this.mExpandedActionView != null) {
            view.setLayoutParams(object);
            this.mHiddenViews.add(view);
            return;
        }
        this.addView(view, object);
    }

    private void ensureContentInsets() {
        if (this.mContentInsets == null) {
            this.mContentInsets = new RtlSpacingHelper();
        }
    }

    private void ensureLogoView() {
        if (this.mLogoView == null) {
            this.mLogoView = new AppCompatImageView(this.getContext());
        }
    }

    private void ensureMenu() {
        this.ensureMenuView();
        if (this.mMenuView.peekMenu() == null) {
            MenuBuilder menuBuilder = (MenuBuilder)this.mMenuView.getMenu();
            if (this.mExpandedMenuPresenter == null) {
                this.mExpandedMenuPresenter = new ExpandedActionViewMenuPresenter();
            }
            this.mMenuView.setExpandedActionViewsExclusive(true);
            menuBuilder.addMenuPresenter(this.mExpandedMenuPresenter, this.mPopupContext);
        }
    }

    private void ensureMenuView() {
        if (this.mMenuView == null) {
            this.mMenuView = new ActionMenuView(this.getContext());
            this.mMenuView.setPopupTheme(this.mPopupTheme);
            this.mMenuView.setOnMenuItemClickListener(this.mMenuViewItemClickListener);
            this.mMenuView.setMenuCallbacks(this.mActionMenuPresenterCallback, this.mMenuBuilderCallback);
            LayoutParams layoutParams = this.generateDefaultLayoutParams();
            layoutParams.gravity = 8388613 | this.mButtonGravity & 112;
            this.mMenuView.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
            this.addSystemView((View)this.mMenuView, false);
        }
    }

    private void ensureNavButtonView() {
        if (this.mNavButtonView == null) {
            this.mNavButtonView = new AppCompatImageButton(this.getContext(), null, R.attr.toolbarNavigationButtonStyle);
            LayoutParams layoutParams = this.generateDefaultLayoutParams();
            layoutParams.gravity = 8388611 | this.mButtonGravity & 112;
            this.mNavButtonView.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
        }
    }

    private int getChildHorizontalGravity(int n) {
        int n2 = ViewCompat.getLayoutDirection((View)this);
        int n3 = GravityCompat.getAbsoluteGravity(n, n2) & 7;
        if (n3 != 1) {
            n = 3;
            if (n3 != 3 && n3 != 5) {
                if (n2 == 1) {
                    n = 5;
                }
                return n;
            }
        }
        return n3;
    }

    private int getChildTop(View view, int n) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        int n2 = view.getMeasuredHeight();
        n = n > 0 ? (n2 - n) / 2 : 0;
        int n3 = this.getChildVerticalGravity(layoutParams.gravity);
        if (n3 != 48) {
            if (n3 != 80) {
                int n4 = this.getPaddingTop();
                n = this.getPaddingBottom();
                int n5 = this.getHeight();
                n3 = (n5 - n4 - n - n2) / 2;
                if (n3 < layoutParams.topMargin) {
                    n = layoutParams.topMargin;
                } else {
                    n2 = n5 - n - n2 - n3 - n4;
                    n = n3;
                    if (n2 < layoutParams.bottomMargin) {
                        n = Math.max(0, n3 - (layoutParams.bottomMargin - n2));
                    }
                }
                return n4 + n;
            }
            return this.getHeight() - this.getPaddingBottom() - n2 - layoutParams.bottomMargin - n;
        }
        return this.getPaddingTop() - n;
    }

    private int getChildVerticalGravity(int n) {
        if ((n &= 112) != 16 && n != 48 && n != 80) {
            return this.mGravity & 112;
        }
        return n;
    }

    private int getHorizontalMargins(View view) {
        view = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
        return MarginLayoutParamsCompat.getMarginStart((ViewGroup.MarginLayoutParams)view) + MarginLayoutParamsCompat.getMarginEnd((ViewGroup.MarginLayoutParams)view);
    }

    private MenuInflater getMenuInflater() {
        return new SupportMenuInflater(this.getContext());
    }

    private int getVerticalMargins(View view) {
        view = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
        return view.topMargin + view.bottomMargin;
    }

    private int getViewListMeasuredWidth(List<View> list, int[] view) {
        int n;
        int n2 = view[0];
        int n3 = view[1];
        int n4 = list.size();
        int n5 = n = 0;
        while (n < n4) {
            view = list.get(n);
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            n2 = layoutParams.leftMargin - n2;
            n3 = layoutParams.rightMargin - n3;
            int n6 = Math.max(0, n2);
            int n7 = Math.max(0, n3);
            n2 = Math.max(0, - n2);
            n3 = Math.max(0, - n3);
            n5 += n6 + view.getMeasuredWidth() + n7;
            ++n;
        }
        return n5;
    }

    private boolean isChildOrHidden(View view) {
        if (view.getParent() != this && !this.mHiddenViews.contains((Object)view)) {
            return false;
        }
        return true;
    }

    private static boolean isCustomView(View view) {
        if (((LayoutParams)view.getLayoutParams()).mViewType == 0) {
            return true;
        }
        return false;
    }

    private int layoutChildLeft(View view, int n, int[] arrn, int n2) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        int n3 = layoutParams.leftMargin - arrn[0];
        arrn[0] = Math.max(0, - n3);
        n2 = this.getChildTop(view, n2);
        n3 = view.getMeasuredWidth();
        view.layout(n, n2, (n += Math.max(0, n3)) + n3, view.getMeasuredHeight() + n2);
        return n + (n3 + layoutParams.rightMargin);
    }

    private int layoutChildRight(View view, int n, int[] arrn, int n2) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        int n3 = layoutParams.rightMargin - arrn[1];
        arrn[1] = Math.max(0, - n3);
        n2 = this.getChildTop(view, n2);
        n3 = view.getMeasuredWidth();
        view.layout(n - n3, n2, n -= Math.max(0, n3), view.getMeasuredHeight() + n2);
        return n - (n3 + layoutParams.leftMargin);
    }

    private int measureChildCollapseMargins(View view, int n, int n2, int n3, int n4, int[] arrn) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
        int n5 = marginLayoutParams.leftMargin - arrn[0];
        int n6 = marginLayoutParams.rightMargin - arrn[1];
        int n7 = Math.max(0, n5) + Math.max(0, n6);
        arrn[0] = Math.max(0, - n5);
        arrn[1] = Math.max(0, - n6);
        view.measure(Toolbar.getChildMeasureSpec((int)n, (int)(this.getPaddingLeft() + this.getPaddingRight() + n7 + n2), (int)marginLayoutParams.width), Toolbar.getChildMeasureSpec((int)n3, (int)(this.getPaddingTop() + this.getPaddingBottom() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin + n4), (int)marginLayoutParams.height));
        return view.getMeasuredWidth() + n7;
    }

    private void measureChildConstrained(View view, int n, int n2, int n3, int n4, int n5) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
        int n6 = Toolbar.getChildMeasureSpec((int)n, (int)(this.getPaddingLeft() + this.getPaddingRight() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin + n2), (int)marginLayoutParams.width);
        n2 = Toolbar.getChildMeasureSpec((int)n3, (int)(this.getPaddingTop() + this.getPaddingBottom() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin + n4), (int)marginLayoutParams.height);
        n3 = View.MeasureSpec.getMode((int)n2);
        n = n2;
        if (n3 != 1073741824) {
            n = n2;
            if (n5 >= 0) {
                n = n5;
                if (n3 != 0) {
                    n = Math.min(View.MeasureSpec.getSize((int)n2), n5);
                }
                n = View.MeasureSpec.makeMeasureSpec((int)n, (int)1073741824);
            }
        }
        view.measure(n6, n);
    }

    private void postShowOverflowMenu() {
        this.removeCallbacks(this.mShowOverflowMenuRunnable);
        this.post(this.mShowOverflowMenuRunnable);
    }

    private boolean shouldCollapse() {
        if (!this.mCollapsible) {
            return false;
        }
        int n = this.getChildCount();
        for (int i = 0; i < n; ++i) {
            View view = this.getChildAt(i);
            if (!this.shouldLayout(view) || view.getMeasuredWidth() <= 0 || view.getMeasuredHeight() <= 0) continue;
            return false;
        }
        return true;
    }

    private boolean shouldLayout(View view) {
        if (view != null && view.getParent() == this && view.getVisibility() != 8) {
            return true;
        }
        return false;
    }

    void addChildrenForExpandedActionView() {
        for (int i = this.mHiddenViews.size() - 1; i >= 0; --i) {
            this.addView(this.mHiddenViews.get(i));
        }
        this.mHiddenViews.clear();
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public boolean canShowOverflowMenu() {
        if (this.getVisibility() == 0 && this.mMenuView != null && this.mMenuView.isOverflowReserved()) {
            return true;
        }
        return false;
    }

    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (super.checkLayoutParams(layoutParams) && layoutParams instanceof LayoutParams) {
            return true;
        }
        return false;
    }

    public void collapseActionView() {
        MenuItemImpl menuItemImpl = this.mExpandedMenuPresenter == null ? null : this.mExpandedMenuPresenter.mCurrentExpandedItem;
        if (menuItemImpl != null) {
            menuItemImpl.collapseActionView();
        }
    }

    public void dismissPopupMenus() {
        if (this.mMenuView != null) {
            this.mMenuView.dismissPopupMenus();
        }
    }

    void ensureCollapseButtonView() {
        if (this.mCollapseButtonView == null) {
            this.mCollapseButtonView = new AppCompatImageButton(this.getContext(), null, R.attr.toolbarNavigationButtonStyle);
            this.mCollapseButtonView.setImageDrawable(this.mCollapseIcon);
            this.mCollapseButtonView.setContentDescription(this.mCollapseDescription);
            LayoutParams layoutParams = this.generateDefaultLayoutParams();
            layoutParams.gravity = 8388611 | this.mButtonGravity & 112;
            layoutParams.mViewType = 2;
            this.mCollapseButtonView.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
            this.mCollapseButtonView.setOnClickListener(new View.OnClickListener(){

                public void onClick(View view) {
                    Toolbar.this.collapseActionView();
                }
            });
        }
    }

    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
    }

    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(this.getContext(), attributeSet);
    }

    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof LayoutParams) {
            return new LayoutParams((LayoutParams)layoutParams);
        }
        if (layoutParams instanceof ActionBar.LayoutParams) {
            return new LayoutParams((ActionBar.LayoutParams)layoutParams);
        }
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            return new LayoutParams((ViewGroup.MarginLayoutParams)layoutParams);
        }
        return new LayoutParams(layoutParams);
    }

    public int getContentInsetEnd() {
        if (this.mContentInsets != null) {
            return this.mContentInsets.getEnd();
        }
        return 0;
    }

    public int getContentInsetEndWithActions() {
        if (this.mContentInsetEndWithActions != Integer.MIN_VALUE) {
            return this.mContentInsetEndWithActions;
        }
        return this.getContentInsetEnd();
    }

    public int getContentInsetLeft() {
        if (this.mContentInsets != null) {
            return this.mContentInsets.getLeft();
        }
        return 0;
    }

    public int getContentInsetRight() {
        if (this.mContentInsets != null) {
            return this.mContentInsets.getRight();
        }
        return 0;
    }

    public int getContentInsetStart() {
        if (this.mContentInsets != null) {
            return this.mContentInsets.getStart();
        }
        return 0;
    }

    public int getContentInsetStartWithNavigation() {
        if (this.mContentInsetStartWithNavigation != Integer.MIN_VALUE) {
            return this.mContentInsetStartWithNavigation;
        }
        return this.getContentInsetStart();
    }

    public int getCurrentContentInsetEnd() {
        MenuBuilder menuBuilder;
        boolean bl = this.mMenuView != null && (menuBuilder = this.mMenuView.peekMenu()) != null && menuBuilder.hasVisibleItems();
        if (bl) {
            return Math.max(this.getContentInsetEnd(), Math.max(this.mContentInsetEndWithActions, 0));
        }
        return this.getContentInsetEnd();
    }

    public int getCurrentContentInsetLeft() {
        if (ViewCompat.getLayoutDirection((View)this) == 1) {
            return this.getCurrentContentInsetEnd();
        }
        return this.getCurrentContentInsetStart();
    }

    public int getCurrentContentInsetRight() {
        if (ViewCompat.getLayoutDirection((View)this) == 1) {
            return this.getCurrentContentInsetStart();
        }
        return this.getCurrentContentInsetEnd();
    }

    public int getCurrentContentInsetStart() {
        if (this.getNavigationIcon() != null) {
            return Math.max(this.getContentInsetStart(), Math.max(this.mContentInsetStartWithNavigation, 0));
        }
        return this.getContentInsetStart();
    }

    public Drawable getLogo() {
        if (this.mLogoView != null) {
            return this.mLogoView.getDrawable();
        }
        return null;
    }

    public CharSequence getLogoDescription() {
        if (this.mLogoView != null) {
            return this.mLogoView.getContentDescription();
        }
        return null;
    }

    public Menu getMenu() {
        this.ensureMenu();
        return this.mMenuView.getMenu();
    }

    @Nullable
    public CharSequence getNavigationContentDescription() {
        if (this.mNavButtonView != null) {
            return this.mNavButtonView.getContentDescription();
        }
        return null;
    }

    @Nullable
    public Drawable getNavigationIcon() {
        if (this.mNavButtonView != null) {
            return this.mNavButtonView.getDrawable();
        }
        return null;
    }

    ActionMenuPresenter getOuterActionMenuPresenter() {
        return this.mOuterActionMenuPresenter;
    }

    @Nullable
    public Drawable getOverflowIcon() {
        this.ensureMenu();
        return this.mMenuView.getOverflowIcon();
    }

    Context getPopupContext() {
        return this.mPopupContext;
    }

    public int getPopupTheme() {
        return this.mPopupTheme;
    }

    public CharSequence getSubtitle() {
        return this.mSubtitleText;
    }

    public CharSequence getTitle() {
        return this.mTitleText;
    }

    public int getTitleMarginBottom() {
        return this.mTitleMarginBottom;
    }

    public int getTitleMarginEnd() {
        return this.mTitleMarginEnd;
    }

    public int getTitleMarginStart() {
        return this.mTitleMarginStart;
    }

    public int getTitleMarginTop() {
        return this.mTitleMarginTop;
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public DecorToolbar getWrapper() {
        if (this.mWrapper == null) {
            this.mWrapper = new ToolbarWidgetWrapper(this, true);
        }
        return this.mWrapper;
    }

    public boolean hasExpandedActionView() {
        if (this.mExpandedMenuPresenter != null && this.mExpandedMenuPresenter.mCurrentExpandedItem != null) {
            return true;
        }
        return false;
    }

    public boolean hideOverflowMenu() {
        if (this.mMenuView != null && this.mMenuView.hideOverflowMenu()) {
            return true;
        }
        return false;
    }

    public void inflateMenu(@MenuRes int n) {
        this.getMenuInflater().inflate(n, this.getMenu());
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public boolean isOverflowMenuShowPending() {
        if (this.mMenuView != null && this.mMenuView.isOverflowMenuShowPending()) {
            return true;
        }
        return false;
    }

    public boolean isOverflowMenuShowing() {
        if (this.mMenuView != null && this.mMenuView.isOverflowMenuShowing()) {
            return true;
        }
        return false;
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public boolean isTitleTruncated() {
        if (this.mTitleTextView == null) {
            return false;
        }
        Layout layout = this.mTitleTextView.getLayout();
        if (layout == null) {
            return false;
        }
        int n = layout.getLineCount();
        for (int i = 0; i < n; ++i) {
            if (layout.getEllipsisCount(i) <= 0) continue;
            return true;
        }
        return false;
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.removeCallbacks(this.mShowOverflowMenuRunnable);
    }

    public boolean onHoverEvent(MotionEvent motionEvent) {
        int n = motionEvent.getActionMasked();
        if (n == 9) {
            this.mEatingHover = false;
        }
        if (!this.mEatingHover) {
            boolean bl = super.onHoverEvent(motionEvent);
            if (n == 9 && !bl) {
                this.mEatingHover = true;
            }
        }
        if (n == 10 || n == 3) {
            this.mEatingHover = false;
        }
        return true;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    protected void onLayout(boolean var1_1, int var2_2, int var3_3, int var4_4, int var5_5) {
        block45 : {
            block43 : {
                block44 : {
                    block41 : {
                        block42 : {
                            var8_6 = ViewCompat.getLayoutDirection((View)this) == 1 ? 1 : 0;
                            var10_7 = this.getWidth();
                            var13_8 = this.getHeight();
                            var6_9 = this.getPaddingLeft();
                            var11_10 = this.getPaddingRight();
                            var12_11 = this.getPaddingTop();
                            var14_12 = this.getPaddingBottom();
                            var9_13 = var10_7 - var11_10;
                            var18_14 = this.mTempMargins;
                            var18_14[1] = 0;
                            var18_14[0] = 0;
                            var2_2 = ViewCompat.getMinimumHeight((View)this);
                            var5_5 = var2_2 >= 0 ? Math.min(var2_2, var5_5 - var3_3) : 0;
                            if (!this.shouldLayout((View)this.mNavButtonView)) break block41;
                            if (var8_6 == 0) break block42;
                            var4_4 = this.layoutChildRight((View)this.mNavButtonView, var9_13, var18_14, var5_5);
                            var7_15 = var6_9;
                            break block43;
                        }
                        var7_15 = this.layoutChildLeft((View)this.mNavButtonView, var6_9, var18_14, var5_5);
                        break block44;
                    }
                    var7_15 = var6_9;
                }
                var4_4 = var9_13;
            }
            var2_2 = var4_4;
            var3_3 = var7_15;
            if (this.shouldLayout((View)this.mCollapseButtonView)) {
                if (var8_6 != 0) {
                    var2_2 = this.layoutChildRight((View)this.mCollapseButtonView, var4_4, var18_14, var5_5);
                    var3_3 = var7_15;
                } else {
                    var3_3 = this.layoutChildLeft((View)this.mCollapseButtonView, var7_15, var18_14, var5_5);
                    var2_2 = var4_4;
                }
            }
            var4_4 = var2_2;
            var7_15 = var3_3;
            if (this.shouldLayout((View)this.mMenuView)) {
                if (var8_6 != 0) {
                    var7_15 = this.layoutChildLeft((View)this.mMenuView, var3_3, var18_14, var5_5);
                    var4_4 = var2_2;
                } else {
                    var4_4 = this.layoutChildRight((View)this.mMenuView, var2_2, var18_14, var5_5);
                    var7_15 = var3_3;
                }
            }
            var3_3 = this.getCurrentContentInsetLeft();
            var2_2 = this.getCurrentContentInsetRight();
            var18_14[0] = Math.max(0, var3_3 - var7_15);
            var18_14[1] = Math.max(0, var2_2 - (var9_13 - var4_4));
            var3_3 = Math.max(var7_15, var3_3);
            var4_4 = Math.min(var4_4, var9_13 - var2_2);
            var2_2 = var3_3;
            var7_15 = var4_4;
            if (this.shouldLayout(this.mExpandedActionView)) {
                if (var8_6 != 0) {
                    var7_15 = this.layoutChildRight(this.mExpandedActionView, var4_4, var18_14, var5_5);
                    var2_2 = var3_3;
                } else {
                    var2_2 = this.layoutChildLeft(this.mExpandedActionView, var3_3, var18_14, var5_5);
                    var7_15 = var4_4;
                }
            }
            var4_4 = var2_2;
            var3_3 = var7_15;
            if (this.shouldLayout((View)this.mLogoView)) {
                if (var8_6 != 0) {
                    var3_3 = this.layoutChildRight((View)this.mLogoView, var7_15, var18_14, var5_5);
                    var4_4 = var2_2;
                } else {
                    var4_4 = this.layoutChildLeft((View)this.mLogoView, var2_2, var18_14, var5_5);
                    var3_3 = var7_15;
                }
            }
            var1_1 = this.shouldLayout((View)this.mTitleTextView);
            var15_16 = this.shouldLayout((View)this.mSubtitleTextView);
            if (var1_1) {
                var16_17 = (LayoutParams)this.mTitleTextView.getLayoutParams();
                var2_2 = var16_17.topMargin + this.mTitleTextView.getMeasuredHeight() + var16_17.bottomMargin + 0;
            } else {
                var2_2 = 0;
            }
            if (var15_16) {
                var16_17 = (LayoutParams)this.mSubtitleTextView.getLayoutParams();
                var2_2 += var16_17.topMargin + this.mSubtitleTextView.getMeasuredHeight() + var16_17.bottomMargin;
            }
            if (var1_1 || var15_16) break block45;
            var2_2 = var4_4;
            ** GOTO lbl135
        }
        var16_17 = var1_1 != false ? this.mTitleTextView : this.mSubtitleTextView;
        if (var15_16) {
            var17_19 = this.mSubtitleTextView;
        } else {
            var17_20 = this.mTitleTextView;
        }
        var16_17 = (LayoutParams)var16_17.getLayoutParams();
        var17_18 = (LayoutParams)var17_21.getLayoutParams();
        var7_15 = var1_1 != false && this.mTitleTextView.getMeasuredWidth() > 0 || var15_16 != false && this.mSubtitleTextView.getMeasuredWidth() > 0 ? 1 : 0;
        var9_13 = this.mGravity & 112;
        if (var9_13 != 48) {
            if (var9_13 != 80) {
                var9_13 = (var13_8 - var12_11 - var14_12 - var2_2) / 2;
                if (var9_13 < var16_17.topMargin + this.mTitleMarginTop) {
                    var2_2 = var16_17.topMargin + this.mTitleMarginTop;
                } else {
                    var13_8 = var13_8 - var14_12 - var2_2 - var9_13 - var12_11;
                    var2_2 = var9_13;
                    if (var13_8 < var16_17.bottomMargin + this.mTitleMarginBottom) {
                        var2_2 = Math.max(0, var9_13 - (var17_18.bottomMargin + this.mTitleMarginBottom - var13_8));
                    }
                }
                var2_2 = var12_11 + var2_2;
            } else {
                var2_2 = var13_8 - var14_12 - var17_18.bottomMargin - this.mTitleMarginBottom - var2_2;
            }
        } else {
            var2_2 = this.getPaddingTop() + var16_17.topMargin + this.mTitleMarginTop;
        }
        if (var8_6 != 0) {
            var8_6 = var7_15 != 0 ? this.mTitleMarginStart : 0;
            var3_3 -= Math.max(0, var8_6 -= var18_14[1]);
            var18_14[1] = Math.max(0, - var8_6);
            if (var1_1) {
                var16_17 = (LayoutParams)this.mTitleTextView.getLayoutParams();
                var9_13 = var3_3 - this.mTitleTextView.getMeasuredWidth();
                var8_6 = this.mTitleTextView.getMeasuredHeight() + var2_2;
                this.mTitleTextView.layout(var9_13, var2_2, var3_3, var8_6);
                var2_2 = var9_13 - this.mTitleMarginEnd;
                var9_13 = var8_6 + var16_17.bottomMargin;
            } else {
                var8_6 = var3_3;
                var9_13 = var2_2;
                var2_2 = var8_6;
            }
            if (var15_16) {
                var16_17 = (LayoutParams)this.mSubtitleTextView.getLayoutParams();
                var8_6 = var9_13 + var16_17.topMargin;
                var9_13 = this.mSubtitleTextView.getMeasuredWidth();
                var12_11 = this.mSubtitleTextView.getMeasuredHeight();
                this.mSubtitleTextView.layout(var3_3 - var9_13, var8_6, var3_3, var12_11 + var8_6);
                var8_6 = var3_3 - this.mTitleMarginEnd;
                var9_13 = var16_17.bottomMargin;
            } else {
                var8_6 = var3_3;
            }
            if (var7_15 != 0) {
                var3_3 = Math.min(var2_2, var8_6);
            }
            var2_2 = var4_4;
lbl135: // 2 sources:
            var4_4 = var3_3;
        } else {
            var8_6 = var7_15 != 0 ? this.mTitleMarginStart : 0;
            var4_4 += Math.max(0, var8_6 -= var18_14[0]);
            var18_14[0] = Math.max(0, - var8_6);
            if (var1_1) {
                var16_17 = (LayoutParams)this.mTitleTextView.getLayoutParams();
                var8_6 = this.mTitleTextView.getMeasuredWidth() + var4_4;
                var9_13 = this.mTitleTextView.getMeasuredHeight() + var2_2;
                this.mTitleTextView.layout(var4_4, var2_2, var8_6, var9_13);
                var8_6 += this.mTitleMarginEnd;
                var2_2 = var9_13 + var16_17.bottomMargin;
            } else {
                var8_6 = var4_4;
            }
            if (var15_16) {
                var16_17 = (LayoutParams)this.mSubtitleTextView.getLayoutParams();
                var9_13 = this.mSubtitleTextView.getMeasuredWidth() + var4_4;
                var12_11 = this.mSubtitleTextView.getMeasuredHeight();
                this.mSubtitleTextView.layout(var4_4, var2_2, var9_13, var12_11 + (var2_2 += var16_17.topMargin));
                var9_13 += this.mTitleMarginEnd;
                var2_2 = var16_17.bottomMargin;
            } else {
                var9_13 = var4_4;
            }
            var2_2 = var4_4;
            var4_4 = var3_3;
            if (var7_15 != 0) {
                var2_2 = Math.max(var8_6, var9_13);
                var4_4 = var3_3;
            }
        }
        var7_15 = var6_9;
        var6_9 = 0;
        this.addCustomViewsWithGravity(this.mTempViews, 3);
        var8_6 = this.mTempViews.size();
        for (var3_3 = 0; var3_3 < var8_6; ++var3_3) {
            var2_2 = this.layoutChildLeft(this.mTempViews.get(var3_3), var2_2, var18_14, var5_5);
        }
        this.addCustomViewsWithGravity(this.mTempViews, 5);
        var8_6 = this.mTempViews.size();
        for (var3_3 = 0; var3_3 < var8_6; ++var3_3) {
            var4_4 = this.layoutChildRight(this.mTempViews.get(var3_3), var4_4, var18_14, var5_5);
        }
        this.addCustomViewsWithGravity(this.mTempViews, 1);
        var8_6 = this.getViewListMeasuredWidth(this.mTempViews, var18_14);
        var3_3 = var7_15 + (var10_7 - var7_15 - var11_10) / 2 - var8_6 / 2;
        var7_15 = var8_6 + var3_3;
        if (var3_3 >= var2_2) {
            var2_2 = var7_15 > var4_4 ? var3_3 - (var7_15 - var4_4) : var3_3;
        }
        var4_4 = this.mTempViews.size();
        var3_3 = var6_9;
        do {
            if (var3_3 >= var4_4) {
                this.mTempViews.clear();
                return;
            }
            var2_2 = this.layoutChildLeft(this.mTempViews.get(var3_3), var2_2, var18_14, var5_5);
            ++var3_3;
        } while (true);
    }

    protected void onMeasure(int n, int n2) {
        int n3;
        int n4;
        int n5;
        int n6;
        int n7;
        int[] arrn = this.mTempMargins;
        if (ViewUtils.isLayoutRtl((View)this)) {
            n5 = 1;
            n6 = 0;
        } else {
            n6 = 1;
            n5 = 0;
        }
        if (this.shouldLayout((View)this.mNavButtonView)) {
            this.measureChildConstrained((View)this.mNavButtonView, n, 0, n2, 0, this.mMaxButtonHeight);
            n4 = this.mNavButtonView.getMeasuredWidth() + this.getHorizontalMargins((View)this.mNavButtonView);
            n3 = Math.max(0, this.mNavButtonView.getMeasuredHeight() + this.getVerticalMargins((View)this.mNavButtonView));
            n7 = View.combineMeasuredStates((int)0, (int)this.mNavButtonView.getMeasuredState());
        } else {
            n7 = n3 = (n4 = 0);
        }
        int n8 = n4;
        int n9 = n3;
        n4 = n7;
        if (this.shouldLayout((View)this.mCollapseButtonView)) {
            this.measureChildConstrained((View)this.mCollapseButtonView, n, 0, n2, 0, this.mMaxButtonHeight);
            n8 = this.mCollapseButtonView.getMeasuredWidth() + this.getHorizontalMargins((View)this.mCollapseButtonView);
            n9 = Math.max(n3, this.mCollapseButtonView.getMeasuredHeight() + this.getVerticalMargins((View)this.mCollapseButtonView));
            n4 = View.combineMeasuredStates((int)n7, (int)this.mCollapseButtonView.getMeasuredState());
        }
        n7 = this.getCurrentContentInsetStart();
        n3 = 0 + Math.max(n7, n8);
        arrn[n5] = Math.max(0, n7 - n8);
        if (this.shouldLayout((View)this.mMenuView)) {
            this.measureChildConstrained((View)this.mMenuView, n, n3, n2, 0, this.mMaxButtonHeight);
            n7 = this.mMenuView.getMeasuredWidth() + this.getHorizontalMargins((View)this.mMenuView);
            n9 = Math.max(n9, this.mMenuView.getMeasuredHeight() + this.getVerticalMargins((View)this.mMenuView));
            n4 = View.combineMeasuredStates((int)n4, (int)this.mMenuView.getMeasuredState());
        } else {
            n7 = 0;
        }
        n8 = this.getCurrentContentInsetEnd();
        n5 = n3 + Math.max(n8, n7);
        arrn[n6] = Math.max(0, n8 - n7);
        n6 = n5;
        n3 = n9;
        n7 = n4;
        if (this.shouldLayout(this.mExpandedActionView)) {
            n6 = n5 + this.measureChildCollapseMargins(this.mExpandedActionView, n, n5, n2, 0, arrn);
            n3 = Math.max(n9, this.mExpandedActionView.getMeasuredHeight() + this.getVerticalMargins(this.mExpandedActionView));
            n7 = View.combineMeasuredStates((int)n4, (int)this.mExpandedActionView.getMeasuredState());
        }
        n9 = n6;
        n5 = n3;
        n4 = n7;
        if (this.shouldLayout((View)this.mLogoView)) {
            n9 = n6 + this.measureChildCollapseMargins((View)this.mLogoView, n, n6, n2, 0, arrn);
            n5 = Math.max(n3, this.mLogoView.getMeasuredHeight() + this.getVerticalMargins((View)this.mLogoView));
            n4 = View.combineMeasuredStates((int)n7, (int)this.mLogoView.getMeasuredState());
        }
        int n10 = this.getChildCount();
        n6 = n5;
        n7 = 0;
        n3 = n9;
        for (n9 = n7; n9 < n10; ++n9) {
            View view = this.getChildAt(n9);
            n8 = n3;
            n5 = n4;
            n7 = n6;
            if (((LayoutParams)view.getLayoutParams()).mViewType == 0) {
                if (!this.shouldLayout(view)) {
                    n8 = n3;
                    n5 = n4;
                    n7 = n6;
                } else {
                    n8 = n3 + this.measureChildCollapseMargins(view, n, n3, n2, 0, arrn);
                    n7 = Math.max(n6, view.getMeasuredHeight() + this.getVerticalMargins(view));
                    n5 = View.combineMeasuredStates((int)n4, (int)view.getMeasuredState());
                }
            }
            n3 = n8;
            n4 = n5;
            n6 = n7;
        }
        n5 = this.mTitleMarginTop + this.mTitleMarginBottom;
        n8 = this.mTitleMarginStart + this.mTitleMarginEnd;
        if (this.shouldLayout((View)this.mTitleTextView)) {
            this.measureChildCollapseMargins((View)this.mTitleTextView, n, n3 + n8, n2, n5, arrn);
            n9 = this.mTitleTextView.getMeasuredWidth();
            n10 = this.getHorizontalMargins((View)this.mTitleTextView);
            n7 = this.mTitleTextView.getMeasuredHeight();
            int n11 = this.getVerticalMargins((View)this.mTitleTextView);
            n4 = View.combineMeasuredStates((int)n4, (int)this.mTitleTextView.getMeasuredState());
            n7 += n11;
            n9 += n10;
        } else {
            n9 = n7 = 0;
        }
        if (this.shouldLayout((View)this.mSubtitleTextView)) {
            n9 = Math.max(n9, this.measureChildCollapseMargins((View)this.mSubtitleTextView, n, n3 + n8, n2, n7 + n5, arrn));
            n7 += this.mSubtitleTextView.getMeasuredHeight() + this.getVerticalMargins((View)this.mSubtitleTextView);
            n4 = View.combineMeasuredStates((int)n4, (int)this.mSubtitleTextView.getMeasuredState());
        }
        n7 = Math.max(n6, n7);
        n8 = this.getPaddingLeft();
        n10 = this.getPaddingRight();
        n6 = this.getPaddingTop();
        n5 = this.getPaddingBottom();
        n9 = View.resolveSizeAndState((int)Math.max(n3 + n9 + (n8 + n10), this.getSuggestedMinimumWidth()), (int)n, (int)(-16777216 & n4));
        n = View.resolveSizeAndState((int)Math.max(n7 + (n6 + n5), this.getSuggestedMinimumHeight()), (int)n2, (int)(n4 << 16));
        if (this.shouldCollapse()) {
            n = 0;
        }
        this.setMeasuredDimension(n9, n);
    }

    protected void onRestoreInstanceState(Parcelable object) {
        if (!(object instanceof SavedState)) {
            super.onRestoreInstanceState(object);
            return;
        }
        SavedState savedState = (SavedState)object;
        super.onRestoreInstanceState(savedState.getSuperState());
        object = this.mMenuView != null ? this.mMenuView.peekMenu() : null;
        if (savedState.expandedMenuItemId != 0 && this.mExpandedMenuPresenter != null && object != null && (object = object.findItem(savedState.expandedMenuItemId)) != null) {
            object.expandActionView();
        }
        if (savedState.isOverflowOpen) {
            this.postShowOverflowMenu();
        }
    }

    public void onRtlPropertiesChanged(int n) {
        if (Build.VERSION.SDK_INT >= 17) {
            super.onRtlPropertiesChanged(n);
        }
        this.ensureContentInsets();
        RtlSpacingHelper rtlSpacingHelper = this.mContentInsets;
        boolean bl = true;
        if (n != 1) {
            bl = false;
        }
        rtlSpacingHelper.setDirection(bl);
    }

    protected Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        if (this.mExpandedMenuPresenter != null && this.mExpandedMenuPresenter.mCurrentExpandedItem != null) {
            savedState.expandedMenuItemId = this.mExpandedMenuPresenter.mCurrentExpandedItem.getItemId();
        }
        savedState.isOverflowOpen = this.isOverflowMenuShowing();
        return savedState;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        int n = motionEvent.getActionMasked();
        if (n == 0) {
            this.mEatingTouch = false;
        }
        if (!this.mEatingTouch) {
            boolean bl = super.onTouchEvent(motionEvent);
            if (n == 0 && !bl) {
                this.mEatingTouch = true;
            }
        }
        if (n == 1 || n == 3) {
            this.mEatingTouch = false;
        }
        return true;
    }

    void removeChildrenForExpandedActionView() {
        for (int i = this.getChildCount() - 1; i >= 0; --i) {
            View view = this.getChildAt(i);
            if (((LayoutParams)view.getLayoutParams()).mViewType == 2 || view == this.mMenuView) continue;
            this.removeViewAt(i);
            this.mHiddenViews.add(view);
        }
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public void setCollapsible(boolean bl) {
        this.mCollapsible = bl;
        this.requestLayout();
    }

    public void setContentInsetEndWithActions(int n) {
        int n2 = n;
        if (n < 0) {
            n2 = Integer.MIN_VALUE;
        }
        if (n2 != this.mContentInsetEndWithActions) {
            this.mContentInsetEndWithActions = n2;
            if (this.getNavigationIcon() != null) {
                this.requestLayout();
            }
        }
    }

    public void setContentInsetStartWithNavigation(int n) {
        int n2 = n;
        if (n < 0) {
            n2 = Integer.MIN_VALUE;
        }
        if (n2 != this.mContentInsetStartWithNavigation) {
            this.mContentInsetStartWithNavigation = n2;
            if (this.getNavigationIcon() != null) {
                this.requestLayout();
            }
        }
    }

    public void setContentInsetsAbsolute(int n, int n2) {
        this.ensureContentInsets();
        this.mContentInsets.setAbsolute(n, n2);
    }

    public void setContentInsetsRelative(int n, int n2) {
        this.ensureContentInsets();
        this.mContentInsets.setRelative(n, n2);
    }

    public void setLogo(@DrawableRes int n) {
        this.setLogo(AppCompatResources.getDrawable(this.getContext(), n));
    }

    public void setLogo(Drawable drawable) {
        if (drawable != null) {
            this.ensureLogoView();
            if (!this.isChildOrHidden((View)this.mLogoView)) {
                this.addSystemView((View)this.mLogoView, true);
            }
        } else if (this.mLogoView != null && this.isChildOrHidden((View)this.mLogoView)) {
            this.removeView((View)this.mLogoView);
            this.mHiddenViews.remove((Object)this.mLogoView);
        }
        if (this.mLogoView != null) {
            this.mLogoView.setImageDrawable(drawable);
        }
    }

    public void setLogoDescription(@StringRes int n) {
        this.setLogoDescription(this.getContext().getText(n));
    }

    public void setLogoDescription(CharSequence charSequence) {
        if (!TextUtils.isEmpty((CharSequence)charSequence)) {
            this.ensureLogoView();
        }
        if (this.mLogoView != null) {
            this.mLogoView.setContentDescription(charSequence);
        }
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public void setMenu(MenuBuilder menuBuilder, ActionMenuPresenter actionMenuPresenter) {
        if (menuBuilder == null && this.mMenuView == null) {
            return;
        }
        this.ensureMenuView();
        MenuBuilder menuBuilder2 = this.mMenuView.peekMenu();
        if (menuBuilder2 == menuBuilder) {
            return;
        }
        if (menuBuilder2 != null) {
            menuBuilder2.removeMenuPresenter(this.mOuterActionMenuPresenter);
            menuBuilder2.removeMenuPresenter(this.mExpandedMenuPresenter);
        }
        if (this.mExpandedMenuPresenter == null) {
            this.mExpandedMenuPresenter = new ExpandedActionViewMenuPresenter();
        }
        actionMenuPresenter.setExpandedActionViewsExclusive(true);
        if (menuBuilder != null) {
            menuBuilder.addMenuPresenter(actionMenuPresenter, this.mPopupContext);
            menuBuilder.addMenuPresenter(this.mExpandedMenuPresenter, this.mPopupContext);
        } else {
            actionMenuPresenter.initForMenu(this.mPopupContext, null);
            this.mExpandedMenuPresenter.initForMenu(this.mPopupContext, null);
            actionMenuPresenter.updateMenuView(true);
            this.mExpandedMenuPresenter.updateMenuView(true);
        }
        this.mMenuView.setPopupTheme(this.mPopupTheme);
        this.mMenuView.setPresenter(actionMenuPresenter);
        this.mOuterActionMenuPresenter = actionMenuPresenter;
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public void setMenuCallbacks(MenuPresenter.Callback callback, MenuBuilder.Callback callback2) {
        this.mActionMenuPresenterCallback = callback;
        this.mMenuBuilderCallback = callback2;
        if (this.mMenuView != null) {
            this.mMenuView.setMenuCallbacks(callback, callback2);
        }
    }

    public void setNavigationContentDescription(@StringRes int n) {
        CharSequence charSequence = n != 0 ? this.getContext().getText(n) : null;
        this.setNavigationContentDescription(charSequence);
    }

    public void setNavigationContentDescription(@Nullable CharSequence charSequence) {
        if (!TextUtils.isEmpty((CharSequence)charSequence)) {
            this.ensureNavButtonView();
        }
        if (this.mNavButtonView != null) {
            this.mNavButtonView.setContentDescription(charSequence);
        }
    }

    public void setNavigationIcon(@DrawableRes int n) {
        this.setNavigationIcon(AppCompatResources.getDrawable(this.getContext(), n));
    }

    public void setNavigationIcon(@Nullable Drawable drawable) {
        if (drawable != null) {
            this.ensureNavButtonView();
            if (!this.isChildOrHidden((View)this.mNavButtonView)) {
                this.addSystemView((View)this.mNavButtonView, true);
            }
        } else if (this.mNavButtonView != null && this.isChildOrHidden((View)this.mNavButtonView)) {
            this.removeView((View)this.mNavButtonView);
            this.mHiddenViews.remove((Object)this.mNavButtonView);
        }
        if (this.mNavButtonView != null) {
            this.mNavButtonView.setImageDrawable(drawable);
        }
    }

    public void setNavigationOnClickListener(View.OnClickListener onClickListener) {
        this.ensureNavButtonView();
        this.mNavButtonView.setOnClickListener(onClickListener);
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        this.mOnMenuItemClickListener = onMenuItemClickListener;
    }

    public void setOverflowIcon(@Nullable Drawable drawable) {
        this.ensureMenu();
        this.mMenuView.setOverflowIcon(drawable);
    }

    public void setPopupTheme(@StyleRes int n) {
        if (this.mPopupTheme != n) {
            this.mPopupTheme = n;
            if (n == 0) {
                this.mPopupContext = this.getContext();
                return;
            }
            this.mPopupContext = new ContextThemeWrapper(this.getContext(), n);
        }
    }

    public void setSubtitle(@StringRes int n) {
        this.setSubtitle(this.getContext().getText(n));
    }

    public void setSubtitle(CharSequence charSequence) {
        if (!TextUtils.isEmpty((CharSequence)charSequence)) {
            if (this.mSubtitleTextView == null) {
                Context context = this.getContext();
                this.mSubtitleTextView = new AppCompatTextView(context);
                this.mSubtitleTextView.setSingleLine();
                this.mSubtitleTextView.setEllipsize(TextUtils.TruncateAt.END);
                if (this.mSubtitleTextAppearance != 0) {
                    this.mSubtitleTextView.setTextAppearance(context, this.mSubtitleTextAppearance);
                }
                if (this.mSubtitleTextColor != 0) {
                    this.mSubtitleTextView.setTextColor(this.mSubtitleTextColor);
                }
            }
            if (!this.isChildOrHidden((View)this.mSubtitleTextView)) {
                this.addSystemView((View)this.mSubtitleTextView, true);
            }
        } else if (this.mSubtitleTextView != null && this.isChildOrHidden((View)this.mSubtitleTextView)) {
            this.removeView((View)this.mSubtitleTextView);
            this.mHiddenViews.remove((Object)this.mSubtitleTextView);
        }
        if (this.mSubtitleTextView != null) {
            this.mSubtitleTextView.setText(charSequence);
        }
        this.mSubtitleText = charSequence;
    }

    public void setSubtitleTextAppearance(Context context, @StyleRes int n) {
        this.mSubtitleTextAppearance = n;
        if (this.mSubtitleTextView != null) {
            this.mSubtitleTextView.setTextAppearance(context, n);
        }
    }

    public void setSubtitleTextColor(@ColorInt int n) {
        this.mSubtitleTextColor = n;
        if (this.mSubtitleTextView != null) {
            this.mSubtitleTextView.setTextColor(n);
        }
    }

    public void setTitle(@StringRes int n) {
        this.setTitle(this.getContext().getText(n));
    }

    public void setTitle(CharSequence charSequence) {
        if (!TextUtils.isEmpty((CharSequence)charSequence)) {
            if (this.mTitleTextView == null) {
                Context context = this.getContext();
                this.mTitleTextView = new AppCompatTextView(context);
                this.mTitleTextView.setSingleLine();
                this.mTitleTextView.setEllipsize(TextUtils.TruncateAt.END);
                if (this.mTitleTextAppearance != 0) {
                    this.mTitleTextView.setTextAppearance(context, this.mTitleTextAppearance);
                }
                if (this.mTitleTextColor != 0) {
                    this.mTitleTextView.setTextColor(this.mTitleTextColor);
                }
            }
            if (!this.isChildOrHidden((View)this.mTitleTextView)) {
                this.addSystemView((View)this.mTitleTextView, true);
            }
        } else if (this.mTitleTextView != null && this.isChildOrHidden((View)this.mTitleTextView)) {
            this.removeView((View)this.mTitleTextView);
            this.mHiddenViews.remove((Object)this.mTitleTextView);
        }
        if (this.mTitleTextView != null) {
            this.mTitleTextView.setText(charSequence);
        }
        this.mTitleText = charSequence;
    }

    public void setTitleMargin(int n, int n2, int n3, int n4) {
        this.mTitleMarginStart = n;
        this.mTitleMarginTop = n2;
        this.mTitleMarginEnd = n3;
        this.mTitleMarginBottom = n4;
        this.requestLayout();
    }

    public void setTitleMarginBottom(int n) {
        this.mTitleMarginBottom = n;
        this.requestLayout();
    }

    public void setTitleMarginEnd(int n) {
        this.mTitleMarginEnd = n;
        this.requestLayout();
    }

    public void setTitleMarginStart(int n) {
        this.mTitleMarginStart = n;
        this.requestLayout();
    }

    public void setTitleMarginTop(int n) {
        this.mTitleMarginTop = n;
        this.requestLayout();
    }

    public void setTitleTextAppearance(Context context, @StyleRes int n) {
        this.mTitleTextAppearance = n;
        if (this.mTitleTextView != null) {
            this.mTitleTextView.setTextAppearance(context, n);
        }
    }

    public void setTitleTextColor(@ColorInt int n) {
        this.mTitleTextColor = n;
        if (this.mTitleTextView != null) {
            this.mTitleTextView.setTextColor(n);
        }
    }

    public boolean showOverflowMenu() {
        if (this.mMenuView != null && this.mMenuView.showOverflowMenu()) {
            return true;
        }
        return false;
    }

    private class ExpandedActionViewMenuPresenter
    implements MenuPresenter {
        MenuItemImpl mCurrentExpandedItem;
        MenuBuilder mMenu;

        ExpandedActionViewMenuPresenter() {
        }

        @Override
        public boolean collapseItemActionView(MenuBuilder menuBuilder, MenuItemImpl menuItemImpl) {
            if (Toolbar.this.mExpandedActionView instanceof CollapsibleActionView) {
                ((CollapsibleActionView)Toolbar.this.mExpandedActionView).onActionViewCollapsed();
            }
            Toolbar.this.removeView(Toolbar.this.mExpandedActionView);
            Toolbar.this.removeView((View)Toolbar.this.mCollapseButtonView);
            Toolbar.this.mExpandedActionView = null;
            Toolbar.this.addChildrenForExpandedActionView();
            this.mCurrentExpandedItem = null;
            Toolbar.this.requestLayout();
            menuItemImpl.setActionViewExpanded(false);
            return true;
        }

        @Override
        public boolean expandItemActionView(MenuBuilder object, MenuItemImpl menuItemImpl) {
            Toolbar.this.ensureCollapseButtonView();
            object = Toolbar.this.mCollapseButtonView.getParent();
            if (object != Toolbar.this) {
                if (object instanceof ViewGroup) {
                    ((ViewGroup)object).removeView((View)Toolbar.this.mCollapseButtonView);
                }
                Toolbar.this.addView((View)Toolbar.this.mCollapseButtonView);
            }
            Toolbar.this.mExpandedActionView = menuItemImpl.getActionView();
            this.mCurrentExpandedItem = menuItemImpl;
            object = Toolbar.this.mExpandedActionView.getParent();
            if (object != Toolbar.this) {
                if (object instanceof ViewGroup) {
                    ((ViewGroup)object).removeView(Toolbar.this.mExpandedActionView);
                }
                object = Toolbar.this.generateDefaultLayoutParams();
                object.gravity = 8388611 | Toolbar.this.mButtonGravity & 112;
                object.mViewType = 2;
                Toolbar.this.mExpandedActionView.setLayoutParams((ViewGroup.LayoutParams)object);
                Toolbar.this.addView(Toolbar.this.mExpandedActionView);
            }
            Toolbar.this.removeChildrenForExpandedActionView();
            Toolbar.this.requestLayout();
            menuItemImpl.setActionViewExpanded(true);
            if (Toolbar.this.mExpandedActionView instanceof CollapsibleActionView) {
                ((CollapsibleActionView)Toolbar.this.mExpandedActionView).onActionViewExpanded();
            }
            return true;
        }

        @Override
        public boolean flagActionItems() {
            return false;
        }

        @Override
        public int getId() {
            return 0;
        }

        @Override
        public MenuView getMenuView(ViewGroup viewGroup) {
            return null;
        }

        @Override
        public void initForMenu(Context context, MenuBuilder menuBuilder) {
            if (this.mMenu != null && this.mCurrentExpandedItem != null) {
                this.mMenu.collapseItemActionView(this.mCurrentExpandedItem);
            }
            this.mMenu = menuBuilder;
        }

        @Override
        public void onCloseMenu(MenuBuilder menuBuilder, boolean bl) {
        }

        @Override
        public void onRestoreInstanceState(Parcelable parcelable) {
        }

        @Override
        public Parcelable onSaveInstanceState() {
            return null;
        }

        @Override
        public boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
            return false;
        }

        @Override
        public void setCallback(MenuPresenter.Callback callback) {
        }

        @Override
        public void updateMenuView(boolean bl) {
            if (this.mCurrentExpandedItem != null) {
                boolean bl2;
                MenuBuilder menuBuilder = this.mMenu;
                boolean bl3 = bl2 = false;
                if (menuBuilder != null) {
                    int n = this.mMenu.size();
                    int n2 = 0;
                    do {
                        bl3 = bl2;
                        if (n2 >= n) break;
                        if (this.mMenu.getItem(n2) == this.mCurrentExpandedItem) {
                            bl3 = true;
                            break;
                        }
                        ++n2;
                    } while (true);
                }
                if (!bl3) {
                    this.collapseItemActionView(this.mMenu, this.mCurrentExpandedItem);
                }
            }
        }
    }

    public static class LayoutParams
    extends ActionBar.LayoutParams {
        static final int CUSTOM = 0;
        static final int EXPANDED = 2;
        static final int SYSTEM = 1;
        int mViewType = 0;

        public LayoutParams(int n) {
            this(-2, -1, n);
        }

        public LayoutParams(int n, int n2) {
            super(n, n2);
            this.gravity = 8388627;
        }

        public LayoutParams(int n, int n2, int n3) {
            super(n, n2);
            this.gravity = n3;
        }

        public LayoutParams(@NonNull Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(ActionBar.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(LayoutParams layoutParams) {
            super(layoutParams);
            this.mViewType = layoutParams.mViewType;
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super((ViewGroup.LayoutParams)marginLayoutParams);
            this.copyMarginsFromCompat(marginLayoutParams);
        }

        void copyMarginsFromCompat(ViewGroup.MarginLayoutParams marginLayoutParams) {
            this.leftMargin = marginLayoutParams.leftMargin;
            this.topMargin = marginLayoutParams.topMargin;
            this.rightMargin = marginLayoutParams.rightMargin;
            this.bottomMargin = marginLayoutParams.bottomMargin;
        }
    }

    public static interface OnMenuItemClickListener {
        public boolean onMenuItemClick(MenuItem var1);
    }

    public static class SavedState
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
        int expandedMenuItemId;
        boolean isOverflowOpen;

        public SavedState(Parcel parcel) {
            this(parcel, null);
        }

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.expandedMenuItemId = parcel.readInt();
            boolean bl = parcel.readInt() != 0;
            this.isOverflowOpen = bl;
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            RuntimeException runtimeException;
            super("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.provideAs(TypeTransformer.java:780)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.e1expr(TypeTransformer.java:496)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:713)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:698)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s1stmt(TypeTransformer.java:810)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:840)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:269)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
            throw runtimeException;
        }

    }

}
