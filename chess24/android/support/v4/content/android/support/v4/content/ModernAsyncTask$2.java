/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Binder
 *  android.os.Process
 */
package android.support.v4.content;

import android.os.Binder;
import android.os.Process;
import android.support.v4.content.ModernAsyncTask;
import java.util.concurrent.atomic.AtomicBoolean;

class ModernAsyncTask
extends ModernAsyncTask.WorkerRunnable<Params, Result> {
    ModernAsyncTask() {
    }

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
}
