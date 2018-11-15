/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ContentProvider
 *  android.content.ContentValues
 *  android.content.Context
 *  android.content.pm.PackageManager
 *  android.content.pm.ProviderInfo
 *  android.content.res.XmlResourceParser
 *  android.database.Cursor
 *  android.database.MatrixCursor
 *  android.net.Uri
 *  android.net.Uri$Builder
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Environment
 *  android.os.ParcelFileDescriptor
 *  android.text.TextUtils
 *  android.webkit.MimeTypeMap
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.support.v4.content;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.res.XmlResourceParser;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.support.annotation.GuardedBy;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.xmlpull.v1.XmlPullParserException;

public class FileProvider
extends ContentProvider {
    private static final String ATTR_NAME = "name";
    private static final String ATTR_PATH = "path";
    private static final String[] COLUMNS = new String[]{"_display_name", "_size"};
    private static final File DEVICE_ROOT = new File("/");
    private static final String META_DATA_FILE_PROVIDER_PATHS = "android.support.FILE_PROVIDER_PATHS";
    private static final String TAG_CACHE_PATH = "cache-path";
    private static final String TAG_EXTERNAL = "external-path";
    private static final String TAG_EXTERNAL_CACHE = "external-cache-path";
    private static final String TAG_EXTERNAL_FILES = "external-files-path";
    private static final String TAG_EXTERNAL_MEDIA = "external-media-path";
    private static final String TAG_FILES_PATH = "files-path";
    private static final String TAG_ROOT_PATH = "root-path";
    @GuardedBy(value="sCache")
    private static HashMap<String, PathStrategy> sCache = new HashMap();
    private PathStrategy mStrategy;

    private static /* varargs */ File buildPath(File file, String ... arrstring) {
        for (String string : arrstring) {
            File file2 = file;
            if (string != null) {
                file2 = new File(file, string);
            }
            file = file2;
        }
        return file;
    }

    private static Object[] copyOf(Object[] arrobject, int n) {
        Object[] arrobject2 = new Object[n];
        System.arraycopy(arrobject, 0, arrobject2, 0, n);
        return arrobject2;
    }

    private static String[] copyOf(String[] arrstring, int n) {
        String[] arrstring2 = new String[n];
        System.arraycopy(arrstring, 0, arrstring2, 0, n);
        return arrstring2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static PathStrategy getPathStrategy(Context context, String string) {
        HashMap<String, PathStrategy> hashMap = sCache;
        synchronized (hashMap) {
            PathStrategy pathStrategy;
            PathStrategy pathStrategy2;
            pathStrategy = pathStrategy2 = sCache.get(string);
            if (pathStrategy2 == null) {
                try {
                    pathStrategy = FileProvider.parsePathStrategy(context, string);
                }
                catch (XmlPullParserException xmlPullParserException) {
                    throw new IllegalArgumentException("Failed to parse android.support.FILE_PROVIDER_PATHS meta-data", (Throwable)xmlPullParserException);
                }
                catch (IOException iOException) {
                    throw new IllegalArgumentException("Failed to parse android.support.FILE_PROVIDER_PATHS meta-data", iOException);
                }
                sCache.put(string, pathStrategy);
            }
            return pathStrategy;
        }
    }

    public static Uri getUriForFile(@NonNull Context context, @NonNull String string, @NonNull File file) {
        return FileProvider.getPathStrategy(context, string).getUriForFile(file);
    }

    private static int modeToMode(String string) {
        if ("r".equals(string)) {
            return 268435456;
        }
        if (!"w".equals(string) && !"wt".equals(string)) {
            if ("wa".equals(string)) {
                return 704643072;
            }
            if ("rw".equals(string)) {
                return 939524096;
            }
            if ("rwt".equals(string)) {
                return 1006632960;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid mode: ");
            stringBuilder.append(string);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        return 738197504;
    }

    private static PathStrategy parsePathStrategy(Context context, String object) throws IOException, XmlPullParserException {
        int n;
        SimplePathStrategy simplePathStrategy = new SimplePathStrategy((String)object);
        XmlResourceParser xmlResourceParser = context.getPackageManager().resolveContentProvider((String)object, 128).loadXmlMetaData(context.getPackageManager(), META_DATA_FILE_PROVIDER_PATHS);
        if (xmlResourceParser == null) {
            throw new IllegalArgumentException("Missing android.support.FILE_PROVIDER_PATHS meta-data");
        }
        while ((n = xmlResourceParser.next()) != 1) {
            if (n != 2) continue;
            File[] arrfile = xmlResourceParser.getName();
            Object var3_5 = null;
            String string = xmlResourceParser.getAttributeValue(null, ATTR_NAME);
            String string2 = xmlResourceParser.getAttributeValue(null, ATTR_PATH);
            if (TAG_ROOT_PATH.equals(arrfile)) {
                object = DEVICE_ROOT;
            } else if (TAG_FILES_PATH.equals(arrfile)) {
                object = context.getFilesDir();
            } else if (TAG_CACHE_PATH.equals(arrfile)) {
                object = context.getCacheDir();
            } else if (TAG_EXTERNAL.equals(arrfile)) {
                object = Environment.getExternalStorageDirectory();
            } else if (TAG_EXTERNAL_FILES.equals(arrfile)) {
                arrfile = ContextCompat.getExternalFilesDirs(context, null);
                object = var3_5;
                if (arrfile.length > 0) {
                    object = arrfile[0];
                }
            } else if (TAG_EXTERNAL_CACHE.equals(arrfile)) {
                arrfile = ContextCompat.getExternalCacheDirs(context);
                object = var3_5;
                if (arrfile.length > 0) {
                    object = arrfile[0];
                }
            } else {
                object = var3_5;
                if (Build.VERSION.SDK_INT >= 21) {
                    object = var3_5;
                    if (TAG_EXTERNAL_MEDIA.equals(arrfile)) {
                        arrfile = context.getExternalMediaDirs();
                        object = var3_5;
                        if (arrfile.length > 0) {
                            object = arrfile[0];
                        }
                    }
                }
            }
            if (object == null) continue;
            simplePathStrategy.addRoot(string, FileProvider.buildPath((File)object, string2));
        }
        return simplePathStrategy;
    }

    public void attachInfo(@NonNull Context context, @NonNull ProviderInfo providerInfo) {
        super.attachInfo(context, providerInfo);
        if (providerInfo.exported) {
            throw new SecurityException("Provider must not be exported");
        }
        if (!providerInfo.grantUriPermissions) {
            throw new SecurityException("Provider must grant uri permissions");
        }
        this.mStrategy = FileProvider.getPathStrategy(context, providerInfo.authority);
    }

    public int delete(@NonNull Uri uri, @Nullable String string, @Nullable String[] arrstring) {
        RuntimeException runtimeException;
        super("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.provideAs(TypeTransformer.java:780)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:659)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s1stmt(TypeTransformer.java:810)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:840)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:269)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
        throw runtimeException;
    }

    public String getType(@NonNull Uri object) {
        int n = (object = this.mStrategy.getFileForUri((Uri)object)).getName().lastIndexOf(46);
        if (n >= 0) {
            object = object.getName().substring(n + 1);
            object = MimeTypeMap.getSingleton().getMimeTypeFromExtension((String)object);
            if (object != null) {
                return object;
            }
        }
        return "application/octet-stream";
    }

    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {
        throw new UnsupportedOperationException("No external inserts");
    }

    public boolean onCreate() {
        return true;
    }

    public ParcelFileDescriptor openFile(@NonNull Uri uri, @NonNull String string) throws FileNotFoundException {
        return ParcelFileDescriptor.open((File)this.mStrategy.getFileForUri(uri), (int)FileProvider.modeToMode(string));
    }

    /*
     * Enabled aggressive block sorting
     */
    public Cursor query(@NonNull Uri matrixCursor, @Nullable String[] matrixCursor2, @Nullable String object, @Nullable String[] arrstring, @Nullable String object2) {
        object = this.mStrategy.getFileForUri((Uri)matrixCursor);
        matrixCursor = matrixCursor2;
        if (matrixCursor2 == null) {
            matrixCursor = COLUMNS;
        }
        int n = 0;
        arrstring = new String[((Object[])matrixCursor).length];
        matrixCursor2 = new Object[((Object[])matrixCursor).length];
        int n2 = ((Object[])matrixCursor).length;
        int n3 = 0;
        do {
            int n4;
            block8 : {
                block7 : {
                    block6 : {
                        if (n >= n2) {
                            matrixCursor = FileProvider.copyOf(arrstring, n3);
                            matrixCursor2 = FileProvider.copyOf((Object[])matrixCursor2, n3);
                            matrixCursor = new MatrixCursor((String[])matrixCursor, 1);
                            matrixCursor.addRow((Object[])matrixCursor2);
                            return matrixCursor;
                        }
                        object2 = matrixCursor[n];
                        if (!"_display_name".equals(object2)) break block6;
                        arrstring[n3] = "_display_name";
                        n4 = n3 + 1;
                        matrixCursor2[n3] = object.getName();
                        n3 = n4;
                        break block7;
                    }
                    n4 = n3;
                    if (!"_size".equals(object2)) break block8;
                    arrstring[n3] = "_size";
                    n4 = n3 + 1;
                    matrixCursor2[n3] = Long.valueOf(object.length());
                    n3 = n4;
                }
                n4 = n3;
            }
            ++n;
            n3 = n4;
        } while (true);
    }

    public int update(@NonNull Uri uri, ContentValues contentValues, @Nullable String string, @Nullable String[] arrstring) {
        throw new UnsupportedOperationException("No external updates");
    }

    static interface PathStrategy {
        public File getFileForUri(Uri var1);

        public Uri getUriForFile(File var1);
    }

    static class SimplePathStrategy
    implements PathStrategy {
        private final String mAuthority;
        private final HashMap<String, File> mRoots = new HashMap();

        SimplePathStrategy(String string) {
            this.mAuthority = string;
        }

        void addRoot(String string, File file) {
            if (TextUtils.isEmpty((CharSequence)string)) {
                throw new IllegalArgumentException("Name must not be empty");
            }
            try {
                File file2 = file.getCanonicalFile();
                this.mRoots.put(string, file2);
                return;
            }
            catch (IOException iOException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Failed to resolve canonical path for ");
                stringBuilder.append(file);
                throw new IllegalArgumentException(stringBuilder.toString(), iOException);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public File getFileForUri(Uri object) {
            Object object2 = object.getEncodedPath();
            int n = object2.indexOf(47, 1);
            Object object3 = Uri.decode((String)object2.substring(1, n));
            object2 = Uri.decode((String)object2.substring(n + 1));
            if ((object3 = this.mRoots.get(object3)) == null) {
                object3 = new StringBuilder();
                object3.append("Unable to find configured root for ");
                object3.append(object);
                throw new IllegalArgumentException(object3.toString());
            }
            object = new File((File)object3, (String)object2);
            try {
                object2 = object.getCanonicalFile();
                if (!object2.getPath().startsWith(object3.getPath())) {
                    throw new SecurityException("Resolved path jumped beyond configured root");
                }
                return object2;
            }
            catch (IOException iOException) {}
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Failed to resolve canonical path for ");
            stringBuilder.append(object);
            throw new IllegalArgumentException(stringBuilder.toString());
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public Uri getUriForFile(File object) {
            void var2_8;
            CharSequence charSequence;
            try {
                charSequence = object.getCanonicalPath();
                object = null;
            }
            catch (IOException iOException) {}
            for (Map.Entry<String, File> entry : this.mRoots.entrySet()) {
                String string = entry.getValue().getPath();
                if (!charSequence.startsWith(string) || object != null && string.length() <= object.getValue().getPath().length()) continue;
                object = entry;
            }
            if (object == null) {
                object = new StringBuilder();
                object.append("Failed to find configured root that contains ");
                object.append((String)charSequence);
                throw new IllegalArgumentException(object.toString());
            }
            String string = ((File)object.getValue()).getPath();
            if (string.endsWith("/")) {
                String string2 = charSequence.substring(string.length());
            } else {
                String string3 = charSequence.substring(string.length() + 1);
            }
            charSequence = new StringBuilder();
            charSequence.append(Uri.encode((String)object.getKey()));
            charSequence.append('/');
            charSequence.append(Uri.encode((String)var2_8, (String)"/"));
            object = charSequence.toString();
            return new Uri.Builder().scheme("content").authority(this.mAuthority).encodedPath((String)object).build();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Failed to resolve canonical path for ");
            stringBuilder.append(object);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
    }

}
