package com.kaos.walnut.api.logic.controller.balance;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kaos.walnut.core.type.MediaType;
import com.kaos.walnut.core.type.annotations.ApiName;

@Validated
@RestController
@RequestMapping("/api/balance/invoice")
public class InvoiceController {

    /**
     * 开具住院结算电子发票
     * 
     * @param reqBody 请求参数
     * @return
     */
    @ApiName("开具住院结算电子票")
    @RequestMapping(value = "genInpatientBalanceInvoice", method = RequestMethod.POST, produces = MediaType.JSON)
    public Object genInpatientBalanceInvoice(@RequestBody @Valid Parameter.ReqBody reqBody) {
        return null;
    }

    /**
     * 参数
     */
    static class Parameter {
        /**
         * 通用请求参数
         */
        static class ReqBody {
            /**
             * 发票号 - HIS系统内部发票
             */
            @NotBlank(message = "发票号为空")
            String invoiceNo;

            /**
             * 请求源
             */
            @NotNull(message = "请求来源为空")
            String sourceType;

            /**
             * 支付宝小程序码
             */
            String alipayCode;

            /**
             * 微信小程序码
             */
            String weChatOrderNo;

            /**
             * 微信公众号码
             */
            String openId;
        }
    }
}
