package com.kaos.walnut.api.logic.service.outpatient.register;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kaos.walnut.api.data.his.entity.xyhis.FinOprPayModel;
import com.kaos.walnut.api.data.his.entity.xyhis.FinOprRegister;
import com.kaos.walnut.api.data.his.enums.TransTypeEnum;
import com.kaos.walnut.api.data.his.mapper.xyhis.FinOprPayModelMapper;
import com.kaos.walnut.api.data.his.mapper.xyhis.FinOprRegisterMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class RegisterService {
    @Autowired
    FinOprRegisterMapper finOprRegisterMapper;

    @Autowired
    FinOprPayModelMapper finOprPayModelMapper;

    /**
     * 退号
     */
    @Transactional
    public void cancel(String clinicCode) {
        // 检索挂号记录
        var queryWrapper1 = new LambdaQueryWrapper<FinOprRegister>();
        queryWrapper1.eq(FinOprRegister::getClinicCode, clinicCode);
        queryWrapper1.eq(FinOprRegister::getTransType, TransTypeEnum.Positive);
        var register = finOprRegisterMapper.selectOne(queryWrapper1);
        if (register == null) {
            String errMsg = "挂号记录不存在, clinicCode = ".concat(clinicCode);
            log.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        // 检索付费记录
        var queryWrapper2 = new LambdaQueryWrapper<FinOprPayModel>();
        queryWrapper2.eq(FinOprPayModel::getClinicCode, clinicCode);
        var payModels = finOprPayModelMapper.selectList(queryWrapper2);
        switch (payModels.size()) {
            case 0 -> {
                String errMsg = "费用记录不存在, clinicCode = ".concat(clinicCode);
                log.error(errMsg);
                throw new RuntimeException(errMsg);
            }
            case 1 -> {
                break;
            }
            default -> {
                String errMsg = "费用记录不唯一, clinicCode = ".concat(clinicCode);
                log.error(errMsg);
                throw new RuntimeException(errMsg);
            }
        }
        // var payModel = payModels.get(0);
    }
}
