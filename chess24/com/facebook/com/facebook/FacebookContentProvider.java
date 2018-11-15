/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ContentProvider
 *  android.content.ContentValues
 *  android.database.Cursor
 *  android.net.Uri
 *  android.os.ParcelFileDescriptor
 *  android.util.Log
 *  android.util.Pair
 */
package com.facebook;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.util.Pair;
import com.facebook.internal.NativeAppCallAttachmentStore;
import java.io.FileNotFoundException;
import java.util.UUID;

public class FacebookContentProvider
extends ContentProvider {
    private static final String ATTACHMENT_URL_BASE = "content://com.facebook.app.FacebookContentProvider";
    private static final String INVALID_FILE_NAME = "..";
    private static final String TAG = "com.facebook.FacebookContentProvider";

    public static String getAttachmentUrl(String string, UUID uUID, String string2) {
        return String.format("%s%s/%s/%s", ATTACHMENT_URL_BASE, string, uUID.toString(), string2);
    }

    public int delete(Uri uri, String string, String[] arrstring) {
        return 0;
    }

    public String getType(Uri uri) {
        return null;
    }

    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    public boolean onCreate() {
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public ParcelFileDescriptor openFile(Uri parcelFileDescriptor, String string) throws FileNotFoundException {
        if ((parcelFileDescriptor = this.parseCallIdAndAttachmentName((Uri)parcelFileDescriptor)) == null) {
            throw new FileNotFoundException();
        }
        try {
            parcelFileDescriptor = NativeAppCallAttachmentStore.openAttachment((UUID)parcelFileDescriptor.first, (String)parcelFileDescriptor.second);
            if (parcelFileDescriptor != null) return ParcelFileDescriptor.open(parcelFileDescriptor, (int)268435456);
        }
        catch (FileNotFoundException fileNotFoundException) {
            string = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Got unexpected exception:");
            stringBuilder.append(fileNotFoundException);
            Log.e((String)string, (String)stringBuilder.toString());
            throw fileNotFoundException;
        }
        throw new FileNotFoundException();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    Pair<UUID, String> parseCallIdAndAttachmentName(Uri object) {
        String[] arrstring;
        try {
            arrstring = object.getPath().substring(1).split("/");
        }
        catch (Exception exception) {
            return null;
        }
        String string = arrstring[0];
        String string2 = arrstring[1];
        if (INVALID_FILE_NAME.contentEquals(string) || INVALID_FILE_NAME.contentEquals(string2)) throw new Exception();
        return new Pair((Object)UUID.fromString(string), (Object)string2);
    }

    public Cursor query(Uri uri, String[] arrstring, String string, String[] arrstring2, String string2) {
        return null;
    }

    public int update(Uri uri, ContentValues contentValues, String string, String[] arrstring) {
        return 0;
    }
}
