/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board.view;

import android.view.View;
import de.cisha.android.board.view.ViewSelectionHelper;
import java.util.List;

static final class ViewSelectionHelper
implements View.OnClickListener {
    final /* synthetic */ List val$selectableViews;
    final /* synthetic */ ViewSelectionHelper.ResourceSelectionAdapter val$selectionAdapter;

    ViewSelectionHelper(List list, ViewSelectionHelper.ResourceSelectionAdapter resourceSelectionAdapter) {
        this.val$selectableViews = list;
        this.val$selectionAdapter = resourceSelectionAdapter;
    }

    public void onClick(View view) {
        for (View view2 : this.val$selectableViews) {
            ViewSelectionHelper.ResourceSelectionAdapter resourceSelectionAdapter = this.val$selectionAdapter;
            boolean bl = view2 == view;
            resourceSelectionAdapter.selectView(view2, bl);
        }
        this.val$selectionAdapter.onResourceSelected(view.getId());
    }
}
