// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.statistics.view;

import java.util.Iterator;
import de.cisha.android.board.broadcast.model.TournamentGameInfo;
import java.text.NumberFormat;
import de.cisha.android.board.broadcast.model.Tournament;
import de.cisha.android.board.broadcast.model.TournamentPlayer;
import android.view.ViewGroup;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.content.Context;
import de.cisha.android.view.WebImageView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.LinearLayout;

public class BroadcastPlayerView extends LinearLayout
{
    private TextView _nameText;
    private TextView _performanceText;
    private ImageView _playerCountryFlag;
    private TextView _playerCountryText;
    private WebImageView _playerFlag;
    private TextView _pointsText;
    private TextView _ratingGainText;
    private TextView _ratingText;
    private TextView _titleText;
    
    public BroadcastPlayerView(final Context context) {
        super(context);
        this.setupViews();
    }
    
    public BroadcastPlayerView(final Context context, final AttributeSet set) {
        super(context, set);
        this.setupViews();
    }
    
    private void reset() {
        this._playerCountryFlag.setImageResource(2131231366);
        this._playerFlag.setImageDrawable((Drawable)null);
        this._nameText.setText((CharSequence)"");
        this._titleText.setText((CharSequence)"");
        this._playerCountryText.setText((CharSequence)"");
        this._ratingText.setText((CharSequence)"");
        this._pointsText.setText((CharSequence)"");
        this._performanceText.setText((CharSequence)"");
        this._ratingGainText.setText((CharSequence)"");
    }
    
    private void setupViews() {
        this.setOrientation(1);
        inflate(this.getContext(), 2131427380, (ViewGroup)this);
        this._playerCountryFlag = (ImageView)this.findViewById(2131296663);
        this._playerFlag = (WebImageView)this.findViewById(2131296667);
        this._nameText = (TextView)this.findViewById(2131296666);
        this._titleText = (TextView)this.findViewById(2131296673);
        this._playerCountryText = (TextView)this.findViewById(2131296664);
        this._ratingText = (TextView)this.findViewById(2131296671);
        this._pointsText = (TextView)this.findViewById(2131296670);
        this._performanceText = (TextView)this.findViewById(2131296669);
        this._ratingGainText = (TextView)this.findViewById(2131296665);
        this.reset();
    }
    
    public void setDataForPlayer(final TournamentPlayer tournamentPlayer, final Tournament tournament) {
        this.reset();
        if (tournamentPlayer != null) {
            if (tournamentPlayer.getCountry() != null) {
                this._playerCountryFlag.setImageResource(tournamentPlayer.getCountry().getImageId());
                this._playerCountryText.setText((CharSequence)tournamentPlayer.getCountry().getDisplayName(this.getContext().getResources()));
            }
            this._nameText.setText((CharSequence)tournamentPlayer.getFullName());
            final TextView titleText = this._titleText;
            String title;
            if (tournamentPlayer.getTitle() != null) {
                title = tournamentPlayer.getTitle();
            }
            else {
                title = "";
            }
            titleText.setText((CharSequence)title);
            final TextView ratingText = this._ratingText;
            final StringBuilder sb = new StringBuilder();
            sb.append("");
            sb.append(tournamentPlayer.getElo());
            ratingText.setText((CharSequence)sb.toString());
            if (tournament != null) {
                this._playerFlag.setWebImageFrom(tournament.getPlayerImageUrl(tournamentPlayer));
                final NumberFormat instance = NumberFormat.getInstance();
                instance.setMinimumFractionDigits(1);
                instance.setMaximumFractionDigits(1);
                final float ratingChangeForPlayer = tournament.getRatingChangeForPlayer(tournamentPlayer);
                final TextView ratingGainText = this._ratingGainText;
                final StringBuilder sb2 = new StringBuilder();
                float n = 0.0f;
                String s;
                if (ratingChangeForPlayer > 0.0f) {
                    s = "+";
                }
                else {
                    s = "";
                }
                sb2.append(s);
                sb2.append(instance.format(ratingChangeForPlayer));
                ratingGainText.setText((CharSequence)sb2.toString());
                int n2 = 0;
                for (final TournamentGameInfo tournamentGameInfo : tournament.getGamesForPlayer(tournamentPlayer)) {
                    if (tournamentGameInfo.getStatus().isFinished()) {
                        ++n2;
                        n += tournamentGameInfo.getScoreForPlayer(tournamentPlayer);
                    }
                }
                String string = "-";
                if (n2 > 0) {
                    final StringBuilder sb3 = new StringBuilder();
                    sb3.append("");
                    sb3.append(tournament.getPerformanceForPlayer(tournamentPlayer));
                    string = sb3.toString();
                }
                this._performanceText.setText((CharSequence)string);
                final TextView pointsText = this._pointsText;
                final StringBuilder sb4 = new StringBuilder();
                sb4.append(instance.format(n));
                sb4.append(" / ");
                sb4.append(n2);
                pointsText.setText((CharSequence)sb4.toString());
            }
        }
    }
}
