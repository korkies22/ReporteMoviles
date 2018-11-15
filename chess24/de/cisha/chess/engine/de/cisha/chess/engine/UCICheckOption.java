/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.engine;

import de.cisha.chess.engine.UCIOption;
import de.cisha.chess.engine.UCIOptionListener;
import de.cisha.chess.engine.UCIValuedOption;

public class UCICheckOption
extends UCIValuedOption<Boolean> {
    public UCICheckOption(String string, UCIOptionListener uCIOptionListener, boolean bl) {
        super(string, uCIOptionListener, bl);
    }

    @Override
    public UCIOption.UCIOptionType getType() {
        return UCIOption.UCIOptionType.UCIOPTION_TYPE_CHECK;
    }

    @Override
    public boolean isValidValue(Boolean bl) {
        return true;
    }
}
