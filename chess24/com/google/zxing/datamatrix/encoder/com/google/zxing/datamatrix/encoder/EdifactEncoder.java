/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.datamatrix.encoder;

import com.google.zxing.datamatrix.encoder.Encoder;
import com.google.zxing.datamatrix.encoder.EncoderContext;
import com.google.zxing.datamatrix.encoder.HighLevelEncoder;
import com.google.zxing.datamatrix.encoder.SymbolInfo;

final class EdifactEncoder
implements Encoder {
    EdifactEncoder() {
    }

    private static void encodeChar(char c, StringBuilder stringBuilder) {
        if (c >= ' ' && c <= '?') {
            stringBuilder.append(c);
            return;
        }
        if (c >= '@' && c <= '^') {
            stringBuilder.append((char)(c - 64));
            return;
        }
        HighLevelEncoder.illegalCharacter(c);
    }

    private static String encodeToCodewords(CharSequence charSequence, int n) {
        int n2 = charSequence.length() - n;
        if (n2 == 0) {
            throw new IllegalStateException("StringBuilder must not be empty");
        }
        char c = charSequence.charAt(n);
        char c2 = '\u0000';
        char c3 = n2 >= 2 ? charSequence.charAt(n + 1) : (char)'\u0000';
        char c4 = n2 >= 3 ? charSequence.charAt(n + 2) : (char)'\u0000';
        if (n2 >= 4) {
            c2 = charSequence.charAt(n + 3);
        }
        n = (c << 18) + (c3 << 12) + (c4 << 6) + c2;
        char c5 = (char)(n >> 16 & 255);
        char c6 = (char)(n >> 8 & 255);
        char c7 = (char)(n & 255);
        charSequence = new StringBuilder(3);
        charSequence.append(c5);
        if (n2 >= 2) {
            charSequence.append(c6);
        }
        if (n2 >= 3) {
            charSequence.append(c7);
        }
        return ((StringBuilder)charSequence).toString();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static void handleEOD(EncoderContext encoderContext, CharSequence charSequence) {
        int n;
        int n2;
        int n3;
        block14 : {
            block13 : {
                block12 : {
                    int n4;
                    int n5;
                    try {
                        n2 = charSequence.length();
                        if (n2 == 0) {
                            encoderContext.signalEncoderChange(0);
                            return;
                        }
                        n3 = 1;
                        if (n2 != 1) break block12;
                        encoderContext.updateSymbolInfo();
                        n = encoderContext.getSymbolInfo().getDataCapacity();
                        n5 = encoderContext.getCodewordCount();
                        n4 = encoderContext.getRemainingCharacters();
                    }
                    catch (Throwable throwable) {
                        encoderContext.signalEncoderChange(0);
                        throw throwable;
                    }
                    if (n4 == 0 && n - n5 <= 2) {
                        encoderContext.signalEncoderChange(0);
                        return;
                    }
                }
                if (n2 > 4) {
                    throw new IllegalStateException("Count must not exceed 4");
                }
                n = n2 - 1;
                charSequence = EdifactEncoder.encodeToCodewords(charSequence, 0);
                if (!(encoderContext.hasMoreCharacters() ^ true) || n > 2) break block13;
                break block14;
            }
            n3 = 0;
        }
        n2 = n3;
        if (n <= 2) {
            encoderContext.updateSymbolInfo(encoderContext.getCodewordCount() + n);
            n2 = n3;
            if (encoderContext.getSymbolInfo().getDataCapacity() - encoderContext.getCodewordCount() >= 3) {
                encoderContext.updateSymbolInfo(encoderContext.getCodewordCount() + ((String)charSequence).length());
                n2 = 0;
            }
        }
        if (n2 != 0) {
            encoderContext.resetSymbolInfo();
            encoderContext.pos -= n;
        } else {
            encoderContext.writeCodewords((String)charSequence);
        }
        encoderContext.signalEncoderChange(0);
    }

    @Override
    public void encode(EncoderContext encoderContext) {
        StringBuilder stringBuilder = new StringBuilder();
        while (encoderContext.hasMoreCharacters()) {
            EdifactEncoder.encodeChar(encoderContext.getCurrentChar(), stringBuilder);
            ++encoderContext.pos;
            if (stringBuilder.length() < 4) continue;
            encoderContext.writeCodewords(EdifactEncoder.encodeToCodewords(stringBuilder, 0));
            stringBuilder.delete(0, 4);
            if (HighLevelEncoder.lookAheadTest(encoderContext.getMessage(), encoderContext.pos, this.getEncodingMode()) == this.getEncodingMode()) continue;
            encoderContext.signalEncoderChange(0);
            break;
        }
        stringBuilder.append('\u001f');
        EdifactEncoder.handleEOD(encoderContext, stringBuilder);
    }

    @Override
    public int getEncodingMode() {
        return 4;
    }
}
