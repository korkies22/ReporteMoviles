/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.crashlytics.android.core;

import com.crashlytics.android.core.UserMetaData;
import org.json.JSONException;
import org.json.JSONObject;

static final class MetaDataStore
extends JSONObject {
    final /* synthetic */ UserMetaData val$userData;

    MetaDataStore(UserMetaData userMetaData) throws JSONException {
        this.val$userData = userMetaData;
        this.put(com.crashlytics.android.core.MetaDataStore.KEY_USER_ID, (Object)this.val$userData.id);
        this.put(com.crashlytics.android.core.MetaDataStore.KEY_USER_NAME, (Object)this.val$userData.name);
        this.put(com.crashlytics.android.core.MetaDataStore.KEY_USER_EMAIL, (Object)this.val$userData.email);
    }
}
