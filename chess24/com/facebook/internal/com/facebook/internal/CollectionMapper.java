/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.internal;

import com.facebook.FacebookException;
import com.facebook.internal.Mutable;
import java.util.Iterator;
import java.util.LinkedList;

public class CollectionMapper {
    private CollectionMapper() {
    }

    public static <T> void iterate(final Collection<T> collection, ValueMapper valueMapper, final OnMapperCompleteListener onMapperCompleteListener) {
        Iterator iterator = new Mutable<Boolean>(false);
        Mutable<Integer> mutable = new Mutable<Integer>(1);
        onMapperCompleteListener = new OnMapperCompleteListener((Mutable)((Object)iterator), mutable, onMapperCompleteListener){
            final /* synthetic */ Mutable val$didReturnError;
            final /* synthetic */ OnMapperCompleteListener val$onMapperCompleteListener;
            final /* synthetic */ Mutable val$pendingJobCount;
            {
                this.val$didReturnError = mutable;
                this.val$pendingJobCount = mutable2;
                this.val$onMapperCompleteListener = onMapperCompleteListener;
            }

            @Override
            public void onComplete() {
                if (((Boolean)this.val$didReturnError.value).booleanValue()) {
                    return;
                }
                Mutable mutable = this.val$pendingJobCount;
                Integer n = (Integer)this.val$pendingJobCount.value - 1;
                mutable.value = n;
                if (n == 0) {
                    this.val$onMapperCompleteListener.onComplete();
                }
            }

            @Override
            public void onError(FacebookException facebookException) {
                if (((Boolean)this.val$didReturnError.value).booleanValue()) {
                    return;
                }
                this.val$didReturnError.value = true;
                this.val$onMapperCompleteListener.onError(facebookException);
            }
        };
        iterator = collection.keyIterator();
        Object object = new LinkedList();
        while (iterator.hasNext()) {
            object.add(iterator.next());
        }
        iterator = object.iterator();
        while (iterator.hasNext()) {
            final Object object2 = iterator.next();
            object = collection.get(object2);
            object2 = new OnMapValueCompleteListener(){

                @Override
                public void onComplete(Object object) {
                    collection.set(object2, object, onMapperCompleteListener);
                    onMapperCompleteListener.onComplete();
                }

                @Override
                public void onError(FacebookException facebookException) {
                    onMapperCompleteListener.onError(facebookException);
                }
            };
            Integer n = (Integer)mutable.value;
            mutable.value = (Integer)mutable.value + 1;
            valueMapper.mapValue(object, (OnMapValueCompleteListener)object2);
        }
        onMapperCompleteListener.onComplete();
    }

    public static interface Collection<T> {
        public Object get(T var1);

        public Iterator<T> keyIterator();

        public void set(T var1, Object var2, OnErrorListener var3);
    }

    public static interface OnErrorListener {
        public void onError(FacebookException var1);
    }

    public static interface OnMapValueCompleteListener
    extends OnErrorListener {
        public void onComplete(Object var1);
    }

    public static interface OnMapperCompleteListener
    extends OnErrorListener {
        public void onComplete();
    }

    public static interface ValueMapper {
        public void mapValue(Object var1, OnMapValueCompleteListener var2);
    }

}
