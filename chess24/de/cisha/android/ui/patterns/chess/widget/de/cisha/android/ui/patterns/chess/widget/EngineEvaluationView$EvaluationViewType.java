/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.ui.patterns.chess.widget;

import de.cisha.android.ui.patterns.R;
import de.cisha.android.ui.patterns.chess.widget.EngineEvaluationView;

public static enum EngineEvaluationView.EvaluationViewType {
    WHITE_LEFT(R.drawable.engine_eval_horizontal_white_left),
    WHITE_RIGHT(R.drawable.engine_eval_horizontal_white_right);
    
    private int _resDrawableId;

    private EngineEvaluationView.EvaluationViewType(int n2) {
        this._resDrawableId = n2;
    }

    static /* synthetic */ int access$000(EngineEvaluationView.EvaluationViewType evaluationViewType) {
        return evaluationViewType._resDrawableId;
    }
}
