// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.model.jsonparsers;

import de.cisha.chess.util.Logger;
import de.cisha.android.board.broadcast.model.jsonparsers.tournament.MappingTeamRoundRobin;
import de.cisha.android.board.broadcast.model.jsonparsers.tournament.MappingSinglePlayerTournament;
import de.cisha.android.board.broadcast.model.jsonparsers.tournament.MappingMultiknockout;
import de.cisha.android.board.broadcast.model.jsonparsers.tournament.MappingUnknownTournament;
import de.cisha.android.board.broadcast.model.jsonparsers.tournament.AbstractBroadcastTournamentModelMapping;

public enum BroadcastTournamentType
{
    MULTIKNOCKOUT("knockout", (Class<? extends AbstractBroadcastTournamentModelMapping>)MappingMultiknockout.class, true), 
    SINGLEPLAYER_OPEN("open", (Class<? extends AbstractBroadcastTournamentModelMapping>)MappingSinglePlayerTournament.class, true), 
    SINGLEPLAYER_ROUNDROBIN("roundRobin", (Class<? extends AbstractBroadcastTournamentModelMapping>)MappingSinglePlayerTournament.class, true), 
    TEAMROUNDROBIN("teamRoundRobin", (Class<? extends AbstractBroadcastTournamentModelMapping>)MappingTeamRoundRobin.class, false), 
    UNKNOWN("", (Class<? extends AbstractBroadcastTournamentModelMapping>)MappingUnknownTournament.class, false);
    
    private String _key;
    private AbstractBroadcastTournamentModelMapping _mapping;
    private boolean _showStandings;
    
    private BroadcastTournamentType(final String key, final Class<? extends AbstractBroadcastTournamentModelMapping> clazz, final boolean showStandings) {
        this._key = key;
        this._showStandings = showStandings;
        try {
            this._mapping = (AbstractBroadcastTournamentModelMapping)clazz.newInstance();
        }
        catch (IllegalAccessException ex) {
            Logger.getInstance().debug(BroadcastTournamentType.class.getName(), IllegalAccessException.class.getName(), ex);
        }
        catch (InstantiationException ex2) {
            Logger.getInstance().debug(BroadcastTournamentType.class.getName(), InstantiationException.class.getName(), ex2);
        }
    }
    
    public static BroadcastTournamentType fromKey(final String s) {
        final BroadcastTournamentType[] values = values();
        for (int i = 0; i < values.length; ++i) {
            final BroadcastTournamentType broadcastTournamentType = values[i];
            if (broadcastTournamentType._key.equals(s)) {
                return broadcastTournamentType;
            }
        }
        return BroadcastTournamentType.UNKNOWN;
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
