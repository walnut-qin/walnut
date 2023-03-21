package com.kaos.walnut.api.logic.controller.surgery;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotBlank;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.kaos.walnut.api.logic.service.surgery.privilege.InfoService;
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
@RequestMapping("/api/surgery/dictionary")
public class DictionaryController {
    /**
     * 权限信息业务
     */
    @Autowired
    InfoService privilegeInfoService;

    @ApiName("获取授权的手术")
    @RequestMapping(value = "getGrantedSurgeries", method = RequestMethod.GET, produces = MediaType.JSON)
    public List<Map<String, Object>> getGrantedSurgeries(@RequestParam @NotBlank(message = "患者科室不能为空") String deptId,
            @RequestParam @NotBlank(message = "医师编码不能为空") String emplId) {
        // 检索被授权手术
        var surgeries = this.privilegeInfoService.getGrantedSurgeries(deptId, emplId);

        // 构造响应
        List<Map<String, Object>> result = Lists.newArrayList();
        surgeries.forEach(x -> {
            Map<String, Object> item = Maps.newHashMap();
            item.put("icdCode", x.getIcdCode());
            item.put("icdName", x.getIcdName());
            item.put("surgeryLevel", x.getSurgeryLevel());
            item.put("spellCode", x.getSpellCode());
            result.add(item);
        });
        return result;
    }
}
