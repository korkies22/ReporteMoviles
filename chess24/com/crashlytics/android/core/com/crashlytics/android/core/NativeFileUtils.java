/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.crashlytics.android.core;

import android.content.Context;
import com.crashlytics.android.core.BinaryImagesConverter;
import com.crashlytics.android.core.Sha1FileIdStrategy;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

final class NativeFileUtils {
    private NativeFileUtils() {
    }

    static byte[] binaryImagesJsonFromDirectory(File file, Context context) throws IOException {
        if ((file = NativeFileUtils.filter(file, ".binary_libs")) == null) {
            return null;
        }
        return NativeFileUtils.binaryImagesJsonFromFile(file, context);
    }

    private static byte[] binaryImagesJsonFromFile(File arrby, Context context) throws IOException {
        if ((arrby = NativeFileUtils.readFile((File)arrby)) != null && arrby.length != 0) {
            return NativeFileUtils.processBinaryImages(context, new String(arrby));
        }
        return null;
    }

    private static File filter(File arrfile, String string) {
        for (File file : arrfile.listFiles()) {
            if (!file.getName().endsWith(string)) continue;
            return file;
        }
        return null;
    }

    static byte[] metadataJsonFromDirectory(File file) {
        if ((file = NativeFileUtils.filter(file, ".device_info")) == null) {
            return null;
        }
        return NativeFileUtils.readFile(file);
    }

    static byte[] minidumpFromDirectory(File file) {
        if ((file = NativeFileUtils.filter(file, ".dmp")) == null) {
            return new byte[0];
        }
        return NativeFileUtils.minidumpFromFile(file);
    }

    private static byte[] minidumpFromFile(File file) {
        return NativeFileUtils.readFile(file);
    }

    private static byte[] processBinaryImages(Context context, String string) throws IOException {
        return new BinaryImagesConverter(context, new Sha1FileIdStrategy()).convert(string);
    }

    private static byte[] readBytes(InputStream inputStream) throws IOException {
        int n;
        byte[] arrby = new byte[1024];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        while ((n = inputStream.read(arrby)) != -1) {
            byteArrayOutputStream.write(arrby, 0, n);
        }
        return byteArrayOutputStream.toByteArray();
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    static byte[] readFile(File object) {
        block10 : {
            block9 : {
                void var1_7;
                block8 : {
                    byte[] arrby;
                    Object var2_3 = null;
                    object = new FileInputStream((File)object);
                    try {
                        arrby = NativeFileUtils.readBytes((InputStream)object);
                    }
                    catch (Throwable throwable) {
                        break block8;
                    }
                    CommonUtils.closeQuietly((Closeable)object);
                    return arrby;
                    catch (Throwable throwable) {
                        object = var2_3;
                    }
                }
                CommonUtils.closeQuietly((Closeable)object);
                throw var1_7;
                catch (FileNotFoundException fileNotFoundException) {}
                object = null;
                break block9;
                catch (IOException iOException) {}
                object = null;
                break block10;
                catch (FileNotFoundException fileNotFoundException) {}
            }
            CommonUtils.closeQuietly((Closeable)object);
            return null;
            catch (IOException iOException) {}
        }
        CommonUtils.closeQuietly((Closeable)object);
        return null;
    }
}
