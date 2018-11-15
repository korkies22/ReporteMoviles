/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.ViewGroup
 */
package android.support.v4.view;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.AsyncLayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public static interface AsyncLayoutInflater.OnInflateFinishedListener {
    public void onInflateFinished(@NonNull View var1, @LayoutRes int var2, @Nullable ViewGroup var3);
}
