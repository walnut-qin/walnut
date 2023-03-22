package com.kaos.walnut.api.logic.controller.surgery;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Maps;
import com.kaos.walnut.api.data.entity.MetOpsApply;
import com.kaos.walnut.api.data.mapper.MetOpsApplyMapper;
import com.kaos.walnut.api.data.mapper.MetOpsOperationItemMapper;
import com.kaos.walnut.core.type.MediaType;
import com.kaos.walnut.core.type.annotations.ApiName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/surgery/application")
public class ApplicationController {
    /**
     * 手术申请表接口
     */
    @Autowired
    MetOpsApplyMapper metOpsApplyMapper;

    /**
     * 手术申请项目表接口
     */
    @Autowired
    MetOpsOperationItemMapper metOpsOperationItemMapper;

    @ApiName("获取手术申请信息")
    @RequestMapping(value = "getInfo", method = RequestMethod.GET, produces = MediaType.JSON)
    Map<String, Object> getInfo(@RequestParam @NotBlank(message = "申请单号不能为空") String applyNo) {
        // 检索数据库
        var application = this.metOpsApplyMapper.selectById(applyNo);
        if (application == null) {
            return null;
        }

        // 检索item表
        var surgeryItem = this.metOpsOperationItemMapper.selectById(applyNo);
        if (surgeryItem == null) {
            return null;
        }

        // 声明结果
        Map<String, Object> result = Maps.newHashMap();
        result.put("applyNo", application.getApplyNo());
        result.put("icdCode", surgeryItem.getItemCode());
        result.put("icdName", surgeryItem.getItemName());
        result.put("state", application.getExecStatus());
        result.put("surgeryLevel", application.getDegree() == null ? null : application.getDegree().ordinal());
        result.put("surgeryType", application.getOpsKind());
        result.put("icuFlag", application.getIcuFlag());

        return result;
    }

    @ApiName("获取科室有效的手术申请")
    @RequestMapping(value = "getApplicationsInDept", method = RequestMethod.GET, produces = MediaType.JSON)
    List<Map<String, Object>> getApplicationsInDept(@RequestParam @NotBlank(message = "科室编码不能为空") String deptId,
            @RequestParam @NotNull(message = "开始时间不能为空") LocalDateTime beginDate,
            @RequestParam @NotNull(message = "结束时间不能为空") LocalDateTime endDate) {
        // 检索记录
        var wrapper = new QueryWrapper<MetOpsApply>().lambda();
        wrapper.eq(MetOpsApply::getApplyDeptCode, deptId);
        wrapper.between(MetOpsApply::getPreDate, beginDate, endDate);
        wrapper.eq(MetOpsApply::getYnValid, true);
        wrapper.eq(MetOpsApply::getIsCancel, false);
        var applications = this.metOpsApplyMapper.selectList(wrapper);

        // 实体映射
        return applications.stream().map(x -> {
            return this.getInfo(x.getApplyNo());
        }).toList();
    }
}
