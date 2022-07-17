package com.kaos.walnut.api.data.his.mapper.kaos;

import java.util.List;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kaos.walnut.api.data.his.entity.kaos.SurgeryDict;

@DS("his")
public interface SurgeryDictMapper extends BaseMapper<SurgeryDict> {
    /**
     * 查询被授权的手术
     * 
     * @param emplCode
     * @param deptCode
     * @return
     */
    List<SurgeryDict> selectGrantedList(String emplCode, String deptCode);
}
