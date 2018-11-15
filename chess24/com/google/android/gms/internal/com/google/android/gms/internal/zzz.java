/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.apache.http.Header
 *  org.apache.http.HttpEntity
 *  org.apache.http.HttpResponse
 *  org.apache.http.ProtocolVersion
 *  org.apache.http.StatusLine
 *  org.apache.http.entity.BasicHttpEntity
 *  org.apache.http.message.BasicHeader
 *  org.apache.http.message.BasicHttpResponse
 *  org.apache.http.message.BasicStatusLine
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzk;
import com.google.android.gms.internal.zzy;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;

public class zzz
implements zzy {
    private final zza zzaD;
    private final SSLSocketFactory zzaE;

    public zzz() {
        this(null);
    }

    public zzz(zza zza2) {
        this(zza2, null);
    }

    public zzz(zza zza2, SSLSocketFactory sSLSocketFactory) {
        this.zzaD = zza2;
        this.zzaE = sSLSocketFactory;
    }

    private HttpURLConnection zza(URL uRL, zzk<?> zzk2) throws IOException {
        HttpURLConnection httpURLConnection = this.zza(uRL);
        int n = zzk2.zzp();
        httpURLConnection.setConnectTimeout(n);
        httpURLConnection.setReadTimeout(n);
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setDoInput(true);
        if ("https".equals(uRL.getProtocol()) && this.zzaE != null) {
            ((HttpsURLConnection)httpURLConnection).setSSLSocketFactory(this.zzaE);
        }
        return httpURLConnection;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static HttpEntity zza(HttpURLConnection httpURLConnection) {
        BasicHttpEntity basicHttpEntity;
        InputStream inputStream;
        block2 : {
            basicHttpEntity = new BasicHttpEntity();
            try {
                inputStream = httpURLConnection.getInputStream();
                break block2;
            }
            catch (IOException iOException) {}
            inputStream = httpURLConnection.getErrorStream();
        }
        basicHttpEntity.setContent(inputStream);
        basicHttpEntity.setContentLength((long)httpURLConnection.getContentLength());
        basicHttpEntity.setContentEncoding(httpURLConnection.getContentEncoding());
        basicHttpEntity.setContentType(httpURLConnection.getContentType());
        return basicHttpEntity;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    static void zza(HttpURLConnection var0, zzk<?> var1_1) throws IOException, com.google.android.gms.internal.zza {
        switch (var1_1.getMethod()) {
            default: {
                throw new IllegalStateException("Unknown method type.");
            }
            case 7: {
                var2_2 = "PATCH";
                ** GOTO lbl24
            }
            case 6: {
                var1_1 = "TRACE";
                ** GOTO lbl29
            }
            case 5: {
                var1_1 = "OPTIONS";
                ** GOTO lbl29
            }
            case 4: {
                var1_1 = "HEAD";
                ** GOTO lbl29
            }
            case 3: {
                var1_1 = "DELETE";
                ** GOTO lbl29
            }
            case 2: {
                var2_2 = "PUT";
                ** GOTO lbl24
            }
            case 1: {
                var2_2 = "POST";
lbl24: // 3 sources:
                var0.setRequestMethod(var2_2);
                zzz.zzb((HttpURLConnection)var0, var1_1);
                return;
            }
            case 0: {
                var1_1 = "GET";
lbl29: // 5 sources:
                var0.setRequestMethod((String)var1_1);
                return;
            }
            case -1: 
        }
        var2_3 = var1_1.zzj();
        if (var2_3 == null) return;
        var0.setDoOutput(true);
        var0.setRequestMethod("POST");
        var0.addRequestProperty("Content-Type", var1_1.zzi());
        var0 = new DataOutputStream(var0.getOutputStream());
        var0.write(var2_3);
        var0.close();
    }

    private static void zzb(HttpURLConnection object, zzk<?> zzk2) throws IOException, com.google.android.gms.internal.zza {
        byte[] arrby = zzk2.zzm();
        if (arrby != null) {
            object.setDoOutput(true);
            object.addRequestProperty("Content-Type", zzk2.zzl());
            object = new DataOutputStream(object.getOutputStream());
            object.write(arrby);
            object.close();
        }
    }

    protected HttpURLConnection zza(URL uRL) throws IOException {
        return (HttpURLConnection)uRL.openConnection();
    }

    @Override
    public HttpResponse zza(zzk<?> object, Map<String, String> iterator) throws IOException, com.google.android.gms.internal.zza {
        Map.Entry<String, List<String>> entry2 = object.getUrl();
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.putAll(object.getHeaders());
        hashMap.putAll((Map<String, String>)((Object)iterator));
        if (this.zzaD != null) {
            String string = this.zzaD.zzh((String)((Object)entry2));
            iterator = string;
            if (string == null) {
                object = String.valueOf(entry2);
                object = object.length() != 0 ? "URL blocked by rewriter: ".concat((String)object) : new String("URL blocked by rewriter: ");
                throw new IOException((String)object);
            }
        } else {
            iterator = entry2;
        }
        iterator = this.zza(new URL((String)((Object)iterator)), (zzk<?>)object);
        for (String string : hashMap.keySet()) {
            iterator.addRequestProperty(string, (String)hashMap.get(string));
        }
        zzz.zza((HttpURLConnection)((Object)iterator), object);
        object = new ProtocolVersion("HTTP", 1, 1);
        if (iterator.getResponseCode() == -1) {
            throw new IOException("Could not retrieve response code from HttpUrlConnection.");
        }
        object = new BasicHttpResponse((StatusLine)new BasicStatusLine((ProtocolVersion)object, iterator.getResponseCode(), iterator.getResponseMessage()));
        object.setEntity(zzz.zza((HttpURLConnection)((Object)iterator)));
        for (Map.Entry<String, List<String>> entry2 : iterator.getHeaderFields().entrySet()) {
            if (entry2.getKey() == null) continue;
            object.addHeader((Header)new BasicHeader(entry2.getKey(), entry2.getValue().get(0)));
        }
        return object;
    }

    public static interface zza {
        public String zzh(String var1);
    }

}
