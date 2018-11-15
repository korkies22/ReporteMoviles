/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.profile;

import de.cisha.android.board.profile.TournamentsWidgetController;

static class TournamentsWidgetController {
    static final /* synthetic */ int[] $SwitchMap$de$cisha$android$board$profile$TournamentsWidgetController$TournamentWidgetState;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static {
        $SwitchMap$de$cisha$android$board$profile$TournamentsWidgetController$TournamentWidgetState = new int[TournamentsWidgetController.TournamentWidgetState.values().length];
        try {
            TournamentsWidgetController.$SwitchMap$de$cisha$android$board$profile$TournamentsWidgetController$TournamentWidgetState[TournamentsWidgetController.TournamentWidgetState.LOADING_SUCCEEDED_WITH_NO_TOURNAMENTS.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            TournamentsWidgetController.$SwitchMap$de$cisha$android$board$profile$TournamentsWidgetController$TournamentWidgetState[TournamentsWidgetController.TournamentWidgetState.LOADING_FAILED.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            TournamentsWidgetController.$SwitchMap$de$cisha$android$board$profile$TournamentsWidgetController$TournamentWidgetState[TournamentsWidgetController.TournamentWidgetState.IS_LOADING.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            TournamentsWidgetController.$SwitchMap$de$cisha$android$board$profile$TournamentsWidgetController$TournamentWidgetState[TournamentsWidgetController.TournamentWidgetState.LOADING_SUCCEEDED.ordinal()] = 4;
            return;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            return;
        }
    }
}
