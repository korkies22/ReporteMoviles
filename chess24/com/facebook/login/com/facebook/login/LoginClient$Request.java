/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.facebook.login;

import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.internal.Validate;
import com.facebook.login.DefaultAudience;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginClient;
import com.facebook.login.LoginManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public static class LoginClient.Request
implements Parcelable {
    public static final Parcelable.Creator<LoginClient.Request> CREATOR = new Parcelable.Creator(){

        public LoginClient.Request createFromParcel(Parcel parcel) {
            return new LoginClient.Request(parcel);
        }

        public LoginClient.Request[] newArray(int n) {
            return new LoginClient.Request[n];
        }
    };
    private final String applicationId;
    private final String authId;
    private final DefaultAudience defaultAudience;
    private String deviceRedirectUriString;
    private boolean isRerequest;
    private final LoginBehavior loginBehavior;
    private Set<String> permissions;

    private LoginClient.Request(Parcel parcel) {
        boolean bl = false;
        this.isRerequest = false;
        Object object = parcel.readString();
        Object var4_4 = null;
        object = object != null ? LoginBehavior.valueOf(object) : null;
        this.loginBehavior = object;
        object = new ArrayList();
        parcel.readStringList((List)object);
        this.permissions = new HashSet<String>((Collection<String>)object);
        String string = parcel.readString();
        object = var4_4;
        if (string != null) {
            object = DefaultAudience.valueOf(string);
        }
        this.defaultAudience = object;
        this.applicationId = parcel.readString();
        this.authId = parcel.readString();
        if (parcel.readByte() != 0) {
            bl = true;
        }
        this.isRerequest = bl;
        this.deviceRedirectUriString = parcel.readString();
    }

    LoginClient.Request(LoginBehavior loginBehavior, Set<String> set, DefaultAudience defaultAudience, String string, String string2) {
        this.isRerequest = false;
        this.loginBehavior = loginBehavior;
        if (set == null) {
            set = new HashSet<String>();
        }
        this.permissions = set;
        this.defaultAudience = defaultAudience;
        this.applicationId = string;
        this.authId = string2;
    }

    public int describeContents() {
        return 0;
    }

    String getApplicationId() {
        return this.applicationId;
    }

    String getAuthId() {
        return this.authId;
    }

    DefaultAudience getDefaultAudience() {
        return this.defaultAudience;
    }

    String getDeviceRedirectUriString() {
        return this.deviceRedirectUriString;
    }

    LoginBehavior getLoginBehavior() {
        return this.loginBehavior;
    }

    Set<String> getPermissions() {
        return this.permissions;
    }

    boolean hasPublishPermission() {
        Iterator<String> iterator = this.permissions.iterator();
        while (iterator.hasNext()) {
            if (!LoginManager.isPublishPermission(iterator.next())) continue;
            return true;
        }
        return false;
    }

    boolean isRerequest() {
        return this.isRerequest;
    }

    void setDeviceRedirectUriString(String string) {
        this.deviceRedirectUriString = string;
    }

    void setPermissions(Set<String> set) {
        Validate.notNull(set, "permissions");
        this.permissions = set;
    }

    void setRerequest(boolean bl) {
        this.isRerequest = bl;
    }

    public void writeToParcel(Parcel parcel, int n) {
        Object object = this.loginBehavior;
        Object var4_4 = null;
        object = object != null ? this.loginBehavior.name() : null;
        parcel.writeString((String)object);
        parcel.writeStringList(new ArrayList<String>(this.permissions));
        object = var4_4;
        if (this.defaultAudience != null) {
            object = this.defaultAudience.name();
        }
        parcel.writeString((String)object);
        parcel.writeString(this.applicationId);
        parcel.writeString(this.authId);
        parcel.writeByte((byte)(this.isRerequest ? 1 : 0));
        parcel.writeString(this.deviceRedirectUriString);
    }

}
