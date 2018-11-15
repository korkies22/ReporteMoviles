/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.os.Bundle
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.ImageView
 *  android.widget.ImageView$ScaleType
 */
package de.cisha.android.board.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import de.cisha.android.board.R;
import de.cisha.android.board.StateHolder;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.SettingsService;
import de.cisha.android.board.view.BoardView;
import de.cisha.android.board.view.ScaleableGridLayout;
import de.cisha.chess.model.position.Position;
import de.cisha.chess.util.Logger;
import java.lang.reflect.Field;

public class FieldView
extends ScaleableGridLayout
implements StateHolder {
    private static final String FIELDVIEW_PERSPECTIVE_KEY = "FIELDVIEW_PERSPECTIVE_KEY";
    private static final int TOTAL_COLUMNS = 34;
    private BoardView _boardView;
    private boolean _drawCircle = false;
    private Paint _legendPaint = new Paint();
    private boolean _perspective = true;
    private boolean _showLegends = true;

    public FieldView(Context context) {
        super(context, 34, 34);
        this.init();
    }

    public FieldView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 34, 34);
        this.init();
    }

    public FieldView(Context object, BoardView boardView) {
        super((Context)object, 34, 34);
        this._boardView = boardView;
        object = new ScaleableGridLayout.LayoutParams(1, 1, 32, 32);
        this.addView((View)this._boardView, (ViewGroup.LayoutParams)object);
        this.init();
    }

    private void drawActivityIndicatorCircle(Canvas canvas) {
        Paint paint = new Paint();
        if (this._boardView != null && this._boardView.getPosition().getActiveColor()) {
            paint.setColor(-1);
        } else {
            paint.setColor(-16777216);
        }
        Rect rect = this.getBounds(0, 33);
        Paint paint2 = new Paint();
        paint2.setColor(-1);
        canvas.drawCircle((float)(rect.left / 2 + rect.right / 2), (float)(rect.top / 2 + rect.bottom / 2), (float)Math.round((double)rect.height() / 3.9), paint2);
        canvas.drawCircle((float)(rect.left / 2 + rect.right / 2), (float)(rect.top / 2 + rect.bottom / 2), (float)(rect.height() / 4), paint);
    }

    private void drawLegends(Canvas canvas) {
        int n = this._totalCols / 8;
        this._legendPaint.setColor(-1);
        Rect rect = this.getBounds(1, 1);
        float f = rect.right - rect.left;
        int n2 = (int)(0.9f * f);
        this._legendPaint.setTextSize((float)n2);
        for (n2 = 1; n2 < 9; ++n2) {
            int n3 = this._perspective ? n2 : 9 - n2;
            int n4 = n / 2;
            rect = this.getBounds(n2 * n + 1 - n4, this._totalCols - 1);
            CharSequence charSequence = new StringBuilder();
            charSequence.append("");
            charSequence.append((char)(97 + n3 - 1));
            charSequence = ((StringBuilder)charSequence).toString();
            float f2 = rect.left;
            float f3 = rect.bottom;
            float f4 = 0.2f * f;
            canvas.drawText((String)charSequence, f2, f3 - f4, this._legendPaint);
            rect = this.getBounds(0, (n2 - 1) * n + 1 + n4);
            charSequence = new StringBuilder();
            charSequence.append("");
            charSequence.append(9 - n3);
            canvas.drawText(((StringBuilder)charSequence).toString(), (float)rect.left + f4, (float)rect.bottom - 0.6f * f, this._legendPaint);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private int getBoardId(String string) {
        CharSequence charSequence;
        String string2 = "slver_frame_";
        try {
            charSequence = new StringBuilder();
            charSequence.append(ServiceProvider.getInstance().getSettingsService().getBoardType().getPrefix());
            charSequence.append("_frame_");
            CharSequence charSequence2 = charSequence = ((StringBuilder)charSequence).toString();
        }
        catch (RuntimeException runtimeException) {}
        try {
            void var3_5;
            charSequence = new StringBuilder();
            charSequence.append((String)var3_5);
            charSequence.append(string);
            return R.drawable.class.getDeclaredField(((StringBuilder)charSequence).toString()).getInt(new R.drawable());
        }
        catch (Exception exception) {
            Logger.getInstance().debug("", "Error loading board: ", exception);
            return 0;
        }
    }

    private void init() {
        this.setClickable(true);
        this.setLongClickable(true);
        this.setWillNotDraw(false);
        if (this.isInEditMode()) {
            ScaleableGridLayout.LayoutParams layoutParams = new ScaleableGridLayout.LayoutParams(1, 0, 32, 1);
            ImageView imageView = new ImageView(this.getContext());
            imageView.setImageResource(2131231767);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            this.addView((View)imageView, (ViewGroup.LayoutParams)layoutParams);
            layoutParams = new ScaleableGridLayout.LayoutParams(1, 33, 32, 1);
            imageView = new ImageView(this.getContext());
            imageView.setImageResource(2131231764);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            this.addView((View)imageView, (ViewGroup.LayoutParams)layoutParams);
            layoutParams = new ScaleableGridLayout.LayoutParams(0, 0, 1, 34);
            imageView = new ImageView(this.getContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(2131231765);
            this.addView((View)imageView, (ViewGroup.LayoutParams)layoutParams);
            layoutParams = new ScaleableGridLayout.LayoutParams(33, 0, 1, 34);
            imageView = new ImageView(this.getContext());
            imageView.setImageResource(2131231766);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            this.addView((View)imageView, (ViewGroup.LayoutParams)layoutParams);
            this.setBoardView(new BoardView(this.getContext()));
        }
    }

    public void flip() {
        this.flip(this._perspective ^ true);
    }

    public void flip(boolean bl) {
        this._perspective = bl;
        if (this._boardView != null) {
            this._boardView.flip(this._perspective);
        }
        this.invalidate();
    }

    public BoardView getBoardView() {
        return this._boardView;
    }

    public boolean isDrawingActivityIndicator() {
        return this._drawCircle;
    }

    public boolean isShowingLegends() {
        return this._showLegends;
    }

    protected void onDraw(Canvas canvas) {
        if (!this.isInEditMode()) {
            Drawable drawable2 = this.getResources().getDrawable(this.getBoardId("left"));
            drawable2.setBounds(this.getBounds(0, 0, 1, this._totalRows));
            drawable2.draw(canvas);
            drawable2 = this.getResources().getDrawable(this.getBoardId("bottom"));
            drawable2.setBounds(this.getBounds(1, this._totalRows - 1, this._totalCols - 2, 1));
            drawable2.draw(canvas);
            drawable2 = this.getResources().getDrawable(this.getBoardId("top"));
            drawable2.setBounds(this.getBounds(1, 0, this._totalCols - 2, 1));
            drawable2.draw(canvas);
            drawable2 = this.getResources().getDrawable(this.getBoardId("right"));
            drawable2.setBounds(this.getBounds(this._totalCols - 1, 0, 1, this._totalRows));
            drawable2.draw(canvas);
            if (this._drawCircle) {
                this.drawActivityIndicatorCircle(canvas);
            }
            if (this._showLegends) {
                this.drawLegends(canvas);
            }
        }
    }

    protected void onSizeChanged(int n, int n2, int n3, int n4) {
        super.onSizeChanged(n, n2, n3, n4);
        this._width = n;
        this._height = n2;
        this.invalidate();
    }

    @Override
    public void restoreState(Bundle bundle) {
        boolean bl;
        this._perspective = bl = bundle.getBoolean(FIELDVIEW_PERSPECTIVE_KEY, true);
        if (this._boardView != null) {
            this._boardView.flip(bl);
        }
    }

    @Override
    public void saveState(Bundle bundle) {
        bundle.putBoolean(FIELDVIEW_PERSPECTIVE_KEY, this._perspective);
    }

    public void setBoardView(BoardView object) {
        if (this._boardView == null) {
            this.removeView((View)this._boardView);
        }
        this._boardView = object;
        object = new ScaleableGridLayout.LayoutParams(1, 1, 32, this._totalRows - 2);
        this.addView((View)this._boardView, (ViewGroup.LayoutParams)object);
        this.invalidate();
    }

    public void setIsDrawingActivityIndicator(boolean bl) {
        this._drawCircle = bl;
    }

    public void setShowLegends(boolean bl) {
        this._showLegends = bl;
    }
}
