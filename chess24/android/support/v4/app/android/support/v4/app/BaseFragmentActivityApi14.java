/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentSender
 *  android.content.IntentSender$SendIntentException
 *  android.util.AttributeSet
 *  android.view.View
 */
package android.support.v4.app;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.SupportActivity;
import android.util.AttributeSet;
import android.view.View;

@RequiresApi(value=14)
abstract class BaseFragmentActivityApi14
extends SupportActivity {
    boolean mStartedIntentSenderFromFragment;

    BaseFragmentActivityApi14() {
    }

    static void checkForValidRequestCode(int n) {
        if ((n & -65536) != 0) {
            throw new IllegalArgumentException("Can only use lower 16 bits for requestCode");
        }
    }

    abstract View dispatchFragmentsOnCreateView(View var1, String var2, Context var3, AttributeSet var4);

    public View onCreateView(View view, String string, Context context, AttributeSet attributeSet) {
        View view2 = this.dispatchFragmentsOnCreateView(view, string, context, attributeSet);
        if (view2 == null) {
            return super.onCreateView(view, string, context, attributeSet);
        }
        return view2;
    }

    public View onCreateView(String string, Context context, AttributeSet attributeSet) {
        View view = this.dispatchFragmentsOnCreateView(null, string, context, attributeSet);
        if (view == null) {
            return super.onCreateView(string, context, attributeSet);
        }
        return view;
    }

    public void startIntentSenderForResult(IntentSender intentSender, int n, @Nullable Intent intent, int n2, int n3, int n4) throws IntentSender.SendIntentException {
        if (!this.mStartedIntentSenderFromFragment && n != -1) {
            BaseFragmentActivityApi14.checkForValidRequestCode(n);
        }
        super.startIntentSenderForResult(intentSender, n, intent, n2, n3, n4);
    }
}
