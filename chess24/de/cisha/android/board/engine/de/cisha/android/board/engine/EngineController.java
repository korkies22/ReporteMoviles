/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.AsyncTask
 *  android.util.SparseArray
 */
package de.cisha.android.board.engine;

import android.os.AsyncTask;
import android.util.SparseArray;
import de.cisha.android.board.engine.EngineObserver;
import de.cisha.android.board.engine.EvaluationInfo;
import de.cisha.chess.engine.EngineDelegate;
import de.cisha.chess.engine.UCIEngine;
import de.cisha.chess.engine.UCIInfo;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.SEP;
import de.cisha.chess.model.position.Position;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class EngineController
implements EngineDelegate {
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
    private int _variations = 1;

    public EngineController(UCIEngine uCIEngine) {
        this._engine = uCIEngine;
        this._observers = new LinkedList<EngineObserver>();
        uCIEngine.setEngineDelegate(this);
        this._lines = new SparseArray(5);
        this._evalToShow = "";
    }

    private void notifiyObserversEngineStart() {
        Iterator<EngineObserver> iterator = this._observers.iterator();
        while (iterator.hasNext()) {
            iterator.next().onEngineStarted();
        }
    }

    private void notifiyObserversEngineStop(Move move) {
        Iterator<EngineObserver> iterator = this._observers.iterator();
        while (iterator.hasNext()) {
            iterator.next().onEngineStopped(move);
        }
    }

    private void notifyObserversNewLine() {
        if (this._observers.size() > 0) {
            List<EvaluationInfo> list = this.getCurrentEvaluations();
            Iterator<EngineObserver> iterator = this._observers.iterator();
            while (iterator.hasNext()) {
                iterator.next().onVariationsChanged(list);
            }
        }
    }

    public void addEngineObserver(EngineObserver engineObserver) {
        this._observers.add(engineObserver);
    }

    public void addEngineVariation() {
        synchronized (this) {
            ++this._variations;
            this._engine.setVariations(this._variations);
            this.notifyObserversNewLine();
            return;
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
    public void engineReceivedInfo(UCIInfo uCIInfo) {
        if (!uCIInfo.getPosition().equals(this._currentPosition)) {
            this._currentPosition = uCIInfo.getPosition();
            this.clear();
            if (this._currentPosition.isCheckMate()) {
                this._evalToShow = "#";
            }
        }
        if (uCIInfo.getLineNumber() == 1) {
            this._eval = uCIInfo.getEval();
            this._evalToShow = uCIInfo.getEvalString();
        }
        if (uCIInfo.getNodes() > 0) {
            this._nodes = uCIInfo.getNodes();
        }
        if (uCIInfo.getTimeInMills() != 0) {
            this._mills = uCIInfo.getTimeInMills();
        }
        if (uCIInfo.getLineNumber() <= this._variations) {
            EvaluationInfo evaluationInfo = new EvaluationInfo(uCIInfo);
            this._depth = Math.max(this._depth, evaluationInfo.getDepth());
            this._lines.put(uCIInfo.getLineNumber(), (Object)evaluationInfo);
            this.notifyObserversNewLine();
        }
    }

    public List<EvaluationInfo> getCurrentEvaluations() {
        ArrayList<EvaluationInfo> arrayList = new ArrayList<EvaluationInfo>(this._lines.size());
        for (int i = 0; i < this._variations; ++i) {
            if (i < this._lines.size()) {
                arrayList.add((EvaluationInfo)this._lines.valueAt(i));
                continue;
            }
            arrayList.add(new EvaluationInfo(new UCIInfo()));
        }
        return arrayList;
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

    public void positionChanged(Position position) {
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
            return;
        }
    }

    public void setVariationsNumber(int n) {
        this._variations = n;
        this._engine.setVariations(this._variations);
    }

    public void startCalculation() {
        synchronized (this) {
            if (this._engine.isReady()) {
                this.startEngine();
            } else {
                this._engine.sendStopAndGo("infinite");
            }
            return;
        }
    }

    public void startEngine() {
        new Thread(new Runnable(){

            @Override
            public void run() {
                if (EngineController.this._engine != null && EngineController.this._engine.start()) {
                    EngineController.this.notifiyObserversEngineStart();
                }
            }
        }).start();
    }

    public void startEngine(final int n) {
        new AsyncTask<Void, Void, Move>(){

            protected /* varargs */ Move doInBackground(Void ... object) {
                EngineController.this.notifiyObserversEngineStart();
                object = EngineController.this._engine.getMoveWithThinkingTime(n);
                if (object != null) {
                    return EngineController.this._engine.getCurrentPosition().createMoveFrom((SEP)object);
                }
                return null;
            }

            protected void onPostExecute(Move move) {
                EngineController.this.notifiyObserversEngineStop(move);
            }
        }.execute((Object[])new Void[]{null});
    }

    public void stopEngine() {
        new Thread(new Runnable(){

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
