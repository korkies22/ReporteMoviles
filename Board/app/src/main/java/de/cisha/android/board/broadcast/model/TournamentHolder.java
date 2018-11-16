// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.model;

import java.util.Iterator;
import com.crashlytics.android.Crashlytics;
import java.util.LinkedList;
import java.util.Map;
import java.util.Collections;
import java.util.WeakHashMap;
import java.util.List;
import java.util.Set;

public class TournamentHolder implements TournamentChangeListener
{
    private Set<TournamentPlayerSelectionListener> _playerObservers;
    private TournamentPlayer _selectedPlayer;
    private TournamentRoundInfo _selectedRound;
    private TournamentTeam _selectedTeam;
    private Set<TournamentTeamSelectionListener> _teamObservers;
    private Tournament _tournament;
    private List<TournamentGamesObserver> _tournamentObserver;
    
    public TournamentHolder() {
        this._teamObservers = Collections.newSetFromMap(new WeakHashMap<TournamentTeamSelectionListener, Boolean>());
        this._playerObservers = Collections.newSetFromMap(new WeakHashMap<TournamentPlayerSelectionListener, Boolean>());
        this._tournamentObserver = new LinkedList<TournamentGamesObserver>();
        Crashlytics.setString("holderInstanceThread", Thread.currentThread().getName());
    }
    
    private void notifyObserversAllGamesChanged() {
        final Iterator<TournamentGamesObserver> iterator = this._tournamentObserver.iterator();
        while (iterator.hasNext()) {
            iterator.next().allGameInfosChanged();
        }
    }
    
    private void notifyObserversGameChanged(final TournamentGameInfo tournamentGameInfo) {
        final Iterator<TournamentGamesObserver> iterator = this._tournamentObserver.iterator();
        while (iterator.hasNext()) {
            iterator.next().gameInfoChanged(tournamentGameInfo);
        }
    }
    
    private void notifyObserversSelectRound(final TournamentRoundInfo tournamentRoundInfo) {
        final Iterator<TournamentGamesObserver> iterator = this._tournamentObserver.iterator();
        while (iterator.hasNext()) {
            iterator.next().onSelectRound(tournamentRoundInfo);
        }
    }
    
    private void notifyPlayerListeners() {
        final Iterator<TournamentPlayerSelectionListener> iterator = this._playerObservers.iterator();
        while (iterator.hasNext()) {
            iterator.next().selectedPlayerChanged(this._selectedPlayer);
        }
    }
    
    private void notifyTeamListeners() {
        final Iterator<TournamentTeamSelectionListener> iterator = this._teamObservers.iterator();
        while (iterator.hasNext()) {
            iterator.next().selectedTeamChanged(this._selectedTeam);
        }
    }
    
    private void notifyTournamentChanged(final boolean b) {
        final Iterator<TournamentGamesObserver> iterator = this._tournamentObserver.iterator();
        while (iterator.hasNext()) {
            iterator.next().registeredForChangesOn(this._tournament, this._selectedRound, b);
        }
    }
    
    private void selectRound(final TournamentRoundInfo selectedRound) {
        this.notifyObserversSelectRound(this._selectedRound = selectedRound);
    }
    
    public void addPlayerSelectionListener(final TournamentPlayerSelectionListener tournamentPlayerSelectionListener) {
        this._playerObservers.add(tournamentPlayerSelectionListener);
    }
    
    public void addTeamSelectionListener(final TournamentTeamSelectionListener tournamentTeamSelectionListener) {
        this._teamObservers.add(tournamentTeamSelectionListener);
    }
    
    public void addTournamentGamesObserver(final TournamentGamesObserver tournamentGamesObserver) {
        this._tournamentObserver.add(tournamentGamesObserver);
        if (this._tournament != null) {
            tournamentGamesObserver.registeredForChangesOn(this._tournament, this._selectedRound, false);
        }
    }
    
    public List<TournamentGameInfo> getGamesForCurrentRound() {
        List<TournamentGameInfo> gamesForRound;
        final LinkedList<TournamentGameInfo> list = (LinkedList<TournamentGameInfo>)(gamesForRound = new LinkedList<TournamentGameInfo>());
        if (this._tournament != null) {
            gamesForRound = list;
            if (this._selectedRound != null) {
                gamesForRound = this._tournament.getGamesForRound(this._selectedRound);
            }
        }
        return gamesForRound;
    }
    
    public TournamentPlayer getSelectedPlayer() {
        return this._selectedPlayer;
    }
    
    public TournamentRoundInfo getSelectedRound() {
        return this._selectedRound;
    }
    
    public TournamentTeam getSelectedTeam() {
        return this._selectedTeam;
    }
    
    public List<TournamentTeamPairing> getTeamPairingsForSelectedRound() {
        List<TournamentTeamPairing> teamPairingsForRound;
        final LinkedList<TournamentTeamPairing> list = (LinkedList<TournamentTeamPairing>)(teamPairingsForRound = new LinkedList<TournamentTeamPairing>());
        if (this._tournament != null) {
            teamPairingsForRound = list;
            if (this._selectedRound != null) {
                teamPairingsForRound = this._tournament.getTeamPairingsForRound(this._selectedRound);
            }
        }
        return teamPairingsForRound;
    }
    
    public Tournament getTournament() {
        if (this._tournament == null) {
            throw new RuntimeException("make sure there is a tournament loaded before calling getTournament()");
        }
        return this._tournament;
    }
    
    public boolean hasNextRound() {
        final List<TournamentRoundInfo> rounds = this._tournament.getRounds();
        return rounds.indexOf(this._selectedRound) < rounds.size() - 1;
    }
    
    public boolean hasPreviousRound() {
        return this._tournament.getRounds().indexOf(this._selectedRound) > 0;
    }
    
    public boolean hasTournament() {
        return this._tournament != null;
    }
    
    public void removePlayerSelectionListener(final TournamentPlayerSelectionListener tournamentPlayerSelectionListener) {
        this._playerObservers.remove(tournamentPlayerSelectionListener);
    }
    
    public void removeTeamSelectionListener(final TournamentTeamSelectionListener tournamentTeamSelectionListener) {
        this._teamObservers.remove(tournamentTeamSelectionListener);
    }
    
    public void removeTournamentGamesObserver(final TournamentGamesObserver tournamentGamesObserver) {
        this._tournamentObserver.remove(tournamentGamesObserver);
    }
    
    public void selectFirstRound() {
        final List<TournamentRoundInfo> rounds = this._tournament.getRounds();
        if (rounds.size() > 0) {
            this.selectRound(rounds.get(0));
        }
    }
    
    public void selectLastRound() {
        final List<TournamentRoundInfo> rounds = this._tournament.getRounds();
        if (rounds.size() > 0) {
            this.selectRound(rounds.get(rounds.size() - 1));
        }
    }
    
    public void selectNextMainRound() {
        final List<TournamentRoundInfo> rounds = this._tournament.getRounds();
        if (this._selectedRound != null) {
            final TournamentRoundInfo mainRound = this._selectedRound.getMainRound();
            int index = rounds.indexOf(this._selectedRound);
            TournamentRoundInfo tournamentRoundInfo;
            do {
                ++index;
                if (index >= rounds.size()) {
                    return;
                }
                tournamentRoundInfo = rounds.get(index);
            } while (tournamentRoundInfo.getMainRound().equals(mainRound));
            this.selectRound(tournamentRoundInfo);
        }
    }
    
    public void selectNextRound() {
        final List<TournamentRoundInfo> rounds = this._tournament.getRounds();
        int n;
        if ((n = rounds.indexOf(this._selectedRound) + 1) >= rounds.size()) {
            n = rounds.size() - 1;
        }
        if (rounds.size() > 0) {
            this.selectRound(rounds.get(n));
        }
    }
    
    public void selectPreviousMainRound() {
        final List<TournamentRoundInfo> rounds = this._tournament.getRounds();
        if (this._selectedRound != null) {
            final int index = rounds.indexOf(this._selectedRound);
            if (index > 0) {
                int n = index - 1;
                TournamentRoundInfo tournamentRoundInfo = rounds.get(n);
                final TournamentRoundInfo mainRound = tournamentRoundInfo.getMainRound();
                while (true) {
                    --n;
                    if (n < 0 || !mainRound.equals(rounds.get(n).getMainRound())) {
                        break;
                    }
                    tournamentRoundInfo = rounds.get(n);
                }
                this.selectRound(tournamentRoundInfo);
            }
        }
    }
    
    public void selectPreviousRound() {
        final List<TournamentRoundInfo> rounds = this._tournament.getRounds();
        int n;
        if ((n = rounds.indexOf(this._selectedRound) - 1) < 0) {
            n = 0;
        }
        if (n < rounds.size()) {
            this.selectRound(rounds.get(n));
        }
    }
    
    public void setSelectedPlayer(final TournamentPlayer selectedPlayer) {
        this._selectedPlayer = selectedPlayer;
        this.notifyPlayerListeners();
    }
    
    public void setSelectedTeam(final TournamentTeam selectedTeam) {
        this._selectedTeam = selectedTeam;
        this.notifyTeamListeners();
    }
    
    public void setTournament(final Tournament tournament) {
        Crashlytics.setString("setTournamentThread", Thread.currentThread().getName());
        Crashlytics.setString("setTournamentName", tournament.getName());
        Crashlytics.setInt("HolderObjectId", System.identityHashCode(this));
        this._tournament = tournament;
        this._selectedRound = this._tournament.getCurrentRound();
        this.notifyTournamentChanged(true);
    }
    
    @Override
    public void tournamentAllGamesChanged() {
        this.notifyObserversAllGamesChanged();
    }
    
    @Override
    public void tournamentGameChanged(final TournamentGameInfo tournamentGameInfo) {
        this.notifyObserversGameChanged(tournamentGameInfo);
    }
    
    public interface TournamentPlayerSelectionListener
    {
        void selectedPlayerChanged(final TournamentPlayer p0);
    }
    
    public interface TournamentTeamSelectionListener
    {
        void selectedTeamChanged(final TournamentTeam p0);
    }
}
