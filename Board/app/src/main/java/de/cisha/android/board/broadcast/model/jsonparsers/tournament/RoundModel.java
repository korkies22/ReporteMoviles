// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.model.jsonparsers.tournament;

import java.util.Collections;
import java.util.Comparator;
import java.util.Collection;
import java.util.ArrayList;
import de.cisha.android.board.playzone.model.TimeControl;
import android.util.SparseArray;
import java.util.List;

public class RoundModel
{
    private List<MatchModel> _matches;
    private String _roundName;
    private int _roundNumber;
    private long _roundStartTimestamp;
    private SparseArray<TimeControl> _timeControls;
    
    public RoundModel(final String roundName, final long roundStartTimestamp, final SparseArray<TimeControl> timeControls, final List<MatchModel> list) {
        this._roundNumber = -1;
        this._roundName = roundName;
        this._roundStartTimestamp = roundStartTimestamp;
        this._timeControls = timeControls;
        List<MatchModel> matches = list;
        if (list == null) {
            matches = new ArrayList<MatchModel>();
        }
        this._matches = matches;
    }
    
    public List<MatchModel> getMatches() {
        final ArrayList<Object> list = new ArrayList<Object>(this._matches);
        Collections.sort(list, (Comparator<? super Object>)new Comparator<MatchModel>() {
            @Override
            public int compare(final MatchModel matchModel, final MatchModel matchModel2) {
                if (matchModel != null && matchModel2 != null) {
                    return matchModel.getMatchNumber() - matchModel2.getMatchNumber();
                }
                return 0;
            }
        });
        return (List<MatchModel>)list;
    }
    
    public String getRoundName() {
        return this._roundName;
    }
    
    public int getRoundNumber() {
        return this._roundNumber;
    }
    
    public long getRoundStartTimestamp() {
        return this._roundStartTimestamp;
    }
    
    public SparseArray<TimeControl> getTimeControls() {
        return this._timeControls;
    }
    
    public void setRoundNumber(final int roundNumber) {
        this._roundNumber = roundNumber;
    }
}
