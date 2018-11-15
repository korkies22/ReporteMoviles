/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewParent
 */
package de.cisha.android.board.view.notation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import de.cisha.android.board.view.BlockLinearLayoutHorizontal;
import de.cisha.android.board.view.notation.ColorHelper;
import de.cisha.android.board.view.notation.MoveView;
import de.cisha.chess.model.Move;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

class Line
extends BlockLinearLayoutHorizontal {
    private static final int LAYER_INDENT = 5;
    private List<Line> _childLines;
    private Paint _dividerPaint;
    private boolean _dividerTop;
    private int _layer;
    private Paint[] _layerPaints;
    private int _lineNumber;
    private int _maxWidth;
    private List<MoveView> _moves;
    private Line _nextLine;
    private final int _originMaxWidth;
    private Line _parentLine;
    private int _pos;
    private Line _previousLine;

    public Line(Context context, int n, int n2) {
        super(context);
        this._layer = n;
        this._maxWidth = n2;
        this._originMaxWidth = n2;
        this._moves = new LinkedList<MoveView>();
        this._childLines = new LinkedList<Line>();
        this._maxWidth -= this._layer * 5 * 2;
        this.setPadding(this._layer * 5, 0, this._layer * 5, 0);
        this._layerPaints = new Paint[this._layer];
        for (n = 0; n < this._layer; ++n) {
            context = new Paint();
            context.setColor(ColorHelper.getColorForLayer(n));
            this._layerPaints[n] = context;
        }
        this._dividerPaint = new Paint();
        this._dividerPaint.setColor(ColorHelper.getColorForLayer(this._layer - 1));
        this.setWillNotDraw(false);
    }

    private void addChildLine(Line line) {
        this._childLines.add(line);
    }

    private void markDeletion(boolean bl) {
        Iterator<MoveView> iterator = this.getMoves().iterator();
        while (iterator.hasNext()) {
            iterator.next().markDeletion(bl);
        }
    }

    public boolean addMoveView(MoveView moveView) {
        moveView.measure(View.MeasureSpec.makeMeasureSpec((int)this._maxWidth, (int)0), View.MeasureSpec.makeMeasureSpec((int)this._maxWidth, (int)0));
        if (moveView.getMeasuredWidth() < this._maxWidth) {
            this._moves.add(moveView);
            this._maxWidth -= moveView.getMeasuredWidth();
            int n = this._pos;
            this._pos = n + 1;
            this.addView((View)moveView, n);
            return true;
        }
        return false;
    }

    public List<Line> getAllChildLines() {
        LinkedList<Line> linkedList = new LinkedList<Line>();
        for (Line line : this._childLines) {
            linkedList.add(line);
            linkedList.addAll(line.getAllChildLines());
        }
        return linkedList;
    }

    public int getLayer() {
        return this._layer;
    }

    public int getLineNumber() {
        return this._lineNumber;
    }

    public List<MoveView> getMoves() {
        return this._moves;
    }

    public Line getNextLine() {
        return this._nextLine;
    }

    public Line getParentLine() {
        return this._parentLine;
    }

    public void markAllMovesDeletion() {
        this.markDeletion(true);
    }

    public void markMovesDeletionStartingWith(Move move) {
        Iterator<MoveView> iterator = this.getMoves().iterator();
        boolean bl = false;
        while (iterator.hasNext()) {
            boolean bl2;
            MoveView moveView = iterator.next();
            bl = bl2 = bl | moveView.getMove().equals(move);
            if (!bl2) continue;
            moveView.markDeletion(true);
            bl = bl2;
        }
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(ColorHelper.getColorForLayer(this._layer));
        int n = this.getMeasuredWidth();
        int n2 = this.getMeasuredHeight();
        for (int i = 0; i < this._layerPaints.length; ++i) {
            Paint paint = this._layerPaints[i];
            int n3 = i * 5;
            float f = n3;
            float f2 = this._layer * 5;
            float f3 = n2;
            canvas.drawRect(f, 0.0f, f2, f3, paint);
            canvas.drawRect((float)(n - this._layer * 5), 0.0f, (float)(n - n3), f3, paint);
        }
        if (this._dividerTop) {
            canvas.drawLine((float)((this._layer - 1) * 5), 0.0f, (float)(n - (this._layer - 1) * 5), 0.0f, this._dividerPaint);
        }
    }

    public boolean reMeasureWidth() {
        this._maxWidth = this._originMaxWidth;
        this._maxWidth -= this._layer * 5 * 2;
        int n = View.MeasureSpec.makeMeasureSpec((int)this._maxWidth, (int)0);
        int n2 = View.MeasureSpec.makeMeasureSpec((int)this.getHeight(), (int)0);
        for (MoveView moveView : this._moves) {
            moveView.measure(n, n2);
            if (this._maxWidth >= moveView.getMeasuredWidth()) {
                this._maxWidth -= moveView.getMeasuredWidth();
                continue;
            }
            return false;
        }
        this.resetMargins();
        return true;
    }

    public void removeFromParent() {
        ViewParent viewParent = this.getParent();
        if (viewParent != null && viewParent instanceof ViewGroup) {
            ((ViewGroup)viewParent).removeView((View)this);
            if (this._previousLine != null) {
                this._previousLine.setNextLine(this.getNextLine());
            }
        }
    }

    public void setDividerTop(boolean bl) {
        this._dividerTop = bl;
    }

    public void setLineNumber(int n) {
        this._lineNumber = n;
    }

    public void setNextLine(Line line) {
        this._nextLine = line;
        if (line != null) {
            line._previousLine = this;
        }
    }

    public void setParentLine(Line line) {
        if (line != null) {
            this._parentLine = line;
            line.addChildLine(this);
            return;
        }
        this._parentLine = null;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        stringBuilder.append(" LineNo ");
        stringBuilder.append(this.getLineNumber());
        stringBuilder.append(" ");
        stringBuilder.append(this._moves.toString());
        return stringBuilder.toString();
    }

    public void unmarkAllMovesDeletion() {
        this.markDeletion(false);
    }

    public void updateChildrenLineNumbers() {
        Line line = this.getNextLine();
        if (line != null) {
            line.setLineNumber(this._lineNumber + 1);
            line.updateChildrenLineNumbers();
        }
    }
}
