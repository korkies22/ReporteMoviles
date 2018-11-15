/*
 * Decompiled with CFR 0_134.
 */
package com.example.android.trivialdrivesample.util;

import com.example.android.trivialdrivesample.util.IabHelper;
import com.example.android.trivialdrivesample.util.IabResult;
import com.example.android.trivialdrivesample.util.Purchase;
import java.util.List;

class IabHelper
implements Runnable {
    final /* synthetic */ List val$results;

    IabHelper(List list) {
        this.val$results = list;
    }

    @Override
    public void run() {
        3.this.val$singleListener.onConsumeFinished((Purchase)3.this.val$purchases.get(0), (IabResult)this.val$results.get(0));
    }
}
