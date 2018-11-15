/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.widget.ListView
 *  android.widget.TextView
 */
package de.cisha.android.board.analyze;

import android.content.Context;
import android.widget.ListView;
import android.widget.TextView;
import de.cisha.android.board.analyze.EngineAnalyzeViewController;
import de.cisha.android.board.engine.EngineController;
import de.cisha.android.board.engine.EngineObserver;
import de.cisha.android.board.engine.EvaluationInfo;
import de.cisha.android.board.service.IEngineService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.ui.patterns.text.CustomTextView;
import de.cisha.chess.engine.UCIEngine;
import de.cisha.chess.model.FEN;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.position.Position;
import de.cisha.chess.model.position.PositionObservable;
import java.util.Collection;
import java.util.List;

class EngineAnalyzeViewController
implements Runnable {
    final /* synthetic */ PositionObservable val$positionObservable;

    EngineAnalyzeViewController(PositionObservable positionObservable) {
        this.val$positionObservable = positionObservable;
    }

    @Override
    public void run() {
        int n = EngineAnalyzeViewController.this._currentEvaluations.size();
        UCIEngine uCIEngine = ServiceProvider.getInstance().getEngineService().getDefaultSingleEngine();
        EngineAnalyzeViewController.this._engineController = new EngineController(uCIEngine);
        Object object2 = EngineAnalyzeViewController.this._engineController;
        int n2 = n;
        if (n == 0) {
            n2 = 1;
        }
        object2.setVariationsNumber(n2);
        uCIEngine.setPosition(this.val$positionObservable.getFEN());
        EngineAnalyzeViewController.this._engineController.addEngineObserver(EngineAnalyzeViewController.this);
        EngineAnalyzeViewController.this.activateButtons();
        EngineAnalyzeViewController.this._startStopButton.setText((CharSequence)EngineAnalyzeViewController.this._context.getString(2131689547));
        EngineAnalyzeViewController.this._infoText.setVisibility(8);
        EngineAnalyzeViewController.this._infoText.setText((CharSequence)"");
        EngineAnalyzeViewController.this._currentEvaluations.clear();
        EngineAnalyzeViewController.this._currentEvaluations.addAll(EngineAnalyzeViewController.this._engineController.getCurrentEvaluations());
        EngineAnalyzeViewController.this._listView.setVisibility(0);
        EngineAnalyzeViewController.this._listAdapter.notifyDataSetChanged();
        EngineAnalyzeViewController.this.positionChanged(this.val$positionObservable.getPosition(), null);
        for (Object object2 : EngineAnalyzeViewController.this._engineObservers) {
            EngineAnalyzeViewController.this._engineController.addEngineObserver((EngineObserver)object2);
        }
        if (EngineAnalyzeViewController.this._engineRunning) {
            EngineAnalyzeViewController.this._startStopButton.postDelayed(new Runnable(){

                @Override
                public void run() {
                    EngineAnalyzeViewController.this._engineController.startEngine();
                }
            }, 100L);
        }
        EngineAnalyzeViewController.this._engineObservers.clear();
    }

}
