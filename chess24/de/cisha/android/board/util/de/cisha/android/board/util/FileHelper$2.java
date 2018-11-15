/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.AsyncTask
 */
package de.cisha.android.board.util;

import android.os.AsyncTask;
import de.cisha.android.board.util.FileHelper;
import java.io.InputStream;
import java.nio.charset.Charset;

static final class FileHelper
extends AsyncTask<InputStream, Void, String> {
    final /* synthetic */ FileHelper.FileReaderDelegate val$delegate;
    final /* synthetic */ Charset val$optionalCharset;

    FileHelper(Charset charset, FileHelper.FileReaderDelegate fileReaderDelegate) {
        this.val$optionalCharset = charset;
        this.val$delegate = fileReaderDelegate;
    }

    protected /* varargs */ String doInBackground(InputStream ... arrinputStream) {
        return de.cisha.android.board.util.FileHelper.readStreamAsString(arrinputStream[0], this.val$optionalCharset);
    }

    protected void onPostExecute(String string) {
        this.val$delegate.fileRead(string);
    }
}
