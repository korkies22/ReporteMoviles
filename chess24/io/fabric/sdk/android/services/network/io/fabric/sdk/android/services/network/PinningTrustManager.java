/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android.services.network;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.network.CertificateChainCleaner;
import io.fabric.sdk.android.services.network.PinningInfoProvider;
import io.fabric.sdk.android.services.network.SystemKeyStore;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

class PinningTrustManager
implements X509TrustManager {
    private static final X509Certificate[] NO_ISSUERS = new X509Certificate[0];
    private static final long PIN_FRESHNESS_DURATION_MILLIS = 15552000000L;
    private final Set<X509Certificate> cache = Collections.synchronizedSet(new HashSet());
    private final long pinCreationTimeMillis;
    private final List<byte[]> pins = new LinkedList<byte[]>();
    private final SystemKeyStore systemKeyStore;
    private final TrustManager[] systemTrustManagers;

    public PinningTrustManager(SystemKeyStore arrstring, PinningInfoProvider object2) {
        this.systemTrustManagers = this.initializeSystemTrustManagers((SystemKeyStore)arrstring);
        this.systemKeyStore = arrstring;
        this.pinCreationTimeMillis = object2.getPinCreationTimeInMillis();
        for (String string : object2.getPins()) {
            this.pins.add(this.hexStringToByteArray(string));
        }
    }

    private void checkPinTrust(X509Certificate[] object) throws CertificateException {
        if (this.pinCreationTimeMillis != -1L && System.currentTimeMillis() - this.pinCreationTimeMillis > 15552000000L) {
            object = Fabric.getLogger();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Certificate pins are stale, (");
            stringBuilder.append(System.currentTimeMillis() - this.pinCreationTimeMillis);
            stringBuilder.append(" millis vs ");
            stringBuilder.append(15552000000L);
            stringBuilder.append(" millis) falling back to system trust.");
            object.w("Fabric", stringBuilder.toString());
            return;
        }
        object = CertificateChainCleaner.getCleanChain((X509Certificate[])object, this.systemKeyStore);
        int n = ((X509Certificate[])object).length;
        for (int i = 0; i < n; ++i) {
            if (!this.isValidPin(object[i])) continue;
            return;
        }
        throw new CertificateException("No valid pins found in chain!");
    }

    private void checkSystemTrust(X509Certificate[] arrx509Certificate, String string) throws CertificateException {
        TrustManager[] arrtrustManager = this.systemTrustManagers;
        int n = arrtrustManager.length;
        for (int i = 0; i < n; ++i) {
            ((X509TrustManager)arrtrustManager[i]).checkServerTrusted(arrx509Certificate, string);
        }
    }

    private byte[] hexStringToByteArray(String string) {
        int n = string.length();
        byte[] arrby = new byte[n / 2];
        for (int i = 0; i < n; i += 2) {
            arrby[i / 2] = (byte)((Character.digit(string.charAt(i), 16) << 4) + Character.digit(string.charAt(i + 1), 16));
        }
        return arrby;
    }

    private TrustManager[] initializeSystemTrustManagers(SystemKeyStore arrtrustManager) {
        try {
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("X509");
            trustManagerFactory.init(arrtrustManager.trustStore);
            arrtrustManager = trustManagerFactory.getTrustManagers();
            return arrtrustManager;
        }
        catch (KeyStoreException keyStoreException) {
            throw new AssertionError(keyStoreException);
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            throw new AssertionError(noSuchAlgorithmException);
        }
    }

    private boolean isValidPin(X509Certificate arrby) throws CertificateException {
        try {
            arrby = MessageDigest.getInstance("SHA1").digest(arrby.getPublicKey().getEncoded());
            Iterator<byte[]> iterator = this.pins.iterator();
            while (iterator.hasNext()) {
                boolean bl = Arrays.equals(iterator.next(), arrby);
                if (!bl) continue;
                return true;
            }
            return false;
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            throw new CertificateException(noSuchAlgorithmException);
        }
    }

    @Override
    public void checkClientTrusted(X509Certificate[] arrx509Certificate, String string) throws CertificateException {
        throw new CertificateException("Client certificates not supported!");
    }

    @Override
    public void checkServerTrusted(X509Certificate[] arrx509Certificate, String string) throws CertificateException {
        if (this.cache.contains(arrx509Certificate[0])) {
            return;
        }
        this.checkSystemTrust(arrx509Certificate, string);
        this.checkPinTrust(arrx509Certificate);
        this.cache.add(arrx509Certificate[0]);
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return NO_ISSUERS;
    }
}
