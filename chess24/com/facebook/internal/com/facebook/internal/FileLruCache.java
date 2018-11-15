/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 *  org.json.JSONTokener
 */
package com.facebook.internal;

import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;
import com.facebook.internal.Logger;
import com.facebook.internal.Utility;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidParameterException;
import java.util.Date;
import java.util.PriorityQueue;
import java.util.concurrent.atomic.AtomicLong;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public final class FileLruCache {
    private static final String HEADER_CACHEKEY_KEY = "key";
    private static final String HEADER_CACHE_CONTENT_TAG_KEY = "tag";
    static final String TAG = "FileLruCache";
    private static final AtomicLong bufferIndex = new AtomicLong();
    private final File directory;
    private boolean isTrimInProgress;
    private boolean isTrimPending;
    private AtomicLong lastClearCacheTime = new AtomicLong(0L);
    private final Limits limits;
    private final Object lock;
    private final String tag;

    public FileLruCache(String string, Limits limits) {
        this.tag = string;
        this.limits = limits;
        this.directory = new File(FacebookSdk.getCacheDir(), string);
        this.lock = new Object();
        if (this.directory.mkdirs() || this.directory.isDirectory()) {
            BufferFile.deleteAll(this.directory);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void postTrim() {
        Object object = this.lock;
        synchronized (object) {
            if (!this.isTrimPending) {
                this.isTrimPending = true;
                FacebookSdk.getExecutor().execute(new Runnable(){

                    @Override
                    public void run() {
                        FileLruCache.this.trim();
                    }
                });
            }
            return;
        }
    }

    private void renameToTargetAndTrim(String string, File file) {
        if (!file.renameTo(new File(this.directory, Utility.md5hash(string)))) {
            file.delete();
        }
        this.postTrim();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void trim() {
        block20 : {
            block19 : {
                var10_1 = this;
                var9_2 = var10_1.lock;
                // MONITORENTER : var9_2
                var10_1.isTrimPending = false;
                var10_1.isTrimInProgress = true;
                // MONITOREXIT : var9_2
                try {
                    Logger.log(LoggingBehavior.CACHE, FileLruCache.TAG, "trim started");
                    var9_2 = new PriorityQueue<Object>();
                    var10_1 = var10_1.directory.listFiles(BufferFile.excludeBufferFiles());
                    var5_6 = 0L;
                    if (var10_1 == null) {
                        var3_8 = 0L;
                        break block19;
                    }
                    var2_7 = ((File[])var10_1).length;
                    var3_8 = var5_6 = 0L;
                }
lbl59: // 4 sources:
                catch (Throwable var9_4) {
                    // empty catch block
                }
                for (var1_9 = 0; var1_9 < var2_7; ++var1_9, ++var3_8, var5_6 += var7_10) {
                    var11_11 = var10_1[var1_9];
                    var12_12 = new ModifiedFile((File)var11_11);
                    var9_2.add(var12_12);
                    var13_13 = LoggingBehavior.CACHE;
                    var14_14 = FileLruCache.TAG;
                    var15_15 = new StringBuilder();
                    var15_15.append("  trim considering time=");
                    try {
                        var15_15.append((Object)var12_12.getModified());
                        var15_15.append(" name=");
                        var15_15.append(var12_12.getFile().getName());
                        Logger.log((LoggingBehavior)var13_13, var14_14, var15_15.toString());
                        var7_10 = var11_11.length();
                        continue;
                    }
                    catch (Throwable var9_3) {
                        break block20;
                    }
                }
            }
            do {
                var10_1 = this;
                ** try [egrp 4[TRYBLOCK] [5 : 224->248)] { 
lbl39: // 1 sources:
                if (var5_6 <= (long)var10_1.limits.getByteCount() && var3_8 <= (long)(var1_9 = var10_1.limits.getFileCount())) {
                    var9_2 = var10_1.lock;
                    // MONITORENTER : var9_2
                    var10_1.isTrimInProgress = false;
                    var10_1.lock.notifyAll();
                    // MONITOREXIT : var9_2
                    return;
                }
                var10_1 = ((ModifiedFile)var9_2.remove()).getFile();
                var11_11 = LoggingBehavior.CACHE;
                var12_12 = FileLruCache.TAG;
                var13_13 = new StringBuilder();
                var13_13.append("  trim removing ");
                var13_13.append(var10_1.getName());
                Logger.log((LoggingBehavior)var11_11, (String)var12_12, var13_13.toString());
                var7_10 = var10_1.length();
                var10_1.delete();
                --var3_8;
                var5_6 -= var7_10;
                continue;
                break;
            } while (true);
        }
        var10_1 = this;
        var11_11 = var10_1.lock;
        // MONITORENTER : var11_11
        var10_1.isTrimInProgress = false;
        var10_1.lock.notifyAll();
        // MONITOREXIT : var11_11
        throw var9_5;
    }

    public void clearCache() {
        final File[] arrfile = this.directory.listFiles(BufferFile.excludeBufferFiles());
        this.lastClearCacheTime.set(System.currentTimeMillis());
        if (arrfile != null) {
            FacebookSdk.getExecutor().execute(new Runnable(){

                @Override
                public void run() {
                    File[] arrfile2 = arrfile;
                    int n = arrfile2.length;
                    for (int i = 0; i < n; ++i) {
                        arrfile2[i].delete();
                    }
                }
            });
        }
    }

    public InputStream get(String string) throws IOException {
        return this.get(string, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public InputStream get(String object, String string) throws IOException {
        InputStream inputStream;
        File file = new File(this.directory, Utility.md5hash(object));
        try {
            inputStream = new FileInputStream(file);
            inputStream = new BufferedInputStream(inputStream, 8192);
        }
        catch (IOException iOException) {
            return null;
        }
        try {
            Object object2 = StreamHeader.readHeader(inputStream);
            if (object2 == null) {
                return null;
            }
            String string2 = object2.optString(HEADER_CACHEKEY_KEY);
            if (string2 == null) return null;
            {
                if (!string2.equals(object)) {
                    return null;
                } else {
                    boolean bl;
                    object = object2.optString(HEADER_CACHE_CONTENT_TAG_KEY, null);
                    if (string == null && object != null || string != null && !(bl = string.equals(object))) {
                        return null;
                    }
                    long l = new Date().getTime();
                    object = LoggingBehavior.CACHE;
                    string = TAG;
                    object2 = new StringBuilder();
                    object2.append("Setting lastModified to ");
                    object2.append((Object)l);
                    object2.append(" for ");
                    object2.append(file.getName());
                    Logger.log((LoggingBehavior)((Object)object), string, object2.toString());
                    file.setLastModified(l);
                    return inputStream;
                }
            }
        }
        finally {
            ((BufferedInputStream)inputStream).close();
        }
    }

    public String getLocation() {
        return this.directory.getPath();
    }

    public InputStream interceptAndPut(String string, InputStream inputStream) throws IOException {
        return new CopyingInputStream(inputStream, this.openPutStream(string));
    }

    public OutputStream openPutStream(String string) throws IOException {
        return this.openPutStream(string, null);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public OutputStream openPutStream(String charSequence, String object) throws IOException {
        Throwable throwable22222;
        Object object2;
        block8 : {
            Object object3;
            object2 = BufferFile.newFile(this.directory);
            object2.delete();
            if (!object2.createNewFile()) {
                charSequence = new StringBuilder();
                charSequence.append("Could not create file at ");
                charSequence.append(object2.getAbsolutePath());
                throw new IOException(charSequence.toString());
            }
            try {
                object3 = new FileOutputStream((File)object2);
                object2 = new BufferedOutputStream(new CloseCallbackOutputStream((OutputStream)object3, new StreamCloseCallback(System.currentTimeMillis(), (File)object2, (String)charSequence){
                    final /* synthetic */ File val$buffer;
                    final /* synthetic */ long val$bufferFileCreateTime;
                    final /* synthetic */ String val$key;
                    {
                        this.val$bufferFileCreateTime = l;
                        this.val$buffer = file;
                        this.val$key = string;
                    }

                    @Override
                    public void onClose() {
                        if (this.val$bufferFileCreateTime < FileLruCache.this.lastClearCacheTime.get()) {
                            this.val$buffer.delete();
                            return;
                        }
                        FileLruCache.this.renameToTargetAndTrim(this.val$key, this.val$buffer);
                    }
                }), 8192);
            }
            catch (FileNotFoundException fileNotFoundException) {
                object = LoggingBehavior.CACHE;
                object2 = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Error creating buffer output stream: ");
                stringBuilder.append(fileNotFoundException);
                Logger.log((LoggingBehavior)((Object)object), 5, (String)object2, stringBuilder.toString());
                throw new IOException(fileNotFoundException.getMessage());
            }
            try {
                object3 = new JSONObject();
                object3.put(HEADER_CACHEKEY_KEY, (Object)charSequence);
                if (!Utility.isNullOrEmpty(object)) {
                    object3.put(HEADER_CACHE_CONTENT_TAG_KEY, object);
                }
                StreamHeader.writeHeader((OutputStream)object2, (JSONObject)object3);
                return object2;
            }
            catch (Throwable throwable22222) {
                break block8;
            }
            catch (JSONException jSONException) {
                object = LoggingBehavior.CACHE;
                object3 = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Error creating JSON header for cache file: ");
                stringBuilder.append((Object)jSONException);
                Logger.log((LoggingBehavior)((Object)object), 5, (String)object3, stringBuilder.toString());
                throw new IOException(jSONException.getMessage());
            }
        }
        object2.close();
        throw throwable22222;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    long sizeInBytesForTest() {
        File[] arrfile = this.lock;
        synchronized (arrfile) {
            do {
                if (!this.isTrimPending && !this.isTrimInProgress) {
                    long l;
                    // MONITOREXIT [3, 5, 8] lbl5 : MonitorExitStatement: MONITOREXIT : var7_1
                    arrfile = this.directory.listFiles();
                    long l2 = l = 0L;
                    if (arrfile != null) {
                        int n = arrfile.length;
                        int n2 = 0;
                        do {
                            l2 = l;
                            if (n2 >= n) break;
                            l2 = arrfile[n2].length();
                            ++n2;
                            l += l2;
                        } while (true);
                    }
                    return l2;
                }
                try {
                    this.lock.wait();
                }
                catch (InterruptedException interruptedException) {
                }
            } while (true);
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{FileLruCache: tag:");
        stringBuilder.append(this.tag);
        stringBuilder.append(" file:");
        stringBuilder.append(this.directory.getName());
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    private static class BufferFile {
        private static final String FILE_NAME_PREFIX = "buffer";
        private static final FilenameFilter filterExcludeBufferFiles = new FilenameFilter(){

            @Override
            public boolean accept(File file, String string) {
                return string.startsWith(BufferFile.FILE_NAME_PREFIX) ^ true;
            }
        };
        private static final FilenameFilter filterExcludeNonBufferFiles = new FilenameFilter(){

            @Override
            public boolean accept(File file, String string) {
                return string.startsWith(BufferFile.FILE_NAME_PREFIX);
            }
        };

        private BufferFile() {
        }

        static void deleteAll(File arrfile) {
            if ((arrfile = arrfile.listFiles(BufferFile.excludeNonBufferFiles())) != null) {
                int n = arrfile.length;
                for (int i = 0; i < n; ++i) {
                    arrfile[i].delete();
                }
            }
        }

        static FilenameFilter excludeBufferFiles() {
            return filterExcludeBufferFiles;
        }

        static FilenameFilter excludeNonBufferFiles() {
            return filterExcludeNonBufferFiles;
        }

        static File newFile(File file) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(FILE_NAME_PREFIX);
            stringBuilder.append(Long.valueOf(bufferIndex.incrementAndGet()).toString());
            return new File(file, stringBuilder.toString());
        }

    }

    private static class CloseCallbackOutputStream
    extends OutputStream {
        final StreamCloseCallback callback;
        final OutputStream innerStream;

        CloseCallbackOutputStream(OutputStream outputStream, StreamCloseCallback streamCloseCallback) {
            this.innerStream = outputStream;
            this.callback = streamCloseCallback;
        }

        @Override
        public void close() throws IOException {
            try {
                this.innerStream.close();
                return;
            }
            finally {
                this.callback.onClose();
            }
        }

        @Override
        public void flush() throws IOException {
            this.innerStream.flush();
        }

        @Override
        public void write(int n) throws IOException {
            this.innerStream.write(n);
        }

        @Override
        public void write(byte[] arrby) throws IOException {
            this.innerStream.write(arrby);
        }

        @Override
        public void write(byte[] arrby, int n, int n2) throws IOException {
            this.innerStream.write(arrby, n, n2);
        }
    }

    private static final class CopyingInputStream
    extends InputStream {
        final InputStream input;
        final OutputStream output;

        CopyingInputStream(InputStream inputStream, OutputStream outputStream) {
            this.input = inputStream;
            this.output = outputStream;
        }

        @Override
        public int available() throws IOException {
            return this.input.available();
        }

        @Override
        public void close() throws IOException {
            try {
                this.input.close();
                return;
            }
            finally {
                this.output.close();
            }
        }

        @Override
        public void mark(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean markSupported() {
            return false;
        }

        @Override
        public int read() throws IOException {
            int n = this.input.read();
            if (n >= 0) {
                this.output.write(n);
            }
            return n;
        }

        @Override
        public int read(byte[] arrby) throws IOException {
            int n = this.input.read(arrby);
            if (n > 0) {
                this.output.write(arrby, 0, n);
            }
            return n;
        }

        @Override
        public int read(byte[] arrby, int n, int n2) throws IOException {
            if ((n2 = this.input.read(arrby, n, n2)) > 0) {
                this.output.write(arrby, n, n2);
            }
            return n2;
        }

        @Override
        public void reset() {
            synchronized (this) {
                throw new UnsupportedOperationException();
            }
        }

        @Override
        public long skip(long l) throws IOException {
            int n;
            long l2;
            byte[] arrby = new byte[1024];
            for (l2 = 0L; l2 < l; l2 += (long)n) {
                n = this.read(arrby, 0, (int)Math.min(l - l2, (long)arrby.length));
                if (n >= 0) continue;
                return l2;
            }
            return l2;
        }
    }

    public static final class Limits {
        private int byteCount = 1048576;
        private int fileCount = 1024;

        int getByteCount() {
            return this.byteCount;
        }

        int getFileCount() {
            return this.fileCount;
        }

        void setByteCount(int n) {
            if (n < 0) {
                throw new InvalidParameterException("Cache byte-count limit must be >= 0");
            }
            this.byteCount = n;
        }

        void setFileCount(int n) {
            if (n < 0) {
                throw new InvalidParameterException("Cache file count limit must be >= 0");
            }
            this.fileCount = n;
        }
    }

    private static final class ModifiedFile
    implements Comparable<ModifiedFile> {
        private static final int HASH_MULTIPLIER = 37;
        private static final int HASH_SEED = 29;
        private final File file;
        private final long modified;

        ModifiedFile(File file) {
            this.file = file;
            this.modified = file.lastModified();
        }

        @Override
        public int compareTo(ModifiedFile modifiedFile) {
            if (this.getModified() < modifiedFile.getModified()) {
                return -1;
            }
            if (this.getModified() > modifiedFile.getModified()) {
                return 1;
            }
            return this.getFile().compareTo(modifiedFile.getFile());
        }

        public boolean equals(Object object) {
            if (object instanceof ModifiedFile && this.compareTo((ModifiedFile)object) == 0) {
                return true;
            }
            return false;
        }

        File getFile() {
            return this.file;
        }

        long getModified() {
            return this.modified;
        }

        public int hashCode() {
            return (1073 + this.file.hashCode()) * 37 + (int)(this.modified % Integer.MAX_VALUE);
        }
    }

    private static interface StreamCloseCallback {
        public void onClose();
    }

    private static final class StreamHeader {
        private static final int HEADER_VERSION = 0;

        private StreamHeader() {
        }

        static JSONObject readHeader(InputStream object) throws IOException {
            block7 : {
                int n;
                if (object.read() != 0) {
                    return null;
                }
                int n2 = 0;
                int n3 = n = 0;
                while (n < 3) {
                    int n4 = object.read();
                    if (n4 == -1) {
                        Logger.log(LoggingBehavior.CACHE, FileLruCache.TAG, "readHeader: stream.read returned -1 while reading header size");
                        return null;
                    }
                    n3 = (n3 << 8) + (n4 & 255);
                    ++n;
                }
                Object object2 = new byte[n3];
                for (n = n2; n < ((byte[])object2).length; n += n3) {
                    n3 = object.read((byte[])object2, n, ((byte[])object2).length - n);
                    if (n3 >= 1) continue;
                    object = LoggingBehavior.CACHE;
                    String string = FileLruCache.TAG;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("readHeader: stream.read stopped at ");
                    stringBuilder.append((Object)n);
                    stringBuilder.append(" when expected ");
                    stringBuilder.append(((byte[])object2).length);
                    Logger.log((LoggingBehavior)((Object)object), string, stringBuilder.toString());
                    return null;
                }
                object = new JSONTokener(new String((byte[])object2));
                try {
                    object = object.nextValue();
                    if (object instanceof JSONObject) break block7;
                    object2 = LoggingBehavior.CACHE;
                    String string = FileLruCache.TAG;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("readHeader: expected JSONObject, got ");
                    stringBuilder.append(object.getClass().getCanonicalName());
                    Logger.log((LoggingBehavior)((Object)object2), string, stringBuilder.toString());
                    return null;
                }
                catch (JSONException jSONException) {
                    throw new IOException(jSONException.getMessage());
                }
            }
            object = (JSONObject)object;
            return object;
        }

        static void writeHeader(OutputStream outputStream, JSONObject arrby) throws IOException {
            arrby = arrby.toString().getBytes();
            outputStream.write(0);
            outputStream.write(arrby.length >> 16 & 255);
            outputStream.write(arrby.length >> 8 & 255);
            outputStream.write(arrby.length >> 0 & 255);
            outputStream.write(arrby);
        }
    }

}
