/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.crashlytics.android.core;

import com.crashlytics.android.core.UserMetaData;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

class MetaDataStore {
    private static final String KEYDATA_SUFFIX = "keys";
    private static final String KEY_USER_EMAIL = "userEmail";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_USER_NAME = "userName";
    private static final String METADATA_EXT = ".meta";
    private static final String USERDATA_SUFFIX = "user";
    private static final Charset UTF_8 = Charset.forName("UTF-8");
    private final File filesDir;

    public MetaDataStore(File file) {
        this.filesDir = file;
    }

    private static Map<String, String> jsonToKeysData(String string) throws JSONException {
        string = new JSONObject(string);
        HashMap<String, String> hashMap = new HashMap<String, String>();
        Iterator iterator = string.keys();
        while (iterator.hasNext()) {
            String string2 = (String)iterator.next();
            hashMap.put(string2, MetaDataStore.valueOrNull((JSONObject)string, string2));
        }
        return hashMap;
    }

    private static UserMetaData jsonToUserData(String string) throws JSONException {
        string = new JSONObject(string);
        return new UserMetaData(MetaDataStore.valueOrNull((JSONObject)string, KEY_USER_ID), MetaDataStore.valueOrNull((JSONObject)string, KEY_USER_NAME), MetaDataStore.valueOrNull((JSONObject)string, KEY_USER_EMAIL));
    }

    private static String keysDataToJson(Map<String, String> map) throws JSONException {
        return new JSONObject(map).toString();
    }

    private static String userDataToJson(final UserMetaData userMetaData) throws JSONException {
        return new JSONObject(){
            {
                this.put(MetaDataStore.KEY_USER_ID, (Object)userMetaData.id);
                this.put(MetaDataStore.KEY_USER_NAME, (Object)userMetaData.name);
                this.put(MetaDataStore.KEY_USER_EMAIL, (Object)userMetaData.email);
            }
        }.toString();
    }

    private static String valueOrNull(JSONObject jSONObject, String string) {
        boolean bl = jSONObject.isNull(string);
        String string2 = null;
        if (!bl) {
            string2 = jSONObject.optString(string, null);
        }
        return string2;
    }

    public File getKeysFileForSession(String string) {
        File file = this.filesDir;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(KEYDATA_SUFFIX);
        stringBuilder.append(METADATA_EXT);
        return new File(file, stringBuilder.toString());
    }

    public File getUserDataFileForSession(String string) {
        File file = this.filesDir;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(USERDATA_SUFFIX);
        stringBuilder.append(METADATA_EXT);
        return new File(file, stringBuilder.toString());
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public Map<String, String> readKeyData(String object) {
        Object object2;
        block8 : {
            block9 : {
                object2 = this.getKeysFileForSession((String)object);
                if (!object2.exists()) {
                    return Collections.emptyMap();
                }
                Object var4_4 = null;
                object = null;
                object2 = new FileInputStream((File)object2);
                try {
                    object = MetaDataStore.jsonToKeysData(CommonUtils.streamToString((InputStream)object2));
                }
                catch (Throwable throwable) {
                    object = object2;
                    object2 = throwable;
                    break block8;
                }
                catch (Exception exception) {
                    break block9;
                }
                CommonUtils.closeOrLog((Closeable)object2, "Failed to close user metadata file.");
                return object;
                catch (Throwable throwable) {
                    break block8;
                }
                catch (Exception exception) {
                    object2 = var4_4;
                }
            }
            object = object2;
            {
                void var3_8;
                Fabric.getLogger().e("CrashlyticsCore", "Error deserializing user metadata.", (Throwable)var3_8);
            }
            CommonUtils.closeOrLog((Closeable)object2, "Failed to close user metadata file.");
            return Collections.emptyMap();
        }
        CommonUtils.closeOrLog((Closeable)object, "Failed to close user metadata file.");
        throw object2;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public UserMetaData readUserData(String object) {
        Object object2;
        block8 : {
            block9 : {
                object2 = this.getUserDataFileForSession((String)object);
                if (!object2.exists()) {
                    return UserMetaData.EMPTY;
                }
                Object var4_4 = null;
                object = null;
                object2 = new FileInputStream((File)object2);
                try {
                    object = MetaDataStore.jsonToUserData(CommonUtils.streamToString((InputStream)object2));
                }
                catch (Throwable throwable) {
                    object = object2;
                    object2 = throwable;
                    break block8;
                }
                catch (Exception exception) {
                    break block9;
                }
                CommonUtils.closeOrLog((Closeable)object2, "Failed to close user metadata file.");
                return object;
                catch (Throwable throwable) {
                    break block8;
                }
                catch (Exception exception) {
                    object2 = var4_4;
                }
            }
            object = object2;
            {
                void var3_8;
                Fabric.getLogger().e("CrashlyticsCore", "Error deserializing user metadata.", (Throwable)var3_8);
            }
            CommonUtils.closeOrLog((Closeable)object2, "Failed to close user metadata file.");
            return UserMetaData.EMPTY;
        }
        CommonUtils.closeOrLog((Closeable)object, "Failed to close user metadata file.");
        throw object2;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void writeKeyData(String object, Map<String, String> object2) {
        block7 : {
            block8 : {
                File file = this.getKeysFileForSession((String)object);
                Object var4_5 = null;
                Object var3_6 = null;
                object = var3_6;
                String string = MetaDataStore.keysDataToJson((Map<String, String>)object2);
                object = var3_6;
                object2 = new BufferedWriter(new OutputStreamWriter((OutputStream)new FileOutputStream(file), UTF_8));
                try {
                    object2.write(string);
                    object2.flush();
                }
                catch (Throwable throwable) {
                    object = object2;
                    object2 = throwable;
                    break block7;
                }
                catch (Exception exception) {
                    break block8;
                }
                CommonUtils.closeOrLog((Closeable)object2, "Failed to close key/value metadata file.");
                return;
                catch (Throwable throwable) {
                    break block7;
                }
                catch (Exception exception) {
                    object2 = var4_5;
                }
            }
            object = object2;
            {
                void var3_10;
                Fabric.getLogger().e("CrashlyticsCore", "Error serializing key/value metadata.", (Throwable)var3_10);
            }
            CommonUtils.closeOrLog((Closeable)object2, "Failed to close key/value metadata file.");
            return;
        }
        CommonUtils.closeOrLog((Closeable)object, "Failed to close key/value metadata file.");
        throw object2;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void writeUserData(String object, UserMetaData object2) {
        block7 : {
            block8 : {
                File file = this.getUserDataFileForSession((String)object);
                Object var4_5 = null;
                Object var3_6 = null;
                object = var3_6;
                String string = MetaDataStore.userDataToJson((UserMetaData)object2);
                object = var3_6;
                object2 = new BufferedWriter(new OutputStreamWriter((OutputStream)new FileOutputStream(file), UTF_8));
                try {
                    object2.write(string);
                    object2.flush();
                }
                catch (Throwable throwable) {
                    object = object2;
                    object2 = throwable;
                    break block7;
                }
                catch (Exception exception) {
                    break block8;
                }
                CommonUtils.closeOrLog((Closeable)object2, "Failed to close user metadata file.");
                return;
                catch (Throwable throwable) {
                    break block7;
                }
                catch (Exception exception) {
                    object2 = var4_5;
                }
            }
            object = object2;
            {
                void var3_10;
                Fabric.getLogger().e("CrashlyticsCore", "Error serializing user metadata.", (Throwable)var3_10);
            }
            CommonUtils.closeOrLog((Closeable)object2, "Failed to close user metadata file.");
            return;
        }
        CommonUtils.closeOrLog((Closeable)object, "Failed to close user metadata file.");
        throw object2;
    }

}
