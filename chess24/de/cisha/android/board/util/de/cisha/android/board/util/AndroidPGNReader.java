/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.AsyncTask
 */
package de.cisha.android.board.util;

import android.os.AsyncTask;
import de.cisha.android.board.service.ICountryService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.util.AsyncTaskCompatHelper;
import de.cisha.chess.model.Country;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.GameHolder;
import de.cisha.chess.model.MoveContainer;
import de.cisha.chess.model.PGNGame;
import de.cisha.chess.util.PGNReader;
import de.cisha.chess.util.PGNReaderDelegate;

public class AndroidPGNReader
extends PGNReader {
    @Override
    protected Country getCountryForString(String string) {
        if (string != null) {
            return ServiceProvider.getInstance().getCountryService().getCountryForString(string);
        }
        return null;
    }

    @Override
    public void readGameDataBlock(final Game game, final String string, final PGNReader.PGNReaderFinishDelegate pGNReaderFinishDelegate, final GameHolder gameHolder) {
        AsyncTaskCompatHelper.executeOnExecutorPool(new AsyncTask<Void, Void, Void>(){

            protected /* varargs */ Void doInBackground(Void ... arrvoid) {
                AndroidPGNReader.this.readMoveBlock(game, string, 0, gameHolder);
                return null;
            }

            protected void onPostExecute(Void void_) {
                if (pGNReaderFinishDelegate != null) {
                    pGNReaderFinishDelegate.finishedReadingMoves();
                }
                super.onPostExecute((Object)void_);
            }
        }, new Void[0]);
    }

    @Override
    public void readPGN(String string, PGNReaderDelegate pGNReaderDelegate) {
        AsyncTaskCompatHelper.executeOnExecutorPool(new MyPGNReaderAsyncAdapter(pGNReaderDelegate), string);
    }

    private class MyPGNReaderAsyncAdapter
    extends AsyncTask<String, PGNGame, Void>
    implements PGNReaderDelegate {
        private PGNReaderDelegate _delegate;

        public MyPGNReaderAsyncAdapter(PGNReaderDelegate pGNReaderDelegate) {
            this._delegate = pGNReaderDelegate;
        }

        @Override
        public void addPGNGame(PGNGame pGNGame) {
            this.publishProgress((Object[])new PGNGame[]{pGNGame});
        }

        protected /* varargs */ Void doInBackground(String ... arrstring) {
            String string = arrstring[0];
            arrstring[0] = null;
            AndroidPGNReader.super.readPGN(string, this);
            return null;
        }

        @Override
        public void finishedReadingPGN() {
        }

        protected void onPostExecute(Void void_) {
            this._delegate.finishedReadingPGN();
        }

        protected /* varargs */ void onProgressUpdate(PGNGame ... arrpGNGame) {
            this._delegate.addPGNGame(arrpGNGame[0]);
        }
    }

}
