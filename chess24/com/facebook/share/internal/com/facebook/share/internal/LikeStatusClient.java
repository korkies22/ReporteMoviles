/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 */
package com.facebook.share.internal;

import android.content.Context;
import android.os.Bundle;
import com.facebook.internal.PlatformServiceClient;

@Deprecated
final class LikeStatusClient
extends PlatformServiceClient {
    private String objectId;

    LikeStatusClient(Context context, String string, String string2) {
        super(context, 65542, 65543, 20141001, string);
        this.objectId = string2;
    }

    @Override
    protected void populateRequestBundle(Bundle bundle) {
        bundle.putString("com.facebook.platform.extra.OBJECT_ID", this.objectId);
    }
}
