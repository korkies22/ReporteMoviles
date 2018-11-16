// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.model;

import java.util.List;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;

public interface ITournamentListService
{
    void getTournaments(final int p0, final int p1, final LoadCommandCallback<List<TournamentInfo>> p2);
}
