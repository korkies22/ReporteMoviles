/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.engine.model;

import de.cisha.android.board.engine.EvaluationInfo;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.Rating;
import java.util.List;

public interface IEngineHumanization {
    public boolean acceptDrawOffer();

    public Move chooseMove(List<EvaluationInfo> var1);

    public long getMaximumThinkingTimeForNextMove();

    public Rating getRating();

    public boolean resign();
}
