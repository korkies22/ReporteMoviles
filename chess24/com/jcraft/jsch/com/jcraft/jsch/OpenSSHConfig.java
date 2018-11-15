/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

import com.jcraft.jsch.ConfigRepository;
import com.jcraft.jsch.Util;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Hashtable;
import java.util.Vector;

public class OpenSSHConfig
implements ConfigRepository {
    private static final Hashtable keymap = new Hashtable();
    private final Hashtable config = new Hashtable();
    private final Vector hosts = new Vector();

    static {
        keymap.put("kex", "KexAlgorithms");
        keymap.put("server_host_key", "HostKeyAlgorithms");
        keymap.put("cipher.c2s", "Ciphers");
        keymap.put("cipher.s2c", "Ciphers");
        keymap.put("mac.c2s", "Macs");
        keymap.put("mac.s2c", "Macs");
        keymap.put("compression.s2c", "Compression");
        keymap.put("compression.c2s", "Compression");
        keymap.put("compression_level", "CompressionLevel");
        keymap.put("MaxAuthTries", "NumberOfPasswordPrompts");
    }

    OpenSSHConfig(Reader reader) throws IOException {
        this._parse(reader);
    }

    private void _parse(Reader vector) throws IOException {
        String[] arrstring;
        BufferedReader bufferedReader = new BufferedReader((Reader)((Object)vector));
        String string = "";
        vector = new Vector();
        while ((arrstring = bufferedReader.readLine()) != null) {
            if ((arrstring = arrstring.trim()).length() == 0 || arrstring.startsWith("#")) continue;
            arrstring = arrstring.split("[= \t]", 2);
            for (int i = 0; i < arrstring.length; ++i) {
                arrstring[i] = arrstring[i].trim();
            }
            if (arrstring.length <= 1) continue;
            if (arrstring[0].equals("Host")) {
                this.config.put(string, vector);
                this.hosts.addElement(string);
                string = arrstring[1];
                vector = new Vector<String[]>();
                continue;
            }
            vector.addElement(arrstring);
        }
        this.config.put(string, vector);
        this.hosts.addElement(string);
    }

    public static OpenSSHConfig parse(String object) throws IOException {
        object = new StringReader((String)object);
        try {
            OpenSSHConfig openSSHConfig = new OpenSSHConfig((Reader)object);
            return openSSHConfig;
        }
        finally {
            object.close();
        }
    }

    public static OpenSSHConfig parseFile(String object) throws IOException {
        object = new FileReader(Util.checkTilde((String)object));
        try {
            OpenSSHConfig openSSHConfig = new OpenSSHConfig((Reader)object);
            return openSSHConfig;
        }
        finally {
            object.close();
        }
    }

    @Override
    public ConfigRepository.Config getConfig(String string) {
        return new MyConfig(string);
    }

    class MyConfig
    implements ConfigRepository.Config {
        private Vector _configs = new Vector();
        private String host;

        MyConfig(String string) {
            this.host = string;
            this._configs.addElement(OpenSSHConfig.this.config.get(""));
            byte[] arrby = Util.str2byte(string);
            if (OpenSSHConfig.this.hosts.size() > 1) {
                for (int i = 1; i < OpenSSHConfig.this.hosts.size(); ++i) {
                    String[] arrstring = ((String)OpenSSHConfig.this.hosts.elementAt(i)).split("[ \t]");
                    for (int j = 0; j < arrstring.length; ++j) {
                        boolean bl;
                        string = arrstring[j].trim();
                        if (string.startsWith("!")) {
                            string = string.substring(1).trim();
                            bl = true;
                        } else {
                            bl = false;
                        }
                        if (Util.glob(Util.str2byte(string), arrby)) {
                            if (bl) continue;
                            this._configs.addElement(OpenSSHConfig.this.config.get((String)OpenSSHConfig.this.hosts.elementAt(i)));
                            continue;
                        }
                        if (!bl) continue;
                        this._configs.addElement(OpenSSHConfig.this.config.get((String)OpenSSHConfig.this.hosts.elementAt(i)));
                    }
                }
            }
        }

        private String find(String arrstring) {
            Object object = arrstring;
            if (keymap.get(arrstring) != null) {
                object = (String)keymap.get(arrstring);
            }
            String string = object.toUpperCase();
            arrstring = null;
            for (int i = 0; i < this._configs.size(); ++i) {
                Vector vector = (Vector)this._configs.elementAt(i);
                int n = 0;
                do {
                    object = arrstring;
                    if (n >= vector.size()) break;
                    object = (String[])vector.elementAt(n);
                    if (object[0].toUpperCase().equals(string)) {
                        object = object[1];
                        break;
                    }
                    ++n;
                } while (true);
                if (object != null) {
                    return object;
                }
                arrstring = object;
            }
            return arrstring;
        }

        private String[] multiFind(String object) {
            String[] arrstring = object.toUpperCase();
            object = new Vector();
            for (int i = 0; i < this._configs.size(); ++i) {
                Vector vector = (Vector)this._configs.elementAt(i);
                for (int j = 0; j < vector.size(); ++j) {
                    Object object2 = (String[])vector.elementAt(j);
                    if (!object2[0].toUpperCase().equals(arrstring) || (object2 = object2[1]) == null) continue;
                    object.remove(object2);
                    object.addElement(object2);
                }
            }
            arrstring = new String[object.size()];
            object.toArray(arrstring);
            return arrstring;
        }

        @Override
        public String getHostname() {
            return this.find("Hostname");
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public int getPort() {
            String string = this.find("Port");
            try {
                return Integer.parseInt(string);
            }
            catch (NumberFormatException numberFormatException) {
                return -1;
            }
        }

        @Override
        public String getUser() {
            return this.find("User");
        }

        @Override
        public String getValue(String string) {
            if (!string.equals("compression.s2c") && !string.equals("compression.c2s")) {
                return this.find(string);
            }
            if ((string = this.find(string)) != null && !string.equals("no")) {
                return "zlib@openssh.com,zlib,none";
            }
            return "none,zlib@openssh.com,zlib";
        }

        @Override
        public String[] getValues(String string) {
            return this.multiFind(string);
        }
    }

}
