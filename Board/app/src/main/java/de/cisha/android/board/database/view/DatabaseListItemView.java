// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.database.view;

import de.cisha.chess.model.Rating;
import de.cisha.chess.model.Country;
import android.graphics.Color;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.util.AttributeSet;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.FrameLayout;

public class DatabaseListItemView extends FrameLayout
{
    private TextView _dateTimeView;
    private ImageView _iconView;
    private TextView _leftElo;
    private ImageView _leftFlag;
    private View _overlayView;
    private TextView _playerLeftName;
    private TextView _playerRightName;
    private TextView _resultText;
    private TextView _rightElo;
    private ImageView _rightFlag;
    private TextView _titleView;
    
    public DatabaseListItemView(final Context context) {
        super(context);
        this.init();
    }
    
    public DatabaseListItemView(final Context context, final AttributeSet set) {
        super(context, set);
        this.init();
    }
    
    private void init() {
        final LinearLayout linearLayout = new LinearLayout(this.getContext());
        int visibility = 0;
        linearLayout.setOrientation(0);
        this.addView((View)linearLayout);
        final LinearLayout linearLayout2 = new LinearLayout(this.getContext());
        linearLayout2.setOrientation(1);
        (this._iconView = new ImageView(this.getContext())).setScaleType(ImageView.ScaleType.FIT_CENTER);
        this._iconView.setBackgroundResource(2131230989);
        this._iconView.setImageResource(2131231440);
        final LinearLayout.LayoutParams linearLayout.LayoutParams = new LinearLayout.LayoutParams(-2, -2);
        final float density = this.getResources().getDisplayMetrics().density;
        final int n = (int)(3.0f * density);
        final int n2 = (int)(5.0f * density);
        this._iconView.setPadding(n, n, n, n);
        linearLayout.LayoutParams.setMargins(0, 0, n, 0);
        linearLayout.LayoutParams.gravity = 17;
        linearLayout.addView((View)this._iconView, (ViewGroup.LayoutParams)linearLayout.LayoutParams);
        final LinearLayout.LayoutParams linearLayout.LayoutParams2 = new LinearLayout.LayoutParams(-1, -2);
        linearLayout2.setPadding(n, 0, n, 0);
        linearLayout.setPadding(0, n2, n2, n2);
        linearLayout.addView((View)linearLayout2, (ViewGroup.LayoutParams)linearLayout.LayoutParams2);
        inflate(this.getContext(), 2131427414, (ViewGroup)linearLayout2);
        inflate(this.getContext(), 2131427413, (ViewGroup)linearLayout2);
        this._playerLeftName = (TextView)this.findViewById(2131296471);
        this._resultText = (TextView)this.findViewById(2131296473);
        this._playerRightName = (TextView)this.findViewById(2131296472);
        this._dateTimeView = (TextView)this.findViewById(2131296467);
        this._titleView = (TextView)this.findViewById(2131296475);
        (this._overlayView = new View(this.getContext())).setBackgroundColor(Color.argb(220, 255, 255, 255));
        final View overlayView = this._overlayView;
        if (this.isEnabled()) {
            visibility = 8;
        }
        overlayView.setVisibility(visibility);
        this.addView(this._overlayView, -1, -1);
    }
    
    public void setDateTimeText(final String text) {
        this._dateTimeView.setText((CharSequence)text);
    }
    
    public void setEnabled(final boolean enabled) {
        super.setEnabled(enabled);
        final View overlayView = this._overlayView;
        int visibility;
        if (enabled) {
            visibility = 8;
        }
        else {
            visibility = 0;
        }
        overlayView.setVisibility(visibility);
    }
    
    public void setIconResourceId(final int imageResource) {
        this._iconView.setImageResource(imageResource);
    }
    
    public void setLeftCountry(final Country country) {
    }
    
    public void setLeftRating(final Rating rating) {
    }
    
    public void setPlayerLeftName(final String text) {
        this._playerLeftName.setText((CharSequence)text);
    }
    
    public void setPlayerRightName(final String text) {
        this._playerRightName.setText((CharSequence)text);
    }
    
    public void setResultText(final String text) {
        this._resultText.setText((CharSequence)text);
    }
    
    public void setRightCountry(final Country country) {
    }
    
    public void setRightRating(final Rating rating) {
    }
    
    public void setTitle(final String text) {
        this._titleView.setText((CharSequence)text);
    }
    
    public void showTitle(final boolean b) {
        if (b) {
            this._titleView.setVisibility(0);
            this._resultText.setVisibility(8);
            this._playerLeftName.setVisibility(8);
            this._playerRightName.setVisibility(8);
            return;
        }
        this._titleView.setVisibility(8);
        this._resultText.setVisibility(0);
        this._playerLeftName.setVisibility(0);
        this._playerRightName.setVisibility(0);
    }
}
