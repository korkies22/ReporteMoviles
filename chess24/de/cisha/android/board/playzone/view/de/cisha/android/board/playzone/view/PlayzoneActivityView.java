/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.RelativeLayout
 */
package de.cisha.android.board.playzone.view;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.playzone.view.ChessClockView;
import de.cisha.android.board.view.FieldView;
import de.cisha.android.board.view.TakenPiecesView;

public class PlayzoneActivityView
extends RelativeLayout {
    public PlayzoneActivityView(Context context) {
        super(context);
    }

    public PlayzoneActivityView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    private void measureOptionalViews() {
        synchronized (this) {
            IContentPresenter iContentPresenter = (IContentPresenter)this.getContext();
            int n = iContentPresenter.getContentMaxHeight();
            int n2 = iContentPresenter.getContentMaxWidth();
            ChessClockView chessClockView = (ChessClockView)this.findViewById(2131296712);
            TakenPiecesView takenPiecesView = (TakenPiecesView)this.findViewById(2131296777);
            TakenPiecesView takenPiecesView2 = (TakenPiecesView)this.findViewById(2131296778);
            n2 = View.MeasureSpec.makeMeasureSpec((int)n2, (int)Integer.MIN_VALUE);
            int n3 = View.MeasureSpec.makeMeasureSpec((int)0, (int)0);
            chessClockView.measure(n2, n3);
            View view = this.findViewById(2131296747);
            view.measure(n2, n3);
            n = n - iContentPresenter.getContentMaxWidth() - chessClockView.getMeasuredHeight() - view.getMeasuredHeight();
            iContentPresenter = this.findViewById(2131296776);
            iContentPresenter.measure(n2, n3);
            chessClockView = this.findViewById(2131296749);
            chessClockView.measure(n2, n3);
            view = this.findViewById(2131296764);
            view.measure(n2, n3);
            if (chessClockView.getMeasuredHeight() > n) {
                chessClockView.setVisibility(8);
                view.setVisibility(8);
            } else {
                chessClockView.setVisibility(0);
                view.setVisibility(0);
                n -= chessClockView.getMeasuredHeight() + view.getMeasuredHeight();
            }
            if (iContentPresenter.getMeasuredHeight() > n) {
                takenPiecesView.setVisibility(8);
                takenPiecesView2.setVisibility(8);
            } else {
                takenPiecesView.setVisibility(0);
                takenPiecesView2.setVisibility(0);
                takenPiecesView.getMeasuredHeight();
            }
            this.requestLayout();
            return;
        }
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        if (this.getResources().getConfiguration().orientation == 1) {
            this.measureOptionalViews();
            return;
        }
        View view = this.findViewWithTag((Object)"MoveListView");
        FieldView fieldView = (FieldView)this.findViewById(2131296529);
        view.getLayoutParams().width = view.getMeasuredWidth();
        fieldView.getLayoutParams().width = fieldView.getMeasuredWidth();
        fieldView.getLayoutParams().height = fieldView.getMeasuredHeight();
    }
}
