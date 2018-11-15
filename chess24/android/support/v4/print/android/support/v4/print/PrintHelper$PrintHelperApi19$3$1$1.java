/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.CancellationSignal
 *  android.os.CancellationSignal$OnCancelListener
 */
package android.support.v4.print;

import android.os.CancellationSignal;
import android.support.v4.print.PrintHelper;

class PrintHelper.PrintHelperApi19
implements CancellationSignal.OnCancelListener {
    PrintHelper.PrintHelperApi19() {
    }

    public void onCancel() {
        1.this.this$1.cancelLoad();
        1.this.cancel(false);
    }
}
