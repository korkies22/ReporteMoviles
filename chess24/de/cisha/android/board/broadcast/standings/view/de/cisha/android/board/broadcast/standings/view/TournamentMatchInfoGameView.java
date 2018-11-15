/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Color
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.ImageView
 *  android.widget.LinearLayout
 *  android.widget.TextView
 */
package de.cisha.android.board.broadcast.standings.view;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import de.cisha.android.ui.patterns.chess.widget.EngineEvaluationView;
import de.cisha.chess.model.GameResult;

public class TournamentMatchInfoGameView
extends LinearLayout {
    private float _eval;
    private GameResult _result;
    private boolean _running;
    private boolean _showEval = false;
    private boolean _whiteLeft;

    public TournamentMatchInfoGameView(Context context, boolean bl, boolean bl2, float f) {
        super(context);
        this._whiteLeft = bl;
        this._running = bl2;
        this._eval = f;
        this._showEval = true;
        this._result = GameResult.NO_RESULT;
        this.init();
    }

    public TournamentMatchInfoGameView(Context context, boolean bl, boolean bl2, GameResult gameResult) {
        super(context);
        this._whiteLeft = bl;
        this._running = bl2;
        this._result = gameResult;
        this.init();
    }

    private void init() {
        this.setOrientation(0);
        this.setGravity(17);
        this.setBackgroundColor(Color.rgb((int)191, (int)191, (int)191));
        if (!this._showEval) {
            TournamentMatchInfoGameView.inflate((Context)this.getContext(), (int)2131427393, (ViewGroup)this);
            TextView textView = (TextView)this.findViewById(2131297178);
            ImageView imageView = (ImageView)this.findViewById(2131297179);
            ImageView imageView2 = (ImageView)this.findViewById(2131297180);
            if (this._result != GameResult.NO_RESULT) {
                boolean bl = this._whiteLeft;
                int n = 2131231863;
                int n2 = bl ? 2131231864 : 2131231863;
                imageView.setImageResource(n2);
                n2 = n;
                if (!this._whiteLeft) {
                    n2 = 2131231864;
                }
                imageView2.setImageResource(n2);
                textView.setText((CharSequence)this._result.getShortDescription(this._whiteLeft));
                return;
            }
            imageView.setVisibility(8);
            imageView2.setVisibility(8);
            textView.setText((CharSequence)"_-_");
            return;
        }
        Context context = this.getContext();
        Object object = this._whiteLeft ? EngineEvaluationView.EvaluationViewType.WHITE_LEFT : EngineEvaluationView.EvaluationViewType.WHITE_RIGHT;
        object = new EngineEvaluationView(context, (EngineEvaluationView.EvaluationViewType)((Object)object));
        object.setEvaluationValue(this._eval);
        this.addView((View)object);
    }
}
