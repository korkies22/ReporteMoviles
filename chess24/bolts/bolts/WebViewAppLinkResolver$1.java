/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  org.json.JSONArray
 */
package bolts;

import android.net.Uri;
import bolts.AppLink;
import bolts.Continuation;
import bolts.Task;
import org.json.JSONArray;

class WebViewAppLinkResolver
implements Continuation<JSONArray, AppLink> {
    final /* synthetic */ Uri val$url;

    WebViewAppLinkResolver(Uri uri) {
        this.val$url = uri;
    }

    @Override
    public AppLink then(Task<JSONArray> task) throws Exception {
        return bolts.WebViewAppLinkResolver.makeAppLinkFromAlData(bolts.WebViewAppLinkResolver.parseAlData(task.getResult()), this.val$url);
    }
}
