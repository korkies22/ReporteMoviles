// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.engine;

import java.util.Iterator;
import de.cisha.chess.engine.UCIInfo;
import java.util.ArrayList;
import java.util.TimerTask;
import java.util.Timer;
import de.cisha.chess.util.Logger;
import java.util.List;
import de.cisha.chess.model.position.Position;
import de.cisha.chess.engine.UCISpinOption;
import de.cisha.android.stockfish.UCIEngineRemote;
import de.cisha.android.stockfish.StockfishEngineStrength;
import de.cisha.chess.engine.UCIEngine;

public class StockfishEngine implements IMoveSource
{
    private int _depth;
    private UCIEngine _stockfish;
    
    public StockfishEngine(final int n) {
        final StockfishEngineStrength stockfishEngineStrength = new StockfishEngineStrength(n);
        this._depth = stockfishEngineStrength.getDepth();
        (this._stockfish = UCIEngineRemote.getInstance()).startup();
        final UCISpinOption uciSpinOption = this._stockfish.getUCISpinOption("Skill Level");
        this._stockfish.setVariations(stockfishEngineStrength.getVariants());
        if (uciSpinOption != null) {
            uciSpinOption.setValue(stockfishEngineStrength.getSkillLevel());
        }
    }
    
    @Override
    public void destroy() {
        this._stockfish.destroy();
    }
    
    @Override
    public List<EvaluationInfo> getMovesWithMaximumTime(final Position position, final long n) {
        this._stockfish.setPosition(position.getFEN());
        final Logger instance = Logger.getInstance();
        final StringBuilder sb = new StringBuilder();
        sb.append("trying to find move in ");
        sb.append(n);
        sb.append(" millis");
        instance.debug("StockfishHuman", sb.toString());
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                StockfishEngine.this._stockfish.stop();
            }
        }, n);
        final List<UCIInfo> analysePositionWithDepth = this._stockfish.analysePositionWithDepth(this._depth);
        Logger.getInstance().debug("StockfishHuman", "found Move");
        timer.cancel();
        List<UCIInfo> analysePositionWithDepth2 = null;
        Label_0133: {
            if (analysePositionWithDepth != null) {
                analysePositionWithDepth2 = analysePositionWithDepth;
                if (analysePositionWithDepth.size() != 0) {
                    break Label_0133;
                }
            }
            analysePositionWithDepth2 = this._stockfish.analysePositionWithDepth(1);
        }
        final ArrayList list = new ArrayList<EvaluationInfo>(analysePositionWithDepth2.size());
        final Iterator<UCIInfo> iterator = analysePositionWithDepth2.iterator();
        while (iterator.hasNext()) {
            list.add(new EvaluationInfo(iterator.next()));
        }
        return (List<EvaluationInfo>)list;
    }
}
