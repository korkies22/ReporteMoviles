/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.internal.zzai;
import com.google.android.gms.internal.zzaj;
import com.google.android.gms.internal.zzbgi;
import com.google.android.gms.tagmanager.DataLayer;
import com.google.android.gms.tagmanager.zzaj;
import com.google.android.gms.tagmanager.zzbo;
import com.google.android.gms.tagmanager.zzbw;
import com.google.android.gms.tagmanager.zzce;
import com.google.android.gms.tagmanager.zzcj;
import com.google.android.gms.tagmanager.zzcx;
import com.google.android.gms.tagmanager.zzdm;
import com.google.android.gms.tagmanager.zzu;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Container {
    private final Context mContext;
    private final String zzbCS;
    private final DataLayer zzbCT;
    private zzcx zzbCU;
    private Map<String, FunctionCallMacroCallback> zzbCV = new HashMap<String, FunctionCallMacroCallback>();
    private Map<String, FunctionCallTagCallback> zzbCW = new HashMap<String, FunctionCallTagCallback>();
    private volatile long zzbCX;
    private volatile String zzbCY = "";

    Container(Context context, DataLayer dataLayer, String string, long l, zzai.zzj zzj2) {
        this.mContext = context;
        this.zzbCT = dataLayer;
        this.zzbCS = string;
        this.zzbCX = l;
        this.zza(zzj2.zzlu);
        if (zzj2.zzlt != null) {
            this.zza(zzj2.zzlt);
        }
    }

    Container(Context context, DataLayer dataLayer, String string, long l, zzbgi.zzc zzc2) {
        this.mContext = context;
        this.zzbCT = dataLayer;
        this.zzbCS = string;
        this.zzbCX = l;
        this.zza(zzc2);
    }

    private zzcx zzOB() {
        synchronized (this) {
            zzcx zzcx2 = this.zzbCU;
            return zzcx2;
        }
    }

    private void zza(zzai.zzf object) {
        if (object == null) {
            throw new NullPointerException();
        }
        try {
            zzbgi.zzc zzc2 = zzbgi.zzb((zzai.zzf)object);
            this.zza(zzc2);
            return;
        }
        catch (zzbgi.zzg zzg2) {
            object = String.valueOf(object);
            String string = String.valueOf(zzg2.toString());
            StringBuilder stringBuilder = new StringBuilder(46 + String.valueOf(object).length() + String.valueOf(string).length());
            stringBuilder.append("Not loading resource: ");
            stringBuilder.append((String)object);
            stringBuilder.append(" because it is invalid: ");
            stringBuilder.append(string);
            zzbo.e(stringBuilder.toString());
            return;
        }
    }

    private void zza(zzbgi.zzc zzc2) {
        this.zzbCY = zzc2.getVersion();
        zzaj zzaj2 = this.zzgY(this.zzbCY);
        this.zza(new zzcx(this.mContext, zzc2, this.zzbCT, new zza(), new zzb(), zzaj2));
        if (this.getBoolean("_gtm.loadEventEnabled")) {
            this.zzbCT.pushEvent("gtm.load", DataLayer.mapOf("gtm.id", this.zzbCS));
        }
    }

    private void zza(zzcx zzcx2) {
        synchronized (this) {
            this.zzbCU = zzcx2;
            return;
        }
    }

    private void zza(zzai.zzi[] arrzzi) {
        ArrayList<zzai.zzi> arrayList = new ArrayList<zzai.zzi>();
        int n = arrzzi.length;
        for (int i = 0; i < n; ++i) {
            arrayList.add(arrzzi[i]);
        }
        this.zzOB().zzN(arrayList);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean getBoolean(String string) {
        Object object = this.zzOB();
        if (object == null) {
            string = "getBoolean called for closed container.";
        } else {
            try {
                return zzdm.zzi(object.zzht(string).getObject());
            }
            catch (Exception exception) {
                string = String.valueOf(exception.getMessage());
                object = new StringBuilder(66 + String.valueOf(string).length());
                object.append("Calling getBoolean() threw an exception: ");
                object.append(string);
                object.append(" Returning default value.");
                string = object.toString();
            }
        }
        zzbo.e(string);
        return zzdm.zzQj();
    }

    public String getContainerId() {
        return this.zzbCS;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public double getDouble(String string) {
        Object object = this.zzOB();
        if (object == null) {
            string = "getDouble called for closed container.";
        } else {
            try {
                return zzdm.zzh(object.zzht(string).getObject());
            }
            catch (Exception exception) {
                string = String.valueOf(exception.getMessage());
                object = new StringBuilder(65 + String.valueOf(string).length());
                object.append("Calling getDouble() threw an exception: ");
                object.append(string);
                object.append(" Returning default value.");
                string = object.toString();
            }
        }
        zzbo.e(string);
        return zzdm.zzQi();
    }

    public long getLastRefreshTime() {
        return this.zzbCX;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public long getLong(String string) {
        Object object = this.zzOB();
        if (object == null) {
            string = "getLong called for closed container.";
        } else {
            try {
                return zzdm.zzg(object.zzht(string).getObject());
            }
            catch (Exception exception) {
                string = String.valueOf(exception.getMessage());
                object = new StringBuilder(63 + String.valueOf(string).length());
                object.append("Calling getLong() threw an exception: ");
                object.append(string);
                object.append(" Returning default value.");
                string = object.toString();
            }
        }
        zzbo.e(string);
        return zzdm.zzQh();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String getString(String string) {
        Object object = this.zzOB();
        if (object == null) {
            string = "getString called for closed container.";
        } else {
            try {
                return zzdm.zze(object.zzht(string).getObject());
            }
            catch (Exception exception) {
                string = String.valueOf(exception.getMessage());
                object = new StringBuilder(65 + String.valueOf(string).length());
                object.append("Calling getString() threw an exception: ");
                object.append(string);
                object.append(" Returning default value.");
                string = object.toString();
            }
        }
        zzbo.e(string);
        return zzdm.zzQl();
    }

    public boolean isDefault() {
        if (this.getLastRefreshTime() == 0L) {
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void registerFunctionCallMacroCallback(String string, FunctionCallMacroCallback functionCallMacroCallback) {
        if (functionCallMacroCallback == null) {
            throw new NullPointerException("Macro handler must be non-null");
        }
        Map<String, FunctionCallMacroCallback> map = this.zzbCV;
        synchronized (map) {
            this.zzbCV.put(string, functionCallMacroCallback);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void registerFunctionCallTagCallback(String string, FunctionCallTagCallback functionCallTagCallback) {
        if (functionCallTagCallback == null) {
            throw new NullPointerException("Tag callback must be non-null");
        }
        Map<String, FunctionCallTagCallback> map = this.zzbCW;
        synchronized (map) {
            this.zzbCW.put(string, functionCallTagCallback);
            return;
        }
    }

    void release() {
        this.zzbCU = null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void unregisterFunctionCallMacroCallback(String string) {
        Map<String, FunctionCallMacroCallback> map = this.zzbCV;
        synchronized (map) {
            this.zzbCV.remove(string);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void unregisterFunctionCallTagCallback(String string) {
        Map<String, FunctionCallTagCallback> map = this.zzbCW;
        synchronized (map) {
            this.zzbCW.remove(string);
            return;
        }
    }

    public String zzOA() {
        return this.zzbCY;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    FunctionCallMacroCallback zzgV(String object) {
        Map<String, FunctionCallMacroCallback> map = this.zzbCV;
        synchronized (map) {
            return this.zzbCV.get(object);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public FunctionCallTagCallback zzgW(String object) {
        Map<String, FunctionCallTagCallback> map = this.zzbCW;
        synchronized (map) {
            return this.zzbCW.get(object);
        }
    }

    public void zzgX(String string) {
        this.zzOB().zzgX(string);
    }

    zzaj zzgY(String string) {
        zzcj.zzPz().zzPA().equals((Object)zzcj.zza.zzbFh);
        return new zzbw();
    }

    public static interface FunctionCallMacroCallback {
        public Object getValue(String var1, Map<String, Object> var2);
    }

    public static interface FunctionCallTagCallback {
        public void execute(String var1, Map<String, Object> var2);
    }

    private class zza
    implements zzu.zza {
        private zza() {
        }

        @Override
        public Object zze(String string, Map<String, Object> map) {
            FunctionCallMacroCallback functionCallMacroCallback = Container.this.zzgV(string);
            if (functionCallMacroCallback == null) {
                return null;
            }
            return functionCallMacroCallback.getValue(string, map);
        }
    }

    private class zzb
    implements zzu.zza {
        private zzb() {
        }

        @Override
        public Object zze(String string, Map<String, Object> map) {
            FunctionCallTagCallback functionCallTagCallback = Container.this.zzgW(string);
            if (functionCallTagCallback != null) {
                functionCallTagCallback.execute(string, map);
            }
            return zzdm.zzQl();
        }
    }

}
