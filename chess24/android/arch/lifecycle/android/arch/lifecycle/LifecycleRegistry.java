/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package android.arch.lifecycle;

import android.arch.core.internal.FastSafeIterableMap;
import android.arch.core.internal.SafeIterableMap;
import android.arch.lifecycle.GenericLifecycleObserver;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Lifecycling;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class LifecycleRegistry
extends Lifecycle {
    private static final String LOG_TAG = "LifecycleRegistry";
    private int mAddingObserverCounter = 0;
    private boolean mHandlingEvent = false;
    private final WeakReference<LifecycleOwner> mLifecycleOwner;
    private boolean mNewEventOccurred = false;
    private FastSafeIterableMap<LifecycleObserver, ObserverWithState> mObserverMap = new FastSafeIterableMap();
    private ArrayList<Lifecycle.State> mParentStates = new ArrayList();
    private Lifecycle.State mState;

    public LifecycleRegistry(@NonNull LifecycleOwner lifecycleOwner) {
        this.mLifecycleOwner = new WeakReference<LifecycleOwner>(lifecycleOwner);
        this.mState = Lifecycle.State.INITIALIZED;
    }

    private void backwardPass(LifecycleOwner lifecycleOwner) {
        Iterator iterator = this.mObserverMap.descendingIterator();
        while (iterator.hasNext() && !this.mNewEventOccurred) {
            Map.Entry entry = iterator.next();
            ObserverWithState observerWithState = (ObserverWithState)entry.getValue();
            while (observerWithState.mState.compareTo(this.mState) > 0 && !this.mNewEventOccurred && this.mObserverMap.contains((LifecycleObserver)entry.getKey())) {
                Lifecycle.Event event = LifecycleRegistry.downEvent(observerWithState.mState);
                this.pushParentState(LifecycleRegistry.getStateAfter(event));
                observerWithState.dispatchEvent(lifecycleOwner, event);
                this.popParentState();
            }
        }
    }

    private Lifecycle.State calculateTargetState(LifecycleObserver object) {
        object = this.mObserverMap.ceil((LifecycleObserver)object);
        Lifecycle.State state = null;
        object = object != null ? ((ObserverWithState)object.getValue()).mState : null;
        if (!this.mParentStates.isEmpty()) {
            state = this.mParentStates.get(this.mParentStates.size() - 1);
        }
        return LifecycleRegistry.min(LifecycleRegistry.min(this.mState, (Lifecycle.State)((Object)object)), state);
    }

    private static Lifecycle.Event downEvent(Lifecycle.State state) {
        switch (.$SwitchMap$android$arch$lifecycle$Lifecycle$State[state.ordinal()]) {
            default: {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unexpected state value ");
                stringBuilder.append((Object)state);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            case 5: {
                throw new IllegalArgumentException();
            }
            case 4: {
                return Lifecycle.Event.ON_PAUSE;
            }
            case 3: {
                return Lifecycle.Event.ON_STOP;
            }
            case 2: {
                return Lifecycle.Event.ON_DESTROY;
            }
            case 1: 
        }
        throw new IllegalArgumentException();
    }

    private void forwardPass(LifecycleOwner lifecycleOwner) {
        SafeIterableMap.IteratorWithAdditions iteratorWithAdditions = this.mObserverMap.iteratorWithAdditions();
        while (iteratorWithAdditions.hasNext() && !this.mNewEventOccurred) {
            Map.Entry entry = (Map.Entry)iteratorWithAdditions.next();
            ObserverWithState observerWithState = (ObserverWithState)entry.getValue();
            while (observerWithState.mState.compareTo(this.mState) < 0 && !this.mNewEventOccurred && this.mObserverMap.contains((LifecycleObserver)entry.getKey())) {
                this.pushParentState(observerWithState.mState);
                observerWithState.dispatchEvent(lifecycleOwner, LifecycleRegistry.upEvent(observerWithState.mState));
                this.popParentState();
            }
        }
    }

    static Lifecycle.State getStateAfter(Lifecycle.Event event) {
        switch (.$SwitchMap$android$arch$lifecycle$Lifecycle$Event[event.ordinal()]) {
            default: {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unexpected event value ");
                stringBuilder.append((Object)event);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            case 6: {
                return Lifecycle.State.DESTROYED;
            }
            case 5: {
                return Lifecycle.State.RESUMED;
            }
            case 3: 
            case 4: {
                return Lifecycle.State.STARTED;
            }
            case 1: 
            case 2: 
        }
        return Lifecycle.State.CREATED;
    }

    private boolean isSynced() {
        if (this.mObserverMap.size() == 0) {
            return true;
        }
        Lifecycle.State state = ((ObserverWithState)this.mObserverMap.eldest().getValue()).mState;
        Lifecycle.State state2 = ((ObserverWithState)this.mObserverMap.newest().getValue()).mState;
        if (state == state2 && this.mState == state2) {
            return true;
        }
        return false;
    }

    static Lifecycle.State min(@NonNull Lifecycle.State state, @Nullable Lifecycle.State state2) {
        Lifecycle.State state3 = state;
        if (state2 != null) {
            state3 = state;
            if (state2.compareTo(state) < 0) {
                state3 = state2;
            }
        }
        return state3;
    }

    private void moveToState(Lifecycle.State state) {
        if (this.mState == state) {
            return;
        }
        this.mState = state;
        if (!this.mHandlingEvent && this.mAddingObserverCounter == 0) {
            this.mHandlingEvent = true;
            this.sync();
            this.mHandlingEvent = false;
            return;
        }
        this.mNewEventOccurred = true;
    }

    private void popParentState() {
        this.mParentStates.remove(this.mParentStates.size() - 1);
    }

    private void pushParentState(Lifecycle.State state) {
        this.mParentStates.add(state);
    }

    private void sync() {
        LifecycleOwner lifecycleOwner = (LifecycleOwner)this.mLifecycleOwner.get();
        if (lifecycleOwner == null) {
            Log.w((String)LOG_TAG, (String)"LifecycleOwner is garbage collected, you shouldn't try dispatch new events from it.");
            return;
        }
        while (!this.isSynced()) {
            this.mNewEventOccurred = false;
            if (this.mState.compareTo(((ObserverWithState)this.mObserverMap.eldest().getValue()).mState) < 0) {
                this.backwardPass(lifecycleOwner);
            }
            Map.Entry entry = this.mObserverMap.newest();
            if (this.mNewEventOccurred || entry == null || this.mState.compareTo(((ObserverWithState)entry.getValue()).mState) <= 0) continue;
            this.forwardPass(lifecycleOwner);
        }
        this.mNewEventOccurred = false;
    }

    private static Lifecycle.Event upEvent(Lifecycle.State state) {
        switch (.$SwitchMap$android$arch$lifecycle$Lifecycle$State[state.ordinal()]) {
            default: {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unexpected state value ");
                stringBuilder.append((Object)state);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            case 4: {
                throw new IllegalArgumentException();
            }
            case 3: {
                return Lifecycle.Event.ON_RESUME;
            }
            case 2: {
                return Lifecycle.Event.ON_START;
            }
            case 1: 
            case 5: 
        }
        return Lifecycle.Event.ON_CREATE;
    }

    @Override
    public void addObserver(@NonNull LifecycleObserver lifecycleObserver) {
        Lifecycle.State state = this.mState == Lifecycle.State.DESTROYED ? Lifecycle.State.DESTROYED : Lifecycle.State.INITIALIZED;
        ObserverWithState observerWithState = new ObserverWithState(lifecycleObserver, state);
        if (this.mObserverMap.putIfAbsent(lifecycleObserver, observerWithState) != null) {
            return;
        }
        LifecycleOwner lifecycleOwner = (LifecycleOwner)this.mLifecycleOwner.get();
        if (lifecycleOwner == null) {
            return;
        }
        boolean bl = this.mAddingObserverCounter != 0 || this.mHandlingEvent;
        state = this.calculateTargetState(lifecycleObserver);
        ++this.mAddingObserverCounter;
        while (observerWithState.mState.compareTo(state) < 0 && this.mObserverMap.contains(lifecycleObserver)) {
            this.pushParentState(observerWithState.mState);
            observerWithState.dispatchEvent(lifecycleOwner, LifecycleRegistry.upEvent(observerWithState.mState));
            this.popParentState();
            state = this.calculateTargetState(lifecycleObserver);
        }
        if (!bl) {
            this.sync();
        }
        --this.mAddingObserverCounter;
    }

    @NonNull
    @Override
    public Lifecycle.State getCurrentState() {
        return this.mState;
    }

    public int getObserverCount() {
        return this.mObserverMap.size();
    }

    public void handleLifecycleEvent(@NonNull Lifecycle.Event event) {
        this.moveToState(LifecycleRegistry.getStateAfter(event));
    }

    @MainThread
    public void markState(@NonNull Lifecycle.State state) {
        this.moveToState(state);
    }

    @Override
    public void removeObserver(@NonNull LifecycleObserver lifecycleObserver) {
        this.mObserverMap.remove(lifecycleObserver);
    }

    static class ObserverWithState {
        GenericLifecycleObserver mLifecycleObserver;
        Lifecycle.State mState;

        ObserverWithState(LifecycleObserver lifecycleObserver, Lifecycle.State state) {
            this.mLifecycleObserver = Lifecycling.getCallback(lifecycleObserver);
            this.mState = state;
        }

        void dispatchEvent(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
            Lifecycle.State state = LifecycleRegistry.getStateAfter(event);
            this.mState = LifecycleRegistry.min(this.mState, state);
            this.mLifecycleObserver.onStateChanged(lifecycleOwner, event);
            this.mState = state;
        }
    }

}
