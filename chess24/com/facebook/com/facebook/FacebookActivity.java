/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.content.res.Configuration
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.util.Log
 */
package com.facebook;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.common.R;
import com.facebook.internal.FacebookDialogFragment;
import com.facebook.internal.NativeProtocol;
import com.facebook.login.LoginFragment;
import com.facebook.share.internal.DeviceShareDialogFragment;
import com.facebook.share.model.ShareContent;

public class FacebookActivity
extends FragmentActivity {
    private static String FRAGMENT_TAG = "SingleFragment";
    public static String PASS_THROUGH_CANCEL_ACTION = "PassThrough";
    private static final String TAG = "com.facebook.FacebookActivity";
    private Fragment singleFragment;

    private void handlePassThroughError() {
        FacebookException facebookException = NativeProtocol.getExceptionFromErrorData(NativeProtocol.getMethodArgumentsFromIntent(this.getIntent()));
        this.setResult(0, NativeProtocol.createProtocolResultIntent(this.getIntent(), null, facebookException));
        this.finish();
    }

    public Fragment getCurrentFragment() {
        return this.singleFragment;
    }

    protected Fragment getFragment() {
        Fragment fragment;
        Intent intent = this.getIntent();
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        Fragment fragment2 = fragment = fragmentManager.findFragmentByTag(FRAGMENT_TAG);
        if (fragment == null) {
            if ("FacebookDialogFragment".equals(intent.getAction())) {
                fragment2 = new FacebookDialogFragment();
                fragment2.setRetainInstance(true);
                fragment2.show(fragmentManager, FRAGMENT_TAG);
                return fragment2;
            }
            if ("DeviceShareDialogFragment".equals(intent.getAction())) {
                fragment2 = new DeviceShareDialogFragment();
                fragment2.setRetainInstance(true);
                fragment2.setShareContent((ShareContent)intent.getParcelableExtra("content"));
                fragment2.show(fragmentManager, FRAGMENT_TAG);
                return fragment2;
            }
            fragment2 = new LoginFragment();
            fragment2.setRetainInstance(true);
            fragmentManager.beginTransaction().add(R.id.com_facebook_fragment_container, fragment2, FRAGMENT_TAG).commit();
        }
        return fragment2;
    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (this.singleFragment != null) {
            this.singleFragment.onConfigurationChanged(configuration);
        }
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        bundle = this.getIntent();
        if (!FacebookSdk.isInitialized()) {
            Log.d((String)TAG, (String)"Facebook SDK not initialized. Make sure you call sdkInitialize inside your Application's onCreate method.");
            FacebookSdk.sdkInitialize(this.getApplicationContext());
        }
        this.setContentView(R.layout.com_facebook_activity_layout);
        if (PASS_THROUGH_CANCEL_ACTION.equals(bundle.getAction())) {
            this.handlePassThroughError();
            return;
        }
        this.singleFragment = this.getFragment();
    }
}
