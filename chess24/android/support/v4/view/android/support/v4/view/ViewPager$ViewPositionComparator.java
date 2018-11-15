/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 */
package android.support.v4.view;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import java.util.Comparator;

static class ViewPager.ViewPositionComparator
implements Comparator<View> {
    ViewPager.ViewPositionComparator() {
    }

    @Override
    public int compare(View object, View object2) {
        object = (ViewPager.LayoutParams)object.getLayoutParams();
        object2 = (ViewPager.LayoutParams)object2.getLayoutParams();
        if (object.isDecor != object2.isDecor) {
            if (object.isDecor) {
                return 1;
            }
            return -1;
        }
        return object.position - object2.position;
    }
}
