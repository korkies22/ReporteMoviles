/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Color
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewParent
 *  android.widget.LinearLayout
 *  android.widget.LinearLayout$LayoutParams
 *  android.widget.TextView
 */
package de.cisha.android.board.view.notation;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;
import android.widget.TextView;
import de.cisha.android.board.view.notation.ColorHelper;
import de.cisha.android.board.view.notation.Line;
import de.cisha.android.ui.patterns.text.CustomTextView;
import de.cisha.android.ui.patterns.text.CustomTextViewTextStyle;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.Piece;
import java.util.List;

public class MoveView
extends LinearLayout {
    private static final int PADDING = 5;
    private static int _deletionColor;
    private int _layer;
    private int _markColor;
    private boolean _markUserMoves = false;
    private Move _move;
    private boolean _showVariation = false;
    private TextView _textView;

    public MoveView(Context context, Move move, int n) {
        this(context, move, n, false);
    }

    public MoveView(Context context, Move move, int n, boolean bl) {
        super(context);
        this._layer = n;
        this.setOrientation(1);
        this._textView = new CustomTextView(context, CustomTextViewTextStyle.MOVELIST, CustomTextViewTextStyle.MOVELIST_SELECTED);
        this.addView((View)this._textView);
        this._move = move;
        this.setSelected(false);
        this._markUserMoves = bl;
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

    public void mark(int n) {
        this._markColor = n;
        this._textView.setTextColor(n);
    }

    public void markDeletion(boolean bl) {
        if (bl) {
            if (_deletionColor <= 0) {
                _deletionColor = this.getResources().getColor(2131099797);
            }
            this._textView.setTextColor(_deletionColor);
            return;
        }
        this.resetSelectionColor();
    }

    public void refreshView() {
        boolean bl = this.showsVariation();
        this.resetSelectionColor();
        int n = bl ? 5 : 0;
        this._textView.setPadding(5, 5, 5, n);
        n = bl ? 0 : 5;
        Object object = (LinearLayout.LayoutParams)this._textView.getLayoutParams();
        object.bottomMargin = n;
        object.topMargin = 5;
        object.rightMargin = 5;
        object.leftMargin = 5;
        n = this._markUserMoves && this._move.isUserMove() ? 1 : 0;
        if (this._move.hasSiblings() && !this._move.isVariationStart()) {
            int n2 = ColorHelper.getColorForLayer(this._layer + 1);
            if (n != 0) {
                n2 = Color.argb((int)50, (int)0, (int)74, (int)175);
            }
            this._textView.setBackgroundColor(n2);
        } else {
            this._textView.setBackgroundResource(0);
            if (n != 0) {
                this._textView.setBackgroundColor(Color.argb((int)50, (int)0, (int)74, (int)175));
            }
        }
        TextView textView = this._textView;
        StringBuilder stringBuilder = new StringBuilder();
        if (this._move.getPiece().getColor()) {
            object = new StringBuilder();
            object.append(" ");
            object.append(this._move.getMoveNumber());
            object.append(". ");
            object = object.toString();
        } else {
            object = this._move.isVariationStart() ? "\u2026" : "";
        }
        stringBuilder.append((String)object);
        stringBuilder.append(" ");
        stringBuilder.append(this._move.getFAN());
        textView.setText((CharSequence)stringBuilder.toString());
    }

    public void setMarkUserMoves(boolean bl) {
        this._markUserMoves = bl;
    }

    public void setSelected(boolean bl) {
        if (this._move.hasSiblings() && !this._move.isVariationStart()) {
            TextView textView = this._textView;
            int n = bl ? 2131230840 : 2131230841;
            textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, n, 0);
        }
        super.setSelected(bl);
        if (this._markColor != 0) {
            this._textView.setTextColor(this._markColor);
        }
    }

    public void setVariationShown(boolean bl) {
        Object object;
        if (bl && (object = this.getLine()) != null) {
            for (MoveView moveView : object.getMoves()) {
                if (!moveView._showVariation) continue;
                moveView._showVariation = false;
                moveView.refreshView();
            }
        }
        this._showVariation = bl;
        this.refreshView();
    }

    public boolean showsVariation() {
        return this._showVariation;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this._move.getFAN());
        stringBuilder.append(" ");
        stringBuilder.append(super.toString());
        return stringBuilder.toString();
    }

    public void unmark() {
        this._markColor = 0;
        this.resetSelectionColor();
    }
}
