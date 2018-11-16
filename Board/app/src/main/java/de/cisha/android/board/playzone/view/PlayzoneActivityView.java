// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.playzone.view;

import de.cisha.android.board.view.FieldView;
import android.view.View;
import android.view.View.MeasureSpec;
import de.cisha.android.board.view.TakenPiecesView;
import de.cisha.android.board.IContentPresenter;
import android.util.AttributeSet;
import android.content.Context;
import android.widget.RelativeLayout;

public class PlayzoneActivityView extends RelativeLayout
{
    public PlayzoneActivityView(final Context context) {
        super(context);
    }
    
    public PlayzoneActivityView(final Context context, final AttributeSet set) {
        super(context, set);
    }
    
    private void measureOptionalViews() {
        synchronized (this) {
            final IContentPresenter contentPresenter = (IContentPresenter)this.getContext();
            final int contentMaxHeight = contentPresenter.getContentMaxHeight();
            final int contentMaxWidth = contentPresenter.getContentMaxWidth();
            final ChessClockView chessClockView = (ChessClockView)this.findViewById(2131296712);
            final TakenPiecesView takenPiecesView = (TakenPiecesView)this.findViewById(2131296777);
            final TakenPiecesView takenPiecesView2 = (TakenPiecesView)this.findViewById(2131296778);
            final int measureSpec = View.MeasureSpec.makeMeasureSpec(contentMaxWidth, Integer.MIN_VALUE);
            final int measureSpec2 = View.MeasureSpec.makeMeasureSpec(0, 0);
            chessClockView.measure(measureSpec, measureSpec2);
            final View viewById = this.findViewById(2131296747);
            viewById.measure(measureSpec, measureSpec2);
            int n = contentMaxHeight - contentPresenter.getContentMaxWidth() - chessClockView.getMeasuredHeight() - viewById.getMeasuredHeight();
            final View viewById2 = this.findViewById(2131296776);
            viewById2.measure(measureSpec, measureSpec2);
            final View viewById3 = this.findViewById(2131296749);
            viewById3.measure(measureSpec, measureSpec2);
            final View viewById4 = this.findViewById(2131296764);
            viewById4.measure(measureSpec, measureSpec2);
            if (viewById3.getMeasuredHeight() > n) {
                viewById3.setVisibility(8);
                viewById4.setVisibility(8);
            }
            else {
                viewById3.setVisibility(0);
                viewById4.setVisibility(0);
                n -= viewById3.getMeasuredHeight() + viewById4.getMeasuredHeight();
            }
            if (viewById2.getMeasuredHeight() > n) {
                takenPiecesView.setVisibility(8);
                takenPiecesView2.setVisibility(8);
            }
            else {
                takenPiecesView.setVisibility(0);
                takenPiecesView2.setVisibility(0);
                takenPiecesView.getMeasuredHeight();
            }
            this.requestLayout();
        }
    }
    
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (this.getResources().getConfiguration().orientation == 1) {
            this.measureOptionalViews();
            return;
        }
        final View viewWithTag = this.findViewWithTag((Object)"MoveListView");
        final FieldView fieldView = (FieldView)this.findViewById(2131296529);
        viewWithTag.getLayoutParams().width = viewWithTag.getMeasuredWidth();
        fieldView.getLayoutParams().width = fieldView.getMeasuredWidth();
        fieldView.getLayoutParams().height = fieldView.getMeasuredHeight();
    }
}
