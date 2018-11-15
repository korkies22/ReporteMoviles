/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  bolts.Task.TaskCompletionSource
 */
package bolts;

import bolts.AggregateException;
import bolts.AndroidExecutors;
import bolts.BoltsExecutors;
import bolts.CancellationToken;
import bolts.CancellationTokenRegistration;
import bolts.Capture;
import bolts.Continuation;
import bolts.ExecutorException;
import bolts.UnobservedErrorNotifier;
import bolts.UnobservedTaskException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Task<TResult> {
    public static final ExecutorService BACKGROUND_EXECUTOR = BoltsExecutors.background();
    private static final Executor IMMEDIATE_EXECUTOR = BoltsExecutors.immediate();
    private static Task<?> TASK_CANCELLED;
    private static Task<Boolean> TASK_FALSE;
    private static Task<?> TASK_NULL;
    private static Task<Boolean> TASK_TRUE;
    public static final Executor UI_THREAD_EXECUTOR;
    private static volatile UnobservedExceptionHandler unobservedExceptionHandler;
    private boolean cancelled;
    private boolean complete;
    private List<Continuation<TResult, Void>> continuations = new ArrayList<Continuation<TResult, Void>>();
    private Exception error;
    private boolean errorHasBeenObserved;
    private final Object lock = new Object();
    private TResult result;
    private UnobservedErrorNotifier unobservedErrorNotifier;

    static {
        UI_THREAD_EXECUTOR = AndroidExecutors.uiThread();
        TASK_NULL = new Task<Object>(null);
        TASK_TRUE = new Task<Boolean>(Boolean.valueOf(true));
        TASK_FALSE = new Task<Boolean>(Boolean.valueOf(false));
        TASK_CANCELLED = new Task<TResult>(true);
    }

    Task() {
    }

    private Task(TResult TResult) {
        this.trySetResult(TResult);
    }

    private Task(boolean bl) {
        if (bl) {
            this.trySetCancelled();
            return;
        }
        this.trySetResult(null);
    }

    public static <TResult> Task<TResult> call(Callable<TResult> callable) {
        return Task.call(callable, IMMEDIATE_EXECUTOR, null);
    }

    public static <TResult> Task<TResult> call(Callable<TResult> callable, CancellationToken cancellationToken) {
        return Task.call(callable, IMMEDIATE_EXECUTOR, cancellationToken);
    }

    public static <TResult> Task<TResult> call(Callable<TResult> callable, Executor executor) {
        return Task.call(callable, executor, null);
    }

    public static <TResult> Task<TResult> call(final Callable<TResult> callable, Executor executor, final CancellationToken cancellationToken) {
        final bolts.TaskCompletionSource taskCompletionSource = new bolts.TaskCompletionSource();
        try {
            executor.execute(new Runnable(){

                /*
                 * Enabled aggressive block sorting
                 * Enabled unnecessary exception pruning
                 * Enabled aggressive exception aggregation
                 */
                @Override
                public void run() {
                    if (cancellationToken != null && cancellationToken.isCancellationRequested()) {
                        taskCompletionSource.setCancelled();
                        return;
                    }
                    try {
                        taskCompletionSource.setResult(callable.call());
                        return;
                    }
                    catch (Exception exception) {
                        taskCompletionSource.setError(exception);
                        return;
                    }
                    catch (CancellationException cancellationException) {}
                    taskCompletionSource.setCancelled();
                }
            });
        }
        catch (Exception exception) {
            taskCompletionSource.setError(new ExecutorException(exception));
        }
        return taskCompletionSource.getTask();
    }

    public static <TResult> Task<TResult> callInBackground(Callable<TResult> callable) {
        return Task.call(callable, BACKGROUND_EXECUTOR, null);
    }

    public static <TResult> Task<TResult> callInBackground(Callable<TResult> callable, CancellationToken cancellationToken) {
        return Task.call(callable, BACKGROUND_EXECUTOR, cancellationToken);
    }

    public static <TResult> Task<TResult> cancelled() {
        return TASK_CANCELLED;
    }

    private static <TContinuationResult, TResult> void completeAfterTask(final bolts.TaskCompletionSource<TContinuationResult> taskCompletionSource, final Continuation<TResult, Task<TContinuationResult>> continuation, final Task<TResult> task, Executor executor, final CancellationToken cancellationToken) {
        try {
            executor.execute(new Runnable(){

                /*
                 * Enabled aggressive block sorting
                 * Enabled unnecessary exception pruning
                 * Enabled aggressive exception aggregation
                 */
                @Override
                public void run() {
                    if (cancellationToken != null && cancellationToken.isCancellationRequested()) {
                        taskCompletionSource.setCancelled();
                        return;
                    }
                    try {
                        Task task2 = (Task)continuation.then(task);
                        if (task2 == null) {
                            taskCompletionSource.setResult(null);
                            return;
                        }
                        task2.continueWith(new Continuation<TContinuationResult, Void>(){

                            @Override
                            public Void then(Task<TContinuationResult> task) {
                                if (cancellationToken != null && cancellationToken.isCancellationRequested()) {
                                    taskCompletionSource.setCancelled();
                                    return null;
                                }
                                if (task.isCancelled()) {
                                    taskCompletionSource.setCancelled();
                                    return null;
                                }
                                if (task.isFaulted()) {
                                    taskCompletionSource.setError(task.getError());
                                    return null;
                                }
                                taskCompletionSource.setResult(task.getResult());
                                return null;
                            }
                        });
                        return;
                    }
                    catch (Exception exception) {
                        taskCompletionSource.setError(exception);
                        return;
                    }
                    catch (CancellationException cancellationException) {}
                    taskCompletionSource.setCancelled();
                }

            });
            return;
        }
        catch (Exception exception) {
            taskCompletionSource.setError(new ExecutorException(exception));
            return;
        }
    }

    private static <TContinuationResult, TResult> void completeImmediately(final bolts.TaskCompletionSource<TContinuationResult> taskCompletionSource, final Continuation<TResult, TContinuationResult> continuation, final Task<TResult> task, Executor executor, final CancellationToken cancellationToken) {
        try {
            executor.execute(new Runnable(){

                /*
                 * Enabled aggressive block sorting
                 * Enabled unnecessary exception pruning
                 * Enabled aggressive exception aggregation
                 */
                @Override
                public void run() {
                    if (cancellationToken != null && cancellationToken.isCancellationRequested()) {
                        taskCompletionSource.setCancelled();
                        return;
                    }
                    try {
                        Object TContinuationResult = continuation.then(task);
                        taskCompletionSource.setResult(TContinuationResult);
                        return;
                    }
                    catch (Exception exception) {
                        taskCompletionSource.setError(exception);
                        return;
                    }
                    catch (CancellationException cancellationException) {}
                    taskCompletionSource.setCancelled();
                }
            });
            return;
        }
        catch (Exception exception) {
            taskCompletionSource.setError(new ExecutorException(exception));
            return;
        }
    }

    public static <TResult> bolts.Task.TaskCompletionSource create() {
        Task<TResult> task = new Task<TResult>();
        task.getClass();
        return task.new TaskCompletionSource();
    }

    public static Task<Void> delay(long l) {
        return Task.delay(l, BoltsExecutors.scheduled(), null);
    }

    public static Task<Void> delay(long l, CancellationToken cancellationToken) {
        return Task.delay(l, BoltsExecutors.scheduled(), cancellationToken);
    }

    static Task<Void> delay(long l, ScheduledExecutorService object, CancellationToken cancellationToken) {
        if (cancellationToken != null && cancellationToken.isCancellationRequested()) {
            return Task.cancelled();
        }
        if (l <= 0L) {
            return Task.forResult(null);
        }
        final bolts.TaskCompletionSource taskCompletionSource = new bolts.TaskCompletionSource();
        object = object.schedule(new Runnable(){

            @Override
            public void run() {
                taskCompletionSource.trySetResult(null);
            }
        }, l, TimeUnit.MILLISECONDS);
        if (cancellationToken != null) {
            cancellationToken.register(new Runnable((ScheduledFuture)object, taskCompletionSource){
                final /* synthetic */ ScheduledFuture val$scheduled;
                final /* synthetic */ bolts.TaskCompletionSource val$tcs;
                {
                    this.val$scheduled = scheduledFuture;
                    this.val$tcs = taskCompletionSource;
                }

                @Override
                public void run() {
                    this.val$scheduled.cancel(true);
                    this.val$tcs.trySetCancelled();
                }
            });
        }
        return taskCompletionSource.getTask();
    }

    public static <TResult> Task<TResult> forError(Exception exception) {
        bolts.TaskCompletionSource taskCompletionSource = new bolts.TaskCompletionSource();
        taskCompletionSource.setError(exception);
        return taskCompletionSource.getTask();
    }

    public static <TResult> Task<TResult> forResult(TResult TResult) {
        if (TResult == null) {
            return TASK_NULL;
        }
        if (TResult instanceof Boolean) {
            if (((Boolean)TResult).booleanValue()) {
                return TASK_TRUE;
            }
            return TASK_FALSE;
        }
        bolts.TaskCompletionSource<TResult> taskCompletionSource = new bolts.TaskCompletionSource<TResult>();
        taskCompletionSource.setResult(TResult);
        return taskCompletionSource.getTask();
    }

    public static UnobservedExceptionHandler getUnobservedExceptionHandler() {
        return unobservedExceptionHandler;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void runContinuations() {
        Object object = this.lock;
        synchronized (object) {
            Iterator<Continuation<TResult, Void>> iterator = this.continuations.iterator();
            do {
                if (!iterator.hasNext()) {
                    this.continuations = null;
                    return;
                }
                Continuation<TResult, Void> continuation = iterator.next();
                try {
                    continuation.then(this);
                }
                catch (Exception exception) {
                    throw new RuntimeException(exception);
                }
                catch (RuntimeException runtimeException) {
                    throw runtimeException;
                }
            } while (true);
        }
    }

    public static void setUnobservedExceptionHandler(UnobservedExceptionHandler unobservedExceptionHandler) {
        Task.unobservedExceptionHandler = unobservedExceptionHandler;
    }

    public static Task<Void> whenAll(Collection<? extends Task<?>> object) {
        if (object.size() == 0) {
            return Task.forResult(null);
        }
        final bolts.TaskCompletionSource taskCompletionSource = new bolts.TaskCompletionSource();
        final ArrayList arrayList = new ArrayList();
        final Object object2 = new Object();
        final AtomicInteger atomicInteger = new AtomicInteger(object.size());
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        object = object.iterator();
        while (object.hasNext()) {
            ((Task)object.next()).continueWith(new Continuation<Object, Void>(){

                /*
                 * Enabled aggressive block sorting
                 * Enabled unnecessary exception pruning
                 * Enabled aggressive exception aggregation
                 */
                @Override
                public Void then(Task<Object> object) {
                    if (object.isFaulted()) {
                        Object object22 = object2;
                        synchronized (object22) {
                            arrayList.add(object.getError());
                        }
                    }
                    if (object.isCancelled()) {
                        atomicBoolean.set(true);
                    }
                    if (atomicInteger.decrementAndGet() == 0) {
                        if (arrayList.size() != 0) {
                            if (arrayList.size() == 1) {
                                taskCompletionSource.setError((Exception)arrayList.get(0));
                                return null;
                            }
                            object = new AggregateException(String.format("There were %d exceptions.", arrayList.size()), arrayList);
                            taskCompletionSource.setError((Exception)object);
                            return null;
                        }
                        if (atomicBoolean.get()) {
                            taskCompletionSource.setCancelled();
                            return null;
                        }
                        taskCompletionSource.setResult(null);
                    }
                    return null;
                }
            });
        }
        return taskCompletionSource.getTask();
    }

    public static <TResult> Task<List<TResult>> whenAllResult(final Collection<? extends Task<TResult>> collection) {
        return Task.whenAll(collection).onSuccess(new Continuation<Void, List<TResult>>(){

            @Override
            public List<TResult> then(Task<Void> object) throws Exception {
                if (collection.size() == 0) {
                    return Collections.emptyList();
                }
                object = new ArrayList();
                Iterator iterator = collection.iterator();
                while (iterator.hasNext()) {
                    object.add(((Task)iterator.next()).getResult());
                }
                return object;
            }
        });
    }

    public static Task<Task<?>> whenAny(Collection<? extends Task<?>> object) {
        if (object.size() == 0) {
            return Task.forResult(null);
        }
        final bolts.TaskCompletionSource taskCompletionSource = new bolts.TaskCompletionSource();
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        object = object.iterator();
        while (object.hasNext()) {
            ((Task)object.next()).continueWith(new Continuation<Object, Void>(){

                @Override
                public Void then(Task<Object> task) {
                    if (atomicBoolean.compareAndSet(false, true)) {
                        taskCompletionSource.setResult(task);
                    } else {
                        task.getError();
                    }
                    return null;
                }
            });
        }
        return taskCompletionSource.getTask();
    }

    public static <TResult> Task<Task<TResult>> whenAnyResult(Collection<? extends Task<TResult>> object) {
        if (object.size() == 0) {
            return Task.forResult(null);
        }
        final bolts.TaskCompletionSource taskCompletionSource = new bolts.TaskCompletionSource();
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        object = object.iterator();
        while (object.hasNext()) {
            ((Task)object.next()).continueWith(new Continuation<TResult, Void>(){

                @Override
                public Void then(Task<TResult> task) {
                    if (atomicBoolean.compareAndSet(false, true)) {
                        taskCompletionSource.setResult(task);
                    } else {
                        task.getError();
                    }
                    return null;
                }
            });
        }
        return taskCompletionSource.getTask();
    }

    public <TOut> Task<TOut> cast() {
        return this;
    }

    public Task<Void> continueWhile(Callable<Boolean> callable, Continuation<Void, Task<Void>> continuation) {
        return this.continueWhile(callable, continuation, IMMEDIATE_EXECUTOR, null);
    }

    public Task<Void> continueWhile(Callable<Boolean> callable, Continuation<Void, Task<Void>> continuation, CancellationToken cancellationToken) {
        return this.continueWhile(callable, continuation, IMMEDIATE_EXECUTOR, cancellationToken);
    }

    public Task<Void> continueWhile(Callable<Boolean> callable, Continuation<Void, Task<Void>> continuation, Executor executor) {
        return this.continueWhile(callable, continuation, executor, null);
    }

    public Task<Void> continueWhile(final Callable<Boolean> callable, final Continuation<Void, Task<Void>> continuation, final Executor executor, final CancellationToken cancellationToken) {
        final Capture<> capture = new Capture<>();
        capture.set(new Continuation<Void, Task<Void>>(){

            @Override
            public Task<Void> then(Task<Void> task) throws Exception {
                if (cancellationToken != null && cancellationToken.isCancellationRequested()) {
                    return Task.cancelled();
                }
                if (((Boolean)callable.call()).booleanValue()) {
                    return Task.forResult(null).onSuccessTask(continuation, executor).onSuccessTask((Continuation)capture.get(), executor);
                }
                return Task.forResult(null);
            }
        });
        return this.makeVoid().continueWithTask((Continuation)capture.get(), executor);
    }

    public <TContinuationResult> Task<TContinuationResult> continueWith(Continuation<TResult, TContinuationResult> continuation) {
        return this.continueWith(continuation, IMMEDIATE_EXECUTOR, null);
    }

    public <TContinuationResult> Task<TContinuationResult> continueWith(Continuation<TResult, TContinuationResult> continuation, CancellationToken cancellationToken) {
        return this.continueWith(continuation, IMMEDIATE_EXECUTOR, cancellationToken);
    }

    public <TContinuationResult> Task<TContinuationResult> continueWith(Continuation<TResult, TContinuationResult> continuation, Executor executor) {
        return this.continueWith(continuation, executor, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public <TContinuationResult> Task<TContinuationResult> continueWith(final Continuation<TResult, TContinuationResult> continuation, final Executor executor, final CancellationToken cancellationToken) {
        final bolts.TaskCompletionSource taskCompletionSource = new bolts.TaskCompletionSource();
        Object object = this.lock;
        // MONITORENTER : object
        boolean bl = this.isCompleted();
        if (!bl) {
            this.continuations.add(new Continuation<TResult, Void>(){

                @Override
                public Void then(Task<TResult> task) {
                    Task.completeImmediately(taskCompletionSource, continuation, task, executor, cancellationToken);
                    return null;
                }
            });
        }
        // MONITOREXIT : object
        if (!bl) return taskCompletionSource.getTask();
        Task.completeImmediately(taskCompletionSource, continuation, this, executor, cancellationToken);
        return taskCompletionSource.getTask();
    }

    public <TContinuationResult> Task<TContinuationResult> continueWithTask(Continuation<TResult, Task<TContinuationResult>> continuation) {
        return this.continueWithTask(continuation, IMMEDIATE_EXECUTOR, null);
    }

    public <TContinuationResult> Task<TContinuationResult> continueWithTask(Continuation<TResult, Task<TContinuationResult>> continuation, CancellationToken cancellationToken) {
        return this.continueWithTask(continuation, IMMEDIATE_EXECUTOR, cancellationToken);
    }

    public <TContinuationResult> Task<TContinuationResult> continueWithTask(Continuation<TResult, Task<TContinuationResult>> continuation, Executor executor) {
        return this.continueWithTask(continuation, executor, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public <TContinuationResult> Task<TContinuationResult> continueWithTask(final Continuation<TResult, Task<TContinuationResult>> continuation, final Executor executor, final CancellationToken cancellationToken) {
        final bolts.TaskCompletionSource taskCompletionSource = new bolts.TaskCompletionSource();
        Object object = this.lock;
        // MONITORENTER : object
        boolean bl = this.isCompleted();
        if (!bl) {
            this.continuations.add(new Continuation<TResult, Void>(){

                @Override
                public Void then(Task<TResult> task) {
                    Task.completeAfterTask(taskCompletionSource, continuation, task, executor, cancellationToken);
                    return null;
                }
            });
        }
        // MONITOREXIT : object
        if (!bl) return taskCompletionSource.getTask();
        Task.completeAfterTask(taskCompletionSource, continuation, this, executor, cancellationToken);
        return taskCompletionSource.getTask();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Exception getError() {
        Object object = this.lock;
        synchronized (object) {
            if (this.error == null) return this.error;
            this.errorHasBeenObserved = true;
            if (this.unobservedErrorNotifier == null) return this.error;
            this.unobservedErrorNotifier.setObserved();
            this.unobservedErrorNotifier = null;
            return this.error;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public TResult getResult() {
        Object object = this.lock;
        synchronized (object) {
            TResult TResult = this.result;
            return TResult;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isCancelled() {
        Object object = this.lock;
        synchronized (object) {
            return this.cancelled;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isCompleted() {
        Object object = this.lock;
        synchronized (object) {
            return this.complete;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isFaulted() {
        Object object = this.lock;
        synchronized (object) {
            if (this.getError() == null) return false;
            return true;
        }
    }

    public Task<Void> makeVoid() {
        return this.continueWithTask(new Continuation<TResult, Task<Void>>(){

            @Override
            public Task<Void> then(Task<TResult> task) throws Exception {
                if (task.isCancelled()) {
                    return Task.cancelled();
                }
                if (task.isFaulted()) {
                    return Task.forError(task.getError());
                }
                return Task.forResult(null);
            }
        });
    }

    public <TContinuationResult> Task<TContinuationResult> onSuccess(Continuation<TResult, TContinuationResult> continuation) {
        return this.onSuccess(continuation, IMMEDIATE_EXECUTOR, null);
    }

    public <TContinuationResult> Task<TContinuationResult> onSuccess(Continuation<TResult, TContinuationResult> continuation, CancellationToken cancellationToken) {
        return this.onSuccess(continuation, IMMEDIATE_EXECUTOR, cancellationToken);
    }

    public <TContinuationResult> Task<TContinuationResult> onSuccess(Continuation<TResult, TContinuationResult> continuation, Executor executor) {
        return this.onSuccess(continuation, executor, null);
    }

    public <TContinuationResult> Task<TContinuationResult> onSuccess(final Continuation<TResult, TContinuationResult> continuation, Executor executor, final CancellationToken cancellationToken) {
        return this.continueWithTask(new Continuation<TResult, Task<TContinuationResult>>(){

            @Override
            public Task<TContinuationResult> then(Task<TResult> task) {
                if (cancellationToken != null && cancellationToken.isCancellationRequested()) {
                    return Task.cancelled();
                }
                if (task.isFaulted()) {
                    return Task.forError(task.getError());
                }
                if (task.isCancelled()) {
                    return Task.cancelled();
                }
                return task.continueWith(continuation);
            }
        }, executor);
    }

    public <TContinuationResult> Task<TContinuationResult> onSuccessTask(Continuation<TResult, Task<TContinuationResult>> continuation) {
        return this.onSuccessTask(continuation, IMMEDIATE_EXECUTOR);
    }

    public <TContinuationResult> Task<TContinuationResult> onSuccessTask(Continuation<TResult, Task<TContinuationResult>> continuation, CancellationToken cancellationToken) {
        return this.onSuccessTask(continuation, IMMEDIATE_EXECUTOR, cancellationToken);
    }

    public <TContinuationResult> Task<TContinuationResult> onSuccessTask(Continuation<TResult, Task<TContinuationResult>> continuation, Executor executor) {
        return this.onSuccessTask(continuation, executor, null);
    }

    public <TContinuationResult> Task<TContinuationResult> onSuccessTask(final Continuation<TResult, Task<TContinuationResult>> continuation, Executor executor, final CancellationToken cancellationToken) {
        return this.continueWithTask(new Continuation<TResult, Task<TContinuationResult>>(){

            @Override
            public Task<TContinuationResult> then(Task<TResult> task) {
                if (cancellationToken != null && cancellationToken.isCancellationRequested()) {
                    return Task.cancelled();
                }
                if (task.isFaulted()) {
                    return Task.forError(task.getError());
                }
                if (task.isCancelled()) {
                    return Task.cancelled();
                }
                return task.continueWithTask(continuation);
            }
        }, executor);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    boolean trySetCancelled() {
        Object object = this.lock;
        synchronized (object) {
            if (this.complete) {
                return false;
            }
            this.complete = true;
            this.cancelled = true;
            this.lock.notifyAll();
            this.runContinuations();
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    boolean trySetError(Exception exception) {
        Object object = this.lock;
        synchronized (object) {
            if (this.complete) {
                return false;
            }
            this.complete = true;
            this.error = exception;
            this.errorHasBeenObserved = false;
            this.lock.notifyAll();
            this.runContinuations();
            if (!this.errorHasBeenObserved && Task.getUnobservedExceptionHandler() != null) {
                this.unobservedErrorNotifier = new UnobservedErrorNotifier(this);
            }
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    boolean trySetResult(TResult TResult) {
        Object object = this.lock;
        synchronized (object) {
            if (this.complete) {
                return false;
            }
            this.complete = true;
            this.result = TResult;
            this.lock.notifyAll();
            this.runContinuations();
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void waitForCompletion() throws InterruptedException {
        Object object = this.lock;
        synchronized (object) {
            if (!this.isCompleted()) {
                this.lock.wait();
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean waitForCompletion(long l, TimeUnit timeUnit) throws InterruptedException {
        Object object = this.lock;
        synchronized (object) {
            if (this.isCompleted()) return this.isCompleted();
            this.lock.wait(timeUnit.toMillis(l));
            return this.isCompleted();
        }
    }

    public class TaskCompletionSource
    extends bolts.TaskCompletionSource<TResult> {
        TaskCompletionSource() {
        }
    }

    public static interface UnobservedExceptionHandler {
        public void unobservedException(Task<?> var1, UnobservedTaskException var2);
    }

}
