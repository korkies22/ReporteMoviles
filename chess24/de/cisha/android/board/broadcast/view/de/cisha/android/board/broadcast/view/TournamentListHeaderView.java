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
package de.cisha.android.board.broadcast.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TournamentListHeaderView
extends LinearLayout {
    private TextView _text;

    public TournamentListHeaderView(Context context) {
        super(context);
        this.init();
    }

    public TournamentListHeaderView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init();
    }

    private void init() {
        this.setOrientation(0);
        this.setGravity(1);
        this.setBackgroundResource(2131230990);
        TournamentListHeaderView.inflate((Context)this.getContext(), (int)2131427390, (ViewGroup)this);
        this._text = (TextView)this.findViewById(2131296393);
    }

    public void setTitle(CharSequence charSequence) {
        this._text.setText(charSequence);
    }
}
