// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.tactics;

import de.cisha.chess.model.exercise.TacticsExerciseSolutionInfo;
import org.json.JSONObject;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.chess.util.Logger;
import android.os.AsyncTask;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.chess.model.exercise.TacticsExerciseUserSolution;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.SEP;
import de.cisha.chess.model.Square;
import de.cisha.chess.model.position.Position;
import de.cisha.chess.model.FEN;
import de.cisha.chess.model.Game;
import java.util.ArrayList;
import de.cisha.chess.model.exercise.TacticsExerciseSession;
import de.cisha.chess.model.Rating;
import de.cisha.chess.model.exercise.TacticsExercise;
import java.util.List;

public class DummyTacticsExerciseService implements ITacticsExerciseService
{
    private static DummyTacticsExerciseService _instance;
    private List<TacticsExercise> _allExercises;
    private int _exerciseIndex;
    private Rating _rating;
    private TacticsExerciseSession _session;
    
    private DummyTacticsExerciseService() {
        this.init();
    }
    
    public static DummyTacticsExerciseService getInstance() {
        synchronized (DummyTacticsExerciseService.class) {
            if (DummyTacticsExerciseService._instance == null) {
                DummyTacticsExerciseService._instance = new DummyTacticsExerciseService();
            }
            return DummyTacticsExerciseService._instance;
        }
    }
    
    private void init() {
        this._exerciseIndex = 0;
        this._session = null;
        this._allExercises = new ArrayList<TacticsExercise>();
        this._rating = new Rating(1200);
        final Game game = new Game();
        final Position startingPosition = new Position(new FEN("2q2r1k/1b4pp/7N/3Q4/8/8/8/4R1K1 w - - 0 1"));
        game.setStartingPosition(startingPosition);
        final Move move = startingPosition.createMoveFrom(new SEP(Square.SQUARE_D5, Square.SQUARE_G8));
        game.addMove(move);
        final Move move2 = move.getResultingPosition().createMoveFrom(new SEP(Square.SQUARE_F8, Square.SQUARE_G8));
        move.addMove(move2);
        move2.addMove(move2.getResultingPosition().createMoveFrom(new SEP(Square.SQUARE_H6, Square.SQUARE_F7)));
        this._allExercises.add(new TacticsExercise(game, true, new Rating(1300), 5555));
        final Game game2 = new Game();
        final Position startingPosition2 = new Position(new FEN("8/8/8/8/4p1p1/2N1pkp1/8/R3K2R w KQ - 0 1"));
        game2.setStartingPosition(startingPosition2);
        game2.addMove(startingPosition2.createMoveFrom(new SEP(Square.SQUARE_E1, Square.SQUARE_G1)));
        final Move moveFromSAN = startingPosition2.createMoveFromSAN("Rf1+");
        game2.addMove(moveFromSAN);
        final Move moveFromSAN2 = moveFromSAN.getResultingPosition().createMoveFromSAN("Kg2");
        moveFromSAN.addMove(moveFromSAN2);
        final Move moveFromSAN3 = moveFromSAN2.getResultingPosition().createMoveFromSAN("Ra2+");
        moveFromSAN2.addMove(moveFromSAN3);
        final Move moveFromSAN4 = moveFromSAN3.getResultingPosition().createMoveFromSAN("Kh3");
        moveFromSAN3.addMove(moveFromSAN4);
        final Move moveFromSAN5 = moveFromSAN4.getResultingPosition().createMoveFromSAN("Rh1#");
        moveFromSAN4.addMove(moveFromSAN5);
        moveFromSAN5.getResultingPosition();
        this._allExercises.add(new TacticsExercise(game2, true, new Rating(1400), 12122));
        final Game game3 = new Game();
        final Position startingPosition3 = new Position(new FEN("6rk/1q1n1prp/p3pN1Q/1p1b4/6R1/6P1/PP2PP1P/3R2K1 w - - 0 1"));
        game3.setStartingPosition(startingPosition3);
        final Move moveFromSAN6 = startingPosition3.createMoveFromSAN("Qxh7+");
        game3.addMoveInMainLine(moveFromSAN6);
        final Move moveFromSAN7 = moveFromSAN6.getResultingPosition().createMoveFromSAN("Rxh7");
        final Position resultingPosition = moveFromSAN7.getResultingPosition();
        game3.addMoveInMainLine(moveFromSAN7);
        final Move moveFromSAN8 = resultingPosition.createMoveFromSAN("Rxg8#");
        moveFromSAN8.getResultingPosition();
        game3.addMoveInMainLine(moveFromSAN8);
        this._allExercises.add(new TacticsExercise(game3, true, new Rating(1200), 4444));
    }
    
    @Override
    public void exerciseSolved(final TacticsExerciseUserSolution tacticsExerciseUserSolution, final LoadCommandCallback<Rating> loadCommandCallback) {
        this.exerciseSolved(tacticsExerciseUserSolution, loadCommandCallback, true);
    }
    
    @Override
    public void exerciseSolved(final TacticsExerciseUserSolution tacticsExerciseUserSolution, final LoadCommandCallback<Rating> loadCommandCallback, final boolean b) {
        new AsyncTask<Void, Void, Void>() {
            protected Void doInBackground(final Void... array) {
                try {
                    Thread.sleep(1000L);
                }
                catch (InterruptedException ex) {
                    Logger.getInstance().debug(DummyTacticsExerciseService.class.getName(), InterruptedException.class.getName(), ex);
                }
                return null;
            }
            
            protected void onPostExecute(final Void void1) {
                if (tacticsExerciseUserSolution.isCorrect()) {
                    DummyTacticsExerciseService.this._rating = new Rating(DummyTacticsExerciseService.this._rating.getRating() + 23);
                }
                else {
                    DummyTacticsExerciseService.this._rating = new Rating(DummyTacticsExerciseService.this._rating.getRating() - 23);
                }
                loadCommandCallback.loadingSucceded(DummyTacticsExerciseService.this._rating);
            }
        }.execute((Object[])new Void[0]);
    }
    
    @Override
    public void getCurrentSession(final LoadCommandCallback<TacticsExerciseSession> loadCommandCallback) {
        new AsyncTask<Void, Void, Void>() {
            protected Void doInBackground(final Void... array) {
                try {
                    Thread.sleep(100L);
                }
                catch (InterruptedException ex) {
                    Logger.getInstance().debug(DummyTacticsExerciseService.class.getName(), InterruptedException.class.getName(), ex);
                }
                return null;
            }
            
            protected void onPostExecute(final Void void1) {
                if (DummyTacticsExerciseService.this._session != null) {
                    loadCommandCallback.loadingSucceded(DummyTacticsExerciseService.this._session);
                    return;
                }
                loadCommandCallback.loadingFailed(APIStatusCode.INTERNAL_UNKNOWN, "always call startNewSession before getCurrentSesion", null, null);
            }
        }.execute((Object[])new Void[0]);
    }
    
    @Override
    public void getNextExercise(final LoadCommandCallback<TacticsExerciseUserSolution> loadCommandCallback) {
        if (this._session == null) {
            this._session = new TacticsExerciseSession();
        }
        final TacticsExerciseUserSolution tacticsExerciseUserSolution = new TacticsExerciseUserSolution(this.getNextTacticsExercise());
        tacticsExerciseUserSolution.setInfo(new TacticsExerciseSolutionInfo(0, new Rating(1200), new Rating(1300), new Rating(1210)));
        this._session.addExercise(tacticsExerciseUserSolution);
        new AsyncTask<Void, Void, Void>() {
            protected Void doInBackground(final Void... array) {
                try {
                    Thread.sleep(2500L);
                }
                catch (InterruptedException ex) {
                    Logger.getInstance().debug(DummyTacticsExerciseService.class.getName(), InterruptedException.class.getName(), ex);
                }
                return null;
            }
            
            protected void onPostExecute(final Void void1) {
                loadCommandCallback.loadingSucceded(tacticsExerciseUserSolution);
            }
        }.execute((Object[])new Void[0]);
    }
    
    protected TacticsExercise getNextTacticsExercise() {
        ++this._exerciseIndex;
        if (this._exerciseIndex >= this._allExercises.size()) {
            this._exerciseIndex = 0;
        }
        return this._allExercises.get(this._exerciseIndex);
    }
    
    @Override
    public void getTacticsTrainerInfo(final LoadCommandCallback<TacticsTrainerInfo> loadCommandCallback) {
        new AsyncTask<Void, Void, Void>() {
            protected Void doInBackground(final Void... array) {
                try {
                    Thread.sleep(1000L);
                }
                catch (InterruptedException ex) {
                    Logger.getInstance().debug(DummyTacticsExerciseService.class.getName(), InterruptedException.class.getName(), ex);
                }
                return null;
            }
            
            protected void onPostExecute(final Void void1) {
                loadCommandCallback.loadingSucceded(new TacticsTrainerInfo(true, 5, 0L));
            }
        }.execute((Object[])new Void[0]);
    }
    
    @Override
    public void getUserExerciseRating(final LoadCommandCallback<Rating> loadCommandCallback) {
        new AsyncTask<Void, Void, Void>() {
            protected Void doInBackground(final Void... array) {
                try {
                    Thread.sleep(1000L);
                }
                catch (InterruptedException ex) {
                    Logger.getInstance().debug(DummyTacticsExerciseService.class.getName(), InterruptedException.class.getName(), ex);
                }
                return null;
            }
            
            protected void onPostExecute(final Void void1) {
                loadCommandCallback.loadingSucceded(DummyTacticsExerciseService.this._rating);
            }
        }.execute((Object[])new Void[0]);
    }
    
    public void reset() {
        this.init();
    }
    
    @Override
    public Square showHintForPuzzle(final TacticsExerciseUserSolution tacticsExerciseUserSolution) {
        return tacticsExerciseUserSolution.getHint();
    }
    
    @Override
    public TacticsExerciseSession startNewSession() {
        return this._session = new TacticsExerciseSession();
    }
}
