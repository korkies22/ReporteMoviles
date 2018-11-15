/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzah;
import com.google.android.gms.internal.zzaj;
import com.google.android.gms.internal.zzbgi;
import com.google.android.gms.tagmanager.zzdm;
import com.google.android.gms.tagmanager.zzn;
import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class zzbh {
    private static zzaj.zza zzK(Object object) throws JSONException {
        return zzdm.zzR(zzbh.zzL(object));
    }

    static Object zzL(Object object) throws JSONException {
        if (object instanceof JSONArray) {
            throw new RuntimeException("JSONArrays are not supported");
        }
        if (JSONObject.NULL.equals(object)) {
            throw new RuntimeException("JSON nulls are not supported");
        }
        if (object instanceof JSONObject) {
            object = (JSONObject)object;
            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            Iterator iterator = object.keys();
            while (iterator.hasNext()) {
                String string = (String)iterator.next();
                hashMap.put(string, zzbh.zzL(object.get(string)));
            }
            return hashMap;
        }
        return object;
    }

    public static zzbgi.zzc zzho(String object) throws JSONException {
        object = zzbh.zzK((Object)new JSONObject((String)object));
        zzbgi.zzd zzd2 = zzbgi.zzc.zzRW();
        for (int i = 0; i < object.zzly.length; ++i) {
            zzd2.zzc(zzbgi.zza.zzRU().zzb(zzah.zzhL.toString(), object.zzly[i]).zzb(zzah.zzhz.toString(), zzdm.zzhz(zzn.zzOy())).zzb(zzn.zzOz(), object.zzlz[i]).zzRV());
        }
        return zzd2.zzRY();
    }
}
