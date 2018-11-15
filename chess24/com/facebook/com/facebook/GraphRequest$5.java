/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.util.Pair
 */
package com.facebook;

import android.util.Pair;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestBatch;
import com.facebook.GraphResponse;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

static final class GraphRequest
implements Runnable {
    final /* synthetic */ ArrayList val$callbacks;
    final /* synthetic */ GraphRequestBatch val$requests;

    GraphRequest(ArrayList arrayList, GraphRequestBatch graphRequestBatch) {
        this.val$callbacks = arrayList;
        this.val$requests = graphRequestBatch;
    }

    @Override
    public void run() {
        for (Pair pair : this.val$callbacks) {
            ((GraphRequest.Callback)pair.first).onCompleted((GraphResponse)pair.second);
        }
        Iterator<Object> iterator = this.val$requests.getCallbacks().iterator();
        while (iterator.hasNext()) {
            ((GraphRequestBatch.Callback)iterator.next()).onBatchCompleted(this.val$requests);
        }
    }
}
