/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgn;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

class zzbgk
implements zzbgl {
    private HttpURLConnection zzbKt;

    zzbgk() {
    }

    private InputStream zzd(HttpURLConnection object) throws IOException {
        int n = object.getResponseCode();
        if (n == 200) {
            return object.getInputStream();
        }
        object = new StringBuilder(25);
        object.append("Bad response: ");
        object.append(n);
        object = object.toString();
        if (n == 404) {
            throw new FileNotFoundException((String)object);
        }
        if (n == 503) {
            throw new zzbgn((String)object);
        }
        throw new IOException((String)object);
    }

    private void zze(HttpURLConnection httpURLConnection) {
        if (httpURLConnection != null) {
            httpURLConnection.disconnect();
        }
    }

    @Override
    public void close() {
        this.zze(this.zzbKt);
    }

    @Override
    public InputStream zzia(String string) throws IOException {
        this.zzbKt = this.zzib(string);
        return this.zzd(this.zzbKt);
    }

    HttpURLConnection zzib(String object) throws IOException {
        object = (HttpURLConnection)new URL((String)object).openConnection();
        object.setReadTimeout(20000);
        object.setConnectTimeout(20000);
        return object;
    }
}
