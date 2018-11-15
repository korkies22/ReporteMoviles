/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.common.api;

import com.google.android.gms.common.api.Api;

public static interface Api.ApiOptions {

    public static interface HasOptions
    extends Api.ApiOptions {
    }

    public static final class NoOptions
    implements NotRequiredOptions {
        private NoOptions() {
        }
    }

    public static interface NotRequiredOptions
    extends Api.ApiOptions {
    }

    public static interface Optional
    extends HasOptions,
    NotRequiredOptions {
    }

}
