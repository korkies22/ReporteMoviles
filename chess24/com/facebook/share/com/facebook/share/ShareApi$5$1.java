/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.share;

import com.facebook.internal.Mutable;
import com.facebook.share.ShareApi;
import java.util.Iterator;

class ShareApi
implements Iterator<Integer> {
    final /* synthetic */ Mutable val$current;
    final /* synthetic */ int val$size;

    ShareApi(Mutable mutable, int n) {
        this.val$current = mutable;
        this.val$size = n;
    }

    @Override
    public boolean hasNext() {
        if ((Integer)this.val$current.value < this.val$size) {
            return true;
        }
        return false;
    }

    @Override
    public Integer next() {
        Integer n = (Integer)this.val$current.value;
        Mutable mutable = this.val$current;
        mutable.value = (Integer)mutable.value + 1;
        return n;
    }

    @Override
    public void remove() {
    }
}
