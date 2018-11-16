// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.engine;

import de.cisha.chess.engine.UCIInfo;
import de.cisha.chess.engine.EngineEvaluation;
import java.util.Random;
import de.cisha.chess.model.Move;
import java.util.LinkedList;
import java.util.List;
import de.cisha.chess.model.position.Position;

public class RandomEngine implements IMoveSource
{
    private float _eval1;
    private float _eval2;
    
    public RandomEngine() {
        this._eval1 = 0.0f;
        this._eval2 = 0.0f;
    }
    
    @Override
    public void destroy() {
    }
    
    @Override
    public List<EvaluationInfo> getMovesWithMaximumTime(final Position position, final long n) {
        final LinkedList<EvaluationInfo> list = new LinkedList<EvaluationInfo>();
        final List<Move> allMoves = position.getAllMoves();
        final int size = allMoves.size();
        if (size == 0) {
            return list;
        }
        final Move move = allMoves.get(new Random().nextInt(size));
        final Move move2 = allMoves.get(new Random().nextInt(size));
        this._eval1 += 0.5f;
        this._eval2 += 0.2f;
        list.add(new EvaluationInfo(new UCIInfo(position, 1, move.getEAN(), new EngineEvaluation(this._eval1), 5, 55, 555)));
        list.add(new EvaluationInfo(new UCIInfo(position, 2, move2.getEAN(), new EngineEvaluation(this._eval2), 5, 55, 555)));
        return list;
    }
}
