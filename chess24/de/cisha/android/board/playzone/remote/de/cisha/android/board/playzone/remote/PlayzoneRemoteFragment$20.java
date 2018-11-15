/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.remote;

import de.cisha.android.board.playzone.remote.PlayzoneRemoteFragment;

static class PlayzoneRemoteFragment {
    static final /* synthetic */ int[] $SwitchMap$de$cisha$android$board$playzone$remote$PlayzoneRemoteFragment$WaitingScreenState;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static {
        $SwitchMap$de$cisha$android$board$playzone$remote$PlayzoneRemoteFragment$WaitingScreenState = new int[PlayzoneRemoteFragment.WaitingScreenState.values().length];
        try {
            PlayzoneRemoteFragment.$SwitchMap$de$cisha$android$board$playzone$remote$PlayzoneRemoteFragment$WaitingScreenState[PlayzoneRemoteFragment.WaitingScreenState.WAITINGSCREENSTATE_HIDDEN.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            PlayzoneRemoteFragment.$SwitchMap$de$cisha$android$board$playzone$remote$PlayzoneRemoteFragment$WaitingScreenState[PlayzoneRemoteFragment.WaitingScreenState.WAITINGSCREENSTATE_SEARCHING.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            PlayzoneRemoteFragment.$SwitchMap$de$cisha$android$board$playzone$remote$PlayzoneRemoteFragment$WaitingScreenState[PlayzoneRemoteFragment.WaitingScreenState.WAITINGSCREENSTATE_NO_OPPONENT_FOUND.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            PlayzoneRemoteFragment.$SwitchMap$de$cisha$android$board$playzone$remote$PlayzoneRemoteFragment$WaitingScreenState[PlayzoneRemoteFragment.WaitingScreenState.WAITINGSCREENSTATE_NO_NETWORK.ordinal()] = 4;
            return;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            return;
        }
    }
}
