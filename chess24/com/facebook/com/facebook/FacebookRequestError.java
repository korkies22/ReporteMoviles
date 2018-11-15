/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.facebook;

import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.FacebookServiceException;
import com.facebook.internal.FacebookRequestErrorClassification;
import com.facebook.internal.FetchedAppSettingsManager;
import com.facebook.internal.Utility;
import java.net.HttpURLConnection;
import org.json.JSONException;
import org.json.JSONObject;

public final class FacebookRequestError
implements Parcelable {
    private static final String BODY_KEY = "body";
    private static final String CODE_KEY = "code";
    public static final Parcelable.Creator<FacebookRequestError> CREATOR;
    private static final String ERROR_CODE_FIELD_KEY = "code";
    private static final String ERROR_CODE_KEY = "error_code";
    private static final String ERROR_IS_TRANSIENT_KEY = "is_transient";
    private static final String ERROR_KEY = "error";
    private static final String ERROR_MESSAGE_FIELD_KEY = "message";
    private static final String ERROR_MSG_KEY = "error_msg";
    private static final String ERROR_REASON_KEY = "error_reason";
    private static final String ERROR_SUB_CODE_KEY = "error_subcode";
    private static final String ERROR_TYPE_FIELD_KEY = "type";
    private static final String ERROR_USER_MSG_KEY = "error_user_msg";
    private static final String ERROR_USER_TITLE_KEY = "error_user_title";
    static final Range HTTP_RANGE_SUCCESS;
    public static final int INVALID_ERROR_CODE = -1;
    public static final int INVALID_HTTP_STATUS_CODE = -1;
    private final Object batchRequestResult;
    private final Category category;
    private final HttpURLConnection connection;
    private final int errorCode;
    private final String errorMessage;
    private final String errorRecoveryMessage;
    private final String errorType;
    private final String errorUserMessage;
    private final String errorUserTitle;
    private final FacebookException exception;
    private final JSONObject requestResult;
    private final JSONObject requestResultBody;
    private final int requestStatusCode;
    private final int subErrorCode;

    static {
        HTTP_RANGE_SUCCESS = new Range(200, 299);
        CREATOR = new Parcelable.Creator<FacebookRequestError>(){

            public FacebookRequestError createFromParcel(Parcel parcel) {
                return new FacebookRequestError(parcel);
            }

            public FacebookRequestError[] newArray(int n) {
                return new FacebookRequestError[n];
            }
        };
    }

    private FacebookRequestError(int n, int n2, int n3, String object, String object2, String string, String string2, boolean bl, JSONObject jSONObject, JSONObject jSONObject2, Object object3, HttpURLConnection httpURLConnection, FacebookException facebookException) {
        this.requestStatusCode = n;
        this.errorCode = n2;
        this.subErrorCode = n3;
        this.errorType = object;
        this.errorMessage = object2;
        this.requestResultBody = jSONObject;
        this.requestResult = jSONObject2;
        this.batchRequestResult = object3;
        this.connection = httpURLConnection;
        this.errorUserTitle = string;
        this.errorUserMessage = string2;
        if (facebookException != null) {
            this.exception = facebookException;
            n = 1;
        } else {
            this.exception = new FacebookServiceException(this, (String)object2);
            n = 0;
        }
        object2 = FacebookRequestError.getErrorClassification();
        object = n != 0 ? Category.OTHER : object2.classify(n2, n3, bl);
        this.category = object;
        this.errorRecoveryMessage = object2.getRecoveryMessage(this.category);
    }

    public FacebookRequestError(int n, String string, String string2) {
        this(-1, n, -1, string, string2, null, null, false, null, null, null, null, null);
    }

    private FacebookRequestError(Parcel parcel) {
        this(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), false, null, null, null, null, null);
    }

    FacebookRequestError(HttpURLConnection httpURLConnection, Exception exception) {
        exception = exception instanceof FacebookException ? (FacebookException)exception : new FacebookException(exception);
        this(-1, -1, -1, null, null, null, null, false, null, null, null, httpURLConnection, (FacebookException)exception);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static FacebookRequestError checkResponseAndCreateError(JSONObject object, Object object2, HttpURLConnection httpURLConnection) {
        void var2_4;
        Object object3;
        void var1_3;
        int n;
        block4 : {
            int n2;
            boolean bl;
            Object object4;
            Object object5;
            Object object6;
            int n3;
            boolean bl2;
            JSONObject jSONObject;
            block5 : {
                block6 : {
                    try {
                        if (!object.has("code")) return null;
                        n = object.getInt("code");
                        object3 = Utility.getStringPropertyAsJSON(object, BODY_KEY, "FACEBOOK_NON_JSON_RESULT");
                        if (object3 == null || !(object3 instanceof JSONObject)) break block4;
                        jSONObject = (JSONObject)object3;
                        bl2 = jSONObject.has(ERROR_KEY);
                        bl = true;
                        n3 = -1;
                        if (bl2) {
                            JSONObject jSONObject2 = (JSONObject)Utility.getStringPropertyAsJSON(jSONObject, ERROR_KEY, null);
                            object3 = jSONObject2.optString(ERROR_TYPE_FIELD_KEY, null);
                            object6 = jSONObject2.optString(ERROR_MESSAGE_FIELD_KEY, null);
                            n3 = jSONObject2.optInt("code", -1);
                            n2 = jSONObject2.optInt(ERROR_SUB_CODE_KEY, -1);
                            object4 = jSONObject2.optString(ERROR_USER_MSG_KEY, null);
                            object5 = jSONObject2.optString(ERROR_USER_TITLE_KEY, null);
                            bl2 = jSONObject2.optBoolean(ERROR_IS_TRANSIENT_KEY, false);
                            break block5;
                        }
                        if (!jSONObject.has(ERROR_CODE_KEY) && !jSONObject.has(ERROR_MSG_KEY) && !jSONObject.has(ERROR_REASON_KEY)) break block6;
                        object3 = jSONObject.optString(ERROR_REASON_KEY, null);
                        object6 = jSONObject.optString(ERROR_MSG_KEY, null);
                        n3 = jSONObject.optInt(ERROR_CODE_KEY, -1);
                        n2 = jSONObject.optInt(ERROR_SUB_CODE_KEY, -1);
                        bl2 = false;
                        object5 = object4 = null;
                        break block5;
                    }
                    catch (JSONException jSONException) {
                        return null;
                    }
                }
                bl2 = bl = false;
                n2 = -1;
                object5 = object4 = (object6 = (object3 = null));
            }
            if (bl) {
                return new FacebookRequestError(n, n3, n2, (String)object3, (String)object6, (String)object5, (String)object4, bl2, jSONObject, (JSONObject)object, var1_3, (HttpURLConnection)var2_4, null);
            }
        }
        if (HTTP_RANGE_SUCCESS.contains(n)) return null;
        object3 = object.has(BODY_KEY) ? (JSONObject)Utility.getStringPropertyAsJSON(object, BODY_KEY, "FACEBOOK_NON_JSON_RESULT") : null;
        return new FacebookRequestError(n, -1, -1, null, null, null, null, false, (JSONObject)object3, (JSONObject)object, var1_3, (HttpURLConnection)var2_4, null);
    }

    static FacebookRequestErrorClassification getErrorClassification() {
        synchronized (FacebookRequestError.class) {
            Object object = FetchedAppSettingsManager.getAppSettingsWithoutQuery(FacebookSdk.getApplicationId());
            if (object == null) {
                object = FacebookRequestErrorClassification.getDefaultErrorClassification();
                return object;
            }
            object = object.getErrorClassification();
            return object;
        }
    }

    public int describeContents() {
        return 0;
    }

    public Object getBatchRequestResult() {
        return this.batchRequestResult;
    }

    public Category getCategory() {
        return this.category;
    }

    public HttpURLConnection getConnection() {
        return this.connection;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public String getErrorMessage() {
        if (this.errorMessage != null) {
            return this.errorMessage;
        }
        return this.exception.getLocalizedMessage();
    }

    public String getErrorRecoveryMessage() {
        return this.errorRecoveryMessage;
    }

    public String getErrorType() {
        return this.errorType;
    }

    public String getErrorUserMessage() {
        return this.errorUserMessage;
    }

    public String getErrorUserTitle() {
        return this.errorUserTitle;
    }

    public FacebookException getException() {
        return this.exception;
    }

    public JSONObject getRequestResult() {
        return this.requestResult;
    }

    public JSONObject getRequestResultBody() {
        return this.requestResultBody;
    }

    public int getRequestStatusCode() {
        return this.requestStatusCode;
    }

    public int getSubErrorCode() {
        return this.subErrorCode;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("{HttpStatus: ");
        stringBuilder.append(this.requestStatusCode);
        stringBuilder.append(", errorCode: ");
        stringBuilder.append(this.errorCode);
        stringBuilder.append(", errorType: ");
        stringBuilder.append(this.errorType);
        stringBuilder.append(", errorMessage: ");
        stringBuilder.append(this.getErrorMessage());
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.requestStatusCode);
        parcel.writeInt(this.errorCode);
        parcel.writeInt(this.subErrorCode);
        parcel.writeString(this.errorType);
        parcel.writeString(this.errorMessage);
        parcel.writeString(this.errorUserTitle);
        parcel.writeString(this.errorUserMessage);
    }

    public static enum Category {
        LOGIN_RECOVERABLE,
        OTHER,
        TRANSIENT;
        

        private Category() {
        }
    }

    private static class Range {
        private final int end;
        private final int start;

        private Range(int n, int n2) {
            this.start = n;
            this.end = n2;
        }

        boolean contains(int n) {
            if (this.start <= n && n <= this.end) {
                return true;
            }
            return false;
        }
    }

}
