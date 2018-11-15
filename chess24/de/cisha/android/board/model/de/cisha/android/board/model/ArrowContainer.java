/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.model;

import de.cisha.android.board.model.Arrow;
import de.cisha.android.board.model.ArrowCategory;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

public class ArrowContainer {
    private Set<Arrow> _allArrows;

    public ArrowContainer() {
        this._allArrows = new HashSet<Arrow>();
    }

    public ArrowContainer(ArrowContainer arrowContainer) {
        this._allArrows = new HashSet<Arrow>(arrowContainer.getAllArrows());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void addArrow(Arrow arrow) {
        Set<Arrow> set = this._allArrows;
        synchronized (set) {
            this._allArrows.add(arrow);
            return;
        }
    }

    public Collection<Arrow> getAllArrows() {
        return Collections.unmodifiableSet(this._allArrows);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void removeAllArrows() {
        Set<Arrow> set = this._allArrows;
        synchronized (set) {
            this._allArrows.clear();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void removeArrow(Arrow arrow) {
        Set<Arrow> set = this._allArrows;
        synchronized (set) {
            this._allArrows.remove(arrow);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void removeArrowsForCategory(ArrowCategory arrowCategory) {
        Set<Arrow> set = this._allArrows;
        synchronized (set) {
            LinkedList<Arrow> linkedList = new LinkedList<Arrow>();
            Iterator<Arrow> iterator = this._allArrows.iterator();
            do {
                if (!iterator.hasNext()) {
                    this._allArrows.removeAll(linkedList);
                    return;
                }
                Arrow arrow = iterator.next();
                if (arrow.getCategory() != arrowCategory) continue;
                linkedList.add(arrow);
            } while (true);
        }
    }
}
