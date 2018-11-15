/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.model.exercise;

import de.cisha.chess.model.exercise.TacticsObserver;

public interface TacticsObservable {
    public void addTacticsObserver(TacticsObserver var1);

    public void removeTacticsObserver(TacticsObserver var1);
}
