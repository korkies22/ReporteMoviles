// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.util;

import de.cisha.android.board.service.IConfigService;
import de.cisha.chess.util.HTTPHelper;
import de.cisha.android.board.service.ServiceProvider;
import java.util.Map;
import java.util.TreeMap;
import java.net.URL;
import de.cisha.chess.util.HttpResponse;
import android.os.Looper;
import java.util.concurrent.Executors;
import android.os.Handler;
import java.util.concurrent.ExecutorService;

public class HTTPHelperAsyn
{
    private static ExecutorService _execService;
    private static Handler _uiThreadHandler;
    
    static {
        HTTPHelperAsyn._execService = Executors.newFixedThreadPool(5);
        HTTPHelperAsyn._uiThreadHandler = new Handler(Looper.getMainLooper());
    }
    
    private static Runnable createUIThreadRunnable(final HTTPHelperDelegate httpHelperDelegate, final HttpResponse httpResponse) {
        return new Runnable() {
            @Override
            public void run() {
                if (httpResponse.isOk()) {
                    httpHelperDelegate.urlLoaded(httpResponse.getBody());
                    return;
                }
                httpHelperDelegate.loadingFailed(httpResponse.getHttpErrorCode(), httpResponse.getBody());
            }
        };
    }
    
    public static void loadUrlGet(final URL url, final HTTPHelperDelegate httpHelperDelegate) {
        loadUrlGet(url, new TreeMap<String, String>(), httpHelperDelegate);
    }
    
    public static void loadUrlGet(final URL url, final Map<String, String> map, final HTTPHelperDelegate httpHelperDelegate) {
        HTTPHelperAsyn._execService.execute(new Runnable() {
            @Override
            public void run() {
                final IConfigService configService = ServiceProvider.getInstance().getConfigService();
                final boolean useBasicAuth = configService.useBasicAuth();
                String basicAuthUsername = null;
                String basicAuthPasswort;
                if (useBasicAuth) {
                    basicAuthUsername = configService.getBasicAuthUsername();
                    basicAuthPasswort = configService.getBasicAuthPasswort();
                }
                else {
                    basicAuthPasswort = null;
                }
                HTTPHelperAsyn._uiThreadHandler.post(createUIThreadRunnable(httpHelperDelegate, HTTPHelper.loadUrlGet(url, map, basicAuthUsername, basicAuthPasswort)));
            }
        });
    }
    
    public static void loadUrlPost(final URL url, final Map<String, String> map, final HTTPHelperDelegate httpHelperDelegate) {
        uploadFileUrlPost(url, map, null, httpHelperDelegate);
    }
    
    public static void uploadFileUrlPost(final URL url, final Map<String, String> map, final Map<String, HTTPHelper.FileUploadInformation> map2, final HTTPHelperDelegate httpHelperDelegate) {
        HTTPHelperAsyn._execService.execute(new Runnable() {
            @Override
            public void run() {
                final IConfigService configService = ServiceProvider.getInstance().getConfigService();
                final boolean useBasicAuth = configService.useBasicAuth();
                String basicAuthUsername = null;
                String basicAuthPasswort;
                if (useBasicAuth) {
                    basicAuthUsername = configService.getBasicAuthUsername();
                    basicAuthPasswort = configService.getBasicAuthPasswort();
                }
                else {
                    basicAuthPasswort = null;
                }
                HTTPHelperAsyn._uiThreadHandler.post(createUIThreadRunnable(httpHelperDelegate, HTTPHelper.loadUrlPost(url, map, map2, basicAuthUsername, basicAuthPasswort)));
            }
        });
    }
    
    public interface HTTPHelperDelegate
    {
        void loadingFailed(final int p0, final String p1);
        
        void urlLoaded(final String p0);
    }
}
