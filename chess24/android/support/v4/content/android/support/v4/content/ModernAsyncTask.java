/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Binder
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 *  android.os.Process
 *  android.util.Log
 */
package android.support.v4.content;

import android.os.Binder;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.support.annotation.RestrictTo;
import android.util.Log;
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

abstract class ModernAsyncTask<Params, Progress, Result> {
    private static final int CORE_POOL_SIZE = 5;
    private static final int KEEP_ALIVE = 1;
    private static final String LOG_TAG = "AsyncTask";
    private static final int MAXIMUM_POOL_SIZE = 128;
    private static final int MESSAGE_POST_PROGRESS = 2;
    private static final int MESSAGE_POST_RESULT = 1;
    public static final Executor THREAD_POOL_EXECUTOR;
    private static volatile Executor sDefaultExecutor;
    private static InternalHandler sHandler;
    private static final BlockingQueue<Runnable> sPoolWorkQueue;
    private static final ThreadFactory sThreadFactory;
    private final AtomicBoolean mCancelled = new AtomicBoolean();
    private final FutureTask<Result> mFuture = new FutureTask<Result>(this.mWorker){

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        protected void done() {
            try {
                Object v = this.get();
                ModernAsyncTask.this.postResultIfNotInvoked(v);
                return;
            }
            catch (Throwable throwable) {
                throw new RuntimeException("An error occurred while executing doInBackground()", throwable);
            }
            catch (ExecutionException executionException) {
                throw new RuntimeException("An error occurred while executing doInBackground()", executionException.getCause());
            }
            catch (InterruptedException interruptedException) {
                Log.w((String)ModernAsyncTask.LOG_TAG, (Throwable)interruptedException);
                return;
            }
            catch (CancellationException cancellationException) {}
            ModernAsyncTask.this.postResultIfNotInvoked(null);
        }
    };
    private volatile Status mStatus = Status.PENDING;
    private final AtomicBoolean mTaskInvoked = new AtomicBoolean();
    private final WorkerRunnable<Params, Result> mWorker = new WorkerRunnable<Params, Result>(){

        /*
         * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
         * Loose catch block
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        @Override
        public Result call() throws Exception {
            Throwable throwable2;
            Throwable throwable;
            block7 : {
                Throwable throwable3;
                block8 : {
                    ModernAsyncTask.this.mTaskInvoked.set(true);
                    throwable3 = null;
                    throwable2 = throwable = null;
                    Process.setThreadPriority((int)10);
                    throwable2 = throwable;
                    throwable = ModernAsyncTask.this.doInBackground(this.mParams);
                    try {
                        Binder.flushPendingCommands();
                        ModernAsyncTask.this.postResult(throwable);
                    }
                    catch (Throwable throwable4) {
                        throwable2 = throwable;
                        throwable = throwable4;
                        break block7;
                    }
                    catch (Throwable throwable5) {
                        break block8;
                    }
                    return (Result)throwable;
                    catch (Throwable throwable6) {
                        break block7;
                    }
                    catch (Throwable throwable7) {
                        throwable = throwable3;
                        throwable3 = throwable7;
                    }
                }
                throwable2 = throwable;
                {
                    ModernAsyncTask.this.mCancelled.set(true);
                    throwable2 = throwable;
                    throw throwable3;
                }
            }
            ModernAsyncTask.this.postResult(throwable2);
            throw throwable;
        }
    };

    static {
        sThreadFactory = new ThreadFactory(){
            private final AtomicInteger mCount = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable runnable) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("ModernAsyncTask #");
                stringBuilder.append(this.mCount.getAndIncrement());
                return new Thread(runnable, stringBuilder.toString());
            }
        };
        sPoolWorkQueue = new LinkedBlockingQueue<Runnable>(10);
        sDefaultExecutor = THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(5, 128, 1L, TimeUnit.SECONDS, sPoolWorkQueue, sThreadFactory);
    }

    ModernAsyncTask() {
    }

    public static void execute(Runnable runnable) {
        sDefaultExecutor.execute(runnable);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static Handler getHandler() {
        synchronized (ModernAsyncTask.class) {
            if (sHandler != null) return sHandler;
            sHandler = new InternalHandler();
            return sHandler;
        }
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public static void setDefaultExecutor(Executor executor) {
        sDefaultExecutor = executor;
    }

    public final boolean cancel(boolean bl) {
        this.mCancelled.set(true);
        return this.mFuture.cancel(bl);
    }

    protected /* varargs */ abstract Result doInBackground(Params ... var1);

    public final /* varargs */ ModernAsyncTask<Params, Progress, Result> execute(Params ... arrParams) {
        return this.executeOnExecutor(sDefaultExecutor, arrParams);
    }

    public final /* varargs */ ModernAsyncTask<Params, Progress, Result> executeOnExecutor(Executor executor, Params ... arrParams) {
        if (this.mStatus != Status.PENDING) {
            switch (.$SwitchMap$android$support$v4$content$ModernAsyncTask$Status[this.mStatus.ordinal()]) {
                default: {
                    throw new IllegalStateException("We should never reach this state");
                }
                case 2: {
                    throw new IllegalStateException("Cannot execute task: the task has already been executed (a task can be executed only once)");
                }
                case 1: 
            }
            throw new IllegalStateException("Cannot execute task: the task is already running.");
        }
        this.mStatus = Status.RUNNING;
        this.onPreExecute();
        this.mWorker.mParams = arrParams;
        executor.execute(this.mFuture);
        return this;
    }

    void finish(Result Result) {
        if (this.isCancelled()) {
            this.onCancelled(Result);
        } else {
            this.onPostExecute(Result);
        }
        this.mStatus = Status.FINISHED;
    }

    public final Result get() throws InterruptedException, ExecutionException {
        return this.mFuture.get();
    }

    public final Result get(long l, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        return this.mFuture.get(l, timeUnit);
    }

    public final Status getStatus() {
        return this.mStatus;
    }

    public final boolean isCancelled() {
        return this.mCancelled.get();
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

    Result postResult(Result Result) {
        ModernAsyncTask.getHandler().obtainMessage(1, new AsyncTaskResult<Object>(this, Result)).sendToTarget();
        return Result;
    }

    void postResultIfNotInvoked(Result Result) {
        if (!this.mTaskInvoked.get()) {
            this.postResult(Result);
        }
    }

    protected final /* varargs */ void publishProgress(Progress ... arrProgress) {
        if (!this.isCancelled()) {
            ModernAsyncTask.getHandler().obtainMessage(2, new AsyncTaskResult<Progress>(this, arrProgress)).sendToTarget();
        }
    }

    private static class AsyncTaskResult<Data> {
        final Data[] mData;
        final ModernAsyncTask mTask;

        /* varargs */ AsyncTaskResult(ModernAsyncTask modernAsyncTask, Data ... arrData) {
            this.mTask = modernAsyncTask;
            this.mData = arrData;
        }
    }

    private static class InternalHandler
    extends Handler {
        InternalHandler() {
            super(Looper.getMainLooper());
        }

        public void handleMessage(Message message) {
            AsyncTaskResult asyncTaskResult = (AsyncTaskResult)message.obj;
            switch (message.what) {
                default: {
                    return;
                }
                case 2: {
                    asyncTaskResult.mTask.onProgressUpdate(asyncTaskResult.mData);
                    return;
                }
                case 1: 
            }
            asyncTaskResult.mTask.finish(asyncTaskResult.mData[0]);
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
        Params[] mParams;

        WorkerRunnable() {
        }
    }

}
