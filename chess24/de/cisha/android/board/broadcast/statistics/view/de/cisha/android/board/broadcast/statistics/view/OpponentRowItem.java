/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Paint
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.ImageView
 *  android.widget.LinearLayout
 */
package de.cisha.android.board.broadcast.statistics.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.neovisionaries.i18n.CountryCode;
import de.cisha.android.board.broadcast.model.BroadcastGameStatus;
import de.cisha.android.board.broadcast.model.TournamentGameInfo;
import de.cisha.android.board.broadcast.model.TournamentPlayer;
import de.cisha.android.ui.patterns.text.CustomTextView;
import de.cisha.chess.model.GameResult;

public class OpponentRowItem
extends LinearLayout {
    private ImageView _colorIndicatorView;
    private ImageView _flagView;
    private ImageView _openGameView;
    private CustomTextView _viewBoardPointsText;
    private CustomTextView _viewName;
    private CustomTextView _viewResult;
    private CustomTextView _viewRoundNumberText;

    public OpponentRowItem(Context context) {
        super(context);
        this.init();
    }

    public OpponentRowItem(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init();
    }

    private void init() {
        this.setHardwareLayerType();
        OpponentRowItem.inflate((Context)this.getContext(), (int)2131427381, (ViewGroup)this);
        this._viewRoundNumberText = (CustomTextView)this.findViewById(2131296681);
        this._viewName = (CustomTextView)this.findViewById(2131296678);
        this._viewResult = (CustomTextView)this.findViewById(2131296680);
        this._flagView = (ImageView)this.findViewById(2131296677);
        this._colorIndicatorView = (ImageView)this.findViewById(2131296676);
        this._openGameView = (ImageView)this.findViewById(2131296679);
        this.setPadding(0, 0, 0, (int)this.getResources().getDisplayMetrics().density);
    }

    @SuppressLint(value={"NewApi"})
    private void setHardwareLayerType() {
        if (Build.VERSION.SDK_INT >= 16) {
            this.setLayerType(2, null);
        }
    }

    public void setDataForOpponentOfPlayer(TournamentPlayer object, TournamentGameInfo tournamentGameInfo) {
        Object object2 = this._viewRoundNumberText;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(tournamentGameInfo.getRoundNumber());
        object2.setText((CharSequence)stringBuilder.toString());
        object2 = tournamentGameInfo.getOpponentOfPlayer((TournamentPlayer)object);
        if (tournamentGameInfo.getColorForPlayer((TournamentPlayer)object)) {
            this._colorIndicatorView.setImageResource(2131231910);
            object = tournamentGameInfo.getStatus().getResult().getResultWhite();
        } else {
            this._colorIndicatorView.setImageResource(2131230881);
            object = tournamentGameInfo.getStatus().getResult().getResultBlack();
        }
        this._viewResult.setText((CharSequence)object);
        if (object2 != null) {
            if (object2.getCountry() != null) {
                this._flagView.setImageResource(object2.getCountry().getImageId());
                this._flagView.setVisibility(0);
            } else {
                this._flagView.setVisibility(8);
            }
            this._viewName.setText((CharSequence)object2.getNameWithTitleAndRating());
        }
    }

    public void setGameSelectionListener(View.OnClickListener onClickListener) {
        this._openGameView.setOnClickListener(onClickListener);
        this._colorIndicatorView.setOnClickListener(onClickListener);
        this._viewResult.setOnClickListener(onClickListener);
    }
}
