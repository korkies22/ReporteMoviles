/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.engine;

import de.cisha.android.board.engine.EvaluationInfo;
import de.cisha.android.board.engine.IMoveSource;
import de.cisha.chess.engine.EngineEvaluation;
import de.cisha.chess.engine.UCIInfo;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.position.Position;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class RandomEngine
implements IMoveSource {
    private float _eval1 = 0.0f;
    private float _eval2 = 0.0f;

    @Override
    public void destroy() {
    }

    @Override
    public List<EvaluationInfo> getMovesWithMaximumTime(Position position, long l) {
        LinkedList<EvaluationInfo> linkedList = new LinkedList<EvaluationInfo>();
        List<Move> list = position.getAllMoves();
        int n = list.size();
        if (n == 0) {
            return linkedList;
        }
        Move move = list.get(new Random().nextInt(n));
        list = list.get(new Random().nextInt(n));
        this._eval1 += 0.5f;
        this._eval2 += 0.2f;
        linkedList.add(new EvaluationInfo(new UCIInfo(position, 1, move.getEAN(), new EngineEvaluation(this._eval1), 5, 55, 555)));
        linkedList.add(new EvaluationInfo(new UCIInfo(position, 2, list.getEAN(), new EngineEvaluation(this._eval2), 5, 55, 555)));
        return linkedList;
    }
}
