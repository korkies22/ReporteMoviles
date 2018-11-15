/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.graphics.Color
 *  android.graphics.Paint
 *  android.util.AttributeSet
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.FrameLayout
 */
package de.cisha.android.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import de.cisha.android.board.action.BoardAction;

public class PremiumButtonOverlayLayout
extends FrameLayout {
    private static final int OVERLAY_COLOR = Color.argb((int)200, (int)255, (int)255, (int)255);
    private BoardAction _action;
    private View _button;
    private boolean _enableOverlay = true;
    private boolean _firstClickDone;
    private boolean _hideBeforeClickedEnabled = true;
    private OverlayLayout _overlay;

    public PremiumButtonOverlayLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        PremiumButtonOverlayLayout.inflate((Context)this.getContext(), (int)2131427509, (ViewGroup)this);
        PremiumButtonOverlayLayout.inflate((Context)this.getContext(), (int)this.getLayoutResIdForButton(), (ViewGroup)this);
        this._overlay = (OverlayLayout)this.findViewById(2131296801);
        this._button = this.findViewById(2131296802);
        this._button.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                if (PremiumButtonOverlayLayout.this._action != null) {
                    PremiumButtonOverlayLayout.this._action.perform();
                }
            }
        });
    }

    /*
     * Exception decompiling
     */
    private void updateOverlayViews() {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Statement already marked as first in another block
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.markFirstStatementInBlock(Op03SimpleStatement.java:426)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.Misc.markWholeBlock(Misc.java:225)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.ConditionalRewriter.considerAsSimpleIf(ConditionalRewriter.java:622)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.ConditionalRewriter.identifyNonjumpingConditionals(ConditionalRewriter.java:50)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:590)
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

    public void addView(View view) {
        if (this._overlay != null) {
            this._overlay.addView(view);
            return;
        }
        super.addView(view);
    }

    public void addView(View view, int n) {
        if (this._overlay != null) {
            this._overlay.addView(view, n);
            return;
        }
        super.addView(view, n);
    }

    public void addView(View view, int n, int n2) {
        if (this._overlay != null) {
            this._overlay.addView(view, n, n2);
            return;
        }
        super.addView(view, n, n2);
    }

    public void addView(View view, int n, ViewGroup.LayoutParams layoutParams) {
        if (this._overlay != null) {
            this._overlay.addView(view, n, layoutParams);
            return;
        }
        super.addView(view, n, layoutParams);
    }

    public void addView(View view, ViewGroup.LayoutParams layoutParams) {
        if (this._overlay != null) {
            this._overlay.addView(view, layoutParams);
            return;
        }
        super.addView(view, layoutParams);
    }

    protected int getLayoutResIdForButton() {
        return 2131427510;
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this.updateOverlayViews();
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (!this._firstClickDone) {
            this._firstClickDone = true;
            this.updateOverlayViews();
            return this._enableOverlay;
        }
        return false;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        return true;
    }

    public void setHideBeforeClickedEnabled(boolean bl) {
        this._hideBeforeClickedEnabled = bl;
    }

    public void setOverlayButtonAction(BoardAction boardAction) {
        this._action = boardAction;
    }

    public void setPremiumOverlayEnabled(boolean bl) {
        this._enableOverlay = bl;
        this.updateOverlayViews();
    }

    public static class OverlayLayout
    extends FrameLayout {
        private boolean _overlayEnabled;
        private Paint _paint;

        public OverlayLayout(Context context) {
            super(context);
            this.init();
        }

        public OverlayLayout(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.init();
        }

        private void init() {
            this._overlayEnabled = false;
            this._paint = new Paint();
            this._paint.setColor(OVERLAY_COLOR);
        }

        protected void dispatchDraw(Canvas canvas) {
            super.dispatchDraw(canvas);
            if (this._overlayEnabled) {
                canvas.drawPaint(this._paint);
            }
        }

        public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            return this._overlayEnabled;
        }

        public boolean onTouchEvent(MotionEvent motionEvent) {
            return true;
        }

        public void setOverlayEnabled(boolean bl) {
            this._overlayEnabled = bl;
            this.invalidate();
        }
    }

}
