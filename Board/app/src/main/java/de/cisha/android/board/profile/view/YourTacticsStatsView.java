// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.profile.view;

import de.cisha.android.board.profile.model.TacticStatisticData;
import de.cisha.android.ui.patterns.buttons.CustomButton;
import de.cisha.android.board.view.FieldView;
import android.view.ViewGroup;
import android.util.AttributeSet;
import android.content.Context;
import android.widget.TextView;
import android.widget.LinearLayout;

public class YourTacticsStatsView extends LinearLayout
{
    private TextView _attempsView;
    private TextView _ratingView;
    private TextView _successView;
    private TextView _tempoView;
    
    public YourTacticsStatsView(final Context context) {
        super(context);
        this.init();
    }
    
    public YourTacticsStatsView(final Context context, final AttributeSet set) {
        super(context, set);
        this.init();
    }
    
    private void init() {
        this.setOrientation(1);
        inflate(this.getContext(), 2131427524, (ViewGroup)this);
        this._ratingView = (TextView)this.findViewById(2131296873);
        this._attempsView = (TextView)this.findViewById(2131296869);
        this._successView = (TextView)this.findViewById(2131296875);
        this._tempoView = (TextView)this.findViewById(2131296877);
    }
    
    public FieldView getFieldView() {
        return (FieldView)this.findViewById(2131296870);
    }
    
    public CustomButton getGotoTacticsButton() {
        return (CustomButton)this.findViewById(2131296871);
    }
    
    public void setTacticStatisticData(final TacticStatisticData tacticStatisticData) {
        if (tacticStatisticData != null) {
            this._ratingView.setText((CharSequence)tacticStatisticData.getRatingClassic().getRatingString());
            final TextView attempsView = this._attempsView;
            final StringBuilder sb = new StringBuilder();
            sb.append(tacticStatisticData.getAttemptsClassic());
            sb.append(" (");
            sb.append(tacticStatisticData.getAttemptsCorrectClassic());
            sb.append(")");
            attempsView.setText((CharSequence)sb.toString());
            final TextView successView = this._successView;
            final StringBuilder sb2 = new StringBuilder();
            sb2.append(String.format("%.1f", 100.0f * tacticStatisticData.getRatioClassic()));
            sb2.append(" %");
            successView.setText((CharSequence)sb2.toString());
            final TextView tempoView = this._tempoView;
            final StringBuilder sb3 = new StringBuilder();
            sb3.append(tacticStatisticData.getSecondsPerExerciseClassic());
            sb3.append(" s/pos");
            tempoView.setText((CharSequence)sb3.toString());
        }
    }
}
