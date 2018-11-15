/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.os.Bundle
 *  android.text.Html
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.TextView
 */
package de.cisha.android.board.broadcast;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import de.cisha.android.board.broadcast.TournamentSubFragment;
import de.cisha.android.board.broadcast.model.Tournament;
import de.cisha.android.board.broadcast.model.TournamentGameInfo;
import de.cisha.android.board.broadcast.model.TournamentGamesObserver;
import de.cisha.android.board.broadcast.model.TournamentHolder;
import de.cisha.android.board.broadcast.model.TournamentRoundInfo;
import de.cisha.android.view.WebImageView;
import java.net.URL;
import java.util.List;

public class TournamentInfoFragment
extends TournamentSubFragment
implements TournamentGamesObserver {
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
        Tournament tournament = this._tournament;
        this._numberOfFinishedGames = tournament.getAllFinishedGames().size();
        this._numberOfOngoingGames = tournament.getAllRunningGames().size();
        String string = tournament.getCurrentRound() != null ? tournament.getCurrentRound().getRoundString() : "-";
        this._currentRoundString = string;
        this._descriptionString = tournament.getDescription();
        this._logoUrl = tournament.getLogoUrl();
        this._titleText = tournament.getName();
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                TournamentInfoFragment.this._currentRoundTextView.setText((CharSequence)TournamentInfoFragment.this._currentRoundString);
                TextView textView = TournamentInfoFragment.this._ongoingGamesTextView;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(TournamentInfoFragment.this._numberOfOngoingGames);
                stringBuilder.append("");
                textView.setText((CharSequence)stringBuilder.toString());
                textView = TournamentInfoFragment.this._finishedGamesTextView;
                stringBuilder = new StringBuilder();
                stringBuilder.append(TournamentInfoFragment.this._numberOfFinishedGames);
                stringBuilder.append("");
                textView.setText((CharSequence)stringBuilder.toString());
                TournamentInfoFragment.this._descriptionTextView.setText((CharSequence)Html.fromHtml((String)TournamentInfoFragment.this._descriptionString));
                TournamentInfoFragment.this._logoView.setImageWebUrl(TournamentInfoFragment.this._logoUrl);
                TournamentInfoFragment.this._titleTextView.setText((CharSequence)TournamentInfoFragment.this._titleText);
            }
        });
    }

    @Override
    public void gameInfoChanged(TournamentGameInfo tournamentGameInfo) {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.getTournamentHolder().addTournamentGamesObserver(this);
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(2131427383, viewGroup, false);
    }

    @Override
    public void onSelectRound(TournamentRoundInfo tournamentRoundInfo) {
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this._currentRoundTextView = (TextView)view.findViewById(2131296359);
        this._ongoingGamesTextView = (TextView)view.findViewById(2131296363);
        this._finishedGamesTextView = (TextView)view.findViewById(2131296360);
        this._descriptionTextView = (TextView)view.findViewById(2131296361);
        this._titleTextView = (TextView)view.findViewById(2131296364);
        this._logoView = (WebImageView)view.findViewById(2131296362);
    }

    @Override
    public void registeredForChangesOn(Tournament tournament, TournamentRoundInfo tournamentRoundInfo, boolean bl) {
        this._tournament = tournament;
        this.allGameInfosChanged();
    }

}
