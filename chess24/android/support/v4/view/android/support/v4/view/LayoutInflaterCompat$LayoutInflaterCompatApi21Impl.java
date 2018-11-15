/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.LayoutInflater
 *  android.view.LayoutInflater$Factory2
 */
package android.support.v4.view;

import android.support.annotation.RequiresApi;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.view.LayoutInflater;

@RequiresApi(value=21)
static class LayoutInflaterCompat.LayoutInflaterCompatApi21Impl
extends LayoutInflaterCompat.LayoutInflaterCompatBaseImpl {
    LayoutInflaterCompat.LayoutInflaterCompatApi21Impl() {
    }

    @Override
    public void setFactory(LayoutInflater layoutInflater, LayoutInflaterFactory object) {
        object = object != null ? new LayoutInflaterCompat.Factory2Wrapper((LayoutInflaterFactory)object) : null;
        layoutInflater.setFactory2((LayoutInflater.Factory2)object);
    }

    @Override
    public void setFactory2(LayoutInflater layoutInflater, LayoutInflater.Factory2 factory2) {
        layoutInflater.setFactory2(factory2);
    }
}
