/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Comparator;

final class Utils {
    private static final FilenameFilter ALL_FILES_FILTER = new FilenameFilter(){

        @Override
        public boolean accept(File file, String string) {
            return true;
        }
    };

    private Utils() {
    }

    static int capFileCount(File file, int n, Comparator<File> comparator) {
        return Utils.capFileCount(file, ALL_FILES_FILTER, n, comparator);
    }

    static int capFileCount(File arrfile, FilenameFilter object2, int n, Comparator<File> comparator) {
        void var3_5;
        arrfile = arrfile.listFiles((FilenameFilter)object2);
        if (arrfile == null) {
            return 0;
        }
        int n2 = arrfile.length;
        Arrays.sort(arrfile, var3_5);
        for (File file : arrfile) {
            void var2_4;
            if (n2 <= var2_4) {
                return n2;
            }
            file.delete();
            --n2;
        }
        return n2;
    }

}
