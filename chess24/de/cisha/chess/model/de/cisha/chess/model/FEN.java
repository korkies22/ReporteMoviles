/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.model;

import de.cisha.chess.util.Logger;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FEN
implements Serializable {
    public static final char ACTIVE_COLOR_BLACK = 'b';
    public static final char ACTIVE_COLOR_WHITE = 'w';
    public static final FEN EMPTY_POSITION;
    private static final String REGEX_FEN = "[PNBRQKpnbrqk_]{8}(/[PNBRQKpnbrqk_]{8}){7}\\s(w|b)\\s(-|[KkQqHhAa]{1,4})\\s(-|([abcdefgh][36]))(\\s(\\d)+){2}";
    public static final FEN STARTING_POSITION;
    private static final long serialVersionUID = 3431658779297237108L;
    private String _fenString;
    private boolean _fenValidated = false;
    private boolean _isValid = false;

    static {
        STARTING_POSITION = new FEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w HAha - 0 1");
        EMPTY_POSITION = new FEN("8/8/8/8/8/8/8/8 w KQkq - 0 1");
    }

    public FEN() {
        this._fenString = "8/8/8/8/8/8/8/8 w - - 0 1";
    }

    public FEN(String string) {
        this._fenString = string;
    }

    public boolean equals(Object object) {
        if (this._fenString != null && object != null && object instanceof FEN) {
            return this._fenString.equals(((FEN)object).getFENString());
        }
        return false;
    }

    public char getActiveColorChar() {
        if (this.isValid()) {
            int n = this._fenString.indexOf(32);
            int n2 = this._fenString.length();
            if (n2 > ++n) {
                return this._fenString.charAt(n);
            }
        }
        return 'w';
    }

    public boolean getActiveColorGuess() {
        int n = this._fenString.indexOf(32);
        int n2 = this._fenString.length();
        n2 = n2 > ++n ? (int)this._fenString.charAt(n) : 119;
        if (119 == n2) {
            return true;
        }
        return false;
    }

    public String getCastlingString() {
        String string;
        String string2 = string = "-";
        if (this.isValid()) {
            int n = this._fenString.indexOf(32);
            n = this._fenString.indexOf(32, n + 1);
            string2 = this._fenString;
            int n2 = string2.indexOf(32, ++n);
            string2 = string;
            if (n2 != -1) {
                string2 = this._fenString.substring(n, n2);
            }
        }
        return string2;
    }

    public String getEnPassantString() {
        String string;
        String string2 = string = "-";
        if (this.isValid()) {
            int n = this._fenString.indexOf(32);
            n = this._fenString.indexOf(32, n + 1);
            n = this._fenString.indexOf(32, n + 1);
            string2 = this._fenString;
            int n2 = string2.indexOf(32, ++n);
            string2 = string;
            if (n2 != -1) {
                string2 = this._fenString.substring(n, n2);
            }
        }
        return string2;
    }

    public String getFENString() {
        return this._fenString;
    }

    public int getMoveNumber() {
        if (this.isValid()) {
            int n = this._fenString.indexOf(32);
            n = this._fenString.indexOf(32, n + 1);
            n = this._fenString.indexOf(32, n + 1);
            n = this._fenString.indexOf(32, n + 1);
            n = this._fenString.indexOf(32, n + 1);
            if (this._fenString.length() > n) {
                String string = this._fenString.substring(n + 1);
                DecimalFormat decimalFormat = new DecimalFormat();
                try {
                    n = decimalFormat.parse(string).intValue();
                    return n;
                }
                catch (ParseException parseException) {
                    Logger.getInstance().debug("FEN", "wrong FEN", parseException);
                }
            }
        }
        return 1;
    }

    public int getNoActionNumber() {
        if (this.isValid()) {
            int n;
            int n2 = this._fenString.indexOf(32);
            n2 = this._fenString.indexOf(32, n2 + 1);
            n2 = this._fenString.indexOf(32, n2 + 1);
            n2 = this._fenString.indexOf(32, n2 + 1);
            String string = this._fenString;
            if ((n = string.indexOf(32, ++n2)) != -1) {
                string = this._fenString.substring(n2, n);
                DecimalFormat decimalFormat = new DecimalFormat();
                try {
                    n2 = decimalFormat.parse(string).intValue();
                    return n2;
                }
                catch (ParseException parseException) {
                    Logger.getInstance().debug("FEN", "wrong FEN", parseException);
                }
            }
        }
        return 0;
    }

    public String getPiecePlacmentString() {
        String string = "8/8/8/8/8/8/8/8";
        if (this.isValid()) {
            string = this._fenString.substring(0, this._fenString.indexOf(" "));
        }
        return string;
    }

    public String getRank(int n) {
        String string;
        int n2 = 8 - n;
        String string2 = string = "";
        if (this.isValid()) {
            if (n2 >= 0 && n2 < 7) {
                string2 = "";
                int n3 = 0;
                for (n = 0; n <= n2; ++n) {
                    int n4 = this._fenString.indexOf("/", n3 + 1);
                    if (n == n2) {
                        string2 = this._fenString.substring(n3, n4);
                    }
                    n3 = n4 + 1;
                }
                return string2;
            }
            string2 = string;
            if (n2 == 7) {
                string2 = this._fenString.substring(this._fenString.lastIndexOf("/") + 1, this._fenString.indexOf(" "));
            }
        }
        return string2;
    }

    public int hashCode() {
        if (this._fenString != null) {
            return this._fenString.hashCode();
        }
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isValid() {
        synchronized (this) {
            if (this._fenValidated) {
                return this._isValid;
            }
            this._fenValidated = true;
            if (this._fenString == null) {
                this._isValid = false;
                return this._isValid;
            }
            int n = this._fenString.indexOf(" ");
            if (n < 0) {
                this._isValid = false;
                return false;
            }
            CharSequence charSequence = this._fenString.substring(0, n);
            String string = "";
            int n2 = 0;
            do {
                StringBuilder stringBuilder;
                if (n2 >= charSequence.length()) {
                    charSequence = new StringBuilder();
                    charSequence.append(string);
                    charSequence.append(this._fenString.substring(n));
                    this._isValid = charSequence.toString().matches(REGEX_FEN);
                    return this._isValid;
                }
                char c = this._fenString.charAt(n2);
                if (c >= '1' && c <= '8') {
                    for (int i = 0; i < c - 49 + 1; ++i) {
                        stringBuilder = new StringBuilder();
                        stringBuilder.append(string);
                        stringBuilder.append("_");
                        string = stringBuilder.toString();
                    }
                } else {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(string);
                    stringBuilder.append(c);
                    string = stringBuilder.toString();
                }
                ++n2;
            } while (true);
        }
    }

    public void removeEnPassantString() {
        if (this.isValid()) {
            Pattern.compile("\\s[abcdefgh][36]\\s").matcher(this._fenString).replaceFirst(" - ");
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("FEN: ");
        stringBuilder.append(this._fenString);
        return stringBuilder.toString();
    }
}
