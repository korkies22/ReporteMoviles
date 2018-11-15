/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.util.AttributeSet
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.LinearLayout
 *  android.widget.TextView
 */
package de.cisha.android.board.video.view;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import de.cisha.android.board.video.model.EloRangeRepresentation;

public class VideoAuthorAndEloRangeView
extends LinearLayout {
    private TextView _authorTextView;
    private TextView _eloTextView;

    public VideoAuthorAndEloRangeView(Context context) {
        super(context);
        this.initialize(context);
    }

    public VideoAuthorAndEloRangeView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.initialize(context);
    }

    private void initialize(Context context) {
        this.setOrientation(0);
        this.setGravity(16);
        ((LayoutInflater)context.getSystemService("layout_inflater")).inflate(2131427571, (ViewGroup)this, true);
        this.setBackgroundColor(context.getResources().getColor(2131099702));
        this._authorTextView = (TextView)this.findViewById(2131297251);
        this._eloTextView = (TextView)this.findViewById(2131297253);
    }

    private void setEloText(String string) {
        this._eloTextView.setText((CharSequence)string);
    }

    public void setAuthor(String string) {
        this._authorTextView.setText((CharSequence)string);
    }

    public void setEloRange(EloRangeRepresentation eloRangeRepresentation) {
        this.setEloText(eloRangeRepresentation.getRangeString(this.getResources()));
    }
}
