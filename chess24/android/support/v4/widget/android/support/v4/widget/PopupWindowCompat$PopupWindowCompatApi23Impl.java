/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.widget.PopupWindow
 */
package android.support.v4.widget;

import android.support.annotation.RequiresApi;
import android.support.v4.widget.PopupWindowCompat;
import android.widget.PopupWindow;

@RequiresApi(value=23)
static class PopupWindowCompat.PopupWindowCompatApi23Impl
extends PopupWindowCompat.PopupWindowCompatApi21Impl {
    PopupWindowCompat.PopupWindowCompatApi23Impl() {
    }

    @Override
    public boolean getOverlapAnchor(PopupWindow popupWindow) {
        return popupWindow.getOverlapAnchor();
    }

    @Override
    public int getWindowLayoutType(PopupWindow popupWindow) {
        return popupWindow.getWindowLayoutType();
    }

    @Override
    public void setOverlapAnchor(PopupWindow popupWindow, boolean bl) {
        popupWindow.setOverlapAnchor(bl);
    }

    @Override
    public void setWindowLayoutType(PopupWindow popupWindow, int n) {
        popupWindow.setWindowLayoutType(n);
    }
}
