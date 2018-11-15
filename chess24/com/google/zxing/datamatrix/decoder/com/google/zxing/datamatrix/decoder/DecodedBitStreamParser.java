/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.datamatrix.decoder;

import com.google.zxing.FormatException;
import com.google.zxing.common.BitSource;
import com.google.zxing.common.DecoderResult;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

final class DecodedBitStreamParser {
    private static final char[] C40_BASIC_SET_CHARS = new char[]{'*', '*', '*', ' ', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    private static final char[] C40_SHIFT2_SET_CHARS = new char[]{'!', '\"', '#', '$', '%', '&', '\'', '(', ')', '*', '+', ',', '-', '.', '/', ':', ';', '<', '=', '>', '?', '@', '[', '\\', ']', '^', '_'};
    private static final char[] TEXT_BASIC_SET_CHARS = new char[]{'*', '*', '*', ' ', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    private static final char[] TEXT_SHIFT2_SET_CHARS = C40_SHIFT2_SET_CHARS;
    private static final char[] TEXT_SHIFT3_SET_CHARS = new char[]{'`', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '{', '|', '}', '~', ''};

    private DecodedBitStreamParser() {
    }

    static DecoderResult decode(byte[] arrby) throws FormatException {
        Object object = new BitSource(arrby);
        StringBuilder stringBuilder = new StringBuilder(100);
        StringBuilder stringBuilder2 = new StringBuilder(0);
        ArrayList<byte[]> arrayList = new ArrayList<byte[]>(1);
        Object object2 = Mode.ASCII_ENCODE;
        do {
            if (object2 == Mode.ASCII_ENCODE) {
                object2 = DecodedBitStreamParser.decodeAsciiSegment((BitSource)object, stringBuilder, stringBuilder2);
                continue;
            }
            switch (.$SwitchMap$com$google$zxing$datamatrix$decoder$DecodedBitStreamParser$Mode[object2.ordinal()]) {
                default: {
                    throw FormatException.getFormatInstance();
                }
                case 5: {
                    DecodedBitStreamParser.decodeBase256Segment((BitSource)object, stringBuilder, arrayList);
                    break;
                }
                case 4: {
                    DecodedBitStreamParser.decodeEdifactSegment((BitSource)object, stringBuilder);
                    break;
                }
                case 3: {
                    DecodedBitStreamParser.decodeAnsiX12Segment((BitSource)object, stringBuilder);
                    break;
                }
                case 2: {
                    DecodedBitStreamParser.decodeTextSegment((BitSource)object, stringBuilder);
                    break;
                }
                case 1: {
                    DecodedBitStreamParser.decodeC40Segment((BitSource)object, stringBuilder);
                }
            }
            object2 = Mode.ASCII_ENCODE;
        } while (object2 != Mode.PAD_ENCODE && object.available() > 0);
        if (stringBuilder2.length() > 0) {
            stringBuilder.append(stringBuilder2);
        }
        object = stringBuilder.toString();
        object2 = arrayList;
        if (arrayList.isEmpty()) {
            object2 = null;
        }
        return new DecoderResult(arrby, (String)object, (List<byte[]>)object2, null);
    }

    private static void decodeAnsiX12Segment(BitSource bitSource, StringBuilder stringBuilder) throws FormatException {
        int[] arrn = new int[3];
        do {
            if (bitSource.available() == 8) {
                return;
            }
            int n = bitSource.readBits(8);
            if (n == 254) {
                return;
            }
            DecodedBitStreamParser.parseTwoBytes(n, bitSource.readBits(8), arrn);
            for (n = 0; n < 3; ++n) {
                int n2 = arrn[n];
                if (n2 == 0) {
                    stringBuilder.append('\r');
                    continue;
                }
                if (n2 == 1) {
                    stringBuilder.append('*');
                    continue;
                }
                if (n2 == 2) {
                    stringBuilder.append('>');
                    continue;
                }
                if (n2 == 3) {
                    stringBuilder.append(' ');
                    continue;
                }
                if (n2 < 14) {
                    stringBuilder.append((char)(n2 + 44));
                    continue;
                }
                if (n2 < 40) {
                    stringBuilder.append((char)(n2 + 51));
                    continue;
                }
                throw FormatException.getFormatInstance();
            }
        } while (bitSource.available() > 0);
    }

    private static Mode decodeAsciiSegment(BitSource bitSource, StringBuilder stringBuilder, StringBuilder stringBuilder2) throws FormatException {
        int n = 0;
        do {
            int n2;
            block15 : {
                block20 : {
                    int n3;
                    block19 : {
                        block18 : {
                            block17 : {
                                block16 : {
                                    block14 : {
                                        if ((n3 = bitSource.readBits(8)) == 0) {
                                            throw FormatException.getFormatInstance();
                                        }
                                        if (n3 <= 128) {
                                            n2 = n3;
                                            if (n != 0) {
                                                n2 = n3 + 128;
                                            }
                                            stringBuilder.append((char)(n2 - 1));
                                            return Mode.ASCII_ENCODE;
                                        }
                                        if (n3 == 129) {
                                            return Mode.PAD_ENCODE;
                                        }
                                        if (n3 > 229) break block14;
                                        n2 = n3 - 130;
                                        if (n2 < 10) {
                                            stringBuilder.append('0');
                                        }
                                        stringBuilder.append(n2);
                                        n2 = n;
                                        break block15;
                                    }
                                    if (n3 == 230) {
                                        return Mode.C40_ENCODE;
                                    }
                                    if (n3 == 231) {
                                        return Mode.BASE256_ENCODE;
                                    }
                                    if (n3 != 232) break block16;
                                    stringBuilder.append('\u001d');
                                    n2 = n;
                                    break block15;
                                }
                                n2 = n;
                                if (n3 == 233) break block15;
                                n2 = n;
                                if (n3 == 234) break block15;
                                if (n3 != 235) break block17;
                                n2 = 1;
                                break block15;
                            }
                            if (n3 != 236) break block18;
                            stringBuilder.append("[)>\u001e05\u001d");
                            stringBuilder2.insert(0, "\u001e\u0004");
                            n2 = n;
                            break block15;
                        }
                        if (n3 != 237) break block19;
                        stringBuilder.append("[)>\u001e06\u001d");
                        stringBuilder2.insert(0, "\u001e\u0004");
                        n2 = n;
                        break block15;
                    }
                    if (n3 == 238) {
                        return Mode.ANSIX12_ENCODE;
                    }
                    if (n3 == 239) {
                        return Mode.TEXT_ENCODE;
                    }
                    if (n3 == 240) {
                        return Mode.EDIFACT_ENCODE;
                    }
                    n2 = n;
                    if (n3 == 241) break block15;
                    n2 = n;
                    if (n3 < 242) break block15;
                    if (n3 != 254) break block20;
                    n2 = n;
                    if (bitSource.available() == 0) break block15;
                }
                throw FormatException.getFormatInstance();
            }
            n = n2;
        } while (bitSource.available() > 0);
        return Mode.ASCII_ENCODE;
    }

    private static void decodeBase256Segment(BitSource bitSource, StringBuilder stringBuilder, Collection<byte[]> collection) throws FormatException {
        int n = 1 + bitSource.getByteOffset();
        int n2 = bitSource.readBits(8);
        int n3 = n + 1;
        if ((n = DecodedBitStreamParser.unrandomize255State(n2, n)) == 0) {
            n = bitSource.available() / 8;
        } else if (n >= 250) {
            n = DecodedBitStreamParser.unrandomize255State(bitSource.readBits(8), n3) + 250 * (n - 249);
            ++n3;
        }
        if (n < 0) {
            throw FormatException.getFormatInstance();
        }
        byte[] arrby = new byte[n];
        n2 = 0;
        while (n2 < n) {
            if (bitSource.available() < 8) {
                throw FormatException.getFormatInstance();
            }
            arrby[n2] = (byte)DecodedBitStreamParser.unrandomize255State(bitSource.readBits(8), n3);
            ++n2;
            ++n3;
        }
        collection.add(arrby);
        try {
            stringBuilder.append(new String(arrby, "ISO8859_1"));
            return;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            stringBuilder = new StringBuilder("Platform does not support required encoding: ");
            stringBuilder.append(unsupportedEncodingException);
            throw new IllegalStateException(stringBuilder.toString());
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private static void decodeC40Segment(BitSource var0, StringBuilder var1_1) throws FormatException {
        var7_2 = new int[3];
        var4_4 = var3_3 = 0;
        do {
            if (var0.available() == 8) {
                return;
            }
            var5_6 = var0.readBits(8);
            if (var5_6 == 254) {
                return;
            }
            DecodedBitStreamParser.parseTwoBytes(var5_6, var0.readBits(8), var7_2);
            block7 : for (var5_6 = 0; var5_6 < 3; ++var5_6) {
                var6_7 = var7_2[var5_6];
                switch (var4_4) {
                    default: {
                        throw FormatException.getFormatInstance();
                    }
                    case 3: {
                        if (var3_3 == 0) ** GOTO lbl19
                        var1_1.append((char)(var6_7 + 224));
                        ** GOTO lbl39
lbl19: // 1 sources:
                        var1_1.append((char)(var6_7 + 96));
                        ** GOTO lbl42
                    }
                    case 2: {
                        if (var6_7 < DecodedBitStreamParser.C40_SHIFT2_SET_CHARS.length) {
                            var2_5 = DecodedBitStreamParser.C40_SHIFT2_SET_CHARS[var6_7];
                            if (var3_3 != 0) {
                                var1_1.append((char)(var2_5 + 128));
                                var3_3 = 0;
                            } else {
                                var1_1.append(var2_5);
                            }
                        } else if (var6_7 == 27) {
                            var1_1.append('\u001d');
                        } else {
                            if (var6_7 != 30) throw FormatException.getFormatInstance();
                            var3_3 = 1;
                        }
                        ** GOTO lbl42
                    }
                    case 1: {
                        if (var3_3 != 0) {
                            var1_1.append((char)(var6_7 + 128));
lbl39: // 2 sources:
                            var3_3 = 0;
                        } else {
                            var1_1.append((char)var6_7);
                        }
lbl42: // 7 sources:
                        var4_4 = 0;
                        continue block7;
                    }
                    case 0: 
                }
                if (var6_7 < 3) {
                    var4_4 = var6_7 + 1;
                    continue;
                }
                if (var6_7 >= DecodedBitStreamParser.C40_BASIC_SET_CHARS.length) throw FormatException.getFormatInstance();
                var2_5 = DecodedBitStreamParser.C40_BASIC_SET_CHARS[var6_7];
                if (var3_3 != 0) {
                    var1_1.append((char)(var2_5 + 128));
                    var3_3 = 0;
                    continue;
                }
                var1_1.append(var2_5);
            }
        } while (var0.available() > 0);
    }

    private static void decodeEdifactSegment(BitSource bitSource, StringBuilder stringBuilder) {
        do {
            if (bitSource.available() <= 16) {
                return;
            }
            for (int i = 0; i < 4; ++i) {
                int n = bitSource.readBits(6);
                if (n == 31) {
                    i = 8 - bitSource.getBitOffset();
                    if (i != 8) {
                        bitSource.readBits(i);
                    }
                    return;
                }
                int n2 = n;
                if ((n & 32) == 0) {
                    n2 = n | 64;
                }
                stringBuilder.append((char)n2);
            }
        } while (bitSource.available() > 0);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private static void decodeTextSegment(BitSource var0, StringBuilder var1_1) throws FormatException {
        var7_2 = new int[3];
        var4_4 = var3_3 = 0;
        do {
            if (var0.available() == 8) {
                return;
            }
            var5_6 = var0.readBits(8);
            if (var5_6 == 254) {
                return;
            }
            DecodedBitStreamParser.parseTwoBytes(var5_6, var0.readBits(8), var7_2);
            block7 : for (var5_6 = 0; var5_6 < 3; ++var5_6) {
                var6_7 = var7_2[var5_6];
                switch (var4_4) {
                    default: {
                        throw FormatException.getFormatInstance();
                    }
                    case 3: {
                        if (var6_7 >= DecodedBitStreamParser.TEXT_SHIFT3_SET_CHARS.length) throw FormatException.getFormatInstance();
                        var2_5 = DecodedBitStreamParser.TEXT_SHIFT3_SET_CHARS[var6_7];
                        if (var3_3 == 0) ** GOTO lbl21
                        var1_1.append((char)(var2_5 + 128));
                        ** GOTO lbl41
lbl21: // 1 sources:
                        var1_1.append(var2_5);
                        ** GOTO lbl44
                    }
                    case 2: {
                        if (var6_7 < DecodedBitStreamParser.TEXT_SHIFT2_SET_CHARS.length) {
                            var2_5 = DecodedBitStreamParser.TEXT_SHIFT2_SET_CHARS[var6_7];
                            if (var3_3 != 0) {
                                var1_1.append((char)(var2_5 + 128));
                                var3_3 = 0;
                            } else {
                                var1_1.append(var2_5);
                            }
                        } else if (var6_7 == 27) {
                            var1_1.append('\u001d');
                        } else {
                            if (var6_7 != 30) throw FormatException.getFormatInstance();
                            var3_3 = 1;
                        }
                        ** GOTO lbl44
                    }
                    case 1: {
                        if (var3_3 != 0) {
                            var1_1.append((char)(var6_7 + 128));
lbl41: // 2 sources:
                            var3_3 = 0;
                        } else {
                            var1_1.append((char)var6_7);
                        }
lbl44: // 7 sources:
                        var4_4 = 0;
                        continue block7;
                    }
                    case 0: 
                }
                if (var6_7 < 3) {
                    var4_4 = var6_7 + 1;
                    continue;
                }
                if (var6_7 >= DecodedBitStreamParser.TEXT_BASIC_SET_CHARS.length) throw FormatException.getFormatInstance();
                var2_5 = DecodedBitStreamParser.TEXT_BASIC_SET_CHARS[var6_7];
                if (var3_3 != 0) {
                    var1_1.append((char)(var2_5 + 128));
                    var3_3 = 0;
                    continue;
                }
                var1_1.append(var2_5);
            }
        } while (var0.available() > 0);
    }

    private static void parseTwoBytes(int n, int n2, int[] arrn) {
        n = (n << 8) + n2 - 1;
        arrn[0] = n2 = n / 1600;
        arrn[1] = n2 = (n -= n2 * 1600) / 40;
        arrn[2] = n - n2 * 40;
    }

    private static int unrandomize255State(int n, int n2) {
        if ((n -= n2 * 149 % 255 + 1) >= 0) {
            return n;
        }
        return n + 256;
    }

    private static enum Mode {
        PAD_ENCODE,
        ASCII_ENCODE,
        C40_ENCODE,
        TEXT_ENCODE,
        ANSIX12_ENCODE,
        EDIFACT_ENCODE,
        BASE256_ENCODE;
        

        private Mode() {
        }
    }

}
