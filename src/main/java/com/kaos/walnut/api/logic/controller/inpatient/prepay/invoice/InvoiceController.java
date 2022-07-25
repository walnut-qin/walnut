package com.kaos.walnut.api.logic.controller.inpatient.prepay.invoice;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Maps;
import com.kaos.walnut.api.data.his.entity.xyhis.FinIpbInPrepay;
import com.kaos.walnut.api.data.his.enums.TransTypeEnum;
import com.kaos.walnut.api.data.his.mapper.xyhis.FinComElectronicInvoiceMapper;
import com.kaos.walnut.api.data.his.mapper.xyhis.FinIpbInPrepayMapper;
import com.kaos.walnut.api.logic.service.inpatient.prepay.invoice.InvoiceService;
import com.kaos.walnut.core.tool.lock.Lock;
import com.kaos.walnut.core.tool.lock.LockExecutor;
import com.kaos.walnut.core.type.MediaType;
import com.kaos.walnut.core.type.annotations.ApiName;
import com.kaos.walnut.core.util.ObjectUtils;

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
@RequestMapping("/api/inpatient/prepay/invoice")
class InvoiceController {
    /**
     * 锁
     */
    Lock lock = new Lock("电子发票修复锁", 20);

    /**
     * 住院主表缓存
     */
    @Autowired
    FinIpbInPrepayMapper finIpbInPrepayMapper;

    /**
     * 电子票接口
     */
    @Autowired
    FinComElectronicInvoiceMapper electronicInvoiceMapper;

    /**
     * 事务
     */
    @Autowired
    InvoiceService invoiceService;

    /**
     * 查询患者信息
     * 
     * @param cardNo
     * @return
     */
    @ResponseBody
    @ApiName("获取住院患者开过电子发票的预交金")
    @RequestMapping(value = "getInfo", method = RequestMethod.GET, produces = MediaType.JSON)
    List<Map<String, Object>> getBoSoftInvoices(@NotBlank(message = "住院号不能为空") String patientNo) {
        // 调用服务
        QueryWrapper<FinIpbInPrepay> wrapper = new QueryWrapper<>();
        wrapper.eq("INPATIENT_NO", "ZY01".concat(patientNo));
        wrapper.orderByAsc("HAPPEN_NO");
        var prepays = finIpbInPrepayMapper.selectList(wrapper);

        // 构造响应体
        return prepays.stream().map(x -> {
            // 检索
            var electronicInvoice = electronicInvoiceMapper.selectByMultiId(x.getReceiptNo(), TransTypeEnum.Positive);
            if (electronicInvoice == null) {
                return null;
            }

            // 构造响应
            Map<String, Object> item = Maps.newHashMap();
            item.put("receiptNo", electronicInvoice.getInvoiceNo());
            item.put("billBatchNo", electronicInvoice.getBillBatchCode());
            item.put("billNo", electronicInvoice.getBillNo());
            item.put("random", electronicInvoice.getRandom());
            return item;
        }).filter(Objects::nonNull).toList();
    }

    /**
     * 隔日召回修改预交金
     * 
     * @param reqBody
     * @return
     */
    @ResponseBody
    @ApiName("修复预交金电子票据信息")
    @RequestMapping(value = "fixData", method = RequestMethod.POST, produces = MediaType.JSON)
    Object fixData(@RequestBody @Valid FixData.ReqBody reqBody) throws Exception {
        LockExecutor.link(lock, reqBody.receiptNo);
        LockExecutor.execute(() -> {
            invoiceService.fixInvoiceData(reqBody.receiptNo);
        });
        return ObjectUtils.EMPTY;
    }

    /**
     * fixRecallPrepay的请求和响应body
     */
    static class FixData {
        /**
         * 请求body
         */
        static class ReqBody {
            /**
             * 住院号
             */
            @NotBlank(message = "预交金票据号不能为空")
            String receiptNo;
        }
    }
}
