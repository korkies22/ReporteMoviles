/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast.standings.model;

import de.cisha.android.board.broadcast.model.TournamentPlayer;
import de.cisha.android.board.broadcast.standings.model.GamePairing;

public class MultiGameKnockoutPairing
extends GamePairing<TournamentPlayer> {
    public MultiGameKnockoutPairing(TournamentPlayer tournamentPlayer, TournamentPlayer tournamentPlayer2) {
        super(tournamentPlayer, tournamentPlayer2);
    }
}
