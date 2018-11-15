/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.widget.PopupWindow
 */
package android.support.v4.widget;

import android.support.annotation.RequiresApi;
import android.support.v4.widget.PopupWindowCompat;
import android.view.View;
import android.widget.PopupWindow;

@RequiresApi(value=19)
static class PopupWindowCompat.PopupWindowCompatApi19Impl
extends PopupWindowCompat.PopupWindowCompatBaseImpl {
    PopupWindowCompat.PopupWindowCompatApi19Impl() {
    }

    @Override
    public void showAsDropDown(PopupWindow popupWindow, View view, int n, int n2, int n3) {
        popupWindow.showAsDropDown(view, n, n2, n3);
    }
}
