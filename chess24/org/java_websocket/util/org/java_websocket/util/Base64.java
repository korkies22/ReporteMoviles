/*
 * Decompiled with CFR 0_134.
 */
package org.java_websocket.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Base64 {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int DECODE = 0;
    public static final int DONT_GUNZIP = 4;
    public static final int DO_BREAK_LINES = 8;
    public static final int ENCODE = 1;
    private static final byte EQUALS_SIGN = 61;
    private static final byte EQUALS_SIGN_ENC = -1;
    public static final int GZIP = 2;
    private static final int MAX_LINE_LENGTH = 76;
    private static final byte NEW_LINE = 10;
    public static final int NO_OPTIONS = 0;
    public static final int ORDERED = 32;
    private static final String PREFERRED_ENCODING = "US-ASCII";
    public static final int URL_SAFE = 16;
    private static final byte WHITE_SPACE_ENC = -5;
    private static final byte[] _ORDERED_ALPHABET;
    private static final byte[] _ORDERED_DECODABET;
    private static final byte[] _STANDARD_ALPHABET;
    private static final byte[] _STANDARD_DECODABET;
    private static final byte[] _URL_SAFE_ALPHABET;
    private static final byte[] _URL_SAFE_DECODABET;

    static {
        _STANDARD_ALPHABET = new byte[]{65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};
        _STANDARD_DECODABET = new byte[]{-9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -5, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 62, -9, -9, -9, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -9, -9, -9, -1, -9, -9, -9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -9, -9, -9, -9, -9, -9, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9};
        _URL_SAFE_ALPHABET = new byte[]{65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95};
        _URL_SAFE_DECODABET = new byte[]{-9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -5, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 62, -9, -9, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -9, -9, -9, -1, -9, -9, -9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -9, -9, -9, -9, 63, -9, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9};
        _ORDERED_ALPHABET = new byte[]{45, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 95, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122};
        _ORDERED_DECODABET = new byte[]{-9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -5, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 0, -9, -9, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, -9, -9, -9, -1, -9, -9, -9, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, -9, -9, -9, -9, 37, -9, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9};
    }

    private Base64() {
    }

    public static byte[] decode(String string) throws IOException {
        return Base64.decode(string, 0);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static byte[] decode(String object, int n) throws IOException {
        Object object2;
        Object object3;
        block41 : {
            Object object4;
            byte[] arrby;
            block40 : {
                block37 : {
                    block38 : {
                        Object object5;
                        block45 : {
                            block39 : {
                                block36 : {
                                    if (object == null) {
                                        throw new NullPointerException("Input string was null.");
                                    }
                                    try {
                                        object = object2 = object.getBytes(PREFERRED_ENCODING);
                                        break block36;
                                    }
                                    catch (UnsupportedEncodingException unsupportedEncodingException) {}
                                    object = object.getBytes();
                                }
                                arrby = Base64.decode((byte[])object, 0, ((Object)object).length, n);
                                n = (n & 4) != 0 ? 1 : 0;
                                if (arrby == null) return arrby;
                                if (arrby.length < 4) return arrby;
                                if (n != 0) return arrby;
                                n = arrby[0];
                                if (35615 != (arrby[1] << 8 & 65280 | n & 255)) return arrby;
                                object5 = new byte[2048];
                                object3 = null;
                                object2 = null;
                                object4 = new ByteArrayOutputStream();
                                object = new ByteArrayInputStream(arrby);
                                object2 = new GZIPInputStream((java.io.InputStream)object);
                                while ((n = object2.read((byte[])object5)) >= 0) {
                                    object4.write((byte[])object5, 0, n);
                                }
                                object3 = object4.toByteArray();
                                try {
                                    object4.close();
                                    break block37;
                                }
                                catch (Exception exception) {}
                                catch (Throwable throwable) {
                                    object3 = object2;
                                    object2 = throwable;
                                    break block38;
                                }
                                catch (IOException iOException) {
                                    object3 = object;
                                    object = object2;
                                    object2 = iOException;
                                    break block39;
                                }
                                catch (Throwable throwable) {
                                    break block38;
                                }
                                catch (IOException iOException) {
                                    object5 = null;
                                    object3 = object;
                                    object = object5;
                                    break block39;
                                }
                                catch (Throwable throwable) {
                                    object = null;
                                    break block38;
                                }
                                catch (IOException iOException) {
                                    object = object3 = null;
                                }
                            }
                            object5 = object4;
                            object4 = object3;
                            object3 = object2;
                            object2 = object5;
                            break block45;
                            catch (Throwable throwable) {
                                object = object4 = null;
                                break block38;
                            }
                            catch (IOException iOException) {
                                object = object4 = null;
                            }
                        }
                        object3.printStackTrace();
                        try {
                            object2.close();
                            break block40;
                        }
                        catch (Exception exception) {}
                        catch (Throwable throwable) {
                            object5 = object2;
                            object2 = throwable;
                            object3 = object;
                            object = object4;
                            object4 = object5;
                        }
                    }
                    try {
                        object4.close();
                        break block41;
                    }
                    catch (Exception exception) {}
                }
                try {
                    object2.close();
                }
                catch (Exception exception) {}
                try {
                    object.close();
                    return object3;
                }
                catch (Exception exception) {
                    return object3;
                }
            }
            try {
                object.close();
            }
            catch (Exception exception) {}
            try {
                object4.close();
                return arrby;
            }
            catch (Exception exception) {
                return arrby;
            }
        }
        try {
            object3.close();
        }
        catch (Exception exception) {}
        try {
            object.close();
        }
        catch (Exception exception) {
            throw object2;
        }
        throw object2;
    }

    public static byte[] decode(byte[] arrby) throws IOException {
        return Base64.decode(arrby, 0, arrby.length, 0);
    }

    public static byte[] decode(byte[] object, int n, int n2, int n3) throws IOException {
        int n4;
        if (object == null) {
            throw new NullPointerException("Cannot decode null source array.");
        }
        if (n >= 0 && (n4 = n + n2) <= ((byte[])object).length) {
            int n5;
            byte[] arrby;
            block8 : {
                int n6;
                if (n2 == 0) {
                    return new byte[0];
                }
                if (n2 < 4) {
                    object = new StringBuilder();
                    object.append("Base64-encoded string must have at least four characters, but length specified was ");
                    object.append(n2);
                    throw new IllegalArgumentException(object.toString());
                }
                byte[] arrby2 = Base64.getDecodabet(n3);
                arrby = new byte[n2 * 3 / 4];
                byte[] arrby3 = new byte[4];
                n2 = n6 = 0;
                int n7 = n;
                n = n2;
                do {
                    n5 = n;
                    if (n7 >= n4) break block8;
                    byte by = arrby2[object[n7] & 255];
                    if (by < -5) break;
                    n2 = n6;
                    n5 = n;
                    if (by >= -1) {
                        n2 = n6 + 1;
                        arrby3[n6] = (byte)object[n7];
                        if (n2 > 3) {
                            n5 = n + Base64.decode4to3(arrby3, 0, arrby, n, n3);
                            if (object[n7] == 61) break block8;
                            n2 = 0;
                        } else {
                            n5 = n;
                        }
                    }
                    ++n7;
                    n6 = n2;
                    n = n5;
                } while (true);
                throw new IOException(String.format("Bad Base64 input character decimal %d in array position %d", object[n7] & 255, n7));
            }
            object = new byte[n5];
            System.arraycopy(arrby, 0, object, 0, n5);
            return object;
        }
        throw new IllegalArgumentException(String.format("Source array with length %d cannot have offset of %d and process %d bytes.", ((byte[])object).length, n, n2));
    }

    private static int decode4to3(byte[] arrby, int n, byte[] arrby2, int n2, int n3) {
        int n4;
        if (arrby == null) {
            throw new NullPointerException("Source array was null.");
        }
        if (arrby2 == null) {
            throw new NullPointerException("Destination array was null.");
        }
        if (n >= 0 && (n4 = n + 3) < arrby.length) {
            int n5;
            if (n2 >= 0 && (n5 = n2 + 2) < arrby2.length) {
                byte[] arrby3 = Base64.getDecodabet(n3);
                n3 = n + 2;
                if (arrby[n3] == 61) {
                    n3 = arrby3[arrby[n]];
                    arrby2[n2] = (byte)(((arrby3[arrby[n + 1]] & 255) << 12 | (n3 & 255) << 18) >>> 16);
                    return 1;
                }
                if (arrby[n4] == 61) {
                    n5 = arrby3[arrby[n]];
                    n = arrby3[arrby[n + 1]];
                    n = (arrby3[arrby[n3]] & 255) << 6 | ((n & 255) << 12 | (n5 & 255) << 18);
                    arrby2[n2] = (byte)(n >>> 16);
                    arrby2[n2 + 1] = (byte)(n >>> 8);
                    return 2;
                }
                byte by = arrby3[arrby[n]];
                n = arrby3[arrby[n + 1]];
                n3 = arrby3[arrby[n3]];
                n = arrby3[arrby[n4]] & 255 | ((n & 255) << 12 | (by & 255) << 18 | (n3 & 255) << 6);
                arrby2[n2] = (byte)(n >> 16);
                arrby2[n2 + 1] = (byte)(n >> 8);
                arrby2[n5] = (byte)n;
                return 3;
            }
            throw new IllegalArgumentException(String.format("Destination array with length %d cannot have offset of %d and still store three bytes.", arrby2.length, n2));
        }
        throw new IllegalArgumentException(String.format("Source array with length %d cannot have offset of %d and still process four bytes.", arrby.length, n));
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static void decodeFileToFile(String object, String object2) throws IOException {
        block11 : {
            byte[] arrby = Base64.decodeFromFile((String)object);
            Object var2_7 = null;
            object = null;
            object2 = new BufferedOutputStream(new FileOutputStream((String)object2));
            object2.write(arrby);
            try {
                object2.close();
                return;
            }
            catch (Exception exception) {
                return;
            }
            catch (Throwable throwable) {
                object = object2;
                object2 = throwable;
                break block11;
            }
            catch (IOException iOException) {
                object = object2;
                object2 = iOException;
                throw object2;
            }
            catch (Throwable throwable) {
                break block11;
            }
            catch (IOException iOException) {
                object = var2_7;
            }
            {
                throw object2;
            }
        }
        try {
            object.close();
        }
        catch (Exception exception) {
            throw object2;
        }
        throw object2;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static byte[] decodeFromFile(String object) throws IOException {
        byte[] arrby;
        block14 : {
            int n;
            FilterInputStream filterInputStream = null;
            FilterInputStream filterInputStream2 = null;
            arrby = filterInputStream2;
            object = new File((String)object);
            arrby = filterInputStream2;
            if (object.length() > Integer.MAX_VALUE) {
                arrby = filterInputStream2;
                StringBuilder stringBuilder = new StringBuilder();
                arrby = filterInputStream2;
                stringBuilder.append("File is too big for this convenience method (");
                arrby = filterInputStream2;
                stringBuilder.append(object.length());
                arrby = filterInputStream2;
                stringBuilder.append(" bytes).");
                arrby = filterInputStream2;
                throw new IOException(stringBuilder.toString());
            }
            arrby = filterInputStream2;
            byte[] arrby2 = new byte[(int)object.length()];
            arrby = filterInputStream2;
            object = new InputStream(new BufferedInputStream(new FileInputStream((File)object)), 0);
            int n2 = 0;
            while ((n = object.read(arrby2, n2, 4096)) >= 0) {
                n2 += n;
            }
            arrby = new byte[n2];
            System.arraycopy(arrby2, 0, arrby, 0, n2);
            try {
                object.close();
                return arrby;
            }
            catch (Exception exception) {
                return arrby;
            }
            catch (Throwable throwable) {
                arrby = object;
                object = throwable;
                break block14;
            }
            catch (IOException iOException) {
                arrby = object;
                object = iOException;
                throw object;
            }
            catch (Throwable throwable) {
                break block14;
            }
            catch (IOException iOException) {
                arrby = filterInputStream;
            }
            {
                throw object;
            }
        }
        try {
            arrby.close();
        }
        catch (Exception exception) {
            throw object;
        }
        throw object;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static void decodeToFile(String string, String object) throws IOException {
        Object object2;
        void var0_5;
        block11 : {
            OutputStream outputStream = null;
            object2 = null;
            object = new OutputStream(new FileOutputStream((String)object), 0);
            object.write(string.getBytes(PREFERRED_ENCODING));
            try {
                object.close();
                return;
            }
            catch (Exception exception) {
                return;
            }
            catch (Throwable throwable) {
                object2 = object;
                break block11;
            }
            catch (IOException iOException) {
                object2 = object;
                throw string;
            }
            catch (Throwable throwable) {
                break block11;
            }
            catch (IOException iOException) {
                object2 = outputStream;
            }
            {
                throw string;
            }
        }
        try {
            object2.close();
        }
        catch (Exception exception) {
            throw var0_5;
        }
        throw var0_5;
    }

    public static Object decodeToObject(String string) throws IOException, ClassNotFoundException {
        return Base64.decodeToObject(string, 0, null);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static Object decodeToObject(String var0, int var1_4, ClassLoader var2_5) throws IOException, ClassNotFoundException {
        block19 : {
            var3_8 = Base64.decode((String)var0, var1_4);
            var5_12 = null;
            var4_14 = null;
            var8_17 = null;
            var7_18 = null;
            var6_19 = null;
            var0 = null;
            var3_8 = new ByteArrayInputStream((byte[])var3_8);
            if (var2_5 != null) ** GOTO lbl16
            var4_14 = var8_17;
            var5_12 = var7_18;
            var0 = new ObjectInputStream((java.io.InputStream)var3_8);
            break block19;
lbl16: // 1 sources:
            var4_14 = var8_17;
            var5_12 = var7_18;
            var0 = new ObjectInputStream((java.io.InputStream)var3_8, (ClassLoader)var2_5){
                final /* synthetic */ ClassLoader val$loader;
                {
                    this.val$loader = classLoader;
                    super(inputStream);
                }

                @Override
                public Class<?> resolveClass(ObjectStreamClass objectStreamClass) throws IOException, ClassNotFoundException {
                    Class<?> class_ = Class.forName(objectStreamClass.getName(), false, this.val$loader);
                    if (class_ == null) {
                        return super.resolveClass(objectStreamClass);
                    }
                    return class_;
                }
            };
        }
        var4_14 = var0;
        var5_12 = var0;
        var6_19 = var0;
        var2_5 = var0.readObject();
        var3_8.close();
        ** GOTO lbl34
        {
            catch (Exception var3_10) {}
        }
        catch (Throwable var0_1) {
            var3_8 = null;
        }
        catch (ClassNotFoundException var3_9) {
            block20 : {
                var2_5 = null;
                break block20;
lbl34: // 2 sources:
                try {
                    var0.close();
                    return var2_5;
                }
                catch (Exception var0_2) {
                    return var2_5;
                }
                catch (Throwable var0_3) {
                }
                catch (ClassNotFoundException var4_15) {
                    var2_5 = var5_12;
                    var0 = var3_8;
                    var3_8 = var4_15;
                }
            }
            try {
                throw var3_8;
            }
            catch (Throwable var5_13) {
                var3_8 = var0;
                var4_14 = var2_5;
                var0 = var5_13;
            }
        }
        try {
            var3_8.close();
        }
        catch (Exception var2_6) {}
        try {
            var4_14.close();
        }
        catch (Exception var2_7) {
            throw var0;
        }
        throw var0;
        catch (IOException var4_16) {
            var2_5 = var6_19;
            var0 = var3_8;
            var3_8 = var4_16;
            throw var3_8;
        }
        catch (IOException var3_11) {
            var2_5 = null;
            var0 = var5_12;
            throw var3_8;
        }
    }

    public static void encode(ByteBuffer byteBuffer, ByteBuffer byteBuffer2) {
        byte[] arrby = new byte[3];
        byte[] arrby2 = new byte[4];
        while (byteBuffer.hasRemaining()) {
            int n = Math.min(3, byteBuffer.remaining());
            byteBuffer.get(arrby, 0, n);
            Base64.encode3to4(arrby2, arrby, n, 0);
            byteBuffer2.put(arrby2);
        }
    }

    public static void encode(ByteBuffer byteBuffer, CharBuffer charBuffer) {
        byte[] arrby = new byte[3];
        byte[] arrby2 = new byte[4];
        while (byteBuffer.hasRemaining()) {
            int n = Math.min(3, byteBuffer.remaining());
            byteBuffer.get(arrby, 0, n);
            Base64.encode3to4(arrby2, arrby, n, 0);
            for (int i = 0; i < 4; ++i) {
                charBuffer.put((char)(arrby2[i] & 255));
            }
        }
    }

    private static byte[] encode3to4(byte[] arrby, int n, int n2, byte[] arrby2, int n3, int n4) {
        byte[] arrby3 = Base64.getAlphabet(n4);
        int n5 = 0;
        n4 = n2 > 0 ? arrby[n] << 24 >>> 8 : 0;
        int n6 = n2 > 1 ? arrby[n + 1] << 24 >>> 16 : 0;
        if (n2 > 2) {
            n5 = arrby[n + 2] << 24 >>> 24;
        }
        n = n4 | n6 | n5;
        switch (n2) {
            default: {
                return arrby2;
            }
            case 3: {
                arrby2[n3] = arrby3[n >>> 18];
                arrby2[n3 + 1] = arrby3[n >>> 12 & 63];
                arrby2[n3 + 2] = arrby3[n >>> 6 & 63];
                arrby2[n3 + 3] = arrby3[n & 63];
                return arrby2;
            }
            case 2: {
                arrby2[n3] = arrby3[n >>> 18];
                arrby2[n3 + 1] = arrby3[n >>> 12 & 63];
                arrby2[n3 + 2] = arrby3[n >>> 6 & 63];
                arrby2[n3 + 3] = 61;
                return arrby2;
            }
            case 1: 
        }
        arrby2[n3] = arrby3[n >>> 18];
        arrby2[n3 + 1] = arrby3[n >>> 12 & 63];
        arrby2[n3 + 2] = 61;
        arrby2[n3 + 3] = 61;
        return arrby2;
    }

    private static byte[] encode3to4(byte[] arrby, byte[] arrby2, int n, int n2) {
        Base64.encode3to4(arrby2, 0, n, arrby, 0, n2);
        return arrby;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static String encodeBytes(byte[] object) {
        try {
            return Base64.encodeBytes(object, 0, ((byte[])object).length, 0);
        }
        catch (IOException iOException) {
            return null;
        }
    }

    public static String encodeBytes(byte[] arrby, int n) throws IOException {
        return Base64.encodeBytes(arrby, 0, arrby.length, n);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static String encodeBytes(byte[] object, int n, int n2) {
        try {
            void var2_4;
            void var1_3;
            return Base64.encodeBytes(object, (int)var1_3, (int)var2_4, 0);
        }
        catch (IOException iOException) {
            return null;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static String encodeBytes(byte[] arrby, int n, int n2, int n3) throws IOException {
        arrby = Base64.encodeBytesToBytes(arrby, n, n2, n3);
        try {
            return new String(arrby, PREFERRED_ENCODING);
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            return new String(arrby);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static byte[] encodeBytesToBytes(byte[] arrby) {
        try {
            return Base64.encodeBytesToBytes(arrby, 0, arrby.length, 0);
        }
        catch (IOException iOException) {
            return null;
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static byte[] encodeBytesToBytes(byte[] object, int n, int n2, int n3) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream;
        void var0_4;
        OutputStream outputStream;
        block38 : {
            block33 : {
                int n4;
                block41 : {
                    Object object2;
                    block35 : {
                        OutputStream outputStream2;
                        ByteArrayOutputStream byteArrayOutputStream2;
                        block34 : {
                            block37 : {
                                block36 : {
                                    if (object == null) {
                                        throw new NullPointerException("Cannot serialize a null array.");
                                    }
                                    if (n < 0) {
                                        object = new StringBuilder();
                                        object.append("Cannot have negative offset: ");
                                        object.append(n);
                                        throw new IllegalArgumentException(object.toString());
                                    }
                                    if (n2 < 0) {
                                        object = new StringBuilder();
                                        object.append("Cannot have length offset: ");
                                        object.append(n2);
                                        throw new IllegalArgumentException(object.toString());
                                    }
                                    if (n + n2 > ((Object)object).length) {
                                        throw new IllegalArgumentException(String.format("Cannot have offset of %d and length of %d with array of length %d", n, n2, ((Object)object).length));
                                    }
                                    if ((n3 & 2) == 0) break block41;
                                    byteArrayOutputStream = new ByteArrayOutputStream();
                                    outputStream = new OutputStream(byteArrayOutputStream, 1 | n3);
                                    GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(outputStream);
                                    byteArrayOutputStream2 = byteArrayOutputStream;
                                    outputStream2 = outputStream;
                                    object2 = gZIPOutputStream;
                                    gZIPOutputStream.write((byte[])object, n, n2);
                                    byteArrayOutputStream2 = byteArrayOutputStream;
                                    outputStream2 = outputStream;
                                    object2 = gZIPOutputStream;
                                    gZIPOutputStream.close();
                                    gZIPOutputStream.close();
                                    break block33;
                                    catch (IOException iOException) {
                                        object = gZIPOutputStream;
                                        break block34;
                                    }
                                    catch (Throwable throwable) {
                                        object2 = null;
                                        break block35;
                                    }
                                    catch (IOException iOException) {
                                        object = null;
                                        break block34;
                                    }
                                    catch (Throwable throwable) {
                                        break block36;
                                    }
                                    catch (IOException iOException) {
                                        break block37;
                                    }
                                    catch (Throwable throwable) {
                                        byteArrayOutputStream = null;
                                    }
                                }
                                outputStream = null;
                                object2 = null;
                                break block35;
                                catch (IOException iOException) {
                                    byteArrayOutputStream = null;
                                }
                            }
                            outputStream = null;
                            object = null;
                        }
                        byteArrayOutputStream2 = byteArrayOutputStream;
                        outputStream2 = outputStream;
                        object2 = object;
                        try {
                            void var14_26;
                            throw var14_26;
                        }
                        catch (Throwable throwable) {
                            byteArrayOutputStream = byteArrayOutputStream2;
                            outputStream = outputStream2;
                        }
                    }
                    object2.close();
                    break block38;
                }
                boolean bl = (n3 & 8) != 0;
                int n5 = n2 / 3;
                int n6 = n2 % 3 > 0 ? 4 : 0;
                n6 = n5 = n5 * 4 + n6;
                if (bl) {
                    n6 = n5 + n5 / 76;
                }
                byte[] arrby = new byte[n6];
                int n7 = n2 - 2;
                n5 = n6 = (n4 = 0);
                while (n4 < n7) {
                    Base64.encode3to4((byte[])object, n4 + n, 3, arrby, n6, n3);
                    if (bl && (n5 += 4) >= 76) {
                        arrby[n6 + 4] = 10;
                        ++n6;
                        n5 = 0;
                    }
                    n4 += 3;
                    n6 += 4;
                }
                n5 = n6;
                if (n4 < n2) {
                    Base64.encode3to4((byte[])object, n4 + n, n2 - n4, arrby, n6, n3);
                    n5 = n6 + 4;
                }
                if (n5 > arrby.length - 1) return arrby;
                object = new byte[n5];
                System.arraycopy(arrby, 0, object, 0, n5);
                return object;
                catch (Exception exception) {}
            }
            try {
                outputStream.close();
            }
            catch (Exception exception) {}
            try {
                byteArrayOutputStream.close();
                return byteArrayOutputStream.toByteArray();
            }
            catch (Exception exception) {
                return byteArrayOutputStream.toByteArray();
            }
            catch (Exception exception) {}
        }
        try {
            outputStream.close();
        }
        catch (Exception exception) {}
        try {
            byteArrayOutputStream.close();
        }
        catch (Exception exception) {
            throw var0_4;
        }
        throw var0_4;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static void encodeFileToFile(String object, String object2) throws IOException {
        block11 : {
            String string = Base64.encodeFromFile((String)object);
            Object var2_7 = null;
            object = null;
            object2 = new BufferedOutputStream(new FileOutputStream((String)object2));
            object2.write(string.getBytes(PREFERRED_ENCODING));
            try {
                object2.close();
                return;
            }
            catch (Exception exception) {
                return;
            }
            catch (Throwable throwable) {
                object = object2;
                object2 = throwable;
                break block11;
            }
            catch (IOException iOException) {
                object = object2;
                object2 = iOException;
                throw object2;
            }
            catch (Throwable throwable) {
                break block11;
            }
            catch (IOException iOException) {
                object = var2_7;
            }
            {
                throw object2;
            }
        }
        try {
            object.close();
        }
        catch (Exception exception) {
            throw object2;
        }
        throw object2;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static String encodeFromFile(String object) throws IOException {
        Object object2;
        block13 : {
            int n;
            FilterInputStream filterInputStream = null;
            FilterInputStream filterInputStream2 = null;
            object2 = filterInputStream2;
            object = new File((String)object);
            object2 = filterInputStream2;
            byte[] arrby = new byte[Math.max((int)((double)object.length() * 1.4 + 1.0), 40)];
            object2 = filterInputStream2;
            object = new InputStream(new BufferedInputStream(new FileInputStream((File)object)), 1);
            int n2 = 0;
            while ((n = object.read(arrby, n2, 4096)) >= 0) {
                n2 += n;
            }
            object2 = new String(arrby, 0, n2, PREFERRED_ENCODING);
            try {
                object.close();
                return object2;
            }
            catch (Exception exception) {
                return object2;
            }
            catch (Throwable throwable) {
                object2 = object;
                object = throwable;
                break block13;
            }
            catch (IOException iOException) {
                object2 = object;
                object = iOException;
                throw object;
            }
            catch (Throwable throwable) {
                break block13;
            }
            catch (IOException iOException) {
                object2 = filterInputStream;
            }
            {
                throw object;
            }
        }
        try {
            object2.close();
        }
        catch (Exception exception) {
            throw object;
        }
        throw object;
    }

    public static String encodeObject(Serializable serializable) throws IOException {
        return Base64.encodeObject(serializable, 0);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static String encodeObject(Serializable object, int n) throws IOException {
        Object object2;
        java.io.OutputStream outputStream;
        java.io.OutputStream outputStream2;
        block40 : {
            OutputStream outputStream3;
            block39 : {
                Object object3;
                block37 : {
                    java.io.OutputStream outputStream4;
                    block38 : {
                        DeflaterOutputStream deflaterOutputStream;
                        java.io.OutputStream outputStream5;
                        block36 : {
                            if (object == null) {
                                throw new NullPointerException("Cannot serialize a null object.");
                            }
                            outputStream5 = null;
                            outputStream4 = null;
                            object3 = null;
                            outputStream = null;
                            object2 = new ByteArrayOutputStream();
                            outputStream3 = new OutputStream((java.io.OutputStream)object2, 1 | n);
                            if ((n & 2) != 0) {
                                outputStream2 = new GZIPOutputStream(outputStream3);
                                object3 = outputStream;
                                deflaterOutputStream = outputStream2;
                                outputStream4 = outputStream2;
                                outputStream = new ObjectOutputStream(outputStream2);
                                break block36;
                            }
                            try {
                                outputStream = new ObjectOutputStream(outputStream3);
                                outputStream2 = null;
                            }
                            catch (Throwable throwable) {
                                outputStream2 = null;
                                outputStream = outputStream3;
                                break block37;
                            }
                            catch (IOException iOException) {
                                outputStream2 = null;
                                object = object2;
                                object2 = outputStream2;
                                outputStream = outputStream3;
                                break block38;
                            }
                        }
                        object3 = outputStream;
                        deflaterOutputStream = outputStream2;
                        outputStream5 = outputStream;
                        outputStream4 = outputStream2;
                        try {
                            outputStream.writeObject(object);
                        }
                        catch (Throwable throwable) {
                            outputStream = outputStream3;
                            outputStream2 = deflaterOutputStream;
                            break block37;
                        }
                        catch (IOException iOException) {
                            object = object2;
                            object2 = outputStream5;
                            outputStream = outputStream3;
                            outputStream2 = outputStream4;
                            break block38;
                        }
                        try {
                            ((ObjectOutputStream)outputStream).close();
                            break block39;
                        }
                        catch (Exception exception) {}
                        catch (Throwable throwable) {
                            outputStream = outputStream2 = null;
                            break block37;
                        }
                        catch (IOException iOException) {
                            outputStream = outputStream2 = null;
                            object = object2;
                            object2 = outputStream;
                            break block38;
                        }
                        catch (Throwable throwable) {
                            outputStream2 = null;
                            object2 = outputStream2;
                            outputStream = object2;
                            break block37;
                        }
                        catch (IOException iOException) {
                            outputStream2 = null;
                            object2 = outputStream2;
                            outputStream = object2;
                            object = outputStream4;
                        }
                    }
                    try {
                        throw object3;
                    }
                    catch (Throwable throwable) {
                        object3 = object;
                        outputStream4 = object2;
                        object = throwable;
                        object2 = object3;
                        object3 = outputStream4;
                    }
                }
                try {
                    object3.close();
                    break block40;
                }
                catch (Exception exception) {}
            }
            try {
                ((DeflaterOutputStream)outputStream2).close();
            }
            catch (Exception exception) {}
            try {
                ((java.io.OutputStream)outputStream3).close();
            }
            catch (Exception exception) {}
            try {
                object2.close();
            }
            catch (Exception exception) {}
            try {
                return new String(object2.toByteArray(), PREFERRED_ENCODING);
            }
            catch (UnsupportedEncodingException unsupportedEncodingException) {
                return new String(object2.toByteArray());
            }
        }
        try {
            ((DeflaterOutputStream)outputStream2).close();
        }
        catch (Exception exception) {}
        try {
            outputStream.close();
        }
        catch (Exception exception) {}
        try {
            object2.close();
        }
        catch (Exception exception) {
            throw object;
        }
        throw object;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static void encodeToFile(byte[] arrby, String object) throws IOException {
        Object object2;
        void var0_5;
        block12 : {
            if (arrby == null) {
                throw new NullPointerException("Data to encode was null.");
            }
            OutputStream outputStream = null;
            object2 = null;
            object = new OutputStream(new FileOutputStream((String)object), 1);
            object.write(arrby);
            try {
                object.close();
                return;
            }
            catch (Exception exception) {
                return;
            }
            catch (Throwable throwable) {
                object2 = object;
                break block12;
            }
            catch (IOException iOException) {
                object2 = object;
                throw arrby;
            }
            catch (Throwable throwable) {
                break block12;
            }
            catch (IOException iOException) {
                object2 = outputStream;
            }
            {
                throw arrby;
            }
        }
        try {
            object2.close();
        }
        catch (Exception exception) {
            throw var0_5;
        }
        throw var0_5;
    }

    private static final byte[] getAlphabet(int n) {
        if ((n & 16) == 16) {
            return _URL_SAFE_ALPHABET;
        }
        if ((n & 32) == 32) {
            return _ORDERED_ALPHABET;
        }
        return _STANDARD_ALPHABET;
    }

    private static final byte[] getDecodabet(int n) {
        if ((n & 16) == 16) {
            return _URL_SAFE_DECODABET;
        }
        if ((n & 32) == 32) {
            return _ORDERED_DECODABET;
        }
        return _STANDARD_DECODABET;
    }

    public static class InputStream
    extends FilterInputStream {
        private boolean breakLines;
        private byte[] buffer;
        private int bufferLength;
        private byte[] decodabet;
        private boolean encode;
        private int lineLength;
        private int numSigBytes;
        private int options;
        private int position;

        public InputStream(java.io.InputStream inputStream) {
            this(inputStream, 0);
        }

        public InputStream(java.io.InputStream inputStream, int n) {
            super(inputStream);
            this.options = n;
            boolean bl = true;
            boolean bl2 = (n & 8) > 0;
            this.breakLines = bl2;
            bl2 = (n & 1) > 0 ? bl : false;
            this.encode = bl2;
            int n2 = this.encode ? 4 : 3;
            this.bufferLength = n2;
            this.buffer = new byte[this.bufferLength];
            this.position = -1;
            this.lineLength = 0;
            this.decodabet = Base64.getDecodabet(n);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public int read() throws IOException {
            int n;
            byte[] arrby;
            if (this.position < 0) {
                if (this.encode) {
                    int n2;
                    arrby = new byte[3];
                    int n3 = n = 0;
                    while (n < 3 && (n2 = this.in.read()) >= 0) {
                        arrby[n] = (byte)n2;
                        ++n3;
                        ++n;
                    }
                    if (n3 <= 0) return -1;
                    Base64.encode3to4(arrby, 0, n3, this.buffer, 0, this.options);
                    this.position = 0;
                    this.numSigBytes = 4;
                } else {
                    arrby = new byte[4];
                    for (n = 0; n < 4; ++n) {
                        int n4;
                        while ((n4 = this.in.read()) >= 0 && this.decodabet[n4 & 127] <= -5) {
                        }
                        if (n4 < 0) break;
                        arrby[n] = (byte)n4;
                    }
                    if (n == 4) {
                        this.numSigBytes = Base64.decode4to3(arrby, 0, this.buffer, 0, this.options);
                        this.position = 0;
                    } else {
                        if (n != 0) throw new IOException("Improperly padded Base64 input.");
                        return -1;
                    }
                }
            }
            if (this.position < 0) throw new IOException("Error in Base64 code reading stream.");
            if (this.position >= this.numSigBytes) {
                return -1;
            }
            if (this.encode && this.breakLines && this.lineLength >= 76) {
                this.lineLength = 0;
                return 10;
            }
            ++this.lineLength;
            arrby = this.buffer;
            n = this.position;
            this.position = n + 1;
            n = arrby[n];
            if (this.position < this.bufferLength) return n & 255;
            this.position = -1;
            return n & 255;
        }

        @Override
        public int read(byte[] arrby, int n, int n2) throws IOException {
            int n3;
            for (n3 = 0; n3 < n2; ++n3) {
                int n4 = this.read();
                if (n4 >= 0) {
                    arrby[n + n3] = (byte)n4;
                    continue;
                }
                if (n3 != 0) break;
                return -1;
            }
            return n3;
        }
    }

    public static class OutputStream
    extends FilterOutputStream {
        private byte[] b4;
        private boolean breakLines;
        private byte[] buffer;
        private int bufferLength;
        private byte[] decodabet;
        private boolean encode;
        private int lineLength;
        private int options;
        private int position;
        private boolean suspendEncoding;

        public OutputStream(java.io.OutputStream outputStream) {
            this(outputStream, 1);
        }

        public OutputStream(java.io.OutputStream outputStream, int n) {
            super(outputStream);
            boolean bl = true;
            boolean bl2 = (n & 8) != 0;
            this.breakLines = bl2;
            bl2 = (n & 1) != 0 ? bl : false;
            this.encode = bl2;
            int n2 = this.encode ? 3 : 4;
            this.bufferLength = n2;
            this.buffer = new byte[this.bufferLength];
            this.position = 0;
            this.lineLength = 0;
            this.suspendEncoding = false;
            this.b4 = new byte[4];
            this.options = n;
            this.decodabet = Base64.getDecodabet(n);
        }

        @Override
        public void close() throws IOException {
            this.flushBase64();
            super.close();
            this.buffer = null;
            this.out = null;
        }

        public void flushBase64() throws IOException {
            if (this.position > 0) {
                if (this.encode) {
                    this.out.write(Base64.encode3to4(this.b4, this.buffer, this.position, this.options));
                    this.position = 0;
                    return;
                }
                throw new IOException("Base64 input not properly padded.");
            }
        }

        public void resumeEncoding() {
            this.suspendEncoding = false;
        }

        public void suspendEncoding() throws IOException {
            this.flushBase64();
            this.suspendEncoding = true;
        }

        @Override
        public void write(int n) throws IOException {
            if (this.suspendEncoding) {
                this.out.write(n);
                return;
            }
            if (this.encode) {
                byte[] arrby = this.buffer;
                int n2 = this.position;
                this.position = n2 + 1;
                arrby[n2] = (byte)n;
                if (this.position >= this.bufferLength) {
                    this.out.write(Base64.encode3to4(this.b4, this.buffer, this.bufferLength, this.options));
                    this.lineLength += 4;
                    if (this.breakLines && this.lineLength >= 76) {
                        this.out.write(10);
                        this.lineLength = 0;
                    }
                    this.position = 0;
                    return;
                }
            } else {
                byte[] arrby = this.decodabet;
                int n3 = n & 127;
                if (arrby[n3] > -5) {
                    arrby = this.buffer;
                    n3 = this.position;
                    this.position = n3 + 1;
                    arrby[n3] = (byte)n;
                    if (this.position >= this.bufferLength) {
                        n = Base64.decode4to3(this.buffer, 0, this.b4, 0, this.options);
                        this.out.write(this.b4, 0, n);
                        this.position = 0;
                        return;
                    }
                } else if (this.decodabet[n3] != -5) {
                    throw new IOException("Invalid character in Base64 data.");
                }
            }
        }

        @Override
        public void write(byte[] arrby, int n, int n2) throws IOException {
            if (this.suspendEncoding) {
                this.out.write(arrby, n, n2);
                return;
            }
            for (int i = 0; i < n2; ++i) {
                this.write(arrby[n + i]);
            }
        }
    }

}
