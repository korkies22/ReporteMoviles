/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.ui.list;

import de.cisha.android.ui.list.RefreshingListHeaderView;

static class RefreshingListHeaderView {
    static final /* synthetic */ int[] $SwitchMap$de$cisha$android$ui$list$RefreshingListHeaderView$State;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static {
        $SwitchMap$de$cisha$android$ui$list$RefreshingListHeaderView$State = new int[RefreshingListHeaderView.State.values().length];
        try {
            RefreshingListHeaderView.$SwitchMap$de$cisha$android$ui$list$RefreshingListHeaderView$State[RefreshingListHeaderView.State.PULLDOWN.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            RefreshingListHeaderView.$SwitchMap$de$cisha$android$ui$list$RefreshingListHeaderView$State[RefreshingListHeaderView.State.FINGER_UP.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            RefreshingListHeaderView.$SwitchMap$de$cisha$android$ui$list$RefreshingListHeaderView$State[RefreshingListHeaderView.State.REFRESHING.ordinal()] = 3;
            return;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            return;
        }
    }
}
