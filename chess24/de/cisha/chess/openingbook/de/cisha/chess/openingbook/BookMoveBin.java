/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.openingbook;

import de.cisha.chess.model.SEP;
import de.cisha.chess.model.Square;
import de.cisha.chess.openingbook.BookMove;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class BookMoveBin
extends BookMove {
    private static final int[] powsof2 = new int[]{1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192};
    private byte[] _bytes;

    public BookMoveBin(byte[] arrby) {
        this._bytes = arrby;
    }

    private int getBitsAsInt(byte[] arrby, int n, int n2, int n3) {
        int n4;
        if (n3 < (n + 1) * 8 - n2 && n < arrby.length) {
            int n5 = 0;
            int n6 = 0;
            do {
                n4 = n6;
                if (n5 < n3) {
                    int n7 = n2 + n5;
                    byte by = arrby[n - n7 / 8];
                    n4 = n6;
                    if ((powsof2[n7 % 8] & by) != 0) {
                        n4 = n6 + powsof2[n5];
                    }
                    ++n5;
                    n6 = n4;
                    continue;
                }
                break;
            } while (true);
        } else {
            n4 = -1;
        }
        return n4;
    }

    private int getTwoByteAsInt(int n) {
        if (this._bytes != null && this._bytes.length == 8) {
            ByteBuffer byteBuffer = ByteBuffer.allocate(4);
            byteBuffer.put(new byte[]{0, 0});
            byteBuffer.put(this._bytes, n, 2);
            byteBuffer.order(ByteOrder.BIG_ENDIAN);
            byteBuffer.flip();
            return byteBuffer.getInt();
        }
        return -1;
    }

    @Override
    public int getBlackWins() {
        return this.getTwoByteAsInt(6);
    }

    @Override
    public int getDraws() {
        return this.getNumberOfGames() - this.getWhiteWins() - this.getBlackWins();
    }

    @Override
    public int getNumberOfGames() {
        return this.getTwoByteAsInt(2);
    }

    @Override
    public SEP getSEP() {
        if (this._bytes != null && this._bytes.length == 8) {
            byte[] arrby = this._bytes;
            int n = 0;
            int n2 = this.getBitsAsInt(arrby, 1, 0, 3);
            int n3 = this.getBitsAsInt(this._bytes, 1, 3, 3);
            int n4 = this.getBitsAsInt(this._bytes, 1, 6, 3);
            int n5 = this.getBitsAsInt(this._bytes, 1, 9, 3);
            int n6 = n;
            switch (this.getBitsAsInt(this._bytes, 1, 12, 3)) {
                default: {
                    n6 = n;
                    break;
                }
                case 4: {
                    n6 = 3;
                    break;
                }
                case 2: {
                    n6 = 2;
                    break;
                }
                case 1: {
                    n6 = 1;
                }
                case 3: 
            }
            return new SEP(Square.instanceForRowAndColumn(n5, n4), Square.instanceForRowAndColumn(n3, n2), n6);
        }
        return null;
    }

    @Override
    public int getWhiteWins() {
        return this.getTwoByteAsInt(4);
    }
}
