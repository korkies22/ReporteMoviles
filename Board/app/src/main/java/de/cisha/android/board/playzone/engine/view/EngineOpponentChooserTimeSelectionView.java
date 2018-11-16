// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.playzone.engine.view;

import de.cisha.android.board.playzone.model.TimeControl;
import de.cisha.chess.model.Rating;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.view.View;
import android.view.LayoutInflater;
import android.util.AttributeSet;
import android.content.Context;
import android.widget.CompoundButton;
import de.cisha.android.ui.patterns.input.CustomSeekBar;
import android.widget.TextView;
import android.widget.SeekBar;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class EngineOpponentChooserTimeSelectionView extends LinearLayout
{
    private LinearLayout _dropDownListView;
    private ViewGroup _dropDownTimeSelection;
    private SeekBar _eloBar;
    private TextView _eloView;
    private CustomSeekBar _increment;
    private CustomSeekBar _minutes;
    private EngineOpponentView _opponentView;
    private CompoundButton _playWithTimeCheckbox;
    private boolean _selected;
    
    public EngineOpponentChooserTimeSelectionView(final Context context) {
        super(context);
        this.init();
    }
    
    public EngineOpponentChooserTimeSelectionView(final Context context, final AttributeSet set) {
        super(context, set);
        this.init();
    }
    
    private void init() {
        this.setOrientation(1);
        this._dropDownTimeSelection = (ViewGroup)LayoutInflater.from(this.getContext()).inflate(2131427495, (ViewGroup)null);
        (this._opponentView = new EngineOpponentView(this.getContext())).setName(this.getContext().getString(2131689963));
        this._opponentView.setDescriptionText(this.getContext().getString(2131689962));
        this._opponentView.setOpponentImage(EngineOpponentView.EngineOpponent.CUSTOM);
        (this._dropDownListView = new LinearLayout(this.getContext())).setOrientation(1);
        this._dropDownListView.addView((View)this._opponentView, -1, -2);
        this._dropDownListView.addView((View)this._dropDownTimeSelection, -1, -2);
        this.addView((View)this._dropDownListView, -1, -2);
        this._eloView = (TextView)this._dropDownTimeSelection.findViewById(2131296745);
        (this._eloBar = (SeekBar)this._dropDownTimeSelection.findViewById(2131296744)).setMax(2300);
        this._eloBar.setOnSeekBarChangeListener((SeekBar.OnSeekBarChangeListener)new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(final SeekBar seekBar, final int n, final boolean b) {
                final TextView access.000 = EngineOpponentChooserTimeSelectionView.this._eloView;
                final StringBuilder sb = new StringBuilder();
                sb.append("");
                sb.append(n + 500);
                access.000.setText((CharSequence)sb.toString());
            }
            
            public void onStartTrackingTouch(final SeekBar seekBar) {
            }
            
            public void onStopTrackingTouch(final SeekBar seekBar) {
            }
        });
        this._eloBar.setProgress(1300);
        (this._playWithTimeCheckbox = (CompoundButton)this._dropDownTimeSelection.findViewById(2131296743)).setChecked(true);
        this._playWithTimeCheckbox.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener)new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(final CompoundButton compoundButton, final boolean b) {
                EngineOpponentChooserTimeSelectionView.this._increment.setEnabled(b);
                EngineOpponentChooserTimeSelectionView.this._minutes.setEnabled(b);
            }
        });
        (this._minutes = (CustomSeekBar)this._dropDownTimeSelection.findViewById(2131296785)).setMax(59);
        this._minutes.setProgress(4);
        this.setMinutesText(4);
        this._minutes.setOnSeekBarChangeListener((SeekBar.OnSeekBarChangeListener)new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(final SeekBar seekBar, final int n, final boolean b) {
                EngineOpponentChooserTimeSelectionView.this.setMinutesText(n);
            }
            
            public void onStartTrackingTouch(final SeekBar seekBar) {
            }
            
            public void onStopTrackingTouch(final SeekBar seekBar) {
            }
        });
        (this._increment = (CustomSeekBar)this._dropDownTimeSelection.findViewById(2131296782)).setMax(60);
        this._increment.setProgress(0);
        this.setIncrementText(0);
        this._increment.setOnSeekBarChangeListener((SeekBar.OnSeekBarChangeListener)new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(final SeekBar seekBar, final int n, final boolean b) {
                EngineOpponentChooserTimeSelectionView.this.setIncrementText(n);
            }
            
            public void onStartTrackingTouch(final SeekBar seekBar) {
            }
            
            public void onStopTrackingTouch(final SeekBar seekBar) {
            }
        });
    }
    
    private void setIncrementText(final int n) {
        final TextView textView = (TextView)this.findViewById(2131296783);
        final StringBuilder sb = new StringBuilder();
        sb.append("+");
        sb.append(n);
        sb.append(" Sec");
        textView.setText((CharSequence)sb.toString());
    }
    
    private void setMinutesText(final int n) {
        final TextView textView = (TextView)this.findViewById(2131296786);
        final StringBuilder sb = new StringBuilder();
        sb.append(n + 1);
        sb.append(" Min");
        textView.setText((CharSequence)sb.toString());
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
    
    public void setRating(final int n) {
        this._eloBar.setProgress(n - 500);
    }
    
    public void setSelected(final boolean b) {
        super.setSelected(b);
        if (b != this._selected) {
            final ViewGroup dropDownTimeSelection = this._dropDownTimeSelection;
            int visibility;
            if (b) {
                visibility = 0;
            }
            else {
                visibility = 8;
            }
            dropDownTimeSelection.setVisibility(visibility);
        }
        this._selected = b;
    }
    
    public void setTimeControl(final int n, final int progress) {
        this._increment.setProgress(progress);
        this._minutes.setProgress(n - 1);
    }
    
    public void setTimeControlChecked(final boolean checked) {
        this._playWithTimeCheckbox.setChecked(checked);
    }
}
