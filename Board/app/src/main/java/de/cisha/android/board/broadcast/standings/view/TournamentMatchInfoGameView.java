// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.standings.view;

import android.widget.ImageView;
import android.widget.TextView;
import android.view.ViewGroup;
import android.view.View;
import de.cisha.android.ui.patterns.chess.widget.EngineEvaluationView;
import android.graphics.Color;
import android.content.Context;
import de.cisha.chess.model.GameResult;
import android.widget.LinearLayout;

public class TournamentMatchInfoGameView extends LinearLayout
{
    private float _eval;
    private GameResult _result;
    private boolean _running;
    private boolean _showEval;
    private boolean _whiteLeft;
    
    public TournamentMatchInfoGameView(final Context context, final boolean whiteLeft, final boolean running, final float eval) {
        super(context);
        this._showEval = false;
        this._whiteLeft = whiteLeft;
        this._running = running;
        this._eval = eval;
        this._showEval = true;
        this._result = GameResult.NO_RESULT;
        this.init();
    }
    
    public TournamentMatchInfoGameView(final Context context, final boolean whiteLeft, final boolean running, final GameResult result) {
        super(context);
        this._showEval = false;
        this._whiteLeft = whiteLeft;
        this._running = running;
        this._result = result;
        this.init();
    }
    
    private void init() {
        this.setOrientation(0);
        this.setGravity(17);
        this.setBackgroundColor(Color.rgb(191, 191, 191));
        if (this._showEval) {
            final Context context = this.getContext();
            EngineEvaluationView.EvaluationViewType evaluationViewType;
            if (this._whiteLeft) {
                evaluationViewType = EngineEvaluationView.EvaluationViewType.WHITE_LEFT;
            }
            else {
                evaluationViewType = EngineEvaluationView.EvaluationViewType.WHITE_RIGHT;
            }
            final EngineEvaluationView engineEvaluationView = new EngineEvaluationView(context, evaluationViewType);
            engineEvaluationView.setEvaluationValue(this._eval);
            this.addView((View)engineEvaluationView);
            return;
        }
        inflate(this.getContext(), 2131427393, (ViewGroup)this);
        final TextView textView = (TextView)this.findViewById(2131297178);
        final ImageView imageView = (ImageView)this.findViewById(2131297179);
        final ImageView imageView2 = (ImageView)this.findViewById(2131297180);
        if (this._result != GameResult.NO_RESULT) {
            final boolean whiteLeft = this._whiteLeft;
            final int n = 2131231863;
            int imageResource;
            if (whiteLeft) {
                imageResource = 2131231864;
            }
            else {
                imageResource = 2131231863;
            }
            imageView.setImageResource(imageResource);
            int imageResource2 = n;
            if (!this._whiteLeft) {
                imageResource2 = 2131231864;
            }
            imageView2.setImageResource(imageResource2);
            textView.setText((CharSequence)this._result.getShortDescription(this._whiteLeft));
            return;
        }
        imageView.setVisibility(8);
        imageView2.setVisibility(8);
        textView.setText((CharSequence)"_-_");
    }
}
