/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.view.inputmethod.InputConnection
 *  android.view.inputmethod.InputConnectionWrapper
 */
package android.support.v13.view.inputmethod;

import android.os.Bundle;
import android.support.v13.view.inputmethod.InputConnectionCompat;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;

static final class InputConnectionCompat
extends InputConnectionWrapper {
    final /* synthetic */ InputConnectionCompat.OnCommitContentListener val$listener;

    InputConnectionCompat(InputConnection inputConnection, boolean bl, InputConnectionCompat.OnCommitContentListener onCommitContentListener) {
        this.val$listener = onCommitContentListener;
        super(inputConnection, bl);
    }

    public boolean performPrivateCommand(String string, Bundle bundle) {
        if (android.support.v13.view.inputmethod.InputConnectionCompat.handlePerformPrivateCommand(string, bundle, this.val$listener)) {
            return true;
        }
        return super.performPrivateCommand(string, bundle);
    }
}
