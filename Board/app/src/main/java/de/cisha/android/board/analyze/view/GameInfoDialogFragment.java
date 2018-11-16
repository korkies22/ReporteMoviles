// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.analyze.view;

import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.os.Bundle;
import de.cisha.chess.model.Opponent;
import android.text.format.DateFormat;
import de.cisha.android.ui.patterns.buttons.CustomButton;
import android.view.View.OnClickListener;
import de.cisha.android.ui.patterns.buttons.CustomButtonAlternative;
import android.text.TextUtils.TruncateAt;
import android.view.ViewGroup.LayoutParams;
import android.view.View;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.content.Context;
import de.cisha.android.ui.patterns.text.CustomTextView;
import de.cisha.android.ui.patterns.text.CustomTextViewTextStyle;
import de.cisha.chess.model.Country;
import de.cisha.chess.model.Game;
import de.cisha.android.board.view.KeyValueInfoDialogFragment;

public class GameInfoDialogFragment extends KeyValueInfoDialogFragment
{
    private GameInfoEditDelegate _delegate;
    private Game _game;
    
    private void addCountryRowView(final String s, final Country country) {
        if (country != null) {
            final CustomTextView customTextView = new CustomTextView((Context)this.getActivity(), CustomTextViewTextStyle.ANALYSE_MOVELIST_BOLD, null);
            final StringBuilder sb = new StringBuilder();
            sb.append(s);
            sb.append(":");
            customTextView.setText((CharSequence)sb.toString());
            final float density = this.getResources().getDisplayMetrics().density;
            final LinearLayout linearLayout = new LinearLayout((Context)this.getActivity());
            linearLayout.setOrientation(0);
            final ImageView imageView = new ImageView((Context)this.getActivity());
            imageView.setImageResource(country.getImageId());
            final int n = (int)(16.0f * density);
            final LinearLayout.LayoutParams linearLayout.LayoutParams = new LinearLayout.LayoutParams(n, n);
            linearLayout.LayoutParams.gravity = 16;
            final int n2 = (int)(6.0f * density);
            linearLayout.LayoutParams.rightMargin = n2;
            linearLayout.LayoutParams.leftMargin = n2;
            linearLayout.addView((View)imageView, (ViewGroup.LayoutParams)linearLayout.LayoutParams);
            final CustomTextView customTextView2 = new CustomTextView((Context)this.getActivity(), CustomTextViewTextStyle.ANALYSE_MOVELIST, null);
            customTextView2.setText((CharSequence)country.getDisplayName(this.getActivity().getResources()));
            customTextView2.setMaxLines(1);
            customTextView2.setEllipsize(TextUtils.TruncateAt.END);
            linearLayout.addView((View)customTextView2, (ViewGroup.LayoutParams)new LinearLayout.LayoutParams(-2, -2));
            this.addRowView(this.getRow((View)customTextView, (View)linearLayout));
            return;
        }
        this.addRowView(s, "");
    }
    
    private void editButtonClicked() {
        if (this._delegate != null) {
            this._delegate.editButtonClicked();
        }
    }
    
    private void initGameView(final Game game) {
        if (game.getType().isEditable()) {
            final CustomButtonAlternative customButtonAlternative = new CustomButtonAlternative((Context)this.getActivity());
            customButtonAlternative.setText(2131689542);
            customButtonAlternative.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
                public void onClick(final View view) {
                    GameInfoDialogFragment.this.editButtonClicked();
                }
            });
            this.addButton(customButtonAlternative);
        }
        if (this.isFilled(game.getTitle())) {
            this.addRowView(this.getActivity().getString(2131690001), game.getTitle());
        }
        if (this.isFilled(game.getEvent())) {
            this.addRowView(this.getActivity().getString(2131690000), game.getEvent());
        }
        if (this.isFilled(game.getSite())) {
            this.addRowView(this.getActivity().getString(2131690003), game.getSite());
        }
        if (game.getStartDate() != null) {
            this.addRowView(this.getActivity().getString(2131689999), DateFormat.getDateFormat((Context)this.getActivity()).format(game.getStartDate()));
        }
        final Opponent whitePlayer = game.getWhitePlayer();
        final Opponent blackPlayer = game.getBlackPlayer();
        final boolean b = false;
        final boolean b2 = (whitePlayer != null && whitePlayer.hasRating()) || (blackPlayer != null && blackPlayer.hasRating());
        boolean b3 = false;
        Label_0253: {
            if (whitePlayer == null || whitePlayer.getCountry() == null) {
                b3 = b;
                if (blackPlayer == null) {
                    break Label_0253;
                }
                b3 = b;
                if (blackPlayer.getCountry() == null) {
                    break Label_0253;
                }
            }
            b3 = true;
        }
        if (game.getBoard() > 0) {
            final String string = this.getActivity().getString(2131689998);
            final StringBuilder sb = new StringBuilder();
            sb.append("");
            sb.append(game.getBoard());
            this.addRowView(string, sb.toString());
        }
        this.addRowView(this.getActivity().getString(2131690002), game.getResult().getResult().getShortDescription());
        if (whitePlayer != null) {
            this.addRowView(this.getActivity().getString(2131690007), whitePlayer.getNameAndTitle());
            if (b2) {
                final String string2 = this.getActivity().getString(2131690006);
                String ratingString;
                if (whitePlayer.hasRating()) {
                    ratingString = whitePlayer.getRating().getRatingString();
                }
                else {
                    ratingString = "";
                }
                this.addRowView(string2, ratingString);
            }
            if (b3) {
                this.addCountryRowView(this.getActivity().getString(2131690005), whitePlayer.getCountry());
            }
        }
        if (blackPlayer != null) {
            this.addRowView(this.getActivity().getString(2131689997), blackPlayer.getNameAndTitle());
            if (b2) {
                final String string3 = this.getActivity().getString(2131689996);
                String ratingString2;
                if (blackPlayer.hasRating()) {
                    ratingString2 = blackPlayer.getRating().getRatingString();
                }
                else {
                    ratingString2 = "";
                }
                this.addRowView(string3, ratingString2);
            }
            if (b3) {
                this.addCountryRowView(this.getActivity().getString(2131689995), blackPlayer.getCountry());
            }
        }
    }
    
    private boolean isFilled(final String s) {
        return s != null && !s.trim().equals("");
    }
    
    @Override
    public void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this.setTitle(this.getActivity().getString(2131690004));
    }
    
    @Override
    public View onCreateView(final LayoutInflater layoutInflater, final ViewGroup viewGroup, final Bundle bundle) {
        final View onCreateView = super.onCreateView(layoutInflater, viewGroup, bundle);
        if (this._game != null) {
            this.initGameView(this._game);
        }
        return onCreateView;
    }
    
    public void setEditDelegate(final GameInfoEditDelegate delegate) {
        this._delegate = delegate;
    }
    
    public void setGame(final Game game) {
        this._game = game;
    }
    
    public interface GameInfoEditDelegate
    {
        void editButtonClicked();
    }
}
