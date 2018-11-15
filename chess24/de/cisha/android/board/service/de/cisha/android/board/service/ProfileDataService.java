/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.content.res.Resources
 *  android.graphics.Bitmap
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package de.cisha.android.board.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import de.cisha.android.board.profile.model.DashboardData;
import de.cisha.android.board.profile.model.JSONDashboardDataParser;
import de.cisha.android.board.service.CouchImageCache;
import de.cisha.android.board.service.ILoginService;
import de.cisha.android.board.service.IProfileDataService;
import de.cisha.android.board.service.ITrackingService;
import de.cisha.android.board.service.JSONUploadProfileImageResponseParser;
import de.cisha.android.board.service.JSONUserParser;
import de.cisha.android.board.service.LoginService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.jsonmapping.JSONMappingUser;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.GeneralJSONAPICommandLoader;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWrapper;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.board.service.jsonparser.SetUserDataResponseParser;
import de.cisha.android.board.user.User;
import de.cisha.chess.model.CishaUUID;
import de.cisha.chess.util.BitmapHelper;
import de.cisha.chess.util.FileUploadInformationImpl;
import de.cisha.chess.util.HTTPHelper;
import de.cisha.chess.util.Logger;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.WeakHashMap;
import org.json.JSONException;
import org.json.JSONObject;

public class ProfileDataService
implements IProfileDataService {
    private static final String EDIT_PROFILE_IMAGE_TRACKING_ACTION = "Edit profile";
    private static final String ERROR = "error";
    private static final String PREF_NAME = "ProfileDataService";
    private static final String PREF_USER_JSON = "user_as_json";
    private static final String SUCCESS = "success";
    private static final String UPLOAD_PROFILE_IMAGE_TRACKING_ACTION = "Upload profile image";
    private static Context _context;
    private static ProfileDataService _instance;
    private DashboardData _dashBoardData;
    private JSONDashboardDataParser _dashboardDataParser = new JSONDashboardDataParser();
    private Set<IProfileDataService.IUserDataChangedListener> _listeners = Collections.newSetFromMap(new WeakHashMap());
    private User _user;

    private ProfileDataService() {
        this.loadUserFromPreferences();
    }

    public static ProfileDataService getInstance(Context context) {
        _context = context;
        if (_instance == null) {
            _instance = new ProfileDataService();
        }
        return _instance;
    }

    private SharedPreferences getSharedPreferences() {
        CharSequence charSequence = new StringBuilder();
        charSequence.append("PROFILE_DASHBOARD_DATA");
        charSequence.append(ServiceProvider.getInstance().getLoginService().getUserPrefix());
        charSequence = charSequence.toString();
        return _context.getSharedPreferences((String)charSequence, 0);
    }

    private DashboardData loadDashBoardDataFromPreferences(JSONAPIResultParser<DashboardData> object) {
        SharedPreferences sharedPreferences = this.getSharedPreferences();
        if (sharedPreferences.contains("jsonData")) {
            try {
                object = object.parseResult(new JSONObject(sharedPreferences.getString("jsonData", "")));
                return object;
            }
            catch (JSONException jSONException) {
                Logger.getInstance().debug(ProfileDataService.class.getName(), JSONException.class.getName(), (Throwable)jSONException);
            }
            catch (InvalidJsonForObjectException invalidJsonForObjectException) {
                Logger.getInstance().debug(ProfileDataService.class.getName(), InvalidJsonForObjectException.class.getName(), invalidJsonForObjectException);
            }
        }
        return null;
    }

    private void loadUserFromPreferences() {
        String string = _context.getSharedPreferences(PREF_NAME, 0).getString(PREF_USER_JSON, null);
        if (string != null) {
            try {
                this._user = new JSONUserParser().parseResult(new JSONObject(string));
                return;
            }
            catch (JSONException jSONException) {
                Logger.getInstance().debug(LoginService.class.getName(), JSONException.class.getName(), (Throwable)jSONException);
                return;
            }
            catch (InvalidJsonForObjectException invalidJsonForObjectException) {
                Logger.getInstance().debug(LoginService.class.getName(), InvalidJsonForObjectException.class.getName(), invalidJsonForObjectException);
                return;
            }
        }
        this._user = null;
    }

    private void notifyUserDataChanged() {
        Iterator<IProfileDataService.IUserDataChangedListener> iterator = this._listeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().userDataChanged(this._user);
        }
    }

    private void setUserDataApiCall(Map<String, String> map, LoadCommandCallback<Map<String, String>> loadCommandCallback) {
        new GeneralJSONAPICommandLoader<Map<String, String>>().loadApiCommandPost(loadCommandCallback, "mobileAPI/SetUserData", map, new SetUserDataResponseParser(), true);
    }

    private void storeDashboardData(JSONObject jSONObject) {
        this.getSharedPreferences().edit().putString("jsonData", jSONObject.toString()).commit();
    }

    private void storeUserDataInPreferences() {
        SharedPreferences.Editor editor = _context.getSharedPreferences(PREF_NAME, 0).edit();
        if (this._user != null) {
            try {
                editor.putString(PREF_USER_JSON, new JSONMappingUser().mapToJSON(this._user).toString());
            }
            catch (JSONException jSONException) {
                Logger.getInstance().debug(LoginService.class.getName(), JSONException.class.getName(), (Throwable)jSONException);
            }
        } else {
            editor.remove(PREF_USER_JSON);
        }
        editor.commit();
    }

    @Override
    public void addUserDataChangedListener(IProfileDataService.IUserDataChangedListener iUserDataChangedListener) {
        this._listeners.add(iUserDataChangedListener);
    }

    @Override
    public DashboardData getCurrentDashboardData() {
        if (this._dashBoardData == null) {
            this._dashBoardData = this.loadDashBoardDataFromPreferences(this._dashboardDataParser);
        }
        return this._dashBoardData;
    }

    @Override
    public User getCurrentUserData() {
        if (this._user == null && ServiceProvider.getInstance().getLoginService().getAuthToken() != null) {
            this.getUserData(null);
        }
        return this._user;
    }

    @Override
    public void getDashboardData(LoadCommandCallback<DashboardData> loadCommandCallback) {
        GeneralJSONAPICommandLoader<DashboardData> generalJSONAPICommandLoader = new GeneralJSONAPICommandLoader<DashboardData>();
        generalJSONAPICommandLoader.addOutputListener(new GeneralJSONAPICommandLoader.JSONOutputListener<DashboardData>(){

            @Override
            public void output(DashboardData dashboardData, JSONObject jSONObject) {
                ProfileDataService.this.storeDashboardData(jSONObject);
            }
        });
        generalJSONAPICommandLoader.loadApiCommandGet(loadCommandCallback, "mobileAPI/GetDashboardData", null, this._dashboardDataParser, true);
    }

    @Override
    public void getUserData(LoadCommandCallback<User> loadCommandCallback) {
        LoadCommandCallback<User> loadCommandCallback2 = loadCommandCallback;
        if (loadCommandCallback == null) {
            loadCommandCallback2 = new LoadCommandCallback<User>(){

                @Override
                public void loadingCancelled() {
                }

                @Override
                public void loadingFailed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
                }

                @Override
                public void loadingSucceded(User user) {
                }
            };
        }
        new GeneralJSONAPICommandLoader().loadApiCommandGet(new LoadCommandCallbackWrapper<User>((LoadCommandCallback)loadCommandCallback2){

            @Override
            protected void succeded(User user) {
                ProfileDataService.this._user = user;
                super.succeded(user);
                ProfileDataService.this.notifyUserDataChanged();
                ProfileDataService.this.storeUserDataInPreferences();
            }
        }, "mobileAPI/GetUserData", null, new JSONUserParser(), true);
    }

    @Override
    public void getUserForUuid(CishaUUID cishaUUID, LoadCommandCallback<User> loadCommandCallback) {
    }

    @Override
    public void invalidateUserData() {
        this._user = null;
        this._dashBoardData = null;
        this.getSharedPreferences().edit().clear().commit();
        this.notifyUserDataChanged();
        this.getUserData(null);
    }

    @Override
    public void setEmail(String string, String string2, LoadCommandCallback<Map<String, String>> loadCommandCallback) {
        if (string2 != null && string2 != "" && string != null) {
            TreeMap<String, String> treeMap = new TreeMap<String, String>();
            treeMap.put("email", string);
            treeMap.put("old_password", string2);
            this.setUserDataApiCall(treeMap, loadCommandCallback);
        }
    }

    @Override
    public void setPassword(String object, String object2, String string, LoadCommandCallback<Map<String, String>> loadCommandCallback) {
        if (object2 != null && string != null) {
            if (object2.equals(string)) {
                TreeMap<String, String> treeMap = new TreeMap<String, String>();
                treeMap.put("old_password", (String)object);
                treeMap.put("new_password", (String)object2);
                treeMap.put("repeat_password", string);
                this.setUserDataApiCall(treeMap, loadCommandCallback);
                return;
            }
            object = new LoadFieldError("repeat_password", _context.getResources().getString(2131689606));
            object2 = new LinkedList();
            object2.add(object);
            loadCommandCallback.loadingFailed(APIStatusCode.INTERNAL_UNKNOWN, "Passwords do not match", (List<LoadFieldError>)object2, null);
            return;
        }
        object = new LinkedList();
        object.add(new LoadFieldError("repeat_password", _context.getResources().getString(2131689607)));
        loadCommandCallback.loadingFailed(APIStatusCode.INTERNAL_UNKNOWN, "Password is null", (List<LoadFieldError>)object, null);
    }

    @Override
    public void setProfileImage(final Bitmap object, LoadCommandCallback<IProfileDataService.SetProfileImageResponse> object2) {
        GeneralJSONAPICommandLoader generalJSONAPICommandLoader = new GeneralJSONAPICommandLoader();
        FileUploadInformationImpl fileUploadInformationImpl = new FileUploadInformationImpl("my_android_image.png", BitmapHelper.createPNG(object), "image/png");
        HashMap<String, HTTPHelper.FileUploadInformation> hashMap = new HashMap<String, HTTPHelper.FileUploadInformation>();
        hashMap.put("file", fileUploadInformationImpl);
        object = new LoadCommandCallbackWrapper<IProfileDataService.SetProfileImageResponse>(object2){

            @Override
            public void loadingFailed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
                synchronized (this) {
                    super.loadingFailed(aPIStatusCode, string, list, jSONObject);
                    ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.PROFILE, ProfileDataService.UPLOAD_PROFILE_IMAGE_TRACKING_ACTION, ProfileDataService.ERROR);
                    return;
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void loadingSucceded(IProfileDataService.SetProfileImageResponse setProfileImageResponse) {
                synchronized (this) {
                    super.loadingSucceded(setProfileImageResponse);
                    if (ProfileDataService.this._user != null) {
                        ProfileDataService.this._user.setProfileImageCouchId(setProfileImageResponse.getCouchId());
                        String string = setProfileImageResponse.getRevision();
                        if (string != null && string.length() > 0) {
                            CouchImageCache.getInstance(_context).makeCouchImageCacheEntry(setProfileImageResponse.getCouchId(), string, 300, object);
                        }
                        ProfileDataService.this.storeUserDataInPreferences();
                        ProfileDataService.this.notifyUserDataChanged();
                    }
                    ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.PROFILE, ProfileDataService.UPLOAD_PROFILE_IMAGE_TRACKING_ACTION, ProfileDataService.SUCCESS);
                    return;
                }
            }
        };
        object2 = new TreeMap();
        object2.put("width", "300");
        object2.put("height", "300");
        generalJSONAPICommandLoader.uploadFileToAPICommand(object, "mobileAPI/SetProfileImage", (Map<String, String>)object2, (Map<String, HTTPHelper.FileUploadInformation>)hashMap, new JSONUploadProfileImageResponseParser(), true);
    }

    @Override
    public void setUserData(Map<String, String> map, LoadCommandCallback<Map<String, String>> loadCommandCallback) {
        this.setUserDataApiCall(map, (LoadCommandCallback<Map<String, String>>)new LoadCommandCallbackWrapper<Map<String, String>>(loadCommandCallback){

            @Override
            protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
                super.failed(aPIStatusCode, string, list, jSONObject);
                ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.PROFILE, ProfileDataService.EDIT_PROFILE_IMAGE_TRACKING_ACTION, ProfileDataService.ERROR);
            }

            @Override
            protected void succeded(Map<String, String> map) {
                ProfileDataService.this.getUserData(null);
                super.succeded(map);
                ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.PROFILE, ProfileDataService.EDIT_PROFILE_IMAGE_TRACKING_ACTION, ProfileDataService.SUCCESS);
            }
        });
    }

}
