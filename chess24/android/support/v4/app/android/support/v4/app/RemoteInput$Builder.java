/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package android.support.v4.app;

import android.os.Bundle;
import android.support.v4.app.RemoteInput;
import java.util.HashSet;
import java.util.Set;

public static final class RemoteInput.Builder {
    private boolean mAllowFreeFormTextInput = true;
    private final Set<String> mAllowedDataTypes = new HashSet<String>();
    private CharSequence[] mChoices;
    private Bundle mExtras = new Bundle();
    private CharSequence mLabel;
    private final String mResultKey;

    public RemoteInput.Builder(String string) {
        if (string == null) {
            throw new IllegalArgumentException("Result key can't be null");
        }
        this.mResultKey = string;
    }

    public RemoteInput.Builder addExtras(Bundle bundle) {
        if (bundle != null) {
            this.mExtras.putAll(bundle);
        }
        return this;
    }

    public RemoteInput build() {
        return new RemoteInput(this.mResultKey, this.mLabel, this.mChoices, this.mAllowFreeFormTextInput, this.mExtras, this.mAllowedDataTypes);
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    public RemoteInput.Builder setAllowDataType(String string, boolean bl) {
        if (bl) {
            this.mAllowedDataTypes.add(string);
            return this;
        }
        this.mAllowedDataTypes.remove(string);
        return this;
    }

    public RemoteInput.Builder setAllowFreeFormInput(boolean bl) {
        this.mAllowFreeFormTextInput = bl;
        return this;
    }

    public RemoteInput.Builder setChoices(CharSequence[] arrcharSequence) {
        this.mChoices = arrcharSequence;
        return this;
    }

    public RemoteInput.Builder setLabel(CharSequence charSequence) {
        this.mLabel = charSequence;
        return this;
    }
}
