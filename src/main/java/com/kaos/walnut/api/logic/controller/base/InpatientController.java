package com.kaos.walnut.api.logic.controller.base;

import java.util.Map;

import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;
import com.kaos.walnut.api.data.mapper.ComPatientInfoMapper;
import com.kaos.walnut.api.data.mapper.DawnOrgDeptMapper;
import com.kaos.walnut.api.data.mapper.DawnOrgEmplMapper;
import com.kaos.walnut.api.data.mapper.FinIprInMainInfoMapper;
import com.kaos.walnut.core.type.MediaType;
import com.kaos.walnut.core.type.annotations.ApiName;

@Validated
@CrossOrigin
@RestController
@RequestMapping("/api/base/inpatient")
public class InpatientController {
    /**
     * 住院接口
     */
    @Autowired
    FinIprInMainInfoMapper finIprInMainInfoMapper;

    /**
     * 患者接口
     */
    @Autowired
    ComPatientInfoMapper comPatientInfoMapper;

    /**
     * 医师接口
     */
    @Autowired
    DawnOrgEmplMapper dawnOrgEmplMapper;

    /**
     * 科室接口
     */
    @Autowired
    DawnOrgDeptMapper dawnOrgDeptMapper;

    /**
     * 获取住院患者实体
     * 
     * @param patientNo
     * @return
     */
    @ApiName("获取住院患者信息")
    @RequestMapping(value = "get", method = RequestMethod.GET, produces = MediaType.JSON)
    Map<String, Object> get(@RequestParam @NotBlank(message = "住院号不能为空") String patientNo) {
        // 读取住院记录
        var inpatient = this.finIprInMainInfoMapper.selectById("ZY01" + patientNo);
        if (inpatient == null) {
            throw new RuntimeException("未检索到住院记录");
        }

        // 构造响应
        Map<String, Object> result = Maps.newHashMap();
        result.put("patientNo", inpatient.getPatientNo());
        if (inpatient.getBedNo().startsWith(inpatient.getNurseCellCode())) {
            result.put("bedNo", inpatient.getBedNo().substring(inpatient.getNurseCellCode().length()));
        } else {
            result.put("bedNo", inpatient.getBedNo());
        }
        result.put("inState", inpatient.getInState());
        result.put("inDate", inpatient.getInDate());
        result.put("outDate", inpatient.getOutDate());

        // 加入就诊卡信息
        var card = this.comPatientInfoMapper.selectById(inpatient.getCardNo());
        if (card != null) {
            Map<String, Object> node = Maps.newHashMap();
            node.put("cardNo", card.getCardNo());
            node.put("name", card.getName());
            node.put("sex", card.getSex());
            result.put("card", node);
        }

        // 加入科室信息
        var dept = this.dawnOrgDeptMapper.selectById(inpatient.getDeptCode());
        if (dept != null) {
            Map<String, Object> node = Maps.newHashMap();
            node.put("code", dept.getDeptCode());
            node.put("name", dept.getDeptName());
            node.put("own", dept.getDeptOwn());
            result.put("dept", node);
        }

        // 加入病区信息
        var nurseCell = this.dawnOrgDeptMapper.selectById(inpatient.getNurseCellCode());
        if (nurseCell != null) {
            Map<String, Object> node = Maps.newHashMap();
            node.put("code", nurseCell.getDeptCode());
            node.put("name", nurseCell.getDeptName());
            node.put("own", nurseCell.getDeptOwn());
            result.put("nurseCell", node);
        }

        // 加入住院医师信息
        var houseDoc = this.dawnOrgEmplMapper.selectById(inpatient.getHouseDocCode());
        if (houseDoc != null) {
            Map<String, Object> node = Maps.newHashMap();
            node.put("code", houseDoc.getEmplCode());
            node.put("name", houseDoc.getEmplName());
            result.put("houseDoc", node);
        }

        // 加入上级医师信息
        var chargeDoc = this.dawnOrgEmplMapper.selectById(inpatient.getChargeDocCode());
        if (chargeDoc != null) {
            Map<String, Object> node = Maps.newHashMap();
            node.put("code", chargeDoc.getEmplCode());
            node.put("name", chargeDoc.getEmplName());
            result.put("chargeDoc", node);
        }

        // 加入住院医师信息
        var chiefDoc = this.dawnOrgEmplMapper.selectById(inpatient.getChiefDocCode());
        if (chiefDoc != null) {
            Map<String, Object> node = Maps.newHashMap();
            node.put("code", chiefDoc.getEmplCode());
            node.put("name", chiefDoc.getEmplName());
            result.put("chiefDoc", node);
        }

        return result;
    }
}
