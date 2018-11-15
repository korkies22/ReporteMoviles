/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package de.cisha.android.board.service;

import android.content.Context;
import android.content.SharedPreferences;
import de.cisha.android.board.service.IDUUIDService;
import de.cisha.android.board.service.IInternetAvailabilityService;
import de.cisha.android.board.service.ILoginService;
import de.cisha.android.board.service.IMembershipService;
import de.cisha.android.board.service.IProfileDataService;
import de.cisha.android.board.service.ITrackingService;
import de.cisha.android.board.service.JSONUserLoginDataParser;
import de.cisha.android.board.service.JSONUserParser;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.UserLoginData;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.GeneralJSONAPICommandLoader;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWrapper;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.board.user.User;
import de.cisha.chess.model.CishaUUID;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.WeakHashMap;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginService
implements ILoginService {
    private static final String FACEBOOK_APP_ID = "381403181941949";
    private static final String[] FACEBOOK_PERMISSIONS = new String[]{"email", "user_location"};
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
    private final Set<ILoginService.LoginObserver> _weakLoginObserver;

    private LoginService(Context context) {
        Context context2 = context.getApplicationContext();
        if (context2 != null) {
            context = context2;
        }
        this._context = context;
        this._weakLoginObserver = Collections.newSetFromMap(new WeakHashMap());
        this._loginInfo = new LoginInformation();
        this.loadLoginInformation();
    }

    private boolean checkInternetAndDuuid(CishaUUID cishaUUID, LoadCommandCallback<UserLoginData> loadCommandCallback) {
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

    public static LoginService getInstance(Context context) {
        if (_instance == null) {
            _instance = new LoginService(context);
        }
        return _instance;
    }

    private void loadLoginInformation() {
        Object object = this._context.getSharedPreferences(PREF_NAME, 0);
        String string = object.getString(PREF_STRING_UUID, null);
        this._loginInfo.resetUser();
        if (string != null) {
            this._loginInfo._uuid = new CishaUUID(string, true);
        } else {
            this._loginInfo._uuid = null;
        }
        string = object.getString(PREF_STRING_AUTH_TOKEN, null);
        if (string != null && !string.equals("")) {
            this._loginInfo._authToken = new CishaUUID(string, true);
        } else {
            this._loginInfo._authToken = null;
        }
        object = object.getString(PREF_STRING_LAST_GUEST_AUTH_TOKEN, null);
        if (object != null && !object.equals("")) {
            this._loginInfo._lastGuestAuthToken = new CishaUUID((String)object, true);
            return;
        }
        this._loginInfo._lastGuestAuthToken = null;
    }

    private void notifyLoginObserver() {
        for (ILoginService.LoginObserver loginObserver : this._weakLoginObserver) {
            boolean bl = this.getAuthToken() != null;
            loginObserver.loginStateChanged(bl);
        }
    }

    private void saveLoginInformation() {
        SharedPreferences.Editor editor = this._context.getSharedPreferences(PREF_NAME, 0).edit();
        if (this._loginInfo._uuid != null) {
            editor.putString(PREF_STRING_UUID, this._loginInfo._uuid.getUuid());
        } else {
            editor.remove(PREF_STRING_UUID);
        }
        if (this._loginInfo._authToken != null) {
            editor.putString(PREF_STRING_AUTH_TOKEN, this._loginInfo._authToken.getUuid());
        } else {
            editor.remove(PREF_STRING_AUTH_TOKEN);
        }
        if (this._loginInfo._lastGuestAuthToken != null) {
            editor.putString(PREF_STRING_LAST_GUEST_AUTH_TOKEN, this._loginInfo._lastGuestAuthToken.getUuid());
        } else {
            editor.remove(PREF_STRING_LAST_GUEST_AUTH_TOKEN);
        }
        editor.commit();
        ServiceProvider.getInstance().getProfileDataService().invalidateUserData();
        this.notifyLoginObserver();
    }

    @Override
    public void addLoginListener(ILoginService.LoginObserver loginObserver) {
        this._weakLoginObserver.add(loginObserver);
    }

    @Override
    public void authenticateAsGuest(final LoadCommandCallback<UserLoginData> loadCommandCallback) {
        ServiceProvider.getInstance().getDUUIDService().getDuuid((LoadCommandCallback<CishaUUID>)new LoadCommandCallbackWithTimeout<CishaUUID>(){

            @Override
            protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
                loadCommandCallback.loadingFailed(aPIStatusCode, string, null, jSONObject);
            }

            @Override
            protected void succeded(CishaUUID object) {
                if (LoginService.this.checkInternetAndDuuid((CishaUUID)object, loadCommandCallback)) {
                    return;
                }
                if (LoginService.this._loginInfo._lastGuestAuthToken != null) {
                    LoginService.this._loginInfo._authToken = LoginService.this._loginInfo._lastGuestAuthToken;
                    LoginService.this._loginInfo._uuid = LoginService.this._loginInfo._lastGuestUuid;
                    LoginService.this.verifyAuthToken(new GetLoginDataCallback(loadCommandCallback, "Guest"){

                        @Override
                        protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
                            LoginService.this._loginInfo.resetUser();
                            LoginService.this._loginInfo.resetGuest();
                            LoginService.this.saveLoginInformation();
                            LoginService.this.authenticateAsGuest(loadCommandCallback);
                        }
                    });
                    return;
                }
                TreeMap<String, String> treeMap = new TreeMap<String, String>();
                treeMap.put("duuid", object.getUuid());
                object = new GetLoginDataCallback(loadCommandCallback, "Guest"){

                    @Override
                    protected void succeded(UserLoginData userLoginData) {
                        LoginService.this._loginInfo._lastGuestAuthToken = userLoginData.getAuthToken();
                        LoginService.this._loginInfo._lastGuestUuid = userLoginData.getUserId();
                        LoginService.this.saveLoginInformation();
                        super.succeded(userLoginData);
                    }
                };
                new GeneralJSONAPICommandLoader().loadApiCommandPost(object, "mobileAPI/AuthenticateGuest", (Map<String, String>)treeMap, new JSONUserLoginDataParser(), false);
            }

        });
        this.notifyLoginObserver();
    }

    @Override
    public void authenticateByFacebook(final String string, final LoadCommandCallback<UserLoginData> loadCommandCallback) {
        ServiceProvider.getInstance().getDUUIDService().getDuuid((LoadCommandCallback<CishaUUID>)new LoadCommandCallbackWithTimeout<CishaUUID>(){

            @Override
            protected void failed(APIStatusCode aPIStatusCode, String string2, List<LoadFieldError> list, JSONObject jSONObject) {
                loadCommandCallback.loadingFailed(aPIStatusCode, string2, list, null);
            }

            @Override
            protected void succeded(CishaUUID object) {
                TreeMap<String, String> treeMap = new TreeMap<String, String>();
                treeMap.put("duuid", object.getUuid());
                treeMap.put("fbAuthToken", string);
                if (LoginService.this._loginInfo._lastGuestAuthToken != null) {
                    treeMap.put(LoginService.PREF_STRING_AUTH_TOKEN, LoginService.this._loginInfo._lastGuestAuthToken.getUuid());
                }
                object = new GetLoginDataCallback(loadCommandCallback, "Facebook"){

                    @Override
                    protected void succeded(UserLoginData userLoginData) {
                        LoginService.this._loginInfo.resetGuest();
                        super.succeded(userLoginData);
                    }
                };
                new GeneralJSONAPICommandLoader().loadApiCommandPost(object, "mobileAPI/AuthenticateByFacebook", (Map<String, String>)treeMap, new JSONUserLoginDataParser(), false);
            }

        });
    }

    @Override
    public void authenticateByLogin(final String string, final String string2, final LoadCommandCallback<UserLoginData> loadCommandCallback) {
        if (string != null && string2 != null && loadCommandCallback != null) {
            ServiceProvider.getInstance().getDUUIDService().getDuuid((LoadCommandCallback<CishaUUID>)new LoadCommandCallbackWithTimeout<CishaUUID>(){

                @Override
                protected void failed(APIStatusCode aPIStatusCode, String string3, List<LoadFieldError> object, JSONObject object2) {
                    object = loadCommandCallback;
                    object2 = new StringBuilder();
                    object2.append("Could not login by user name and password because DUUID could not be retrieved. Message was:");
                    object2.append(string3);
                    object.loadingFailed(aPIStatusCode, object2.toString(), null, null);
                }

                @Override
                protected void succeded(CishaUUID cishaUUID) {
                    if (LoginService.this.checkInternetAndDuuid(cishaUUID, loadCommandCallback)) {
                        return;
                    }
                    GeneralJSONAPICommandLoader generalJSONAPICommandLoader = new GeneralJSONAPICommandLoader();
                    TreeMap<String, String> treeMap = new TreeMap<String, String>();
                    treeMap.put("duuid", cishaUUID.getUuid());
                    treeMap.put(LoginService.FACEBOOK_PERMISSION_EMAIL, string);
                    treeMap.put("password", string2);
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
    public void getUserByID(CishaUUID cishaUUID, LoadCommandCallback<User> loadCommandCallback) {
        if (cishaUUID != null && loadCommandCallback != null) {
            TreeMap<String, String> treeMap = new TreeMap<String, String>();
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
        if (this._loginInfo._authToken != null && this._loginInfo._authToken.getUuid() != null) {
            return true;
        }
        return false;
    }

    @Override
    public void logOut(final ILoginService.LogoutCallback logoutCallback) {
        if (logoutCallback == null) {
            throw new NullPointerException("LogoutCallback is null");
        }
        if (this._loginInfo._authToken == null) {
            logoutCallback.logoutFailed("not authtoken available. try to login!");
            return;
        }
        if (ServiceProvider.getInstance().getMembershipService().getMembershipLevel() == IMembershipService.MembershipLevel.MembershipLevelGuest) {
            logoutCallback.logoutSucceeded();
        } else {
            new GeneralJSONAPICommandLoader().loadApiCommandPost(new LoadCommandCallback<Void>(){

                @Override
                public void loadingCancelled() {
                    logoutCallback.logoutFailed("logout was cancelled");
                }

                @Override
                public void loadingFailed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
                    logoutCallback.logoutFailed(string);
                }

                @Override
                public void loadingSucceded(Void void_) {
                    logoutCallback.logoutSucceeded();
                }
            }, "mobileAPI/AuthenticateLogout", null, null, true);
        }
        this._loginInfo.resetUser();
        this.notifyLoginObserver();
        this.saveLoginInformation();
    }

    @Override
    public void lostPassword(final String string, final LoadCommandCallback<Boolean> loadCommandCallback) {
        if (!ServiceProvider.getInstance().getInternetAvailabilityService().isNetworkAvailable()) {
            loadCommandCallback.loadingFailed(APIStatusCode.INTERNAL_TIMEOUT, "no network connection availabe", null, null);
            return;
        }
        ServiceProvider.getInstance().getDUUIDService().getDuuid((LoadCommandCallback<CishaUUID>)new LoadCommandCallbackWithTimeout<CishaUUID>(){

            @Override
            protected void failed(APIStatusCode aPIStatusCode, String string2, List<LoadFieldError> list, JSONObject jSONObject) {
                loadCommandCallback.loadingFailed(aPIStatusCode, string2, list, null);
            }

            @Override
            protected void succeded(CishaUUID cishaUUID) {
                GeneralJSONAPICommandLoader generalJSONAPICommandLoader = new GeneralJSONAPICommandLoader();
                TreeMap<String, String> treeMap = new TreeMap<String, String>();
                treeMap.put("duuid", cishaUUID.getUuid());
                treeMap.put(LoginService.FACEBOOK_PERMISSION_EMAIL, string);
                generalJSONAPICommandLoader.loadApiCommandPost(loadCommandCallback, "mobileAPI/lostPassword", treeMap, null, false);
            }
        });
    }

    @Override
    public void register(String object, String string, String string2, LoadCommandCallback<Boolean> loadCommandCallback) {
        if (!ServiceProvider.getInstance().getInternetAvailabilityService().isNetworkAvailable()) {
            loadCommandCallback.loadingFailed(APIStatusCode.INTERNAL_TIMEOUT, "no network connection available", null, null);
            return;
        }
        final GeneralJSONAPICommandLoader generalJSONAPICommandLoader = new GeneralJSONAPICommandLoader();
        final TreeMap<String, String> treeMap = new TreeMap<String, String>();
        treeMap.put("nick", (String)object);
        treeMap.put(FACEBOOK_PERMISSION_EMAIL, string);
        treeMap.put("password", string2);
        if (this._loginInfo._lastGuestAuthToken != null) {
            treeMap.put(PREF_STRING_AUTH_TOKEN, this._loginInfo._lastGuestAuthToken.getUuid());
        }
        object = new LoadCommandCallbackWrapper<Boolean>(loadCommandCallback){

            @Override
            protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
                super.failed(aPIStatusCode, string, list, jSONObject);
                ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.USER, "User registered", "error");
            }

            @Override
            protected void succeded(Boolean bl) {
                super.succeded(bl);
                ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.USER, "User registered", "success");
                LoginService.this._loginInfo.resetGuest();
            }
        };
        ServiceProvider.getInstance().getDUUIDService().getDuuid((LoadCommandCallback<CishaUUID>)new LoadCommandCallbackWithTimeout<CishaUUID>((LoadCommandCallbackWrapper)object){
            final /* synthetic */ LoadCommandCallbackWrapper val$callbackWrapper;
            {
                this.val$callbackWrapper = loadCommandCallbackWrapper;
            }

            @Override
            protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
                this.val$callbackWrapper.loadingFailed(aPIStatusCode, string, list, null);
            }

            @Override
            protected void succeded(CishaUUID cishaUUID) {
                treeMap.put("duuid", cishaUUID.getUuid());
                generalJSONAPICommandLoader.loadApiCommandPost(this.val$callbackWrapper, "mobileAPI/register", treeMap, null, false);
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
        new GeneralJSONAPICommandLoader().loadApiCommandPost(new LoadCommandCallbackWithTimeout<Boolean>(){

            @Override
            protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
                loadCommandCallback.loadingFailed(aPIStatusCode, string, null, null);
            }

            @Override
            protected void succeded(Boolean bl) {
                if (bl.booleanValue()) {
                    loadCommandCallback.loadingSucceded(new UserLoginData(LoginService.this.getAuthToken(), LoginService.this.getUserUUID()));
                    ServiceProvider.getInstance().getTrackingService().trackUserLogin(LoginService.this._loginInfo._uuid, "Authtoken");
                    return;
                }
                loadCommandCallback.loadingFailed(APIStatusCode.API_ERROR_INVALID_AUTHTOKEN, "login not valid anymore", null, null);
            }
        }, "mobileAPI/VerifyAuthToken", null, new JSONAPIResultParser<Boolean>(){

            @Override
            public Boolean parseResult(JSONObject jSONObject) throws InvalidJsonForObjectException {
                int n = jSONObject.optInt("valid", -1);
                boolean bl = true;
                if (n != 1) {
                    bl = false;
                }
                return bl;
            }
        }, true);
    }

    private class GetLoginDataCallback
    extends LoadCommandCallbackWithTimeout<UserLoginData> {
        private LoadCommandCallback<UserLoginData> _delegateCallback;
        private String _optionalTrackingLabel;

        public GetLoginDataCallback(LoadCommandCallback<UserLoginData> loadCommandCallback, String string) {
            this._delegateCallback = loadCommandCallback;
            this._optionalTrackingLabel = string;
        }

        @Override
        protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
            LoginService.this._loginInfo._authToken = null;
            this._delegateCallback.loadingFailed(aPIStatusCode, string, list, null);
            LoginService.this.saveLoginInformation();
            LoginService.this.notifyLoginObserver();
        }

        @Override
        protected void succeded(final UserLoginData userLoginData) {
            LoginService.this._loginInfo.resetUser();
            LoginService.this._loginInfo._authToken = userLoginData.getAuthToken();
            LoginService.this._loginInfo._uuid = userLoginData.getUserId();
            LoginService.this.saveLoginInformation();
            ServiceProvider.getInstance().getProfileDataService().getUserData((LoadCommandCallback<User>)new LoadCommandCallbackWithTimeout<User>(){

                @Override
                protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
                    LoadCommandCallback loadCommandCallback = GetLoginDataCallback.this._delegateCallback;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Loading own userData failed with message:");
                    stringBuilder.append(string);
                    loadCommandCallback.loadingFailed(aPIStatusCode, stringBuilder.toString(), list, jSONObject);
                }

                @Override
                protected void succeded(User user) {
                    GetLoginDataCallback.this._delegateCallback.loadingSucceded(userLoginData);
                    ServiceProvider.getInstance().getTrackingService().trackUserLogin(userLoginData.getUserId(), GetLoginDataCallback.this._optionalTrackingLabel);
                }
            });
        }

    }

    private static class LoginInformation {
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
