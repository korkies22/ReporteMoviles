// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.engine;

import de.cisha.chess.model.SEP;
import android.os.AsyncTask;
import java.util.ArrayList;
import de.cisha.chess.engine.UCIInfo;
import java.util.Iterator;
import de.cisha.chess.model.Move;
import java.util.LinkedList;
import java.util.List;
import android.util.SparseArray;
import de.cisha.chess.engine.UCIEngine;
import de.cisha.chess.model.position.Position;
import de.cisha.chess.engine.EngineDelegate;

public class EngineController implements EngineDelegate
{
    private static final int MAIN_LINE_NUMBER = 1;
    private Position _currentPosition;
    private int _depth;
    private UCIEngine _engine;
    private float _eval;
    private String _evalToShow;
    private SparseArray<EvaluationInfo> _lines;
    private int _mills;
    private int _nodes;
    private List<EngineObserver> _observers;
    private int _variations;
    
    public EngineController(final UCIEngine engine) {
        this._variations = 1;
        this._engine = engine;
        this._observers = new LinkedList<EngineObserver>();
        engine.setEngineDelegate(this);
        this._lines = (SparseArray<EvaluationInfo>)new SparseArray(5);
        this._evalToShow = "";
    }
    
    private void notifiyObserversEngineStart() {
        final Iterator<EngineObserver> iterator = this._observers.iterator();
        while (iterator.hasNext()) {
            iterator.next().onEngineStarted();
        }
    }
    
    private void notifiyObserversEngineStop(final Move move) {
        final Iterator<EngineObserver> iterator = this._observers.iterator();
        while (iterator.hasNext()) {
            iterator.next().onEngineStopped(move);
        }
    }
    
    private void notifyObserversNewLine() {
        if (this._observers.size() > 0) {
            final List<EvaluationInfo> currentEvaluations = this.getCurrentEvaluations();
            final Iterator<EngineObserver> iterator = this._observers.iterator();
            while (iterator.hasNext()) {
                iterator.next().onVariationsChanged(currentEvaluations);
            }
        }
    }
    
    public void addEngineObserver(final EngineObserver engineObserver) {
        this._observers.add(engineObserver);
    }
    
    public void addEngineVariation() {
        synchronized (this) {
            ++this._variations;
            this._engine.setVariations(this._variations);
            this.notifyObserversNewLine();
        }
    }
    
    public void clear() {
        this._lines.clear();
        this._depth = 0;
        this._eval = 0.0f;
        this._evalToShow = "...";
        this._mills = 0;
        this._nodes = 0;
    }
    
    public void destroyEngine() {
        if (this._engine != null) {
            this._engine.destroy();
        }
    }
    
    @Override
    public void engineReceivedInfo(final UCIInfo uciInfo) {
        if (!uciInfo.getPosition().equals(this._currentPosition)) {
            this._currentPosition = uciInfo.getPosition();
            this.clear();
            if (this._currentPosition.isCheckMate()) {
                this._evalToShow = "#";
            }
        }
        if (uciInfo.getLineNumber() == 1) {
            this._eval = uciInfo.getEval();
            this._evalToShow = uciInfo.getEvalString();
        }
        if (uciInfo.getNodes() > 0) {
            this._nodes = uciInfo.getNodes();
        }
        if (uciInfo.getTimeInMills() != 0) {
            this._mills = uciInfo.getTimeInMills();
        }
        if (uciInfo.getLineNumber() <= this._variations) {
            final EvaluationInfo evaluationInfo = new EvaluationInfo(uciInfo);
            this._depth = Math.max(this._depth, evaluationInfo.getDepth());
            this._lines.put(uciInfo.getLineNumber(), (Object)evaluationInfo);
            this.notifyObserversNewLine();
        }
    }
    
    public List<EvaluationInfo> getCurrentEvaluations() {
        final ArrayList<EvaluationInfo> list = new ArrayList<EvaluationInfo>(this._lines.size());
        for (int i = 0; i < this._variations; ++i) {
            if (i < this._lines.size()) {
                list.add((EvaluationInfo)this._lines.valueAt(i));
            }
            else {
                list.add(new EvaluationInfo(new UCIInfo()));
            }
        }
        return list;
    }
    
    public int getDepth() {
        return this._depth;
    }
    
    @Deprecated
    public UCIEngine getEngine() {
        return this._engine;
    }
    
    public float getEval() {
        return this._eval;
    }
    
    public String getEvalString() {
        return this._evalToShow;
    }
    
    public int getMills() {
        return this._mills;
    }
    
    public int getNodes() {
        return this._nodes;
    }
    
    public int getVariationCount() {
        return this._variations;
    }
    
    public void positionChanged(final Position position) {
        this._engine.positionChanged(position);
    }
    
    public void removeEngineVariation() {
        synchronized (this) {
            if (this._variations > 1) {
                this._lines.remove(this._variations);
                --this._variations;
                this._engine.setVariations(this._variations);
                this.notifyObserversNewLine();
            }
        }
    }
    
    public void setVariationsNumber(final int variations) {
        this._variations = variations;
        this._engine.setVariations(this._variations);
    }
    
    public void startCalculation() {
        synchronized (this) {
            if (this._engine.isReady()) {
                this.startEngine();
            }
            else {
                this._engine.sendStopAndGo("infinite");
            }
        }
    }
    
    public void startEngine() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (EngineController.this._engine != null && EngineController.this._engine.start()) {
                    EngineController.this.notifiyObserversEngineStart();
                }
            }
        }).start();
    }
    
    public void startEngine(final int n) {
        new AsyncTask<Void, Void, Move>() {
            protected Move doInBackground(final Void... array) {
                EngineController.this.notifiyObserversEngineStart();
                final SEP moveWithThinkingTime = EngineController.this._engine.getMoveWithThinkingTime(n);
                if (moveWithThinkingTime != null) {
                    return EngineController.this._engine.getCurrentPosition().createMoveFrom(moveWithThinkingTime);
                }
                return null;
            }
            
            protected void onPostExecute(final Move move) {
                EngineController.this.notifiyObserversEngineStop(move);
            }
        }.execute((Object[])new Void[] { null });
    }
    
    public void stopEngine() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (EngineController.this._engine != null) {
                    EngineController.this._engine.stop();
                    EngineController.this.notifiyObserversEngineStop(null);
                }
            }
        }).start();
    }
}
