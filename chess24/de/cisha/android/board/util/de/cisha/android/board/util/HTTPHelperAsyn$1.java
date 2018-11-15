/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 */
package de.cisha.android.board.util;

import android.os.Handler;
import de.cisha.android.board.service.IConfigService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.util.HTTPHelperAsyn;
import de.cisha.chess.util.HTTPHelper;
import de.cisha.chess.util.HttpResponse;
import java.net.URL;
import java.util.Map;

static final class HTTPHelperAsyn
implements Runnable {
    final /* synthetic */ HTTPHelperAsyn.HTTPHelperDelegate val$delegate;
    final /* synthetic */ Map val$params;
    final /* synthetic */ URL val$url;

    HTTPHelperAsyn(URL uRL, Map map, HTTPHelperAsyn.HTTPHelperDelegate hTTPHelperDelegate) {
        this.val$url = uRL;
        this.val$params = map;
        this.val$delegate = hTTPHelperDelegate;
    }

    @Override
    public void run() {
        Object object = ServiceProvider.getInstance().getConfigService();
        boolean bl = object.useBasicAuth();
        String string = null;
        if (bl) {
            string = object.getBasicAuthUsername();
            object = object.getBasicAuthPasswort();
        } else {
            object = null;
        }
        object = HTTPHelper.loadUrlGet(this.val$url, this.val$params, string, (String)object);
        object = de.cisha.android.board.util.HTTPHelperAsyn.createUIThreadRunnable(this.val$delegate, (HttpResponse)object);
        _uiThreadHandler.post((Runnable)object);
    }
}
