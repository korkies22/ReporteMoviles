/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.qrcode.decoder;

import com.google.zxing.FormatException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.decoder.FormatInformation;

public final class Version {
    private static final Version[] VERSIONS;
    private static final int[] VERSION_DECODE_INFO;
    private final int[] alignmentPatternCenters;
    private final ECBlocks[] ecBlocks;
    private final int totalCodewords;
    private final int versionNumber;

    static {
        VERSION_DECODE_INFO = new int[]{31892, 34236, 39577, 42195, 48118, 51042, 55367, 58893, 63784, 68472, 70749, 76311, 79154, 84390, 87683, 92361, 96236, 102084, 102881, 110507, 110734, 117786, 119615, 126325, 127568, 133589, 136944, 141498, 145311, 150283, 152622, 158308, 161089, 167017};
        VERSIONS = Version.buildVersions();
    }

    private /* varargs */ Version(int n, int[] arrobject, ECBlocks ... object) {
        this.versionNumber = n;
        this.alignmentPatternCenters = arrobject;
        this.ecBlocks = object;
        int n2 = object[0].getECCodewordsPerBlock();
        arrobject = object[0].getECBlocks();
        int n3 = arrobject.length;
        int n4 = 0;
        for (n = 0; n < n3; ++n) {
            object = arrobject[n];
            n4 += object.getCount() * (object.getDataCodewords() + n2);
        }
        this.totalCodewords = n4;
    }

    private static Version[] buildVersions() {
        Object object = new ECBlocks(7, new ECB(1, 19));
        Object object2 = new ECBlocks(10, new ECB(1, 16));
        Object object3 = new ECBlocks(13, new ECB(1, 13));
        Object object4 = new ECBlocks(17, new ECB(1, 9));
        object = new Version(1, new int[0], new ECBlocks[]{object, object2, object3, object4});
        object2 = new ECBlocks(10, new ECB(1, 34));
        object3 = new ECBlocks(16, new ECB(1, 28));
        object4 = new ECBlocks(22, new ECB(1, 22));
        Object object5 = new ECBlocks(28, new ECB(1, 16));
        object2 = new Version(2, new int[]{6, 18}, new ECBlocks[]{object2, object3, object4, object5});
        object3 = new ECBlocks(15, new ECB(1, 55));
        object4 = new ECBlocks(26, new ECB(1, 44));
        object5 = new ECBlocks(18, new ECB(2, 17));
        Object object6 = new ECBlocks(22, new ECB(2, 13));
        object3 = new Version(3, new int[]{6, 22}, new ECBlocks[]{object3, object4, object5, object6});
        object4 = new ECBlocks(20, new ECB(1, 80));
        object5 = new ECBlocks(18, new ECB(2, 32));
        object6 = new ECBlocks(26, new ECB(2, 24));
        Object object7 = new ECBlocks(16, new ECB(4, 9));
        object4 = new Version(4, new int[]{6, 26}, new ECBlocks[]{object4, object5, object6, object7});
        object5 = new ECBlocks(26, new ECB(1, 108));
        object6 = new ECBlocks(24, new ECB(2, 43));
        object7 = new ECBlocks(18, new ECB(2, 15), new ECB(2, 16));
        Object object8 = new ECBlocks(22, new ECB(2, 11), new ECB(2, 12));
        object5 = new Version(5, new int[]{6, 30}, new ECBlocks[]{object5, object6, object7, object8});
        object6 = new ECBlocks(18, new ECB(2, 68));
        object7 = new ECBlocks(16, new ECB(4, 27));
        object8 = new ECBlocks(24, new ECB(4, 19));
        Object object9 = new ECBlocks(28, new ECB(4, 15));
        object6 = new Version(6, new int[]{6, 34}, new ECBlocks[]{object6, object7, object8, object9});
        object7 = new ECBlocks(20, new ECB(2, 78));
        object8 = new ECBlocks(18, new ECB(4, 31));
        object9 = new ECBlocks(18, new ECB(2, 14), new ECB(4, 15));
        Object object10 = new ECBlocks(26, new ECB(4, 13), new ECB(1, 14));
        object7 = new Version(7, new int[]{6, 22, 38}, new ECBlocks[]{object7, object8, object9, object10});
        object8 = new ECBlocks(24, new ECB(2, 97));
        object9 = new ECBlocks(22, new ECB(2, 38), new ECB(2, 39));
        object10 = new ECBlocks(22, new ECB(4, 18), new ECB(2, 19));
        Object object11 = new ECBlocks(26, new ECB(4, 14), new ECB(2, 15));
        object8 = new Version(8, new int[]{6, 24, 42}, new ECBlocks[]{object8, object9, object10, object11});
        object9 = new ECBlocks(30, new ECB(2, 116));
        object10 = new ECBlocks(22, new ECB(3, 36), new ECB(2, 37));
        object11 = new ECBlocks(20, new ECB(4, 16), new ECB(4, 17));
        Object object12 = new ECBlocks(24, new ECB(4, 12), new ECB(4, 13));
        object9 = new Version(9, new int[]{6, 26, 46}, new ECBlocks[]{object9, object10, object11, object12});
        object10 = new ECBlocks(18, new ECB(2, 68), new ECB(2, 69));
        object11 = new ECBlocks(26, new ECB(4, 43), new ECB(1, 44));
        object12 = new ECBlocks(24, new ECB(6, 19), new ECB(2, 20));
        Object object13 = new ECBlocks(28, new ECB(6, 15), new ECB(2, 16));
        object10 = new Version(10, new int[]{6, 28, 50}, new ECBlocks[]{object10, object11, object12, object13});
        object11 = new ECBlocks(20, new ECB(4, 81));
        object12 = new ECBlocks(30, new ECB(1, 50), new ECB(4, 51));
        object13 = new ECBlocks(28, new ECB(4, 22), new ECB(4, 23));
        Object object14 = new ECBlocks(24, new ECB(3, 12), new ECB(8, 13));
        object11 = new Version(11, new int[]{6, 30, 54}, new ECBlocks[]{object11, object12, object13, object14});
        object12 = new ECBlocks(24, new ECB(2, 92), new ECB(2, 93));
        object13 = new ECBlocks(22, new ECB(6, 36), new ECB(2, 37));
        object14 = new ECBlocks(26, new ECB(4, 20), new ECB(6, 21));
        Object object15 = new ECBlocks(28, new ECB(7, 14), new ECB(4, 15));
        object12 = new Version(12, new int[]{6, 32, 58}, new ECBlocks[]{object12, object13, object14, object15});
        object13 = new ECBlocks(26, new ECB(4, 107));
        object14 = new ECBlocks(22, new ECB(8, 37), new ECB(1, 38));
        object15 = new ECBlocks(24, new ECB(8, 20), new ECB(4, 21));
        Object object16 = new ECBlocks(22, new ECB(12, 11), new ECB(4, 12));
        object13 = new Version(13, new int[]{6, 34, 62}, new ECBlocks[]{object13, object14, object15, object16});
        object14 = new ECBlocks(30, new ECB(3, 115), new ECB(1, 116));
        object15 = new ECBlocks(24, new ECB(4, 40), new ECB(5, 41));
        object16 = new ECBlocks(20, new ECB(11, 16), new ECB(5, 17));
        Object object17 = new ECBlocks(24, new ECB(11, 12), new ECB(5, 13));
        object14 = new Version(14, new int[]{6, 26, 46, 66}, new ECBlocks[]{object14, object15, object16, object17});
        object15 = new ECBlocks(22, new ECB(5, 87), new ECB(1, 88));
        object16 = new ECBlocks(24, new ECB(5, 41), new ECB(5, 42));
        object17 = new ECBlocks(30, new ECB(5, 24), new ECB(7, 25));
        Object object18 = new ECBlocks(24, new ECB(11, 12), new ECB(7, 13));
        object15 = new Version(15, new int[]{6, 26, 48, 70}, new ECBlocks[]{object15, object16, object17, object18});
        object16 = new ECBlocks(24, new ECB(5, 98), new ECB(1, 99));
        object17 = new ECBlocks(28, new ECB(7, 45), new ECB(3, 46));
        object18 = new ECBlocks(24, new ECB(15, 19), new ECB(2, 20));
        Object object19 = new ECBlocks(30, new ECB(3, 15), new ECB(13, 16));
        object16 = new Version(16, new int[]{6, 26, 50, 74}, new ECBlocks[]{object16, object17, object18, object19});
        object17 = new ECBlocks(28, new ECB(1, 107), new ECB(5, 108));
        object18 = new ECBlocks(28, new ECB(10, 46), new ECB(1, 47));
        object19 = new ECBlocks(28, new ECB(1, 22), new ECB(15, 23));
        Object object20 = new ECBlocks(28, new ECB(2, 14), new ECB(17, 15));
        object17 = new Version(17, new int[]{6, 30, 54, 78}, new ECBlocks[]{object17, object18, object19, object20});
        object18 = new ECBlocks(30, new ECB(5, 120), new ECB(1, 121));
        object19 = new ECBlocks(26, new ECB(9, 43), new ECB(4, 44));
        object20 = new ECBlocks(28, new ECB(17, 22), new ECB(1, 23));
        Object object21 = new ECBlocks(28, new ECB(2, 14), new ECB(19, 15));
        object18 = new Version(18, new int[]{6, 30, 56, 82}, new ECBlocks[]{object18, object19, object20, object21});
        object19 = new ECBlocks(28, new ECB(3, 113), new ECB(4, 114));
        object20 = new ECBlocks(26, new ECB(3, 44), new ECB(11, 45));
        object21 = new ECBlocks(26, new ECB(17, 21), new ECB(4, 22));
        Object object22 = new ECBlocks(26, new ECB(9, 13), new ECB(16, 14));
        object19 = new Version(19, new int[]{6, 30, 58, 86}, new ECBlocks[]{object19, object20, object21, object22});
        object20 = new ECBlocks(28, new ECB(3, 107), new ECB(5, 108));
        object21 = new ECBlocks(26, new ECB(3, 41), new ECB(13, 42));
        object22 = new ECBlocks(30, new ECB(15, 24), new ECB(5, 25));
        Object object23 = new ECBlocks(28, new ECB(15, 15), new ECB(10, 16));
        object20 = new Version(20, new int[]{6, 34, 62, 90}, new ECBlocks[]{object20, object21, object22, object23});
        object21 = new ECBlocks(28, new ECB(4, 116), new ECB(4, 117));
        object22 = new ECBlocks(26, new ECB(17, 42));
        object23 = new ECBlocks(28, new ECB(17, 22), new ECB(6, 23));
        Object object24 = new ECBlocks(30, new ECB(19, 16), new ECB(6, 17));
        object21 = new Version(21, new int[]{6, 28, 50, 72, 94}, new ECBlocks[]{object21, object22, object23, object24});
        object22 = new ECBlocks(28, new ECB(2, 111), new ECB(7, 112));
        object23 = new ECBlocks(28, new ECB(17, 46));
        object24 = new ECBlocks(30, new ECB(7, 24), new ECB(16, 25));
        Object object25 = new ECBlocks(24, new ECB(34, 13));
        object22 = new Version(22, new int[]{6, 26, 50, 74, 98}, new ECBlocks[]{object22, object23, object24, object25});
        object23 = new ECBlocks(30, new ECB(4, 121), new ECB(5, 122));
        object24 = new ECBlocks(28, new ECB(4, 47), new ECB(14, 48));
        object25 = new ECBlocks(30, new ECB(11, 24), new ECB(14, 25));
        Object object26 = new ECBlocks(30, new ECB(16, 15), new ECB(14, 16));
        object23 = new Version(23, new int[]{6, 30, 54, 78, 102}, new ECBlocks[]{object23, object24, object25, object26});
        object24 = new ECBlocks(30, new ECB(6, 117), new ECB(4, 118));
        object25 = new ECBlocks(28, new ECB(6, 45), new ECB(14, 46));
        object26 = new ECBlocks(30, new ECB(11, 24), new ECB(16, 25));
        Object object27 = new ECBlocks(30, new ECB(30, 16), new ECB(2, 17));
        object24 = new Version(24, new int[]{6, 28, 54, 80, 106}, new ECBlocks[]{object24, object25, object26, object27});
        object25 = new ECBlocks(26, new ECB(8, 106), new ECB(4, 107));
        object26 = new ECBlocks(28, new ECB(8, 47), new ECB(13, 48));
        object27 = new ECBlocks(30, new ECB(7, 24), new ECB(22, 25));
        Object object28 = new ECBlocks(30, new ECB(22, 15), new ECB(13, 16));
        object25 = new Version(25, new int[]{6, 32, 58, 84, 110}, new ECBlocks[]{object25, object26, object27, object28});
        object26 = new ECBlocks(28, new ECB(10, 114), new ECB(2, 115));
        object27 = new ECBlocks(28, new ECB(19, 46), new ECB(4, 47));
        object28 = new ECBlocks(28, new ECB(28, 22), new ECB(6, 23));
        Object object29 = new ECBlocks(30, new ECB(33, 16), new ECB(4, 17));
        object26 = new Version(26, new int[]{6, 30, 58, 86, 114}, new ECBlocks[]{object26, object27, object28, object29});
        object27 = new ECBlocks(30, new ECB(8, 122), new ECB(4, 123));
        object28 = new ECBlocks(28, new ECB(22, 45), new ECB(3, 46));
        object29 = new ECBlocks(30, new ECB(8, 23), new ECB(26, 24));
        Object object30 = new ECBlocks(30, new ECB(12, 15), new ECB(28, 16));
        object27 = new Version(27, new int[]{6, 34, 62, 90, 118}, new ECBlocks[]{object27, object28, object29, object30});
        object28 = new ECBlocks(30, new ECB(3, 117), new ECB(10, 118));
        object29 = new ECBlocks(28, new ECB(3, 45), new ECB(23, 46));
        object30 = new ECBlocks(30, new ECB(4, 24), new ECB(31, 25));
        Object object31 = new ECBlocks(30, new ECB(11, 15), new ECB(31, 16));
        object28 = new Version(28, new int[]{6, 26, 50, 74, 98, 122}, new ECBlocks[]{object28, object29, object30, object31});
        object29 = new ECBlocks(30, new ECB(7, 116), new ECB(7, 117));
        object30 = new ECBlocks(28, new ECB(21, 45), new ECB(7, 46));
        object31 = new ECBlocks(30, new ECB(1, 23), new ECB(37, 24));
        Object object32 = new ECBlocks(30, new ECB(19, 15), new ECB(26, 16));
        object29 = new Version(29, new int[]{6, 30, 54, 78, 102, 126}, new ECBlocks[]{object29, object30, object31, object32});
        object30 = new ECBlocks(30, new ECB(5, 115), new ECB(10, 116));
        object31 = new ECBlocks(28, new ECB(19, 47), new ECB(10, 48));
        object32 = new ECBlocks(30, new ECB(15, 24), new ECB(25, 25));
        Object object33 = new ECBlocks(30, new ECB(23, 15), new ECB(25, 16));
        object30 = new Version(30, new int[]{6, 26, 52, 78, 104, 130}, new ECBlocks[]{object30, object31, object32, object33});
        object31 = new ECBlocks(30, new ECB(13, 115), new ECB(3, 116));
        object32 = new ECBlocks(28, new ECB(2, 46), new ECB(29, 47));
        object33 = new ECBlocks(30, new ECB(42, 24), new ECB(1, 25));
        Object object34 = new ECBlocks(30, new ECB(23, 15), new ECB(28, 16));
        object31 = new Version(31, new int[]{6, 30, 56, 82, 108, 134}, new ECBlocks[]{object31, object32, object33, object34});
        object32 = new ECBlocks(30, new ECB(17, 115));
        object33 = new ECBlocks(28, new ECB(10, 46), new ECB(23, 47));
        object34 = new ECBlocks(30, new ECB(10, 24), new ECB(35, 25));
        Object object35 = new ECBlocks(30, new ECB(19, 15), new ECB(35, 16));
        object32 = new Version(32, new int[]{6, 34, 60, 86, 112, 138}, new ECBlocks[]{object32, object33, object34, object35});
        object33 = new ECBlocks(30, new ECB(17, 115), new ECB(1, 116));
        object34 = new ECBlocks(28, new ECB(14, 46), new ECB(21, 47));
        object35 = new ECBlocks(30, new ECB(29, 24), new ECB(19, 25));
        Object object36 = new ECBlocks(30, new ECB(11, 15), new ECB(46, 16));
        object33 = new Version(33, new int[]{6, 30, 58, 86, 114, 142}, new ECBlocks[]{object33, object34, object35, object36});
        object34 = new ECBlocks(30, new ECB(13, 115), new ECB(6, 116));
        object35 = new ECBlocks(28, new ECB(14, 46), new ECB(23, 47));
        object36 = new ECBlocks(30, new ECB(44, 24), new ECB(7, 25));
        Object object37 = new ECBlocks(30, new ECB(59, 16), new ECB(1, 17));
        object34 = new Version(34, new int[]{6, 34, 62, 90, 118, 146}, new ECBlocks[]{object34, object35, object36, object37});
        object35 = new ECBlocks(30, new ECB(12, 121), new ECB(7, 122));
        object36 = new ECBlocks(28, new ECB(12, 47), new ECB(26, 48));
        object37 = new ECBlocks(30, new ECB(39, 24), new ECB(14, 25));
        Object object38 = new ECBlocks(30, new ECB(22, 15), new ECB(41, 16));
        object35 = new Version(35, new int[]{6, 30, 54, 78, 102, 126, 150}, new ECBlocks[]{object35, object36, object37, object38});
        object36 = new ECBlocks(30, new ECB(6, 121), new ECB(14, 122));
        object37 = new ECBlocks(28, new ECB(6, 47), new ECB(34, 48));
        object38 = new ECBlocks(30, new ECB(46, 24), new ECB(10, 25));
        Object object39 = new ECBlocks(30, new ECB(2, 15), new ECB(64, 16));
        object36 = new Version(36, new int[]{6, 24, 50, 76, 102, 128, 154}, new ECBlocks[]{object36, object37, object38, object39});
        object37 = new ECBlocks(30, new ECB(17, 122), new ECB(4, 123));
        object38 = new ECBlocks(28, new ECB(29, 46), new ECB(14, 47));
        object39 = new ECBlocks(30, new ECB(49, 24), new ECB(10, 25));
        ECBlocks eCBlocks = new ECBlocks(30, new ECB(24, 15), new ECB(46, 16));
        object37 = new Version(37, new int[]{6, 28, 54, 80, 106, 132, 158}, new ECBlocks[]{object37, object38, object39, eCBlocks});
        object38 = new ECBlocks(30, new ECB(4, 122), new ECB(18, 123));
        object39 = new ECBlocks(28, new ECB(13, 46), new ECB(32, 47));
        eCBlocks = new ECBlocks(30, new ECB(48, 24), new ECB(14, 25));
        ECBlocks eCBlocks2 = new ECBlocks(30, new ECB(42, 15), new ECB(32, 16));
        object38 = new Version(38, new int[]{6, 32, 58, 84, 110, 136, 162}, new ECBlocks[]{object38, object39, eCBlocks, eCBlocks2});
        object39 = new ECBlocks(30, new ECB(20, 117), new ECB(4, 118));
        eCBlocks = new ECBlocks(28, new ECB(40, 47), new ECB(7, 48));
        eCBlocks2 = new ECBlocks(30, new ECB(43, 24), new ECB(22, 25));
        ECBlocks eCBlocks3 = new ECBlocks(30, new ECB(10, 15), new ECB(67, 16));
        object39 = new Version(39, new int[]{6, 26, 54, 82, 110, 138, 166}, new ECBlocks[]{object39, eCBlocks, eCBlocks2, eCBlocks3});
        eCBlocks = new ECBlocks(30, new ECB(19, 118), new ECB(6, 119));
        eCBlocks2 = new ECBlocks(28, new ECB(18, 47), new ECB(31, 48));
        eCBlocks3 = new ECBlocks(30, new ECB(34, 24), new ECB(34, 25));
        ECBlocks eCBlocks4 = new ECBlocks(30, new ECB(20, 15), new ECB(61, 16));
        return new Version[]{object, object2, object3, object4, object5, object6, object7, object8, object9, object10, object11, object12, object13, object14, object15, object16, object17, object18, object19, object20, object21, object22, object23, object24, object25, object26, object27, object28, object29, object30, object31, object32, object33, object34, object35, object36, object37, object38, object39, new Version(40, new int[]{6, 30, 58, 86, 114, 142, 170}, eCBlocks, eCBlocks2, eCBlocks3, eCBlocks4)};
    }

    static Version decodeVersionInformation(int n) {
        int n2 = Integer.MAX_VALUE;
        int n3 = 0;
        for (int i = 0; i < VERSION_DECODE_INFO.length; ++i) {
            int n4 = VERSION_DECODE_INFO[i];
            if (n4 == n) {
                return Version.getVersionForNumber(i + 7);
            }
            int n5 = FormatInformation.numBitsDiffering(n, n4);
            n4 = n2;
            if (n5 < n2) {
                n3 = i + 7;
                n4 = n5;
            }
            n2 = n4;
        }
        if (n2 <= 3) {
            return Version.getVersionForNumber(n3);
        }
        return null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static Version getProvisionalVersionForDimension(int n) throws FormatException {
        if (n % 4 != 1) {
            throw FormatException.getFormatInstance();
        }
        try {
            return Version.getVersionForNumber((n - 17) / 4);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw FormatException.getFormatInstance();
        }
    }

    public static Version getVersionForNumber(int n) {
        if (n > 0 && n <= 40) {
            return VERSIONS[n - 1];
        }
        throw new IllegalArgumentException();
    }

    BitMatrix buildFunctionPattern() {
        int n = this.getDimensionForVersion();
        BitMatrix bitMatrix = new BitMatrix(n);
        bitMatrix.setRegion(0, 0, 9, 9);
        int n2 = n - 8;
        bitMatrix.setRegion(n2, 0, 8, 9);
        bitMatrix.setRegion(0, n2, 9, 8);
        int n3 = this.alignmentPatternCenters.length;
        for (n2 = 0; n2 < n3; ++n2) {
            int n4 = this.alignmentPatternCenters[n2];
            for (int i = 0; i < n3; ++i) {
                if (n2 == 0 && (i == 0 || i == n3 - 1) || n2 == n3 - 1 && i == 0) continue;
                bitMatrix.setRegion(this.alignmentPatternCenters[i] - 2, n4 - 2, 5, 5);
            }
        }
        n2 = n - 17;
        bitMatrix.setRegion(6, 9, 1, n2);
        bitMatrix.setRegion(9, 6, n2, 1);
        if (this.versionNumber > 6) {
            n2 = n - 11;
            bitMatrix.setRegion(n2, 0, 3, 6);
            bitMatrix.setRegion(0, n2, 6, 3);
        }
        return bitMatrix;
    }

    public int[] getAlignmentPatternCenters() {
        return this.alignmentPatternCenters;
    }

    public int getDimensionForVersion() {
        return 17 + 4 * this.versionNumber;
    }

    public ECBlocks getECBlocksForLevel(ErrorCorrectionLevel errorCorrectionLevel) {
        return this.ecBlocks[errorCorrectionLevel.ordinal()];
    }

    public int getTotalCodewords() {
        return this.totalCodewords;
    }

    public int getVersionNumber() {
        return this.versionNumber;
    }

    public String toString() {
        return String.valueOf(this.versionNumber);
    }

    public static final class ECB {
        private final int count;
        private final int dataCodewords;

        ECB(int n, int n2) {
            this.count = n;
            this.dataCodewords = n2;
        }

        public int getCount() {
            return this.count;
        }

        public int getDataCodewords() {
            return this.dataCodewords;
        }
    }

    public static final class ECBlocks {
        private final ECB[] ecBlocks;
        private final int ecCodewordsPerBlock;

        /* varargs */ ECBlocks(int n, ECB ... arreCB) {
            this.ecCodewordsPerBlock = n;
            this.ecBlocks = arreCB;
        }

        public ECB[] getECBlocks() {
            return this.ecBlocks;
        }

        public int getECCodewordsPerBlock() {
            return this.ecCodewordsPerBlock;
        }

        public int getNumBlocks() {
            ECB[] arreCB = this.ecBlocks;
            int n = arreCB.length;
            int n2 = 0;
            for (int i = 0; i < n; ++i) {
                n2 += arreCB[i].getCount();
            }
            return n2;
        }

        public int getTotalECCodewords() {
            return this.ecCodewordsPerBlock * this.getNumBlocks();
        }
    }

}
