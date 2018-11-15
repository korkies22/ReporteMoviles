/*
 * Decompiled with CFR 0_134.
 */
package android.support.v4.view;

class ViewPager
implements Runnable {
    ViewPager() {
    }

    @Override
    public void run() {
        ViewPager.this.setScrollState(0);
        ViewPager.this.populate();
    }
}
