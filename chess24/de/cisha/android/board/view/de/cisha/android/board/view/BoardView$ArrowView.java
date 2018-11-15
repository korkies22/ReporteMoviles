/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.graphics.Color
 *  android.graphics.Paint
 *  android.graphics.Paint$Style
 *  android.graphics.Path
 *  android.graphics.Path$FillType
 *  android.graphics.Point
 *  android.graphics.Rect
 *  android.view.View
 */
package de.cisha.android.board.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.View;
import de.cisha.android.board.model.Arrow;
import de.cisha.android.board.model.ArrowContainer;
import de.cisha.android.board.model.BoardMarks;
import de.cisha.android.board.view.BoardView;
import de.cisha.chess.model.SEP;
import de.cisha.chess.model.Square;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

private class BoardView.ArrowView
extends View {
    Set<Arrow> arrows;
    private Paint markerPaint;
    private Path path;
    private Point point1;
    private Point point2;
    private Point point3;
    private Point wayFromStartToFinish;

    public BoardView.ArrowView(Context context) {
        super(context);
        this.wayFromStartToFinish = new Point();
        this.point1 = new Point();
        this.point2 = new Point();
        this.point3 = new Point();
        this.markerPaint = new Paint();
        this.path = new Path();
        this.arrows = new HashSet<Arrow>();
        this.setWillNotDraw(false);
    }

    protected void onDraw(Canvas canvas) {
        this.arrows.clear();
        Collection<Arrow> collection = BoardView.this._localMarkings.getArrowContainer().getAllArrows();
        if (collection != null) {
            this.arrows.addAll(collection);
        }
        if (BoardView.this._externalBoardMarkings != null && (collection = BoardView.this._externalBoardMarkings.getArrowContainer().getAllArrows()) != null) {
            this.arrows.addAll(collection);
        }
        for (Arrow arrow : this.arrows) {
            int n = arrow.getColor();
            this.markerPaint.setColor(Color.argb((int)128, (int)Color.red((int)n), (int)Color.green((int)n), (int)Color.blue((int)n)));
            Serializable serializable = arrow.getSep();
            Square square = serializable.getStartSquare();
            serializable = serializable.getEndSquare();
            Rect object = BoardView.this.getBounds(square.getColumn(), square.getRow());
            serializable = BoardView.this.getBounds(serializable.getColumn(), serializable.getRow());
            n = object.width();
            this.markerPaint.setStrokeWidth((float)Math.max(n / 7, 1));
            this.markerPaint.setStyle(Paint.Style.FILL);
            this.markerPaint.setAntiAlias(true);
            this.wayFromStartToFinish.x = serializable.centerX() - object.centerX();
            this.wayFromStartToFinish.y = serializable.centerY() - object.centerY();
            double d = Math.sqrt(this.wayFromStartToFinish.x * this.wayFromStartToFinish.x + this.wayFromStartToFinish.y * this.wayFromStartToFinish.y);
            float f = (float)false;
            d = (double)n / d * (double)3;
            this.wayFromStartToFinish.x = (int)Math.round((double)this.wayFromStartToFinish.x * d);
            this.wayFromStartToFinish.y = (int)Math.round((double)this.wayFromStartToFinish.y * d);
            this.point1.x = serializable.centerX() + (int)((float)(this.wayFromStartToFinish.x / 8) * f);
            this.point1.y = serializable.centerY() + (int)((float)(this.wayFromStartToFinish.y / 8) * f);
            this.point2.x = serializable.centerX() - this.wayFromStartToFinish.y / 8 - this.wayFromStartToFinish.x / 5;
            this.point2.y = serializable.centerY() + this.wayFromStartToFinish.x / 8 - this.wayFromStartToFinish.y / 5;
            this.point3.x = serializable.centerX() + this.wayFromStartToFinish.y / 8 - this.wayFromStartToFinish.x / 5;
            this.point3.y = serializable.centerY() - this.wayFromStartToFinish.x / 8 - this.wayFromStartToFinish.y / 5;
            this.path.reset();
            this.path.setFillType(Path.FillType.EVEN_ODD);
            this.path.moveTo((float)this.point1.x, (float)this.point1.y);
            this.path.lineTo((float)this.point2.x, (float)this.point2.y);
            this.path.lineTo((float)this.point3.x, (float)this.point3.y);
            this.path.lineTo((float)this.point1.x, (float)this.point1.y);
            this.path.close();
            canvas.drawLine((float)object.centerX(), (float)object.centerY(), (float)(serializable.centerX() - this.wayFromStartToFinish.x / 5), (float)(serializable.centerY() - this.wayFromStartToFinish.y / 5), this.markerPaint);
            canvas.drawPath(this.path, this.markerPaint);
        }
    }
}
