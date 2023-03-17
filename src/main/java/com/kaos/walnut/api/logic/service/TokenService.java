package com.kaos.walnut.api.logic.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.regex.Pattern;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.kaos.walnut.api.data.cache.KaosUserCache;
import com.kaos.walnut.core.frame.entity.User;

import org.apache.commons.math3.util.Pair;
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
        builder.withExpiresAt(OffsetDateTime.now().plus(Duration.ofMinutes(4)).toInstant());
        return builder.sign(Algorithm.HMAC256(secret));
    }

    /**
     * 分析token
     * 
     * @return
     */
    public Pair<User, String> parseToken(String token) throws Exception {
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
    private Pair<User, String> parseSpecialToken(String token) throws Exception {
        // 提取uid
        var pattern = Pattern.compile(specialPrefix + "(.*)");
        var matcher = pattern.matcher(token);
        if (!matcher.find()) {
            throw new RuntimeException("token中未发现用户编码");
        }
        String uid = matcher.group(1);

        return new Pair<User, String>(new User(uid, uid), null);
    }

    /**
     * 处理特殊token
     * 
     * @param uid
     * @return
     * @throws Exception
     */
    private Pair<User, String> parseNormalToken(String token) throws Exception {
        // token解码
        DecodedJWT decodedJWT = JWT.decode(token);

        // token校验
        String newToken = null;
        try {
            // 校验token
            decodedJWT = JWT.require(Algorithm.HMAC256(secret)).build().verify(decodedJWT);

            // 读取uid
            var user = this.kaosUserCache.get(decodedJWT.getClaim("uid").asString());
            if (user == null) {
                throw new RuntimeException("用户不存在");
            }

            return new Pair<>(new User(user.getUserCode(), user.getUserName()), null);
        } catch (TokenExpiredException e) {
            // token过期处理
            var expireOn = LocalDateTime.ofInstant(e.getExpiredOn(), ZoneId.systemDefault());

            // 若超时超过6分钟
            if (expireOn.plus(Duration.ofMinutes(6)).isBefore(LocalDateTime.now())) {
                throw e;
            }

            // 读取uid
            var uid = decodedJWT.getClaim("uid").asString();

            // 读取uid
            var user = this.kaosUserCache.get(uid);
            if (user == null) {
                throw new RuntimeException("用户不存在");
            }

            // 分配新token
            newToken = this.genToken(uid);

            return new Pair<>(new User(user.getUserCode(), user.getUserName()), newToken);
        } catch (Exception e) {
            throw new RuntimeException("token校验失败");
        }
    }
}
