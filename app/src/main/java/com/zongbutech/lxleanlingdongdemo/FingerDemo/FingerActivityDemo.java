package com.zongbutech.lxleanlingdongdemo.FingerDemo;

import android.os.Build;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v4.os.CancellationSignal;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.zongbutech.lxleanlingdongdemo.R;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class FingerActivityDemo extends AppCompatActivity {
    FingerprintManagerCompat fingerprintManager;
    KeyStore mKeyStore;
    KeyGenerator mKeyGenerator;
    public String defaultKeyName = "com.zongbutech.lxleanlingdongdemo.FingerDemo";

    SecretKey key;
    Cipher defaultCipher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger_demo);

        fingerprintManager = FingerprintManagerCompat.from(this);


        try {
            mKeyStore = KeyStore.getInstance("AndroidKeyStore");
        } catch (KeyStoreException e) {
            throw new RuntimeException("Failed to get an instance of KeyStore", e);
        }
        // 对称加密， 创建 KeyGenerator 对象
        try {
            mKeyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            throw new RuntimeException("Failed to get an instance of KeyGenerator", e);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                mKeyStore.load(null);
                KeyGenParameterSpec.Builder builder = new KeyGenParameterSpec.Builder(defaultKeyName,
                        KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                        .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                        .setUserAuthenticationRequired(true)
                        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    builder.setInvalidatedByBiometricEnrollment(true);
                }
                mKeyGenerator.init(builder.build());
                key = mKeyGenerator.generateKey();

            } catch (CertificateException | NoSuchAlgorithmException | IOException | InvalidAlgorithmParameterException e) {
                e.printStackTrace();
            }
        }


        try {
            defaultCipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/"
                    + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);

            defaultCipher.init(Cipher.ENCRYPT_MODE | Cipher.DECRYPT_MODE, key);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
            throw new RuntimeException("创建Cipher对象失败", e);
        }

    }

    public class MyAuthCallback extends FingerprintManagerCompat.AuthenticationCallback {
        @Override
        public void onAuthenticationError(int errMsgId, CharSequence errString) {
            super.onAuthenticationError(errMsgId, errString);
        }

        @Override
        public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
            super.onAuthenticationHelp(helpMsgId, helpString);
        }

        @Override
        public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
            super.onAuthenticationSucceeded(result);
            boolean isKey = false;
            try {
                result.getCryptoObject().getCipher().doFinal(key.getEncoded());
                isKey = true;
            } catch (Exception e) {
                e.printStackTrace();
                isKey = false;
            }

            Log.e("", "isKey");
        }

        @Override
        public void onAuthenticationFailed() {
            super.onAuthenticationFailed();
        }

    }

    CancellationSignal mCancellationSignal;

    public void Start(View v) {
        mCancellationSignal = new CancellationSignal();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            fingerprintManager.authenticate(
                    new FingerprintManagerCompat.CryptoObject(defaultCipher),
                    0,
                    mCancellationSignal,
                    new MyAuthCallback(),
                    null);
        }
    }

    public void Close(View v) {
        mCancellationSignal.cancel();
        mCancellationSignal = null;
    }
}
