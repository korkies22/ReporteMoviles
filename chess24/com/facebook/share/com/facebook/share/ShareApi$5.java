/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONArray
 *  org.json.JSONException
 */
package com.facebook.share;

import com.facebook.FacebookException;
import com.facebook.internal.CollectionMapper;
import com.facebook.internal.Mutable;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;

class ShareApi
implements CollectionMapper.Collection<Integer> {
    final /* synthetic */ ArrayList val$arrayList;
    final /* synthetic */ JSONArray val$stagedObject;

    ShareApi(ArrayList arrayList, JSONArray jSONArray) {
        this.val$arrayList = arrayList;
        this.val$stagedObject = jSONArray;
    }

    @Override
    public Object get(Integer n) {
        return this.val$arrayList.get(n);
    }

    @Override
    public Iterator<Integer> keyIterator() {
        int n = this.val$arrayList.size();
        return new Iterator<Integer>(new Mutable<Integer>(0), n){
            final /* synthetic */ Mutable val$current;
            final /* synthetic */ int val$size;
            {
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
        };
    }

    @Override
    public void set(Integer n, Object object, CollectionMapper.OnErrorListener onErrorListener) {
        try {
            this.val$stagedObject.put(n.intValue(), object);
            return;
        }
        catch (JSONException jSONException) {
            Object object2 = object = jSONException.getLocalizedMessage();
            if (object == null) {
                object2 = "Error staging object.";
            }
            onErrorListener.onError(new FacebookException((String)object2));
            return;
        }
    }

}
