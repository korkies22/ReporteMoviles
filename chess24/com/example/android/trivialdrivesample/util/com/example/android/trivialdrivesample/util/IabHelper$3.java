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
import com.example.android.trivialdrivesample.util.Purchase;
import java.util.ArrayList;
import java.util.List;

class IabHelper
implements Runnable {
    final /* synthetic */ Handler val$handler;
    final /* synthetic */ IabHelper.OnConsumeMultiFinishedListener val$multiListener;
    final /* synthetic */ List val$purchases;
    final /* synthetic */ IabHelper.OnConsumeFinishedListener val$singleListener;

    IabHelper(List list, IabHelper.OnConsumeFinishedListener onConsumeFinishedListener, Handler handler, IabHelper.OnConsumeMultiFinishedListener onConsumeMultiFinishedListener) {
        this.val$purchases = list;
        this.val$singleListener = onConsumeFinishedListener;
        this.val$handler = handler;
        this.val$multiListener = onConsumeMultiFinishedListener;
    }

    @Override
    public void run() {
        final ArrayList<IabResult> arrayList = new ArrayList<IabResult>();
        for (Purchase purchase : this.val$purchases) {
            try {
                IabHelper.this.consume(purchase);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Successful consume of sku ");
                stringBuilder.append(purchase.getSku());
                arrayList.add(new IabResult(0, stringBuilder.toString()));
            }
            catch (IabException iabException) {
                arrayList.add(iabException.getResult());
            }
        }
        IabHelper.this.flagEndAsync();
        if (!IabHelper.this.mDisposed && this.val$singleListener != null) {
            this.val$handler.post(new Runnable(){

                @Override
                public void run() {
                    3.this.val$singleListener.onConsumeFinished((Purchase)3.this.val$purchases.get(0), (IabResult)arrayList.get(0));
                }
            });
        }
        if (!IabHelper.this.mDisposed && this.val$multiListener != null) {
            this.val$handler.post(new Runnable(){

                @Override
                public void run() {
                    3.this.val$multiListener.onConsumeMultiFinished(3.this.val$purchases, arrayList);
                }
            });
        }
    }

}
