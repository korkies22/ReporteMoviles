/*
 * Decompiled with CFR 0_134.
 */
package bolts;

import bolts.Task;
import bolts.UnobservedTaskException;

class UnobservedErrorNotifier {
    private Task<?> task;

    public UnobservedErrorNotifier(Task<?> task) {
        this.task = task;
    }

    protected void finalize() throws Throwable {
        block5 : {
            Task<?> task = this.task;
            if (task == null) break block5;
            Task.UnobservedExceptionHandler unobservedExceptionHandler = Task.getUnobservedExceptionHandler();
            if (unobservedExceptionHandler == null) break block5;
            unobservedExceptionHandler.unobservedException(task, new UnobservedTaskException(task.getError()));
        }
        return;
        finally {
            super.finalize();
        }
    }

    public void setObserved() {
        this.task = null;
    }
}
