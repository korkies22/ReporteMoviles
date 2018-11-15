/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.net.Uri
 *  android.os.CancellationSignal
 *  android.os.Process
 *  android.util.Log
 */
package android.support.v4.graphics;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.CancellationSignal;
import android.os.Process;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.util.Log;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

@RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
public class TypefaceCompatUtil {
    private static final String CACHE_FILE_PREFIX = ".font";
    private static final String TAG = "TypefaceCompatUtil";

    private TypefaceCompatUtil() {
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static void closeQuietly(Closeable closeable) {
        if (closeable == null) return;
        try {
            closeable.close();
            return;
        }
        catch (IOException iOException) {
            return;
        }
    }

    @Nullable
    @RequiresApi(value=19)
    public static ByteBuffer copyToDirectBuffer(Context object, Resources object2, int n) {
        block5 : {
            if ((object = TypefaceCompatUtil.getTempFile((Context)object)) == null) {
                return null;
            }
            boolean bl = TypefaceCompatUtil.copyToFile((File)object, object2, n);
            if (bl) break block5;
            object.delete();
            return null;
        }
        try {
            object2 = TypefaceCompatUtil.mmap((File)object);
            return object2;
        }
        finally {
            object.delete();
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static boolean copyToFile(File file, Resources object, int n) {
        void var0_3;
        block4 : {
            boolean bl;
            object = object.openRawResource(n);
            try {
                bl = TypefaceCompatUtil.copyToFile(file, (InputStream)object);
            }
            catch (Throwable throwable) {
                break block4;
            }
            TypefaceCompatUtil.closeQuietly((Closeable)object);
            return bl;
            catch (Throwable throwable) {
                object = null;
            }
        }
        TypefaceCompatUtil.closeQuietly((Closeable)object);
        throw var0_3;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static boolean copyToFile(File object, InputStream inputStream) {
        Object object2;
        block8 : {
            StringBuilder stringBuilder;
            block9 : {
                stringBuilder = null;
                object2 = null;
                object = new FileOutputStream((File)object, false);
                try {
                    int n;
                    object2 = new byte[1024];
                    while ((n = inputStream.read((byte[])object2)) != -1) {
                        object.write((byte[])object2, 0, n);
                    }
                }
                catch (Throwable throwable) {
                    object2 = object;
                    object = throwable;
                    break block8;
                }
                catch (IOException iOException) {
                    break block9;
                }
                TypefaceCompatUtil.closeQuietly((Closeable)object);
                return true;
                catch (Throwable throwable) {
                    break block8;
                }
                catch (IOException iOException) {
                    object = stringBuilder;
                }
            }
            object2 = object;
            {
                void var1_6;
                stringBuilder = new StringBuilder();
                object2 = object;
                stringBuilder.append("Error copying resource contents to temp file: ");
                object2 = object;
                stringBuilder.append(var1_6.getMessage());
                object2 = object;
                Log.e((String)TAG, (String)stringBuilder.toString());
            }
            TypefaceCompatUtil.closeQuietly((Closeable)object);
            return false;
        }
        TypefaceCompatUtil.closeQuietly(object2);
        throw object;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Nullable
    public static File getTempFile(Context context) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(CACHE_FILE_PREFIX);
        stringBuilder.append(Process.myPid());
        stringBuilder.append("-");
        stringBuilder.append(Process.myTid());
        stringBuilder.append("-");
        String string = stringBuilder.toString();
        int n = 0;
        do {
            if (n >= 100) {
                return null;
            }
            File file = context.getCacheDir();
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append(string);
            stringBuilder2.append(n);
            file = new File(file, stringBuilder2.toString());
            try {
                boolean bl = file.createNewFile();
                if (bl) {
                    return file;
                }
            }
            catch (IOException iOException) {}
            ++n;
        } while (true);
    }

    /*
     * Exception decompiling
     */
    @Nullable
    @RequiresApi(value=19)
    public static ByteBuffer mmap(Context var0, CancellationSignal var1_6, Uri var2_10) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [6[TRYBLOCK]], but top level block is 15[CATCHBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:424)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:476)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:2898)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:716)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:186)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:131)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:378)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:884)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:786)
        // org.benf.cfr.reader.Main.doClass(Main.java:54)
        // org.benf.cfr.reader.Main.main(Main.java:247)
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Nullable
    @RequiresApi(value=19)
    private static ByteBuffer mmap(File var0) {
        try {
            var4_3 = new FileInputStream((File)var0);
        }
        catch (IOException var0_2) {
            return null;
        }
        var0 = var4_3.getChannel();
        var1_5 = var0.size();
        var0 = var0.map(FileChannel.MapMode.READ_ONLY, 0L, var1_5);
        if (var4_3 == null) return var0;
        var4_3.close();
        return var0;
        catch (Throwable var3_6) {
            var0 = null;
        }
        catch (Throwable var0_1) {
            try {
                throw var0_1;
            }
            catch (Throwable var3_7) {
                // empty catch block
            }
        }
        if (var4_3 == null) throw var3_8;
        if (var0 == null) ** GOTO lbl30
        try {
            var4_3.close();
            throw var3_8;
        }
        catch (Throwable var4_4) {
            var0.addSuppressed(var4_4);
            throw var3_8;
        }
lbl30: // 1 sources:
        var4_3.close();
        throw var3_8;
    }
}
