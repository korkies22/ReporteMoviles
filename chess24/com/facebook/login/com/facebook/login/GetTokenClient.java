/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 */
package com.facebook.login;

import android.content.Context;
import android.os.Bundle;
import com.facebook.internal.PlatformServiceClient;

final class GetTokenClient
extends PlatformServiceClient {
    GetTokenClient(Context context, String string) {
        super(context, 65536, 65537, 20121101, string);
    }

    @Override
    protected void populateRequestBundle(Bundle bundle) {
    }
}
