package com.kaos.walnut.api.logic.controller.common;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Maps;
import com.kaos.walnut.api.data.his.cache.xyhis.DawnOrgDeptCache;
import com.kaos.walnut.api.data.his.entity.xyhis.DawnOrgDept;
import com.kaos.walnut.api.data.his.enums.ValidEnum;
import com.kaos.walnut.api.data.his.mapper.xyhis.DawnOrgDeptMapper;
import com.kaos.walnut.core.type.MediaType;
import com.kaos.walnut.core.type.annotations.ApiName;
import com.kaos.walnut.core.util.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
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
    DawnOrgDeptMapper dawnOrgDeptMapper;

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

    /**
     * 查询患者信息
     * 
     * @param cardNo
     * @return
     */
    @ResponseBody
    @ApiName("获取科室基本信息")
    @RequestMapping(value = "fetchInfos", method = RequestMethod.POST, produces = MediaType.JSON)
    List<Map<String, Object>> fetchInfos(@RequestBody @Valid GetInfos.ReqBody reqBody) {
        // 调用服务
        var queryWrapper = new LambdaQueryWrapper<DawnOrgDept>();
        queryWrapper.eq(DawnOrgDept::getValid, ValidEnum.VALID);
        if (!StringUtils.isBlank(reqBody.keyword)) {
            queryWrapper.likeRight(DawnOrgDept::getDeptName, reqBody.keyword);
        }
        var depts = this.dawnOrgDeptMapper.selectList(queryWrapper);

        // 构造响应体
        return depts.stream().map(x -> {
            Map<String, Object> result = Maps.newHashMap();
            result.put("deptCode", x.getDeptCode());
            result.put("deptName", x.getDeptName());
            result.put("deptOwn", x.getDeptOwn());
            return result;
        }).toList();
    }

    static class GetInfos {
        static class ReqBody {
            /**
             * 关键字
             */
            String keyword;
        }
    }
}
