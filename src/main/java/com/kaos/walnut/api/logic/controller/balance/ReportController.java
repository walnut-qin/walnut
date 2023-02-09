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

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.kaos.walnut.api.data.mapper.FinIpbBalanceHeadMapper;
import com.kaos.walnut.core.type.Enum;
import com.kaos.walnut.core.type.MediaType;
import com.kaos.walnut.core.type.annotations.ApiName;

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

    @ApiName("查询新医保日结")
    @RequestMapping(value = "queryNewYbCost", method = RequestMethod.POST, produces = MediaType.JSON)
    Double queryNewYbCost(@RequestBody @Valid QueryNewYbCost.ReqBody reqBody) {
        return 0.0;
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
}
