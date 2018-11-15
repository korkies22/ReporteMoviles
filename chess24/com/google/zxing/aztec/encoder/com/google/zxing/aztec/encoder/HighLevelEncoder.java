/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.aztec.encoder;

import com.google.zxing.aztec.encoder.State;
import com.google.zxing.common.BitArray;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public final class HighLevelEncoder {
    private static final int[][] CHAR_MAP;
    static final int[][] LATCH_TABLE;
    static final int MODE_DIGIT = 2;
    static final int MODE_LOWER = 1;
    static final int MODE_MIXED = 3;
    static final String[] MODE_NAMES;
    static final int MODE_PUNCT = 4;
    static final int MODE_UPPER = 0;
    static final int[][] SHIFT_TABLE;
    private final byte[] text;

    static {
        int n;
        MODE_NAMES = new String[]{"UPPER", "LOWER", "DIGIT", "MIXED", "PUNCT"};
        int[] arrn = new int[]{0, 327708, 327710, 327709, 656318};
        int[] arrn2 = new int[]{262158, 590300, 0, 590301, 932798};
        int[] arrn3 = new int[]{327709, 327708, 656318, 0, 327710};
        LATCH_TABLE = new int[][]{arrn, {590318, 0, 327710, 327709, 656318}, arrn2, arrn3, {327711, 656380, 656382, 656381, 0}};
        arrn = (int[][])Array.newInstance(Integer.TYPE, 5, 256);
        CHAR_MAP = arrn;
        arrn[0][32] = true;
        for (n = 65; n <= 90; ++n) {
            HighLevelEncoder.CHAR_MAP[0][n] = n - 65 + 2;
        }
        HighLevelEncoder.CHAR_MAP[1][32] = 1;
        for (n = 97; n <= 122; ++n) {
            HighLevelEncoder.CHAR_MAP[1][n] = n - 97 + 2;
        }
        HighLevelEncoder.CHAR_MAP[2][32] = 1;
        for (n = 48; n <= 57; ++n) {
            HighLevelEncoder.CHAR_MAP[2][n] = n - 48 + 2;
        }
        HighLevelEncoder.CHAR_MAP[2][44] = 12;
        HighLevelEncoder.CHAR_MAP[2][46] = 13;
        n = 0;
        while (n < 28) {
            HighLevelEncoder.CHAR_MAP[3][new int[]{0, 32, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 27, 28, 29, 30, 31, 64, 92, 94, 95, 96, 124, 126, 127}[n]] = n++;
        }
        int[] arrn4 = arrn = new int[31];
        arrn4[0] = 0;
        arrn4[1] = 13;
        arrn4[2] = 0;
        arrn4[3] = 0;
        arrn4[4] = 0;
        arrn4[5] = 0;
        arrn4[6] = 33;
        arrn4[7] = 39;
        arrn4[8] = 35;
        arrn4[9] = 36;
        arrn4[10] = 37;
        arrn4[11] = 38;
        arrn4[12] = 39;
        arrn4[13] = 40;
        arrn4[14] = 41;
        arrn4[15] = 42;
        arrn4[16] = 43;
        arrn4[17] = 44;
        arrn4[18] = 45;
        arrn4[19] = 46;
        arrn4[20] = 47;
        arrn4[21] = 58;
        arrn4[22] = 59;
        arrn4[23] = 60;
        arrn4[24] = 61;
        arrn4[25] = 62;
        arrn4[26] = 63;
        arrn4[27] = 91;
        arrn4[28] = 93;
        arrn4[29] = 123;
        arrn4[30] = 125;
        for (n = 0; n < 31; ++n) {
            if (arrn[n] <= 0) continue;
            HighLevelEncoder.CHAR_MAP[4][arrn[n]] = n;
        }
        arrn = (int[][])Array.newInstance(Integer.TYPE, 6, 6);
        SHIFT_TABLE = arrn;
        int n2 = arrn.length;
        for (n = 0; n < n2; ++n) {
            Arrays.fill((int[])arrn[n], -1);
        }
        HighLevelEncoder.SHIFT_TABLE[0][4] = 0;
        HighLevelEncoder.SHIFT_TABLE[1][4] = 0;
        HighLevelEncoder.SHIFT_TABLE[1][0] = 28;
        HighLevelEncoder.SHIFT_TABLE[3][4] = 0;
        HighLevelEncoder.SHIFT_TABLE[2][4] = 0;
        HighLevelEncoder.SHIFT_TABLE[2][0] = 15;
    }

    public HighLevelEncoder(byte[] arrby) {
        this.text = arrby;
    }

    private static Collection<State> simplifyStates(Iterable<State> object) {
        LinkedList<State> linkedList = new LinkedList<State>();
        object = object.iterator();
        while (object.hasNext()) {
            boolean bl;
            State state = (State)object.next();
            boolean bl2 = true;
            Iterator iterator = linkedList.iterator();
            do {
                bl = bl2;
                if (!iterator.hasNext()) break;
                State state2 = (State)iterator.next();
                if (state2.isBetterThanOrEqualTo(state)) {
                    bl = false;
                    break;
                }
                if (!state.isBetterThanOrEqualTo(state2)) continue;
                iterator.remove();
            } while (true);
            if (!bl) continue;
            linkedList.add(state);
        }
        return linkedList;
    }

    private void updateStateForChar(State state, int n, Collection<State> collection) {
        char c = (char)(this.text[n] & 255);
        int n2 = CHAR_MAP[state.getMode()][c];
        n2 = n2 > 0 ? 1 : 0;
        State state2 = null;
        for (int i = 0; i <= 4; ++i) {
            int n3 = CHAR_MAP[i][c];
            State state3 = state2;
            if (n3 > 0) {
                State state4 = state2;
                if (state2 == null) {
                    state4 = state.endBinaryShift(n);
                }
                if (n2 == 0 || i == state.getMode() || i == 2) {
                    collection.add(state4.latchAndAppend(i, n3));
                }
                state3 = state4;
                if (n2 == 0) {
                    state3 = state4;
                    if (SHIFT_TABLE[state.getMode()][i] >= 0) {
                        collection.add(state4.shiftAndAppend(i, n3));
                        state3 = state4;
                    }
                }
            }
            state2 = state3;
        }
        if (state.getBinaryShiftByteCount() > 0 || CHAR_MAP[state.getMode()][c] == 0) {
            collection.add(state.addBinaryShiftChar(n));
        }
    }

    private static void updateStateForPair(State state, int n, int n2, Collection<State> collection) {
        State state2 = state.endBinaryShift(n);
        collection.add(state2.latchAndAppend(4, n2));
        if (state.getMode() != 4) {
            collection.add(state2.shiftAndAppend(4, n2));
        }
        if (n2 == 3 || n2 == 4) {
            collection.add(state2.latchAndAppend(2, 16 - n2).latchAndAppend(2, 1));
        }
        if (state.getBinaryShiftByteCount() > 0) {
            collection.add(state.addBinaryShiftChar(n).addBinaryShiftChar(n + 1));
        }
    }

    private Collection<State> updateStateListForChar(Iterable<State> object, int n) {
        LinkedList<State> linkedList = new LinkedList<State>();
        object = object.iterator();
        while (object.hasNext()) {
            this.updateStateForChar((State)object.next(), n, linkedList);
        }
        return HighLevelEncoder.simplifyStates(linkedList);
    }

    private static Collection<State> updateStateListForPair(Iterable<State> object, int n, int n2) {
        LinkedList<State> linkedList = new LinkedList<State>();
        object = object.iterator();
        while (object.hasNext()) {
            HighLevelEncoder.updateStateForPair((State)object.next(), n, n2, linkedList);
        }
        return HighLevelEncoder.simplifyStates(linkedList);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public BitArray encode() {
        var5_1 = Collections.singletonList(State.INITIAL_STATE);
        var2_6 = 0;
        while (var2_6 < this.text.length) {
            block8 : {
                block5 : {
                    block6 : {
                        block7 : {
                            var3_8 = var2_6 + 1;
                            var1_7 = var3_8 < this.text.length ? this.text[var3_8] : 0;
                            var4_9 = this.text[var2_6];
                            if (var4_9 == 13) break block5;
                            if (var4_9 == 44) break block6;
                            if (var4_9 == 46) break block7;
                            if (var4_9 != 58 || var1_7 != 32) ** GOTO lbl-1000
                            var1_7 = 5;
                            break block8;
                        }
                        if (var1_7 != 32) ** GOTO lbl-1000
                        var1_7 = 3;
                        break block8;
                    }
                    if (var1_7 != 32) ** GOTO lbl-1000
                    var1_7 = 4;
                    break block8;
                }
                if (var1_7 == 10) {
                    var1_7 = 2;
                } else lbl-1000: // 4 sources:
                {
                    var1_7 = 0;
                }
            }
            if (var1_7 > 0) {
                var5_3 = HighLevelEncoder.updateStateListForPair((Iterable<State>)var5_2, var2_6, var1_7);
                var1_7 = var3_8;
            } else {
                var5_4 = this.updateStateListForChar((Iterable<State>)var5_2, var2_6);
                var1_7 = var2_6;
            }
            var2_6 = var1_7 + 1;
        }
        return ((State)Collections.min(var5_2, new Comparator<State>(){

            @Override
            public int compare(State state, State state2) {
                return state.getBitCount() - state2.getBitCount();
            }
        })).toBitArray(this.text);
    }

}
