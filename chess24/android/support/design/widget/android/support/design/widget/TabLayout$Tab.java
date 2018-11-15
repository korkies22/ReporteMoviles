/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.drawable.Drawable
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 */
package android.support.design.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.TabLayout;
import android.support.v7.content.res.AppCompatResources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public static final class TabLayout.Tab {
    public static final int INVALID_POSITION = -1;
    private CharSequence mContentDesc;
    private View mCustomView;
    private Drawable mIcon;
    TabLayout mParent;
    private int mPosition = -1;
    private Object mTag;
    private CharSequence mText;
    TabLayout.TabView mView;

    TabLayout.Tab() {
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
    public TabLayout.Tab setContentDescription(@StringRes int n) {
        if (this.mParent == null) {
            throw new IllegalArgumentException("Tab not attached to a TabLayout");
        }
        return this.setContentDescription(this.mParent.getResources().getText(n));
    }

    @NonNull
    public TabLayout.Tab setContentDescription(@Nullable CharSequence charSequence) {
        this.mContentDesc = charSequence;
        this.updateView();
        return this;
    }

    @NonNull
    public TabLayout.Tab setCustomView(@LayoutRes int n) {
        return this.setCustomView(LayoutInflater.from((Context)this.mView.getContext()).inflate(n, (ViewGroup)this.mView, false));
    }

    @NonNull
    public TabLayout.Tab setCustomView(@Nullable View view) {
        this.mCustomView = view;
        this.updateView();
        return this;
    }

    @NonNull
    public TabLayout.Tab setIcon(@DrawableRes int n) {
        if (this.mParent == null) {
            throw new IllegalArgumentException("Tab not attached to a TabLayout");
        }
        return this.setIcon(AppCompatResources.getDrawable(this.mParent.getContext(), n));
    }

    @NonNull
    public TabLayout.Tab setIcon(@Nullable Drawable drawable) {
        this.mIcon = drawable;
        this.updateView();
        return this;
    }

    void setPosition(int n) {
        this.mPosition = n;
    }

    @NonNull
    public TabLayout.Tab setTag(@Nullable Object object) {
        this.mTag = object;
        return this;
    }

    @NonNull
    public TabLayout.Tab setText(@StringRes int n) {
        if (this.mParent == null) {
            throw new IllegalArgumentException("Tab not attached to a TabLayout");
        }
        return this.setText(this.mParent.getResources().getText(n));
    }

    @NonNull
    public TabLayout.Tab setText(@Nullable CharSequence charSequence) {
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
