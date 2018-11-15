/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.view.View
 *  android.view.ViewTreeObserver
 *  android.view.ViewTreeObserver$OnGlobalLayoutListener
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.ListAdapter
 *  android.widget.ListView
 *  android.widget.PopupWindow
 *  android.widget.PopupWindow$OnDismissListener
 *  android.widget.SpinnerAdapter
 */
package android.support.v7.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.ViewUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SpinnerAdapter;

private class AppCompatSpinner.DropdownPopup
extends ListPopupWindow {
    ListAdapter mAdapter;
    private CharSequence mHintText;
    private final Rect mVisibleRect;

    public AppCompatSpinner.DropdownPopup(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.mVisibleRect = new Rect();
        this.setAnchorView((View)AppCompatSpinner.this);
        this.setModal(true);
        this.setPromptPosition(0);
        this.setOnItemClickListener(new AdapterView.OnItemClickListener(AppCompatSpinner.this){
            final /* synthetic */ AppCompatSpinner val$this$0;
            {
                this.val$this$0 = appCompatSpinner;
            }

            public void onItemClick(AdapterView<?> adapterView, View view, int n, long l) {
                AppCompatSpinner.this.setSelection(n);
                if (AppCompatSpinner.this.getOnItemClickListener() != null) {
                    AppCompatSpinner.this.performItemClick(view, n, DropdownPopup.this.mAdapter.getItemId(n));
                }
                DropdownPopup.this.dismiss();
            }
        });
    }

    void computeContentWidth() {
        Drawable drawable = this.getBackground();
        int n = 0;
        if (drawable != null) {
            drawable.getPadding(AppCompatSpinner.this.mTempRect);
            n = ViewUtils.isLayoutRtl((View)AppCompatSpinner.this) ? AppCompatSpinner.access$100((AppCompatSpinner)AppCompatSpinner.this).right : - AppCompatSpinner.access$100((AppCompatSpinner)AppCompatSpinner.this).left;
        } else {
            drawable = AppCompatSpinner.this.mTempRect;
            AppCompatSpinner.access$100((AppCompatSpinner)AppCompatSpinner.this).right = 0;
            drawable.left = 0;
        }
        int n2 = AppCompatSpinner.this.getPaddingLeft();
        int n3 = AppCompatSpinner.this.getPaddingRight();
        int n4 = AppCompatSpinner.this.getWidth();
        if (AppCompatSpinner.this.mDropDownWidth == -2) {
            int n5 = AppCompatSpinner.this.compatMeasureContentWidth((SpinnerAdapter)this.mAdapter, this.getBackground());
            int n6 = AppCompatSpinner.this.getContext().getResources().getDisplayMetrics().widthPixels - AppCompatSpinner.access$100((AppCompatSpinner)AppCompatSpinner.this).left - AppCompatSpinner.access$100((AppCompatSpinner)AppCompatSpinner.this).right;
            int n7 = n5;
            if (n5 > n6) {
                n7 = n6;
            }
            this.setContentWidth(Math.max(n7, n4 - n2 - n3));
        } else if (AppCompatSpinner.this.mDropDownWidth == -1) {
            this.setContentWidth(n4 - n2 - n3);
        } else {
            this.setContentWidth(AppCompatSpinner.this.mDropDownWidth);
        }
        n = ViewUtils.isLayoutRtl((View)AppCompatSpinner.this) ? (n += n4 - n3 - this.getWidth()) : (n += n2);
        this.setHorizontalOffset(n);
    }

    public CharSequence getHintText() {
        return this.mHintText;
    }

    boolean isVisibleToUser(View view) {
        if (ViewCompat.isAttachedToWindow(view) && view.getGlobalVisibleRect(this.mVisibleRect)) {
            return true;
        }
        return false;
    }

    @Override
    public void setAdapter(ListAdapter listAdapter) {
        super.setAdapter(listAdapter);
        this.mAdapter = listAdapter;
    }

    public void setPromptText(CharSequence charSequence) {
        this.mHintText = charSequence;
    }

    @Override
    public void show() {
        boolean bl = this.isShowing();
        this.computeContentWidth();
        this.setInputMethodMode(2);
        super.show();
        this.getListView().setChoiceMode(1);
        this.setSelection(AppCompatSpinner.this.getSelectedItemPosition());
        if (bl) {
            return;
        }
        ViewTreeObserver viewTreeObserver = AppCompatSpinner.this.getViewTreeObserver();
        if (viewTreeObserver != null) {
            final ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener(){

                public void onGlobalLayout() {
                    if (!DropdownPopup.this.isVisibleToUser((View)AppCompatSpinner.this)) {
                        DropdownPopup.this.dismiss();
                        return;
                    }
                    DropdownPopup.this.computeContentWidth();
                    AppCompatSpinner.DropdownPopup.super.show();
                }
            };
            viewTreeObserver.addOnGlobalLayoutListener(onGlobalLayoutListener);
            this.setOnDismissListener(new PopupWindow.OnDismissListener(){

                public void onDismiss() {
                    ViewTreeObserver viewTreeObserver = AppCompatSpinner.this.getViewTreeObserver();
                    if (viewTreeObserver != null) {
                        viewTreeObserver.removeGlobalOnLayoutListener(onGlobalLayoutListener);
                    }
                }
            });
        }
    }

}
