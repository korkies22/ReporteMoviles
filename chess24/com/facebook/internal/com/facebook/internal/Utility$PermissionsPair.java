/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.internal;

import com.facebook.internal.Utility;
import java.util.List;

public static class Utility.PermissionsPair {
    List<String> declinedPermissions;
    List<String> grantedPermissions;

    public Utility.PermissionsPair(List<String> list, List<String> list2) {
        this.grantedPermissions = list;
        this.declinedPermissions = list2;
    }

    public List<String> getDeclinedPermissions() {
        return this.declinedPermissions;
    }

    public List<String> getGrantedPermissions() {
        return this.grantedPermissions;
    }
}
