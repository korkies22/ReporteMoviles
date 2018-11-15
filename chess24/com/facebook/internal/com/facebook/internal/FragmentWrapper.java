/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Fragment
 *  android.content.Intent
 */
package com.facebook.internal;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import com.facebook.internal.Validate;

public class FragmentWrapper {
    private Fragment nativeFragment;
    private android.support.v4.app.Fragment supportFragment;

    public FragmentWrapper(Fragment fragment) {
        Validate.notNull((Object)fragment, "fragment");
        this.nativeFragment = fragment;
    }

    public FragmentWrapper(android.support.v4.app.Fragment fragment) {
        Validate.notNull(fragment, "fragment");
        this.supportFragment = fragment;
    }

    public final Activity getActivity() {
        if (this.supportFragment != null) {
            return this.supportFragment.getActivity();
        }
        return this.nativeFragment.getActivity();
    }

    public Fragment getNativeFragment() {
        return this.nativeFragment;
    }

    public android.support.v4.app.Fragment getSupportFragment() {
        return this.supportFragment;
    }

    public void startActivityForResult(Intent intent, int n) {
        if (this.supportFragment != null) {
            this.supportFragment.startActivityForResult(intent, n);
            return;
        }
        this.nativeFragment.startActivityForResult(intent, n);
    }
}
