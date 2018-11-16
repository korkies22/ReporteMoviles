// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.openingbook;

import de.cisha.chess.model.Square;
import de.cisha.chess.model.SEP;
import java.nio.ByteOrder;
import java.nio.ByteBuffer;

public class BookMoveBin extends BookMove
{
    private static final int[] powsof2;
    private byte[] _bytes;
    
    static {
        powsof2 = new int[] { 1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192 };
    }
    
    public BookMoveBin(final byte[] bytes) {
        this._bytes = bytes;
    }
    
    private int getBitsAsInt(final byte[] array, final int n, final int n2, final int n3) {
        int n6;
        if (n3 < (n + 1) * 8 - n2 && n < array.length) {
            int n4 = 0;
            int n5 = 0;
            while (true) {
                n6 = n5;
                if (n4 >= n3) {
                    break;
                }
                final int n7 = n2 + n4;
                final byte b = array[n - n7 / 8];
                int n8 = n5;
                if ((BookMoveBin.powsof2[n7 % 8] & b) != 0x0) {
                    n8 = n5 + BookMoveBin.powsof2[n4];
                }
                ++n4;
                n5 = n8;
            }
        }
        else {
            n6 = -1;
        }
        return n6;
    }
    
    private int getTwoByteAsInt(final int n) {
        if (this._bytes != null && this._bytes.length == 8) {
            final ByteBuffer allocate = ByteBuffer.allocate(4);
            allocate.put(new byte[] { 0, 0 });
            allocate.put(this._bytes, n, 2);
            allocate.order(ByteOrder.BIG_ENDIAN);
            allocate.flip();
            return allocate.getInt();
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
        if (this._bytes == null || this._bytes.length != 8) {
            return null;
        }
        final byte[] bytes = this._bytes;
        final boolean b = false;
        final int bitsAsInt = this.getBitsAsInt(bytes, 1, 0, 3);
        final int bitsAsInt2 = this.getBitsAsInt(this._bytes, 1, 3, 3);
        final int bitsAsInt3 = this.getBitsAsInt(this._bytes, 1, 6, 3);
        final int bitsAsInt4 = this.getBitsAsInt(this._bytes, 1, 9, 3);
        int n = b ? 1 : 0;
        switch (this.getBitsAsInt(this._bytes, 1, 12, 3)) {
            default: {
                n = (b ? 1 : 0);
                return new SEP(Square.instanceForRowAndColumn(bitsAsInt4, bitsAsInt3), Square.instanceForRowAndColumn(bitsAsInt2, bitsAsInt), n);
            }
            case 3: {
                return new SEP(Square.instanceForRowAndColumn(bitsAsInt4, bitsAsInt3), Square.instanceForRowAndColumn(bitsAsInt2, bitsAsInt), n);
            }
            case 4: {
                n = 3;
                return new SEP(Square.instanceForRowAndColumn(bitsAsInt4, bitsAsInt3), Square.instanceForRowAndColumn(bitsAsInt2, bitsAsInt), n);
            }
            case 2: {
                n = 2;
                return new SEP(Square.instanceForRowAndColumn(bitsAsInt4, bitsAsInt3), Square.instanceForRowAndColumn(bitsAsInt2, bitsAsInt), n);
            }
            case 1: {
                n = 1;
                return new SEP(Square.instanceForRowAndColumn(bitsAsInt4, bitsAsInt3), Square.instanceForRowAndColumn(bitsAsInt2, bitsAsInt), n);
            }
        }
    }
    
    @Override
    public int getWhiteWins() {
        return this.getTwoByteAsInt(4);
    }
}
