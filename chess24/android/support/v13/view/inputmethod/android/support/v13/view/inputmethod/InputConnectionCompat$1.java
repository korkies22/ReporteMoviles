/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.view.inputmethod.InputConnection
 *  android.view.inputmethod.InputConnectionWrapper
 *  android.view.inputmethod.InputContentInfo
 */
package android.support.v13.view.inputmethod;

import android.os.Bundle;
import android.support.v13.view.inputmethod.InputConnectionCompat;
import android.support.v13.view.inputmethod.InputContentInfoCompat;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import android.view.inputmethod.InputContentInfo;

static final class InputConnectionCompat
extends InputConnectionWrapper {
    final /* synthetic */ InputConnectionCompat.OnCommitContentListener val$listener;

    InputConnectionCompat(InputConnection inputConnection, boolean bl, InputConnectionCompat.OnCommitContentListener onCommitContentListener) {
        this.val$listener = onCommitContentListener;
        super(inputConnection, bl);
    }

    public boolean commitContent(InputContentInfo inputContentInfo, int n, Bundle bundle) {
        if (this.val$listener.onCommitContent(InputContentInfoCompat.wrap((Object)inputContentInfo), n, bundle)) {
            return true;
        }
        return super.commitContent(inputContentInfo, n, bundle);
    }
}
