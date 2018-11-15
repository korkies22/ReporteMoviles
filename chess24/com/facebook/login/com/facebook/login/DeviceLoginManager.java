/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 */
package com.facebook.login;

import android.net.Uri;
import com.facebook.login.LoginClient;
import com.facebook.login.LoginManager;
import java.util.Collection;

public class DeviceLoginManager
extends LoginManager {
    private static volatile DeviceLoginManager instance;
    private Uri deviceRedirectUri;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static DeviceLoginManager getInstance() {
        if (instance != null) return instance;
        synchronized (DeviceLoginManager.class) {
            if (instance != null) return instance;
            instance = new DeviceLoginManager();
            return instance;
        }
    }

    @Override
    protected LoginClient.Request createLoginRequest(Collection<String> object) {
        object = super.createLoginRequest((Collection<String>)object);
        Uri uri = this.getDeviceRedirectUri();
        if (uri != null) {
            object.setDeviceRedirectUriString(uri.toString());
        }
        return object;
    }

    public Uri getDeviceRedirectUri() {
        return this.deviceRedirectUri;
    }

    public void setDeviceRedirectUri(Uri uri) {
        this.deviceRedirectUri = uri;
    }
}
