/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.aztec.encoder;

import com.google.zxing.aztec.encoder.Token;
import com.google.zxing.common.BitArray;

final class BinaryShiftToken
extends Token {
    private final short binaryShiftByteCount;
    private final short binaryShiftStart;

    BinaryShiftToken(Token token, int n, int n2) {
        super(token);
        this.binaryShiftStart = (short)n;
        this.binaryShiftByteCount = (short)n2;
    }

    @Override
    public void appendTo(BitArray bitArray, byte[] arrby) {
        for (int i = 0; i < this.binaryShiftByteCount; ++i) {
            if (i == 0 || i == 31 && this.binaryShiftByteCount <= 62) {
                bitArray.appendBits(31, 5);
                if (this.binaryShiftByteCount > 62) {
                    bitArray.appendBits(this.binaryShiftByteCount - 31, 16);
                } else if (i == 0) {
                    bitArray.appendBits(Math.min(this.binaryShiftByteCount, 31), 5);
                } else {
                    bitArray.appendBits(this.binaryShiftByteCount - 31, 5);
                }
            }
            bitArray.appendBits(arrby[this.binaryShiftStart + i], 8);
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("<");
        stringBuilder.append(this.binaryShiftStart);
        stringBuilder.append("::");
        stringBuilder.append(this.binaryShiftStart + this.binaryShiftByteCount - 1);
        stringBuilder.append('>');
        return stringBuilder.toString();
    }
}
