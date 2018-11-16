// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.tactics.view;

import android.view.ViewGroup;
import android.util.AttributeSet;
import android.content.Context;
import android.widget.TextView;
import android.widget.ProgressBar;
import android.widget.ImageView;
import de.cisha.android.board.view.ToastView;

public class SolvedToastView extends ToastView
{
    private ImageView _image;
    private ProgressBar _progress;
    private boolean _solved;
    private TextView _text;
    
    public SolvedToastView(final Context context) {
        super(context);
        this.init();
    }
    
    public SolvedToastView(final Context context, final AttributeSet set) {
        super(context, set);
        this.init();
    }
    
    private void init() {
        inflate(this.getContext(), 2131427566, (ViewGroup)this);
        this._image = (ImageView)this.findViewById(2131297122);
        this._text = (TextView)this.findViewById(2131297124);
        (this._progress = (ProgressBar)this.findViewById(2131297123)).setVisibility(4);
    }
    
    public boolean isSolved() {
        return this._solved;
    }
    
    public void setSolved(final boolean solved) {
        this._solved = solved;
        final ImageView image = this._image;
        int imageResource;
        if (solved) {
            imageResource = 2131231831;
        }
        else {
            imageResource = 2131231832;
        }
        image.setImageResource(imageResource);
    }
    
    public void setText(final CharSequence text) {
        this._text.setText(text);
    }
    
    public void showLoadingAnimation(final boolean b) {
        if (b) {
            this._progress.setVisibility(0);
            return;
        }
        this._progress.setVisibility(4);
    }
}
