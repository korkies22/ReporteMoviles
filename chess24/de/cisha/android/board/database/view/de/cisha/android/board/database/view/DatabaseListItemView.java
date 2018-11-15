/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Color
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.FrameLayout
 *  android.widget.ImageView
 *  android.widget.ImageView$ScaleType
 *  android.widget.LinearLayout
 *  android.widget.LinearLayout$LayoutParams
 *  android.widget.TextView
 */
package de.cisha.android.board.database.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import de.cisha.chess.model.Country;
import de.cisha.chess.model.Rating;

public class DatabaseListItemView
extends FrameLayout {
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

    public DatabaseListItemView(Context context) {
        super(context);
        this.init();
    }

    public DatabaseListItemView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init();
    }

    private void init() {
        LinearLayout linearLayout = new LinearLayout(this.getContext());
        int n = 0;
        linearLayout.setOrientation(0);
        this.addView((View)linearLayout);
        LinearLayout linearLayout2 = new LinearLayout(this.getContext());
        linearLayout2.setOrientation(1);
        this._iconView = new ImageView(this.getContext());
        this._iconView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        this._iconView.setBackgroundResource(2131230989);
        this._iconView.setImageResource(2131231440);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
        float f = this.getResources().getDisplayMetrics().density;
        int n2 = (int)(3.0f * f);
        int n3 = (int)(5.0f * f);
        this._iconView.setPadding(n2, n2, n2, n2);
        layoutParams.setMargins(0, 0, n2, 0);
        layoutParams.gravity = 17;
        linearLayout.addView((View)this._iconView, (ViewGroup.LayoutParams)layoutParams);
        layoutParams = new LinearLayout.LayoutParams(-1, -2);
        linearLayout2.setPadding(n2, 0, n2, 0);
        linearLayout.setPadding(0, n3, n3, n3);
        linearLayout.addView((View)linearLayout2, (ViewGroup.LayoutParams)layoutParams);
        DatabaseListItemView.inflate((Context)this.getContext(), (int)2131427414, (ViewGroup)linearLayout2);
        DatabaseListItemView.inflate((Context)this.getContext(), (int)2131427413, (ViewGroup)linearLayout2);
        this._playerLeftName = (TextView)this.findViewById(2131296471);
        this._resultText = (TextView)this.findViewById(2131296473);
        this._playerRightName = (TextView)this.findViewById(2131296472);
        this._dateTimeView = (TextView)this.findViewById(2131296467);
        this._titleView = (TextView)this.findViewById(2131296475);
        this._overlayView = new View(this.getContext());
        this._overlayView.setBackgroundColor(Color.argb((int)220, (int)255, (int)255, (int)255));
        linearLayout = this._overlayView;
        if (this.isEnabled()) {
            n = 8;
        }
        linearLayout.setVisibility(n);
        this.addView(this._overlayView, -1, -1);
    }

    public void setDateTimeText(String string) {
        this._dateTimeView.setText((CharSequence)string);
    }

    public void setEnabled(boolean bl) {
        super.setEnabled(bl);
        View view = this._overlayView;
        int n = bl ? 8 : 0;
        view.setVisibility(n);
    }

    public void setIconResourceId(int n) {
        this._iconView.setImageResource(n);
    }

    public void setLeftCountry(Country country) {
    }

    public void setLeftRating(Rating rating) {
    }

    public void setPlayerLeftName(String string) {
        this._playerLeftName.setText((CharSequence)string);
    }

    public void setPlayerRightName(String string) {
        this._playerRightName.setText((CharSequence)string);
    }

    public void setResultText(String string) {
        this._resultText.setText((CharSequence)string);
    }

    public void setRightCountry(Country country) {
    }

    public void setRightRating(Rating rating) {
    }

    public void setTitle(String string) {
        this._titleView.setText((CharSequence)string);
    }

    public void showTitle(boolean bl) {
        if (bl) {
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
