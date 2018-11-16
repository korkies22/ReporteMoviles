// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.view;

import de.cisha.chess.model.Country;
import de.cisha.chess.model.Opponent;
import android.view.ViewGroup;
import android.util.AttributeSet;
import android.content.Context;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class TournamentPlayerInfoView extends RelativeLayout
{
    private ImageView _playerNameImageView;
    private TextView _playerNameTextView;
    private TextView _playerTitleTextView;
    
    public TournamentPlayerInfoView(final Context context) {
        super(context);
        this.init();
    }
    
    public TournamentPlayerInfoView(final Context context, final AttributeSet set) {
        super(context, set);
        this.init();
    }
    
    private void init() {
        inflate(this.getContext(), 2131427386, (ViewGroup)this);
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
    
    public void setOpponnent(final Opponent opponent) {
        this.setPlayerName(opponent.getName());
        String title = "";
        if (opponent.hasTitle()) {
            title = opponent.getTitle();
        }
        String string = title;
        if (opponent.hasRating()) {
            final StringBuilder sb = new StringBuilder();
            sb.append(title);
            sb.append(" ");
            sb.append(opponent.getRating().getRatingString());
            string = sb.toString();
        }
        this.setPlayerTitle(string);
        this.setPlayerFlagForCountry(opponent.getCountry());
    }
    
    public void setPlayerFlagForCountry(final Country country) {
        if (country != null) {
            this._playerNameImageView.setVisibility(0);
            this._playerNameImageView.setImageResource(country.getImageId());
            return;
        }
        this._playerNameImageView.setVisibility(4);
    }
    
    public void setPlayerName(String text) {
        final TextView playerNameTextView = this._playerNameTextView;
        if (text == null) {
            text = "";
        }
        playerNameTextView.setText((CharSequence)text);
    }
    
    public void setPlayerTitle(String text) {
        final TextView playerTitleTextView = this._playerTitleTextView;
        if (text == null) {
            text = "";
        }
        playerTitleTextView.setText((CharSequence)text);
    }
}
