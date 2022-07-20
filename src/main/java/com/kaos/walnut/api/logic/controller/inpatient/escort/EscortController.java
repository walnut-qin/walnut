package com.kaos.walnut.api.logic.controller.inpatient.escort;

import java.util.Map;

import javax.validation.constraints.NotBlank;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Maps;
import com.kaos.walnut.api.data.his.entity.xyhis.FinIprInMainInfo;
import com.kaos.walnut.api.data.his.entity.xyhis.FinIprInMainInfo.InStateEnum;
import com.kaos.walnut.api.data.his.mapper.kaos.EscortMainInfoMapper;
import com.kaos.walnut.api.data.his.mapper.xyhis.FinIprInMainInfoMapper;
import com.kaos.walnut.core.type.MediaType;
import com.kaos.walnut.core.type.annotations.ApiName;

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
@RequestMapping("/api/inpatient/escort")
public class EscortController {
    /**
     * 住院主表接口
     */
    @Autowired
    FinIprInMainInfoMapper finIprInMainInfoMapper;

    /**
     * 陪护主表接口
     */
    @Autowired
    EscortMainInfoMapper escortMainInfoMapper;

    /**
     * 查询患者信息
     * 
     * @param cardNo
     * @return
     */
    @ResponseBody
    @ApiName("获取科室陪护信息")
    @RequestMapping(value = "getDeptInfo", method = RequestMethod.GET, produces = MediaType.JSON)
    Map<String, Object> getDeptInfo(@NotBlank(message = "科室编码不能为空") String deptCode) {
        // 检索指定科室的患者清单
        var patientQueryWrapper = new LambdaQueryWrapper<FinIprInMainInfo>();
        patientQueryWrapper.eq(FinIprInMainInfo::getDeptCode, deptCode);
        patientQueryWrapper.eq(FinIprInMainInfo::getInState, InStateEnum.病房接诊);
        var patients = finIprInMainInfoMapper.selectList(patientQueryWrapper);

        // 构造响应体
        Map<String, Object> result = Maps.newHashMap();
        // result.put("patientNo", patient.getPatientNo());
        // result.put("cardNo", patient.getCardNo());
        // result.put("deptCode", patient.getDeptCode());
        // result.put("houseDocCode", patient.getHouseDocCode());
        // result.put("chargeDocCode", patient.getChargeDocCode());
        // result.put("chiefDocCode", patient.getChiefDocCode());

        return result;
    }
}
