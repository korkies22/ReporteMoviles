/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.engine;

import de.cisha.chess.engine.UCIEngine;
import de.cisha.chess.engine.UCIInfo;
import java.util.Map;

class UCIEngine
extends UCIEngine.EngineOutputLineReceiver {
    final /* synthetic */ Map val$mapLineToInfo;

    UCIEngine(Map map) {
        this.val$mapLineToInfo = map;
        super(UCIEngine.this, null);
    }

    @Override
    boolean receivedLine(String object) {
        if (object.startsWith("info") && object.contains("multipv")) {
            object = UCIEngine.this.parseInfoLine((String)object);
            int n = object.getLineNumber();
            this.val$mapLineToInfo.put(n, object);
        } else if (object.startsWith("bestmove")) {
            return true;
        }
        return false;
    }
}
