/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Dialog
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.Intent
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.text.Html
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.Button
 *  android.widget.ProgressBar
 *  android.widget.TextView
 *  android.widget.Toast
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.facebook.share.internal;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.AccessToken;
import com.facebook.FacebookRequestError;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.common.R;
import com.facebook.devicerequests.internal.DeviceRequestsHelper;
import com.facebook.internal.Validate;
import com.facebook.share.internal.WebDialogParameters;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareOpenGraphContent;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.json.JSONException;
import org.json.JSONObject;

public class DeviceShareDialogFragment
extends DialogFragment {
    private static final String DEVICE_SHARE_ENDPOINT = "device/share";
    private static final String EXTRA_ERROR = "error";
    private static final String REQUEST_STATE_KEY = "request_state";
    public static final String TAG = "DeviceShareDialogFragment";
    private static ScheduledThreadPoolExecutor backgroundExecutor;
    private volatile ScheduledFuture codeExpiredFuture;
    private TextView confirmationCode;
    private volatile RequestState currentRequestState;
    private Dialog dialog;
    private ProgressBar progressBar;
    private ShareContent shareContent;

    private void detach() {
        if (this.isAdded()) {
            this.getFragmentManager().beginTransaction().remove(this).commit();
        }
    }

    private void finishActivity(int n, Intent intent) {
        Object object;
        if (this.currentRequestState != null) {
            DeviceRequestsHelper.cleanUpAdvertisementService(this.currentRequestState.getUserCode());
        }
        if ((object = (FacebookRequestError)intent.getParcelableExtra(EXTRA_ERROR)) != null) {
            Toast.makeText((Context)this.getContext(), (CharSequence)object.getErrorMessage(), (int)0).show();
        }
        if (this.isAdded()) {
            object = this.getActivity();
            object.setResult(n, intent);
            object.finish();
        }
    }

    private void finishActivityWithError(FacebookRequestError facebookRequestError) {
        this.detach();
        Intent intent = new Intent();
        intent.putExtra(EXTRA_ERROR, (Parcelable)facebookRequestError);
        this.finishActivity(-1, intent);
    }

    private static ScheduledThreadPoolExecutor getBackgroundExecutor() {
        synchronized (DeviceShareDialogFragment.class) {
            if (backgroundExecutor == null) {
                backgroundExecutor = new ScheduledThreadPoolExecutor(1);
            }
            ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = backgroundExecutor;
            return scheduledThreadPoolExecutor;
        }
    }

    private Bundle getGraphParametersForShareContent() {
        ShareContent shareContent = this.shareContent;
        if (shareContent == null) {
            return null;
        }
        if (shareContent instanceof ShareLinkContent) {
            return WebDialogParameters.create((ShareLinkContent)shareContent);
        }
        if (shareContent instanceof ShareOpenGraphContent) {
            return WebDialogParameters.create((ShareOpenGraphContent)shareContent);
        }
        return null;
    }

    private void setCurrentRequestState(RequestState requestState) {
        this.currentRequestState = requestState;
        this.confirmationCode.setText((CharSequence)requestState.getUserCode());
        this.confirmationCode.setVisibility(0);
        this.progressBar.setVisibility(8);
        this.codeExpiredFuture = DeviceShareDialogFragment.getBackgroundExecutor().schedule(new Runnable(){

            @Override
            public void run() {
                DeviceShareDialogFragment.this.dialog.dismiss();
            }
        }, requestState.getExpiresIn(), TimeUnit.SECONDS);
    }

    private void startShare() {
        Bundle bundle = this.getGraphParametersForShareContent();
        if (bundle == null || bundle.size() == 0) {
            this.finishActivityWithError(new FacebookRequestError(0, "", "Failed to get share content"));
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Validate.hasAppID());
        stringBuilder.append("|");
        stringBuilder.append(Validate.hasClientToken());
        bundle.putString("access_token", stringBuilder.toString());
        bundle.putString("device_info", DeviceRequestsHelper.getDeviceInfo());
        new GraphRequest(null, DEVICE_SHARE_ENDPOINT, bundle, HttpMethod.POST, new GraphRequest.Callback(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onCompleted(GraphResponse graphResponse) {
                Object object = graphResponse.getError();
                if (object != null) {
                    DeviceShareDialogFragment.this.finishActivityWithError((FacebookRequestError)object);
                    return;
                }
                graphResponse = graphResponse.getJSONObject();
                object = new RequestState();
                try {
                    object.setUserCode(graphResponse.getString("user_code"));
                    object.setExpiresIn(graphResponse.getLong("expires_in"));
                }
                catch (JSONException jSONException) {}
                DeviceShareDialogFragment.this.setCurrentRequestState((RequestState)object);
                return;
                DeviceShareDialogFragment.this.finishActivityWithError(new FacebookRequestError(0, "", "Malformed server response"));
            }
        }).executeAsync();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle bundle) {
        this.dialog = new Dialog((Context)this.getActivity(), R.style.com_facebook_auth_dialog);
        bundle = this.getActivity().getLayoutInflater().inflate(R.layout.com_facebook_device_auth_dialog_fragment, null);
        this.progressBar = (ProgressBar)bundle.findViewById(R.id.progress_bar);
        this.confirmationCode = (TextView)bundle.findViewById(R.id.confirmation_code);
        ((Button)bundle.findViewById(R.id.cancel_button)).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                DeviceShareDialogFragment.this.dialog.dismiss();
            }
        });
        ((TextView)bundle.findViewById(R.id.com_facebook_device_auth_instructions)).setText((CharSequence)Html.fromHtml((String)this.getString(R.string.com_facebook_device_auth_instructions)));
        this.dialog.setContentView((View)bundle);
        this.startShare();
        return this.dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup object, Bundle bundle) {
        layoutInflater = super.onCreateView(layoutInflater, (ViewGroup)object, bundle);
        if (bundle != null && (object = (RequestState)bundle.getParcelable(REQUEST_STATE_KEY)) != null) {
            this.setCurrentRequestState((RequestState)object);
        }
        return layoutInflater;
    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {
        super.onDismiss(dialogInterface);
        if (this.codeExpiredFuture != null) {
            this.codeExpiredFuture.cancel(true);
        }
        this.finishActivity(-1, new Intent());
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (this.currentRequestState != null) {
            bundle.putParcelable(REQUEST_STATE_KEY, (Parcelable)this.currentRequestState);
        }
    }

    public void setShareContent(ShareContent shareContent) {
        this.shareContent = shareContent;
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
        private long expiresIn;
        private String userCode;

        RequestState() {
        }

        protected RequestState(Parcel parcel) {
            this.userCode = parcel.readString();
            this.expiresIn = parcel.readLong();
        }

        public int describeContents() {
            return 0;
        }

        public long getExpiresIn() {
            return this.expiresIn;
        }

        public String getUserCode() {
            return this.userCode;
        }

        public void setExpiresIn(long l) {
            this.expiresIn = l;
        }

        public void setUserCode(String string) {
            this.userCode = string;
        }

        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeString(this.userCode);
            parcel.writeLong(this.expiresIn);
        }

    }

}
