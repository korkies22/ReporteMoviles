/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.net.Uri
 *  android.webkit.JavascriptInterface
 *  android.webkit.WebSettings
 *  android.webkit.WebView
 *  android.webkit.WebViewClient
 *  org.json.JSONArray
 *  org.json.JSONException
 */
package bolts;

import android.content.Context;
import android.net.Uri;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import bolts.Capture;
import bolts.Continuation;
import bolts.Task;
import bolts.TaskCompletionSource;
import org.json.JSONArray;
import org.json.JSONException;

class WebViewAppLinkResolver
implements Continuation<Void, Task<JSONArray>> {
    final /* synthetic */ Capture val$content;
    final /* synthetic */ Capture val$contentType;
    final /* synthetic */ Uri val$url;

    WebViewAppLinkResolver(Capture capture, Uri uri, Capture capture2) {
        this.val$contentType = capture;
        this.val$url = uri;
        this.val$content = capture2;
    }

    @Override
    public Task<JSONArray> then(Task<Void> object) throws Exception {
        final TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        WebView webView = new WebView(WebViewAppLinkResolver.this.context);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setNetworkAvailable(false);
        webView.setWebViewClient(new WebViewClient(){
            private boolean loaded = false;

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
        });
        webView.addJavascriptInterface(new Object(){

            @JavascriptInterface
            public void setValue(String string) {
                try {
                    taskCompletionSource.trySetResult(new JSONArray(string));
                    return;
                }
                catch (JSONException jSONException) {
                    taskCompletionSource.trySetError((Exception)jSONException);
                    return;
                }
            }
        }, "boltsWebViewAppLinkResolverResult");
        object = this.val$contentType.get() != null ? ((String)this.val$contentType.get()).split(";")[0] : null;
        webView.loadDataWithBaseURL(this.val$url.toString(), (String)this.val$content.get(), (String)object, null, null);
        return taskCompletionSource.getTask();
    }

}
