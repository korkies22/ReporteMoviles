/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.util.AttributeSet
 */
package de.cisha.android.board.playzone.remote.view;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import de.cisha.android.board.playzone.remote.view.DisconnectedDeviceView;

public class DisconnectedOpponentView
extends DisconnectedDeviceView {
    public DisconnectedOpponentView(Context context) {
        super(context);
    }

    public DisconnectedOpponentView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    protected String getMessageStringForSeconds(int n, boolean bl) {
        if (!bl) {
            Resources resources = this.getContext().getResources();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("");
            stringBuilder.append(n);
            return resources.getString(2131690167, new Object[]{stringBuilder.toString()});
        }
        Resources resources = this.getContext().getResources();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(n);
        return resources.getString(2131690166, new Object[]{stringBuilder.toString()});
    }
}
