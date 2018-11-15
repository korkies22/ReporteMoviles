/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast;

import de.cisha.android.board.broadcast.model.jsonparsers.BroadcastTournamentType;

static class TournamentDetailsFragment {
    static final /* synthetic */ int[] $SwitchMap$de$cisha$android$board$broadcast$model$jsonparsers$BroadcastTournamentType;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static {
        $SwitchMap$de$cisha$android$board$broadcast$model$jsonparsers$BroadcastTournamentType = new int[BroadcastTournamentType.values().length];
        try {
            TournamentDetailsFragment.$SwitchMap$de$cisha$android$board$broadcast$model$jsonparsers$BroadcastTournamentType[BroadcastTournamentType.MULTIKNOCKOUT.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            TournamentDetailsFragment.$SwitchMap$de$cisha$android$board$broadcast$model$jsonparsers$BroadcastTournamentType[BroadcastTournamentType.SINGLEPLAYER_OPEN.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            TournamentDetailsFragment.$SwitchMap$de$cisha$android$board$broadcast$model$jsonparsers$BroadcastTournamentType[BroadcastTournamentType.SINGLEPLAYER_ROUNDROBIN.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            TournamentDetailsFragment.$SwitchMap$de$cisha$android$board$broadcast$model$jsonparsers$BroadcastTournamentType[BroadcastTournamentType.TEAMROUNDROBIN.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            TournamentDetailsFragment.$SwitchMap$de$cisha$android$board$broadcast$model$jsonparsers$BroadcastTournamentType[BroadcastTournamentType.UNKNOWN.ordinal()] = 5;
            return;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            return;
        }
    }
}
