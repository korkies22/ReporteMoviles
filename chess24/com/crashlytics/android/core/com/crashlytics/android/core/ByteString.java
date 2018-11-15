/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

import com.crashlytics.android.core.CodedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FilterOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.List;

final class ByteString {
    public static final ByteString EMPTY = new ByteString(new byte[0]);
    private final byte[] bytes;
    private volatile int hash = 0;

    private ByteString(byte[] arrby) {
        this.bytes = arrby;
    }

    public static ByteString copyFrom(String string, String string2) throws UnsupportedEncodingException {
        return new ByteString(string.getBytes(string2));
    }

    public static ByteString copyFrom(ByteBuffer byteBuffer) {
        return ByteString.copyFrom(byteBuffer, byteBuffer.remaining());
    }

    public static ByteString copyFrom(ByteBuffer byteBuffer, int n) {
        byte[] arrby = new byte[n];
        byteBuffer.get(arrby);
        return new ByteString(arrby);
    }

    public static ByteString copyFrom(List<ByteString> object) {
        if (object.size() == 0) {
            return EMPTY;
        }
        if (object.size() == 1) {
            return object.get(0);
        }
        byte[] arrby = object.iterator();
        int n = 0;
        while (arrby.hasNext()) {
            n += arrby.next().size();
        }
        arrby = new byte[n];
        object = object.iterator();
        n = 0;
        while (object.hasNext()) {
            ByteString byteString = (ByteString)object.next();
            System.arraycopy(byteString.bytes, 0, arrby, n, byteString.size());
            n += byteString.size();
        }
        return new ByteString(arrby);
    }

    public static ByteString copyFrom(byte[] arrby) {
        return ByteString.copyFrom(arrby, 0, arrby.length);
    }

    public static ByteString copyFrom(byte[] arrby, int n, int n2) {
        byte[] arrby2 = new byte[n2];
        System.arraycopy(arrby, n, arrby2, 0, n2);
        return new ByteString(arrby2);
    }

    public static ByteString copyFromUtf8(String object) {
        try {
            object = new ByteString(object.getBytes("UTF-8"));
            return object;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new RuntimeException("UTF-8 not supported.", unsupportedEncodingException);
        }
    }

    static CodedBuilder newCodedBuilder(int n) {
        return new CodedBuilder(n);
    }

    public static Output newOutput() {
        return ByteString.newOutput(32);
    }

    public static Output newOutput(int n) {
        return new Output(new ByteArrayOutputStream(n));
    }

    public ByteBuffer asReadOnlyByteBuffer() {
        return ByteBuffer.wrap(this.bytes).asReadOnlyBuffer();
    }

    public byte byteAt(int n) {
        return this.bytes[n];
    }

    public void copyTo(ByteBuffer byteBuffer) {
        byteBuffer.put(this.bytes, 0, this.bytes.length);
    }

    public void copyTo(byte[] arrby, int n) {
        System.arraycopy(this.bytes, 0, arrby, n, this.bytes.length);
    }

    public void copyTo(byte[] arrby, int n, int n2, int n3) {
        System.arraycopy(this.bytes, n, arrby, n2, n3);
    }

    public boolean equals(Object arrby) {
        if (arrby == this) {
            return true;
        }
        if (!(arrby instanceof ByteString)) {
            return false;
        }
        byte[] arrby2 = arrby;
        int n = this.bytes.length;
        if (n != arrby2.bytes.length) {
            return false;
        }
        arrby = this.bytes;
        arrby2 = arrby2.bytes;
        for (int i = 0; i < n; ++i) {
            if (arrby[i] == arrby2[i]) continue;
            return false;
        }
        return true;
    }

    public int hashCode() {
        int n;
        int n2 = n = this.hash;
        if (n == 0) {
            byte[] arrby = this.bytes;
            int n3 = this.bytes.length;
            n2 = n3;
            for (n = 0; n < n3; ++n) {
                n2 = n2 * 31 + arrby[n];
            }
            if (n2 == 0) {
                n2 = 1;
            }
            this.hash = n2;
        }
        return n2;
    }

    public boolean isEmpty() {
        if (this.bytes.length == 0) {
            return true;
        }
        return false;
    }

    public InputStream newInput() {
        return new ByteArrayInputStream(this.bytes);
    }

    public int size() {
        return this.bytes.length;
    }

    public byte[] toByteArray() {
        int n = this.bytes.length;
        byte[] arrby = new byte[n];
        System.arraycopy(this.bytes, 0, arrby, 0, n);
        return arrby;
    }

    public String toString(String string) throws UnsupportedEncodingException {
        return new String(this.bytes, string);
    }

    public String toStringUtf8() {
        try {
            String string = new String(this.bytes, "UTF-8");
            return string;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new RuntimeException("UTF-8 not supported?", unsupportedEncodingException);
        }
    }

    static final class CodedBuilder {
        private final byte[] buffer;
        private final CodedOutputStream output;

        private CodedBuilder(int n) {
            this.buffer = new byte[n];
            this.output = CodedOutputStream.newInstance(this.buffer);
        }

        public ByteString build() {
            this.output.checkNoSpaceLeft();
            return new ByteString(this.buffer);
        }

        public CodedOutputStream getCodedOutput() {
            return this.output;
        }
    }

    static final class Output
    extends FilterOutputStream {
        private final ByteArrayOutputStream bout;

        private Output(ByteArrayOutputStream byteArrayOutputStream) {
            super(byteArrayOutputStream);
            this.bout = byteArrayOutputStream;
        }

        public ByteString toByteString() {
            return new ByteString(this.bout.toByteArray());
        }
    }

}
