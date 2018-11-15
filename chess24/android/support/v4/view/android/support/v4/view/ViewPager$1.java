/*
 * Decompiled with CFR 0_134.
 */
package android.support.v4.view;

import android.support.v4.view.ViewPager;
import java.util.Comparator;

static final class ViewPager
implements Comparator<ViewPager.ItemInfo> {
    ViewPager() {
    }

    @Override
    public int compare(ViewPager.ItemInfo itemInfo, ViewPager.ItemInfo itemInfo2) {
        return itemInfo.position - itemInfo2.position;
    }
}
