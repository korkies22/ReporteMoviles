/*
 * Decompiled with CFR 0_134.
 */
package fi.harism.curl;

import fi.harism.curl.CurlRenderer;

public static interface CurlRenderer.Observer {
    public void onDrawFrame();

    public void onPageSizeChanged(int var1, int var2);

    public void onSurfaceCreated();
}
