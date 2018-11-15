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

class LoginActivity
implements TextView.OnEditorActionListener {
    LoginActivity() {
    }

    public boolean onEditorAction(TextView textView, int n, KeyEvent keyEvent) {
        if (n == 4) {
            LoginActivity.this.confirmFormAction();
            return true;
        }
        return false;
    }
}
