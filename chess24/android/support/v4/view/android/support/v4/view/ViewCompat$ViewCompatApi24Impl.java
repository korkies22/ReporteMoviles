/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ClipData
 *  android.view.PointerIcon
 *  android.view.View
 *  android.view.View$DragShadowBuilder
 */
package android.support.v4.view;

import android.content.ClipData;
import android.support.annotation.RequiresApi;
import android.support.v4.view.PointerIconCompat;
import android.support.v4.view.ViewCompat;
import android.view.PointerIcon;
import android.view.View;

@RequiresApi(value=24)
static class ViewCompat.ViewCompatApi24Impl
extends ViewCompat.ViewCompatApi23Impl {
    ViewCompat.ViewCompatApi24Impl() {
    }

    @Override
    public void cancelDragAndDrop(View view) {
        view.cancelDragAndDrop();
    }

    @Override
    public void dispatchFinishTemporaryDetach(View view) {
        view.dispatchFinishTemporaryDetach();
    }

    @Override
    public void dispatchStartTemporaryDetach(View view) {
        view.dispatchStartTemporaryDetach();
    }

    @Override
    public void setPointerIcon(View view, PointerIconCompat object) {
        object = object != null ? object.getPointerIcon() : null;
        view.setPointerIcon((PointerIcon)object);
    }

    @Override
    public boolean startDragAndDrop(View view, ClipData clipData, View.DragShadowBuilder dragShadowBuilder, Object object, int n) {
        return view.startDragAndDrop(clipData, dragShadowBuilder, object, n);
    }

    @Override
    public void updateDragShadow(View view, View.DragShadowBuilder dragShadowBuilder) {
        view.updateDragShadow(dragShadowBuilder);
    }
}
