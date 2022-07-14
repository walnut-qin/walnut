package com.kaos.skynet.api.logic.controller.inpatient;

import java.util.Map;

import javax.validation.constraints.NotBlank;

import com.google.common.collect.Maps;
import com.kaos.skynet.api.data.his.cache.xyhis.FinIprInMainInfoCache;
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
@RequestMapping("/api/inpatient")
public class InpatientController {
    /**
     * 住院主表缓存
     */
    @Autowired
    FinIprInMainInfoCache finIprInMainInfoCache;

    /**
     * 查询患者信息
     * 
     * @param cardNo
     * @return
     */
    @ResponseBody
    @ApiName("获取住院患者信息")
    @RequestMapping(value = "getInfo", method = RequestMethod.GET, produces = MediaType.JSON)
    Map<String, Object> getInfo(@NotBlank(message = "住院号不能为空") String patientNo) {
        // 调用服务
        var patient = this.finIprInMainInfoCache.get("ZY01".concat(patientNo));
        if (patient == null) {
            throw new RuntimeException("住院号不存在!");
        }

        // 构造响应体
        Map<String, Object> result = Maps.newHashMap();
        result.put("patientNo", patient.getPatientNo());
        result.put("cardNo", patient.getCardNo());
        result.put("deptCode", patient.getDeptCode());
        result.put("houseDocCode", patient.getHouseDocCode());
        result.put("chargeDocCode", patient.getChargeDocCode());
        result.put("chiefDocCode", patient.getChiefDocCode());

        return result;
    }
}
