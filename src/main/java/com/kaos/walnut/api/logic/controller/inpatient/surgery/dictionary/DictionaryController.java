package com.kaos.walnut.api.logic.controller.inpatient.surgery.dictionary;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.google.common.collect.Maps;
import com.kaos.walnut.api.data.his.cache.kaos.SurgeryDeptPrivCache;
import com.kaos.walnut.api.data.his.cache.kaos.SurgeryEmplPrivCache;
import com.kaos.walnut.api.data.his.mapper.kaos.SurgeryDictMapper;
import com.kaos.walnut.core.type.MediaType;
import com.kaos.walnut.core.type.annotations.ApiName;

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
@RequestMapping("/api/inpatient/surgery/dictionary")
class DictionaryController {
    /**
     * 手术字典表
     */
    @Autowired
    SurgeryDictMapper surgeryDictMapper;

    /**
     * 人员授权表
     */
    @Autowired
    SurgeryEmplPrivCache surgeryEmplPrivCache;

    /**
     * 科室授权表
     */
    @Autowired
    SurgeryDeptPrivCache surgeryDeptPrivCache;

    @ResponseBody
    @ApiName("获取授权的手术字典")
    @RequestMapping(value = "fetchGrantedInfo", method = RequestMethod.POST, produces = MediaType.JSON)
    List<Map<String, Object>> fetchGrantedInfo(@RequestBody @Valid GetInfo.ReqBody reqBody) {
        // 检索有授权的手术
        var surgeryDicts = surgeryDictMapper.selectGrantedList(reqBody.emplCode, reqBody.deptCode);

        // 构造响应体
        return surgeryDicts.stream().map(x -> {
            Map<String, Object> result = Maps.newHashMap();
            result.put("icdCode", x.getIcdCode());
            result.put("surgeryName", x.getSurgeryName());
            result.put("surgeryLevel", x.getSurgeryLevel());
            result.put("keyword", x.getKeyword());
            // 授权人员名单
            var emplList = surgeryEmplPrivCache.get(x.getIcdCode()).stream().map(y -> y.getEmplCode()).toList();
            result.put("employee", emplList);
            result.put("employeeString", String.join(",", emplList));
            // 授权科室名单
            var deptList = surgeryDeptPrivCache.get(x.getIcdCode()).stream().map(y -> y.getDeptCode()).toList();
            result.put("department", deptList);
            result.put("departmentString", String.join(",", deptList));
            return result;
        }).toList();
    }

    static class GetInfo {
        static class ReqBody {
            /**
             * 科室编码
             */
            String emplCode;

            /**
             * 科室编码
             */
            String deptCode;
        }
    }
}
