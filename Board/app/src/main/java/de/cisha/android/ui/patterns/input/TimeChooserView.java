// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.ui.patterns.input;

import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import java.util.List;
import android.graphics.Canvas;
import android.view.ViewGroup.LayoutParams;
import android.view.View;
import android.widget.RelativeLayout.LayoutParams;
import android.graphics.Color;
import android.graphics.Shader;
import android.graphics.BitmapShader;
import android.graphics.Shader.TileMode;
import android.graphics.BitmapFactory;
import de.cisha.android.ui.patterns.R;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;
import de.cisha.android.ui.view.HorizontalListView;
import android.graphics.Path;
import android.graphics.Paint;
import android.widget.RelativeLayout;

public class TimeChooserView extends RelativeLayout
{
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
    
    public TimeChooserView(final Context context) {
        super(context);
        this.initViews();
    }
    
    public TimeChooserView(final Context context, final AttributeSet set) {
        super(context, set);
        this.initViews();
    }
    
    private int getPixels(final int n) {
        return (int)(n * this._density);
    }
    
    private void initBackgroundShape() {
        final int width = this.getWidth();
        final int height = this.getHeight();
        final float n = this._topMarginForList + 0.0f;
        final float n2 = height - this._bottomMarginForList;
        final float n3 = n2 - n;
        final float n4 = n3 / 2.0f;
        final float n5 = n4 / 3.0f;
        final float n6 = n3 / 10.0f;
        final float n7 = 1.0f + n5;
        final float n8 = n7 + n6;
        final float n9 = width - 1.0f;
        final float n10 = n9 - n5;
        final float n11 = n10 - n6;
        final float n12 = (n3 - n4) / 2.0f + n;
        final float n13 = n4 / 2.0f + n12;
        final float n14 = n4 + n12;
        (this._backgroundPath = new Path()).moveTo(n8, n);
        this._backgroundPath.lineTo(n11, n);
        final Path backgroundPath = this._backgroundPath;
        final float n15 = n11 + n6;
        final float n16 = n + n6;
        backgroundPath.arcTo(new RectF(n11, n, n15, n16), 270.0f, 90.0f);
        this._backgroundPath.lineTo(n10, n12);
        this._backgroundPath.lineTo(n9, n13);
        this._backgroundPath.lineTo(n10, n14);
        final Path backgroundPath2 = this._backgroundPath;
        final float n17 = n2 - n6;
        backgroundPath2.lineTo(n10, n17);
        this._backgroundPath.arcTo(new RectF(n11, n17, n15, n2), 0.0f, 90.0f);
        this._backgroundPath.lineTo(n8, n2);
        this._backgroundPath.arcTo(new RectF(n7, n17, n8, n2), 90.0f, 90.0f);
        this._backgroundPath.lineTo(n7, n14);
        this._backgroundPath.lineTo(1.0f, n13);
        this._backgroundPath.lineTo(n7, n12);
        this._backgroundPath.lineTo(n7, n16);
        this._backgroundPath.arcTo(new RectF(n7, n, n8, n16), 180.0f, 90.0f);
        this._backgroundPath.lineTo(n8, n);
        (this._backgroundOuterPaint = new Paint()).setStrokeWidth((float)this.getPixels(2));
        this._backgroundOuterPaint.setARGB(255, 255, 255, 255);
        this._backgroundOuterPaint.setStyle(Paint.Style.STROKE);
        (this._backGroundInnerPaint = new Paint()).setShader((Shader)new BitmapShader(BitmapFactory.decodeResource(this.getContext().getResources(), R.drawable.scrollview_dotted_background), Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
        this._backGroundInnerPaint.setColor(Color.argb(255, 184, 184, 184));
        this._backGroundInnerPaint.setColor(Color.argb(255, 0, 0, 0));
        (this._backGroundInnerColor = new Paint()).setColor(Color.argb(244, 244, 244, 244));
    }
    
    private void initViews() {
        this._density = this.getContext().getResources().getDisplayMetrics().density;
        this.setWillNotDraw(false);
        (this._selectorView = new ImageView(this.getContext())).setImageResource(R.drawable.timecontrol_selection);
        final RelativeLayout.LayoutParams relativeLayout.LayoutParams = new RelativeLayout.LayoutParams(-2, -2);
        relativeLayout.LayoutParams.addRule(13, -1);
        this.addView((View)this._selectorView, (ViewGroup.LayoutParams)relativeLayout.LayoutParams);
        (this._selectorDescription = new TextView(this.getContext())).setTextColor(-1);
        final RelativeLayout.LayoutParams relativeLayout.LayoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
        relativeLayout.LayoutParams2.addRule(14, -1);
        relativeLayout.LayoutParams2.addRule(12, -1);
        relativeLayout.LayoutParams2.bottomMargin = this.getPixels(2);
        this.addView((View)this._selectorDescription, (ViewGroup.LayoutParams)relativeLayout.LayoutParams2);
        (this._list = new HorizontalListView(this.getContext())).setSnappingToCenter(true);
        this._list.setCircleScrolling(true);
        final RelativeLayout.LayoutParams relativeLayout.LayoutParams3 = new RelativeLayout.LayoutParams(-1, this.getPixels(48));
        relativeLayout.LayoutParams3.addRule(15, -1);
        this._bottomMarginForList = this.getPixels(18);
        this._topMarginForList = this.getPixels(7);
        relativeLayout.LayoutParams3.topMargin = this._topMarginForList;
        relativeLayout.LayoutParams3.bottomMargin = this._bottomMarginForList;
        relativeLayout.LayoutParams3.rightMargin = this.getPixels(10);
        relativeLayout.LayoutParams3.leftMargin = this.getPixels(10);
        this.addView((View)this._list, (ViewGroup.LayoutParams)relativeLayout.LayoutParams3);
        this.initBackgroundShape();
    }
    
    public int getSelectedIndex() {
        return this._list.getSelectedItemPosition();
    }
    
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(this._backgroundPath, this._backGroundInnerColor);
        canvas.drawPath(this._backgroundPath, this._backGroundInnerPaint);
        canvas.drawPath(this._backgroundPath, this._backgroundOuterPaint);
    }
    
    protected void onSizeChanged(final int n, final int n2, final int n3, final int n4) {
        super.onSizeChanged(n, n2, n3, n4);
        this.initBackgroundShape();
    }
    
    public void setChooserDescription(final CharSequence text) {
        this._selectorDescription.setText(text);
    }
    
    public void setSelectedIndex(final int selection) {
        if (this._list != null && this._list.getAdapter() != null && selection < this._list.getAdapter().getCount()) {
            this._list.setSelection(selection);
        }
    }
    
    public void setValues(final List<String> list) {
        this._list.setAdapter((ListAdapter)new MyTimesAdapter(list));
    }
    
    class MyTimesAdapter extends BaseAdapter
    {
        private List<String> _times;
        
        public MyTimesAdapter(final List<String> times) {
            this._times = times;
        }
        
        public int getCount() {
            return this._times.size();
        }
        
        public Object getItem(final int n) {
            return this._times.get(n);
        }
        
        public long getItemId(final int n) {
            return 0L;
        }
        
        public View getView(final int n, final View view, final ViewGroup viewGroup) {
            TextView textView;
            if ((textView = (TextView)view) == null) {
                textView = new TextView(TimeChooserView.this.getContext());
                textView.setTextSize(30.0f);
                textView.setGravity(17);
            }
            final StringBuilder sb = new StringBuilder();
            sb.append("  ");
            sb.append(this._times.get(n));
            sb.append("  ");
            textView.setText((CharSequence)sb.toString());
            textView.setSelected(true);
            return (View)textView;
        }
    }
}
