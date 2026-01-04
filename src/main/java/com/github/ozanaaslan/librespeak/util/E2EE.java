package com.github.ozanaaslan.librespeak.util;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

public class E2EE {
    private KeyPair keyPair;
    private String publicKey;
    private String privateKey;

    public E2EE() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        keyPair = keyGen.generateKeyPair();
        publicKey = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
        privateKey = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());
    }

    public String getPublicKey() {
        return publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public byte[] encrypt(String publicKey, byte[] message) throws Exception {
        // Decode the public key from base64
        byte[] decodedPublicKey = Base64.getDecoder().decode(publicKey);
        PublicKey key = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decodedPublicKey));

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(message);
    }

    public byte[] decrypt(byte[] message) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
        return cipher.doFinal(message);
    }

    public String sign(String data) throws Exception {
        Signature privateSignature = Signature.getInstance("SHA256withRSA");
        privateSignature.initSign(keyPair.getPrivate());
        privateSignature.update(data.getBytes());
        byte[] signature = privateSignature.sign();
        return Base64.getEncoder().encodeToString(signature);
    }

    public boolean verify(String data, String signature, String publicKeyStr) throws Exception {
        byte[] decodedKey = Base64.getDecoder().decode(publicKeyStr);
        PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decodedKey));

        Signature publicSignature = Signature.getInstance("SHA256withRSA");
        publicSignature.initVerify(publicKey);
        publicSignature.update(data.getBytes());
        byte[] signatureBytes = Base64.getDecoder().decode(signature);
        return publicSignature.verify(signatureBytes);
    }

    public E2EE(String pubBase64, String privBase64) throws Exception {
        this.publicKey = pubBase64;
        this.privateKey = privBase64;

        // We need to reconstruct the KeyPair objects for signing/decrypting
        KeyFactory kf = KeyFactory.getInstance("RSA");

        byte[] pubBytes = Base64.getDecoder().decode(pubBase64);
        PublicKey pub = kf.generatePublic(new X509EncodedKeySpec(pubBytes));

        byte[] privBytes = Base64.getDecoder().decode(privBase64);
        PrivateKey priv = kf.generatePrivate(new PKCS8EncodedKeySpec(privBytes));

        this.keyPair = new KeyPair(pub, priv);
    }
}