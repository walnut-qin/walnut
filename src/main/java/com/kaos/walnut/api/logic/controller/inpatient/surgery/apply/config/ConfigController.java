package com.kaos.walnut.api.logic.controller.inpatient.surgery.apply.config;

import javax.validation.constraints.NotBlank;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kaos.walnut.api.data.his.entity.kaos.ConfigList;
import com.kaos.walnut.api.data.his.mapper.kaos.ConfigListMapper;
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
@RequestMapping("/api/inpatient/surgery/apply/config")
class ConfigController {
    /**
     * 开关接口
     */
    @Autowired
    ConfigListMapper configListMapper;

    /**
     * 查询人员开关
     * 
     * @param cardNo
     * @return
     */
    @ResponseBody
    @ApiName("获取高值耗材功能开关")
    @RequestMapping(value = "isHighValueConsumableEmpl", method = RequestMethod.GET, produces = MediaType.JSON)
    Boolean isHighValueConsumableEmpl(@NotBlank(message = "员工编码不能为空") String emplCode) {
        // 检索手术申请单
        var queryWrapper = new LambdaQueryWrapper<ConfigList>();
        queryWrapper.eq(ConfigList::getName, "SHVC_Empl");
        queryWrapper.eq(ConfigList::getValue, emplCode);
        queryWrapper.eq(ConfigList::getValid, true);
        var result = configListMapper.selectOne(queryWrapper);

        // 未查询到开关
        if (result == null) {
            return false;
        }

        return true;
    }

    /**
     * 查询人员开关
     * 
     * @param cardNo
     * @return
     */
    @ResponseBody
    @ApiName("获取高值耗材功能开关")
    @RequestMapping(value = "isHighValueConsumableDept", method = RequestMethod.GET, produces = MediaType.JSON)
    Boolean isHighValueConsumableDept(@NotBlank(message = "科室编码不能为空") String deptCode) {
        // 检索手术申请单
        var queryWrapper = new LambdaQueryWrapper<ConfigList>();
        queryWrapper.eq(ConfigList::getName, "SHVC_Dept");
        queryWrapper.eq(ConfigList::getValue, deptCode);
        queryWrapper.eq(ConfigList::getValid, true);
        var result = configListMapper.selectOne(queryWrapper);

        // 未查询到开关
        if (result == null) {
            return false;
        }

        return true;
    }
}
