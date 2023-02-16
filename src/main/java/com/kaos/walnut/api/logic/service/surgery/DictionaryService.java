package com.kaos.walnut.api.logic.service.surgery;

import javax.validation.constraints.NotNull;

import com.google.common.collect.Queues;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class DictionaryService {
    @Transactional
    public Integer importSurgery(@NotNull Workbook workbook) {
        // 读取sheet
        var sheet = workbook.getSheetAt(0);

        // 校验数据
        this.checkSheet(sheet);
        return 0;
    }

    private Boolean checkSheet(Sheet sheet) {
        // 校验header
        var header = sheet.getRow(0);
        if (!header.getCell(0).getStringCellValue().equals("手术编码")) {
            throw new RuntimeException("列信息校验失败: 首列不是手术编码");
        } else if (!header.getCell(4).getStringCellValue().equals("授权医师")) {
            throw new RuntimeException("列信息校验失败: 第5列不是授权医师");
        }

        // 行校验
        for (var row : sheet) {
            // 跳过header
            if (row.getRowNum() == 0) {
                continue;
            }

            // 数据校验
            var icd = row.getCell(0);
            var doctor = Queues.newArrayDeque();
            for (Integer i = 4; i < row.getLastCellNum(); i++) {
                doctor.push(row.getCell(i));
            }

            // 写日志
            log.info(String.format("手术<%s>授权给%s", icd, doctor));
        }
        return true;
    }
}
