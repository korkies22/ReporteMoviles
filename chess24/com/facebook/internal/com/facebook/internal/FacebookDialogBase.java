/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Fragment
 *  android.content.Intent
 *  android.util.Log
 */
package com.facebook.internal;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.util.Log;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookDialog;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;
import com.facebook.internal.AppCall;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.internal.DialogPresenter;
import com.facebook.internal.FragmentWrapper;
import com.facebook.internal.Logger;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import java.util.Iterator;
import java.util.List;

public abstract class FacebookDialogBase<CONTENT, RESULT>
implements FacebookDialog<CONTENT, RESULT> {
    protected static final Object BASE_AUTOMATIC_MODE = new Object();
    private static final String TAG = "FacebookDialog";
    private final Activity activity;
    private final FragmentWrapper fragmentWrapper;
    private List<FacebookDialogBase<CONTENT, RESULT>> modeHandlers;
    private int requestCode;

    protected FacebookDialogBase(Activity activity, int n) {
        Validate.notNull((Object)activity, "activity");
        this.activity = activity;
        this.fragmentWrapper = null;
        this.requestCode = n;
    }

    protected FacebookDialogBase(FragmentWrapper fragmentWrapper, int n) {
        Validate.notNull(fragmentWrapper, "fragmentWrapper");
        this.fragmentWrapper = fragmentWrapper;
        this.activity = null;
        this.requestCode = n;
        if (fragmentWrapper.getActivity() == null) {
            throw new IllegalArgumentException("Cannot use a fragment that is not attached to an activity");
        }
    }

    private List<FacebookDialogBase<CONTENT, RESULT>> cachedModeHandlers() {
        if (this.modeHandlers == null) {
            this.modeHandlers = this.getOrderedModeHandlers();
        }
        return this.modeHandlers;
    }

    private AppCall createAppCallForMode(CONTENT object, Object object2) {
        Object object3;
        block4 : {
            boolean bl = object2 == BASE_AUTOMATIC_MODE;
            ModeHandler modeHandler = null;
            Iterator<FacebookDialogBase<CONTENT, RESULT>> iterator = this.cachedModeHandlers().iterator();
            do {
                object3 = modeHandler;
                if (!iterator.hasNext()) break block4;
                object3 = (ModeHandler)((Object)iterator.next());
            } while (!bl && !Utility.areObjectsEqual(object3.getMode(), object2) || !object3.canShow(object, true));
            try {
                object3 = object3.createAppCall(object);
            }
            catch (FacebookException facebookException) {
                object3 = this.createBaseAppCall();
                DialogPresenter.setupAppCallForValidationError((AppCall)object3, facebookException);
            }
        }
        object = object3;
        if (object3 == null) {
            object = this.createBaseAppCall();
            DialogPresenter.setupAppCallForCannotShowError(object);
        }
        return object;
    }

    @Override
    public boolean canShow(CONTENT CONTENT) {
        return this.canShowImpl(CONTENT, BASE_AUTOMATIC_MODE);
    }

    protected boolean canShowImpl(CONTENT CONTENT, Object object) {
        boolean bl = object == BASE_AUTOMATIC_MODE;
        for (ModeHandler modeHandler : this.cachedModeHandlers()) {
            if (!bl && !Utility.areObjectsEqual(modeHandler.getMode(), object) || !modeHandler.canShow(CONTENT, false)) continue;
            return true;
        }
        return false;
    }

    protected abstract AppCall createBaseAppCall();

    protected Activity getActivityContext() {
        if (this.activity != null) {
            return this.activity;
        }
        if (this.fragmentWrapper != null) {
            return this.fragmentWrapper.getActivity();
        }
        return null;
    }

    protected abstract List<FacebookDialogBase<CONTENT, RESULT>> getOrderedModeHandlers();

    public int getRequestCode() {
        return this.requestCode;
    }

    @Override
    public final void registerCallback(CallbackManager callbackManager, FacebookCallback<RESULT> facebookCallback) {
        if (!(callbackManager instanceof CallbackManagerImpl)) {
            throw new FacebookException("Unexpected CallbackManager, please use the provided Factory.");
        }
        this.registerCallbackImpl((CallbackManagerImpl)callbackManager, facebookCallback);
    }

    @Override
    public final void registerCallback(CallbackManager callbackManager, FacebookCallback<RESULT> facebookCallback, int n) {
        this.setRequestCode(n);
        this.registerCallback(callbackManager, facebookCallback);
    }

    protected abstract void registerCallbackImpl(CallbackManagerImpl var1, FacebookCallback<RESULT> var2);

    protected void setRequestCode(int n) {
        if (FacebookSdk.isFacebookRequestCode(n)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Request code ");
            stringBuilder.append(n);
            stringBuilder.append(" cannot be within the range reserved by the Facebook SDK.");
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        this.requestCode = n;
    }

    @Override
    public void show(CONTENT CONTENT) {
        this.showImpl(CONTENT, BASE_AUTOMATIC_MODE);
    }

    protected void showImpl(CONTENT object, Object object2) {
        if ((object = this.createAppCallForMode(object, object2)) != null) {
            if (this.fragmentWrapper != null) {
                DialogPresenter.present(object, this.fragmentWrapper);
                return;
            }
            DialogPresenter.present(object, this.activity);
            return;
        }
        Log.e((String)"FacebookDialog", (String)"No code path should ever result in a null appCall");
        if (FacebookSdk.isDebugEnabled()) {
            throw new IllegalStateException("No code path should ever result in a null appCall");
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    protected void startActivityForResult(Intent var1_1, int var2_2) {
        block5 : {
            block3 : {
                block4 : {
                    block2 : {
                        if (this.activity == null) break block2;
                        this.activity.startActivityForResult(var1_1 /* !! */ , (int)var2_6);
                        ** GOTO lbl12
                    }
                    if (this.fragmentWrapper == null) break block3;
                    if (this.fragmentWrapper.getNativeFragment() == null) break block4;
                    this.fragmentWrapper.getNativeFragment().startActivityForResult(var1_1 /* !! */ , (int)var2_6);
                    ** GOTO lbl12
                }
                if (this.fragmentWrapper.getSupportFragment() != null) {
                    this.fragmentWrapper.getSupportFragment().startActivityForResult(var1_1 /* !! */ , (int)var2_6);
lbl12: // 3 sources:
                    var1_2 = null;
                } else {
                    var1_3 = "Failed to find Activity or Fragment to startActivityForResult ";
                }
                break block5;
            }
            var1_4 = "Failed to find Activity or Fragment to startActivityForResult ";
        }
        if (var1_5 == null) return;
        Logger.log(LoggingBehavior.DEVELOPER_ERRORS, 6, this.getClass().getName(), (String)var1_5);
    }

    protected abstract class ModeHandler {
        protected ModeHandler() {
        }

        public abstract boolean canShow(CONTENT var1, boolean var2);

        public abstract AppCall createAppCall(CONTENT var1);

        public Object getMode() {
            return FacebookDialogBase.BASE_AUTOMATIC_MODE;
        }
    }

}
