// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Collections;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ArrowContainer
{
    private Set<Arrow> _allArrows;
    
    public ArrowContainer() {
        this._allArrows = new HashSet<Arrow>();
    }
    
    public ArrowContainer(final ArrowContainer arrowContainer) {
        this._allArrows = new HashSet<Arrow>(arrowContainer.getAllArrows());
    }
    
    public void addArrow(final Arrow arrow) {
        synchronized (this._allArrows) {
            this._allArrows.add(arrow);
        }
    }
    
    public Collection<Arrow> getAllArrows() {
        return (Collection<Arrow>)Collections.unmodifiableSet((Set<?>)this._allArrows);
    }
    
    public void removeAllArrows() {
        synchronized (this._allArrows) {
            this._allArrows.clear();
        }
    }
    
    public void removeArrow(final Arrow arrow) {
        synchronized (this._allArrows) {
            this._allArrows.remove(arrow);
        }
    }
    
    public void removeArrowsForCategory(final ArrowCategory arrowCategory) {
        synchronized (this._allArrows) {
            final LinkedList<Arrow> list = new LinkedList<Arrow>();
            for (final Arrow arrow : this._allArrows) {
                if (arrow.getCategory() == arrowCategory) {
                    list.add(arrow);
                }
            }
            this._allArrows.removeAll(list);
        }
    }
}
