/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.datamatrix.encoder;

import com.google.zxing.datamatrix.encoder.Encoder;
import com.google.zxing.datamatrix.encoder.EncoderContext;
import com.google.zxing.datamatrix.encoder.HighLevelEncoder;
import com.google.zxing.datamatrix.encoder.SymbolInfo;

class C40Encoder
implements Encoder {
    C40Encoder() {
    }

    private int backtrackOneCharacter(EncoderContext encoderContext, StringBuilder stringBuilder, StringBuilder stringBuilder2, int n) {
        int n2 = stringBuilder.length();
        stringBuilder.delete(n2 - n, n2);
        --encoderContext.pos;
        n = this.encodeChar(encoderContext.getCurrentChar(), stringBuilder2);
        encoderContext.resetSymbolInfo();
        return n;
    }

    private static String encodeToCodewords(CharSequence charSequence, int n) {
        n = charSequence.charAt(n) * 1600 + charSequence.charAt(n + 1) * 40 + charSequence.charAt(n + 2) + 1;
        return new String(new char[]{(char)(n / 256), (char)(n % 256)});
    }

    static void writeNextTriplet(EncoderContext encoderContext, StringBuilder stringBuilder) {
        encoderContext.writeCodewords(C40Encoder.encodeToCodewords(stringBuilder, 0));
        stringBuilder.delete(0, 3);
    }

    @Override
    public void encode(EncoderContext encoderContext) {
        StringBuilder stringBuilder = new StringBuilder();
        while (encoderContext.hasMoreCharacters()) {
            int n;
            block4 : {
                StringBuilder stringBuilder2;
                int n2;
                block5 : {
                    int n3;
                    block6 : {
                        char c = encoderContext.getCurrentChar();
                        ++encoderContext.pos;
                        n3 = this.encodeChar(c, stringBuilder);
                        n = stringBuilder.length() / 3;
                        n = encoderContext.getCodewordCount() + (n << 1);
                        encoderContext.updateSymbolInfo(n);
                        n2 = encoderContext.getSymbolInfo().getDataCapacity() - n;
                        if (encoderContext.hasMoreCharacters()) break block4;
                        stringBuilder2 = new StringBuilder();
                        n = n3;
                        if (stringBuilder.length() % 3 != 2) break block5;
                        if (n2 < 2) break block6;
                        n = n3;
                        if (n2 <= 2) break block5;
                    }
                    n = this.backtrackOneCharacter(encoderContext, stringBuilder, stringBuilder2, n3);
                }
                while (stringBuilder.length() % 3 == 1 && (n <= 3 && n2 != 1 || n > 3)) {
                    n = this.backtrackOneCharacter(encoderContext, stringBuilder, stringBuilder2, n);
                }
                break;
            }
            if (stringBuilder.length() % 3 != 0 || (n = HighLevelEncoder.lookAheadTest(encoderContext.getMessage(), encoderContext.pos, this.getEncodingMode())) == this.getEncodingMode()) continue;
            encoderContext.signalEncoderChange(n);
            break;
        }
        this.handleEOD(encoderContext, stringBuilder);
    }

    int encodeChar(char c, StringBuilder stringBuilder) {
        if (c == ' ') {
            stringBuilder.append('\u0003');
            return 1;
        }
        if (c >= '0' && c <= '9') {
            stringBuilder.append((char)(c - 48 + 4));
            return 1;
        }
        if (c >= 'A' && c <= 'Z') {
            stringBuilder.append((char)(c - 65 + 14));
            return 1;
        }
        if (c >= '\u0000' && c <= '\u001f') {
            stringBuilder.append('\u0000');
            stringBuilder.append(c);
            return 2;
        }
        if (c >= '!' && c <= '/') {
            stringBuilder.append('\u0001');
            stringBuilder.append((char)(c - 33));
            return 2;
        }
        if (c >= ':' && c <= '@') {
            stringBuilder.append('\u0001');
            stringBuilder.append((char)(c - 58 + 15));
            return 2;
        }
        if (c >= '[' && c <= '_') {
            stringBuilder.append('\u0001');
            stringBuilder.append((char)(c - 91 + 22));
            return 2;
        }
        if (c >= '`' && c <= '') {
            stringBuilder.append('\u0002');
            stringBuilder.append((char)(c - 96));
            return 2;
        }
        if (c >= '?') {
            stringBuilder.append("\u0001\u001e");
            return 2 + this.encodeChar((char)(c - 128), stringBuilder);
        }
        stringBuilder = new StringBuilder("Illegal character: ");
        stringBuilder.append(c);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @Override
    public int getEncodingMode() {
        return 1;
    }

    void handleEOD(EncoderContext encoderContext, StringBuilder stringBuilder) {
        block13 : {
            block11 : {
                int n;
                int n2;
                block12 : {
                    block10 : {
                        n2 = stringBuilder.length() / 3;
                        n = stringBuilder.length() % 3;
                        n2 = encoderContext.getCodewordCount() + (n2 << 1);
                        encoderContext.updateSymbolInfo(n2);
                        n2 = encoderContext.getSymbolInfo().getDataCapacity() - n2;
                        if (n != 2) break block10;
                        stringBuilder.append('\u0000');
                        while (stringBuilder.length() >= 3) {
                            C40Encoder.writeNextTriplet(encoderContext, stringBuilder);
                        }
                        if (encoderContext.hasMoreCharacters()) {
                            encoderContext.writeCodeword('\u00fe');
                        }
                        break block11;
                    }
                    if (n2 != 1 || n != 1) break block12;
                    while (stringBuilder.length() >= 3) {
                        C40Encoder.writeNextTriplet(encoderContext, stringBuilder);
                    }
                    if (encoderContext.hasMoreCharacters()) {
                        encoderContext.writeCodeword('\u00fe');
                    }
                    --encoderContext.pos;
                    break block11;
                }
                if (n != 0) break block13;
                while (stringBuilder.length() >= 3) {
                    C40Encoder.writeNextTriplet(encoderContext, stringBuilder);
                }
                if (n2 > 0 || encoderContext.hasMoreCharacters()) {
                    encoderContext.writeCodeword('\u00fe');
                }
            }
            encoderContext.signalEncoderChange(0);
            return;
        }
        throw new IllegalStateException("Unexpected case. Please report!");
    }
}
