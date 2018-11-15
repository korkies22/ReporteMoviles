/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.ArrayAdapter
 *  android.widget.TextView
 */
package de.cisha.android.board.analyze;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import de.cisha.chess.model.GameResult;

class EditGameInformationFragment
extends ArrayAdapter<GameResult> {
    EditGameInformationFragment(Context context, int n, GameResult[] arrgameResult) {
        super(context, n, (Object[])arrgameResult);
    }

    public View getDropDownView(int n, View view, ViewGroup viewGroup) {
        view = (TextView)super.getDropDownView(n, view, viewGroup);
        view.setText((CharSequence)((GameResult)((Object)this.getItem(n))).getShortDescription());
        return view;
    }

    public View getView(int n, View view, ViewGroup viewGroup) {
        view = (TextView)super.getView(n, view, viewGroup);
        view.setText((CharSequence)((GameResult)((Object)this.getItem(n))).getShortDescription());
        return view;
    }
}
