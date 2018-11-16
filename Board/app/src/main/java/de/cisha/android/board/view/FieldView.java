// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.view;

import android.os.Bundle;
import android.graphics.drawable.Drawable;
import android.widget.ImageView.ScaleType;
import android.widget.ImageView;
import de.cisha.chess.util.Logger;
import de.cisha.android.board.R;
import de.cisha.android.board.service.ServiceProvider;
import android.graphics.Rect;
import android.graphics.Canvas;
import android.view.ViewGroup.LayoutParams;
import android.view.View;
import android.util.AttributeSet;
import android.content.Context;
import android.graphics.Paint;
import de.cisha.android.board.StateHolder;

public class FieldView extends ScaleableGridLayout implements StateHolder
{
    private static final String FIELDVIEW_PERSPECTIVE_KEY = "FIELDVIEW_PERSPECTIVE_KEY";
    private static final int TOTAL_COLUMNS = 34;
    private BoardView _boardView;
    private boolean _drawCircle;
    private Paint _legendPaint;
    private boolean _perspective;
    private boolean _showLegends;
    
    public FieldView(final Context context) {
        super(context, 34, 34);
        this._perspective = true;
        this._drawCircle = false;
        this._showLegends = true;
        this._legendPaint = new Paint();
        this.init();
    }
    
    public FieldView(final Context context, final AttributeSet set) {
        super(context, set, 34, 34);
        this._perspective = true;
        this._drawCircle = false;
        this._showLegends = true;
        this._legendPaint = new Paint();
        this.init();
    }
    
    public FieldView(final Context context, final BoardView boardView) {
        super(context, 34, 34);
        this._perspective = true;
        this._drawCircle = false;
        this._showLegends = true;
        this._legendPaint = new Paint();
        this.addView((View)(this._boardView = boardView), (ViewGroup.LayoutParams)new LayoutParams(1, 1, 32, 32));
        this.init();
    }
    
    private void drawActivityIndicatorCircle(final Canvas canvas) {
        final Paint paint = new Paint();
        if (this._boardView != null && this._boardView.getPosition().getActiveColor()) {
            paint.setColor(-1);
        }
        else {
            paint.setColor(-16777216);
        }
        final Rect bounds = this.getBounds(0, 33);
        final Paint paint2 = new Paint();
        paint2.setColor(-1);
        canvas.drawCircle((float)(bounds.left / 2 + bounds.right / 2), (float)(bounds.top / 2 + bounds.bottom / 2), (float)Math.round(bounds.height() / 3.9), paint2);
        canvas.drawCircle((float)(bounds.left / 2 + bounds.right / 2), (float)(bounds.top / 2 + bounds.bottom / 2), (float)(bounds.height() / 4), paint);
    }
    
    private void drawLegends(final Canvas canvas) {
        final int n = this._totalCols / 8;
        this._legendPaint.setColor(-1);
        final Rect bounds = this.getBounds(1, 1);
        final float n2 = bounds.right - bounds.left;
        this._legendPaint.setTextSize((float)(int)(0.9f * n2));
        for (int i = 1; i < 9; ++i) {
            int n3;
            if (this._perspective) {
                n3 = i;
            }
            else {
                n3 = 9 - i;
            }
            final int n4 = n / 2;
            final Rect bounds2 = this.getBounds(i * n + 1 - n4, this._totalCols - 1);
            final StringBuilder sb = new StringBuilder();
            sb.append("");
            sb.append((char)(97 + n3 - 1));
            final String string = sb.toString();
            final float n5 = bounds2.left;
            final float n6 = bounds2.bottom;
            final float n7 = 0.2f * n2;
            canvas.drawText(string, n5, n6 - n7, this._legendPaint);
            final Rect bounds3 = this.getBounds(0, (i - 1) * n + 1 + n4);
            final StringBuilder sb2 = new StringBuilder();
            sb2.append("");
            sb2.append(9 - n3);
            canvas.drawText(sb2.toString(), bounds3.left + n7, bounds3.bottom - 0.6f * n2, this._legendPaint);
        }
    }
    
    private int getBoardId(final String ex) {
        String string = "slver_frame_";
        while (true) {
            try {
                final StringBuilder sb = new StringBuilder();
                sb.append(ServiceProvider.getInstance().getSettingsService().getBoardType().getPrefix());
                sb.append("_frame_");
                string = sb.toString();
                try {
                    final StringBuilder sb2 = new StringBuilder();
                    sb2.append(string);
                    sb2.append((String)ex);
                    return R.drawable.class.getDeclaredField(sb2.toString()).getInt(new R.drawable());
                }
                catch (Exception ex) {
                    Logger.getInstance().debug("", "Error loading board: ", ex);
                    return 0;
                }
            }
            catch (RuntimeException ex2) {
                continue;
            }
            break;
        }
    }
    
    private void init() {
        this.setClickable(true);
        this.setLongClickable(true);
        this.setWillNotDraw(false);
        if (this.isInEditMode()) {
            final LayoutParams layoutParams = new LayoutParams(1, 0, 32, 1);
            final ImageView imageView = new ImageView(this.getContext());
            imageView.setImageResource(2131231767);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            this.addView((View)imageView, (ViewGroup.LayoutParams)layoutParams);
            final LayoutParams layoutParams2 = new LayoutParams(1, 33, 32, 1);
            final ImageView imageView2 = new ImageView(this.getContext());
            imageView2.setImageResource(2131231764);
            imageView2.setScaleType(ImageView.ScaleType.FIT_XY);
            this.addView((View)imageView2, (ViewGroup.LayoutParams)layoutParams2);
            final LayoutParams layoutParams3 = new LayoutParams(0, 0, 1, 34);
            final ImageView imageView3 = new ImageView(this.getContext());
            imageView3.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView3.setImageResource(2131231765);
            this.addView((View)imageView3, (ViewGroup.LayoutParams)layoutParams3);
            final LayoutParams layoutParams4 = new LayoutParams(33, 0, 1, 34);
            final ImageView imageView4 = new ImageView(this.getContext());
            imageView4.setImageResource(2131231766);
            imageView4.setScaleType(ImageView.ScaleType.FIT_XY);
            this.addView((View)imageView4, (ViewGroup.LayoutParams)layoutParams4);
            this.setBoardView(new BoardView(this.getContext()));
        }
    }
    
    public void flip() {
        this.flip(this._perspective ^ true);
    }
    
    public void flip(final boolean perspective) {
        this._perspective = perspective;
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
    
    protected void onDraw(final Canvas canvas) {
        if (!this.isInEditMode()) {
            final Drawable drawable = this.getResources().getDrawable(this.getBoardId("left"));
            drawable.setBounds(this.getBounds(0, 0, 1, this._totalRows));
            drawable.draw(canvas);
            final Drawable drawable2 = this.getResources().getDrawable(this.getBoardId("bottom"));
            drawable2.setBounds(this.getBounds(1, this._totalRows - 1, this._totalCols - 2, 1));
            drawable2.draw(canvas);
            final Drawable drawable3 = this.getResources().getDrawable(this.getBoardId("top"));
            drawable3.setBounds(this.getBounds(1, 0, this._totalCols - 2, 1));
            drawable3.draw(canvas);
            final Drawable drawable4 = this.getResources().getDrawable(this.getBoardId("right"));
            drawable4.setBounds(this.getBounds(this._totalCols - 1, 0, 1, this._totalRows));
            drawable4.draw(canvas);
            if (this._drawCircle) {
                this.drawActivityIndicatorCircle(canvas);
            }
            if (this._showLegends) {
                this.drawLegends(canvas);
            }
        }
    }
    
    protected void onSizeChanged(final int width, final int height, final int n, final int n2) {
        super.onSizeChanged(width, height, n, n2);
        this._width = width;
        this._height = height;
        this.invalidate();
    }
    
    @Override
    public void restoreState(final Bundle bundle) {
        final boolean boolean1 = bundle.getBoolean("FIELDVIEW_PERSPECTIVE_KEY", true);
        this._perspective = boolean1;
        if (this._boardView != null) {
            this._boardView.flip(boolean1);
        }
    }
    
    @Override
    public void saveState(final Bundle bundle) {
        bundle.putBoolean("FIELDVIEW_PERSPECTIVE_KEY", this._perspective);
    }
    
    public void setBoardView(final BoardView boardView) {
        if (this._boardView == null) {
            this.removeView((View)this._boardView);
        }
        this.addView((View)(this._boardView = boardView), (ViewGroup.LayoutParams)new LayoutParams(1, 1, 32, this._totalRows - 2));
        this.invalidate();
    }
    
    public void setIsDrawingActivityIndicator(final boolean drawCircle) {
        this._drawCircle = drawCircle;
    }
    
    public void setShowLegends(final boolean showLegends) {
        this._showLegends = showLegends;
    }
}
