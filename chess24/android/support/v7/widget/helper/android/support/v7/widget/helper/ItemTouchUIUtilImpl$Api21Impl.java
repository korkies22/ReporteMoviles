/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.view.View
 */
package android.support.v7.widget.helper;

import android.graphics.Canvas;
import android.support.v4.view.ViewCompat;
import android.support.v7.recyclerview.R;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchUIUtilImpl;
import android.view.View;

static class ItemTouchUIUtilImpl.Api21Impl
extends ItemTouchUIUtilImpl.BaseImpl {
    ItemTouchUIUtilImpl.Api21Impl() {
    }

    private float findMaxElevation(RecyclerView recyclerView, View view) {
        int n = recyclerView.getChildCount();
        float f = 0.0f;
        for (int i = 0; i < n; ++i) {
            float f2;
            View view2 = recyclerView.getChildAt(i);
            if (view2 == view) {
                f2 = f;
            } else {
                float f3 = ViewCompat.getElevation(view2);
                f2 = f;
                if (f3 > f) {
                    f2 = f3;
                }
            }
            f = f2;
        }
        return f;
    }

    @Override
    public void clearView(View view) {
        Object object = view.getTag(R.id.item_touch_helper_previous_elevation);
        if (object != null && object instanceof Float) {
            ViewCompat.setElevation(view, ((Float)object).floatValue());
        }
        view.setTag(R.id.item_touch_helper_previous_elevation, null);
        super.clearView(view);
    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView recyclerView, View view, float f, float f2, int n, boolean bl) {
        if (bl && view.getTag(R.id.item_touch_helper_previous_elevation) == null) {
            float f3 = ViewCompat.getElevation(view);
            ViewCompat.setElevation(view, 1.0f + this.findMaxElevation(recyclerView, view));
            view.setTag(R.id.item_touch_helper_previous_elevation, (Object)Float.valueOf(f3));
        }
        super.onDraw(canvas, recyclerView, view, f, f2, n, bl);
    }
}
