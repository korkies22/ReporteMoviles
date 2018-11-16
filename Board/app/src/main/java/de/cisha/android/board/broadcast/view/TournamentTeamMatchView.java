// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.view;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.os.Build.VERSION;
import android.view.ViewGroup;
import java.io.Serializable;
import android.util.AttributeSet;
import android.content.Context;
import android.widget.TextView;
import android.view.View;
import android.widget.LinearLayout;

public class TournamentTeamMatchView extends LinearLayout
{
    private int COLOR_DEFAULT_TEAM_BACKGROUND;
    private int COLOR_DEFAULT_TEAM_TEXT;
    private int COLOR_LEADING_TEAM_BACKGROUND;
    private int COLOR_LEADING_TEAM_TEXT;
    private int COLOR_TIE_TEAM_BACKGROUND;
    private boolean _flagIsLive;
    private View _viewLiveIndicator;
    private TextView _viewTeamNameLeftText;
    private TextView _viewTeamNameRightText;
    private TextView _viewTeamPointsLeftText;
    private TextView _viewTeamPointsRightText;
    
    public TournamentTeamMatchView(final Context context) {
        super(context);
        this.init();
    }
    
    public TournamentTeamMatchView(final Context context, final AttributeSet set) {
        super(context, set);
        this.init();
    }
    
    private String getPointString(final float n) {
        final int n2 = (int)n;
        final boolean b = n - n2 > 0.1;
        final StringBuilder sb = new StringBuilder();
        sb.append("");
        Serializable value;
        if (n2 == 0 && b) {
            value = "";
        }
        else {
            value = n2;
        }
        sb.append(value);
        String s;
        if (b) {
            s = "�";
        }
        else {
            s = "";
        }
        sb.append(s);
        return sb.toString();
    }
    
    private void init() {
        this.setHardwareLayerType();
        this.COLOR_LEADING_TEAM_BACKGROUND = this.getResources().getColor(2131099690);
        this.COLOR_DEFAULT_TEAM_BACKGROUND = this.getResources().getColor(2131099689);
        this.COLOR_TIE_TEAM_BACKGROUND = this.getResources().getColor(2131099692);
        this.COLOR_LEADING_TEAM_TEXT = this.getResources().getColor(2131099691);
        this.COLOR_DEFAULT_TEAM_TEXT = this.getResources().getColor(2131099846);
        this.setOrientation(1);
        inflate(this.getContext(), 2131427395, (ViewGroup)this);
        this._viewTeamNameLeftText = (TextView)this.findViewById(2131296406);
        this._viewTeamNameRightText = (TextView)this.findViewById(2131296407);
        this._viewTeamPointsLeftText = (TextView)this.findViewById(2131296408);
        this._viewTeamPointsRightText = (TextView)this.findViewById(2131296409);
        this._viewLiveIndicator = this.findViewById(2131296405);
    }
    
    @SuppressLint({ "NewApi" })
    private void setHardwareLayerType() {
        if (Build.VERSION.SDK_INT >= 16) {
            this.setLayerType(2, (Paint)null);
        }
    }
    
    public void setMatchIsOngoing(final boolean flagIsLive) {
        this._flagIsLive = flagIsLive;
        final View viewLiveIndicator = this._viewLiveIndicator;
        int visibility;
        if (flagIsLive) {
            visibility = 0;
        }
        else {
            visibility = 4;
        }
        viewLiveIndicator.setVisibility(visibility);
    }
    
    public void setPointsText(final float n, final float n2) {
        this._viewTeamPointsLeftText.setText((CharSequence)this.getPointString(n));
        this._viewTeamPointsRightText.setText((CharSequence)this.getPointString(n2));
        final boolean flagIsLive = this._flagIsLive;
        final boolean b = false;
        final boolean b2 = !flagIsLive && n == n2;
        final boolean b3 = !this._flagIsLive && n > n2;
        boolean b4 = b;
        if (!this._flagIsLive) {
            b4 = b;
            if (n2 > n) {
                b4 = true;
            }
        }
        if (!b4 && !b3) {
            int n3;
            if (b2) {
                n3 = this.COLOR_TIE_TEAM_BACKGROUND;
            }
            else {
                n3 = this.COLOR_DEFAULT_TEAM_BACKGROUND;
            }
            this._viewTeamPointsLeftText.setTextColor(this.COLOR_DEFAULT_TEAM_TEXT);
            this._viewTeamPointsLeftText.setBackgroundColor(n3);
            this._viewTeamPointsRightText.setTextColor(this.COLOR_DEFAULT_TEAM_TEXT);
            this._viewTeamPointsRightText.setBackgroundColor(n3);
            this._viewTeamNameLeftText.setTextColor(this.COLOR_DEFAULT_TEAM_TEXT);
            this._viewTeamNameLeftText.setBackgroundColor(n3);
            this._viewTeamNameRightText.setTextColor(this.COLOR_DEFAULT_TEAM_TEXT);
            this._viewTeamNameRightText.setBackgroundColor(n3);
            return;
        }
        int n4;
        if (b4) {
            n4 = this.COLOR_LEADING_TEAM_TEXT;
        }
        else {
            n4 = this.COLOR_DEFAULT_TEAM_TEXT;
        }
        int n5;
        if (b4) {
            n5 = this.COLOR_LEADING_TEAM_BACKGROUND;
        }
        else {
            n5 = this.COLOR_DEFAULT_TEAM_BACKGROUND;
        }
        int n6;
        if (b3) {
            n6 = this.COLOR_LEADING_TEAM_TEXT;
        }
        else {
            n6 = this.COLOR_DEFAULT_TEAM_TEXT;
        }
        int n7;
        if (b3) {
            n7 = this.COLOR_LEADING_TEAM_BACKGROUND;
        }
        else {
            n7 = this.COLOR_DEFAULT_TEAM_BACKGROUND;
        }
        this._viewTeamPointsLeftText.setTextColor(n6);
        this._viewTeamPointsLeftText.setBackgroundColor(n7);
        this._viewTeamPointsRightText.setTextColor(n4);
        this._viewTeamPointsRightText.setBackgroundColor(n5);
        this._viewTeamNameLeftText.setTextColor(n6);
        this._viewTeamNameLeftText.setBackgroundColor(n7);
        this._viewTeamNameRightText.setTextColor(n4);
        this._viewTeamNameRightText.setBackgroundColor(n5);
    }
    
    public void setTeamName(final String text, final String text2) {
        this._viewTeamNameLeftText.setText((CharSequence)text);
        this._viewTeamNameRightText.setText((CharSequence)text2);
    }
}
