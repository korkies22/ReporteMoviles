// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.tactics;

import de.cisha.chess.model.Square;
import de.cisha.android.board.tactics.model.TacticsClassicRatingParser;
import de.cisha.android.board.service.jsonparser.TacticsTrainerInfoParser;
import de.cisha.android.board.tactics.model.TacticsExerciseSessionInfoParser;
import de.cisha.chess.util.Logger;
import de.cisha.android.board.tactics.model.TacticsExerciseSessionInfo;
import de.cisha.chess.model.MoveContainer;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.Rating;
import de.cisha.android.board.tactics.model.TacticsExerciseSolutionInfoParser;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import java.util.Map;
import de.cisha.android.board.tactics.model.TacticsExercisesRequestParser;
import java.util.Collection;
import de.cisha.android.board.tactics.model.TacticsExercisesBundle;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import java.util.HashMap;
import de.cisha.android.board.service.jsonparser.GeneralJSONAPICommandLoader;
import java.util.ArrayList;
import org.json.JSONObject;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.chess.model.exercise.TacticsExerciseSession;
import de.cisha.chess.model.exercise.TacticsExerciseSolutionInfo;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.chess.model.exercise.TacticsExerciseUserSolution;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.chess.model.exercise.TacticsExercise;
import java.util.List;
import de.cisha.chess.model.CishaUUID;

public class APITacticsExerciseService implements ITacticsExerciseService
{
    private static final int EXERCISE_CACHE = 3;
    private static APITacticsExerciseService _instance;
    private CishaUUID _authTokenOfCurrentUser;
    private List<TacticsExercise> _cachedExercises;
    private boolean _failedLoadingTactics;
    private LoadCommandCallback<TacticsExerciseUserSolution> _getNextTacticCallback;
    private boolean _isLoadingTactics;
    private boolean _isLoadingUserSolutionInfo;
    private APIStatusCode _loadingTacticsErrorCode;
    private TacticsExerciseSolutionInfo _nextInfo;
    private TacticsExerciseSession _session;
    
    private APITacticsExerciseService() {
        this._isLoadingTactics = false;
        this._isLoadingUserSolutionInfo = false;
        this.checkAuthToken();
    }
    
    private void checkAuthToken() {
        final CishaUUID authToken = ServiceProvider.getInstance().getLoginService().getAuthToken();
        if (this._authTokenOfCurrentUser == null || !this._authTokenOfCurrentUser.equals(authToken)) {
            this._authTokenOfCurrentUser = authToken;
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
        }
        else {
            tacticsExercise = null;
        }
        if (this._cachedExercises.size() == 0) {
            this.loadTactics(false);
        }
        return tacticsExercise;
    }
    
    public static APITacticsExerciseService getInstance() {
        synchronized (APITacticsExerciseService.class) {
            if (APITacticsExerciseService._instance == null) {
                APITacticsExerciseService._instance = new APITacticsExerciseService();
            }
            return APITacticsExerciseService._instance;
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
                final TacticsExercise andRemoveCachedExercise = this.getAndRemoveCachedExercise();
                if (andRemoveCachedExercise != null && this._nextInfo != null && andRemoveCachedExercise.getExerciseId() == this._nextInfo.getExerciseId()) {
                    final TacticsExerciseUserSolution tacticsExerciseUserSolution = new TacticsExerciseUserSolution(andRemoveCachedExercise);
                    this._session.addExercise(tacticsExerciseUserSolution);
                    tacticsExerciseUserSolution.setInfo(this._nextInfo);
                    this._getNextTacticCallback.loadingSucceded(tacticsExerciseUserSolution);
                    this._nextInfo = null;
                }
                else {
                    this._getNextTacticCallback.loadingFailed(APIStatusCode.INTERNAL_UNKNOWN, "no Exercise or no solution Info", null, null);
                }
                this._getNextTacticCallback = null;
            }
        }
    }
    
    private void loadTactics(final boolean b) {
        if (!this._isLoadingTactics) {
            this._isLoadingTactics = true;
            final GeneralJSONAPICommandLoader generalJSONAPICommandLoader = new GeneralJSONAPICommandLoader();
            final HashMap<String, String> hashMap = new HashMap<String, String>();
            this._isLoadingTactics = true;
            hashMap.put("type", "classic");
            hashMap.put("count", "3");
            if (!b && this._session != null && this._session.getSessionId().isVerified()) {
                hashMap.put("session", this._session.getSessionId().getUuid());
            }
            else {
                this._session = new TacticsExerciseSession();
                this._nextInfo = null;
                this._cachedExercises = new ArrayList<TacticsExercise>(3);
                hashMap.put("reset", "1");
            }
            generalJSONAPICommandLoader.loadApiCommandGet(new LoadCommandCallbackWithTimeout<TacticsExercisesBundle>(20000) {
                public void failed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                    APITacticsExerciseService.this._isLoadingTactics = false;
                    APITacticsExerciseService.this.loadingExercisesFinished(apiStatusCode);
                }
                
                public void succeded(final TacticsExercisesBundle tacticsExercisesBundle) {
                    APITacticsExerciseService.this._cachedExercises.addAll(tacticsExercisesBundle.getExercises());
                    APITacticsExerciseService.this._session.setSessionId(tacticsExercisesBundle.getSessionId());
                    APITacticsExerciseService.this._isLoadingTactics = false;
                    if (tacticsExercisesBundle.hasExercises()) {
                        APITacticsExerciseService.this.loadingExercisesFinished(APIStatusCode.API_OK);
                        return;
                    }
                    final TacticsExercisesBundle.NoMoreExercisesReason noExercisesReason = tacticsExercisesBundle.getNoExercisesReason();
                    APIStatusCode apiStatusCode = null;
                    switch (APITacticsExerciseService.5..SwitchMap.de.cisha.android.board.tactics.model.TacticsExercisesBundle.NoMoreExercisesReason[noExercisesReason.ordinal()]) {
                        case 3: {
                            apiStatusCode = APIStatusCode.INTERNAL_TACTICTRAINER_GUEST_LIMIT_REACHED;
                            break;
                        }
                        case 2: {
                            apiStatusCode = APIStatusCode.INTERNAL_TACTICTRAINER_DAILY_LIMIT_REACHED;
                            break;
                        }
                        case 1: {
                            apiStatusCode = APIStatusCode.INTERNAL_TACTICTRAINER_NO_MORE_EXERCISES;
                            break;
                        }
                    }
                    APITacticsExerciseService.this.loadingExercisesFinished(apiStatusCode);
                }
            }, "mobileAPI/GetTacticTrainerPuzzles", hashMap, new TacticsExercisesRequestParser(), true);
        }
    }
    
    private void loadUserSolutionInfo() {
        this._isLoadingUserSolutionInfo = true;
        if (this._cachedExercises.size() > 0) {
            final TacticsExercise tacticsExercise = this._cachedExercises.get(0);
            final HashMap<String, String> hashMap = new HashMap<String, String>(2);
            hashMap.put("type", "classic");
            final StringBuilder sb = new StringBuilder();
            sb.append("");
            sb.append(tacticsExercise.getExerciseId());
            hashMap.put("promise", sb.toString());
            hashMap.put("session", this._session.getSessionId().getUuid());
            new GeneralJSONAPICommandLoader().loadApiCommandGet((LoadCommandCallback)new LoadCommandCallbackWithTimeout<TacticsExerciseSolutionInfo>(20000) {
                public void failed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                    APITacticsExerciseService.this.loadingUserSolutionInfoFinished(apiStatusCode);
                    APITacticsExerciseService.this._isLoadingUserSolutionInfo = false;
                }
                
                public void succeded(final TacticsExerciseSolutionInfo tacticsExerciseSolutionInfo) {
                    APITacticsExerciseService.this._nextInfo = tacticsExerciseSolutionInfo;
                    APITacticsExerciseService.this.loadingUserSolutionInfoFinished(APIStatusCode.API_OK);
                    APITacticsExerciseService.this._isLoadingUserSolutionInfo = false;
                }
            }, "mobileAPI/GetTacticTrainerPromise", hashMap, (JSONAPIResultParser)new TacticsExerciseSolutionInfoParser(), true);
        }
    }
    
    private void loadingExercisesFinished(final APIStatusCode loadingTacticsErrorCode) {
        if (loadingTacticsErrorCode != APIStatusCode.API_OK) {
            if (this._getNextTacticCallback != null) {
                this._getNextTacticCallback.loadingFailed(loadingTacticsErrorCode, "Failed To Load Tactics", null, null);
                this._getNextTacticCallback = null;
            }
            else {
                this._failedLoadingTactics = true;
                this._loadingTacticsErrorCode = loadingTacticsErrorCode;
            }
        }
        this._isLoadingTactics = false;
        this.handleGetNextTacticsCallback();
    }
    
    private void loadingUserSolutionInfoFinished(final APIStatusCode apiStatusCode) {
        if (apiStatusCode != APIStatusCode.API_OK && this._getNextTacticCallback != null) {
            this._getNextTacticCallback.loadingFailed(apiStatusCode, "Failed to load user promise", null, null);
            this._getNextTacticCallback = null;
        }
        this._isLoadingUserSolutionInfo = false;
        this.handleGetNextTacticsCallback();
    }
    
    private void setNextExerciseCallback(final LoadCommandCallback<TacticsExerciseUserSolution> getNextTacticCallback) {
        if (this._getNextTacticCallback != null) {
            this._getNextTacticCallback.loadingFailed(APIStatusCode.INTERNAL_TIMEOUT, "timeout - next request already cam in", null, null);
        }
        this._getNextTacticCallback = getNextTacticCallback;
    }
    
    @Override
    public void exerciseSolved(final TacticsExerciseUserSolution tacticsExerciseUserSolution, final LoadCommandCallback<Rating> loadCommandCallback) {
        this.exerciseSolved(tacticsExerciseUserSolution, loadCommandCallback, true);
    }
    
    @Override
    public void exerciseSolved(final TacticsExerciseUserSolution tacticsExerciseUserSolution, final LoadCommandCallback<Rating> loadCommandCallback, final boolean b) {
        final HashMap<String, String> hashMap = new HashMap<String, String>(2);
        if (this._cachedExercises.size() > 0 && b) {
            this._isLoadingUserSolutionInfo = true;
            final TacticsExercise tacticsExercise = this._cachedExercises.get(0);
            final StringBuilder sb = new StringBuilder();
            sb.append("");
            sb.append(tacticsExercise.getExerciseId());
            hashMap.put("promise", sb.toString());
        }
        final StringBuilder sb2 = new StringBuilder();
        sb2.append("");
        sb2.append(tacticsExerciseUserSolution.getExercise().getExerciseId());
        hashMap.put("puzzle", sb2.toString());
        final StringBuilder sb3 = new StringBuilder();
        sb3.append("");
        sb3.append(tacticsExerciseUserSolution.getTimeUsed() / 1000.0f);
        hashMap.put("time", sb3.toString());
        if (tacticsExerciseUserSolution.isCorrect()) {
            hashMap.put("result", "1");
        }
        else {
            hashMap.put("result", "0");
            final Move failingMove = tacticsExerciseUserSolution.getFailingMove();
            if (failingMove != null) {
                final MoveContainer parent = failingMove.getParent();
                if (parent != null) {
                    final int moveId = parent.getMoveId();
                    hashMap.put("failure", failingMove.getSAN());
                    final StringBuilder sb4 = new StringBuilder();
                    sb4.append("");
                    sb4.append(moveId);
                    hashMap.put("failureat", sb4.toString());
                }
            }
        }
        hashMap.put("type", "classic");
        hashMap.put("session", this._session.getSessionId().getUuid());
        new GeneralJSONAPICommandLoader().loadApiCommandPost((LoadCommandCallback)new LoadCommandCallbackWithTimeout<TacticsExerciseSolutionInfo>(20000) {
            public void failed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                APITacticsExerciseService.this._isLoadingUserSolutionInfo = false;
                if (apiStatusCode == APIStatusCode.INTERNAL_INCORRECT_SERVER_JSON_NO_DATA_OBJECT) {
                    APITacticsExerciseService.this.loadingUserSolutionInfoFinished(APIStatusCode.API_OK);
                    APITacticsExerciseService.this.getUserExerciseRating(loadCommandCallback);
                    return;
                }
                APITacticsExerciseService.this.loadingUserSolutionInfoFinished(apiStatusCode);
                if (!b) {
                    APITacticsExerciseService.this.getUserExerciseRating(loadCommandCallback);
                    return;
                }
                loadCommandCallback.loadingFailed(apiStatusCode, s, null, null);
            }
            
            public void succeded(final TacticsExerciseSolutionInfo tacticsExerciseSolutionInfo) {
                APITacticsExerciseService.this._nextInfo = tacticsExerciseSolutionInfo;
                APITacticsExerciseService.this.loadingUserSolutionInfoFinished(APIStatusCode.API_OK);
                APITacticsExerciseService.this._isLoadingUserSolutionInfo = false;
                loadCommandCallback.loadingSucceded(tacticsExerciseSolutionInfo.getUserRatingNow());
            }
        }, "mobileAPI/SaveTacticTrainerReport", hashMap, (JSONAPIResultParser)new TacticsExerciseSolutionInfoParser(), true);
    }
    
    @Override
    public void getCurrentSession(final LoadCommandCallback<TacticsExerciseSession> loadCommandCallback) {
        final GeneralJSONAPICommandLoader generalJSONAPICommandLoader = new GeneralJSONAPICommandLoader();
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        if (this._session != null) {
            hashMap.put("session", this._session.getSessionId().getUuid());
            generalJSONAPICommandLoader.loadApiCommandGet(new LoadCommandCallbackWithTimeout<TacticsExerciseSessionInfo>(20000) {
                public void failed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                    if (APITacticsExerciseService.this._session != null) {
                        Logger.getInstance().error("APITacticsService", "failed to load session - using local one");
                        loadCommandCallback.loadingSucceded(APITacticsExerciseService.this._session);
                        return;
                    }
                    loadCommandCallback.loadingFailed(apiStatusCode, s, list, null);
                }
                
                public void succeded(final TacticsExerciseSessionInfo tacticsExerciseSessionInfo) {
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
    public void getNextExercise(final LoadCommandCallback<TacticsExerciseUserSolution> nextExerciseCallback) {
        if (this._cachedExercises.size() > 0) {
            this.setNextExerciseCallback(nextExerciseCallback);
            this.handleGetNextTacticsCallback();
            return;
        }
        if (this._isLoadingTactics) {
            this.setNextExerciseCallback(nextExerciseCallback);
            return;
        }
        if (this._failedLoadingTactics) {
            nextExerciseCallback.loadingFailed(this._loadingTacticsErrorCode, "Failed to load Tactics", null, null);
            this._failedLoadingTactics = false;
            return;
        }
        this.setNextExerciseCallback(nextExerciseCallback);
        this.loadTactics(false);
    }
    
    @Override
    public void getTacticsTrainerInfo(final LoadCommandCallback<TacticsTrainerInfo> loadCommandCallback) {
        new GeneralJSONAPICommandLoader<TacticsTrainerInfo>().loadApiCommandGet(loadCommandCallback, "mobileAPI/GetTacticTrainerInfo", null, new TacticsTrainerInfoParser(), true);
    }
    
    @Override
    public void getUserExerciseRating(final LoadCommandCallback<Rating> loadCommandCallback) {
        final HashMap<String, String> hashMap = new HashMap<String, String>(2);
        hashMap.put("type", "classic");
        new GeneralJSONAPICommandLoader<Rating>().loadApiCommandGet(loadCommandCallback, "mobileAPI/GetTacticTrainerRating", hashMap, new TacticsClassicRatingParser(), true);
    }
    
    @Override
    public Square showHintForPuzzle(final TacticsExerciseUserSolution tacticsExerciseUserSolution) {
        return tacticsExerciseUserSolution.getHint();
    }
    
    @Override
    public TacticsExerciseSession startNewSession() {
        this.loadTactics(true);
        return this._session;
    }
}
