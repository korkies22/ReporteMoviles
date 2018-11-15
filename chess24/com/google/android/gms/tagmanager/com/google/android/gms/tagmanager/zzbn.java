/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.tagmanager;

interface zzbn<T> {
    public void onSuccess(T var1);

    public void zza(zza var1);

    public static final class zza
    extends Enum<zza> {
        public static final /* enum */ zza zzbEG = new zza();
        public static final /* enum */ zza zzbEH = new zza();
        public static final /* enum */ zza zzbEI = new zza();
        public static final /* enum */ zza zzbEJ = new zza();
        private static final /* synthetic */ zza[] zzbEK;

        static {
            zzbEK = new zza[]{zzbEG, zzbEH, zzbEI, zzbEJ};
        }

        private zza() {
        }

        public static zza[] values() {
            return (zza[])zzbEK.clone();
        }
    }

}
