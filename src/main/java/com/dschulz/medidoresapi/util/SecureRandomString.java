package com.dschulz.medidoresapi.util;

import java.security.SecureRandom;
import java.util.Base64;

public class SecureRandomString {
    private static final SecureRandom random = new SecureRandom();
    private static final Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();
    

    public static String generate() {
       return generate(10);
    }
    
    public static String generate(int length) {
    	
    	assert(length>1) : "length must be > 1";
    	
        byte[] buffer = new byte[length];
        random.nextBytes(buffer);
        
        
        return encoder.encodeToString(buffer).replaceAll("_", "x").replaceAll("-", "Z");
    }
}