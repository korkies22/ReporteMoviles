/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.facebook.share.widget;

import android.os.Bundle;
import com.facebook.share.widget.GameRequestDialog;
import java.util.ArrayList;
import java.util.List;

public static final class GameRequestDialog.Result {
    String requestId;
    List<String> to;

    private GameRequestDialog.Result(Bundle bundle) {
        this.requestId = bundle.getString("request");
        this.to = new ArrayList<String>();
        while (bundle.containsKey(String.format("to[%d]", this.to.size()))) {
            this.to.add(bundle.getString(String.format("to[%d]", this.to.size())));
        }
    }

    public String getRequestId() {
        return this.requestId;
    }

    public List<String> getRequestRecipients() {
        return this.to;
    }
}
