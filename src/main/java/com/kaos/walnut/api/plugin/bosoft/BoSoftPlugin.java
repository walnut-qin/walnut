package com.kaos.walnut.api.plugin.bosoft;

import com.kaos.walnut.api.plugin.bosoft.BoSoftReqWrapper.ReqBody;
import com.kaos.walnut.core.tool.RestTemplateWrapper;

import org.springframework.stereotype.Component;

@Component
public class BoSoftPlugin {
    /**
     * web接口
     */
    final RestTemplateWrapper restTemplateWrapper;

    /**
     * 请求包装器
     */
    final BoSoftReqWrapper boSoftReqWrapper;

    /**
     * 请求包装器
     */
    final BoSoftRspWrapper boSoftRspWrapper;

    /**
     * API前缀
     */
    final String apiPrefix;

    /**
     * 构造函数
     */
    public BoSoftPlugin() {
        restTemplateWrapper = new RestTemplateWrapper("172.16.100.123", 17001);
        boSoftReqWrapper = new BoSoftReqWrapper();
        boSoftRspWrapper = new BoSoftRspWrapper();
        apiPrefix = "/medical-web/api/medical/";
    }

    /**
     * 发送POST请求
     * 
     * @param <R>      请求类型
     * @param <S>      响应类型
     * @param apiType  业务类型
     * @param data     请求数据
     * @param classOfS 响应数据类型
     * @return
     */
    public <R, S> BoSoftData<S> postForObject(String apiType, R data, Class<S> classOfS) {
        // 加密请求信息
        ReqBody reqBody = boSoftReqWrapper.wrap(data);

        // 发送post请求
        var rspBody = restTemplateWrapper.post(apiPrefix.concat(apiType), reqBody, BoSoftRspWrapper.RspBody.class);

        // 解密并抽取响应
        return boSoftRspWrapper.unwrap(rspBody, classOfS);
    }
}
