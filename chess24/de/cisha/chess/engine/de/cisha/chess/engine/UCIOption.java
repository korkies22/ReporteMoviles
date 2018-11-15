/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.engine;

import de.cisha.chess.engine.UCIOptionListener;

public abstract class UCIOption {
    protected static final String SETOPTION_COMMAND_STRING = "setoption name ";
    protected static final String SETOPTION_VALUE_STRING = " value ";
    private UCIOptionListener _listener;
    private String _name;

    public UCIOption(String string, UCIOptionListener uCIOptionListener) {
        this._name = string;
        this._listener = uCIOptionListener;
    }

    public String getName() {
        return this._name;
    }

    public abstract UCIOptionType getType();

    public abstract String getUCICommand();

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("UCI option name ");
        stringBuilder.append(this.getName());
        stringBuilder.append(" type ");
        stringBuilder.append(this.getType().getType());
        return stringBuilder.toString();
    }

    protected void valueChanged() {
        if (this._listener != null) {
            this._listener.optionChanged(this);
        }
    }

    public static enum UCIOptionType {
        UCIOPTION_TYPE_SPIN("spin"),
        UCIOPTION_TYPE_STRING("string"),
        UCIOPTION_TYPE_CHECK("check"),
        UCIOPTION_TYPE_COMBO("combo"),
        UCIOPTION_TYPE_BUTTON("button");
        
        private String _type;

        private UCIOptionType(String string2) {
            this._type = string2;
        }

        public String getType() {
            return this._type;
        }
    }

}
