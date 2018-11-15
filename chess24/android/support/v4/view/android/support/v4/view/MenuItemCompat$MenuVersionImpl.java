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

static interface MenuItemCompat.MenuVersionImpl {
    public int getAlphabeticModifiers(MenuItem var1);

    public CharSequence getContentDescription(MenuItem var1);

    public ColorStateList getIconTintList(MenuItem var1);

    public PorterDuff.Mode getIconTintMode(MenuItem var1);

    public int getNumericModifiers(MenuItem var1);

    public CharSequence getTooltipText(MenuItem var1);

    public void setAlphabeticShortcut(MenuItem var1, char var2, int var3);

    public void setContentDescription(MenuItem var1, CharSequence var2);

    public void setIconTintList(MenuItem var1, ColorStateList var2);

    public void setIconTintMode(MenuItem var1, PorterDuff.Mode var2);

    public void setNumericShortcut(MenuItem var1, char var2, int var3);

    public void setShortcut(MenuItem var1, char var2, char var3, int var4, int var5);

    public void setTooltipText(MenuItem var1, CharSequence var2);
}
