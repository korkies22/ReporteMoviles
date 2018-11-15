/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.service;

import de.cisha.android.board.analyze.model.OpeningPositionInformation;
import de.cisha.chess.model.position.Position;

public interface IOpeningTreeService {
    public void closeBook();

    public OpeningPositionInformation getInformationForPosition(Position var1);
}
