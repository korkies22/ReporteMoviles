/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.engine;

import de.cisha.android.board.engine.EvaluationInfo;
import de.cisha.android.board.engine.IMoveSource;
import de.cisha.android.stockfish.StockfishEngineStrength;
import de.cisha.android.stockfish.UCIEngineRemote;
import de.cisha.chess.engine.UCIEngine;
import de.cisha.chess.engine.UCIInfo;
import de.cisha.chess.engine.UCISpinOption;
import de.cisha.chess.model.FEN;
import de.cisha.chess.model.position.Position;
import de.cisha.chess.util.Logger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class StockfishEngine
implements IMoveSource {
    private int _depth;
    private UCIEngine _stockfish;

    public StockfishEngine(int n) {
        StockfishEngineStrength stockfishEngineStrength = new StockfishEngineStrength(n);
        this._depth = stockfishEngineStrength.getDepth();
        this._stockfish = UCIEngineRemote.getInstance();
        this._stockfish.startup();
        UCISpinOption uCISpinOption = this._stockfish.getUCISpinOption("Skill Level");
        this._stockfish.setVariations(stockfishEngineStrength.getVariants());
        if (uCISpinOption != null) {
            uCISpinOption.setValue(stockfishEngineStrength.getSkillLevel());
        }
    }

    @Override
    public void destroy() {
        this._stockfish.destroy();
    }

    @Override
    public List<EvaluationInfo> getMovesWithMaximumTime(Position object, long l) {
        ArrayList<EvaluationInfo> arrayList;
        block4 : {
            block3 : {
                this._stockfish.setPosition(object.getFEN());
                object = Logger.getInstance();
                arrayList = new StringBuilder();
                arrayList.append("trying to find move in ");
                arrayList.append(l);
                arrayList.append(" millis");
                object.debug("StockfishHuman", ((StringBuilder)((Object)arrayList)).toString());
                object = new Timer();
                object.schedule(new TimerTask(){

                    @Override
                    public void run() {
                        StockfishEngine.this._stockfish.stop();
                    }
                }, l);
                arrayList = this._stockfish.analysePositionWithDepth(this._depth);
                Logger.getInstance().debug("StockfishHuman", "found Move");
                object.cancel();
                if (arrayList == null) break block3;
                object = arrayList;
                if (arrayList.size() != 0) break block4;
            }
            object = this._stockfish.analysePositionWithDepth(1);
        }
        arrayList = new ArrayList<EvaluationInfo>(object.size());
        object = object.iterator();
        while (object.hasNext()) {
            arrayList.add(new EvaluationInfo((UCIInfo)object.next()));
        }
        return arrayList;
    }

}
