package com.kaos.walnut.api.logic.Risu;

import com.kaos.walnut.api.data.his.cache.xyhis.DawnOrgEmplCache;
import com.kaos.walnut.api.data.his.entity.xyhis.DawnOrgEmpl;
import com.kaos.walnut.core.type.Risu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 从员工编码翻译到员工姓名
 */
@Component
public class EmplNameRisu implements Risu<String, String> {
    @Autowired
    DawnOrgEmplCache dawnOrgEmplCache;

    @Override
    public String run(String emplCode) {
        // 检索HIS员工表
        DawnOrgEmpl dawnOrgEmpl = dawnOrgEmplCache.get(emplCode);
        if (dawnOrgEmpl != null) {
            return dawnOrgEmpl.getEmplName();
        }

        // 无法翻译则返回原始code
        return emplCode;
    }
}
