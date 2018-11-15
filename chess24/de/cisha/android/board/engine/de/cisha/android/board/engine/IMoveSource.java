/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.engine;

import de.cisha.android.board.engine.EvaluationInfo;
import de.cisha.chess.model.position.Position;
import java.util.List;

public interface IMoveSource {
    public void destroy();

    public List<EvaluationInfo> getMovesWithMaximumTime(Position var1, long var2);
}
