/*
 * Decompiled with CFR 0_134.
 */
package android.support.v7.widget;

class TooltipCompatHandler
implements Runnable {
    TooltipCompatHandler() {
    }

    @Override
    public void run() {
        TooltipCompatHandler.this.show(false);
    }
}
