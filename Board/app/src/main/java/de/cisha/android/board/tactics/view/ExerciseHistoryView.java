// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.tactics.view;

import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.util.AttributeSet;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.HorizontalScrollView;

public class ExerciseHistoryView extends HorizontalScrollView
{
    private LinearLayout _linearLayout;
    private LinearLayout _placeHolderLayout;
    private boolean _removeInvisibles;
    private View _selectedView;
    private HistorySelectionListener _selectionListener;
    
    public ExerciseHistoryView(final Context context) {
        super(context);
        this._removeInvisibles = false;
        this.init();
    }
    
    public ExerciseHistoryView(final Context context, final AttributeSet set) {
        super(context, set);
        this._removeInvisibles = false;
        this.init();
    }
    
    private void init() {
        (this._linearLayout = new LinearLayout(this.getContext())).setOrientation(0);
        this.addView((View)this._linearLayout, -2, -2);
        this.addSolvedExcercise(SolvedIndicatorView.SolveType.INVISIBLE);
        if (this.isInEditMode()) {
            this.addSolvedExcercise(SolvedIndicatorView.SolveType.RUNNING);
            this.addSolvedExcercise(SolvedIndicatorView.SolveType.INCORRECT);
            this.addSolvedExcercise(SolvedIndicatorView.SolveType.INCORRECT);
            this.addSolvedExcercise(SolvedIndicatorView.SolveType.CORRECT);
            this.addSolvedExcercise(SolvedIndicatorView.SolveType.INCORRECT);
            return;
        }
        this._removeInvisibles = true;
        this._placeHolderLayout = new LinearLayout(this.getContext());
        this._linearLayout.addView((View)this._placeHolderLayout, 0, 0);
    }
    
    private void selectView(final View selectedView) {
        this._selectedView = selectedView;
        for (int i = 0; i < this._linearLayout.getChildCount(); ++i) {
            final View child = this._linearLayout.getChildAt(i);
            if (child instanceof SolvedIndicatorView) {
                child.setSelected(selectedView == child);
            }
        }
    }
    
    public void addSolvedExcercise(final SolvedIndicatorView.SolveType solveType) {
        if (this._removeInvisibles) {
            for (int i = 0; i < this._linearLayout.getChildCount(); ++i) {
                final View child = this._linearLayout.getChildAt(i);
                if (child instanceof SolvedIndicatorView) {
                    this._linearLayout.removeView(child);
                }
            }
            this._removeInvisibles = false;
        }
        final SolvedIndicatorView solvedIndicatorView = (SolvedIndicatorView)LayoutInflater.from(this.getContext()).inflate(2131427558, (ViewGroup)this._linearLayout, false);
        Enum<SolvedIndicatorView.SolveType> running = solveType;
        if (this._linearLayout.getChildCount() > 1) {
            final SolvedIndicatorView solvedIndicatorView2 = (SolvedIndicatorView)this._linearLayout.getChildAt(this._linearLayout.getChildCount() - 2);
            running = solveType;
            if (solvedIndicatorView2.getSolveType() == SolvedIndicatorView.SolveType.RUNNING) {
                solvedIndicatorView2.setSolveType(solveType);
                running = SolvedIndicatorView.SolveType.RUNNING;
            }
        }
        solvedIndicatorView.setSolveType((SolvedIndicatorView.SolveType)running);
        if (running != SolvedIndicatorView.SolveType.RUNNING) {
            solvedIndicatorView.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
                final /* synthetic */ int val.number = ExerciseHistoryView.this._linearLayout.getChildCount() - 1;
                
                public void onClick(final View view) {
                    if (ExerciseHistoryView.this._selectionListener != null) {
                        ExerciseHistoryView.this._selectionListener.onHistoryNumberClicked(this.val.number);
                    }
                    ExerciseHistoryView.this.selectView(view);
                }
            });
        }
        this._linearLayout.addView((View)solvedIndicatorView, this._linearLayout.getChildCount() - 1);
    }
    
    public void reset() {
        this.removeView((View)this._linearLayout);
        this.init();
    }
    
    public void scrollToSelectedExercise() {
        if (this._selectedView != null) {
            this.scrollTo(this._selectedView.getLeft() - 10, 0);
        }
    }
    
    public void selectElementNumber(final int n) {
        if (this._linearLayout.getChildCount() > n) {
            this.selectView(this._linearLayout.getChildAt(n));
        }
    }
    
    public void setHistorySelectionListener(final HistorySelectionListener selectionListener) {
        this._selectionListener = selectionListener;
    }
    
    public void setOnClickListener(final View.OnClickListener onClickListener) {
        this._linearLayout.setOnClickListener(onClickListener);
    }
    
    public void setPlaceHolderWidth(final int width) {
        this._placeHolderLayout.getLayoutParams().width = width;
    }
    
    public interface HistorySelectionListener
    {
        void onHistoryNumberClicked(final int p0);
    }
}
