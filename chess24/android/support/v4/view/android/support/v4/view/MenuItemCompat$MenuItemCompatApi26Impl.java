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
import android.support.annotation.RequiresApi;
import android.support.v4.view.MenuItemCompat;
import android.view.MenuItem;

@RequiresApi(value=26)
static class MenuItemCompat.MenuItemCompatApi26Impl
extends MenuItemCompat.MenuItemCompatBaseImpl {
    MenuItemCompat.MenuItemCompatApi26Impl() {
    }

    @Override
    public int getAlphabeticModifiers(MenuItem menuItem) {
        return menuItem.getAlphabeticModifiers();
    }

    @Override
    public CharSequence getContentDescription(MenuItem menuItem) {
        return menuItem.getContentDescription();
    }

    @Override
    public ColorStateList getIconTintList(MenuItem menuItem) {
        return menuItem.getIconTintList();
    }

    @Override
    public PorterDuff.Mode getIconTintMode(MenuItem menuItem) {
        return menuItem.getIconTintMode();
    }

    @Override
    public int getNumericModifiers(MenuItem menuItem) {
        return menuItem.getNumericModifiers();
    }

    @Override
    public CharSequence getTooltipText(MenuItem menuItem) {
        return menuItem.getTooltipText();
    }

    @Override
    public void setAlphabeticShortcut(MenuItem menuItem, char c, int n) {
        menuItem.setAlphabeticShortcut(c, n);
    }

    @Override
    public void setContentDescription(MenuItem menuItem, CharSequence charSequence) {
        menuItem.setContentDescription(charSequence);
    }

    @Override
    public void setIconTintList(MenuItem menuItem, ColorStateList colorStateList) {
        menuItem.setIconTintList(colorStateList);
    }

    @Override
    public void setIconTintMode(MenuItem menuItem, PorterDuff.Mode mode) {
        menuItem.setIconTintMode(mode);
    }

    @Override
    public void setNumericShortcut(MenuItem menuItem, char c, int n) {
        menuItem.setNumericShortcut(c, n);
    }

    @Override
    public void setShortcut(MenuItem menuItem, char c, char c2, int n, int n2) {
        menuItem.setShortcut(c, c2, n, n2);
    }

    @Override
    public void setTooltipText(MenuItem menuItem, CharSequence charSequence) {
        menuItem.setTooltipText(charSequence);
    }
}
