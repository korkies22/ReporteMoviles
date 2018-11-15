/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 *  android.view.View
 */
package android.support.v4.app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentContainer;
import android.support.v4.app.FragmentHostCallback;
import android.view.View;

class Fragment
extends FragmentContainer {
    Fragment() {
    }

    @Override
    public android.support.v4.app.Fragment instantiate(Context context, String string, Bundle bundle) {
        return Fragment.this.mHost.instantiate(context, string, bundle);
    }

    @Nullable
    @Override
    public View onFindViewById(int n) {
        if (Fragment.this.mView == null) {
            throw new IllegalStateException("Fragment does not have a view");
        }
        return Fragment.this.mView.findViewById(n);
    }

    @Override
    public boolean onHasView() {
        if (Fragment.this.mView != null) {
            return true;
        }
        return false;
    }
}
