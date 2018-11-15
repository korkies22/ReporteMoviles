/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.database.Cursor
 *  android.net.Uri
 *  android.provider.DocumentsContract
 *  android.util.Log
 */
package android.support.v4.provider;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.support.annotation.RequiresApi;
import android.support.v4.provider.DocumentFile;
import android.support.v4.provider.DocumentsContractApi19;
import android.util.Log;
import java.util.ArrayList;

@RequiresApi(value=21)
class TreeDocumentFile
extends DocumentFile {
    private Context mContext;
    private Uri mUri;

    TreeDocumentFile(DocumentFile documentFile, Context context, Uri uri) {
        super(documentFile);
        this.mContext = context;
        this.mUri = uri;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static void closeQuietly(AutoCloseable autoCloseable) {
        if (autoCloseable == null) return;
        try {
            autoCloseable.close();
            return;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (Exception exception) {
            return;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static Uri createFile(Context context, Uri uri, String string, String string2) {
        try {
            return DocumentsContract.createDocument((ContentResolver)context.getContentResolver(), (Uri)uri, (String)string, (String)string2);
        }
        catch (Exception exception) {
            return null;
        }
    }

    @Override
    public boolean canRead() {
        return DocumentsContractApi19.canRead(this.mContext, this.mUri);
    }

    @Override
    public boolean canWrite() {
        return DocumentsContractApi19.canWrite(this.mContext, this.mUri);
    }

    @Override
    public DocumentFile createDirectory(String string) {
        if ((string = TreeDocumentFile.createFile(this.mContext, this.mUri, "vnd.android.document/directory", string)) != null) {
            return new TreeDocumentFile(this, this.mContext, (Uri)string);
        }
        return null;
    }

    @Override
    public DocumentFile createFile(String string, String string2) {
        if ((string = TreeDocumentFile.createFile(this.mContext, this.mUri, string, string2)) != null) {
            return new TreeDocumentFile(this, this.mContext, (Uri)string);
        }
        return null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean delete() {
        try {
            return DocumentsContract.deleteDocument((ContentResolver)this.mContext.getContentResolver(), (Uri)this.mUri);
        }
        catch (Exception exception) {
            return false;
        }
    }

    @Override
    public boolean exists() {
        return DocumentsContractApi19.exists(this.mContext, this.mUri);
    }

    @Override
    public String getName() {
        return DocumentsContractApi19.getName(this.mContext, this.mUri);
    }

    @Override
    public String getType() {
        return DocumentsContractApi19.getType(this.mContext, this.mUri);
    }

    @Override
    public Uri getUri() {
        return this.mUri;
    }

    @Override
    public boolean isDirectory() {
        return DocumentsContractApi19.isDirectory(this.mContext, this.mUri);
    }

    @Override
    public boolean isFile() {
        return DocumentsContractApi19.isFile(this.mContext, this.mUri);
    }

    @Override
    public boolean isVirtual() {
        return DocumentsContractApi19.isVirtual(this.mContext, this.mUri);
    }

    @Override
    public long lastModified() {
        return DocumentsContractApi19.lastModified(this.mContext, this.mUri);
    }

    @Override
    public long length() {
        return DocumentsContractApi19.length(this.mContext, this.mUri);
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public DocumentFile[] listFiles() {
        DocumentFile[] arrdocumentFile;
        Object object;
        block9 : {
            ArrayList<Uri> arrayList;
            int n;
            block11 : {
                Exception exception;
                Object object2;
                block10 : {
                    arrdocumentFile = this.mContext.getContentResolver();
                    object2 = DocumentsContract.buildChildDocumentsUriUsingTree((Uri)this.mUri, (String)DocumentsContract.getDocumentId((Uri)this.mUri));
                    arrayList = new ArrayList<Uri>();
                    n = 0;
                    exception = null;
                    object = null;
                    arrdocumentFile = arrdocumentFile.query((Uri)object2, new String[]{"document_id"}, null, null, null);
                    try {
                        while (arrdocumentFile.moveToNext()) {
                            object = arrdocumentFile.getString(0);
                            arrayList.add(DocumentsContract.buildDocumentUriUsingTree((Uri)this.mUri, (String)object));
                        }
                    }
                    catch (Throwable throwable) {
                        object = arrdocumentFile;
                        arrdocumentFile = throwable;
                        break block9;
                    }
                    catch (Exception exception2) {
                        break block10;
                    }
                    TreeDocumentFile.closeQuietly((AutoCloseable)arrdocumentFile);
                    break block11;
                    catch (Throwable throwable) {
                        break block9;
                    }
                    catch (Exception exception3) {
                        arrdocumentFile = exception;
                        exception = exception3;
                    }
                }
                object = arrdocumentFile;
                {
                    object2 = new StringBuilder();
                    object = arrdocumentFile;
                    object2.append("Failed query: ");
                    object = arrdocumentFile;
                    object2.append(exception);
                    object = arrdocumentFile;
                    Log.w((String)"DocumentFile", (String)object2.toString());
                }
                TreeDocumentFile.closeQuietly((AutoCloseable)arrdocumentFile);
            }
            object = arrayList.toArray((T[])new Uri[arrayList.size()]);
            arrdocumentFile = new DocumentFile[((ContentResolver)object).length];
            while (n < ((ContentResolver)object).length) {
                arrdocumentFile[n] = new TreeDocumentFile(this, this.mContext, (Uri)object[n]);
                ++n;
            }
            return arrdocumentFile;
        }
        TreeDocumentFile.closeQuietly(object);
        throw arrdocumentFile;
    }

    @Override
    public boolean renameTo(String string) {
        block3 : {
            try {
                string = DocumentsContract.renameDocument((ContentResolver)this.mContext.getContentResolver(), (Uri)this.mUri, (String)string);
                if (string == null) break block3;
            }
            catch (Exception exception) {
                return false;
            }
            this.mUri = string;
            return true;
        }
        return false;
    }
}
