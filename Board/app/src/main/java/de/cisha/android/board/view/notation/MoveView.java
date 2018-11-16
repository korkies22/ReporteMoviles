// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.view.notation;

import java.util.Iterator;
import android.graphics.Color;
import android.widget.LinearLayout.LayoutParams;
import android.view.View;
import de.cisha.android.ui.patterns.text.CustomTextView;
import de.cisha.android.ui.patterns.text.CustomTextViewTextStyle;
import android.content.Context;
import android.widget.TextView;
import de.cisha.chess.model.Move;
import android.widget.LinearLayout;

public class MoveView extends LinearLayout
{
    private static final int PADDING = 5;
    private static int _deletionColor;
    private int _layer;
    private int _markColor;
    private boolean _markUserMoves;
    private Move _move;
    private boolean _showVariation;
    private TextView _textView;
    
    public MoveView(final Context context, final Move move, final int n) {
        this(context, move, n, false);
    }
    
    public MoveView(final Context context, final Move move, final int layer, final boolean markUserMoves) {
        super(context);
        this._showVariation = false;
        this._markUserMoves = false;
        this._layer = layer;
        this.setOrientation(1);
        this.addView((View)(this._textView = new CustomTextView(context, CustomTextViewTextStyle.MOVELIST, CustomTextViewTextStyle.MOVELIST_SELECTED)));
        this._move = move;
        this.setSelected(false);
        this._markUserMoves = markUserMoves;
        this.refreshView();
        this._textView.setCompoundDrawablePadding(5);
    }
    
    private void resetSelectionColor() {
        this.setSelected(this.isSelected());
    }
    
    public Line getLine() {
        return (Line)this.getParent();
    }
    
    public Move getMove() {
        return this._move;
    }
    
    public void mark(final int n) {
        this._markColor = n;
        this._textView.setTextColor(n);
    }
    
    public void markDeletion(final boolean b) {
        if (b) {
            if (MoveView._deletionColor <= 0) {
                MoveView._deletionColor = this.getResources().getColor(2131099797);
            }
            this._textView.setTextColor(MoveView._deletionColor);
            return;
        }
        this.resetSelectionColor();
    }
    
    public void refreshView() {
        final boolean showsVariation = this.showsVariation();
        this.resetSelectionColor();
        int n;
        if (showsVariation) {
            n = 5;
        }
        else {
            n = 0;
        }
        this._textView.setPadding(5, 5, 5, n);
        int bottomMargin;
        if (showsVariation) {
            bottomMargin = 0;
        }
        else {
            bottomMargin = 5;
        }
        final LinearLayout.LayoutParams linearLayout.LayoutParams = (LinearLayout.LayoutParams)this._textView.getLayoutParams();
        linearLayout.LayoutParams.bottomMargin = bottomMargin;
        linearLayout.LayoutParams.topMargin = 5;
        linearLayout.LayoutParams.rightMargin = 5;
        linearLayout.LayoutParams.leftMargin = 5;
        final boolean b = this._markUserMoves && this._move.isUserMove();
        if (this._move.hasSiblings() && !this._move.isVariationStart()) {
            int backgroundColor = ColorHelper.getColorForLayer(this._layer + 1);
            if (b) {
                backgroundColor = Color.argb(50, 0, 74, 175);
            }
            this._textView.setBackgroundColor(backgroundColor);
        }
        else {
            this._textView.setBackgroundResource(0);
            if (b) {
                this._textView.setBackgroundColor(Color.argb(50, 0, 74, 175));
            }
        }
        final TextView textView = this._textView;
        final StringBuilder sb = new StringBuilder();
        String string;
        if (this._move.getPiece().getColor()) {
            final StringBuilder sb2 = new StringBuilder();
            sb2.append(" ");
            sb2.append(this._move.getMoveNumber());
            sb2.append(". ");
            string = sb2.toString();
        }
        else if (this._move.isVariationStart()) {
            string = "\u2026";
        }
        else {
            string = "";
        }
        sb.append(string);
        sb.append(" ");
        sb.append(this._move.getFAN());
        textView.setText((CharSequence)sb.toString());
    }
    
    public void setMarkUserMoves(final boolean markUserMoves) {
        this._markUserMoves = markUserMoves;
    }
    
    public void setSelected(final boolean selected) {
        if (this._move.hasSiblings() && !this._move.isVariationStart()) {
            final TextView textView = this._textView;
            int n;
            if (selected) {
                n = 2131230840;
            }
            else {
                n = 2131230841;
            }
            textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, n, 0);
        }
        super.setSelected(selected);
        if (this._markColor != 0) {
            this._textView.setTextColor(this._markColor);
        }
    }
    
    public void setVariationShown(final boolean showVariation) {
        if (showVariation) {
            final Line line = this.getLine();
            if (line != null) {
                for (final MoveView moveView : line.getMoves()) {
                    if (moveView._showVariation) {
                        moveView._showVariation = false;
                        moveView.refreshView();
                    }
                }
            }
        }
        this._showVariation = showVariation;
        this.refreshView();
    }
    
    public boolean showsVariation() {
        return this._showVariation;
    }
    
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(this._move.getFAN());
        sb.append(" ");
        sb.append(super.toString());
        return sb.toString();
    }
    
    public void unmark() {
        this._markColor = 0;
        this.resetSelectionColor();
    }
}
