/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.ImageView
 *  android.widget.LinearLayout
 *  android.widget.TextView
 */
package de.cisha.android.board.broadcast.statistics.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.neovisionaries.i18n.CountryCode;
import de.cisha.android.board.broadcast.model.BroadcastGameStatus;
import de.cisha.android.board.broadcast.model.Tournament;
import de.cisha.android.board.broadcast.model.TournamentGameInfo;
import de.cisha.android.board.broadcast.model.TournamentPlayer;
import de.cisha.android.view.WebImageView;
import java.text.NumberFormat;
import java.util.List;

public class BroadcastPlayerView
extends LinearLayout {
    private TextView _nameText;
    private TextView _performanceText;
    private ImageView _playerCountryFlag;
    private TextView _playerCountryText;
    private WebImageView _playerFlag;
    private TextView _pointsText;
    private TextView _ratingGainText;
    private TextView _ratingText;
    private TextView _titleText;

    public BroadcastPlayerView(Context context) {
        super(context);
        this.setupViews();
    }

    public BroadcastPlayerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.setupViews();
    }

    private void reset() {
        this._playerCountryFlag.setImageResource(2131231366);
        this._playerFlag.setImageDrawable(null);
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
        BroadcastPlayerView.inflate((Context)this.getContext(), (int)2131427380, (ViewGroup)this);
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

    public void setDataForPlayer(TournamentPlayer tournamentPlayer, Tournament object) {
        this.reset();
        if (tournamentPlayer != null) {
            if (tournamentPlayer.getCountry() != null) {
                this._playerCountryFlag.setImageResource(tournamentPlayer.getCountry().getImageId());
                this._playerCountryText.setText((CharSequence)tournamentPlayer.getCountry().getDisplayName(this.getContext().getResources()));
            }
            this._nameText.setText((CharSequence)tournamentPlayer.getFullName());
            Object object2 = this._titleText;
            Object object3 = tournamentPlayer.getTitle() != null ? tournamentPlayer.getTitle() : "";
            object2.setText((CharSequence)object3);
            object3 = this._ratingText;
            object2 = new StringBuilder();
            object2.append("");
            object2.append(tournamentPlayer.getElo());
            object3.setText((CharSequence)object2.toString());
            if (object != null) {
                this._playerFlag.setWebImageFrom(object.getPlayerImageUrl(tournamentPlayer));
                object2 = NumberFormat.getInstance();
                object2.setMinimumFractionDigits(1);
                object2.setMaximumFractionDigits(1);
                float f = object.getRatingChangeForPlayer(tournamentPlayer);
                TextView object42 = this._ratingGainText;
                StringBuilder stringBuilder = new StringBuilder();
                float f2 = 0.0f;
                object3 = f > 0.0f ? "+" : "";
                stringBuilder.append((String)object3);
                stringBuilder.append(object2.format(f));
                object42.setText((CharSequence)stringBuilder.toString());
                int n = 0;
                for (TournamentGameInfo tournamentGameInfo : object.getGamesForPlayer(tournamentPlayer)) {
                    if (!tournamentGameInfo.getStatus().isFinished()) continue;
                    ++n;
                    f2 += tournamentGameInfo.getScoreForPlayer(tournamentPlayer);
                }
                object3 = "-";
                if (n > 0) {
                    object3 = new StringBuilder();
                    object3.append("");
                    object3.append(object.getPerformanceForPlayer(tournamentPlayer));
                    object3 = object3.toString();
                }
                this._performanceText.setText((CharSequence)object3);
                tournamentPlayer = this._pointsText;
                object = new StringBuilder();
                object.append(object2.format(f2));
                object.append(" / ");
                object.append(n);
                tournamentPlayer.setText((CharSequence)object.toString());
            }
        }
    }
}
