package com.xihua.easyctlserver.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Map;

public class JWTUtil {

    public static String generateToken(Map<String, String> map, String secret, int availableHours) {
        JWTCreator.Builder builder = JWT.create();

        //payload
        map.forEach(builder::withClaim);

        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.HOUR, availableHours);

        builder.withExpiresAt(instance.getTime());
        return builder.sign(Algorithm.HMAC256(secret));
    }

    public static DecodedJWT verify(String token, String secret) {
        return JWT.require(Algorithm.HMAC256(secret)).build().verify(token);
    }

    public static Map<String, Claim> getTokenInfo(String token, String secret) {
        return JWT.require(Algorithm.HMAC256(secret)).build().verify(token).getClaims();
    }
}