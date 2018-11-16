// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.playzone.remote.view;

import android.content.res.Resources;
import android.util.AttributeSet;
import android.content.Context;

public class DisconnectedOpponentView extends DisconnectedDeviceView
{
    public DisconnectedOpponentView(final Context context) {
        super(context);
    }
    
    public DisconnectedOpponentView(final Context context, final AttributeSet set) {
        super(context, set);
    }
    
    @Override
    protected String getMessageStringForSeconds(final int n, final boolean b) {
        if (!b) {
            final Resources resources = this.getContext().getResources();
            final StringBuilder sb = new StringBuilder();
            sb.append("");
            sb.append(n);
            return resources.getString(2131690167, new Object[] { sb.toString() });
        }
        final Resources resources2 = this.getContext().getResources();
        final StringBuilder sb2 = new StringBuilder();
        sb2.append("");
        sb2.append(n);
        return resources2.getString(2131690166, new Object[] { sb2.toString() });
    }
}
