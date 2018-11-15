/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.engine;

import de.cisha.chess.engine.UCIOption;

public static enum UCIOption.UCIOptionType {
    UCIOPTION_TYPE_SPIN("spin"),
    UCIOPTION_TYPE_STRING("string"),
    UCIOPTION_TYPE_CHECK("check"),
    UCIOPTION_TYPE_COMBO("combo"),
    UCIOPTION_TYPE_BUTTON("button");
    
    private String _type;

    private UCIOption.UCIOptionType(String string2) {
        this._type = string2;
    }

    public String getType() {
        return this._type;
    }
}
