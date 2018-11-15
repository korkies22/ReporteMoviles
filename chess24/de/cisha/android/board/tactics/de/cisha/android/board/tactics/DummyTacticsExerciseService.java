/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.AsyncTask
 *  org.json.JSONObject
 */
package de.cisha.android.board.tactics;

import android.os.AsyncTask;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.board.tactics.ITacticsExerciseService;
import de.cisha.chess.model.BaseObject;
import de.cisha.chess.model.FEN;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.Rating;
import de.cisha.chess.model.SEP;
import de.cisha.chess.model.Square;
import de.cisha.chess.model.exercise.TacticsExercise;
import de.cisha.chess.model.exercise.TacticsExerciseSession;
import de.cisha.chess.model.exercise.TacticsExerciseSolutionInfo;
import de.cisha.chess.model.exercise.TacticsExerciseUserSolution;
import de.cisha.chess.model.position.Position;
import de.cisha.chess.util.Logger;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public class DummyTacticsExerciseService
implements ITacticsExerciseService {
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
            if (_instance == null) {
                _instance = new DummyTacticsExerciseService();
            }
            DummyTacticsExerciseService dummyTacticsExerciseService = _instance;
            return dummyTacticsExerciseService;
        }
    }

    private void init() {
        this._exerciseIndex = 0;
        this._session = null;
        this._allExercises = new ArrayList<TacticsExercise>();
        this._rating = new Rating(1200);
        Game game = new Game();
        BaseObject baseObject = new Position(new FEN("2q2r1k/1b4pp/7N/3Q4/8/8/8/4R1K1 w - - 0 1"));
        game.setStartingPosition((Position)baseObject);
        baseObject = baseObject.createMoveFrom(new SEP(Square.SQUARE_D5, Square.SQUARE_G8));
        game.addMove((Move)baseObject);
        BaseObject baseObject2 = baseObject.getResultingPosition().createMoveFrom(new SEP(Square.SQUARE_F8, Square.SQUARE_G8));
        baseObject.addMove((Move)baseObject2);
        baseObject2.addMove(baseObject2.getResultingPosition().createMoveFrom(new SEP(Square.SQUARE_H6, Square.SQUARE_F7)));
        this._allExercises.add(new TacticsExercise(game, true, new Rating(1300), 5555));
        game = new Game();
        baseObject = new Position(new FEN("8/8/8/8/4p1p1/2N1pkp1/8/R3K2R w KQ - 0 1"));
        game.setStartingPosition((Position)baseObject);
        game.addMove(baseObject.createMoveFrom(new SEP(Square.SQUARE_E1, Square.SQUARE_G1)));
        baseObject2 = baseObject.createMoveFromSAN("Rf1+");
        game.addMove((Move)baseObject2);
        baseObject = baseObject2.getResultingPosition().createMoveFromSAN("Kg2");
        baseObject2.addMove((Move)baseObject);
        baseObject2 = baseObject.getResultingPosition().createMoveFromSAN("Ra2+");
        baseObject.addMove((Move)baseObject2);
        baseObject = baseObject2.getResultingPosition().createMoveFromSAN("Kh3");
        baseObject2.addMove((Move)baseObject);
        baseObject2 = baseObject.getResultingPosition().createMoveFromSAN("Rh1#");
        baseObject.addMove((Move)baseObject2);
        baseObject2.getResultingPosition();
        this._allExercises.add(new TacticsExercise(game, true, new Rating(1400), 12122));
        game = new Game();
        baseObject = new Position(new FEN("6rk/1q1n1prp/p3pN1Q/1p1b4/6R1/6P1/PP2PP1P/3R2K1 w - - 0 1"));
        game.setStartingPosition((Position)baseObject);
        baseObject = baseObject.createMoveFromSAN("Qxh7+");
        game.addMoveInMainLine((Move)baseObject);
        baseObject = baseObject.getResultingPosition().createMoveFromSAN("Rxh7");
        baseObject2 = baseObject.getResultingPosition();
        game.addMoveInMainLine((Move)baseObject);
        baseObject = baseObject2.createMoveFromSAN("Rxg8#");
        baseObject.getResultingPosition();
        game.addMoveInMainLine((Move)baseObject);
        this._allExercises.add(new TacticsExercise(game, true, new Rating(1200), 4444));
    }

    @Override
    public void exerciseSolved(TacticsExerciseUserSolution tacticsExerciseUserSolution, LoadCommandCallback<Rating> loadCommandCallback) {
        this.exerciseSolved(tacticsExerciseUserSolution, loadCommandCallback, true);
    }

    @Override
    public void exerciseSolved(final TacticsExerciseUserSolution tacticsExerciseUserSolution, final LoadCommandCallback<Rating> loadCommandCallback, boolean bl) {
        new AsyncTask<Void, Void, Void>(){

            protected /* varargs */ Void doInBackground(Void ... arrvoid) {
                try {
                    Thread.sleep(1000L);
                }
                catch (InterruptedException interruptedException) {
                    Logger.getInstance().debug(DummyTacticsExerciseService.class.getName(), InterruptedException.class.getName(), interruptedException);
                }
                return null;
            }

            protected void onPostExecute(Void void_) {
                if (tacticsExerciseUserSolution.isCorrect()) {
                    DummyTacticsExerciseService.this._rating = new Rating(DummyTacticsExerciseService.this._rating.getRating() + 23);
                } else {
                    DummyTacticsExerciseService.this._rating = new Rating(DummyTacticsExerciseService.this._rating.getRating() - 23);
                }
                loadCommandCallback.loadingSucceded(DummyTacticsExerciseService.this._rating);
            }
        }.execute((Object[])new Void[0]);
    }

    @Override
    public void getCurrentSession(final LoadCommandCallback<TacticsExerciseSession> loadCommandCallback) {
        new AsyncTask<Void, Void, Void>(){

            protected /* varargs */ Void doInBackground(Void ... arrvoid) {
                try {
                    Thread.sleep(100L);
                }
                catch (InterruptedException interruptedException) {
                    Logger.getInstance().debug(DummyTacticsExerciseService.class.getName(), InterruptedException.class.getName(), interruptedException);
                }
                return null;
            }

            protected void onPostExecute(Void void_) {
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
        new AsyncTask<Void, Void, Void>(){

            protected /* varargs */ Void doInBackground(Void ... arrvoid) {
                try {
                    Thread.sleep(2500L);
                }
                catch (InterruptedException interruptedException) {
                    Logger.getInstance().debug(DummyTacticsExerciseService.class.getName(), InterruptedException.class.getName(), interruptedException);
                }
                return null;
            }

            protected void onPostExecute(Void void_) {
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
    public void getTacticsTrainerInfo(final LoadCommandCallback<ITacticsExerciseService.TacticsTrainerInfo> loadCommandCallback) {
        new AsyncTask<Void, Void, Void>(){

            protected /* varargs */ Void doInBackground(Void ... arrvoid) {
                try {
                    Thread.sleep(1000L);
                }
                catch (InterruptedException interruptedException) {
                    Logger.getInstance().debug(DummyTacticsExerciseService.class.getName(), InterruptedException.class.getName(), interruptedException);
                }
                return null;
            }

            protected void onPostExecute(Void void_) {
                loadCommandCallback.loadingSucceded(new ITacticsExerciseService.TacticsTrainerInfo(true, 5, 0L));
            }
        }.execute((Object[])new Void[0]);
    }

    @Override
    public void getUserExerciseRating(final LoadCommandCallback<Rating> loadCommandCallback) {
        new AsyncTask<Void, Void, Void>(){

            protected /* varargs */ Void doInBackground(Void ... arrvoid) {
                try {
                    Thread.sleep(1000L);
                }
                catch (InterruptedException interruptedException) {
                    Logger.getInstance().debug(DummyTacticsExerciseService.class.getName(), InterruptedException.class.getName(), interruptedException);
                }
                return null;
            }

            protected void onPostExecute(Void void_) {
                loadCommandCallback.loadingSucceded(DummyTacticsExerciseService.this._rating);
            }
        }.execute((Object[])new Void[0]);
    }

    public void reset() {
        this.init();
    }

    @Override
    public Square showHintForPuzzle(TacticsExerciseUserSolution tacticsExerciseUserSolution) {
        return tacticsExerciseUserSolution.getHint();
    }

    @Override
    public TacticsExerciseSession startNewSession() {
        this._session = new TacticsExerciseSession();
        return this._session;
    }

}
