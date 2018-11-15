/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.net.Uri$Builder
 *  android.text.TextUtils
 */
package android.support.v4.content;

import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

static class FileProvider.SimplePathStrategy
implements FileProvider.PathStrategy {
    private final String mAuthority;
    private final HashMap<String, File> mRoots = new HashMap();

    FileProvider.SimplePathStrategy(String string) {
        this.mAuthority = string;
    }

    void addRoot(String string, File file) {
        if (TextUtils.isEmpty((CharSequence)string)) {
            throw new IllegalArgumentException("Name must not be empty");
        }
        try {
            File file2 = file.getCanonicalFile();
            this.mRoots.put(string, file2);
            return;
        }
        catch (IOException iOException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Failed to resolve canonical path for ");
            stringBuilder.append(file);
            throw new IllegalArgumentException(stringBuilder.toString(), iOException);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public File getFileForUri(Uri object) {
        Object object2 = object.getEncodedPath();
        int n = object2.indexOf(47, 1);
        Object object3 = Uri.decode((String)object2.substring(1, n));
        object2 = Uri.decode((String)object2.substring(n + 1));
        if ((object3 = this.mRoots.get(object3)) == null) {
            object3 = new StringBuilder();
            object3.append("Unable to find configured root for ");
            object3.append(object);
            throw new IllegalArgumentException(object3.toString());
        }
        object = new File((File)object3, (String)object2);
        try {
            object2 = object.getCanonicalFile();
            if (!object2.getPath().startsWith(object3.getPath())) {
                throw new SecurityException("Resolved path jumped beyond configured root");
            }
            return object2;
        }
        catch (IOException iOException) {}
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Failed to resolve canonical path for ");
        stringBuilder.append(object);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public Uri getUriForFile(File object) {
        CharSequence charSequence;
        void var2_8;
        try {
            charSequence = object.getCanonicalPath();
            object = null;
        }
        catch (IOException iOException) {}
        for (Map.Entry<String, File> entry : this.mRoots.entrySet()) {
            String string = entry.getValue().getPath();
            if (!charSequence.startsWith(string) || object != null && string.length() <= object.getValue().getPath().length()) continue;
            object = entry;
        }
        if (object == null) {
            object = new StringBuilder();
            object.append("Failed to find configured root that contains ");
            object.append((String)charSequence);
            throw new IllegalArgumentException(object.toString());
        }
        String string = ((File)object.getValue()).getPath();
        if (string.endsWith("/")) {
            String string2 = charSequence.substring(string.length());
        } else {
            String string3 = charSequence.substring(string.length() + 1);
        }
        charSequence = new StringBuilder();
        charSequence.append(Uri.encode((String)object.getKey()));
        charSequence.append('/');
        charSequence.append(Uri.encode((String)var2_8, (String)"/"));
        object = charSequence.toString();
        return new Uri.Builder().scheme("content").authority(this.mAuthority).encodedPath((String)object).build();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Failed to resolve canonical path for ");
        stringBuilder.append(object);
        throw new IllegalArgumentException(stringBuilder.toString());
    }
}
