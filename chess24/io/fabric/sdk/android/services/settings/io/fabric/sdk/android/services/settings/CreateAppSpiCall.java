/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android.services.settings;

import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.services.network.HttpMethod;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import io.fabric.sdk.android.services.settings.AbstractAppSpiCall;
import io.fabric.sdk.android.services.settings.AppRequestData;

public class CreateAppSpiCall
extends AbstractAppSpiCall {
    public CreateAppSpiCall(Kit kit, String string, String string2, HttpRequestFactory httpRequestFactory) {
        super(kit, string, string2, httpRequestFactory, HttpMethod.POST);
    }
}
