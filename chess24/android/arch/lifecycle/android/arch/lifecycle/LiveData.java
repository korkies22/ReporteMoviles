/*
 * Decompiled with CFR 0_134.
 */
package android.arch.lifecycle;

import android.arch.core.executor.ArchTaskExecutor;
import android.arch.core.internal.SafeIterableMap;
import android.arch.lifecycle.GenericLifecycleObserver;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.Map;

public abstract class LiveData<T> {
    private static final Object NOT_SET = new Object();
    static final int START_VERSION = -1;
    private int mActiveCount = 0;
    private volatile Object mData = NOT_SET;
    private final Object mDataLock = new Object();
    private boolean mDispatchInvalidated;
    private boolean mDispatchingValue;
    private SafeIterableMap<Observer<T>, LiveData<T>> mObservers = new SafeIterableMap();
    private volatile Object mPendingData = NOT_SET;
    private final Runnable mPostValueRunnable = new Runnable(){

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void run() {
            Object object;
            Object object2 = LiveData.this.mDataLock;
            synchronized (object2) {
                object = LiveData.this.mPendingData;
                LiveData.this.mPendingData = NOT_SET;
            }
            LiveData.this.setValue(object);
        }
    };
    private int mVersion = -1;

    private static void assertMainThread(String string) {
        if (!ArchTaskExecutor.getInstance().isMainThread()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot invoke ");
            stringBuilder.append(string);
            stringBuilder.append(" on a background");
            stringBuilder.append(" thread");
            throw new IllegalStateException(stringBuilder.toString());
        }
    }

    private void considerNotify(LiveData<T> liveData) {
        if (!liveData.mActive) {
            return;
        }
        if (!liveData.shouldBeActive()) {
            liveData.activeStateChanged(false);
            return;
        }
        if (liveData.mLastVersion >= this.mVersion) {
            return;
        }
        liveData.mLastVersion = this.mVersion;
        liveData.mObserver.onChanged(this.mData);
    }

    private void dispatchingValue(@Nullable LiveData<T> liveData) {
        if (this.mDispatchingValue) {
            this.mDispatchInvalidated = true;
            return;
        }
        this.mDispatchingValue = true;
        do {
            LiveData<T> liveData2;
            block5 : {
                this.mDispatchInvalidated = false;
                if (liveData != null) {
                    this.considerNotify(liveData);
                    liveData2 = null;
                } else {
                    SafeIterableMap.IteratorWithAdditions iteratorWithAdditions = this.mObservers.iteratorWithAdditions();
                    do {
                        liveData2 = liveData;
                        if (!iteratorWithAdditions.hasNext()) break block5;
                        this.considerNotify((ObserverWrapper)((Map.Entry)iteratorWithAdditions.next()).getValue());
                    } while (!this.mDispatchInvalidated);
                    liveData2 = liveData;
                }
            }
            liveData = liveData2;
        } while (this.mDispatchInvalidated);
        this.mDispatchingValue = false;
    }

    @Nullable
    public T getValue() {
        Object object = this.mData;
        if (object != NOT_SET) {
            return (T)object;
        }
        return null;
    }

    int getVersion() {
        return this.mVersion;
    }

    public boolean hasActiveObservers() {
        if (this.mActiveCount > 0) {
            return true;
        }
        return false;
    }

    public boolean hasObservers() {
        if (this.mObservers.size() > 0) {
            return true;
        }
        return false;
    }

    @MainThread
    public void observe(@NonNull LifecycleOwner lifecycleOwner, @NonNull Observer<T> object) {
        if (lifecycleOwner.getLifecycle().getCurrentState() == Lifecycle.State.DESTROYED) {
            return;
        }
        LifecycleBoundObserver lifecycleBoundObserver = new LifecycleBoundObserver(lifecycleOwner, object);
        if ((object = (ObserverWrapper)((Object)this.mObservers.putIfAbsent((Observer<T>)object, lifecycleBoundObserver))) != null && !object.isAttachedTo(lifecycleOwner)) {
            throw new IllegalArgumentException("Cannot add the same observer with different lifecycles");
        }
        if (object != null) {
            return;
        }
        lifecycleOwner.getLifecycle().addObserver(lifecycleBoundObserver);
    }

    @MainThread
    public void observeForever(@NonNull Observer<T> object) {
        AlwaysActiveObserver alwaysActiveObserver = new AlwaysActiveObserver(object);
        if ((object = (ObserverWrapper)((Object)this.mObservers.putIfAbsent((Observer<T>)object, alwaysActiveObserver))) != null && object instanceof LifecycleBoundObserver) {
            throw new IllegalArgumentException("Cannot add the same observer with different lifecycles");
        }
        if (object != null) {
            return;
        }
        alwaysActiveObserver.activeStateChanged(true);
    }

    protected void onActive() {
    }

    protected void onInactive() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    protected void postValue(T t) {
        Object object = this.mDataLock;
        // MONITORENTER : object
        boolean bl = this.mPendingData == NOT_SET;
        this.mPendingData = t;
        // MONITOREXIT : object
        if (!bl) {
            return;
        }
        ArchTaskExecutor.getInstance().postToMainThread(this.mPostValueRunnable);
    }

    @MainThread
    public void removeObserver(@NonNull Observer<T> object) {
        LiveData.assertMainThread("removeObserver");
        object = (ObserverWrapper)((Object)this.mObservers.remove((Observer<T>)object));
        if (object == null) {
            return;
        }
        object.detachObserver();
        object.activeStateChanged(false);
    }

    @MainThread
    public void removeObservers(@NonNull LifecycleOwner lifecycleOwner) {
        LiveData.assertMainThread("removeObservers");
        for (Map.Entry<Observer<T>, LiveData<T>> entry : this.mObservers) {
            if (!((ObserverWrapper)((Object)entry.getValue())).isAttachedTo(lifecycleOwner)) continue;
            this.removeObserver(entry.getKey());
        }
    }

    @MainThread
    protected void setValue(T t) {
        LiveData.assertMainThread("setValue");
        ++this.mVersion;
        this.mData = t;
        this.dispatchingValue(null);
    }

    private class AlwaysActiveObserver
    extends LiveData<T> {
        AlwaysActiveObserver(Observer<T> observer) {
            super(observer);
        }

        boolean shouldBeActive() {
            return true;
        }
    }

    class LifecycleBoundObserver
    extends LiveData<T>
    implements GenericLifecycleObserver {
        @NonNull
        final LifecycleOwner mOwner;

        LifecycleBoundObserver(LifecycleOwner lifecycleOwner, Observer<T> observer) {
            super(observer);
            this.mOwner = lifecycleOwner;
        }

        void detachObserver() {
            this.mOwner.getLifecycle().removeObserver(this);
        }

        boolean isAttachedTo(LifecycleOwner lifecycleOwner) {
            if (this.mOwner == lifecycleOwner) {
                return true;
            }
            return false;
        }

        @Override
        public void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
            if (this.mOwner.getLifecycle().getCurrentState() == Lifecycle.State.DESTROYED) {
                LiveData.this.removeObserver(this.mObserver);
                return;
            }
            this.activeStateChanged(this.shouldBeActive());
        }

        boolean shouldBeActive() {
            return this.mOwner.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED);
        }
    }

    private abstract class ObserverWrapper {
        boolean mActive;
        int mLastVersion = -1;
        final Observer<T> mObserver;

        ObserverWrapper(Observer<T> observer) {
            this.mObserver = observer;
        }

        void activeStateChanged(boolean bl) {
            if (bl == this.mActive) {
                return;
            }
            this.mActive = bl;
            int n = LiveData.this.mActiveCount;
            int n2 = 1;
            n = n == 0 ? 1 : 0;
            LiveData liveData = LiveData.this;
            int n3 = liveData.mActiveCount;
            if (!this.mActive) {
                n2 = -1;
            }
            liveData.mActiveCount = n3 + n2;
            if (n != 0 && this.mActive) {
                LiveData.this.onActive();
            }
            if (LiveData.this.mActiveCount == 0 && !this.mActive) {
                LiveData.this.onInactive();
            }
            if (this.mActive) {
                LiveData.this.dispatchingValue(this);
            }
        }

        void detachObserver() {
        }

        boolean isAttachedTo(LifecycleOwner lifecycleOwner) {
            return false;
        }

        abstract boolean shouldBeActive();
    }

}
