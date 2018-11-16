// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.analyze.model;

import java.util.Collections;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class OpeningPositionInformation
{
    private List<OpeningMoveInformation> _moveInformation;
    
    public OpeningPositionInformation(final List<OpeningMoveInformation> moveInformation) {
        this._moveInformation = moveInformation;
    }
    
    public List<OpeningMoveInformation> getMoveInformation() {
        final LinkedList<Comparable> list = new LinkedList<Comparable>(this._moveInformation);
        Collections.sort(list);
        return (List<OpeningMoveInformation>)list;
    }
}
