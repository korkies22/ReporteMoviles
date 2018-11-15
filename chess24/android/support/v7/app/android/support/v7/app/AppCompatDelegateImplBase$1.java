/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.res.Resources
 *  android.content.res.Resources$NotFoundException
 */
package android.support.v7.app;

import android.content.res.Resources;

static final class AppCompatDelegateImplBase
implements Thread.UncaughtExceptionHandler {
    final /* synthetic */ Thread.UncaughtExceptionHandler val$defHandler;

    AppCompatDelegateImplBase(Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
        this.val$defHandler = uncaughtExceptionHandler;
    }

    private boolean shouldWrapException(Throwable object) {
        block2 : {
            boolean bl;
            block3 : {
                block4 : {
                    bl = object instanceof Resources.NotFoundException;
                    boolean bl2 = false;
                    if (!bl) break block2;
                    object = object.getMessage();
                    bl = bl2;
                    if (object == null) break block3;
                    if (object.contains("drawable")) break block4;
                    bl = bl2;
                    if (!object.contains("Drawable")) break block3;
                }
                bl = true;
            }
            return bl;
        }
        return false;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        if (this.shouldWrapException(throwable)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(throwable.getMessage());
            stringBuilder.append(android.support.v7.app.AppCompatDelegateImplBase.EXCEPTION_HANDLER_MESSAGE_SUFFIX);
            stringBuilder = new Resources.NotFoundException(stringBuilder.toString());
            stringBuilder.initCause(throwable.getCause());
            stringBuilder.setStackTrace(throwable.getStackTrace());
            this.val$defHandler.uncaughtException(thread, (Throwable)((Object)stringBuilder));
            return;
        }
        this.val$defHandler.uncaughtException(thread, throwable);
    }
}
