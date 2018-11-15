/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.drawable.Drawable
 *  android.text.TextUtils
 *  android.text.TextUtils$TruncateAt
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewParent
 *  android.view.accessibility.AccessibilityEvent
 *  android.view.accessibility.AccessibilityNodeInfo
 *  android.widget.ImageView
 *  android.widget.LinearLayout
 *  android.widget.LinearLayout$LayoutParams
 *  android.widget.TextView
 */
package android.support.v7.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.appcompat.R;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.ScrollingTabContainerView;
import android.support.v7.widget.TintTypedArray;
import android.support.v7.widget.TooltipCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

private class ScrollingTabContainerView.TabView
extends LinearLayout {
    private final int[] BG_ATTRS;
    private View mCustomView;
    private ImageView mIconView;
    private ActionBar.Tab mTab;
    private TextView mTextView;

    public ScrollingTabContainerView.TabView(Context context, ActionBar.Tab tab, boolean bl) {
        super(context, null, R.attr.actionBarTabStyle);
        this.BG_ATTRS = new int[]{16842964};
        this.mTab = tab;
        ScrollingTabContainerView.this = TintTypedArray.obtainStyledAttributes(context, null, this.BG_ATTRS, R.attr.actionBarTabStyle, 0);
        if (ScrollingTabContainerView.this.hasValue(0)) {
            this.setBackgroundDrawable(ScrollingTabContainerView.this.getDrawable(0));
        }
        ScrollingTabContainerView.this.recycle();
        if (bl) {
            this.setGravity(8388627);
        }
        this.update();
    }

    public void bindTab(ActionBar.Tab tab) {
        this.mTab = tab;
        this.update();
    }

    public ActionBar.Tab getTab() {
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
        super.onMeasure(n, n2);
        if (ScrollingTabContainerView.this.mMaxTabWidth > 0 && this.getMeasuredWidth() > ScrollingTabContainerView.this.mMaxTabWidth) {
            super.onMeasure(View.MeasureSpec.makeMeasureSpec((int)ScrollingTabContainerView.this.mMaxTabWidth, (int)1073741824), n2);
        }
    }

    public void setSelected(boolean bl) {
        boolean bl2 = this.isSelected() != bl;
        super.setSelected(bl);
        if (bl2 && bl) {
            this.sendAccessibilityEvent(4);
        }
    }

    public void update() {
        ActionBar.Tab tab = this.mTab;
        Object object = tab.getCustomView();
        Object object2 = null;
        if (object != null) {
            object2 = object.getParent();
            if (object2 != this) {
                if (object2 != null) {
                    ((ViewGroup)object2).removeView(object);
                }
                this.addView(object);
            }
            this.mCustomView = object;
            if (this.mTextView != null) {
                this.mTextView.setVisibility(8);
            }
            if (this.mIconView != null) {
                this.mIconView.setVisibility(8);
                this.mIconView.setImageDrawable(null);
                return;
            }
        } else {
            AppCompatImageView appCompatImageView;
            if (this.mCustomView != null) {
                this.removeView(this.mCustomView);
                this.mCustomView = null;
            }
            Object object3 = tab.getIcon();
            object = tab.getText();
            if (object3 != null) {
                if (this.mIconView == null) {
                    appCompatImageView = new AppCompatImageView(this.getContext());
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
                    layoutParams.gravity = 16;
                    appCompatImageView.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
                    this.addView((View)appCompatImageView, 0);
                    this.mIconView = appCompatImageView;
                }
                this.mIconView.setImageDrawable(object3);
                this.mIconView.setVisibility(0);
            } else if (this.mIconView != null) {
                this.mIconView.setVisibility(8);
                this.mIconView.setImageDrawable(null);
            }
            boolean bl = TextUtils.isEmpty((CharSequence)object) ^ true;
            if (bl) {
                if (this.mTextView == null) {
                    object3 = new AppCompatTextView(this.getContext(), null, R.attr.actionBarTabTextStyle);
                    object3.setEllipsize(TextUtils.TruncateAt.END);
                    appCompatImageView = new LinearLayout.LayoutParams(-2, -2);
                    appCompatImageView.gravity = 16;
                    object3.setLayoutParams((ViewGroup.LayoutParams)appCompatImageView);
                    this.addView((View)object3);
                    this.mTextView = object3;
                }
                this.mTextView.setText((CharSequence)object);
                this.mTextView.setVisibility(0);
            } else if (this.mTextView != null) {
                this.mTextView.setVisibility(8);
                this.mTextView.setText(null);
            }
            if (this.mIconView != null) {
                this.mIconView.setContentDescription(tab.getContentDescription());
            }
            if (!bl) {
                object2 = tab.getContentDescription();
            }
            TooltipCompat.setTooltipText((View)this, (CharSequence)object2);
        }
    }
}
