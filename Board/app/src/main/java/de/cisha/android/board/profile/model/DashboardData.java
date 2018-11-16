// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.profile.model;

public class DashboardData
{
    private PlayzoneStatisticData _playzoneData;
    private TacticStatisticData _tacticsData;
    
    public DashboardData(final TacticStatisticData tacticsData, final PlayzoneStatisticData playzoneData) {
        this._tacticsData = tacticsData;
        this._playzoneData = playzoneData;
    }
    
    public PlayzoneStatisticData getPlayzoneData() {
        return this._playzoneData;
    }
    
    public TacticStatisticData getTacticsData() {
        return this._tacticsData;
    }
}
