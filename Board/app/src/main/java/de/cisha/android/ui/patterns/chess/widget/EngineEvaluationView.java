// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.ui.patterns.chess.widget;

import android.content.res.TypedArray;
import de.cisha.android.ui.patterns.R;
import android.util.AttributeSet;
import android.content.Context;
import android.widget.ImageView;

public class EngineEvaluationView extends ImageView
{
    private static final float EVAL_INCREMENT_FACTOR = 1250.0f;
    private static final float EVAL_SPAN = 4.0f;
    private EvaluationViewType _type;
    
    public EngineEvaluationView(Context obtainStyledAttributes, final AttributeSet set) {
    Label_0044_Outer:
        while (true) {
            super(obtainStyledAttributes, set);
            obtainStyledAttributes = (Context)obtainStyledAttributes.obtainStyledAttributes(set, R.styleable.EngineEvaluationView);
            while (true) {
                Label_0092: {
                    try {
                        switch (((TypedArray)obtainStyledAttributes).getInt(R.styleable.EngineEvaluationView_type, 0)) {
                            case 1: {
                                this._type = EvaluationViewType.WHITE_RIGHT;
                                break;
                            }
                            case 0: {
                                this._type = EvaluationViewType.WHITE_LEFT;
                                break;
                            }
                            default: {
                                break Label_0092;
                            }
                        }
                        while (true) {
                            ((TypedArray)obtainStyledAttributes).recycle();
                            this.init();
                            return;
                            this._type = EvaluationViewType.WHITE_LEFT;
                            continue Label_0044_Outer;
                        }
                    }
                    finally {
                        ((TypedArray)obtainStyledAttributes).recycle();
                    }
                }
                continue;
            }
        }
    }
    
    public EngineEvaluationView(final Context context, final EvaluationViewType type) {
        super(context);
        this._type = type;
        this.init();
    }
    
    private void init() {
        this.setImageResource(this._type._resDrawableId);
    }
    
    public void setEvaluationValue(float n) {
        n = n * 1250.0f + 5000.0f;
        if (n > 0.0f) {
            if (n >= 10000.0f) {
                n = 10000.0f;
            }
        }
        else {
            n = 0.0f;
        }
        this.setImageLevel((int)n);
    }
    
    public void setEvaluationViewType(final EvaluationViewType type) {
        this._type = type;
        this.init();
    }
    
    public enum EvaluationViewType
    {
        WHITE_LEFT(R.drawable.engine_eval_horizontal_white_left), 
        WHITE_RIGHT(R.drawable.engine_eval_horizontal_white_right);
        
        private int _resDrawableId;
        
        private EvaluationViewType(final int resDrawableId) {
            this._resDrawableId = resDrawableId;
        }
    }
}
