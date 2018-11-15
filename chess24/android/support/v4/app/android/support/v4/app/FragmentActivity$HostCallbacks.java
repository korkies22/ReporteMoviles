/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentSender
 *  android.content.IntentSender$SendIntentException
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.Window
 *  android.view.WindowManager
 *  android.view.WindowManager$LayoutParams
 */
package android.support.v4.app;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentHostCallback;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import java.io.FileDescriptor;
import java.io.PrintWriter;

class FragmentActivity.HostCallbacks
extends FragmentHostCallback<FragmentActivity> {
    public FragmentActivity.HostCallbacks() {
        super(FragmentActivity.this);
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        FragmentActivity.this.onAttachFragment(fragment);
    }

    @Override
    public void onDump(String string, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        FragmentActivity.this.dump(string, fileDescriptor, printWriter, arrstring);
    }

    @Nullable
    @Override
    public View onFindViewById(int n) {
        return FragmentActivity.this.findViewById(n);
    }

    @Override
    public FragmentActivity onGetHost() {
        return FragmentActivity.this;
    }

    @Override
    public LayoutInflater onGetLayoutInflater() {
        return FragmentActivity.this.getLayoutInflater().cloneInContext((Context)FragmentActivity.this);
    }

    @Override
    public int onGetWindowAnimations() {
        Window window = FragmentActivity.this.getWindow();
        if (window == null) {
            return 0;
        }
        return window.getAttributes().windowAnimations;
    }

    @Override
    public boolean onHasView() {
        Window window = FragmentActivity.this.getWindow();
        if (window != null && window.peekDecorView() != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean onHasWindowAnimations() {
        if (FragmentActivity.this.getWindow() != null) {
            return true;
        }
        return false;
    }

    @Override
    public void onRequestPermissionsFromFragment(@NonNull Fragment fragment, @NonNull String[] arrstring, int n) {
        FragmentActivity.this.requestPermissionsFromFragment(fragment, arrstring, n);
    }

    @Override
    public boolean onShouldSaveFragmentState(Fragment fragment) {
        return FragmentActivity.this.isFinishing() ^ true;
    }

    @Override
    public boolean onShouldShowRequestPermissionRationale(@NonNull String string) {
        return ActivityCompat.shouldShowRequestPermissionRationale(FragmentActivity.this, string);
    }

    @Override
    public void onStartActivityFromFragment(Fragment fragment, Intent intent, int n) {
        FragmentActivity.this.startActivityFromFragment(fragment, intent, n);
    }

    @Override
    public void onStartActivityFromFragment(Fragment fragment, Intent intent, int n, @Nullable Bundle bundle) {
        FragmentActivity.this.startActivityFromFragment(fragment, intent, n, bundle);
    }

    @Override
    public void onStartIntentSenderFromFragment(Fragment fragment, IntentSender intentSender, int n, @Nullable Intent intent, int n2, int n3, int n4, Bundle bundle) throws IntentSender.SendIntentException {
        FragmentActivity.this.startIntentSenderFromFragment(fragment, intentSender, n, intent, n2, n3, n4, bundle);
    }

    @Override
    public void onSupportInvalidateOptionsMenu() {
        FragmentActivity.this.supportInvalidateOptionsMenu();
    }
}
