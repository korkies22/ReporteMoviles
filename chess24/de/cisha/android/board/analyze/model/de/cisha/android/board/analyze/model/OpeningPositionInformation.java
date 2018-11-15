/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.analyze.model;

import de.cisha.android.board.analyze.model.OpeningMoveInformation;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class OpeningPositionInformation {
    private List<OpeningMoveInformation> _moveInformation;

    public OpeningPositionInformation(List<OpeningMoveInformation> list) {
        this._moveInformation = list;
    }

    public List<OpeningMoveInformation> getMoveInformation() {
        LinkedList<OpeningMoveInformation> linkedList = new LinkedList<OpeningMoveInformation>(this._moveInformation);
        Collections.sort(linkedList);
        return linkedList;
    }
}
