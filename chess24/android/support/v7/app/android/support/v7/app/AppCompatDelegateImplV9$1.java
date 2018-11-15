/*
 * Decompiled with CFR 0_134.
 */
package android.support.v7.app;

class AppCompatDelegateImplV9
implements Runnable {
    AppCompatDelegateImplV9() {
    }

    @Override
    public void run() {
        if ((AppCompatDelegateImplV9.this.mInvalidatePanelMenuFeatures & 1) != 0) {
            AppCompatDelegateImplV9.this.doInvalidatePanelMenu(0);
        }
        if ((AppCompatDelegateImplV9.this.mInvalidatePanelMenuFeatures & 4096) != 0) {
            AppCompatDelegateImplV9.this.doInvalidatePanelMenu(108);
        }
        AppCompatDelegateImplV9.this.mInvalidatePanelMenuPosted = false;
        AppCompatDelegateImplV9.this.mInvalidatePanelMenuFeatures = 0;
    }
}
