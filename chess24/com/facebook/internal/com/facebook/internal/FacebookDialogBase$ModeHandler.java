/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.internal;

import com.facebook.internal.AppCall;
import com.facebook.internal.FacebookDialogBase;

protected abstract class FacebookDialogBase.ModeHandler {
    protected FacebookDialogBase.ModeHandler() {
    }

    public abstract boolean canShow(CONTENT var1, boolean var2);

    public abstract AppCall createAppCall(CONTENT var1);

    public Object getMode() {
        return FacebookDialogBase.BASE_AUTOMATIC_MODE;
    }
}
