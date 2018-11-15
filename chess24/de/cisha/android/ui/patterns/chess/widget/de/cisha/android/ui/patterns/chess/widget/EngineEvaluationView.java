/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.widget.ImageView
 */
package de.cisha.android.ui.patterns.chess.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import de.cisha.android.ui.patterns.R;

public class EngineEvaluationView
extends ImageView {
    private static final float EVAL_INCREMENT_FACTOR = 1250.0f;
    private static final float EVAL_SPAN = 4.0f;
    private EvaluationViewType _type;

    /*
     * Exception decompiling
     */
    public EngineEvaluationView(Context var1_1, AttributeSet var2_2) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: First case is not immediately after switch.
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:367)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:66)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:374)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:186)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:131)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:378)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:884)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:786)
        // org.benf.cfr.reader.Main.doClass(Main.java:54)
        // org.benf.cfr.reader.Main.main(Main.java:247)
        throw new IllegalStateException("Decompilation failed");
    }

    public EngineEvaluationView(Context context, EvaluationViewType evaluationViewType) {
        super(context);
        this._type = evaluationViewType;
        this.init();
    }

    private void init() {
        this.setImageResource(this._type._resDrawableId);
    }

    public void setEvaluationValue(float f) {
        if ((f = f * 1250.0f + 5000.0f) > 0.0f) {
            if (f >= 10000.0f) {
                f = 10000.0f;
            }
        } else {
            f = 0.0f;
        }
        this.setImageLevel((int)f);
    }

    public void setEvaluationViewType(EvaluationViewType evaluationViewType) {
        this._type = evaluationViewType;
        this.init();
    }

    public static enum EvaluationViewType {
        WHITE_LEFT(R.drawable.engine_eval_horizontal_white_left),
        WHITE_RIGHT(R.drawable.engine_eval_horizontal_white_right);
        
        private int _resDrawableId;

        private EvaluationViewType(int n2) {
            this._resDrawableId = n2;
        }
    }

}
