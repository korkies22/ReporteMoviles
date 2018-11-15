/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone;

import de.cisha.android.board.playzone.AbstractPlayzoneFragment;

static class AbstractPlayzoneFragment {
    static final /* synthetic */ int[] $SwitchMap$de$cisha$android$board$playzone$AbstractPlayzoneFragment$PlayzoneState;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static {
        $SwitchMap$de$cisha$android$board$playzone$AbstractPlayzoneFragment$PlayzoneState = new int[AbstractPlayzoneFragment.PlayzoneState.values().length];
        try {
            AbstractPlayzoneFragment.$SwitchMap$de$cisha$android$board$playzone$AbstractPlayzoneFragment$PlayzoneState[AbstractPlayzoneFragment.PlayzoneState.AFTER.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            AbstractPlayzoneFragment.$SwitchMap$de$cisha$android$board$playzone$AbstractPlayzoneFragment$PlayzoneState[AbstractPlayzoneFragment.PlayzoneState.CHOOSING_BEFORE.ordinal()] = 2;
            return;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            return;
        }
    }
}
