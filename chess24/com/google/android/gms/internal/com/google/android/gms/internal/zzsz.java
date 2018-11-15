/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.net.ConnectivityManager
 *  android.net.NetworkInfo
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.internal.zzrs;
import com.google.android.gms.internal.zzru;
import com.google.android.gms.internal.zzrv;
import com.google.android.gms.internal.zzrw;
import com.google.android.gms.internal.zzsd;
import com.google.android.gms.internal.zzsg;
import com.google.android.gms.internal.zzsj;
import com.google.android.gms.internal.zzst;
import com.google.android.gms.internal.zzsx;
import com.google.android.gms.internal.zztd;
import com.google.android.gms.internal.zztg;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPOutputStream;

class zzsz
extends zzru {
    private static final byte[] zzafy = "\n".getBytes();
    private final String zzHY = zzsz.zza("GoogleAnalytics", zzrv.VERSION, Build.VERSION.RELEASE, zztg.zza(Locale.getDefault()), Build.MODEL, Build.ID);
    private final zztd zzafx;

    zzsz(zzrw zzrw2) {
        super(zzrw2);
        this.zzafx = new zztd(zzrw2.zznq());
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private int zza(URL object, byte[] object2) {
        void var4_27;
        void var2_12;
        block21 : {
            Object object3;
            block23 : {
                void var2_9;
                Object object4;
                block22 : {
                    Object var7_29;
                    int n;
                    block20 : {
                        zzac.zzw(object);
                        zzac.zzw(object2);
                        this.zzb("POST bytes, url", ((Object)object2).length, object);
                        if (this.zzkh()) {
                            this.zza("Post payload\n", new String((byte[])object2));
                        }
                        object4 = null;
                        Object var4_17 = null;
                        var7_29 = null;
                        this.getContext().getPackageName();
                        object = this.zzc((URL)object);
                        OutputStream outputStream = object4;
                        object3 = object;
                        object.setDoOutput(true);
                        OutputStream outputStream2 = object4;
                        object3 = object;
                        object.setFixedLengthStreamingMode(((Object)object2).length);
                        OutputStream outputStream3 = object4;
                        object3 = object;
                        object.connect();
                        OutputStream outputStream4 = object4;
                        object3 = object;
                        object4 = object.getOutputStream();
                        object4.write((byte[])object2);
                        this.zzb((HttpURLConnection)object);
                        n = object.getResponseCode();
                        if (n == 200) {
                            this.zzlZ().zzno();
                        }
                        this.zzb("POST status", n);
                        if (object4 == null) break block20;
                        try {
                            object4.close();
                        }
                        catch (IOException iOException) {
                            this.zze("Error closing http post connection output stream", iOException);
                        }
                    }
                    if (object == null) return n;
                    object.disconnect();
                    return n;
                    catch (Throwable throwable) {
                        OutputStream outputStream = object4;
                        break block21;
                    }
                    catch (IOException iOException) {
                        OutputStream outputStream = object4;
                        object4 = iOException;
                        break block22;
                    }
                    catch (IOException iOException) {
                        Object var2_6 = var7_29;
                        break block22;
                    }
                    catch (Throwable throwable) {
                        object = null;
                        break block21;
                    }
                    catch (IOException iOException) {
                        object = null;
                        Object var2_8 = var7_29;
                    }
                }
                void var4_25 = var2_9;
                object3 = object;
                this.zzd("Network POST connection error", object4);
                if (var2_9 == null) break block23;
                try {
                    var2_9.close();
                }
                catch (IOException iOException) {
                    this.zze("Error closing http post connection output stream", iOException);
                }
            }
            if (object == null) return 0;
            object.disconnect();
            return 0;
            catch (Throwable throwable) {
                object = object3;
            }
        }
        if (var4_27 != null) {
            try {
                var4_27.close();
            }
            catch (IOException iOException) {
                this.zze("Error closing http post connection output stream", iOException);
            }
        }
        if (object == null) throw var2_12;
        object.disconnect();
        throw var2_12;
    }

    private static String zza(String string, String string2, String string3, String string4, String string5, String string6) {
        return String.format("%s/%s (Linux; U; Android %s; %s; %s Build/%s)", string, string2, string3, string4, string5, string6);
    }

    private void zza(StringBuilder stringBuilder, String string, String string2) throws UnsupportedEncodingException {
        if (stringBuilder.length() != 0) {
            stringBuilder.append('&');
        }
        stringBuilder.append(URLEncoder.encode(string, "UTF-8"));
        stringBuilder.append('=');
        stringBuilder.append(URLEncoder.encode(string2, "UTF-8"));
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private int zzb(URL object) {
        void var3_5;
        block9 : {
            HttpURLConnection httpURLConnection;
            block10 : {
                zzac.zzw(object);
                this.zzb("GET request", object);
                httpURLConnection = this.zzc((URL)object);
                object = httpURLConnection;
                httpURLConnection.connect();
                object = httpURLConnection;
                this.zzb(httpURLConnection);
                object = httpURLConnection;
                int n = httpURLConnection.getResponseCode();
                if (n == 200) {
                    object = httpURLConnection;
                    this.zzlZ().zzno();
                }
                object = httpURLConnection;
                this.zzb("GET status", n);
                if (httpURLConnection == null) return n;
                httpURLConnection.disconnect();
                return n;
                catch (Throwable throwable) {
                    object = null;
                    break block9;
                }
                catch (IOException iOException) {
                    httpURLConnection = null;
                    break block10;
                }
                catch (Throwable throwable) {
                    break block9;
                }
                catch (IOException iOException) {}
            }
            object = httpURLConnection;
            {
                void var4_9;
                this.zzd("Network GET connection error", var4_9);
                if (httpURLConnection == null) return 0;
                httpURLConnection.disconnect();
                return 0;
            }
        }
        if (object == null) throw var3_5;
        object.disconnect();
        throw var3_5;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private int zzb(URL var1_1, byte[] var2_3) {
        block20 : {
            block22 : {
                block21 : {
                    zzac.zzw(var1_1);
                    zzac.zzw(var2_3);
                    var5_7 = null;
                    var4_10 = null;
                    this.getContext().getPackageName();
                    var6_14 = zzsz.zzk((byte[])var2_3);
                    this.zza("POST compressed size, ratio %, url", var6_14.length, 100L * (long)var6_14.length / (long)((Object)var2_3).length, var1_1);
                    if (var6_14.length > ((Object)var2_3).length) {
                        this.zzc("Compressed payload is larger then uncompressed. compressed, uncompressed", var6_14.length, ((Object)var2_3).length);
                    }
                    if (this.zzkh()) {
                        var2_3 = (var2_3 = String.valueOf(new String((byte[])var2_3))).length() != 0 ? "\n".concat((String)var2_3) : new String("\n");
                        this.zza("Post payload", var2_3);
                    }
                    var1_1 = this.zzc((URL)var1_1);
                    var1_1.setDoOutput(true);
                    var1_1.addRequestProperty("Content-Encoding", "gzip");
                    var1_1.setFixedLengthStreamingMode(var6_14.length);
                    var1_1.connect();
                    var2_3 = var1_1.getOutputStream();
                    var2_3.write(var6_14);
                    var2_3.close();
                    this.zzb((HttpURLConnection)var1_1);
                    var3_15 = var1_1.getResponseCode();
                    if (var3_15 != 200) ** GOTO lbl30
                    this.zzlZ().zzno();
lbl30: // 2 sources:
                    this.zzb("POST status", var3_15);
                    if (var1_1 == null) return var3_15;
                    var1_1.disconnect();
                    return var3_15;
                    catch (Throwable var4_11) {
                        var5_7 = var1_1;
                        var1_1 = var4_11;
                        var4_10 = var2_3;
                        var2_3 = var5_7;
                        break block20;
                    }
                    catch (IOException var5_8) {
                        var4_10 = var2_3;
                        var2_3 = var5_8;
                        break block21;
                    }
                    catch (Throwable var4_12) {
                        var2_3 = var1_1;
                        var1_1 = var4_12;
                        var4_10 = var5_7;
                        break block20;
                    }
                    catch (IOException var2_4) {
                        break block21;
                    }
                    catch (Throwable var1_2) {
                        var2_3 = null;
                        var4_10 = var5_7;
                        break block20;
                    }
                    catch (IOException var2_5) {
                        var1_1 = null;
                    }
                }
                this.zzd("Network compressed POST connection error", var2_3);
                if (var4_10 == null) break block22;
                try {
                    var4_10.close();
                }
                catch (IOException var2_6) {
                    this.zze("Error closing http compressed post connection output stream", var2_6);
                }
            }
            if (var1_1 == null) return 0;
            var1_1.disconnect();
            return 0;
            catch (Throwable var5_9) {
                var2_3 = var1_1;
                var1_1 = var5_9;
            }
        }
        if (var4_10 != null) {
            try {
                var4_10.close();
            }
            catch (IOException var4_13) {
                this.zze("Error closing http compressed post connection output stream", var4_13);
            }
        }
        if (var2_3 == null) throw var1_1;
        var2_3.disconnect();
        throw var1_1;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private URL zzb(zzst object, String string) {
        StringBuilder stringBuilder;
        String string2;
        if (object.zzps()) {
            string2 = String.valueOf(this.zzns().zzoJ());
            object = String.valueOf(this.zzns().zzoL());
            stringBuilder = new StringBuilder(1 + String.valueOf(string2).length() + String.valueOf(object).length() + String.valueOf(string).length());
        } else {
            string2 = String.valueOf(this.zzns().zzoK());
            object = String.valueOf(this.zzns().zzoL());
            stringBuilder = new StringBuilder(1 + String.valueOf(string2).length() + String.valueOf(object).length() + String.valueOf(string).length());
        }
        stringBuilder.append(string2);
        stringBuilder.append((String)object);
        stringBuilder.append("?");
        stringBuilder.append(string);
        object = stringBuilder.toString();
        try {
            return new URL((String)object);
        }
        catch (MalformedURLException malformedURLException) {
            this.zze("Error trying to parse the hardcoded host url", malformedURLException);
            return null;
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void zzb(HttpURLConnection arrby) throws IOException {
        InputStream inputStream;
        void var1_5;
        block9 : {
            inputStream = arrby.getInputStream();
            try {
                int n;
                arrby = new byte[1024];
                while ((n = inputStream.read(arrby)) > 0) {
                }
                if (inputStream == null) return;
            }
            catch (Throwable throwable) {}
            try {
                inputStream.close();
                return;
            }
            catch (IOException iOException) {
                this.zze("Error closing http connection input stream", iOException);
            }
            return;
            break block9;
            catch (Throwable throwable) {
                inputStream = null;
            }
        }
        if (inputStream == null) throw var1_5;
        try {
            inputStream.close();
            throw var1_5;
        }
        catch (IOException iOException) {
            this.zze("Error closing http connection input stream", iOException);
        }
        throw var1_5;
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean zzg(zzst object) {
        byte[] arrby;
        block15 : {
            block12 : {
                block14 : {
                    void var2_4;
                    String string;
                    block9 : {
                        String string2;
                        block13 : {
                            block10 : {
                                block11 : {
                                    String string3;
                                    block8 : {
                                        zzac.zzw(object);
                                        string3 = this.zza((zzst)object, object.zzps() ^ true);
                                        if (string3 != null) break block8;
                                        zzsx zzsx2 = this.zznr();
                                        string = "Error formatting hit for upload";
                                        break block9;
                                    }
                                    if (string3.length() > this.zzns().zzoy()) break block10;
                                    if ((object = this.zzb((zzst)object, string3)) != null) break block11;
                                    object = "Failed to build collect GET endpoint url";
                                    break block12;
                                }
                                if (this.zzb((URL)object) == 200) {
                                    return true;
                                }
                                return false;
                            }
                            string2 = this.zza((zzst)object, false);
                            if (string2 != null) break block13;
                            zzsx zzsx3 = this.zznr();
                            string = "Error formatting hit for POST upload";
                            break block9;
                        }
                        arrby = string2.getBytes();
                        if (arrby.length <= this.zzns().zzoA()) break block14;
                        zzsx zzsx4 = this.zznr();
                        string = "Hit payload exceeds size limit";
                    }
                    var2_4.zza((zzst)object, string);
                    return true;
                }
                if ((object = this.zzh((zzst)object)) != null) break block15;
                object = "Failed to build collect POST endpoint url";
            }
            this.zzbS((String)object);
            return false;
        }
        if (this.zza((URL)object, arrby) == 200) {
            return true;
        }
        return false;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private URL zzh(zzst var1_1) {
        block5 : {
            block4 : {
                if (!var1_1.zzps()) break block4;
                var2_3 = String.valueOf(this.zzns().zzoJ());
                var1_1 = String.valueOf(this.zzns().zzoL());
                if (var1_1.length() != 0) ** GOTO lbl-1000
                var1_1 = new String(var2_3);
                break block5;
            }
            var2_3 = String.valueOf(this.zzns().zzoK());
            var1_1 = String.valueOf(this.zzns().zzoL());
            if (var1_1.length() != 0) lbl-1000: // 2 sources:
            {
                var1_1 = var2_3.concat((String)var1_1);
            } else {
                var1_1 = new String(var2_3);
            }
        }
        try {
            return new URL((String)var1_1);
        }
        catch (MalformedURLException var1_2) {
            this.zze("Error trying to parse the hardcoded host url", var1_2);
            return null;
        }
    }

    private String zzi(zzst zzst2) {
        return String.valueOf(zzst2.zzpp());
    }

    private static byte[] zzk(byte[] arrby) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
        gZIPOutputStream.write(arrby);
        gZIPOutputStream.close();
        byteArrayOutputStream.close();
        return byteArrayOutputStream.toByteArray();
    }

    private URL zzpB() {
        Object object = String.valueOf(this.zzns().zzoJ());
        String string = String.valueOf(this.zzns().zzoM());
        object = string.length() != 0 ? object.concat(string) : new String((String)object);
        try {
            object = new URL((String)object);
            return object;
        }
        catch (MalformedURLException malformedURLException) {
            this.zze("Error trying to parse the hardcoded host url", malformedURLException);
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    String zza(zzst object, boolean bl) {
        zzac.zzw(object);
        StringBuilder stringBuilder = new StringBuilder();
        try {
            for (Map.Entry<String, String> entry : object.zzfz().entrySet()) {
                String string = entry.getKey();
                if ("ht".equals(string) || "qt".equals(string) || "AppUID".equals(string) || "z".equals(string) || "_gmsv".equals(string)) continue;
                this.zza(stringBuilder, string, entry.getValue());
            }
            this.zza(stringBuilder, "ht", String.valueOf(object.zzpq()));
            this.zza(stringBuilder, "qt", String.valueOf(this.zznq().currentTimeMillis() - object.zzpq()));
            if (!bl) return stringBuilder.toString();
            long l = object.zzpt();
            object = l != 0L ? String.valueOf(l) : this.zzi((zzst)object);
            this.zza(stringBuilder, "z", (String)object);
            return stringBuilder.toString();
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            this.zze("Failed to encode name or value", unsupportedEncodingException);
            return null;
        }
    }

    List<Long> zza(List<zzst> object, boolean bl) {
        zzst zzst2;
        zzac.zzas(object.isEmpty() ^ true);
        this.zza("Uploading batched hits. compression, count", bl, object.size());
        zza zza2 = new zza();
        ArrayList<Long> arrayList = new ArrayList<Long>();
        object = object.iterator();
        while (object.hasNext() && zza2.zzj(zzst2 = (zzst)object.next())) {
            arrayList.add(zzst2.zzpp());
        }
        if (zza2.zzpD() == 0) {
            return arrayList;
        }
        object = this.zzpB();
        if (object == null) {
            this.zzbS("Failed to build batching endpoint url");
            return Collections.emptyList();
        }
        int n = bl ? this.zzb((URL)object, zza2.getPayload()) : this.zza((URL)object, zza2.getPayload());
        if (200 == n) {
            this.zza("Batched upload completed. Hits batched", zza2.zzpD());
            return arrayList;
        }
        this.zza("Network error uploading hits. status code", n);
        if (this.zzns().zzoP().contains(n)) {
            this.zzbR("Server instructed the client to stop batching");
            this.zzafx.start();
        }
        return Collections.emptyList();
    }

    HttpURLConnection zzc(URL object) throws IOException {
        if (!((object = object.openConnection()) instanceof HttpURLConnection)) {
            throw new IOException("Failed to obtain http connection");
        }
        object = (HttpURLConnection)object;
        object.setDefaultUseCaches(false);
        object.setConnectTimeout(this.zzns().zzoW());
        object.setReadTimeout(this.zzns().zzoX());
        object.setInstanceFollowRedirects(false);
        object.setRequestProperty("User-Agent", this.zzHY);
        object.setDoInput(true);
        return object;
    }

    @Override
    protected void zzmr() {
        this.zza("Network initialized. User agent", this.zzHY);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean zzpA() {
        ConnectivityManager connectivityManager;
        block3 : {
            this.zzmq();
            this.zznA();
            connectivityManager = (ConnectivityManager)this.getContext().getSystemService("connectivity");
            try {
                connectivityManager = connectivityManager.getActiveNetworkInfo();
                break block3;
            }
            catch (SecurityException securityException) {}
            connectivityManager = null;
        }
        if (connectivityManager != null && connectivityManager.isConnected()) {
            return true;
        }
        this.zzbO("No network connectivity");
        return false;
    }

    public List<Long> zzt(List<zzst> list) {
        boolean bl;
        this.zzmq();
        this.zznA();
        zzac.zzw(list);
        boolean bl2 = this.zzns().zzoP().isEmpty();
        boolean bl3 = false;
        if (!bl2 && this.zzafx.zzz(this.zzns().zzoI() * 1000L)) {
            boolean bl4 = this.zzns().zzoN() != zzsd.zzadK;
            bl = bl4;
            if (this.zzns().zzoO() == zzsg.zzadV) {
                bl3 = true;
                bl = bl4;
            }
        } else {
            bl = false;
        }
        if (bl) {
            return this.zza(list, bl3);
        }
        return this.zzu(list);
    }

    List<Long> zzu(List<zzst> object) {
        ArrayList<Long> arrayList = new ArrayList<Long>(object.size());
        object = object.iterator();
        while (object.hasNext()) {
            zzst zzst2 = (zzst)object.next();
            if (!this.zzg(zzst2)) {
                return arrayList;
            }
            arrayList.add(zzst2.zzpp());
            if (arrayList.size() < this.zzns().zzoG()) continue;
        }
        return arrayList;
    }

    private class zza {
        private ByteArrayOutputStream zzafA = new ByteArrayOutputStream();
        private int zzafz;

        public byte[] getPayload() {
            return this.zzafA.toByteArray();
        }

        public boolean zzj(zzst zzst2) {
            zzac.zzw(zzst2);
            if (this.zzafz + 1 > zzsz.this.zzns().zzoH()) {
                return false;
            }
            byte[] arrby = zzsz.this.zza(zzst2, false);
            if (arrby == null) {
                zzsz.this.zznr().zza(zzst2, "Error formatting hit");
                return true;
            }
            int n = (arrby = arrby.getBytes()).length;
            if (n > zzsz.this.zzns().zzoz()) {
                zzsz.this.zznr().zza(zzst2, "Hit size exceeds the maximum size limit");
                return true;
            }
            int n2 = n;
            if (this.zzafA.size() > 0) {
                n2 = n + 1;
            }
            if (this.zzafA.size() + n2 > zzsz.this.zzns().zzoB()) {
                return false;
            }
            try {
                if (this.zzafA.size() > 0) {
                    this.zzafA.write(zzafy);
                }
                this.zzafA.write(arrby);
                ++this.zzafz;
                return true;
            }
            catch (IOException iOException) {
                zzsz.this.zze("Failed to write payload when batching hits", iOException);
                return true;
            }
        }

        public int zzpD() {
            return this.zzafz;
        }
    }

}
