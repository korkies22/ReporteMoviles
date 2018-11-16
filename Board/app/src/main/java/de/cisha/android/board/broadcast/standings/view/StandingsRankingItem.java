// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.standings.view;

import de.cisha.android.ui.patterns.text.CustomTextViewTextStyle;
import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.os.Build.VERSION;
import android.view.ViewGroup;
import android.util.AttributeSet;
import android.content.Context;
import de.cisha.android.ui.patterns.text.CustomTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class StandingsRankingItem extends LinearLayout
{
    private ImageView _flagView;
    private CustomTextView _viewBoardPointsText;
    private CustomTextView _viewMatchPointsText;
    private CustomTextView _viewRankingNumberText;
    private CustomTextView _viewTeamName;
    
    public StandingsRankingItem(final Context context) {
        super(context);
        this.init();
    }
    
    public StandingsRankingItem(final Context context, final AttributeSet set) {
        super(context, set);
        this.init();
    }
    
    private void init() {
        this.setHardwareLayerType();
        inflate(this.getContext(), 2131427382, (ViewGroup)this);
        this._viewRankingNumberText = (CustomTextView)this.findViewById(2131297048);
        this._viewTeamName = (CustomTextView)this.findViewById(2131297047);
        this._viewMatchPointsText = (CustomTextView)this.findViewById(2131297046);
        this._viewBoardPointsText = (CustomTextView)this.findViewById(2131297045);
        this._flagView = (ImageView)this.findViewById(2131297044);
        this.setPadding(0, 0, 0, (int)this.getResources().getDisplayMetrics().density);
    }
    
    @SuppressLint({ "NewApi" })
    private void setHardwareLayerType() {
        if (Build.VERSION.SDK_INT >= 16) {
            this.setLayerType(2, (Paint)null);
        }
    }
    
    public void hideLastRow() {
        this._viewBoardPointsText.setVisibility(8);
    }
    
    public void hideLastTwoRows() {
        this._viewBoardPointsText.setVisibility(8);
        this._viewMatchPointsText.setVisibility(8);
    }
    
    public void setFlagImage(final int imageResource) {
        this._flagView.setImageResource(imageResource);
    }
    
    public void setIsTableHeader() {
        final CustomTextViewTextStyle list_ITEM_SECTION_HEADER = CustomTextViewTextStyle.LIST_ITEM_SECTION_HEADER;
        this._viewBoardPointsText.setBackgroundColor(0);
        this._viewMatchPointsText.setBackgroundColor(0);
        this._viewRankingNumberText.setBackgroundColor(0);
        this._viewTeamName.setBackgroundColor(0);
        this.setBackgroundResource(2131230990);
        this._viewBoardPointsText.setPadding(0, 0, 0, 0);
        this._viewMatchPointsText.setPadding(0, 0, 0, 0);
        this._viewRankingNumberText.setPadding(0, 0, 0, 0);
        this._viewTeamName.setPadding(0, 0, 0, 0);
        this._viewBoardPointsText.setStyle(list_ITEM_SECTION_HEADER);
        this._viewMatchPointsText.setStyle(list_ITEM_SECTION_HEADER);
        this._viewRankingNumberText.setStyle(list_ITEM_SECTION_HEADER);
        this._viewTeamName.setStyle(list_ITEM_SECTION_HEADER);
        this._flagView.setVisibility(8);
        this._viewBoardPointsText.setGravity(1);
        this._viewMatchPointsText.setGravity(1);
        this._viewRankingNumberText.setGravity(1);
        this._viewTeamName.setGravity(1);
    }
    
    public void setItemText(final String text, final String text2, final String text3) {
        this._viewRankingNumberText.setText((CharSequence)text);
        this._viewMatchPointsText.setText((CharSequence)text3);
        this._viewTeamName.setText((CharSequence)text2);
    }
    
    public void setItemText(final String s, final String s2, final String s3, final String text) {
        this.setItemText(s, s2, s3);
        this._viewBoardPointsText.setText((CharSequence)text);
    }
    
    public void setNameTextStyle(final CustomTextViewTextStyle style) {
        this._viewTeamName.setStyle(style);
    }
    
    public void setShowsFlag(final boolean b) {
        if (b) {
            this._flagView.setVisibility(0);
            return;
        }
        this._flagView.setVisibility(8);
    }
}
