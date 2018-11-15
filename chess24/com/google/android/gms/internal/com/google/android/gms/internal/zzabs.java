/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Binder
 */
package com.google.android.gms.internal;

import android.os.Binder;

public abstract class zzabs<T> {
    private static String READ_PERMISSION = "com.google.android.providers.gsf.permission.READ_GSERVICES";
    private static zza zzaCd;
    private static int zzaCe;
    private static final Object zztU;
    protected final String zzAH;
    protected final T zzAI;
    private T zzaCf = null;

    static {
        zztU = new Object();
    }

    protected zzabs(String string, T t) {
        this.zzAH = string;
        this.zzAI = t;
    }

    public static zzabs<String> zzA(String string, String string2) {
        return new zzabs<String>(string, string2){

            @Override
            protected /* synthetic */ Object zzdd(String string) {
                return this.zzdi(string);
            }

            protected String zzdi(String string) {
                string = this.zzAH;
                string = (String)this.zzAI;
                throw new NullPointerException();
            }
        };
    }

    public static zzabs<Float> zza(String string, Float f) {
        return new zzabs<Float>(string, f){

            @Override
            protected /* synthetic */ Object zzdd(String string) {
                return this.zzdh(string);
            }

            protected Float zzdh(String object) {
                object = this.zzAH;
                object = (Float)this.zzAI;
                throw new NullPointerException();
            }
        };
    }

    public static zzabs<Integer> zza(String string, Integer n) {
        return new zzabs<Integer>(string, n){

            @Override
            protected /* synthetic */ Object zzdd(String string) {
                return this.zzdg(string);
            }

            protected Integer zzdg(String object) {
                object = this.zzAH;
                object = (Integer)this.zzAI;
                throw new NullPointerException();
            }
        };
    }

    public static zzabs<Long> zza(String string, Long l) {
        return new zzabs<Long>(string, l){

            @Override
            protected /* synthetic */ Object zzdd(String string) {
                return this.zzdf(string);
            }

            protected Long zzdf(String object) {
                object = this.zzAH;
                object = (Long)this.zzAI;
                throw new NullPointerException();
            }
        };
    }

    public static zzabs<Boolean> zzj(String string, boolean bl) {
        return new zzabs<Boolean>(string, Boolean.valueOf(bl)){

            @Override
            protected /* synthetic */ Object zzdd(String string) {
                return this.zzde(string);
            }

            protected Boolean zzde(String object) {
                object = this.zzAH;
                object = (Boolean)this.zzAI;
                throw new NullPointerException();
            }
        };
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final T get() {
        T t;
        try {
            t = this.zzdd(this.zzAH);
        }
        catch (SecurityException securityException) {}
        return t;
        long l = Binder.clearCallingIdentity();
        try {
            T t2 = this.zzdd(this.zzAH);
            return t2;
        }
        finally {
            Binder.restoreCallingIdentity((long)l);
        }
    }

    protected abstract T zzdd(String var1);

    private static interface zza {
        public Long getLong(String var1, Long var2);

        public String getString(String var1, String var2);

        public Boolean zza(String var1, Boolean var2);

        public Float zzb(String var1, Float var2);

        public Integer zzb(String var1, Integer var2);
    }

}
