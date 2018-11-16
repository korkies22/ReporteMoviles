// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.ui.patterns.navigationbar;

import android.content.res.TypedArray;
import android.view.View.MeasureSpec;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import android.view.KeyEvent;
import android.view.ViewGroup.LayoutParams;
import de.cisha.android.ui.patterns.R;
import android.view.View;
import android.util.AttributeSet;
import android.content.Context;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import de.cisha.android.ui.patterns.dialogs.ArrowBottomContainerView;
import android.view.ViewGroup;

public class MenuBar extends ViewGroup
{
    private MenuBarItem _clickedMainGroupItem;
    private ArrowBottomContainerView _currentPopup;
    private MenuBarItem _lastSelectedMainGroupItem;
    private boolean _lastSelectedMainGroupItemWasSelectedBefore;
    private View.OnLongClickListener _longClickListener;
    private View.OnClickListener _mainGroupListener;
    private int _minSpacing;
    private MenuBarMoreActionsView _moreActions;
    private MenuBarItem _moreBarItem;
    private boolean _moreBarItemIsAdded;
    private View.OnClickListener _moreGroupListener;
    private MenuBarObserver _observer;
    private ViewGroup _popupViewGroup;
    private MenuBarItem _selectedItem;
    
    public MenuBar(final Context context) {
        super(context);
        this._moreBarItemIsAdded = false;
        this._minSpacing = 30;
        this.initView();
    }
    
    public MenuBar(final Context context, final AttributeSet set) {
        super(context, set);
        this._moreBarItemIsAdded = false;
        this._minSpacing = 30;
        this.initView();
    }
    
    private void initView() {
        this.setFocusableInTouchMode(true);
        this._moreGroupListener = (View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                MenuBar.this.itemInMoreGroupClicked(view);
            }
        };
        this._mainGroupListener = (View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                MenuBar.this.itemInMainGroupClicked(view);
            }
        };
        this._longClickListener = (View.OnLongClickListener)new View.OnLongClickListener() {
            public boolean onLongClick(final View view) {
                MenuBar.this.itemLongClicked(view);
                return false;
            }
        };
        (this._moreBarItem = new MenuBarItem(this.getContext(), this.getContext().getString(R.string.NavigationBarMoreButton), R.drawable.analysis_more, R.drawable.analysis_more_active, 0)).setSelectable(false);
        this._moreActions = new MenuBarMoreActionsView(this.getContext());
        this.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.menubar_background));
    }
    
    private void itemInMainGroupClicked(final View view) {
        if (view instanceof MenuBarItem) {
            this._clickedMainGroupItem = (MenuBarItem)view;
        }
        if (view == this._moreBarItem) {
            this.moreClicked();
        }
        this.itemSelected(view);
    }
    
    private void itemInMoreGroupClicked(final View view) {
        this._clickedMainGroupItem = this._moreBarItem;
        this.itemSelected(view);
        this.hidePopup();
    }
    
    private void itemLongClicked(final View view) {
        if (this._observer != null && view instanceof MenuBarItem) {
            this._observer.menuItemLongClicked((MenuBarItem)view);
        }
    }
    
    private void itemSelected(final View view) {
        if (view instanceof MenuBarItem) {
            final MenuBarItem menuBarItem = (MenuBarItem)view;
            if (menuBarItem.isSelectable()) {
                this.selectItem(menuBarItem);
            }
            if (this._observer != null) {
                this._observer.menuItemClicked(menuBarItem);
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
    
    private void selectChild(final View view) {
        for (int i = 0; i < this.getChildCount(); ++i) {
            this.getChildAt(i).setSelected(false);
        }
        int n;
        for (int j = n = 0; j < this._moreActions.getChildCount(); ++j) {
            final View child = this._moreActions.getChildAt(j);
            child.setSelected(false);
            if (child == view) {
                n = 1;
            }
        }
        if (n != 0) {
            this._moreBarItem.setSelected(true);
        }
        if (view != null) {
            view.setSelected(true);
        }
    }
    
    public void addMenuItem(final MenuBarItem menuBarItem) {
        if (this._moreBarItemIsAdded) {
            super.addView((View)menuBarItem, this.getChildCount() - 1);
            return;
        }
        super.addView((View)menuBarItem);
    }
    
    protected boolean checkLayoutParams(final ViewGroup.LayoutParams viewGroup.LayoutParams) {
        return viewGroup.LayoutParams instanceof LayoutParams;
    }
    
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
    }
    
    public LayoutParams generateLayoutParams(final AttributeSet set) {
        return new LayoutParams(this.getContext(), set);
    }
    
    protected LayoutParams generateLayoutParams(final ViewGroup.LayoutParams viewGroup.LayoutParams) {
        return new LayoutParams(viewGroup.LayoutParams);
    }
    
    public MenuBarItem getMenuItemById(final int n) {
        Object o;
        final View view = (View)(o = this.findViewById(n));
        if (view == null) {
            o = view;
            if (this._moreActions != null) {
                o = this._moreActions.findViewById(n);
            }
        }
        if (o instanceof MenuBarItem) {
            return (MenuBarItem)o;
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
    
    public boolean onKeyDown(final int n, final KeyEvent keyEvent) {
        if (n == 4 && this._currentPopup != null) {
            this.hidePopup();
            return true;
        }
        return false;
    }
    
    protected void onLayout(final boolean b, int i, int j, int n, int n2) {
        n2 = this.getChildCount();
        final int n3 = n - i - this.getPaddingLeft() - this.getPaddingRight();
        i = this.getPaddingLeft();
        final int n4 = 0;
        View child;
        for (j = 0; j < n2; ++j) {
            child = this.getChildAt(j);
            ((LayoutParams)child.getLayoutParams())._x = i;
            i += child.getMeasuredWidth();
            child.setOnLongClickListener(this._longClickListener);
        }
        final int childCount = this._moreActions.getChildCount();
        n = childCount - 1;
        final LayoutParams layoutParams = null;
        j = i;
        List<View> list = null;
        View child2;
        int measuredWidth;
        List<View> list2;
        LayoutParams generateDefaultLayoutParams;
        for (i = n; i >= 0; --i, j = n, list = list2) {
            child2 = this._moreActions.getChildAt(i);
            measuredWidth = child2.getMeasuredWidth();
            if (this._moreBarItemIsAdded && childCount == 1) {
                n2 = this._moreBarItem.getWidth();
            }
            else {
                n2 = 0;
            }
            n = j;
            list2 = list;
            if (measuredWidth < n2 + n3 - (this.getPaddingRight() + j)) {
                if ((list2 = list) == null) {
                    list2 = new ArrayList<View>(5);
                }
                this._moreActions.removeViewInLayout(child2);
                generateDefaultLayoutParams = this.generateDefaultLayoutParams();
                list2.add(0, child2);
                child2.setOnClickListener(this._mainGroupListener);
                generateDefaultLayoutParams._x = j;
                child2.setLayoutParams((ViewGroup.LayoutParams)generateDefaultLayoutParams);
                n = j + (measuredWidth + this._minSpacing);
            }
        }
        if (list != null) {
            for (final View view : list) {
                this.addViewInLayout(view, this.getChildCount(), view.getLayoutParams());
            }
        }
        if (this._moreActions.getChildCount() > 0) {
            i = 1;
        }
        else {
            i = 0;
        }
        j = this.getChildCount();
        List<View> list4 = null;
        Label_0507: {
            if (j > 0) {
                n = j - 1;
                if (((LayoutParams)this.getChildAt(n).getLayoutParams())._x >= n3 || i != 0) {
                    List<View> list3 = null;
                    while (true) {
                        list4 = list3;
                        j = i;
                        if (n < 0) {
                            break Label_0507;
                        }
                        final View child3 = this.getChildAt(n);
                        List<View> list5 = list3;
                        j = i;
                        if (child3 != this._moreBarItem) {
                            list5 = list3;
                            j = i;
                            if (((LayoutParams)child3.getLayoutParams())._x + child3.getMeasuredWidth() > n3 - this._moreBarItem.getMeasuredWidth()) {
                                if ((list5 = list3) == null) {
                                    list5 = new ArrayList<View>(5);
                                }
                                list5.add(0, child3);
                                j = 1;
                            }
                        }
                        --n;
                        list3 = list5;
                        i = j;
                    }
                }
            }
            list4 = null;
            j = i;
        }
        if (list4 != null) {
            for (final View view2 : list4) {
                this.removeViewInLayout(view2);
                this._moreActions.addView(view2, -2, -1);
                view2.setOnClickListener(this._moreGroupListener);
            }
        }
        i = this.getChildCount();
        ViewGroup.LayoutParams layoutParams2 = layoutParams;
        if (this.getChildCount() > 0) {
            layoutParams2 = this.getChildAt(i - 1).getLayoutParams();
        }
        final LayoutParams layoutParams3 = (LayoutParams)layoutParams2;
        if (layoutParams3 == null) {
            i = 0;
        }
        else {
            i = this.getChildAt(i - 1).getMeasuredWidth() + layoutParams3._x;
        }
        n = i;
        if (j != 0) {
            n = i;
            if (!this._moreBarItemIsAdded) {
                final LayoutParams generateDefaultLayoutParams2 = this.generateDefaultLayoutParams();
                this.addViewInLayout((View)this._moreBarItem, this.getChildCount(), (ViewGroup.LayoutParams)generateDefaultLayoutParams2);
                generateDefaultLayoutParams2._x = i;
                n = i + this._moreBarItem.getMeasuredWidth();
                this._moreBarItemIsAdded = true;
                this._moreBarItem.setOnClickListener(this._mainGroupListener);
            }
        }
        if (this._moreBarItemIsAdded && this._moreActions.getChildCount() == 0) {
            this.removeViewInLayout((View)this._moreBarItem);
            this._moreBarItemIsAdded = false;
        }
        i = this.getPaddingRight();
        j = this.getChildCount();
        final float n5 = (n3 - n - i) / (j + 1);
        i = n4;
        while (i < j) {
            final View child4 = this.getChildAt(i);
            final LayoutParams layoutParams4 = (LayoutParams)child4.getLayoutParams();
            ++i;
            n = (int)(i * n5 + layoutParams4._x);
            n2 = (int)(n5 / 2.0f);
            child4.layout(n - n2, this.getPaddingTop(), n + child4.getMeasuredWidth() + n2, this.getMeasuredHeight() - this.getPaddingBottom());
            child4.setOnClickListener(this._mainGroupListener);
        }
    }
    
    protected void onMeasure(final int n, final int n2) {
        final int size = View.MeasureSpec.getSize(n);
        final int size2 = View.MeasureSpec.getSize(n2);
        final int paddingLeft = this.getPaddingLeft();
        final int paddingRight = this.getPaddingRight();
        this._moreBarItem.measure(View.MeasureSpec.makeMeasureSpec(size, 0), View.MeasureSpec.makeMeasureSpec(size2, 0));
        final int childCount = this.getChildCount();
        int n3 = paddingLeft + paddingRight;
        int max;
        for (int i = max = 0; i < childCount; ++i) {
            final View child = this.getChildAt(i);
            child.measure(View.MeasureSpec.makeMeasureSpec(size, 0), View.MeasureSpec.makeMeasureSpec(size2, 0));
            max = Math.max(max, child.getMeasuredHeight());
            n3 += child.getMeasuredWidth() + this._minSpacing;
        }
        int n4 = Math.max(max, this._moreBarItem.getMeasuredHeight());
        if (View.MeasureSpec.getMode(n2) != 0) {
            n4 = Math.min(n4, size2);
        }
        int min = n3;
        if (View.MeasureSpec.getMode(n) != 0) {
            min = Math.min(size, n3);
        }
        this.setMeasuredDimension(resolveSize(min, n), resolveSize(n4 + this.getPaddingTop() + this.getPaddingBottom(), n2));
    }
    
    public void removeMenuBarItem(final MenuBarItem menuBarItem) {
        super.removeView((View)menuBarItem);
        if (this._moreActions != null) {
            this._moreActions.removeView((View)menuBarItem);
        }
    }
    
    public void selectItem(final MenuBarItem selectedItem) {
        if (selectedItem != this._moreBarItem) {
            this._selectedItem = selectedItem;
        }
        this.selectChild((View)selectedItem);
    }
    
    public void setObserver(final MenuBarObserver observer) {
        this._observer = observer;
    }
    
    public void setPopupViewGroup(final ViewGroup popupViewGroup) {
        if (this._popupViewGroup != null && this._currentPopup != null) {
            this._popupViewGroup.removeView((View)this._currentPopup);
        }
        this._popupViewGroup = popupViewGroup;
    }
    
    public void showPopup(final ArrowBottomContainerView arrowBottomContainerView) {
        this.showPopup(arrowBottomContainerView, this._clickedMainGroupItem);
    }
    
    public void showPopup(final ArrowBottomContainerView arrowBottomContainerView, final MenuBarItem menuBarItem) {
        this.requestFocus();
        if (this._popupViewGroup != null && menuBarItem != null) {
            this.hidePopup();
            this.post((Runnable)new Runnable() {
                @Override
                public void run() {
                    MenuBar.this._lastSelectedMainGroupItem = menuBarItem;
                    MenuBar.this._lastSelectedMainGroupItemWasSelectedBefore = menuBarItem.isSelected();
                    menuBarItem.setSelected(true);
                    if (arrowBottomContainerView.getParent() == null) {
                        MenuBar.this._popupViewGroup.addView((View)arrowBottomContainerView, -2, -2);
                        arrowBottomContainerView.setDrawsArrow(true);
                        arrowBottomContainerView.measure(View.MeasureSpec.makeMeasureSpec(MenuBar.this._popupViewGroup.getWidth(), Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(MenuBar.this._popupViewGroup.getHeight(), Integer.MIN_VALUE));
                        arrowBottomContainerView.clipArrowToView((View)menuBarItem);
                        MenuBar.this._currentPopup = arrowBottomContainerView;
                    }
                }
            });
        }
    }
    
    public static class LayoutParams extends ViewGroup.LayoutParams
    {
        private int _x;
        public boolean needed;
        public int weight;
        
        public LayoutParams(final int n, final int n2) {
            super(n, n2);
            this.weight = 1;
            this.needed = false;
            this._x = 0;
        }
        
        public LayoutParams(Context obtainStyledAttributes, final AttributeSet set) {
            super(obtainStyledAttributes, set);
            this.weight = 1;
            this.needed = false;
            this._x = 0;
            obtainStyledAttributes = (Context)obtainStyledAttributes.obtainStyledAttributes(set, R.styleable.MenuBar_LayoutParams);
            try {
                this.weight = ((TypedArray)obtainStyledAttributes).getInteger(R.styleable.MenuBar_LayoutParams_layout_weight, 1);
                this.needed = ((TypedArray)obtainStyledAttributes).getBoolean(R.styleable.MenuBar_LayoutParams_layout_needed, false);
            }
            finally {
                ((TypedArray)obtainStyledAttributes).recycle();
            }
        }
        
        public LayoutParams(final ViewGroup.LayoutParams viewGroup.LayoutParams) {
            this(viewGroup.LayoutParams.width, viewGroup.LayoutParams.height);
        }
    }
    
    public interface MenuBarObserver
    {
        void menuItemClicked(final MenuBarItem p0);
        
        void menuItemLongClicked(final MenuBarItem p0);
    }
}
