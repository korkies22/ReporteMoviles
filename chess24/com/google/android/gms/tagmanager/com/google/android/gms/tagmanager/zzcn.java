/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzag;
import com.google.android.gms.internal.zzah;
import com.google.android.gms.internal.zzaj;
import com.google.android.gms.tagmanager.zzdh;
import com.google.android.gms.tagmanager.zzdm;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

class zzcn
extends zzdh {
    private static final String ID = zzag.zzel.toString();
    private static final String zzbFn = zzah.zzhI.toString();

    public zzcn() {
        super(ID);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    protected boolean zza(String string, String string2, Map<String, zzaj.zza> map) {
        int n = zzdm.zzi(map.get(zzbFn)) != false ? 66 : 64;
        try {
            return Pattern.compile(string2, n).matcher(string).find();
        }
        catch (PatternSyntaxException patternSyntaxException) {
            return false;
        }
    }
}
