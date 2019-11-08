package com.zuulserver.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * jwt认证工具类
 *
 * @author f
 * @date 2019-11-07
 */
public class SignUtils {

    /**
     * jwt密钥
     */
    public static final String signingSecret = "SIGNING_SECRET";

    /**
     * 加密算法map缓存
     */
    private static Map<String, Algorithm> algorithmMap = new ConcurrentHashMap<>();

    /**
     * 验证算法map缓存
     */
    private static Map<String, JWTVerifier> verifierMap = new HashMap<>();

    /**
     * 根据密钥获得加密算法
     *
     * @param secret
     * @return
     */
    private static Algorithm getAlgorithm(String secret) {
        Algorithm algorithm = algorithmMap.get(secret);
        if (algorithm == null) {
            algorithm = Algorithm.HMAC512(secret);
            algorithmMap.put(secret, algorithm);
        }
        return algorithm;
    }

    /**
     * 验证token
     *
     * @param token
     * @param secret
     * @return
     */
    public static DecodedJWT verifyToken(String token, String secret) {
        JWTVerifier verifier = verifierMap.get(secret);
        if (verifier == null) {
            Algorithm algorithm = Algorithm.HMAC512(secret);
            verifier = JWT.require(algorithm).build();
            verifierMap.put(secret, verifier);
        }
        DecodedJWT jwt = verifier.verify(token);
        return jwt;
    }

}
