/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 */
package com.google.android.gms.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.util.zzc;
import com.google.android.gms.common.util.zzp;
import com.google.android.gms.common.util.zzq;
import com.google.android.gms.internal.zzacf;
import com.google.android.gms.internal.zzacm;
import com.google.android.gms.internal.zzaco;
import com.google.android.gms.internal.zzacr;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class zzack {
    /*
     * Enabled aggressive block sorting
     */
    private void zza(StringBuilder stringBuilder, zza object, Object object2) {
        if (object.zzxL() == 11) {
            object = object.zzxR().cast(object2).toString();
        } else {
            if (object.zzxL() != 7) {
                stringBuilder.append(object2);
                return;
            }
            stringBuilder.append("\"");
            stringBuilder.append(zzp.zzdC((String)object2));
            object = "\"";
        }
        stringBuilder.append((String)object);
    }

    private void zza(StringBuilder stringBuilder, zza zza2, ArrayList<Object> arrayList) {
        stringBuilder.append("[");
        int n = arrayList.size();
        for (int i = 0; i < n; ++i) {
            Object object;
            if (i > 0) {
                stringBuilder.append(",");
            }
            if ((object = arrayList.get(i)) == null) continue;
            this.zza(stringBuilder, zza2, object);
        }
        stringBuilder.append("]");
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public String toString() {
        var3_1 = this.zzxK();
        var2_2 = new StringBuilder(100);
        block5 : for (String var5_5 : var3_1.keySet()) {
            block6 : {
                var6_6 = var3_1.get(var5_5);
                if (!this.zza(var6_6)) continue;
                var7_7 = this.zza(var6_6, this.zzb(var6_6));
                var1_4 = var2_2.length() == 0 ? "{" : ",";
                var2_2.append(var1_4);
                var2_2.append("\"");
                var2_2.append(var5_5);
                var2_2.append("\":");
                if (var7_7 != null) break block6;
                var1_4 = "null";
                ** GOTO lbl33
            }
            switch (var6_6.zzxN()) {
                default: {
                    if (!var6_6.zzxM()) break;
                    this.zza(var2_2, var6_6, (ArrayList)var7_7);
                    continue block5;
                }
                case 10: {
                    zzq.zza(var2_2, (HashMap)var7_7);
                    continue block5;
                }
                case 9: {
                    var2_2.append("\"");
                    var1_4 = zzc.zzr((byte[])var7_7);
                    ** break;
                }
                case 8: {
                    var2_2.append("\"");
                    var1_4 = zzc.zzq((byte[])var7_7);
lbl31: // 2 sources:
                    var2_2.append(var1_4);
                    var1_4 = "\"";
lbl33: // 2 sources:
                    var2_2.append(var1_4);
                    continue block5;
                }
            }
            this.zza(var2_2, var6_6, var7_7);
        }
        var1_4 = var2_2.length() > 0 ? "}" : "{}";
        var2_2.append(var1_4);
        return var2_2.toString();
    }

    protected <O, I> I zza(zza<I, O> zza2, Object object) {
        if (zza2.zzaFJ != null) {
            return zza2.convertBack(object);
        }
        return (I)object;
    }

    protected boolean zza(zza zza2) {
        if (zza2.zzxN() == 11) {
            if (zza2.zzxO()) {
                return this.zzdz(zza2.zzxP());
            }
            return this.zzdy(zza2.zzxP());
        }
        return this.zzdx(zza2.zzxP());
    }

    protected Object zzb(zza object) {
        CharSequence charSequence = object.zzxP();
        if (object.zzxR() != null) {
            this.zzdw(object.zzxP());
            zzac.zza(true, "Concrete field shouldn't be value object: %s", object.zzxP());
            object.zzxO();
            try {
                char c = Character.toUpperCase(charSequence.charAt(0));
                object = String.valueOf(charSequence.substring(1));
                charSequence = new StringBuilder(4 + String.valueOf(object).length());
                charSequence.append("get");
                charSequence.append(c);
                charSequence.append((String)object);
                object = charSequence.toString();
                object = this.getClass().getMethod((String)object, new Class[0]).invoke(this, new Object[0]);
                return object;
            }
            catch (Exception exception) {
                throw new RuntimeException(exception);
            }
        }
        return this.zzdw(object.zzxP());
    }

    protected abstract Object zzdw(String var1);

    protected abstract boolean zzdx(String var1);

    protected boolean zzdy(String string) {
        throw new UnsupportedOperationException("Concrete types not supported");
    }

    protected boolean zzdz(String string) {
        throw new UnsupportedOperationException("Concrete type arrays not supported");
    }

    public abstract Map<String, zza<?, ?>> zzxK();

    public static class zza<I, O>
    extends com.google.android.gms.common.internal.safeparcel.zza {
        public static final zzacm CREATOR = new zzacm();
        private final int mVersionCode;
        protected final int zzaFA;
        protected final boolean zzaFB;
        protected final int zzaFC;
        protected final boolean zzaFD;
        protected final String zzaFE;
        protected final int zzaFF;
        protected final Class<? extends zzack> zzaFG;
        protected final String zzaFH;
        private zzaco zzaFI;
        private zzb<I, O> zzaFJ;

        /*
         * Enabled aggressive block sorting
         */
        zza(int n, int n2, boolean bl, int n3, boolean bl2, String zzb2, int n4, String string, zzacf zzacf2) {
            this.mVersionCode = n;
            this.zzaFA = n2;
            this.zzaFB = bl;
            this.zzaFC = n3;
            this.zzaFD = bl2;
            this.zzaFE = zzb2;
            this.zzaFF = n4;
            zzb2 = null;
            if (string == null) {
                this.zzaFG = null;
                this.zzaFH = null;
            } else {
                this.zzaFG = zzacr.class;
                this.zzaFH = string;
            }
            if (zzacf2 != null) {
                zzb2 = zzacf2.zzxI();
            }
            this.zzaFJ = zzb2;
        }

        /*
         * Enabled aggressive block sorting
         */
        protected zza(int n, boolean bl, int n2, boolean bl2, String string, int n3, Class<? extends zzack> class_, zzb<I, O> zzb2) {
            this.mVersionCode = 1;
            this.zzaFA = n;
            this.zzaFB = bl;
            this.zzaFC = n2;
            this.zzaFD = bl2;
            this.zzaFE = string;
            this.zzaFF = n3;
            this.zzaFG = class_;
            string = class_ == null ? null : class_.getCanonicalName();
            this.zzaFH = string;
            this.zzaFJ = zzb2;
        }

        public static zza zza(String string, int n, zzb<?, ?> zzb2, boolean bl) {
            return new zza(7, bl, 0, false, string, n, null, zzb2);
        }

        public static <T extends zzack> zza<T, T> zza(String string, int n, Class<T> class_) {
            return new zza<I, O>(11, false, 11, false, string, n, class_, null);
        }

        public static <T extends zzack> zza<ArrayList<T>, ArrayList<T>> zzb(String string, int n, Class<T> class_) {
            return new zza<ArrayList<T>, ArrayList<T>>(11, true, 11, true, string, n, class_, null);
        }

        public static zza<Integer, Integer> zzk(String string, int n) {
            return new zza<Integer, Integer>(0, false, 0, false, string, n, null, null);
        }

        public static zza<Boolean, Boolean> zzl(String string, int n) {
            return new zza<Boolean, Boolean>(6, false, 6, false, string, n, null, null);
        }

        public static zza<String, String> zzm(String string, int n) {
            return new zza<String, String>(7, false, 7, false, string, n, null, null);
        }

        public I convertBack(O o) {
            return this.zzaFJ.convertBack(o);
        }

        public int getVersionCode() {
            return this.mVersionCode;
        }

        public String toString() {
            zzaa.zza zza2 = zzaa.zzv(this).zzg("versionCode", this.mVersionCode).zzg("typeIn", this.zzaFA).zzg("typeInArray", this.zzaFB).zzg("typeOut", this.zzaFC).zzg("typeOutArray", this.zzaFD).zzg("outputFieldName", this.zzaFE).zzg("safeParcelFieldId", this.zzaFF).zzg("concreteTypeName", this.zzxS());
            Class<zzack> class_ = this.zzxR();
            if (class_ != null) {
                zza2.zzg("concreteType.class", class_.getCanonicalName());
            }
            if (this.zzaFJ != null) {
                zza2.zzg("converterName", this.zzaFJ.getClass().getCanonicalName());
            }
            return zza2.toString();
        }

        public void writeToParcel(Parcel parcel, int n) {
            zzacm.zza(this, parcel, n);
        }

        public void zza(zzaco zzaco2) {
            this.zzaFI = zzaco2;
        }

        public int zzxL() {
            return this.zzaFA;
        }

        public boolean zzxM() {
            return this.zzaFB;
        }

        public int zzxN() {
            return this.zzaFC;
        }

        public boolean zzxO() {
            return this.zzaFD;
        }

        public String zzxP() {
            return this.zzaFE;
        }

        public int zzxQ() {
            return this.zzaFF;
        }

        public Class<? extends zzack> zzxR() {
            return this.zzaFG;
        }

        String zzxS() {
            if (this.zzaFH == null) {
                return null;
            }
            return this.zzaFH;
        }

        public boolean zzxT() {
            if (this.zzaFJ != null) {
                return true;
            }
            return false;
        }

        zzacf zzxU() {
            if (this.zzaFJ == null) {
                return null;
            }
            return zzacf.zza(this.zzaFJ);
        }

        public Map<String, zza<?, ?>> zzxV() {
            zzac.zzw(this.zzaFH);
            zzac.zzw(this.zzaFI);
            return this.zzaFI.zzdA(this.zzaFH);
        }
    }

    public static interface zzb<I, O> {
        public I convertBack(O var1);
    }

}
