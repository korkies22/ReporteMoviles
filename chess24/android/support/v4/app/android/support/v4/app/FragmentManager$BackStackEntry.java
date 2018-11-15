/*
 * Decompiled with CFR 0_134.
 */
package android.support.v4.app;

import android.support.annotation.StringRes;
import android.support.v4.app.FragmentManager;

public static interface FragmentManager.BackStackEntry {
    public CharSequence getBreadCrumbShortTitle();

    @StringRes
    public int getBreadCrumbShortTitleRes();

    public CharSequence getBreadCrumbTitle();

    @StringRes
    public int getBreadCrumbTitleRes();

    public int getId();

    public String getName();
}
