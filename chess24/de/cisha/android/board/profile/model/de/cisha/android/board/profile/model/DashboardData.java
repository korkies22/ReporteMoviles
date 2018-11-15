/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.profile.model;

import de.cisha.android.board.profile.model.PlayzoneStatisticData;
import de.cisha.android.board.profile.model.TacticStatisticData;

public class DashboardData {
    private PlayzoneStatisticData _playzoneData;
    private TacticStatisticData _tacticsData;

    public DashboardData(TacticStatisticData tacticStatisticData, PlayzoneStatisticData playzoneStatisticData) {
        this._tacticsData = tacticStatisticData;
        this._playzoneData = playzoneStatisticData;
    }

    public PlayzoneStatisticData getPlayzoneData() {
        return this._playzoneData;
    }

    public TacticStatisticData getTacticsData() {
        return this._tacticsData;
    }
}
