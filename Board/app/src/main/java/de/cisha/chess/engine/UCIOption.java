// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.engine;

public abstract class UCIOption
{
    protected static final String SETOPTION_COMMAND_STRING = "setoption name ";
    protected static final String SETOPTION_VALUE_STRING = " value ";
    private UCIOptionListener _listener;
    private String _name;
    
    public UCIOption(final String name, final UCIOptionListener listener) {
        this._name = name;
        this._listener = listener;
    }
    
    public String getName() {
        return this._name;
    }
    
    public abstract UCIOptionType getType();
    
    public abstract String getUCICommand();
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("UCI option name ");
        sb.append(this.getName());
        sb.append(" type ");
        sb.append(this.getType().getType());
        return sb.toString();
    }
    
    protected void valueChanged() {
        if (this._listener != null) {
            this._listener.optionChanged(this);
        }
    }
    
    public enum UCIOptionType
    {
        UCIOPTION_TYPE_BUTTON("button"), 
        UCIOPTION_TYPE_CHECK("check"), 
        UCIOPTION_TYPE_COMBO("combo"), 
        UCIOPTION_TYPE_SPIN("spin"), 
        UCIOPTION_TYPE_STRING("string");
        
        private String _type;
        
        private UCIOptionType(final String type) {
            this._type = type;
        }
        
        public String getType() {
            return this._type;
        }
    }
}
