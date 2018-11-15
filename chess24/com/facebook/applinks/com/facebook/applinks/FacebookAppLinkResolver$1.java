/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 */
package com.facebook.applinks;

import android.net.Uri;
import bolts.AppLink;
import bolts.Continuation;
import bolts.Task;
import java.util.Map;

class FacebookAppLinkResolver
implements Continuation<Map<Uri, AppLink>, AppLink> {
    final /* synthetic */ Uri val$uri;

    FacebookAppLinkResolver(Uri uri) {
        this.val$uri = uri;
    }

    @Override
    public AppLink then(Task<Map<Uri, AppLink>> task) throws Exception {
        return task.getResult().get((Object)this.val$uri);
    }
}
