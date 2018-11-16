// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.statistics.view;

import android.view.View.OnClickListener;
import de.cisha.android.board.broadcast.model.TournamentGameInfo;
import de.cisha.android.board.broadcast.model.TournamentPlayer;
import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.os.Build.VERSION;
import android.view.ViewGroup;
import android.util.AttributeSet;
import android.content.Context;
import de.cisha.android.ui.patterns.text.CustomTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class OpponentRowItem extends LinearLayout
{
    private ImageView _colorIndicatorView;
    private ImageView _flagView;
    private ImageView _openGameView;
    private CustomTextView _viewBoardPointsText;
    private CustomTextView _viewName;
    private CustomTextView _viewResult;
    private CustomTextView _viewRoundNumberText;
    
    public OpponentRowItem(final Context context) {
        super(context);
        this.init();
    }
    
    public OpponentRowItem(final Context context, final AttributeSet set) {
        super(context, set);
        this.init();
    }
    
    private void init() {
        this.setHardwareLayerType();
        inflate(this.getContext(), 2131427381, (ViewGroup)this);
        this._viewRoundNumberText = (CustomTextView)this.findViewById(2131296681);
        this._viewName = (CustomTextView)this.findViewById(2131296678);
        this._viewResult = (CustomTextView)this.findViewById(2131296680);
        this._flagView = (ImageView)this.findViewById(2131296677);
        this._colorIndicatorView = (ImageView)this.findViewById(2131296676);
        this._openGameView = (ImageView)this.findViewById(2131296679);
        this.setPadding(0, 0, 0, (int)this.getResources().getDisplayMetrics().density);
    }
    
    @SuppressLint({ "NewApi" })
    private void setHardwareLayerType() {
        if (Build.VERSION.SDK_INT >= 16) {
            this.setLayerType(2, (Paint)null);
        }
    }
    
    public void setDataForOpponentOfPlayer(final TournamentPlayer tournamentPlayer, final TournamentGameInfo tournamentGameInfo) {
        final CustomTextView viewRoundNumberText = this._viewRoundNumberText;
        final StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(tournamentGameInfo.getRoundNumber());
        viewRoundNumberText.setText((CharSequence)sb.toString());
        final TournamentPlayer opponentOfPlayer = tournamentGameInfo.getOpponentOfPlayer(tournamentPlayer);
        String text;
        if (tournamentGameInfo.getColorForPlayer(tournamentPlayer)) {
            this._colorIndicatorView.setImageResource(2131231910);
            text = tournamentGameInfo.getStatus().getResult().getResultWhite();
        }
        else {
            this._colorIndicatorView.setImageResource(2131230881);
            text = tournamentGameInfo.getStatus().getResult().getResultBlack();
        }
        this._viewResult.setText((CharSequence)text);
        if (opponentOfPlayer != null) {
            if (opponentOfPlayer.getCountry() != null) {
                this._flagView.setImageResource(opponentOfPlayer.getCountry().getImageId());
                this._flagView.setVisibility(0);
            }
            else {
                this._flagView.setVisibility(8);
            }
            this._viewName.setText((CharSequence)opponentOfPlayer.getNameWithTitleAndRating());
        }
    }
    
    public void setGameSelectionListener(final View.OnClickListener onClickListener) {
        this._openGameView.setOnClickListener(onClickListener);
        this._colorIndicatorView.setOnClickListener(onClickListener);
        this._viewResult.setOnClickListener(onClickListener);
    }
}
