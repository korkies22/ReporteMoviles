/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.os.Bundle
 *  android.text.TextUtils
 *  android.text.TextUtils$TruncateAt
 *  android.text.format.DateFormat
 *  android.util.DisplayMetrics
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.ImageView
 *  android.widget.LinearLayout
 *  android.widget.LinearLayout$LayoutParams
 */
package de.cisha.android.board.analyze.view;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import de.cisha.android.board.view.KeyValueInfoDialogFragment;
import de.cisha.android.ui.patterns.buttons.CustomButton;
import de.cisha.android.ui.patterns.buttons.CustomButtonAlternative;
import de.cisha.android.ui.patterns.text.CustomTextView;
import de.cisha.android.ui.patterns.text.CustomTextViewTextStyle;
import de.cisha.chess.model.Country;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.GameResult;
import de.cisha.chess.model.GameStatus;
import de.cisha.chess.model.GameType;
import de.cisha.chess.model.Opponent;
import de.cisha.chess.model.Rating;
import java.util.Date;

public class GameInfoDialogFragment
extends KeyValueInfoDialogFragment {
    private GameInfoEditDelegate _delegate;
    private Game _game;

    private void addCountryRowView(String string, Country country) {
        if (country != null) {
            CustomTextView customTextView = new CustomTextView((Context)this.getActivity(), CustomTextViewTextStyle.ANALYSE_MOVELIST_BOLD, null);
            Object object = new StringBuilder();
            object.append(string);
            object.append(":");
            customTextView.setText((CharSequence)object.toString());
            float f = this.getResources().getDisplayMetrics().density;
            string = new LinearLayout((Context)this.getActivity());
            string.setOrientation(0);
            object = new ImageView((Context)this.getActivity());
            object.setImageResource(country.getImageId());
            int n = (int)(16.0f * f);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(n, n);
            layoutParams.gravity = 16;
            layoutParams.rightMargin = n = (int)(6.0f * f);
            layoutParams.leftMargin = n;
            string.addView((View)object, (ViewGroup.LayoutParams)layoutParams);
            object = new CustomTextView((Context)this.getActivity(), CustomTextViewTextStyle.ANALYSE_MOVELIST, null);
            object.setText((CharSequence)country.getDisplayName(this.getActivity().getResources()));
            object.setMaxLines(1);
            object.setEllipsize(TextUtils.TruncateAt.END);
            string.addView((View)object, (ViewGroup.LayoutParams)new LinearLayout.LayoutParams(-2, -2));
            this.addRowView(this.getRow((View)customTextView, (View)string));
            return;
        }
        this.addRowView(string, "");
    }

    private void editButtonClicked() {
        if (this._delegate != null) {
            this._delegate.editButtonClicked();
        }
    }

    private void initGameView(Game object) {
        boolean bl;
        Object object2;
        Object object3;
        boolean bl2;
        String string;
        block16 : {
            block15 : {
                if (object.getType().isEditable()) {
                    object3 = new CustomButtonAlternative((Context)this.getActivity());
                    object3.setText(2131689542);
                    object3.setOnClickListener(new View.OnClickListener(){

                        public void onClick(View view) {
                            GameInfoDialogFragment.this.editButtonClicked();
                        }
                    });
                    this.addButton((CustomButton)((Object)object3));
                }
                if (this.isFilled(object.getTitle())) {
                    this.addRowView(this.getActivity().getString(2131690001), object.getTitle());
                }
                if (this.isFilled(object.getEvent())) {
                    this.addRowView(this.getActivity().getString(2131690000), object.getEvent());
                }
                if (this.isFilled(object.getSite())) {
                    this.addRowView(this.getActivity().getString(2131690003), object.getSite());
                }
                if (object.getStartDate() != null) {
                    object3 = DateFormat.getDateFormat((Context)this.getActivity());
                    this.addRowView(this.getActivity().getString(2131689999), object3.format(object.getStartDate()));
                }
                object2 = object.getWhitePlayer();
                object3 = object.getBlackPlayer();
                boolean bl3 = false;
                bl2 = object2 != null && object2.hasRating() || object3 != null && object3.hasRating();
                if (object2 != null && object2.getCountry() != null) break block15;
                bl = bl3;
                if (object3 == null) break block16;
                bl = bl3;
                if (object3.getCountry() == null) break block16;
            }
            bl = true;
        }
        if (object.getBoard() > 0) {
            string = this.getActivity().getString(2131689998);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("");
            stringBuilder.append(object.getBoard());
            this.addRowView(string, stringBuilder.toString());
        }
        this.addRowView(this.getActivity().getString(2131690002), object.getResult().getResult().getShortDescription());
        if (object2 != null) {
            this.addRowView(this.getActivity().getString(2131690007), object2.getNameAndTitle());
            if (bl2) {
                string = this.getActivity().getString(2131690006);
                object = object2.hasRating() ? object2.getRating().getRatingString() : "";
                this.addRowView(string, (String)object);
            }
            if (bl) {
                this.addCountryRowView(this.getActivity().getString(2131690005), object2.getCountry());
            }
        }
        if (object3 != null) {
            this.addRowView(this.getActivity().getString(2131689997), object3.getNameAndTitle());
            if (bl2) {
                object2 = this.getActivity().getString(2131689996);
                object = object3.hasRating() ? object3.getRating().getRatingString() : "";
                this.addRowView((String)object2, (String)object);
            }
            if (bl) {
                this.addCountryRowView(this.getActivity().getString(2131689995), object3.getCountry());
            }
        }
    }

    private boolean isFilled(String string) {
        if (string != null && !string.trim().equals("")) {
            return true;
        }
        return false;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setTitle(this.getActivity().getString(2131690004));
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        layoutInflater = super.onCreateView(layoutInflater, viewGroup, bundle);
        if (this._game != null) {
            this.initGameView(this._game);
        }
        return layoutInflater;
    }

    public void setEditDelegate(GameInfoEditDelegate gameInfoEditDelegate) {
        this._delegate = gameInfoEditDelegate;
    }

    public void setGame(Game game) {
        this._game = game;
    }

    public static interface GameInfoEditDelegate {
        public void editButtonClicked();
    }

}
