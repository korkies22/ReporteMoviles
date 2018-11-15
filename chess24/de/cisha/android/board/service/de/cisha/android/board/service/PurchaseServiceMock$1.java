/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.service;

import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import java.util.List;

class PurchaseServiceMock
implements Runnable {
    final /* synthetic */ LoadCommandCallback val$productCallback;
    final /* synthetic */ List val$result;

    PurchaseServiceMock(LoadCommandCallback loadCommandCallback, List list) {
        this.val$productCallback = loadCommandCallback;
        this.val$result = list;
    }

    @Override
    public void run() {
        this.val$productCallback.loadingSucceded(this.val$result);
    }
}
