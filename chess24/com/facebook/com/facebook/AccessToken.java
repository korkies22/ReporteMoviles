/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.content.Intent
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.text.TextUtils
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.facebook;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.facebook.AccessTokenManager;
import com.facebook.AccessTokenSource;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.LegacyTokenHelper;
import com.facebook.LoggingBehavior;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class AccessToken
implements Parcelable {
    public static final String ACCESS_TOKEN_KEY = "access_token";
    private static final String APPLICATION_ID_KEY = "application_id";
    public static final Parcelable.Creator<AccessToken> CREATOR;
    private static final int CURRENT_JSON_FORMAT = 1;
    private static final String DECLINED_PERMISSIONS_KEY = "declined_permissions";
    private static final AccessTokenSource DEFAULT_ACCESS_TOKEN_SOURCE;
    private static final Date DEFAULT_EXPIRATION_TIME;
    private static final Date DEFAULT_LAST_REFRESH_TIME;
    private static final String EXPIRES_AT_KEY = "expires_at";
    public static final String EXPIRES_IN_KEY = "expires_in";
    private static final String LAST_REFRESH_KEY = "last_refresh";
    private static final Date MAX_DATE;
    private static final String PERMISSIONS_KEY = "permissions";
    private static final String SOURCE_KEY = "source";
    private static final String TOKEN_KEY = "token";
    public static final String USER_ID_KEY = "user_id";
    private static final String VERSION_KEY = "version";
    private final String applicationId;
    private final Set<String> declinedPermissions;
    private final Date expires;
    private final Date lastRefresh;
    private final Set<String> permissions;
    private final AccessTokenSource source;
    private final String token;
    private final String userId;

    static {
        DEFAULT_EXPIRATION_TIME = MAX_DATE = new Date(Long.MAX_VALUE);
        DEFAULT_LAST_REFRESH_TIME = new Date();
        DEFAULT_ACCESS_TOKEN_SOURCE = AccessTokenSource.FACEBOOK_APPLICATION_WEB;
        CREATOR = new Parcelable.Creator(){

            public AccessToken createFromParcel(Parcel parcel) {
                return new AccessToken(parcel);
            }

            public AccessToken[] newArray(int n) {
                return new AccessToken[n];
            }
        };
    }

    AccessToken(Parcel parcel) {
        this.expires = new Date(parcel.readLong());
        ArrayList arrayList = new ArrayList();
        parcel.readStringList(arrayList);
        this.permissions = Collections.unmodifiableSet(new HashSet(arrayList));
        arrayList.clear();
        parcel.readStringList(arrayList);
        this.declinedPermissions = Collections.unmodifiableSet(new HashSet(arrayList));
        this.token = parcel.readString();
        this.source = AccessTokenSource.valueOf(parcel.readString());
        this.lastRefresh = new Date(parcel.readLong());
        this.applicationId = parcel.readString();
        this.userId = parcel.readString();
    }

    public AccessToken(String string, String string2, String string3, @Nullable Collection<String> collection, @Nullable Collection<String> collection2, @Nullable AccessTokenSource accessTokenSource, @Nullable Date date, @Nullable Date date2) {
        Validate.notNullOrEmpty(string, "accessToken");
        Validate.notNullOrEmpty(string2, "applicationId");
        Validate.notNullOrEmpty(string3, "userId");
        if (date == null) {
            date = DEFAULT_EXPIRATION_TIME;
        }
        this.expires = date;
        collection = collection != null ? new HashSet<String>(collection) : new HashSet<String>();
        this.permissions = Collections.unmodifiableSet(collection);
        collection = collection2 != null ? new HashSet<String>(collection2) : new HashSet<String>();
        this.declinedPermissions = Collections.unmodifiableSet(collection);
        this.token = string;
        if (accessTokenSource == null) {
            accessTokenSource = DEFAULT_ACCESS_TOKEN_SOURCE;
        }
        this.source = accessTokenSource;
        if (date2 == null) {
            date2 = DEFAULT_LAST_REFRESH_TIME;
        }
        this.lastRefresh = date2;
        this.applicationId = string2;
        this.userId = string3;
    }

    private void appendPermissions(StringBuilder stringBuilder) {
        stringBuilder.append(" permissions:");
        if (this.permissions == null) {
            stringBuilder.append("null");
            return;
        }
        stringBuilder.append("[");
        stringBuilder.append(TextUtils.join((CharSequence)", ", this.permissions));
        stringBuilder.append("]");
    }

    private static AccessToken createFromBundle(List<String> list, Bundle object, AccessTokenSource accessTokenSource, Date date, String string) {
        String string2 = object.getString(ACCESS_TOKEN_KEY);
        date = Utility.getBundleLongAsDate(object, EXPIRES_IN_KEY, date);
        object = object.getString(USER_ID_KEY);
        if (!Utility.isNullOrEmpty(string2) && date != null) {
            return new AccessToken(string2, string, (String)object, list, null, accessTokenSource, date, new Date());
        }
        return null;
    }

    static AccessToken createFromJSONObject(JSONObject jSONObject) throws JSONException {
        if (jSONObject.getInt(VERSION_KEY) > 1) {
            throw new FacebookException("Unknown AccessToken serialization format.");
        }
        String string = jSONObject.getString(TOKEN_KEY);
        Date date = new Date(jSONObject.getLong(EXPIRES_AT_KEY));
        JSONArray jSONArray = jSONObject.getJSONArray(PERMISSIONS_KEY);
        JSONArray jSONArray2 = jSONObject.getJSONArray(DECLINED_PERMISSIONS_KEY);
        Date date2 = new Date(jSONObject.getLong(LAST_REFRESH_KEY));
        AccessTokenSource accessTokenSource = AccessTokenSource.valueOf(jSONObject.getString(SOURCE_KEY));
        return new AccessToken(string, jSONObject.getString(APPLICATION_ID_KEY), jSONObject.getString(USER_ID_KEY), Utility.jsonArrayToStringList(jSONArray), Utility.jsonArrayToStringList(jSONArray2), accessTokenSource, date, date2);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static AccessToken createFromLegacyCache(Bundle bundle) {
        String string;
        String string2;
        List<String> list = AccessToken.getPermissionsFromBundle(bundle, "com.facebook.TokenCachingStrategy.Permissions");
        List<String> list2 = AccessToken.getPermissionsFromBundle(bundle, "com.facebook.TokenCachingStrategy.DeclinedPermissions");
        String string3 = string2 = LegacyTokenHelper.getApplicationId(bundle);
        if (Utility.isNullOrEmpty(string2)) {
            string3 = FacebookSdk.getApplicationId();
        }
        string2 = LegacyTokenHelper.getToken(bundle);
        JSONObject jSONObject = Utility.awaitGetGraphMeRequestWithCache(string2);
        try {
            string = jSONObject.getString("id");
        }
        catch (JSONException jSONException) {
            return null;
        }
        return new AccessToken(string2, string3, string, list, list2, LegacyTokenHelper.getSource(bundle), LegacyTokenHelper.getDate(bundle, "com.facebook.TokenCachingStrategy.ExpirationDate"), LegacyTokenHelper.getDate(bundle, "com.facebook.TokenCachingStrategy.LastRefreshDate"));
    }

    public static void createFromNativeLinkingIntent(Intent intent, String string, AccessTokenCreationCallback accessTokenCreationCallback) {
        Validate.notNull((Object)intent, "intent");
        if (intent.getExtras() == null) {
            accessTokenCreationCallback.onError(new FacebookException("No extras found on intent"));
            return;
        }
        String string2 = (intent = new Bundle(intent.getExtras())).getString(ACCESS_TOKEN_KEY);
        if (string2 != null && !string2.isEmpty()) {
            String string3 = intent.getString(USER_ID_KEY);
            if (string3 != null && !string3.isEmpty()) {
                accessTokenCreationCallback.onSuccess(AccessToken.createFromBundle(null, (Bundle)intent, AccessTokenSource.FACEBOOK_APPLICATION_WEB, new Date(), string));
                return;
            }
            Utility.getGraphMeRequestWithCacheAsync(string2, new Utility.GraphMeRequestWithCacheCallback((Bundle)intent, accessTokenCreationCallback, string){
                final /* synthetic */ AccessTokenCreationCallback val$accessTokenCallback;
                final /* synthetic */ String val$applicationId;
                final /* synthetic */ Bundle val$extras;
                {
                    this.val$extras = bundle;
                    this.val$accessTokenCallback = accessTokenCreationCallback;
                    this.val$applicationId = string;
                }

                @Override
                public void onFailure(FacebookException facebookException) {
                    this.val$accessTokenCallback.onError(facebookException);
                }

                /*
                 * Enabled aggressive block sorting
                 * Enabled unnecessary exception pruning
                 * Enabled aggressive exception aggregation
                 */
                @Override
                public void onSuccess(JSONObject object) {
                    try {
                        String string = object.getString("id");
                        this.val$extras.putString(AccessToken.USER_ID_KEY, string);
                        this.val$accessTokenCallback.onSuccess(AccessToken.createFromBundle(null, this.val$extras, AccessTokenSource.FACEBOOK_APPLICATION_WEB, new Date(), this.val$applicationId));
                        return;
                    }
                    catch (JSONException jSONException) {}
                    this.val$accessTokenCallback.onError(new FacebookException("Unable to generate access token due to missing user id"));
                }
            });
            return;
        }
        accessTokenCreationCallback.onError(new FacebookException("No access token found on intent"));
    }

    @SuppressLint(value={"FieldGetter"})
    static AccessToken createFromRefresh(AccessToken accessToken, Bundle object) {
        if (accessToken.source != AccessTokenSource.FACEBOOK_APPLICATION_WEB && accessToken.source != AccessTokenSource.FACEBOOK_APPLICATION_NATIVE && accessToken.source != AccessTokenSource.FACEBOOK_APPLICATION_SERVICE) {
            object = new StringBuilder();
            object.append("Invalid token source: ");
            object.append((Object)accessToken.source);
            throw new FacebookException(object.toString());
        }
        Date date = Utility.getBundleLongAsDate((Bundle)object, EXPIRES_IN_KEY, new Date(0L));
        if (Utility.isNullOrEmpty((String)(object = object.getString(ACCESS_TOKEN_KEY)))) {
            return null;
        }
        return new AccessToken((String)object, accessToken.applicationId, accessToken.getUserId(), accessToken.getPermissions(), accessToken.getDeclinedPermissions(), accessToken.source, date, new Date());
    }

    public static AccessToken getCurrentAccessToken() {
        return AccessTokenManager.getInstance().getCurrentAccessToken();
    }

    static List<String> getPermissionsFromBundle(Bundle object, String string) {
        if ((object = object.getStringArrayList(string)) == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(new ArrayList(object));
    }

    public static void refreshCurrentAccessTokenAsync() {
        AccessTokenManager.getInstance().refreshCurrentAccessToken(null);
    }

    public static void refreshCurrentAccessTokenAsync(AccessTokenRefreshCallback accessTokenRefreshCallback) {
        AccessTokenManager.getInstance().refreshCurrentAccessToken(accessTokenRefreshCallback);
    }

    public static void setCurrentAccessToken(AccessToken accessToken) {
        AccessTokenManager.getInstance().setCurrentAccessToken(accessToken);
    }

    private String tokenToString() {
        if (this.token == null) {
            return "null";
        }
        if (FacebookSdk.isLoggingBehaviorEnabled(LoggingBehavior.INCLUDE_ACCESS_TOKENS)) {
            return this.token;
        }
        return "ACCESS_TOKEN_REMOVED";
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof AccessToken)) {
            return false;
        }
        object = (AccessToken)object;
        if (this.expires.equals(object.expires) && this.permissions.equals(object.permissions) && this.declinedPermissions.equals(object.declinedPermissions) && this.token.equals(object.token) && this.source == object.source && this.lastRefresh.equals(object.lastRefresh) && (this.applicationId == null ? object.applicationId == null : this.applicationId.equals(object.applicationId)) && this.userId.equals(object.userId)) {
            return true;
        }
        return false;
    }

    public String getApplicationId() {
        return this.applicationId;
    }

    public Set<String> getDeclinedPermissions() {
        return this.declinedPermissions;
    }

    public Date getExpires() {
        return this.expires;
    }

    public Date getLastRefresh() {
        return this.lastRefresh;
    }

    public Set<String> getPermissions() {
        return this.permissions;
    }

    public AccessTokenSource getSource() {
        return this.source;
    }

    public String getToken() {
        return this.token;
    }

    public String getUserId() {
        return this.userId;
    }

    public int hashCode() {
        int n = this.expires.hashCode();
        int n2 = this.permissions.hashCode();
        int n3 = this.declinedPermissions.hashCode();
        int n4 = this.token.hashCode();
        int n5 = this.source.hashCode();
        int n6 = this.lastRefresh.hashCode();
        int n7 = this.applicationId == null ? 0 : this.applicationId.hashCode();
        return (((((((527 + n) * 31 + n2) * 31 + n3) * 31 + n4) * 31 + n5) * 31 + n6) * 31 + n7) * 31 + this.userId.hashCode();
    }

    public boolean isExpired() {
        return new Date().after(this.expires);
    }

    JSONObject toJSONObject() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(VERSION_KEY, 1);
        jSONObject.put(TOKEN_KEY, (Object)this.token);
        jSONObject.put(EXPIRES_AT_KEY, this.expires.getTime());
        jSONObject.put(PERMISSIONS_KEY, (Object)new JSONArray(this.permissions));
        jSONObject.put(DECLINED_PERMISSIONS_KEY, (Object)new JSONArray(this.declinedPermissions));
        jSONObject.put(LAST_REFRESH_KEY, this.lastRefresh.getTime());
        jSONObject.put(SOURCE_KEY, (Object)this.source.name());
        jSONObject.put(APPLICATION_ID_KEY, (Object)this.applicationId);
        jSONObject.put(USER_ID_KEY, (Object)this.userId);
        return jSONObject;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{AccessToken");
        stringBuilder.append(" token:");
        stringBuilder.append(this.tokenToString());
        this.appendPermissions(stringBuilder);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeLong(this.expires.getTime());
        parcel.writeStringList(new ArrayList<String>(this.permissions));
        parcel.writeStringList(new ArrayList<String>(this.declinedPermissions));
        parcel.writeString(this.token);
        parcel.writeString(this.source.name());
        parcel.writeLong(this.lastRefresh.getTime());
        parcel.writeString(this.applicationId);
        parcel.writeString(this.userId);
    }

    public static interface AccessTokenCreationCallback {
        public void onError(FacebookException var1);

        public void onSuccess(AccessToken var1);
    }

    public static interface AccessTokenRefreshCallback {
        public void OnTokenRefreshFailed(FacebookException var1);

        public void OnTokenRefreshed(AccessToken var1);
    }

}
