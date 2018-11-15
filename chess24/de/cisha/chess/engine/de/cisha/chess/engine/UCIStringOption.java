/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.engine;

import de.cisha.chess.engine.UCIOption;
import de.cisha.chess.engine.UCIOptionListener;
import de.cisha.chess.engine.UCIValuedOption;

public class UCIStringOption
extends UCIValuedOption<String> {
    public UCIStringOption(String string, UCIOptionListener uCIOptionListener, String string2) {
        super(string, uCIOptionListener, string2);
    }

    @Override
    public UCIOption.UCIOptionType getType() {
        return UCIOption.UCIOptionType.UCIOPTION_TYPE_STRING;
    }

    @Override
    public boolean isValidValue(String string) {
        return true;
    }
}
