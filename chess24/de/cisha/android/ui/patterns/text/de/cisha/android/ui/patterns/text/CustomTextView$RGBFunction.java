/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Color
 */
package de.cisha.android.ui.patterns.text;

import android.graphics.Color;
import de.cisha.android.ui.patterns.text.CustomTextView;

private static enum CustomTextView.RGBFunction {
    RED{

        @Override
        public int returnIntFor(int n) {
            return Color.red((int)n);
        }
    }
    ,
    GREEN{

        @Override
        public int returnIntFor(int n) {
            return Color.green((int)n);
        }
    }
    ,
    BLUE{

        @Override
        public int returnIntFor(int n) {
            return Color.blue((int)n);
        }
    };
    

    private CustomTextView.RGBFunction() {
    }

    public abstract int returnIntFor(int var1);

}
