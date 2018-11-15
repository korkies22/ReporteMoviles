/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.database.Cursor
 *  android.net.Uri
 *  android.os.IBinder
 *  android.os.RemoteException
 *  android.util.Base64
 *  android.util.Log
 *  dalvik.system.PathClassLoader
 */
package com.google.android.gms.dynamite;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Base64;
import android.util.Log;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.util.DynamiteApi;
import com.google.android.gms.common.zzc;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.dynamite.zza;
import com.google.android.gms.dynamite.zzb;
import dalvik.system.PathClassLoader;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;

public final class DynamiteModule {
    public static final zzb zzaQA;
    private static com.google.android.gms.dynamite.zza zzaQr;
    private static final HashMap<String, byte[]> zzaQs;
    private static String zzaQt;
    private static final zzb.zza zzaQu;
    private static final zzb.zza zzaQv;
    public static final zzb zzaQw;
    public static final zzb zzaQx;
    public static final zzb zzaQy;
    public static final zzb zzaQz;
    private final Context zzaQB;

    static {
        zzaQs = new HashMap();
        zzaQu = new zzb.zza(){

            @Override
            public int zzA(Context context, String string) {
                return DynamiteModule.zzA(context, string);
            }

            @Override
            public DynamiteModule zza(Context context, String string, int n) throws zza {
                return DynamiteModule.zza(context, string, n);
            }

            @Override
            public int zzb(Context context, String string, boolean bl) throws zza {
                return DynamiteModule.zzb(context, string, bl);
            }
        };
        zzaQv = new zzb.zza(){

            @Override
            public int zzA(Context context, String string) {
                return DynamiteModule.zzA(context, string);
            }

            @Override
            public DynamiteModule zza(Context context, String string, int n) throws zza {
                return DynamiteModule.zzb(context, string, n);
            }

            @Override
            public int zzb(Context context, String string, boolean bl) throws zza {
                return DynamiteModule.zzc(context, string, bl);
            }
        };
        zzaQw = new zzb(){

            @Override
            public zzb$zzb zza(Context context, String string, zzb.zza zza2) throws zza {
                zzb$zzb zzb$zzb2 = new zzb$zzb();
                zzb$zzb2.zzaQE = zza2.zzb(context, string, true);
                if (zzb$zzb2.zzaQE != 0) {
                    zzb$zzb2.zzaQF = 1;
                    return zzb$zzb2;
                }
                zzb$zzb2.zzaQD = zza2.zzA(context, string);
                if (zzb$zzb2.zzaQD != 0) {
                    zzb$zzb2.zzaQF = -1;
                }
                return zzb$zzb2;
            }
        };
        zzaQx = new zzb(){

            @Override
            public zzb$zzb zza(Context context, String string, zzb.zza zza2) throws zza {
                zzb$zzb zzb$zzb2 = new zzb$zzb();
                zzb$zzb2.zzaQD = zza2.zzA(context, string);
                if (zzb$zzb2.zzaQD != 0) {
                    zzb$zzb2.zzaQF = -1;
                    return zzb$zzb2;
                }
                zzb$zzb2.zzaQE = zza2.zzb(context, string, true);
                if (zzb$zzb2.zzaQE != 0) {
                    zzb$zzb2.zzaQF = 1;
                }
                return zzb$zzb2;
            }
        };
        zzaQy = new zzb(){

            /*
             * Enabled aggressive block sorting
             */
            @Override
            public zzb$zzb zza(Context context, String string, zzb.zza zza2) throws zza {
                int n;
                zzb$zzb zzb$zzb2 = new zzb$zzb();
                zzb$zzb2.zzaQD = zza2.zzA(context, string);
                zzb$zzb2.zzaQE = zza2.zzb(context, string, true);
                if (zzb$zzb2.zzaQD == 0 && zzb$zzb2.zzaQE == 0) {
                    n = 0;
                } else {
                    if (zzb$zzb2.zzaQD < zzb$zzb2.zzaQE) {
                        zzb$zzb2.zzaQF = 1;
                        return zzb$zzb2;
                    }
                    n = -1;
                }
                zzb$zzb2.zzaQF = n;
                return zzb$zzb2;
            }
        };
        zzaQz = new zzb(){

            /*
             * Enabled aggressive block sorting
             */
            @Override
            public zzb$zzb zza(Context context, String string, zzb.zza zza2) throws zza {
                int n;
                zzb$zzb zzb$zzb2 = new zzb$zzb();
                zzb$zzb2.zzaQD = zza2.zzA(context, string);
                zzb$zzb2.zzaQE = zza2.zzb(context, string, true);
                if (zzb$zzb2.zzaQD == 0 && zzb$zzb2.zzaQE == 0) {
                    n = 0;
                } else {
                    if (zzb$zzb2.zzaQE >= zzb$zzb2.zzaQD) {
                        zzb$zzb2.zzaQF = 1;
                        return zzb$zzb2;
                    }
                    n = -1;
                }
                zzb$zzb2.zzaQF = n;
                return zzb$zzb2;
            }
        };
        zzaQA = new zzb(){

            /*
             * Enabled aggressive block sorting
             */
            @Override
            public zzb$zzb zza(Context context, String string, zzb.zza zza2) throws zza {
                zzb$zzb zzb$zzb2 = new zzb$zzb();
                zzb$zzb2.zzaQD = zza2.zzA(context, string);
                int n = zzb$zzb2.zzaQD != 0 ? zza2.zzb(context, string, false) : zza2.zzb(context, string, true);
                zzb$zzb2.zzaQE = n;
                if (zzb$zzb2.zzaQD == 0 && zzb$zzb2.zzaQE == 0) {
                    zzb$zzb2.zzaQF = 0;
                    return zzb$zzb2;
                }
                if (zzb$zzb2.zzaQE >= zzb$zzb2.zzaQD) {
                    zzb$zzb2.zzaQF = 1;
                    return zzb$zzb2;
                }
                zzb$zzb2.zzaQF = -1;
                return zzb$zzb2;
            }
        };
    }

    private DynamiteModule(Context context) {
        this.zzaQB = zzac.zzw(context);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static int zzA(Context object, String string) {
        try {
            object = object.getApplicationContext().getClassLoader();
            Object object2 = String.valueOf("com.google.android.gms.dynamite.descriptors.");
            String string2 = String.valueOf("ModuleDescriptor");
            StringBuilder stringBuilder = new StringBuilder(1 + String.valueOf(object2).length() + String.valueOf(string).length() + String.valueOf(string2).length());
            stringBuilder.append((String)object2);
            stringBuilder.append(string);
            stringBuilder.append(".");
            stringBuilder.append(string2);
            object2 = object.loadClass(stringBuilder.toString());
            object = object2.getDeclaredField("MODULE_ID");
            object2 = object2.getDeclaredField("MODULE_VERSION");
            if (object.get(null).equals(string)) return object2.getInt(null);
            object = String.valueOf(object.get(null));
            object2 = new StringBuilder(51 + String.valueOf(object).length() + String.valueOf(string).length());
            object2.append("Module descriptor id '");
            object2.append((String)object);
            object2.append("' didn't match expected id '");
            object2.append(string);
            object2.append("'");
            Log.e((String)"DynamiteModule", (String)object2.toString());
            return 0;
        }
        catch (Exception exception) {
            String string3 = String.valueOf(exception.getMessage());
            string3 = string3.length() != 0 ? "Failed to load module descriptor class: ".concat(string3) : new String("Failed to load module descriptor class: ");
            Log.e((String)"DynamiteModule", (String)string3);
            return 0;
        }
        catch (ClassNotFoundException classNotFoundException) {}
        StringBuilder stringBuilder = new StringBuilder(45 + String.valueOf(string).length());
        stringBuilder.append("Local module descriptor class for ");
        stringBuilder.append(string);
        stringBuilder.append(" not found.");
        Log.w((String)"DynamiteModule", (String)stringBuilder.toString());
        return 0;
    }

    public static int zzB(Context context, String string) {
        return DynamiteModule.zzb(context, string, false);
    }

    private static DynamiteModule zzC(Context context, String string) {
        string = (string = String.valueOf(string)).length() != 0 ? "Selected local version of ".concat(string) : new String("Selected local version of ");
        Log.i((String)"DynamiteModule", (String)string);
        return new DynamiteModule(context.getApplicationContext());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static ClassLoader zzD(Context object, String object2) throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException {
        synchronized (DynamiteLoaderClassLoader.class) {
            if (DynamiteLoaderClassLoader.sClassLoader != null) {
                return DynamiteLoaderClassLoader.sClassLoader;
            }
            object = object.getApplicationContext().getClassLoader().loadClass(DynamiteLoaderClassLoader.class.getName());
            Field field = object.getDeclaredField("sClassLoader");
            synchronized (object) {
                DynamiteLoaderClassLoader.sClassLoader = (ClassLoader)field.get(null);
                if (DynamiteLoaderClassLoader.sClassLoader != null) {
                    return DynamiteLoaderClassLoader.sClassLoader;
                }
                DynamiteLoaderClassLoader.sClassLoader = new PathClassLoader((String)object2, ClassLoader.getSystemClassLoader()){

                    /*
                     * Enabled force condition propagation
                     * Lifted jumps to return sites
                     */
                    protected Class<?> loadClass(String string, boolean bl) throws ClassNotFoundException {
                        if (string.startsWith("java.")) return super.loadClass(string, bl);
                        if (string.startsWith("android.")) return super.loadClass(string, bl);
                        try {
                            return this.findClass(string);
                        }
                        catch (ClassNotFoundException classNotFoundException) {
                            return super.loadClass(string, bl);
                        }
                    }
                };
                field.set(null, DynamiteLoaderClassLoader.sClassLoader);
                return DynamiteLoaderClassLoader.sClassLoader;
            }
        }
    }

    private static Context zza(Context context, String string, byte[] arrby, String string2) {
        if (string2 != null && !string2.isEmpty()) {
            try {
                context = (Context)zze.zzE(zzb.zza.zzcf((IBinder)DynamiteModule.zzD(context, string2).loadClass("com.google.android.gms.dynamiteloader.DynamiteLoaderV2").getConstructor(new Class[0]).newInstance(new Object[0])).zza(zze.zzA(context), string, arrby));
                return context;
            }
            catch (Exception exception) {
                String string3 = String.valueOf(exception.toString());
                string3 = string3.length() != 0 ? "Failed to load DynamiteLoader: ".concat(string3) : new String("Failed to load DynamiteLoader: ");
                Log.e((String)"DynamiteModule", (String)string3);
                return null;
            }
        }
        Log.e((String)"DynamiteModule", (String)"No valid DynamiteLoader APK path");
        return null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static DynamiteModule zza(Context context, zzb zzb2, String string) throws zza {
        if ("com.google.android.gms".equals(context.getApplicationContext().getPackageName())) {
            do {
                return DynamiteModule.zza(context, zzb2, string, zzaQu);
                break;
            } while (true);
        }
        try {
            return DynamiteModule.zza(context, zzb2, string, zzaQv);
        }
        catch (zza zza2) {
            String string2 = String.valueOf(zza2.toString());
            string2 = string2.length() != 0 ? "Failed to load module via fast route".concat(string2) : new String("Failed to load module via fast route");
            Log.w((String)"DynamiteModule", (String)string2);
            return DynamiteModule.zza(context, zzb2, string, zzaQu);
        }
    }

    public static DynamiteModule zza(Context object, zzb zzb2, String string, zzb.zza object2) throws zza {
        zzb$zzb zzb$zzb2 = zzb2.zza((Context)object, string, (zzb.zza)object2);
        int n = zzb$zzb2.zzaQD;
        int n2 = zzb$zzb2.zzaQE;
        StringBuilder stringBuilder = new StringBuilder(68 + String.valueOf(string).length() + String.valueOf(string).length());
        stringBuilder.append("Considering local module ");
        stringBuilder.append(string);
        stringBuilder.append(":");
        stringBuilder.append(n);
        stringBuilder.append(" and remote module ");
        stringBuilder.append(string);
        stringBuilder.append(":");
        stringBuilder.append(n2);
        Log.i((String)"DynamiteModule", (String)stringBuilder.toString());
        if (!(zzb$zzb2.zzaQF == 0 || zzb$zzb2.zzaQF == -1 && zzb$zzb2.zzaQD == 0 || zzb$zzb2.zzaQF == 1 && zzb$zzb2.zzaQE == 0)) {
            if (zzb$zzb2.zzaQF == -1) {
                return DynamiteModule.zzC((Context)object, string);
            }
            if (zzb$zzb2.zzaQF == 1) {
                try {
                    object2 = object2.zza((Context)object, string, zzb$zzb2.zzaQE);
                    return object2;
                }
                catch (zza zza2) {
                    object2 = String.valueOf(zza2.getMessage());
                    object2 = object2.length() != 0 ? "Failed to load remote module: ".concat((String)object2) : new String("Failed to load remote module: ");
                    Log.w((String)"DynamiteModule", (String)object2);
                    if (zzb$zzb2.zzaQD != 0 && zzb2.zza((Context)object, (String)string, (zzb.zza)new zzb.zza(){

                        @Override
                        public int zzA(Context context, String string) {
                            return zzaQC;
                        }

                        @Override
                        public DynamiteModule zza(Context context, String string, int n) throws zza {
                            throw new zza("local only VersionPolicy should not load from remote");
                        }

                        @Override
                        public int zzb(Context context, String string, boolean bl) {
                            return 0;
                        }
                    }).zzaQF == -1) {
                        return DynamiteModule.zzC((Context)object, string);
                    }
                    throw new zza("Remote load failed. No local fallback found.", zza2);
                }
            }
            n = zzb$zzb2.zzaQF;
            object = new StringBuilder(47);
            object.append("VersionPolicy returned invalid code:");
            object.append(n);
            throw new zza(object.toString());
        }
        n = zzb$zzb2.zzaQD;
        n2 = zzb$zzb2.zzaQE;
        object = new StringBuilder(91);
        object.append("No acceptable module found. Local version is ");
        object.append(n);
        object.append(" and remote version is ");
        object.append(n2);
        object.append(".");
        throw new zza(object.toString());
    }

    private static DynamiteModule zza(Context object, String string, int n) throws zza {
        Object object2 = new StringBuilder(51 + String.valueOf(string).length());
        object2.append("Selected remote version of ");
        object2.append(string);
        object2.append(", version >= ");
        object2.append(n);
        Log.i((String)"DynamiteModule", (String)object2.toString());
        object2 = DynamiteModule.zzaU(object);
        if (object2 == null) {
            throw new zza("Failed to create IDynamiteLoader.");
        }
        try {
            object = object2.zza(zze.zzA(object), string, n);
        }
        catch (RemoteException remoteException) {
            throw new zza("Failed to load remote module.", (Throwable)remoteException);
        }
        if (zze.zzE((zzd)object) == null) {
            throw new zza("Failed to load remote module.");
        }
        return new DynamiteModule((Context)zze.zzE((zzd)object));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static com.google.android.gms.dynamite.zza zzaU(Context object) {
        synchronized (DynamiteModule.class) {
            if (zzaQr != null) {
                return zzaQr;
            }
            if (zzc.zzuz().isGooglePlayServicesAvailable((Context)object) != 0) {
                return null;
            }
            try {
                object = zza.zza.zzce((IBinder)object.createPackageContext("com.google.android.gms", 3).getClassLoader().loadClass("com.google.android.gms.chimera.container.DynamiteLoaderImpl").newInstance());
                if (object == null) return null;
                zzaQr = object;
            }
            catch (Exception exception) {
                String string = String.valueOf(exception.getMessage());
                string = string.length() != 0 ? "Failed to load IDynamiteLoader from GmsCore: ".concat(string) : new String("Failed to load IDynamiteLoader from GmsCore: ");
                Log.e((String)"DynamiteModule", (String)string);
            }
            return object;
            return null;
        }
    }

    public static int zzb(Context context, String string, boolean bl) {
        com.google.android.gms.dynamite.zza zza2 = DynamiteModule.zzaU(context);
        if (zza2 == null) {
            return 0;
        }
        try {
            int n = zza2.zza(zze.zzA(context), string, bl);
            return n;
        }
        catch (RemoteException remoteException) {
            String string2 = String.valueOf(remoteException.getMessage());
            string2 = string2.length() != 0 ? "Failed to retrieve remote module version: ".concat(string2) : new String("Failed to retrieve remote module version: ");
            Log.w((String)"DynamiteModule", (String)string2);
            return 0;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private static DynamiteModule zzb(Context context, String string, int n) throws zza {
        Serializable serializable = new StringBuilder(51 + String.valueOf(string).length());
        serializable.append("Selected remote version of ");
        serializable.append(string);
        serializable.append(", version >= ");
        serializable.append(n);
        Log.i((String)"DynamiteModule", (String)serializable.toString());
        // MONITORENTER : com.google.android.gms.dynamite.DynamiteModule.class
        serializable = zzaQs;
        CharSequence charSequence = new StringBuilder(12 + String.valueOf(string).length());
        charSequence.append(string);
        charSequence.append(":");
        charSequence.append(n);
        serializable = (byte[])serializable.get(charSequence.toString());
        charSequence = zzaQt;
        // MONITOREXIT : com.google.android.gms.dynamite.DynamiteModule.class
        if (serializable == null) {
            throw new zza("Module implementation could not be found.");
        }
        if ((context = DynamiteModule.zza(context.getApplicationContext(), string, (byte[])serializable, (String)charSequence)) != null) return new DynamiteModule(context);
        throw new zza("Failed to get module context");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public static int zzc(Context context, String string, boolean bl) throws zza {
        void var1_6;
        block17 : {
            HashMap<String, byte[]> hashMap;
            block14 : {
                block15 : {
                    int n;
                    block16 : {
                        hashMap = null;
                        Object object = bl ? "api_force_staging" : "api";
                        CharSequence charSequence = String.valueOf("content://com.google.android.gms.chimera/");
                        StringBuilder stringBuilder = new StringBuilder(1 + String.valueOf(charSequence).length() + String.valueOf(object).length() + String.valueOf(string).length());
                        stringBuilder.append((String)charSequence);
                        stringBuilder.append((String)object);
                        stringBuilder.append("/");
                        stringBuilder.append(string);
                        object = Uri.parse((String)stringBuilder.toString());
                        if (context == null || (context = context.getContentResolver()) == null) break block14;
                        if ((context = context.query((Uri)object, null, null, null, null)) == null) break block15;
                        if (!context.moveToFirst()) break block15;
                        n = context.getInt(0);
                        if (n <= 0) break block16;
                        // MONITORENTER : com.google.android.gms.dynamite.DynamiteModule.class
                        object = Base64.decode((String)context.getString(3), (int)0);
                        hashMap = zzaQs;
                        charSequence = new StringBuilder(12 + String.valueOf(string).length());
                        charSequence.append(string);
                        charSequence.append(":");
                        charSequence.append(n);
                        hashMap.put(charSequence.toString(), (byte[])object);
                        zzaQt = context.getString(2);
                        // MONITOREXIT : com.google.android.gms.dynamite.DynamiteModule.class
                    }
                    if (context == null) return n;
                    context.close();
                    return n;
                }
                try {
                    Log.w((String)"DynamiteModule", (String)"Failed to retrieve remote module version.");
                    throw new zza("Failed to connect to dynamite module ContentResolver.");
                }
                catch (Throwable throwable) {}
                catch (Exception exception) {}
                finally {
                    break block17;
                }
            }
            try {
                throw new zza("Failed to get dynamite module ContentResolver.");
            }
            catch (Throwable throwable) {
                context = hashMap;
                break block17;
            }
            catch (Exception exception) {
                context = null;
            }
            try {
                void var1_4;
                if (!(var1_4 instanceof zza)) throw new zza("V2 version check failed", (Throwable)var1_4);
                throw var1_4;
            }
            catch (Throwable throwable) {
                // empty catch block
            }
        }
        if (context == null) throw var1_6;
        context.close();
        throw var1_6;
    }

    public Context zzBd() {
        return this.zzaQB;
    }

    public IBinder zzdX(String string) throws zza {
        try {
            IBinder iBinder = (IBinder)this.zzaQB.getClassLoader().loadClass(string).newInstance();
            return iBinder;
        }
        catch (ClassNotFoundException | IllegalAccessException | InstantiationException reflectiveOperationException) {
            string = String.valueOf(string);
            string = string.length() != 0 ? "Failed to instantiate module class: ".concat(string) : new String("Failed to instantiate module class: ");
            throw new zza(string, reflectiveOperationException);
        }
    }

    @DynamiteApi
    public static class DynamiteLoaderClassLoader {
        public static ClassLoader sClassLoader;
    }

    public static class zza
    extends Exception {
        private zza(String string) {
            super(string);
        }

        private zza(String string, Throwable throwable) {
            super(string, throwable);
        }
    }

    public static interface zzb {
        public zzb$zzb zza(Context var1, String var2, zza var3) throws com.google.android.gms.dynamite.DynamiteModule$zza;

        public static interface zza {
            public int zzA(Context var1, String var2);

            public DynamiteModule zza(Context var1, String var2, int var3) throws com.google.android.gms.dynamite.DynamiteModule$zza;

            public int zzb(Context var1, String var2, boolean var3) throws com.google.android.gms.dynamite.DynamiteModule$zza;
        }

    }

    public static class zzb$zzb {
        public int zzaQD = 0;
        public int zzaQE = 0;
        public int zzaQF = 0;
    }

}
