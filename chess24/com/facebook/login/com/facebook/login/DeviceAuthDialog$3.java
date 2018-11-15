/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.login;

class DeviceAuthDialog
implements Runnable {
    DeviceAuthDialog() {
    }

    @Override
    public void run() {
        DeviceAuthDialog.this.poll();
    }
}
