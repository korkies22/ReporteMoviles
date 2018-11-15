/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.service;

import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.chess.model.CishaUUID;

public interface IDUUIDService {
    public void getDuuid(LoadCommandCallback<CishaUUID> var1);

    public void renewDuuid();
}
