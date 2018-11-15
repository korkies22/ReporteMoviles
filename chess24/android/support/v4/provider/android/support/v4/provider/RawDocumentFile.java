/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.util.Log
 *  android.webkit.MimeTypeMap
 */
package android.support.v4.provider;

import android.net.Uri;
import android.support.v4.provider.DocumentFile;
import android.util.Log;
import android.webkit.MimeTypeMap;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

class RawDocumentFile
extends DocumentFile {
    private File mFile;

    RawDocumentFile(DocumentFile documentFile, File file) {
        super(documentFile);
        this.mFile = file;
    }

    private static boolean deleteContents(File arrfile) {
        arrfile = arrfile.listFiles();
        boolean bl = true;
        if (arrfile != null) {
            int n = arrfile.length;
            bl = true;
            for (int i = 0; i < n; ++i) {
                File file = arrfile[i];
                boolean bl2 = bl;
                if (file.isDirectory()) {
                    bl2 = bl & RawDocumentFile.deleteContents(file);
                }
                bl = bl2;
                if (file.delete()) continue;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Failed to delete ");
                stringBuilder.append(file);
                Log.w((String)"DocumentFile", (String)stringBuilder.toString());
                bl = false;
            }
        }
        return bl;
    }

    private static String getTypeForName(String string) {
        int n = string.lastIndexOf(46);
        if (n >= 0) {
            string = string.substring(n + 1).toLowerCase();
            string = MimeTypeMap.getSingleton().getMimeTypeFromExtension(string);
            if (string != null) {
                return string;
            }
        }
        return "application/octet-stream";
    }

    @Override
    public boolean canRead() {
        return this.mFile.canRead();
    }

    @Override
    public boolean canWrite() {
        return this.mFile.canWrite();
    }

    @Override
    public DocumentFile createDirectory(String object) {
        if (!(object = new File(this.mFile, (String)object)).isDirectory() && !object.mkdir()) {
            return null;
        }
        return new RawDocumentFile(this, (File)object);
    }

    @Override
    public DocumentFile createFile(String object, String charSequence) {
        String string = MimeTypeMap.getSingleton().getExtensionFromMimeType((String)object);
        object = charSequence;
        if (string != null) {
            object = new StringBuilder();
            object.append((String)charSequence);
            object.append(".");
            object.append(string);
            object = object.toString();
        }
        object = new File(this.mFile, (String)object);
        try {
            object.createNewFile();
            object = new RawDocumentFile(this, (File)object);
            return object;
        }
        catch (IOException iOException) {
            charSequence = new StringBuilder();
            charSequence.append("Failed to createFile: ");
            charSequence.append(iOException);
            Log.w((String)"DocumentFile", (String)charSequence.toString());
            return null;
        }
    }

    @Override
    public boolean delete() {
        RawDocumentFile.deleteContents(this.mFile);
        return this.mFile.delete();
    }

    @Override
    public boolean exists() {
        return this.mFile.exists();
    }

    @Override
    public String getName() {
        return this.mFile.getName();
    }

    @Override
    public String getType() {
        if (this.mFile.isDirectory()) {
            return null;
        }
        return RawDocumentFile.getTypeForName(this.mFile.getName());
    }

    @Override
    public Uri getUri() {
        return Uri.fromFile((File)this.mFile);
    }

    @Override
    public boolean isDirectory() {
        return this.mFile.isDirectory();
    }

    @Override
    public boolean isFile() {
        return this.mFile.isFile();
    }

    @Override
    public boolean isVirtual() {
        return false;
    }

    @Override
    public long lastModified() {
        return this.mFile.lastModified();
    }

    @Override
    public long length() {
        return this.mFile.length();
    }

    @Override
    public DocumentFile[] listFiles() {
        ArrayList<RawDocumentFile> arrayList = new ArrayList<RawDocumentFile>();
        File[] arrfile = this.mFile.listFiles();
        if (arrfile != null) {
            int n = arrfile.length;
            for (int i = 0; i < n; ++i) {
                arrayList.add(new RawDocumentFile(this, arrfile[i]));
            }
        }
        return arrayList.toArray(new DocumentFile[arrayList.size()]);
    }

    @Override
    public boolean renameTo(String object) {
        object = new File(this.mFile.getParentFile(), (String)object);
        if (this.mFile.renameTo((File)object)) {
            this.mFile = object;
            return true;
        }
        return false;
    }
}
