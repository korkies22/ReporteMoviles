/*
 * Decompiled with CFR 0_134.
 */
package android.support.v4.provider;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class SelfDestructiveThread
implements Runnable {
    final /* synthetic */ Callable val$callable;
    final /* synthetic */ Condition val$cond;
    final /* synthetic */ AtomicReference val$holder;
    final /* synthetic */ ReentrantLock val$lock;
    final /* synthetic */ AtomicBoolean val$running;

    SelfDestructiveThread(AtomicReference atomicReference, Callable callable, ReentrantLock reentrantLock, AtomicBoolean atomicBoolean, Condition condition) {
        this.val$holder = atomicReference;
        this.val$callable = callable;
        this.val$lock = reentrantLock;
        this.val$running = atomicBoolean;
        this.val$cond = condition;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void run() {
        try {
            this.val$holder.set(this.val$callable.call());
        }
        catch (Exception exception) {}
        this.val$lock.lock();
        try {
            this.val$running.set(false);
            this.val$cond.signal();
            return;
        }
        finally {
            this.val$lock.unlock();
        }
    }
}
