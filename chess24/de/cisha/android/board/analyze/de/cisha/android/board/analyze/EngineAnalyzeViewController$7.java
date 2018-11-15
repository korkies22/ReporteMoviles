/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.widget.TextView
 */
package de.cisha.android.board.analyze;

import android.widget.TextView;
import de.cisha.android.board.analyze.EngineAnalyzeViewController;
import de.cisha.android.board.engine.EngineController;
import de.cisha.android.board.engine.EvaluationInfo;
import java.util.Collection;
import java.util.List;

class EngineAnalyzeViewController
implements Runnable {
    final /* synthetic */ List val$variations;

    EngineAnalyzeViewController(List list) {
        this.val$variations = list;
    }

    @Override
    public void run() {
        EngineAnalyzeViewController.this._currentEvaluations.clear();
        EngineAnalyzeViewController.this._currentEvaluations.addAll(this.val$variations);
        EngineAnalyzeViewController.this._listAdapter.notifyDataSetChanged();
        float f = EngineAnalyzeViewController.this._engineController.getEval();
        EngineAnalyzeViewController.this._eval.setText((CharSequence)EngineAnalyzeViewController.this._engineController.getEvalString());
        EngineAnalyzeViewController.this.setEval(f);
        TextView textView = EngineAnalyzeViewController.this._depth;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(EngineAnalyzeViewController.this._engineController.getDepth());
        textView.setText((CharSequence)stringBuilder.toString());
        if (EngineAnalyzeViewController.this.hasSpecialPosition()) {
            EngineAnalyzeViewController.this.updateMateInfo();
        }
        EngineAnalyzeViewController.this.checkForEngineDepthPermissions();
    }
}
