/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.LinearLayout
 *  android.widget.TextView
 */
package de.cisha.android.board.profile.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import de.cisha.android.board.profile.model.PlayzoneStatisticData;
import de.cisha.android.board.view.BoardView;
import de.cisha.android.board.view.FieldView;
import de.cisha.android.ui.patterns.buttons.CustomButton;
import de.cisha.chess.model.FEN;
import de.cisha.chess.model.position.Position;

public class YourGamesView
extends LinearLayout {
    TextView _draw;
    FieldView _field;
    TextView _lost;
    TextView _totalGames;
    TextView _won;

    public YourGamesView(Context context) {
        super(context);
        this.init();
    }

    public YourGamesView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init();
    }

    private void init() {
        this.setOrientation(1);
        YourGamesView.inflate((Context)this.getContext(), (int)2131427523, (ViewGroup)this);
        this._totalGames = (TextView)this.findViewById(2131296858);
        this._won = (TextView)this.findViewById(2131296867);
        this._lost = (TextView)this.findViewById(2131296863);
        this._draw = (TextView)this.findViewById(2131296860);
        this._field = (FieldView)this.findViewById(2131296861);
        this.setPlayzonGameData(null);
    }

    public FieldView getFieldView() {
        return (FieldView)this.findViewById(2131296861);
    }

    public CustomButton getPlayNowButton() {
        return (CustomButton)this.findViewById(2131296864);
    }

    public void setPlayzonGameData(PlayzoneStatisticData playzoneStatisticData) {
        if (playzoneStatisticData == null) {
            this._totalGames.setText((CharSequence)"");
            this._draw.setText((CharSequence)"");
            this._won.setText((CharSequence)"");
            this._lost.setText((CharSequence)"");
            if (this._field.getBoardView() != null) {
                this._field.getBoardView().setPosition(new Position());
                return;
            }
        } else {
            TextView textView = this._totalGames;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("");
            stringBuilder.append(playzoneStatisticData.getTotalGameCount());
            textView.setText((CharSequence)stringBuilder.toString());
            textView = this._draw;
            stringBuilder = new StringBuilder();
            stringBuilder.append("");
            stringBuilder.append(playzoneStatisticData.getDraws());
            textView.setText((CharSequence)stringBuilder.toString());
            textView = this._won;
            stringBuilder = new StringBuilder();
            stringBuilder.append("");
            stringBuilder.append(playzoneStatisticData.getWins());
            textView.setText((CharSequence)stringBuilder.toString());
            textView = this._lost;
            stringBuilder = new StringBuilder();
            stringBuilder.append("");
            stringBuilder.append(playzoneStatisticData.getLosses());
            textView.setText((CharSequence)stringBuilder.toString());
            if (this._field.getBoardView() != null) {
                this._field.getBoardView().setPosition(new Position(playzoneStatisticData.getFen()));
            }
        }
    }
}
