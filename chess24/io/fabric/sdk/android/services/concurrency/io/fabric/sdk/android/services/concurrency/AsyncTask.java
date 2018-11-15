/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 *  android.os.Process
 *  android.util.Log
 */
package io.fabric.sdk.android.services.concurrency;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AsyncTask<Params, Progress, Result> {
    private static final int CORE_POOL_SIZE;
    private static final int CPU_COUNT;
    private static final int KEEP_ALIVE = 1;
    private static final String LOG_TAG = "AsyncTask";
    private static final int MAXIMUM_POOL_SIZE;
    private static final int MESSAGE_POST_PROGRESS = 2;
    private static final int MESSAGE_POST_RESULT = 1;
    public static final Executor SERIAL_EXECUTOR;
    public static final Executor THREAD_POOL_EXECUTOR;
    private static volatile Executor defaultExecutor;
    private static final InternalHandler handler;
    private static final BlockingQueue<Runnable> poolWorkQueue;
    private static final ThreadFactory threadFactory;
    private final AtomicBoolean cancelled = new AtomicBoolean();
    private final FutureTask<Result> future = new FutureTask<Result>(this.worker){

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        protected void done() {
            try {
                AsyncTask.this.postResultIfNotInvoked(this.get());
                return;
            }
            catch (ExecutionException executionException) {
                throw new RuntimeException("An error occured while executing doInBackground()", executionException.getCause());
            }
            catch (InterruptedException interruptedException) {
                Log.w((String)AsyncTask.LOG_TAG, (Throwable)interruptedException);
                return;
            }
            catch (CancellationException cancellationException) {}
            AsyncTask.this.postResultIfNotInvoked(null);
        }
    };
    private volatile Status status = Status.PENDING;
    private final AtomicBoolean taskInvoked = new AtomicBoolean();
    private final WorkerRunnable<Params, Result> worker = new WorkerRunnable<Params, Result>(){

        @Override
        public Result call() throws Exception {
            AsyncTask.this.taskInvoked.set(true);
            Process.setThreadPriority((int)10);
            return (Result)AsyncTask.this.postResult(AsyncTask.this.doInBackground(this.params));
        }
    };

    static {
        CPU_COUNT = Runtime.getRuntime().availableProcessors();
        CORE_POOL_SIZE = CPU_COUNT + 1;
        MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
        threadFactory = new ThreadFactory(){
            private final AtomicInteger count = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable runnable) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("AsyncTask #");
                stringBuilder.append(this.count.getAndIncrement());
                return new Thread(runnable, stringBuilder.toString());
            }
        };
        poolWorkQueue = new LinkedBlockingQueue<Runnable>(128);
        THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, 1L, TimeUnit.SECONDS, poolWorkQueue, threadFactory);
        SERIAL_EXECUTOR = new SerialExecutor();
        handler = new InternalHandler();
        defaultExecutor = SERIAL_EXECUTOR;
    }

    public static void execute(Runnable runnable) {
        defaultExecutor.execute(runnable);
    }

    private void finish(Result Result) {
        if (this.isCancelled()) {
            this.onCancelled(Result);
        } else {
            this.onPostExecute(Result);
        }
        this.status = Status.FINISHED;
    }

    public static void init() {
        handler.getLooper();
    }

    private Result postResult(Result Result) {
        handler.obtainMessage(1, new AsyncTaskResult<Object>(this, Result)).sendToTarget();
        return Result;
    }

    private void postResultIfNotInvoked(Result Result) {
        if (!this.taskInvoked.get()) {
            this.postResult(Result);
        }
    }

    public static void setDefaultExecutor(Executor executor) {
        defaultExecutor = executor;
    }

    public final boolean cancel(boolean bl) {
        this.cancelled.set(true);
        return this.future.cancel(bl);
    }

    protected /* varargs */ abstract Result doInBackground(Params ... var1);

    public final /* varargs */ AsyncTask<Params, Progress, Result> execute(Params ... arrParams) {
        return this.executeOnExecutor(defaultExecutor, arrParams);
    }

    public final /* varargs */ AsyncTask<Params, Progress, Result> executeOnExecutor(Executor executor, Params ... arrParams) {
        if (this.status != Status.PENDING) {
            switch (.$SwitchMap$io$fabric$sdk$android$services$concurrency$AsyncTask$Status[this.status.ordinal()]) {
                default: {
                    break;
                }
                case 2: {
                    throw new IllegalStateException("Cannot execute task: the task has already been executed (a task can be executed only once)");
                }
                case 1: {
                    throw new IllegalStateException("Cannot execute task: the task is already running.");
                }
            }
        }
        this.status = Status.RUNNING;
        this.onPreExecute();
        this.worker.params = arrParams;
        executor.execute(this.future);
        return this;
    }

    public final Result get() throws InterruptedException, ExecutionException {
        return this.future.get();
    }

    public final Result get(long l, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        return this.future.get(l, timeUnit);
    }

    public final Status getStatus() {
        return this.status;
    }

    public final boolean isCancelled() {
        return this.cancelled.get();
    }

    protected void onCancelled() {
    }

    protected void onCancelled(Result Result) {
        this.onCancelled();
    }

    protected void onPostExecute(Result Result) {
    }

    protected void onPreExecute() {
    }

    protected /* varargs */ void onProgressUpdate(Progress ... arrProgress) {
    }

    protected final /* varargs */ void publishProgress(Progress ... arrProgress) {
        if (!this.isCancelled()) {
            handler.obtainMessage(2, new AsyncTaskResult<Progress>(this, arrProgress)).sendToTarget();
        }
    }

    private static class AsyncTaskResult<Data> {
        final Data[] data;
        final AsyncTask task;

        /* varargs */ AsyncTaskResult(AsyncTask asyncTask, Data ... arrData) {
            this.task = asyncTask;
            this.data = arrData;
        }
    }

    private static class InternalHandler
    extends Handler {
        public InternalHandler() {
            super(Looper.getMainLooper());
        }

        public void handleMessage(Message message) {
            AsyncTaskResult asyncTaskResult = (AsyncTaskResult)message.obj;
            switch (message.what) {
                default: {
                    return;
                }
                case 2: {
                    asyncTaskResult.task.onProgressUpdate(asyncTaskResult.data);
                    return;
                }
                case 1: 
            }
            asyncTaskResult.task.finish(asyncTaskResult.data[0]);
        }
    }

    private static class SerialExecutor
    implements Executor {
        Runnable active;
        final LinkedList<Runnable> tasks = new LinkedList();

        private SerialExecutor() {
        }

        @Override
        public void execute(final Runnable runnable) {
            synchronized (this) {
                this.tasks.offer(new Runnable(){

                    @Override
                    public void run() {
                        try {
                            runnable.run();
                            return;
                        }
                        finally {
                            SerialExecutor.this.scheduleNext();
                        }
                    }
                });
                if (this.active == null) {
                    this.scheduleNext();
                }
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        protected void scheduleNext() {
            synchronized (this) {
                Runnable runnable;
                this.active = runnable = this.tasks.poll();
                if (runnable != null) {
                    AsyncTask.THREAD_POOL_EXECUTOR.execute(this.active);
                }
                return;
            }
        }

    }

    public static enum Status {
        PENDING,
        RUNNING,
        FINISHED;
        

        private Status() {
        }
    }

    private static abstract class WorkerRunnable<Params, Result>
    implements Callable<Result> {
        Params[] params;

        private WorkerRunnable() {
        }
    }

}
