/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.internal.safeparcel;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class zzb {
    public static double[] zzA(Parcel parcel, int n) {
        n = zzb.zza(parcel, n);
        int n2 = parcel.dataPosition();
        if (n == 0) {
            return null;
        }
        double[] arrd = parcel.createDoubleArray();
        parcel.setDataPosition(n2 + n);
        return arrd;
    }

    public static BigDecimal[] zzB(Parcel parcel, int n) {
        int n2 = zzb.zza(parcel, n);
        int n3 = parcel.dataPosition();
        if (n2 == 0) {
            return null;
        }
        int n4 = parcel.readInt();
        BigDecimal[] arrbigDecimal = new BigDecimal[n4];
        for (n = 0; n < n4; ++n) {
            byte[] arrby = parcel.createByteArray();
            int n5 = parcel.readInt();
            arrbigDecimal[n] = new BigDecimal(new BigInteger(arrby), n5);
        }
        parcel.setDataPosition(n3 + n2);
        return arrbigDecimal;
    }

    public static String[] zzC(Parcel parcel, int n) {
        n = zzb.zza(parcel, n);
        int n2 = parcel.dataPosition();
        if (n == 0) {
            return null;
        }
        String[] arrstring = parcel.createStringArray();
        parcel.setDataPosition(n2 + n);
        return arrstring;
    }

    public static ArrayList<Integer> zzD(Parcel parcel, int n) {
        int n2 = zzb.zza(parcel, n);
        int n3 = parcel.dataPosition();
        if (n2 == 0) {
            return null;
        }
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        int n4 = parcel.readInt();
        for (n = 0; n < n4; ++n) {
            arrayList.add(parcel.readInt());
        }
        parcel.setDataPosition(n3 + n2);
        return arrayList;
    }

    public static ArrayList<String> zzE(Parcel parcel, int n) {
        n = zzb.zza(parcel, n);
        int n2 = parcel.dataPosition();
        if (n == 0) {
            return null;
        }
        ArrayList arrayList = parcel.createStringArrayList();
        parcel.setDataPosition(n2 + n);
        return arrayList;
    }

    public static Parcel zzF(Parcel parcel, int n) {
        n = zzb.zza(parcel, n);
        int n2 = parcel.dataPosition();
        if (n == 0) {
            return null;
        }
        Parcel parcel2 = Parcel.obtain();
        parcel2.appendFrom(parcel, n2, n);
        parcel.setDataPosition(n2 + n);
        return parcel2;
    }

    public static Parcel[] zzG(Parcel parcel, int n) {
        int n2 = zzb.zza(parcel, n);
        int n3 = parcel.dataPosition();
        if (n2 == 0) {
            return null;
        }
        int n4 = parcel.readInt();
        Parcel[] arrparcel = new Parcel[n4];
        for (n = 0; n < n4; ++n) {
            int n5 = parcel.readInt();
            if (n5 != 0) {
                int n6 = parcel.dataPosition();
                Parcel parcel2 = Parcel.obtain();
                parcel2.appendFrom(parcel, n6, n5);
                arrparcel[n] = parcel2;
                parcel.setDataPosition(n6 + n5);
                continue;
            }
            arrparcel[n] = null;
        }
        parcel.setDataPosition(n3 + n2);
        return arrparcel;
    }

    public static int zza(Parcel parcel, int n) {
        if ((n & -65536) != -65536) {
            return n >> 16 & 65535;
        }
        return parcel.readInt();
    }

    public static <T extends Parcelable> T zza(Parcel parcel, int n, Parcelable.Creator<T> parcelable) {
        n = zzb.zza(parcel, n);
        int n2 = parcel.dataPosition();
        if (n == 0) {
            return null;
        }
        parcelable = (Parcelable)parcelable.createFromParcel(parcel);
        parcel.setDataPosition(n2 + n);
        return (T)parcelable;
    }

    private static void zza(Parcel parcel, int n, int n2) {
        if ((n = zzb.zza(parcel, n)) != n2) {
            String string = String.valueOf(Integer.toHexString(n));
            StringBuilder stringBuilder = new StringBuilder(46 + String.valueOf(string).length());
            stringBuilder.append("Expected size ");
            stringBuilder.append(n2);
            stringBuilder.append(" got ");
            stringBuilder.append(n);
            stringBuilder.append(" (0x");
            stringBuilder.append(string);
            stringBuilder.append(")");
            throw new zza(stringBuilder.toString(), parcel);
        }
    }

    private static void zza(Parcel parcel, int n, int n2, int n3) {
        if (n2 != n3) {
            String string = String.valueOf(Integer.toHexString(n2));
            StringBuilder stringBuilder = new StringBuilder(46 + String.valueOf(string).length());
            stringBuilder.append("Expected size ");
            stringBuilder.append(n3);
            stringBuilder.append(" got ");
            stringBuilder.append(n2);
            stringBuilder.append(" (0x");
            stringBuilder.append(string);
            stringBuilder.append(")");
            throw new zza(stringBuilder.toString(), parcel);
        }
    }

    public static void zza(Parcel parcel, int n, List list, ClassLoader classLoader) {
        n = zzb.zza(parcel, n);
        int n2 = parcel.dataPosition();
        if (n == 0) {
            return;
        }
        parcel.readList(list, classLoader);
        parcel.setDataPosition(n2 + n);
    }

    public static int zzaT(Parcel parcel) {
        return parcel.readInt();
    }

    public static int zzaU(Parcel parcel) {
        int n = zzb.zzaT(parcel);
        int n2 = zzb.zza(parcel, n);
        int n3 = parcel.dataPosition();
        if (zzb.zzcW(n) != 20293) {
            String string = String.valueOf(Integer.toHexString(n));
            string = string.length() != 0 ? "Expected object header. Got 0x".concat(string) : new String("Expected object header. Got 0x");
            throw new zza(string, parcel);
        }
        n = n2 + n3;
        if (n >= n3 && n <= parcel.dataSize()) {
            return n;
        }
        StringBuilder stringBuilder = new StringBuilder(54);
        stringBuilder.append("Size read is invalid start=");
        stringBuilder.append(n3);
        stringBuilder.append(" end=");
        stringBuilder.append(n);
        throw new zza(stringBuilder.toString(), parcel);
    }

    public static void zzb(Parcel parcel, int n) {
        n = zzb.zza(parcel, n);
        parcel.setDataPosition(parcel.dataPosition() + n);
    }

    public static <T> T[] zzb(Parcel parcel, int n, Parcelable.Creator<T> arrobject) {
        n = zzb.zza(parcel, n);
        int n2 = parcel.dataPosition();
        if (n == 0) {
            return null;
        }
        arrobject = parcel.createTypedArray(arrobject);
        parcel.setDataPosition(n2 + n);
        return arrobject;
    }

    public static <T> ArrayList<T> zzc(Parcel parcel, int n, Parcelable.Creator<T> object) {
        n = zzb.zza(parcel, n);
        int n2 = parcel.dataPosition();
        if (n == 0) {
            return null;
        }
        object = parcel.createTypedArrayList(object);
        parcel.setDataPosition(n2 + n);
        return object;
    }

    public static boolean zzc(Parcel parcel, int n) {
        zzb.zza(parcel, n, 4);
        if (parcel.readInt() != 0) {
            return true;
        }
        return false;
    }

    public static int zzcW(int n) {
        return n & 65535;
    }

    public static Boolean zzd(Parcel parcel, int n) {
        int n2 = zzb.zza(parcel, n);
        if (n2 == 0) {
            return null;
        }
        zzb.zza(parcel, n, n2, 4);
        boolean bl = parcel.readInt() != 0;
        return bl;
    }

    public static byte zze(Parcel parcel, int n) {
        zzb.zza(parcel, n, 4);
        return (byte)parcel.readInt();
    }

    public static short zzf(Parcel parcel, int n) {
        zzb.zza(parcel, n, 4);
        return (short)parcel.readInt();
    }

    public static int zzg(Parcel parcel, int n) {
        zzb.zza(parcel, n, 4);
        return parcel.readInt();
    }

    public static Integer zzh(Parcel parcel, int n) {
        int n2 = zzb.zza(parcel, n);
        if (n2 == 0) {
            return null;
        }
        zzb.zza(parcel, n, n2, 4);
        return parcel.readInt();
    }

    public static long zzi(Parcel parcel, int n) {
        zzb.zza(parcel, n, 8);
        return parcel.readLong();
    }

    public static Long zzj(Parcel parcel, int n) {
        int n2 = zzb.zza(parcel, n);
        if (n2 == 0) {
            return null;
        }
        zzb.zza(parcel, n, n2, 8);
        return parcel.readLong();
    }

    public static BigInteger zzk(Parcel parcel, int n) {
        n = zzb.zza(parcel, n);
        int n2 = parcel.dataPosition();
        if (n == 0) {
            return null;
        }
        byte[] arrby = parcel.createByteArray();
        parcel.setDataPosition(n2 + n);
        return new BigInteger(arrby);
    }

    public static float zzl(Parcel parcel, int n) {
        zzb.zza(parcel, n, 4);
        return parcel.readFloat();
    }

    public static Float zzm(Parcel parcel, int n) {
        int n2 = zzb.zza(parcel, n);
        if (n2 == 0) {
            return null;
        }
        zzb.zza(parcel, n, n2, 4);
        return Float.valueOf(parcel.readFloat());
    }

    public static double zzn(Parcel parcel, int n) {
        zzb.zza(parcel, n, 8);
        return parcel.readDouble();
    }

    public static Double zzo(Parcel parcel, int n) {
        int n2 = zzb.zza(parcel, n);
        if (n2 == 0) {
            return null;
        }
        zzb.zza(parcel, n, n2, 8);
        return parcel.readDouble();
    }

    public static BigDecimal zzp(Parcel parcel, int n) {
        n = zzb.zza(parcel, n);
        int n2 = parcel.dataPosition();
        if (n == 0) {
            return null;
        }
        byte[] arrby = parcel.createByteArray();
        int n3 = parcel.readInt();
        parcel.setDataPosition(n2 + n);
        return new BigDecimal(new BigInteger(arrby), n3);
    }

    public static String zzq(Parcel parcel, int n) {
        n = zzb.zza(parcel, n);
        int n2 = parcel.dataPosition();
        if (n == 0) {
            return null;
        }
        String string = parcel.readString();
        parcel.setDataPosition(n2 + n);
        return string;
    }

    public static IBinder zzr(Parcel parcel, int n) {
        n = zzb.zza(parcel, n);
        int n2 = parcel.dataPosition();
        if (n == 0) {
            return null;
        }
        IBinder iBinder = parcel.readStrongBinder();
        parcel.setDataPosition(n2 + n);
        return iBinder;
    }

    public static Bundle zzs(Parcel parcel, int n) {
        n = zzb.zza(parcel, n);
        int n2 = parcel.dataPosition();
        if (n == 0) {
            return null;
        }
        Bundle bundle = parcel.readBundle();
        parcel.setDataPosition(n2 + n);
        return bundle;
    }

    public static byte[] zzt(Parcel parcel, int n) {
        n = zzb.zza(parcel, n);
        int n2 = parcel.dataPosition();
        if (n == 0) {
            return null;
        }
        byte[] arrby = parcel.createByteArray();
        parcel.setDataPosition(n2 + n);
        return arrby;
    }

    public static byte[][] zzu(Parcel parcel, int n) {
        int n2 = zzb.zza(parcel, n);
        int n3 = parcel.dataPosition();
        if (n2 == 0) {
            return null;
        }
        int n4 = parcel.readInt();
        byte[][] arrarrby = new byte[n4][];
        for (n = 0; n < n4; ++n) {
            arrarrby[n] = parcel.createByteArray();
        }
        parcel.setDataPosition(n3 + n2);
        return arrarrby;
    }

    public static boolean[] zzv(Parcel parcel, int n) {
        n = zzb.zza(parcel, n);
        int n2 = parcel.dataPosition();
        if (n == 0) {
            return null;
        }
        boolean[] arrbl = parcel.createBooleanArray();
        parcel.setDataPosition(n2 + n);
        return arrbl;
    }

    public static int[] zzw(Parcel parcel, int n) {
        n = zzb.zza(parcel, n);
        int n2 = parcel.dataPosition();
        if (n == 0) {
            return null;
        }
        int[] arrn = parcel.createIntArray();
        parcel.setDataPosition(n2 + n);
        return arrn;
    }

    public static long[] zzx(Parcel parcel, int n) {
        n = zzb.zza(parcel, n);
        int n2 = parcel.dataPosition();
        if (n == 0) {
            return null;
        }
        long[] arrl = parcel.createLongArray();
        parcel.setDataPosition(n2 + n);
        return arrl;
    }

    public static BigInteger[] zzy(Parcel parcel, int n) {
        int n2 = zzb.zza(parcel, n);
        int n3 = parcel.dataPosition();
        if (n2 == 0) {
            return null;
        }
        int n4 = parcel.readInt();
        BigInteger[] arrbigInteger = new BigInteger[n4];
        for (n = 0; n < n4; ++n) {
            arrbigInteger[n] = new BigInteger(parcel.createByteArray());
        }
        parcel.setDataPosition(n3 + n2);
        return arrbigInteger;
    }

    public static float[] zzz(Parcel parcel, int n) {
        n = zzb.zza(parcel, n);
        int n2 = parcel.dataPosition();
        if (n == 0) {
            return null;
        }
        float[] arrf = parcel.createFloatArray();
        parcel.setDataPosition(n2 + n);
        return arrf;
    }

    public static class zza
    extends RuntimeException {
        public zza(String string, Parcel object) {
            int n = object.dataPosition();
            int n2 = object.dataSize();
            object = new StringBuilder(41 + String.valueOf(string).length());
            object.append(string);
            object.append(" Parcel: pos=");
            object.append(n);
            object.append(" size=");
            object.append(n2);
            super(object.toString());
        }
    }

}
