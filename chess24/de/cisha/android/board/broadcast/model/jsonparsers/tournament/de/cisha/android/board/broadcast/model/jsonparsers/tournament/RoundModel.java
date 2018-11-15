/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.util.SparseArray
 */
package de.cisha.android.board.broadcast.model.jsonparsers.tournament;

import android.util.SparseArray;
import de.cisha.android.board.broadcast.model.jsonparsers.tournament.MatchModel;
import de.cisha.android.board.playzone.model.TimeControl;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RoundModel {
    private List<MatchModel> _matches;
    private String _roundName;
    private int _roundNumber = -1;
    private long _roundStartTimestamp;
    private SparseArray<TimeControl> _timeControls;

    public RoundModel(String arrayList, long l, SparseArray<TimeControl> sparseArray, List<MatchModel> list) {
        this._roundName = arrayList;
        this._roundStartTimestamp = l;
        this._timeControls = sparseArray;
        arrayList = list;
        if (list == null) {
            arrayList = new ArrayList();
        }
        this._matches = arrayList;
    }

    public List<MatchModel> getMatches() {
        ArrayList<MatchModel> arrayList = new ArrayList<MatchModel>(this._matches);
        Collections.sort(arrayList, new Comparator<MatchModel>(){

            @Override
            public int compare(MatchModel matchModel, MatchModel matchModel2) {
                if (matchModel != null && matchModel2 != null) {
                    return matchModel.getMatchNumber() - matchModel2.getMatchNumber();
                }
                return 0;
            }
        });
        return arrayList;
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

    public void setRoundNumber(int n) {
        this._roundNumber = n;
    }

}
