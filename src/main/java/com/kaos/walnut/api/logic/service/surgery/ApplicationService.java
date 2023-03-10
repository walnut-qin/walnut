package com.kaos.walnut.api.logic.service.surgery;

import java.util.Map;

import javax.validation.constraints.NotBlank;

import com.kaos.walnut.api.data.mapper.MetOpsApplyMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 手术基本业务
 */
@Service
public class ApplicationService {
    /**
     * 手术申请单接口
     */
    @Autowired
    MetOpsApplyMapper metOpsApplyMapper;

    /**
     * 通过手术申请单号获取手术申请单信息
     * 
     * @param applyNo 手术申请单号
     * @return
     */
    public Map<String, Object> getInfo(@NotBlank(message = "手术申请单号不能为空") String applyNo) {
        return null;
    }
}
