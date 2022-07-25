package com.kaos.walnut.core.tool;

import java.net.Inet4Address;
import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * 运行环境
 */
@Component
public class LocalEnvironment {
    /**
     * 系统环境
     */
    @Autowired
    Environment env;

    /**
     * 获取本地IPv4地址
     * 
     * @return
     * @throws UnknownHostException
     */
    public String getLocalHostAddress() throws UnknownHostException {
        return Inet4Address.getLocalHost().getHostAddress();
    }

    /**
     * 获取监听的端口号
     */
    public Integer getListenedPort() {
        return Integer.parseInt(env.getProperty("local.server.port"));
    }

    /**
     * 获取本地URI前缀
     * 
     * @return http://xxx.xxx.xxx.xxx:xxxx
     */
    public String getLocalAddress() throws UnknownHostException {
        return String.format("http://%s:%d", getLocalHostAddress(), getListenedPort());
    }
}
