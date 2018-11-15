/*
 * Decompiled with CFR 0_134.
 */
package com.example.android.trivialdrivesample.util;

import com.example.android.trivialdrivesample.util.IabHelper;
import com.example.android.trivialdrivesample.util.IabResult;
import com.example.android.trivialdrivesample.util.Inventory;

class IabHelper
implements Runnable {
    final /* synthetic */ Inventory val$inv_f;
    final /* synthetic */ IabResult val$result_f;

    IabHelper(IabResult iabResult, Inventory inventory) {
        this.val$result_f = iabResult;
        this.val$inv_f = inventory;
    }

    @Override
    public void run() {
        2.this.val$listener.onQueryInventoryFinished(this.val$result_f, this.val$inv_f);
    }
}
