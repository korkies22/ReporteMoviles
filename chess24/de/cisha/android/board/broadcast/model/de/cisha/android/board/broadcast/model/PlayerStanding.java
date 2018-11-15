/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast.model;

import de.cisha.android.board.broadcast.model.TournamentsStandings;

public class PlayerStanding
extends TournamentsStandings {
    private float _points;

    public PlayerStanding(float f, int n, int n2, int n3) {
        super(n, n2, n3);
        this._points = f;
    }

    public float getPoints() {
        return this._points;
    }
}
