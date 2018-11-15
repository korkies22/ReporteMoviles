/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.CompoundButton
 *  android.widget.CompoundButton$OnCheckedChangeListener
 *  android.widget.LinearLayout
 *  android.widget.SeekBar
 *  android.widget.SeekBar$OnSeekBarChangeListener
 *  android.widget.TextView
 */
package de.cisha.android.board.playzone.engine.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import de.cisha.android.board.playzone.engine.view.EngineOpponentView;
import de.cisha.android.board.playzone.model.TimeControl;
import de.cisha.android.ui.patterns.input.CustomSeekBar;
import de.cisha.chess.model.Rating;

public class EngineOpponentChooserTimeSelectionView
extends LinearLayout {
    private LinearLayout _dropDownListView;
    private ViewGroup _dropDownTimeSelection;
    private SeekBar _eloBar;
    private TextView _eloView;
    private CustomSeekBar _increment;
    private CustomSeekBar _minutes;
    private EngineOpponentView _opponentView;
    private CompoundButton _playWithTimeCheckbox;
    private boolean _selected;

    public EngineOpponentChooserTimeSelectionView(Context context) {
        super(context);
        this.init();
    }

    public EngineOpponentChooserTimeSelectionView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init();
    }

    private void init() {
        this.setOrientation(1);
        this._dropDownTimeSelection = (ViewGroup)LayoutInflater.from((Context)this.getContext()).inflate(2131427495, null);
        this._opponentView = new EngineOpponentView(this.getContext());
        this._opponentView.setName(this.getContext().getString(2131689963));
        this._opponentView.setDescriptionText(this.getContext().getString(2131689962));
        this._opponentView.setOpponentImage(EngineOpponentView.EngineOpponent.CUSTOM);
        this._dropDownListView = new LinearLayout(this.getContext());
        this._dropDownListView.setOrientation(1);
        this._dropDownListView.addView((View)this._opponentView, -1, -2);
        this._dropDownListView.addView((View)this._dropDownTimeSelection, -1, -2);
        this.addView((View)this._dropDownListView, -1, -2);
        this._eloView = (TextView)this._dropDownTimeSelection.findViewById(2131296745);
        this._eloBar = (SeekBar)this._dropDownTimeSelection.findViewById(2131296744);
        this._eloBar.setMax(2300);
        this._eloBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            public void onProgressChanged(SeekBar seekBar, int n, boolean bl) {
                seekBar = EngineOpponentChooserTimeSelectionView.this._eloView;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("");
                stringBuilder.append(n + 500);
                seekBar.setText((CharSequence)stringBuilder.toString());
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        this._eloBar.setProgress(1300);
        this._playWithTimeCheckbox = (CompoundButton)this._dropDownTimeSelection.findViewById(2131296743);
        this._playWithTimeCheckbox.setChecked(true);
        this._playWithTimeCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                EngineOpponentChooserTimeSelectionView.this._increment.setEnabled(bl);
                EngineOpponentChooserTimeSelectionView.this._minutes.setEnabled(bl);
            }
        });
        this._minutes = (CustomSeekBar)this._dropDownTimeSelection.findViewById(2131296785);
        this._minutes.setMax(59);
        this._minutes.setProgress(4);
        this.setMinutesText(4);
        this._minutes.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            public void onProgressChanged(SeekBar seekBar, int n, boolean bl) {
                EngineOpponentChooserTimeSelectionView.this.setMinutesText(n);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        this._increment = (CustomSeekBar)this._dropDownTimeSelection.findViewById(2131296782);
        this._increment.setMax(60);
        this._increment.setProgress(0);
        this.setIncrementText(0);
        this._increment.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            public void onProgressChanged(SeekBar seekBar, int n, boolean bl) {
                EngineOpponentChooserTimeSelectionView.this.setIncrementText(n);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    private void setIncrementText(int n) {
        TextView textView = (TextView)this.findViewById(2131296783);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("+");
        stringBuilder.append(n);
        stringBuilder.append(" Sec");
        textView.setText((CharSequence)stringBuilder.toString());
    }

    private void setMinutesText(int n) {
        TextView textView = (TextView)this.findViewById(2131296786);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(n + 1);
        stringBuilder.append(" Min");
        textView.setText((CharSequence)stringBuilder.toString());
    }

    public Rating getSelectedElo() {
        return new Rating(this._eloBar.getProgress() + 500);
    }

    public TimeControl getTimeControlChosen() {
        if (this._playWithTimeCheckbox.isChecked()) {
            return new TimeControl(this._minutes.getProgress() + 1, this._increment.getProgress());
        }
        return TimeControl.NO_TIME;
    }

    public EngineOpponentView getViewEngineOpponentView() {
        return this._opponentView;
    }

    public void setRating(int n) {
        this._eloBar.setProgress(n - 500);
    }

    public void setSelected(boolean bl) {
        super.setSelected(bl);
        if (bl != this._selected) {
            ViewGroup viewGroup = this._dropDownTimeSelection;
            int n = bl ? 0 : 8;
            viewGroup.setVisibility(n);
        }
        this._selected = bl;
    }

    public void setTimeControl(int n, int n2) {
        this._increment.setProgress(n2);
        this._minutes.setProgress(n - 1);
    }

    public void setTimeControlChecked(boolean bl) {
        this._playWithTimeCheckbox.setChecked(bl);
    }

}
