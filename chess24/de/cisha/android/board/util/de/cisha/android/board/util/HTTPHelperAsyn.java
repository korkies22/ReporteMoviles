/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Looper
 */
package de.cisha.android.board.util;

import android.os.Handler;
import android.os.Looper;
import de.cisha.android.board.service.IConfigService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.chess.util.HTTPHelper;
import de.cisha.chess.util.HttpResponse;
import java.net.URL;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HTTPHelperAsyn {
    private static ExecutorService _execService = Executors.newFixedThreadPool(5);
    private static Handler _uiThreadHandler = new Handler(Looper.getMainLooper());

    private static Runnable createUIThreadRunnable(final HTTPHelperDelegate hTTPHelperDelegate, final HttpResponse httpResponse) {
        return new Runnable(){

            @Override
            public void run() {
                if (httpResponse.isOk()) {
                    hTTPHelperDelegate.urlLoaded(httpResponse.getBody());
                    return;
                }
                hTTPHelperDelegate.loadingFailed(httpResponse.getHttpErrorCode(), httpResponse.getBody());
            }
        };
    }

    public static void loadUrlGet(URL uRL, HTTPHelperDelegate hTTPHelperDelegate) {
        HTTPHelperAsyn.loadUrlGet(uRL, new TreeMap<String, String>(), hTTPHelperDelegate);
    }

    public static void loadUrlGet(URL object, Map<String, String> map, HTTPHelperDelegate hTTPHelperDelegate) {
        object = new Runnable((URL)object, map, hTTPHelperDelegate){
            final /* synthetic */ HTTPHelperDelegate val$delegate;
            final /* synthetic */ Map val$params;
            final /* synthetic */ URL val$url;
            {
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
                object = HTTPHelperAsyn.createUIThreadRunnable(this.val$delegate, (HttpResponse)object);
                _uiThreadHandler.post((Runnable)object);
            }
        };
        _execService.execute((Runnable)object);
    }

    public static void loadUrlPost(URL uRL, Map<String, String> map, HTTPHelperDelegate hTTPHelperDelegate) {
        HTTPHelperAsyn.uploadFileUrlPost(uRL, map, null, hTTPHelperDelegate);
    }

    public static void uploadFileUrlPost(URL object, Map<String, String> map, Map<String, HTTPHelper.FileUploadInformation> map2, HTTPHelperDelegate hTTPHelperDelegate) {
        object = new Runnable((URL)object, map, map2, hTTPHelperDelegate){
            final /* synthetic */ HTTPHelperDelegate val$delegate;
            final /* synthetic */ Map val$optionalFiles;
            final /* synthetic */ Map val$optionalParams;
            final /* synthetic */ URL val$url;
            {
                this.val$url = uRL;
                this.val$optionalParams = map;
                this.val$optionalFiles = map2;
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
                object = HTTPHelper.loadUrlPost(this.val$url, this.val$optionalParams, this.val$optionalFiles, string, (String)object);
                object = HTTPHelperAsyn.createUIThreadRunnable(this.val$delegate, (HttpResponse)object);
                _uiThreadHandler.post((Runnable)object);
            }
        };
        _execService.execute((Runnable)object);
    }

    public static interface HTTPHelperDelegate {
        public void loadingFailed(int var1, String var2);

        public void urlLoaded(String var1);
    }

}
