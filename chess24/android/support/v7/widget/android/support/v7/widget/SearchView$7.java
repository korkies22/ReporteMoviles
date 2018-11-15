/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.KeyEvent
 *  android.widget.TextView
 *  android.widget.TextView$OnEditorActionListener
 */
package android.support.v7.widget;

import android.view.KeyEvent;
import android.widget.TextView;

class SearchView
implements TextView.OnEditorActionListener {
    SearchView() {
    }

    public boolean onEditorAction(TextView textView, int n, KeyEvent keyEvent) {
        SearchView.this.onSubmitQuery();
        return true;
    }
}
