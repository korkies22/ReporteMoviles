/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.view.KeyEvent
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.View$OnClickListener
 *  android.view.View$OnLongClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewParent
 */
package de.cisha.android.ui.patterns.navigationbar;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import de.cisha.android.ui.patterns.R;
import de.cisha.android.ui.patterns.dialogs.ArrowBottomContainerView;
import de.cisha.android.ui.patterns.navigationbar.MenuBarItem;
import de.cisha.android.ui.patterns.navigationbar.MenuBarMoreActionsView;
import java.util.ArrayList;

public class MenuBar
extends ViewGroup {
    private MenuBarItem _clickedMainGroupItem;
    private ArrowBottomContainerView _currentPopup;
    private MenuBarItem _lastSelectedMainGroupItem;
    private boolean _lastSelectedMainGroupItemWasSelectedBefore;
    private View.OnLongClickListener _longClickListener;
    private View.OnClickListener _mainGroupListener;
    private int _minSpacing = 30;
    private MenuBarMoreActionsView _moreActions;
    private MenuBarItem _moreBarItem;
    private boolean _moreBarItemIsAdded = false;
    private View.OnClickListener _moreGroupListener;
    private MenuBarObserver _observer;
    private ViewGroup _popupViewGroup;
    private MenuBarItem _selectedItem;

    public MenuBar(Context context) {
        super(context);
        this.initView();
    }

    public MenuBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.initView();
    }

    private void initView() {
        this.setFocusableInTouchMode(true);
        this._moreGroupListener = new View.OnClickListener(){

            public void onClick(View view) {
                MenuBar.this.itemInMoreGroupClicked(view);
            }
        };
        this._mainGroupListener = new View.OnClickListener(){

            public void onClick(View view) {
                MenuBar.this.itemInMainGroupClicked(view);
            }
        };
        this._longClickListener = new View.OnLongClickListener(){

            public boolean onLongClick(View view) {
                MenuBar.this.itemLongClicked(view);
                return false;
            }
        };
        this._moreBarItem = new MenuBarItem(this.getContext(), this.getContext().getString(R.string.NavigationBarMoreButton), R.drawable.analysis_more, R.drawable.analysis_more_active, 0);
        this._moreBarItem.setSelectable(false);
        this._moreActions = new MenuBarMoreActionsView(this.getContext());
        this.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.menubar_background));
    }

    private void itemInMainGroupClicked(View view) {
        if (view instanceof MenuBarItem) {
            this._clickedMainGroupItem = (MenuBarItem)view;
        }
        if (view == this._moreBarItem) {
            this.moreClicked();
        }
        this.itemSelected(view);
    }

    private void itemInMoreGroupClicked(View view) {
        this._clickedMainGroupItem = this._moreBarItem;
        this.itemSelected(view);
        this.hidePopup();
    }

    private void itemLongClicked(View view) {
        if (this._observer != null && view instanceof MenuBarItem) {
            this._observer.menuItemLongClicked((MenuBarItem)view);
        }
    }

    private void itemSelected(View object) {
        if (object instanceof MenuBarItem) {
            if ((object = (MenuBarItem)((Object)object)).isSelectable()) {
                this.selectItem((MenuBarItem)((Object)object));
            }
            if (this._observer != null) {
                this._observer.menuItemClicked((MenuBarItem)((Object)object));
            }
        }
    }

    private void moreClicked() {
        if (this._currentPopup == this._moreActions) {
            this.hidePopup();
            return;
        }
        this.showPopup(this._moreActions, this._moreBarItem);
    }

    private void selectChild(View view) {
        int n;
        for (n = 0; n < this.getChildCount(); ++n) {
            this.getChildAt(n).setSelected(false);
        }
        int n2 = n = 0;
        while (n < this._moreActions.getChildCount()) {
            View view2 = this._moreActions.getChildAt(n);
            view2.setSelected(false);
            if (view2 == view) {
                n2 = 1;
            }
            ++n;
        }
        if (n2 != 0) {
            this._moreBarItem.setSelected(true);
        }
        if (view != null) {
            view.setSelected(true);
        }
    }

    public void addMenuItem(MenuBarItem menuBarItem) {
        if (this._moreBarItemIsAdded) {
            super.addView((View)menuBarItem, this.getChildCount() - 1);
            return;
        }
        super.addView((View)menuBarItem);
    }

    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
    }

    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(this.getContext(), attributeSet);
    }

    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new LayoutParams(layoutParams);
    }

    public MenuBarItem getMenuItemById(int n) {
        View view;
        View view2 = view = this.findViewById(n);
        if (view == null) {
            view2 = view;
            if (this._moreActions != null) {
                view2 = this._moreActions.findViewById(n);
            }
        }
        if (view2 instanceof MenuBarItem) {
            return (MenuBarItem)view2;
        }
        return null;
    }

    public void hidePopup() {
        if (this._popupViewGroup != null) {
            if (this._currentPopup != null) {
                this._popupViewGroup.removeView((View)this._currentPopup);
            }
            if (!this._lastSelectedMainGroupItemWasSelectedBefore && this._lastSelectedMainGroupItem != null) {
                this._lastSelectedMainGroupItem.setSelected(false);
                this.selectChild((View)this._selectedItem);
            }
        }
        this._lastSelectedMainGroupItem = null;
        this._currentPopup = null;
    }

    public boolean onKeyDown(int n, KeyEvent keyEvent) {
        if (n == 4 && this._currentPopup != null) {
            this.hidePopup();
            return true;
        }
        return false;
    }

    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        Object object3;
        Object object2;
        View view;
        n4 = this.getChildCount();
        int n5 = n3 - n - this.getPaddingLeft() - this.getPaddingRight();
        n = this.getPaddingLeft();
        int n6 = 0;
        for (n2 = 0; n2 < n4; ++n2) {
            object2 = this.getChildAt(n2);
            ((LayoutParams)object2.getLayoutParams())._x = n;
            n += object2.getMeasuredWidth();
            object2.setOnLongClickListener(this._longClickListener);
        }
        int n7 = this._moreActions.getChildCount();
        n3 = n7 - 1;
        Object var13_10 = null;
        n2 = n;
        object2 = null;
        for (n = n3; n >= 0; --n) {
            view = this._moreActions.getChildAt(n);
            int n8 = view.getMeasuredWidth();
            n4 = this._moreBarItemIsAdded && n7 == 1 ? this._moreBarItem.getWidth() : 0;
            n3 = n2;
            object3 = object2;
            if (n8 < n4 + n5 - (this.getPaddingRight() + n2)) {
                object3 = object2;
                if (object2 == null) {
                    object3 = new ArrayList(5);
                }
                this._moreActions.removeViewInLayout(view);
                object2 = this.generateDefaultLayoutParams();
                object3.add(0, view);
                view.setOnClickListener(this._mainGroupListener);
                ((LayoutParams)((Object)object2))._x = n2;
                view.setLayoutParams((ViewGroup.LayoutParams)object2);
                n3 = n2 + (n8 + this._minSpacing);
            }
            n2 = n3;
            object2 = object3;
        }
        if (object2 != null) {
            for (Object object3 : object2) {
                this.addViewInLayout(object3, this.getChildCount(), object3.getLayoutParams());
            }
        }
        n = this._moreActions.getChildCount() > 0 ? 1 : 0;
        n2 = this.getChildCount();
        if (n2 > 0 && (((LayoutParams)this.getChildAt(n3 = n2 - 1).getLayoutParams())._x >= n5 || n != 0)) {
            object2 = null;
            do {
                object3 = object2;
                n2 = n;
                if (n3 >= 0) {
                    view = this.getChildAt(n3);
                    object3 = object2;
                    n2 = n;
                    if (view != this._moreBarItem) {
                        object3 = object2;
                        n2 = n;
                        if (((LayoutParams)view.getLayoutParams())._x + view.getMeasuredWidth() > n5 - this._moreBarItem.getMeasuredWidth()) {
                            object3 = object2;
                            if (object2 == null) {
                                object3 = new ArrayList(5);
                            }
                            object3.add(0, view);
                            n2 = 1;
                        }
                    }
                    --n3;
                    object2 = object3;
                    n = n2;
                    continue;
                }
                break;
            } while (true);
        } else {
            object3 = null;
            n2 = n;
        }
        if (object3 != null) {
            for (Object object3 : object3) {
                this.removeViewInLayout(object3);
                this._moreActions.addView(object3, -2, -1);
                object3.setOnClickListener(this._moreGroupListener);
            }
        }
        n = this.getChildCount();
        object2 = var13_10;
        if (this.getChildCount() > 0) {
            object2 = this.getChildAt(n - 1).getLayoutParams();
        }
        n = (object2 = (LayoutParams)((Object)object2)) == null ? 0 : this.getChildAt(n - 1).getMeasuredWidth() + ((LayoutParams)((Object)object2))._x;
        n3 = n;
        if (n2 != 0) {
            n3 = n;
            if (!this._moreBarItemIsAdded) {
                object2 = this.generateDefaultLayoutParams();
                this.addViewInLayout((View)this._moreBarItem, this.getChildCount(), (ViewGroup.LayoutParams)object2);
                ((LayoutParams)((Object)object2))._x = n;
                n3 = n + this._moreBarItem.getMeasuredWidth();
                this._moreBarItemIsAdded = true;
                this._moreBarItem.setOnClickListener(this._mainGroupListener);
            }
        }
        if (this._moreBarItemIsAdded && this._moreActions.getChildCount() == 0) {
            this.removeViewInLayout((View)this._moreBarItem);
            this._moreBarItemIsAdded = false;
        }
        n = this.getPaddingRight();
        n2 = this.getChildCount();
        float f = (float)(n5 - n3 - n) / (float)(n2 + 1);
        n = n6;
        while (n < n2) {
            object2 = this.getChildAt(n);
            object3 = (LayoutParams)object2.getLayoutParams();
            n3 = (int)((float)(++n) * f + (float)((LayoutParams)((Object)object3))._x);
            n4 = (int)(f / 2.0f);
            object2.layout(n3 - n4, this.getPaddingTop(), n3 + object2.getMeasuredWidth() + n4, this.getMeasuredHeight() - this.getPaddingBottom());
            object2.setOnClickListener(this._mainGroupListener);
        }
    }

    protected void onMeasure(int n, int n2) {
        int n3 = View.MeasureSpec.getSize((int)n);
        int n4 = View.MeasureSpec.getSize((int)n2);
        int n5 = this.getPaddingLeft();
        int n6 = this.getPaddingRight();
        this._moreBarItem.measure(View.MeasureSpec.makeMeasureSpec((int)n3, (int)0), View.MeasureSpec.makeMeasureSpec((int)n4, (int)0));
        int n7 = this.getChildCount();
        n5 += n6;
        int n8 = n6 = 0;
        while (n6 < n7) {
            View view = this.getChildAt(n6);
            view.measure(View.MeasureSpec.makeMeasureSpec((int)n3, (int)0), View.MeasureSpec.makeMeasureSpec((int)n4, (int)0));
            n8 = Math.max(n8, view.getMeasuredHeight());
            n5 += view.getMeasuredWidth() + this._minSpacing;
            ++n6;
        }
        n6 = n8 = Math.max(n8, this._moreBarItem.getMeasuredHeight());
        if (View.MeasureSpec.getMode((int)n2) != 0) {
            n6 = Math.min(n8, n4);
        }
        n8 = n5;
        if (View.MeasureSpec.getMode((int)n) != 0) {
            n8 = Math.min(n3, n5);
        }
        this.setMeasuredDimension(MenuBar.resolveSize((int)n8, (int)n), MenuBar.resolveSize((int)(n6 + this.getPaddingTop() + this.getPaddingBottom()), (int)n2));
    }

    public void removeMenuBarItem(MenuBarItem menuBarItem) {
        super.removeView((View)menuBarItem);
        if (this._moreActions != null) {
            this._moreActions.removeView((View)menuBarItem);
        }
    }

    public void selectItem(MenuBarItem menuBarItem) {
        if (menuBarItem != this._moreBarItem) {
            this._selectedItem = menuBarItem;
        }
        this.selectChild((View)menuBarItem);
    }

    public void setObserver(MenuBarObserver menuBarObserver) {
        this._observer = menuBarObserver;
    }

    public void setPopupViewGroup(ViewGroup viewGroup) {
        if (this._popupViewGroup != null && this._currentPopup != null) {
            this._popupViewGroup.removeView((View)this._currentPopup);
        }
        this._popupViewGroup = viewGroup;
    }

    public void showPopup(ArrowBottomContainerView arrowBottomContainerView) {
        this.showPopup(arrowBottomContainerView, this._clickedMainGroupItem);
    }

    public void showPopup(final ArrowBottomContainerView arrowBottomContainerView, final MenuBarItem menuBarItem) {
        this.requestFocus();
        if (this._popupViewGroup != null && menuBarItem != null) {
            this.hidePopup();
            this.post(new Runnable(){

                @Override
                public void run() {
                    MenuBar.this._lastSelectedMainGroupItem = menuBarItem;
                    MenuBar.this._lastSelectedMainGroupItemWasSelectedBefore = menuBarItem.isSelected();
                    menuBarItem.setSelected(true);
                    if (arrowBottomContainerView.getParent() == null) {
                        MenuBar.this._popupViewGroup.addView((View)arrowBottomContainerView, -2, -2);
                        arrowBottomContainerView.setDrawsArrow(true);
                        arrowBottomContainerView.measure(View.MeasureSpec.makeMeasureSpec((int)MenuBar.this._popupViewGroup.getWidth(), (int)Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec((int)MenuBar.this._popupViewGroup.getHeight(), (int)Integer.MIN_VALUE));
                        arrowBottomContainerView.clipArrowToView((View)menuBarItem);
                        MenuBar.this._currentPopup = arrowBottomContainerView;
                    }
                }
            });
        }
    }

    public static class LayoutParams
    extends ViewGroup.LayoutParams {
        private int _x = 0;
        public boolean needed = false;
        public int weight = 1;

        public LayoutParams(int n, int n2) {
            super(n, n2);
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            context = context.obtainStyledAttributes(attributeSet, R.styleable.MenuBar_LayoutParams);
            try {
                this.weight = context.getInteger(R.styleable.MenuBar_LayoutParams_layout_weight, 1);
                this.needed = context.getBoolean(R.styleable.MenuBar_LayoutParams_layout_needed, false);
                return;
            }
            finally {
                context.recycle();
            }
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            this(layoutParams.width, layoutParams.height);
        }
    }

    public static interface MenuBarObserver {
        public void menuItemClicked(MenuBarItem var1);

        public void menuItemLongClicked(MenuBarItem var1);
    }

}
