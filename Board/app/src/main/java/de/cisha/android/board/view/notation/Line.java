// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.view.notation;

import android.view.ViewParent;
import android.view.ViewGroup;
import android.graphics.Canvas;
import de.cisha.chess.model.Move;
import java.util.Collection;
import android.view.View;
import android.view.View.MeasureSpec;
import java.util.Iterator;
import java.util.LinkedList;
import android.content.Context;
import android.graphics.Paint;
import java.util.List;
import de.cisha.android.board.view.BlockLinearLayoutHorizontal;

class Line extends BlockLinearLayoutHorizontal
{
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
    
    public Line(final Context context, int i, final int n) {
        super(context);
        this._layer = i;
        this._maxWidth = n;
        this._originMaxWidth = n;
        this._moves = new LinkedList<MoveView>();
        this._childLines = new LinkedList<Line>();
        this._maxWidth -= this._layer * 5 * 2;
        this.setPadding(this._layer * 5, 0, this._layer * 5, 0);
        this._layerPaints = new Paint[this._layer];
        Paint paint;
        for (i = 0; i < this._layer; ++i) {
            paint = new Paint();
            paint.setColor(ColorHelper.getColorForLayer(i));
            this._layerPaints[i] = paint;
        }
        (this._dividerPaint = new Paint()).setColor(ColorHelper.getColorForLayer(this._layer - 1));
        this.setWillNotDraw(false);
    }
    
    private void addChildLine(final Line line) {
        this._childLines.add(line);
    }
    
    private void markDeletion(final boolean b) {
        final Iterator<MoveView> iterator = this.getMoves().iterator();
        while (iterator.hasNext()) {
            iterator.next().markDeletion(b);
        }
    }
    
    public boolean addMoveView(final MoveView moveView) {
        moveView.measure(View.MeasureSpec.makeMeasureSpec(this._maxWidth, 0), View.MeasureSpec.makeMeasureSpec(this._maxWidth, 0));
        if (moveView.getMeasuredWidth() < this._maxWidth) {
            this._moves.add(moveView);
            this._maxWidth -= moveView.getMeasuredWidth();
            this.addView((View)moveView, this._pos++);
            return true;
        }
        return false;
    }
    
    public List<Line> getAllChildLines() {
        final LinkedList<Object> list = new LinkedList<Object>();
        for (final Line line : this._childLines) {
            list.add(line);
            list.addAll(line.getAllChildLines());
        }
        return (List<Line>)list;
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
    
    public void markMovesDeletionStartingWith(final Move move) {
        final Iterator<MoveView> iterator = this.getMoves().iterator();
        boolean b = false;
        while (iterator.hasNext()) {
            final MoveView moveView = iterator.next();
            final boolean b2 = b |= moveView.getMove().equals(move);
            if (b2) {
                moveView.markDeletion(true);
                b = b2;
            }
        }
    }
    
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(ColorHelper.getColorForLayer(this._layer));
        final int measuredWidth = this.getMeasuredWidth();
        final int measuredHeight = this.getMeasuredHeight();
        for (int i = 0; i < this._layerPaints.length; ++i) {
            final Paint paint = this._layerPaints[i];
            final int n = i * 5;
            final float n2 = n;
            final float n3 = this._layer * 5;
            final float n4 = measuredHeight;
            canvas.drawRect(n2, 0.0f, n3, n4, paint);
            canvas.drawRect((float)(measuredWidth - this._layer * 5), 0.0f, (float)(measuredWidth - n), n4, paint);
        }
        if (this._dividerTop) {
            canvas.drawLine((float)((this._layer - 1) * 5), 0.0f, (float)(measuredWidth - (this._layer - 1) * 5), 0.0f, this._dividerPaint);
        }
    }
    
    public boolean reMeasureWidth() {
        this._maxWidth = this._originMaxWidth;
        this._maxWidth -= this._layer * 5 * 2;
        final int measureSpec = View.MeasureSpec.makeMeasureSpec(this._maxWidth, 0);
        final int measureSpec2 = View.MeasureSpec.makeMeasureSpec(this.getHeight(), 0);
        for (final MoveView moveView : this._moves) {
            moveView.measure(measureSpec, measureSpec2);
            if (this._maxWidth < moveView.getMeasuredWidth()) {
                return false;
            }
            this._maxWidth -= moveView.getMeasuredWidth();
        }
        this.resetMargins();
        return true;
    }
    
    public void removeFromParent() {
        final ViewParent parent = this.getParent();
        if (parent != null && parent instanceof ViewGroup) {
            ((ViewGroup)parent).removeView((View)this);
            if (this._previousLine != null) {
                this._previousLine.setNextLine(this.getNextLine());
            }
        }
    }
    
    public void setDividerTop(final boolean dividerTop) {
        this._dividerTop = dividerTop;
    }
    
    public void setLineNumber(final int lineNumber) {
        this._lineNumber = lineNumber;
    }
    
    public void setNextLine(final Line nextLine) {
        this._nextLine = nextLine;
        if (nextLine != null) {
            nextLine._previousLine = this;
        }
    }
    
    public void setParentLine(final Line parentLine) {
        if (parentLine != null) {
            (this._parentLine = parentLine).addChildLine(this);
            return;
        }
        this._parentLine = null;
    }
    
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append(" LineNo ");
        sb.append(this.getLineNumber());
        sb.append(" ");
        sb.append(this._moves.toString());
        return sb.toString();
    }
    
    public void unmarkAllMovesDeletion() {
        this.markDeletion(false);
    }
    
    public void updateChildrenLineNumbers() {
        final Line nextLine = this.getNextLine();
        if (nextLine != null) {
            nextLine.setLineNumber(this._lineNumber + 1);
            nextLine.updateChildrenLineNumbers();
        }
    }
}
