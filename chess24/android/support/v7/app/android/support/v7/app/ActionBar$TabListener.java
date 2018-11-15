/*
 * Decompiled with CFR 0_134.
 */
package android.support.v7.app;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;

@Deprecated
public static interface ActionBar.TabListener {
    public void onTabReselected(ActionBar.Tab var1, FragmentTransaction var2);

    public void onTabSelected(ActionBar.Tab var1, FragmentTransaction var2);

    public void onTabUnselected(ActionBar.Tab var1, FragmentTransaction var2);
}
