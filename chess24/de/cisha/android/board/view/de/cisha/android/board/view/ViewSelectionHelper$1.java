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
    final /* synthetic */ View val$parentView;
    final /* synthetic */ List val$selectableViews;
    final /* synthetic */ ViewSelectionHelper.ResourceSelectionAdapter val$selectionAdapter;

    ViewSelectionHelper(List list, ViewSelectionHelper.ResourceSelectionAdapter resourceSelectionAdapter, View view) {
        this.val$selectableViews = list;
        this.val$selectionAdapter = resourceSelectionAdapter;
        this.val$parentView = view;
    }

    public void onClick(View view) {
        for (Integer n : this.val$selectableViews) {
            ViewSelectionHelper.ResourceSelectionAdapter resourceSelectionAdapter = this.val$selectionAdapter;
            View view2 = this.val$parentView.findViewById(n.intValue());
            boolean bl = n.intValue() == view.getId();
            resourceSelectionAdapter.selectView(view2, bl);
        }
        this.val$selectionAdapter.onResourceSelected(view.getId());
    }
}
