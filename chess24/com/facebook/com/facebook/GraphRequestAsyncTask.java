/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.AsyncTask
 *  android.os.Handler
 *  android.os.HandlerThread
 *  android.os.Looper
 *  android.util.Log
 */
package com.facebook;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestBatch;
import com.facebook.GraphResponse;
import java.net.HttpURLConnection;
import java.util.Collection;
import java.util.List;

public class GraphRequestAsyncTask
extends AsyncTask<Void, Void, List<GraphResponse>> {
    private static final String TAG = GraphRequestAsyncTask.class.getCanonicalName();
    private final HttpURLConnection connection;
    private Exception exception;
    private final GraphRequestBatch requests;

    public GraphRequestAsyncTask(GraphRequestBatch graphRequestBatch) {
        this((HttpURLConnection)null, graphRequestBatch);
    }

    public GraphRequestAsyncTask(HttpURLConnection httpURLConnection, GraphRequestBatch graphRequestBatch) {
        this.requests = graphRequestBatch;
        this.connection = httpURLConnection;
    }

    public GraphRequestAsyncTask(HttpURLConnection httpURLConnection, Collection<GraphRequest> collection) {
        this(httpURLConnection, new GraphRequestBatch(collection));
    }

    public /* varargs */ GraphRequestAsyncTask(HttpURLConnection httpURLConnection, GraphRequest ... arrgraphRequest) {
        this(httpURLConnection, new GraphRequestBatch(arrgraphRequest));
    }

    public GraphRequestAsyncTask(Collection<GraphRequest> collection) {
        this((HttpURLConnection)null, new GraphRequestBatch(collection));
    }

    public /* varargs */ GraphRequestAsyncTask(GraphRequest ... arrgraphRequest) {
        this((HttpURLConnection)null, new GraphRequestBatch(arrgraphRequest));
    }

    protected /* varargs */ List<GraphResponse> doInBackground(Void ... object) {
        try {
            if (this.connection == null) {
                return this.requests.executeAndWait();
            }
            object = GraphRequest.executeConnectionAndWait(this.connection, this.requests);
            return object;
        }
        catch (Exception exception) {
            this.exception = exception;
            return null;
        }
    }

    protected final Exception getException() {
        return this.exception;
    }

    protected final GraphRequestBatch getRequests() {
        return this.requests;
    }

    protected void onPostExecute(List<GraphResponse> list) {
        super.onPostExecute(list);
        if (this.exception != null) {
            Log.d((String)TAG, (String)String.format("onPostExecute: exception encountered during request: %s", this.exception.getMessage()));
        }
    }

    protected void onPreExecute() {
        super.onPreExecute();
        if (FacebookSdk.isDebugEnabled()) {
            Log.d((String)TAG, (String)String.format("execute async task: %s", new Object[]{this}));
        }
        if (this.requests.getCallbackHandler() == null) {
            Handler handler = Thread.currentThread() instanceof HandlerThread ? new Handler() : new Handler(Looper.getMainLooper());
            this.requests.setCallbackHandler(handler);
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{RequestAsyncTask: ");
        stringBuilder.append(" connection: ");
        stringBuilder.append(this.connection);
        stringBuilder.append(", requests: ");
        stringBuilder.append(this.requests);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}
