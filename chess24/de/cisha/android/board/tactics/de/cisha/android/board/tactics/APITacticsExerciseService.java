/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package de.cisha.android.board.tactics;

import de.cisha.android.board.service.ILoginService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.GeneralJSONAPICommandLoader;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.board.service.jsonparser.TacticsTrainerInfoParser;
import de.cisha.android.board.tactics.ITacticsExerciseService;
import de.cisha.android.board.tactics.model.TacticsClassicRatingParser;
import de.cisha.android.board.tactics.model.TacticsExerciseSessionInfo;
import de.cisha.android.board.tactics.model.TacticsExerciseSessionInfoParser;
import de.cisha.android.board.tactics.model.TacticsExerciseSolutionInfoParser;
import de.cisha.android.board.tactics.model.TacticsExercisesBundle;
import de.cisha.android.board.tactics.model.TacticsExercisesRequestParser;
import de.cisha.chess.model.CishaUUID;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.MoveContainer;
import de.cisha.chess.model.Rating;
import de.cisha.chess.model.Square;
import de.cisha.chess.model.exercise.TacticsExercise;
import de.cisha.chess.model.exercise.TacticsExerciseSession;
import de.cisha.chess.model.exercise.TacticsExerciseSolutionInfo;
import de.cisha.chess.model.exercise.TacticsExerciseUserSolution;
import de.cisha.chess.util.Logger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

public class APITacticsExerciseService
implements ITacticsExerciseService {
    private static final int EXERCISE_CACHE = 3;
    private static APITacticsExerciseService _instance;
    private CishaUUID _authTokenOfCurrentUser;
    private List<TacticsExercise> _cachedExercises;
    private boolean _failedLoadingTactics;
    private LoadCommandCallback<TacticsExerciseUserSolution> _getNextTacticCallback;
    private boolean _isLoadingTactics = false;
    private boolean _isLoadingUserSolutionInfo = false;
    private APIStatusCode _loadingTacticsErrorCode;
    private TacticsExerciseSolutionInfo _nextInfo;
    private TacticsExerciseSession _session;

    private APITacticsExerciseService() {
        this.checkAuthToken();
    }

    private void checkAuthToken() {
        CishaUUID cishaUUID = ServiceProvider.getInstance().getLoginService().getAuthToken();
        if (this._authTokenOfCurrentUser == null || !this._authTokenOfCurrentUser.equals(cishaUUID)) {
            this._authTokenOfCurrentUser = cishaUUID;
            if (this._getNextTacticCallback != null) {
                this._getNextTacticCallback.loadingFailed(APIStatusCode.INTERNAL_NOT_LOGGED_IN, "login changed", null, null);
            }
            this._getNextTacticCallback = null;
            this._cachedExercises = new ArrayList<TacticsExercise>(3);
            this._session = null;
        }
    }

    private TacticsExercise getAndRemoveCachedExercise() {
        TacticsExercise tacticsExercise;
        if (this._cachedExercises.size() > 0) {
            tacticsExercise = this._cachedExercises.get(0);
            this._cachedExercises.remove(0);
        } else {
            tacticsExercise = null;
        }
        if (this._cachedExercises.size() == 0) {
            this.loadTactics(false);
        }
        return tacticsExercise;
    }

    public static APITacticsExerciseService getInstance() {
        synchronized (APITacticsExerciseService.class) {
            if (_instance == null) {
                _instance = new APITacticsExerciseService();
            }
            APITacticsExerciseService aPITacticsExerciseService = _instance;
            return aPITacticsExerciseService;
        }
    }

    private void handleGetNextTacticsCallback() {
        if (this._getNextTacticCallback != null) {
            if (!this._isLoadingTactics && this._cachedExercises.size() == 0) {
                this.loadTactics(false);
                return;
            }
            if (!this._isLoadingUserSolutionInfo && this._nextInfo == null) {
                this.loadUserSolutionInfo();
            }
            if (!this._isLoadingTactics && !this._isLoadingUserSolutionInfo) {
                Object object = this.getAndRemoveCachedExercise();
                if (object != null && this._nextInfo != null && object.getExerciseId() == this._nextInfo.getExerciseId()) {
                    object = new TacticsExerciseUserSolution((TacticsExercise)object);
                    this._session.addExercise((TacticsExerciseUserSolution)object);
                    object.setInfo(this._nextInfo);
                    this._getNextTacticCallback.loadingSucceded((TacticsExerciseUserSolution)object);
                    this._nextInfo = null;
                } else {
                    this._getNextTacticCallback.loadingFailed(APIStatusCode.INTERNAL_UNKNOWN, "no Exercise or no solution Info", null, null);
                }
                this._getNextTacticCallback = null;
            }
        }
    }

    private void loadTactics(boolean bl) {
        if (!this._isLoadingTactics) {
            this._isLoadingTactics = true;
            GeneralJSONAPICommandLoader generalJSONAPICommandLoader = new GeneralJSONAPICommandLoader();
            HashMap<String, String> hashMap = new HashMap<String, String>();
            this._isLoadingTactics = true;
            hashMap.put("type", "classic");
            hashMap.put("count", "3");
            if (!bl && this._session != null && this._session.getSessionId().isVerified()) {
                hashMap.put("session", this._session.getSessionId().getUuid());
            } else {
                this._session = new TacticsExerciseSession();
                this._nextInfo = null;
                this._cachedExercises = new ArrayList<TacticsExercise>(3);
                hashMap.put("reset", "1");
            }
            generalJSONAPICommandLoader.loadApiCommandGet(new LoadCommandCallbackWithTimeout<TacticsExercisesBundle>(20000){

                @Override
                public void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
                    APITacticsExerciseService.this._isLoadingTactics = false;
                    APITacticsExerciseService.this.loadingExercisesFinished(aPIStatusCode);
                }

                @Override
                public void succeded(TacticsExercisesBundle object) {
                    APITacticsExerciseService.this._cachedExercises.addAll(object.getExercises());
                    APITacticsExerciseService.this._session.setSessionId(object.getSessionId());
                    APITacticsExerciseService.this._isLoadingTactics = false;
                    if (object.hasExercises()) {
                        APITacticsExerciseService.this.loadingExercisesFinished(APIStatusCode.API_OK);
                        return;
                    }
                    TacticsExercisesBundle.NoMoreExercisesReason noMoreExercisesReason = object.getNoExercisesReason();
                    object = null;
                    switch (.$SwitchMap$de$cisha$android$board$tactics$model$TacticsExercisesBundle$NoMoreExercisesReason[noMoreExercisesReason.ordinal()]) {
                        default: {
                            break;
                        }
                        case 3: {
                            object = APIStatusCode.INTERNAL_TACTICTRAINER_GUEST_LIMIT_REACHED;
                            break;
                        }
                        case 2: {
                            object = APIStatusCode.INTERNAL_TACTICTRAINER_DAILY_LIMIT_REACHED;
                            break;
                        }
                        case 1: {
                            object = APIStatusCode.INTERNAL_TACTICTRAINER_NO_MORE_EXERCISES;
                        }
                    }
                    APITacticsExerciseService.this.loadingExercisesFinished((APIStatusCode)((Object)object));
                }
            }, "mobileAPI/GetTacticTrainerPuzzles", hashMap, new TacticsExercisesRequestParser(), true);
        }
    }

    private void loadUserSolutionInfo() {
        this._isLoadingUserSolutionInfo = true;
        if (this._cachedExercises.size() > 0) {
            TacticsExercise tacticsExercise = this._cachedExercises.get(0);
            HashMap<String, String> hashMap = new HashMap<String, String>(2);
            hashMap.put("type", "classic");
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("");
            stringBuilder.append(tacticsExercise.getExerciseId());
            hashMap.put("promise", stringBuilder.toString());
            hashMap.put("session", this._session.getSessionId().getUuid());
            new GeneralJSONAPICommandLoader().loadApiCommandGet(new LoadCommandCallbackWithTimeout<TacticsExerciseSolutionInfo>(20000){

                @Override
                public void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
                    APITacticsExerciseService.this.loadingUserSolutionInfoFinished(aPIStatusCode);
                    APITacticsExerciseService.this._isLoadingUserSolutionInfo = false;
                }

                @Override
                public void succeded(TacticsExerciseSolutionInfo tacticsExerciseSolutionInfo) {
                    APITacticsExerciseService.this._nextInfo = tacticsExerciseSolutionInfo;
                    APITacticsExerciseService.this.loadingUserSolutionInfoFinished(APIStatusCode.API_OK);
                    APITacticsExerciseService.this._isLoadingUserSolutionInfo = false;
                }
            }, "mobileAPI/GetTacticTrainerPromise", hashMap, new TacticsExerciseSolutionInfoParser(), true);
        }
    }

    private void loadingExercisesFinished(APIStatusCode aPIStatusCode) {
        if (aPIStatusCode != APIStatusCode.API_OK) {
            if (this._getNextTacticCallback != null) {
                this._getNextTacticCallback.loadingFailed(aPIStatusCode, "Failed To Load Tactics", null, null);
                this._getNextTacticCallback = null;
            } else {
                this._failedLoadingTactics = true;
                this._loadingTacticsErrorCode = aPIStatusCode;
            }
        }
        this._isLoadingTactics = false;
        this.handleGetNextTacticsCallback();
    }

    private void loadingUserSolutionInfoFinished(APIStatusCode aPIStatusCode) {
        if (aPIStatusCode != APIStatusCode.API_OK && this._getNextTacticCallback != null) {
            this._getNextTacticCallback.loadingFailed(aPIStatusCode, "Failed to load user promise", null, null);
            this._getNextTacticCallback = null;
        }
        this._isLoadingUserSolutionInfo = false;
        this.handleGetNextTacticsCallback();
    }

    private void setNextExerciseCallback(LoadCommandCallback<TacticsExerciseUserSolution> loadCommandCallback) {
        if (this._getNextTacticCallback != null) {
            this._getNextTacticCallback.loadingFailed(APIStatusCode.INTERNAL_TIMEOUT, "timeout - next request already cam in", null, null);
        }
        this._getNextTacticCallback = loadCommandCallback;
    }

    @Override
    public void exerciseSolved(TacticsExerciseUserSolution tacticsExerciseUserSolution, LoadCommandCallback<Rating> loadCommandCallback) {
        this.exerciseSolved(tacticsExerciseUserSolution, loadCommandCallback, true);
    }

    @Override
    public void exerciseSolved(TacticsExerciseUserSolution object, final LoadCommandCallback<Rating> loadCommandCallback, final boolean bl) {
        Object object2;
        HashMap<String, String> hashMap = new HashMap<String, String>(2);
        if (this._cachedExercises.size() > 0 && bl) {
            this._isLoadingUserSolutionInfo = true;
            object2 = this._cachedExercises.get(0);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("");
            stringBuilder.append(object2.getExerciseId());
            hashMap.put("promise", stringBuilder.toString());
        }
        object2 = new StringBuilder();
        object2.append("");
        object2.append(object.getExercise().getExerciseId());
        hashMap.put("puzzle", object2.toString());
        object2 = new StringBuilder();
        object2.append("");
        object2.append((float)object.getTimeUsed() / 1000.0f);
        hashMap.put("time", object2.toString());
        if (object.isCorrect()) {
            hashMap.put("result", "1");
        } else {
            hashMap.put("result", "0");
            object = object.getFailingMove();
            if (object != null && (object2 = object.getParent()) != null) {
                int n = object2.getMoveId();
                hashMap.put("failure", object.getSAN());
                object = new StringBuilder();
                object.append("");
                object.append(n);
                hashMap.put("failureat", object.toString());
            }
        }
        hashMap.put("type", "classic");
        hashMap.put("session", this._session.getSessionId().getUuid());
        new GeneralJSONAPICommandLoader().loadApiCommandPost(new LoadCommandCallbackWithTimeout<TacticsExerciseSolutionInfo>(20000){

            @Override
            public void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
                APITacticsExerciseService.this._isLoadingUserSolutionInfo = false;
                if (aPIStatusCode == APIStatusCode.INTERNAL_INCORRECT_SERVER_JSON_NO_DATA_OBJECT) {
                    APITacticsExerciseService.this.loadingUserSolutionInfoFinished(APIStatusCode.API_OK);
                    APITacticsExerciseService.this.getUserExerciseRating(loadCommandCallback);
                    return;
                }
                APITacticsExerciseService.this.loadingUserSolutionInfoFinished(aPIStatusCode);
                if (!bl) {
                    APITacticsExerciseService.this.getUserExerciseRating(loadCommandCallback);
                    return;
                }
                loadCommandCallback.loadingFailed(aPIStatusCode, string, null, null);
            }

            @Override
            public void succeded(TacticsExerciseSolutionInfo tacticsExerciseSolutionInfo) {
                APITacticsExerciseService.this._nextInfo = tacticsExerciseSolutionInfo;
                APITacticsExerciseService.this.loadingUserSolutionInfoFinished(APIStatusCode.API_OK);
                APITacticsExerciseService.this._isLoadingUserSolutionInfo = false;
                loadCommandCallback.loadingSucceded(tacticsExerciseSolutionInfo.getUserRatingNow());
            }
        }, "mobileAPI/SaveTacticTrainerReport", hashMap, new TacticsExerciseSolutionInfoParser(), true);
    }

    @Override
    public void getCurrentSession(final LoadCommandCallback<TacticsExerciseSession> loadCommandCallback) {
        GeneralJSONAPICommandLoader generalJSONAPICommandLoader = new GeneralJSONAPICommandLoader();
        HashMap<String, String> hashMap = new HashMap<String, String>();
        if (this._session != null) {
            hashMap.put("session", this._session.getSessionId().getUuid());
            generalJSONAPICommandLoader.loadApiCommandGet(new LoadCommandCallbackWithTimeout<TacticsExerciseSessionInfo>(20000){

                @Override
                public void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
                    if (APITacticsExerciseService.this._session != null) {
                        Logger.getInstance().error("APITacticsService", "failed to load session - using local one");
                        loadCommandCallback.loadingSucceded(APITacticsExerciseService.this._session);
                        return;
                    }
                    loadCommandCallback.loadingFailed(aPIStatusCode, string, list, null);
                }

                @Override
                public void succeded(TacticsExerciseSessionInfo tacticsExerciseSessionInfo) {
                    APITacticsExerciseService.this._session.setRatingPerformance(tacticsExerciseSessionInfo.getUserPerformance());
                    APITacticsExerciseService.this._session.setUserStartRating(tacticsExerciseSessionInfo.getUserStartRating());
                    APITacticsExerciseService.this._session.setUserEndRating(tacticsExerciseSessionInfo.getUserCurrentRating());
                    loadCommandCallback.loadingSucceded(APITacticsExerciseService.this._session);
                }
            }, "mobileAPI/GetTacticTrainerSummary", hashMap, new TacticsExerciseSessionInfoParser(), true);
            return;
        }
        loadCommandCallback.loadingFailed(APIStatusCode.API_ERROR_NOT_SET, "No active session!", null, null);
    }

    @Override
    public void getNextExercise(LoadCommandCallback<TacticsExerciseUserSolution> loadCommandCallback) {
        if (this._cachedExercises.size() > 0) {
            this.setNextExerciseCallback(loadCommandCallback);
            this.handleGetNextTacticsCallback();
            return;
        }
        if (this._isLoadingTactics) {
            this.setNextExerciseCallback(loadCommandCallback);
            return;
        }
        if (this._failedLoadingTactics) {
            loadCommandCallback.loadingFailed(this._loadingTacticsErrorCode, "Failed to load Tactics", null, null);
            this._failedLoadingTactics = false;
            return;
        }
        this.setNextExerciseCallback(loadCommandCallback);
        this.loadTactics(false);
    }

    @Override
    public void getTacticsTrainerInfo(LoadCommandCallback<ITacticsExerciseService.TacticsTrainerInfo> loadCommandCallback) {
        new GeneralJSONAPICommandLoader<ITacticsExerciseService.TacticsTrainerInfo>().loadApiCommandGet(loadCommandCallback, "mobileAPI/GetTacticTrainerInfo", null, new TacticsTrainerInfoParser(), true);
    }

    @Override
    public void getUserExerciseRating(LoadCommandCallback<Rating> loadCommandCallback) {
        HashMap<String, String> hashMap = new HashMap<String, String>(2);
        hashMap.put("type", "classic");
        new GeneralJSONAPICommandLoader<Rating>().loadApiCommandGet(loadCommandCallback, "mobileAPI/GetTacticTrainerRating", hashMap, new TacticsClassicRatingParser(), true);
    }

    @Override
    public Square showHintForPuzzle(TacticsExerciseUserSolution tacticsExerciseUserSolution) {
        return tacticsExerciseUserSolution.getHint();
    }

    @Override
    public TacticsExerciseSession startNewSession() {
        this.loadTactics(true);
        return this._session;
    }

}
