/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.ImageView
 *  android.widget.RelativeLayout
 *  android.widget.TextView
 */
package de.cisha.android.board.broadcast.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import de.cisha.chess.model.Country;
import de.cisha.chess.model.Opponent;
import de.cisha.chess.model.Rating;

public class TournamentPlayerInfoView
extends RelativeLayout {
    private ImageView _playerNameImageView;
    private TextView _playerNameTextView;
    private TextView _playerTitleTextView;

    public TournamentPlayerInfoView(Context context) {
        super(context);
        this.init();
    }

    public TournamentPlayerInfoView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init();
    }

    private void init() {
        TournamentPlayerInfoView.inflate((Context)this.getContext(), (int)2131427386, (ViewGroup)this);
        this._playerNameTextView = (TextView)this.findViewById(2131296383);
        this._playerTitleTextView = (TextView)this.findViewById(2131296384);
        this._playerNameImageView = (ImageView)this.findViewById(2131296382);
        if (this.isInEditMode()) {
            this._playerNameImageView.setImageResource(2131231190);
            this._playerNameTextView.setText((CharSequence)"Garry Kasparow");
            this._playerTitleTextView.setText((CharSequence)"GM 2856");
            return;
        }
        this._playerNameImageView.setImageResource(2131231366);
        this._playerNameTextView.setText((CharSequence)"");
        this._playerTitleTextView.setText((CharSequence)"");
    }

    public void setOpponnent(Opponent opponent) {
        this.setPlayerName(opponent.getName());
        String string = "";
        if (opponent.hasTitle()) {
            string = opponent.getTitle();
        }
        CharSequence charSequence = string;
        if (opponent.hasRating()) {
            charSequence = new StringBuilder();
            charSequence.append(string);
            charSequence.append(" ");
            charSequence.append(opponent.getRating().getRatingString());
            charSequence = charSequence.toString();
        }
        this.setPlayerTitle((String)charSequence);
        this.setPlayerFlagForCountry(opponent.getCountry());
    }

    public void setPlayerFlagForCountry(Country country) {
        if (country != null) {
            this._playerNameImageView.setVisibility(0);
            this._playerNameImageView.setImageResource(country.getImageId());
            return;
        }
        this._playerNameImageView.setVisibility(4);
    }

    public void setPlayerName(String string) {
        TextView textView = this._playerNameTextView;
        if (string == null) {
            string = "";
        }
        textView.setText((CharSequence)string);
    }

    public void setPlayerTitle(String string) {
        TextView textView = this._playerTitleTextView;
        if (string == null) {
            string = "";
        }
        textView.setText((CharSequence)string);
    }
}
