/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.engine;

import de.cisha.chess.engine.UCIOption;
import de.cisha.chess.engine.UCIOptionListener;

public class UCIButtonOption
extends UCIOption {
    public UCIButtonOption(String string, UCIOptionListener uCIOptionListener) {
        super(string, uCIOptionListener);
    }

    @Override
    public UCIOption.UCIOptionType getType() {
        return UCIOption.UCIOptionType.UCIOPTION_TYPE_BUTTON;
    }

    @Override
    public String getUCICommand() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("setoption name ");
        stringBuilder.append(this.getName());
        return stringBuilder.toString();
    }

    public void pushButton() {
        this.valueChanged();
    }
}
