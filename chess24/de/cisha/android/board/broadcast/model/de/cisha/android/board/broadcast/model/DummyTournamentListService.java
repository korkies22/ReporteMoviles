/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.AsyncTask
 */
package de.cisha.android.board.broadcast.model;

import android.os.AsyncTask;
import de.cisha.android.board.broadcast.model.ITournamentListService;
import de.cisha.android.board.broadcast.model.MainKnockoutRoundInfo;
import de.cisha.android.board.broadcast.model.MultiGameKnockOutTournamentRoundInfo;
import de.cisha.android.board.broadcast.model.TournamentID;
import de.cisha.android.board.broadcast.model.TournamentInfo;
import de.cisha.android.board.broadcast.model.TournamentRoundInfo;
import de.cisha.android.board.broadcast.model.TournamentState;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.tactics.DummyTacticsExerciseService;
import de.cisha.chess.util.Logger;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class DummyTournamentListService
implements ITournamentListService {
    private static final String[] _titles = new String[]{"Grenke Chess Classic Baden-Baden  ", "Tata Steel Chess Tournament ", "Porto Mannu ", "Scottish Blitz Chess ChampionsShip ", "Rapidplay Tournament Kings Place ", "Chess World Cup Tromso ", "Grenke Chess Classic Baden-Baden 2 ", "Tata Steel Chess Tournament 2", "Porto Mannu 2", "Scottish Blitz Chess ChampionsShip 2", "Rapidplay Tournament Kings Place 2", "Chess World Cup Tromso 2"};
    private List<TournamentInfo> _dummList = new ArrayList<TournamentInfo>(100);

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public DummyTournamentListService() {
        int n = 0;
        do {
            MultiGameKnockOutTournamentRoundInfo multiGameKnockOutTournamentRoundInfo;
            boolean bl;
            CharSequence charSequence;
            Object object;
            if (n >= 100) {
                return;
            }
            Date date = new Date(new Date().getTime() - 50000000L);
            Object object2 = TournamentState.ONGOING;
            if (n > 5) {
                date = new Date(new Date().getTime() + (long)(50000000 * n));
                object2 = TournamentState.UPCOMING;
            }
            if (n > 13) {
                date = new Date(new Date().getTime() - (long)(604800000 * n));
                object2 = TournamentState.FINISHED;
            }
            try {
                object = new StringBuilder();
                object.append("asdasdad");
                object.append(n);
                object = new TournamentID(object.toString());
                charSequence = new StringBuilder();
                charSequence.append(_titles[n % _titles.length]);
                charSequence.append(n + 1990);
                charSequence = charSequence.toString();
                multiGameKnockOutTournamentRoundInfo = new MultiGameKnockOutTournamentRoundInfo(new MainKnockoutRoundInfo(n % 9), n / 9);
                bl = n % 7 == 0;
            }
            catch (MalformedURLException malformedURLException) {}
            object2 = new TournamentInfo((TournamentID)object, (String)charSequence, n, multiGameKnockOutTournamentRoundInfo, n * 5, n * 8, null, bl, (TournamentState)((Object)object2), date);
            this._dummList.add((TournamentInfo)object2);
            ++n;
        } while (true);
    }

    @Override
    public void getTournaments(int n, int n2, final LoadCommandCallback<List<TournamentInfo>> loadCommandCallback) {
        if (n > 100) {
            loadCommandCallback.loadingSucceded(new LinkedList());
            return;
        }
        final ArrayList<TournamentInfo> arrayList = new ArrayList<TournamentInfo>();
        int n3 = n;
        if (n < 0) {
            n3 = 0;
        }
        for (n = n3; n < n3 + n2; ++n) {
            arrayList.add(this._dummList.get(n % this._dummList.size()));
        }
        new AsyncTask<Void, Void, Void>(){

            protected /* varargs */ Void doInBackground(Void ... arrvoid) {
                try {
                    Thread.sleep(500L);
                }
                catch (InterruptedException interruptedException) {
                    Logger.getInstance().debug(DummyTacticsExerciseService.class.getName(), InterruptedException.class.getName(), interruptedException);
                }
                return null;
            }

            protected void onPostExecute(Void void_) {
                loadCommandCallback.loadingSucceded(arrayList);
            }
        }.execute((Object[])new Void[0]);
    }

}
