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
package android.support.v7.app;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.RestrictTo;
import android.support.v7.app.ActionBar;
import android.support.v7.app.WindowDecorActionBar;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.ScrollingTabContainerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

@RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
public class WindowDecorActionBar.TabImpl
extends ActionBar.Tab {
    private ActionBar.TabListener mCallback;
    private CharSequence mContentDesc;
    private View mCustomView;
    private Drawable mIcon;
    private int mPosition = -1;
    private Object mTag;
    private CharSequence mText;

    public ActionBar.TabListener getCallback() {
        return this.mCallback;
    }

    @Override
    public CharSequence getContentDescription() {
        return this.mContentDesc;
    }

    @Override
    public View getCustomView() {
        return this.mCustomView;
    }

    @Override
    public Drawable getIcon() {
        return this.mIcon;
    }

    @Override
    public int getPosition() {
        return this.mPosition;
    }

    @Override
    public Object getTag() {
        return this.mTag;
    }

    @Override
    public CharSequence getText() {
        return this.mText;
    }

    @Override
    public void select() {
        WindowDecorActionBar.this.selectTab(this);
    }

    @Override
    public ActionBar.Tab setContentDescription(int n) {
        return this.setContentDescription(WindowDecorActionBar.this.mContext.getResources().getText(n));
    }

    @Override
    public ActionBar.Tab setContentDescription(CharSequence charSequence) {
        this.mContentDesc = charSequence;
        if (this.mPosition >= 0) {
            WindowDecorActionBar.this.mTabScrollView.updateTab(this.mPosition);
        }
        return this;
    }

    @Override
    public ActionBar.Tab setCustomView(int n) {
        return this.setCustomView(LayoutInflater.from((Context)WindowDecorActionBar.this.getThemedContext()).inflate(n, null));
    }

    @Override
    public ActionBar.Tab setCustomView(View view) {
        this.mCustomView = view;
        if (this.mPosition >= 0) {
            WindowDecorActionBar.this.mTabScrollView.updateTab(this.mPosition);
        }
        return this;
    }

    @Override
    public ActionBar.Tab setIcon(int n) {
        return this.setIcon(AppCompatResources.getDrawable(WindowDecorActionBar.this.mContext, n));
    }

    @Override
    public ActionBar.Tab setIcon(Drawable drawable) {
        this.mIcon = drawable;
        if (this.mPosition >= 0) {
            WindowDecorActionBar.this.mTabScrollView.updateTab(this.mPosition);
        }
        return this;
    }

    public void setPosition(int n) {
        this.mPosition = n;
    }

    @Override
    public ActionBar.Tab setTabListener(ActionBar.TabListener tabListener) {
        this.mCallback = tabListener;
        return this;
    }

    @Override
    public ActionBar.Tab setTag(Object object) {
        this.mTag = object;
        return this;
    }

    @Override
    public ActionBar.Tab setText(int n) {
        return this.setText(WindowDecorActionBar.this.mContext.getResources().getText(n));
    }

    @Override
    public ActionBar.Tab setText(CharSequence charSequence) {
        this.mText = charSequence;
        if (this.mPosition >= 0) {
            WindowDecorActionBar.this.mTabScrollView.updateTab(this.mPosition);
        }
        return this;
    }
}
