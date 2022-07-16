package com.kaos.walnut.api.data.his.mapper.xyhis;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kaos.walnut.api.data.his.entity.xyhis.FinComElectronicInvoice;
import com.kaos.walnut.api.data.his.enums.TransTypeEnum;

@DS("his")
public interface FinComElectronicInvoiceMapper extends BaseMapper<FinComElectronicInvoice> {
    /**
     * 主键查询
     * 
     * @param inoiceNo
     * @param transType
     * @return
     */
    FinComElectronicInvoice selectByMultiId(String invoiceNo, TransTypeEnum transType);
}
