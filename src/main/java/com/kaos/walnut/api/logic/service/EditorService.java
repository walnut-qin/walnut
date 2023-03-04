package com.kaos.walnut.api.logic.service;

import java.time.Duration;
import java.time.format.DateTimeFormatter;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kaos.walnut.api.data.entity.MedOperationMaster;
import com.kaos.walnut.api.data.mapper.MedOperationMasterMapper;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.hutool.core.util.RandomUtil;

@Service
public class EditorService {
    /**
     * 手术主表接口
     */
    @Autowired
    MedOperationMasterMapper medOperationMasterMapper;

    /**
     * 制作假数据
     * 
     * @param orgData
     * @return
     */
    @Transactional
    public Workbook makeFakeData(Workbook data) {
        // 锚定表单
        var sheet = data.getSheetAt(0);

        // 批量处理
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            // 锚定行
            var row = sheet.getRow(i);

            // 修改数据
            this.makeFakeRow(row);

            // 达到指定行数后结束逻辑
            if (i == 6832) {
                break;
            }
        }

        return data;
    }

    /**
     * 造行数据
     * 
     * @param row
     */
    private void makeFakeRow(Row row) {
        // 读取主键
        var patientId = row.getCell(0).toString();
        var visitId = Double.valueOf(row.getCell(1).toString()).intValue();
        var operId = Double.valueOf(row.getCell(2).toString()).intValue();

        // 检索数据库数据
        var queryWrapper = new QueryWrapper<MedOperationMaster>().lambda();
        queryWrapper.eq(MedOperationMaster::getPatientId, patientId);
        queryWrapper.eq(MedOperationMaster::getVisitId, visitId);
        queryWrapper.eq(MedOperationMaster::getOperId, operId);
        var data = this.medOperationMasterMapper.selectOne(queryWrapper);

        // 读取到手术结束时间
        var endDataTime = data.getEndDateTime();
        var inDateTime = endDataTime.plus(Duration.ofSeconds(RandomUtil.randomLong(300, 600)));
        var outDateTime = inDateTime.plus(Duration.ofSeconds(RandomUtil.randomLong(600, 1200)));

        // 设置新的复苏时间
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if (row.getCell(4) == null) {
            row.createCell(4, CellType.STRING);
        }
        row.getCell(4).setCellValue(inDateTime.format(formatter));
        if (row.getCell(5) == null) {
            row.createCell(5, CellType.STRING);
        }
        row.getCell(5).setCellValue(outDateTime.format(formatter));
    }
}
