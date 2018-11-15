/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.text.Layout
 *  android.text.TextPaint
 *  android.text.TextUtils
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.ViewParent
 *  android.view.accessibility.AccessibilityEvent
 *  android.view.accessibility.AccessibilityNodeInfo
 *  android.widget.ImageView
 *  android.widget.LinearLayout
 *  android.widget.TextView
 */
package android.support.design.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.design.R;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PointerIconCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.TooltipCompat;
import android.text.Layout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

class TabLayout.TabView
extends LinearLayout {
    private ImageView mCustomIconView;
    private TextView mCustomTextView;
    private View mCustomView;
    private int mDefaultMaxLines;
    private ImageView mIconView;
    private TabLayout.Tab mTab;
    private TextView mTextView;

    public TabLayout.TabView(Context context) {
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

    public TabLayout.Tab getTab() {
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
            float f;
            int n4;
            block14 : {
                block15 : {
                    int n5;
                    int n6;
                    block11 : {
                        block12 : {
                            n3 = View.MeasureSpec.getSize((int)n);
                            n6 = View.MeasureSpec.getMode((int)n);
                            n5 = TabLayout.this.getTabMaxWidth();
                            n4 = n;
                            if (n5 <= 0) break block11;
                            if (n6 == 0) break block12;
                            n4 = n;
                            if (n3 <= n5) break block11;
                        }
                        n4 = View.MeasureSpec.makeMeasureSpec((int)TabLayout.this.mTabMaxWidth, (int)Integer.MIN_VALUE);
                    }
                    super.onMeasure(n4, n2);
                    if (this.mTextView == null) break block13;
                    this.getResources();
                    float f2 = TabLayout.this.mTabTextSize;
                    n3 = this.mDefaultMaxLines;
                    ImageView imageView = this.mIconView;
                    n6 = 1;
                    if (imageView != null && this.mIconView.getVisibility() == 0) {
                        n = 1;
                        f = f2;
                    } else {
                        f = f2;
                        n = n3;
                        if (this.mTextView != null) {
                            f = f2;
                            n = n3;
                            if (this.mTextView.getLineCount() > 1) {
                                f = TabLayout.this.mTabTextMultiLineSize;
                                n = n3;
                            }
                        }
                    }
                    f2 = this.mTextView.getTextSize();
                    n5 = this.mTextView.getLineCount();
                    n3 = TextViewCompat.getMaxLines(this.mTextView);
                    if (f == f2 && (n3 < 0 || n == n3)) break block13;
                    n3 = n6;
                    if (TabLayout.this.mMode != 1) break block14;
                    n3 = n6;
                    if (f <= f2) break block14;
                    n3 = n6;
                    if (n5 != 1) break block14;
                    imageView = this.mTextView.getLayout();
                    if (imageView == null) break block15;
                    n3 = n6;
                    if (this.approximateLineWidth((Layout)imageView, 0, f) <= (float)(this.getMeasuredWidth() - this.getPaddingLeft() - this.getPaddingRight())) break block14;
                }
                n3 = 0;
            }
            if (n3 != 0) {
                this.mTextView.setTextSize(0, f);
                this.mTextView.setMaxLines(n);
                super.onMeasure(n4, n2);
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

    void setTab(@Nullable TabLayout.Tab tab) {
        if (tab != this.mTab) {
            this.mTab = tab;
            this.update();
        }
    }

    final void update() {
        TabLayout.Tab tab = this.mTab;
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
