/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.datamatrix.encoder;

import com.google.zxing.datamatrix.encoder.Encoder;
import com.google.zxing.datamatrix.encoder.EncoderContext;
import com.google.zxing.datamatrix.encoder.HighLevelEncoder;

final class ASCIIEncoder
implements Encoder {
    ASCIIEncoder() {
    }

    private static char encodeASCIIDigits(char c, char c2) {
        if (HighLevelEncoder.isDigit(c) && HighLevelEncoder.isDigit(c2)) {
            return (char)((c - 48) * 10 + (c2 - 48) + 130);
        }
        StringBuilder stringBuilder = new StringBuilder("not digits: ");
        stringBuilder.append(c);
        stringBuilder.append(c2);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @Override
    public void encode(EncoderContext object) {
        if (HighLevelEncoder.determineConsecutiveDigitCount(object.getMessage(), object.pos) >= 2) {
            object.writeCodeword(ASCIIEncoder.encodeASCIIDigits(object.getMessage().charAt(object.pos), object.getMessage().charAt(object.pos + 1)));
            object.pos += 2;
            return;
        }
        char c = object.getCurrentChar();
        int n = HighLevelEncoder.lookAheadTest(object.getMessage(), object.pos, this.getEncodingMode());
        if (n != this.getEncodingMode()) {
            switch (n) {
                default: {
                    object = new StringBuilder("Illegal mode: ");
                    object.append(n);
                    throw new IllegalStateException(object.toString());
                }
                case 5: {
                    object.writeCodeword('\u00e7');
                    object.signalEncoderChange(5);
                    return;
                }
                case 4: {
                    object.writeCodeword('\u00f0');
                    object.signalEncoderChange(4);
                    return;
                }
                case 3: {
                    object.writeCodeword('\u00ee');
                    object.signalEncoderChange(3);
                    return;
                }
                case 2: {
                    object.writeCodeword('\u00ef');
                    object.signalEncoderChange(2);
                    return;
                }
                case 1: 
            }
            object.writeCodeword('\u00e6');
            object.signalEncoderChange(1);
            return;
        }
        if (HighLevelEncoder.isExtendedASCII(c)) {
            object.writeCodeword('\u00eb');
            object.writeCodeword((char)(c - 128 + 1));
            ++object.pos;
            return;
        }
        object.writeCodeword((char)(c + '\u0001'));
        ++object.pos;
    }

    @Override
    public int getEncodingMode() {
        return 0;
    }
}
