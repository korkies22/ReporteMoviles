/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.apache.http.HttpEntity
 *  org.apache.http.HttpResponse
 *  org.apache.http.client.HttpClient
 *  org.apache.http.client.methods.HttpDelete
 *  org.apache.http.client.methods.HttpEntityEnclosingRequestBase
 *  org.apache.http.client.methods.HttpGet
 *  org.apache.http.client.methods.HttpHead
 *  org.apache.http.client.methods.HttpOptions
 *  org.apache.http.client.methods.HttpPost
 *  org.apache.http.client.methods.HttpPut
 *  org.apache.http.client.methods.HttpTrace
 *  org.apache.http.client.methods.HttpUriRequest
 *  org.apache.http.entity.ByteArrayEntity
 *  org.apache.http.params.HttpConnectionParams
 *  org.apache.http.params.HttpParams
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzk;
import com.google.android.gms.internal.zzy;
import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.Set;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpTrace;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class zzw
implements zzy {
    protected final HttpClient zzaC;

    public zzw(HttpClient httpClient) {
        this.zzaC = httpClient;
    }

    private static void zza(HttpEntityEnclosingRequestBase httpEntityEnclosingRequestBase, zzk<?> arrby) throws com.google.android.gms.internal.zza {
        if ((arrby = arrby.zzm()) != null) {
            httpEntityEnclosingRequestBase.setEntity((HttpEntity)new ByteArrayEntity(arrby));
        }
    }

    private static void zza(HttpUriRequest httpUriRequest, Map<String, String> map) {
        for (String string : map.keySet()) {
            httpUriRequest.setHeader(string, map.get(string));
        }
    }

    static HttpUriRequest zzb(zzk<?> zzk2, Map<String, String> httpPut) throws com.google.android.gms.internal.zza {
        switch (zzk2.getMethod()) {
            default: {
                throw new IllegalStateException("Unknown request method.");
            }
            case 7: {
                httpPut = new zza(zzk2.getUrl());
                httpPut.addHeader("Content-Type", zzk2.zzl());
                zzw.zza((HttpEntityEnclosingRequestBase)httpPut, zzk2);
                return httpPut;
            }
            case 6: {
                return new HttpTrace(zzk2.getUrl());
            }
            case 5: {
                return new HttpOptions(zzk2.getUrl());
            }
            case 4: {
                return new HttpHead(zzk2.getUrl());
            }
            case 3: {
                return new HttpDelete(zzk2.getUrl());
            }
            case 2: {
                httpPut = new HttpPut(zzk2.getUrl());
                httpPut.addHeader("Content-Type", zzk2.zzl());
                zzw.zza((HttpEntityEnclosingRequestBase)httpPut, zzk2);
                return httpPut;
            }
            case 1: {
                httpPut = new HttpPost(zzk2.getUrl());
                httpPut.addHeader("Content-Type", zzk2.zzl());
                zzw.zza((HttpEntityEnclosingRequestBase)httpPut, zzk2);
                return httpPut;
            }
            case 0: {
                return new HttpGet(zzk2.getUrl());
            }
            case -1: 
        }
        httpPut = zzk2.zzj();
        if (httpPut != null) {
            HttpPost httpPost = new HttpPost(zzk2.getUrl());
            httpPost.addHeader("Content-Type", zzk2.zzi());
            httpPost.setEntity((HttpEntity)new ByteArrayEntity((byte[])httpPut));
            return httpPost;
        }
        return new HttpGet(zzk2.getUrl());
    }

    @Override
    public HttpResponse zza(zzk<?> zzk2, Map<String, String> httpParams) throws IOException, com.google.android.gms.internal.zza {
        HttpUriRequest httpUriRequest = zzw.zzb(zzk2, httpParams);
        zzw.zza(httpUriRequest, httpParams);
        zzw.zza(httpUriRequest, zzk2.getHeaders());
        httpParams = httpUriRequest.getParams();
        int n = zzk2.zzp();
        HttpConnectionParams.setConnectionTimeout((HttpParams)httpParams, (int)5000);
        HttpConnectionParams.setSoTimeout((HttpParams)httpParams, (int)n);
        return this.zzaC.execute(httpUriRequest);
    }

    public static final class zza
    extends HttpEntityEnclosingRequestBase {
        public zza() {
        }

        public zza(String string) {
            this.setURI(URI.create(string));
        }

        public String getMethod() {
            return "PATCH";
        }
    }

}
