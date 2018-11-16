// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.standings.view;

import android.graphics.drawable.Drawable;
import de.cisha.chess.model.Country;
import de.cisha.android.board.broadcast.model.TournamentPlayer;
import java.util.Iterator;
import de.cisha.chess.model.GameResult;
import android.view.ViewGroup.LayoutParams;
import android.view.View.OnClickListener;
import android.widget.LinearLayout.LayoutParams;
import java.util.List;
import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.os.Build.VERSION;
import android.view.LayoutInflater;
import java.io.Serializable;
import android.graphics.Color;
import de.cisha.android.board.broadcast.model.TournamentGameInfo;
import android.util.AttributeSet;
import android.content.Context;
import android.widget.TextView;
import de.cisha.android.board.broadcast.view.TournamentPlayerInfoView;
import de.cisha.android.board.broadcast.standings.model.KnockoutMatch;
import android.view.ViewGroup;
import de.cisha.android.board.broadcast.GameSelectedListener;
import android.view.View;
import android.widget.LinearLayout;

public class TournamentMatchInfoView extends LinearLayout
{
    private View _footerView;
    private GameSelectedListener _gameSelectionListener;
    private ViewGroup _gamesContainerView;
    private View _headerView;
    private KnockoutMatch _match;
    private TournamentPlayerInfoView _playerLeft;
    private TournamentPlayerInfoView _playerRight;
    private TextView _pointsViewLeft;
    private TextView _pointsViewRight;
    private boolean _showGames;
    
    public TournamentMatchInfoView(final Context context) {
        super(context);
        this._showGames = false;
        this.init();
    }
    
    public TournamentMatchInfoView(final Context context, final AttributeSet set) {
        super(context, set);
        this._showGames = false;
        this.init();
    }
    
    private LinearLayout addRowForGames() {
        final LinearLayout linearLayout = new LinearLayout(this.getContext());
        linearLayout.setOrientation(0);
        linearLayout.setBackgroundColor(Color.rgb(166, 166, 166));
        this._gamesContainerView.addView((View)linearLayout, -1, -2);
        return linearLayout;
    }
    
    private BroadcastTournamentMatchInfoBackground createBackground(final boolean b, final boolean b2, final boolean b3) {
        return new BroadcastTournamentMatchInfoBackground((int)(2.0f * this.getContext().getResources().getDisplayMetrics().density), b, b2, b3);
    }
    
    private void gameSelected(final TournamentGameInfo tournamentGameInfo) {
        if (this._gameSelectionListener != null) {
            this._gameSelectionListener.gameSelected(tournamentGameInfo.getGameID());
        }
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
        this.setOrientation(1);
        final LayoutInflater from = LayoutInflater.from(this.getContext());
        this.addView(this._headerView = from.inflate(2131427391, (ViewGroup)this, false));
        this.addView(this._footerView = from.inflate(2131427392, (ViewGroup)this, false));
        final LinearLayout linearLayout = new LinearLayout(this.getContext());
        linearLayout.setBackgroundColor(Color.rgb(166, 166, 166));
        this.addView((View)linearLayout, -1, (int)this.getContext().getResources().getDisplayMetrics().density);
        this._gamesContainerView = (ViewGroup)this.findViewById(2131296394);
        this._pointsViewLeft = (TextView)this.findViewById(2131296396);
        this._pointsViewRight = (TextView)this.findViewById(2131296397);
        this._playerLeft = (TournamentPlayerInfoView)this.findViewById(2131296398);
        this._playerRight = (TournamentPlayerInfoView)this.findViewById(2131296399);
    }
    
    @SuppressLint({ "NewApi" })
    private void setHardwareLayerType() {
        if (Build.VERSION.SDK_INT >= 16) {
            this.setLayerType(1, (Paint)null);
        }
    }
    
    private void updateGames(final List<TournamentGameInfo> list) {
        final int width = this.getWidth();
        final float density = this.getContext().getResources().getDisplayMetrics().density;
        int n;
        if ((n = width / (int)(75.0f * density)) <= 0) {
            n = 1;
        }
        final LinearLayout.LayoutParams linearLayout.LayoutParams = new LinearLayout.LayoutParams(width / n, (int)(30.0f * density));
        linearLayout.LayoutParams.rightMargin = 1;
        linearLayout.LayoutParams.topMargin = 1;
        final boolean showGames = this._showGames;
        final int n2 = 0;
        if (showGames) {
            this._gamesContainerView.removeAllViews();
            LinearLayout linearLayout = this.addRowForGames();
            final int actualDesiredNumberOfGamesInMatch = this._match.getActualDesiredNumberOfGamesInMatch();
            final int size = list.size();
            final Iterator<TournamentGameInfo> iterator = list.iterator();
            int n3 = 0;
            while (iterator.hasNext()) {
                final TournamentGameInfo tournamentGameInfo = iterator.next();
                if (++n3 > n) {
                    linearLayout = this.addRowForGames();
                    n3 = 1;
                }
                final boolean b = this._match.getPlayerLeft().equals(tournamentGameInfo.getPlayerLeft()) == tournamentGameInfo.isWhitePlayerLeftSide();
                TournamentMatchInfoGameView tournamentMatchInfoGameView;
                if (tournamentGameInfo.getStatus().isOngoing()) {
                    tournamentMatchInfoGameView = new TournamentMatchInfoGameView(this.getContext(), b, true, tournamentGameInfo.getEval());
                }
                else {
                    tournamentMatchInfoGameView = new TournamentMatchInfoGameView(this.getContext(), b, false, tournamentGameInfo.getStatus().getResult());
                }
                tournamentMatchInfoGameView.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
                    public void onClick(final View view) {
                        TournamentMatchInfoView.this.gameSelected(tournamentGameInfo);
                    }
                });
                linearLayout.addView((View)tournamentMatchInfoGameView, (ViewGroup.LayoutParams)linearLayout.LayoutParams);
            }
            for (int i = 0; i < actualDesiredNumberOfGamesInMatch - size; ++i) {
                if (++n3 > n) {
                    linearLayout = this.addRowForGames();
                    n3 = 1;
                }
                linearLayout.addView((View)new TournamentMatchInfoGameView(this.getContext(), true, false, GameResult.NO_RESULT), (ViewGroup.LayoutParams)linearLayout.LayoutParams);
            }
        }
        int visibility;
        if (this._showGames && list.size() > 0) {
            visibility = n2;
        }
        else {
            visibility = 8;
        }
        if (this._footerView.getVisibility() != visibility) {
            this._footerView.setVisibility(visibility);
        }
    }
    
    private void updateViewWithPlayer(final TournamentPlayerInfoView tournamentPlayerInfoView, final TournamentPlayer tournamentPlayer) {
        tournamentPlayerInfoView.setPlayerName(tournamentPlayer.getFullName());
        final StringBuilder sb = new StringBuilder();
        String string;
        if (tournamentPlayer.getTitle() != null) {
            final StringBuilder sb2 = new StringBuilder();
            sb2.append(tournamentPlayer.getTitle());
            sb2.append(" ");
            string = sb2.toString();
        }
        else {
            string = " ";
        }
        sb.append(string);
        String string2;
        if (tournamentPlayer.getElo() == 0) {
            string2 = "";
        }
        else {
            final StringBuilder sb3 = new StringBuilder();
            sb3.append("");
            sb3.append(tournamentPlayer.getElo());
            string2 = sb3.toString();
        }
        sb.append(string2);
        tournamentPlayerInfoView.setPlayerTitle(sb.toString());
        tournamentPlayerInfoView.setPlayerFlagForCountry(tournamentPlayer.getCountry());
    }
    
    public void setGameSelectionListener(final GameSelectedListener gameSelectionListener) {
        this._gameSelectionListener = gameSelectionListener;
    }
    
    public void setGamesViewEnabled(final boolean showGames) {
        this._showGames = showGames;
        final View footerView = this._footerView;
        int visibility;
        if (this._showGames) {
            visibility = 0;
        }
        else {
            visibility = 8;
        }
        footerView.setVisibility(visibility);
        if (this._match != null) {
            this.updateGames(this._match.getGames());
        }
    }
    
    public void setOnHeaderClickListener(final View.OnClickListener onClickListener) {
        this._headerView.setOnClickListener(onClickListener);
    }
    
    public void updateWithMatch(final KnockoutMatch match) {
        this._match = match;
        this.updateViewWithPlayer(this._playerLeft, this._match.getPlayerLeft());
        this.updateViewWithPlayer(this._playerRight, this._match.getPlayerRight());
        this._pointsViewLeft.setText((CharSequence)this.getPointString(match.getPlayerLeftPoints()));
        this._pointsViewRight.setText((CharSequence)this.getPointString(match.getPlayerRightPoints()));
        final boolean playerLeftTheWinner = match.isPlayerLeftTheWinner();
        final boolean playerRightTheWinner = match.isPlayerRightTheWinner();
        final boolean b = !playerLeftTheWinner && !playerRightTheWinner;
        this._pointsViewLeft.setBackgroundDrawable((Drawable)this.createBackground(playerLeftTheWinner, true, b));
        this._pointsViewRight.setBackgroundDrawable((Drawable)this.createBackground(playerRightTheWinner, false, b));
        this.updateGames(match.getGames());
    }
}
