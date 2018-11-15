/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$CompressFormat
 *  android.net.Uri
 *  android.util.Log
 */
package com.facebook.internal;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import com.facebook.FacebookContentProvider;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.UUID;

public final class NativeAppCallAttachmentStore {
    static final String ATTACHMENTS_DIR_NAME = "com.facebook.NativeAppCallAttachmentStore.files";
    private static final String TAG = "com.facebook.internal.NativeAppCallAttachmentStore";
    private static File attachmentsDirectory;

    private NativeAppCallAttachmentStore() {
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static void addAttachments(Collection<Attachment> object) {
        if (object == null) return;
        if (object.size() == 0) {
            return;
        }
        if (attachmentsDirectory == null) {
            NativeAppCallAttachmentStore.cleanupAllAttachments();
        }
        NativeAppCallAttachmentStore.ensureAttachmentsDirectoryExists();
        Object object2 = new ArrayList<File>();
        try {
            object = object.iterator();
            while (object.hasNext()) {
                Attachment attachment = (Attachment)object.next();
                if (!attachment.shouldCreateFile) continue;
                File file = NativeAppCallAttachmentStore.getAttachmentFile(attachment.callId, attachment.attachmentName, true);
                object2.add(file);
                if (attachment.bitmap != null) {
                    NativeAppCallAttachmentStore.processAttachmentBitmap(attachment.bitmap, file);
                    continue;
                }
                if (attachment.originalUri == null) continue;
                NativeAppCallAttachmentStore.processAttachmentFile(attachment.originalUri, attachment.isContentUri, file);
            }
            return;
        }
        catch (IOException iOException) {
            Object object3 = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Got unexpected exception:");
            stringBuilder.append(iOException);
            Log.e((String)object3, (String)stringBuilder.toString());
            object2 = object2.iterator();
            if (!object2.hasNext()) throw new FacebookException(iOException);
            object3 = (File)object2.next();
            try {
                object3.delete();
            }
            catch (Exception exception) {
            }
        }
    }

    public static void cleanupAllAttachments() {
        Utility.deleteDirectory(NativeAppCallAttachmentStore.getAttachmentsDirectory());
    }

    public static void cleanupAttachmentsForCall(UUID comparable) {
        if ((comparable = NativeAppCallAttachmentStore.getAttachmentsDirectoryForCall(comparable, false)) != null) {
            Utility.deleteDirectory((File)comparable);
        }
    }

    public static Attachment createAttachment(UUID uUID, Bitmap bitmap) {
        Validate.notNull(uUID, "callId");
        Validate.notNull((Object)bitmap, "attachmentBitmap");
        return new Attachment(uUID, bitmap, null);
    }

    public static Attachment createAttachment(UUID uUID, Uri uri) {
        Validate.notNull(uUID, "callId");
        Validate.notNull((Object)uri, "attachmentUri");
        return new Attachment(uUID, null, uri);
    }

    static File ensureAttachmentsDirectoryExists() {
        File file = NativeAppCallAttachmentStore.getAttachmentsDirectory();
        file.mkdirs();
        return file;
    }

    static File getAttachmentFile(UUID comparable, String string, boolean bl) throws IOException {
        if ((comparable = NativeAppCallAttachmentStore.getAttachmentsDirectoryForCall(comparable, bl)) == null) {
            return null;
        }
        try {
            comparable = new File((File)comparable, URLEncoder.encode(string, "UTF-8"));
            return comparable;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            return null;
        }
    }

    static File getAttachmentsDirectory() {
        synchronized (NativeAppCallAttachmentStore.class) {
            if (attachmentsDirectory == null) {
                attachmentsDirectory = new File(FacebookSdk.getApplicationContext().getCacheDir(), ATTACHMENTS_DIR_NAME);
            }
            File file = attachmentsDirectory;
            return file;
        }
    }

    static File getAttachmentsDirectoryForCall(UUID comparable, boolean bl) {
        if (attachmentsDirectory == null) {
            return null;
        }
        comparable = new File(attachmentsDirectory, comparable.toString());
        if (bl && !comparable.exists()) {
            comparable.mkdirs();
        }
        return comparable;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static File openAttachment(UUID comparable, String string) throws FileNotFoundException {
        void var1_3;
        if (Utility.isNullOrEmpty((String)var1_3)) throw new FileNotFoundException();
        if (comparable == null) throw new FileNotFoundException();
        try {
            return NativeAppCallAttachmentStore.getAttachmentFile(comparable, (String)var1_3, false);
        }
        catch (IOException iOException) {
            throw new FileNotFoundException();
        }
    }

    private static void processAttachmentBitmap(Bitmap bitmap, File object) throws IOException {
        object = new FileOutputStream((File)object);
        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, (OutputStream)object);
            return;
        }
        finally {
            Utility.closeQuietly((Closeable)object);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static void processAttachmentFile(Uri var0, boolean var1_2, File var2_3) throws IOException {
        var2_6 = new FileOutputStream((File)var2_6);
        if (var1_5 != false) ** GOTO lbl6
        try {
            block2 : {
                var0_1 = new FileInputStream(var0 /* !! */ .getPath());
                break block2;
lbl6: // 1 sources:
                var0_2 = FacebookSdk.getApplicationContext().getContentResolver().openInputStream(var0 /* !! */ );
            }
            Utility.copyAndCloseInputStream((InputStream)var0_3, var2_6);
        }
        catch (Throwable var0_4) {}
        Utility.closeQuietly(var2_6);
        return;
        Utility.closeQuietly(var2_6);
        throw var0_4;
    }

    public static final class Attachment {
        private final String attachmentName;
        private final String attachmentUrl;
        private Bitmap bitmap;
        private final UUID callId;
        private boolean isContentUri;
        private Uri originalUri;
        private boolean shouldCreateFile;

        private Attachment(UUID object, Bitmap object2, Uri uri) {
            block12 : {
                block11 : {
                    block10 : {
                        this.callId = object;
                        this.bitmap = object2;
                        this.originalUri = uri;
                        boolean bl = true;
                        if (uri == null) break block10;
                        object2 = uri.getScheme();
                        if ("content".equalsIgnoreCase((String)object2)) {
                            this.isContentUri = true;
                            if (uri.getAuthority() == null || uri.getAuthority().startsWith("media")) {
                                bl = false;
                            }
                            this.shouldCreateFile = bl;
                        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
                            this.shouldCreateFile = true;
                        } else if (!Utility.isWebUri(uri)) {
                            object = new StringBuilder();
                            object.append("Unsupported scheme for media Uri : ");
                            object.append((String)object2);
                            throw new FacebookException(object.toString());
                        }
                        break block11;
                    }
                    if (object2 == null) break block12;
                    this.shouldCreateFile = true;
                }
                object2 = !this.shouldCreateFile ? null : UUID.randomUUID().toString();
                this.attachmentName = object2;
                object = !this.shouldCreateFile ? this.originalUri.toString() : FacebookContentProvider.getAttachmentUrl(FacebookSdk.getApplicationId(), (UUID)object, this.attachmentName);
                this.attachmentUrl = object;
                return;
            }
            throw new FacebookException("Cannot share media without a bitmap or Uri set");
        }

        public String getAttachmentUrl() {
            return this.attachmentUrl;
        }
    }

}
