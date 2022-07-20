package com.kaos.walnut.api.logic.controller.inpatient.escort.annex;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.constraints.NotBlank;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.kaos.walnut.api.data.his.entity.kaos.EscortAnnexInfo;
import com.kaos.walnut.api.data.his.entity.xyhis.FinIprInMainInfo;
import com.kaos.walnut.api.data.his.entity.xyhis.FinIprInMainInfo.InStateEnum;
import com.kaos.walnut.api.data.his.mapper.kaos.EscortAnnexChkMapper;
import com.kaos.walnut.api.data.his.mapper.kaos.EscortAnnexInfoMapper;
import com.kaos.walnut.api.data.his.mapper.kaos.EscortMainInfoMapper;
import com.kaos.walnut.api.data.his.mapper.xyhis.FinIprInMainInfoMapper;
import com.kaos.walnut.core.type.MediaType;
import com.kaos.walnut.core.type.annotations.ApiName;
import com.kaos.walnut.core.util.collection.ListUtils;

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
@RequestMapping("/api/inpatient/escort/annex")
class AnnexController {
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
     * 附件信息接口
     */
    @Autowired
    EscortAnnexInfoMapper escortAnnexInfoMapper;

    /**
     * 附件信息接口
     */
    @Autowired
    EscortAnnexChkMapper escortAnnexChkMapper;

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

        // 检索陪护
        Set<String> helperCardNo = Sets.newHashSet();
        patients.forEach(x -> {
            escortMainInfoMapper.selectByPatient(x.getCardNo(), true).forEach(y -> {
                helperCardNo.add(y.getHelperCardNo());
            });
        });

        // 检索附件记录
        Map<String, Object> result = Maps.newHashMap();
        List<Map<String, Object>> checked = Lists.newArrayList();
        List<Map<String, Object>> unchecked = Lists.newArrayList();
        result.put("checked", checked);
        result.put("unchecked", unchecked);

        // 处理所有陪护人
        helperCardNo.forEach(x -> {
            // 创建分发篓
            List<EscortAnnexInfo> trueList = Lists.newLinkedList();
            List<EscortAnnexInfo> falseList = Lists.newLinkedList();

            // 分发 - 规则：上传时间倒序，是否审核
            var wrapper = new LambdaQueryWrapper<EscortAnnexInfo>();
            wrapper.eq(EscortAnnexInfo::getCardNo, x);
            wrapper.orderByDesc(EscortAnnexInfo::getOperDate);
            ListUtils.distribute(escortAnnexInfoMapper.selectList(wrapper), y -> {
                return escortAnnexChkMapper.selectById(y.getAnnexNo()) != null;
            }, trueList, falseList);

            // 加入最新的元素
            if (!trueList.isEmpty()) {
                var inf = trueList.get(0);
                var chk = escortAnnexChkMapper.selectById(inf.getAnnexNo());
                Map<String, Object> resultItem = Maps.newHashMap();
                resultItem.put("annexNo", inf.getAnnexNo());
                resultItem.put("cardNo", inf.getCardNo());
                resultItem.put("annexUrl", inf.getAnnexUrl());
                resultItem.put("negative", chk.getNegative());
                resultItem.put("execDate", chk.getExecDate());
                checked.add(resultItem);
            }
            if (!falseList.isEmpty()) {
                var inf = trueList.get(0);
                Map<String, Object> resultItem = Maps.newHashMap();
                resultItem.put("annexNo", inf.getAnnexNo());
                resultItem.put("cardNo", inf.getCardNo());
                resultItem.put("annexUrl", inf.getAnnexUrl());
                checked.add(resultItem);
            }
        });

        return result;
    }
}
