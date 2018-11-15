/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.view.ContextThemeWrapper
 *  android.view.Menu
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewDebug
 *  android.view.ViewDebug$ExportedProperty
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.accessibility.AccessibilityEvent
 */
package android.support.v7.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.StyleRes;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.view.menu.MenuPresenter;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.ActionMenuPresenter;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.ViewUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;

public class ActionMenuView
extends LinearLayoutCompat
implements MenuBuilder.ItemInvoker,
MenuView {
    static final int GENERATED_ITEM_PADDING = 4;
    static final int MIN_CELL_SIZE = 56;
    private static final String TAG = "ActionMenuView";
    private MenuPresenter.Callback mActionMenuPresenterCallback;
    private boolean mFormatItems;
    private int mFormatItemsWidth;
    private int mGeneratedItemPadding;
    private MenuBuilder mMenu;
    MenuBuilder.Callback mMenuBuilderCallback;
    private int mMinCellSize;
    OnMenuItemClickListener mOnMenuItemClickListener;
    private Context mPopupContext;
    private int mPopupTheme;
    private ActionMenuPresenter mPresenter;
    private boolean mReserveOverflow;

    public ActionMenuView(Context context) {
        this(context, null);
    }

    public ActionMenuView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.setBaselineAligned(false);
        float f = context.getResources().getDisplayMetrics().density;
        this.mMinCellSize = (int)(56.0f * f);
        this.mGeneratedItemPadding = (int)(4.0f * f);
        this.mPopupContext = context;
        this.mPopupTheme = 0;
    }

    static int measureChildForCells(View view, int n, int n2, int n3, int n4) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        int n5 = View.MeasureSpec.makeMeasureSpec((int)(View.MeasureSpec.getSize((int)n3) - n4), (int)View.MeasureSpec.getMode((int)n3));
        ActionMenuItemView actionMenuItemView = view instanceof ActionMenuItemView ? (ActionMenuItemView)view : null;
        boolean bl = false;
        n3 = actionMenuItemView != null && actionMenuItemView.hasText() ? 1 : 0;
        n4 = 2;
        if (n2 > 0 && (n3 == 0 || n2 >= 2)) {
            int n6;
            view.measure(View.MeasureSpec.makeMeasureSpec((int)(n2 * n), (int)Integer.MIN_VALUE), n5);
            int n7 = view.getMeasuredWidth();
            n2 = n6 = n7 / n;
            if (n7 % n != 0) {
                n2 = n6 + 1;
            }
            if (n3 != 0 && n2 < 2) {
                n2 = n4;
            }
        } else {
            n2 = 0;
        }
        boolean bl2 = bl;
        if (!layoutParams.isOverflowButton) {
            bl2 = bl;
            if (n3 != 0) {
                bl2 = true;
            }
        }
        layoutParams.expandable = bl2;
        layoutParams.cellsUsed = n2;
        view.measure(View.MeasureSpec.makeMeasureSpec((int)(n * n2), (int)1073741824), n5);
        return n2;
    }

    private void onMeasureExactFormat(int n, int n2) {
        long l;
        int n3;
        Object object;
        int n4;
        int n5;
        LayoutParams layoutParams;
        int n6 = View.MeasureSpec.getMode((int)n2);
        n = View.MeasureSpec.getSize((int)n);
        int n7 = View.MeasureSpec.getSize((int)n2);
        int n8 = this.getPaddingLeft();
        int n9 = this.getPaddingRight();
        int n10 = this.getPaddingTop() + this.getPaddingBottom();
        int n11 = ActionMenuView.getChildMeasureSpec((int)n2, (int)n10, (int)-2);
        int n12 = n - (n8 + n9);
        int n13 = n12 / this.mMinCellSize;
        n = this.mMinCellSize;
        if (n13 == 0) {
            this.setMeasuredDimension(n12, 0);
            return;
        }
        int n14 = this.mMinCellSize + n12 % n / n13;
        int n15 = this.getChildCount();
        n9 = n = (n2 = (n8 = (n4 = (n5 = 0))));
        long l2 = 0L;
        int n16 = n;
        n = n13;
        int n17 = n2;
        n2 = n7;
        while (n5 < n15) {
            object = this.getChildAt(n5);
            if (object.getVisibility() == 8) {
                n7 = n17;
                n3 = n16;
            } else {
                boolean bl = object instanceof ActionMenuItemView;
                n7 = n17 + 1;
                if (bl) {
                    object.setPadding(this.mGeneratedItemPadding, 0, this.mGeneratedItemPadding, 0);
                }
                layoutParams = (LayoutParams)object.getLayoutParams();
                layoutParams.expanded = false;
                layoutParams.extraPixels = 0;
                layoutParams.cellsUsed = 0;
                layoutParams.expandable = false;
                layoutParams.leftMargin = 0;
                layoutParams.rightMargin = 0;
                bl = bl && ((ActionMenuItemView)object).hasText();
                layoutParams.preventEdgeOffset = bl;
                n13 = layoutParams.isOverflowButton ? 1 : n;
                n17 = ActionMenuView.measureChildForCells(object, n14, n13, n11, n10);
                n3 = Math.max(n16, n17);
                n13 = n9;
                if (layoutParams.expandable) {
                    n13 = n9 + 1;
                }
                if (layoutParams.isOverflowButton) {
                    n8 = 1;
                }
                n -= n17;
                n4 = Math.max(n4, object.getMeasuredHeight());
                if (n17 == 1) {
                    l = 1 << n5;
                    l2 |= l;
                    n9 = n13;
                } else {
                    n9 = n13;
                }
            }
            ++n5;
            n17 = n7;
            n16 = n3;
        }
        n5 = n8 != 0 && n17 == 2 ? 1 : 0;
        n3 = 0;
        n10 = n;
        n7 = n15;
        n13 = n11;
        n = n3;
        while (n9 > 0 && n10 > 0) {
            n11 = Integer.MAX_VALUE;
            int n18 = 0;
            long l3 = 0L;
            for (n15 = 0; n15 < n7; ++n15) {
                int n19;
                object = (LayoutParams)this.getChildAt(n15).getLayoutParams();
                if (!object.expandable) {
                    n3 = n18;
                    n19 = n11;
                    l = l3;
                } else if (object.cellsUsed < n11) {
                    n19 = object.cellsUsed;
                    l = 1L << n15;
                    n3 = 1;
                } else {
                    n3 = n18;
                    n19 = n11;
                    l = l3;
                    if (object.cellsUsed == n11) {
                        n3 = n18 + 1;
                        l = l3 | 1L << n15;
                        n19 = n11;
                    }
                }
                n18 = n3;
                n11 = n19;
                l3 = l;
            }
            l2 |= l3;
            if (n18 > n10) break;
            for (n = 0; n < n7; ++n) {
                object = this.getChildAt(n);
                layoutParams = (LayoutParams)object.getLayoutParams();
                long l4 = 1 << n;
                if ((l3 & l4) == 0L) {
                    n3 = n10;
                    l = l2;
                    if (layoutParams.cellsUsed == n11 + 1) {
                        l = l2 | l4;
                        n3 = n10;
                    }
                } else {
                    if (n5 != 0 && layoutParams.preventEdgeOffset && n10 == 1) {
                        object.setPadding(this.mGeneratedItemPadding + n14, 0, this.mGeneratedItemPadding, 0);
                    }
                    ++layoutParams.cellsUsed;
                    layoutParams.expanded = true;
                    n3 = n10 - 1;
                    l = l2;
                }
                n10 = n3;
                l2 = l;
            }
            n = 1;
        }
        n8 = n8 == 0 && n17 == 1 ? 1 : 0;
        if (n10 > 0 && l2 != 0L && (n10 < n17 - 1 || n8 != 0 || n16 > 1)) {
            float f = Long.bitCount(l2);
            if (n8 == 0) {
                float f2;
                if ((l2 & 1L) != 0L) {
                    f2 = f;
                    if (!((LayoutParams)this.getChildAt((int)0).getLayoutParams()).preventEdgeOffset) {
                        f2 = f - 0.5f;
                    }
                } else {
                    f2 = f;
                }
                n8 = n7 - 1;
                f = f2;
                if ((l2 & (long)(1 << n8)) != 0L) {
                    f = f2;
                    if (!((LayoutParams)this.getChildAt((int)n8).getLayoutParams()).preventEdgeOffset) {
                        f = f2 - 0.5f;
                    }
                }
            }
            n9 = f > 0.0f ? (int)((float)(n10 * n14) / f) : 0;
            n3 = 0;
            n16 = n7;
            do {
                n8 = n;
                if (n3 < n16) {
                    if ((l2 & (long)(1 << n3)) == 0L) {
                        n8 = n;
                    } else {
                        object = this.getChildAt(n3);
                        layoutParams = (LayoutParams)object.getLayoutParams();
                        if (object instanceof ActionMenuItemView) {
                            layoutParams.extraPixels = n9;
                            layoutParams.expanded = true;
                            if (n3 == 0 && !layoutParams.preventEdgeOffset) {
                                layoutParams.leftMargin = (- n9) / 2;
                            }
                            n8 = 1;
                        } else if (layoutParams.isOverflowButton) {
                            layoutParams.extraPixels = n9;
                            layoutParams.expanded = true;
                            layoutParams.rightMargin = (- n9) / 2;
                            n8 = 1;
                        } else {
                            if (n3 != 0) {
                                layoutParams.leftMargin = n9 / 2;
                            }
                            n8 = n;
                            if (n3 != n16 - 1) {
                                layoutParams.rightMargin = n9 / 2;
                                n8 = n;
                            }
                        }
                    }
                    ++n3;
                    n = n8;
                    continue;
                }
                break;
            } while (true);
        } else {
            n8 = n;
        }
        if (n8 != 0) {
            for (n = 0; n < n7; ++n) {
                object = this.getChildAt(n);
                layoutParams = (LayoutParams)object.getLayoutParams();
                if (!layoutParams.expanded) continue;
                object.measure(View.MeasureSpec.makeMeasureSpec((int)(layoutParams.cellsUsed * n14 + layoutParams.extraPixels), (int)1073741824), n13);
            }
        }
        if (n6 != 1073741824) {
            n2 = n4;
        }
        this.setMeasuredDimension(n12, n2);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams != null && layoutParams instanceof LayoutParams) {
            return true;
        }
        return false;
    }

    public void dismissPopupMenus() {
        if (this.mPresenter != null) {
            this.mPresenter.dismissPopupMenus();
        }
    }

    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        return false;
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        LayoutParams layoutParams = new LayoutParams(-2, -2);
        layoutParams.gravity = 16;
        return layoutParams;
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(this.getContext(), attributeSet);
    }

    @Override
    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams object) {
        if (object != null) {
            object = object instanceof LayoutParams ? new LayoutParams((LayoutParams)((Object)object)) : new LayoutParams((ViewGroup.LayoutParams)object);
            if (object.gravity <= 0) {
                object.gravity = 16;
            }
            return object;
        }
        return this.generateDefaultLayoutParams();
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public LayoutParams generateOverflowButtonLayoutParams() {
        LayoutParams layoutParams = this.generateDefaultLayoutParams();
        layoutParams.isOverflowButton = true;
        return layoutParams;
    }

    public Menu getMenu() {
        if (this.mMenu == null) {
            Object object = this.getContext();
            this.mMenu = new MenuBuilder((Context)object);
            this.mMenu.setCallback(new MenuBuilderCallback());
            this.mPresenter = new ActionMenuPresenter((Context)object);
            this.mPresenter.setReserveOverflow(true);
            ActionMenuPresenter actionMenuPresenter = this.mPresenter;
            object = this.mActionMenuPresenterCallback != null ? this.mActionMenuPresenterCallback : new ActionMenuPresenterCallback();
            actionMenuPresenter.setCallback((MenuPresenter.Callback)object);
            this.mMenu.addMenuPresenter(this.mPresenter, this.mPopupContext);
            this.mPresenter.setMenuView(this);
        }
        return this.mMenu;
    }

    @Nullable
    public Drawable getOverflowIcon() {
        this.getMenu();
        return this.mPresenter.getOverflowIcon();
    }

    public int getPopupTheme() {
        return this.mPopupTheme;
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    @Override
    public int getWindowAnimations() {
        return 0;
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    protected boolean hasSupportDividerBeforeChildAt(int n) {
        boolean bl = false;
        if (n == 0) {
            return false;
        }
        View view = this.getChildAt(n - 1);
        View view2 = this.getChildAt(n);
        boolean bl2 = bl;
        if (n < this.getChildCount()) {
            bl2 = bl;
            if (view instanceof ActionMenuChildView) {
                bl2 = false | ((ActionMenuChildView)view).needsDividerAfter();
            }
        }
        bl = bl2;
        if (n > 0) {
            bl = bl2;
            if (view2 instanceof ActionMenuChildView) {
                bl = bl2 | ((ActionMenuChildView)view2).needsDividerBefore();
            }
        }
        return bl;
    }

    public boolean hideOverflowMenu() {
        if (this.mPresenter != null && this.mPresenter.hideOverflowMenu()) {
            return true;
        }
        return false;
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    @Override
    public void initialize(MenuBuilder menuBuilder) {
        this.mMenu = menuBuilder;
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    @Override
    public boolean invokeItem(MenuItemImpl menuItemImpl) {
        return this.mMenu.performItemAction(menuItemImpl, 0);
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public boolean isOverflowMenuShowPending() {
        if (this.mPresenter != null && this.mPresenter.isOverflowMenuShowPending()) {
            return true;
        }
        return false;
    }

    public boolean isOverflowMenuShowing() {
        if (this.mPresenter != null && this.mPresenter.isOverflowMenuShowing()) {
            return true;
        }
        return false;
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public boolean isOverflowReserved() {
        return this.mReserveOverflow;
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (this.mPresenter != null) {
            this.mPresenter.updateMenuView(false);
            if (this.mPresenter.isOverflowMenuShowing()) {
                this.mPresenter.hideOverflowMenu();
                this.mPresenter.showOverflowMenu();
            }
        }
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.dismissPopupMenus();
    }

    @Override
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        int n5;
        int n6;
        LayoutParams layoutParams;
        View view;
        if (!this.mFormatItems) {
            super.onLayout(bl, n, n2, n3, n4);
            return;
        }
        int n7 = this.getChildCount();
        int n8 = (n4 - n2) / 2;
        int n9 = this.getDividerWidth();
        int n10 = n3 - n;
        n = this.getPaddingRight();
        n2 = this.getPaddingLeft();
        bl = ViewUtils.isLayoutRtl((View)this);
        n = n10 - n - n2;
        n4 = 0;
        n3 = 0;
        for (n2 = 0; n2 < n7; ++n2) {
            view = this.getChildAt(n2);
            if (view.getVisibility() == 8) continue;
            layoutParams = (LayoutParams)view.getLayoutParams();
            if (layoutParams.isOverflowButton) {
                n4 = n5 = view.getMeasuredWidth();
                if (this.hasSupportDividerBeforeChildAt(n2)) {
                    n4 = n5 + n9;
                }
                int n11 = view.getMeasuredHeight();
                if (bl) {
                    n6 = this.getPaddingLeft() + layoutParams.leftMargin;
                    n5 = n6 + n4;
                } else {
                    n5 = this.getWidth() - this.getPaddingRight() - layoutParams.rightMargin;
                    n6 = n5 - n4;
                }
                int n12 = n8 - n11 / 2;
                view.layout(n6, n12, n5, n11 + n12);
                n -= n4;
                n4 = 1;
                continue;
            }
            n -= view.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
            this.hasSupportDividerBeforeChildAt(n2);
            ++n3;
        }
        if (n7 == 1 && n4 == 0) {
            view = this.getChildAt(0);
            n = view.getMeasuredWidth();
            n2 = view.getMeasuredHeight();
            n3 = n10 / 2 - n / 2;
            n4 = n8 - n2 / 2;
            view.layout(n3, n4, n + n3, n2 + n4);
            return;
        }
        n2 = n3 - (n4 ^ 1);
        n = n2 > 0 ? (n /= n2) : 0;
        n4 = 0;
        n3 = 0;
        n5 = Math.max(0, n);
        if (bl) {
            n2 = this.getWidth() - this.getPaddingRight();
            for (n = n3; n < n7; ++n) {
                view = this.getChildAt(n);
                layoutParams = (LayoutParams)view.getLayoutParams();
                n3 = n2;
                if (view.getVisibility() != 8) {
                    if (layoutParams.isOverflowButton) {
                        n3 = n2;
                    } else {
                        n3 = view.getMeasuredWidth();
                        n4 = view.getMeasuredHeight();
                        n6 = n8 - n4 / 2;
                        view.layout(n2 - n3, n6, n2 -= layoutParams.rightMargin, n4 + n6);
                        n3 = n2 - (n3 + layoutParams.leftMargin + n5);
                    }
                }
                n2 = n3;
            }
        } else {
            n2 = this.getPaddingLeft();
            for (n = n4; n < n7; ++n) {
                view = this.getChildAt(n);
                layoutParams = (LayoutParams)view.getLayoutParams();
                n3 = n2;
                if (view.getVisibility() != 8) {
                    if (layoutParams.isOverflowButton) {
                        n3 = n2;
                    } else {
                        n3 = view.getMeasuredWidth();
                        n4 = view.getMeasuredHeight();
                        n6 = n8 - n4 / 2;
                        view.layout(n2, n6, (n2 += layoutParams.leftMargin) + n3, n4 + n6);
                        n3 = n2 + (n3 + layoutParams.rightMargin + n5);
                    }
                }
                n2 = n3;
            }
        }
    }

    @Override
    protected void onMeasure(int n, int n2) {
        boolean bl = this.mFormatItems;
        boolean bl2 = View.MeasureSpec.getMode((int)n) == 1073741824;
        this.mFormatItems = bl2;
        if (bl != this.mFormatItems) {
            this.mFormatItemsWidth = 0;
        }
        int n3 = View.MeasureSpec.getSize((int)n);
        if (this.mFormatItems && this.mMenu != null && n3 != this.mFormatItemsWidth) {
            this.mFormatItemsWidth = n3;
            this.mMenu.onItemsChanged(true);
        }
        int n4 = this.getChildCount();
        if (this.mFormatItems && n4 > 0) {
            this.onMeasureExactFormat(n, n2);
            return;
        }
        for (n3 = 0; n3 < n4; ++n3) {
            LayoutParams layoutParams = (LayoutParams)this.getChildAt(n3).getLayoutParams();
            layoutParams.rightMargin = 0;
            layoutParams.leftMargin = 0;
        }
        super.onMeasure(n, n2);
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public MenuBuilder peekMenu() {
        return this.mMenu;
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public void setExpandedActionViewsExclusive(boolean bl) {
        this.mPresenter.setExpandedActionViewsExclusive(bl);
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public void setMenuCallbacks(MenuPresenter.Callback callback, MenuBuilder.Callback callback2) {
        this.mActionMenuPresenterCallback = callback;
        this.mMenuBuilderCallback = callback2;
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        this.mOnMenuItemClickListener = onMenuItemClickListener;
    }

    public void setOverflowIcon(@Nullable Drawable drawable) {
        this.getMenu();
        this.mPresenter.setOverflowIcon(drawable);
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public void setOverflowReserved(boolean bl) {
        this.mReserveOverflow = bl;
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

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public void setPresenter(ActionMenuPresenter actionMenuPresenter) {
        this.mPresenter = actionMenuPresenter;
        this.mPresenter.setMenuView(this);
    }

    public boolean showOverflowMenu() {
        if (this.mPresenter != null && this.mPresenter.showOverflowMenu()) {
            return true;
        }
        return false;
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public static interface ActionMenuChildView {
        public boolean needsDividerAfter();

        public boolean needsDividerBefore();
    }

    private static class ActionMenuPresenterCallback
    implements MenuPresenter.Callback {
        ActionMenuPresenterCallback() {
        }

        @Override
        public void onCloseMenu(MenuBuilder menuBuilder, boolean bl) {
        }

        @Override
        public boolean onOpenSubMenu(MenuBuilder menuBuilder) {
            return false;
        }
    }

    public static class LayoutParams
    extends LinearLayoutCompat.LayoutParams {
        @ViewDebug.ExportedProperty
        public int cellsUsed;
        @ViewDebug.ExportedProperty
        public boolean expandable;
        boolean expanded;
        @ViewDebug.ExportedProperty
        public int extraPixels;
        @ViewDebug.ExportedProperty
        public boolean isOverflowButton;
        @ViewDebug.ExportedProperty
        public boolean preventEdgeOffset;

        public LayoutParams(int n, int n2) {
            super(n, n2);
            this.isOverflowButton = false;
        }

        LayoutParams(int n, int n2, boolean bl) {
            super(n, n2);
            this.isOverflowButton = bl;
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(LayoutParams layoutParams) {
            super((ViewGroup.LayoutParams)layoutParams);
            this.isOverflowButton = layoutParams.isOverflowButton;
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }
    }

    private class MenuBuilderCallback
    implements MenuBuilder.Callback {
        MenuBuilderCallback() {
        }

        @Override
        public boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
            if (ActionMenuView.this.mOnMenuItemClickListener != null && ActionMenuView.this.mOnMenuItemClickListener.onMenuItemClick(menuItem)) {
                return true;
            }
            return false;
        }

        @Override
        public void onMenuModeChange(MenuBuilder menuBuilder) {
            if (ActionMenuView.this.mMenuBuilderCallback != null) {
                ActionMenuView.this.mMenuBuilderCallback.onMenuModeChange(menuBuilder);
            }
        }
    }

    public static interface OnMenuItemClickListener {
        public boolean onMenuItemClick(MenuItem var1);
    }

}
