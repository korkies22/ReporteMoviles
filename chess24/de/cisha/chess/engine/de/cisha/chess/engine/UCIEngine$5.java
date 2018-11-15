/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.engine;

import de.cisha.chess.engine.UCIInfo;
import java.util.Comparator;

class UCIEngine
implements Comparator<UCIInfo> {
    UCIEngine() {
    }

    @Override
    public int compare(UCIInfo uCIInfo, UCIInfo uCIInfo2) {
        return uCIInfo.getLineNumber() - uCIInfo2.getLineNumber();
    }
}
