/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.oned;

import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.common.BitArray;
import com.google.zxing.oned.OneDReader;
import java.util.Map;

public final class Code128Reader
extends OneDReader {
    private static final int CODE_CODE_A = 101;
    private static final int CODE_CODE_B = 100;
    private static final int CODE_CODE_C = 99;
    private static final int CODE_FNC_1 = 102;
    private static final int CODE_FNC_2 = 97;
    private static final int CODE_FNC_3 = 96;
    private static final int CODE_FNC_4_A = 101;
    private static final int CODE_FNC_4_B = 100;
    static final int[][] CODE_PATTERNS;
    private static final int CODE_SHIFT = 98;
    private static final int CODE_START_A = 103;
    private static final int CODE_START_B = 104;
    private static final int CODE_START_C = 105;
    private static final int CODE_STOP = 106;
    private static final float MAX_AVG_VARIANCE = 0.25f;
    private static final float MAX_INDIVIDUAL_VARIANCE = 0.7f;

    static {
        int[] arrn = new int[]{2, 1, 2, 2, 2, 2};
        int[] arrn2 = new int[]{2, 2, 2, 1, 2, 2};
        int[] arrn3 = new int[]{1, 2, 1, 2, 2, 3};
        int[] arrn4 = new int[]{1, 2, 1, 3, 2, 2};
        int[] arrn5 = new int[]{1, 3, 1, 2, 2, 2};
        int[] arrn6 = new int[]{1, 2, 2, 2, 1, 3};
        int[] arrn7 = new int[]{2, 2, 1, 3, 1, 2};
        int[] arrn8 = new int[]{2, 3, 1, 2, 1, 2};
        int[] arrn9 = new int[]{1, 1, 2, 2, 3, 2};
        int[] arrn10 = new int[]{1, 2, 2, 1, 3, 2};
        int[] arrn11 = new int[]{1, 1, 3, 2, 2, 2};
        int[] arrn12 = new int[]{1, 2, 3, 1, 2, 2};
        int[] arrn13 = new int[]{3, 1, 1, 2, 2, 2};
        int[] arrn14 = new int[]{3, 1, 2, 2, 1, 2};
        int[] arrn15 = new int[]{3, 2, 2, 1, 1, 2};
        int[] arrn16 = new int[]{2, 1, 2, 1, 2, 3};
        int[] arrn17 = new int[]{2, 3, 2, 1, 2, 1};
        int[] arrn18 = new int[]{1, 1, 1, 3, 2, 3};
        int[] arrn19 = new int[]{2, 1, 1, 3, 1, 3};
        int[] arrn20 = new int[]{2, 3, 1, 1, 1, 3};
        int[] arrn21 = new int[]{2, 3, 1, 3, 1, 1};
        int[] arrn22 = new int[]{1, 3, 2, 1, 3, 1};
        int[] arrn23 = new int[]{1, 1, 3, 3, 2, 1};
        int[] arrn24 = new int[]{1, 3, 3, 1, 2, 1};
        int[] arrn25 = new int[]{2, 3, 1, 1, 3, 1};
        int[] arrn26 = new int[]{2, 1, 3, 1, 1, 3};
        int[] arrn27 = new int[]{2, 1, 3, 3, 1, 1};
        int[] arrn28 = new int[]{3, 1, 1, 3, 2, 1};
        int[] arrn29 = new int[]{3, 3, 2, 1, 1, 1};
        int[] arrn30 = new int[]{3, 1, 4, 1, 1, 1};
        int[] arrn31 = new int[]{2, 2, 1, 4, 1, 1};
        int[] arrn32 = new int[]{1, 1, 1, 4, 2, 2};
        int[] arrn33 = new int[]{1, 2, 1, 4, 2, 1};
        int[] arrn34 = new int[]{1, 4, 1, 1, 2, 2};
        int[] arrn35 = new int[]{1, 1, 2, 2, 1, 4};
        int[] arrn36 = new int[]{1, 1, 2, 4, 1, 2};
        int[] arrn37 = new int[]{1, 2, 2, 1, 1, 4};
        int[] arrn38 = new int[]{1, 2, 2, 4, 1, 1};
        int[] arrn39 = new int[]{1, 4, 2, 2, 1, 1};
        int[] arrn40 = new int[]{1, 1, 1, 2, 4, 2};
        int[] arrn41 = new int[]{1, 2, 1, 1, 4, 2};
        int[] arrn42 = new int[]{1, 2, 1, 2, 4, 1};
        int[] arrn43 = new int[]{1, 1, 4, 2, 1, 2};
        int[] arrn44 = new int[]{4, 1, 1, 2, 1, 2};
        int[] arrn45 = new int[]{2, 1, 2, 1, 4, 1};
        int[] arrn46 = new int[]{4, 1, 2, 1, 2, 1};
        int[] arrn47 = new int[]{1, 3, 1, 1, 4, 1};
        int[] arrn48 = new int[]{1, 1, 4, 1, 1, 3};
        int[] arrn49 = new int[]{4, 1, 1, 1, 1, 3};
        int[] arrn50 = new int[]{4, 1, 1, 3, 1, 1};
        int[] arrn51 = new int[]{1, 1, 3, 1, 4, 1};
        int[] arrn52 = new int[]{1, 1, 4, 1, 3, 1};
        int[] arrn53 = new int[]{3, 1, 1, 1, 4, 1};
        int[] arrn54 = new int[]{4, 1, 1, 1, 3, 1};
        int[] arrn55 = new int[]{2, 1, 1, 2, 1, 4};
        int[] arrn56 = new int[]{2, 1, 1, 2, 3, 2};
        CODE_PATTERNS = new int[][]{arrn, arrn2, {2, 2, 2, 2, 2, 1}, arrn3, arrn4, arrn5, arrn6, {1, 2, 2, 3, 1, 2}, {1, 3, 2, 2, 1, 2}, {2, 2, 1, 2, 1, 3}, arrn7, arrn8, arrn9, arrn10, {1, 2, 2, 2, 3, 1}, arrn11, arrn12, {1, 2, 3, 2, 2, 1}, {2, 2, 3, 2, 1, 1}, {2, 2, 1, 1, 3, 2}, {2, 2, 1, 2, 3, 1}, {2, 1, 3, 2, 1, 2}, {2, 2, 3, 1, 1, 2}, {3, 1, 2, 1, 3, 1}, arrn13, {3, 2, 1, 1, 2, 2}, {3, 2, 1, 2, 2, 1}, arrn14, arrn15, {3, 2, 2, 2, 1, 1}, arrn16, {2, 1, 2, 3, 2, 1}, arrn17, arrn18, {1, 3, 1, 1, 2, 3}, {1, 3, 1, 3, 2, 1}, {1, 1, 2, 3, 1, 3}, {1, 3, 2, 1, 1, 3}, {1, 3, 2, 3, 1, 1}, arrn19, arrn20, arrn21, {1, 1, 2, 1, 3, 3}, {1, 1, 2, 3, 3, 1}, arrn22, {1, 1, 3, 1, 2, 3}, arrn23, arrn24, {3, 1, 3, 1, 2, 1}, {2, 1, 1, 3, 3, 1}, arrn25, arrn26, arrn27, {2, 1, 3, 1, 3, 1}, {3, 1, 1, 1, 2, 3}, arrn28, {3, 3, 1, 1, 2, 1}, {3, 1, 2, 1, 1, 3}, {3, 1, 2, 3, 1, 1}, arrn29, arrn30, arrn31, {4, 3, 1, 1, 1, 1}, {1, 1, 1, 2, 2, 4}, arrn32, {1, 2, 1, 1, 2, 4}, arrn33, arrn34, {1, 4, 1, 2, 2, 1}, arrn35, arrn36, arrn37, arrn38, {1, 4, 2, 1, 1, 2}, arrn39, {2, 4, 1, 2, 1, 1}, {2, 2, 1, 1, 1, 4}, {4, 1, 3, 1, 1, 1}, {2, 4, 1, 1, 1, 2}, {1, 3, 4, 1, 1, 1}, arrn40, arrn41, arrn42, arrn43, {1, 2, 4, 1, 1, 2}, {1, 2, 4, 2, 1, 1}, arrn44, {4, 2, 1, 1, 1, 2}, {4, 2, 1, 2, 1, 1}, arrn45, {2, 1, 4, 1, 2, 1}, arrn46, {1, 1, 1, 1, 4, 3}, {1, 1, 1, 3, 4, 1}, arrn47, arrn48, {1, 1, 4, 3, 1, 1}, arrn49, arrn50, arrn51, arrn52, arrn53, arrn54, {2, 1, 1, 4, 1, 2}, arrn55, arrn56, {2, 3, 3, 1, 1, 1, 2}};
    }

    private static int decodeCode(BitArray bitArray, int[] arrn, int n) throws NotFoundException {
        Code128Reader.recordPattern(bitArray, n, arrn);
        float f = 0.25f;
        int n2 = -1;
        for (n = 0; n < CODE_PATTERNS.length; ++n) {
            float f2 = Code128Reader.patternMatchVariance(arrn, CODE_PATTERNS[n], 0.7f);
            float f3 = f;
            if (f2 < f) {
                n2 = n;
                f3 = f2;
            }
            f = f3;
        }
        if (n2 >= 0) {
            return n2;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private static int[] findStartPattern(BitArray bitArray) throws NotFoundException {
        int n;
        int n2;
        int n3 = bitArray.getSize();
        int[] arrn = new int[6];
        int n4 = n2 = 0;
        int n5 = n;
        for (n = bitArray.getNextSet((int)0); n < n3; ++n) {
            int n6;
            if (bitArray.get(n) ^ n2) {
                arrn[n4] = arrn[n4] + 1;
                n6 = n5;
            } else {
                if (n4 == 5) {
                    float f = 0.25f;
                    int n7 = -1;
                    for (n6 = 103; n6 <= 105; ++n6) {
                        float f2 = Code128Reader.patternMatchVariance(arrn, CODE_PATTERNS[n6], 0.7f);
                        float f3 = f;
                        if (f2 < f) {
                            n7 = n6;
                            f3 = f2;
                        }
                        f = f3;
                    }
                    if (n7 >= 0 && bitArray.isRange(Math.max(0, n5 - (n - n5) / 2), n5, false)) {
                        return new int[]{n5, n, n7};
                    }
                    n6 = n5 + (arrn[0] + arrn[1]);
                    System.arraycopy(arrn, 2, arrn, 0, 4);
                    arrn[4] = 0;
                    arrn[5] = 0;
                    n5 = n4 - 1;
                } else {
                    n6 = n5;
                    n5 = ++n4;
                }
                arrn[n5] = 1;
                n2 ^= 1;
                n4 = n5;
            }
            n5 = n6;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    /*
     * Exception decompiling
     */
    @Override
    public Result decodeRow(int var1_1, BitArray var2_2, Map<DecodeHintType, ?> var3_3) throws NotFoundException, FormatException, ChecksumException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.CannotPerformDecode: reachable test BLOCK was exited and re-entered.
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.Misc.getFarthestReachableInRange(Misc.java:148)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:386)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.rebuildSwitches(SwitchReplacer.java:335)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:466)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:186)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:131)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:378)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:884)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:786)
        // org.benf.cfr.reader.Main.doClass(Main.java:54)
        // org.benf.cfr.reader.Main.main(Main.java:247)
        throw new IllegalStateException("Decompilation failed");
    }
}
