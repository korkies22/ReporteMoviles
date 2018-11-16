// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.analyze;

import android.view.ViewGroup;
import android.util.AttributeSet;
import android.content.Context;
import de.cisha.android.board.view.ToastView;

public class AnalyzeSavedToast extends ToastView
{
    public AnalyzeSavedToast(final Context context) {
        super(context);
        this.initView();
    }
    
    public AnalyzeSavedToast(final Context context, final AttributeSet set) {
        super(context, set);
        this.initView();
    }
    
    private void initView() {
        inflate(this.getContext(), 2131427371, (ViewGroup)this);
    }
}
