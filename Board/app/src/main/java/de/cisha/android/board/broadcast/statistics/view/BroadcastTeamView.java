// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.statistics.view;

import de.cisha.android.board.broadcast.model.TeamStanding;
import de.cisha.android.board.broadcast.model.TournamentTeam;
import android.view.ViewGroup;
import android.util.AttributeSet;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.LinearLayout;

public class BroadcastTeamView extends LinearLayout
{
    private TextView _averageRating;
    private TextView _boardPoints;
    private TextView _matchPoints;
    private ImageView _teamFlag;
    private TextView _teamName;
    
    public BroadcastTeamView(final Context context) {
        super(context);
        this.setupViews();
    }
    
    public BroadcastTeamView(final Context context, final AttributeSet set) {
        super(context, set);
        this.setupViews();
    }
    
    private void setupViews() {
        this.setOrientation(1);
        inflate(this.getContext(), 2131427567, (ViewGroup)this);
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
    
    public void showTeam(final TournamentTeam tournamentTeam, final int n) {
        if (tournamentTeam != null) {
            if (tournamentTeam.getCountry() != null) {
                this._teamFlag.setVisibility(0);
                this._teamFlag.setImageResource(tournamentTeam.getCountry().getImageId());
            }
            else {
                this._teamFlag.setVisibility(8);
            }
            final TeamStanding currentStandings = tournamentTeam.getCurrentStandings();
            if (currentStandings != null) {
                final TextView boardPoints = this._boardPoints;
                final StringBuilder sb = new StringBuilder();
                sb.append("");
                sb.append(currentStandings.getBoardPoints());
                boardPoints.setText((CharSequence)sb.toString());
                final TextView matchPoints = this._matchPoints;
                final StringBuilder sb2 = new StringBuilder();
                sb2.append("");
                sb2.append(currentStandings.getTeamPoints());
                matchPoints.setText((CharSequence)sb2.toString());
            }
            else {
                this._boardPoints.setText((CharSequence)"");
                this._matchPoints.setText((CharSequence)"");
            }
            this._teamName.setText((CharSequence)tournamentTeam.getName());
            final float averageRatingOfTopPlayers = tournamentTeam.getAverageRatingOfTopPlayers(n);
            final TextView averageRating = this._averageRating;
            final StringBuilder sb3 = new StringBuilder();
            sb3.append("");
            sb3.append(Math.round(averageRatingOfTopPlayers));
            averageRating.setText((CharSequence)sb3.toString());
            return;
        }
        this.reset();
    }
}
