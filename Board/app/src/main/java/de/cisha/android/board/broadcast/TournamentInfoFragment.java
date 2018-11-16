// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast;

import de.cisha.android.board.broadcast.model.TournamentRoundInfo;
import android.view.View;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.app.Activity;
import de.cisha.android.board.broadcast.model.TournamentGameInfo;
import android.text.Html;
import de.cisha.android.board.broadcast.model.Tournament;
import de.cisha.android.view.WebImageView;
import java.net.URL;
import android.widget.TextView;
import de.cisha.android.board.broadcast.model.TournamentGamesObserver;

public class TournamentInfoFragment extends TournamentSubFragment implements TournamentGamesObserver
{
    public static final String TAGNAME = "BROADCAST_TOURNAMENT_INFO_FRAGMENT";
    private String _currentRoundString;
    private TextView _currentRoundTextView;
    private String _descriptionString;
    private TextView _descriptionTextView;
    private TextView _finishedGamesTextView;
    private URL _logoUrl;
    private WebImageView _logoView;
    private int _numberOfFinishedGames;
    private int _numberOfOngoingGames;
    private TextView _ongoingGamesTextView;
    private String _titleText;
    private TextView _titleTextView;
    private Tournament _tournament;
    
    @Override
    public void allGameInfosChanged() {
        final Tournament tournament = this._tournament;
        this._numberOfFinishedGames = tournament.getAllFinishedGames().size();
        this._numberOfOngoingGames = tournament.getAllRunningGames().size();
        String roundString;
        if (tournament.getCurrentRound() != null) {
            roundString = tournament.getCurrentRound().getRoundString();
        }
        else {
            roundString = "-";
        }
        this._currentRoundString = roundString;
        this._descriptionString = tournament.getDescription();
        this._logoUrl = tournament.getLogoUrl();
        this._titleText = tournament.getName();
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
            @Override
            public void run() {
                TournamentInfoFragment.this._currentRoundTextView.setText((CharSequence)TournamentInfoFragment.this._currentRoundString);
                final TextView access.300 = TournamentInfoFragment.this._ongoingGamesTextView;
                final StringBuilder sb = new StringBuilder();
                sb.append(TournamentInfoFragment.this._numberOfOngoingGames);
                sb.append("");
                access.300.setText((CharSequence)sb.toString());
                final TextView access.301 = TournamentInfoFragment.this._finishedGamesTextView;
                final StringBuilder sb2 = new StringBuilder();
                sb2.append(TournamentInfoFragment.this._numberOfFinishedGames);
                sb2.append("");
                access.301.setText((CharSequence)sb2.toString());
                TournamentInfoFragment.this._descriptionTextView.setText((CharSequence)Html.fromHtml(TournamentInfoFragment.this._descriptionString));
                TournamentInfoFragment.this._logoView.setImageWebUrl(TournamentInfoFragment.this._logoUrl);
                TournamentInfoFragment.this._titleTextView.setText((CharSequence)TournamentInfoFragment.this._titleText);
            }
        });
    }
    
    @Override
    public void gameInfoChanged(final TournamentGameInfo tournamentGameInfo) {
    }
    
    @Override
    public void onAttach(final Activity activity) {
        super.onAttach(activity);
        this.getTournamentHolder().addTournamentGamesObserver(this);
    }
    
    @Override
    public View onCreateView(final LayoutInflater layoutInflater, final ViewGroup viewGroup, final Bundle bundle) {
        return layoutInflater.inflate(2131427383, viewGroup, false);
    }
    
    @Override
    public void onSelectRound(final TournamentRoundInfo tournamentRoundInfo) {
    }
    
    @Override
    public void onViewCreated(final View view, final Bundle bundle) {
        super.onViewCreated(view, bundle);
        this._currentRoundTextView = (TextView)view.findViewById(2131296359);
        this._ongoingGamesTextView = (TextView)view.findViewById(2131296363);
        this._finishedGamesTextView = (TextView)view.findViewById(2131296360);
        this._descriptionTextView = (TextView)view.findViewById(2131296361);
        this._titleTextView = (TextView)view.findViewById(2131296364);
        this._logoView = (WebImageView)view.findViewById(2131296362);
    }
    
    @Override
    public void registeredForChangesOn(final Tournament tournament, final TournamentRoundInfo tournamentRoundInfo, final boolean b) {
        this._tournament = tournament;
        this.allGameInfosChanged();
    }
}
