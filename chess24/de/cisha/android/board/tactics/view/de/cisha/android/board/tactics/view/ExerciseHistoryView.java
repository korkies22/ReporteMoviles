/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.HorizontalScrollView
 *  android.widget.LinearLayout
 */
package de.cisha.android.board.tactics.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import de.cisha.android.board.tactics.view.SolvedIndicatorView;

public class ExerciseHistoryView
extends HorizontalScrollView {
    private LinearLayout _linearLayout;
    private LinearLayout _placeHolderLayout;
    private boolean _removeInvisibles = false;
    private View _selectedView;
    private HistorySelectionListener _selectionListener;

    public ExerciseHistoryView(Context context) {
        super(context);
        this.init();
    }

    public ExerciseHistoryView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init();
    }

    private void init() {
        this._linearLayout = new LinearLayout(this.getContext());
        this._linearLayout.setOrientation(0);
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

    private void selectView(View view) {
        this._selectedView = view;
        for (int i = 0; i < this._linearLayout.getChildCount(); ++i) {
            View view2 = this._linearLayout.getChildAt(i);
            if (!(view2 instanceof SolvedIndicatorView)) continue;
            boolean bl = view == view2;
            view2.setSelected(bl);
        }
    }

    public void addSolvedExcercise(SolvedIndicatorView.SolveType solveType) {
        Object object;
        if (this._removeInvisibles) {
            for (int i = 0; i < this._linearLayout.getChildCount(); ++i) {
                object = this._linearLayout.getChildAt(i);
                if (!(object instanceof SolvedIndicatorView)) continue;
                this._linearLayout.removeView(object);
            }
            this._removeInvisibles = false;
        }
        SolvedIndicatorView solvedIndicatorView = (SolvedIndicatorView)LayoutInflater.from((Context)this.getContext()).inflate(2131427558, (ViewGroup)this._linearLayout, false);
        object = solveType;
        if (this._linearLayout.getChildCount() > 1) {
            SolvedIndicatorView solvedIndicatorView2 = (SolvedIndicatorView)this._linearLayout.getChildAt(this._linearLayout.getChildCount() - 2);
            object = solveType;
            if (solvedIndicatorView2.getSolveType() == SolvedIndicatorView.SolveType.RUNNING) {
                solvedIndicatorView2.setSolveType(solveType);
                object = SolvedIndicatorView.SolveType.RUNNING;
            }
        }
        solvedIndicatorView.setSolveType((SolvedIndicatorView.SolveType)((Object)object));
        if (object != SolvedIndicatorView.SolveType.RUNNING) {
            solvedIndicatorView.setOnClickListener(new View.OnClickListener(this._linearLayout.getChildCount() - 1){
                final /* synthetic */ int val$number;
                {
                    this.val$number = n;
                }

                public void onClick(View view) {
                    if (ExerciseHistoryView.this._selectionListener != null) {
                        ExerciseHistoryView.this._selectionListener.onHistoryNumberClicked(this.val$number);
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

    public void selectElementNumber(int n) {
        if (this._linearLayout.getChildCount() > n) {
            this.selectView(this._linearLayout.getChildAt(n));
        }
    }

    public void setHistorySelectionListener(HistorySelectionListener historySelectionListener) {
        this._selectionListener = historySelectionListener;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this._linearLayout.setOnClickListener(onClickListener);
    }

    public void setPlaceHolderWidth(int n) {
        this._placeHolderLayout.getLayoutParams().width = n;
    }

    public static interface HistorySelectionListener {
        public void onHistoryNumberClicked(int var1);
    }

}
