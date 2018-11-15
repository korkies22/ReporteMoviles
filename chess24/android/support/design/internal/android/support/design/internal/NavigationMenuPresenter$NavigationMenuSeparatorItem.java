/*
 * Decompiled with CFR 0_134.
 */
package android.support.design.internal;

import android.support.design.internal.NavigationMenuPresenter;

private static class NavigationMenuPresenter.NavigationMenuSeparatorItem
implements NavigationMenuPresenter.NavigationMenuItem {
    private final int mPaddingBottom;
    private final int mPaddingTop;

    public NavigationMenuPresenter.NavigationMenuSeparatorItem(int n, int n2) {
        this.mPaddingTop = n;
        this.mPaddingBottom = n2;
    }

    public int getPaddingBottom() {
        return this.mPaddingBottom;
    }

    public int getPaddingTop() {
        return this.mPaddingTop;
    }
}
