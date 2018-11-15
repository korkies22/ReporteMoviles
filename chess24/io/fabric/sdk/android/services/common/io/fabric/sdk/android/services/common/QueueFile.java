/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android.services.common;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QueueFile
implements Closeable {
    static final int HEADER_LENGTH = 16;
    private static final int INITIAL_LENGTH = 4096;
    private static final Logger LOGGER = Logger.getLogger(QueueFile.class.getName());
    private final byte[] buffer = new byte[16];
    private int elementCount;
    int fileLength;
    private Element first;
    private Element last;
    private final RandomAccessFile raf;

    public QueueFile(File file) throws IOException {
        if (!file.exists()) {
            QueueFile.initialize(file);
        }
        this.raf = QueueFile.open(file);
        this.readHeader();
    }

    QueueFile(RandomAccessFile randomAccessFile) throws IOException {
        this.raf = randomAccessFile;
        this.readHeader();
    }

    private void expandIfNecessary(int n) throws IOException {
        int n2;
        int n3;
        int n4 = n + 4;
        int n5 = this.remainingBytes();
        if (n5 >= n4) {
            return;
        }
        n = this.fileLength;
        do {
            n3 = n5 + n;
            n2 = n << 1;
            n5 = n3;
            n = n2;
        } while (n3 < n4);
        this.setLength(n2);
        n = this.wrapPosition(this.last.position + 4 + this.last.length);
        if (n < this.first.position) {
            FileChannel fileChannel = this.raf.getChannel();
            fileChannel.position(this.fileLength);
            long l = n - 4;
            if (fileChannel.transferTo(16L, l, fileChannel) != l) {
                throw new AssertionError((Object)"Copied insufficient number of bytes!");
            }
        }
        if (this.last.position < this.first.position) {
            n = this.fileLength + this.last.position - 16;
            this.writeHeader(n2, this.elementCount, this.first.position, n);
            this.last = new Element(n, this.last.length);
        } else {
            this.writeHeader(n2, this.elementCount, this.first.position, this.last.position);
        }
        this.fileLength = n2;
    }

    private static void initialize(File file) throws IOException {
        Object object = new StringBuilder();
        object.append(file.getPath());
        object.append(".tmp");
        File file2 = new File(object.toString());
        object = QueueFile.open(file2);
        try {
            object.setLength(4096L);
            object.seek(0L);
            byte[] arrby = new byte[16];
            QueueFile.writeInts(arrby, 4096, 0, 0, 0);
            object.write(arrby);
            if (!file2.renameTo(file)) {
                throw new IOException("Rename failed!");
            }
            return;
        }
        finally {
            object.close();
        }
    }

    private static <T> T nonNull(T t, String string) {
        if (t == null) {
            throw new NullPointerException(string);
        }
        return t;
    }

    private static RandomAccessFile open(File file) throws FileNotFoundException {
        return new RandomAccessFile(file, "rwd");
    }

    private Element readElement(int n) throws IOException {
        if (n == 0) {
            return Element.NULL;
        }
        this.raf.seek(n);
        return new Element(n, this.raf.readInt());
    }

    private void readHeader() throws IOException {
        this.raf.seek(0L);
        this.raf.readFully(this.buffer);
        this.fileLength = QueueFile.readInt(this.buffer, 0);
        if ((long)this.fileLength > this.raf.length()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("File is truncated. Expected length: ");
            stringBuilder.append(this.fileLength);
            stringBuilder.append(", Actual length: ");
            stringBuilder.append(this.raf.length());
            throw new IOException(stringBuilder.toString());
        }
        this.elementCount = QueueFile.readInt(this.buffer, 4);
        int n = QueueFile.readInt(this.buffer, 8);
        int n2 = QueueFile.readInt(this.buffer, 12);
        this.first = this.readElement(n);
        this.last = this.readElement(n2);
    }

    private static int readInt(byte[] arrby, int n) {
        return ((arrby[n] & 255) << 24) + ((arrby[n + 1] & 255) << 16) + ((arrby[n + 2] & 255) << 8) + (arrby[n + 3] & 255);
    }

    private int remainingBytes() {
        return this.fileLength - this.usedBytes();
    }

    private void ringRead(int n, byte[] arrby, int n2, int n3) throws IOException {
        if ((n = this.wrapPosition(n)) + n3 <= this.fileLength) {
            this.raf.seek(n);
            this.raf.readFully(arrby, n2, n3);
            return;
        }
        int n4 = this.fileLength - n;
        this.raf.seek(n);
        this.raf.readFully(arrby, n2, n4);
        this.raf.seek(16L);
        this.raf.readFully(arrby, n2 + n4, n3 - n4);
    }

    private void ringWrite(int n, byte[] arrby, int n2, int n3) throws IOException {
        if ((n = this.wrapPosition(n)) + n3 <= this.fileLength) {
            this.raf.seek(n);
            this.raf.write(arrby, n2, n3);
            return;
        }
        int n4 = this.fileLength - n;
        this.raf.seek(n);
        this.raf.write(arrby, n2, n4);
        this.raf.seek(16L);
        this.raf.write(arrby, n2 + n4, n3 - n4);
    }

    private void setLength(int n) throws IOException {
        this.raf.setLength(n);
        this.raf.getChannel().force(true);
    }

    private int wrapPosition(int n) {
        if (n < this.fileLength) {
            return n;
        }
        return 16 + n - this.fileLength;
    }

    private void writeHeader(int n, int n2, int n3, int n4) throws IOException {
        QueueFile.writeInts(this.buffer, n, n2, n3, n4);
        this.raf.seek(0L);
        this.raf.write(this.buffer);
    }

    private static void writeInt(byte[] arrby, int n, int n2) {
        arrby[n] = (byte)(n2 >> 24);
        arrby[n + 1] = (byte)(n2 >> 16);
        arrby[n + 2] = (byte)(n2 >> 8);
        arrby[n + 3] = (byte)n2;
    }

    private static /* varargs */ void writeInts(byte[] arrby, int ... arrn) {
        int n = arrn.length;
        int n2 = 0;
        for (int i = 0; i < n; ++i) {
            QueueFile.writeInt(arrby, n2, arrn[i]);
            n2 += 4;
        }
    }

    public void add(byte[] arrby) throws IOException {
        this.add(arrby, 0, arrby.length);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void add(byte[] arrby, int n, int n2) throws IOException {
        synchronized (this) {
            void var3_3;
            int n3;
            QueueFile.nonNull(arrby, "buffer");
            if ((n3 | var3_3) < 0) throw new IndexOutOfBoundsException();
            if (var3_3 > arrby.length - n3) {
                throw new IndexOutOfBoundsException();
            }
            this.expandIfNecessary((int)var3_3);
            boolean bl = this.isEmpty();
            int n4 = bl ? 16 : this.wrapPosition(this.last.position + 4 + this.last.length);
            Element element = new Element(n4, (int)var3_3);
            QueueFile.writeInt(this.buffer, 0, (int)var3_3);
            this.ringWrite(element.position, this.buffer, 0, 4);
            this.ringWrite(element.position + 4, arrby, n3, (int)var3_3);
            n3 = bl ? element.position : this.first.position;
            this.writeHeader(this.fileLength, this.elementCount + 1, n3, element.position);
            this.last = element;
            ++this.elementCount;
            if (!bl) return;
            this.first = this.last;
            return;
        }
    }

    public void clear() throws IOException {
        synchronized (this) {
            this.writeHeader(4096, 0, 0, 0);
            this.elementCount = 0;
            this.first = Element.NULL;
            this.last = Element.NULL;
            if (this.fileLength > 4096) {
                this.setLength(4096);
            }
            this.fileLength = 4096;
            return;
        }
    }

    @Override
    public void close() throws IOException {
        synchronized (this) {
            this.raf.close();
            return;
        }
    }

    public void forEach(ElementReader elementReader) throws IOException {
        synchronized (this) {
            int n = this.first.position;
            int n2 = 0;
            do {
                if (n2 >= this.elementCount) break;
                Element element = this.readElement(n);
                elementReader.read(new ElementInputStream(element), element.length);
                n = this.wrapPosition(element.position + 4 + element.length);
                ++n2;
            } while (true);
            return;
        }
    }

    public boolean hasSpaceFor(int n, int n2) {
        if (this.usedBytes() + 4 + n <= n2) {
            return true;
        }
        return false;
    }

    public boolean isEmpty() {
        synchronized (this) {
            int n = this.elementCount;
            boolean bl = n == 0;
            return bl;
        }
    }

    public void peek(ElementReader elementReader) throws IOException {
        synchronized (this) {
            if (this.elementCount > 0) {
                elementReader.read(new ElementInputStream(this.first), this.first.length);
            }
            return;
        }
    }

    public byte[] peek() throws IOException {
        synchronized (this) {
            block4 : {
                boolean bl = this.isEmpty();
                if (!bl) break block4;
                return null;
            }
            int n = this.first.length;
            byte[] arrby = new byte[n];
            this.ringRead(this.first.position + 4, arrby, 0, n);
            return arrby;
        }
    }

    public void remove() throws IOException {
        synchronized (this) {
            if (this.isEmpty()) {
                throw new NoSuchElementException();
            }
            if (this.elementCount == 1) {
                this.clear();
            } else {
                int n = this.wrapPosition(this.first.position + 4 + this.first.length);
                this.ringRead(n, this.buffer, 0, 4);
                int n2 = QueueFile.readInt(this.buffer, 0);
                this.writeHeader(this.fileLength, this.elementCount - 1, n, this.last.position);
                --this.elementCount;
                this.first = new Element(n, n2);
            }
            return;
        }
    }

    public int size() {
        synchronized (this) {
            int n = this.elementCount;
            return n;
        }
    }

    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getClass().getSimpleName());
        stringBuilder.append('[');
        stringBuilder.append("fileLength=");
        stringBuilder.append(this.fileLength);
        stringBuilder.append(", size=");
        stringBuilder.append(this.elementCount);
        stringBuilder.append(", first=");
        stringBuilder.append(this.first);
        stringBuilder.append(", last=");
        stringBuilder.append(this.last);
        stringBuilder.append(", element lengths=[");
        try {
            this.forEach(new ElementReader(){
                boolean first = true;

                @Override
                public void read(InputStream inputStream, int n) throws IOException {
                    if (this.first) {
                        this.first = false;
                    } else {
                        stringBuilder.append(", ");
                    }
                    stringBuilder.append(n);
                }
            });
        }
        catch (IOException iOException) {
            LOGGER.log(Level.WARNING, "read error", iOException);
        }
        stringBuilder.append("]]");
        return stringBuilder.toString();
    }

    public int usedBytes() {
        if (this.elementCount == 0) {
            return 16;
        }
        if (this.last.position >= this.first.position) {
            return this.last.position - this.first.position + 4 + this.last.length + 16;
        }
        return this.last.position + 4 + this.last.length + this.fileLength - this.first.position;
    }

    static class Element {
        static final int HEADER_LENGTH = 4;
        static final Element NULL = new Element(0, 0);
        final int length;
        final int position;

        Element(int n, int n2) {
            this.position = n;
            this.length = n2;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.getClass().getSimpleName());
            stringBuilder.append("[position = ");
            stringBuilder.append(this.position);
            stringBuilder.append(", length = ");
            stringBuilder.append(this.length);
            stringBuilder.append("]");
            return stringBuilder.toString();
        }
    }

    private final class ElementInputStream
    extends InputStream {
        private int position;
        private int remaining;

        private ElementInputStream(Element element) {
            this.position = QueueFile.this.wrapPosition(element.position + 4);
            this.remaining = element.length;
        }

        @Override
        public int read() throws IOException {
            if (this.remaining == 0) {
                return -1;
            }
            QueueFile.this.raf.seek(this.position);
            int n = QueueFile.this.raf.read();
            this.position = QueueFile.this.wrapPosition(this.position + 1);
            --this.remaining;
            return n;
        }

        @Override
        public int read(byte[] arrby, int n, int n2) throws IOException {
            QueueFile.nonNull(arrby, "buffer");
            if ((n | n2) >= 0 && n2 <= arrby.length - n) {
                if (this.remaining > 0) {
                    int n3 = n2;
                    if (n2 > this.remaining) {
                        n3 = this.remaining;
                    }
                    QueueFile.this.ringRead(this.position, arrby, n, n3);
                    this.position = QueueFile.this.wrapPosition(this.position + n3);
                    this.remaining -= n3;
                    return n3;
                }
                return -1;
            }
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    public static interface ElementReader {
        public void read(InputStream var1, int var2) throws IOException;
    }

}
