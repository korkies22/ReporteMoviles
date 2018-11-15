/*
 * Decompiled with CFR 0_134.
 */
package bolts;

import bolts.AppLinkNavigation;

public static enum AppLinkNavigation.NavigationResult {
    FAILED("failed", false),
    WEB("web", true),
    APP("app", true);
    
    private String code;
    private boolean succeeded;

    private AppLinkNavigation.NavigationResult(String string2, boolean bl) {
        this.code = string2;
        this.succeeded = bl;
    }

    public String getCode() {
        return this.code;
    }

    public boolean isSucceeded() {
        return this.succeeded;
    }
}
