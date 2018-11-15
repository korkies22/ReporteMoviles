/*
 * Decompiled with CFR 0_134.
 */
package bolts;

import bolts.AggregateException;
import bolts.Continuation;
import bolts.TaskCompletionSource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

static final class Task
implements Continuation<Object, Void> {
    final /* synthetic */ TaskCompletionSource val$allFinished;
    final /* synthetic */ ArrayList val$causes;
    final /* synthetic */ AtomicInteger val$count;
    final /* synthetic */ Object val$errorLock;
    final /* synthetic */ AtomicBoolean val$isCancelled;

    Task(Object object, ArrayList arrayList, AtomicBoolean atomicBoolean, AtomicInteger atomicInteger, TaskCompletionSource taskCompletionSource) {
        this.val$errorLock = object;
        this.val$causes = arrayList;
        this.val$isCancelled = atomicBoolean;
        this.val$count = atomicInteger;
        this.val$allFinished = taskCompletionSource;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public Void then(bolts.Task<Object> object) {
        if (object.isFaulted()) {
            Object object2 = this.val$errorLock;
            synchronized (object2) {
                this.val$causes.add(object.getError());
            }
        }
        if (object.isCancelled()) {
            this.val$isCancelled.set(true);
        }
        if (this.val$count.decrementAndGet() == 0) {
            if (this.val$causes.size() != 0) {
                if (this.val$causes.size() == 1) {
                    this.val$allFinished.setError((Exception)this.val$causes.get(0));
                    return null;
                }
                object = new AggregateException(String.format("There were %d exceptions.", this.val$causes.size()), this.val$causes);
                this.val$allFinished.setError((Exception)object);
                return null;
            }
            if (this.val$isCancelled.get()) {
                this.val$allFinished.setCancelled();
                return null;
            }
            this.val$allFinished.setResult(null);
        }
        return null;
    }
}
