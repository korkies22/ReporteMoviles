// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.view;

import android.widget.ImageView;
import android.view.ViewGroup;
import android.util.AttributeSet;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.widget.LinearLayout;

public class RookieLoadingView extends LinearLayout
{
    private AnimationDrawable _loadingAanimationDrawable;
    
    public RookieLoadingView(final Context context) {
        super(context);
        this.init();
    }
    
    public RookieLoadingView(final Context context, final AttributeSet set) {
        super(context, set);
        this.init();
    }
    
    private void init() {
        this.setGravity(17);
        inflate(this.getContext(), 2131427529, (ViewGroup)this);
        this._loadingAanimationDrawable = (AnimationDrawable)((ImageView)this.findViewById(2131296575)).getDrawable();
    }
    
    public void enableLoadingAnimation(final boolean b) {
        if (b) {
            this._loadingAanimationDrawable.start();
            return;
        }
        this._loadingAanimationDrawable.stop();
    }
}
