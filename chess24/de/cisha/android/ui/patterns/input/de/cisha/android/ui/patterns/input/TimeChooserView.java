/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Bitmap
 *  android.graphics.BitmapFactory
 *  android.graphics.BitmapShader
 *  android.graphics.Canvas
 *  android.graphics.Color
 *  android.graphics.Paint
 *  android.graphics.Paint$Style
 *  android.graphics.Path
 *  android.graphics.RectF
 *  android.graphics.Shader
 *  android.graphics.Shader$TileMode
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.BaseAdapter
 *  android.widget.ImageView
 *  android.widget.ListAdapter
 *  android.widget.RelativeLayout
 *  android.widget.RelativeLayout$LayoutParams
 *  android.widget.TextView
 */
package de.cisha.android.ui.patterns.input;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import de.cisha.android.ui.patterns.R;
import de.cisha.android.ui.view.HorizontalListView;
import java.util.List;

public class TimeChooserView
extends RelativeLayout {
    private Paint _backGroundInnerColor;
    private Paint _backGroundInnerPaint;
    private Paint _backgroundOuterPaint;
    private Path _backgroundPath;
    private Paint _blackShadow;
    private int _bottomMarginForList;
    private float _density;
    private HorizontalListView _list;
    private TextView _selectorDescription;
    private ImageView _selectorView;
    private int _topMarginForList;

    public TimeChooserView(Context context) {
        super(context);
        this.initViews();
    }

    public TimeChooserView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.initViews();
    }

    private int getPixels(int n) {
        return (int)((float)n * this._density);
    }

    private void initBackgroundShape() {
        int n = this.getWidth();
        int n2 = this.getHeight();
        float f = (float)this._topMarginForList + 0.0f;
        float f2 = n2 - this._bottomMarginForList;
        float f3 = f2 - f;
        float f4 = f3 / 2.0f;
        float f5 = f4 / 3.0f;
        float f6 = f3 / 10.0f;
        float f7 = 1.0f + f5;
        float f8 = f7 + f6;
        float f9 = (float)n - 1.0f;
        f5 = f9 - f5;
        float f10 = f5 - f6;
        f3 = (f3 - f4) / 2.0f + f;
        float f11 = f4 / 2.0f + f3;
        this._backgroundPath = new Path();
        this._backgroundPath.moveTo(f8, f);
        this._backgroundPath.lineTo(f10, f);
        Path path = this._backgroundPath;
        float f12 = f10 + f6;
        float f13 = f + f6;
        path.arcTo(new RectF(f10, f, f12, f13), 270.0f, 90.0f);
        this._backgroundPath.lineTo(f5, f3);
        this._backgroundPath.lineTo(f9, f11);
        this._backgroundPath.lineTo(f5, f4 += f3);
        path = this._backgroundPath;
        f6 = f2 - f6;
        path.lineTo(f5, f6);
        this._backgroundPath.arcTo(new RectF(f10, f6, f12, f2), 0.0f, 90.0f);
        this._backgroundPath.lineTo(f8, f2);
        this._backgroundPath.arcTo(new RectF(f7, f6, f8, f2), 90.0f, 90.0f);
        this._backgroundPath.lineTo(f7, f4);
        this._backgroundPath.lineTo(1.0f, f11);
        this._backgroundPath.lineTo(f7, f3);
        this._backgroundPath.lineTo(f7, f13);
        this._backgroundPath.arcTo(new RectF(f7, f, f8, f13), 180.0f, 90.0f);
        this._backgroundPath.lineTo(f8, f);
        this._backgroundOuterPaint = new Paint();
        this._backgroundOuterPaint.setStrokeWidth((float)this.getPixels(2));
        this._backgroundOuterPaint.setARGB(255, 255, 255, 255);
        this._backgroundOuterPaint.setStyle(Paint.Style.STROKE);
        path = new BitmapShader(BitmapFactory.decodeResource((Resources)this.getContext().getResources(), (int)R.drawable.scrollview_dotted_background), Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        this._backGroundInnerPaint = new Paint();
        this._backGroundInnerPaint.setShader((Shader)path);
        this._backGroundInnerPaint.setColor(Color.argb((int)255, (int)184, (int)184, (int)184));
        this._backGroundInnerPaint.setColor(Color.argb((int)255, (int)0, (int)0, (int)0));
        this._backGroundInnerColor = new Paint();
        this._backGroundInnerColor.setColor(Color.argb((int)244, (int)244, (int)244, (int)244));
    }

    private void initViews() {
        this._density = this.getContext().getResources().getDisplayMetrics().density;
        this.setWillNotDraw(false);
        this._selectorView = new ImageView(this.getContext());
        this._selectorView.setImageResource(R.drawable.timecontrol_selection);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(13, -1);
        this.addView((View)this._selectorView, (ViewGroup.LayoutParams)layoutParams);
        this._selectorDescription = new TextView(this.getContext());
        this._selectorDescription.setTextColor(-1);
        layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(14, -1);
        layoutParams.addRule(12, -1);
        layoutParams.bottomMargin = this.getPixels(2);
        this.addView((View)this._selectorDescription, (ViewGroup.LayoutParams)layoutParams);
        this._list = new HorizontalListView(this.getContext());
        this._list.setSnappingToCenter(true);
        this._list.setCircleScrolling(true);
        layoutParams = new RelativeLayout.LayoutParams(-1, this.getPixels(48));
        layoutParams.addRule(15, -1);
        this._bottomMarginForList = this.getPixels(18);
        layoutParams.topMargin = this._topMarginForList = this.getPixels(7);
        layoutParams.bottomMargin = this._bottomMarginForList;
        layoutParams.rightMargin = this.getPixels(10);
        layoutParams.leftMargin = this.getPixels(10);
        this.addView((View)this._list, (ViewGroup.LayoutParams)layoutParams);
        this.initBackgroundShape();
    }

    public int getSelectedIndex() {
        return this._list.getSelectedItemPosition();
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(this._backgroundPath, this._backGroundInnerColor);
        canvas.drawPath(this._backgroundPath, this._backGroundInnerPaint);
        canvas.drawPath(this._backgroundPath, this._backgroundOuterPaint);
    }

    protected void onSizeChanged(int n, int n2, int n3, int n4) {
        super.onSizeChanged(n, n2, n3, n4);
        this.initBackgroundShape();
    }

    public void setChooserDescription(CharSequence charSequence) {
        this._selectorDescription.setText(charSequence);
    }

    public void setSelectedIndex(int n) {
        if (this._list != null && this._list.getAdapter() != null && n < this._list.getAdapter().getCount()) {
            this._list.setSelection(n);
        }
    }

    public void setValues(List<String> list) {
        this._list.setAdapter((ListAdapter)new MyTimesAdapter(list));
    }

    class MyTimesAdapter
    extends BaseAdapter {
        private List<String> _times;

        public MyTimesAdapter(List<String> list) {
            this._times = list;
        }

        public int getCount() {
            return this._times.size();
        }

        public Object getItem(int n) {
            return this._times.get(n);
        }

        public long getItemId(int n) {
            return 0L;
        }

        public View getView(int n, View view, ViewGroup object) {
            object = (TextView)view;
            view = object;
            if (object == null) {
                view = new TextView(TimeChooserView.this.getContext());
                view.setTextSize(30.0f);
                view.setGravity(17);
            }
            object = new StringBuilder();
            object.append("  ");
            object.append(this._times.get(n));
            object.append("  ");
            view.setText((CharSequence)object.toString());
            view.setSelected(true);
            return view;
        }
    }

}
