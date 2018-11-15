/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Logger;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.tagmanager.zzbo;

public class zzdj {
    private Context mContext;
    private Tracker zzaaC;
    private GoogleAnalytics zzaaE;

    public zzdj(Context context) {
        this.mContext = context;
    }

    private void zzhx(String string) {
        synchronized (this) {
            if (this.zzaaE == null) {
                this.zzaaE = GoogleAnalytics.getInstance(this.mContext);
                this.zzaaE.setLogger(new zza());
                this.zzaaC = this.zzaaE.newTracker(string);
            }
            return;
        }
    }

    public Tracker zzhw(String string) {
        this.zzhx(string);
        return this.zzaaC;
    }

    static class zza
    implements Logger {
        zza() {
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
            return zza.zzmX(zzbo.getLogLevel());
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

}
