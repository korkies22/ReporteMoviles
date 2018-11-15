/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.AsyncTask
 */
package de.cisha.android.board.util;

import android.os.AsyncTask;
import de.cisha.android.board.util.FileHelper;
import java.io.File;

static final class FileHelper
extends AsyncTask<File, Void, String> {
    final /* synthetic */ FileHelper.FileReaderDelegate val$delegate;

    FileHelper(FileHelper.FileReaderDelegate fileReaderDelegate) {
        this.val$delegate = fileReaderDelegate;
    }

    protected /* varargs */ String doInBackground(File ... arrfile) {
        return de.cisha.android.board.util.FileHelper.readFileAsString(arrfile[0]);
    }

    protected void onPostExecute(String string) {
        this.val$delegate.fileRead(string);
    }
}
