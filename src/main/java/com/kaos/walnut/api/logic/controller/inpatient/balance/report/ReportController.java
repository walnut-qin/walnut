package com.kaos.walnut.api.logic.controller.inpatient.balance.report;

import java.time.LocalDateTime;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.base.Optional;
import com.kaos.walnut.api.data.his.entity.xyhis.FinIpbBalanceHead;
import com.kaos.walnut.api.data.his.mapper.xyhis.FinIpbBalanceHeadMapper;
import com.kaos.walnut.core.type.Enum;
import com.kaos.walnut.core.type.MediaType;
import com.kaos.walnut.core.type.annotations.ApiName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
    @ApiName("查询新医保日结")
    @RequestMapping(value = "queryNewYbDayBalanceCost", method = RequestMethod.POST, produces = MediaType.JSON)
    Double queryNewYbDayBalanceCost(@RequestBody @Valid QueryNewYbDayBalanceCost.ReqBody reqBody) {
        // 获取符合条件的未日结的信息
        var queryWrapper = new LambdaQueryWrapper<FinIpbBalanceHead>();
        queryWrapper.eq(FinIpbBalanceHead::getBalanceOperCode, reqBody.balancer);
        queryWrapper.between(FinIpbBalanceHead::getBalanceDate, reqBody.beginDate, reqBody.endDate);
        queryWrapper.eq(FinIpbBalanceHead::getPactCode, "18");
        var balanceHeads = finIpbBalanceHeadMapper.selectList(queryWrapper);

        // 算和
        Double cost = balanceHeads.stream().mapToDouble(x -> {
            return Optional.fromNullable(switch (reqBody.type) {
                case PUB -> x.getPubCost();
                case PAY -> x.getPayCost();
            }).or(0.0);
        }).sum();

        // 返回响应数据
        return cost;
    }

    static class QueryNewYbDayBalanceCost {
        static class ReqBody {
            /**
             * 结算员
             */
            @NotBlank(message = "结算员不能为空")
            String balancer;

            /**
             * 开始时间
             */
            @NotNull(message = "开始时间不能为空")
            LocalDateTime beginDate;

            /**
             * 截至时间
             */
            @NotNull(message = "截止时间不能为空")
            LocalDateTime endDate;

            /**
             * 类型
             */
            @NotNull(message = "查询类型不能为空")
            TypeEnum type;

            /**
             * 类型
             */
            @Getter
            @AllArgsConstructor
            enum TypeEnum implements Enum {
                PUB("1", "1"),
                PAY("2", "2");

                /**
                 * 数据库存值
                 */
                String value;

                /**
                 * 描述存值
                 */
                String description;
            }
        }
    }
}
