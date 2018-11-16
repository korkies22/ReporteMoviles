// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.view;

import de.cisha.android.board.playzone.model.ChessClock;
import de.cisha.android.board.broadcast.model.TournamentPlayer;
import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.os.Build.VERSION;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.widget.TextView;
import android.widget.ImageView;
import de.cisha.android.ui.patterns.chess.widget.EngineEvaluationView;
import de.cisha.android.board.broadcast.model.TournamentGameInfo;
import android.widget.LinearLayout;

public class TournamentGameInfoView extends LinearLayout
{
    private TournamentGameInfoFooterView _footerView;
    private TournamentGameInfo _gameInfo;
    private EngineEvaluationView _imgEval;
    private ImageView _player1Image;
    private TextView _player1Info;
    private TextView _player1Name;
    private ImageView _player2Image;
    private TextView _player2Info;
    private TextView _player2Name;
    private TextView _resultText;
    
    public TournamentGameInfoView(final Context context, final TournamentGameInfo gameInfo) {
        super(context);
        this._gameInfo = gameInfo;
        this.init();
    }
    
    private void init() {
        this.setHardwareLayerType();
        this.setOrientation(1);
        this.setBackgroundResource(2131230991);
        inflate(this.getContext(), 2131427387, (ViewGroup)this);
        this.addView((View)(this._footerView = new TournamentGameInfoFooterView(this.getContext())), -1, -2);
        this._player1Name = (TextView)this.findViewById(2131296373);
        this._player2Name = (TextView)this.findViewById(2131296374);
        this._player1Info = (TextView)this.findViewById(2131296375);
        this._player2Info = (TextView)this.findViewById(2131296377);
        this._player1Image = (ImageView)this.findViewById(2131296376);
        this._player2Image = (ImageView)this.findViewById(2131296378);
        this._imgEval = (EngineEvaluationView)this.findViewById(2131296371);
        final EngineEvaluationView imgEval = this._imgEval;
        EngineEvaluationView.EvaluationViewType evaluationViewType;
        if (this._gameInfo.isWhitePlayerLeftSide()) {
            evaluationViewType = EngineEvaluationView.EvaluationViewType.WHITE_LEFT;
        }
        else {
            evaluationViewType = EngineEvaluationView.EvaluationViewType.WHITE_RIGHT;
        }
        imgEval.setEvaluationViewType(evaluationViewType);
        this._resultText = (TextView)this.findViewById(2131296379);
        this.updateWithGameInfo(this._gameInfo);
    }
    
    @SuppressLint({ "NewApi" })
    private void setHardwareLayerType() {
        if (Build.VERSION.SDK_INT >= 16) {
            this.setLayerType(1, (Paint)null);
        }
    }
    
    private void setInfoPlayer1(final CharSequence text) {
        this._player1Info.setText(text);
    }
    
    private void setInfoPlayer2(final CharSequence text) {
        this._player2Info.setText(text);
    }
    
    private void setLastMove(final CharSequence textLastMove) {
        this._footerView.setTextLastMove(textLastMove);
    }
    
    private void setNamePlayer1(final CharSequence text) {
        this._player1Name.setText(text);
    }
    
    private void setNamePlayer2(final CharSequence text) {
        this._player2Name.setText(text);
    }
    
    private void setTimePlayer1(final CharSequence textTime1) {
        this._footerView.setTextTime1(textTime1);
    }
    
    private void setTimePlayer2(final CharSequence textTime2) {
        this._footerView.setTextTime2(textTime2);
    }
    
    private void showEvaluation(final float evaluationValue) {
        this._imgEval.setVisibility(0);
        this._imgEval.setEvaluationValue(evaluationValue);
        this._resultText.setVisibility(8);
    }
    
    private void showResultString(final String text) {
        this._imgEval.setVisibility(8);
        this._resultText.setVisibility(0);
        this._resultText.setText((CharSequence)text);
    }
    
    private void updateWithGameInfo(final TournamentGameInfo tournamentGameInfo) {
        TournamentPlayer playerLeft;
        if ((playerLeft = tournamentGameInfo.getPlayerLeft()) == null) {
            playerLeft = new TournamentPlayer("", 0);
        }
        this.setNamePlayer1(playerLeft.getFullName());
        TournamentPlayer playerRight;
        if ((playerRight = tournamentGameInfo.getPlayerRight()) == null) {
            playerRight = new TournamentPlayer("", 0);
        }
        this.setNamePlayer2(playerRight.getFullName());
        String title;
        if (playerLeft.getTitle() == null) {
            title = "  ";
        }
        else {
            title = playerLeft.getTitle();
        }
        String title2;
        if (playerRight.getTitle() == null) {
            title2 = "  ";
        }
        else {
            title2 = playerRight.getTitle();
        }
        final StringBuilder sb = new StringBuilder();
        sb.append(title);
        sb.append(" ");
        sb.append(playerLeft.getElo());
        this.setInfoPlayer1(sb.toString());
        final StringBuilder sb2 = new StringBuilder();
        sb2.append(title2);
        sb2.append(" ");
        sb2.append(playerRight.getElo());
        this.setInfoPlayer2(sb2.toString());
        final EngineEvaluationView imgEval = this._imgEval;
        EngineEvaluationView.EvaluationViewType evaluationViewType;
        if (tournamentGameInfo.isWhitePlayerLeftSide()) {
            evaluationViewType = EngineEvaluationView.EvaluationViewType.WHITE_LEFT;
        }
        else {
            evaluationViewType = EngineEvaluationView.EvaluationViewType.WHITE_RIGHT;
        }
        imgEval.setEvaluationViewType(evaluationViewType);
        if (playerLeft.getCountry() != null) {
            this._player1Image.setImageResource(playerLeft.getCountry().getImageId());
            this._player1Image.setVisibility(0);
        }
        else {
            this._player1Image.setVisibility(4);
        }
        if (playerRight.getCountry() != null) {
            this._player2Image.setImageResource(playerRight.getCountry().getImageId());
            this._player2Image.setVisibility(0);
        }
        else {
            this._player2Image.setVisibility(4);
        }
        this.setLastMove(tournamentGameInfo.getLastMoveText());
        if (tournamentGameInfo.getStatus().isOngoing()) {
            this.showEvaluation(tournamentGameInfo.getEval());
            return;
        }
        if (tournamentGameInfo.getStatus().isUpcoming()) {
            this.showResultString(" - ");
            return;
        }
        switch (TournamentGameInfoView.2..SwitchMap.de.cisha.chess.model.GameResult[tournamentGameInfo.getStatus().getResult().ordinal()]) {
            default: {
                this.showResultString("� - �");
            }
            case 2: {
                String s;
                if (!tournamentGameInfo.isWhitePlayerLeftSide()) {
                    s = "1 - 0";
                }
                else {
                    s = "0 - 1";
                }
                this.showResultString(s);
            }
            case 1: {
                String s2;
                if (tournamentGameInfo.isWhitePlayerLeftSide()) {
                    s2 = "1 - 0";
                }
                else {
                    s2 = "0 - 1";
                }
                this.showResultString(s2);
            }
        }
    }
    
    public void setGameInfo(final TournamentGameInfo gameInfo) {
        this._gameInfo = gameInfo;
        this.updateRemainingTimes();
        this.updateWithGameInfo(this._gameInfo);
        this.post((Runnable)new Runnable() {
            @Override
            public void run() {
            }
        });
    }
    
    public void updateRemainingTimes() {
        long n2;
        long n3;
        if (this._gameInfo.getStatus().isOngoing()) {
            final long n = System.currentTimeMillis() - this._gameInfo.getLastMoveTimeStamp();
            if (this._gameInfo.isWhitePlayerActive()) {
                n2 = this._gameInfo.getRemainingTimeMillisWhite() - n;
                n3 = this._gameInfo.getRemainingTimeMillisBlack();
            }
            else {
                n2 = this._gameInfo.getRemainingTimeMillisWhite();
                n3 = this._gameInfo.getRemainingTimeMillisBlack() - n;
            }
        }
        else {
            n2 = this._gameInfo.getRemainingTimeMillisWhite();
            n3 = this._gameInfo.getRemainingTimeMillisBlack();
        }
        final boolean whitePlayerLeftSide = this._gameInfo.isWhitePlayerLeftSide();
        long n4;
        if (whitePlayerLeftSide) {
            n4 = n2;
        }
        else {
            n4 = n3;
        }
        this.setTimePlayer1(ChessClock.formatTime(n4, true));
        if (!whitePlayerLeftSide) {
            n3 = n2;
        }
        this.setTimePlayer2(ChessClock.formatTime(n3, true));
    }
}
