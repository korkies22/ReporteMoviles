/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.AlertDialog
 *  android.app.AlertDialog$Builder
 *  android.app.Fragment
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.content.res.TypedArray
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Paint$FontMetrics
 *  android.graphics.drawable.Drawable
 *  android.os.Bundle
 *  android.text.TextPaint
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package com.facebook.login.widget;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.content.res.AppCompatResources;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookButtonBase;
import com.facebook.FacebookCallback;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.common.R;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.internal.FetchedAppSettings;
import com.facebook.internal.FetchedAppSettingsManager;
import com.facebook.internal.LoginAuthorizationType;
import com.facebook.internal.Utility;
import com.facebook.login.DefaultAudience;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.R;
import com.facebook.login.widget.ToolTipPopup;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class LoginButton
extends FacebookButtonBase {
    private static final String TAG = "com.facebook.login.widget.LoginButton";
    private AccessTokenTracker accessTokenTracker;
    private boolean confirmLogout;
    private String loginLogoutEventName = "fb_login_view_usage";
    private LoginManager loginManager;
    private String loginText;
    private String logoutText;
    private LoginButtonProperties properties = new LoginButtonProperties();
    private boolean toolTipChecked;
    private long toolTipDisplayTime = 6000L;
    private ToolTipMode toolTipMode;
    private ToolTipPopup toolTipPopup;
    private ToolTipPopup.Style toolTipStyle = ToolTipPopup.Style.BLUE;

    public LoginButton(Context context) {
        super(context, null, 0, 0, "fb_login_button_create", "fb_login_button_did_tap");
    }

    public LoginButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0, 0, "fb_login_button_create", "fb_login_button_did_tap");
    }

    public LoginButton(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n, 0, "fb_login_button_create", "fb_login_button_did_tap");
    }

    private void checkToolTipSettings() {
        switch (.$SwitchMap$com$facebook$login$widget$LoginButton$ToolTipMode[this.toolTipMode.ordinal()]) {
            default: {
                return;
            }
            case 2: {
                this.displayToolTip(this.getResources().getString(R.string.com_facebook_tooltip_default));
                return;
            }
            case 1: 
        }
        final String string2 = Utility.getMetadataApplicationId(this.getContext());
        FacebookSdk.getExecutor().execute(new Runnable(){

            @Override
            public void run() {
                final FetchedAppSettings fetchedAppSettings = FetchedAppSettingsManager.queryAppSettings(string2, false);
                LoginButton.this.getActivity().runOnUiThread(new Runnable(){

                    @Override
                    public void run() {
                        LoginButton.this.showToolTipPerSettings(fetchedAppSettings);
                    }
                });
            }

        });
    }

    private void displayToolTip(String string2) {
        this.toolTipPopup = new ToolTipPopup(string2, (View)this);
        this.toolTipPopup.setStyle(this.toolTipStyle);
        this.toolTipPopup.setNuxDisplayTime(this.toolTipDisplayTime);
        this.toolTipPopup.show();
    }

    private int measureButtonWidth(String string2) {
        int n = this.measureTextWidth(string2);
        return this.getCompoundPaddingLeft() + this.getCompoundDrawablePadding() + n + this.getCompoundPaddingRight();
    }

    private void parseLoginButtonAttributes(Context context, AttributeSet attributeSet, int n, int n2) {
        this.toolTipMode = ToolTipMode.DEFAULT;
        context = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.com_facebook_login_view, n, n2);
        try {
            this.confirmLogout = context.getBoolean(R.styleable.com_facebook_login_view_com_facebook_confirm_logout, true);
            this.loginText = context.getString(R.styleable.com_facebook_login_view_com_facebook_login_text);
            this.logoutText = context.getString(R.styleable.com_facebook_login_view_com_facebook_logout_text);
            this.toolTipMode = ToolTipMode.fromInt(context.getInt(R.styleable.com_facebook_login_view_com_facebook_tooltip_mode, ToolTipMode.DEFAULT.getValue()));
            return;
        }
        finally {
            context.recycle();
        }
    }

    private void setButtonText() {
        Resources resources = this.getResources();
        if (!this.isInEditMode() && AccessToken.getCurrentAccessToken() != null) {
            String string2 = this.logoutText != null ? this.logoutText : resources.getString(R.string.com_facebook_loginview_log_out_button);
            this.setText((CharSequence)string2);
            return;
        }
        if (this.loginText != null) {
            this.setText((CharSequence)this.loginText);
            return;
        }
        String string3 = resources.getString(R.string.com_facebook_loginview_log_in_button_continue);
        int n = this.getWidth();
        String string4 = string3;
        if (n != 0) {
            string4 = string3;
            if (this.measureButtonWidth(string3) > n) {
                string4 = resources.getString(R.string.com_facebook_loginview_log_in_button);
            }
        }
        this.setText((CharSequence)string4);
    }

    private void showToolTipPerSettings(FetchedAppSettings fetchedAppSettings) {
        if (fetchedAppSettings != null && fetchedAppSettings.getNuxEnabled() && this.getVisibility() == 0) {
            this.displayToolTip(fetchedAppSettings.getNuxContent());
        }
    }

    public void clearPermissions() {
        this.properties.clearPermissions();
    }

    @Override
    protected void configureButton(Context context, AttributeSet attributeSet, int n, int n2) {
        super.configureButton(context, attributeSet, n, n2);
        this.setInternalOnClickListener(this.getNewLoginClickListener());
        this.parseLoginButtonAttributes(context, attributeSet, n, n2);
        if (this.isInEditMode()) {
            this.setBackgroundColor(this.getResources().getColor(R.color.com_facebook_blue));
            this.loginText = "Continue with Facebook";
        } else {
            this.accessTokenTracker = new AccessTokenTracker(){

                @Override
                protected void onCurrentAccessTokenChanged(AccessToken accessToken, AccessToken accessToken2) {
                    LoginButton.this.setButtonText();
                }
            };
        }
        this.setButtonText();
        this.setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(this.getContext(), R.drawable.com_facebook_button_login_logo), null, null, null);
    }

    public void dismissToolTip() {
        if (this.toolTipPopup != null) {
            this.toolTipPopup.dismiss();
            this.toolTipPopup = null;
        }
    }

    public DefaultAudience getDefaultAudience() {
        return this.properties.getDefaultAudience();
    }

    @Override
    protected int getDefaultRequestCode() {
        return CallbackManagerImpl.RequestCodeOffset.Login.toRequestCode();
    }

    @Override
    protected int getDefaultStyleResource() {
        return R.style.com_facebook_loginview_default_style;
    }

    public LoginBehavior getLoginBehavior() {
        return this.properties.getLoginBehavior();
    }

    LoginManager getLoginManager() {
        if (this.loginManager == null) {
            this.loginManager = LoginManager.getInstance();
        }
        return this.loginManager;
    }

    protected LoginClickListener getNewLoginClickListener() {
        return new LoginClickListener();
    }

    List<String> getPermissions() {
        return this.properties.getPermissions();
    }

    public long getToolTipDisplayTime() {
        return this.toolTipDisplayTime;
    }

    public ToolTipMode getToolTipMode() {
        return this.toolTipMode;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.accessTokenTracker != null && !this.accessTokenTracker.isTracking()) {
            this.accessTokenTracker.startTracking();
            this.setButtonText();
        }
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.accessTokenTracker != null) {
            this.accessTokenTracker.stopTracking();
        }
        this.dismissToolTip();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!this.toolTipChecked && !this.isInEditMode()) {
            this.toolTipChecked = true;
            this.checkToolTipSettings();
        }
    }

    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        super.onLayout(bl, n, n2, n3, n4);
        this.setButtonText();
    }

    protected void onMeasure(int n, int n2) {
        int n3;
        Object object = this.getPaint().getFontMetrics();
        n2 = this.getCompoundPaddingTop();
        int n4 = (int)Math.ceil(Math.abs(object.top) + Math.abs(object.bottom));
        int n5 = this.getCompoundPaddingBottom();
        Resources resources = this.getResources();
        String string2 = this.loginText;
        object = string2;
        if (string2 == null && LoginButton.resolveSize((int)(n3 = this.measureButtonWidth((String)(object = resources.getString(R.string.com_facebook_loginview_log_in_button_continue)))), (int)n) < n3) {
            object = resources.getString(R.string.com_facebook_loginview_log_in_button);
        }
        n3 = this.measureButtonWidth((String)object);
        string2 = this.logoutText;
        object = string2;
        if (string2 == null) {
            object = resources.getString(R.string.com_facebook_loginview_log_out_button);
        }
        this.setMeasuredDimension(LoginButton.resolveSize((int)Math.max(n3, this.measureButtonWidth((String)object)), (int)n), n2 + n4 + n5);
    }

    protected void onVisibilityChanged(View view, int n) {
        super.onVisibilityChanged(view, n);
        if (n != 0) {
            this.dismissToolTip();
        }
    }

    public void registerCallback(CallbackManager callbackManager, FacebookCallback<LoginResult> facebookCallback) {
        this.getLoginManager().registerCallback(callbackManager, facebookCallback);
    }

    public void setDefaultAudience(DefaultAudience defaultAudience) {
        this.properties.setDefaultAudience(defaultAudience);
    }

    public void setLoginBehavior(LoginBehavior loginBehavior) {
        this.properties.setLoginBehavior(loginBehavior);
    }

    void setLoginManager(LoginManager loginManager) {
        this.loginManager = loginManager;
    }

    void setProperties(LoginButtonProperties loginButtonProperties) {
        this.properties = loginButtonProperties;
    }

    public void setPublishPermissions(List<String> list) {
        this.properties.setPublishPermissions(list);
    }

    public /* varargs */ void setPublishPermissions(String ... arrstring) {
        this.properties.setPublishPermissions(Arrays.asList(arrstring));
    }

    public void setReadPermissions(List<String> list) {
        this.properties.setReadPermissions(list);
    }

    public /* varargs */ void setReadPermissions(String ... arrstring) {
        this.properties.setReadPermissions(Arrays.asList(arrstring));
    }

    public void setToolTipDisplayTime(long l) {
        this.toolTipDisplayTime = l;
    }

    public void setToolTipMode(ToolTipMode toolTipMode) {
        this.toolTipMode = toolTipMode;
    }

    public void setToolTipStyle(ToolTipPopup.Style style2) {
        this.toolTipStyle = style2;
    }

    public void unregisterCallback(CallbackManager callbackManager) {
        this.getLoginManager().unregisterCallback(callbackManager);
    }

    static class LoginButtonProperties {
        private LoginAuthorizationType authorizationType = null;
        private DefaultAudience defaultAudience = DefaultAudience.FRIENDS;
        private LoginBehavior loginBehavior = LoginBehavior.NATIVE_WITH_FALLBACK;
        private List<String> permissions = Collections.emptyList();

        LoginButtonProperties() {
        }

        public void clearPermissions() {
            this.permissions = null;
            this.authorizationType = null;
        }

        public DefaultAudience getDefaultAudience() {
            return this.defaultAudience;
        }

        public LoginBehavior getLoginBehavior() {
            return this.loginBehavior;
        }

        List<String> getPermissions() {
            return this.permissions;
        }

        public void setDefaultAudience(DefaultAudience defaultAudience) {
            this.defaultAudience = defaultAudience;
        }

        public void setLoginBehavior(LoginBehavior loginBehavior) {
            this.loginBehavior = loginBehavior;
        }

        public void setPublishPermissions(List<String> list) {
            if (LoginAuthorizationType.READ.equals((Object)this.authorizationType)) {
                throw new UnsupportedOperationException("Cannot call setPublishPermissions after setReadPermissions has been called.");
            }
            if (Utility.isNullOrEmpty(list)) {
                throw new IllegalArgumentException("Permissions for publish actions cannot be null or empty.");
            }
            this.permissions = list;
            this.authorizationType = LoginAuthorizationType.PUBLISH;
        }

        public void setReadPermissions(List<String> list) {
            if (LoginAuthorizationType.PUBLISH.equals((Object)this.authorizationType)) {
                throw new UnsupportedOperationException("Cannot call setReadPermissions after setPublishPermissions has been called.");
            }
            this.permissions = list;
            this.authorizationType = LoginAuthorizationType.READ;
        }
    }

    protected class LoginClickListener
    implements View.OnClickListener {
        protected LoginClickListener() {
        }

        protected LoginManager getLoginManager() {
            LoginManager loginManager = LoginManager.getInstance();
            loginManager.setDefaultAudience(LoginButton.this.getDefaultAudience());
            loginManager.setLoginBehavior(LoginButton.this.getLoginBehavior());
            return loginManager;
        }

        public void onClick(View object) {
            LoginButton.this.callExternalOnClickListener(object);
            object = AccessToken.getCurrentAccessToken();
            if (object != null) {
                this.performLogout(LoginButton.this.getContext());
            } else {
                this.performLogin();
            }
            AppEventsLogger appEventsLogger = AppEventsLogger.newLogger(LoginButton.this.getContext());
            Bundle bundle = new Bundle();
            int n = object != null ? 0 : 1;
            bundle.putInt("logging_in", n);
            appEventsLogger.logSdkEvent(LoginButton.this.loginLogoutEventName, null, bundle);
        }

        protected void performLogin() {
            LoginManager loginManager = this.getLoginManager();
            if (LoginAuthorizationType.PUBLISH.equals((Object)LoginButton.this.properties.authorizationType)) {
                if (LoginButton.this.getFragment() != null) {
                    loginManager.logInWithPublishPermissions(LoginButton.this.getFragment(), (Collection<String>)LoginButton.this.properties.permissions);
                    return;
                }
                if (LoginButton.this.getNativeFragment() != null) {
                    loginManager.logInWithPublishPermissions(LoginButton.this.getNativeFragment(), (Collection<String>)LoginButton.this.properties.permissions);
                    return;
                }
                loginManager.logInWithPublishPermissions(LoginButton.this.getActivity(), (Collection<String>)LoginButton.this.properties.permissions);
                return;
            }
            if (LoginButton.this.getFragment() != null) {
                loginManager.logInWithReadPermissions(LoginButton.this.getFragment(), (Collection<String>)LoginButton.this.properties.permissions);
                return;
            }
            if (LoginButton.this.getNativeFragment() != null) {
                loginManager.logInWithReadPermissions(LoginButton.this.getNativeFragment(), (Collection<String>)LoginButton.this.properties.permissions);
                return;
            }
            loginManager.logInWithReadPermissions(LoginButton.this.getActivity(), (Collection<String>)LoginButton.this.properties.permissions);
        }

        protected void performLogout(Context context) {
            final LoginManager loginManager = this.getLoginManager();
            if (LoginButton.this.confirmLogout) {
                String string2 = LoginButton.this.getResources().getString(R.string.com_facebook_loginview_log_out_action);
                String string3 = LoginButton.this.getResources().getString(R.string.com_facebook_loginview_cancel_action);
                Object object = Profile.getCurrentProfile();
                object = object != null && object.getName() != null ? String.format(LoginButton.this.getResources().getString(R.string.com_facebook_loginview_logged_in_as), object.getName()) : LoginButton.this.getResources().getString(R.string.com_facebook_loginview_logged_in_using_facebook);
                context = new AlertDialog.Builder(context);
                context.setMessage((CharSequence)object).setCancelable(true).setPositiveButton((CharSequence)string2, new DialogInterface.OnClickListener(){

                    public void onClick(DialogInterface dialogInterface, int n) {
                        loginManager.logOut();
                    }
                }).setNegativeButton((CharSequence)string3, null);
                context.create().show();
                return;
            }
            loginManager.logOut();
        }

    }

    public static enum ToolTipMode {
        AUTOMATIC("automatic", 0),
        DISPLAY_ALWAYS("display_always", 1),
        NEVER_DISPLAY("never_display", 2);
        
        public static ToolTipMode DEFAULT;
        private int intValue;
        private String stringValue;

        static {
            DEFAULT = AUTOMATIC;
        }

        private ToolTipMode(String string3, int n2) {
            this.stringValue = string3;
            this.intValue = n2;
        }

        public static ToolTipMode fromInt(int n) {
            for (ToolTipMode toolTipMode : ToolTipMode.values()) {
                if (toolTipMode.getValue() != n) continue;
                return toolTipMode;
            }
            return null;
        }

        public int getValue() {
            return this.intValue;
        }

        public String toString() {
            return this.stringValue;
        }
    }

}
