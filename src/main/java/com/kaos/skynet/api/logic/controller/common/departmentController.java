package com.kaos.skynet.api.logic.controller.common;

import java.util.Map;

import javax.validation.constraints.NotBlank;

import com.google.common.collect.Maps;
import com.kaos.skynet.api.data.his.cache.xyhis.DawnOrgDeptCache;
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
@RequestMapping("/api/common/department")
public class departmentController {
    /**
     * 住院主表缓存
     */
    @Autowired
    DawnOrgDeptCache dawnOrgDeptCache;

    /**
     * 查询患者信息
     * 
     * @param cardNo
     * @return
     */
    @ResponseBody
    @ApiName("获取科室基本信息")
    @RequestMapping(value = "getInfo", method = RequestMethod.GET, produces = MediaType.JSON)
    Map<String, Object> getInfo(@NotBlank(message = "科室编码不能为空") String deptCode) {
        // 调用服务
        var dept = this.dawnOrgDeptCache.get(deptCode);
        if (dept == null) {
            throw new RuntimeException("科室不存在!");
        }

        // 构造响应体
        Map<String, Object> result = Maps.newHashMap();
        result.put("deptCode", dept.getDeptCode());
        result.put("deptName", dept.getDeptName());
        result.put("deptOwn", dept.getDeptOwn());

        return result;
    }
}
