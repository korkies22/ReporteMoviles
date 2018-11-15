/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.content.ComponentCallbacks
 *  android.content.ComponentCallbacks2
 *  android.content.Context
 *  android.content.res.Configuration
 *  android.net.Uri
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Handler
 *  android.os.Looper
 */
package com.google.android.gms.tagmanager;

import android.annotation.TargetApi;
import android.content.ComponentCallbacks;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.RawRes;
import android.support.annotation.RequiresPermission;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.tagmanager.ContainerHolder;
import com.google.android.gms.tagmanager.DataLayer;
import com.google.android.gms.tagmanager.zzbo;
import com.google.android.gms.tagmanager.zzcj;
import com.google.android.gms.tagmanager.zzd;
import com.google.android.gms.tagmanager.zzdb;
import com.google.android.gms.tagmanager.zzdc;
import com.google.android.gms.tagmanager.zzo;
import com.google.android.gms.tagmanager.zzp;
import com.google.android.gms.tagmanager.zzt;
import com.google.android.gms.tagmanager.zzx;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class TagManager {
    private static TagManager zzbGy;
    private final Context mContext;
    private final DataLayer zzbCT;
    private final zzt zzbFs;
    private final zza zzbGv;
    private final zzdb zzbGw;
    private final ConcurrentMap<String, zzo> zzbGx;

    TagManager(Context context, zza zza2, DataLayer dataLayer, zzdb zzdb2) {
        if (context == null) {
            throw new NullPointerException("context cannot be null");
        }
        this.mContext = context.getApplicationContext();
        this.zzbGw = zzdb2;
        this.zzbGv = zza2;
        this.zzbGx = new ConcurrentHashMap<String, zzo>();
        this.zzbCT = dataLayer;
        this.zzbCT.zza(new DataLayer.zzb(){

            @Override
            public void zzZ(Map<String, Object> object) {
                if ((object = object.get("event")) != null) {
                    TagManager.this.zzhv(object.toString());
                }
            }
        });
        this.zzbCT.zza(new zzd(this.mContext));
        this.zzbFs = new zzt();
        this.zzPZ();
        this.zzQa();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @RequiresPermission(allOf={"android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE"})
    public static TagManager getInstance(Context object) {
        synchronized (TagManager.class) {
            if (zzbGy != null) return zzbGy;
            if (object == null) {
                zzbo.e("TagManager.getInstance requires non-null context.");
                throw new NullPointerException();
            }
            zzbGy = new TagManager((Context)object, new zza(){

                @Override
                public zzp zza(Context context, TagManager tagManager, Looper looper, String string, int n, zzt zzt2) {
                    return new zzp(context, tagManager, looper, string, n, zzt2);
                }
            }, new DataLayer(new zzx((Context)object)), zzdc.zzPT());
            return zzbGy;
        }
    }

    @TargetApi(value=14)
    private void zzPZ() {
        if (Build.VERSION.SDK_INT >= 14) {
            this.mContext.registerComponentCallbacks((ComponentCallbacks)new ComponentCallbacks2(){

                public void onConfigurationChanged(Configuration configuration) {
                }

                public void onLowMemory() {
                }

                public void onTrimMemory(int n) {
                    if (n == 20) {
                        TagManager.this.dispatch();
                    }
                }
            });
        }
    }

    private void zzQa() {
        com.google.android.gms.tagmanager.zza.zzbA(this.mContext);
    }

    private void zzhv(String string) {
        Iterator iterator = this.zzbGx.values().iterator();
        while (iterator.hasNext()) {
            ((zzo)iterator.next()).zzgX(string);
        }
    }

    public void dispatch() {
        this.zzbGw.dispatch();
    }

    public DataLayer getDataLayer() {
        return this.zzbCT;
    }

    public PendingResult<ContainerHolder> loadContainerDefaultOnly(String object, @RawRes int n) {
        object = this.zzbGv.zza(this.mContext, this, null, (String)object, n, this.zzbFs);
        object.zzOF();
        return object;
    }

    public PendingResult<ContainerHolder> loadContainerDefaultOnly(String object, @RawRes int n, Handler handler) {
        object = this.zzbGv.zza(this.mContext, this, handler.getLooper(), (String)object, n, this.zzbFs);
        object.zzOF();
        return object;
    }

    public PendingResult<ContainerHolder> loadContainerPreferFresh(String object, @RawRes int n) {
        object = this.zzbGv.zza(this.mContext, this, null, (String)object, n, this.zzbFs);
        object.zzOH();
        return object;
    }

    public PendingResult<ContainerHolder> loadContainerPreferFresh(String object, @RawRes int n, Handler handler) {
        object = this.zzbGv.zza(this.mContext, this, handler.getLooper(), (String)object, n, this.zzbFs);
        object.zzOH();
        return object;
    }

    public PendingResult<ContainerHolder> loadContainerPreferNonDefault(String object, @RawRes int n) {
        object = this.zzbGv.zza(this.mContext, this, null, (String)object, n, this.zzbFs);
        object.zzOG();
        return object;
    }

    public PendingResult<ContainerHolder> loadContainerPreferNonDefault(String object, @RawRes int n, Handler handler) {
        object = this.zzbGv.zza(this.mContext, this, handler.getLooper(), (String)object, n, this.zzbFs);
        object.zzOG();
        return object;
    }

    public void setVerboseLoggingEnabled(boolean bl) {
        int n = bl ? 2 : 5;
        zzbo.setLogLevel(n);
    }

    public int zza(zzo zzo2) {
        this.zzbGx.put(zzo2.getContainerId(), zzo2);
        return this.zzbGx.size();
    }

    public boolean zzb(zzo zzo2) {
        if (this.zzbGx.remove(zzo2.getContainerId()) != null) {
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    boolean zzv(Uri object) {
        synchronized (this) {
            zzcj zzcj2 = zzcj.zzPz();
            if (!zzcj2.zzv((Uri)object)) return false;
            object = zzcj2.getContainerId();
            switch (.zzbGA[zzcj2.zzPA().ordinal()]) {
                case 2: 
                case 3: {
                    Iterator iterator = this.zzbGx.keySet().iterator();
                    while (iterator.hasNext()) {
                        String string = (String)iterator.next();
                        zzo zzo2 = (zzo)this.zzbGx.get(string);
                        if (string.equals(object)) {
                            zzo2.zzgZ(zzcj2.zzPB());
                        } else {
                            if (zzo2.zzOC() == null) continue;
                            zzo2.zzgZ(null);
                        }
                        zzo2.refresh();
                    }
                    return true;
                }
                case 1: {
                    object = (zzo)this.zzbGx.get(object);
                    if (object == null) return true;
                    object.zzgZ(null);
                    object.refresh();
                    return true;
                }
            }
            return true;
        }
    }

    public static interface zza {
        public zzp zza(Context var1, TagManager var2, Looper var3, String var4, int var5, zzt var6);
    }

}
