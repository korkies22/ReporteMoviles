// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.playzone.view;

import android.text.Html;
import de.cisha.android.board.playzone.model.GameEndMessageHelper;
import android.view.ViewGroup;
import android.util.AttributeSet;
import android.content.Context;
import android.widget.TextView;
import de.cisha.android.board.playzone.model.AfterGameInformation;
import de.cisha.android.board.ModelHolder;
import android.widget.LinearLayout;

public class PlayzoneResultView extends LinearLayout implements ModelChangeListener<AfterGameInformation>
{
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
    
    public PlayzoneResultView(final Context context) {
        super(context);
        this.initView();
    }
    
    public PlayzoneResultView(final Context context, final AttributeSet set) {
        super(context, set);
        this.initView();
    }
    
    private String getScoreText(final float n) {
        if (n == 0.0f) {
            return "0";
        }
        if (n < 1.0f) {
            return "&frac12;";
        }
        final double n2 = n;
        final double floor = Math.floor(n2);
        final StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append((int)floor);
        String s;
        if (n2 - floor == 0.5) {
            s = "&frac12;";
        }
        else {
            s = "";
        }
        sb.append(s);
        return sb.toString();
    }
    
    private void initView() {
        this.setOrientation(1);
        this.setGravity(1);
        inflate(this.getContext(), 2131427503, (ViewGroup)this);
        this._introTextView = (TextView)this.findViewById(2131296765);
        this._newRatingTextView = (TextView)this.findViewById(2131296767);
        this._changeRatingTextView = (TextView)this.findViewById(2131296768);
        this._indicatorTextView = (TextView)this.findViewById(2131296769);
        this._scoreTextView = (TextView)this.findViewById(2131296770);
        this._newRatingDescriptionTextView = (TextView)this.findViewById(2131296771);
        this._newScoreDescriptionTextView = (TextView)this.findViewById(2131296772);
    }
    
    private void setAfterGameInformation(final AfterGameInformation afterGameInformation) {
        final String title = afterGameInformation.getOpponent().getTitle();
        if (title != null) {
            if (!title.trim().equals("")) {
                final StringBuilder sb = new StringBuilder();
                sb.append("(");
                sb.append(title);
                sb.append(")");
                sb.toString();
            }
        }
        this._introTextView.setText((CharSequence)new GameEndMessageHelper(this.getResources()).getMessage(afterGameInformation.getStatus(), afterGameInformation.playerIsWhite(), afterGameInformation.playerToMove(), afterGameInformation.getOpponent().getName()));
        final boolean waitForResultView = this._waitForResultView;
        final int n = 8;
        int visibility;
        if (waitForResultView && !afterGameInformation.hasScore() && !afterGameInformation.hasRating()) {
            visibility = 0;
        }
        else {
            visibility = 8;
        }
        this.findViewById(2131296766).setVisibility(visibility);
        final int ratingChangePlayer = afterGameInformation.getRatingChangePlayer();
        if (afterGameInformation.hasRating()) {
            this._newRatingDescriptionTextView.setText(2131689993);
            final TextView newRatingTextView = this._newRatingTextView;
            final StringBuilder sb2 = new StringBuilder();
            sb2.append(afterGameInformation.getRatingPlayer().getRatingString());
            sb2.append(" |");
            newRatingTextView.setText((CharSequence)sb2.toString());
            final TextView changeRatingTextView = this._changeRatingTextView;
            final StringBuilder sb3 = new StringBuilder();
            sb3.append(Math.abs(ratingChangePlayer));
            sb3.append("");
            changeRatingTextView.setText((CharSequence)sb3.toString());
            final TextView indicatorTextView = this._indicatorTextView;
            String text;
            if (ratingChangePlayer == 0) {
                text = "";
            }
            else if (ratingChangePlayer > 0) {
                text = "\u279a";
            }
            else {
                text = "\u2798";
            }
            indicatorTextView.setText((CharSequence)text);
            final TextView indicatorTextView2 = this._indicatorTextView;
            int textColor;
            if (ratingChangePlayer > 0) {
                textColor = -6964161;
            }
            else {
                textColor = -3460298;
            }
            indicatorTextView2.setTextColor(textColor);
        }
        int n2 = n;
        if (this._waitForResultView) {
            n2 = 4;
        }
        int n3;
        if (afterGameInformation.hasRating()) {
            n3 = 0;
        }
        else {
            n3 = n2;
        }
        if (afterGameInformation.hasScore()) {
            n2 = 0;
        }
        this._newRatingDescriptionTextView.setVisibility(n3);
        this._newRatingTextView.setVisibility(n3);
        this._changeRatingTextView.setVisibility(n3);
        this._indicatorTextView.setVisibility(n3);
        if (afterGameInformation.hasScore()) {
            final StringBuilder sb4 = new StringBuilder();
            sb4.append(this.getScoreText(afterGameInformation.getScorePlayer()));
            sb4.append(" - ");
            sb4.append(this.getScoreText(afterGameInformation.getScoreOpponent()));
            this._scoreTextView.setText((CharSequence)Html.fromHtml(sb4.toString()));
        }
        this._newScoreDescriptionTextView.setVisibility(n2);
        this._scoreTextView.setVisibility(n2);
    }
    
    public void modelChanged(final AfterGameInformation afterGameInformation) {
        this.setAfterGameInformation(afterGameInformation);
    }
    
    public void setAfterGameInformationHolder(final ModelHolder<AfterGameInformation> modelHolder) {
        modelHolder.addModelChangeListener((ModelHolder.ModelChangeListener<AfterGameInformation>)this);
        this.setAfterGameInformation(modelHolder.getModel());
    }
    
    public void setWaitForResultLoaderEnabled(final boolean waitForResultView) {
        this._waitForResultView = waitForResultView;
    }
}
