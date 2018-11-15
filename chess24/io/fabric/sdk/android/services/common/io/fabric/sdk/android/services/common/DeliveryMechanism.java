/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android.services.common;

public enum DeliveryMechanism {
    DEVELOPER(1),
    USER_SIDELOAD(2),
    TEST_DISTRIBUTION(3),
    APP_STORE(4);
    
    public static final String BETA_APP_PACKAGE_NAME = "io.crash.air";
    private final int id;

    private DeliveryMechanism(int n2) {
        this.id = n2;
    }

    public static DeliveryMechanism determineFrom(String string) {
        if (BETA_APP_PACKAGE_NAME.equals(string)) {
            return TEST_DISTRIBUTION;
        }
        if (string != null) {
            return APP_STORE;
        }
        return DEVELOPER;
    }

    public int getId() {
        return this.id;
    }

    public String toString() {
        return Integer.toString(this.id);
    }
}
