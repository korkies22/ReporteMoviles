/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package android.support.design.widget;

import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.view.View;
import java.util.Comparator;

static class CoordinatorLayout.ViewElevationComparator
implements Comparator<View> {
    CoordinatorLayout.ViewElevationComparator() {
    }

    @Override
    public int compare(View view, View view2) {
        float f;
        float f2 = ViewCompat.getZ(view);
        if (f2 > (f = ViewCompat.getZ(view2))) {
            return -1;
        }
        if (f2 < f) {
            return 1;
        }
        return 0;
    }
}
