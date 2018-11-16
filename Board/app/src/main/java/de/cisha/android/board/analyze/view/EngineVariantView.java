// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.analyze.view;

import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.util.AttributeSet;
import android.content.Context;
import android.widget.TextView;
import de.cisha.android.board.engine.EvaluationInfo;
import android.view.View;
import android.widget.RelativeLayout;

public class EngineVariantView extends RelativeLayout
{
    private static final int MAXIMIZED_LINES = 4;
    private static final int MINIMIZED_LINES = 1;
    private static final String[] ROMAN_NUMS;
    private View _button;
    private View _buttonArrow;
    private EvaluationInfo _evaluationInfo;
    private boolean _minimized;
    private TextView _text;
    private TextView _varNo;
    
    static {
        ROMAN_NUMS = new String[] { "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X" };
    }
    
    public EngineVariantView(final Context context) {
        super(context);
    }
    
    public EngineVariantView(final Context context, final AttributeSet set) {
        super(context, set);
    }
    
    private static String romanNumberOf(final int n) {
        if (n > 0 && n <= 10 && n <= EngineVariantView.ROMAN_NUMS.length) {
            return EngineVariantView.ROMAN_NUMS[n - 1];
        }
        return "";
    }
    
    private void setToMinimized(final boolean minimized) {
        if (minimized != this._minimized) {
            this._minimized = minimized;
            final TextView text = this._text;
            int maxLines;
            if (minimized) {
                maxLines = 1;
            }
            else {
                maxLines = 4;
            }
            text.setMaxLines(maxLines);
            float n;
            if (minimized) {
                n = -90.0f;
            }
            else {
                n = 0.0f;
            }
            float n2;
            if (minimized) {
                n2 = 0.0f;
            }
            else {
                n2 = -90.0f;
            }
            final RotateAnimation rotateAnimation = new RotateAnimation(n, n2, 1, 0.5f, 1, 0.5f);
            rotateAnimation.setDuration(0L);
            rotateAnimation.setFillEnabled(true);
            rotateAnimation.setFillAfter(true);
            this._buttonArrow.startAnimation((Animation)rotateAnimation);
        }
    }
    
    protected void onFinishInflate() {
        this._button = this.findViewById(2131296516);
        this._buttonArrow = this.findViewById(2131296517);
        this._varNo = (TextView)this.findViewById(2131296519);
        this._text = (TextView)this.findViewById(2131296518);
        this._minimized = true;
        this._text.setMaxLines(1);
        this._button.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                EngineVariantView.this.setToMinimized(EngineVariantView.this._minimized ^ true);
            }
        });
    }
    
    public void setEvalInfo(final EvaluationInfo evaluationInfo) {
        this._text.setText((CharSequence)evaluationInfo.getLineToString());
        final TextView varNo = this._varNo;
        final StringBuilder sb = new StringBuilder();
        sb.append(romanNumberOf(evaluationInfo.getLineNumber()));
        sb.append(". (");
        sb.append(evaluationInfo.getEvalString());
        sb.append(")");
        varNo.setText((CharSequence)sb.toString());
        if (this._evaluationInfo != null && this._evaluationInfo.getMove() != null && evaluationInfo != null && evaluationInfo.getMove() != null && !evaluationInfo.getMove().getSEP().equals(this._evaluationInfo.getMove().getSEP())) {
            this.setToMinimized(true);
        }
        this._evaluationInfo = evaluationInfo;
    }
}
