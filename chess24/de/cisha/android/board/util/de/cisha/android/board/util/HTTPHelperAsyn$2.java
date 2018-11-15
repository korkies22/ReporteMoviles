/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.util;

import de.cisha.android.board.util.HTTPHelperAsyn;
import de.cisha.chess.util.HttpResponse;

static final class HTTPHelperAsyn
implements Runnable {
    final /* synthetic */ HTTPHelperAsyn.HTTPHelperDelegate val$delegate;
    final /* synthetic */ HttpResponse val$response;

    HTTPHelperAsyn(HttpResponse httpResponse, HTTPHelperAsyn.HTTPHelperDelegate hTTPHelperDelegate) {
        this.val$response = httpResponse;
        this.val$delegate = hTTPHelperDelegate;
    }

    @Override
    public void run() {
        if (this.val$response.isOk()) {
            this.val$delegate.urlLoaded(this.val$response.getBody());
            return;
        }
        this.val$delegate.loadingFailed(this.val$response.getHttpErrorCode(), this.val$response.getBody());
    }
}
