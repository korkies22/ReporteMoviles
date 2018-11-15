/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.database.ContentObserver
 *  android.database.Cursor
 *  android.net.Uri
 */
package android.support.v4.content;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.ContentResolverCompat;
import android.support.v4.content.Loader;
import android.support.v4.os.CancellationSignal;
import android.support.v4.os.OperationCanceledException;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Arrays;

public class CursorLoader
extends AsyncTaskLoader<Cursor> {
    CancellationSignal mCancellationSignal;
    Cursor mCursor;
    final Loader<Cursor> mObserver = new Loader.ForceLoadContentObserver(this);
    String[] mProjection;
    String mSelection;
    String[] mSelectionArgs;
    String mSortOrder;
    Uri mUri;

    public CursorLoader(@NonNull Context context) {
        super(context);
    }

    public CursorLoader(@NonNull Context context, @NonNull Uri uri, @Nullable String[] arrstring, @Nullable String string, @Nullable String[] arrstring2, @Nullable String string2) {
        super(context);
        this.mUri = uri;
        this.mProjection = arrstring;
        this.mSelection = string;
        this.mSelectionArgs = arrstring2;
        this.mSortOrder = string2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void cancelLoadInBackground() {
        super.cancelLoadInBackground();
        synchronized (this) {
            if (this.mCancellationSignal != null) {
                this.mCancellationSignal.cancel();
            }
            return;
        }
    }

    @Override
    public void deliverResult(Cursor cursor) {
        if (this.isReset()) {
            if (cursor != null) {
                cursor.close();
            }
            return;
        }
        Cursor cursor2 = this.mCursor;
        this.mCursor = cursor;
        if (this.isStarted()) {
            super.deliverResult(cursor);
        }
        if (cursor2 != null && cursor2 != cursor && !cursor2.isClosed()) {
            cursor2.close();
        }
    }

    @Override
    public void dump(String string, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        super.dump(string, fileDescriptor, printWriter, arrstring);
        printWriter.print(string);
        printWriter.print("mUri=");
        printWriter.println((Object)this.mUri);
        printWriter.print(string);
        printWriter.print("mProjection=");
        printWriter.println(Arrays.toString(this.mProjection));
        printWriter.print(string);
        printWriter.print("mSelection=");
        printWriter.println(this.mSelection);
        printWriter.print(string);
        printWriter.print("mSelectionArgs=");
        printWriter.println(Arrays.toString(this.mSelectionArgs));
        printWriter.print(string);
        printWriter.print("mSortOrder=");
        printWriter.println(this.mSortOrder);
        printWriter.print(string);
        printWriter.print("mCursor=");
        printWriter.println((Object)this.mCursor);
        printWriter.print(string);
        printWriter.print("mContentChanged=");
        printWriter.println(this.mContentChanged);
    }

    @Nullable
    public String[] getProjection() {
        return this.mProjection;
    }

    @Nullable
    public String getSelection() {
        return this.mSelection;
    }

    @Nullable
    public String[] getSelectionArgs() {
        return this.mSelectionArgs;
    }

    @Nullable
    public String getSortOrder() {
        return this.mSortOrder;
    }

    @NonNull
    public Uri getUri() {
        return this.mUri;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public Cursor loadInBackground() {
        synchronized (this) {
            if (this.isLoadInBackgroundCanceled()) {
                throw new OperationCanceledException();
            }
            this.mCancellationSignal = new CancellationSignal();
        }
        try {
            Cursor cursor = ContentResolverCompat.query(this.getContext().getContentResolver(), this.mUri, this.mProjection, this.mSelection, this.mSelectionArgs, this.mSortOrder, this.mCancellationSignal);
            if (cursor == null) return cursor;
            try {
                cursor.getCount();
                cursor.registerContentObserver(this.mObserver);
                return cursor;
            }
            catch (RuntimeException runtimeException) {
                cursor.close();
                throw runtimeException;
            }
        }
        finally {
            synchronized (this) {
                this.mCancellationSignal = null;
            }
        }
    }

    @Override
    public void onCanceled(Cursor cursor) {
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
    }

    @Override
    protected void onReset() {
        super.onReset();
        this.onStopLoading();
        if (this.mCursor != null && !this.mCursor.isClosed()) {
            this.mCursor.close();
        }
        this.mCursor = null;
    }

    @Override
    protected void onStartLoading() {
        if (this.mCursor != null) {
            this.deliverResult(this.mCursor);
        }
        if (this.takeContentChanged() || this.mCursor == null) {
            this.forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        this.cancelLoad();
    }

    public void setProjection(@Nullable String[] arrstring) {
        this.mProjection = arrstring;
    }

    public void setSelection(@Nullable String string) {
        this.mSelection = string;
    }

    public void setSelectionArgs(@Nullable String[] arrstring) {
        this.mSelectionArgs = arrstring;
    }

    public void setSortOrder(@Nullable String string) {
        this.mSortOrder = string;
    }

    public void setUri(@NonNull Uri uri) {
        this.mUri = uri;
    }
}
