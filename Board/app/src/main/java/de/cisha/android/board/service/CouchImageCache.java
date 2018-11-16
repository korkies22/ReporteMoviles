// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service;

import java.io.IOException;
import java.io.OutputStream;
import android.graphics.Bitmap.CompressFormat;
import java.io.FileOutputStream;
import de.cisha.chess.model.CishaUUID;
import de.cisha.chess.util.Logger;
import android.os.StatFs;
import java.util.Comparator;
import java.util.List;
import java.util.Collection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;
import java.util.WeakHashMap;
import java.io.File;
import android.content.Context;
import android.graphics.Bitmap;
import java.util.Map;
import android.content.SharedPreferences;

public class CouchImageCache
{
    private static final int MAX_SIZE_BYTES = 16580608;
    private static final int MIN_FREE_CACH_SPACE_BYTES = 2097152;
    private static final int MIN_SIZE_TO_CLEAN_BYTES = 2097152;
    private static final String PATH_CACHE = "/Image/Couch/";
    private static CouchImageCache _instance;
    private double _freeSpace;
    private String _path;
    private SharedPreferences _prefRevisionId;
    private SharedPreferences _prefSize;
    private SharedPreferences _prefTime;
    private Map<String, Long> _timestampMap;
    private Map<String, Bitmap> _weakBitmapMap;
    
    private CouchImageCache(final Context context) {
        this._prefRevisionId = context.getSharedPreferences("couch_image_cache_revision", 0);
        this._prefTime = context.getSharedPreferences("couch_image_cache_time", 0);
        this._prefSize = context.getSharedPreferences("couch_image_cache_size", 0);
        if (context.getExternalCacheDir() != null && context.getExternalCacheDir().exists()) {
            final StringBuilder sb = new StringBuilder();
            sb.append(context.getExternalCacheDir().toString());
            sb.append("/Image/Couch/");
            this._path = sb.toString();
        }
        else {
            final StringBuilder sb2 = new StringBuilder();
            sb2.append(context.getCacheDir().toString());
            sb2.append("/Image/Couch/");
            this._path = sb2.toString();
        }
        final File file = new File(this._path);
        if (!file.exists()) {
            file.mkdirs();
        }
        if (file.exists()) {
            this._freeSpace = this.getFreeSpaceOnCache();
        }
        this._weakBitmapMap = Collections.synchronizedMap(new WeakHashMap<String, Bitmap>());
        this._timestampMap = Collections.synchronizedMap(new HashMap<String, Long>());
    }
    
    private void cleanUp() {
        final File[] listFiles = new File(this._path).listFiles();
        if (listFiles == null) {
            return;
        }
        final int length = listFiles.length;
        long n = 0L;
        long length2;
        for (int i = 0; i < length; ++i, n += length2) {
            length2 = listFiles[i].length();
        }
        if (n > 16580608L || this._freeSpace < 2097152.0) {
            final ArrayList<Map.Entry<String, V>> list = new ArrayList<Map.Entry<String, V>>(this._prefTime.getAll().entrySet());
            Collections.sort((List<Object>)list, (Comparator<? super Object>)new EntryStringLongComperator());
            for (int n2 = 0; n2 < 2097152 && list.size() > 0; n2 += (int)this.removeFromCache(list.remove(0).getKey())) {}
        }
        this._freeSpace = this.getFreeSpaceOnCache();
    }
    
    private double getFreeSpaceOnCache() {
        if (this._path != null) {
            try {
                final StatFs statFs = new StatFs(this._path);
                return statFs.getBlockSize() * statFs.getAvailableBlocks();
            }
            catch (Exception ex) {
                Logger.getInstance().error("CouchImageCache", "Exception trying to get free space", ex);
            }
        }
        return 0.0;
    }
    
    public static CouchImageCache getInstance(final Context context) {
        if (CouchImageCache._instance == null) {
            CouchImageCache._instance = new CouchImageCache(context);
        }
        return CouchImageCache._instance;
    }
    
    private static Bitmap getLocalBitmap(final String p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     0: aconst_null    
        //     1: astore_2       
        //     2: new             Ljava/io/FileInputStream;
        //     5: dup            
        //     6: aload_0        
        //     7: invokespecial   java/io/FileInputStream.<init>:(Ljava/lang/String;)V
        //    10: astore_1       
        //    11: aload_1        
        //    12: astore_0       
        //    13: aload_1        
        //    14: invokestatic    android/graphics/BitmapFactory.decodeStream:(Ljava/io/InputStream;)Landroid/graphics/Bitmap;
        //    17: astore_2       
        //    18: aload_1        
        //    19: ifnull          46
        //    22: aload_1        
        //    23: invokevirtual   java/io/FileInputStream.close:()V
        //    26: aload_2        
        //    27: areturn        
        //    28: astore_0       
        //    29: invokestatic    de/cisha/chess/util/Logger.getInstance:()Lde/cisha/chess/util/Logger;
        //    32: ldc             Lde/cisha/android/board/service/CouchImageCache;.class
        //    34: invokevirtual   java/lang/Class.getName:()Ljava/lang/String;
        //    37: ldc             Ljava/io/IOException;.class
        //    39: invokevirtual   java/lang/Class.getName:()Ljava/lang/String;
        //    42: aload_0        
        //    43: invokevirtual   de/cisha/chess/util/Logger.debug:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
        //    46: aload_2        
        //    47: areturn        
        //    48: astore_2       
        //    49: goto            61
        //    52: astore_0       
        //    53: aload_2        
        //    54: astore_1       
        //    55: goto            102
        //    58: astore_2       
        //    59: aconst_null    
        //    60: astore_1       
        //    61: aload_1        
        //    62: astore_0       
        //    63: aload_2        
        //    64: invokevirtual   java/io/FileNotFoundException.printStackTrace:()V
        //    67: aload_1        
        //    68: ifnull          95
        //    71: aload_1        
        //    72: invokevirtual   java/io/FileInputStream.close:()V
        //    75: aconst_null    
        //    76: areturn        
        //    77: astore_0       
        //    78: invokestatic    de/cisha/chess/util/Logger.getInstance:()Lde/cisha/chess/util/Logger;
        //    81: ldc             Lde/cisha/android/board/service/CouchImageCache;.class
        //    83: invokevirtual   java/lang/Class.getName:()Ljava/lang/String;
        //    86: ldc             Ljava/io/IOException;.class
        //    88: invokevirtual   java/lang/Class.getName:()Ljava/lang/String;
        //    91: aload_0        
        //    92: invokevirtual   de/cisha/chess/util/Logger.debug:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
        //    95: aconst_null    
        //    96: areturn        
        //    97: astore_2       
        //    98: aload_0        
        //    99: astore_1       
        //   100: aload_2        
        //   101: astore_0       
        //   102: aload_1        
        //   103: ifnull          131
        //   106: aload_1        
        //   107: invokevirtual   java/io/FileInputStream.close:()V
        //   110: goto            131
        //   113: astore_1       
        //   114: invokestatic    de/cisha/chess/util/Logger.getInstance:()Lde/cisha/chess/util/Logger;
        //   117: ldc             Lde/cisha/android/board/service/CouchImageCache;.class
        //   119: invokevirtual   java/lang/Class.getName:()Ljava/lang/String;
        //   122: ldc             Ljava/io/IOException;.class
        //   124: invokevirtual   java/lang/Class.getName:()Ljava/lang/String;
        //   127: aload_1        
        //   128: invokevirtual   de/cisha/chess/util/Logger.debug:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
        //   131: aload_0        
        //   132: athrow         
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                           
        //  -----  -----  -----  -----  -------------------------------
        //  2      11     58     61     Ljava/io/FileNotFoundException;
        //  2      11     52     58     Any
        //  13     18     48     52     Ljava/io/FileNotFoundException;
        //  13     18     97     102    Any
        //  22     26     28     46     Ljava/io/IOException;
        //  63     67     97     102    Any
        //  71     75     77     95     Ljava/io/IOException;
        //  106    110    113    131    Ljava/io/IOException;
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Expression is linked from several locations: Label_0046:
        //     at com.strobel.decompiler.ast.Error.expressionLinkedFromMultipleLocations(Error.java:27)
        //     at com.strobel.decompiler.ast.AstOptimizer.mergeDisparateObjectInitializations(AstOptimizer.java:2592)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:235)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:42)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:214)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:757)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:655)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:532)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:499)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:141)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:130)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:105)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:317)
        //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:238)
        //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:123)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private long removeFromCache(final String s) {
        final StringBuilder sb = new StringBuilder();
        sb.append(this._path);
        sb.append(s);
        sb.append(".png");
        final File file = new File(sb.toString());
        final boolean exists = file.exists();
        long n = 0L;
        if (exists) {
            final long length = file.length();
            file.delete();
            n = 0L + length;
        }
        this._prefRevisionId.edit().remove(s).commit();
        this._prefSize.edit().remove(s).commit();
        this._prefTime.edit().remove(s).commit();
        return n;
    }
    
    public long getLastTimeOfUpdate(final CishaUUID cishaUUID) {
        final Long n = this._timestampMap.get(cishaUUID.getUuid());
        if (n == null) {
            return 0L;
        }
        return n;
    }
    
    public String getRevision(final CishaUUID cishaUUID) {
        return this._prefRevisionId.getString(cishaUUID.getUuid(), "");
    }
    
    public Bitmap lookupForCouchId(final CishaUUID cishaUUID, final int n) {
        if (!this._prefRevisionId.contains(cishaUUID.getUuid()) || this._prefSize.getInt(cishaUUID.getUuid(), -1) < n) {
            return null;
        }
        final Map<String, Bitmap> weakBitmapMap = this._weakBitmapMap;
        final StringBuilder sb = new StringBuilder();
        sb.append(cishaUUID.getUuid());
        sb.append(":");
        sb.append(n);
        Bitmap localBitmap;
        if ((localBitmap = weakBitmapMap.get(sb.toString())) == null) {
            final StringBuilder sb2 = new StringBuilder();
            sb2.append(this._path);
            sb2.append(cishaUUID.getUuid());
            sb2.append(".png");
            localBitmap = getLocalBitmap(sb2.toString());
        }
        if (localBitmap == null) {
            this._prefRevisionId.edit().remove(cishaUUID.getUuid()).commit();
            this._prefSize.edit().remove(cishaUUID.getUuid()).commit();
            this._prefTime.edit().remove(cishaUUID.getUuid()).commit();
            return localBitmap;
        }
        this._prefTime.edit().putLong(cishaUUID.getUuid(), System.currentTimeMillis()).commit();
        final Map<String, Bitmap> weakBitmapMap2 = this._weakBitmapMap;
        final StringBuilder sb3 = new StringBuilder();
        sb3.append(cishaUUID.getUuid());
        sb3.append(":");
        sb3.append(n);
        weakBitmapMap2.put(sb3.toString(), localBitmap);
        return localBitmap;
    }
    
    public void makeCouchImageCacheEntry(final CishaUUID timeOfUpdate, final String s, final int n, final Bitmap bitmap) {
        synchronized (this) {
            this.cleanUp();
            if (this._freeSpace > 2097152.0) {
                final File file = new File(this._path);
                if (!file.exists()) {
                    file.mkdir();
                }
                Label_0291: {
                    if (this._prefRevisionId.getString(timeOfUpdate.getUuid(), "").equals(s)) {
                        if (this._prefSize.getInt(timeOfUpdate.getUuid(), 0) >= n) {
                            break Label_0291;
                        }
                    }
                    try {
                        final StringBuilder sb = new StringBuilder();
                        sb.append(timeOfUpdate.getUuid());
                        sb.append(".png");
                        final File file2 = new File(file, sb.toString());
                        if (file2.exists()) {
                            file2.delete();
                        }
                        file2.createNewFile();
                        final FileOutputStream fileOutputStream = new FileOutputStream(file2);
                        if (bitmap.compress(Bitmap.CompressFormat.PNG, 80, (OutputStream)fileOutputStream)) {
                            this._prefRevisionId.edit().putString(timeOfUpdate.getUuid(), s).commit();
                            this._prefSize.edit().putInt(timeOfUpdate.getUuid(), n).commit();
                            this._prefTime.edit().putLong(timeOfUpdate.getUuid(), System.currentTimeMillis()).commit();
                        }
                        fileOutputStream.flush();
                        fileOutputStream.close();
                        this.setTimeOfUpdate(timeOfUpdate);
                    }
                    catch (IOException ex) {
                        Logger.getInstance().debug(CouchImageCache.class.getName(), IOException.class.getName(), ex);
                    }
                }
                this._freeSpace = this.getFreeSpaceOnCache();
            }
        }
    }
    
    public void setTimeOfUpdate(final CishaUUID cishaUUID) {
        this._timestampMap.put(cishaUUID.getUuid(), System.currentTimeMillis());
    }
    
    private static class EntryStringLongComperator implements Comparator<Map.Entry<String, ?>>
    {
        @Override
        public int compare(final Map.Entry<String, ?> entry, final Map.Entry<String, ?> entry2) {
            final long long1 = Long.parseLong(entry.getValue().toString());
            final long long2 = Long.parseLong(entry2.getValue().toString());
            if (long1 < long2) {
                return -1;
            }
            if (long1 > long2) {
                return 1;
            }
            return 0;
        }
    }
}
