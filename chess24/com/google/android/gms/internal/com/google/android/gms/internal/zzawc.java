/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.internal.zzawd;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Comparator;

public class zzawc
extends com.google.android.gms.common.internal.safeparcel.zza
implements Comparable<zzawc> {
    public static final Parcelable.Creator<zzawc> CREATOR = new zzawd();
    private static final Charset UTF_8 = Charset.forName("UTF-8");
    public static final zza zzbzx = new zza();
    final int mVersionCode;
    public final String name;
    final String zzaFy;
    final boolean zzbgG;
    final double zzbgI;
    final long zzbzt;
    final byte[] zzbzu;
    public final int zzbzv;
    public final int zzbzw;

    zzawc(int n, String string, long l, boolean bl, double d, String string2, byte[] arrby, int n2, int n3) {
        this.mVersionCode = n;
        this.name = string;
        this.zzbzt = l;
        this.zzbgG = bl;
        this.zzbgI = d;
        this.zzaFy = string2;
        this.zzbzu = arrby;
        this.zzbzv = n2;
        this.zzbzw = n3;
    }

    private static int compare(byte by, byte by2) {
        return by - by2;
    }

    private static int compare(int n, int n2) {
        if (n < n2) {
            return -1;
        }
        if (n == n2) {
            return 0;
        }
        return 1;
    }

    private static int compare(long l, long l2) {
        if (l < l2) {
            return -1;
        }
        if (l == l2) {
            return 0;
        }
        return 1;
    }

    private static int compare(String string, String string2) {
        if (string == string2) {
            return 0;
        }
        if (string == null) {
            return -1;
        }
        if (string2 == null) {
            return 1;
        }
        return string.compareTo(string2);
    }

    private static int compare(boolean bl, boolean bl2) {
        if (bl == bl2) {
            return 0;
        }
        if (bl) {
            return 1;
        }
        return -1;
    }

    @Override
    public /* synthetic */ int compareTo(Object object) {
        return this.zza((zzawc)object);
    }

    public boolean equals(Object object) {
        boolean bl = false;
        boolean bl2 = false;
        boolean bl3 = false;
        boolean bl4 = bl2;
        if (object != null) {
            bl4 = bl2;
            if (object instanceof zzawc) {
                object = (zzawc)object;
                bl4 = bl2;
                if (this.mVersionCode == object.mVersionCode) {
                    bl4 = bl2;
                    if (zzaa.equal(this.name, object.name)) {
                        bl4 = bl2;
                        if (this.zzbzv == object.zzbzv) {
                            if (this.zzbzw != object.zzbzw) {
                                return false;
                            }
                            switch (this.zzbzv) {
                                default: {
                                    int n = this.zzbzv;
                                    object = new StringBuilder(31);
                                    object.append("Invalid enum value: ");
                                    object.append(n);
                                    throw new AssertionError((Object)object.toString());
                                }
                                case 5: {
                                    return Arrays.equals(this.zzbzu, object.zzbzu);
                                }
                                case 4: {
                                    return zzaa.equal(this.zzaFy, object.zzaFy);
                                }
                                case 3: {
                                    bl4 = bl3;
                                    if (this.zzbgI == object.zzbgI) {
                                        bl4 = true;
                                    }
                                    return bl4;
                                }
                                case 2: {
                                    bl4 = bl;
                                    if (this.zzbgG == object.zzbgG) {
                                        bl4 = true;
                                    }
                                    return bl4;
                                }
                                case 1: 
                            }
                            bl4 = bl2;
                            if (this.zzbzt == object.zzbzt) {
                                bl4 = true;
                            }
                        }
                    }
                }
            }
        }
        return bl4;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        this.zza(stringBuilder);
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel parcel, int n) {
        zzawd.zza(this, parcel, n);
    }

    public int zza(zzawc object) {
        int n = this.name.compareTo(object.name);
        if (n != 0) {
            return n;
        }
        n = zzawc.compare(this.zzbzv, object.zzbzv);
        if (n != 0) {
            return n;
        }
        switch (this.zzbzv) {
            default: {
                n = this.zzbzv;
                object = new StringBuilder(31);
                object.append("Invalid enum value: ");
                object.append(n);
                throw new AssertionError((Object)object.toString());
            }
            case 5: {
                byte[] arrby = this.zzbzu;
                byte[] arrby2 = object.zzbzu;
                if (arrby == arrby2) {
                    return 0;
                }
                if (this.zzbzu == null) {
                    return -1;
                }
                if (object.zzbzu == null) {
                    return 1;
                }
                for (n = 0; n < Math.min(this.zzbzu.length, object.zzbzu.length); ++n) {
                    int n2 = zzawc.compare(this.zzbzu[n], object.zzbzu[n]);
                    if (n2 == 0) continue;
                    return n2;
                }
                return zzawc.compare(this.zzbzu.length, object.zzbzu.length);
            }
            case 4: {
                return zzawc.compare(this.zzaFy, object.zzaFy);
            }
            case 3: {
                return Double.compare(this.zzbgI, object.zzbgI);
            }
            case 2: {
                return zzawc.compare(this.zzbgG, object.zzbgG);
            }
            case 1: 
        }
        return zzawc.compare(this.zzbzt, object.zzbzt);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public String zza(StringBuilder var1_1) {
        block7 : {
            var1_1.append("Flag(");
            var1_1.append(this.mVersionCode);
            var1_1.append(", ");
            var1_1.append(this.name);
            var1_1.append(", ");
            switch (this.zzbzv) {
                default: {
                    var1_1 = this.name;
                    var2_2 = this.zzbzv;
                    var3_3 = new StringBuilder(27 + String.valueOf(var1_1).length());
                    var3_3.append("Invalid type: ");
                    var3_3.append((String)var1_1);
                    var3_3.append(", ");
                    var3_3.append(var2_2);
                    throw new AssertionError((Object)var3_3.toString());
                }
                case 5: {
                    if (this.zzbzu != null) ** GOTO lbl20
                    var3_4 = "null";
                    ** GOTO lbl28
lbl20: // 1 sources:
                    var1_1.append("'");
                    var3_4 = new String(this.zzbzu, zzawc.UTF_8);
                    ** GOTO lbl26
                }
                case 4: {
                    var1_1.append("'");
                    var3_4 = this.zzaFy;
lbl26: // 2 sources:
                    var1_1.append(var3_4);
                    var3_4 = "'";
lbl28: // 2 sources:
                    var1_1.append(var3_4);
                    break block7;
                }
                case 3: {
                    var1_1.append(this.zzbgI);
                    break block7;
                }
                case 2: {
                    var1_1.append(this.zzbgG);
                    break block7;
                }
                case 1: 
            }
            var1_1.append(this.zzbzt);
        }
        var1_1.append(", ");
        var1_1.append(this.zzbzv);
        var1_1.append(", ");
        var1_1.append(this.zzbzw);
        var1_1.append(")");
        return var1_1.toString();
    }

    public static class zza
    implements Comparator<zzawc> {
        @Override
        public /* synthetic */ int compare(Object object, Object object2) {
            return this.zza((zzawc)object, (zzawc)object2);
        }

        public int zza(zzawc zzawc2, zzawc zzawc3) {
            if (zzawc2.zzbzw == zzawc3.zzbzw) {
                return zzawc2.name.compareTo(zzawc3.name);
            }
            return zzawc2.zzbzw - zzawc3.zzbzw;
        }
    }

}
