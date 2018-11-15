/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemSelectedListener
 */
package android.support.v7.widget;

import android.support.v7.widget.DropDownListView;
import android.view.View;
import android.widget.AdapterView;

class ListPopupWindow
implements AdapterView.OnItemSelectedListener {
    ListPopupWindow() {
    }

    public void onItemSelected(AdapterView<?> object, View view, int n, long l) {
        if (n != -1 && (object = ListPopupWindow.this.mDropDownList) != null) {
            object.setListSelectionHidden(false);
        }
    }

    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}
