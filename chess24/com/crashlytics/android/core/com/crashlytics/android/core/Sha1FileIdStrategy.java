/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

import com.crashlytics.android.core.BinaryImagesConverter;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

class Sha1FileIdStrategy
implements BinaryImagesConverter.FileIdStrategy {
    Sha1FileIdStrategy() {
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static String getFileSHA(String object) throws IOException {
        void var1_5;
        block4 : {
            String string;
            Object var2_1 = null;
            object = new BufferedInputStream(new FileInputStream((String)object));
            try {
                string = CommonUtils.sha1((InputStream)object);
            }
            catch (Throwable throwable) {
                break block4;
            }
            CommonUtils.closeQuietly((Closeable)object);
            return string;
            catch (Throwable throwable) {
                object = var2_1;
            }
        }
        CommonUtils.closeQuietly((Closeable)object);
        throw var1_5;
    }

    @Override
    public String createId(File file) throws IOException {
        return Sha1FileIdStrategy.getFileSHA(file.getPath());
    }
}
