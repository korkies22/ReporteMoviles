/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.facebook;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.facebook.AccessToken;
import com.facebook.FacebookException;
import com.facebook.ProfileManager;
import com.facebook.internal.ImageRequest;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import org.json.JSONException;
import org.json.JSONObject;

public final class Profile
implements Parcelable {
    public static final Parcelable.Creator<Profile> CREATOR = new Parcelable.Creator(){

        public Profile createFromParcel(Parcel parcel) {
            return new Profile(parcel);
        }

        public Profile[] newArray(int n) {
            return new Profile[n];
        }
    };
    private static final String FIRST_NAME_KEY = "first_name";
    private static final String ID_KEY = "id";
    private static final String LAST_NAME_KEY = "last_name";
    private static final String LINK_URI_KEY = "link_uri";
    private static final String MIDDLE_NAME_KEY = "middle_name";
    private static final String NAME_KEY = "name";
    private final String firstName;
    private final String id;
    private final String lastName;
    private final Uri linkUri;
    private final String middleName;
    private final String name;

    private Profile(Parcel object) {
        this.id = object.readString();
        this.firstName = object.readString();
        this.middleName = object.readString();
        this.lastName = object.readString();
        this.name = object.readString();
        object = object.readString();
        object = object == null ? null : Uri.parse((String)object);
        this.linkUri = object;
    }

    public Profile(String string, @Nullable String string2, @Nullable String string3, @Nullable String string4, @Nullable String string5, @Nullable Uri uri) {
        Validate.notNullOrEmpty(string, ID_KEY);
        this.id = string;
        this.firstName = string2;
        this.middleName = string3;
        this.lastName = string4;
        this.name = string5;
        this.linkUri = uri;
    }

    Profile(JSONObject object) {
        Object var2_2 = null;
        this.id = object.optString(ID_KEY, null);
        this.firstName = object.optString(FIRST_NAME_KEY, null);
        this.middleName = object.optString(MIDDLE_NAME_KEY, null);
        this.lastName = object.optString(LAST_NAME_KEY, null);
        this.name = object.optString(NAME_KEY, null);
        object = object.optString(LINK_URI_KEY, null);
        object = object == null ? var2_2 : Uri.parse((String)object);
        this.linkUri = object;
    }

    public static void fetchProfileForCurrentAccessToken() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken == null) {
            Profile.setCurrentProfile(null);
            return;
        }
        Utility.getGraphMeRequestWithCacheAsync(accessToken.getToken(), new Utility.GraphMeRequestWithCacheCallback(){

            @Override
            public void onFailure(FacebookException facebookException) {
            }

            @Override
            public void onSuccess(JSONObject object) {
                String string = object.optString(Profile.ID_KEY);
                if (string == null) {
                    return;
                }
                String string2 = object.optString("link");
                String string3 = object.optString(Profile.FIRST_NAME_KEY);
                String string4 = object.optString(Profile.MIDDLE_NAME_KEY);
                String string5 = object.optString(Profile.LAST_NAME_KEY);
                String string6 = object.optString(Profile.NAME_KEY);
                object = string2 != null ? Uri.parse((String)string2) : null;
                Profile.setCurrentProfile(new Profile(string, string3, string4, string5, string6, (Uri)object));
            }
        });
    }

    public static Profile getCurrentProfile() {
        return ProfileManager.getInstance().getCurrentProfile();
    }

    public static void setCurrentProfile(Profile profile) {
        ProfileManager.getInstance().setCurrentProfile(profile);
    }

    public int describeContents() {
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Profile)) {
            return false;
        }
        object = (Profile)object;
        if (this.id.equals(object.id) && this.firstName == null) {
            if (object.firstName != null) return false;
            return true;
        }
        if (this.firstName.equals(object.firstName) && this.middleName == null) {
            if (object.middleName != null) return false;
            return true;
        }
        if (this.middleName.equals(object.middleName) && this.lastName == null) {
            if (object.lastName != null) return false;
            return true;
        }
        if (this.lastName.equals(object.lastName) && this.name == null) {
            if (object.name != null) return false;
            return true;
        }
        if (!this.name.equals(object.name) || this.linkUri != null) return this.linkUri.equals((Object)object.linkUri);
        if (object.linkUri == null) return true;
        return false;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getId() {
        return this.id;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Uri getLinkUri() {
        return this.linkUri;
    }

    public String getMiddleName() {
        return this.middleName;
    }

    public String getName() {
        return this.name;
    }

    public Uri getProfilePictureUri(int n, int n2) {
        return ImageRequest.getProfilePictureUri(this.id, n, n2);
    }

    public int hashCode() {
        int n;
        int n2 = n = 527 + this.id.hashCode();
        if (this.firstName != null) {
            n2 = n * 31 + this.firstName.hashCode();
        }
        n = n2;
        if (this.middleName != null) {
            n = n2 * 31 + this.middleName.hashCode();
        }
        n2 = n;
        if (this.lastName != null) {
            n2 = n * 31 + this.lastName.hashCode();
        }
        n = n2;
        if (this.name != null) {
            n = n2 * 31 + this.name.hashCode();
        }
        n2 = n;
        if (this.linkUri != null) {
            n2 = n * 31 + this.linkUri.hashCode();
        }
        return n2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    JSONObject toJSONObject() {
        JSONObject jSONObject;
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject2.put(ID_KEY, (Object)this.id);
            jSONObject2.put(FIRST_NAME_KEY, (Object)this.firstName);
            jSONObject2.put(MIDDLE_NAME_KEY, (Object)this.middleName);
            jSONObject2.put(LAST_NAME_KEY, (Object)this.lastName);
            jSONObject2.put(NAME_KEY, (Object)this.name);
            jSONObject = jSONObject2;
        }
        catch (JSONException jSONException) {
            return null;
        }
        if (this.linkUri == null) return jSONObject;
        jSONObject2.put(LINK_URI_KEY, (Object)this.linkUri.toString());
        return jSONObject2;
    }

    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.id);
        parcel.writeString(this.firstName);
        parcel.writeString(this.middleName);
        parcel.writeString(this.lastName);
        parcel.writeString(this.name);
        String string = this.linkUri == null ? null : this.linkUri.toString();
        parcel.writeString(string);
    }

}
