// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.model;

import de.cisha.android.board.tactics.DummyTacticsExerciseService;
import de.cisha.chess.util.Logger;
import android.os.AsyncTask;
import java.util.LinkedList;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class DummyTournamentListService implements ITournamentListService
{
    private static final String[] _titles;
    private List<TournamentInfo> _dummList;
    
    static {
        _titles = new String[] { "Grenke Chess Classic Baden-Baden  ", "Tata Steel Chess Tournament ", "Porto Mannu ", "Scottish Blitz Chess ChampionsShip ", "Rapidplay Tournament Kings Place ", "Chess World Cup Tromso ", "Grenke Chess Classic Baden-Baden 2 ", "Tata Steel Chess Tournament 2", "Porto Mannu 2", "Scottish Blitz Chess ChampionsShip 2", "Rapidplay Tournament Kings Place 2", "Chess World Cup Tromso 2" };
    }
    
    public DummyTournamentListService() {
        this._dummList = new ArrayList<TournamentInfo>(100);
        int n = 0;
        while (true) {
            if (n >= 100) {
                return;
            }
            Date date = new Date(new Date().getTime() - 50000000L);
            TournamentState tournamentState = TournamentState.ONGOING;
            if (n > 5) {
                date = new Date(new Date().getTime() + 50000000 * n);
                tournamentState = TournamentState.UPCOMING;
            }
            if (n > 13) {
                date = new Date(new Date().getTime() - 604800000 * n);
                tournamentState = TournamentState.FINISHED;
            }
            while (true) {
                while (true) {
                Label_0288:
                    while (true) {
                        try {
                            final StringBuilder sb = new StringBuilder();
                            sb.append("asdasdad");
                            sb.append(n);
                            final TournamentID tournamentID = new TournamentID(sb.toString());
                            final StringBuilder sb2 = new StringBuilder();
                            sb2.append(DummyTournamentListService._titles[n % DummyTournamentListService._titles.length]);
                            sb2.append(n + 1990);
                            final String string = sb2.toString();
                            final MultiGameKnockOutTournamentRoundInfo multiGameKnockOutTournamentRoundInfo = new MultiGameKnockOutTournamentRoundInfo(new MainKnockoutRoundInfo(n % 9), n / 9);
                            if (n % 7 == 0) {
                                final boolean b = true;
                                this._dummList.add(new TournamentInfo(tournamentID, string, n, multiGameKnockOutTournamentRoundInfo, n * 5, n * 8, null, b, tournamentState, date));
                                ++n;
                                break;
                            }
                            break Label_0288;
                        }
                        catch (MalformedURLException ex) {
                            continue;
                        }
                        break;
                    }
                    final boolean b = false;
                    continue;
                }
            }
        }
    }
    
    @Override
    public void getTournaments(int i, final int n, final LoadCommandCallback<List<TournamentInfo>> loadCommandCallback) {
        if (i > 100) {
            loadCommandCallback.loadingSucceded(new LinkedList<TournamentInfo>());
            return;
        }
        final ArrayList<TournamentInfo> list = new ArrayList<TournamentInfo>();
        int n2;
        if ((n2 = i) < 0) {
            n2 = 0;
        }
        for (i = n2; i < n2 + n; ++i) {
            list.add(this._dummList.get(i % this._dummList.size()));
        }
        new AsyncTask<Void, Void, Void>() {
            protected Void doInBackground(final Void... array) {
                try {
                    Thread.sleep(500L);
                }
                catch (InterruptedException ex) {
                    Logger.getInstance().debug(DummyTacticsExerciseService.class.getName(), InterruptedException.class.getName(), ex);
                }
                return null;
            }
            
            protected void onPostExecute(final Void void1) {
                loadCommandCallback.loadingSucceded(list);
            }
        }.execute((Object[])new Void[0]);
    }
}
