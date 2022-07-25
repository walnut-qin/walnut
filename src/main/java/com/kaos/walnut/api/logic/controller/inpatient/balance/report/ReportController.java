package com.kaos.walnut.api.logic.controller.inpatient.balance.report;

import java.time.LocalDateTime;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Maps;
import com.kaos.walnut.api.data.his.entity.xyhis.FinIpbBalanceHead;
import com.kaos.walnut.api.data.his.mapper.xyhis.FinIpbBalanceHeadMapper;
import com.kaos.walnut.core.type.MediaType;
import com.kaos.walnut.core.type.annotations.ApiName;
import com.kaos.walnut.core.util.DoubleUtils;
import com.kaos.walnut.core.util.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Validated
@CrossOrigin
@RestController
@RequestMapping("/api/inpatient/balance/report")
class ReportController {
    /**
     * 结算头表接口
     */
    @Autowired
    FinIpbBalanceHeadMapper finIpbBalanceHeadMapper;

    @ResponseBody
    @ApiName("抽取日结信息")
    @RequestMapping(value = "fetchInfo", method = RequestMethod.POST, produces = MediaType.JSON)
    Map<String, Object> fetchInfo(@RequestBody @Valid FetchInfo.ReqBody reqBody) {
        // 获取符合条件的未日结的信息
        var queryWrapper = new LambdaQueryWrapper<FinIpbBalanceHead>();
        queryWrapper.eq(FinIpbBalanceHead::getBalanceOperCode, reqBody.operCode);
        queryWrapper.between(FinIpbBalanceHead::getBalanceDate, reqBody.beginDate, reqBody.endDate);
        queryWrapper.eq(FinIpbBalanceHead::getDaybalanceFlag, false);
        var balanceHeads = finIpbBalanceHeadMapper.selectList(queryWrapper).stream();

        // 构造响应
        Map<String, Object> result = Maps.newHashMap();

        // 医保
        Map<String, Object> medIns = Maps.newHashMap();
        {
            // 过滤出新医保数据
            var data = balanceHeads.filter(x -> StringUtils.equals(x.getPactCode(), "18")).toList();
            medIns.put("pubCost", data.stream().mapToDouble(x -> DoubleUtils.eraseNull(x.getPubCost())).sum());
            medIns.put("payCost", data.stream().mapToDouble(x -> DoubleUtils.eraseNull(x.getPayCost())).sum());
        }
        result.put("medIns", medIns);

        // 返回响应数据
        return result;
    }

    static class FetchInfo {
        static class ReqBody {
            /**
             * 结算员编码
             */
            @NotBlank(message = "结算员不能为空")
            String operCode;

            /**
             * 日结开始时间
             */
            @NotNull(message = "日结开始时间不能为空")
            LocalDateTime beginDate;

            /**
             * 日结结束时间
             */
            @NotNull(message = "日结结束时间不能为空")
            LocalDateTime endDate;
        }
    }
}
