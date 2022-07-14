package com.kaos.skynet.api.logic.controller.common;

import java.time.LocalDate;
import java.time.Period;
import java.util.Map;

import javax.validation.constraints.NotBlank;

import com.google.common.collect.Maps;
import com.kaos.skynet.api.data.his.cache.xyhis.ComPatientInfoCache;
import com.kaos.skynet.core.type.MediaType;
import com.kaos.skynet.core.type.annotations.ApiName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Validated
@CrossOrigin
@RestController
@RequestMapping("/api/common/patient")
class patientController {
    /**
     * 住院主表缓存
     */
    @Autowired
    ComPatientInfoCache comPatientInfoCache;

    /**
     * 查询患者信息
     * 
     * @param cardNo
     * @return
     */
    @ResponseBody
    @ApiName("获取患者基本信息")
    @RequestMapping(value = "getInfo", method = RequestMethod.GET, produces = MediaType.JSON)
    Map<String, Object> getInfo(@NotBlank(message = "就诊卡号不能为空") String cardNo) {
        // 调用服务
        var patient = this.comPatientInfoCache.get(cardNo);
        if (patient == null) {
            throw new RuntimeException("就诊卡不存在!");
        }

        // 构造响应体
        Map<String, Object> result = Maps.newHashMap();
        result.put("cardNo", patient.getCardNo());
        result.put("name", patient.getName());
        result.put("sex", patient.getSex());
        result.put("age", Period.between(patient.getBirthday().toLocalDate(), LocalDate.now()));
        result.put("idenNo", patient.getIdenNo());
        result.put("tel", patient.getHomeTel());

        return result;
    }
}
