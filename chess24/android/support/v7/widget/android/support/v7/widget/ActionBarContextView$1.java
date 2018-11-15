/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package android.support.v7.widget;

import android.support.v7.view.ActionMode;
import android.view.View;

class ActionBarContextView
implements View.OnClickListener {
    final /* synthetic */ ActionMode val$mode;

    ActionBarContextView(ActionMode actionMode) {
        this.val$mode = actionMode;
    }

    public void onClick(View view) {
        this.val$mode.finish();
    }
}
