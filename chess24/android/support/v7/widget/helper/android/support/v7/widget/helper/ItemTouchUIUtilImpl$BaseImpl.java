/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.view.View
 */
package android.support.v7.widget.helper;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchUIUtil;
import android.support.v7.widget.helper.ItemTouchUIUtilImpl;
import android.view.View;

static class ItemTouchUIUtilImpl.BaseImpl
implements ItemTouchUIUtil {
    ItemTouchUIUtilImpl.BaseImpl() {
    }

    @Override
    public void clearView(View view) {
        view.setTranslationX(0.0f);
        view.setTranslationY(0.0f);
    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView recyclerView, View view, float f, float f2, int n, boolean bl) {
        view.setTranslationX(f);
        view.setTranslationY(f2);
    }

    @Override
    public void onDrawOver(Canvas canvas, RecyclerView recyclerView, View view, float f, float f2, int n, boolean bl) {
    }

    @Override
    public void onSelected(View view) {
    }
}
