// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service;

import java.util.HashMap;
import de.cisha.chess.util.FileUploadInformationImpl;
import de.cisha.chess.util.BitmapHelper;
import android.graphics.Bitmap;
import java.util.LinkedList;
import java.util.TreeMap;
import de.cisha.chess.model.CishaUUID;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWrapper;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import java.util.List;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import android.content.SharedPreferences.Editor;
import de.cisha.android.board.service.jsonmapping.JSONMappingUser;
import de.cisha.android.board.service.jsonparser.SetUserDataResponseParser;
import de.cisha.android.board.service.jsonparser.GeneralJSONAPICommandLoader;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import java.util.Iterator;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import org.json.JSONException;
import de.cisha.chess.util.Logger;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import android.content.SharedPreferences;
import org.json.JSONObject;
import java.util.Map;
import java.util.Collections;
import java.util.WeakHashMap;
import de.cisha.android.board.user.User;
import java.util.Set;
import de.cisha.android.board.profile.model.JSONDashboardDataParser;
import de.cisha.android.board.profile.model.DashboardData;
import android.content.Context;

public class ProfileDataService implements IProfileDataService
{
    private static final String EDIT_PROFILE_IMAGE_TRACKING_ACTION = "Edit profile";
    private static final String ERROR = "error";
    private static final String PREF_NAME = "ProfileDataService";
    private static final String PREF_USER_JSON = "user_as_json";
    private static final String SUCCESS = "success";
    private static final String UPLOAD_PROFILE_IMAGE_TRACKING_ACTION = "Upload profile image";
    private static Context _context;
    private static ProfileDataService _instance;
    private DashboardData _dashBoardData;
    private JSONDashboardDataParser _dashboardDataParser;
    private Set<IUserDataChangedListener> _listeners;
    private User _user;
    
    private ProfileDataService() {
        this._listeners = Collections.newSetFromMap(new WeakHashMap<IUserDataChangedListener, Boolean>());
        this._dashboardDataParser = new JSONDashboardDataParser();
        this.loadUserFromPreferences();
    }
    
    public static ProfileDataService getInstance(final Context context) {
        ProfileDataService._context = context;
        if (ProfileDataService._instance == null) {
            ProfileDataService._instance = new ProfileDataService();
        }
        return ProfileDataService._instance;
    }
    
    private SharedPreferences getSharedPreferences() {
        final StringBuilder sb = new StringBuilder();
        sb.append("PROFILE_DASHBOARD_DATA");
        sb.append(ServiceProvider.getInstance().getLoginService().getUserPrefix());
        return ProfileDataService._context.getSharedPreferences(sb.toString(), 0);
    }
    
    private DashboardData loadDashBoardDataFromPreferences(final JSONAPIResultParser<DashboardData> jsonapiResultParser) {
        final SharedPreferences sharedPreferences = this.getSharedPreferences();
        if (sharedPreferences.contains("jsonData")) {
            try {
                return jsonapiResultParser.parseResult(new JSONObject(sharedPreferences.getString("jsonData", "")));
            }
            catch (JSONException ex) {
                Logger.getInstance().debug(ProfileDataService.class.getName(), JSONException.class.getName(), (Throwable)ex);
            }
            catch (InvalidJsonForObjectException ex2) {
                Logger.getInstance().debug(ProfileDataService.class.getName(), InvalidJsonForObjectException.class.getName(), ex2);
            }
        }
        return null;
    }
    
    private void loadUserFromPreferences() {
        final String string = ProfileDataService._context.getSharedPreferences("ProfileDataService", 0).getString("user_as_json", (String)null);
        if (string != null) {
            try {
                this._user = new JSONUserParser().parseResult(new JSONObject(string));
                return;
            }
            catch (JSONException ex) {
                Logger.getInstance().debug(LoginService.class.getName(), JSONException.class.getName(), (Throwable)ex);
                return;
            }
            catch (InvalidJsonForObjectException ex2) {
                Logger.getInstance().debug(LoginService.class.getName(), InvalidJsonForObjectException.class.getName(), ex2);
                return;
            }
        }
        this._user = null;
    }
    
    private void notifyUserDataChanged() {
        final Iterator<IUserDataChangedListener> iterator = this._listeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().userDataChanged(this._user);
        }
    }
    
    private void setUserDataApiCall(final Map<String, String> map, final LoadCommandCallback<Map<String, String>> loadCommandCallback) {
        new GeneralJSONAPICommandLoader<Map<String, String>>().loadApiCommandPost(loadCommandCallback, "mobileAPI/SetUserData", map, new SetUserDataResponseParser(), true);
    }
    
    private void storeDashboardData(final JSONObject jsonObject) {
        this.getSharedPreferences().edit().putString("jsonData", jsonObject.toString()).commit();
    }
    
    private void storeUserDataInPreferences() {
        final SharedPreferences.Editor edit = ProfileDataService._context.getSharedPreferences("ProfileDataService", 0).edit();
        if (this._user != null) {
            try {
                edit.putString("user_as_json", new JSONMappingUser().mapToJSON(this._user).toString());
            }
            catch (JSONException ex) {
                Logger.getInstance().debug(LoginService.class.getName(), JSONException.class.getName(), (Throwable)ex);
            }
        }
        else {
            edit.remove("user_as_json");
        }
        edit.commit();
    }
    
    @Override
    public void addUserDataChangedListener(final IUserDataChangedListener userDataChangedListener) {
        this._listeners.add(userDataChangedListener);
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
    public void getDashboardData(final LoadCommandCallback<DashboardData> loadCommandCallback) {
        final GeneralJSONAPICommandLoader<DashboardData> generalJSONAPICommandLoader = new GeneralJSONAPICommandLoader<DashboardData>();
        generalJSONAPICommandLoader.addOutputListener((GeneralJSONAPICommandLoader.JSONOutputListener<DashboardData>)new GeneralJSONAPICommandLoader.JSONOutputListener<DashboardData>() {
            public void output(final DashboardData dashboardData, final JSONObject jsonObject) {
                ProfileDataService.this.storeDashboardData(jsonObject);
            }
        });
        generalJSONAPICommandLoader.loadApiCommandGet(loadCommandCallback, "mobileAPI/GetDashboardData", null, this._dashboardDataParser, true);
    }
    
    @Override
    public void getUserData(final LoadCommandCallback<User> loadCommandCallback) {
        LoadCommandCallback<User> loadCommandCallback2 = loadCommandCallback;
        if (loadCommandCallback == null) {
            loadCommandCallback2 = new LoadCommandCallback<User>() {
                @Override
                public void loadingCancelled() {
                }
                
                @Override
                public void loadingFailed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                }
                
                @Override
                public void loadingSucceded(final User user) {
                }
            };
        }
        new GeneralJSONAPICommandLoader().loadApiCommandGet((LoadCommandCallback)new LoadCommandCallbackWrapper<User>(loadCommandCallback2) {
            @Override
            protected void succeded(final User user) {
                ProfileDataService.this._user = user;
                super.succeded(user);
                ProfileDataService.this.notifyUserDataChanged();
                ProfileDataService.this.storeUserDataInPreferences();
            }
        }, "mobileAPI/GetUserData", null, (JSONAPIResultParser)new JSONUserParser(), true);
    }
    
    @Override
    public void getUserForUuid(final CishaUUID cishaUUID, final LoadCommandCallback<User> loadCommandCallback) {
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
    public void setEmail(final String s, final String s2, final LoadCommandCallback<Map<String, String>> loadCommandCallback) {
        if (s2 != null && s2 != "" && s != null) {
            final TreeMap<String, String> treeMap = new TreeMap<String, String>();
            treeMap.put("email", s);
            treeMap.put("old_password", s2);
            this.setUserDataApiCall(treeMap, loadCommandCallback);
        }
    }
    
    @Override
    public void setPassword(final String s, final String s2, final String s3, final LoadCommandCallback<Map<String, String>> loadCommandCallback) {
        if (s2 == null || s3 == null) {
            final LinkedList<LoadFieldError> list = new LinkedList<LoadFieldError>();
            list.add(new LoadFieldError("repeat_password", ProfileDataService._context.getResources().getString(2131689607)));
            loadCommandCallback.loadingFailed(APIStatusCode.INTERNAL_UNKNOWN, "Password is null", list, null);
            return;
        }
        if (s2.equals(s3)) {
            final TreeMap<String, String> treeMap = new TreeMap<String, String>();
            treeMap.put("old_password", s);
            treeMap.put("new_password", s2);
            treeMap.put("repeat_password", s3);
            this.setUserDataApiCall(treeMap, loadCommandCallback);
            return;
        }
        final LoadFieldError loadFieldError = new LoadFieldError("repeat_password", ProfileDataService._context.getResources().getString(2131689606));
        final LinkedList<LoadFieldError> list2 = new LinkedList<LoadFieldError>();
        list2.add(loadFieldError);
        loadCommandCallback.loadingFailed(APIStatusCode.INTERNAL_UNKNOWN, "Passwords do not match", list2, null);
    }
    
    @Override
    public void setProfileImage(final Bitmap bitmap, final LoadCommandCallback<SetProfileImageResponse> loadCommandCallback) {
        final GeneralJSONAPICommandLoader generalJSONAPICommandLoader = new GeneralJSONAPICommandLoader();
        final FileUploadInformationImpl fileUploadInformationImpl = new FileUploadInformationImpl("my_android_image.png", BitmapHelper.createPNG(bitmap), "image/png");
        final HashMap<String, FileUploadInformationImpl> hashMap = new HashMap<String, FileUploadInformationImpl>();
        hashMap.put("file", fileUploadInformationImpl);
        final LoadCommandCallbackWrapper<SetProfileImageResponse> loadCommandCallbackWrapper = new LoadCommandCallbackWrapper<SetProfileImageResponse>(loadCommandCallback) {
            @Override
            public void loadingFailed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                synchronized (this) {
                    super.loadingFailed(apiStatusCode, s, list, jsonObject);
                    ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.PROFILE, "Upload profile image", "error");
                }
            }
            
            @Override
            public void loadingSucceded(final SetProfileImageResponse setProfileImageResponse) {
                synchronized (this) {
                    super.loadingSucceded(setProfileImageResponse);
                    if (ProfileDataService.this._user != null) {
                        ProfileDataService.this._user.setProfileImageCouchId(setProfileImageResponse.getCouchId());
                        final String revision = setProfileImageResponse.getRevision();
                        if (revision != null && revision.length() > 0) {
                            CouchImageCache.getInstance(ProfileDataService._context).makeCouchImageCacheEntry(setProfileImageResponse.getCouchId(), revision, 300, bitmap);
                        }
                        ProfileDataService.this.storeUserDataInPreferences();
                        ProfileDataService.this.notifyUserDataChanged();
                    }
                    ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.PROFILE, "Upload profile image", "success");
                }
            }
        };
        final TreeMap<String, String> treeMap = new TreeMap<String, String>();
        treeMap.put("width", "300");
        treeMap.put("height", "300");
        generalJSONAPICommandLoader.uploadFileToAPICommand(loadCommandCallbackWrapper, "mobileAPI/SetProfileImage", treeMap, hashMap, new JSONUploadProfileImageResponseParser(), true);
    }
    
    @Override
    public void setUserData(final Map<String, String> map, final LoadCommandCallback<Map<String, String>> loadCommandCallback) {
        this.setUserDataApiCall(map, new LoadCommandCallbackWrapper<Map<String, String>>(loadCommandCallback) {
            @Override
            protected void failed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                super.failed(apiStatusCode, s, list, jsonObject);
                ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.PROFILE, "Edit profile", "error");
            }
            
            @Override
            protected void succeded(final Map<String, String> map) {
                ProfileDataService.this.getUserData(null);
                super.succeded(map);
                ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.PROFILE, "Edit profile", "success");
            }
        });
    }
}
