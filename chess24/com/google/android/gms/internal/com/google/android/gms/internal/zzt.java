/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.SystemClock
 *  org.apache.http.Header
 *  org.apache.http.HttpEntity
 *  org.apache.http.HttpResponse
 *  org.apache.http.StatusLine
 *  org.apache.http.conn.ConnectTimeoutException
 *  org.apache.http.impl.cookie.DateUtils
 */
package com.google.android.gms.internal;

import android.os.SystemClock;
import com.google.android.gms.internal.zza;
import com.google.android.gms.internal.zzaa;
import com.google.android.gms.internal.zzb;
import com.google.android.gms.internal.zzf;
import com.google.android.gms.internal.zzh;
import com.google.android.gms.internal.zzi;
import com.google.android.gms.internal.zzj;
import com.google.android.gms.internal.zzk;
import com.google.android.gms.internal.zzo;
import com.google.android.gms.internal.zzp;
import com.google.android.gms.internal.zzq;
import com.google.android.gms.internal.zzr;
import com.google.android.gms.internal.zzs;
import com.google.android.gms.internal.zzu;
import com.google.android.gms.internal.zzy;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.cookie.DateUtils;

public class zzt
implements zzf {
    protected static final boolean DEBUG = zzs.DEBUG;
    private static int zzam = 3000;
    private static int zzan = 4096;
    protected final zzy zzao;
    protected final zzu zzap;

    public zzt(zzy zzy2) {
        this(zzy2, new zzu(zzan));
    }

    public zzt(zzy zzy2, zzu zzu2) {
        this.zzao = zzy2;
        this.zzap = zzu2;
    }

    protected static Map<String, String> zza(Header[] arrheader) {
        TreeMap<String, String> treeMap = new TreeMap<String, String>(String.CASE_INSENSITIVE_ORDER);
        for (int i = 0; i < arrheader.length; ++i) {
            treeMap.put(arrheader[i].getName(), arrheader[i].getValue());
        }
        return treeMap;
    }

    private void zza(long l, zzk<?> zzk2, byte[] object, StatusLine statusLine) {
        if (DEBUG || l > (long)zzam) {
            object = object != null ? Integer.valueOf(((byte[])object).length) : "null";
            zzs.zzb("HTTP response for request=<%s> [lifetime=%d], [size=%s], [rc=%d], [retryCount=%s]", zzk2, l, object, statusLine.getStatusCode(), zzk2.zzq().zzd());
        }
    }

    private static void zza(String string, zzk<?> zzk2, zzr zzr2) throws zzr {
        zzo zzo2 = zzk2.zzq();
        int n = zzk2.zzp();
        try {
            zzo2.zza(zzr2);
        }
        catch (zzr zzr3) {
            zzk2.zzc(String.format("%s-timeout-giveup [timeout=%s]", string, n));
            throw zzr3;
        }
        zzk2.zzc(String.format("%s-retry [timeout=%s]", string, n));
    }

    private void zza(Map<String, String> map, zzb.zza zza2) {
        if (zza2 == null) {
            return;
        }
        if (zza2.zza != null) {
            map.put("If-None-Match", zza2.zza);
        }
        if (zza2.zzc > 0L) {
            map.put("If-Modified-Since", DateUtils.formatDate((Date)new Date(zza2.zzc)));
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private byte[] zza(HttpEntity httpEntity) throws IOException, zzp {
        zzaa zzaa2;
        void var4_9;
        byte[] arrby;
        block11 : {
            byte[] arrby2;
            block10 : {
                block12 : {
                    zzaa2 = new zzaa(this.zzap, (int)httpEntity.getContentLength());
                    byte[] arrby3 = null;
                    arrby2 = httpEntity.getContent();
                    if (arrby2 == null) {
                        throw new zzp();
                    }
                    arrby = this.zzap.zzb(1024);
                    try {
                        int n;
                        while ((n = arrby2.read(arrby)) != -1) {
                            zzaa2.write(arrby, 0, n);
                        }
                        arrby2 = zzaa2.toByteArray();
                    }
                    catch (Throwable throwable) {}
                    try {
                        httpEntity.consumeContent();
                        break block10;
                    }
                    catch (IOException iOException) {}
                    break block12;
                    catch (Throwable throwable) {
                        arrby = arrby3;
                    }
                }
                try {
                    httpEntity.consumeContent();
                    break block11;
                }
                catch (IOException iOException) {}
                zzs.zza("Error occured when calling consumingContent", new Object[0]);
            }
            this.zzap.zza(arrby);
            zzaa2.close();
            return arrby2;
            zzs.zza("Error occured when calling consumingContent", new Object[0]);
        }
        this.zzap.zza(arrby);
        zzaa2.close();
        throw var4_9;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public zzi zza(zzk<?> object) throws zzr {
        long l = SystemClock.elapsedRealtime();
        do {
            Object object2;
            Object object3;
            block32 : {
                Map<String, String> map;
                Object object4;
                int n;
                block28 : {
                    object4 = Collections.emptyMap();
                    try {
                        Object object5;
                        block31 : {
                            block30 : {
                                block29 : {
                                    block27 : {
                                        block26 : {
                                            block25 : {
                                                object3 = new HashMap();
                                                this.zza((Map<String, String>)object3, object.zzh());
                                                map = this.zzao.zza((zzk<?>)object, (Map<String, String>)object3);
                                                object5 = map.getStatusLine();
                                                n = object5.getStatusCode();
                                                object3 = zzt.zza(map.getAllHeaders());
                                                if (n != 304) break block25;
                                                object2 = object.zzh();
                                                if (object2 == null) {
                                                    return new zzi(304, null, (Map<String, String>)object3, true, SystemClock.elapsedRealtime() - l);
                                                }
                                                object2.zzf.putAll((Map<String, String>)object3);
                                                return new zzi(304, object2.data, object2.zzf, true, SystemClock.elapsedRealtime() - l);
                                            }
                                            try {
                                                object2 = map.getEntity();
                                                if (object2 == null) break block26;
                                            }
                                            catch (IOException iOException) {
                                                object4 = object3;
                                                object5 = null;
                                                object3 = iOException;
                                                object2 = object5;
                                                break block28;
                                            }
                                            try {
                                                object2 = this.zza(map.getEntity());
                                                break block27;
                                            }
                                            catch (IOException iOException) {
                                                object5 = null;
                                                object4 = object3;
                                                object3 = iOException;
                                                object2 = object5;
                                                break block28;
                                            }
                                        }
                                        object2 = new byte[]{};
                                    }
                                    this.zza(SystemClock.elapsedRealtime() - l, (zzk<?>)object, (byte[])object2, (StatusLine)object5);
                                    if (n < 200) throw new IOException();
                                    if (n <= 299) break block29;
                                    throw new IOException();
                                }
                                long l2 = SystemClock.elapsedRealtime();
                                try {
                                    return new zzi(n, (byte[])object2, (Map<String, String>)object3, false, l2 - l);
                                }
                                catch (IOException iOException) {
                                    break block30;
                                }
                                catch (IOException iOException) {
                                    // empty catch block
                                }
                            }
                            object5 = object4;
                            object4 = object3;
                            object3 = object5;
                            break block28;
                            catch (IOException iOException) {
                                object3 = map;
                            }
                            break block31;
                            catch (IOException iOException) {
                                object3 = null;
                            }
                        }
                        map = null;
                        object5 = object2;
                        object2 = map;
                        map = object3;
                        object3 = object5;
                    }
                    catch (MalformedURLException malformedURLException) {
                        object = String.valueOf(object.getUrl());
                        if (object.length() != 0) {
                            object = "Bad URL ".concat((String)object);
                            throw new RuntimeException((String)object, malformedURLException);
                        }
                        object = new String("Bad URL ");
                        throw new RuntimeException((String)object, malformedURLException);
                    }
                    catch (SocketTimeoutException socketTimeoutException) {}
                }
                if (map == null) throw new zzj((Throwable)object3);
                n = map.getStatusLine().getStatusCode();
                zzs.zzc("Unexpected response code %d for %s", n, object.getUrl());
                if (object2 == null) throw new zzh(null);
                object3 = new zzi(n, (byte[])object2, (Map<String, String>)object4, false, SystemClock.elapsedRealtime() - l);
                if (n != 401) {
                    if (n != 403) throw new zzp((zzi)object3);
                }
                zzt.zza("auth", object, new zza((zzi)object3));
                continue;
                object3 = "socket";
                object2 = new zzq();
                break block32;
                catch (ConnectTimeoutException connectTimeoutException) {}
                object3 = "connection";
                object2 = new zzq();
            }
            zzt.zza((String)object3, object, (zzr)object2);
        } while (true);
    }
}
