/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.animation.Animation
 *  android.view.animation.RotateAnimation
 *  android.widget.RelativeLayout
 *  android.widget.TextView
 */
package de.cisha.android.board.analyze.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;
import de.cisha.android.board.engine.EvaluationInfo;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.SEP;

public class EngineVariantView
extends RelativeLayout {
    private static final int MAXIMIZED_LINES = 4;
    private static final int MINIMIZED_LINES = 1;
    private static final String[] ROMAN_NUMS = new String[]{"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
    private View _button;
    private View _buttonArrow;
    private EvaluationInfo _evaluationInfo;
    private boolean _minimized;
    private TextView _text;
    private TextView _varNo;

    public EngineVariantView(Context context) {
        super(context);
    }

    public EngineVariantView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    private static String romanNumberOf(int n) {
        if (n > 0 && n <= 10 && n <= ROMAN_NUMS.length) {
            return ROMAN_NUMS[n - 1];
        }
        return "";
    }

    private void setToMinimized(boolean bl) {
        if (bl != this._minimized) {
            this._minimized = bl;
            TextView textView = this._text;
            int n = bl ? 1 : 4;
            textView.setMaxLines(n);
            float f = bl ? -90.0f : 0.0f;
            float f2 = bl ? 0.0f : -90.0f;
            textView = new RotateAnimation(f, f2, 1, 0.5f, 1, 0.5f);
            textView.setDuration(0L);
            textView.setFillEnabled(true);
            textView.setFillAfter(true);
            this._buttonArrow.startAnimation((Animation)textView);
        }
    }

    protected void onFinishInflate() {
        this._button = this.findViewById(2131296516);
        this._buttonArrow = this.findViewById(2131296517);
        this._varNo = (TextView)this.findViewById(2131296519);
        this._text = (TextView)this.findViewById(2131296518);
        this._minimized = true;
        this._text.setMaxLines(1);
        this._button.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                EngineVariantView.this.setToMinimized(EngineVariantView.this._minimized ^ true);
            }
        });
    }

    public void setEvalInfo(EvaluationInfo evaluationInfo) {
        this._text.setText((CharSequence)evaluationInfo.getLineToString());
        TextView textView = this._varNo;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(EngineVariantView.romanNumberOf(evaluationInfo.getLineNumber()));
        stringBuilder.append(". (");
        stringBuilder.append(evaluationInfo.getEvalString());
        stringBuilder.append(")");
        textView.setText((CharSequence)stringBuilder.toString());
        if (this._evaluationInfo != null && this._evaluationInfo.getMove() != null && evaluationInfo != null && evaluationInfo.getMove() != null && !evaluationInfo.getMove().getSEP().equals(this._evaluationInfo.getMove().getSEP())) {
            this.setToMinimized(true);
        }
        this._evaluationInfo = evaluationInfo;
    }

}
