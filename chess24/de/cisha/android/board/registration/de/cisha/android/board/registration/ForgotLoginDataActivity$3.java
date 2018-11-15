/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.KeyEvent
 *  android.widget.TextView
 *  android.widget.TextView$OnEditorActionListener
 */
package de.cisha.android.board.registration;

import android.view.KeyEvent;
import android.widget.TextView;

class ForgotLoginDataActivity
implements TextView.OnEditorActionListener {
    ForgotLoginDataActivity() {
    }

    public boolean onEditorAction(TextView textView, int n, KeyEvent keyEvent) {
        if (n == 4) {
            ForgotLoginDataActivity.this.confirmForm();
            return true;
        }
        return false;
    }
}
