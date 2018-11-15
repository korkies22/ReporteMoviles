/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$CompressFormat
 *  android.graphics.BitmapFactory
 *  android.os.StatFs
 */
package de.cisha.android.board.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StatFs;
import de.cisha.chess.model.CishaUUID;
import de.cisha.chess.util.Logger;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

public class CouchImageCache {
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

    private CouchImageCache(Context object) {
        this._prefRevisionId = object.getSharedPreferences("couch_image_cache_revision", 0);
        this._prefTime = object.getSharedPreferences("couch_image_cache_time", 0);
        this._prefSize = object.getSharedPreferences("couch_image_cache_size", 0);
        if (object.getExternalCacheDir() != null && object.getExternalCacheDir().exists()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(object.getExternalCacheDir().toString());
            stringBuilder.append(PATH_CACHE);
            this._path = stringBuilder.toString();
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(object.getCacheDir().toString());
            stringBuilder.append(PATH_CACHE);
            this._path = stringBuilder.toString();
        }
        object = new File(this._path);
        if (!object.exists()) {
            object.mkdirs();
        }
        if (object.exists()) {
            this._freeSpace = this.getFreeSpaceOnCache();
        }
        this._weakBitmapMap = Collections.synchronizedMap(new WeakHashMap());
        this._timestampMap = Collections.synchronizedMap(new HashMap());
    }

    private void cleanUp() {
        Object object = new File(this._path).listFiles();
        if (object == null) {
            return;
        }
        int n = ((File[])object).length;
        long l = 0L;
        int n2 = 0;
        while (n2 < n) {
            long l2 = object[n2].length();
            ++n2;
            l += l2;
        }
        if (l > 16580608L || this._freeSpace < 2097152.0) {
            object = new ArrayList(this._prefTime.getAll().entrySet());
            Collections.sort(object, new EntryStringLongComperator());
            n2 = 0;
            while (n2 < 2097152 && object.size() > 0) {
                Map.Entry entry = (Map.Entry)object.remove(0);
                n2 = (int)((long)n2 + this.removeFromCache((String)entry.getKey()));
            }
        }
        this._freeSpace = this.getFreeSpaceOnCache();
    }

    private double getFreeSpaceOnCache() {
        if (this._path != null) {
            int n;
            double d;
            try {
                StatFs statFs = new StatFs(this._path);
                d = statFs.getBlockSize();
                n = statFs.getAvailableBlocks();
            }
            catch (Exception exception) {
                Logger.getInstance().error("CouchImageCache", "Exception trying to get free space", exception);
            }
            return d * (double)n;
        }
        return 0.0;
    }

    public static CouchImageCache getInstance(Context context) {
        if (_instance == null) {
            _instance = new CouchImageCache(context);
        }
        return _instance;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static Bitmap getLocalBitmap(String object) {
        Object object2;
        block14 : {
            FileInputStream fileInputStream;
            block13 : {
                fileInputStream = null;
                object = object2 = new FileInputStream((String)object);
                fileInputStream = BitmapFactory.decodeStream((InputStream)object2);
                if (object2 == null) return fileInputStream;
                try {
                    object2.close();
                    return fileInputStream;
                }
                catch (IOException iOException) {
                    Logger.getInstance().debug(CouchImageCache.class.getName(), IOException.class.getName(), iOException);
                }
                return fileInputStream;
                catch (FileNotFoundException fileNotFoundException) {
                    break block13;
                }
                catch (Throwable throwable) {
                    object2 = fileInputStream;
                    break block14;
                }
                catch (FileNotFoundException fileNotFoundException) {
                    object2 = null;
                }
            }
            object = object2;
            fileInputStream.printStackTrace();
            if (object2 == null) return null;
            try {
                object2.close();
                return null;
            }
            catch (IOException iOException) {
                Logger.getInstance().debug(CouchImageCache.class.getName(), IOException.class.getName(), iOException);
            }
            return null;
            catch (Throwable throwable) {
                object2 = object;
                object = throwable;
            }
        }
        if (object2 == null) throw object;
        try {
            object2.close();
            throw object;
        }
        catch (IOException iOException) {
            Logger.getInstance().debug(CouchImageCache.class.getName(), IOException.class.getName(), iOException);
        }
        throw object;
    }

    private long removeFromCache(String string) {
        Serializable serializable = new StringBuilder();
        serializable.append(this._path);
        serializable.append(string);
        serializable.append(".png");
        serializable = new File(serializable.toString());
        boolean bl = serializable.exists();
        long l = 0L;
        if (bl) {
            l = serializable.length();
            serializable.delete();
            l = 0L + l;
        }
        this._prefRevisionId.edit().remove(string).commit();
        this._prefSize.edit().remove(string).commit();
        this._prefTime.edit().remove(string).commit();
        return l;
    }

    public long getLastTimeOfUpdate(CishaUUID object) {
        if ((object = this._timestampMap.get(object.getUuid())) == null) {
            return 0L;
        }
        return object.longValue();
    }

    public String getRevision(CishaUUID cishaUUID) {
        return this._prefRevisionId.getString(cishaUUID.getUuid(), "");
    }

    public Bitmap lookupForCouchId(CishaUUID cishaUUID, int n) {
        if (this._prefRevisionId.contains(cishaUUID.getUuid()) && this._prefSize.getInt(cishaUUID.getUuid(), -1) >= n) {
            Object object = this._weakBitmapMap;
            Object object2 = new StringBuilder();
            object2.append(cishaUUID.getUuid());
            object2.append(":");
            object2.append(n);
            object = object2 = object.get(object2.toString());
            if (object2 == null) {
                object = new StringBuilder();
                object.append(this._path);
                object.append(cishaUUID.getUuid());
                object.append(".png");
                object = CouchImageCache.getLocalBitmap(object.toString());
            }
            if (object == null) {
                this._prefRevisionId.edit().remove(cishaUUID.getUuid()).commit();
                this._prefSize.edit().remove(cishaUUID.getUuid()).commit();
                this._prefTime.edit().remove(cishaUUID.getUuid()).commit();
                return object;
            }
            this._prefTime.edit().putLong(cishaUUID.getUuid(), System.currentTimeMillis()).commit();
            object2 = this._weakBitmapMap;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(cishaUUID.getUuid());
            stringBuilder.append(":");
            stringBuilder.append(n);
            object2.put(stringBuilder.toString(), object);
            return object;
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void makeCouchImageCacheEntry(CishaUUID cishaUUID, String string, int n, Bitmap bitmap) {
        synchronized (this) {
            this.cleanUp();
            if (this._freeSpace > 2097152.0) {
                void var2_3;
                void var3_4;
                int n2;
                Object object = new File(this._path);
                if (!object.exists()) {
                    object.mkdir();
                }
                if (!this._prefRevisionId.getString(cishaUUID.getUuid(), "").equals(var2_3) || (n2 = this._prefSize.getInt(cishaUUID.getUuid(), 0)) < var3_4) {
                    try {
                        void var4_5;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(cishaUUID.getUuid());
                        stringBuilder.append(".png");
                        object = new File((File)object, stringBuilder.toString());
                        if (object.exists()) {
                            object.delete();
                        }
                        object.createNewFile();
                        object = new FileOutputStream((File)object);
                        if (var4_5.compress(Bitmap.CompressFormat.PNG, 80, (OutputStream)object)) {
                            this._prefRevisionId.edit().putString(cishaUUID.getUuid(), (String)var2_3).commit();
                            this._prefSize.edit().putInt(cishaUUID.getUuid(), (int)var3_4).commit();
                            this._prefTime.edit().putLong(cishaUUID.getUuid(), System.currentTimeMillis()).commit();
                        }
                        object.flush();
                        object.close();
                        this.setTimeOfUpdate(cishaUUID);
                    }
                    catch (IOException iOException) {
                        Logger.getInstance().debug(CouchImageCache.class.getName(), IOException.class.getName(), iOException);
                    }
                }
                this._freeSpace = this.getFreeSpaceOnCache();
            }
            return;
        }
    }

    public void setTimeOfUpdate(CishaUUID cishaUUID) {
        this._timestampMap.put(cishaUUID.getUuid(), System.currentTimeMillis());
    }

    private static class EntryStringLongComperator
    implements Comparator<Map.Entry<String, ?>> {
        private EntryStringLongComperator() {
        }

        @Override
        public int compare(Map.Entry<String, ?> entry, Map.Entry<String, ?> entry2) {
            long l;
            long l2 = Long.parseLong(entry.getValue().toString());
            if (l2 < (l = Long.parseLong(entry2.getValue().toString()))) {
                return -1;
            }
            if (l2 > l) {
                return 1;
            }
            return 0;
        }
    }

}
