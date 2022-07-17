package com.kaos.walnut.api.logic.controller.inpatient.surgery.dictionary;

import java.util.Map;

import com.google.common.collect.Maps;
import com.kaos.walnut.api.data.his.mapper.kaos.SurgeryDictMapper;
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
@RequestMapping("/api/inpatient/surgery/dictionary")
class DictionaryController {
    /**
     * 手术字典表
     */
    @Autowired
    SurgeryDictMapper surgeryDictMapper;

    @ResponseBody
    @ApiName("获取手术字典")
    @RequestMapping(value = "getInfo", method = RequestMethod.GET, produces = MediaType.JSON)
    Map<String, Object> getInfo() {
        // 构造响应体
        Map<String, Object> result = Maps.newHashMap();

        return result;
    }

    static class GetInfo {
        static class ReqBody {
            /**
             * 科室编码
             */
            String deptCode;
        }
    }
}
