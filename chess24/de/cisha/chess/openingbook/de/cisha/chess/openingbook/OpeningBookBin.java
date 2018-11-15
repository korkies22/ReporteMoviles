/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.openingbook;

import de.cisha.chess.model.FEN;
import de.cisha.chess.openingbook.BookMove;
import de.cisha.chess.openingbook.BookMoveBin;
import de.cisha.chess.openingbook.OpeningBook;
import de.cisha.chess.openingbook.PolyglotBinBookHelper;
import de.cisha.chess.util.Logger;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OpeningBookBin
extends OpeningBook {
    byte[] _bytes;

    public OpeningBookBin(InputStream inputStream) {
        this.readStream(inputStream);
    }

    /*
     * Enabled aggressive block sorting
     */
    private BookMove[] getBookEntriesForKeyInternal(long l) {
        int n;
        int n2 = 0;
        int n3 = this._bytes.length / 16;
        int n4 = -1;
        int n5 = 0;
        while (n5 != n3 && n4 == -1) {
            n = (n5 + n3) / 2;
            long l2 = this.getKeyAtIndex(n);
            if (l < l2 && l > 0L && l2 > 0L || l > 0L && l2 < 0L || l < 0L && l2 < 0L && l < l2) {
                n3 = n;
                continue;
            }
            if (l > l2 && l > 0L && l2 > 0L || l < 0L && l2 > 0L || l < 0L && l2 < 0L && l > l2) {
                if (n5 == n) {
                    n = n5 = n + 1;
                    if (this.getKeyAtIndex(n3) == l) {
                        n4 = n3;
                        n = n5;
                    }
                }
                n5 = n;
                continue;
            }
            if (l != l2) break;
            n4 = n;
        }
        if (n4 == -1) return new BookMove[0];
        n3 = n4;
        do {
            n = n4;
            if (n3 <= 0) break;
            n = n4;
            if (this.getKeyAtIndex(n3 - 1) != l) break;
            --n3;
        } while (true);
        while (n < this._bytes.length / 16 - 1 && this.getKeyAtIndex(n4 = n + 1) == l) {
            n = n4;
        }
        BookMove[] arrbookMove = new BookMove[(n -= n3) + 1];
        n4 = n2;
        do {
            BookMove[] arrbookMove2 = arrbookMove;
            if (n4 > n) return arrbookMove2;
            arrbookMove[n4] = new BookMoveBin(this.getBytes((n4 + n3) * 16 + 8, 8));
            ++n4;
        } while (true);
    }

    private byte[] getBytes(int n, int n2) {
        return Arrays.copyOfRange(this._bytes, n, n2 + n);
    }

    private long getKeyAtIndex(int n) {
        return this.longFromByte(this.getBytes(n * 16, 8));
    }

    private long longFromByte(byte[] arrby) {
        if (arrby.length == 8) {
            ByteBuffer byteBuffer = ByteBuffer.allocate(8);
            byteBuffer.put(arrby);
            byteBuffer.flip();
            return byteBuffer.getLong();
        }
        return 0L;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void readStream(InputStream var1_1) {
        var2_4 = new ByteArrayOutputStream();
        var1_1 = new BufferedInputStream(var1_1);
        while (var1_1.available() > 0) {
            var3_6 = new byte[16];
            var1_1.read(var3_6);
            var2_4.write(var3_6);
        }
lbl9: // 2 sources:
        do {
            try {
                var1_1.close();
            }
            catch (IOException var1_2) {}
            this._bytes = var2_4.toByteArray();
            return;
            break;
        } while (true);
        {
            catch (Throwable var2_5) {
            }
            catch (IOException var3_7) {}
            {
                Logger.getInstance().debug(OpeningBookBin.class.getName(), IOException.class.getName(), var3_7);
                ** continue;
            }
        }
        try {
            var1_1.close();
        }
        catch (IOException var1_3) {
            throw var2_5;
        }
        throw var2_5;
    }

    @Override
    public List<BookMove> getBookEntriesForFEN(FEN arrbookMove) {
        arrbookMove = this.getBookEntriesForKeyInternal(PolyglotBinBookHelper.getKeyFromFEN((FEN)arrbookMove));
        ArrayList<BookMove> arrayList = new ArrayList<BookMove>(arrbookMove.length);
        for (int i = 0; i < arrbookMove.length; ++i) {
            arrayList.add(arrbookMove[i]);
        }
        return arrayList;
    }
}
