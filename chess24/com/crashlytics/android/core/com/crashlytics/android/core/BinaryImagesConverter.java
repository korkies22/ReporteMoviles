/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.os.Build
 *  android.os.Build$VERSION
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.crashlytics.android.core;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import com.crashlytics.android.core.ProcMapEntry;
import com.crashlytics.android.core.ProcMapEntryParser;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Logger;
import java.io.File;
import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class BinaryImagesConverter {
    private static final String DATA_DIR = "/data";
    private final Context context;
    private final FileIdStrategy fileIdStrategy;

    BinaryImagesConverter(Context context, FileIdStrategy fileIdStrategy) {
        this.context = context;
        this.fileIdStrategy = fileIdStrategy;
    }

    private File correctDataPath(File file) {
        if (Build.VERSION.SDK_INT < 9) {
            return file;
        }
        if (file.getAbsolutePath().startsWith(DATA_DIR)) {
            try {
                File file2 = new File(this.context.getPackageManager().getApplicationInfo((String)this.context.getPackageName(), (int)0).nativeLibraryDir, file.getName());
                return file2;
            }
            catch (PackageManager.NameNotFoundException nameNotFoundException) {
                Fabric.getLogger().e("CrashlyticsCore", "Error getting ApplicationInfo", (Throwable)nameNotFoundException);
            }
        }
        return file;
    }

    private static JSONObject createBinaryImageJson(String string, ProcMapEntry procMapEntry) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("base_address", procMapEntry.address);
        jSONObject.put("size", procMapEntry.size);
        jSONObject.put("name", (Object)procMapEntry.path);
        jSONObject.put("uuid", (Object)string);
        return jSONObject;
    }

    private static byte[] generateBinaryImagesJsonString(JSONArray jSONArray) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("binary_images", (Object)jSONArray);
        }
        catch (JSONException jSONException) {
            Fabric.getLogger().w("CrashlyticsCore", "Binary images string is null", (Throwable)jSONException);
            return new byte[0];
        }
        return jSONObject.toString().getBytes();
    }

    private File getLibraryFile(String object) {
        File file = new File((String)object);
        object = file;
        if (!file.exists()) {
            object = this.correctDataPath(file);
        }
        return object;
    }

    private static boolean isRelevant(ProcMapEntry procMapEntry) {
        if (procMapEntry.perms.indexOf(120) != -1 && procMapEntry.path.indexOf(47) != -1) {
            return true;
        }
        return false;
    }

    private static String joinMapsEntries(JSONArray jSONArray) throws JSONException {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < jSONArray.length(); ++i) {
            stringBuilder.append(jSONArray.getString(i));
        }
        return stringBuilder.toString();
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private JSONArray parseProcMapsJson(String arrstring) {
        JSONArray jSONArray = new JSONArray();
        try {}
        catch (JSONException jSONException) {
            Fabric.getLogger().w("CrashlyticsCore", "Unable to parse proc maps string", (Throwable)jSONException);
            return jSONArray;
        }
        arrstring = BinaryImagesConverter.joinMapsEntries(new JSONObject((String)arrstring).getJSONArray("maps"));
        arrstring = arrstring.split("\\|");
        int i = 0;
        while (i < ((Object)arrstring).length) {
            block8 : {
                ProcMapEntry procMapEntry = ProcMapEntryParser.parse((String)arrstring[i]);
                if (procMapEntry != null && BinaryImagesConverter.isRelevant(procMapEntry)) {
                    Object object = this.getLibraryFile(procMapEntry.path);
                    object = this.fileIdStrategy.createId((File)object);
                    try {
                        jSONArray.put((Object)BinaryImagesConverter.createBinaryImageJson((String)object, procMapEntry));
                    }
                    catch (JSONException jSONException) {
                        Fabric.getLogger().d("CrashlyticsCore", "Could not create a binary image json string", (Throwable)jSONException);
                    }
                    break block8;
                    catch (IOException iOException) {
                        Logger logger = Fabric.getLogger();
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Could not generate ID for file ");
                        stringBuilder.append(procMapEntry.path);
                        logger.d("CrashlyticsCore", stringBuilder.toString(), iOException);
                    }
                }
            }
            ++i;
        }
        return jSONArray;
    }

    byte[] convert(String string) throws IOException {
        return BinaryImagesConverter.generateBinaryImagesJsonString(this.parseProcMapsJson(string));
    }

    static interface FileIdStrategy {
        public String createId(File var1) throws IOException;
    }

}
