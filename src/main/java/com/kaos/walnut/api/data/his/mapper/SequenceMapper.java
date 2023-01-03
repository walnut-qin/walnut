package com.kaos.walnut.api.data.his.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;

import org.apache.ibatis.annotations.Param;

@DS("his")
public interface SequenceMapper {
    /**
     * 查询序列的值
     * 
     * @param seqName
     * @return
     */
    String query(@Param("seqName") String seqName);
}
