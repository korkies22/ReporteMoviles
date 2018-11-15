/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.webkit.JavascriptInterface
 *  org.json.JSONArray
 *  org.json.JSONException
 */
package bolts;

import android.webkit.JavascriptInterface;
import bolts.TaskCompletionSource;
import bolts.WebViewAppLinkResolver;
import org.json.JSONArray;
import org.json.JSONException;

class WebViewAppLinkResolver {
    final /* synthetic */ TaskCompletionSource val$tcs;

    WebViewAppLinkResolver(TaskCompletionSource taskCompletionSource) {
        this.val$tcs = taskCompletionSource;
    }

    @JavascriptInterface
    public void setValue(String string) {
        try {
            this.val$tcs.trySetResult(new JSONArray(string));
            return;
        }
        catch (JSONException jSONException) {
            this.val$tcs.trySetError((Exception)jSONException);
            return;
        }
    }
}
