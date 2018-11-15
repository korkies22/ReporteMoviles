/*
 * Decompiled with CFR 0_134.
 */
package com.example.android.trivialdrivesample.util;

import com.example.android.trivialdrivesample.util.IabHelper;
import com.example.android.trivialdrivesample.util.IabResult;
import com.example.android.trivialdrivesample.util.Purchase;
import java.util.List;

public static interface IabHelper.OnConsumeMultiFinishedListener {
    public void onConsumeMultiFinished(List<Purchase> var1, List<IabResult> var2);
}
