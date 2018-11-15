/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast.model;

import de.cisha.android.board.broadcast.model.TournamentsStandings;

public class TeamStanding
extends TournamentsStandings {
    private float _boardPoints;
    private float _teamPoints;

    public TeamStanding(float f, float f2, int n, int n2, int n3) {
        super(n, n2, n3);
        this._teamPoints = f;
        this._boardPoints = f2;
    }

    public float getBoardPoints() {
        return this._boardPoints;
    }

    public float getTeamPoints() {
        return this._teamPoints;
    }
}
