/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.engine;

import de.cisha.android.board.engine.EvaluationInfo;
import de.cisha.chess.model.Move;
import java.util.List;

public interface EngineObserver {
    public void onEngineStarted();

    public void onEngineStopped(Move var1);

    public void onVariationsChanged(List<EvaluationInfo> var1);
}
