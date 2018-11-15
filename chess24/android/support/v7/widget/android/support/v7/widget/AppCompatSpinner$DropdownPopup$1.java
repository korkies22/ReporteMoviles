/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.ListAdapter
 */
package android.support.v7.widget;

import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;

class AppCompatSpinner.DropdownPopup
implements AdapterView.OnItemClickListener {
    final /* synthetic */ AppCompatSpinner val$this$0;

    AppCompatSpinner.DropdownPopup(AppCompatSpinner appCompatSpinner) {
        this.val$this$0 = appCompatSpinner;
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int n, long l) {
        DropdownPopup.this.this$0.setSelection(n);
        if (DropdownPopup.this.this$0.getOnItemClickListener() != null) {
            DropdownPopup.this.this$0.performItemClick(view, n, DropdownPopup.this.mAdapter.getItemId(n));
        }
        DropdownPopup.this.dismiss();
    }
}
