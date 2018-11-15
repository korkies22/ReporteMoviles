/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast.model;

import de.cisha.android.board.broadcast.model.TournamentInfo;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import java.util.List;

public interface ITournamentListService {
    public void getTournaments(int var1, int var2, LoadCommandCallback<List<TournamentInfo>> var3);
}
