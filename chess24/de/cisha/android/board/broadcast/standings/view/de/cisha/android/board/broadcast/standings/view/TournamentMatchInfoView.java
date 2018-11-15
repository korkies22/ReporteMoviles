/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Color
 *  android.graphics.Paint
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.LinearLayout
 *  android.widget.LinearLayout$LayoutParams
 *  android.widget.TextView
 */
package de.cisha.android.board.broadcast.standings.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.neovisionaries.i18n.CountryCode;
import de.cisha.android.board.broadcast.GameSelectedListener;
import de.cisha.android.board.broadcast.model.BroadcastGameStatus;
import de.cisha.android.board.broadcast.model.TournamentGameID;
import de.cisha.android.board.broadcast.model.TournamentGameInfo;
import de.cisha.android.board.broadcast.model.TournamentPlayer;
import de.cisha.android.board.broadcast.standings.model.KnockoutMatch;
import de.cisha.android.board.broadcast.standings.view.BroadcastTournamentMatchInfoBackground;
import de.cisha.android.board.broadcast.standings.view.TournamentMatchInfoGameView;
import de.cisha.android.board.broadcast.view.TournamentPlayerInfoView;
import de.cisha.chess.model.Country;
import de.cisha.chess.model.GameResult;
import java.util.Iterator;
import java.util.List;

public class TournamentMatchInfoView
extends LinearLayout {
    private View _footerView;
    private GameSelectedListener _gameSelectionListener;
    private ViewGroup _gamesContainerView;
    private View _headerView;
    private KnockoutMatch _match;
    private TournamentPlayerInfoView _playerLeft;
    private TournamentPlayerInfoView _playerRight;
    private TextView _pointsViewLeft;
    private TextView _pointsViewRight;
    private boolean _showGames = false;

    public TournamentMatchInfoView(Context context) {
        super(context);
        this.init();
    }

    public TournamentMatchInfoView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init();
    }

    private LinearLayout addRowForGames() {
        LinearLayout linearLayout = new LinearLayout(this.getContext());
        linearLayout.setOrientation(0);
        linearLayout.setBackgroundColor(Color.rgb((int)166, (int)166, (int)166));
        this._gamesContainerView.addView((View)linearLayout, -1, -2);
        return linearLayout;
    }

    private BroadcastTournamentMatchInfoBackground createBackground(boolean bl, boolean bl2, boolean bl3) {
        return new BroadcastTournamentMatchInfoBackground((int)(2.0f * this.getContext().getResources().getDisplayMetrics().density), bl, bl2, bl3);
    }

    private void gameSelected(TournamentGameInfo tournamentGameInfo) {
        if (this._gameSelectionListener != null) {
            this._gameSelectionListener.gameSelected(tournamentGameInfo.getGameID());
        }
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
        this.setOrientation(1);
        LayoutInflater layoutInflater = LayoutInflater.from((Context)this.getContext());
        this._headerView = layoutInflater.inflate(2131427391, (ViewGroup)this, false);
        this.addView(this._headerView);
        this._footerView = layoutInflater.inflate(2131427392, (ViewGroup)this, false);
        this.addView(this._footerView);
        layoutInflater = new LinearLayout(this.getContext());
        layoutInflater.setBackgroundColor(Color.rgb((int)166, (int)166, (int)166));
        this.addView((View)layoutInflater, -1, (int)this.getContext().getResources().getDisplayMetrics().density);
        this._gamesContainerView = (ViewGroup)this.findViewById(2131296394);
        this._pointsViewLeft = (TextView)this.findViewById(2131296396);
        this._pointsViewRight = (TextView)this.findViewById(2131296397);
        this._playerLeft = (TournamentPlayerInfoView)this.findViewById(2131296398);
        this._playerRight = (TournamentPlayerInfoView)this.findViewById(2131296399);
    }

    @SuppressLint(value={"NewApi"})
    private void setHardwareLayerType() {
        if (Build.VERSION.SDK_INT >= 16) {
            this.setLayerType(1, null);
        }
    }

    private void updateGames(List<TournamentGameInfo> list) {
        int n;
        int n2 = this.getWidth();
        float f = this.getContext().getResources().getDisplayMetrics().density;
        int n3 = n = n2 / (int)(75.0f * f);
        if (n <= 0) {
            n3 = 1;
        }
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(n2 / n3, (int)(30.0f * f));
        layoutParams.rightMargin = 1;
        layoutParams.topMargin = 1;
        boolean bl = this._showGames;
        int n4 = 0;
        if (bl) {
            this._gamesContainerView.removeAllViews();
            LinearLayout linearLayout = this.addRowForGames();
            int n5 = this._match.getActualDesiredNumberOfGamesInMatch();
            int n6 = list.size();
            Iterator<TournamentGameInfo> iterator = list.iterator();
            n = 0;
            while (iterator.hasNext()) {
                final TournamentGameInfo tournamentGameInfo = iterator.next();
                n = n2 = n + 1;
                if (n2 > n3) {
                    linearLayout = this.addRowForGames();
                    n = 1;
                }
                bl = this._match.getPlayerLeft().equals(tournamentGameInfo.getPlayerLeft()) == tournamentGameInfo.isWhitePlayerLeftSide();
                TournamentMatchInfoGameView tournamentMatchInfoGameView = tournamentGameInfo.getStatus().isOngoing() ? new TournamentMatchInfoGameView(this.getContext(), bl, true, tournamentGameInfo.getEval()) : new TournamentMatchInfoGameView(this.getContext(), bl, false, tournamentGameInfo.getStatus().getResult());
                tournamentMatchInfoGameView.setOnClickListener(new View.OnClickListener(){

                    public void onClick(View view) {
                        TournamentMatchInfoView.this.gameSelected(tournamentGameInfo);
                    }
                });
                linearLayout.addView((View)tournamentMatchInfoGameView, (ViewGroup.LayoutParams)layoutParams);
            }
            for (n2 = 0; n2 < n5 - n6; ++n2) {
                int n7;
                n = n7 = n + 1;
                if (n7 > n3) {
                    linearLayout = this.addRowForGames();
                    n = 1;
                }
                linearLayout.addView((View)new TournamentMatchInfoGameView(this.getContext(), true, false, GameResult.NO_RESULT), (ViewGroup.LayoutParams)layoutParams);
            }
        }
        n = this._showGames && list.size() > 0 ? n4 : 8;
        if (this._footerView.getVisibility() != n) {
            this._footerView.setVisibility(n);
        }
    }

    private void updateViewWithPlayer(TournamentPlayerInfoView tournamentPlayerInfoView, TournamentPlayer tournamentPlayer) {
        CharSequence charSequence;
        tournamentPlayerInfoView.setPlayerName(tournamentPlayer.getFullName());
        StringBuilder stringBuilder = new StringBuilder();
        if (tournamentPlayer.getTitle() != null) {
            charSequence = new StringBuilder();
            charSequence.append(tournamentPlayer.getTitle());
            charSequence.append(" ");
            charSequence = charSequence.toString();
        } else {
            charSequence = " ";
        }
        stringBuilder.append((String)charSequence);
        if (tournamentPlayer.getElo() == 0) {
            charSequence = "";
        } else {
            charSequence = new StringBuilder();
            charSequence.append("");
            charSequence.append(tournamentPlayer.getElo());
            charSequence = charSequence.toString();
        }
        stringBuilder.append((String)charSequence);
        tournamentPlayerInfoView.setPlayerTitle(stringBuilder.toString());
        tournamentPlayerInfoView.setPlayerFlagForCountry(tournamentPlayer.getCountry());
    }

    public void setGameSelectionListener(GameSelectedListener gameSelectedListener) {
        this._gameSelectionListener = gameSelectedListener;
    }

    public void setGamesViewEnabled(boolean bl) {
        this._showGames = bl;
        View view = this._footerView;
        int n = this._showGames ? 0 : 8;
        view.setVisibility(n);
        if (this._match != null) {
            this.updateGames(this._match.getGames());
        }
    }

    public void setOnHeaderClickListener(View.OnClickListener onClickListener) {
        this._headerView.setOnClickListener(onClickListener);
    }

    public void updateWithMatch(KnockoutMatch knockoutMatch) {
        this._match = knockoutMatch;
        this.updateViewWithPlayer(this._playerLeft, this._match.getPlayerLeft());
        this.updateViewWithPlayer(this._playerRight, this._match.getPlayerRight());
        this._pointsViewLeft.setText((CharSequence)this.getPointString(knockoutMatch.getPlayerLeftPoints()));
        this._pointsViewRight.setText((CharSequence)this.getPointString(knockoutMatch.getPlayerRightPoints()));
        boolean bl = knockoutMatch.isPlayerLeftTheWinner();
        boolean bl2 = knockoutMatch.isPlayerRightTheWinner();
        boolean bl3 = !bl && !bl2;
        this._pointsViewLeft.setBackgroundDrawable((Drawable)this.createBackground(bl, true, bl3));
        this._pointsViewRight.setBackgroundDrawable((Drawable)this.createBackground(bl2, false, bl3));
        this.updateGames(knockoutMatch.getGames());
    }

}
