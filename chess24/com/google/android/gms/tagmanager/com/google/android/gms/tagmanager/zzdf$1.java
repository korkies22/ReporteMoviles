/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.tagmanager.zzdf;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

class zzdf
implements zzdf.zzb {
    zzdf() {
    }

    @Override
    public HttpURLConnection zzd(URL uRL) throws IOException {
        return (HttpURLConnection)uRL.openConnection();
    }
}
