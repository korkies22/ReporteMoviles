// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.profile.view;

import android.view.ViewGroup;
import android.util.AttributeSet;
import android.content.Context;
import android.widget.LinearLayout;

public class RegisterWidgetView extends LinearLayout
{
    public RegisterWidgetView(final Context context) {
        super(context);
        this.initView();
    }
    
    public RegisterWidgetView(final Context context, final AttributeSet set) {
        super(context, set);
        this.initView();
    }
    
    private void initView() {
        inflate(this.getContext(), 2131427520, (ViewGroup)this);
    }
}
