package com.kaos.walnut.api.logic.service.invoice.inpatient;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Maps;
import com.kaos.walnut.api.data.entity.FinIpbBalanceHead;
import com.kaos.walnut.api.data.enums.TransTypeEnum;
import com.kaos.walnut.api.data.mapper.FinIpbBalanceHeadMapper;
import com.kaos.walnut.core.util.LocalDateTimeUtils;

/**
 * 住院发票业务
 */
@Service
public class BalanceService {
    /**
     * 结算头表接口
     */
    @Autowired
    FinIpbBalanceHeadMapper finIpbBalanceHeadMapper;

    /**
     * 开具住院结算电子发票
     * 
     * @param invoiceNo
     * @param alipayCode
     * @param weChatOrderNo
     * @param openId
     * @param sourceType
     * @return
     */
    public Map<String, String> genBalanceInvoice(String invoiceNo, String alipayCode, String weChatOrderNo, String openId, String sourceType) {
        // 声明局部数据
        Map<String, String> result = Maps.newHashMap();
        Map<String, String> param = Maps.newHashMap();

        // 填入信息
        this.fillCommonInfo(param);
        this.fillBalanceInfo(param, invoiceNo);

        return result;
    }

    /**
     * 填入公共信息
     * 
     * @param param 参数表
     */
    private void fillCommonInfo(Map<String, String> param) {
    }

    /**
     * 填入结算信息
     * 
     * @param invoiceNo 发票号
     * @param param 参数表
     */
    private void fillBalanceInfo(Map<String, String> param, String invoiceNo) {
        // 检索结算记录
        var wrapper = new QueryWrapper<FinIpbBalanceHead>().lambda();
        wrapper.eq(FinIpbBalanceHead::getInvoiceNo, invoiceNo);
        wrapper.eq(FinIpbBalanceHead::getTransType, TransTypeEnum.Positive);
        var balanceHead = this.finIpbBalanceHeadMapper.selectOne(wrapper);
        if (balanceHead == null) {
            throw new RuntimeException(String.format("未检索到结算信息，发票号：%s", invoiceNo));
        }

        // 填写信息
        param.put("busNo", String.format("%s-%s", LocalDateTimeUtils.format(LocalDateTime.now(), "yyyyMMddHHmmssSSS"), invoiceNo));
    }
}
