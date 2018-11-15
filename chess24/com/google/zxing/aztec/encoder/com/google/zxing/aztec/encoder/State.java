/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.aztec.encoder;

import com.google.zxing.aztec.encoder.HighLevelEncoder;
import com.google.zxing.aztec.encoder.Token;
import com.google.zxing.common.BitArray;
import java.util.Iterator;
import java.util.LinkedList;

final class State {
    static final State INITIAL_STATE = new State(Token.EMPTY, 0, 0, 0);
    private final int binaryShiftByteCount;
    private final int bitCount;
    private final int mode;
    private final Token token;

    private State(Token token, int n, int n2, int n3) {
        this.token = token;
        this.mode = n;
        this.binaryShiftByteCount = n2;
        this.bitCount = n3;
    }

    State addBinaryShiftChar(int n) {
        int n2;
        int n3;
        Object object;
        Object object2;
        int n4;
        block5 : {
            int n5;
            block4 : {
                object = this.token;
                n5 = this.mode;
                n4 = this.bitCount;
                if (this.mode == 4) break block4;
                object2 = object;
                n3 = n5;
                n2 = n4;
                if (this.mode != 2) break block5;
            }
            n2 = HighLevelEncoder.LATCH_TABLE[n5][0];
            n3 = n2 >> 16;
            object2 = object.add(65535 & n2, n3);
            n2 = n4 + n3;
            n3 = 0;
        }
        n4 = this.binaryShiftByteCount != 0 && this.binaryShiftByteCount != 31 ? (this.binaryShiftByteCount == 62 ? 9 : 8) : 18;
        object2 = object = new State((Token)object2, n3, this.binaryShiftByteCount + 1, n2 + n4);
        if (object.binaryShiftByteCount == 2078) {
            object2 = object.endBinaryShift(n + 1);
        }
        return object2;
    }

    State endBinaryShift(int n) {
        if (this.binaryShiftByteCount == 0) {
            return this;
        }
        return new State(this.token.addBinaryShift(n - this.binaryShiftByteCount, this.binaryShiftByteCount), this.mode, 0, this.bitCount);
    }

    int getBinaryShiftByteCount() {
        return this.binaryShiftByteCount;
    }

    int getBitCount() {
        return this.bitCount;
    }

    int getMode() {
        return this.mode;
    }

    Token getToken() {
        return this.token;
    }

    boolean isBetterThanOrEqualTo(State state) {
        int n;
        block4 : {
            int n2;
            block5 : {
                n = n2 = this.bitCount + (HighLevelEncoder.LATCH_TABLE[this.mode][state.mode] >> 16);
                if (state.binaryShiftByteCount <= 0) break block4;
                if (this.binaryShiftByteCount == 0) break block5;
                n = n2;
                if (this.binaryShiftByteCount <= state.binaryShiftByteCount) break block4;
            }
            n = n2 + 10;
        }
        if (n <= state.bitCount) {
            return true;
        }
        return false;
    }

    State latchAndAppend(int n, int n2) {
        int n3 = this.bitCount;
        Token token = this.token;
        int n4 = n3;
        Token token2 = token;
        if (n != this.mode) {
            n4 = HighLevelEncoder.LATCH_TABLE[this.mode][n];
            int n5 = n4 >> 16;
            token2 = token.add(65535 & n4, n5);
            n4 = n3 + n5;
        }
        n3 = n == 2 ? 4 : 5;
        return new State(token2.add(n2, n3), n, 0, n4 + n3);
    }

    State shiftAndAppend(int n, int n2) {
        Token token = this.token;
        int n3 = this.mode == 2 ? 4 : 5;
        return new State(token.add(HighLevelEncoder.SHIFT_TABLE[this.mode][n], n3).add(n2, 5), this.mode, 0, this.bitCount + n3 + 5);
    }

    BitArray toBitArray(byte[] arrby) {
        Object object;
        Object object2 = new LinkedList<Token>();
        for (object = this.endBinaryShift((int)arrby.length).token; object != null; object = object.getPrevious()) {
            object2.addFirst(object);
        }
        object = new BitArray();
        object2 = object2.iterator();
        while (object2.hasNext()) {
            ((Token)object2.next()).appendTo((BitArray)object, arrby);
        }
        return object;
    }

    public String toString() {
        return String.format("%s bits=%d bytes=%d", HighLevelEncoder.MODE_NAMES[this.mode], this.bitCount, this.binaryShiftByteCount);
    }
}
