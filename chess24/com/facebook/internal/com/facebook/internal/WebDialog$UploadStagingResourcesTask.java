/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.ProgressDialog
 *  android.graphics.drawable.Drawable
 *  android.net.Uri
 *  android.os.AsyncTask
 *  android.os.Bundle
 *  android.widget.ImageView
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package com.facebook.internal;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import com.facebook.AccessToken;
import com.facebook.FacebookException;
import com.facebook.FacebookGraphResponseException;
import com.facebook.FacebookRequestError;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.internal.ServerProtocol;
import com.facebook.internal.Utility;
import com.facebook.internal.WebDialog;
import com.facebook.share.internal.ShareInternalUtility;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import org.json.JSONArray;
import org.json.JSONObject;

private class WebDialog.UploadStagingResourcesTask
extends AsyncTask<Void, Void, String[]> {
    private String action;
    private Exception[] exceptions;
    private Bundle parameters;

    WebDialog.UploadStagingResourcesTask(String string, Bundle bundle) {
        this.action = string;
        this.parameters = bundle;
    }

    static /* synthetic */ Exception[] access$800(WebDialog.UploadStagingResourcesTask uploadStagingResourcesTask) {
        return uploadStagingResourcesTask.exceptions;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected /* varargs */ String[] doInBackground(Void ... object) {
        Object object2 = this.parameters.getStringArray("media");
        final String[] arrstring = new String[((String[])object2).length];
        this.exceptions = new Exception[((String[])object2).length];
        final CountDownLatch countDownLatch = new CountDownLatch(((String[])object2).length);
        object = new ConcurrentLinkedQueue();
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        final int n = 0;
        do {
            block10 : {
                try {
                    if (n >= ((String[])object2).length) {
                        countDownLatch.await();
                        return arrstring;
                    }
                    if (this.isCancelled()) {
                        object2 = object.iterator();
                        while (object2.hasNext()) {
                            ((AsyncTask)object2.next()).cancel(true);
                        }
                        return null;
                    }
                    Uri uri = Uri.parse((String)object2[n]);
                    if (Utility.isWebUri(uri)) {
                        arrstring[n] = uri.toString();
                        countDownLatch.countDown();
                    } else {
                        object.add(ShareInternalUtility.newUploadStagingResourceWithImageRequest(accessToken, uri, new GraphRequest.Callback(){

                            /*
                             * Enabled aggressive block sorting
                             * Enabled unnecessary exception pruning
                             * Enabled aggressive exception aggregation
                             */
                            @Override
                            public void onCompleted(GraphResponse object) {
                                try {
                                    Object object2 = object.getError();
                                    if (object2 != null) {
                                        String string = object2.getErrorMessage();
                                        object2 = string;
                                        if (string == null) {
                                            object2 = "Error staging photo.";
                                        }
                                        throw new FacebookGraphResponseException((GraphResponse)object, (String)object2);
                                    }
                                    if ((object = object.getJSONObject()) == null) {
                                        throw new FacebookException("Error staging photo.");
                                    }
                                    if ((object = object.optString("uri")) == null) {
                                        throw new FacebookException("Error staging photo.");
                                    }
                                    arrstring[n] = object;
                                }
                                catch (Exception exception) {
                                    WebDialog.UploadStagingResourcesTask.access$800((WebDialog.UploadStagingResourcesTask)UploadStagingResourcesTask.this)[n] = exception;
                                }
                                countDownLatch.countDown();
                            }
                        }).executeAsync());
                    }
                    break block10;
                }
                catch (Exception exception) {}
                object = object.iterator();
                do {
                    if (!object.hasNext()) {
                        return null;
                    }
                    ((AsyncTask)object.next()).cancel(true);
                } while (true);
            }
            ++n;
        } while (true);
    }

    protected void onPostExecute(String[] object) {
        WebDialog.this.spinner.dismiss();
        for (Exception exception : this.exceptions) {
            if (exception == null) continue;
            WebDialog.this.sendErrorToListener(exception);
            return;
        }
        if (object == null) {
            WebDialog.this.sendErrorToListener(new FacebookException("Failed to stage photos for web dialog"));
            return;
        }
        if ((object = Arrays.asList(object)).contains(null)) {
            WebDialog.this.sendErrorToListener(new FacebookException("Failed to stage photos for web dialog"));
            return;
        }
        Utility.putJSONValueInBundle(this.parameters, "media", (Object)new JSONArray((Collection)object));
        object = ServerProtocol.getDialogAuthority();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(FacebookSdk.getGraphApiVersion());
        stringBuilder.append("/");
        stringBuilder.append("dialog/");
        stringBuilder.append(this.action);
        object = Utility.buildUri((String)object, stringBuilder.toString(), this.parameters);
        WebDialog.this.url = object.toString();
        int n = WebDialog.this.crossImageView.getDrawable().getIntrinsicWidth();
        WebDialog.this.setUpWebView(n / 2 + 1);
    }

}
