/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 */
package com.example.android.trivialdrivesample.util;

import android.os.Handler;
import com.example.android.trivialdrivesample.util.IabException;
import com.example.android.trivialdrivesample.util.IabHelper;
import com.example.android.trivialdrivesample.util.IabResult;
import com.example.android.trivialdrivesample.util.Inventory;
import java.util.List;

class IabHelper
implements Runnable {
    final /* synthetic */ Handler val$handler;
    final /* synthetic */ IabHelper.QueryInventoryFinishedListener val$listener;
    final /* synthetic */ List val$moreSkus;
    final /* synthetic */ boolean val$querySkuDetails;

    IabHelper(boolean bl, List list, IabHelper.QueryInventoryFinishedListener queryInventoryFinishedListener, Handler handler) {
        this.val$querySkuDetails = bl;
        this.val$moreSkus = list;
        this.val$listener = queryInventoryFinishedListener;
        this.val$handler = handler;
    }

    @Override
    public void run() {
        Inventory inventory;
        final IabResult iabResult = new IabResult(0, "Inventory refresh successful.");
        try {
            inventory = IabHelper.this.queryInventory(this.val$querySkuDetails, this.val$moreSkus);
        }
        catch (IabException iabException) {
            iabResult = iabException.getResult();
            inventory = null;
        }
        IabHelper.this.flagEndAsync();
        if (!IabHelper.this.mDisposed && this.val$listener != null) {
            this.val$handler.post(new Runnable(){

                @Override
                public void run() {
                    2.this.val$listener.onQueryInventoryFinished(iabResult, inventory);
                }
            });
        }
    }

}
