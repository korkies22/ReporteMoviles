/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package de.cisha.android.board.playzone.engine.view;

import android.view.View;
import de.cisha.android.board.view.ViewSelectionHelper;

class EngineOfflineOpponentChooserView
implements ViewSelectionHelper.ResourceSelectionAdapter<View> {
    EngineOfflineOpponentChooserView() {
    }

    @Override
    public View getClickableFrom(View view) {
        return view;
    }

    @Override
    public void onResourceSelected(int n) {
        EngineOfflineOpponentChooserView.this._selectedColor = n;
    }

    @Override
    public void selectView(View view, boolean bl) {
        view = view.findViewWithTag((Object)"selection_indicator");
        int n = bl ? 0 : 4;
        view.setVisibility(n);
    }
}
