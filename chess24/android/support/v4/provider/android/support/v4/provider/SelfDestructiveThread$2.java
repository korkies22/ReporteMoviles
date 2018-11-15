/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 */
package android.support.v4.provider;

import android.os.Handler;
import android.support.v4.provider.SelfDestructiveThread;
import java.util.concurrent.Callable;

class SelfDestructiveThread
implements Runnable {
    final /* synthetic */ Callable val$callable;
    final /* synthetic */ Handler val$callingHandler;
    final /* synthetic */ SelfDestructiveThread.ReplyCallback val$reply;

    SelfDestructiveThread(Callable callable, Handler handler, SelfDestructiveThread.ReplyCallback replyCallback) {
        this.val$callable = callable;
        this.val$callingHandler = handler;
        this.val$reply = replyCallback;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void run() {
        Object v;
        block2 : {
            try {
                v = this.val$callable.call();
                break block2;
            }
            catch (Exception exception) {}
            v = null;
        }
        this.val$callingHandler.post(new Runnable(){

            @Override
            public void run() {
                2.this.val$reply.onReply(v);
            }
        });
    }

}
