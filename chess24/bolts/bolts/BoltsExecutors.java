/*
 * Decompiled with CFR 0_134.
 */
package bolts;

import bolts.AndroidExecutors;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

final class BoltsExecutors {
    private static final BoltsExecutors INSTANCE = new BoltsExecutors();
    private final ExecutorService background;
    private final Executor immediate;
    private final ScheduledExecutorService scheduled;

    private BoltsExecutors() {
        ExecutorService executorService = !BoltsExecutors.isAndroidRuntime() ? Executors.newCachedThreadPool() : AndroidExecutors.newCachedThreadPool();
        this.background = executorService;
        this.scheduled = Executors.newSingleThreadScheduledExecutor();
        this.immediate = new ImmediateExecutor();
    }

    public static ExecutorService background() {
        return BoltsExecutors.INSTANCE.background;
    }

    static Executor immediate() {
        return BoltsExecutors.INSTANCE.immediate;
    }

    private static boolean isAndroidRuntime() {
        String string = System.getProperty("java.runtime.name");
        if (string == null) {
            return false;
        }
        return string.toLowerCase(Locale.US).contains("android");
    }

    static ScheduledExecutorService scheduled() {
        return BoltsExecutors.INSTANCE.scheduled;
    }

    private static class ImmediateExecutor
    implements Executor {
        private static final int MAX_DEPTH = 15;
        private ThreadLocal<Integer> executionDepth = new ThreadLocal();

        private ImmediateExecutor() {
        }

        private int decrementDepth() {
            int n;
            Integer n2;
            Integer n3 = n2 = this.executionDepth.get();
            if (n2 == null) {
                n3 = 0;
            }
            if ((n = n3 - 1) == 0) {
                this.executionDepth.remove();
                return n;
            }
            this.executionDepth.set(n);
            return n;
        }

        private int incrementDepth() {
            Integer n;
            Integer n2 = n = this.executionDepth.get();
            if (n == null) {
                n2 = 0;
            }
            int n3 = n2 + 1;
            this.executionDepth.set(n3);
            return n3;
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        @Override
        public void execute(Runnable var1_1) {
            if (this.incrementDepth() > 15) ** GOTO lbl5
            try {
                block2 : {
                    var1_1.run();
                    break block2;
lbl5: // 1 sources:
                    BoltsExecutors.background().execute(var1_1);
                }
                this.decrementDepth();
                return;
            }
            catch (Throwable var1_2) {}
            this.decrementDepth();
            throw var1_2;
        }
    }

}
