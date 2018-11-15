/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.widget.TextView
 */
package de.cisha.android.board.registration;

import android.widget.TextView;
import de.cisha.android.board.StatusCodeErrorHelper;
import java.util.List;

class LoginActivity
implements Runnable {
    final /* synthetic */ String val$defaultMessage;
    final /* synthetic */ List val$errors;

    LoginActivity(String string, List list) {
        this.val$defaultMessage = string;
        this.val$errors = list;
    }

    @Override
    public void run() {
        String string = this.val$defaultMessage;
        if (this.val$errors != null) {
            string = StatusCodeErrorHelper.createErrorMessageFrom(this.val$errors);
        }
        TextView textView = LoginActivity.this._errorTextView;
        int n = string.length() > 0 ? 0 : 8;
        textView.setVisibility(n);
        LoginActivity.this._errorTextView.setText((CharSequence)string);
    }
}
