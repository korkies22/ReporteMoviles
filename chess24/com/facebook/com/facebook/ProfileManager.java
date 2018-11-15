/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.os.Parcelable
 */
package com.facebook;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.content.LocalBroadcastManager;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileCache;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;

public final class ProfileManager {
    public static final String ACTION_CURRENT_PROFILE_CHANGED = "com.facebook.sdk.ACTION_CURRENT_PROFILE_CHANGED";
    public static final String EXTRA_NEW_PROFILE = "com.facebook.sdk.EXTRA_NEW_PROFILE";
    public static final String EXTRA_OLD_PROFILE = "com.facebook.sdk.EXTRA_OLD_PROFILE";
    private static volatile ProfileManager instance;
    private Profile currentProfile;
    private final LocalBroadcastManager localBroadcastManager;
    private final ProfileCache profileCache;

    ProfileManager(LocalBroadcastManager localBroadcastManager, ProfileCache profileCache) {
        Validate.notNull(localBroadcastManager, "localBroadcastManager");
        Validate.notNull(profileCache, "profileCache");
        this.localBroadcastManager = localBroadcastManager;
        this.profileCache = profileCache;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static ProfileManager getInstance() {
        if (instance != null) return instance;
        synchronized (ProfileManager.class) {
            if (instance != null) return instance;
            instance = new ProfileManager(LocalBroadcastManager.getInstance(FacebookSdk.getApplicationContext()), new ProfileCache());
            return instance;
        }
    }

    private void sendCurrentProfileChangedBroadcast(Profile profile, Profile profile2) {
        Intent intent = new Intent(ACTION_CURRENT_PROFILE_CHANGED);
        intent.putExtra(EXTRA_OLD_PROFILE, (Parcelable)profile);
        intent.putExtra(EXTRA_NEW_PROFILE, (Parcelable)profile2);
        this.localBroadcastManager.sendBroadcast(intent);
    }

    private void setCurrentProfile(Profile profile, boolean bl) {
        Profile profile2 = this.currentProfile;
        this.currentProfile = profile;
        if (bl) {
            if (profile != null) {
                this.profileCache.save(profile);
            } else {
                this.profileCache.clear();
            }
        }
        if (!Utility.areObjectsEqual(profile2, profile)) {
            this.sendCurrentProfileChangedBroadcast(profile2, profile);
        }
    }

    Profile getCurrentProfile() {
        return this.currentProfile;
    }

    boolean loadCurrentProfile() {
        Profile profile = this.profileCache.load();
        if (profile != null) {
            this.setCurrentProfile(profile, false);
            return true;
        }
        return false;
    }

    void setCurrentProfile(Profile profile) {
        this.setCurrentProfile(profile, true);
    }
}
