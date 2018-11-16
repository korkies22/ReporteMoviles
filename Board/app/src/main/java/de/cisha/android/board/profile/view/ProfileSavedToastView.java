// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.profile.view;

import android.view.ViewGroup;
import android.util.AttributeSet;
import android.content.Context;
import de.cisha.android.board.view.ToastView;

public class ProfileSavedToastView extends ToastView
{
    public ProfileSavedToastView(final Context context) {
        super(context);
        this.init();
    }
    
    public ProfileSavedToastView(final Context context, final AttributeSet set) {
        super(context, set);
        this.init();
    }
    
    private void init() {
        inflate(this.getContext(), 2131427521, (ViewGroup)this);
    }
}
