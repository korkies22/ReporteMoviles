/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.apache.http.client.methods.HttpEntityEnclosingRequestBase
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzw;
import java.net.URI;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

public static final class zzw.zza
extends HttpEntityEnclosingRequestBase {
    public zzw.zza() {
    }

    public zzw.zza(String string) {
        this.setURI(URI.create(string));
    }

    public String getMethod() {
        return "PATCH";
    }
}
