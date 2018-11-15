/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.AlertDialog
 *  android.app.AlertDialog$Builder
 *  android.app.Dialog
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.content.res.Resources
 *  android.graphics.Bitmap
 *  android.graphics.drawable.BitmapDrawable
 *  android.graphics.drawable.Drawable
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.text.Html
 *  android.text.TextUtils
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.Button
 *  android.widget.ProgressBar
 *  android.widget.TextView
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.facebook.login;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.facebook.AccessToken;
import com.facebook.AccessTokenSource;
import com.facebook.FacebookActivity;
import com.facebook.FacebookException;
import com.facebook.FacebookRequestError;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.common.R;
import com.facebook.devicerequests.internal.DeviceRequestsHelper;
import com.facebook.internal.FetchedAppSettingsManager;
import com.facebook.internal.SmartLoginOption;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import com.facebook.login.DeviceAuthMethodHandler;
import com.facebook.login.LoginClient;
import com.facebook.login.LoginFragment;
import com.facebook.login.LoginMethodHandler;
import java.util.Collection;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONException;
import org.json.JSONObject;

public class DeviceAuthDialog
extends DialogFragment {
    private static final String DEVICE_LOGIN_ENDPOINT = "device/login";
    private static final String DEVICE_LOGIN_STATUS_ENDPOINT = "device/login_status";
    private static final int LOGIN_ERROR_SUBCODE_AUTHORIZATION_DECLINED = 1349173;
    private static final int LOGIN_ERROR_SUBCODE_AUTHORIZATION_PENDING = 1349174;
    private static final int LOGIN_ERROR_SUBCODE_CODE_EXPIRED = 1349152;
    private static final int LOGIN_ERROR_SUBCODE_EXCESSIVE_POLLING = 1349172;
    private static final String REQUEST_STATE_KEY = "request_state";
    private AtomicBoolean completed = new AtomicBoolean();
    private TextView confirmationCode;
    private volatile GraphRequestAsyncTask currentGraphRequestPoll;
    private volatile RequestState currentRequestState;
    private DeviceAuthMethodHandler deviceAuthMethodHandler;
    private Dialog dialog;
    private TextView instructions;
    private boolean isBeingDestroyed = false;
    private boolean isRetry = false;
    private LoginClient.Request mRequest = null;
    private ProgressBar progressBar;
    private volatile ScheduledFuture scheduledPoll;

    private void completeLogin(String string, Utility.PermissionsPair permissionsPair, String string2) {
        this.deviceAuthMethodHandler.onSuccess(string2, FacebookSdk.getApplicationId(), string, permissionsPair.getGrantedPermissions(), permissionsPair.getDeclinedPermissions(), AccessTokenSource.DEVICE_AUTH, null, null);
        this.dialog.dismiss();
    }

    private GraphRequest getPollRequest() {
        Bundle bundle = new Bundle();
        bundle.putString("code", this.currentRequestState.getRequestCode());
        return new GraphRequest(null, DEVICE_LOGIN_STATUS_ENDPOINT, bundle, HttpMethod.POST, new GraphRequest.Callback(){

            @Override
            public void onCompleted(GraphResponse graphResponse) {
                if (DeviceAuthDialog.this.completed.get()) {
                    return;
                }
                FacebookRequestError facebookRequestError = graphResponse.getError();
                if (facebookRequestError != null) {
                    int n = facebookRequestError.getSubErrorCode();
                    if (n != 1349152) {
                        switch (n) {
                            default: {
                                DeviceAuthDialog.this.onError(graphResponse.getError().getException());
                                return;
                            }
                            case 1349172: 
                            case 1349174: {
                                DeviceAuthDialog.this.schedulePoll();
                                return;
                            }
                            case 1349173: 
                        }
                    }
                    DeviceAuthDialog.this.onCancel();
                    return;
                }
                try {
                    graphResponse = graphResponse.getJSONObject();
                    DeviceAuthDialog.this.onSuccess(graphResponse.getString("access_token"));
                    return;
                }
                catch (JSONException jSONException) {
                    DeviceAuthDialog.this.onError(new FacebookException((Throwable)jSONException));
                    return;
                }
            }
        });
    }

    private View initializeContentView(boolean bl) {
        LayoutInflater layoutInflater = this.getActivity().getLayoutInflater();
        layoutInflater = bl ? layoutInflater.inflate(R.layout.com_facebook_smart_device_dialog_fragment, null) : layoutInflater.inflate(R.layout.com_facebook_device_auth_dialog_fragment, null);
        this.progressBar = (ProgressBar)layoutInflater.findViewById(R.id.progress_bar);
        this.confirmationCode = (TextView)layoutInflater.findViewById(R.id.confirmation_code);
        ((Button)layoutInflater.findViewById(R.id.cancel_button)).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                DeviceAuthDialog.this.onCancel();
            }
        });
        this.instructions = (TextView)layoutInflater.findViewById(R.id.com_facebook_device_auth_instructions);
        this.instructions.setText((CharSequence)Html.fromHtml((String)this.getString(R.string.com_facebook_device_auth_instructions)));
        return layoutInflater;
    }

    private void onCancel() {
        if (!this.completed.compareAndSet(false, true)) {
            return;
        }
        if (this.currentRequestState != null) {
            DeviceRequestsHelper.cleanUpAdvertisementService(this.currentRequestState.getUserCode());
        }
        if (this.deviceAuthMethodHandler != null) {
            this.deviceAuthMethodHandler.onCancel();
        }
        this.dialog.dismiss();
    }

    private void onError(FacebookException facebookException) {
        if (!this.completed.compareAndSet(false, true)) {
            return;
        }
        if (this.currentRequestState != null) {
            DeviceRequestsHelper.cleanUpAdvertisementService(this.currentRequestState.getUserCode());
        }
        this.deviceAuthMethodHandler.onError(facebookException);
        this.dialog.dismiss();
    }

    private void onSuccess(final String string2) {
        Bundle bundle = new Bundle();
        bundle.putString("fields", "id,permissions,name");
        new GraphRequest(new AccessToken(string2, FacebookSdk.getApplicationId(), "0", null, null, null, null, null), "me", bundle, HttpMethod.GET, new GraphRequest.Callback(){

            @Override
            public void onCompleted(GraphResponse object) {
                Utility.PermissionsPair permissionsPair;
                Object object2;
                if (DeviceAuthDialog.this.completed.get()) {
                    return;
                }
                if (object.getError() != null) {
                    DeviceAuthDialog.this.onError(object.getError().getException());
                    return;
                }
                try {
                    object2 = object.getJSONObject();
                    object = object2.getString("id");
                    permissionsPair = Utility.handlePermissionResponse(object2);
                    object2 = object2.getString("name");
                }
                catch (JSONException jSONException) {
                    DeviceAuthDialog.this.onError(new FacebookException((Throwable)jSONException));
                    return;
                }
                DeviceRequestsHelper.cleanUpAdvertisementService(DeviceAuthDialog.this.currentRequestState.getUserCode());
                if (FetchedAppSettingsManager.getAppSettingsWithoutQuery(FacebookSdk.getApplicationId()).getSmartLoginOptions().contains((Object)SmartLoginOption.RequireConfirm) && !DeviceAuthDialog.this.isRetry) {
                    DeviceAuthDialog.this.isRetry = true;
                    DeviceAuthDialog.this.presentConfirmation((String)object, permissionsPair, string2, (String)object2);
                    return;
                }
                DeviceAuthDialog.this.completeLogin((String)object, permissionsPair, string2);
            }
        }).executeAsync();
    }

    private void poll() {
        this.currentRequestState.setLastPoll(new Date().getTime());
        this.currentGraphRequestPoll = this.getPollRequest().executeAsync();
    }

    private void presentConfirmation(final String string2, final Utility.PermissionsPair permissionsPair, final String string3, String string4) {
        String string5 = this.getResources().getString(R.string.com_facebook_smart_login_confirmation_title);
        String string6 = this.getResources().getString(R.string.com_facebook_smart_login_confirmation_continue_as);
        String string7 = this.getResources().getString(R.string.com_facebook_smart_login_confirmation_cancel);
        string4 = String.format(string6, string4);
        string6 = new AlertDialog.Builder(this.getContext());
        string6.setMessage((CharSequence)string5).setCancelable(true).setNegativeButton((CharSequence)string4, new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialogInterface, int n) {
                DeviceAuthDialog.this.completeLogin(string2, permissionsPair, string3);
            }
        }).setPositiveButton((CharSequence)string7, new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialogInterface, int n) {
                dialogInterface = DeviceAuthDialog.this.initializeContentView(false);
                DeviceAuthDialog.this.dialog.setContentView((View)dialogInterface);
                DeviceAuthDialog.this.startLogin(DeviceAuthDialog.this.mRequest);
            }
        });
        string6.create().show();
    }

    private void schedulePoll() {
        this.scheduledPoll = DeviceAuthMethodHandler.getBackgroundExecutor().schedule(new Runnable(){

            @Override
            public void run() {
                DeviceAuthDialog.this.poll();
            }
        }, this.currentRequestState.getInterval(), TimeUnit.SECONDS);
    }

    private void setCurrentRequestState(RequestState requestState) {
        this.currentRequestState = requestState;
        this.confirmationCode.setText((CharSequence)requestState.getUserCode());
        Bitmap bitmap = DeviceRequestsHelper.generateQRCode(requestState.getAuthorizationUri());
        bitmap = new BitmapDrawable(this.getResources(), bitmap);
        this.instructions.setCompoundDrawablesWithIntrinsicBounds(null, (Drawable)bitmap, null, null);
        this.confirmationCode.setVisibility(0);
        this.progressBar.setVisibility(8);
        if (!this.isRetry && DeviceRequestsHelper.startAdvertisementService(requestState.getUserCode())) {
            AppEventsLogger.newLogger(this.getContext()).logSdkEvent("fb_smart_login_service", null, null);
        }
        if (requestState.withinLastRefreshWindow()) {
            this.schedulePoll();
            return;
        }
        this.poll();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle bundle) {
        this.dialog = new Dialog((Context)this.getActivity(), R.style.com_facebook_auth_dialog);
        boolean bl = DeviceRequestsHelper.isAvailable() && !this.isRetry;
        bundle = this.initializeContentView(bl);
        this.dialog.setContentView((View)bundle);
        return this.dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup object, Bundle bundle) {
        layoutInflater = super.onCreateView(layoutInflater, (ViewGroup)object, bundle);
        this.deviceAuthMethodHandler = (DeviceAuthMethodHandler)((LoginFragment)((FacebookActivity)this.getActivity()).getCurrentFragment()).getLoginClient().getCurrentHandler();
        if (bundle != null && (object = (RequestState)bundle.getParcelable(REQUEST_STATE_KEY)) != null) {
            this.setCurrentRequestState((RequestState)object);
        }
        return layoutInflater;
    }

    @Override
    public void onDestroy() {
        this.isBeingDestroyed = true;
        this.completed.set(true);
        super.onDestroy();
        if (this.currentGraphRequestPoll != null) {
            this.currentGraphRequestPoll.cancel(true);
        }
        if (this.scheduledPoll != null) {
            this.scheduledPoll.cancel(true);
        }
    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {
        super.onDismiss(dialogInterface);
        if (!this.isBeingDestroyed) {
            this.onCancel();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (this.currentRequestState != null) {
            bundle.putParcelable(REQUEST_STATE_KEY, (Parcelable)this.currentRequestState);
        }
    }

    public void startLogin(LoginClient.Request object) {
        this.mRequest = object;
        Bundle bundle = new Bundle();
        bundle.putString("scope", TextUtils.join((CharSequence)",", object.getPermissions()));
        object = object.getDeviceRedirectUriString();
        if (object != null) {
            bundle.putString("redirect_uri", (String)object);
        }
        object = new StringBuilder();
        object.append(Validate.hasAppID());
        object.append("|");
        object.append(Validate.hasClientToken());
        bundle.putString("access_token", object.toString());
        bundle.putString("device_info", DeviceRequestsHelper.getDeviceInfo());
        new GraphRequest(null, DEVICE_LOGIN_ENDPOINT, bundle, HttpMethod.POST, new GraphRequest.Callback(){

            @Override
            public void onCompleted(GraphResponse graphResponse) {
                if (DeviceAuthDialog.this.isBeingDestroyed) {
                    return;
                }
                if (graphResponse.getError() != null) {
                    DeviceAuthDialog.this.onError(graphResponse.getError().getException());
                    return;
                }
                graphResponse = graphResponse.getJSONObject();
                RequestState requestState = new RequestState();
                try {
                    requestState.setUserCode(graphResponse.getString("user_code"));
                    requestState.setRequestCode(graphResponse.getString("code"));
                    requestState.setInterval(graphResponse.getLong("interval"));
                }
                catch (JSONException jSONException) {
                    DeviceAuthDialog.this.onError(new FacebookException((Throwable)jSONException));
                    return;
                }
                DeviceAuthDialog.this.setCurrentRequestState(requestState);
            }
        }).executeAsync();
    }

    private static class RequestState
    implements Parcelable {
        public static final Parcelable.Creator<RequestState> CREATOR = new Parcelable.Creator<RequestState>(){

            public RequestState createFromParcel(Parcel parcel) {
                return new RequestState(parcel);
            }

            public RequestState[] newArray(int n) {
                return new RequestState[n];
            }
        };
        private String authorizationUri;
        private long interval;
        private long lastPoll;
        private String requestCode;
        private String userCode;

        RequestState() {
        }

        protected RequestState(Parcel parcel) {
            this.userCode = parcel.readString();
            this.requestCode = parcel.readString();
            this.interval = parcel.readLong();
            this.lastPoll = parcel.readLong();
        }

        public int describeContents() {
            return 0;
        }

        public String getAuthorizationUri() {
            return this.authorizationUri;
        }

        public long getInterval() {
            return this.interval;
        }

        public String getRequestCode() {
            return this.requestCode;
        }

        public String getUserCode() {
            return this.userCode;
        }

        public void setInterval(long l) {
            this.interval = l;
        }

        public void setLastPoll(long l) {
            this.lastPoll = l;
        }

        public void setRequestCode(String string) {
            this.requestCode = string;
        }

        public void setUserCode(String string) {
            this.userCode = string;
            this.authorizationUri = String.format(Locale.ENGLISH, "https://facebook.com/device?user_code=%1$s&qr=1", string);
        }

        public boolean withinLastRefreshWindow() {
            long l = this.lastPoll;
            boolean bl = false;
            if (l == 0L) {
                return false;
            }
            if (new Date().getTime() - this.lastPoll - this.interval * 1000L < 0L) {
                bl = true;
            }
            return bl;
        }

        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeString(this.userCode);
            parcel.writeString(this.requestCode);
            parcel.writeLong(this.interval);
            parcel.writeLong(this.lastPoll);
        }

    }

}
