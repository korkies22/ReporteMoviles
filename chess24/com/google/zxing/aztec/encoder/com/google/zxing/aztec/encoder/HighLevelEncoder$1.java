/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.aztec.encoder;

import com.google.zxing.aztec.encoder.State;
import java.util.Comparator;

class HighLevelEncoder
implements Comparator<State> {
    HighLevelEncoder() {
    }

    @Override
    public int compare(State state, State state2) {
        return state.getBitCount() - state2.getBitCount();
    }
}
