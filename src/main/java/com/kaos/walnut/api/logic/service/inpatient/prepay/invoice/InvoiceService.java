package com.kaos.walnut.api.logic.service.inpatient.prepay.invoice;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.kaos.walnut.api.data.his.entity.xyhis.FinComElectronicInvoice;
import com.kaos.walnut.api.data.his.enums.TransTypeEnum;
import com.kaos.walnut.api.data.his.mapper.xyhis.FinComElectronicInvoiceMapper;
import com.kaos.walnut.api.plugin.bosoft.BoSoftPlugin;
import com.kaos.walnut.core.util.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class InvoiceService {
    /**
     * 电子发票接口
     */
    @Autowired
    FinComElectronicInvoiceMapper electronicInvoiceMapper;

    /**
     * 博思接口
     */
    @Autowired
    BoSoftPlugin bsPlugin;

    /**
     * 修复信息
     */
    @Transactional
    public void fixInvoiceData(String invoiceNo) {
        // 检索中间表数据
        var queryWrapper = new LambdaQueryWrapper<FinComElectronicInvoice>();
        queryWrapper.eq(FinComElectronicInvoice::getInvoiceNo, invoiceNo);
        queryWrapper.eq(FinComElectronicInvoice::getTransType, TransTypeEnum.Positive);
        var data = electronicInvoiceMapper.selectOne(queryWrapper);
        if (data == null) {
            log.error("不存在的电子发票数据, invoiceNo = ".concat(invoiceNo));
            throw new RuntimeException("不存在的电子发票数据");
        }

        // 获取服务器数据
        var rsp = bsPlugin.postForObject("getEBillByBusNo", new Bosoft.ReqBody(data.getBusNo()), Bosoft.RspBody.class);
        if (rsp == null) {
            log.error("从博思服务器获取电子发票信息失败, invoiceNo = ".concat(invoiceNo));
            throw new RuntimeException("从博思服务器获取电子发票信息失败");
        }

        // 如果请求结果正常修改数据库
        if (StringUtils.equals(rsp.getCode(), "S0000")) {
            var updateWrapper = new UpdateWrapper<FinComElectronicInvoice>().lambda();
            updateWrapper.eq(FinComElectronicInvoice::getInvoiceNo, invoiceNo);
            updateWrapper.eq(FinComElectronicInvoice::getTransType, TransTypeEnum.Positive);
            updateWrapper.set(FinComElectronicInvoice::getBillBatchCode, rsp.getData().billBatchCode);
            updateWrapper.set(FinComElectronicInvoice::getBillNo, rsp.getData().billNo);
            updateWrapper.set(FinComElectronicInvoice::getRandom, rsp.getData().random);
            var cnt = electronicInvoiceMapper.update(null, updateWrapper);
            if (cnt != 1) {
                throw new RuntimeException("多条电子发票中间表数据被修改");
            }
        }
    }

    /**
     * 接口Body
     */
    static class Bosoft {
        public static class ReqBody {
            /**
             * 业务流水号
             */
            String busNo;

            /**
             * 业务发生时间
             */
            String busDateTime;

            /**
             * 单参数构造
             * 
             * @param busNo
             */
            public ReqBody(String busNo) {
                this.busNo = busNo;
                this.busDateTime = busNo.split("-")[0];
            }
        }

        static class RspBody {
            /**
             * 电子票据代码
             */
            String billBatchCode;

            /**
             * 电子票据号码
             */
            String billNo;

            /**
             * 电子校验码
             */
            String random;
        }
    }
}
