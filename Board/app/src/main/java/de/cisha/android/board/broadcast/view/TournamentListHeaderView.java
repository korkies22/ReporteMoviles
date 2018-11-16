// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.view;

import android.view.ViewGroup;
import android.util.AttributeSet;
import android.content.Context;
import android.widget.TextView;
import android.widget.LinearLayout;

public class TournamentListHeaderView extends LinearLayout
{
    private TextView _text;
    
    public TournamentListHeaderView(final Context context) {
        super(context);
        this.init();
    }
    
    public TournamentListHeaderView(final Context context, final AttributeSet set) {
        super(context, set);
        this.init();
    }
    
    private void init() {
        this.setOrientation(0);
        this.setGravity(1);
        this.setBackgroundResource(2131230990);
        inflate(this.getContext(), 2131427390, (ViewGroup)this);
        this._text = (TextView)this.findViewById(2131296393);
    }
    
    public void setTitle(final CharSequence text) {
        this._text.setText(text);
    }
}
