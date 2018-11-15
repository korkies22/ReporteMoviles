/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.text.Html
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.LinearLayout
 *  android.widget.TextView
 */
package de.cisha.android.board.playzone.view;

import android.content.Context;
import android.content.res.Resources;
import android.text.Html;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import de.cisha.android.board.ModelHolder;
import de.cisha.android.board.playzone.model.AfterGameInformation;
import de.cisha.android.board.playzone.model.GameEndMessageHelper;
import de.cisha.chess.model.GameStatus;
import de.cisha.chess.model.Opponent;
import de.cisha.chess.model.Rating;

public class PlayzoneResultView
extends LinearLayout
implements ModelHolder.ModelChangeListener<AfterGameInformation> {
    private static final int COLOR_RATING_CHANGE_NEGATIVE = -3460298;
    private static final int COLOR_RATING_CHANGE_POSITIVE = -6964161;
    private static final String SYMBOL_RATING_CHANGE_NEGATIVE = "\u2798";
    private static final String SYMBOL_RATING_CHANGE_POSITIVE = "\u279a";
    private TextView _changeRatingTextView;
    private TextView _indicatorTextView;
    private TextView _introTextView;
    private TextView _newRatingDescriptionTextView;
    private TextView _newRatingTextView;
    private TextView _newScoreDescriptionTextView;
    private TextView _scoreTextView;
    private boolean _waitForResultView;

    public PlayzoneResultView(Context context) {
        super(context);
        this.initView();
    }

    public PlayzoneResultView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.initView();
    }

    private String getScoreText(float f) {
        if (f == 0.0f) {
            return "0";
        }
        if (f < 1.0f) {
            return "&frac12;";
        }
        double d = f;
        double d2 = Math.floor(d);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append((int)d2);
        String string = d - d2 == 0.5 ? "&frac12;" : "";
        stringBuilder.append(string);
        return stringBuilder.toString();
    }

    private void initView() {
        this.setOrientation(1);
        this.setGravity(1);
        PlayzoneResultView.inflate((Context)this.getContext(), (int)2131427503, (ViewGroup)this);
        this._introTextView = (TextView)this.findViewById(2131296765);
        this._newRatingTextView = (TextView)this.findViewById(2131296767);
        this._changeRatingTextView = (TextView)this.findViewById(2131296768);
        this._indicatorTextView = (TextView)this.findViewById(2131296769);
        this._scoreTextView = (TextView)this.findViewById(2131296770);
        this._newRatingDescriptionTextView = (TextView)this.findViewById(2131296771);
        this._newScoreDescriptionTextView = (TextView)this.findViewById(2131296772);
    }

    private void setAfterGameInformation(AfterGameInformation object) {
        StringBuilder stringBuilder;
        CharSequence charSequence = object.getOpponent().getTitle();
        if (charSequence != null && !charSequence.trim().equals("")) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("(");
            stringBuilder.append((String)charSequence);
            stringBuilder.append(")");
            stringBuilder.toString();
        }
        charSequence = new GameEndMessageHelper(this.getResources()).getMessage(object.getStatus(), object.playerIsWhite(), object.playerToMove(), object.getOpponent().getName());
        this._introTextView.setText(charSequence);
        boolean bl = this._waitForResultView;
        int n = 8;
        int n2 = bl && !object.hasScore() && !object.hasRating() ? 0 : 8;
        this.findViewById(2131296766).setVisibility(n2);
        n2 = object.getRatingChangePlayer();
        if (object.hasRating()) {
            this._newRatingDescriptionTextView.setText(2131689993);
            charSequence = this._newRatingTextView;
            stringBuilder = new StringBuilder();
            stringBuilder.append(object.getRatingPlayer().getRatingString());
            stringBuilder.append(" |");
            charSequence.setText((CharSequence)stringBuilder.toString());
            charSequence = this._changeRatingTextView;
            stringBuilder = new StringBuilder();
            stringBuilder.append(Math.abs(n2));
            stringBuilder.append("");
            charSequence.setText((CharSequence)stringBuilder.toString());
            stringBuilder = this._indicatorTextView;
            charSequence = n2 == 0 ? "" : (n2 > 0 ? SYMBOL_RATING_CHANGE_POSITIVE : SYMBOL_RATING_CHANGE_NEGATIVE);
            stringBuilder.setText(charSequence);
            charSequence = this._indicatorTextView;
            n2 = n2 > 0 ? -6964161 : -3460298;
            charSequence.setTextColor(n2);
        }
        n2 = n;
        if (this._waitForResultView) {
            n2 = 4;
        }
        n = object.hasRating() ? 0 : n2;
        if (object.hasScore()) {
            n2 = 0;
        }
        this._newRatingDescriptionTextView.setVisibility(n);
        this._newRatingTextView.setVisibility(n);
        this._changeRatingTextView.setVisibility(n);
        this._indicatorTextView.setVisibility(n);
        if (object.hasScore()) {
            charSequence = new StringBuilder();
            charSequence.append(this.getScoreText(object.getScorePlayer()));
            charSequence.append(" - ");
            charSequence.append(this.getScoreText(object.getScoreOpponent()));
            object = charSequence.toString();
            this._scoreTextView.setText((CharSequence)Html.fromHtml((String)object));
        }
        this._newScoreDescriptionTextView.setVisibility(n2);
        this._scoreTextView.setVisibility(n2);
    }

    @Override
    public void modelChanged(AfterGameInformation afterGameInformation) {
        this.setAfterGameInformation(afterGameInformation);
    }

    public void setAfterGameInformationHolder(ModelHolder<AfterGameInformation> modelHolder) {
        modelHolder.addModelChangeListener(this);
        this.setAfterGameInformation(modelHolder.getModel());
    }

    public void setWaitForResultLoaderEnabled(boolean bl) {
        this._waitForResultView = bl;
    }
}
