package com.kaos.walnut.api.logic.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.regex.Pattern;

import javax.validation.constraints.NotNull;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.kaos.walnut.api.data.entity.KaosUser;
import com.kaos.walnut.api.data.entity.KaosUserAccess;
import com.kaos.walnut.api.data.mapper.KaosUserAccessMapper;
import com.kaos.walnut.api.data.mapper.KaosUserMapper;
import com.kaos.walnut.core.frame.entity.User;
import com.kaos.walnut.core.type.exceptions.TokenExpireException;

import org.apache.commons.math3.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Token业务
 */
@Service
public class TokenService {
    /**
     * 秘钥前缀
     */
    static final String specialPrefix = "walnut-";

    /**
     * 默认时区
     */
    static final ZoneId defaultZoneId = ZoneId.systemDefault();

    /**
     * token安全时段 - token生成后这个时段内时，认为是安全的
     */
    static final Duration safeDuration = Duration.ofHours(12);

    /**
     * token预警时段 - 预留该时段用于刷新token
     */
    static final Duration alertDuration = Duration.ofHours(12);

    /**
     * 用户接口
     */
    @Autowired
    KaosUserMapper kaosUserMapper;

    /**
     * 用户接口
     */
    @Autowired
    KaosUserAccessMapper kaosUserAccessMapper;

    /**
     * 生成token
     * 
     * @param uid
     * @return
     */
    public String genToken(String uid) {
        // 定位用户实体
        var user = kaosUserMapper.selectById(uid);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        // 定位用户接入实体
        var access = kaosUserAccessMapper.selectById(uid);
        if (access == null) {
            throw new RuntimeException("用户接入信息不存在");
        }

        return this.genToken(access);
    }

    /**
     * 生成token
     * 
     * @param kaosUserAccess
     * @return
     */
    private String genToken(@NotNull(message = "账户接入信息不能为空") KaosUserAccess kaosUserAccess) {
        var builder = JWT.create();
        LocalDateTime now = LocalDateTime.now();
        builder.withKeyId(now.toString()); // 混淆Header段
        builder.withAudience(kaosUserAccess.getUserCode(), now.toString()); // 插入用户数据和时间段混淆
        builder.withExpiresAt(now.plus(safeDuration).plus(alertDuration).atZone(defaultZoneId).toInstant());
        String tokenPrivateKey = specialPrefix + kaosUserAccess.getTokenMask() + kaosUserAccess.getPassword();
        return builder.sign(Algorithm.HMAC256(tokenPrivateKey));
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

        // 定位用户实体
        var user = kaosUserMapper.selectById(uid);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        return new Pair<User, String>(new User(user.getUserCode(), user.getUserName()), null);
    }

    /**
     * 处理特殊token
     * 
     * @param uid
     * @return
     * @throws Exception
     */
    private Pair<User, String> parseNormalToken(String token) throws Exception {
        // 声明新token和用户
        KaosUser user = null;
        String newToken = null;

        // token解码
        DecodedJWT decodedJWT = null;
        try {
            decodedJWT = JWT.decode(token);
        } catch (Exception e) {
            throw new RuntimeException("token格式异常");
        }

        // 获取系统用户
        var uid = decodedJWT.getAudience().get(0);

        // 定位用户实体
        user = kaosUserMapper.selectById(uid);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        // 定位用户接入实体
        var kaosUserAccess = kaosUserAccessMapper.selectById(uid);
        if (kaosUserAccess == null) {
            throw new RuntimeException("用户接入信息不存在");
        }

        // 校验token
        String tokenPrivateKey = specialPrefix + kaosUserAccess.getTokenMask() + kaosUserAccess.getPassword();
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(tokenPrivateKey)).build();
        try {
            jwtVerifier.verify(token);
        } catch (Exception e) {
            throw new RuntimeException("token校验失败");
        }

        // token有效期校验
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expire = LocalDateTime.ofInstant(decodedJWT.getExpiresAtAsInstant(), ZoneId.systemDefault());
        LocalDateTime alert = expire.minus(alertDuration);
        if (now.isAfter(expire)) {
            throw new TokenExpireException("token已过期");
        } else if (now.isAfter(alert)) {
            newToken = this.genToken(kaosUserAccess);
        }

        return new Pair<User, String>(new User(user.getUserCode(), user.getUserName()), newToken);
    }
}
