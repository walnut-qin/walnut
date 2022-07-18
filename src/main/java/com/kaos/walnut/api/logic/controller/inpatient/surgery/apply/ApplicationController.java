package com.kaos.walnut.api.logic.controller.inpatient.surgery.apply;

import java.util.Map;

import javax.validation.constraints.NotBlank;

import com.google.common.collect.Maps;
import com.kaos.walnut.api.data.his.mapper.xyhis.MetOpsApplyMapper;
import com.kaos.walnut.core.type.MediaType;
import com.kaos.walnut.core.type.annotations.ApiName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Validated
@CrossOrigin
@RestController
@RequestMapping("/api/inpatient/surgery/apply")
public class ApplicationController {
    /**
     * 手术申请单接口
     */
    @Autowired
    MetOpsApplyMapper metOpsApplyMapper;

    /**
     * 查询患者信息
     * 
     * @param cardNo
     * @return
     */
    @ResponseBody
    @ApiName("获取住院患者信息")
    @RequestMapping(value = "getInfo", method = RequestMethod.GET, produces = MediaType.JSON)
    Map<String, Object> getInfo(@NotBlank(message = "住院号不能为空") String applyNo) {
        // 检索手术申请单
        var applyInfo = metOpsApplyMapper.selectById(applyNo);
        if (applyInfo == null) {
            String errMsg = "未检索到手术申请单, applyNo = ".concat(applyNo);
            log.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        // 构造响应体
        Map<String, Object> result = Maps.newHashMap();
        result.put("applyNo", applyInfo.getApplyNo());
        result.put("icuFlag", applyInfo.getIcuFlag());

        return result;
    }
}
