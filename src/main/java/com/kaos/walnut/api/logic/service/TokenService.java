package com.kaos.walnut.api.logic.service;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.regex.Pattern;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.kaos.walnut.api.data.cache.KaosUserCache;
import com.kaos.walnut.core.frame.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Token业务
 */
@Service
public class TokenService {
    /**
     * 免校验token前缀
     */
    static final String specialPrefix = "walnut-";

    /**
     * 秘钥
     */
    static final String secret = "walnut.auth.net";

    /**
     * 用户信息缓存
     */
    @Autowired
    KaosUserCache kaosUserCache;

    /**
     * 生成token
     * 
     * @param uid
     * @return
     */
    public String genToken(String uid) {
        var builder = JWT.create();
        builder.withClaim("uid", uid);
        builder.withExpiresAt(OffsetDateTime.now().plus(Duration.ofHours(1)).toInstant());
        return builder.sign(Algorithm.HMAC256(secret));
    }

    /**
     * 分析token
     * 
     * @return
     */
    public User parseToken(String token) throws Exception {
        // 根据是否为特殊token解码出UID
        if (token.startsWith(specialPrefix)) {
            return this.parseSpecialToken(token);
        } else {
            return this.parseNormalToken(token);
        }
    }

    /**
     * 处理特殊token
     * 
     * @param uid
     * @return
     * @throws Exception
     */
    private User parseSpecialToken(String token) throws Exception {
        // 提取uid
        var pattern = Pattern.compile(specialPrefix + "(.*)");
        var matcher = pattern.matcher(token);
        if (!matcher.find()) {
            throw new RuntimeException("token中未发现用户编码");
        }
        String uid = matcher.group(1);

        return new User(uid, uid);
    }

    /**
     * 处理特殊token
     * 
     * @param uid
     * @return
     * @throws Exception
     */
    private User parseNormalToken(String token) throws Exception {
        // 构造校验器
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret)).build();

        // token校验
        DecodedJWT decodedJWT = jwtVerifier.verify(token);

        // 读取user
        var uid = decodedJWT.getClaim("uid").asString();
        var user = this.kaosUserCache.get(uid);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        return new User(user.getUserCode(), user.getUserName());
    }
}
