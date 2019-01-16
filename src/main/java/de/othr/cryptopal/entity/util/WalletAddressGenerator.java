package de.othr.cryptopal.entity.util;

import java.util.Random;

public class WalletAddressGenerator {

    private static final int walletAddressLengh = 10;

    public static String generate() {
        return "0x" + getRandomHexString(walletAddressLengh);
    }

    private static String getRandomHexString(int numchars){
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        while(sb.length() < numchars){
            sb.append(Integer.toHexString(r.nextInt()));
        }

        return sb.toString().substring(0, numchars);
    }
}
