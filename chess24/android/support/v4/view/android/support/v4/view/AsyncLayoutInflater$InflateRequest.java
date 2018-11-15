/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.ViewGroup
 */
package android.support.v4.view;

import android.support.v4.view.AsyncLayoutInflater;
import android.view.View;
import android.view.ViewGroup;

private static class AsyncLayoutInflater.InflateRequest {
    AsyncLayoutInflater.OnInflateFinishedListener callback;
    AsyncLayoutInflater inflater;
    ViewGroup parent;
    int resid;
    View view;

    AsyncLayoutInflater.InflateRequest() {
    }
}
