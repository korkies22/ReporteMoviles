/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.analytics.Logger;
import com.google.android.gms.tagmanager.zzbo;
import com.google.android.gms.tagmanager.zzdj;

static class zzdj.zza
implements Logger {
    zzdj.zza() {
    }

    private static int zzmX(int n) {
        switch (n) {
            default: {
                return 3;
            }
            case 5: {
                return 2;
            }
            case 3: 
            case 4: {
                return 1;
            }
            case 2: 
        }
        return 0;
    }

    @Override
    public void error(Exception exception) {
        zzbo.zzb("", exception);
    }

    @Override
    public void error(String string) {
        zzbo.e(string);
    }

    @Override
    public int getLogLevel() {
        return zzdj.zza.zzmX(zzbo.getLogLevel());
    }

    @Override
    public void info(String string) {
        zzbo.zzbd(string);
    }

    @Override
    public void setLogLevel(int n) {
        zzbo.zzbe("GA uses GTM logger. Please use TagManager.setLogLevel(int) instead.");
    }

    @Override
    public void verbose(String string) {
        zzbo.v(string);
    }

    @Override
    public void warn(String string) {
        zzbo.zzbe(string);
    }
}
