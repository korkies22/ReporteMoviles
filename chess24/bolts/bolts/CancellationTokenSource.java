/*
 * Decompiled with CFR 0_134.
 */
package bolts;

import bolts.BoltsExecutors;
import bolts.CancellationToken;
import bolts.CancellationTokenRegistration;
import java.io.Closeable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class CancellationTokenSource
implements Closeable {
    private boolean cancellationRequested;
    private boolean closed;
    private final ScheduledExecutorService executor = BoltsExecutors.scheduled();
    private final Object lock = new Object();
    private final List<CancellationTokenRegistration> registrations = new ArrayList<CancellationTokenRegistration>();
    private ScheduledFuture<?> scheduledCancellation;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void cancelAfter(long l, TimeUnit timeUnit) {
        if (l < -1L) {
            throw new IllegalArgumentException("Delay must be >= -1");
        }
        if (l == 0L) {
            this.cancel();
            return;
        }
        Object object = this.lock;
        synchronized (object) {
            if (this.cancellationRequested) {
                return;
            }
            this.cancelScheduledCancellation();
            if (l != -1L) {
                this.scheduledCancellation = this.executor.schedule(new Runnable(){

                    /*
                     * Enabled aggressive block sorting
                     * Enabled unnecessary exception pruning
                     * Enabled aggressive exception aggregation
                     */
                    @Override
                    public void run() {
                        Object object = CancellationTokenSource.this.lock;
                        synchronized (object) {
                            CancellationTokenSource.this.scheduledCancellation = null;
                        }
                        CancellationTokenSource.this.cancel();
                    }
                }, l, timeUnit);
            }
            return;
        }
    }

    private void cancelScheduledCancellation() {
        if (this.scheduledCancellation != null) {
            this.scheduledCancellation.cancel(true);
            this.scheduledCancellation = null;
        }
    }

    private void notifyListeners(List<CancellationTokenRegistration> object) {
        object = object.iterator();
        while (object.hasNext()) {
            ((CancellationTokenRegistration)object.next()).runAction();
        }
    }

    private void throwIfClosed() {
        if (this.closed) {
            throw new IllegalStateException("Object already closed");
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void cancel() {
        ArrayList<CancellationTokenRegistration> arrayList;
        Object object = this.lock;
        synchronized (object) {
            this.throwIfClosed();
            if (this.cancellationRequested) {
                return;
            }
            this.cancelScheduledCancellation();
            this.cancellationRequested = true;
            arrayList = new ArrayList<CancellationTokenRegistration>(this.registrations);
        }
        this.notifyListeners(arrayList);
    }

    public void cancelAfter(long l) {
        this.cancelAfter(l, TimeUnit.MILLISECONDS);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void close() {
        Object object = this.lock;
        synchronized (object) {
            if (this.closed) {
                return;
            }
            this.cancelScheduledCancellation();
            Iterator<CancellationTokenRegistration> iterator = this.registrations.iterator();
            do {
                if (!iterator.hasNext()) {
                    this.registrations.clear();
                    this.closed = true;
                    return;
                }
                iterator.next().close();
            } while (true);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public CancellationToken getToken() {
        Object object = this.lock;
        synchronized (object) {
            this.throwIfClosed();
            return new CancellationToken(this);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isCancellationRequested() {
        Object object = this.lock;
        synchronized (object) {
            this.throwIfClosed();
            return this.cancellationRequested;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    CancellationTokenRegistration register(Runnable object) {
        Object object2 = this.lock;
        synchronized (object2) {
            this.throwIfClosed();
            object = new CancellationTokenRegistration(this, (Runnable)object);
            if (this.cancellationRequested) {
                object.runAction();
            } else {
                this.registrations.add((CancellationTokenRegistration)object);
            }
            return object;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void throwIfCancellationRequested() throws CancellationException {
        Object object = this.lock;
        synchronized (object) {
            this.throwIfClosed();
            if (this.cancellationRequested) {
                throw new CancellationException();
            }
            return;
        }
    }

    public String toString() {
        return String.format(Locale.US, "%s@%s[cancellationRequested=%s]", this.getClass().getName(), Integer.toHexString(this.hashCode()), Boolean.toString(this.isCancellationRequested()));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void unregister(CancellationTokenRegistration cancellationTokenRegistration) {
        Object object = this.lock;
        synchronized (object) {
            this.throwIfClosed();
            this.registrations.remove(cancellationTokenRegistration);
            return;
        }
    }

}
