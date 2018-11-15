/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.content.Context
 *  android.graphics.Paint
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.ImageView
 *  android.widget.LinearLayout
 *  android.widget.TextView
 */
package de.cisha.android.board.broadcast.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.neovisionaries.i18n.CountryCode;
import de.cisha.android.board.broadcast.model.BroadcastGameStatus;
import de.cisha.android.board.broadcast.model.TournamentGameInfo;
import de.cisha.android.board.broadcast.model.TournamentPlayer;
import de.cisha.android.board.broadcast.view.TournamentGameInfoFooterView;
import de.cisha.android.board.playzone.model.ChessClock;
import de.cisha.android.ui.patterns.chess.widget.EngineEvaluationView;
import de.cisha.chess.model.GameResult;

public class TournamentGameInfoView
extends LinearLayout {
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

    public TournamentGameInfoView(Context context, TournamentGameInfo tournamentGameInfo) {
        super(context);
        this._gameInfo = tournamentGameInfo;
        this.init();
    }

    private void init() {
        this.setHardwareLayerType();
        this.setOrientation(1);
        this.setBackgroundResource(2131230991);
        TournamentGameInfoView.inflate((Context)this.getContext(), (int)2131427387, (ViewGroup)this);
        this._footerView = new TournamentGameInfoFooterView(this.getContext());
        this.addView((View)this._footerView, -1, -2);
        this._player1Name = (TextView)this.findViewById(2131296373);
        this._player2Name = (TextView)this.findViewById(2131296374);
        this._player1Info = (TextView)this.findViewById(2131296375);
        this._player2Info = (TextView)this.findViewById(2131296377);
        this._player1Image = (ImageView)this.findViewById(2131296376);
        this._player2Image = (ImageView)this.findViewById(2131296378);
        EngineEvaluationView engineEvaluationView = this._imgEval = (EngineEvaluationView)this.findViewById(2131296371);
        EngineEvaluationView.EvaluationViewType evaluationViewType = this._gameInfo.isWhitePlayerLeftSide() ? EngineEvaluationView.EvaluationViewType.WHITE_LEFT : EngineEvaluationView.EvaluationViewType.WHITE_RIGHT;
        engineEvaluationView.setEvaluationViewType(evaluationViewType);
        this._resultText = (TextView)this.findViewById(2131296379);
        this.updateWithGameInfo(this._gameInfo);
    }

    @SuppressLint(value={"NewApi"})
    private void setHardwareLayerType() {
        if (Build.VERSION.SDK_INT >= 16) {
            this.setLayerType(1, null);
        }
    }

    private void setInfoPlayer1(CharSequence charSequence) {
        this._player1Info.setText(charSequence);
    }

    private void setInfoPlayer2(CharSequence charSequence) {
        this._player2Info.setText(charSequence);
    }

    private void setLastMove(CharSequence charSequence) {
        this._footerView.setTextLastMove(charSequence);
    }

    private void setNamePlayer1(CharSequence charSequence) {
        this._player1Name.setText(charSequence);
    }

    private void setNamePlayer2(CharSequence charSequence) {
        this._player2Name.setText(charSequence);
    }

    private void setTimePlayer1(CharSequence charSequence) {
        this._footerView.setTextTime1(charSequence);
    }

    private void setTimePlayer2(CharSequence charSequence) {
        this._footerView.setTextTime2(charSequence);
    }

    private void showEvaluation(float f) {
        this._imgEval.setVisibility(0);
        this._imgEval.setEvaluationValue(f);
        this._resultText.setVisibility(8);
    }

    private void showResultString(String string) {
        this._imgEval.setVisibility(8);
        this._resultText.setVisibility(0);
        this._resultText.setText((CharSequence)string);
    }

    private void updateWithGameInfo(TournamentGameInfo object) {
        TournamentPlayer tournamentPlayer;
        TournamentPlayer tournamentPlayer2 = tournamentPlayer = object.getPlayerLeft();
        if (tournamentPlayer == null) {
            tournamentPlayer2 = new TournamentPlayer("", 0);
        }
        this.setNamePlayer1(tournamentPlayer2.getFullName());
        Object object2 = object.getPlayerRight();
        tournamentPlayer = object2;
        if (object2 == null) {
            tournamentPlayer = new TournamentPlayer("", 0);
        }
        this.setNamePlayer2(tournamentPlayer.getFullName());
        object2 = tournamentPlayer2.getTitle() == null ? "  " : tournamentPlayer2.getTitle();
        Object object3 = tournamentPlayer.getTitle() == null ? "  " : tournamentPlayer.getTitle();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append((String)object2);
        stringBuilder.append(" ");
        stringBuilder.append(tournamentPlayer2.getElo());
        this.setInfoPlayer1(stringBuilder.toString());
        object2 = new StringBuilder();
        object2.append((String)object3);
        object2.append(" ");
        object2.append(tournamentPlayer.getElo());
        this.setInfoPlayer2(object2.toString());
        object3 = this._imgEval;
        object2 = object.isWhitePlayerLeftSide() ? EngineEvaluationView.EvaluationViewType.WHITE_LEFT : EngineEvaluationView.EvaluationViewType.WHITE_RIGHT;
        object3.setEvaluationViewType((EngineEvaluationView.EvaluationViewType)((Object)object2));
        if (tournamentPlayer2.getCountry() != null) {
            this._player1Image.setImageResource(tournamentPlayer2.getCountry().getImageId());
            this._player1Image.setVisibility(0);
        } else {
            this._player1Image.setVisibility(4);
        }
        if (tournamentPlayer.getCountry() != null) {
            this._player2Image.setImageResource(tournamentPlayer.getCountry().getImageId());
            this._player2Image.setVisibility(0);
        } else {
            this._player2Image.setVisibility(4);
        }
        this.setLastMove(object.getLastMoveText());
        if (object.getStatus().isOngoing()) {
            this.showEvaluation(object.getEval());
            return;
        }
        if (object.getStatus().isUpcoming()) {
            this.showResultString(" - ");
            return;
        }
        switch (.$SwitchMap$de$cisha$chess$model$GameResult[object.getStatus().getResult().ordinal()]) {
            default: {
                this.showResultString("\u00bd - \u00bd");
                return;
            }
            case 2: {
                object = !object.isWhitePlayerLeftSide() ? "1 - 0" : "0 - 1";
                this.showResultString((String)object);
                return;
            }
            case 1: 
        }
        object = object.isWhitePlayerLeftSide() ? "1 - 0" : "0 - 1";
        this.showResultString((String)object);
    }

    public void setGameInfo(TournamentGameInfo tournamentGameInfo) {
        this._gameInfo = tournamentGameInfo;
        this.updateRemainingTimes();
        this.updateWithGameInfo(this._gameInfo);
        this.post(new Runnable(){

            @Override
            public void run() {
            }
        });
    }

    public void updateRemainingTimes() {
        long l;
        long l2;
        if (this._gameInfo.getStatus().isOngoing()) {
            l = System.currentTimeMillis() - this._gameInfo.getLastMoveTimeStamp();
            if (this._gameInfo.isWhitePlayerActive()) {
                l2 = this._gameInfo.getRemainingTimeMillisWhite() - l;
                l = this._gameInfo.getRemainingTimeMillisBlack();
            } else {
                l2 = this._gameInfo.getRemainingTimeMillisWhite();
                l = this._gameInfo.getRemainingTimeMillisBlack() - l;
            }
        } else {
            l2 = this._gameInfo.getRemainingTimeMillisWhite();
            l = this._gameInfo.getRemainingTimeMillisBlack();
        }
        boolean bl = this._gameInfo.isWhitePlayerLeftSide();
        long l3 = bl ? l2 : l;
        this.setTimePlayer1(ChessClock.formatTime(l3, true));
        if (!bl) {
            l = l2;
        }
        this.setTimePlayer2(ChessClock.formatTime(l, true));
    }

}
