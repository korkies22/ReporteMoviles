/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

import com.crashlytics.android.core.ByteString;
import com.crashlytics.android.core.WireFormat;
import java.io.Flushable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

final class CodedOutputStream
implements Flushable {
    public static final int DEFAULT_BUFFER_SIZE = 4096;
    public static final int LITTLE_ENDIAN_32_SIZE = 4;
    public static final int LITTLE_ENDIAN_64_SIZE = 8;
    private final byte[] buffer;
    private final int limit;
    private final OutputStream output;
    private int position;

    private CodedOutputStream(OutputStream outputStream, byte[] arrby) {
        this.output = outputStream;
        this.buffer = arrby;
        this.position = 0;
        this.limit = arrby.length;
    }

    private CodedOutputStream(byte[] arrby, int n, int n2) {
        this.output = null;
        this.buffer = arrby;
        this.position = n;
        this.limit = n + n2;
    }

    public static int computeBoolSize(int n, boolean bl) {
        return CodedOutputStream.computeTagSize(n) + CodedOutputStream.computeBoolSizeNoTag(bl);
    }

    public static int computeBoolSizeNoTag(boolean bl) {
        return 1;
    }

    public static int computeBytesSize(int n, ByteString byteString) {
        return CodedOutputStream.computeTagSize(n) + CodedOutputStream.computeBytesSizeNoTag(byteString);
    }

    public static int computeBytesSizeNoTag(ByteString byteString) {
        return CodedOutputStream.computeRawVarint32Size(byteString.size()) + byteString.size();
    }

    public static int computeDoubleSize(int n, double d) {
        return CodedOutputStream.computeTagSize(n) + CodedOutputStream.computeDoubleSizeNoTag(d);
    }

    public static int computeDoubleSizeNoTag(double d) {
        return 8;
    }

    public static int computeEnumSize(int n, int n2) {
        return CodedOutputStream.computeTagSize(n) + CodedOutputStream.computeEnumSizeNoTag(n2);
    }

    public static int computeEnumSizeNoTag(int n) {
        return CodedOutputStream.computeInt32SizeNoTag(n);
    }

    public static int computeFixed32Size(int n, int n2) {
        return CodedOutputStream.computeTagSize(n) + CodedOutputStream.computeFixed32SizeNoTag(n2);
    }

    public static int computeFixed32SizeNoTag(int n) {
        return 4;
    }

    public static int computeFixed64Size(int n, long l) {
        return CodedOutputStream.computeTagSize(n) + CodedOutputStream.computeFixed64SizeNoTag(l);
    }

    public static int computeFixed64SizeNoTag(long l) {
        return 8;
    }

    public static int computeFloatSize(int n, float f) {
        return CodedOutputStream.computeTagSize(n) + CodedOutputStream.computeFloatSizeNoTag(f);
    }

    public static int computeFloatSizeNoTag(float f) {
        return 4;
    }

    public static int computeInt32Size(int n, int n2) {
        return CodedOutputStream.computeTagSize(n) + CodedOutputStream.computeInt32SizeNoTag(n2);
    }

    public static int computeInt32SizeNoTag(int n) {
        if (n >= 0) {
            return CodedOutputStream.computeRawVarint32Size(n);
        }
        return 10;
    }

    public static int computeInt64Size(int n, long l) {
        return CodedOutputStream.computeTagSize(n) + CodedOutputStream.computeInt64SizeNoTag(l);
    }

    public static int computeInt64SizeNoTag(long l) {
        return CodedOutputStream.computeRawVarint64Size(l);
    }

    static int computePreferredBufferSize(int n) {
        if (n > 4096) {
            return 4096;
        }
        return n;
    }

    public static int computeRawMessageSetExtensionSize(int n, ByteString byteString) {
        return CodedOutputStream.computeTagSize(1) * 2 + CodedOutputStream.computeUInt32Size(2, n) + CodedOutputStream.computeBytesSize(3, byteString);
    }

    public static int computeRawVarint32Size(int n) {
        if ((n & -128) == 0) {
            return 1;
        }
        if ((n & -16384) == 0) {
            return 2;
        }
        if ((-2097152 & n) == 0) {
            return 3;
        }
        if ((n & -268435456) == 0) {
            return 4;
        }
        return 5;
    }

    public static int computeRawVarint64Size(long l) {
        if ((l & -128L) == 0L) {
            return 1;
        }
        if ((l & -16384L) == 0L) {
            return 2;
        }
        if ((l & -2097152L) == 0L) {
            return 3;
        }
        if ((l & -268435456L) == 0L) {
            return 4;
        }
        if ((l & -34359738368L) == 0L) {
            return 5;
        }
        if ((l & -4398046511104L) == 0L) {
            return 6;
        }
        if ((l & -562949953421312L) == 0L) {
            return 7;
        }
        if ((l & -72057594037927936L) == 0L) {
            return 8;
        }
        if ((l & Long.MIN_VALUE) == 0L) {
            return 9;
        }
        return 10;
    }

    public static int computeSFixed32Size(int n, int n2) {
        return CodedOutputStream.computeTagSize(n) + CodedOutputStream.computeSFixed32SizeNoTag(n2);
    }

    public static int computeSFixed32SizeNoTag(int n) {
        return 4;
    }

    public static int computeSFixed64Size(int n, long l) {
        return CodedOutputStream.computeTagSize(n) + CodedOutputStream.computeSFixed64SizeNoTag(l);
    }

    public static int computeSFixed64SizeNoTag(long l) {
        return 8;
    }

    public static int computeSInt32Size(int n, int n2) {
        return CodedOutputStream.computeTagSize(n) + CodedOutputStream.computeSInt32SizeNoTag(n2);
    }

    public static int computeSInt32SizeNoTag(int n) {
        return CodedOutputStream.computeRawVarint32Size(CodedOutputStream.encodeZigZag32(n));
    }

    public static int computeSInt64Size(int n, long l) {
        return CodedOutputStream.computeTagSize(n) + CodedOutputStream.computeSInt64SizeNoTag(l);
    }

    public static int computeSInt64SizeNoTag(long l) {
        return CodedOutputStream.computeRawVarint64Size(CodedOutputStream.encodeZigZag64(l));
    }

    public static int computeStringSize(int n, String string) {
        return CodedOutputStream.computeTagSize(n) + CodedOutputStream.computeStringSizeNoTag(string);
    }

    public static int computeStringSizeNoTag(String arrby) {
        try {
            arrby = arrby.getBytes("UTF-8");
            int n = CodedOutputStream.computeRawVarint32Size(arrby.length);
            int n2 = arrby.length;
            return n + n2;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new RuntimeException("UTF-8 not supported.", unsupportedEncodingException);
        }
    }

    public static int computeTagSize(int n) {
        return CodedOutputStream.computeRawVarint32Size(WireFormat.makeTag(n, 0));
    }

    public static int computeUInt32Size(int n, int n2) {
        return CodedOutputStream.computeTagSize(n) + CodedOutputStream.computeUInt32SizeNoTag(n2);
    }

    public static int computeUInt32SizeNoTag(int n) {
        return CodedOutputStream.computeRawVarint32Size(n);
    }

    public static int computeUInt64Size(int n, long l) {
        return CodedOutputStream.computeTagSize(n) + CodedOutputStream.computeUInt64SizeNoTag(l);
    }

    public static int computeUInt64SizeNoTag(long l) {
        return CodedOutputStream.computeRawVarint64Size(l);
    }

    public static int encodeZigZag32(int n) {
        return n >> 31 ^ n << 1;
    }

    public static long encodeZigZag64(long l) {
        return l << 1 ^ l >> 63;
    }

    public static CodedOutputStream newInstance(OutputStream outputStream) {
        return CodedOutputStream.newInstance(outputStream, 4096);
    }

    public static CodedOutputStream newInstance(OutputStream outputStream, int n) {
        return new CodedOutputStream(outputStream, new byte[n]);
    }

    public static CodedOutputStream newInstance(byte[] arrby) {
        return CodedOutputStream.newInstance(arrby, 0, arrby.length);
    }

    public static CodedOutputStream newInstance(byte[] arrby, int n, int n2) {
        return new CodedOutputStream(arrby, n, n2);
    }

    private void refreshBuffer() throws IOException {
        if (this.output == null) {
            throw new OutOfSpaceException();
        }
        this.output.write(this.buffer, 0, this.position);
        this.position = 0;
    }

    public void checkNoSpaceLeft() {
        if (this.spaceLeft() != 0) {
            throw new IllegalStateException("Did not write as much data as expected.");
        }
    }

    @Override
    public void flush() throws IOException {
        if (this.output != null) {
            this.refreshBuffer();
        }
    }

    public int spaceLeft() {
        if (this.output == null) {
            return this.limit - this.position;
        }
        throw new UnsupportedOperationException("spaceLeft() can only be called on CodedOutputStreams that are writing to a flat array.");
    }

    public void writeBool(int n, boolean bl) throws IOException {
        this.writeTag(n, 0);
        this.writeBoolNoTag(bl);
    }

    public void writeBoolNoTag(boolean bl) throws IOException {
        RuntimeException runtimeException;
        super("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:269)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
        throw runtimeException;
    }

    public void writeBytes(int n, ByteString byteString) throws IOException {
        this.writeTag(n, 2);
        this.writeBytesNoTag(byteString);
    }

    public void writeBytesNoTag(ByteString byteString) throws IOException {
        this.writeRawVarint32(byteString.size());
        this.writeRawBytes(byteString);
    }

    public void writeDouble(int n, double d) throws IOException {
        this.writeTag(n, 1);
        this.writeDoubleNoTag(d);
    }

    public void writeDoubleNoTag(double d) throws IOException {
        this.writeRawLittleEndian64(Double.doubleToRawLongBits(d));
    }

    public void writeEnum(int n, int n2) throws IOException {
        this.writeTag(n, 0);
        this.writeEnumNoTag(n2);
    }

    public void writeEnumNoTag(int n) throws IOException {
        this.writeInt32NoTag(n);
    }

    public void writeFixed32(int n, int n2) throws IOException {
        this.writeTag(n, 5);
        this.writeFixed32NoTag(n2);
    }

    public void writeFixed32NoTag(int n) throws IOException {
        this.writeRawLittleEndian32(n);
    }

    public void writeFixed64(int n, long l) throws IOException {
        this.writeTag(n, 1);
        this.writeFixed64NoTag(l);
    }

    public void writeFixed64NoTag(long l) throws IOException {
        this.writeRawLittleEndian64(l);
    }

    public void writeFloat(int n, float f) throws IOException {
        this.writeTag(n, 5);
        this.writeFloatNoTag(f);
    }

    public void writeFloatNoTag(float f) throws IOException {
        this.writeRawLittleEndian32(Float.floatToRawIntBits(f));
    }

    public void writeInt32(int n, int n2) throws IOException {
        this.writeTag(n, 0);
        this.writeInt32NoTag(n2);
    }

    public void writeInt32NoTag(int n) throws IOException {
        if (n >= 0) {
            this.writeRawVarint32(n);
            return;
        }
        this.writeRawVarint64(n);
    }

    public void writeInt64(int n, long l) throws IOException {
        this.writeTag(n, 0);
        this.writeInt64NoTag(l);
    }

    public void writeInt64NoTag(long l) throws IOException {
        this.writeRawVarint64(l);
    }

    public void writeRawByte(byte by) throws IOException {
        if (this.position == this.limit) {
            this.refreshBuffer();
        }
        byte[] arrby = this.buffer;
        int n = this.position;
        this.position = n + 1;
        arrby[n] = by;
    }

    public void writeRawByte(int n) throws IOException {
        this.writeRawByte((byte)n);
    }

    public void writeRawBytes(ByteString byteString) throws IOException {
        this.writeRawBytes(byteString, 0, byteString.size());
    }

    public void writeRawBytes(ByteString object, int n, int n2) throws IOException {
        if (this.limit - this.position >= n2) {
            object.copyTo(this.buffer, n, this.position, n2);
            this.position += n2;
            return;
        }
        int n3 = this.limit - this.position;
        object.copyTo(this.buffer, n, this.position, n3);
        int n4 = n + n3;
        this.position = this.limit;
        this.refreshBuffer();
        if (n <= this.limit) {
            object.copyTo(this.buffer, n4, 0, n);
            this.position = n;
            return;
        }
        long l = n4;
        if (l != (object = object.newInput()).skip(l)) {
            throw new IllegalStateException("Skip failed.");
        }
        for (n = n2 - n3; n > 0; n -= n3) {
            n2 = Math.min(n, this.limit);
            n3 = object.read(this.buffer, 0, n2);
            if (n3 != n2) {
                throw new IllegalStateException("Read failed.");
            }
            this.output.write(this.buffer, 0, n3);
        }
    }

    public void writeRawBytes(byte[] arrby) throws IOException {
        this.writeRawBytes(arrby, 0, arrby.length);
    }

    public void writeRawBytes(byte[] arrby, int n, int n2) throws IOException {
        if (this.limit - this.position >= n2) {
            System.arraycopy(arrby, n, this.buffer, this.position, n2);
            this.position += n2;
            return;
        }
        int n3 = this.limit - this.position;
        System.arraycopy(arrby, n, this.buffer, this.position, n3);
        n += n3;
        this.position = this.limit;
        this.refreshBuffer();
        if ((n2 -= n3) <= this.limit) {
            System.arraycopy(arrby, n, this.buffer, 0, n2);
            this.position = n2;
            return;
        }
        this.output.write(arrby, n, n2);
    }

    public void writeRawLittleEndian32(int n) throws IOException {
        this.writeRawByte(n & 255);
        this.writeRawByte(n >> 8 & 255);
        this.writeRawByte(n >> 16 & 255);
        this.writeRawByte(n >> 24 & 255);
    }

    public void writeRawLittleEndian64(long l) throws IOException {
        this.writeRawByte((int)l & 255);
        this.writeRawByte((int)(l >> 8) & 255);
        this.writeRawByte((int)(l >> 16) & 255);
        this.writeRawByte((int)(l >> 24) & 255);
        this.writeRawByte((int)(l >> 32) & 255);
        this.writeRawByte((int)(l >> 40) & 255);
        this.writeRawByte((int)(l >> 48) & 255);
        this.writeRawByte((int)(l >> 56) & 255);
    }

    public void writeRawMessageSetExtension(int n, ByteString byteString) throws IOException {
        this.writeTag(1, 3);
        this.writeUInt32(2, n);
        this.writeBytes(3, byteString);
        this.writeTag(1, 4);
    }

    public void writeRawVarint32(int n) throws IOException {
        do {
            if ((n & -128) == 0) {
                this.writeRawByte(n);
                return;
            }
            this.writeRawByte(n & 127 | 128);
            n >>>= 7;
        } while (true);
    }

    public void writeRawVarint64(long l) throws IOException {
        do {
            if ((l & -128L) == 0L) {
                this.writeRawByte((int)l);
                return;
            }
            this.writeRawByte((int)l & 127 | 128);
            l >>>= 7;
        } while (true);
    }

    public void writeSFixed32(int n, int n2) throws IOException {
        this.writeTag(n, 5);
        this.writeSFixed32NoTag(n2);
    }

    public void writeSFixed32NoTag(int n) throws IOException {
        this.writeRawLittleEndian32(n);
    }

    public void writeSFixed64(int n, long l) throws IOException {
        this.writeTag(n, 1);
        this.writeSFixed64NoTag(l);
    }

    public void writeSFixed64NoTag(long l) throws IOException {
        this.writeRawLittleEndian64(l);
    }

    public void writeSInt32(int n, int n2) throws IOException {
        this.writeTag(n, 0);
        this.writeSInt32NoTag(n2);
    }

    public void writeSInt32NoTag(int n) throws IOException {
        this.writeRawVarint32(CodedOutputStream.encodeZigZag32(n));
    }

    public void writeSInt64(int n, long l) throws IOException {
        this.writeTag(n, 0);
        this.writeSInt64NoTag(l);
    }

    public void writeSInt64NoTag(long l) throws IOException {
        this.writeRawVarint64(CodedOutputStream.encodeZigZag64(l));
    }

    public void writeString(int n, String string) throws IOException {
        this.writeTag(n, 2);
        this.writeStringNoTag(string);
    }

    public void writeStringNoTag(String arrby) throws IOException {
        arrby = arrby.getBytes("UTF-8");
        this.writeRawVarint32(arrby.length);
        this.writeRawBytes(arrby);
    }

    public void writeTag(int n, int n2) throws IOException {
        this.writeRawVarint32(WireFormat.makeTag(n, n2));
    }

    public void writeUInt32(int n, int n2) throws IOException {
        this.writeTag(n, 0);
        this.writeUInt32NoTag(n2);
    }

    public void writeUInt32NoTag(int n) throws IOException {
        this.writeRawVarint32(n);
    }

    public void writeUInt64(int n, long l) throws IOException {
        this.writeTag(n, 0);
        this.writeUInt64NoTag(l);
    }

    public void writeUInt64NoTag(long l) throws IOException {
        this.writeRawVarint64(l);
    }

    static class OutOfSpaceException
    extends IOException {
        private static final long serialVersionUID = -6947486886997889499L;

        OutOfSpaceException() {
            super("CodedOutputStream was writing to a flat byte array and ran out of space.");
        }
    }

}
