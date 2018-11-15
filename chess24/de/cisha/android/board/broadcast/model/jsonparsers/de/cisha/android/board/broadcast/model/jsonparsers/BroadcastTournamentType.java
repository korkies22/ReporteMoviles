/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast.model.jsonparsers;

import de.cisha.android.board.broadcast.model.jsonparsers.tournament.AbstractBroadcastTournamentModelMapping;
import de.cisha.android.board.broadcast.model.jsonparsers.tournament.MappingMultiknockout;
import de.cisha.android.board.broadcast.model.jsonparsers.tournament.MappingSinglePlayerTournament;
import de.cisha.android.board.broadcast.model.jsonparsers.tournament.MappingTeamRoundRobin;
import de.cisha.android.board.broadcast.model.jsonparsers.tournament.MappingUnknownTournament;
import de.cisha.chess.util.Logger;

public enum BroadcastTournamentType {
    UNKNOWN("", MappingUnknownTournament.class, false),
    MULTIKNOCKOUT("knockout", MappingMultiknockout.class, true),
    SINGLEPLAYER_ROUNDROBIN("roundRobin", MappingSinglePlayerTournament.class, true),
    SINGLEPLAYER_OPEN("open", MappingSinglePlayerTournament.class, true),
    TEAMROUNDROBIN("teamRoundRobin", MappingTeamRoundRobin.class, false);
    
    private String _key;
    private AbstractBroadcastTournamentModelMapping _mapping;
    private boolean _showStandings;

    private BroadcastTournamentType(String string2, Class<? extends AbstractBroadcastTournamentModelMapping> class_, boolean bl) {
        this._key = string2;
        this._showStandings = bl;
        try {
            this._mapping = class_.newInstance();
            return;
        }
        catch (IllegalAccessException illegalAccessException) {
            Logger.getInstance().debug(BroadcastTournamentType.class.getName(), IllegalAccessException.class.getName(), illegalAccessException);
            return;
        }
        catch (InstantiationException instantiationException) {
            Logger.getInstance().debug(BroadcastTournamentType.class.getName(), InstantiationException.class.getName(), instantiationException);
            return;
        }
    }

    public static BroadcastTournamentType fromKey(String string) {
        for (BroadcastTournamentType broadcastTournamentType : BroadcastTournamentType.values()) {
            if (!broadcastTournamentType._key.equals(string)) continue;
            return broadcastTournamentType;
        }
        return UNKNOWN;
    }

    public AbstractBroadcastTournamentModelMapping getTournamentModelConverter() {
        if (this._mapping != null) {
            return this._mapping;
        }
        return new MappingUnknownTournament();
    }

    public boolean showStandings() {
        return this._showStandings;
    }
}
