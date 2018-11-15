/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 */
package com.google.android.gms.common.internal.safeparcel;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

public class zzc {
    private static int zzH(Parcel parcel, int n) {
        parcel.writeInt(n | -65536);
        parcel.writeInt(0);
        return parcel.dataPosition();
    }

    private static void zzI(Parcel parcel, int n) {
        int n2 = parcel.dataPosition();
        parcel.setDataPosition(n - 4);
        parcel.writeInt(n2 - n);
        parcel.setDataPosition(n2);
    }

    public static void zzJ(Parcel parcel, int n) {
        zzc.zzI(parcel, n);
    }

    public static void zza(Parcel parcel, int n, byte by) {
        zzc.zzb(parcel, n, 4);
        parcel.writeInt((int)by);
    }

    public static void zza(Parcel parcel, int n, double d) {
        zzc.zzb(parcel, n, 8);
        parcel.writeDouble(d);
    }

    public static void zza(Parcel parcel, int n, float f) {
        zzc.zzb(parcel, n, 4);
        parcel.writeFloat(f);
    }

    public static void zza(Parcel parcel, int n, long l) {
        zzc.zzb(parcel, n, 8);
        parcel.writeLong(l);
    }

    public static void zza(Parcel parcel, int n, Bundle bundle, boolean bl) {
        if (bundle == null) {
            if (bl) {
                zzc.zzb(parcel, n, 0);
            }
            return;
        }
        n = zzc.zzH(parcel, n);
        parcel.writeBundle(bundle);
        zzc.zzI(parcel, n);
    }

    public static void zza(Parcel parcel, int n, IBinder iBinder, boolean bl) {
        if (iBinder == null) {
            if (bl) {
                zzc.zzb(parcel, n, 0);
            }
            return;
        }
        n = zzc.zzH(parcel, n);
        parcel.writeStrongBinder(iBinder);
        zzc.zzI(parcel, n);
    }

    public static void zza(Parcel parcel, int n, Parcel parcel2, boolean bl) {
        if (parcel2 == null) {
            if (bl) {
                zzc.zzb(parcel, n, 0);
            }
            return;
        }
        n = zzc.zzH(parcel, n);
        parcel.appendFrom(parcel2, 0, parcel2.dataSize());
        zzc.zzI(parcel, n);
    }

    public static void zza(Parcel parcel, int n, Parcelable parcelable, int n2, boolean bl) {
        if (parcelable == null) {
            if (bl) {
                zzc.zzb(parcel, n, 0);
            }
            return;
        }
        n = zzc.zzH(parcel, n);
        parcelable.writeToParcel(parcel, n2);
        zzc.zzI(parcel, n);
    }

    public static void zza(Parcel parcel, int n, Boolean bl, boolean bl2) {
        RuntimeException runtimeException;
        super("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.provideAs(TypeTransformer.java:780)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:659)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:698)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s1stmt(TypeTransformer.java:810)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:840)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:269)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
        throw runtimeException;
    }

    public static void zza(Parcel parcel, int n, Double d, boolean bl) {
        if (d == null) {
            if (bl) {
                zzc.zzb(parcel, n, 0);
            }
            return;
        }
        zzc.zzb(parcel, n, 8);
        parcel.writeDouble(d.doubleValue());
    }

    public static void zza(Parcel parcel, int n, Float f, boolean bl) {
        if (f == null) {
            if (bl) {
                zzc.zzb(parcel, n, 0);
            }
            return;
        }
        zzc.zzb(parcel, n, 4);
        parcel.writeFloat(f.floatValue());
    }

    public static void zza(Parcel parcel, int n, Integer n2, boolean bl) {
        if (n2 == null) {
            if (bl) {
                zzc.zzb(parcel, n, 0);
            }
            return;
        }
        zzc.zzb(parcel, n, 4);
        parcel.writeInt(n2.intValue());
    }

    public static void zza(Parcel parcel, int n, Long l, boolean bl) {
        if (l == null) {
            if (bl) {
                zzc.zzb(parcel, n, 0);
            }
            return;
        }
        zzc.zzb(parcel, n, 8);
        parcel.writeLong(l.longValue());
    }

    public static void zza(Parcel parcel, int n, String string, boolean bl) {
        if (string == null) {
            if (bl) {
                zzc.zzb(parcel, n, 0);
            }
            return;
        }
        n = zzc.zzH(parcel, n);
        parcel.writeString(string);
        zzc.zzI(parcel, n);
    }

    public static void zza(Parcel parcel, int n, List<Integer> list, boolean bl) {
        int n2 = 0;
        if (list == null) {
            if (bl) {
                zzc.zzb(parcel, n, 0);
            }
            return;
        }
        int n3 = zzc.zzH(parcel, n);
        int n4 = list.size();
        parcel.writeInt(n4);
        for (n = n2; n < n4; ++n) {
            parcel.writeInt(list.get(n).intValue());
        }
        zzc.zzI(parcel, n3);
    }

    public static void zza(Parcel parcel, int n, short s) {
        zzc.zzb(parcel, n, 4);
        parcel.writeInt((int)s);
    }

    public static void zza(Parcel parcel, int n, boolean bl) {
        RuntimeException runtimeException;
        super("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:269)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
        throw runtimeException;
    }

    public static void zza(Parcel parcel, int n, byte[] arrby, boolean bl) {
        if (arrby == null) {
            if (bl) {
                zzc.zzb(parcel, n, 0);
            }
            return;
        }
        n = zzc.zzH(parcel, n);
        parcel.writeByteArray(arrby);
        zzc.zzI(parcel, n);
    }

    public static void zza(Parcel parcel, int n, float[] arrf, boolean bl) {
        if (arrf == null) {
            if (bl) {
                zzc.zzb(parcel, n, 0);
            }
            return;
        }
        n = zzc.zzH(parcel, n);
        parcel.writeFloatArray(arrf);
        zzc.zzI(parcel, n);
    }

    public static void zza(Parcel parcel, int n, int[] arrn, boolean bl) {
        if (arrn == null) {
            if (bl) {
                zzc.zzb(parcel, n, 0);
            }
            return;
        }
        n = zzc.zzH(parcel, n);
        parcel.writeIntArray(arrn);
        zzc.zzI(parcel, n);
    }

    public static void zza(Parcel parcel, int n, long[] arrl, boolean bl) {
        if (arrl == null) {
            if (bl) {
                zzc.zzb(parcel, n, 0);
            }
            return;
        }
        n = zzc.zzH(parcel, n);
        parcel.writeLongArray(arrl);
        zzc.zzI(parcel, n);
    }

    public static <T extends Parcelable> void zza(Parcel parcel, int n, T[] arrT, int n2, boolean bl) {
        if (arrT == null) {
            if (bl) {
                zzc.zzb(parcel, n, 0);
            }
            return;
        }
        int n3 = zzc.zzH(parcel, n);
        int n4 = arrT.length;
        parcel.writeInt(n4);
        for (n = 0; n < n4; ++n) {
            T t = arrT[n];
            if (t == null) {
                parcel.writeInt(0);
                continue;
            }
            zzc.zza(parcel, t, n2);
        }
        zzc.zzI(parcel, n3);
    }

    public static void zza(Parcel parcel, int n, String[] arrstring, boolean bl) {
        if (arrstring == null) {
            if (bl) {
                zzc.zzb(parcel, n, 0);
            }
            return;
        }
        n = zzc.zzH(parcel, n);
        parcel.writeStringArray(arrstring);
        zzc.zzI(parcel, n);
    }

    public static void zza(Parcel parcel, int n, boolean[] arrbl, boolean bl) {
        if (arrbl == null) {
            if (bl) {
                zzc.zzb(parcel, n, 0);
            }
            return;
        }
        n = zzc.zzH(parcel, n);
        parcel.writeBooleanArray(arrbl);
        zzc.zzI(parcel, n);
    }

    public static void zza(Parcel parcel, int n, byte[][] arrby, boolean bl) {
        int n2 = 0;
        if (arrby == null) {
            if (bl) {
                zzc.zzb(parcel, n, 0);
            }
            return;
        }
        int n3 = zzc.zzH(parcel, n);
        int n4 = arrby.length;
        parcel.writeInt(n4);
        for (n = n2; n < n4; ++n) {
            parcel.writeByteArray(arrby[n]);
        }
        zzc.zzI(parcel, n3);
    }

    private static <T extends Parcelable> void zza(Parcel parcel, T t, int n) {
        int n2 = parcel.dataPosition();
        parcel.writeInt(1);
        int n3 = parcel.dataPosition();
        t.writeToParcel(parcel, n);
        n = parcel.dataPosition();
        parcel.setDataPosition(n2);
        parcel.writeInt(n - n3);
        parcel.setDataPosition(n);
    }

    public static int zzaV(Parcel parcel) {
        return zzc.zzH(parcel, 20293);
    }

    private static void zzb(Parcel parcel, int n, int n2) {
        if (n2 >= 65535) {
            parcel.writeInt(n | -65536);
            parcel.writeInt(n2);
            return;
        }
        parcel.writeInt(n | n2 << 16);
    }

    public static void zzb(Parcel parcel, int n, List<String> list, boolean bl) {
        if (list == null) {
            if (bl) {
                zzc.zzb(parcel, n, 0);
            }
            return;
        }
        n = zzc.zzH(parcel, n);
        parcel.writeStringList(list);
        zzc.zzI(parcel, n);
    }

    public static void zzc(Parcel parcel, int n, int n2) {
        zzc.zzb(parcel, n, 4);
        parcel.writeInt(n2);
    }

    public static <T extends Parcelable> void zzc(Parcel parcel, int n, List<T> list, boolean bl) {
        if (list == null) {
            if (bl) {
                zzc.zzb(parcel, n, 0);
            }
            return;
        }
        int n2 = zzc.zzH(parcel, n);
        int n3 = list.size();
        parcel.writeInt(n3);
        for (n = 0; n < n3; ++n) {
            Parcelable parcelable = (Parcelable)list.get(n);
            if (parcelable == null) {
                parcel.writeInt(0);
                continue;
            }
            zzc.zza(parcel, parcelable, 0);
        }
        zzc.zzI(parcel, n2);
    }

    public static void zzd(Parcel parcel, int n, List list, boolean bl) {
        if (list == null) {
            if (bl) {
                zzc.zzb(parcel, n, 0);
            }
            return;
        }
        n = zzc.zzH(parcel, n);
        parcel.writeList(list);
        zzc.zzI(parcel, n);
    }
}
