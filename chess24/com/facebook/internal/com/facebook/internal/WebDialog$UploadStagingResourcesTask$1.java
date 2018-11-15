/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.facebook.internal;

import com.facebook.FacebookException;
import com.facebook.FacebookGraphResponseException;
import com.facebook.FacebookRequestError;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.internal.WebDialog;
import java.util.concurrent.CountDownLatch;
import org.json.JSONObject;

class WebDialog.UploadStagingResourcesTask
implements GraphRequest.Callback {
    final /* synthetic */ CountDownLatch val$latch;
    final /* synthetic */ String[] val$results;
    final /* synthetic */ int val$writeIndex;

    WebDialog.UploadStagingResourcesTask(String[] arrstring, int n, CountDownLatch countDownLatch) {
        this.val$results = arrstring;
        this.val$writeIndex = n;
        this.val$latch = countDownLatch;
    }

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
            this.val$results[this.val$writeIndex] = object;
        }
        catch (Exception exception) {
            WebDialog.UploadStagingResourcesTask.access$800((WebDialog.UploadStagingResourcesTask)UploadStagingResourcesTask.this)[this.val$writeIndex] = exception;
        }
        this.val$latch.countDown();
    }
}
