/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast.model;

import com.crashlytics.android.Crashlytics;
import de.cisha.android.board.broadcast.model.ITournamentConnection;
import de.cisha.android.board.broadcast.model.Tournament;
import de.cisha.android.board.broadcast.model.TournamentGameInfo;
import de.cisha.android.board.broadcast.model.TournamentGamesObserver;
import de.cisha.android.board.broadcast.model.TournamentPlayer;
import de.cisha.android.board.broadcast.model.TournamentRoundInfo;
import de.cisha.android.board.broadcast.model.TournamentTeam;
import de.cisha.android.board.broadcast.model.TournamentTeamPairing;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.WeakHashMap;

public class TournamentHolder
implements ITournamentConnection.TournamentChangeListener {
    private Set<TournamentPlayerSelectionListener> _playerObservers = Collections.newSetFromMap(new WeakHashMap());
    private TournamentPlayer _selectedPlayer;
    private TournamentRoundInfo _selectedRound;
    private TournamentTeam _selectedTeam;
    private Set<TournamentTeamSelectionListener> _teamObservers = Collections.newSetFromMap(new WeakHashMap());
    private Tournament _tournament;
    private List<TournamentGamesObserver> _tournamentObserver = new LinkedList<TournamentGamesObserver>();

    public TournamentHolder() {
        Crashlytics.setString("holderInstanceThread", Thread.currentThread().getName());
    }

    private void notifyObserversAllGamesChanged() {
        Iterator<TournamentGamesObserver> iterator = this._tournamentObserver.iterator();
        while (iterator.hasNext()) {
            iterator.next().allGameInfosChanged();
        }
    }

    private void notifyObserversGameChanged(TournamentGameInfo tournamentGameInfo) {
        Iterator<TournamentGamesObserver> iterator = this._tournamentObserver.iterator();
        while (iterator.hasNext()) {
            iterator.next().gameInfoChanged(tournamentGameInfo);
        }
    }

    private void notifyObserversSelectRound(TournamentRoundInfo tournamentRoundInfo) {
        Iterator<TournamentGamesObserver> iterator = this._tournamentObserver.iterator();
        while (iterator.hasNext()) {
            iterator.next().onSelectRound(tournamentRoundInfo);
        }
    }

    private void notifyPlayerListeners() {
        Iterator<TournamentPlayerSelectionListener> iterator = this._playerObservers.iterator();
        while (iterator.hasNext()) {
            iterator.next().selectedPlayerChanged(this._selectedPlayer);
        }
    }

    private void notifyTeamListeners() {
        Iterator<TournamentTeamSelectionListener> iterator = this._teamObservers.iterator();
        while (iterator.hasNext()) {
            iterator.next().selectedTeamChanged(this._selectedTeam);
        }
    }

    private void notifyTournamentChanged(boolean bl) {
        Iterator<TournamentGamesObserver> iterator = this._tournamentObserver.iterator();
        while (iterator.hasNext()) {
            iterator.next().registeredForChangesOn(this._tournament, this._selectedRound, bl);
        }
    }

    private void selectRound(TournamentRoundInfo tournamentRoundInfo) {
        this._selectedRound = tournamentRoundInfo;
        this.notifyObserversSelectRound(this._selectedRound);
    }

    public void addPlayerSelectionListener(TournamentPlayerSelectionListener tournamentPlayerSelectionListener) {
        this._playerObservers.add(tournamentPlayerSelectionListener);
    }

    public void addTeamSelectionListener(TournamentTeamSelectionListener tournamentTeamSelectionListener) {
        this._teamObservers.add(tournamentTeamSelectionListener);
    }

    public void addTournamentGamesObserver(TournamentGamesObserver tournamentGamesObserver) {
        this._tournamentObserver.add(tournamentGamesObserver);
        if (this._tournament != null) {
            tournamentGamesObserver.registeredForChangesOn(this._tournament, this._selectedRound, false);
        }
    }

    public List<TournamentGameInfo> getGamesForCurrentRound() {
        LinkedList<TournamentGameInfo> linkedList;
        List<TournamentGameInfo> list = linkedList = new LinkedList<TournamentGameInfo>();
        if (this._tournament != null) {
            list = linkedList;
            if (this._selectedRound != null) {
                list = this._tournament.getGamesForRound(this._selectedRound);
            }
        }
        return list;
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
        LinkedList<TournamentTeamPairing> linkedList;
        List<TournamentTeamPairing> list = linkedList = new LinkedList<TournamentTeamPairing>();
        if (this._tournament != null) {
            list = linkedList;
            if (this._selectedRound != null) {
                list = this._tournament.getTeamPairingsForRound(this._selectedRound);
            }
        }
        return list;
    }

    public Tournament getTournament() {
        if (this._tournament == null) {
            throw new RuntimeException("make sure there is a tournament loaded before calling getTournament()");
        }
        return this._tournament;
    }

    public boolean hasNextRound() {
        List<TournamentRoundInfo> list = this._tournament.getRounds();
        if (list.indexOf(this._selectedRound) < list.size() - 1) {
            return true;
        }
        return false;
    }

    public boolean hasPreviousRound() {
        if (this._tournament.getRounds().indexOf(this._selectedRound) > 0) {
            return true;
        }
        return false;
    }

    public boolean hasTournament() {
        if (this._tournament != null) {
            return true;
        }
        return false;
    }

    public void removePlayerSelectionListener(TournamentPlayerSelectionListener tournamentPlayerSelectionListener) {
        this._playerObservers.remove(tournamentPlayerSelectionListener);
    }

    public void removeTeamSelectionListener(TournamentTeamSelectionListener tournamentTeamSelectionListener) {
        this._teamObservers.remove(tournamentTeamSelectionListener);
    }

    public void removeTournamentGamesObserver(TournamentGamesObserver tournamentGamesObserver) {
        this._tournamentObserver.remove(tournamentGamesObserver);
    }

    public void selectFirstRound() {
        List<TournamentRoundInfo> list = this._tournament.getRounds();
        if (list.size() > 0) {
            this.selectRound(list.get(0));
        }
    }

    public void selectLastRound() {
        List<TournamentRoundInfo> list = this._tournament.getRounds();
        if (list.size() > 0) {
            this.selectRound(list.get(list.size() - 1));
        }
    }

    public void selectNextMainRound() {
        List<TournamentRoundInfo> list = this._tournament.getRounds();
        if (this._selectedRound != null) {
            TournamentRoundInfo tournamentRoundInfo = this._selectedRound.getMainRound();
            int n = list.indexOf(this._selectedRound);
            while (++n < list.size()) {
                TournamentRoundInfo tournamentRoundInfo2 = list.get(n);
                if (tournamentRoundInfo2.getMainRound().equals(tournamentRoundInfo)) continue;
                this.selectRound(tournamentRoundInfo2);
                break;
            }
        }
    }

    public void selectNextRound() {
        int n;
        List<TournamentRoundInfo> list = this._tournament.getRounds();
        int n2 = n = list.indexOf(this._selectedRound) + 1;
        if (n >= list.size()) {
            n2 = list.size() - 1;
        }
        if (list.size() > 0) {
            this.selectRound(list.get(n2));
        }
    }

    public void selectPreviousMainRound() {
        int n;
        List<TournamentRoundInfo> list = this._tournament.getRounds();
        if (this._selectedRound != null && (n = list.indexOf(this._selectedRound)) > 0) {
            TournamentRoundInfo tournamentRoundInfo = list.get(--n);
            TournamentRoundInfo tournamentRoundInfo2 = tournamentRoundInfo.getMainRound();
            while (--n >= 0 && tournamentRoundInfo2.equals(list.get(n).getMainRound())) {
                tournamentRoundInfo = list.get(n);
            }
            this.selectRound(tournamentRoundInfo);
        }
    }

    public void selectPreviousRound() {
        int n;
        List<TournamentRoundInfo> list = this._tournament.getRounds();
        int n2 = n = list.indexOf(this._selectedRound) - 1;
        if (n < 0) {
            n2 = 0;
        }
        if (n2 < list.size()) {
            this.selectRound(list.get(n2));
        }
    }

    public void setSelectedPlayer(TournamentPlayer tournamentPlayer) {
        this._selectedPlayer = tournamentPlayer;
        this.notifyPlayerListeners();
    }

    public void setSelectedTeam(TournamentTeam tournamentTeam) {
        this._selectedTeam = tournamentTeam;
        this.notifyTeamListeners();
    }

    public void setTournament(Tournament tournament) {
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
    public void tournamentGameChanged(TournamentGameInfo tournamentGameInfo) {
        this.notifyObserversGameChanged(tournamentGameInfo);
    }

    public static interface TournamentPlayerSelectionListener {
        public void selectedPlayerChanged(TournamentPlayer var1);
    }

    public static interface TournamentTeamSelectionListener {
        public void selectedTeamChanged(TournamentTeam var1);
    }

}
