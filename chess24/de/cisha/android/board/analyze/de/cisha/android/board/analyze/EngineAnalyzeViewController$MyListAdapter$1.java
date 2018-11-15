/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board.analyze;

import android.view.View;
import de.cisha.android.board.analyze.EngineAnalyzeViewController;
import de.cisha.android.board.engine.EvaluationInfo;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.MoveExecutor;
import de.cisha.chess.model.SEP;

class EngineAnalyzeViewController.MyListAdapter
implements View.OnClickListener {
    final /* synthetic */ EvaluationInfo val$info;

    EngineAnalyzeViewController.MyListAdapter(EvaluationInfo evaluationInfo) {
        this.val$info = evaluationInfo;
    }

    public void onClick(View object) {
        object = this.val$info.getMove();
        if (object != null) {
            MyListAdapter.this.this$0._moveExecutor.doMoveInCurrentPosition(object.getSEP());
        }
    }
}
