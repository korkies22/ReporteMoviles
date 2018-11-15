/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.BaseAdapter
 */
package de.cisha.android.board.analyze;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import de.cisha.android.board.analyze.EngineAnalyzeViewController;
import de.cisha.android.board.analyze.view.EngineVariantView;
import de.cisha.android.board.engine.EvaluationInfo;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.MoveExecutor;
import de.cisha.chess.model.SEP;
import java.util.List;

private class EngineAnalyzeViewController.MyListAdapter
extends BaseAdapter {
    public int getCount() {
        return EngineAnalyzeViewController.this._currentEvaluations.size();
    }

    public Object getItem(int n) {
        return EngineAnalyzeViewController.this._currentEvaluations.get(n);
    }

    public long getItemId(int n) {
        return 0L;
    }

    public View getView(int n, View object, ViewGroup viewGroup) {
        viewGroup = object;
        if (object == null) {
            viewGroup = EngineAnalyzeViewController.this._inflater.inflate(2131427437, null);
        }
        object = (EvaluationInfo)EngineAnalyzeViewController.this._currentEvaluations.get(n);
        ((EngineVariantView)viewGroup).setEvalInfo((EvaluationInfo)object);
        viewGroup.findViewById(2131296518).setOnClickListener(new View.OnClickListener((EvaluationInfo)object){
            final /* synthetic */ EvaluationInfo val$info;
            {
                this.val$info = evaluationInfo;
            }

            public void onClick(View object) {
                object = this.val$info.getMove();
                if (object != null) {
                    EngineAnalyzeViewController.this._moveExecutor.doMoveInCurrentPosition(object.getSEP());
                }
            }
        });
        return viewGroup;
    }

}
