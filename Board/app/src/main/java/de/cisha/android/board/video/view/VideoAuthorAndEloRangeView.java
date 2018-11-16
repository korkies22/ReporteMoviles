// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.video.view;

import de.cisha.android.board.video.model.EloRangeRepresentation;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.util.AttributeSet;
import android.content.Context;
import android.widget.TextView;
import android.widget.LinearLayout;

public class VideoAuthorAndEloRangeView extends LinearLayout
{
    private TextView _authorTextView;
    private TextView _eloTextView;
    
    public VideoAuthorAndEloRangeView(final Context context) {
        super(context);
        this.initialize(context);
    }
    
    public VideoAuthorAndEloRangeView(final Context context, final AttributeSet set) {
        super(context, set);
        this.initialize(context);
    }
    
    private void initialize(final Context context) {
        this.setOrientation(0);
        this.setGravity(16);
        ((LayoutInflater)context.getSystemService("layout_inflater")).inflate(2131427571, (ViewGroup)this, true);
        this.setBackgroundColor(context.getResources().getColor(2131099702));
        this._authorTextView = (TextView)this.findViewById(2131297251);
        this._eloTextView = (TextView)this.findViewById(2131297253);
    }
    
    private void setEloText(final String text) {
        this._eloTextView.setText((CharSequence)text);
    }
    
    public void setAuthor(final String text) {
        this._authorTextView.setText((CharSequence)text);
    }
    
    public void setEloRange(final EloRangeRepresentation eloRangeRepresentation) {
        this.setEloText(eloRangeRepresentation.getRangeString(this.getResources()));
    }
}
