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
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.LinearLayout
 *  android.widget.TextView
 */
package de.cisha.android.board.broadcast.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TournamentTeamMatchView
extends LinearLayout {
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

    public TournamentTeamMatchView(Context context) {
        super(context);
        this.init();
    }

    public TournamentTeamMatchView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init();
    }

    private String getPointString(float f) {
        int n = (int)f;
        boolean bl = (double)(f - (float)n) > 0.1;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        Object object = n == 0 && bl ? "" : Integer.valueOf(n);
        stringBuilder.append(object);
        object = bl ? "\u00bd" : "";
        stringBuilder.append((String)object);
        return stringBuilder.toString();
    }

    private void init() {
        this.setHardwareLayerType();
        this.COLOR_LEADING_TEAM_BACKGROUND = this.getResources().getColor(2131099690);
        this.COLOR_DEFAULT_TEAM_BACKGROUND = this.getResources().getColor(2131099689);
        this.COLOR_TIE_TEAM_BACKGROUND = this.getResources().getColor(2131099692);
        this.COLOR_LEADING_TEAM_TEXT = this.getResources().getColor(2131099691);
        this.COLOR_DEFAULT_TEAM_TEXT = this.getResources().getColor(2131099846);
        this.setOrientation(1);
        TournamentTeamMatchView.inflate((Context)this.getContext(), (int)2131427395, (ViewGroup)this);
        this._viewTeamNameLeftText = (TextView)this.findViewById(2131296406);
        this._viewTeamNameRightText = (TextView)this.findViewById(2131296407);
        this._viewTeamPointsLeftText = (TextView)this.findViewById(2131296408);
        this._viewTeamPointsRightText = (TextView)this.findViewById(2131296409);
        this._viewLiveIndicator = this.findViewById(2131296405);
    }

    @SuppressLint(value={"NewApi"})
    private void setHardwareLayerType() {
        if (Build.VERSION.SDK_INT >= 16) {
            this.setLayerType(2, null);
        }
    }

    public void setMatchIsOngoing(boolean bl) {
        this._flagIsLive = bl;
        View view = this._viewLiveIndicator;
        int n = bl ? 0 : 4;
        view.setVisibility(n);
    }

    public void setPointsText(float f, float f2) {
        this._viewTeamPointsLeftText.setText((CharSequence)this.getPointString(f));
        this._viewTeamPointsRightText.setText((CharSequence)this.getPointString(f2));
        boolean bl = this._flagIsLive;
        int n = 0;
        int n2 = !bl && f == f2 ? 1 : 0;
        int n3 = !this._flagIsLive && f > f2 ? 1 : 0;
        int n4 = n;
        if (!this._flagIsLive) {
            n4 = n;
            if (f2 > f) {
                n4 = 1;
            }
        }
        if (n4 == 0 && n3 == 0) {
            n3 = n2 != 0 ? this.COLOR_TIE_TEAM_BACKGROUND : this.COLOR_DEFAULT_TEAM_BACKGROUND;
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
        n2 = n4 != 0 ? this.COLOR_LEADING_TEAM_TEXT : this.COLOR_DEFAULT_TEAM_TEXT;
        n4 = n4 != 0 ? this.COLOR_LEADING_TEAM_BACKGROUND : this.COLOR_DEFAULT_TEAM_BACKGROUND;
        n = n3 != 0 ? this.COLOR_LEADING_TEAM_TEXT : this.COLOR_DEFAULT_TEAM_TEXT;
        n3 = n3 != 0 ? this.COLOR_LEADING_TEAM_BACKGROUND : this.COLOR_DEFAULT_TEAM_BACKGROUND;
        this._viewTeamPointsLeftText.setTextColor(n);
        this._viewTeamPointsLeftText.setBackgroundColor(n3);
        this._viewTeamPointsRightText.setTextColor(n2);
        this._viewTeamPointsRightText.setBackgroundColor(n4);
        this._viewTeamNameLeftText.setTextColor(n);
        this._viewTeamNameLeftText.setBackgroundColor(n3);
        this._viewTeamNameRightText.setTextColor(n2);
        this._viewTeamNameRightText.setBackgroundColor(n4);
    }

    public void setTeamName(String string, String string2) {
        this._viewTeamNameLeftText.setText((CharSequence)string);
        this._viewTeamNameRightText.setText((CharSequence)string2);
    }
}
