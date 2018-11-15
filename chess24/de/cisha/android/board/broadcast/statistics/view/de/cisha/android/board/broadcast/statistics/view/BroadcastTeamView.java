/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.ImageView
 *  android.widget.LinearLayout
 *  android.widget.TextView
 */
package de.cisha.android.board.broadcast.statistics.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import de.cisha.android.board.broadcast.model.TeamStanding;
import de.cisha.android.board.broadcast.model.TournamentTeam;
import de.cisha.chess.model.Country;

public class BroadcastTeamView
extends LinearLayout {
    private TextView _averageRating;
    private TextView _boardPoints;
    private TextView _matchPoints;
    private ImageView _teamFlag;
    private TextView _teamName;

    public BroadcastTeamView(Context context) {
        super(context);
        this.setupViews();
    }

    public BroadcastTeamView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.setupViews();
    }

    private void setupViews() {
        this.setOrientation(1);
        BroadcastTeamView.inflate((Context)this.getContext(), (int)2131427567, (ViewGroup)this);
        this._teamFlag = (ImageView)this.findViewById(2131297135);
        this._boardPoints = (TextView)this.findViewById(2131297134);
        this._matchPoints = (TextView)this.findViewById(2131297136);
        this._averageRating = (TextView)this.findViewById(2131297133);
        this._teamName = (TextView)this.findViewById(2131297138);
        this.reset();
    }

    public void reset() {
        this._teamFlag.setImageResource(2131231366);
        this._boardPoints.setText((CharSequence)"");
        this._matchPoints.setText((CharSequence)"");
        this._averageRating.setText((CharSequence)"");
        this._teamName.setText((CharSequence)"");
    }

    public void showTeam(TournamentTeam tournamentTeam, int n) {
        if (tournamentTeam != null) {
            if (tournamentTeam.getCountry() != null) {
                this._teamFlag.setVisibility(0);
                this._teamFlag.setImageResource(tournamentTeam.getCountry().getImageId());
            } else {
                this._teamFlag.setVisibility(8);
            }
            Object object = tournamentTeam.getCurrentStandings();
            if (object != null) {
                TextView textView = this._boardPoints;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("");
                stringBuilder.append(object.getBoardPoints());
                textView.setText((CharSequence)stringBuilder.toString());
                textView = this._matchPoints;
                stringBuilder = new StringBuilder();
                stringBuilder.append("");
                stringBuilder.append(object.getTeamPoints());
                textView.setText((CharSequence)stringBuilder.toString());
            } else {
                this._boardPoints.setText((CharSequence)"");
                this._matchPoints.setText((CharSequence)"");
            }
            this._teamName.setText((CharSequence)tournamentTeam.getName());
            float f = tournamentTeam.getAverageRatingOfTopPlayers(n);
            tournamentTeam = this._averageRating;
            object = new StringBuilder();
            object.append("");
            object.append(Math.round(f));
            tournamentTeam.setText((CharSequence)object.toString());
            return;
        }
        this.reset();
    }
}
