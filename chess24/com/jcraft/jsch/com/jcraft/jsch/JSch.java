/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

import com.jcraft.jsch.ConfigRepository;
import com.jcraft.jsch.HostKeyRepository;
import com.jcraft.jsch.Identity;
import com.jcraft.jsch.IdentityFile;
import com.jcraft.jsch.IdentityRepository;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.KeyPair;
import com.jcraft.jsch.KnownHosts;
import com.jcraft.jsch.LocalIdentityRepository;
import com.jcraft.jsch.Logger;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.Util;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public class JSch {
    private static final Logger DEVNULL;
    public static final String VERSION = "0.1.54";
    static Hashtable config;
    static Logger logger;
    private ConfigRepository configRepository = null;
    private IdentityRepository defaultIdentityRepository;
    private IdentityRepository identityRepository = this.defaultIdentityRepository = new LocalIdentityRepository(this);
    private HostKeyRepository known_hosts = null;
    private Vector sessionPool = new Vector();

    static {
        config = new Hashtable();
        config.put("kex", "ecdh-sha2-nistp256,ecdh-sha2-nistp384,ecdh-sha2-nistp521,diffie-hellman-group14-sha1,diffie-hellman-group-exchange-sha256,diffie-hellman-group-exchange-sha1,diffie-hellman-group1-sha1");
        config.put("server_host_key", "ssh-rsa,ssh-dss,ecdsa-sha2-nistp256,ecdsa-sha2-nistp384,ecdsa-sha2-nistp521");
        config.put("cipher.s2c", "aes128-ctr,aes128-cbc,3des-ctr,3des-cbc,blowfish-cbc,aes192-ctr,aes192-cbc,aes256-ctr,aes256-cbc");
        config.put("cipher.c2s", "aes128-ctr,aes128-cbc,3des-ctr,3des-cbc,blowfish-cbc,aes192-ctr,aes192-cbc,aes256-ctr,aes256-cbc");
        config.put("mac.s2c", "hmac-md5,hmac-sha1,hmac-sha2-256,hmac-sha1-96,hmac-md5-96");
        config.put("mac.c2s", "hmac-md5,hmac-sha1,hmac-sha2-256,hmac-sha1-96,hmac-md5-96");
        config.put("compression.s2c", "none");
        config.put("compression.c2s", "none");
        config.put("lang.s2c", "");
        config.put("lang.c2s", "");
        config.put("compression_level", "6");
        config.put("diffie-hellman-group-exchange-sha1", "com.jcraft.jsch.DHGEX");
        config.put("diffie-hellman-group1-sha1", "com.jcraft.jsch.DHG1");
        config.put("diffie-hellman-group14-sha1", "com.jcraft.jsch.DHG14");
        config.put("diffie-hellman-group-exchange-sha256", "com.jcraft.jsch.DHGEX256");
        config.put("ecdsa-sha2-nistp256", "com.jcraft.jsch.jce.SignatureECDSA");
        config.put("ecdsa-sha2-nistp384", "com.jcraft.jsch.jce.SignatureECDSA");
        config.put("ecdsa-sha2-nistp521", "com.jcraft.jsch.jce.SignatureECDSA");
        config.put("ecdh-sha2-nistp256", "com.jcraft.jsch.DHEC256");
        config.put("ecdh-sha2-nistp384", "com.jcraft.jsch.DHEC384");
        config.put("ecdh-sha2-nistp521", "com.jcraft.jsch.DHEC521");
        config.put("ecdh-sha2-nistp", "com.jcraft.jsch.jce.ECDHN");
        config.put("dh", "com.jcraft.jsch.jce.DH");
        config.put("3des-cbc", "com.jcraft.jsch.jce.TripleDESCBC");
        config.put("blowfish-cbc", "com.jcraft.jsch.jce.BlowfishCBC");
        config.put("hmac-sha1", "com.jcraft.jsch.jce.HMACSHA1");
        config.put("hmac-sha1-96", "com.jcraft.jsch.jce.HMACSHA196");
        config.put("hmac-sha2-256", "com.jcraft.jsch.jce.HMACSHA256");
        config.put("hmac-md5", "com.jcraft.jsch.jce.HMACMD5");
        config.put("hmac-md5-96", "com.jcraft.jsch.jce.HMACMD596");
        config.put("sha-1", "com.jcraft.jsch.jce.SHA1");
        config.put("sha-256", "com.jcraft.jsch.jce.SHA256");
        config.put("sha-384", "com.jcraft.jsch.jce.SHA384");
        config.put("sha-512", "com.jcraft.jsch.jce.SHA512");
        config.put("md5", "com.jcraft.jsch.jce.MD5");
        config.put("signature.dss", "com.jcraft.jsch.jce.SignatureDSA");
        config.put("signature.rsa", "com.jcraft.jsch.jce.SignatureRSA");
        config.put("signature.ecdsa", "com.jcraft.jsch.jce.SignatureECDSA");
        config.put("keypairgen.dsa", "com.jcraft.jsch.jce.KeyPairGenDSA");
        config.put("keypairgen.rsa", "com.jcraft.jsch.jce.KeyPairGenRSA");
        config.put("keypairgen.ecdsa", "com.jcraft.jsch.jce.KeyPairGenECDSA");
        config.put("random", "com.jcraft.jsch.jce.Random");
        config.put("none", "com.jcraft.jsch.CipherNone");
        config.put("aes128-cbc", "com.jcraft.jsch.jce.AES128CBC");
        config.put("aes192-cbc", "com.jcraft.jsch.jce.AES192CBC");
        config.put("aes256-cbc", "com.jcraft.jsch.jce.AES256CBC");
        config.put("aes128-ctr", "com.jcraft.jsch.jce.AES128CTR");
        config.put("aes192-ctr", "com.jcraft.jsch.jce.AES192CTR");
        config.put("aes256-ctr", "com.jcraft.jsch.jce.AES256CTR");
        config.put("3des-ctr", "com.jcraft.jsch.jce.TripleDESCTR");
        config.put("arcfour", "com.jcraft.jsch.jce.ARCFOUR");
        config.put("arcfour128", "com.jcraft.jsch.jce.ARCFOUR128");
        config.put("arcfour256", "com.jcraft.jsch.jce.ARCFOUR256");
        config.put("userauth.none", "com.jcraft.jsch.UserAuthNone");
        config.put("userauth.password", "com.jcraft.jsch.UserAuthPassword");
        config.put("userauth.keyboard-interactive", "com.jcraft.jsch.UserAuthKeyboardInteractive");
        config.put("userauth.publickey", "com.jcraft.jsch.UserAuthPublicKey");
        config.put("userauth.gssapi-with-mic", "com.jcraft.jsch.UserAuthGSSAPIWithMIC");
        config.put("gssapi-with-mic.krb5", "com.jcraft.jsch.jgss.GSSContextKrb5");
        config.put("zlib", "com.jcraft.jsch.jcraft.Compression");
        config.put("zlib@openssh.com", "com.jcraft.jsch.jcraft.Compression");
        config.put("pbkdf", "com.jcraft.jsch.jce.PBKDF");
        config.put("StrictHostKeyChecking", "ask");
        config.put("HashKnownHosts", "no");
        config.put("PreferredAuthentications", "gssapi-with-mic,publickey,keyboard-interactive,password");
        config.put("CheckCiphers", "aes256-ctr,aes192-ctr,aes128-ctr,aes256-cbc,aes192-cbc,aes128-cbc,3des-ctr,arcfour,arcfour128,arcfour256");
        config.put("CheckKexes", "diffie-hellman-group14-sha1,ecdh-sha2-nistp256,ecdh-sha2-nistp384,ecdh-sha2-nistp521");
        config.put("CheckSignatures", "ecdsa-sha2-nistp256,ecdsa-sha2-nistp384,ecdsa-sha2-nistp521");
        config.put("MaxAuthTries", "6");
        config.put("ClearAllForwardings", "no");
        logger = DEVNULL = new Logger(){

            @Override
            public boolean isEnabled(int n) {
                return false;
            }

            @Override
            public void log(int n, String string) {
            }
        };
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String getConfig(String string) {
        Hashtable hashtable = config;
        synchronized (hashtable) {
            return (String)config.get(string);
        }
    }

    static Logger getLogger() {
        return logger;
    }

    public static void setConfig(String string, String string2) {
        config.put(string, string2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void setConfig(Hashtable hashtable) {
        Hashtable hashtable2 = config;
        synchronized (hashtable2) {
            Enumeration enumeration = hashtable.keys();
            while (enumeration.hasMoreElements()) {
                String string = (String)enumeration.nextElement();
                config.put(string, (String)hashtable.get(string));
            }
            return;
        }
    }

    public static void setLogger(Logger logger) {
        Logger logger2 = logger;
        if (logger == null) {
            logger2 = DEVNULL;
        }
        JSch.logger = logger2;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void addIdentity(Identity arrby, byte[] object) throws JSchException {
        block12 : {
            if (object != null) {
                block11 : {
                    byte[] arrby2 = new byte[((Object)object).length];
                    System.arraycopy(object, 0, arrby2, 0, ((Object)object).length);
                    try {
                        arrby.setPassphrase(arrby2);
                    }
                    catch (Throwable throwable) {
                        arrby = arrby2;
                        break block11;
                    }
                    Util.bzero(arrby2);
                    break block12;
                    catch (Throwable throwable) {
                        arrby = object;
                        object = throwable;
                    }
                }
                Util.bzero((byte[])arrby);
                throw object;
            }
        }
        if (this.identityRepository instanceof LocalIdentityRepository) {
            ((LocalIdentityRepository)this.identityRepository).add((Identity)arrby);
            return;
        }
        if (arrby instanceof IdentityFile && !arrby.isEncrypted()) {
            this.identityRepository.add(((IdentityFile)arrby).getKeyPair().forSSHAgent());
            return;
        }
        // MONITORENTER : this
        if (!(this.identityRepository instanceof IdentityRepository.Wrapper)) {
            this.setIdentityRepository(new IdentityRepository.Wrapper(this.identityRepository));
        }
        // MONITOREXIT : this
        ((IdentityRepository.Wrapper)this.identityRepository).add((Identity)arrby);
    }

    public void addIdentity(String string) throws JSchException {
        this.addIdentity(string, (byte[])null);
    }

    public void addIdentity(String string, String object) throws JSchException {
        object = object != null ? Util.str2byte((String)object) : null;
        this.addIdentity(string, (byte[])object);
        if (object != null) {
            Util.bzero(object);
        }
    }

    public void addIdentity(String string, String string2, byte[] arrby) throws JSchException {
        this.addIdentity(IdentityFile.newInstance(string, string2, this), arrby);
    }

    public void addIdentity(String string, byte[] arrby) throws JSchException {
        this.addIdentity(IdentityFile.newInstance(string, null, this), arrby);
    }

    public void addIdentity(String string, byte[] arrby, byte[] arrby2, byte[] arrby3) throws JSchException {
        this.addIdentity(IdentityFile.newInstance(string, arrby, arrby2, this), arrby3);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void addSession(Session session) {
        Vector vector = this.sessionPool;
        synchronized (vector) {
            this.sessionPool.addElement(session);
            return;
        }
    }

    public ConfigRepository getConfigRepository() {
        return this.configRepository;
    }

    public HostKeyRepository getHostKeyRepository() {
        if (this.known_hosts == null) {
            this.known_hosts = new KnownHosts(this);
        }
        return this.known_hosts;
    }

    public Vector getIdentityNames() throws JSchException {
        Vector<String> vector = new Vector<String>();
        Vector vector2 = this.identityRepository.getIdentities();
        for (int i = 0; i < vector2.size(); ++i) {
            vector.addElement(((Identity)vector2.elementAt(i)).getName());
        }
        return vector;
    }

    public IdentityRepository getIdentityRepository() {
        synchronized (this) {
            IdentityRepository identityRepository = this.identityRepository;
            return identityRepository;
        }
    }

    public Session getSession(String string) throws JSchException {
        return this.getSession(null, string, 22);
    }

    public Session getSession(String string, String string2) throws JSchException {
        return this.getSession(string, string2, 22);
    }

    public Session getSession(String string, String string2, int n) throws JSchException {
        if (string2 == null) {
            throw new JSchException("host must not be null.");
        }
        return new Session(this, string, string2, n);
    }

    public void removeAllIdentity() throws JSchException {
        this.identityRepository.removeAll();
    }

    public void removeIdentity(Identity identity) throws JSchException {
        this.identityRepository.remove(identity.getPublicKeyBlob());
    }

    public void removeIdentity(String string) throws JSchException {
        Vector vector = this.identityRepository.getIdentities();
        for (int i = 0; i < vector.size(); ++i) {
            Identity identity = (Identity)vector.elementAt(i);
            if (!identity.getName().equals(string)) continue;
            if (this.identityRepository instanceof LocalIdentityRepository) {
                ((LocalIdentityRepository)this.identityRepository).remove(identity);
                continue;
            }
            this.identityRepository.remove(identity.getPublicKeyBlob());
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected boolean removeSession(Session session) {
        Vector vector = this.sessionPool;
        synchronized (vector) {
            return this.sessionPool.remove(session);
        }
    }

    public void setConfigRepository(ConfigRepository configRepository) {
        this.configRepository = configRepository;
    }

    public void setHostKeyRepository(HostKeyRepository hostKeyRepository) {
        this.known_hosts = hostKeyRepository;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void setIdentityRepository(IdentityRepository var1_1) {
        // MONITORENTER : this
        if (var1_1 != null) ** GOTO lbl6
        this.identityRepository = this.defaultIdentityRepository;
        return;
lbl6: // 1 sources:
        this.identityRepository = var1_1;
        // MONITOREXIT : this
        return;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setKnownHosts(InputStream inputStream) throws JSchException {
        if (this.known_hosts == null) {
            this.known_hosts = new KnownHosts(this);
        }
        if (this.known_hosts instanceof KnownHosts) {
            HostKeyRepository hostKeyRepository = this.known_hosts;
            synchronized (hostKeyRepository) {
                ((KnownHosts)this.known_hosts).setKnownHosts(inputStream);
                return;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setKnownHosts(String string) throws JSchException {
        if (this.known_hosts == null) {
            this.known_hosts = new KnownHosts(this);
        }
        if (this.known_hosts instanceof KnownHosts) {
            HostKeyRepository hostKeyRepository = this.known_hosts;
            synchronized (hostKeyRepository) {
                ((KnownHosts)this.known_hosts).setKnownHosts(string);
                return;
            }
        }
    }

}
