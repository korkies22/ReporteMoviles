// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service;

import org.json.JSONException;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWrapper;
import de.cisha.android.board.user.User;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import de.cisha.android.board.service.jsonparser.GeneralJSONAPICommandLoader;
import java.util.TreeMap;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import android.content.SharedPreferences.Editor;
import java.util.Iterator;
import android.content.SharedPreferences;
import org.json.JSONObject;
import java.util.List;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.chess.model.CishaUUID;
import java.util.Map;
import java.util.Collections;
import java.util.WeakHashMap;
import java.util.Set;
import android.content.Context;

public class LoginService implements ILoginService
{
    private static final String FACEBOOK_APP_ID = "381403181941949";
    private static final String[] FACEBOOK_PERMISSIONS;
    private static final String FACEBOOK_PERMISSION_EMAIL = "email";
    private static final String FACEBOOK_PERMISSION_LOCATION = "user_location";
    private static final String PREF_NAME = "userObject";
    private static final String PREF_STRING_AUTH_TOKEN = "authToken";
    private static final String PREF_STRING_LAST_GUEST_AUTH_TOKEN = "guestAuthToken";
    private static final String PREF_STRING_LAST_GUEST_UUIS = "guestUser_Uuid";
    private static final String PREF_STRING_UUID = "user_uid";
    private static final String PREF_TMP_UUID = "tmp_uuid";
    private static LoginService _instance;
    private final Context _context;
    private LoginInformation _loginInfo;
    private final Set<LoginObserver> _weakLoginObserver;
    
    static {
        FACEBOOK_PERMISSIONS = new String[] { "email", "user_location" };
    }
    
    private LoginService(Context context) {
        final Context applicationContext = context.getApplicationContext();
        if (applicationContext != null) {
            context = applicationContext;
        }
        this._context = context;
        this._weakLoginObserver = Collections.newSetFromMap(new WeakHashMap<LoginObserver, Boolean>());
        this._loginInfo = new LoginInformation();
        this.loadLoginInformation();
    }
    
    private boolean checkInternetAndDuuid(final CishaUUID cishaUUID, final LoadCommandCallback<UserLoginData> loadCommandCallback) {
        if (!ServiceProvider.getInstance().getInternetAvailabilityService().isNetworkAvailable()) {
            loadCommandCallback.loadingFailed(APIStatusCode.INTERNAL_TIMEOUT, "no network connection", null, null);
            return true;
        }
        if (!cishaUUID.isVerified()) {
            loadCommandCallback.loadingFailed(APIStatusCode.INTERNAL_UNKNOWN, "device DUUID not registered. try again.", null, null);
            return true;
        }
        return false;
    }
    
    public static LoginService getInstance(final Context context) {
        if (LoginService._instance == null) {
            LoginService._instance = new LoginService(context);
        }
        return LoginService._instance;
    }
    
    private void loadLoginInformation() {
        final SharedPreferences sharedPreferences = this._context.getSharedPreferences("userObject", 0);
        final String string = sharedPreferences.getString("user_uid", (String)null);
        this._loginInfo.resetUser();
        if (string != null) {
            this._loginInfo._uuid = new CishaUUID(string, true);
        }
        else {
            this._loginInfo._uuid = null;
        }
        final String string2 = sharedPreferences.getString("authToken", (String)null);
        if (string2 != null && !string2.equals("")) {
            this._loginInfo._authToken = new CishaUUID(string2, true);
        }
        else {
            this._loginInfo._authToken = null;
        }
        final String string3 = sharedPreferences.getString("guestAuthToken", (String)null);
        if (string3 != null && !string3.equals("")) {
            this._loginInfo._lastGuestAuthToken = new CishaUUID(string3, true);
            return;
        }
        this._loginInfo._lastGuestAuthToken = null;
    }
    
    private void notifyLoginObserver() {
        final Iterator<LoginObserver> iterator = this._weakLoginObserver.iterator();
        while (iterator.hasNext()) {
            iterator.next().loginStateChanged(this.getAuthToken() != null);
        }
    }
    
    private void saveLoginInformation() {
        final SharedPreferences.Editor edit = this._context.getSharedPreferences("userObject", 0).edit();
        if (this._loginInfo._uuid != null) {
            edit.putString("user_uid", this._loginInfo._uuid.getUuid());
        }
        else {
            edit.remove("user_uid");
        }
        if (this._loginInfo._authToken != null) {
            edit.putString("authToken", this._loginInfo._authToken.getUuid());
        }
        else {
            edit.remove("authToken");
        }
        if (this._loginInfo._lastGuestAuthToken != null) {
            edit.putString("guestAuthToken", this._loginInfo._lastGuestAuthToken.getUuid());
        }
        else {
            edit.remove("guestAuthToken");
        }
        edit.commit();
        ServiceProvider.getInstance().getProfileDataService().invalidateUserData();
        this.notifyLoginObserver();
    }
    
    @Override
    public void addLoginListener(final LoginObserver loginObserver) {
        this._weakLoginObserver.add(loginObserver);
    }
    
    @Override
    public void authenticateAsGuest(final LoadCommandCallback<UserLoginData> loadCommandCallback) {
        ServiceProvider.getInstance().getDUUIDService().getDuuid(new LoadCommandCallbackWithTimeout<CishaUUID>() {
            @Override
            protected void failed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                loadCommandCallback.loadingFailed(apiStatusCode, s, null, jsonObject);
            }
            
            @Override
            protected void succeded(final CishaUUID cishaUUID) {
                if (LoginService.this.checkInternetAndDuuid(cishaUUID, loadCommandCallback)) {
                    return;
                }
                if (LoginService.this._loginInfo._lastGuestAuthToken != null) {
                    LoginService.this._loginInfo._authToken = LoginService.this._loginInfo._lastGuestAuthToken;
                    LoginService.this._loginInfo._uuid = LoginService.this._loginInfo._lastGuestUuid;
                    LoginService.this.verifyAuthToken(new GetLoginDataCallback(loadCommandCallback, "Guest") {
                        @Override
                        protected void failed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                            LoginService.this._loginInfo.resetUser();
                            LoginService.this._loginInfo.resetGuest();
                            LoginService.this.saveLoginInformation();
                            LoginService.this.authenticateAsGuest(loadCommandCallback);
                        }
                    });
                    return;
                }
                final TreeMap<String, String> treeMap = new TreeMap<String, String>();
                treeMap.put("duuid", cishaUUID.getUuid());
                new GeneralJSONAPICommandLoader().loadApiCommandPost((LoadCommandCallback)new GetLoginDataCallback(loadCommandCallback, "Guest") {
                    @Override
                    protected void succeded(final UserLoginData userLoginData) {
                        LoginService.this._loginInfo._lastGuestAuthToken = userLoginData.getAuthToken();
                        LoginService.this._loginInfo._lastGuestUuid = userLoginData.getUserId();
                        LoginService.this.saveLoginInformation();
                        super.succeded(userLoginData);
                    }
                }, "mobileAPI/AuthenticateGuest", treeMap, (JSONAPIResultParser)new JSONUserLoginDataParser(), false);
            }
        });
        this.notifyLoginObserver();
    }
    
    @Override
    public void authenticateByFacebook(final String s, final LoadCommandCallback<UserLoginData> loadCommandCallback) {
        ServiceProvider.getInstance().getDUUIDService().getDuuid(new LoadCommandCallbackWithTimeout<CishaUUID>() {
            @Override
            protected void failed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                loadCommandCallback.loadingFailed(apiStatusCode, s, list, null);
            }
            
            @Override
            protected void succeded(final CishaUUID cishaUUID) {
                final TreeMap<String, String> treeMap = new TreeMap<String, String>();
                treeMap.put("duuid", cishaUUID.getUuid());
                treeMap.put("fbAuthToken", s);
                if (LoginService.this._loginInfo._lastGuestAuthToken != null) {
                    treeMap.put("authToken", LoginService.this._loginInfo._lastGuestAuthToken.getUuid());
                }
                new GeneralJSONAPICommandLoader().loadApiCommandPost((LoadCommandCallback)new GetLoginDataCallback(loadCommandCallback, "Facebook") {
                    @Override
                    protected void succeded(final UserLoginData userLoginData) {
                        LoginService.this._loginInfo.resetGuest();
                        super.succeded(userLoginData);
                    }
                }, "mobileAPI/AuthenticateByFacebook", treeMap, (JSONAPIResultParser)new JSONUserLoginDataParser(), false);
            }
        });
    }
    
    @Override
    public void authenticateByLogin(final String s, final String s2, final LoadCommandCallback<UserLoginData> loadCommandCallback) {
        if (s != null && s2 != null && loadCommandCallback != null) {
            ServiceProvider.getInstance().getDUUIDService().getDuuid(new LoadCommandCallbackWithTimeout<CishaUUID>() {
                @Override
                protected void failed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                    final LoadCommandCallback val.callback = loadCommandCallback;
                    final StringBuilder sb = new StringBuilder();
                    sb.append("Could not login by user name and password because DUUID could not be retrieved. Message was:");
                    sb.append(s);
                    val.callback.loadingFailed(apiStatusCode, sb.toString(), null, null);
                }
                
                @Override
                protected void succeded(final CishaUUID cishaUUID) {
                    if (LoginService.this.checkInternetAndDuuid(cishaUUID, loadCommandCallback)) {
                        return;
                    }
                    final GeneralJSONAPICommandLoader generalJSONAPICommandLoader = new GeneralJSONAPICommandLoader();
                    final TreeMap<String, String> treeMap = new TreeMap<String, String>();
                    treeMap.put("duuid", cishaUUID.getUuid());
                    treeMap.put("email", s);
                    treeMap.put("password", s2);
                    generalJSONAPICommandLoader.loadApiCommandPost(new GetLoginDataCallback(loadCommandCallback, "Credentials"), "mobileAPI/AuthenticateByLogin", treeMap, new JSONUserLoginDataParser(), false);
                }
            });
            return;
        }
        loadCommandCallback.loadingFailed(APIStatusCode.API_ERROR_MISSING_ARGUMENT, "one argument is null", null, null);
    }
    
    @Override
    public CishaUUID getAuthToken() {
        return this._loginInfo._authToken;
    }
    
    @Override
    public void getUserByID(final CishaUUID cishaUUID, final LoadCommandCallback<User> loadCommandCallback) {
        if (cishaUUID != null && loadCommandCallback != null) {
            final TreeMap<String, String> treeMap = new TreeMap<String, String>();
            treeMap.put("uuid", cishaUUID.getUuid());
            new GeneralJSONAPICommandLoader<User>().loadApiCommandGet(loadCommandCallback, "mobileAPI/GetUserData", treeMap, new JSONUserParser(), true);
            return;
        }
        throw new IllegalArgumentException("one parameter is null");
    }
    
    @Override
    public String getUserPrefix() {
        if (this._loginInfo._uuid != null) {
            return this._loginInfo._uuid.getUuid();
        }
        return "";
    }
    
    @Override
    public CishaUUID getUserUUID() {
        return this._loginInfo._uuid;
    }
    
    @Override
    public boolean isLoggedIn() {
        return this._loginInfo._authToken != null && this._loginInfo._authToken.getUuid() != null;
    }
    
    @Override
    public void logOut(final LogoutCallback logoutCallback) {
        if (logoutCallback == null) {
            throw new NullPointerException("LogoutCallback is null");
        }
        if (this._loginInfo._authToken == null) {
            logoutCallback.logoutFailed("not authtoken available. try to login!");
            return;
        }
        if (ServiceProvider.getInstance().getMembershipService().getMembershipLevel() == IMembershipService.MembershipLevel.MembershipLevelGuest) {
            logoutCallback.logoutSucceeded();
        }
        else {
            new GeneralJSONAPICommandLoader().loadApiCommandPost((LoadCommandCallback)new LoadCommandCallback<Void>() {
                @Override
                public void loadingCancelled() {
                    logoutCallback.logoutFailed("logout was cancelled");
                }
                
                @Override
                public void loadingFailed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                    logoutCallback.logoutFailed(s);
                }
                
                @Override
                public void loadingSucceded(final Void void1) {
                    logoutCallback.logoutSucceeded();
                }
            }, "mobileAPI/AuthenticateLogout", null, null, true);
        }
        this._loginInfo.resetUser();
        this.notifyLoginObserver();
        this.saveLoginInformation();
    }
    
    @Override
    public void lostPassword(final String s, final LoadCommandCallback<Boolean> loadCommandCallback) {
        if (!ServiceProvider.getInstance().getInternetAvailabilityService().isNetworkAvailable()) {
            loadCommandCallback.loadingFailed(APIStatusCode.INTERNAL_TIMEOUT, "no network connection availabe", null, null);
            return;
        }
        ServiceProvider.getInstance().getDUUIDService().getDuuid(new LoadCommandCallbackWithTimeout<CishaUUID>() {
            @Override
            protected void failed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                loadCommandCallback.loadingFailed(apiStatusCode, s, list, null);
            }
            
            @Override
            protected void succeded(final CishaUUID cishaUUID) {
                final GeneralJSONAPICommandLoader generalJSONAPICommandLoader = new GeneralJSONAPICommandLoader();
                final TreeMap<String, String> treeMap = new TreeMap<String, String>();
                treeMap.put("duuid", cishaUUID.getUuid());
                treeMap.put("email", s);
                generalJSONAPICommandLoader.loadApiCommandPost(loadCommandCallback, "mobileAPI/lostPassword", treeMap, null, false);
            }
        });
    }
    
    @Override
    public void register(final String s, final String s2, final String s3, final LoadCommandCallback<Boolean> loadCommandCallback) {
        if (!ServiceProvider.getInstance().getInternetAvailabilityService().isNetworkAvailable()) {
            loadCommandCallback.loadingFailed(APIStatusCode.INTERNAL_TIMEOUT, "no network connection available", null, null);
            return;
        }
        final GeneralJSONAPICommandLoader generalJSONAPICommandLoader = new GeneralJSONAPICommandLoader();
        final TreeMap<String, String> treeMap = new TreeMap<String, String>();
        treeMap.put("nick", s);
        treeMap.put("email", s2);
        treeMap.put("password", s3);
        if (this._loginInfo._lastGuestAuthToken != null) {
            treeMap.put("authToken", this._loginInfo._lastGuestAuthToken.getUuid());
        }
        ServiceProvider.getInstance().getDUUIDService().getDuuid(new LoadCommandCallbackWithTimeout<CishaUUID>() {
            final /* synthetic */ LoadCommandCallbackWrapper val.callbackWrapper = new LoadCommandCallbackWrapper<Boolean>(this, loadCommandCallback) {
                @Override
                protected void failed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                    super.failed(apiStatusCode, s, list, jsonObject);
                    ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.USER, "User registered", "error");
                }
                
                @Override
                protected void succeded(final Boolean b) {
                    super.succeded(b);
                    ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.USER, "User registered", "success");
                    LoginService.this._loginInfo.resetGuest();
                }
            };
            
            @Override
            protected void failed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                this.val.callbackWrapper.loadingFailed(apiStatusCode, s, list, null);
            }
            
            @Override
            protected void succeded(final CishaUUID cishaUUID) {
                treeMap.put("duuid", cishaUUID.getUuid());
                generalJSONAPICommandLoader.loadApiCommandPost(this.val.callbackWrapper, "mobileAPI/register", treeMap, null, false);
            }
        });
    }
    
    @Override
    public void verifyAuthToken(final LoadCommandCallback<UserLoginData> loadCommandCallback) {
        if (loadCommandCallback == null) {
            throw new NullPointerException("one argument is null");
        }
        if (this._loginInfo._authToken == null) {
            loadCommandCallback.loadingFailed(APIStatusCode.INTERNAL_NOT_LOGGED_IN, "no authtoken. login first!", null, null);
            return;
        }
        new GeneralJSONAPICommandLoader().loadApiCommandPost((LoadCommandCallback)new LoadCommandCallbackWithTimeout<Boolean>() {
            @Override
            protected void failed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                loadCommandCallback.loadingFailed(apiStatusCode, s, null, null);
            }
            
            @Override
            protected void succeded(final Boolean b) {
                if (b) {
                    loadCommandCallback.loadingSucceded(new UserLoginData(LoginService.this.getAuthToken(), LoginService.this.getUserUUID()));
                    ServiceProvider.getInstance().getTrackingService().trackUserLogin(LoginService.this._loginInfo._uuid, "Authtoken");
                    return;
                }
                loadCommandCallback.loadingFailed(APIStatusCode.API_ERROR_INVALID_AUTHTOKEN, "login not valid anymore", null, null);
            }
        }, "mobileAPI/VerifyAuthToken", null, (JSONAPIResultParser)new JSONAPIResultParser<Boolean>() {
            @Override
            public Boolean parseResult(final JSONObject jsonObject) throws InvalidJsonForObjectException {
                final int optInt = jsonObject.optInt("valid", -1);
                boolean b = true;
                if (optInt != 1) {
                    b = false;
                }
                return b;
            }
        }, true);
    }
    
    private class GetLoginDataCallback extends LoadCommandCallbackWithTimeout<UserLoginData>
    {
        private LoadCommandCallback<UserLoginData> _delegateCallback;
        private String _optionalTrackingLabel;
        
        public GetLoginDataCallback(final LoadCommandCallback<UserLoginData> delegateCallback, final String optionalTrackingLabel) {
            this._delegateCallback = delegateCallback;
            this._optionalTrackingLabel = optionalTrackingLabel;
        }
        
        @Override
        protected void failed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
            LoginService.this._loginInfo._authToken = null;
            this._delegateCallback.loadingFailed(apiStatusCode, s, list, null);
            LoginService.this.saveLoginInformation();
            LoginService.this.notifyLoginObserver();
        }
        
        @Override
        protected void succeded(final UserLoginData userLoginData) {
            LoginService.this._loginInfo.resetUser();
            LoginService.this._loginInfo._authToken = userLoginData.getAuthToken();
            LoginService.this._loginInfo._uuid = userLoginData.getUserId();
            LoginService.this.saveLoginInformation();
            ServiceProvider.getInstance().getProfileDataService().getUserData(new LoadCommandCallbackWithTimeout<User>() {
                @Override
                protected void failed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                    final LoadCommandCallback access.700 = GetLoginDataCallback.this._delegateCallback;
                    final StringBuilder sb = new StringBuilder();
                    sb.append("Loading own userData failed with message:");
                    sb.append(s);
                    access.700.loadingFailed(apiStatusCode, sb.toString(), list, jsonObject);
                }
                
                @Override
                protected void succeded(final User user) {
                    GetLoginDataCallback.this._delegateCallback.loadingSucceded(userLoginData);
                    ServiceProvider.getInstance().getTrackingService().trackUserLogin(userLoginData.getUserId(), GetLoginDataCallback.this._optionalTrackingLabel);
                }
            });
        }
    }
    
    private static class LoginInformation
    {
        private CishaUUID _authToken;
        private CishaUUID _lastGuestAuthToken;
        private CishaUUID _lastGuestUuid;
        private CishaUUID _uuid;
        
        public LoginInformation() {
            this.resetUser();
        }
        
        public void resetGuest() {
            this._lastGuestAuthToken = null;
            this._lastGuestUuid = null;
        }
        
        public void resetUser() {
            this._uuid = null;
            this._authToken = null;
        }
    }
}
