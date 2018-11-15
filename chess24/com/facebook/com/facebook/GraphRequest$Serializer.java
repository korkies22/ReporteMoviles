/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$CompressFormat
 *  android.net.Uri
 *  android.os.ParcelFileDescriptor
 *  android.os.ParcelFileDescriptor$AutoCloseInputStream
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.facebook;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.ProgressNoopOutputStream;
import com.facebook.RequestOutputStream;
import com.facebook.internal.Logger;
import com.facebook.internal.Utility;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

private static class GraphRequest.Serializer
implements GraphRequest.KeyValueSerializer {
    private boolean firstWrite = true;
    private final Logger logger;
    private final OutputStream outputStream;
    private boolean useUrlEncode = false;

    public GraphRequest.Serializer(OutputStream outputStream, Logger logger, boolean bl) {
        this.outputStream = outputStream;
        this.logger = logger;
        this.useUrlEncode = bl;
    }

    private RuntimeException getInvalidTypeError() {
        return new IllegalArgumentException("value is not a supported type.");
    }

    public /* varargs */ void write(String string, Object ... arrobject) throws IOException {
        if (!this.useUrlEncode) {
            if (this.firstWrite) {
                this.outputStream.write("--".getBytes());
                this.outputStream.write(GraphRequest.MIME_BOUNDARY.getBytes());
                this.outputStream.write("\r\n".getBytes());
                this.firstWrite = false;
            }
            this.outputStream.write(String.format(string, arrobject).getBytes());
            return;
        }
        this.outputStream.write(URLEncoder.encode(String.format(Locale.US, string, arrobject), "UTF-8").getBytes());
    }

    public void writeBitmap(String string, Bitmap object) throws IOException {
        this.writeContentDisposition(string, string, "image/png");
        object.compress(Bitmap.CompressFormat.PNG, 100, this.outputStream);
        this.writeLine("", new Object[0]);
        this.writeRecordBoundary();
        if (this.logger != null) {
            object = this.logger;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("    ");
            stringBuilder.append(string);
            object.appendKeyValue(stringBuilder.toString(), "<Image>");
        }
    }

    public void writeBytes(String string, byte[] arrby) throws IOException {
        this.writeContentDisposition(string, string, "content/unknown");
        this.outputStream.write(arrby);
        this.writeLine("", new Object[0]);
        this.writeRecordBoundary();
        if (this.logger != null) {
            Logger logger = this.logger;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("    ");
            stringBuilder.append(string);
            logger.appendKeyValue(stringBuilder.toString(), String.format(Locale.ROOT, "<Data: %d>", arrby.length));
        }
    }

    public void writeContentDisposition(String string, String string2, String string3) throws IOException {
        if (!this.useUrlEncode) {
            this.write("Content-Disposition: form-data; name=\"%s\"", string);
            if (string2 != null) {
                this.write("; filename=\"%s\"", string2);
            }
            this.writeLine("", new Object[0]);
            if (string3 != null) {
                this.writeLine("%s: %s", GraphRequest.CONTENT_TYPE_HEADER, string3);
            }
            this.writeLine("", new Object[0]);
            return;
        }
        this.outputStream.write(String.format("%s=", string).getBytes());
    }

    public void writeContentUri(String string, Uri object, String charSequence) throws IOException {
        int n;
        String string2 = charSequence;
        if (charSequence == null) {
            string2 = "content/unknown";
        }
        this.writeContentDisposition(string, string, string2);
        if (this.outputStream instanceof ProgressNoopOutputStream) {
            long l = Utility.getContentSize((Uri)object);
            ((ProgressNoopOutputStream)this.outputStream).addProgress(l);
            n = 0;
        } else {
            n = Utility.copyAndCloseInputStream(FacebookSdk.getApplicationContext().getContentResolver().openInputStream((Uri)object), this.outputStream) + 0;
        }
        this.writeLine("", new Object[0]);
        this.writeRecordBoundary();
        if (this.logger != null) {
            object = this.logger;
            charSequence = new StringBuilder();
            charSequence.append("    ");
            charSequence.append(string);
            object.appendKeyValue(charSequence.toString(), String.format(Locale.ROOT, "<Data: %d>", n));
        }
    }

    public void writeFile(String string, ParcelFileDescriptor object, String charSequence) throws IOException {
        int n;
        String string2 = charSequence;
        if (charSequence == null) {
            string2 = "content/unknown";
        }
        this.writeContentDisposition(string, string, string2);
        if (this.outputStream instanceof ProgressNoopOutputStream) {
            ((ProgressNoopOutputStream)this.outputStream).addProgress(object.getStatSize());
            n = 0;
        } else {
            n = Utility.copyAndCloseInputStream((InputStream)new ParcelFileDescriptor.AutoCloseInputStream((ParcelFileDescriptor)object), this.outputStream) + 0;
        }
        this.writeLine("", new Object[0]);
        this.writeRecordBoundary();
        if (this.logger != null) {
            object = this.logger;
            charSequence = new StringBuilder();
            charSequence.append("    ");
            charSequence.append(string);
            object.appendKeyValue(charSequence.toString(), String.format(Locale.ROOT, "<Data: %d>", n));
        }
    }

    public /* varargs */ void writeLine(String string, Object ... arrobject) throws IOException {
        this.write(string, arrobject);
        if (!this.useUrlEncode) {
            this.write("\r\n", new Object[0]);
        }
    }

    public void writeObject(String string, Object object, GraphRequest object2) throws IOException {
        if (this.outputStream instanceof RequestOutputStream) {
            ((RequestOutputStream)((Object)this.outputStream)).setCurrentRequest((GraphRequest)object2);
        }
        if (GraphRequest.isSupportedParameterType(object)) {
            this.writeString(string, GraphRequest.parameterToString(object));
            return;
        }
        if (object instanceof Bitmap) {
            this.writeBitmap(string, (Bitmap)object);
            return;
        }
        if (object instanceof byte[]) {
            this.writeBytes(string, (byte[])object);
            return;
        }
        if (object instanceof Uri) {
            this.writeContentUri(string, (Uri)object, null);
            return;
        }
        if (object instanceof ParcelFileDescriptor) {
            this.writeFile(string, (ParcelFileDescriptor)object, null);
            return;
        }
        if (object instanceof GraphRequest.ParcelableResourceWithMimeType) {
            object2 = (GraphRequest.ParcelableResourceWithMimeType)object;
            object = object2.getResource();
            object2 = object2.getMimeType();
            if (object instanceof ParcelFileDescriptor) {
                this.writeFile(string, (ParcelFileDescriptor)object, (String)object2);
                return;
            }
            if (object instanceof Uri) {
                this.writeContentUri(string, (Uri)object, (String)object2);
                return;
            }
            throw this.getInvalidTypeError();
        }
        throw this.getInvalidTypeError();
    }

    public void writeRecordBoundary() throws IOException {
        if (!this.useUrlEncode) {
            this.writeLine("--%s", GraphRequest.MIME_BOUNDARY);
            return;
        }
        this.outputStream.write("&".getBytes());
    }

    public void writeRequestsAsJson(String string, JSONArray jSONArray, Collection<GraphRequest> object) throws IOException, JSONException {
        if (!(this.outputStream instanceof RequestOutputStream)) {
            this.writeString(string, jSONArray.toString());
            return;
        }
        Object object2 = (RequestOutputStream)((Object)this.outputStream);
        this.writeContentDisposition(string, null, null);
        this.write("[", new Object[0]);
        object = object.iterator();
        int n = 0;
        while (object.hasNext()) {
            GraphRequest graphRequest = (GraphRequest)object.next();
            JSONObject jSONObject = jSONArray.getJSONObject(n);
            object2.setCurrentRequest(graphRequest);
            if (n > 0) {
                this.write(",%s", jSONObject.toString());
            } else {
                this.write("%s", jSONObject.toString());
            }
            ++n;
        }
        this.write("]", new Object[0]);
        if (this.logger != null) {
            object = this.logger;
            object2 = new StringBuilder();
            object2.append("    ");
            object2.append(string);
            object.appendKeyValue(object2.toString(), jSONArray.toString());
        }
    }

    @Override
    public void writeString(String string, String string2) throws IOException {
        this.writeContentDisposition(string, null, null);
        this.writeLine("%s", string2);
        this.writeRecordBoundary();
        if (this.logger != null) {
            Logger logger = this.logger;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("    ");
            stringBuilder.append(string);
            logger.appendKeyValue(stringBuilder.toString(), string2);
        }
    }
}
