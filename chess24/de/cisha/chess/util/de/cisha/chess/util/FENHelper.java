/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.util;

public abstract class FENHelper {
    public static int getPromotionTypeForFENChar(char c) {
        int n = 0;
        switch (c) {
            default: {
                return 0;
            }
            case 'Q': 
            case 'q': {
                return 3;
            }
            case 'N': 
            case 'n': {
                return 1;
            }
            case 'B': 
            case 'b': {
                n = 2;
            }
            case 'R': 
            case 'r': 
        }
        return n;
    }
}
