package com.kaos.walnut.api.logic.Risu;

import com.kaos.walnut.api.data.his.cache.xyhis.ComPatientInfoCache;
import com.kaos.walnut.api.data.his.entity.xyhis.ComPatientInfo;
import com.kaos.walnut.core.type.Risu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PatientNameRisu implements Risu<String, String> {
    @Autowired
    ComPatientInfoCache comPatientInfoCache;

    @Override
    public String run(String cardNo) {
        // 检索患者基本信息表
        ComPatientInfo comPatientInfo = comPatientInfoCache.get(cardNo);
        if (comPatientInfo != null) {
            return comPatientInfo.getName();
        }

        // 翻译失败，直接返回卡号
        return cardNo;
    }
}
