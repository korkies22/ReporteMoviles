/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 *  org.json.JSONTokener
 */
package com.facebook;

import com.facebook.AccessToken;
import com.facebook.FacebookException;
import com.facebook.FacebookRequestError;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestBatch;
import com.facebook.LoggingBehavior;
import com.facebook.internal.Logger;
import com.facebook.internal.Utility;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class GraphResponse {
    private static final String BODY_KEY = "body";
    private static final String CODE_KEY = "code";
    public static final String NON_JSON_RESPONSE_PROPERTY = "FACEBOOK_NON_JSON_RESULT";
    private static final String RESPONSE_LOG_TAG = "Response";
    public static final String SUCCESS_KEY = "success";
    private final HttpURLConnection connection;
    private final FacebookRequestError error;
    private final JSONObject graphObject;
    private final JSONArray graphObjectArray;
    private final String rawResponse;
    private final GraphRequest request;

    GraphResponse(GraphRequest graphRequest, HttpURLConnection httpURLConnection, FacebookRequestError facebookRequestError) {
        this(graphRequest, httpURLConnection, null, null, null, facebookRequestError);
    }

    GraphResponse(GraphRequest graphRequest, HttpURLConnection httpURLConnection, String string, JSONArray jSONArray) {
        this(graphRequest, httpURLConnection, string, null, jSONArray, null);
    }

    GraphResponse(GraphRequest graphRequest, HttpURLConnection httpURLConnection, String string, JSONObject jSONObject) {
        this(graphRequest, httpURLConnection, string, jSONObject, null, null);
    }

    GraphResponse(GraphRequest graphRequest, HttpURLConnection httpURLConnection, String string, JSONObject jSONObject, JSONArray jSONArray, FacebookRequestError facebookRequestError) {
        this.request = graphRequest;
        this.connection = httpURLConnection;
        this.rawResponse = string;
        this.graphObject = jSONObject;
        this.graphObjectArray = jSONArray;
        this.error = facebookRequestError;
    }

    static List<GraphResponse> constructErrorResponses(List<GraphRequest> list, HttpURLConnection httpURLConnection, FacebookException facebookException) {
        int n = list.size();
        ArrayList<GraphResponse> arrayList = new ArrayList<GraphResponse>(n);
        for (int i = 0; i < n; ++i) {
            arrayList.add(new GraphResponse(list.get(i), httpURLConnection, new FacebookRequestError(httpURLConnection, facebookException)));
        }
        return arrayList;
    }

    private static GraphResponse createResponseFromObject(GraphRequest object, HttpURLConnection httpURLConnection, Object object2, Object object3) throws JSONException {
        Object object4 = object2;
        if (object2 instanceof JSONObject) {
            if ((object3 = FacebookRequestError.checkResponseAndCreateError((JSONObject)(object2 = (JSONObject)object2), object3, httpURLConnection)) != null) {
                if (object3.getErrorCode() == 190 && Utility.isCurrentAccessToken(object.getAccessToken())) {
                    AccessToken.setCurrentAccessToken(null);
                }
                return new GraphResponse((GraphRequest)object, httpURLConnection, (FacebookRequestError)object3);
            }
            if ((object2 = Utility.getStringPropertyAsJSON((JSONObject)object2, BODY_KEY, NON_JSON_RESPONSE_PROPERTY)) instanceof JSONObject) {
                return new GraphResponse((GraphRequest)object, httpURLConnection, object2.toString(), (JSONObject)object2);
            }
            if (object2 instanceof JSONArray) {
                return new GraphResponse((GraphRequest)object, httpURLConnection, object2.toString(), (JSONArray)object2);
            }
            object4 = JSONObject.NULL;
        }
        if (object4 == JSONObject.NULL) {
            return new GraphResponse((GraphRequest)object, httpURLConnection, object4.toString(), (JSONObject)null);
        }
        object = new StringBuilder();
        object.append("Got unexpected object type in response, class: ");
        object.append(object4.getClass().getSimpleName());
        throw new FacebookException(object.toString());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static List<GraphResponse> createResponsesFromObject(HttpURLConnection httpURLConnection, List<GraphRequest> list, Object object) throws FacebookException, JSONException {
        int n;
        Object object2;
        GraphRequest graphRequest;
        int n2;
        int n3;
        ArrayList<GraphResponse> arrayList;
        block9 : {
            n = list.size();
            arrayList = new ArrayList<GraphResponse>(n);
            n3 = 0;
            if (n == 1) {
                graphRequest = list.get(0);
                try {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put(BODY_KEY, object);
                    n2 = httpURLConnection != null ? httpURLConnection.getResponseCode() : 200;
                    jSONObject.put(CODE_KEY, n2);
                    object2 = new JSONArray();
                    object2.put((Object)jSONObject);
                    break block9;
                }
                catch (IOException iOException) {
                    arrayList.add(new GraphResponse(graphRequest, httpURLConnection, new FacebookRequestError(httpURLConnection, iOException)));
                }
                catch (JSONException jSONException) {
                    arrayList.add(new GraphResponse(graphRequest, httpURLConnection, new FacebookRequestError(httpURLConnection, (Exception)jSONException)));
                }
            }
            object2 = object;
        }
        if (!(object2 instanceof JSONArray)) throw new FacebookException("Unexpected number of results");
        object2 = (JSONArray)object2;
        n2 = n3;
        if (object2.length() != n) {
            throw new FacebookException("Unexpected number of results");
        }
        while (n2 < object2.length()) {
            graphRequest = list.get(n2);
            try {
                arrayList.add(GraphResponse.createResponseFromObject(graphRequest, httpURLConnection, object2.get(n2), object));
            }
            catch (FacebookException facebookException) {
                arrayList.add(new GraphResponse(graphRequest, httpURLConnection, new FacebookRequestError(httpURLConnection, facebookException)));
            }
            catch (JSONException jSONException) {
                arrayList.add(new GraphResponse(graphRequest, httpURLConnection, new FacebookRequestError(httpURLConnection, (Exception)jSONException)));
            }
            ++n2;
        }
        return arrayList;
    }

    static List<GraphResponse> createResponsesFromStream(InputStream object, HttpURLConnection httpURLConnection, GraphRequestBatch graphRequestBatch) throws FacebookException, JSONException, IOException {
        object = Utility.readStreamToString((InputStream)object);
        Logger.log(LoggingBehavior.INCLUDE_RAW_RESPONSES, RESPONSE_LOG_TAG, "Response (raw)\n  Size: %d\n  Response:\n%s\n", object.length(), object);
        return GraphResponse.createResponsesFromString((String)object, httpURLConnection, graphRequestBatch);
    }

    static List<GraphResponse> createResponsesFromString(String string, HttpURLConnection object, GraphRequestBatch graphRequestBatch) throws FacebookException, JSONException, IOException {
        object = GraphResponse.createResponsesFromObject((HttpURLConnection)object, graphRequestBatch, new JSONTokener(string).nextValue());
        Logger.log(LoggingBehavior.REQUESTS, RESPONSE_LOG_TAG, "Response\n  Id: %s\n  Size: %d\n  Responses:\n%s\n", graphRequestBatch.getId(), string.length(), object);
        return object;
    }

    /*
     * Exception decompiling
     */
    static List<GraphResponse> fromHttpConnection(HttpURLConnection var0, GraphRequestBatch var1_2) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:401)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:2898)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:716)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:186)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:131)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:378)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:884)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:786)
        // org.benf.cfr.reader.Main.doClass(Main.java:54)
        // org.benf.cfr.reader.Main.main(Main.java:247)
        throw new IllegalStateException("Decompilation failed");
    }

    public final HttpURLConnection getConnection() {
        return this.connection;
    }

    public final FacebookRequestError getError() {
        return this.error;
    }

    public final JSONArray getJSONArray() {
        return this.graphObjectArray;
    }

    public final JSONObject getJSONObject() {
        return this.graphObject;
    }

    public String getRawResponse() {
        return this.rawResponse;
    }

    public GraphRequest getRequest() {
        return this.request;
    }

    public GraphRequest getRequestForPagedResults(PagingDirection object) {
        JSONObject jSONObject;
        object = this.graphObject != null && (jSONObject = this.graphObject.optJSONObject("paging")) != null ? (object == PagingDirection.NEXT ? jSONObject.optString("next") : jSONObject.optString("previous")) : null;
        if (Utility.isNullOrEmpty((String)object)) {
            return null;
        }
        if (object != null && object.equals(this.request.getUrlForSingleRequest())) {
            return null;
        }
        try {
            object = new GraphRequest(this.request.getAccessToken(), new URL((String)object));
            return object;
        }
        catch (MalformedURLException malformedURLException) {
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String toString() {
        Object object;
        block2 : {
            try {
                object = Locale.US;
                int n = this.connection != null ? this.connection.getResponseCode() : 200;
                object = String.format((Locale)object, "%d", n);
                break block2;
            }
            catch (IOException iOException) {}
            object = "unknown";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{Response: ");
        stringBuilder.append(" responseCode: ");
        stringBuilder.append((String)object);
        stringBuilder.append(", graphObject: ");
        stringBuilder.append((Object)this.graphObject);
        stringBuilder.append(", error: ");
        stringBuilder.append(this.error);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public static enum PagingDirection {
        NEXT,
        PREVIOUS;
        

        private PagingDirection() {
        }
    }

}
