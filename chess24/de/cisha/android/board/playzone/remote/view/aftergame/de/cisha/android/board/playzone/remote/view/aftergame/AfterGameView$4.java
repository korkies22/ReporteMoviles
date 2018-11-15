/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.remote.view.aftergame;

import de.cisha.android.board.playzone.view.AbstractAfterGameView;

static class AfterGameView {
    static final /* synthetic */ int[] $SwitchMap$de$cisha$android$board$playzone$view$AbstractAfterGameView$AfterGameCategory;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static {
        $SwitchMap$de$cisha$android$board$playzone$view$AbstractAfterGameView$AfterGameCategory = new int[AbstractAfterGameView.AfterGameCategory.values().length];
        try {
            AfterGameView.$SwitchMap$de$cisha$android$board$playzone$view$AbstractAfterGameView$AfterGameCategory[AbstractAfterGameView.AfterGameCategory.ANALYZE.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            AfterGameView.$SwitchMap$de$cisha$android$board$playzone$view$AbstractAfterGameView$AfterGameCategory[AbstractAfterGameView.AfterGameCategory.NEW_GAME.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            AfterGameView.$SwitchMap$de$cisha$android$board$playzone$view$AbstractAfterGameView$AfterGameCategory[AbstractAfterGameView.AfterGameCategory.REMATCH.ordinal()] = 3;
            return;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            return;
        }
    }
}
