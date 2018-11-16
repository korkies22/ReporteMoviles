// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.profile.view;

import de.cisha.chess.model.position.Position;
import de.cisha.android.ui.patterns.buttons.CustomButton;
import de.cisha.android.board.profile.model.PlayzoneStatisticData;
import android.view.ViewGroup;
import android.util.AttributeSet;
import android.content.Context;
import de.cisha.android.board.view.FieldView;
import android.widget.TextView;
import android.widget.LinearLayout;

public class YourGamesView extends LinearLayout
{
    TextView _draw;
    FieldView _field;
    TextView _lost;
    TextView _totalGames;
    TextView _won;
    
    public YourGamesView(final Context context) {
        super(context);
        this.init();
    }
    
    public YourGamesView(final Context context, final AttributeSet set) {
        super(context, set);
        this.init();
    }
    
    private void init() {
        this.setOrientation(1);
        inflate(this.getContext(), 2131427523, (ViewGroup)this);
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
    
    public void setPlayzonGameData(final PlayzoneStatisticData playzoneStatisticData) {
        if (playzoneStatisticData == null) {
            this._totalGames.setText((CharSequence)"");
            this._draw.setText((CharSequence)"");
            this._won.setText((CharSequence)"");
            this._lost.setText((CharSequence)"");
            if (this._field.getBoardView() != null) {
                this._field.getBoardView().setPosition(new Position());
            }
        }
        else {
            final TextView totalGames = this._totalGames;
            final StringBuilder sb = new StringBuilder();
            sb.append("");
            sb.append(playzoneStatisticData.getTotalGameCount());
            totalGames.setText((CharSequence)sb.toString());
            final TextView draw = this._draw;
            final StringBuilder sb2 = new StringBuilder();
            sb2.append("");
            sb2.append(playzoneStatisticData.getDraws());
            draw.setText((CharSequence)sb2.toString());
            final TextView won = this._won;
            final StringBuilder sb3 = new StringBuilder();
            sb3.append("");
            sb3.append(playzoneStatisticData.getWins());
            won.setText((CharSequence)sb3.toString());
            final TextView lost = this._lost;
            final StringBuilder sb4 = new StringBuilder();
            sb4.append("");
            sb4.append(playzoneStatisticData.getLosses());
            lost.setText((CharSequence)sb4.toString());
            if (this._field.getBoardView() != null) {
                this._field.getBoardView().setPosition(new Position(playzoneStatisticData.getFen()));
            }
        }
    }
}
