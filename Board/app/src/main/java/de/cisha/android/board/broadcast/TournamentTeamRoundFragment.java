// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast;

import de.cisha.android.board.broadcast.model.TournamentTeamPairing;
import java.util.List;

public class TournamentTeamRoundFragment extends AbstractTeamPairingsFragment
{
    @Override
    protected List<TournamentTeamPairing> getTeamPairingList() {
        return this.getTournamentHolder().getTeamPairingsForSelectedRound();
    }
}
