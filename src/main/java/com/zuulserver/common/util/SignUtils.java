package com.zuulserver.common.util;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.zuulserver.model.constant.AuthConstant;
import com.zuulserver.model.vo.TokenVO;

import java.util.Date;
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
     * 加密算法map缓存
     */
    private static Map<String, Algorithm> algorithmMap = new ConcurrentHashMap<>();

    /**
     * 验证算法map缓存
     */
    private static Map<String, com.auth0.jwt.JWTVerifier> verifierMap = new ConcurrentHashMap<>();

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

    /**
     * 生成token
     *
     * @param tokenVO
     * @param secret
     * @param duration 有效期
     * @return
     */
    public static String generateToken(TokenVO tokenVO, String secret, long duration) {
        Algorithm algorithm = SignUtils.getAlgorithm(secret);
        String token = JWT.create()
                .withClaim(AuthConstant.MEMBER_ID, tokenVO.getMemberId())
                .withClaim(AuthConstant.TOKEN_VO, JSON.toJSONString(tokenVO))
                .withExpiresAt(new Date(System.currentTimeMillis() + duration))
                .sign(algorithm);
        return token;
    }

}

