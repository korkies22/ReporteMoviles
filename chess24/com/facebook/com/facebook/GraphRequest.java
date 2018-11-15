/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$CompressFormat
 *  android.location.Location
 *  android.net.Uri
 *  android.net.Uri$Builder
 *  android.os.AsyncTask
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Parcel
 *  android.os.ParcelFileDescriptor
 *  android.os.ParcelFileDescriptor$AutoCloseInputStream
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.text.TextUtils
 *  android.util.Log
 *  android.util.Pair
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.facebook;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import com.facebook.AccessToken;
import com.facebook.AccessTokenManager;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphRequestBatch;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.LoggingBehavior;
import com.facebook.ProgressNoopOutputStream;
import com.facebook.ProgressOutputStream;
import com.facebook.RequestOutputStream;
import com.facebook.RequestProgress;
import com.facebook.internal.AttributionIdentifiers;
import com.facebook.internal.InternalSettings;
import com.facebook.internal.Logger;
import com.facebook.internal.ServerProtocol;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPOutputStream;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GraphRequest {
    private static final String ACCEPT_LANGUAGE_HEADER = "Accept-Language";
    public static final String ACCESS_TOKEN_PARAM = "access_token";
    private static final String ATTACHED_FILES_PARAM = "attached_files";
    private static final String ATTACHMENT_FILENAME_PREFIX = "file";
    private static final String BATCH_APP_ID_PARAM = "batch_app_id";
    private static final String BATCH_BODY_PARAM = "body";
    private static final String BATCH_ENTRY_DEPENDS_ON_PARAM = "depends_on";
    private static final String BATCH_ENTRY_NAME_PARAM = "name";
    private static final String BATCH_ENTRY_OMIT_RESPONSE_ON_SUCCESS_PARAM = "omit_response_on_success";
    private static final String BATCH_METHOD_PARAM = "method";
    private static final String BATCH_PARAM = "batch";
    private static final String BATCH_RELATIVE_URL_PARAM = "relative_url";
    private static final String CAPTION_PARAM = "caption";
    private static final String CONTENT_ENCODING_HEADER = "Content-Encoding";
    private static final String CONTENT_TYPE_HEADER = "Content-Type";
    private static final String DEBUG_KEY = "__debug__";
    private static final String DEBUG_MESSAGES_KEY = "messages";
    private static final String DEBUG_MESSAGE_KEY = "message";
    private static final String DEBUG_MESSAGE_LINK_KEY = "link";
    private static final String DEBUG_MESSAGE_TYPE_KEY = "type";
    private static final String DEBUG_PARAM = "debug";
    private static final String DEBUG_SEVERITY_INFO = "info";
    private static final String DEBUG_SEVERITY_WARNING = "warning";
    public static final String FIELDS_PARAM = "fields";
    private static final String FORMAT_JSON = "json";
    private static final String FORMAT_PARAM = "format";
    private static final String GRAPH_PATH_FORMAT = "%s/%s";
    private static final String ISO_8601_FORMAT_STRING = "yyyy-MM-dd'T'HH:mm:ssZ";
    public static final int MAXIMUM_BATCH_SIZE = 50;
    private static final String ME = "me";
    private static final String MIME_BOUNDARY = "3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f";
    private static final String MY_FRIENDS = "me/friends";
    private static final String MY_PHOTOS = "me/photos";
    private static final String PICTURE_PARAM = "picture";
    private static final String SDK_ANDROID = "android";
    private static final String SDK_PARAM = "sdk";
    private static final String SEARCH = "search";
    public static final String TAG = "GraphRequest";
    private static final String USER_AGENT_BASE = "FBAndroidSDK";
    private static final String USER_AGENT_HEADER = "User-Agent";
    private static final String VIDEOS_SUFFIX = "/videos";
    private static String defaultBatchApplicationId;
    private static volatile String userAgent;
    private static Pattern versionPattern;
    private AccessToken accessToken;
    private String batchEntryDependsOn;
    private String batchEntryName;
    private boolean batchEntryOmitResultOnSuccess = true;
    private Callback callback;
    private JSONObject graphObject;
    private String graphPath;
    private HttpMethod httpMethod;
    private String overriddenURL;
    private Bundle parameters;
    private boolean skipClientToken = false;
    private Object tag;
    private String version;

    static {
        versionPattern = Pattern.compile("^/?v\\d+\\.\\d+/(.*)");
    }

    public GraphRequest() {
        this(null, null, null, null, null);
    }

    public GraphRequest(AccessToken accessToken, String string) {
        this(accessToken, string, null, null, null);
    }

    public GraphRequest(AccessToken accessToken, String string, Bundle bundle, HttpMethod httpMethod) {
        this(accessToken, string, bundle, httpMethod, null);
    }

    public GraphRequest(AccessToken accessToken, String string, Bundle bundle, HttpMethod httpMethod, Callback callback) {
        this(accessToken, string, bundle, httpMethod, callback, null);
    }

    public GraphRequest(AccessToken accessToken, String string, Bundle bundle, HttpMethod httpMethod, Callback callback, String string2) {
        this.accessToken = accessToken;
        this.graphPath = string;
        this.version = string2;
        this.setCallback(callback);
        this.setHttpMethod(httpMethod);
        this.parameters = bundle != null ? new Bundle(bundle) : new Bundle();
        if (this.version == null) {
            this.version = FacebookSdk.getGraphApiVersion();
        }
    }

    GraphRequest(AccessToken accessToken, URL uRL) {
        this.accessToken = accessToken;
        this.overriddenURL = uRL.toString();
        this.setHttpMethod(HttpMethod.GET);
        this.parameters = new Bundle();
    }

    private void addCommonParameters() {
        if (this.accessToken != null) {
            if (!this.parameters.containsKey(ACCESS_TOKEN_PARAM)) {
                String string = this.accessToken.getToken();
                Logger.registerAccessToken(string);
                this.parameters.putString(ACCESS_TOKEN_PARAM, string);
            }
        } else if (!this.skipClientToken && !this.parameters.containsKey(ACCESS_TOKEN_PARAM)) {
            String string = FacebookSdk.getApplicationId();
            String string2 = FacebookSdk.getClientToken();
            if (!Utility.isNullOrEmpty(string) && !Utility.isNullOrEmpty(string2)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(string);
                stringBuilder.append("|");
                stringBuilder.append(string2);
                string = stringBuilder.toString();
                this.parameters.putString(ACCESS_TOKEN_PARAM, string);
            } else {
                Log.d((String)TAG, (String)"Warning: Request without access token missing application ID or client token.");
            }
        }
        this.parameters.putString(SDK_PARAM, SDK_ANDROID);
        this.parameters.putString(FORMAT_PARAM, FORMAT_JSON);
        if (FacebookSdk.isLoggingBehaviorEnabled(LoggingBehavior.GRAPH_API_DEBUG_INFO)) {
            this.parameters.putString(DEBUG_PARAM, DEBUG_SEVERITY_INFO);
            return;
        }
        if (FacebookSdk.isLoggingBehaviorEnabled(LoggingBehavior.GRAPH_API_DEBUG_WARNING)) {
            this.parameters.putString(DEBUG_PARAM, DEBUG_SEVERITY_WARNING);
        }
    }

    private String appendParametersToBaseUrl(String object) {
        Uri.Builder builder = Uri.parse((String)object).buildUpon();
        for (String string : this.parameters.keySet()) {
            Object object2;
            object = object2 = this.parameters.get(string);
            if (object2 == null) {
                object = "";
            }
            if (GraphRequest.isSupportedParameterType(object)) {
                builder.appendQueryParameter(string, GraphRequest.parameterToString(object).toString());
                continue;
            }
            if (this.httpMethod != HttpMethod.GET) continue;
            throw new IllegalArgumentException(String.format(Locale.US, "Unsupported parameter type for GET request: %s", object.getClass().getSimpleName()));
        }
        return builder.toString();
    }

    private static HttpURLConnection createConnection(URL object) throws IOException {
        object = (HttpURLConnection)object.openConnection();
        object.setRequestProperty(USER_AGENT_HEADER, GraphRequest.getUserAgent());
        object.setRequestProperty(ACCEPT_LANGUAGE_HEADER, Locale.getDefault().toString());
        object.setChunkedStreamingMode(0);
        return object;
    }

    public static GraphResponse executeAndWait(GraphRequest list) {
        GraphRequest[] arrgraphRequest = new GraphRequest[]{list};
        list = GraphRequest.executeBatchAndWait(arrgraphRequest);
        if (list != null && list.size() == 1) {
            return (GraphResponse)list.get(0);
        }
        throw new FacebookException("invalid state: expected a single response");
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static List<GraphResponse> executeBatchAndWait(GraphRequestBatch object) {
        Throwable throwable;
        block6 : {
            Validate.notEmptyAndContainsNoNulls(object, "requests");
            throwable = null;
            HttpURLConnection httpURLConnection = GraphRequest.toHttpConnection((GraphRequestBatch)object);
            try {
                object = GraphRequest.executeConnectionAndWait(httpURLConnection, (GraphRequestBatch)object);
            }
            catch (Throwable throwable2) {
                object = httpURLConnection;
                break block6;
            }
            Utility.disconnectQuietly(httpURLConnection);
            return object;
            {
                List<GraphResponse> list;
                catch (Throwable throwable3) {
                    object = throwable;
                    throwable = throwable3;
                }
                catch (Exception exception) {}
                {
                    list = GraphResponse.constructErrorResponses(object.getRequests(), null, new FacebookException(exception));
                    GraphRequest.runCallbacks((GraphRequestBatch)object, list);
                }
                Utility.disconnectQuietly(null);
                return list;
            }
        }
        Utility.disconnectQuietly((URLConnection)object);
        throw throwable;
    }

    public static List<GraphResponse> executeBatchAndWait(Collection<GraphRequest> collection) {
        return GraphRequest.executeBatchAndWait(new GraphRequestBatch(collection));
    }

    public static /* varargs */ List<GraphResponse> executeBatchAndWait(GraphRequest ... arrgraphRequest) {
        Validate.notNull(arrgraphRequest, "requests");
        return GraphRequest.executeBatchAndWait(Arrays.asList(arrgraphRequest));
    }

    public static GraphRequestAsyncTask executeBatchAsync(GraphRequestBatch object) {
        Validate.notEmptyAndContainsNoNulls(object, "requests");
        object = new GraphRequestAsyncTask((GraphRequestBatch)object);
        object.executeOnExecutor(FacebookSdk.getExecutor(), (Object[])new Void[0]);
        return object;
    }

    public static GraphRequestAsyncTask executeBatchAsync(Collection<GraphRequest> collection) {
        return GraphRequest.executeBatchAsync(new GraphRequestBatch(collection));
    }

    public static /* varargs */ GraphRequestAsyncTask executeBatchAsync(GraphRequest ... arrgraphRequest) {
        Validate.notNull(arrgraphRequest, "requests");
        return GraphRequest.executeBatchAsync(Arrays.asList(arrgraphRequest));
    }

    public static List<GraphResponse> executeConnectionAndWait(HttpURLConnection httpURLConnection, GraphRequestBatch graphRequestBatch) {
        List<GraphResponse> list = GraphResponse.fromHttpConnection(httpURLConnection, graphRequestBatch);
        Utility.disconnectQuietly(httpURLConnection);
        int n = graphRequestBatch.size();
        if (n != list.size()) {
            throw new FacebookException(String.format(Locale.US, "Received %d responses while expecting %d", list.size(), n));
        }
        GraphRequest.runCallbacks(graphRequestBatch, list);
        AccessTokenManager.getInstance().extendAccessTokenIfNeeded();
        return list;
    }

    public static List<GraphResponse> executeConnectionAndWait(HttpURLConnection httpURLConnection, Collection<GraphRequest> collection) {
        return GraphRequest.executeConnectionAndWait(httpURLConnection, new GraphRequestBatch(collection));
    }

    public static GraphRequestAsyncTask executeConnectionAsync(Handler handler, HttpURLConnection object, GraphRequestBatch graphRequestBatch) {
        Validate.notNull(object, "connection");
        object = new GraphRequestAsyncTask((HttpURLConnection)object, graphRequestBatch);
        graphRequestBatch.setCallbackHandler(handler);
        object.executeOnExecutor(FacebookSdk.getExecutor(), (Object[])new Void[0]);
        return object;
    }

    public static GraphRequestAsyncTask executeConnectionAsync(HttpURLConnection httpURLConnection, GraphRequestBatch graphRequestBatch) {
        return GraphRequest.executeConnectionAsync(null, httpURLConnection, graphRequestBatch);
    }

    private static String getBatchAppId(GraphRequestBatch object) {
        if (!Utility.isNullOrEmpty(object.getBatchApplicationId())) {
            return object.getBatchApplicationId();
        }
        object = object.iterator();
        while (object.hasNext()) {
            Object object2 = ((GraphRequest)object.next()).accessToken;
            if (object2 == null || (object2 = object2.getApplicationId()) == null) continue;
            return object2;
        }
        if (!Utility.isNullOrEmpty(defaultBatchApplicationId)) {
            return defaultBatchApplicationId;
        }
        return FacebookSdk.getApplicationId();
    }

    public static final String getDefaultBatchApplicationId() {
        return defaultBatchApplicationId;
    }

    private static String getDefaultPhotoPathIfNull(String string) {
        String string2 = string;
        if (string == null) {
            string2 = MY_PHOTOS;
        }
        return string2;
    }

    private String getGraphPathWithVersion() {
        if (versionPattern.matcher(this.graphPath).matches()) {
            return this.graphPath;
        }
        return String.format(GRAPH_PATH_FORMAT, this.version, this.graphPath);
    }

    private static String getMimeContentType() {
        return String.format("multipart/form-data; boundary=%s", MIME_BOUNDARY);
    }

    private static String getUserAgent() {
        if (userAgent == null) {
            userAgent = String.format("%s.%s", USER_AGENT_BASE, "4.31.0");
            String string = InternalSettings.getCustomUserAgent();
            if (!Utility.isNullOrEmpty(string)) {
                userAgent = String.format(Locale.ROOT, GRAPH_PATH_FORMAT, userAgent, string);
            }
        }
        return userAgent;
    }

    private static boolean hasOnProgressCallbacks(GraphRequestBatch object) {
        Iterator<GraphRequestBatch.Callback> iterator = object.getCallbacks().iterator();
        while (iterator.hasNext()) {
            if (!(iterator.next() instanceof GraphRequestBatch.OnProgressCallback)) continue;
            return true;
        }
        object = object.iterator();
        while (object.hasNext()) {
            if (!(((GraphRequest)object.next()).getCallback() instanceof OnProgressCallback)) continue;
            return true;
        }
        return false;
    }

    private static boolean isGzipCompressible(GraphRequestBatch object) {
        object = object.iterator();
        while (object.hasNext()) {
            GraphRequest graphRequest = (GraphRequest)object.next();
            for (String string : graphRequest.parameters.keySet()) {
                if (!GraphRequest.isSupportedAttachmentType(graphRequest.parameters.get(string))) continue;
                return false;
            }
        }
        return true;
    }

    private static boolean isMeRequest(String string) {
        Matcher matcher = versionPattern.matcher(string);
        if (matcher.matches()) {
            string = matcher.group(1);
        }
        if (!string.startsWith("me/")) {
            if (string.startsWith("/me/")) {
                return true;
            }
            return false;
        }
        return true;
    }

    private static boolean isSupportedAttachmentType(Object object) {
        if (!(object instanceof Bitmap || object instanceof byte[] || object instanceof Uri || object instanceof ParcelFileDescriptor || object instanceof ParcelableResourceWithMimeType)) {
            return false;
        }
        return true;
    }

    private static boolean isSupportedParameterType(Object object) {
        if (!(object instanceof String || object instanceof Boolean || object instanceof Number || object instanceof Date)) {
            return false;
        }
        return true;
    }

    public static GraphRequest newCustomAudienceThirdPartyIdRequest(AccessToken accessToken, Context context, Callback callback) {
        return GraphRequest.newCustomAudienceThirdPartyIdRequest(accessToken, context, null, callback);
    }

    public static GraphRequest newCustomAudienceThirdPartyIdRequest(AccessToken accessToken, Context context, String string, Callback callback) {
        CharSequence charSequence = string;
        if (string == null) {
            charSequence = string;
            if (accessToken != null) {
                charSequence = accessToken.getApplicationId();
            }
        }
        string = charSequence;
        if (charSequence == null) {
            string = Utility.getMetadataApplicationId(context);
        }
        if (string == null) {
            throw new FacebookException("Facebook App ID cannot be determined");
        }
        charSequence = new StringBuilder();
        charSequence.append(string);
        charSequence.append("/custom_audience_third_party_id");
        charSequence = charSequence.toString();
        AttributionIdentifiers attributionIdentifiers = AttributionIdentifiers.getAttributionIdentifiers(context);
        Bundle bundle = new Bundle();
        if (accessToken == null) {
            if (attributionIdentifiers == null) {
                throw new FacebookException("There is no access token and attribution identifiers could not be retrieved");
            }
            string = attributionIdentifiers.getAttributionId() != null ? attributionIdentifiers.getAttributionId() : attributionIdentifiers.getAndroidAdvertiserId();
            if (attributionIdentifiers.getAttributionId() != null) {
                bundle.putString("udid", string);
            }
        }
        if (FacebookSdk.getLimitEventAndDataUsage(context) || attributionIdentifiers != null && attributionIdentifiers.isTrackingLimited()) {
            bundle.putString("limit_event_usage", "1");
        }
        return new GraphRequest(accessToken, (String)charSequence, bundle, HttpMethod.GET, callback);
    }

    public static GraphRequest newDeleteObjectRequest(AccessToken accessToken, String string, Callback callback) {
        return new GraphRequest(accessToken, string, null, HttpMethod.DELETE, callback);
    }

    public static GraphRequest newGraphPathRequest(AccessToken accessToken, String string, Callback callback) {
        return new GraphRequest(accessToken, string, null, null, callback);
    }

    public static GraphRequest newMeRequest(AccessToken accessToken, final GraphJSONObjectCallback graphJSONObjectCallback) {
        return new GraphRequest(accessToken, ME, null, null, new Callback(){

            @Override
            public void onCompleted(GraphResponse graphResponse) {
                if (graphJSONObjectCallback != null) {
                    graphJSONObjectCallback.onCompleted(graphResponse.getJSONObject(), graphResponse);
                }
            }
        });
    }

    public static GraphRequest newMyFriendsRequest(AccessToken accessToken, final GraphJSONArrayCallback graphJSONArrayCallback) {
        return new GraphRequest(accessToken, MY_FRIENDS, null, null, new Callback(){

            @Override
            public void onCompleted(GraphResponse graphResponse) {
                if (graphJSONArrayCallback != null) {
                    Object object = graphResponse.getJSONObject();
                    object = object != null ? object.optJSONArray("data") : null;
                    graphJSONArrayCallback.onCompleted((JSONArray)object, graphResponse);
                }
            }
        });
    }

    public static GraphRequest newPlacesSearchRequest(AccessToken accessToken, Location object, int n, int n2, String string, final GraphJSONArrayCallback graphJSONArrayCallback) {
        if (object == null && Utility.isNullOrEmpty(string)) {
            throw new FacebookException("Either location or searchText must be specified.");
        }
        Bundle bundle = new Bundle(5);
        bundle.putString(DEBUG_MESSAGE_TYPE_KEY, "place");
        bundle.putInt("limit", n2);
        if (object != null) {
            bundle.putString("center", String.format(Locale.US, "%f,%f", object.getLatitude(), object.getLongitude()));
            bundle.putInt("distance", n);
        }
        if (!Utility.isNullOrEmpty(string)) {
            bundle.putString("q", string);
        }
        object = new Callback(){

            @Override
            public void onCompleted(GraphResponse graphResponse) {
                if (graphJSONArrayCallback != null) {
                    Object object = graphResponse.getJSONObject();
                    object = object != null ? object.optJSONArray("data") : null;
                    graphJSONArrayCallback.onCompleted((JSONArray)object, graphResponse);
                }
            }
        };
        return new GraphRequest(accessToken, SEARCH, bundle, HttpMethod.GET, (Callback)object);
    }

    public static GraphRequest newPostRequest(AccessToken object, String string, JSONObject jSONObject, Callback callback) {
        object = new GraphRequest((AccessToken)object, string, null, HttpMethod.POST, callback);
        object.setGraphObject(jSONObject);
        return object;
    }

    public static GraphRequest newUploadPhotoRequest(AccessToken accessToken, String string, Bitmap bitmap, String string2, Bundle bundle, Callback callback) {
        string = GraphRequest.getDefaultPhotoPathIfNull(string);
        Bundle bundle2 = new Bundle();
        if (bundle != null) {
            bundle2.putAll(bundle);
        }
        bundle2.putParcelable(PICTURE_PARAM, (Parcelable)bitmap);
        if (string2 != null && !string2.isEmpty()) {
            bundle2.putString(CAPTION_PARAM, string2);
        }
        return new GraphRequest(accessToken, string, bundle2, HttpMethod.POST, callback);
    }

    public static GraphRequest newUploadPhotoRequest(AccessToken accessToken, String string, Uri uri, String string2, Bundle bundle, Callback callback) throws FileNotFoundException {
        string = GraphRequest.getDefaultPhotoPathIfNull(string);
        if (Utility.isFileUri(uri)) {
            return GraphRequest.newUploadPhotoRequest(accessToken, string, new File(uri.getPath()), string2, bundle, callback);
        }
        if (!Utility.isContentUri(uri)) {
            throw new FacebookException("The photo Uri must be either a file:// or content:// Uri");
        }
        Bundle bundle2 = new Bundle();
        if (bundle != null) {
            bundle2.putAll(bundle);
        }
        bundle2.putParcelable(PICTURE_PARAM, (Parcelable)uri);
        if (string2 != null && !string2.isEmpty()) {
            bundle2.putString(CAPTION_PARAM, string2);
        }
        return new GraphRequest(accessToken, string, bundle2, HttpMethod.POST, callback);
    }

    public static GraphRequest newUploadPhotoRequest(AccessToken accessToken, String string, File file, String string2, Bundle bundle, Callback callback) throws FileNotFoundException {
        string = GraphRequest.getDefaultPhotoPathIfNull(string);
        file = ParcelFileDescriptor.open((File)file, (int)268435456);
        Bundle bundle2 = new Bundle();
        if (bundle != null) {
            bundle2.putAll(bundle);
        }
        bundle2.putParcelable(PICTURE_PARAM, (Parcelable)file);
        if (string2 != null && !string2.isEmpty()) {
            bundle2.putString(CAPTION_PARAM, string2);
        }
        return new GraphRequest(accessToken, string, bundle2, HttpMethod.POST, callback);
    }

    private static String parameterToString(Object object) {
        if (object instanceof String) {
            return (String)object;
        }
        if (!(object instanceof Boolean) && !(object instanceof Number)) {
            if (object instanceof Date) {
                return new SimpleDateFormat(ISO_8601_FORMAT_STRING, Locale.US).format(object);
            }
            throw new IllegalArgumentException("Unsupported parameter type.");
        }
        return object.toString();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private static void processGraphObject(JSONObject var0, String var1_1, KeyValueSerializer var2_2) throws IOException {
        if (!GraphRequest.isMeRequest((String)var1_1)) ** GOTO lbl-1000
        var3_3 = var1_1.indexOf(":");
        var4_4 = var1_1.indexOf("?");
        if (var3_3 > 3 && (var4_4 == -1 || var3_3 < var4_4)) {
            var3_3 = 1;
        } else lbl-1000: // 2 sources:
        {
            var3_3 = 0;
        }
        var1_1 = var0.keys();
        while (var1_1.hasNext() != false) {
            var6_6 = (String)var1_1.next();
            var7_7 = var0.opt(var6_6);
            var5_5 = var3_3 != 0 && var6_6.equalsIgnoreCase("image") != false;
            GraphRequest.processGraphObjectProperty(var6_6, var7_7, var2_2, var5_5);
        }
    }

    private static void processGraphObjectProperty(String string, Object object, KeyValueSerializer keyValueSerializer, boolean bl) throws IOException {
        Class<?> class_ = object.getClass();
        if (JSONObject.class.isAssignableFrom(class_)) {
            object = (JSONObject)object;
            if (bl) {
                class_ = object.keys();
                while (class_.hasNext()) {
                    String string2 = (String)class_.next();
                    GraphRequest.processGraphObjectProperty(String.format("%s[%s]", string, string2), object.opt(string2), keyValueSerializer, bl);
                }
            } else {
                if (object.has("id")) {
                    GraphRequest.processGraphObjectProperty(string, object.optString("id"), keyValueSerializer, bl);
                    return;
                }
                if (object.has("url")) {
                    GraphRequest.processGraphObjectProperty(string, object.optString("url"), keyValueSerializer, bl);
                    return;
                }
                if (object.has("fbsdk:create_object")) {
                    GraphRequest.processGraphObjectProperty(string, object.toString(), keyValueSerializer, bl);
                    return;
                }
            }
        } else if (JSONArray.class.isAssignableFrom(class_)) {
            object = (JSONArray)object;
            int n = object.length();
            for (int i = 0; i < n; ++i) {
                GraphRequest.processGraphObjectProperty(String.format(Locale.ROOT, "%s[%d]", string, i), object.opt(i), keyValueSerializer, bl);
            }
        } else if (!(String.class.isAssignableFrom(class_) || Number.class.isAssignableFrom(class_) || Boolean.class.isAssignableFrom(class_))) {
            if (Date.class.isAssignableFrom(class_)) {
                object = (Date)object;
                keyValueSerializer.writeString(string, new SimpleDateFormat(ISO_8601_FORMAT_STRING, Locale.US).format((Date)object));
                return;
            }
        } else {
            keyValueSerializer.writeString(string, object.toString());
        }
    }

    private static void processRequest(GraphRequestBatch object, Logger logger, int n, URL object2, OutputStream object3, boolean bl) throws IOException, JSONException {
        object3 = new Serializer((OutputStream)object3, logger, bl);
        if (n == 1) {
            object = object.get(0);
            HashMap<String, Attachment> hashMap = new HashMap<String, Attachment>();
            for (String string : object.parameters.keySet()) {
                Object object4 = object.parameters.get(string);
                if (!GraphRequest.isSupportedAttachmentType(object4)) continue;
                hashMap.put(string, new Attachment((GraphRequest)object, object4));
            }
            if (logger != null) {
                logger.append("  Parameters:\n");
            }
            GraphRequest.serializeParameters(object.parameters, (Serializer)object3, (GraphRequest)object);
            if (logger != null) {
                logger.append("  Attachments:\n");
            }
            GraphRequest.serializeAttachments(hashMap, (Serializer)object3);
            if (object.graphObject != null) {
                GraphRequest.processGraphObject(object.graphObject, object2.getPath(), (KeyValueSerializer)object3);
                return;
            }
        } else {
            object2 = GraphRequest.getBatchAppId((GraphRequestBatch)object);
            if (Utility.isNullOrEmpty((String)object2)) {
                throw new FacebookException("App ID was not specified at the request or Settings.");
            }
            object3.writeString(BATCH_APP_ID_PARAM, (String)object2);
            object2 = new HashMap<String, Attachment>();
            GraphRequest.serializeRequestsAsJSON((Serializer)object3, (Collection<GraphRequest>)object, object2);
            if (logger != null) {
                logger.append("  Attachments:\n");
            }
            GraphRequest.serializeAttachments(object2, (Serializer)object3);
        }
    }

    static void runCallbacks(final GraphRequestBatch graphRequestBatch, List<GraphResponse> object) {
        int n = graphRequestBatch.size();
        final ArrayList<Pair> arrayList = new ArrayList<Pair>();
        for (int i = 0; i < n; ++i) {
            GraphRequest graphRequest = graphRequestBatch.get(i);
            if (graphRequest.callback == null) continue;
            arrayList.add(new Pair((Object)graphRequest.callback, object.get(i)));
        }
        if (arrayList.size() > 0) {
            object = new Runnable(){

                @Override
                public void run() {
                    for (Pair pair : arrayList) {
                        ((Callback)pair.first).onCompleted((GraphResponse)pair.second);
                    }
                    Iterator<Object> iterator = graphRequestBatch.getCallbacks().iterator();
                    while (iterator.hasNext()) {
                        ((GraphRequestBatch.Callback)iterator.next()).onBatchCompleted(graphRequestBatch);
                    }
                }
            };
            if ((graphRequestBatch = graphRequestBatch.getCallbackHandler()) == null) {
                object.run();
                return;
            }
            graphRequestBatch.post((Runnable)object);
        }
    }

    private static void serializeAttachments(Map<String, Attachment> map, Serializer serializer) throws IOException {
        for (String string : map.keySet()) {
            Attachment attachment = map.get(string);
            if (!GraphRequest.isSupportedAttachmentType(attachment.getValue())) continue;
            serializer.writeObject(string, attachment.getValue(), attachment.getRequest());
        }
    }

    private static void serializeParameters(Bundle bundle, Serializer serializer, GraphRequest graphRequest) throws IOException {
        for (String string : bundle.keySet()) {
            Object object = bundle.get(string);
            if (!GraphRequest.isSupportedParameterType(object)) continue;
            serializer.writeObject(string, object, graphRequest);
        }
    }

    private static void serializeRequestsAsJSON(Serializer serializer, Collection<GraphRequest> collection, Map<String, Attachment> map) throws JSONException, IOException {
        JSONArray jSONArray = new JSONArray();
        Iterator<GraphRequest> iterator = collection.iterator();
        while (iterator.hasNext()) {
            iterator.next().serializeToBatch(jSONArray, map);
        }
        serializer.writeRequestsAsJson(BATCH_PARAM, jSONArray, collection);
    }

    private void serializeToBatch(JSONArray jSONArray, Map<String, Attachment> object) throws JSONException, IOException {
        JSONObject jSONObject = new JSONObject();
        if (this.batchEntryName != null) {
            jSONObject.put(BATCH_ENTRY_NAME_PARAM, (Object)this.batchEntryName);
            jSONObject.put(BATCH_ENTRY_OMIT_RESPONSE_ON_SUCCESS_PARAM, this.batchEntryOmitResultOnSuccess);
        }
        if (this.batchEntryDependsOn != null) {
            jSONObject.put(BATCH_ENTRY_DEPENDS_ON_PARAM, (Object)this.batchEntryDependsOn);
        }
        String string = this.getRelativeUrlForBatchedRequest();
        jSONObject.put(BATCH_RELATIVE_URL_PARAM, (Object)string);
        jSONObject.put(BATCH_METHOD_PARAM, (Object)this.httpMethod);
        if (this.accessToken != null) {
            Logger.registerAccessToken(this.accessToken.getToken());
        }
        ArrayList<String> arrayList = new ArrayList<String>();
        for (Object object2 : this.parameters.keySet()) {
            if (!GraphRequest.isSupportedAttachmentType(object2 = this.parameters.get((String)object2))) continue;
            String string2 = String.format(Locale.ROOT, "%s%d", ATTACHMENT_FILENAME_PREFIX, object.size());
            arrayList.add(string2);
            object.put(string2, new Attachment(this, object2));
        }
        if (!arrayList.isEmpty()) {
            jSONObject.put(ATTACHED_FILES_PARAM, (Object)TextUtils.join((CharSequence)",", arrayList));
        }
        if (this.graphObject != null) {
            object = new ArrayList();
            GraphRequest.processGraphObject(this.graphObject, string, new KeyValueSerializer((ArrayList)object){
                final /* synthetic */ ArrayList val$keysAndValues;
                {
                    this.val$keysAndValues = arrayList;
                }

                @Override
                public void writeString(String string, String string2) throws IOException {
                    this.val$keysAndValues.add(String.format(Locale.US, "%s=%s", string, URLEncoder.encode(string2, "UTF-8")));
                }
            });
            jSONObject.put(BATCH_BODY_PARAM, (Object)TextUtils.join((CharSequence)"&", (Iterable)object));
        }
        jSONArray.put((Object)jSONObject);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    static final void serializeToUrlConnection(GraphRequestBatch graphRequestBatch, HttpURLConnection object) throws IOException, JSONException {
        void var0_4;
        Object object2;
        block10 : {
            Logger logger;
            block11 : {
                boolean bl;
                int n;
                URL uRL;
                int n2;
                block9 : {
                    logger = new Logger(LoggingBehavior.REQUESTS, "Request");
                    n = graphRequestBatch.size();
                    bl = GraphRequest.isGzipCompressible(graphRequestBatch);
                    n2 = 0;
                    object2 = n == 1 ? graphRequestBatch.get((int)0).httpMethod : HttpMethod.POST;
                    object.setRequestMethod(object2.name());
                    GraphRequest.setConnectionContentType((HttpURLConnection)object, bl);
                    uRL = object.getURL();
                    logger.append("Request:\n");
                    logger.appendKeyValue("Id", graphRequestBatch.getId());
                    logger.appendKeyValue("URL", uRL);
                    logger.appendKeyValue("Method", object.getRequestMethod());
                    logger.appendKeyValue(USER_AGENT_HEADER, object.getRequestProperty(USER_AGENT_HEADER));
                    logger.appendKeyValue(CONTENT_TYPE_HEADER, object.getRequestProperty(CONTENT_TYPE_HEADER));
                    object.setConnectTimeout(graphRequestBatch.getTimeout());
                    object.setReadTimeout(graphRequestBatch.getTimeout());
                    if (object2 == HttpMethod.POST) {
                        n2 = 1;
                    }
                    if (n2 == 0) {
                        logger.log();
                        return;
                    }
                    object.setDoOutput(true);
                    object = new BufferedOutputStream(object.getOutputStream());
                    if (!bl) break block9;
                    try {
                        object2 = new GZIPOutputStream((OutputStream)object);
                        object = object2;
                    }
                    catch (Throwable throwable) {
                        object2 = object;
                        break block10;
                    }
                }
                Object object3 = object;
                object2 = object;
                try {
                    if (GraphRequest.hasOnProgressCallbacks(graphRequestBatch)) {
                        object2 = object;
                        object3 = new ProgressNoopOutputStream(graphRequestBatch.getCallbackHandler());
                        object2 = object;
                        GraphRequest.processRequest(graphRequestBatch, null, n, uRL, (OutputStream)object3, bl);
                        object2 = object;
                        n2 = object3.getMaxProgress();
                        object2 = object;
                        object3 = new ProgressOutputStream((OutputStream)object, graphRequestBatch, object3.getProgressMap(), n2);
                    }
                    object2 = object3;
                    GraphRequest.processRequest(graphRequestBatch, logger, n, uRL, (OutputStream)object3, bl);
                    if (object3 == null) break block11;
                }
                catch (Throwable throwable) {}
                object3.close();
            }
            logger.log();
            return;
            break block10;
            catch (Throwable throwable) {
                object2 = null;
            }
        }
        if (object2 == null) throw var0_4;
        object2.close();
        throw var0_4;
    }

    private static void setConnectionContentType(HttpURLConnection httpURLConnection, boolean bl) {
        if (bl) {
            httpURLConnection.setRequestProperty(CONTENT_TYPE_HEADER, "application/x-www-form-urlencoded");
            httpURLConnection.setRequestProperty(CONTENT_ENCODING_HEADER, "gzip");
            return;
        }
        httpURLConnection.setRequestProperty(CONTENT_TYPE_HEADER, GraphRequest.getMimeContentType());
    }

    public static final void setDefaultBatchApplicationId(String string) {
        defaultBatchApplicationId = string;
    }

    static final boolean shouldWarnOnMissingFieldsParam(GraphRequest arrstring) {
        boolean bl;
        block6 : {
            block5 : {
                String string = arrstring.getVersion();
                if (Utility.isNullOrEmpty(string)) {
                    return true;
                }
                arrstring = string;
                if (string.startsWith("v")) {
                    arrstring = string.substring(1);
                }
                arrstring = arrstring.split("\\.");
                boolean bl2 = false;
                if (arrstring.length >= 2 && Integer.parseInt(arrstring[0]) > 2) break block5;
                bl = bl2;
                if (Integer.parseInt(arrstring[0]) < 2) break block6;
                bl = bl2;
                if (Integer.parseInt(arrstring[1]) < 4) break block6;
            }
            bl = true;
        }
        return bl;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static HttpURLConnection toHttpConnection(GraphRequestBatch object) {
        Object object2;
        block6 : {
            Object var2_4;
            GraphRequest.validateFieldsParamForGetRequests((GraphRequestBatch)object);
            try {
                object2 = object.size() == 1 ? new URL(object.get(0).getUrlForSingleRequest()) : new URL(ServerProtocol.getGraphUrlBase());
                var2_4 = null;
            }
            catch (MalformedURLException malformedURLException) {
                throw new FacebookException("could not construct URL for request", malformedURLException);
            }
            object2 = GraphRequest.createConnection((URL)object2);
            try {
                GraphRequest.serializeToUrlConnection((GraphRequestBatch)object, (HttpURLConnection)object2);
                return object2;
            }
            catch (IOException | JSONException object3) {
                object = object2;
                object2 = object3;
            }
            break block6;
            catch (IOException | JSONException iOException) {
                object = var2_4;
            }
        }
        Utility.disconnectQuietly((URLConnection)object);
        throw new FacebookException("could not construct request body", (Throwable)object2);
    }

    public static HttpURLConnection toHttpConnection(Collection<GraphRequest> collection) {
        Validate.notEmptyAndContainsNoNulls(collection, "requests");
        return GraphRequest.toHttpConnection(new GraphRequestBatch(collection));
    }

    public static /* varargs */ HttpURLConnection toHttpConnection(GraphRequest ... arrgraphRequest) {
        return GraphRequest.toHttpConnection(Arrays.asList(arrgraphRequest));
    }

    static final void validateFieldsParamForGetRequests(GraphRequestBatch object) {
        object = object.iterator();
        while (object.hasNext()) {
            Bundle bundle;
            GraphRequest graphRequest = (GraphRequest)object.next();
            if (!HttpMethod.GET.equals((Object)graphRequest.getHttpMethod()) || !GraphRequest.shouldWarnOnMissingFieldsParam(graphRequest) || (bundle = graphRequest.getParameters()).containsKey(FIELDS_PARAM) && !Utility.isNullOrEmpty(bundle.getString(FIELDS_PARAM))) continue;
            Logger.log(LoggingBehavior.DEVELOPER_ERRORS, 5, "Request", "starting with Graph API v2.4, GET requests for /%s should contain an explicit \"fields\" parameter.", graphRequest.getGraphPath());
        }
    }

    public final GraphResponse executeAndWait() {
        return GraphRequest.executeAndWait(this);
    }

    public final GraphRequestAsyncTask executeAsync() {
        return GraphRequest.executeBatchAsync(this);
    }

    public final AccessToken getAccessToken() {
        return this.accessToken;
    }

    public final String getBatchEntryDependsOn() {
        return this.batchEntryDependsOn;
    }

    public final String getBatchEntryName() {
        return this.batchEntryName;
    }

    public final boolean getBatchEntryOmitResultOnSuccess() {
        return this.batchEntryOmitResultOnSuccess;
    }

    public final Callback getCallback() {
        return this.callback;
    }

    public final JSONObject getGraphObject() {
        return this.graphObject;
    }

    public final String getGraphPath() {
        return this.graphPath;
    }

    public final HttpMethod getHttpMethod() {
        return this.httpMethod;
    }

    public final Bundle getParameters() {
        return this.parameters;
    }

    final String getRelativeUrlForBatchedRequest() {
        if (this.overriddenURL != null) {
            throw new FacebookException("Can't override URL for a batch request");
        }
        String string = String.format(GRAPH_PATH_FORMAT, ServerProtocol.getGraphUrlBase(), this.getGraphPathWithVersion());
        this.addCommonParameters();
        string = Uri.parse((String)this.appendParametersToBaseUrl(string));
        return String.format("%s?%s", string.getPath(), string.getQuery());
    }

    public final Object getTag() {
        return this.tag;
    }

    final String getUrlForSingleRequest() {
        if (this.overriddenURL != null) {
            return this.overriddenURL.toString();
        }
        String string = this.getHttpMethod() == HttpMethod.POST && this.graphPath != null && this.graphPath.endsWith(VIDEOS_SUFFIX) ? ServerProtocol.getGraphVideoUrlBase() : ServerProtocol.getGraphUrlBase();
        string = String.format(GRAPH_PATH_FORMAT, string, this.getGraphPathWithVersion());
        this.addCommonParameters();
        return this.appendParametersToBaseUrl(string);
    }

    public final String getVersion() {
        return this.version;
    }

    public final void setAccessToken(AccessToken accessToken) {
        this.accessToken = accessToken;
    }

    public final void setBatchEntryDependsOn(String string) {
        this.batchEntryDependsOn = string;
    }

    public final void setBatchEntryName(String string) {
        this.batchEntryName = string;
    }

    public final void setBatchEntryOmitResultOnSuccess(boolean bl) {
        this.batchEntryOmitResultOnSuccess = bl;
    }

    public final void setCallback(final Callback callback) {
        if (!FacebookSdk.isLoggingBehaviorEnabled(LoggingBehavior.GRAPH_API_DEBUG_INFO) && !FacebookSdk.isLoggingBehaviorEnabled(LoggingBehavior.GRAPH_API_DEBUG_WARNING)) {
            this.callback = callback;
            return;
        }
        this.callback = new Callback(){

            @Override
            public void onCompleted(GraphResponse graphResponse) {
                Object object = graphResponse.getJSONObject();
                object = object != null ? object.optJSONObject(GraphRequest.DEBUG_KEY) : null;
                JSONArray jSONArray = object != null ? object.optJSONArray(GraphRequest.DEBUG_MESSAGES_KEY) : null;
                if (jSONArray != null) {
                    for (int i = 0; i < jSONArray.length(); ++i) {
                        Object object2 = jSONArray.optJSONObject(i);
                        object = object2 != null ? object2.optString(GraphRequest.DEBUG_MESSAGE_KEY) : null;
                        CharSequence charSequence = object2 != null ? object2.optString(GraphRequest.DEBUG_MESSAGE_TYPE_KEY) : null;
                        object2 = object2 != null ? object2.optString(GraphRequest.DEBUG_MESSAGE_LINK_KEY) : null;
                        if (object == null || charSequence == null) continue;
                        LoggingBehavior loggingBehavior = LoggingBehavior.GRAPH_API_DEBUG_INFO;
                        if (charSequence.equals(GraphRequest.DEBUG_SEVERITY_WARNING)) {
                            loggingBehavior = LoggingBehavior.GRAPH_API_DEBUG_WARNING;
                        }
                        charSequence = object;
                        if (!Utility.isNullOrEmpty((String)object2)) {
                            charSequence = new StringBuilder();
                            charSequence.append((String)object);
                            charSequence.append(" Link: ");
                            charSequence.append((String)object2);
                            charSequence = charSequence.toString();
                        }
                        Logger.log(loggingBehavior, GraphRequest.TAG, (String)charSequence);
                    }
                }
                if (callback != null) {
                    callback.onCompleted(graphResponse);
                }
            }
        };
    }

    public final void setGraphObject(JSONObject jSONObject) {
        this.graphObject = jSONObject;
    }

    public final void setGraphPath(String string) {
        this.graphPath = string;
    }

    public final void setHttpMethod(HttpMethod httpMethod) {
        if (this.overriddenURL != null && httpMethod != HttpMethod.GET) {
            throw new FacebookException("Can't change HTTP method on request with overridden URL.");
        }
        if (httpMethod == null) {
            httpMethod = HttpMethod.GET;
        }
        this.httpMethod = httpMethod;
    }

    public final void setParameters(Bundle bundle) {
        this.parameters = bundle;
    }

    public final void setSkipClientToken(boolean bl) {
        this.skipClientToken = bl;
    }

    public final void setTag(Object object) {
        this.tag = object;
    }

    public final void setVersion(String string) {
        this.version = string;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{Request: ");
        stringBuilder.append(" accessToken: ");
        Object object = this.accessToken == null ? "null" : this.accessToken;
        stringBuilder.append(object);
        stringBuilder.append(", graphPath: ");
        stringBuilder.append(this.graphPath);
        stringBuilder.append(", graphObject: ");
        stringBuilder.append((Object)this.graphObject);
        stringBuilder.append(", httpMethod: ");
        stringBuilder.append((Object)this.httpMethod);
        stringBuilder.append(", parameters: ");
        stringBuilder.append((Object)this.parameters);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    private static class Attachment {
        private final GraphRequest request;
        private final Object value;

        public Attachment(GraphRequest graphRequest, Object object) {
            this.request = graphRequest;
            this.value = object;
        }

        public GraphRequest getRequest() {
            return this.request;
        }

        public Object getValue() {
            return this.value;
        }
    }

    public static interface Callback {
        public void onCompleted(GraphResponse var1);
    }

    public static interface GraphJSONArrayCallback {
        public void onCompleted(JSONArray var1, GraphResponse var2);
    }

    public static interface GraphJSONObjectCallback {
        public void onCompleted(JSONObject var1, GraphResponse var2);
    }

    private static interface KeyValueSerializer {
        public void writeString(String var1, String var2) throws IOException;
    }

    public static interface OnProgressCallback
    extends Callback {
        public void onProgress(long var1, long var3);
    }

    public static class ParcelableResourceWithMimeType<RESOURCE extends Parcelable>
    implements Parcelable {
        public static final Parcelable.Creator<ParcelableResourceWithMimeType> CREATOR = new Parcelable.Creator<ParcelableResourceWithMimeType>(){

            public ParcelableResourceWithMimeType createFromParcel(Parcel parcel) {
                return new ParcelableResourceWithMimeType(parcel);
            }

            public ParcelableResourceWithMimeType[] newArray(int n) {
                return new ParcelableResourceWithMimeType[n];
            }
        };
        private final String mimeType;
        private final RESOURCE resource;

        private ParcelableResourceWithMimeType(Parcel parcel) {
            this.mimeType = parcel.readString();
            this.resource = parcel.readParcelable(FacebookSdk.getApplicationContext().getClassLoader());
        }

        public ParcelableResourceWithMimeType(RESOURCE RESOURCE, String string) {
            this.mimeType = string;
            this.resource = RESOURCE;
        }

        public int describeContents() {
            return 1;
        }

        public String getMimeType() {
            return this.mimeType;
        }

        public RESOURCE getResource() {
            return this.resource;
        }

        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeString(this.mimeType);
            parcel.writeParcelable(this.resource, n);
        }

    }

    private static class Serializer
    implements KeyValueSerializer {
        private boolean firstWrite = true;
        private final Logger logger;
        private final OutputStream outputStream;
        private boolean useUrlEncode = false;

        public Serializer(OutputStream outputStream, Logger logger, boolean bl) {
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
            if (object instanceof ParcelableResourceWithMimeType) {
                object2 = (ParcelableResourceWithMimeType)object;
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

}
