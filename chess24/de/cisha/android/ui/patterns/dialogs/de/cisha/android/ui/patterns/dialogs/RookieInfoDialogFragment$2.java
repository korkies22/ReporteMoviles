/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.widget.TextView
 */
package de.cisha.android.ui.patterns.dialogs;

import android.widget.TextView;

class RookieInfoDialogFragment
implements Runnable {
    final /* synthetic */ CharSequence val$title;

    RookieInfoDialogFragment(CharSequence charSequence) {
        this.val$title = charSequence;
    }

    @Override
    public void run() {
        RookieInfoDialogFragment.this._title = this.val$title;
        RookieInfoDialogFragment.this._headerText.setText(this.val$title);
        TextView textView = RookieInfoDialogFragment.this._headerText;
        int n = this.val$title.length() > 0 ? 0 : 8;
        textView.setVisibility(n);
    }
}
