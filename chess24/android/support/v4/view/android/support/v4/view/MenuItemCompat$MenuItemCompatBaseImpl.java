/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.res.ColorStateList
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.view.MenuItem
 */
package android.support.v4.view;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.support.v4.view.MenuItemCompat;
import android.view.MenuItem;

static class MenuItemCompat.MenuItemCompatBaseImpl
implements MenuItemCompat.MenuVersionImpl {
    MenuItemCompat.MenuItemCompatBaseImpl() {
    }

    @Override
    public int getAlphabeticModifiers(MenuItem menuItem) {
        return 0;
    }

    @Override
    public CharSequence getContentDescription(MenuItem menuItem) {
        return null;
    }

    @Override
    public ColorStateList getIconTintList(MenuItem menuItem) {
        return null;
    }

    @Override
    public PorterDuff.Mode getIconTintMode(MenuItem menuItem) {
        return null;
    }

    @Override
    public int getNumericModifiers(MenuItem menuItem) {
        return 0;
    }

    @Override
    public CharSequence getTooltipText(MenuItem menuItem) {
        return null;
    }

    @Override
    public void setAlphabeticShortcut(MenuItem menuItem, char c, int n) {
    }

    @Override
    public void setContentDescription(MenuItem menuItem, CharSequence charSequence) {
    }

    @Override
    public void setIconTintList(MenuItem menuItem, ColorStateList colorStateList) {
    }

    @Override
    public void setIconTintMode(MenuItem menuItem, PorterDuff.Mode mode) {
    }

    @Override
    public void setNumericShortcut(MenuItem menuItem, char c, int n) {
    }

    @Override
    public void setShortcut(MenuItem menuItem, char c, char c2, int n, int n2) {
    }

    @Override
    public void setTooltipText(MenuItem menuItem, CharSequence charSequence) {
    }
}
