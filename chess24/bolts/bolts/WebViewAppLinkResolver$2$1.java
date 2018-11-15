/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.webkit.WebView
 *  android.webkit.WebViewClient
 */
package bolts;

import android.webkit.WebView;
import android.webkit.WebViewClient;
import bolts.WebViewAppLinkResolver;

class WebViewAppLinkResolver
extends WebViewClient {
    private boolean loaded = false;

    WebViewAppLinkResolver() {
    }

    private void runJavaScript(WebView webView) {
        if (!this.loaded) {
            this.loaded = true;
            webView.loadUrl(bolts.WebViewAppLinkResolver.TAG_EXTRACTION_JAVASCRIPT);
        }
    }

    public void onLoadResource(WebView webView, String string) {
        super.onLoadResource(webView, string);
        this.runJavaScript(webView);
    }

    public void onPageFinished(WebView webView, String string) {
        super.onPageFinished(webView, string);
        this.runJavaScript(webView);
    }
}
