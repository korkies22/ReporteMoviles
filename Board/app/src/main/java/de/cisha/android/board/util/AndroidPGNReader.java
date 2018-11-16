// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.util;

import de.cisha.chess.model.PGNGame;
import android.os.AsyncTask;
import de.cisha.chess.model.Game;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.chess.model.Country;
import de.cisha.chess.util.PGNReaderDelegate;
import de.cisha.chess.model.GameHolder;
import de.cisha.chess.model.MoveContainer;
import de.cisha.chess.util.PGNReader;

public class AndroidPGNReader extends PGNReader
{
    @Override
    protected Country getCountryForString(final String s) {
        if (s != null) {
            return ServiceProvider.getInstance().getCountryService().getCountryForString(s);
        }
        return null;
    }
    
    @Override
    public void readGameDataBlock(final Game game, final String s, final PGNReaderFinishDelegate pgnReaderFinishDelegate, final GameHolder gameHolder) {
        AsyncTaskCompatHelper.executeOnExecutorPool((android.os.AsyncTask<Void, Object, Object>)new AsyncTask<Void, Void, Void>() {
            protected Void doInBackground(final Void... array) {
                AndroidPGNReader.this.readMoveBlock(game, s, 0, gameHolder);
                return null;
            }
            
            protected void onPostExecute(final Void void1) {
                if (pgnReaderFinishDelegate != null) {
                    pgnReaderFinishDelegate.finishedReadingMoves();
                }
                super.onPostExecute((Object)void1);
            }
        }, new Void[0]);
    }
    
    @Override
    public void readPGN(final String s, final PGNReaderDelegate pgnReaderDelegate) {
        AsyncTaskCompatHelper.executeOnExecutorPool((android.os.AsyncTask<String, Object, Object>)new MyPGNReaderAsyncAdapter(pgnReaderDelegate), s);
    }
    
    private class MyPGNReaderAsyncAdapter extends AsyncTask<String, PGNGame, Void> implements PGNReaderDelegate
    {
        private PGNReaderDelegate _delegate;
        
        public MyPGNReaderAsyncAdapter(final PGNReaderDelegate delegate) {
            this._delegate = delegate;
        }
        
        public void addPGNGame(final PGNGame pgnGame) {
            this.publishProgress((Object[])new PGNGame[] { pgnGame });
        }
        
        protected Void doInBackground(final String... array) {
            final String s = array[0];
            array[0] = null;
            AndroidPGNReader.this.readPGN(s, this);
            return null;
        }
        
        public void finishedReadingPGN() {
        }
        
        protected void onPostExecute(final Void void1) {
            this._delegate.finishedReadingPGN();
        }
        
        protected void onProgressUpdate(final PGNGame... array) {
            this._delegate.addPGNGame(array[0]);
        }
    }
}
