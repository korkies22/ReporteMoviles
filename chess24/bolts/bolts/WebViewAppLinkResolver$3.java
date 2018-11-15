/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 */
package bolts;

import android.net.Uri;
import bolts.Capture;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.Callable;

class WebViewAppLinkResolver
implements Callable<Void> {
    final /* synthetic */ Capture val$content;
    final /* synthetic */ Capture val$contentType;
    final /* synthetic */ Uri val$url;

    WebViewAppLinkResolver(Uri uri, Capture capture, Capture capture2) {
        this.val$url = uri;
        this.val$content = capture;
        this.val$contentType = capture2;
    }

    @Override
    public Void call() throws Exception {
        URL uRL = new URL(this.val$url.toString());
        URLConnection uRLConnection = null;
        while (uRL != null) {
            HttpURLConnection httpURLConnection;
            uRLConnection = uRL.openConnection();
            boolean bl = uRLConnection instanceof HttpURLConnection;
            if (bl) {
                ((HttpURLConnection)uRLConnection).setInstanceFollowRedirects(true);
            }
            uRLConnection.setRequestProperty(bolts.WebViewAppLinkResolver.PREFER_HEADER, bolts.WebViewAppLinkResolver.META_TAG_PREFIX);
            uRLConnection.connect();
            if (bl && (httpURLConnection = (HttpURLConnection)uRLConnection).getResponseCode() >= 300 && httpURLConnection.getResponseCode() < 400) {
                uRL = new URL(httpURLConnection.getHeaderField("Location"));
                httpURLConnection.disconnect();
                continue;
            }
            uRL = null;
        }
        try {
            this.val$content.set(bolts.WebViewAppLinkResolver.readFromConnection(uRLConnection));
            this.val$contentType.set(uRLConnection.getContentType());
            return null;
        }
        finally {
            if (uRLConnection instanceof HttpURLConnection) {
                ((HttpURLConnection)uRLConnection).disconnect();
            }
        }
    }
}
