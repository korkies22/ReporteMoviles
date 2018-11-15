/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.LayoutInflater
 *  android.view.LayoutInflater$Factory
 *  android.view.LayoutInflater$Factory2
 */
package android.support.v4.view;

import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.view.LayoutInflater;

static class LayoutInflaterCompat.LayoutInflaterCompatBaseImpl {
    LayoutInflaterCompat.LayoutInflaterCompatBaseImpl() {
    }

    public LayoutInflaterFactory getFactory(LayoutInflater layoutInflater) {
        if ((layoutInflater = layoutInflater.getFactory()) instanceof LayoutInflaterCompat.Factory2Wrapper) {
            return ((LayoutInflaterCompat.Factory2Wrapper)layoutInflater).mDelegateFactory;
        }
        return null;
    }

    public void setFactory(LayoutInflater layoutInflater, LayoutInflaterFactory object) {
        object = object != null ? new LayoutInflaterCompat.Factory2Wrapper((LayoutInflaterFactory)object) : null;
        this.setFactory2(layoutInflater, (LayoutInflater.Factory2)object);
    }

    public void setFactory2(LayoutInflater layoutInflater, LayoutInflater.Factory2 factory2) {
        layoutInflater.setFactory2(factory2);
        LayoutInflater.Factory factory = layoutInflater.getFactory();
        if (factory instanceof LayoutInflater.Factory2) {
            LayoutInflaterCompat.forceSetFactory2(layoutInflater, (LayoutInflater.Factory2)factory);
            return;
        }
        LayoutInflaterCompat.forceSetFactory2(layoutInflater, factory2);
    }
}
