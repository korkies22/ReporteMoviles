// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.model;

import java.util.regex.Pattern;
import java.text.ParseException;
import de.cisha.chess.util.Logger;
import java.text.DecimalFormat;
import java.io.Serializable;

public class FEN implements Serializable
{
    public static final char ACTIVE_COLOR_BLACK = 'b';
    public static final char ACTIVE_COLOR_WHITE = 'w';
    public static final FEN EMPTY_POSITION;
    private static final String REGEX_FEN = "[PNBRQKpnbrqk_]{8}(/[PNBRQKpnbrqk_]{8}){7}\\s(w|b)\\s(-|[KkQqHhAa]{1,4})\\s(-|([abcdefgh][36]))(\\s(\\d)+){2}";
    public static final FEN STARTING_POSITION;
    private static final long serialVersionUID = 3431658779297237108L;
    private String _fenString;
    private boolean _fenValidated;
    private boolean _isValid;
    
    static {
        STARTING_POSITION = new FEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w HAha - 0 1");
        EMPTY_POSITION = new FEN("8/8/8/8/8/8/8/8 w KQkq - 0 1");
    }
    
    public FEN() {
        this._isValid = false;
        this._fenValidated = false;
        this._fenString = "8/8/8/8/8/8/8/8 w - - 0 1";
    }
    
    public FEN(final String fenString) {
        this._isValid = false;
        this._fenValidated = false;
        this._fenString = fenString;
    }
    
    @Override
    public boolean equals(final Object o) {
        return this._fenString != null && o != null && o instanceof FEN && this._fenString.equals(((FEN)o).getFENString());
    }
    
    public char getActiveColorChar() {
        if (this.isValid()) {
            final int index = this._fenString.indexOf(32);
            final int length = this._fenString.length();
            final int n = index + 1;
            if (length > n) {
                return this._fenString.charAt(n);
            }
        }
        return 'w';
    }
    
    public boolean getActiveColorGuess() {
        final int index = this._fenString.indexOf(32);
        final int length = this._fenString.length();
        final int n = index + 1;
        int char1;
        if (length > n) {
            char1 = this._fenString.charAt(n);
        }
        else {
            char1 = 119;
        }
        return 119 == char1;
    }
    
    public String getCastlingString() {
        String substring = "-";
        if (this.isValid()) {
            final int index = this._fenString.indexOf(32, this._fenString.indexOf(32) + 1);
            final String fenString = this._fenString;
            final int n = index + 1;
            final int index2 = fenString.indexOf(32, n);
            substring = substring;
            if (index2 != -1) {
                substring = this._fenString.substring(n, index2);
            }
        }
        return substring;
    }
    
    public String getEnPassantString() {
        String substring = "-";
        if (this.isValid()) {
            final int index = this._fenString.indexOf(32, this._fenString.indexOf(32, this._fenString.indexOf(32) + 1) + 1);
            final String fenString = this._fenString;
            final int n = index + 1;
            final int index2 = fenString.indexOf(32, n);
            substring = substring;
            if (index2 != -1) {
                substring = this._fenString.substring(n, index2);
            }
        }
        return substring;
    }
    
    public String getFENString() {
        return this._fenString;
    }
    
    public int getMoveNumber() {
        if (this.isValid()) {
            final int index = this._fenString.indexOf(32, this._fenString.indexOf(32, this._fenString.indexOf(32, this._fenString.indexOf(32, this._fenString.indexOf(32) + 1) + 1) + 1) + 1);
            if (this._fenString.length() > index) {
                final String substring = this._fenString.substring(index + 1);
                final DecimalFormat decimalFormat = new DecimalFormat();
                try {
                    return decimalFormat.parse(substring).intValue();
                }
                catch (ParseException ex) {
                    Logger.getInstance().debug("FEN", "wrong FEN", ex);
                }
            }
        }
        return 1;
    }
    
    public int getNoActionNumber() {
        if (this.isValid()) {
            final int index = this._fenString.indexOf(32, this._fenString.indexOf(32, this._fenString.indexOf(32, this._fenString.indexOf(32) + 1) + 1) + 1);
            final String fenString = this._fenString;
            final int n = index + 1;
            final int index2 = fenString.indexOf(32, n);
            if (index2 != -1) {
                final String substring = this._fenString.substring(n, index2);
                final DecimalFormat decimalFormat = new DecimalFormat();
                try {
                    return decimalFormat.parse(substring).intValue();
                }
                catch (ParseException ex) {
                    Logger.getInstance().debug("FEN", "wrong FEN", ex);
                }
            }
        }
        return 0;
    }
    
    public String getPiecePlacmentString() {
        String substring = "8/8/8/8/8/8/8/8";
        if (this.isValid()) {
            substring = this._fenString.substring(0, this._fenString.indexOf(" "));
        }
        return substring;
    }
    
    public String getRank(int i) {
        final int n = 8 - i;
        String substring = "";
        if (this.isValid()) {
            if (n >= 0 && n < 7) {
                i = 0;
                String substring2 = "";
                int n2 = 0;
                while (i <= n) {
                    final int index = this._fenString.indexOf("/", n2 + 1);
                    if (i == n) {
                        substring2 = this._fenString.substring(n2, index);
                    }
                    n2 = index + 1;
                    ++i;
                }
                return substring2;
            }
            substring = substring;
            if (n == 7) {
                substring = this._fenString.substring(this._fenString.lastIndexOf("/") + 1, this._fenString.indexOf(" "));
            }
        }
        return substring;
    }
    
    @Override
    public int hashCode() {
        if (this._fenString != null) {
            return this._fenString.hashCode();
        }
        return 0;
    }
    
    public boolean isValid() {
        while (true) {
            while (true) {
                int n;
                synchronized (this) {
                    if (this._fenValidated) {
                        return this._isValid;
                    }
                    this._fenValidated = true;
                    if (this._fenString == null) {
                        return this._isValid = false;
                    }
                    final int index = this._fenString.indexOf(" ");
                    if (index < 0) {
                        return this._isValid = false;
                    }
                    Serializable substring = this._fenString.substring(0, index);
                    String string = "";
                    n = 0;
                    if (n >= ((String)substring).length()) {
                        substring = new StringBuilder();
                        ((StringBuilder)substring).append(string);
                        ((StringBuilder)substring).append(this._fenString.substring(index));
                        return this._isValid = ((StringBuilder)substring).toString().matches("[PNBRQKpnbrqk_]{8}(/[PNBRQKpnbrqk_]{8}){7}\\s(w|b)\\s(-|[KkQqHhAa]{1,4})\\s(-|([abcdefgh][36]))(\\s(\\d)+){2}");
                    }
                    final char char1 = this._fenString.charAt(n);
                    if (char1 >= '1' && char1 <= '8') {
                        for (char c = '\0'; c < char1 - '1' + '\u0001'; ++c) {
                            final StringBuilder sb = new StringBuilder();
                            sb.append(string);
                            sb.append("_");
                            string = sb.toString();
                        }
                    }
                    else {
                        final StringBuilder sb2 = new StringBuilder();
                        sb2.append(string);
                        sb2.append(char1);
                        sb2.toString();
                    }
                }
                ++n;
                continue;
            }
        }
    }
    
    public void removeEnPassantString() {
        if (this.isValid()) {
            Pattern.compile("\\s[abcdefgh][36]\\s").matcher(this._fenString).replaceFirst(" - ");
        }
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("FEN: ");
        sb.append(this._fenString);
        return sb.toString();
    }
}
