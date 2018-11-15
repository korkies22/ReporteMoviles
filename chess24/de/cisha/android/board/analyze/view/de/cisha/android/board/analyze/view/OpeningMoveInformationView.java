/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.LinearLayout
 *  android.widget.LinearLayout$LayoutParams
 *  android.widget.TableRow
 *  android.widget.TextView
 */
package de.cisha.android.board.analyze.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import de.cisha.android.board.analyze.model.OpeningMoveInformation;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.Piece;

public class OpeningMoveInformationView
extends TableRow {
    public OpeningMoveInformationView(Context context) {
        super(context);
        OpeningMoveInformationView.inflate((Context)this.getContext(), (int)2131427376, (ViewGroup)this);
        this.setOrientation(0);
        this.setGravity(16);
    }

    private LinearLayout findBarView(int n) {
        return (LinearLayout)this.findViewById(n);
    }

    private TextView findTextView(int n) {
        return (TextView)this.findViewById(n);
    }

    private ViewGroup.LayoutParams generateLinearLayoutParams(float f) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, -1);
        layoutParams.weight = f;
        return layoutParams;
    }

    private void setLayoutForBarItem(int n, LinearLayout object, TextView textView) {
        object.setLayoutParams(this.generateLinearLayoutParams(n));
        object = new StringBuilder();
        object.append(n);
        object.append("%");
        textView.setText((CharSequence)object.toString());
    }

    public void setMoveInformation(OpeningMoveInformation openingMoveInformation) {
        Object object = (TextView)this.findViewById(2131296330);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(openingMoveInformation.getMove().getMoveNumber());
        stringBuilder.append(". ");
        String string = openingMoveInformation.getMove().getPiece().getColor() ? "" : "\u2026 ";
        stringBuilder.append(string);
        stringBuilder.append(openingMoveInformation.getMove().getFAN());
        stringBuilder.append("");
        object.setText((CharSequence)stringBuilder.toString());
        string = (TextView)this.findViewById(2131296329);
        object = new StringBuilder();
        object.append(openingMoveInformation.getNumberOfGames());
        object.append("");
        string.setText((CharSequence)object.toString());
        int n = Math.round(openingMoveInformation.getWinWhite() * 100.0f);
        int n2 = Math.round(openingMoveInformation.getWinBlack() * 100.0f);
        this.setLayoutForBarItem(n, this.findBarView(2131296327), this.findTextView(2131296328));
        this.setLayoutForBarItem(100 - n - n2, this.findBarView(2131296325), this.findTextView(2131296326));
        this.setLayoutForBarItem(n2, this.findBarView(2131296323), this.findTextView(2131296324));
        ((TextView)this.findViewById(2131296324)).setTextColor(-1);
    }
}
