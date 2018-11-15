/*
 * Decompiled with CFR 0_134.
 */
package io.socket;

class IOMessage {
    public static final int FIELD_DATA = 3;
    public static final int FIELD_ENDPOINT = 2;
    public static final int FIELD_ID = 1;
    public static final int FIELD_TYPE = 0;
    public static final int NUM_FIELDS = 4;
    public static final int TYPE_ACK = 6;
    public static final int TYPE_CONNECT = 1;
    public static final int TYPE_DISCONNECT = 0;
    public static final int TYPE_ERROR = 7;
    public static final int TYPE_EVENT = 5;
    public static final int TYPE_HEARTBEAT = 2;
    public static final int TYPE_JSON_MESSAGE = 4;
    public static final int TYPE_MESSAGE = 3;
    public static final int TYPE_NOOP = 8;
    private final String[] fields = new String[4];
    private int type;

    public IOMessage(int n, String string, String string2) {
        this(n, null, string, string2);
    }

    public IOMessage(int n, String arrstring, String string, String string2) {
        this.type = n;
        this.fields[1] = arrstring;
        arrstring = this.fields;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(n);
        arrstring[0] = stringBuilder.toString();
        this.fields[2] = string;
        this.fields[3] = string2;
    }

    public IOMessage(String arrstring) {
        arrstring = arrstring.split(":", 4);
        for (int i = 0; i < arrstring.length; ++i) {
            this.fields[i] = arrstring[i];
            if (i != 0) continue;
            this.type = Integer.parseInt(arrstring[i]);
        }
    }

    public String getData() {
        return this.fields[3];
    }

    public String getEndpoint() {
        return this.fields[2];
    }

    public String getId() {
        return this.fields[1];
    }

    public int getType() {
        return this.type;
    }

    public void setId(String string) {
        this.fields[1] = string;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < this.fields.length; ++i) {
            stringBuilder.append(':');
            if (this.fields[i] == null) continue;
            stringBuilder.append(this.fields[i]);
        }
        return stringBuilder.substring(1);
    }
}
