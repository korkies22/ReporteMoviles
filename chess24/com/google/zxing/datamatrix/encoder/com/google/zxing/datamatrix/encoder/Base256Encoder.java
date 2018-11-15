/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.datamatrix.encoder;

import com.google.zxing.datamatrix.encoder.Encoder;
import com.google.zxing.datamatrix.encoder.EncoderContext;
import com.google.zxing.datamatrix.encoder.HighLevelEncoder;
import com.google.zxing.datamatrix.encoder.SymbolInfo;

final class Base256Encoder
implements Encoder {
    Base256Encoder() {
    }

    private static char randomize255State(char c, int n) {
        if ((c = (char)(c + (n * 149 % 255 + 1))) <= '\u00ff') {
            return c;
        }
        return (char)(c - 256);
    }

    @Override
    public void encode(EncoderContext object) {
        int n;
        block6 : {
            StringBuilder stringBuilder;
            int n2;
            block4 : {
                block5 : {
                    stringBuilder = new StringBuilder();
                    int n3 = 0;
                    stringBuilder.append('\u0000');
                    while (object.hasMoreCharacters()) {
                        stringBuilder.append(object.getCurrentChar());
                        ++object.pos;
                        n2 = HighLevelEncoder.lookAheadTest(object.getMessage(), object.pos, this.getEncodingMode());
                        if (n2 == this.getEncodingMode()) continue;
                        object.signalEncoderChange(n2);
                        break;
                    }
                    n = stringBuilder.length() - 1;
                    n2 = object.getCodewordCount() + n + 1;
                    object.updateSymbolInfo(n2);
                    n2 = object.getSymbolInfo().getDataCapacity() - n2 > 0 ? 1 : 0;
                    if (!object.hasMoreCharacters() && n2 == 0) break block4;
                    if (n > 249) break block5;
                    stringBuilder.setCharAt(0, (char)n);
                    break block4;
                }
                if (n > 1555) break block6;
                stringBuilder.setCharAt(0, (char)(n / 250 + 249));
                stringBuilder.insert(1, (char)(n % 250));
            }
            n = stringBuilder.length();
            for (n2 = n3; n2 < n; ++n2) {
                object.writeCodeword(Base256Encoder.randomize255State(stringBuilder.charAt(n2), object.getCodewordCount() + 1));
            }
            return;
        }
        object = new StringBuilder("Message length not in valid ranges: ");
        object.append(n);
        throw new IllegalStateException(object.toString());
    }

    @Override
    public int getEncodingMode() {
        return 5;
    }
}
