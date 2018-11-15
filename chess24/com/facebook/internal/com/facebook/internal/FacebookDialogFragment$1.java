/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.facebook.internal;

import android.os.Bundle;
import com.facebook.FacebookException;
import com.facebook.internal.WebDialog;

class FacebookDialogFragment
implements WebDialog.OnCompleteListener {
    FacebookDialogFragment() {
    }

    @Override
    public void onComplete(Bundle bundle, FacebookException facebookException) {
        FacebookDialogFragment.this.onCompleteWebDialog(bundle, facebookException);
    }
}
