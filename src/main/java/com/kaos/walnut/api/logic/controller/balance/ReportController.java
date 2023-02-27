/*********************************************************
 * File: ReportController.java
 * Created Date: 2023-02-09
 * Author: walnut(覃鹏展)
 * 
 * Description:
 *  日结接口
 * 
 * Copyright (C) 2023 襄阳市中心医院
 *********************************************************/

package com.kaos.walnut.api.logic.controller.balance;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.kaos.walnut.api.data.entity.FinIpbBalanceHead;
import com.kaos.walnut.api.data.entity.FinIpbDayReport;
import com.kaos.walnut.api.data.entity.FinIpbDayReportDetail;
import com.kaos.walnut.api.data.mapper.FinIpbBalanceHeadMapper;
import com.kaos.walnut.api.data.mapper.FinIpbDayReportDetailMapper;
import com.kaos.walnut.api.data.mapper.FinIpbDayReportMapper;
import com.kaos.walnut.api.logic.controller.balance.ReportController.QueryNewYbCost.ReqBody.TypeEnum;
import com.kaos.walnut.core.type.Enum;
import com.kaos.walnut.core.type.MediaType;
import com.kaos.walnut.core.type.annotations.ApiName;
import com.kaos.walnut.core.util.DoubleUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Validated
@RestController
@RequestMapping("/api/balance/report")
public class ReportController {
    /**
     * 结算头表接口
     */
    @Autowired
    FinIpbBalanceHeadMapper finIpbBalanceHeadMapper;

    /**
     * 日结总表接口
     */
    @Autowired
    FinIpbDayReportMapper finIpbDayReportMapper;

    /**
     * 日结明细表接口
     */
    @Autowired
    FinIpbDayReportDetailMapper finIpbDayReportDetailMapper;

    /**
     * 计算新医保的日结数据
     * 
     * @param reqBody
     * @return
     */
    @ApiName("查询新医保日结")
    @RequestMapping(value = "queryNewYbCost", method = RequestMethod.POST, produces = MediaType.JSON)
    Double queryNewYbCost(@RequestBody @Valid QueryNewYbCost.ReqBody reqBody) {
        // 检索相关结算数据
        var queryWrapper = new QueryWrapper<FinIpbBalanceHead>().lambda();
        queryWrapper.eq(FinIpbBalanceHead::getBalanceOperCode, reqBody.balancer);
        queryWrapper.between(FinIpbBalanceHead::getBalanceDate, reqBody.beginDate, reqBody.endDate);
        queryWrapper.eq(FinIpbBalanceHead::getPactCode, 18);
        var balanceHeads = this.finIpbBalanceHeadMapper.selectList(queryWrapper);

        // 算和
        var sum = balanceHeads.stream().mapToDouble(x -> {
            var reg = switch (reqBody.type) {
                case PUB -> x.getPubCost();
                case PAY -> x.getPayCost();
            };
            return DoubleUtils.eraseNull(reg);
        }).sum();

        return Math.round(sum * 100) / 100.0;
    }

    static class QueryNewYbCost {
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
                PUB("统筹", "1"),
                PAY("账户", "2");

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

    /**
     * 检查近期数据的正确性
     */
    @ApiName("核对日结数据正确性")
    @RequestMapping(value = "checkData", method = RequestMethod.POST, produces = MediaType.JSON)
    List<CheckData.ListNode> checkData(@RequestBody @Valid CheckData.ReqBody reqBody) {
        // 声明结果集
        List<CheckData.ListNode> result = Lists.newArrayList();

        // 检索所有指定时间段内的日结记录
        var qWrapper = new QueryWrapper<FinIpbDayReport>().lambda();
        qWrapper.ge(FinIpbDayReport::getBeginDate, reqBody.beginDate);
        qWrapper.le(FinIpbDayReport::getEndDate, reqBody.endDate);
        var reports = this.finIpbDayReportMapper.selectList(qWrapper);

        // 核对
        for (FinIpbDayReport report : reports) {
            // 检索实际日结数据
            var detailBaseWrapper = new QueryWrapper<FinIpbDayReportDetail>().lambda();
            detailBaseWrapper.eq(FinIpbDayReportDetail::getStatNo, report.getStatNo());
            var pubWrapper = detailBaseWrapper.clone();
            pubWrapper.eq(FinIpbDayReportDetail::getStatCode, "市直医保_门诊");
            var payWrapper = detailBaseWrapper.clone();
            payWrapper.eq(FinIpbDayReportDetail::getStatCode, "市直统筹市直外伤");

            // 结算信息查询器
            var param = new QueryNewYbCost.ReqBody();
            param.balancer = report.getOperCode();
            param.beginDate = report.getBeginDate();
            param.endDate = report.getEndDate();

            // 对比统筹数据
            var pubData = this.finIpbDayReportDetailMapper.selectOne(pubWrapper);
            param.type = TypeEnum.PUB;
            var pubNodeData = new CheckData.NodeData();
            pubNodeData.recData = pubData == null ? 0 : pubData.getTotCost();
            pubNodeData.realData = this.queryNewYbCost(param);

            // 对比账户数据
            var payData = this.finIpbDayReportDetailMapper.selectOne(payWrapper);
            param.type = TypeEnum.PAY;
            var payNodeData = new CheckData.NodeData();
            payNodeData.recData = payData == null ? 0 : payData.getTotCost();
            payNodeData.realData = this.queryNewYbCost(param);

            // 加入结果集
            if (!pubNodeData.isCorrect() || !payNodeData.isCorrect()) {
                var listNode = new CheckData.ListNode();
                listNode.statNo = report.getStatNo();
                listNode.balancer = report.getOperCode();
                listNode.beginDate = report.getBeginDate();
                listNode.endDate = report.getEndDate();
                if (!pubNodeData.isCorrect()) {
                    listNode.pubData = pubNodeData;
                }
                if (!payNodeData.isCorrect()) {
                    listNode.payData = payNodeData;
                }
                result.add(listNode);
            }
        }

        return result;
    }

    static class CheckData {
        static class ReqBody {
            /**
             * 开始时间
             */
            @NotNull(message = "开始时间不能为空")
            LocalDateTime beginDate;

            /**
             * 结束时间
             */
            @NotNull(message = "结束时间不能为空")
            LocalDateTime endDate;
        }

        static class ListNode {
            /**
             * 统筹数据
             */
            NodeData pubData;

            /**
             * 账户数据
             */
            NodeData payData;

            /**
             * 日结编号
             */
            String statNo;

            /**
             * 结算员
             */
            String balancer;

            /**
             * 开始时间
             */
            LocalDateTime beginDate;

            /**
             * 截至时间
             */
            LocalDateTime endDate;
        }

        static class NodeData {
            /**
             * 记录的数据
             */
            Double recData;

            /**
             * 实际数据
             */
            Double realData;

            /**
             * 判断合法性
             * 
             * @return
             */
            Boolean isCorrect() {
                return recData.equals(realData);
            }
        }
    }
}
