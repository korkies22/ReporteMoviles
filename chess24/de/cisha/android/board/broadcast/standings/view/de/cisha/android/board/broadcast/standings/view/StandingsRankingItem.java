/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Paint
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.ImageView
 *  android.widget.LinearLayout
 */
package de.cisha.android.board.broadcast.standings.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import de.cisha.android.ui.patterns.text.CustomTextView;
import de.cisha.android.ui.patterns.text.CustomTextViewTextStyle;

public class StandingsRankingItem
extends LinearLayout {
    private ImageView _flagView;
    private CustomTextView _viewBoardPointsText;
    private CustomTextView _viewMatchPointsText;
    private CustomTextView _viewRankingNumberText;
    private CustomTextView _viewTeamName;

    public StandingsRankingItem(Context context) {
        super(context);
        this.init();
    }

    public StandingsRankingItem(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init();
    }

    private void init() {
        this.setHardwareLayerType();
        StandingsRankingItem.inflate((Context)this.getContext(), (int)2131427382, (ViewGroup)this);
        this._viewRankingNumberText = (CustomTextView)this.findViewById(2131297048);
        this._viewTeamName = (CustomTextView)this.findViewById(2131297047);
        this._viewMatchPointsText = (CustomTextView)this.findViewById(2131297046);
        this._viewBoardPointsText = (CustomTextView)this.findViewById(2131297045);
        this._flagView = (ImageView)this.findViewById(2131297044);
        this.setPadding(0, 0, 0, (int)this.getResources().getDisplayMetrics().density);
    }

    @SuppressLint(value={"NewApi"})
    private void setHardwareLayerType() {
        if (Build.VERSION.SDK_INT >= 16) {
            this.setLayerType(2, null);
        }
    }

    public void hideLastRow() {
        this._viewBoardPointsText.setVisibility(8);
    }

    public void hideLastTwoRows() {
        this._viewBoardPointsText.setVisibility(8);
        this._viewMatchPointsText.setVisibility(8);
    }

    public void setFlagImage(int n) {
        this._flagView.setImageResource(n);
    }

    public void setIsTableHeader() {
        CustomTextViewTextStyle customTextViewTextStyle = CustomTextViewTextStyle.LIST_ITEM_SECTION_HEADER;
        this._viewBoardPointsText.setBackgroundColor(0);
        this._viewMatchPointsText.setBackgroundColor(0);
        this._viewRankingNumberText.setBackgroundColor(0);
        this._viewTeamName.setBackgroundColor(0);
        this.setBackgroundResource(2131230990);
        this._viewBoardPointsText.setPadding(0, 0, 0, 0);
        this._viewMatchPointsText.setPadding(0, 0, 0, 0);
        this._viewRankingNumberText.setPadding(0, 0, 0, 0);
        this._viewTeamName.setPadding(0, 0, 0, 0);
        this._viewBoardPointsText.setStyle(customTextViewTextStyle);
        this._viewMatchPointsText.setStyle(customTextViewTextStyle);
        this._viewRankingNumberText.setStyle(customTextViewTextStyle);
        this._viewTeamName.setStyle(customTextViewTextStyle);
        this._flagView.setVisibility(8);
        this._viewBoardPointsText.setGravity(1);
        this._viewMatchPointsText.setGravity(1);
        this._viewRankingNumberText.setGravity(1);
        this._viewTeamName.setGravity(1);
    }

    public void setItemText(String string, String string2, String string3) {
        this._viewRankingNumberText.setText((CharSequence)string);
        this._viewMatchPointsText.setText((CharSequence)string3);
        this._viewTeamName.setText((CharSequence)string2);
    }

    public void setItemText(String string, String string2, String string3, String string4) {
        this.setItemText(string, string2, string3);
        this._viewBoardPointsText.setText((CharSequence)string4);
    }

    public void setNameTextStyle(CustomTextViewTextStyle customTextViewTextStyle) {
        this._viewTeamName.setStyle(customTextViewTextStyle);
    }

    public void setShowsFlag(boolean bl) {
        if (bl) {
            this._flagView.setVisibility(0);
            return;
        }
        this._flagView.setVisibility(8);
    }
}
