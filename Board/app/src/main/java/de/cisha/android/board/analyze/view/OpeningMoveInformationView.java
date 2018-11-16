// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.analyze.view;

import de.cisha.android.board.analyze.model.OpeningMoveInformation;
import android.widget.LinearLayout.LayoutParams;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.view.ViewGroup;
import android.content.Context;
import android.widget.TableRow;

public class OpeningMoveInformationView extends TableRow
{
    public OpeningMoveInformationView(final Context context) {
        super(context);
        inflate(this.getContext(), 2131427376, (ViewGroup)this);
        this.setOrientation(0);
        this.setGravity(16);
    }
    
    private LinearLayout findBarView(final int n) {
        return (LinearLayout)this.findViewById(n);
    }
    
    private TextView findTextView(final int n) {
        return (TextView)this.findViewById(n);
    }
    
    private ViewGroup.LayoutParams generateLinearLayoutParams(final float weight) {
        final LinearLayout.LayoutParams linearLayout.LayoutParams = new LinearLayout.LayoutParams(0, -1);
        linearLayout.LayoutParams.weight = weight;
        return (ViewGroup.LayoutParams)linearLayout.LayoutParams;
    }
    
    private void setLayoutForBarItem(final int n, final LinearLayout linearLayout, final TextView textView) {
        linearLayout.setLayoutParams(this.generateLinearLayoutParams(n));
        final StringBuilder sb = new StringBuilder();
        sb.append(n);
        sb.append("%");
        textView.setText((CharSequence)sb.toString());
    }
    
    public void setMoveInformation(final OpeningMoveInformation openingMoveInformation) {
        final TextView textView = (TextView)this.findViewById(2131296330);
        final StringBuilder sb = new StringBuilder();
        sb.append(openingMoveInformation.getMove().getMoveNumber());
        sb.append(". ");
        String s;
        if (openingMoveInformation.getMove().getPiece().getColor()) {
            s = "";
        }
        else {
            s = "\u2026 ";
        }
        sb.append(s);
        sb.append(openingMoveInformation.getMove().getFAN());
        sb.append("");
        textView.setText((CharSequence)sb.toString());
        final TextView textView2 = (TextView)this.findViewById(2131296329);
        final StringBuilder sb2 = new StringBuilder();
        sb2.append(openingMoveInformation.getNumberOfGames());
        sb2.append("");
        textView2.setText((CharSequence)sb2.toString());
        final int round = Math.round(openingMoveInformation.getWinWhite() * 100.0f);
        final int round2 = Math.round(openingMoveInformation.getWinBlack() * 100.0f);
        this.setLayoutForBarItem(round, this.findBarView(2131296327), this.findTextView(2131296328));
        this.setLayoutForBarItem(100 - round - round2, this.findBarView(2131296325), this.findTextView(2131296326));
        this.setLayoutForBarItem(round2, this.findBarView(2131296323), this.findTextView(2131296324));
        ((TextView)this.findViewById(2131296324)).setTextColor(-1);
    }
}
