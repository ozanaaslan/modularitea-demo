package com.github.ozanaaslan.librespeak.util;

import lombok.SneakyThrows;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {

    private static String hash(String hashType, String inputHash) throws NoSuchAlgorithmException, IOException {
        MessageDigest messageDigest = MessageDigest.getInstance(hashType);
        messageDigest.update(inputHash.getBytes());

        byte[] digest = messageDigest.digest();
        StringBuffer stringBuffer = new StringBuffer();
        for (byte byt : digest)
            stringBuffer.append(String.format("%02x", byt & 0xff));
        return stringBuffer.toString();
    }

    @SneakyThrows
    public static String md5(String input){return hash("MD5", input);}
    @SneakyThrows public static String sha1(String input){return hash("SHA-1", input);}
    @SneakyThrows public static String sha256(String input){return hash("SHA-256", input);}
    @SneakyThrows public static String sha384(String input){return hash("SHA-384", input);}
    @SneakyThrows public static String sha512(String input){return hash("SHA-512", input);}
}
