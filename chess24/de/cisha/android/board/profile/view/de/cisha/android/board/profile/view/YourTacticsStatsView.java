/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.LinearLayout
 *  android.widget.TextView
 */
package de.cisha.android.board.profile.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import de.cisha.android.board.profile.model.TacticStatisticData;
import de.cisha.android.board.view.FieldView;
import de.cisha.android.ui.patterns.buttons.CustomButton;
import de.cisha.chess.model.Rating;

public class YourTacticsStatsView
extends LinearLayout {
    private TextView _attempsView;
    private TextView _ratingView;
    private TextView _successView;
    private TextView _tempoView;

    public YourTacticsStatsView(Context context) {
        super(context);
        this.init();
    }

    public YourTacticsStatsView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init();
    }

    private void init() {
        this.setOrientation(1);
        YourTacticsStatsView.inflate((Context)this.getContext(), (int)2131427524, (ViewGroup)this);
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

    public void setTacticStatisticData(TacticStatisticData tacticStatisticData) {
        if (tacticStatisticData != null) {
            this._ratingView.setText((CharSequence)tacticStatisticData.getRatingClassic().getRatingString());
            TextView textView = this._attempsView;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(tacticStatisticData.getAttemptsClassic());
            stringBuilder.append(" (");
            stringBuilder.append(tacticStatisticData.getAttemptsCorrectClassic());
            stringBuilder.append(")");
            textView.setText((CharSequence)stringBuilder.toString());
            textView = this._successView;
            stringBuilder = new StringBuilder();
            stringBuilder.append(String.format("%.1f", Float.valueOf(100.0f * tacticStatisticData.getRatioClassic())));
            stringBuilder.append(" %");
            textView.setText((CharSequence)stringBuilder.toString());
            textView = this._tempoView;
            stringBuilder = new StringBuilder();
            stringBuilder.append(tacticStatisticData.getSecondsPerExerciseClassic());
            stringBuilder.append(" s/pos");
            textView.setText((CharSequence)stringBuilder.toString());
        }
    }
}
