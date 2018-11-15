/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.ui.list;

public interface UpdatingList {
    public void disableFooter();

    public void disableHeader();

    public void enableFooter();

    public void enableHeader();

    public void setListScrollListener(UpdatingListListener var1);

    public void updateFinished();

    public void updateStarted();

    public static interface UpdatingListListener {
        public void footerReached();

        public void headerReached();
    }

}
