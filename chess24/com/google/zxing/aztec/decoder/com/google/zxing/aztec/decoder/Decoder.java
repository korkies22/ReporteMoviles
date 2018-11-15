/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.aztec.decoder;

import com.google.zxing.FormatException;
import com.google.zxing.aztec.AztecDetectorResult;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.common.reedsolomon.ReedSolomonDecoder;
import com.google.zxing.common.reedsolomon.ReedSolomonException;
import java.util.Arrays;
import java.util.List;

public final class Decoder {
    private static final String[] DIGIT_TABLE;
    private static final String[] LOWER_TABLE;
    private static final String[] MIXED_TABLE;
    private static final String[] PUNCT_TABLE;
    private static final String[] UPPER_TABLE;
    private AztecDetectorResult ddata;

    static {
        UPPER_TABLE = new String[]{"CTRL_PS", " ", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "CTRL_LL", "CTRL_ML", "CTRL_DL", "CTRL_BS"};
        LOWER_TABLE = new String[]{"CTRL_PS", " ", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "CTRL_US", "CTRL_ML", "CTRL_DL", "CTRL_BS"};
        MIXED_TABLE = new String[]{"CTRL_PS", " ", "\u0001", "\u0002", "\u0003", "\u0004", "\u0005", "\u0006", "\u0007", "\b", "\t", "\n", "\u000b", "\f", "\r", "\u001b", "\u001c", "\u001d", "\u001e", "\u001f", "@", "\\", "^", "_", "`", "|", "~", "", "CTRL_LL", "CTRL_UL", "CTRL_PL", "CTRL_BS"};
        PUNCT_TABLE = new String[]{"", "\r", "\r\n", ". ", ", ", ": ", "!", "\"", "#", "$", "%", "&", "'", "(", ")", "*", "+", ",", "-", ".", "/", ":", ";", "<", "=", ">", "?", "[", "]", "{", "}", "CTRL_UL"};
        DIGIT_TABLE = new String[]{"CTRL_PS", " ", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", ",", ".", "CTRL_UL", "CTRL_US"};
    }

    static byte[] convertBoolArrayToByteArray(boolean[] arrbl) {
        byte[] arrby = new byte[(arrbl.length + 7) / 8];
        for (int i = 0; i < arrby.length; ++i) {
            arrby[i] = Decoder.readByte(arrbl, i << 3);
        }
        return arrby;
    }

    private boolean[] correctBits(boolean[] arrbl) throws FormatException {
        GenericGF genericGF;
        int n;
        int n2;
        int n3 = this.ddata.getNbLayers();
        int n4 = 8;
        if (n3 <= 2) {
            n4 = 6;
            genericGF = GenericGF.AZTEC_DATA_6;
        } else if (this.ddata.getNbLayers() <= 8) {
            genericGF = GenericGF.AZTEC_DATA_8;
        } else if (this.ddata.getNbLayers() <= 22) {
            n4 = 10;
            genericGF = GenericGF.AZTEC_DATA_10;
        } else {
            n4 = 12;
            genericGF = GenericGF.AZTEC_DATA_12;
        }
        int n5 = this.ddata.getNbDatablocks();
        int n6 = arrbl.length / n4;
        if (n6 < n5) {
            throw FormatException.getFormatInstance();
        }
        n3 = arrbl.length;
        int[] arrn = new int[n6];
        n3 %= n4;
        int n7 = 0;
        while (n7 < n6) {
            arrn[n7] = Decoder.readCode(arrbl, n3, n4);
            ++n7;
            n3 += n4;
        }
        try {
            new ReedSolomonDecoder(genericGF).decode(arrn, n6 - n5);
            n2 = (1 << n4) - 1;
            n7 = n3 = 0;
        }
        catch (ReedSolomonException reedSolomonException) {
            throw FormatException.getFormatInstance(reedSolomonException);
        }
        while (n3 < n5) {
            block20 : {
                block22 : {
                    block21 : {
                        n = arrn[n3];
                        if (n == 0 || n == n2) break block20;
                        if (n == 1) break block21;
                        n6 = n7;
                        if (n != n2 - 1) break block22;
                    }
                    n6 = n7 + 1;
                }
                ++n3;
                n7 = n6;
                continue;
            }
            throw FormatException.getFormatInstance();
        }
        arrbl = new boolean[n5 * n4 - n7];
        n3 = n6 = 0;
        while (n6 < n5) {
            boolean bl;
            int n8 = arrn[n6];
            if (n8 != 1 && n8 != n2 - 1) {
                n = n4 - 1;
                do {
                    n7 = n3;
                    if (n >= 0) {
                        bl = (1 << n & n8) != 0;
                        arrbl[n3] = bl;
                        --n;
                        ++n3;
                        continue;
                    }
                    break;
                } while (true);
            } else {
                bl = n8 > 1;
                Arrays.fill(arrbl, n3, n3 + n4 - 1, bl);
                n7 = n3 + (n4 - 1);
            }
            ++n6;
            n3 = n7;
        }
        return arrbl;
    }

    private boolean[] extractBits(BitMatrix bitMatrix) {
        int n;
        int n2;
        int n3;
        boolean bl = this.ddata.isCompact();
        int n4 = this.ddata.getNbLayers();
        int n5 = bl ? 11 : 14;
        int n6 = n5 + (n4 << 2);
        int[] arrn = new int[n6];
        boolean[] arrbl = new boolean[Decoder.totalBitsInLayer(n4, bl)];
        if (bl) {
            n5 = 0;
            while (n5 < arrn.length) {
                arrn[n5] = n5++;
            }
        } else {
            n3 = n6 / 2;
            n = (n6 + 1 + (n3 - 1) / 15 * 2) / 2;
            for (n5 = 0; n5 < n3; ++n5) {
                n2 = n5 / 15 + n5;
                arrn[n3 - n5 - 1] = n - n2 - 1;
                arrn[n3 + n5] = n2 + n + 1;
            }
        }
        n3 = 0;
        for (n5 = 0; n5 < n4; ++n5) {
            n = bl ? 9 : 12;
            int n7 = (n4 - n5 << 2) + n;
            int n8 = n5 << 1;
            int n9 = n6 - 1 - n8;
            for (n = 0; n < n7; ++n) {
                int n10 = n << 1;
                for (n2 = 0; n2 < 2; ++n2) {
                    int n11 = n8 + n2;
                    int n12 = arrn[n11];
                    int n13 = n8 + n;
                    arrbl[n3 + n10 + n2] = bitMatrix.get(n12, arrn[n13]);
                    n12 = arrn[n13];
                    n13 = n9 - n2;
                    arrbl[2 * n7 + n3 + n10 + n2] = bitMatrix.get(n12, arrn[n13]);
                    n12 = arrn[n13];
                    n13 = n9 - n;
                    arrbl[4 * n7 + n3 + n10 + n2] = bitMatrix.get(n12, arrn[n13]);
                    arrbl[n7 * 6 + n3 + n10 + n2] = bitMatrix.get(arrn[n13], arrn[n11]);
                }
            }
            n3 += n7 << 3;
        }
        return arrbl;
    }

    private static String getCharacter(Table table, int n) {
        switch (.$SwitchMap$com$google$zxing$aztec$decoder$Decoder$Table[table.ordinal()]) {
            default: {
                throw new IllegalStateException("Bad table");
            }
            case 5: {
                return DIGIT_TABLE[n];
            }
            case 4: {
                return PUNCT_TABLE[n];
            }
            case 3: {
                return MIXED_TABLE[n];
            }
            case 2: {
                return LOWER_TABLE[n];
            }
            case 1: 
        }
        return UPPER_TABLE[n];
    }

    /*
     * Enabled aggressive block sorting
     */
    private static String getEncodedData(boolean[] arrbl) {
        int n = arrbl.length;
        Table table = Table.UPPER;
        Table table2 = Table.UPPER;
        StringBuilder stringBuilder = new StringBuilder(20);
        int n2 = 0;
        while (n2 < n) {
            block9 : {
                int n3;
                int n4;
                block8 : {
                    void var6_4;
                    block7 : {
                        int n5;
                        if (var6_4 != Table.BINARY) break block7;
                        if (n - n2 < 5) break;
                        n4 = Decoder.readCode(arrbl, n2, 5);
                        n2 = n5 = n2 + 5;
                        n3 = n4;
                        if (n4 == 0) {
                            if (n - n5 < 11) break;
                            n3 = Decoder.readCode(arrbl, n5, 11) + 31;
                            n2 = n5 + 11;
                        }
                        break block8;
                    }
                    n3 = var6_4 == Table.DIGIT ? 4 : 5;
                    if (n - n2 < n3) break;
                    n4 = Decoder.readCode(arrbl, n2, n3);
                    n2 += n3;
                    String string = Decoder.getCharacter((Table)var6_4, n4);
                    if (string.startsWith("CTRL_")) {
                        table = Decoder.getTable(string.charAt(5));
                        if (string.charAt(6) != 'L') {
                            Table table3 = table;
                            table = var6_4;
                            Table table4 = table3;
                            continue;
                        }
                    } else {
                        stringBuilder.append(string);
                    }
                    break block9;
                }
                for (n4 = 0; n4 < n3; n2 += 8, ++n4) {
                    if (n - n2 < 8) {
                        n2 = n;
                        break;
                    }
                    stringBuilder.append((char)Decoder.readCode(arrbl, n2, 8));
                }
            }
            Table table5 = table;
        }
        return stringBuilder.toString();
    }

    private static Table getTable(char c) {
        if (c != 'B') {
            if (c != 'D') {
                if (c != 'P') {
                    switch (c) {
                        default: {
                            return Table.UPPER;
                        }
                        case 'M': {
                            return Table.MIXED;
                        }
                        case 'L': 
                    }
                    return Table.LOWER;
                }
                return Table.PUNCT;
            }
            return Table.DIGIT;
        }
        return Table.BINARY;
    }

    public static String highLevelDecode(boolean[] arrbl) {
        return Decoder.getEncodedData(arrbl);
    }

    private static byte readByte(boolean[] arrbl, int n) {
        int n2 = arrbl.length - n;
        if (n2 >= 8) {
            return (byte)Decoder.readCode(arrbl, n, 8);
        }
        return (byte)(Decoder.readCode(arrbl, n, n2) << 8 - n2);
    }

    private static int readCode(boolean[] arrbl, int n, int n2) {
        int n3 = 0;
        for (int i = n; i < n + n2; ++i) {
            int n4;
            n3 = n4 = n3 << 1;
            if (!arrbl[i]) continue;
            n3 = n4 | 1;
        }
        return n3;
    }

    private static int totalBitsInLayer(int n, boolean bl) {
        int n2 = bl ? 88 : 112;
        return (n2 + (n << 4)) * n;
    }

    public DecoderResult decode(AztecDetectorResult arrbl) throws FormatException {
        this.ddata = arrbl;
        arrbl = this.correctBits(this.extractBits(arrbl.getBits()));
        DecoderResult decoderResult = new DecoderResult(Decoder.convertBoolArrayToByteArray(arrbl), Decoder.getEncodedData(arrbl), null, null);
        decoderResult.setNumBits(arrbl.length);
        return decoderResult;
    }

    private static enum Table {
        UPPER,
        LOWER,
        MIXED,
        DIGIT,
        PUNCT,
        BINARY;
        

        private Table() {
        }
    }

}
